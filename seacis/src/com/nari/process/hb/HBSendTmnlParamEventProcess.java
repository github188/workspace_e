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
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.global.Constant;
import com.nari.global.Globals;
import com.nari.model.CMeter;
import com.nari.service.MarketTerminalConfigManager;
import com.nari.util.StringUtil;

/**
 * 下发终端参数
 */
@SuppressWarnings("unused")
public class HBSendTmnlParamEventProcess implements Runnable{
	
	private static final Log log = LogFactory.getLog(HBSendTmnlParamEventProcess.class);
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public HBSendTmnlParamEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents=newEvents;
		this.oldEvents=oldEvents;
	}
	private MarketTerminalConfigManager marketTerminalConfigManager =  null;
	TaskDeal taskDeal = new TaskDeal();
	public static final String AFN = "04";
	public static final String FN_9 = "09";//F9
	public static final String FN_10 = "0A";//F10
	public static final String[] FN_9_ITEM = {"001", "002", "003", "004"};
	
	public void run() {
		log.info("【HB下发终端参数......】");
		marketTerminalConfigManager=(MarketTerminalConfigManager)Constant.getCtx().getBean("marketTerminalConfigManager");
		
		String appNo = (String) newEvents[0].get("appNo");
		String terminalId = (String) newEvents[0].get("terminalId");
		String tmnlAssetNo = (String) newEvents[0].get("tmnlAssetNo");
		Long debugId = (Long) newEvents[0].get("debugId");
		String meterFlag = (String) newEvents[0].get("meterFlag"); //换表标识
		String Status = null;

		try {
			//插入测试进度，将测试进度置为"下发终端参数开始"
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLPARAM_START);
			Status = sendTmnlParam(tmnlAssetNo, appNo);
			if("3".equals(Status)){

				this.updateTmnlParamStatue(tmnlAssetNo);
				//插入测试进度，将测试进度置为"下发终端参数成功"
				marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLPARAM_SUCCESS);
				//负控终端工作正常
				marketTerminalConfigManager.updateRTmnlDebugTerminalFlag(debugId, "1");
				//终端与主站通讯正常
				marketTerminalConfigManager.updateRTmnlDebugMasterCommFlag(debugId, "1");

				try {
					//Thread.sleep(1000 * 60 * 10);//测试的时候等5秒
					log.info("【下发终端参数后请等待......2秒】");
					int sleepTime = 2;
					Thread.sleep(1000 * sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//触发事件：抄读电表数据
				Constant.sendEvent(new HBRelayMeterReadingEvent(appNo, terminalId, tmnlAssetNo, debugId, "0", meterFlag));//TODU
				log.info("******************************************************************************************************************");
				log.info("******************************************************************************************************************");
				log.info("******************************************************************************************************************");
			}else{
				//插入测试进度，将测试进度置为"下发终端参数失败"
				//根据状态确定失败原因
				String failCause = "";
				if("1".equals(Status)){
					failCause = "终端不在线";
				}else if("4".equals(Status)){
					failCause = "终端通讯超时";
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
		} catch (Exception e) {
			e.printStackTrace();
			marketTerminalConfigManager.updateTestStatue(appNo, Globals.DEBUG_STATUE_FAILED);
		}
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
		//取终端规约
		String protocolCode = null;
		RTmnlRun rTmnlRun = new RTmnlRun();
		rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
		protocolCode = Integer.toString(Integer.parseInt(rTmnlRun.getProtocolCode()));
		
		//终端资产号集合
		List<String> tmnlAssetNos = new ArrayList<String>();
		tmnlAssetNos.add(tmnlAssetNo);
		// 参数项设置
		List<ParamItem> params = new ArrayList<ParamItem>();	
		if("1".equals(protocolCode) || "2".equals(protocolCode)){
			ParamItem pitem1 = new ParamItem();
			//04/05规约设置F9、F10；698规约只设置F10
			pitem1.setFn((short) Integer.parseInt("9"));//F9：终端配置数量表
			pitem1.setPoint((short) Integer.parseInt("0"));
			List<Item> items = setTmnlParamF9 (tmnlAssetNo);
			pitem1.setItems((ArrayList<Item>) items);
			params.add(pitem1);
		}
		ParamItem pitem2 = new ParamItem();
		pitem2.setFn((short) Integer.parseInt("10"));//F10：终端电能表/交流采样装置配置参数
		pitem2.setPoint((short) Integer.parseInt("0"));
		List<Item> items = setTmnlParamF10 (tmnlAssetNo, appNo);
		pitem2.setItems((ArrayList<Item>) items);
		
		params.add(pitem2);
		
		//下发状态
		String Status = marketTerminalConfigManager.getStatusByTaskDeal(tmnlAssetNos, FrontConstant.SET_PARAMETERS, params);
		
//		Status = "3";
		return Status;
	}
	
	/**
	 * 设置终端参数F9
	 * @param mpSn 
	 * @param ameterAssetNo 
	 * @param tmnlAssetNo 
	 */
	private List<Item> setTmnlParamF9(String tmnlAssetNo) {
		
		//取终端规约
		String protocolCode = null;
		RTmnlRun rTmnlRun = new RTmnlRun();
		rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
		protocolCode = Integer.toString(Integer.parseInt(rTmnlRun.getProtocolCode()));
		
		String protItemNo = "";//规约项数据编码
		List<Item> items = new ArrayList<Item>();
		for(int i = 0; i < FN_9_ITEM.length; i++){
			protItemNo = protocolCode + AFN + FN_9 + FN_9_ITEM[i];
			Item item = new Item(protItemNo);
			if("001".equals(FN_9_ITEM[i])){
				//F9 电能表/交流采样装置配置总块数TODU
				String mpSns = marketTerminalConfigManager.getMpSnByTmnl(tmnlAssetNo);
				item.setValue(mpSns);
			}else if("002".equals(FN_9_ITEM[i])){
				//F9 脉冲配置路数
				item.setValue("0");
			}else if("003".equals(FN_9_ITEM[i])){
				//F9 电压/电流模拟量配置总路数
				item.setValue("0");
			}else if("004".equals(FN_9_ITEM[i])){
				//F9 总加组配置总组数
				item.setValue("0");
			}
			items.add(item);
			log.info("【下发终端参数】" + tmnlAssetNo + "," + protItemNo +":"+item.getValue());
			this.updateTmnlParam(tmnlAssetNo, "0", item);
		}
		return items;
	}
	
	/**
	 * 设置终端参数F10
	 * @param mpSn 
	 * @param ameterAssetNo 
	 * @param tmnlAssetNo 
	 */
	private List<Item> setTmnlParamF10(String tmnlAssetNo, String appNo) {
		
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
		
		//根据appNo取终端变更TMNL_TASK_TYPE
		String tmnlTaskType = marketTerminalConfigManager.getTmnlTaskTypeByAppNo(appNo);
		String meterFlag = marketTerminalConfigManager.getMeterFlagByAppNo(appNo);
//		if(!"05".equals(tmnlTaskType) || !"1".equals(meterFlag)){//终端变更
			//根据tmnlAssetNo取测量点
			List<?> listEDateMp = marketTerminalConfigManager.getMpSnsByTmnl(tmnlAssetNo);
			if(listEDateMp != null){
				for(int j = 0; j < listEDateMp.size(); j++){
					Map<?, ?> mapEDateMp = (Map<?, ?>) listEDateMp.get(j);
					ameterId = StringUtil.removeNull(mapEDateMp.get("METER_ID"));
					String mpSn = String.valueOf(mapEDateMp.get("MP_SN"));
					String digits = getMeterDigitsByMeterId(ameterId);
					String[] digitsArray = digits.split(",");
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
					
					if(j == 0){
						protItemNo = protocolCode + AFN + FN_10 + "001";
						Item item = new Item(protItemNo);
						item.setValue(String.valueOf(listEDateMp.size()));
						items.add(item);
						log.info("【下发终端参数】终端编号：" + tmnlAssetNo + ",F10 电能表个数" + ":"+item.getValue());
						this.updateTmnlParam(tmnlAssetNo, "0", item);
					}
					
					//根据PROTOCOL_NO取PROT_ITEM_NO
					String protocolNo = protocolCode + AFN + FN_10;
					List<?> listProtItem = marketTerminalConfigManager.getProtItemByProtocolNo(protocolNo);
					if(listProtItem != null){
						for(int i = 1; i < listProtItem.size(); i++){
							Map<?, ?> mapProtItem = (Map<?, ?>) listProtItem.get(i);
							protItemNo = StringUtil.removeNull(mapProtItem.get("PROT_ITEM_NO"));
							String protItemSn = String.valueOf(mapProtItem.get("PROT_ITEM_SN"));
							String protItemName = String.valueOf(mapProtItem.get("PROT_ITEM_NAME"));
							Item item = new Item(protItemNo);
							if("2".equals(protItemSn)){
								//F10 电能表/交流采样装置序号
								item.setValue(mpSn);//待确认序号和测量点号相同TODU
							}else if("3".equals(protItemSn)){
								//F10 所属测量点号
								item.setValue(mpSn);
							}else if("4".equals(protItemSn)){
								//F10 通信端口号（需要召测回来确认，表示电能表、交流采样装置接入终端的通信端口号，取值范围1~31）
								item.setValue("1");
							}else if("5".equals(protItemSn)){
								//F10 通信速率（运行电能表-波特率）
								item.setValue(cMeter.getBaudrate() == null ? "0" : cMeter.getBaudrate());
							}else if("6".equals(protItemSn)){
								//F10 通信协议类型（运行电能表-通讯规约 ：645）
								if ("30".equals(commNo) && ("1".equals(protocolCode) || "2".equals(protocolCode))) {
									commNo = "7";//青海特殊处理(04，05规约  645/2007规约 30改为7)
								}
								item.setValue(commNo == null ? "1" : commNo);
							}else if("7".equals(protItemSn)){
								//F10 通信地址TODU（运行电能表-通讯地址1）
								item.setValue(cMeter.getCommAddr1() == null ? "0" : cMeter.getCommAddr1());
							}else if("8".equals(protItemSn)){
								//F10 通信密码（没有为空）
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
								//F10 有功电能示值整数位个数（数值范围0~3依次表示4~7位整数。6位）
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
								//F10 所属采集器通信地址
//								String commAddr = "";
//								String fmrAssetNo = cMeter.getFmrAssetNo();
//								List<?> listOtherDev = marketTerminalConfigManager.getOtherDev(fmrAssetNo);
//								if(listOtherDev != null){
//									Map<?, ?> mapOtherDev = (Map<?, ?>) listOtherDev.get(0);
//									commAddr = mapOtherDev.get("COMM_ADDR") == null ? "0" : String.valueOf(mapOtherDev.get("COMM_ADDR"));
//								}
//								item.setValue(commAddr);
								item.setValue("0");
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
							log.info("【下发终端参数】" + tmnlAssetNo + "," + protItemName +":"+item.getValue());
							items.add(item);
							this.updateTmnlParam(tmnlAssetNo, mpSn, item);
						}
					}
				}
			}
//		}else{
//			//终端不变，表变更
//			//取变更电表情况
//			List<?> list = marketTerminalConfigManager.getRTmnlTaskSr(appNo);
//			
//			if(list != null){	
//				for(int j = 0; j < list.size(); j++){
//					Map<?, ?> mapRTmnlTaskSr = (Map<?, ?>) list.get(j);
//					String newMeterId = (String) mapRTmnlTaskSr.get("NEW_METER_ID");//新电能表编号
//					List<?> listMeter = marketTerminalConfigManager.getAmeterById(newMeterId);
//					if(null != listMeter && listMeter.size() > 0) {
//						Map<?, ?> mapMeter = (Map<?, ?>) listMeter.get(0);
//						ameterId = (String) mapMeter.get("ASSET_NO");
//					}
//					//根据ameterAssetNo取电能表信息
//					CMeter cMeter = new CMeter();
//					cMeter = marketTerminalConfigManager.getAmeterByAssetNo(ameterId);
//					
//					//取表计的测量点号
//					String mpSn = marketTerminalConfigManager.getMpsnByTmnlAndMeter(tmnlAssetNo, ameterId);
//					
//					if(j == 0){
//						//F10 本次电能表/交流采样装置配置数量n
//						protItemNo = protocolCode + AFN + FN_10 + "001";
//						Item item = new Item(protItemNo);
//						item.setValue(String.valueOf(list.size()));
//						items.add(item);
//						log.info("【下发终端参数】终端编号：" + tmnlAssetNo + ",F10 电能表个数" + ":"+item.getValue());
//						this.updateTmnlParam(tmnlAssetNo, mpSn, item);
//					}
//					
//					//根据PROTOCOL_NO取PROT_ITEM_NO
//					String protocolNo = protocolCode + AFN + FN_10;
//					List<?> listProtItem = marketTerminalConfigManager.getProtItemByProtocolNo(protocolNo);
//					if(listProtItem != null){
//						for(int i = 1; i < listProtItem.size(); i++){
//							Map<?, ?> mapProtItem = (Map<?, ?>) listProtItem.get(i);
//							protItemNo = StringUtil.removeNull(mapProtItem.get("PROT_ITEM_NO"));
//							String protItemSn = String.valueOf(mapProtItem.get("PROT_ITEM_SN"));
//							String protItemName = String.valueOf(mapProtItem.get("PROT_ITEM_NAME"));
//							Item item = new Item(protItemNo);
//							if("2".equals(protItemSn)){
//								//F10 电能表/交流采样装置序号
//								item.setValue(mpSn);//待确认序号和测量点号相同TODU
//							}else if("3".equals(protItemSn)){
//								//F10 所属测量点号
//								item.setValue(mpSn);
//							}else if("4".equals(protItemSn)){
//								//F10 通信端口号（需要召测回来确认，表示电能表、交流采样装置接入终端的通信端口号，取值范围1~31）
//								//集抄（载波）为31，负控（485）为2
//								if("5".equals(protocolCode)){
//									item.setValue("31");
//								}else{
//									item.setValue("2");
//								}
//							}else if("5".equals(protItemSn)){
//								//F10 通信速率TODU（运行电能表-波特率）
//								item.setValue(cMeter.getBaudrate() == null ? "0" : cMeter.getBaudrate());
//							}else if("6".equals(protItemSn)){
//								//F10 通信协议类型（运行电能表-通讯规约 ：645）
//								item.setValue(cMeter.getCommNo() == null ? "30" : cMeter.getCommNo());
//							}else if("7".equals(protItemSn)){
//								//F10 通信地址TODU（运行电能表-通讯地址1）
//								item.setValue(cMeter.getCommAddr1() == null ? "0" : cMeter.getCommAddr1());
//							}else if("8".equals(protItemSn)){
//								//F10 通信密码TODU（没有为空）
//								item.setValue("0");
//							}else if("9".equals(protItemSn)){
//								//F10 电能费率个数TODU（默认3个）
//								item.setValue("4");
//							}else if("10".equals(protItemSn)){
//								//F10 有功电能示值小数位个数TODU（数值范围0~3依次表示1~4位小数。）
//								if(cMeter.getCommNo()==null || "30".equals(cMeter.getCommNo())){
//									item.setValue("3");//4位
//								}else{
//									item.setValue("1");//2位
//								}
//							}else if("11".equals(protItemSn)){
//								//F10 有功电能示值整数位个数TODU（需要召测回来确认）
//								item.setValue("2");
//							}else if("12".equals(protItemSn)){
//								//F10 所属采集器通信地址
////								String commAddr = "";
////								String fmrAssetNo = cMeter.getFmrAssetNo();
////								List<?> listOtherDev = marketTerminalConfigManager.getOtherDev(fmrAssetNo);
////								if(listOtherDev != null){
////									Map<?, ?> mapOtherDev = (Map<?, ?>) listOtherDev.get(0);
////									commAddr = mapOtherDev.get("COMM_ADDR") == null ? "0" : String.valueOf(mapOtherDev.get("COMM_ADDR"));
////								}
////								item.setValue(commAddr);
//								item.setValue("0");//新装表可以透抄，此地址可以不设，默认为零。
//							}else if("13".equals(protItemSn)){
//								//F10 用户大类号（E）
//								item.setValue("5");
//							}else if("14".equals(protItemSn)){
//								//F10 用户小类号
//								item.setValue("1");
//							}
//							log.info("【下发终端参数】" + tmnlAssetNo + "," + protItemName +":"+item.getValue());
//							items.add(item);
//							this.updateTmnlParam(tmnlAssetNo, mpSn, item);
//						}
//					}
//				}
//			}
//		}
		
		return items;
	}
	
	private void setItemF10(){
		
	}
	
	/**
	 * 终端测量点参数t_tmnl_paramF10
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
			String protItemNo = item.getCode();   //规约项数据编码
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
	
	/*
	 * 更新终端参数T_TMNL_PARAM状态为04
	 */
	private void updateTmnlParamStatue(String tmnlAssetNo){
		//取终端规约
		String protocolCode = null;
		RTmnlRun rTmnlRun = new RTmnlRun();
		rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
		protocolCode = Integer.toString(Integer.parseInt(rTmnlRun.getProtocolCode()));
		
		String f9 = protocolCode + AFN + FN_9;
		String f10 = protocolCode + AFN + FN_10;
		marketTerminalConfigManager.updateTmnlParamStatue(tmnlAssetNo, f9, f10);
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
}