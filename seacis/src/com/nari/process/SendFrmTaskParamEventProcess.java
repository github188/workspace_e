package com.nari.process;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;
import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.ami.database.map.basicdata.BClearProtocol;
import com.nari.ami.database.map.runcontrol.RTmnlRun;
import com.nari.ami.database.map.terminalparam.TTaskTemplate;
import com.nari.ami.database.map.terminalparam.TTmnlTask;
import com.nari.ami.database.map.terminalparam.TTmnlTaskId;
import com.nari.coherence.TaskDeal;
import com.nari.eventbean.BackFeedEvent;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.global.Constant;
import com.nari.global.Globals;
import com.nari.model.TbgTask;
import com.nari.model.TbgTaskDetail;
import com.nari.service.MarketTerminalConfigManager;
import com.nari.util.ArrayToListUtil;
import com.nari.util.StringUtil;

/**
 * 下发任务参数
 */
@SuppressWarnings("unused")
public class SendFrmTaskParamEventProcess implements Runnable{
	
	private static final Log log = LogFactory.getLog(SendTaskParamEventProcess.class);
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public SendFrmTaskParamEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents=newEvents;
		this.oldEvents=oldEvents;
	}
	
	//public static final String TEMPLATE_ID = "103";//TODU 需要确认下发的任务模板,（模板是根据“用户容量C_CONS:CONTRACT_CAP”和“厂家”获取，需要新建表）
	public static final String AFN = "04";
	public static final String FN = "41";//F65
	public static final String[] FN_ITEM = {"001", "002", "003", "004", "005", "006"};
	private MarketTerminalConfigManager marketTerminalConfigManager =  null;
	TaskDeal taskDeal = new TaskDeal();

	public void run() {
		log.debug("【SendFrmTaskParamEventProcess下发任务参数开始......】");
		marketTerminalConfigManager=(MarketTerminalConfigManager)Constant.getCtx().getBean("marketTerminalConfigManager");
		
		String appNo = (String) newEvents[0].get("appNo");
		String terminalId = (String) newEvents[0].get("terminalId");
		String tmnlAssetNo = (String) newEvents[0].get("tmnlAssetNo");
		Long debugId = (Long) newEvents[0].get("debugId");
		String meterFlag = (String) newEvents[0].get("meterFlag"); //换表标识
		String Status = null;
		String flowStatus = "0"; //发送任务的结果标识
		String sendUpMode =  null;//终端是否自动上送

		try {
			//更新测试状态为：下发终端测量点任务开始
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTASKPARAM_START);
			
			//从终端流程取出明细
			Map<?,?> map = marketTerminalConfigManager.getIFlowProcess(appNo, terminalId, Globals.SEND_TASK);
			if (map != null) {
				flowStatus = StringUtil.removeNull(map.get("FLOW_STATUS"));
			}

			log.debug("【进入任务保存】");
			String mainMeters = this.getMainMeters(tmnlAssetNo);
			log.debug("主表的测量点号--------------------------------------"+mainMeters+"----------------------------------------------");
			
			String[] mainMetersArray = mainMeters.split(",");
			
			if (!"".equals(mainMeters) && mainMeters != null && mainMeters.length() > 0) {
				if (null == map || (map != null && !"0".equals(flowStatus))) {
					int k = 0;
					String templateIdList = getTemplateIdByTmnlAssetNo(tmnlAssetNo);
					if(templateIdList != null && !"".equals(templateIdList)) {
						String[] templateId = templateIdList.split(",");
						for(int i = 0; i < templateId.length; i++) {
							this.updateTaskParameters(tmnlAssetNo, templateId[i], mainMeters);
							log.debug("【保存任务成功】, tmnlAssetNo = " + tmnlAssetNo + "   templateId = " + templateId[i]);
						}
					}
				}
			}

			if (map == null || (map != null && !"0".equals(flowStatus))) {
				String meterTask = this.getTemplateIdByForOthers();//取得居民表任务模板ID
				String mpSns = "";

				mpSns = this.getMpSnsByTmnlAssetNo(tmnlAssetNo);
				List<?> allMpSn = ArrayToListUtil.arrayToList(mpSns.split(","));
				for (int j = 0; j < mainMetersArray.length; j++) {
					allMpSn.remove(mainMetersArray[j]);
				} 
				mpSns = Arrays.toString(allMpSn.toArray());
				mpSns = mpSns.substring(1, mpSns.length()-1);
				mpSns = mpSns.replaceAll(" ", "");
				
				this.updateTaskParameters(tmnlAssetNo, meterTask, mpSns);
				log.debug("【终端自动上送：保存任务成功】, tmnlAssetNo = " + tmnlAssetNo + "templateId = " + meterTask);
			}
			
			//成功
			flowStatus = "0";
			//保存流程明细
			marketTerminalConfigManager.updateIFlowProcess(appNo, tmnlAssetNo, Globals.SEND_TASK, flowStatus, "", "");

			if  (!"0".equals(flowStatus)) {
				marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTASKPARAM_FAILED);
			} else {
				marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTASKPARAM_SUCCESS);
			}
			
			List<?> list = marketTerminalConfigManager.checkSuccessOrFaliure(appNo, tmnlAssetNo);

			if (null != list && list.size() == 5) {
				marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_END);
				//更新终端状态
				marketTerminalConfigManager.updateTmnlStatus(terminalId);
				log.debug("******************************集抄安装调试流程结束******************************");
			} else {
				marketTerminalConfigManager.updateTestStatue(appNo, Globals.DEBUG_STATUE_FAILED);
			}	
			
			Constant.sendEvent(new BackFeedEvent(appNo, debugId));
			log.debug("SendFrmTaskParamEventProcess下发任务参数结束.....");
			
		} catch (Exception e) {
			e.printStackTrace();
			marketTerminalConfigManager.updateTestStatue(appNo, Globals.DEBUG_STATUE_FAILED);
		} finally {
			this.bulidChkUnit(terminalId);
			this.setParam(terminalId);
			this.updateConsSort(terminalId);
			
			log.debug("***********************************************************************************");
			log.debug("************************************集抄安装流程结束********************************");
			log.debug("***********************************************************************************");
		}
	}
	
	
	/*
	 * 下发任务参数
	 * 
	 */
	private String sendTaskParameters(String tmnlAssetNo, String templateId, String mpSns, String appNo, String ismain) {
		try {
			IStatement statement = CoherenceStatement.getSharedInstance();	
			if(statement == null){
				log.debug("----------分布式缓存连接失败----------");
			}
			TTaskTemplate tTaskTemplate = new TTaskTemplate();
			tTaskTemplate = (TTaskTemplate)statement.getValueByKey(TTaskTemplate.class, Long.parseLong(templateId));
			if(tTaskTemplate==null){
				log.debug("----------下发的任务模板不存在----------");
			}
			//构造测量点和透明规约编码的字符串用来设置数据标识
			List<BClearProtocol> bClearProtocolList = marketTerminalConfigManager.findAllByTemplateId(Long.parseLong(templateId));
			StringBuffer protocolMpsn = new StringBuffer();
			
			if(bClearProtocolList!=null){
				for(int i=0;i < bClearProtocolList.size();i++){
					String[] mpSnArray = mpSns.split(",");
					
					for(int j=0;j < mpSnArray.length;j++){
						protocolMpsn.append(mpSnArray[j]+":"+bClearProtocolList.get(i).getProtocolNo()+";");
					}
				}
			}
			String protocolStr = protocolMpsn.toString();
			
			//终端资产号集合
			List<String> tmnlAssetNos = new ArrayList<String>();
			tmnlAssetNos.add(tmnlAssetNo);
			
			//终端规约
			String protocolCode = null;
			RTmnlRun rTmnlRun = new RTmnlRun();
			rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
			protocolCode = Integer.toString(Integer.parseInt(rTmnlRun.getProtocolCode()));
			
			//根据数据类型构造paramList（参数项设置）
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			ParamItem pitem = new ParamItem();			//设置任务参数
			ParamItem pitem1 = new ParamItem();			//启用要下发的任务
			//根据任务模板的数据类型构造不同的下发参数（dataType:1--历史数据--1类数据，2--实时数据--2类数据）
			if(tTaskTemplate.getDataType()==1){
				pitem.setFn((short) 65);
				pitem.setPoint(Short.valueOf(tTaskTemplate.getTaskNo()));//下发任务的时候setPoint的值为任务号
				List<Item> items = new ArrayList<Item>();
				Item item1 = new Item(protocolCode+"0441001");
				Item item2 = new Item(protocolCode+"0441002");
				Item item3 = new Item(protocolCode+"0441003");
				Item item4 = new Item(protocolCode+"0441004");
				Item item5 = new Item(protocolCode+"0441005");
				Item item6 = new Item(protocolCode+"0441006");
				item1.setValue(tTaskTemplate.getReportCycleUnit());
				item2.setValue(String.valueOf(tTaskTemplate.getReportCycle()));
				item3.setValue(genReferenceTime(tTaskTemplate.getReferenceTime(),tTaskTemplate.getRandomValue()));
				item4.setValue(String.valueOf(tTaskTemplate.getTmnlR()));
				item5.setValue(String.valueOf(tTaskTemplate.getDataCount()));
				item6.setValue(protocolStr);
				items.add(item1);items.add(item2);
				items.add(item3);items.add(item4);
				items.add(item5);items.add(item6);
				pitem.setItems((ArrayList<Item>)items);
				paramList.add(pitem);
				
				pitem1.setFn((short)67);
				pitem1.setPoint(Short.valueOf(tTaskTemplate.getTaskNo()));
				List<Item> items1 = new ArrayList<Item>();
				Item item = new Item(protocolCode+"0443001");	//1类数据
				item.setValue("55");
				items1.add(item);
				pitem1.setItems((ArrayList<Item>)items1);
				paramList.add(pitem1);
			}else if(tTaskTemplate.getDataType()==2){
				pitem.setFn((short) 66);
				pitem.setPoint(Short.valueOf(tTaskTemplate.getTaskNo()));
				List<Item> items = new ArrayList<Item>();
				Item item1 = new Item(protocolCode+"0442001");
				Item item2 = new Item(protocolCode+"0442002");
				Item item3 = new Item(protocolCode+"0442003");
				Item item4 = new Item(protocolCode+"0442004");
				Item item5 = new Item(protocolCode+"0442005");
				Item item6 = new Item(protocolCode+"0442006");
				item1.setValue(tTaskTemplate.getReportCycleUnit());
				item2.setValue(String.valueOf(tTaskTemplate.getReportCycle()));
				item3.setValue(genReferenceTime(tTaskTemplate.getReferenceTime(),tTaskTemplate.getRandomValue()));
				item4.setValue(String.valueOf(tTaskTemplate.getTmnlR()));
				item5.setValue(String.valueOf(tTaskTemplate.getDataCount()));
				item6.setValue(protocolStr);
				items.add(item1);items.add(item2);
				items.add(item3);items.add(item4);
				items.add(item5);items.add(item6);
				pitem.setItems((ArrayList<Item>)items);
				paramList.add(pitem);
				
				pitem1.setFn((short)68);
				pitem1.setPoint(Short.valueOf(tTaskTemplate.getTaskNo()));
				List<Item> items1 = new ArrayList<Item>();
				Item item = new Item(protocolCode+"0444001");	//1类数据
				item.setValue("55");
				items1.add(item);
				pitem1.setItems((ArrayList<Item>)items1);
				paramList.add(pitem1);
			}
			//下发任务参数状态
			String Status = "";
			if ("1".equals(ismain)) { 
				Status = marketTerminalConfigManager.getStatusByTaskDeal(tmnlAssetNos, FrontConstant.SET_PARAMETERS, paramList);
			} else {               
				Status = marketTerminalConfigManager.getTaskStatusByTaskDeal(tmnlAssetNos, FrontConstant.SET_PARAMETERS, paramList, mpSns);
			}
			return Status;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
			log.debug("---------------保存任务结束------------------模板ID ："+templateId+"----------测量点号 ："+mpSns);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 根据终端资产号取测量点列表
	 */
	private String getMpSnsByTmnlAssetNo(String tmnlAssetNo){
		String mpSns = "";
		List<?> listMpSns = marketTerminalConfigManager.getMpSnsByTmnl(tmnlAssetNo);
		if(listMpSns != null){
			for(int i = 0; i < listMpSns.size(); i++){
				Map<?, ?> mapMpSns = (Map<?, ?>) listMpSns.get(i);
				if(i == 0){
					mpSns = String.valueOf(mapMpSns.get("MP_SN"));
				}else{
					mpSns = mpSns + "," + String.valueOf(mapMpSns.get("MP_SN"));
				}
			}
		}
		return mpSns;
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
		log.debug("【取模板列表】用户编号：" + consNo + ",容量等级：" + gapGradeNo);
		if(listTmnlConsTask != null){
			Map<?, ?> mapTmnlConsTask = (Map<?, ?>) listTmnlConsTask.get(0);
			templateIdList = (String) mapTmnlConsTask.get("TEMPLATE_ID_LIST");
			log.debug("【取模板列表】用户编号：" + consNo + ",容量等级：" + gapGradeNo +"模板列表:"+templateIdList);
		}else{
			log.debug("----------未取得该终端对应的模板列表----------");
		}
		return templateIdList;
	}
	
	
	/*
	 * 根据终端资产号取模板Id
	 */
	private String getTemplateIdByForOthers(){
		String templateIdList = "";//任务模板ID列表
		//根据条件查询任务模板ID列表
		List<?> listTmnlConsTask = null;
		//561
		String meterTask = marketTerminalConfigManager.getiTmnlTaskSetUp();
		return meterTask;
	}
	
	/**
	 * 下发保电参数
	 * @param terminalId 终端ID
	 * @return
	 */
	private String setF25(String terminalId) {
		//P0
		String mpSn = "0";
		RTmnlRun RTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(terminalId);
		String protocol = RTmnlRun.getProtocolCode();
		List<String> list = new ArrayList<String>();
		List<ParamItem> params = new ArrayList<ParamItem>();
		
		//参数设置
		ParamItem pitem1 = new ParamItem();
		pitem1.setFn((short) Integer.parseInt("25"));
		pitem1.setPoint((short) 0);
		List<Item> items = new ArrayList<Item>();
		Item item = new Item(protocol+"0519001");
		item.setValue("0");
		items.add(item);
		pitem1.setItems((ArrayList<Item>) items);
		params.add(pitem1);
		list.add(terminalId);
		
		this.updateTmnlParam(terminalId, mpSn, item);
		
		//返回设置状态
		String Status = marketTerminalConfigManager.getStatusByTaskDeal(list, FrontConstant.CONTROL_COMMAND, params);
		return Status;
	}
	
	/**
	 * 设置终端主动上报
	 * @param terminalId 终端ID
	 * @return
	 */
	private String setF29(String terminalId) {
		RTmnlRun RTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(terminalId);
		String protocol = RTmnlRun.getProtocolCode();
		List<String> list = new ArrayList<String>();
		List<ParamItem> params = new ArrayList<ParamItem>();
		
		//参数设置
		ParamItem pitem1 = new ParamItem();
		pitem1.setFn((short) Integer.parseInt("29"));
		pitem1.setPoint((short) 0);
		params.add(pitem1);
		list.add(terminalId);
		
		//返回设置状态
		String Status = marketTerminalConfigManager.getStatusByTaskDeal(list, FrontConstant.SET_PARAMETERS, params);
		return Status;
	}
	
	/**
	 * 下发终端事件分类命令
	 * @param terminalId 终端ID
	 * @return
	 */
	private String setF09(String terminalId) {
		//P0
		String mpSn = "0";
		RTmnlRun RTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(terminalId);
		String protocol = RTmnlRun.getProtocolCode();
		List<String> list = new ArrayList<String>();
		List<ParamItem> params = new ArrayList<ParamItem>();
		
		//参数设置
		ParamItem pitem1 = new ParamItem();
		pitem1.setFn((short) Integer.parseInt("9"));
		pitem1.setPoint((short) 0);
		List<Item> items = new ArrayList<Item>();
		String value = this.getItemValue(protocol);
		String[] arrayValue = value.split(",");
		
		//事件记录有效标志位
		Item item = new Item(protocol+"0409001");
		item.setValue(arrayValue[0]);
		items.add(item);
		
		//事件重要性等级标志位
		Item item2 = new Item(protocol+"0409002");
		item2.setValue(arrayValue[1]);
		items.add(item2);
		
		pitem1.setItems((ArrayList<Item>) items);
		params.add(pitem1);
		list.add(terminalId);
		
		this.updateTmnlParam(terminalId, mpSn, item);
		this.updateTmnlParam(terminalId, mpSn, item2);
		
		//返回设置状态
		String Status = marketTerminalConfigManager.getStatusByTaskDeal(list, FrontConstant.SET_PARAMETERS, params);
		return Status;
	}
	
	/**
	 * 设置F33
	 * @param terminalId 终端ID
	 * @return
	 */
	private String setF33(String terminalId) {
		String mpSn = "0";
		RTmnlRun RTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(terminalId);
		String protocol = RTmnlRun.getProtocolCode();
		List<String> list = new ArrayList<String>();
		List<ParamItem> params = new ArrayList<ParamItem>();
		
		List<?> lst =marketTerminalConfigManager.getCMeterByTmnlId(terminalId);
		int cMeterNum = lst.size();
		
		//参数设置
		ParamItem pitem1 = new ParamItem();
		pitem1.setFn((short) Integer.parseInt("33"));
		pitem1.setPoint((short) 0);
		List<Item> items = new ArrayList<Item>();
		
		Item item = new Item(protocol+"0421001");
		item.setValue("2");
		items.add(item);
		
		int i = 0;
		while (i < 2) {
			Item item2 = new Item(protocol+"0421002");
			Item item3 = new Item(protocol+"0421003");
			Item item4 = new Item(protocol+"0421004");
			Item item5 = new Item(protocol+"0421005");
			Item item6 = new Item(protocol+"0421006");
			Item item7 = new Item(protocol+"0421007");
			Item item8 = new Item(protocol+"0421008");
			
			if (i == 0) {
				item2.setValue("2");
			} else {
				item2.setValue("31");
			}
			item3.setValue("0000000000000000");
			item4.setValue("1" + this.get31Zero());item5.setValue("0000");
			//100块以下60分钟，100-300块表120分钟，300块以上的240分钟
			if (i == 0) {
				item6.setValue("5");
			} else {
				if (cMeterNum < 100) {
					item6.setValue("60");
				} else if (cMeterNum >= 100 && cMeterNum <= 300) {
					item6.setValue("120");
				} else {
					item6.setValue("240");
				}
			}
			item7.setValue("000000");
			item8.setValue("0");
			
			items.add(item2);items.add(item3);
			items.add(item4);items.add(item5);
			items.add(item6);items.add(item7);
			items.add(item8);
			i++;
		}
		pitem1.setItems((ArrayList<Item>) items);
		params.add(pitem1);
		list.add(terminalId);
	
		//返回设置状态
		String Status = marketTerminalConfigManager.getStatusByTaskDeal(list, FrontConstant.SET_PARAMETERS, params);
		return Status;
	}
	
	/**
	 * 设置参数
	 * @param terminalId
	 */
	private void setParam(String terminalId) {
		
		//设置保电参数  
		String f25Status = this.setF25(terminalId);
		log.debug("------------保电参数下发状态: "+f25Status);
		if (!"3".equals(f25Status)) {
			this.daemonTask(terminalId, "25", "0");
		}
		log.debug("【设置保电参数结束】");
		
		//设置设置终端事件分类  
		String f9Status = this.setF09(terminalId);
		if (!"3".equals(f9Status)) {
			this.daemonTask(terminalId, "9", "0");
		}
		log.debug("------------终端事件分类下发状态: "+f9Status);
		log.debug("【设置终端事件分类结束】");
		
		//设置F33
		String f33Status = this.setF33(terminalId);
		log.debug("------------设置抄表F33: "+f33Status);
		log.debug("【设置抄表F33结束】");
	}
	
	/**
	 * 终端测量点参数t_tmnl_param
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
	 * 取得终端事件分类命令、定义终端事件级别
	 * @param protocol
	 * @return
	 */
	private String getItemValue(String protocol) {
		String result = null;
		String[] arrayFlag = new String[64];
		String[] arrayLevel = new String[64];
		StringBuffer sfFlag = new StringBuffer();
		StringBuffer sfLevel = new StringBuffer();
		for (int i = 0; i < arrayFlag.length; i++) {
			arrayFlag[i] = "0";
			arrayLevel[i] = "0";
		}
		//取得终端事件分类
		List<?> list = marketTerminalConfigManager.getLevel(protocol);
		for (int j = 0; j < list.size(); j++) {
			Map<?,?> map = (Map<?,?>) list.get(j);
			String eventNo = String.valueOf(map.get("EVENT_NO"));         //事件编号
			String eventLevel = String.valueOf(map.get("EVENT_LEVEL"));   //事件等级
			String isRec = String.valueOf(map.get("IS_REC"));             //是否记录
			
			String hNum = eventNo.substring(eventNo.length()-2, eventNo.length());
			int num = Integer.parseInt(hNum, 16);
			arrayFlag[num -1] = isRec;
			arrayLevel[num -1] = eventLevel;
		}
		for (int k = 0; k < arrayLevel.length; k++) {
			sfFlag.append(arrayFlag[k]);
			sfLevel.append(arrayLevel[k]);
		}
		result = sfFlag.toString()+","+sfLevel.toString();
		return result;
	}
	
	/**
	 * 后台下发
	 * @param tmnlAssetNo
	 * @param fn
	 * @param isP0
	 */
	private void daemonTask(String tmnlAssetNo, String fn, String isP0) {
		String taskId = marketTerminalConfigManager.getBgTaskId();
		Date now = new Date();
		String orgNo = "";
		
		TbgTask tbgTask = new TbgTask();
		tbgTask.setTaskId(Integer.parseInt(taskId));
		tbgTask.setTaskTime(now);
		tbgTask.setStaffNo("后台自动任务");
		tbgTask.setOrgNo(orgNo);
		tbgTask.setTmnlAssetNo(tmnlAssetNo);
		tbgTask.setObjType(Integer.parseInt(isP0 = "0".equals(isP0)?"0":"1"));
		tbgTask.setObjList(null);
		tbgTask.setDataItemCnt(1);
		tbgTask.setTaskType(Integer.parseInt(isP0 = "0".equals(isP0)?"0":"1"));
		tbgTask.setTaskName("终端参数设置");
		tbgTask.setRwFlag(0);//0设置;1召测
		tbgTask.setMaxTry(2);//最大执行次数
		tbgTask.setSendTimes(0);//已执行次数
		tbgTask.setSendTime(null);
		tbgTask.setNextSendTime(null);
		tbgTask.setTaskStatus("03");
		
		TbgTaskDetail tbgTaskDetail = new TbgTaskDetail();
		tbgTaskDetail.setTaskId(Integer.parseInt(taskId));
		tbgTaskDetail.setDataItemNo(String.valueOf(Integer.parseInt(fn, 16)));
		tbgTaskDetail.setDataType(10);
		marketTerminalConfigManager.addTbgTask(tbgTask);
		marketTerminalConfigManager.addTbgTaskDetail(tbgTaskDetail);
		log.debug("【后台任务添加成功】taskId：" + taskId);
	}
	
	/**
	 * 取得主表
	 * @param tmnlAssetNo
	 * @return
	 */
	private String getMainMeters(String tmnlAssetNo) {
		String mainMeters = "";
		StringBuffer bf = new StringBuffer();
		//取出主表测量点号
		List<?> listEDateMp = marketTerminalConfigManager.getFrmMpSnsByTmnl(tmnlAssetNo);
		if (listEDateMp != null && listEDateMp.size() > 0) {
			for (int i = 0 ; i < listEDateMp.size(); i++) {
				Map<?,?> mapEDataMp = (Map<?,?>) listEDateMp.get(i);
				String msn = StringUtil.removeNull(mapEDataMp.get("MP_SN"));
				bf.append(msn+",");
			}
			mainMeters = bf.substring(0, bf.length()-1); //mainMeter string
		}
		return mainMeters;	
	}
	
	/**
	 * 建立考核
	 * @param terminalId
	 */
	private void bulidChkUnit(String terminalId) {
		log.debug("***************************建立考核单元开始***************************");
		String tgID = "";
		String tgName = "";
		String orgNo = "";
		//取得台区
		List<?> itmnlTaskLst = marketTerminalConfigManager.getTmnlTaskByTmnlId(terminalId);
		if (itmnlTaskLst != null && itmnlTaskLst.size() > 0) {
			Map<?,?> tmnlTaskMap = (Map<?,?>) itmnlTaskLst.get(0);
			tgID = StringUtil.removeNull(tmnlTaskMap.get("TG_ID"));
			tgName = StringUtil.removeNull(tmnlTaskMap.get("TG_NAME"));
			orgNo = StringUtil.removeNull(tmnlTaskMap.get("ORG_NO"));
		}
		//验证是否已经有数据
		List<?> chkUnit = marketTerminalConfigManager.getChkUnit(tgID);
		if (chkUnit != null && chkUnit.size() == 0) {
			String chkUnitId = marketTerminalConfigManager.insertChkUnit(orgNo, tgName, tgID);
			marketTerminalConfigManager.insertChkUnitComp(chkUnitId, tgID);
			marketTerminalConfigManager.insertGIoMpByTmnlId(terminalId, chkUnitId, tgID);
		}
		
		String flowFlag = "1";
		//其他考核单元
		marketTerminalConfigManager.otherChkUnit(terminalId, tgID, flowFlag);
		
		log.debug("***************************建立考核单元结束***************************");
	}
	
	/**
	 * 更新用户分类
	 * @param terminalId
	 */
	private void updateConsSort(String terminalId) {
		log.debug("***************************更新用户分类开始***************************");
		
		String consType = "";//用户类型
		float contractCap = 0;//合同容量
		String consStatus = "";//用户状态
		String typeCode = "";//计量点分类
		String wiringMode = "";//接线方式
		String consSort = "";//用户分类
		String consId = "";//用户ID
		
		//取得用户计量点信息
		List<?> consMpLst = marketTerminalConfigManager.getConsCmpByTmnlId(terminalId);
		if (consMpLst != null && consMpLst.size() > 0) {
			for (int i = 0; i < consMpLst.size(); i++) {
				Map<?,?> consMap = (Map<?,?>) consMpLst.get(i);
				consType = StringUtil.removeNull(consMap.get("CONS_TYPE"));
				contractCap = Float.parseFloat(consMap.get("CONTRACT_CAP") == null?"0":StringUtil.removeNull(consMap.get("CONTRACT_CAP")));
				consStatus = StringUtil.removeNull(consMap.get("STATUS_CODE"));
				typeCode = StringUtil.removeNull(consMap.get("TYPE_CODE"));
				wiringMode = StringUtil.removeNull(consMap.get("WIRING_MODE"));
				consId = StringUtil.removeNull(consMap.get("CONS_ID"));
			
				if (("1".equals(consType) || "1".equals(consType)) && contractCap >= 100 && !"9".equals(consStatus)) {
					consSort = "A";
				}
				else if (("1".equals(consType) || "1".equals(consType)) && contractCap < 100 && !"9".equals(consStatus)) {
					consSort = "B";
				} 
				else if ("01".equals(typeCode) && "3".equals(wiringMode) && "5".equals(consType)) {
					consSort = "C";
				}
				else if ("01".equals(typeCode) && "1".equals(wiringMode)) {
					consSort = "E";
				}
				else if ("03".equals(typeCode)) {
					consSort = "F";
				}
				else if ("02".equals(typeCode) || "04".equals(typeCode) || "05".equals(typeCode)) {
					consSort = "G";
				}
				//更新用户分类
				marketTerminalConfigManager.updateCconsConsSort(consId, consSort);
			}
		}
		//更新虚拟用户的分类
		marketTerminalConfigManager.updateCyberConsSort(terminalId);
		
		log.debug("***************************更新用户分类结束***************************");
	}
	
	/**
	 * 参数
	 * @return
	 */
	private String get31Zero() {
		StringBuffer sf = new StringBuffer();
		for (int i = 0; i < 31; i++) {
			sf.append("0");
		}
		return sf.toString();
	}
	
}