package com.nari.process.hb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;
import com.nari.ami.database.map.runcontrol.RTmnlRun;
import com.nari.coherence.TaskDeal;
import com.nari.eventbean.BackFeedEvent;
import com.nari.eventbean.hb.HBRelayMeterReadingEvent;
import com.nari.fe.commdefine.task.Item;
import com.nari.global.Constant;
import com.nari.global.Globals;
import com.nari.model.CMeter;
import com.nari.model.TbgTask;
import com.nari.model.TbgTaskDetail;
import com.nari.model.TtmnlParam;
import com.nari.service.MarketTerminalConfigManager;
import com.nari.util.StringUtil;

/**
 * 下发终端参数
 */
@SuppressWarnings("unused")
public class HBSendFrmTmnlParamEventProcess implements Runnable{
	
	private static final Log log = LogFactory.getLog(HBSendTmnlParamEventProcess.class);
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public HBSendFrmTmnlParamEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents=newEvents;
		this.oldEvents=oldEvents;
	}
	private MarketTerminalConfigManager marketTerminalConfigManager =  null;
	TaskDeal taskDeal = new TaskDeal();
	public static final String AFN = "04";
	public static final String FN_10 = "0A";//F10
	
	public void run() {
		log.debug("【HBSendFrmTmnlParamEventProcess电表注册开始......】");
		marketTerminalConfigManager=(MarketTerminalConfigManager)Constant.getCtx().getBean("marketTerminalConfigManager");
		
		String appNo = (String) newEvents[0].get("appNo");
		String terminalId = (String) newEvents[0].get("terminalId");
		String tmnlAssetNo = (String) newEvents[0].get("tmnlAssetNo");
		Long debugId = (Long) newEvents[0].get("debugId");
		String meterFlag = (String) newEvents[0].get("meterFlag"); //换表标识
		String Status = null;
		String flowStatus = null;//流程状态 
		String mpSnList = null;  //上次失败的电能表
	
		try {
			
			Map<?,?> map = marketTerminalConfigManager.getIFlowProcess(appNo, terminalId, Globals.SEND_TMNL);
			if (map != null) {
				flowStatus = StringUtil.removeNull(map.get("FLOW_STATUS"));
				mpSnList = StringUtil.removeNull(map.get("MP_SN_LIST"));
				mpSnList =  mpSnList.replaceAll(" ", "");
			}
			//插入测试进度，将测试进度置为"下发终端参数开始"
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLPARAM_START);
			
			if (null == map || (null != map && !"0".equals(flowStatus))) {
				log.debug("----------------------------【电表注册开始】----------------------------");
				Status = sendTmnlParam(tmnlAssetNo, appNo, mpSnList, meterFlag);
				log.debug("----------------------------【电表注册 结束】----------------------------");
			}

			/**-----------------------成功，部分成功执行relayMeter流程---------------------------*/
			if("1".equals(Status) || "0".equals(Status) || "0".equals(flowStatus)){

				marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLPARAM_SUCCESS);
				//负控终端工作正常
				marketTerminalConfigManager.updateRTmnlDebugTerminalFlag(debugId, "1");
				//终端与主站通讯正常
				marketTerminalConfigManager.updateRTmnlDebugMasterCommFlag(debugId, "1");
				try {
					int sleepTime = 1;
					Thread.sleep(1000 * sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				log.debug("----------------------------【HBSendFrmTmnlParamEventProcess流程结束】----------------------------");
				//触发事件：抄读电表数据
				Constant.sendEvent(new HBRelayMeterReadingEvent(appNo, terminalId, tmnlAssetNo, debugId, "1", meterFlag));//TODU
			}else{
				//根据状态确定失败原因
				String failCause = "电表注册不成功";
				marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLPARAM_FAILED, failCause);
				//负控终端工作不正常
				marketTerminalConfigManager.updateRTmnlDebugTerminalFlag(debugId, "0");
				//反馈营销
				log.debug("【下发终端参数失败】, tmnlAssetNo = " + tmnlAssetNo);
				Constant.sendEvent(new BackFeedEvent(appNo, debugId));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			marketTerminalConfigManager.updateTestStatue(appNo, Globals.DEBUG_STATUE_FAILED);
		}
	}

	/**
	 * 下发终端参数
	 * 
	 * @param tmnlAssetNo
	 * @param appNo
	 * @param mpSnList
	 * @throws Exception
	 */
	private String sendTmnlParam(String tmnlAssetNo, String appNo, String mpSnList, String meterFlag){
		
		//终端资产号集合
		List<String> tmnlAssetNos = new ArrayList<String>();
		tmnlAssetNos.add(tmnlAssetNo);
		
		String flag = marketTerminalConfigManager.extraTTmnlParam(tmnlAssetNo);
		// 参数项设置
		if (null == mpSnList || "1".equals(flag)) {
			log.debug("*******************************保存电表注册参数开始**********************************");
			setTmnlParamF10 (tmnlAssetNo, appNo, mpSnList, flag);
			log.debug("*******************************保存电表注册参数结束**********************************");
		}
		//F10小项
//		List<?> list = marketTerminalConfigManager.getF10TmnlParamByTmnlAssetNo(tmnlAssetNo);
		
		//调用后台下发
		String status = daemonTask(appNo, tmnlAssetNo, mpSnList);
		//返回状态
		return status;
	}
	
	/**
	 * 后台下发任务
	 * @param tmnlAssetNo
	 * @param mpSns
	 * @return
	 */
	private String daemonTask(String appNo, String tmnlAssetNo, String mpSnList) {
		//下发状态
		String Status = "";
		//集抄F10后台下发
		String taskId = marketTerminalConfigManager.getBgTaskId();
		Date now = new Date();
		String orgNo = "";
		
		TbgTask tbgTask = new TbgTask();
		tbgTask.setTaskId(Integer.parseInt(taskId));
		tbgTask.setTaskTime(now);
		tbgTask.setStaffNo("营销调试");
		tbgTask.setOrgNo(orgNo);
		tbgTask.setTmnlAssetNo(tmnlAssetNo);
		tbgTask.setObjType(0);//0终端;1测量点;2总加组
		tbgTask.setObjList(null);//终端时不填，测量点或总加组按位存放：1有效，0无效
		tbgTask.setDataItemCnt(1);
		tbgTask.setTaskType(10);//0终端参数;1测量点参数;2终端任务;3测量点数据,10后台程序针对F10单独处理
		tbgTask.setTaskName("终端参数设置");
		tbgTask.setRwFlag(0);//0设置;1召测
		tbgTask.setMaxTry(2);//最大执行次数
		tbgTask.setSendTimes(0);//已执行次数
		tbgTask.setSendTime(null);
		tbgTask.setNextSendTime(null);
		tbgTask.setTaskStatus("03");//01仅保存 02 在线等待 03 待下发 04 成功 05 失败 06 超次数
		
		TbgTaskDetail tbgTaskDetail = new TbgTaskDetail();
		tbgTaskDetail.setTaskId(Integer.parseInt(taskId));
		tbgTaskDetail.setDataItemNo("0A");//F10
		tbgTaskDetail.setDataType(10);//1类数据;2类数据;3事件数据;4 终端参数;10一次下发32个块

		marketTerminalConfigManager.addTbgTask(tbgTask);
		marketTerminalConfigManager.addTbgTaskDetail(tbgTaskDetail);
		log.debug("        后台下发开始........     ");
		log.debug("【后台任务添加成功】taskId：" + taskId);
		
		int time = marketTerminalConfigManager.getFrmTmnlTime();
	
		if (null == mpSnList) {
			
			String mpsn = marketTerminalConfigManager.getMpSnByTmnl(tmnlAssetNo);
			int bgs = Integer.parseInt(mpsn) / 32 + 1;
			log.debug("分成" + bgs + "包下发");
			try {
				log.debug("【后台下发中，请等待"+(time * bgs+200)+"秒......】");
				Thread.sleep(1000 * time * bgs + 200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Status = marketTerminalConfigManager.getStatusByTmnlParamStatueForFrm(appNo, tmnlAssetNo, Globals.SEND_TMNL);
		} 

		else {
			String[] s = mpSnList.split(",");
			log.debug("对失败的"+s.length+"个测量点下发： "+mpSnList);

			int bgs = s.length / 32 + 1;
			log.debug("分成" + bgs + "包下发");
			try {
				log.debug("【后台下发中，请等待"+(time * bgs+200)+"秒......】");
				Thread.sleep(1000 * time * bgs + 200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Status = marketTerminalConfigManager.getStatusByTmnlMpSnParamStatue(appNo, tmnlAssetNo, Globals.SEND_TMNL, s);
		}
		return Status;
	}
	
	
	
	/**
	 * 设置终端参数F10
	 * @param mpSn 
	 * @param ameterAssetNo 
	 * @param tmnlAssetNo 
	 * @param falg 
	 */
	private List<Item> setTmnlParamF10(String tmnlAssetNo, String appNo, String mpSnList, String flag) {
		log.debug("********************************进入终端参数F10设置*************************************");
		//取终端规约
		String protocolCode = null;
		RTmnlRun rTmnlRun = new RTmnlRun();
		rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
		protocolCode = Integer.toString(Integer.parseInt(rTmnlRun.getProtocolCode()));
		
		String factoryCode = rTmnlRun.getFactoryCode();//厂家编号
		String protItemNo = "";//规约项数据编码
		List<Item> items = new ArrayList<Item>();
		String ameterId = "";
		String commNo = "";
		List<?> listEDateMp = null;

		//根据tmnlAssetNo取测量点
		listEDateMp = marketTerminalConfigManager.getMpSnsByTmnl(tmnlAssetNo);
		
		//新增的表测量点数据
		if ("1".equals(flag) && mpSnList != null) {
			listEDateMp = marketTerminalConfigManager.getMpSnsByTmnlSn(tmnlAssetNo, mpSnList.split(","));
		}
		
		if(listEDateMp != null){
			String endFlag = "0";
			List<TtmnlParam> tTmnlParamLst = new ArrayList<TtmnlParam>();
			
			for(int j = 0; j < listEDateMp.size(); j++){
				Map<?, ?> mapEDateMp = (Map<?, ?>) listEDateMp.get(j);
				ameterId = StringUtil.removeNull(mapEDateMp.get("METER_ID"));
				String mpSn = String.valueOf(mapEDateMp.get("MP_SN"));
				String digits = getMeterDigitsByMeterId(ameterId);
				String[] digitsArray = digits.split(",");
				
				if (j+1 == listEDateMp.size()) {
					endFlag = "1";
				}
				
				long isMainMeter = marketTerminalConfigManager.checkMainMater(ameterId);// 2010年/6/30
				
				//根据ameterAssetNo取电能表信息
				CMeter cMeter = new CMeter();
				cMeter = marketTerminalConfigManager.getAmeterByAssetNo(ameterId);
				
				//表中取出为空/从 r_coll_obj或者r_coll_obj_scheme中取
				if (null == cMeter.getCommNo()) {
					commNo = this.getCommNo(protocolCode, ameterId);
				} else {
					commNo = cMeter.getCommNo();
				}

				String wiringMode = this.WiringModeByMeterId(ameterId);
				
				if(j == 0 && !"1".equals(flag)) {
					//F10 本次电能表/交流采样装置配置数量
					protItemNo = protocolCode + AFN + FN_10 + "001";
					Item item = new Item(protItemNo);
					item.setValue(String.valueOf(listEDateMp.size()));
					items.add(item);
					log.debug("【下发终端参数】终端编号：" + tmnlAssetNo + ",F10 电能表个数" + protItemNo+":"+item.getValue());
					this.updateTmnlParam(tmnlAssetNo, "0", item, null, null);
				}
			
				//根据PROTOCOL_NO取PROT_ITEM_NO
				String protocolNo = protocolCode + AFN + FN_10;
				List<?> listProtItem = marketTerminalConfigManager.getProtItemByProtocolNo(protocolNo);
				if(listProtItem != null){
					String protFlag = "0";
					for(int i = 1; i < listProtItem.size(); i++){
						if (i+1 == listProtItem.size() && "1".equals(endFlag)) {
							protFlag = "1";
						}
						Map<?, ?> mapProtItem = (Map<?, ?>) listProtItem.get(i);
						protItemNo = StringUtil.removeNull(mapProtItem.get("PROT_ITEM_NO"));
						String protItemSn = String.valueOf(mapProtItem.get("PROT_ITEM_SN"));
						String protItemName = String.valueOf(mapProtItem.get("PROT_ITEM_NAME"));
						Item item = new Item(protItemNo);
						if("2".equals(protItemSn)){
							//F10 电能表/交流采样装置序号
							item.setValue(mpSn);
						}else if("3".equals(protItemSn)){
							//F10 所属测量点号
							item.setValue(mpSn);
						}else if("4".equals(protItemSn)){
							//F10 通信端口号（需要召测回来确认，表示电能表、交流采样装置接入终端的通信端口号，取值范围1~31）
							String result = this.judgePort(ameterId, tmnlAssetNo);
							if (isMainMeter == 0 || "1".equals(result)) {
								item.setValue("2");
							} else {
								item.setValue("31");
							}
						}else if("5".equals(protItemSn)){
							//F10 通信速率（运行电能表-波特率）
							item.setValue(cMeter.getBaudrate() == null ? "0" : cMeter.getBaudrate());
						}else if("6".equals(protItemSn)){
							//F10 通信协议类型（运行电能表-通讯规约 ：645）
							item.setValue(commNo);
						}else if("7".equals(protItemSn)){
							//F10 通信地址（运行电能表-通讯地址1）
							item.setValue(cMeter.getCommAddr1() == null ? "0" : cMeter.getCommAddr1());
						}else if("8".equals(protItemSn)){
							//F10 通信密码TODU（没有为空）
							item.setValue("0");
						}else if("9".equals(protItemSn)){
							//F10 电能费率个数TODU（默认3个）
							item.setValue("4");
						}else if("10".equals(protItemSn)){
							//F10 有功电能示值小数位个数（数值范围0~3依次表示1~4位小数。）
							if ("1".equals(digitsArray[1])) {
								item.setValue("0");
							} else if("2".equals(digitsArray[1])) {
								item.setValue("1");
							} else if ("3".equals(digitsArray[1])) {
								item.setValue("2");
							} else if ("4".equals(digitsArray[1])) {
								item.setValue("3");
							} else {
								item.setValue("1");
							}
						}else if("11".equals(protItemSn)){
							//F10 有功电能示值整数位个数TODU（数值范围0~3依次表示4~7位整数。6位）
							if ("4".equals(digitsArray[0])) {
								item.setValue("0");
							} else if ("5".equals(digitsArray[0])) {
								item.setValue("1");
							} else if ("6".equals(digitsArray[0])) {
								item.setValue("2");
							} else if ("7".equals(digitsArray[0])) {
								item.setValue("3");
							} else {
								item.setValue("2");
							}
						}else if("12".equals(protItemSn)){
							String frmAddr = this.judgeFrmAddr(ameterId, tmnlAssetNo);
							//F10 所属采集器通信地址
							item.setValue(frmAddr);
						}else if("13".equals(protItemSn)){
							//F10 用户大类号（E）
							item.setValue("5");
						}else if("14".equals(protItemSn)){
							//F10 用户小类号
							if ("1".equals(wiringMode)) {
								item.setValue("1");
							} else if ("2".equals(wiringMode) || "3".equals(wiringMode)) {
								item.setValue("2");
							} else {
								item.setValue("1");
							}
						}
						log.debug("【下发终端参数】" + tmnlAssetNo + "," + protItemName +":"+item.getValue());
						items.add(item);
						this.updateTmnlParam(tmnlAssetNo, mpSn, item, tTmnlParamLst, protFlag);
					}
				}
			}
		}
		return items;
	}
	
	
	/**
	 * 终端测量点参数t_tmnl_paramF10
	 * @param mpSn 
	 * @param ameterAssetNo 
	 * @param tmnlAssetNo 
	 */
	private void updateTmnlParam(String tmnlAssetNo, String mpSn, Item item, List<TtmnlParam> tTmnlParamLst, String protFlag) {
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
		//数量保存
		if (tTmnlParamLst == null) {
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
		//小项保存
		else {
			String historyValue = "";
			Date lastSendTime = null;
			if(tmnlParam != null && tmnlParam.size() > 0){
				//更新终端参数
				Map<?, ?> mapMpParam = (Map<?, ?>) tmnlParam.get(0);
				historyValue = (String) mapMpParam.get("CURRENT_VALUE");//参数历史值
				lastSendTime = (Date) mapMpParam.get("SUCCESS_TIME");//上次下发时间
				//更新终端参数，并置为有效
				isValid = "1";
			}
			TtmnlParam tParam = new TtmnlParam();
			
			tParam.setTmnlAssetNo(tmnlAssetNo);
			tParam.setProtItemNo(protItemNo);
			tParam.setCurrentValue(currentValue);
			tParam.setHistoryValue(historyValue);
			tParam.setBlockSn(blockSn);
			tParam.setInnerBlockSn(innerBlockSn);
			tParam.setStatusCode(statusCode);
			tParam.setIsValid(isValid);
			tParam.setLastSendTime(lastSendTime==null?new Date():lastSendTime);
			tTmnlParamLst.add(tParam);
			
			if ("1".equals(protFlag)) {
				log.debug("****************************批量更新F10****************************");
				//merge
				marketTerminalConfigManager.mergeTTmnlParam(tTmnlParamLst);
			}	
		}
	}
		
	/**
	 * 取得电能表通讯规约
	 * @param protocolCode  规约类型
	 * @param meterId       电能表ID
	 * @return
	 */
	private String getCommNo(String protocolCode, String meterId) {
		//表通讯规约
		return marketTerminalConfigManager.getCommNo(meterId, protocolCode);
	}
	
	/**
	 * 根据表ID取得示数位数
	 * @param meterId 
	 */
	public String getMeterDigitsByMeterId(String meterId) {
		String zNum = "";
		String xNum = "";
		//表通讯规约
		String digits = marketTerminalConfigManager.getMeterDigitsByMeterId(meterId);
		if (digits.length() == 1) {
			zNum = digits.substring(0, 1);//整数
			return zNum+","+"2";
		} else if (digits.length() > 1) {
			zNum = digits.substring(0, 1);//整数
			xNum = digits.substring(2, 3);//小数
			return zNum+","+xNum;
		} else {
			return "6,2";
		}
	}
	
	/**
	 * 判断端口号
	 * @param meterId       表ID
	 * @param tmnlAssetNo   终端资产号
	 * @return 1:表直接接在集中器上
	 *         0：表挂在采集器
	 */
	private String judgePort(String meterId, String tmnlAssetNo) {
		String collectorId =  marketTerminalConfigManager.judgePort(meterId);
		if (null != tmnlAssetNo && tmnlAssetNo.equals(collectorId)) {
			return "1";
		} else {
			return "0";
		}
	}
	
	/**
	 * 
	 * @param meterId
	 * @return
	 */
	private String WiringModeByMeterId(String meterId) {
		List<?> list = marketTerminalConfigManager.getDmeterByMeterId(meterId);
		if (list != null && list.size() > 0) {
			Map<?, ?>  map = (Map<?, ?>) list.get(0);
			String wiringMode = StringUtil.removeNull(map.get("WIRING_MODE"));
			return wiringMode;
		}
		return "";
	}
	
	/**
	 * 取得采集器地址
	 * @param ameterId
	 * @return
	 */
	private String getfrmAddr(String ameterId) {
		String frmAddr = "";
		List<?> list = marketTerminalConfigManager.getfrmAddr(ameterId);
		if (list != null && list.size() > 0) {
			Map<?,?> map = (Map<?,?>) list.get(0);
			frmAddr = StringUtil.removeNull(map.get("COMM_ADDR"));
		}
		 return "".equals(frmAddr)? "" : frmAddr;
	}
	
	/**
	 * 
	 * @param ameterId
	 * @param tmnlAssetNo
	 * @return
	 */
	private String judgeFrmAddr(String ameterId, String tmnlAssetNo) {
		String collMode = null;
		String frmAddr = "0";
		//小无线
		List<?> list = marketTerminalConfigManager.getfrmAddr(ameterId);
		if (list != null && list.size() > 0) {
			Map<?,?> map = (Map<?,?>) list.get(0);
			collMode = StringUtil.removeNull(map.get("COLL_MODE"));
			if ("06".equals(collMode)) {
				frmAddr = StringUtil.removeNull(map.get("COMM_ADDR"));
			}
		}
		return frmAddr;
	}
	
}