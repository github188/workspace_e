package com.nari.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;
import com.nari.ami.database.map.device.DMeter;
import com.nari.ami.database.map.runcontrol.RTmnlRun;
import com.nari.coherence.TaskDeal;
import com.nari.eventbean.BackFeedEvent;
import com.nari.eventbean.SendFrmTaskParamEvent;
import com.nari.eventbean.SendTaskParamEvent;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.global.Constant;
import com.nari.global.Globals;
import com.nari.service.MarketTerminalConfigManager;
import com.nari.util.ArrayToListUtil;
import com.nari.util.StringUtil;

/**
 * 下发终端测量点参数
 */
@SuppressWarnings("unused")
public class SendTmnlMpParamEventProcess implements Runnable{
	
	private static final Log log = LogFactory.getLog(SendTmnlMpParamEventProcess.class);
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public SendTmnlMpParamEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents=newEvents;
		this.oldEvents=oldEvents;
	}
	public static final String AFN = "04";
	public static final String FN = "19";//F25
	private MarketTerminalConfigManager marketTerminalConfigManager =  null;
	TaskDeal taskDeal = new TaskDeal();
	
	public void setMarketTerminalConfigManager(
			MarketTerminalConfigManager marketTerminalConfigManager) {
		this.marketTerminalConfigManager = marketTerminalConfigManager;
	}

	public void run() {
		log.debug("【SendTmnlMpParamEventProcess下发测量点参数开始......】");
		marketTerminalConfigManager=(MarketTerminalConfigManager)Constant.getCtx().getBean("marketTerminalConfigManager");
		
		String appNo = (String) newEvents[0].get("appNo");
		String terminalId = (String) newEvents[0].get("terminalId");
		String tmnlAssetNo = (String) newEvents[0].get("tmnlAssetNo");
		Long debugId = (Long) newEvents[0].get("debugId");
		String flowFlag = (String) newEvents[0].get("flowFlag");//流程标识    
		String meterFlag = (String) newEvents[0].get("meterFlag"); //换表标识
		String Status = null;
		String attachMeterFlag = null;
		String mpSnList = null;
		String flowStatus = null;
		List<String> snList = null;
		
		try {
			//更新测试状态为：下发终端测量点参数开始
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLMPPARAM_START);
			if ("1".equals(flowFlag)) {
				log.debug("--------------------------------------集抄流程开始--------------------------------------");
				Map<?,?> map = marketTerminalConfigManager.getIFlowProcess(appNo, terminalId, Globals.SEND_TMNL_MP);
				if (null != map) {
					flowStatus = StringUtil.removeNull(map.get("FLOW_STATUS"));
					mpSnList = StringUtil.removeNull(map.get("MP_SN_LIST"));   //string
					mpSnList =  mpSnList.replaceAll(" ", "");
					snList = ArrayToListUtil.arrayToList(mpSnList.split(",")); //list
				}
				 
				if (null == map || !"0".equals(flowStatus)) {
					log.debug("【下发终端测量点参数开始】, tmnlAssetNo = " + tmnlAssetNo);
					Status = sendTmnlMpParameters(tmnlAssetNo, appNo, flowFlag);
					log.debug("-----------------------------------------status: " + Status);
				}
				if ("3".equals(Status) || "0".equals(flowStatus)) {
					log.debug("-----------------------------------------status: " + Status);
					log.debug("【下发终端测量点参数成功】, tmnlAssetNo = " + tmnlAssetNo);
					
					//更新测试状态为：下发终端测量点参数成功
					marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLMPPARAM_SUCCESS);
					//更新终端流程表
					marketTerminalConfigManager.updateIFlowProcess(appNo, tmnlAssetNo, Globals.SEND_TMNL_MP, "0", "", "");
					//触发事件：下发任务
					Constant.sendEvent(new SendFrmTaskParamEvent(appNo, terminalId, tmnlAssetNo, debugId, meterFlag));
				} else {
					log.debug("-----------------------------------------status: " + Status);
					//更新测试状态为：下发终端测量点参数失败
					marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLMPPARAM_FAILED);
					log.debug("【下发终端测量点参数失败】, tmnlAssetNo = " + tmnlAssetNo);
					

//					Map<?,?> iFlowNode = marketTerminalConfigManager.getIFlowProcess(appNo, tmnlAssetNo, "03");
//					String f10Status = "0";
//					String failCase = "";
//					if (iFlowNode != null) {
//						f10Status = StringUtil.removeNull(iFlowNode.get("FLOW_STATUS"));
//					}
//					if ("1".equals(Status)) {
//						failCase = "终端不在线";
//					}
//					marketTerminalConfigManager.updateIFlowProcess(appNo, tmnlAssetNo, Globals.RELAY_METER, f10Status, failCase);
					
					String failCode = "1".equals(Status)? "终端离线":Status;
					marketTerminalConfigManager.updateIFlowProcess(appNo, tmnlAssetNo, Globals.SEND_TMNL_MP, "1", this.getMainMeters(tmnlAssetNo), failCode);
					
					//触发下发任务
					Constant.sendEvent(new SendFrmTaskParamEvent(appNo, terminalId, tmnlAssetNo, debugId, meterFlag));
				}	
			}
			else {
				log.debug("--------------------------------------负控流程开始--------------------------------------");
				Status = sendTmnlMpParameters(tmnlAssetNo, appNo, flowFlag);
				
				if("3".equals(Status)){
					//下发成功，更新终端测量点参数状态t_tmnl_mp_param
					this.updateTmnlMpParamStatue(tmnlAssetNo);
					//更新测试状态为：下发终端测量点参数成功
					marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLMPPARAM_SUCCESS);
					
					//根据终端类型触发不同事件，二合一终端触发“下发任务”，普通终端触发“下发表计测量点参数”
					RTmnlRun rTmnlRun = new RTmnlRun();
					rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
					//根据appNo取终端变更TMNL_TASK_TYPE
					String tmnlTaskType = marketTerminalConfigManager.getTmnlTaskTypeByAppNo(appNo);
					Boolean flag = true;
					//取变更电表情况
					List<?> list = marketTerminalConfigManager.getRTmnlTaskSr(appNo);
					if(list != null && list.size() > 0){
						for(int i = 0; i < list.size(); i++){
							Map<?, ?> mapRTmnlTaskSr = (Map<?, ?>) list.get(i);
							//取新电能表编号（新装时，原电能表编号为空，新电能表编号不为空）
							String newMeterId = StringUtil.removeNull(mapRTmnlTaskSr.get("NEW_METER_ID"));//新电能表编号
							String oldMeterId = StringUtil.removeNull(mapRTmnlTaskSr.get("OLD_METER_ID"));//原电能表编号
							if(!newMeterId.equals(oldMeterId)){
								flag = false;
							}
						}
					}else{
						flag = false;
					}
					if("5".equals(tmnlTaskType) && flag){
						//换PT/CT的情况不需要下发任务
						log.debug("换PT/CT的情况不需要下发任务, tmnlAssetNo = " + tmnlAssetNo);
						//更新测试状态为：//测试成功
						marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_END);
						Constant.sendEvent(new BackFeedEvent(appNo, debugId));
					}else{
						//触发事件：下发任务
						Constant.sendEvent(new SendTaskParamEvent(appNo, terminalId, tmnlAssetNo, debugId));//TODU
					}
				}else{
					//更新测试状态为：下发终端测量点参数失败
					marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLMPPARAM_FAILED);
					log.debug("【下发终端测量点参数失败】, tmnlAssetNo = " + tmnlAssetNo);
					//反馈营销
					Constant.sendEvent(new BackFeedEvent(appNo, debugId));
					return;
				}
			}
			log.debug("******************************************************************************************************************");
			log.debug("******************************************************************************************************************");
			log.debug("******************************************************************************************************************");
		} catch (Exception e) {
			e.printStackTrace();
			marketTerminalConfigManager.updateTestStatue(appNo, Globals.DEBUG_STATUE_FAILED);
		}
	}

	/**
	 * 下发终端测量点参数
	 * 
	 * @param tmnlAssetNo
	 * @param appNo
	 * @param flowFlag 
	 * @throws Exception
	 */
	private String sendTmnlMpParameters(String tmnlAssetNo, String appNo, String flowFlag) {
		//根据appNo取终端变更TMNL_TASK_TYPE
		String ameterId = "";
		//终端资产号集合
		List<String> tmnlAssetNos = new ArrayList<String>();
		tmnlAssetNos.add(tmnlAssetNo);
		// 参数项设置
		List<ParamItem> params = new ArrayList<ParamItem>();

		//根据tmnlAssetNo取测量点
		List<?> listEDateMp = marketTerminalConfigManager.getMpSnsByTmnl(tmnlAssetNo);

		if ("1".equals(flowFlag)) {
			listEDateMp = marketTerminalConfigManager.getFrmMpSnsByTmnl(tmnlAssetNo);
			log.debug("集抄流程 --------主表数据长度: "+listEDateMp.size());
		}
		if(listEDateMp != null && listEDateMp.size() > 0){
			for(int j = 0; j < listEDateMp.size(); j++){
				Map<?, ?> mapEDateMp = (Map<?, ?>) listEDateMp.get(j);
				ameterId = StringUtil.removeNull(mapEDateMp.get("METER_ID"));
				String mpSn = String.valueOf(mapEDateMp.get("MP_SN"));
				
				//设置参数
				ParamItem pitem1 = new ParamItem();
				pitem1.setFn((short) Integer.parseInt("25"));//F25：测量点基本参数
				pitem1.setPoint((short) Integer.parseInt(mpSn));
				List<Item> items = new ArrayList<Item>();
				items = setTmnlMpParam (tmnlAssetNo, ameterId, mpSn);
				pitem1.setItems((ArrayList<Item>) items);
				params.add(pitem1);
			}
			//下发F25状态
			String Status = marketTerminalConfigManager.getStatusByTaskDeal(tmnlAssetNos, FrontConstant.SET_PARAMETERS, params);
			//下发成功,更新终端测量点参数状态t_tmnl_mp_param
			if ("3".equals(Status)) {
				this.updateTmnlMpParamStatue(tmnlAssetNo);
			} 
			return Status;
		} else {
			return "3";
		}
	}
	
	/**
	 * 终端测量点参数t_tmnl_mp_param
	 * @param mpSn 
	 * @param ameterAssetNo 
	 * @param tmnlAssetNo 
	 */
	private void updateTmnlMpParam(String tmnlAssetNo, String mpSn, Item item) {
		String blockSn = mpSn;//块序号
		String innerBlockSn = "";//块内序号
		String statusCode = Globals.SEND_STATUS_CODE_3;//状态编码.待下发参数
		//取测量点数据
		String dataId = "";
		List<?> listData = marketTerminalConfigManager.getDataMpByTmnlNoMpSn(tmnlAssetNo, mpSn);
		if(listData.size() > 0){
			Map<?, ?> mapData = (Map<?, ?>) listData.get(0);
			dataId = String.valueOf(mapData.get("ID"));
		}
		
		//判断测量点参数是否存在
		String protItemNo = item.getCode();//规约项数据编码
		String currentValue = item.getValue();//参数当前值
		
		innerBlockSn = marketTerminalConfigManager.getInnerBlockSnByProtItemNo(protItemNo);
		
		List<?> tmnlMpParam = marketTerminalConfigManager.getTmnlMpParamByIdAndProtNo(dataId, protItemNo);
		if(tmnlMpParam != null && tmnlMpParam.size() > 0){
			Map<?, ?> mapMpParam = (Map<?, ?>) tmnlMpParam.get(0);
			//更新终端测量点参数
			String historyValue = mapMpParam.get("CURRENT_VALUE") == null ? "" :String.valueOf(mapMpParam.get("CURRENT_VALUE"));//参数历史值
			Date lastSendTime = (Date) mapMpParam.get("SUCCESS_TIME");//上次下发时间
			marketTerminalConfigManager.updateTmnlMpParam(dataId, protItemNo, currentValue, historyValue, blockSn, innerBlockSn, statusCode, lastSendTime);
		} else {
			//插入终端测量点参数
			marketTerminalConfigManager.insertTmnlMpParam(dataId, protItemNo, currentValue, blockSn, innerBlockSn, statusCode);
		}
	}

	/**
	 * 设置参数
	 * @param mpSn 
	 * @param ameterAssetNo 
	 * @param tmnlAssetNo 
	 */
	private List<Item> setTmnlMpParam(String tmnlAssetNo, String ameterId, String mpSn) {
		//取测量点数据
		List<?> listData = marketTerminalConfigManager.getDataMpByTmnlNoMpSn(tmnlAssetNo, mpSn);
		Map<?, ?> mapData = (Map<?, ?>) listData.get(0);
		String pt = mapData.get("PT") == null ? "0" : String.valueOf(mapData.get("PT"));//PT
		String ct = mapData.get("CT") == null ? "0" : String.valueOf(mapData.get("CT"));//CT
		
		//取表档案信息
		DMeter dMeter = new DMeter();
		dMeter = marketTerminalConfigManager.getDmeterByAssetNo(ameterId);
		String wiringMode = "0";//接线方式
		if (dMeter != null) {
			wiringMode = dMeter.getWiringMode();
		}
		List<?> list = marketTerminalConfigManager.getCapMeasMode(ameterId);
		String contract_cap = null;
		String meas_mode = null;
		if (null != list && list.size() > 0) {
			Map<?,?> map = (Map<?,?>)list.get(0);
			contract_cap = StringUtil.removeNull(map.get("CONTRACT_CAP"));
			meas_mode = StringUtil.removeNull(map.get("MEAS_MODE"));
		}
		log.debug("取得额定电压电流   参数    contract_cap: "+contract_cap+"   wiringMode: "+wiringMode+"   meas_mode: "+meas_mode+"   ct: "+ct+"   pt: "+pt);

		String dydl = marketTerminalConfigManager.getVoltCurr(contract_cap, wiringMode, meas_mode, ct, pt);
		String[] dydlArray = dydl.split(",");
		log.debug("额定电压电流: "+dydl);

		//取终端规约
		String protocolCode = null;
		RTmnlRun rTmnlRun = new RTmnlRun();
		rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
		protocolCode = Integer.toString(Integer.parseInt(rTmnlRun.getProtocolCode()));
		
		String protItemNo = "";//规约项数据编码
		List<Item> items = new ArrayList<Item>();
		//根据PROTOCOL_NO取PROT_ITEM_NO
		String protocolNo = protocolCode + AFN + FN;
		List<?> listProtItem = marketTerminalConfigManager.getProtItemByProtocolNo(protocolNo);
		if(listProtItem != null){
			if("1".equals(protocolCode) || "2".equals(protocolCode)){
				//04.05规约
				for(int i = 0; i < listProtItem.size(); i++){
					Map<?, ?> mapProtItem = (Map<?, ?>) listProtItem.get(i);
					protItemNo = StringUtil.removeNull(mapProtItem.get("PROT_ITEM_NO"));
					String protItemSn = String.valueOf(mapProtItem.get("PROT_ITEM_SN"));
					String protItemName = String.valueOf(mapProtItem.get("PROT_ITEM_NAME"));
					Item item = new Item(protItemNo);
					if("1".equals(protItemSn)){
						item.setValue(pt);              //电压互感器倍率
					}else if("2".equals(protItemSn)){
						item.setValue(ct);              //电流互感器倍率
					}else if("3".equals(protItemSn)){
						item.setValue(dydlArray[0]);    //额定电压
					}else if("4".equals(protItemSn)){
						item.setValue(Double.parseDouble(dydlArray[1]) > 5 ? "5" : dydlArray[1]);    //额定电流
					}else if("5".equals(protItemSn)){
						item.setValue(wiringMode);      //电源接线方式
					}
					items.add(item);
					//保存参数
					log.debug("【下发终端测量点参数】" + tmnlAssetNo + "," + protItemName +":"+item.getValue());
					this.updateTmnlMpParam(tmnlAssetNo, mpSn, item);
				}
			}else{
				//698规约
				for(int i = 0; i < listProtItem.size(); i++){
					Map<?, ?> mapProtItem = (Map<?, ?>) listProtItem.get(i);
					protItemNo = StringUtil.removeNull(mapProtItem.get("PROT_ITEM_NO"));
					String protItemSn = String.valueOf(mapProtItem.get("PROT_ITEM_SN"));
					String protItemName = String.valueOf(mapProtItem.get("PROT_ITEM_NAME"));
					Item item = new Item(protItemNo);
					if("1".equals(protItemSn)){
						item.setValue(pt);             //电压互感器倍率
					}else if("2".equals(protItemSn)){
						item.setValue(ct);             //电流互感器倍率
					}else if("3".equals(protItemSn)){
						item.setValue(dydlArray[0]);   //额定电压
					}else if("4".equals(protItemSn)){
						item.setValue(Double.parseDouble(dydlArray[1]) > 5 ? "5" : dydlArray[1]);   //额定电流
					}else if("5".equals(protItemSn)){       
						item.setValue(contract_cap);   //F25 额定负荷TODU（698特有）
					}else if("6".equals(protItemSn)){
						item.setValue(wiringMode);     //电源接线方式
					}else if("7".equals(protItemSn)){                    
						item.setValue("0");            //F25 单相表接线相TODU（698特有）0-3依次标识不确定，A项，B项，C相。
					}
					items.add(item);
					log.debug("【下发终端测量点参数】" + tmnlAssetNo + "," + protItemName +":"+item.getValue());
					//保存参数
					this.updateTmnlMpParam(tmnlAssetNo, mpSn, item);
				}
			}
		}
		return items;
	}
	

	/*
	 * 下发成功，更新终端测量点参数状态t_tmnl_mp_param
	 */
	private void updateTmnlMpParamStatue(String tmnlAssetNo) {
		//取终端规约
		String protocolCode = null;
		RTmnlRun rTmnlRun = new RTmnlRun();
		rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
		protocolCode = Integer.toString(Integer.parseInt(rTmnlRun.getProtocolCode()));
		
		String f25 = protocolCode + AFN + FN;
		String statusCode = Globals.SEND_STATUS_CODE_4;//状态编码.待下发参数
		String dataId = "";
		List<?> listEDataMp = marketTerminalConfigManager.getEDataMpByTmnl(tmnlAssetNo);
		if(listEDataMp != null){
			for(int i = 0; i < listEDataMp.size(); i++){
				Map<?, ?> mapEDataMp = (Map<?, ?>) listEDataMp.get(i);
				dataId = String.valueOf(mapEDataMp.get("ID"));
				
				//更新测量点参数状态为下发成功
				marketTerminalConfigManager.updateTmnlMpParamStatue(dataId, f25, statusCode);
			}
		}
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
}