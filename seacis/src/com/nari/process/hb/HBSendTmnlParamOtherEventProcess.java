package com.nari.process.hb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.client.EventBean;
import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.ami.database.map.runcontrol.RTmnlRun;
import com.nari.ami.database.map.terminalparam.TTaskTemplate;
import com.nari.ami.database.map.terminalparam.TTmnlTask;
import com.nari.ami.database.map.terminalparam.TTmnlTaskId;
import com.nari.coherence.TaskDeal;
import com.nari.eventbean.BackFeedEvent;
import com.nari.eventbean.RelayMeterReadingEvent;
import com.nari.eventbean.SendTmnlParamEvent;
import com.nari.eventbean.SendTmnlMpParamEvent;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.DbData;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.RealDataItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.ResponseItem;
import com.nari.global.Constant;
import com.nari.global.Globals;
import com.nari.model.CMeter;
import com.nari.model.TbgTask;
import com.nari.model.TbgTaskDetail;
import com.nari.service.MarketTerminalConfigManager;
import com.nari.service.SynDataForMarketingManager;
import com.nari.util.StringUtil;
import com.oracle.coherence.common.logging.Logger;

/**
 * 下发终端参数（其他非国网规约）
 */
@SuppressWarnings("unused")
public class HBSendTmnlParamOtherEventProcess implements Runnable{
	
