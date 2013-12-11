package com.nari.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;
import com.nari.ami.database.map.basicdata.ATmnlRealTime;
import com.nari.ami.front.taskschedule.TaskHandle;
import com.nari.coherence.TaskDeal;
import com.nari.eventbean.BackFeedEvent;
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
import com.nari.service.MarketTerminalConfigManager;
import com.nari.util.ArrayToListUtil;
import com.nari.util.StringUtil;

/**
 * 抄读电表数据
 */
@SuppressWarnings("unused")
public class RelayMeterReadingEventProcess implements Runnable{
	
	private static final Log log = LogFactory.getLog(RelayMeterReadingEventProcess.class);
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public RelayMeterReadingEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents=newEvents;
		this.oldEvents=oldEvents;
	}
	
	public static final String AFN = "0A"; //读取设置参数
	public static final String FN = "0A";  //F10
	private MarketTerminalConfigManager marketTerminalConfigManager =  null;
	TaskDeal taskDeal = new TaskDeal();
	
	public void run() {
		log.debug("【RelayMeterReadingEventProcess抄读电表开始......】");
		log.debug("----------------------------【RelayMeterReadingEventProcess流程开始】----------------------------");
		marketTerminalConfigManager=(MarketTerminalConfigManager)Constant.getCtx().getBean("marketTerminalConfigManager");
		
		String appNo = (String) newEvents[0].get("appNo");
		String terminalId = (String) newEvents[0].get("terminalId");
		String tmnlAssetNo = (String) newEvents[0].get("tmnlAssetNo");
		Long debugId = (Long) newEvents[0].get("debugId");
		String flowFlag = (String) newEvents[0].get("flowFlag");//流程标识   
		String meterFlag = (String) newEvents[0].get("meterFlag"); //换表标识
		String mpSnList = null;
		String flowStatus = null;

		try {
			//更新测试状态为：抄读电表数据开始
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_METERREADING_START);

			if ("1".equals(flowFlag)) {
				log.debug("----------------------------【RelayMeterReadingEventProcess集抄流程开始】----------------------------");

				Map<?,?> map = marketTerminalConfigManager.getIFlowProcess(appNo, terminalId, Globals.SEND_TMNL);
				mpSnList = StringUtil.removeNull(map.get("MP_SN_LIST"));    //设置F10失败的测量点号
				mpSnList =  mpSnList.replaceAll(" ", "");
				flowStatus = StringUtil.removeNull(map.get("FLOW_STATUS")); //设置终端参数成功失败的标识符
			
				if (!"0".equals(flowStatus)) {

					String successList = relayF10MeterReading(tmnlAssetNo, mpSnList);
					if (successList.length() > 1) {
						successList = successList.substring(0, successList.length()-1);
						String[] s = successList.split(",");
						String[] m = mpSnList.split(",");
						List<String> mList = ArrayToListUtil.arrayToList(m);
						for (int i = 0; i < s.length; i++) {
							mList.remove(s[i]);
							log.debug("-------------------------除去成功的测量点：" +s[i]);
						}
						String temp = Arrays.toString(mList.toArray());
						mpSnList = temp.substring(1, temp.length()-1);
						mpSnList = mpSnList.replaceAll(" ", "");//余下的失败测量点
						log.debug("--------------------余下的测量点 ：" +mpSnList);
					}
				}

				if (null != mpSnList && mpSnList.length() >= 1) {
					flowStatus = "1";
				} else {
					flowStatus = "0";
				}
				
				String failCode = "";
				//重新更新设置F10的明细
				log.debug("申请编号="+appNo+",终端资产号="+tmnlAssetNo+",流程节点="+Globals.SEND_TMNL+",流程状态="+flowStatus+",失败测量点明细="+mpSnList);
				Map<?,?> flowmap = marketTerminalConfigManager.getIFlowProcess(appNo, tmnlAssetNo, Globals.SEND_TMNL);
				if (flowmap != null ) {
					failCode = StringUtil.removeNull(flowmap.get("FAILURE_CODE"));
				}
				marketTerminalConfigManager.updateIFlowProcess(appNo, tmnlAssetNo, Globals.SEND_TMNL, flowStatus, mpSnList, failCode);
				
				//设置 relayMeter流程明细
				marketTerminalConfigManager.updateIFlowProcess(appNo, tmnlAssetNo, Globals.RELAY_METER, flowStatus, "", "");
				log.debug("----------------------------【RelayMeterReadingEventProcess集抄流程结束】----------------------------");
				
			} else {
				log.debug("----------------------------【RelayMeterReadingEventProcess负控流程开始】----------------------------");
				flowStatus = relayMeterReading(tmnlAssetNo);
				log.debug("----------------------------【RelayMeterReadingEventProcess负控流程结束】----------------------------");
			}
			
			if ("1".equals(flowFlag)) {
				//更新测试状态为：抄读电表数据成功
				marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_METERREADING_SUCCESS);
				//终端与电能表通讯正常
				marketTerminalConfigManager.updateRTmnlDebugMeterCommFlag(debugId, "1");
				log.debug("----------------------------【RelayMeterReadingEventProcess流程结束】----------------------------");
				//触发事件：下发终端测量点参数
				Constant.sendEvent(new SendTmnlMpParamEvent(appNo, terminalId, tmnlAssetNo, debugId, flowFlag, meterFlag));//TODU
			} else {
				if("3".equals(flowStatus)){
					//更新测试状态为：抄读电表数据成功
					marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_METERREADING_SUCCESS);
					//终端与电能表通讯正常
					marketTerminalConfigManager.updateRTmnlDebugMeterCommFlag(debugId, "1");
					log.debug("----------------------------【RelayMeterReadingEventProcess流程结束】----------------------------");
					//触发事件：下发终端测量点参数
					Constant.sendEvent(new SendTmnlMpParamEvent(appNo, terminalId, tmnlAssetNo, debugId, flowFlag, meterFlag));//TODU
				}else{
					//更新测试状态为：抄读电表数据失败
					marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_SENDTMNLPARAM_FAILED, "抄读电表超时");
					//终端与电能表通讯正常
					marketTerminalConfigManager.updateRTmnlDebugMeterCommFlag(debugId, "0");
					log.debug("【抄读电表数据失败】, tmnlAssetNo = " + tmnlAssetNo);
					//触发事件：反馈营销
					Constant.sendEvent(new BackFeedEvent(appNo, debugId));
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
	 * 抄读电表数据
	 * 
	 * @param tmnlAssetNo
	 * @throws Exception
	 */
	private String relayMeterReading (String tmnlAssetNo){
		//抄表状态
		String Status = "";
		//终端资产号集合
		List<String> tmnlAssetNos = new ArrayList<String>();
		tmnlAssetNos.add(tmnlAssetNo);
		
		String mpSn = "";
		List<?> listMpSns = marketTerminalConfigManager.getMpSnsByTmnl(tmnlAssetNo);

		List<RealDataItem> params = new ArrayList<RealDataItem>();
		List<Response> respList = null;
		if(listMpSns != null && listMpSns.size() > 0){	
			for(int i = 0; i < listMpSns.size(); i++){	
				Map<?, ?> mapMpSns = (Map<?, ?>) listMpSns.get(i);
				mpSn = String.valueOf(mapMpSns.get("MP_SN"));
				
				//抄F33
				ArrayList<Item> codes = new ArrayList<Item>();
				Item item  = new Item("1112FF");//F33大项的透明规约编码
				codes.add(item);
				
				RealDataItem pitem = new RealDataItem();
				pitem.setCodes(codes);
				pitem.setPoint((short) Integer.parseInt(mpSn));
				params.add(pitem);
				
				if((i+1) % 10 == 0 || (i+1) == listMpSns.size()){

					TaskDeal taskDeal = new TaskDeal();
					taskDeal.callRealData(tmnlAssetNos, params);
					respList = taskDeal.getAllResponse(marketTerminalConfigManager.getListenTime());

					if (respList != null && respList.size() > 0) {
						for (int j = 0; j < respList.size(); j++) {
							List<DbData> datas = respList.get(j).getDbDatas();
							String flag = StringUtil.removeNull(respList.get(j).getTaskStatus());
							if (datas != null) {
								for (int m = 0; m < datas.size(); m++) {
									String pn = StringUtil.removeNull(datas.get(m).getPn());
									List<?> listDataMp = marketTerminalConfigManager.getDataMpByTmnlNoMpSn(tmnlAssetNo, pn);
									String meterId = "";
									String mr_flag = "";
									if(listDataMp != null && listDataMp.size() > 0){
										Map<?, ?> mapDataMp = (Map<?, ?>) listDataMp.get(0);
										meterId = StringUtil.removeNull(mapDataMp.get("METER_ID"));
										if(listDataMp.size() > 1){
											log.debug("终端" + tmnlAssetNo + "的测量点" + pn + "数据有问题！");
										}
										String mr_num = StringUtil.removeNull(datas.get(m).getDataCodes().get(1).getValue());
										//保存调试抄表状态
										if("3".equals(flag)){
											Status = "3";
											mr_flag = "1";
										}else{
											mr_flag = "0";
										}
										marketTerminalConfigManager.insertIMeterRead(meterId, mr_flag, mr_num);
									}
								}
							}
						}
					}
					//参数初始化params
					params = new ArrayList<RealDataItem>();
				}
			}
			
		}
		return Status;
	}
	
	/**
	 * 集抄失败测量点抄读电表数据
	 * 
	 * @param tmnlAssetNo
	 * @param mpSnList
	 * @throws Exception
	 */
	private String relayF10MeterReading (String tmnlAssetNo, String mpSnList){
		String Status = "";
		String[] mpSnArrary = mpSnList.split(",");
		List<String> tmnlAssetNos = new ArrayList<String>();
		tmnlAssetNos.add(tmnlAssetNo);
		
		String mpSn = "";
		List<Response> respList = null;
		StringBuffer sf = new StringBuffer();
		ArrayList<Item> itemList = new ArrayList<Item>();
		ParamItem pitem = new ParamItem();
		ArrayList<ParamItem> paramsList = new ArrayList<ParamItem>();
		int seq = 0;

		int callNum = marketTerminalConfigManager.getCallNum();
		log.debug("电表注册失败的测量点抄F10参数: "+mpSnList);
		
		for(int i = 0; i < mpSnArrary.length; i++){
			mpSn = mpSnArrary[i];
			String protocol = null;
			Item item = null;
			if (i == 0 || (i+1) % callNum == 0) {
				protocol= "50A0A001";
				item = new Item(protocol);
				item.setValue(String.valueOf(mpSnArrary.length));
				itemList.add(item);
				item = new Item("50A0A002");
				item.setValue(mpSnArrary[i]);
				itemList.add(item);
			} else {
				protocol= "50A0A002";
				item = new Item(protocol);
				item.setValue(mpSnArrary[i]);
				itemList.add(item);
			}

			if((i+1) % callNum == 0 || (i+1) == mpSnArrary.length){
				pitem.setFn((short) Integer.parseInt("10")); 
				pitem.setPoint((short) Integer.parseInt("0"));
				pitem.setItems(itemList);
				paramsList.add(pitem);
				log.debug("---------抄F10序号---------"+(seq++));

				TaskDeal taskDeal = new TaskDeal();
				taskDeal.qstTermParamTaskResult(tmnlAssetNos, FrontConstant.QUERY_PARAMS, paramsList);
				log.debug("【终端ID: "+tmnlAssetNo+" 召测F10等待时间.....90秒】");
				respList = taskDeal.getAllResponse(marketTerminalConfigManager.getListenTime());
				//取抄表结果
				if (respList != null && respList.size() > 0) {
					for (int j = 0; j < respList.size(); j++) {
						List<ResponseItem> responseItemList = respList.get(j).getResponseItems();
						if (null != responseItemList) {
							String pn = "";       //测量点号
							String commAddr = ""; //通信地址
							String frmAddr = "";  //采集器地址

							for (int m = 0; m < responseItemList.size(); m++) {
								ResponseItem responseItem = responseItemList.get(m);
								if (null != responseItem) {
									String porcotolCode = StringUtil.removeNull(responseItemList.get(m).getCode());

									if ("5040A002".equals(porcotolCode)) {
										pn = responseItemList.get(m).getValue();
										log.debug("-----------------测量点号: "+pn);
									}

									if ("5040A007".equals(porcotolCode)) {
										commAddr = responseItemList.get(m).getValue();	
										log.debug("-----------------测量点地址: "+commAddr);
									}
									if ("5040A00C".equals(porcotolCode)) {
										frmAddr = responseItemList.get(m).getValue();	
										log.debug("-----------------采集器地址: "+frmAddr);
									}

									if (!"".equals(pn) && !"".equals(commAddr) && "000000000000".equals(frmAddr)) {
										commAddr =  this.getRealCommAddr(commAddr);
										List<?> eDataMp = marketTerminalConfigManager.getDataMpByMpSnComm1(pn, commAddr);
										if (null != eDataMp && eDataMp.size() > 0) {
											sf.append(pn+",");
										}
										pn = "";
										commAddr = "";
										frmAddr = "";
									}
								}
							}
						}
					}
				}
				pitem = new ParamItem();
				itemList = new ArrayList<Item>();
				paramsList = new ArrayList<ParamItem>();
			}
		}
		log.debug("-----------补召F10参数成功的测量点: " +sf.toString());
		
		marketTerminalConfigManager.batchUpdateTTmnlParamStatus(tmnlAssetNo, sf.toString());
		
		return sf.toString();
	}
	
	
	/**
	 * 取得正确的通讯地址
	 * @param commAddr
	 * @return
	 */
	private String getRealCommAddr(String commAddr) {
		int flag = 0;
		StringBuffer sf = new StringBuffer();
		char a[] = commAddr.toCharArray();
		for (int i  = 0; i < a.length; i++) {
			if (!"0".equals(String.valueOf(a[i]))) {
				flag = 1;
			}
			if (flag == 1) {
				sf.append(a[i]);
			}
		}
		return 	sf.toString();	
	}
}