	private static final Log log = LogFactory.getLog(HBSendTmnlParamOtherEventProcess.class);
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public HBSendTmnlParamOtherEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents=newEvents;
		this.oldEvents=oldEvents;
	}
	private MarketTerminalConfigManager marketTerminalConfigManager =  null;
	TaskDeal taskDeal = new TaskDeal();
	
	public void run() {
		log.info("【下发终端参数......】");
		marketTerminalConfigManager=(MarketTerminalConfigManager)Constant.getCtx().getBean("marketTerminalConfigManager");
		
		String appNo = (String) newEvents[0].get("appNo");
		String terminalId = (String) newEvents[0].get("terminalId");
		String tmnlAssetNo = (String) newEvents[0].get("tmnlAssetNo");
		Long debugId = (Long) newEvents[0].get("debugId");
		
		String Status = null;

		//普通终端
		//插入测试进度，将测试进度置为"下发终端参数开始"
		marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLPARAM_START);
		Status = sendTmnlParam(tmnlAssetNo, appNo);
		if("3".equals(Status)){
			//插入测试进度，将测试进度置为"下发终端参数成功"
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLPARAM_SUCCESS);
			//负控终端工作正常
			marketTerminalConfigManager.updateRTmnlDebugTerminalFlag(debugId, "1");
			//终端与主站通讯正常
			marketTerminalConfigManager.updateRTmnlDebugMasterCommFlag(debugId, "1");
			//考虑到终端从电能表读取数据频率，建议下发成功后等10分钟再抄读数据
			try {
				//Thread.sleep(1000 * 60 * 10);//测试的时候等5秒
				log.info("【下发终端参数后请等待......】");
				int sleepTime = 500;
				sleepTime = marketTerminalConfigManager.getSleepTime();//取等待时间
				Thread.sleep(1000 * sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//触发事件：抄读电表数据
			this.meterReadingRun(appNo, terminalId, tmnlAssetNo, debugId);
//			this.sendTaskRun(appNo, terminalId, tmnlAssetNo, debugId);
		}else{
			//插入测试进度，将测试进度置为"下发终端参数失败"
			//根据状态确定失败原因
			String failCause = "";
			if("1".equals(Status)){
				failCause = "终端不在线";
			}else if("4".equals(Status)){
				failCause = "通讯失败";
			}
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLPARAM_FAILED, failCause);
			//负控终端工作不正常
			marketTerminalConfigManager.updateRTmnlDebugTerminalFlag(debugId, "0");
			//终端与主站通讯不正常
			marketTerminalConfigManager.updateRTmnlDebugMasterCommFlag(debugId, "0");
			//反馈营销
			log.info("【下发终端参数失败】, tmnlAssetNo = " + tmnlAssetNo);
			Constant.sendEvent(new BackFeedEvent(appNo, debugId));
			return;
		}
	}
	
	/**
	 * 抄读电表数据
	 */
	public void meterReadingRun(String appNo, String terminalId, String tmnlAssetNo, Long debugId){
		log.info("抄读电表数据开始...");
		String Status = meterReading(tmnlAssetNo);
		if("3".equals(Status)){
			//更新测试状态为：抄读电表数据成功
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_METERREADING_SUCCESS);
			//终端与电能表通讯正常
			marketTerminalConfigManager.updateRTmnlDebugMeterCommFlag(debugId, "1");
			//下发任务
			this.sendTaskRun(appNo, terminalId, tmnlAssetNo, debugId);
		}else{
			//更新测试状态为：抄读电表数据失败
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_METERREADING_FAILED);
			//终端与电能表通讯正常
			marketTerminalConfigManager.updateRTmnlDebugMeterCommFlag(debugId, "0");
			log.info("【抄读电表数据失败】, tmnlAssetNo = " + tmnlAssetNo);
			//触发事件：反馈营销
			Constant.sendEvent(new BackFeedEvent(appNo, debugId));
			return;
		}
		log.info("抄读电表数据结束...");
	}
	
	/**
	 * 下发任务参数
	 */
	public void sendTaskRun(String appNo, String terminalId, String tmnlAssetNo, Long debugId){
		log.info("下发任务参数开始...");	
		String templateIdList = getTemplateIdByTmnlAssetNo(tmnlAssetNo);
		if(templateIdList != null && !"".equals(templateIdList)){
			String[] templateId = templateIdList.split(",");
			for(int i = 0; i < templateId.length; i++){
				String mpSn = "0";//对集中器才冻结数据
				this.updateTaskParameters(tmnlAssetNo, templateId[i], mpSn);
			}
		}
		
		//下发参数，冻结时间
		String Status = this.sendTask(tmnlAssetNo);
		if("3".equals(Status)){
			//更新测试状态为：下发任务成功
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTASKPARAM_SUCCESS);
			//更新测试状态为：//测试成功
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_END);
		}else{
			//更新测试状态为：下发任务失败
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTASKPARAM_FAILED);
		}
		//反馈营销
		Constant.sendEvent(new BackFeedEvent(appNo, debugId));
		log.info("下发任务参数结束...");
	}
	
	public String sendTask(String tmnlAssetNo){
		String Status = "";
		//终端资产号集合
		List<String> tmnlAssetNos = new ArrayList<String>();
		tmnlAssetNos.add(tmnlAssetNo);
		// 参数项设置
		List<ParamItem> params = new ArrayList<ParamItem>();
		
		ParamItem pitem = new ParamItem();
		//04/05规约设置F9、F10；698规约只设置F10
		pitem.setFn((short) Integer.parseInt("3"));//FN
		pitem.setPoint((short) Integer.parseInt("0"));//PN
		List<Item> items = new ArrayList<Item>();
		Item item1 = new Item("70403001");
		Item item2 = new Item("70403002");
		Item item3 = new Item("70403003");
		item1.setValue("99");//【日，99表示每天】
		item2.setValue("00");//【时，99表示每小时】
		item3.setValue("05");//【分，可选值0-59】
		items.add(item1);
		items.add(item2);
		items.add(item3);
		pitem.setItems((ArrayList<Item>) items);
		params.add(pitem);
		
		Status = marketTerminalConfigManager.getStatusByTaskDeal(tmnlAssetNos, FrontConstant.SET_PARAMETERS, params);
		return Status;
	}
	
	/**
	 * 抄读电表数据过程
	 */
	public String meterReading(String tmnlAssetNo){
		String Status = "";
		//终端资产号集合
		List<String> tmnlAssetNos = new ArrayList<String>();
		tmnlAssetNos.add(tmnlAssetNo);
		String mpSn = "";
		String meterId = "";
		List<?> listMpSns = marketTerminalConfigManager.getMpSnsByTmnl(tmnlAssetNo);
		
		int flag = 0;
		if(listMpSns != null && listMpSns.size() > 0){	
			for(int i = 0; i < listMpSns.size(); i++){	
				List<RealDataItem> params = new ArrayList<RealDataItem>();
				List<Response> respList = null;
				Map<?, ?> mapMpSns = (Map<?, ?>) listMpSns.get(i);
				mpSn = String.valueOf(mapMpSns.get("MP_SN"));
				meterId = StringUtil.removeNull(mapMpSns.get("METER_ID"));
				
				//设置F33
				ArrayList<Item> codes = new ArrayList<Item>();
				Item item  = new Item("11140F");//当前有功电量（总、1-4费率）
				codes.add(item);
				
				RealDataItem pitem = new RealDataItem();
				pitem.setCodes(codes);
				pitem.setPoint((short) Integer.parseInt(mpSn));
				pitem.setFreezingType((short)4);
				params.add(pitem);
				
				//调用前置机
				TaskDeal taskDeal = new TaskDeal();
				taskDeal.callRealData(tmnlAssetNos, params);
				respList = taskDeal.getAllResponse(marketTerminalConfigManager.getListenTime());
				//取抄表结果
				if (respList != null && respList.size() > 0) {
					for (int j = 0; j < respList.size(); j++) {
						List<ResponseItem> list  = respList.get(j).getResponseItems();
						if(respList.get(j).getTaskStatus() == 3 && list != null){
							String value = list.get(0).getValue();
							String[] values = value.split(",");
							if(values == null) return null;
							String mr_num = "";
							if(values.length > 2){
								mr_num = values[1];
							}
							Status = "3";
							
							marketTerminalConfigManager.insertIMeterRead(meterId, "1", mr_num);
						}else{
							marketTerminalConfigManager.insertIMeterRead(meterId, "0", "");
							flag++;
							//抄表失败3次则退出
							if(flag >= 3){
								return "3";
							}
						}
					}
				}	
			}
		}
		Status = "3";//抄表不成功也走下面的流程。20100827+
		return Status;
	}

	/**
	 * 下发终端参数
	 * 
	 * @param tmnlAssetNo
	 * @param ameterAssetNo
	 * @param mpSn
	 * @throws Exception
	 */
	private String sendTmnlParam (String tmnlAssetNo, String appNo){
		
		String Status = "";
		//取终端规约
		String protocolCode = null;
		RTmnlRun rTmnlRun = new RTmnlRun();
		rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
		protocolCode = Integer.toString(Integer.parseInt(rTmnlRun.getProtocolCode()));
		
		if("7".equals(protocolCode)){//东软规约
			log.debug("【东软规约参数组装】");
			//判断终端参数中是否有对应的值
			List<?> listEDateMp = null;
			String regStatus = "0";
			listEDateMp = marketTerminalConfigManager.getMpSnsByTmnlAndStatus(tmnlAssetNo, regStatus);
			if(listEDateMp != null){
				for(int j = 0; j < listEDateMp.size(); j++){
					Map<?, ?> mapEDateMp = (Map<?, ?>) listEDateMp.get(j);
					String mpSn = StringUtil.removeNull(mapEDateMp.get("MP_SN"));
					String meterId = StringUtil.removeNull(mapEDateMp.get("METER_ID"));
					String commAddr = StringUtil.removeNull(mapEDateMp.get("COMM_ADDR"));
					//终端资产号集合
					List<String> tmnlAssetNos = new ArrayList<String>();
					tmnlAssetNos.add(tmnlAssetNo);
					// 参数项设置
					List<ParamItem> params = new ArrayList<ParamItem>();
					
					ParamItem pitem1 = new ParamItem();
					
					pitem1.setFn((short) 18);//FN
					pitem1.setPoint((short) Integer.parseInt("0"));//PN
					List<Item> items = setTmnlParam(tmnlAssetNo, mpSn, meterId, commAddr);
					pitem1.setItems((ArrayList<Item>) items);
					params.add(pitem1);
					
					if(!"".equals(commAddr)){//表地址为空不下发。
						Status = marketTerminalConfigManager.getStatusByTaskDeal(tmnlAssetNos, FrontConstant.SET_PARAMETERS, params);
						//更新终端参数T_TMNL_PARAM状态为04
						if("3".equals(Status)){
							String protItemNo = "70412";
							String blockSn = mpSn;
							marketTerminalConfigManager.updateTmnlParamOtherStatue(tmnlAssetNo, blockSn, protItemNo);
							
							//注册成功，更新注册状态
							marketTerminalConfigManager.updateMeterRegStatus(tmnlAssetNo, mpSn);
							log.info("电能表:" + meterId + " 测量点号:" + mpSn + ":注册成功");
						}
					}else{
						log.info("电能表" + meterId + ":表地址为空,注册失败");
					}
				}
			}else{
				Status = "3";
			}
		}
		Status = "3";//不成功也走下面的流程。20100827+
		return Status;
	}
	
	/**
	 * 设置终端参数
	 * @param mpSn 
	 * @param ameterAssetNo 
	 * @param tmnlAssetNo 
	 */
	private List<Item> setTmnlParam(String tmnlAssetNo, String mpSn, String meterId, String commAddr) {

		String protItemNo = "";//规约项数据编码
		List<Item> items = new ArrayList<Item>();
		
		//根据PROTOCOL_NO取PROT_ITEM_NO
		String protocolNo = "70412";//增加载波电能表
		List<?> listProtItem = marketTerminalConfigManager.getProtItemByProtocolNo(protocolNo);
		if(listProtItem != null){
			for(int i = 0; i < listProtItem.size(); i++){
				Map<?, ?> mapProtItem = (Map<?, ?>) listProtItem.get(i);
				protItemNo = StringUtil.removeNull(mapProtItem.get("PROT_ITEM_NO"));
				String protItemSn = String.valueOf(mapProtItem.get("PROT_ITEM_SN"));
				String protItemName = String.valueOf(mapProtItem.get("PROT_ITEM_NAME"));
				Item item = new Item(protItemNo);
				if("1".equals(protItemSn)){
					//表地址
					item.setValue(commAddr);
				}else if("2".equals(protItemSn)){
					//费率号
					item.setValue("1");
				}
				log.info("【下发终端参数】终端地址：" + tmnlAssetNo + "," + protItemName +":"+item.getValue());
				items.add(item);
				this.updateTmnlParam(tmnlAssetNo, mpSn, item);
			}
		}
		return items;
	}
	
	/**
	 * 更新终端参数
	 * @param mpSn 
	 * @param ameterAssetNo 
	 * @param tmnlAssetNo 
	 */
	private void updateTmnlParam(String tmnlAssetNo, String mpSn, Item item) {

			String blockSn = mpSn;//块序号
			String innerBlockSn = "";//块内序号
			String statusCode = Globals.SEND_STATUS_CODE_3;//状态编码.待下发参数
			String isValid = "";//是否有效
			//根据终端资产编号 和 块序号判断所属的规约项编码是否存在
			List<?> list = marketTerminalConfigManager.getTmnlParamByTmnlAssetNoAndBlockSn(tmnlAssetNo, blockSn);
			if(list != null && list.size() > 0){
				//设置这些参数是否无效 T_TMNL_PARAM :IS_VALID = 0
				isValid = "0";
				marketTerminalConfigManager.updateTmnlParam(tmnlAssetNo, mpSn, isValid);
			}
			//判断终端参数是否存在
			String protItemNo = item.getCode();//规约项数据编码
			String currentValue = item.getValue();//参数当前值
			
			innerBlockSn = marketTerminalConfigManager.getInnerBlockSnByProtItemNo(protItemNo);
			
			List<?> tmnlParam = marketTerminalConfigManager.getTmnlParamByKey(tmnlAssetNo, protItemNo, blockSn, innerBlockSn);
			if(tmnlParam != null && tmnlParam.size() > 0){
				//更新终端参数
				Map<?, ?> mapMpParam = (Map<?, ?>) tmnlParam.get(0);
				String historyValue = (String) mapMpParam.get("CURRENT_VALUE");//参数历史值
				Date lastSendTime = (Date) mapMpParam.get("SUCCESS_TIME");//上次下发时间
				//更新终端参数，并置为有效
				isValid = "1";
				marketTerminalConfigManager.updateTmnlParam(tmnlAssetNo, protItemNo, currentValue, historyValue, blockSn, innerBlockSn, statusCode, isValid,  lastSendTime);
			}else{
				//插入终端参数
				marketTerminalConfigManager.insertTmnlParam(tmnlAssetNo, protItemNo, currentValue, blockSn, innerBlockSn, statusCode);
			}
	}
	
	/**
	 * 调用分布式缓存保存任务
	 */
	private void updateTaskParameters(String tmnlAssetNo, String templateId, String mpSns) {
		try {
			IStatement statement = CoherenceStatement.getSharedInstance();	
			//取任务模板
			TTaskTemplate tTaskTemplate = new TTaskTemplate();
			tTaskTemplate = (TTaskTemplate)statement.getValueByKey(TTaskTemplate.class, Long.parseLong(templateId));
			//取终端任务
			TTmnlTask tTmnlTaskTemp = new TTmnlTask();
			tTmnlTaskTemp = this.marketTerminalConfigManager.findTaskByNoId(tmnlAssetNo, Long.parseLong(templateId));
			
			//删除任务（一个终端对同一个模板只能存在一个任务，在设置之前把原先的删除）
			if(tTmnlTaskTemp!=null){
				statement.removeValueByKey(TTmnlTask.class, tTmnlTaskTemp.getId());
			}
			
			//取终端规约
			String protocolCode = null;
			RTmnlRun rTmnlRun = new RTmnlRun();
			rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
			protocolCode = Integer.toString(Integer.parseInt(rTmnlRun.getProtocolCode()));
			//测量点个数
			int mpCnt = mpSns.split(",").length;
			
			//构造任务ID
			TTmnlTaskId tTmnlTaskId = new TTmnlTaskId();
			tTmnlTaskId.setTaskNo(tTaskTemplate.getTaskNo());
			tTmnlTaskId.setTmnlAssetNo(tmnlAssetNo);
			//构造任务
			TTmnlTask tTmnlTask = new TTmnlTask();
			tTmnlTask.setId(tTmnlTaskId);
			tTmnlTask.setTemplateId(Long.valueOf(templateId));
			tTmnlTask.setTaskProperty(tTaskTemplate.getTaskProperty());
			tTmnlTask.setMasterR(tTaskTemplate.getMasterR());
			tTmnlTask.setMpCnt((short) mpCnt);
			tTmnlTask.setMpSn(mpSns);
			tTmnlTask.setRecallPolicy(tTaskTemplate.getRecallPolicy());
			tTmnlTask.setSendTime(new Date());
			tTmnlTask.setEnableDate(new Date());
			tTmnlTask.setProtocolCode(protocolCode);
			tTmnlTask.setIsEnable(true);
			tTmnlTask.setIsValid(true);
			tTmnlTask.setStaffNo("");//staffNo只有在主站调用接口的时候才填入
			tTmnlTask.setStatusCode("04");
			tTmnlTask.setReferenceTime(genReferenceTime(tTaskTemplate.getReferenceTime(),tTaskTemplate.getRandomValue()));
		
			//this.tTmnlTaskDao.saveOrUpdate(tTmnlTask);
			//调用分布式缓存保存任务
			statement.executeUpdate(TTmnlTask.class, tTmnlTask.getId(), tTmnlTask);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 上报基准时间计算
	 * @param referTime 模板基准时间
	 * @param i 上报随机数
	 * @return 任务基准时间
	 */
	public String genReferenceTime(String referTime,int i){
		if(referTime == null || "".equals(referTime)) return "";
		referTime = referTime.replace("：", ":");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Double dValue = Math.random()*i;
		try {
			Date d = sdf.parse(referTime);
			Date d1 = new Date(d.getTime()+dValue.intValue()*1000);
			String s1 = sdf.format(d1);
			String tDate = sdf1.format(new Date());
			s1 = tDate + " " + s1;
			return s1;
		} catch (ParseException e) {
			return "";
		}
	}
	
	/*
	 * 根据终端资产号取模板Id
	 */
	private String getTemplateIdByTmnlAssetNo(String tmnlAssetNo){
		String consNo = "";//用户编号
		String templateIdList = "";//任务模板ID列表
		
		String orgNo = "";//供电单位编号
		String consType = "";//用电用户类型
		String gapGradeNo = "";//容量等级
		String tmnlTypeCode = "";//终端类型
		String protocolCode = "";//通信规约类型
		String factoryCode = "";//生产厂家
		String modelCode = "";//型号
		//用户终端关系
		List<?> listConsTmnlRela =  marketTerminalConfigManager.getConsTmnlRelaByTmnlAssetNo(tmnlAssetNo);
		if(listConsTmnlRela != null){
			Map<?, ?> mapConsTmnlRela = (Map<?, ?>) listConsTmnlRela.get(0);
			consNo = String.valueOf(mapConsTmnlRela.get("CONS_NO"));
			orgNo = String.valueOf(mapConsTmnlRela.get("ORG_NO"));
		}
		//用户表
		List<?> listCCons = marketTerminalConfigManager.getCConsByConsNo(consNo);
		if(listCCons != null){
			Map<?, ?> mapCCons = (Map<?, ?>) listCCons.get(0);
			gapGradeNo = String.valueOf(mapCCons.get("CAP_GRADE_NO"));
			consType = String.valueOf(mapCCons.get("CONS_TYPE"));
			
		}
		//运行终端
		RTmnlRun rTmnlRun = new RTmnlRun();
		rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
		tmnlTypeCode = rTmnlRun.getTerminalTypeCode();
		protocolCode = Integer.toString(Integer.parseInt(rTmnlRun.getProtocolCode()));
		factoryCode = rTmnlRun.getFactoryCode();
		modelCode = rTmnlRun.getId();
		
		//根据条件查询任务模板ID列表
		List<?> listTmnlConsTask = null;
		listTmnlConsTask = marketTerminalConfigManager.getTmnlConsTaskByGapGradeNo(consType, gapGradeNo);
		if(listTmnlConsTask == null){
			listTmnlConsTask = marketTerminalConfigManager.getTmnlConsTaskByGapGradeNo(consType);
		}
		log.info("【取模板列表】用户编号：" + consNo + ",容量等级：" + gapGradeNo);
		if(listTmnlConsTask != null){
			Map<?, ?> mapTmnlConsTask = (Map<?, ?>) listTmnlConsTask.get(0);
			templateIdList = (String) mapTmnlConsTask.get("TEMPLATE_ID_LIST");
			log.info("【取模板列表】用户编号：" + consNo + ",容量等级：" + gapGradeNo +"模板列表:"+templateIdList);
		}else{
			log.info("----------未取得该终端对应的模板列表----------");
		}
		
		return templateIdList;
	}
	
}