package com.nari.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;
import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.ami.database.map.measurepoint.EDataMp;
import com.nari.ami.database.map.measurepoint.EDataMpId;
import com.nari.ami.database.map.runcontrol.RTmnlRun;
import com.nari.eventbean.SendFrmTmnlParamEvent;
import com.nari.eventbean.SendTmnlParamEvent;
import com.nari.eventbean.SendTmnlParamOtherEvent;
import com.nari.global.Constant;
import com.nari.global.Globals;
import com.nari.model.CMeter;
import com.nari.model.Edatamp;
import com.nari.service.MarketTerminalConfigManager;
import com.nari.util.StringUtil;

/**
 * 触发测试事件
 */
@SuppressWarnings("unused")
public class TriggerTestEventProcess implements Runnable{
	
	private static final Log log = LogFactory.getLog(TriggerTestEventProcess.class);
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public TriggerTestEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents=newEvents;
		this.oldEvents=oldEvents;
	}
	private MarketTerminalConfigManager marketTerminalConfigManager;
	
	public MarketTerminalConfigManager getMarketTerminalConfigManager() {
		return marketTerminalConfigManager;
	}

	public void setMarketTerminalConfigManager(
			MarketTerminalConfigManager marketTerminalConfigManager) {
		this.marketTerminalConfigManager = marketTerminalConfigManager;
	}

	public void run() {
		
		marketTerminalConfigManager=(MarketTerminalConfigManager)Constant.getCtx().getBean("marketTerminalConfigManager");
		String appNo = (String) newEvents[0].get("appNo");
		String terminalId = (String) newEvents[0].get("terminalId");
		String tmnlAssetNo = (String) newEvents[0].get("tmnlAssetNo");
		String flowFlag = (String) newEvents[0].get("flowFlag");
		String meterFlag = marketTerminalConfigManager.getMeterFlagByAppNo(appNo);//是否换表标识
		
		//根据appNo取终端变更TMNL_TASK_TYPE
		String tmnlTaskType = marketTerminalConfigManager.getTmnlTaskTypeByAppNo(appNo);
		
		try {
			log.debug("【TriggerTestEventProcess建档开始......】");

			String flowStatus = null;//流程状态 
			String failureNum = null;//失败总数
			String failureList = null;//失败测量点明细
			
			//插入测试进度
			marketTerminalConfigManager.insertTmnlDebug(appNo, terminalId, terminalId, Globals.DEBUG_START);
			//测量点数据主表数量
			String successNum = String.valueOf(marketTerminalConfigManager.getEDataMpNumByCpNo(terminalId));
			//插入终端调试信息
			Long debugId = marketTerminalConfigManager.insertRTmnlDebug(terminalId);
			
			//从终端流程取出明细
			Map<?,?> map = marketTerminalConfigManager.getIFlowProcess(appNo, terminalId, Globals.TRIGGER_EVENT);
			if (map != null) {
				flowStatus = StringUtil.removeNull(map.get("FLOW_STATUS"));
			}
			
			String changeTmnlAddrFlag = this.analyseTmnlAddr(tmnlAssetNo);
			if ("0".equals(flowFlag) || ("1".equals(flowFlag) && null == map && !"1".equals(meterFlag)) || "1".equals(changeTmnlAddrFlag)) {
				if ("0".equals(flowFlag)) {
					log.debug("----------------------------【负控流程开始】----------------------------");
				} else {
					log.debug("----------------------------【集抄流程开始】----------------------------");
				}
				
				//通知分布式缓存
				this.coherenceStatement(terminalId);
				log.debug("【通知分布式缓存R_TMNL_RUN结束】terminalId:" + terminalId);
				
				String mpSn = "";
				String ameterId = "";
				
				//建测量点0
				mpSn = "0";
				this.updateDataMp(tmnlAssetNo, tmnlAssetNo, mpSn, 0, flowFlag, null, null);
				log.debug("----------------------------【为终端建立测量点数据主表数据结束】----------------------------");
				
				//根据appNo取CP_NO
				String cpNo = marketTerminalConfigManager.getCpNoByAppNo(appNo);
				//根据CP_NO取METER_ID
				List<?> listCollObj = marketTerminalConfigManager.getMeterListByCpNo(cpNo);
				int flag = 0;
				List<Edatamp> edataLst = new ArrayList<Edatamp>();
				if(listCollObj != null){
					int mainMeterCnt = this.caclMainMeterNum(listCollObj);
					int k = 1;
					int q = 1;
					int w = 0;
					String endFlag = "0";
					for(int i = 0; i < listCollObj.size(); i++){
						Map<?, ?> mapCollObj = (Map<?, ?>) listCollObj.get(i);
						ameterId = String.valueOf(mapCollObj.get("METER_ID"));
						long z = 9;
						if ("0".equals(flowFlag)) {
							log.debug("--------表ID： "+ameterId);
							mpSn = this.caclMpSn(appNo, tmnlAssetNo, ameterId);	
							log.debug("--------测量点号 ： "+mpSn);
						}
						else {
							z = this.checkMainMater(ameterId); //0：主表   1：居民表
							if (z == 0) {
								w = this.checkMainMeterIsHaved(tmnlAssetNo, k, w);
								mpSn = String.valueOf(w);
								k++;
							} else {
								mpSn = String.valueOf(mainMeterCnt + q);
								q++;
							}
						}
						if (i+1 == listCollObj.size()) {
							endFlag = "1";
						}
						//建立测量点
						this.updateDataMp(tmnlAssetNo, ameterId, mpSn, z, flowFlag, edataLst, endFlag);
						//更新运行电能表c_meter中的，注册序号REG_SN，（REG_SN=mpSn）//需要确认是否所有的表计都需要更新此状态
						marketTerminalConfigManager.updateMeterRegSn(ameterId, mpSn);
					}
				}
				//更新运行终端表中的接线方式
				marketTerminalConfigManager.updateWiringMode(tmnlAssetNo);
				if ("0".equals(flowFlag)) {
					log.debug("----------------------------【负控流程结束】----------------------------");
				} else {
					log.debug("----------------------------【集抄流程结束】----------------------------");
				}
			}
				
			if ("1".equals(flowFlag) && "1".equals(meterFlag) && !"0".equals(flowStatus) ) {
				log.debug("----------------------------【进入集抄换表流程】----------------------------");
			    String ameterId = null;        //电能表ID
			    String mpSn = null;            //测量点编号
			    String changeOrAddMpFlag = ""; //换表or新增表标识(0:换表  1：新增)
			    StringBuffer failMp = new StringBuffer();
				List<?> list = marketTerminalConfigManager.getRTmnlTaskSr(appNo);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Map<?, ?> mapRTmnlTaskSr = (Map<?, ?>) list.get(i);
						String newMeterId = StringUtil.removeNull(mapRTmnlTaskSr.get("NEW_METER_ID"));//新电能表编号
						String oldMeterId = StringUtil.removeNull(mapRTmnlTaskSr.get("OLD_METER_ID"));//原电能表编号

						if("".equals(oldMeterId)){
							log.debug("----------------------------【新增加电能表】----------------------------");
							//新装
							ameterId = newMeterId;
							mpSn = marketTerminalConfigManager.getMaxMpSn(tmnlAssetNo);
							failMp.append(mpSn+",");
							changeOrAddMpFlag = "1";
							
							log.debug("---------------------------METER_ID: "+ameterId);
							log.debug("---------------------------测量点号: "+mpSn);
						}else if(!oldMeterId.equals(newMeterId) && !"".equals(newMeterId) && !"".equals(oldMeterId)){
							log.debug("----------------------------【更换电能表】----------------------------");
							ameterId = newMeterId;
							mpSn = marketTerminalConfigManager.getMpsnByTmnlAndMeter(tmnlAssetNo, oldMeterId);
							failMp.append(mpSn+",");
							changeOrAddMpFlag = "0";

							log.debug("---------------------------METER_ID: "+ameterId);
							log.debug("---------------------------测量点号: "+mpSn);
						}else if(oldMeterId.equals(newMeterId)){
							log.debug("----------------------------【更换CT/PT】----------------------------");
							ameterId = newMeterId;
							mpSn = marketTerminalConfigManager.getMpsnByTmnlAndMeter(tmnlAssetNo, ameterId);
							failMp.append(mpSn+",");
							changeOrAddMpFlag = "2";
							
							log.debug("---------------------------METER_ID: "+ameterId);
							log.debug("---------------------------测量点号: "+mpSn);
						}
						long z = this.checkMainMater(ameterId);
						//建立测量点
						this.updateDataMp(tmnlAssetNo, ameterId, mpSn, z, flowFlag, null, null);
						//删除
						marketTerminalConfigManager.cancelMeter(oldMeterId);
						//注册序号REG_SN，（REG_SN=mpSn）
						marketTerminalConfigManager.updateMeterRegSn(ameterId, mpSn);
						
						//新增加表
						if ("".equals(oldMeterId) && !oldMeterId.equals(newMeterId)) {
							marketTerminalConfigManager.insertGIoMpByMeterId(tmnlAssetNo, oldMeterId, newMeterId);
						}
						//换表
						else if (!"".equals(oldMeterId) && !oldMeterId.equals(newMeterId)) {
							marketTerminalConfigManager.updateGIoMp(oldMeterId);
							marketTerminalConfigManager.insertGIoMpByMeterId(tmnlAssetNo, oldMeterId, newMeterId);
						}
					}
				}
				failureList = failMp.substring(0, failMp.length()-1);
				if (!"03".equals(tmnlTaskType)) {
					//更新流程明细
					this.updateIFlowProcessForChangeAddMeter(appNo, terminalId, changeOrAddMpFlag, failureList);
				}
				
				log.debug("----------------------------【集抄换表流程结束】----------------------------");
			}
			
			try {
				//Thread.sleep(1000 * 60 * 10);//测试的时候等5秒
				log.debug("【通知分布式缓存R_TMNL_RUN和E_DATA_MP后等待1秒下发终端参数......】");
				Thread.sleep(1000 * 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			if ("1".equals(flowFlag)) {
				log.debug("----------------------------【进入更新集抄流程明细】----------------------------");
				String mpSnList = "";
				flowStatus = "0";    //成功后状态为0
				failureNum = "0";    //失败总数

				if (!"0".equals(flowStatus)) {
					if ("1".equals(meterFlag)) {
						successNum = String.valueOf(failureList.split(",").length);
						failureNum = "0";
					}	
				}
				
				log.debug("申请编号="+appNo+",终端资产号="+tmnlAssetNo+",流程节点="+Globals.TRIGGER_EVENT+",流程状态="+flowStatus+",成功数量="+successNum+",失败数量="+failureNum+",失败测量点明细="+mpSnList);
				marketTerminalConfigManager.updateIFlowProcess(appNo, tmnlAssetNo, Globals.TRIGGER_EVENT, flowStatus, mpSnList, "");
				log.debug("----------------------------【更新流程明细TriggerTestEvent结束】----------------------------");
				
				RTmnlRun rTmnlRun = new RTmnlRun();
				rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
				String protocolCode = Integer.toString(Integer.parseInt(rTmnlRun.getProtocolCode()));
				//规约分叉调用不同流程
				if("5".equals(protocolCode)){
					Constant.sendEvent(new SendFrmTmnlParamEvent(appNo, terminalId, tmnlAssetNo, debugId, flowFlag, meterFlag));
				} else {
					/**-------------东软等其他规约---------------*/
					Constant.sendEvent(new SendTmnlParamOtherEvent(appNo, terminalId, tmnlAssetNo, debugId));
				}
			}
			
			else {
				Constant.sendEvent(new SendTmnlParamEvent(appNo, terminalId, tmnlAssetNo, debugId, meterFlag));
			}
			log.debug("----------------------------【建档TriggerTestEventProcess结束】----------------------------");
			log.debug("******************************************************************************************************************");
			log.debug("******************************************************************************************************************");
			log.debug("******************************************************************************************************************");
		} catch(Exception e) {
			e.printStackTrace();
			marketTerminalConfigManager.updateTestStatue(appNo, Globals.DEBUG_STATUE_FAILED);
		}
	}
	
	/**
	 * 检验是否主表
	 */
	private long checkMainMater(String ameterId) {
		//检验是否是电能主表
		return marketTerminalConfigManager.checkMainMater(ameterId);
	}
	
	/**
	 * 计算主表数量
	 * @param listCollObj
	 * @return
	 */
	private int caclMainMeterNum(List<?> listCollObj) {
		int cnt = 0;
		for (int i = 0; i < listCollObj.size(); i++) {
			Map<?, ?> mapCollObj = (Map<?, ?>) listCollObj.get(i);
			String ameterId = String.valueOf(mapCollObj.get("METER_ID"));
			long z = this.checkMainMater(ameterId);
			if (z == 0) {
				cnt++;
			}
		}
		return cnt;
	}
	
	
	/**
	 * 取得负控测量点号
	 * @param appNo
	 * @param tmnlAssetNo
	 * @param ameterId
	 * @return
	 */
	private String caclMpSn(String appNo, String tmnlAssetNo, String ameterId) {
		String tmnlTaskType = "";
		List<?> iTaskLst = marketTerminalConfigManager.getITmnlTaskByAppNo(appNo);
		if (iTaskLst != null && iTaskLst.size() > 0) {
			Map<?,?> taskMap = (Map<?,?>) iTaskLst.get(0);
			tmnlTaskType = StringUtil.removeNull(taskMap.get("TMNL_TASK_TYPE"));
		}
		
		String mpSn = "";
		List<?> lst = marketTerminalConfigManager.getEDataMpByMeterId(tmnlAssetNo, ameterId);
		if (lst != null && lst.size() > 0) {
			log.debug("-------------------测量点号:E_DATA_MP存在数据--------------------");
			Map<?,?> map = (Map<?,?>) lst.get(0);
			mpSn = StringUtil.removeNull(map.get("MP_SN"));
		} 
		else {
			List<?> taskSrLst = marketTerminalConfigManager.getRTmnlTaskSr(appNo, ameterId);
			if ((taskSrLst != null && taskSrLst.size() > 0) && !"03".equals(tmnlTaskType)) {
				
				Map<?,?> map = (Map<?,?>) taskSrLst.get(0);
				String oldMeterId = StringUtil.removeNull(map.get("OLD_METER_ID"));
				if (!"".equals(oldMeterId)) {
					log.debug("-------------------测量点号：R_TMNL_TASK_SR存在表变更: 换表--------------------");
					marketTerminalConfigManager.updateEDataMpByTmnlMeterId(tmnlAssetNo, oldMeterId);
					List<?> oldlst = marketTerminalConfigManager.getEDataMpForChangeMeterByMeterId(tmnlAssetNo, oldMeterId);
					Map<?,?> oldMap = (Map<?,?>) oldlst.get(0);
					mpSn = StringUtil.removeNull(oldMap.get("MP_SN"));
				}
				else {
					log.debug("-------------------测量点号：R_TMNL_TASK_SR存在表变更: 新增表--------------------");
					String maxMpSn = marketTerminalConfigManager.getEDataMpMaxMpsn(tmnlAssetNo);
					if ("".equals(maxMpSn)) {
						mpSn = "1";
					} else {
						mpSn = String.valueOf(Integer.parseInt(maxMpSn) + 1);
					}
				}
			} else {
				log.debug("-------------------测量点号：新安装或者换终端--------------------");
				String maxMpSn = marketTerminalConfigManager.getEDataMpMaxMpsn(tmnlAssetNo);
				if ("".equals(maxMpSn)) {
					mpSn = "1";
				} else {
					mpSn = String.valueOf(Integer.parseInt(maxMpSn) + 1);
				}
			}
		}
		return mpSn;
	}
	
	/**
	 * 通知分布式缓存R_TMNL_RUN
	 * @param terminalId
	 * @throws Exception
	 */
	private void coherenceStatement(String terminalId) {
		IStatement statement = CoherenceStatement.getSharedInstance();
		RTmnlRun rTmnlRun = new RTmnlRun();
		List<?> listTmnl = marketTerminalConfigManager.getTerminalById(terminalId);
		if(listTmnl != null && listTmnl.size() > 0){
			Map<?, ?> mapTmnl = (Map<?, ?>) listTmnl.get(0);
			rTmnlRun.setTerminalId(terminalId);//TODO
//			rTmnlRun.setTerminalId("123456");//TODO
			rTmnlRun.setCpNo(StringUtil.removeNull(mapTmnl.get("CP_NO")));
			rTmnlRun.setTmnlAssetNo(StringUtil.removeNull(mapTmnl.get("TMNL_ASSET_NO")));
//			rTmnlRun.setTmnlAssetNo(StringUtil.removeNull("123456"));
			rTmnlRun.setTerminalAddr(StringUtil.removeNull(mapTmnl.get("TERMINAL_ADDR")));
			rTmnlRun.setCisAssetNo(StringUtil.removeNull(mapTmnl.get("CIS_ASSET_NO")));
			rTmnlRun.setSimNo(StringUtil.removeNull(mapTmnl.get("SIM_NO")));
			rTmnlRun.setId(StringUtil.removeNull(mapTmnl.get("ID")));
			rTmnlRun.setFactoryCode(StringUtil.removeNull(mapTmnl.get("FACTORY_CODE")));
			rTmnlRun.setAttachMeterFlag(StringUtil.removeNull(mapTmnl.get("ATTACH_METER_FLAG")));
			rTmnlRun.setTerminalTypeCode(StringUtil.removeNull(mapTmnl.get("TERMINAL_TYPE_CODE")));
			rTmnlRun.setCollMode(StringUtil.removeNull(mapTmnl.get("COLL_MODE")));
			rTmnlRun.setProtocolCode(StringUtil.removeNull(mapTmnl.get("PROTOCOL_CODE")));
			rTmnlRun.setCommPassword(StringUtil.removeNull(mapTmnl.get("COMM_PASSWORD")));
			rTmnlRun.setRunDate((Date) (mapTmnl.get("RUN_DATE")== null ? new Date() : mapTmnl.get("RUN_DATE")));
			rTmnlRun.setStatusCode(StringUtil.removeNull(mapTmnl.get("STATUS_CODE")));
			rTmnlRun.setHarmonicDevFlag(StringUtil.removeNull(mapTmnl.get("HARMONIC_DEV_FLAG")));
			rTmnlRun.setPsEnsureFlag(StringUtil.removeNull(mapTmnl.get("PS_ENSURE_FLAG")));
			rTmnlRun.setAcSamplingFlag(StringUtil.removeNull(mapTmnl.get("AC_SAMPLING_FLAG")));
			rTmnlRun.setEliminateFlag(StringUtil.removeNull(mapTmnl.get("ELIMINATE_FLAG")));
			rTmnlRun.setGateAttrFlag(StringUtil.removeNull(mapTmnl.get("GATE_ATTR_FLAG")));
			rTmnlRun.setPrioPsMode(StringUtil.removeNull(mapTmnl.get("PRIO_PS_MODE")));
			rTmnlRun.setFreezeMode(StringUtil.removeNull(mapTmnl.get("FREEZE_MODE")));
			rTmnlRun.setFreezeCycleNum(Integer.parseInt(StringUtil.removeNull(mapTmnl.get("FREEZE_CYCLE_NUM"))));
//			rTmnlRun.setMaxLoadCurveDays(Integer.parseInt("".equals(StringUtil.removeNull(mapTmnl.get("MAX_LOAD_CURVE_DAYS")))? "0" : StringUtil.removeNull(mapTmnl.get("MAX_LOAD_CURVE_DAYS"))));
//			rTmnlRun.setPsLineLen(Integer.parseInt(StringUtil.removeNull(mapTmnl.get("PS_LINE_LEN"))));
			rTmnlRun.setWorkPs(StringUtil.removeNull(mapTmnl.get("WORK_PS")));
			rTmnlRun.setSpeakerFlag(StringUtil.removeNull(mapTmnl.get("SPEAKER_FLAG")));
//			rTmnlRun.setSpeakerDist(Integer.parseInt(String.valueOf(mapTmnl.get("SPEAKER_DIST"))));
			if("1".equals(StringUtil.removeNull(mapTmnl.get("SEND_UP_MODE")))){
				rTmnlRun.setSendUpMode(true);
			}else{
				rTmnlRun.setSendUpMode(false);
			}	
			rTmnlRun.setCommMode(StringUtil.removeNull(mapTmnl.get("COMM_MODE")));
			rTmnlRun.setFrameNumber(Short.valueOf(StringUtil.removeNull(mapTmnl.get("FRAME_NUMBER"))));
			rTmnlRun.setPowerCutDate((Date) mapTmnl.get("POWER_CUT_DATE"));
		}
		try {
			//调用分布式缓存
			statement.executeUpdate(RTmnlRun.class, rTmnlRun.getTerminalId(), rTmnlRun);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建立测量点
	 * 
	 * @param tmnlAssetNo
	 * @param ameterAssetNo
	 * @param mpSn
	 * @param oldMeterId
	 * @param isMainMeter
	 * @throws Exception
	 */
	private void updateDataMp(String tmnlAssetNo, String aMeterId, String mpSn, long isMainMeter, 
			String flowFlag, List<Edatamp> edataLst, String endFlag) {	

		String orgNo = "";//供电单位
		String areaNo = "";//供电区域编码
		String consNo = "";//用户编号
		String mpType = "";//测量点类型
		String dataSrc = "1";//数据来源.01-485,02-脉冲,03-交采,04-直流模拟量(默认为1)
		String collObjId = "";//采集对象标识
		String pt = "";//PT 
		String ct = "";//CT
		String selfFactor = "";//自身倍率
		String calcMode = "0";//参与计算方式?
		String isValid = "";//是否有效
		String ameterAssetNo = "";
		String areaCode = "";
		String cpNo = "";//采集点编号
		List<?> listData = marketTerminalConfigManager.getDataMpByParam(tmnlAssetNo, mpSn, aMeterId);

		//根据终端资产号取采集点编号
		RTmnlRun rTmnlRun = new RTmnlRun();
		rTmnlRun = marketTerminalConfigManager.getTmnlByAssetNo(tmnlAssetNo);
		String cisTmnlAssetNo = rTmnlRun.getCisAssetNo();//0525+终端资产号
		String terminalAddr = rTmnlRun.getTerminalAddr();//0525+终端地址码
		String commAddr = "";//0525+电能表通讯地址
			
		if(rTmnlRun != null){
			cpNo = rTmnlRun.getCpNo();
		}

		CMeter cMeter = new CMeter();
		Long meterId = null;
		meterId = Long.valueOf(aMeterId);
		List<?> listCollObj = null;
		if("0".equals(mpSn)){
			//根据采集点编号取采集对象 
			listCollObj = marketTerminalConfigManager.getCollObjByCpNo(cpNo);
			mpType = "0";
			//根据终端资产号，取户号和单位
			List<?> listConsTmnlRela = marketTerminalConfigManager.getConsTmnlRelaByTmnlAssetNo(tmnlAssetNo);
			if(listConsTmnlRela != null && listConsTmnlRela.size() > 0) {
				Map<?, ?> mapConsTmnlRela = (Map<?, ?>) listConsTmnlRela.get(0);
				consNo = (String) mapConsTmnlRela.get("CONS_NO");
				orgNo = (String) mapConsTmnlRela.get("ORG_NO");
				areaNo = StringUtil.subStr(orgNo, 0, 7);
				areaCode = areaNo.substring(3, 5);
			}else{
				log.debug("tmnlAssetNo:" + tmnlAssetNo + "在终端用户关系表r_cons_tmnl_rela中没有对应的数据");
			}
		}else{
			//根据电能表标识取采集对象 
			listCollObj = marketTerminalConfigManager.getCollObjByMeterId(meterId);
			if (!"".equals(aMeterId) && null != aMeterId) {
				selfFactor = StringUtil.removeNull(marketTerminalConfigManager.getSelfFactorMeterId(aMeterId));//自身倍率
			}
			mpType = "1";
			cMeter = marketTerminalConfigManager.getAmeterByAssetNo(aMeterId);
			consNo = cMeter.getConsNo();
			orgNo = cMeter.getOrgNo();
			areaNo = StringUtil.subStr(orgNo, 0, 7);
			areaCode = areaNo.substring(3, 5);
			ameterAssetNo = StringUtil.removeNull(cMeter.getAssetNo());
			commAddr = StringUtil.removeNull(cMeter.getCommAddr1());
		}
		if(listCollObj != null && listCollObj.size() > 0){
			Map<?, ?> mapCollObj = (Map<?, ?>) listCollObj.get(0);//TODU可能出现多条数据。
			pt = StringUtil.removeNull(mapCollObj.get("PT_RATIO"));
			ct = StringUtil.removeNull(mapCollObj.get("CT_RATIO"));
			selfFactor = StringUtil.removeNull("".equals(selfFactor)?"1":selfFactor);
			collObjId = StringUtil.removeNull(mapCollObj.get("COLL_OBJ_ID"));
		}
		//插入测量点数据
		isValid = "1";

		if ("0".equals(mpSn)) {
			collObjId = "";
			aMeterId = "";
			dataSrc = "0";
		}

		if (isMainMeter == 1) {
			calcMode = "9";
		}
		//保存终端的测量点数据
		if (edataLst == null || "0".equals(flowFlag)) {
			if(listData == null || listData.size() == 0){
				marketTerminalConfigManager.insertDataMp(orgNo, consNo, tmnlAssetNo, cisTmnlAssetNo, terminalAddr, aMeterId, 
						ameterAssetNo, commAddr, mpType, collObjId, dataSrc, mpSn, pt, ct, selfFactor, calcMode, isValid, areaCode);
			}
			else{
				Map<?, ?> mapDataMp = (Map<?, ?>) listData.get(0);
				String dataId = String.valueOf(mapDataMp.get("ID"));
				if ("1".equals(flowFlag)) {
					mpSn = String.valueOf(mapDataMp.get("MP_SN"));
				}
				marketTerminalConfigManager.updateDateMp(dataId, orgNo, consNo, tmnlAssetNo, cisTmnlAssetNo, terminalAddr, aMeterId,
						ameterAssetNo, commAddr, mpType, collObjId, dataSrc, mpSn, pt, ct, selfFactor, calcMode, isValid);
			}
		} else {
			String dataId = "";
			if (listData != null && listData.size() > 0) {
				Map<?, ?> mapDataMp = (Map<?, ?>) listData.get(0);
				dataId = StringUtil.removeNull(mapDataMp.get("ID"));
				if ("1".equals(flowFlag)) {
					mpSn = String.valueOf(mapDataMp.get("MP_SN"));
				}
			}
			
			Edatamp edataMp = new Edatamp();
			if (!"".equals(dataId)) {
				edataMp.setId(dataId);
			}
			edataMp.setOrgNo(orgNo);
			edataMp.setConsNo(consNo);
			edataMp.setTmnlAssetNo(tmnlAssetNo);
			edataMp.setCisTmnlAssetNo(cisTmnlAssetNo);
			edataMp.setTerminalAddr(terminalAddr);
			edataMp.setMeterId(aMeterId);
			edataMp.setAssetNo(ameterAssetNo);
			edataMp.setCommAddr(commAddr);
			edataMp.setMpType(mpType);
			edataMp.setCollObjId(collObjId);
			edataMp.setDataSrc(dataSrc);
			edataMp.setMpSn(mpSn);
			edataMp.setPt(pt);
			edataMp.setCt(ct);
			edataMp.setSelfFactor(selfFactor);
			edataMp.setCalcMode(calcMode);
			edataMp.setIsValid(isValid);
			edataMp.setAreaCode(areaCode);
			edataMp.setAreaNo(areaNo);
			//压入list
			edataLst.add(edataMp);
			if ("1".equals(endFlag)) {
				log.debug("																			");
				log.debug("****************************批量更新测量点数据主表****************************");
				log.debug("																			");
				//merge
				marketTerminalConfigManager.mergeEDataMp(edataLst);
				
				//分布式缓存注册
				this.regEDataMp(tmnlAssetNo);
			}	
		}
		log.debug("【为终端："+ tmnlAssetNo +"建立表(METER_ID: "+aMeterId+") "+ mpSn +"号测量点】");
	}
	
	/**
	 * 更新失败测量点集合
	 * @param appNo        申请编号
	 * @param terminalId   终端ID
	 * @param flowNode     节点
	 * @param 流程状态
	 * @param failureList  失败测量点
	 */
	private void updateFailureList(String appNo, String terminalId, String flowNode, String flowStatus, String failure) {
		//取得下发终端测量点参数流程
		Map<?,?> map = marketTerminalConfigManager.getIFlowProcess(appNo, terminalId, flowNode);
		if (null != map) {
		} else {
			marketTerminalConfigManager.updateIFlowProcess(appNo, terminalId, flowNode, flowStatus, failure, "");
		}
	}
	
	/**
	 * 根据终端资产号和测量点号更新测量点数据状态
	 * @param tmnlAssetNo
	 * @param mpSn 
	 */
	public void updateDataMpIsValid(String tmnlAssetNo, String mpSn) {
		marketTerminalConfigManager.updateDataMpIsValid(tmnlAssetNo, mpSn);
	}
	
	/**
	 * 为新增或者更换表更新流程明细
	 * @param changeOrAddMpFlag
	 * @param failureList
	 */
	public void updateIFlowProcessForChangeAddMeter(String appNo, String tmnlId, String changeOrAddMpFlag, String mpSnList) {

		if ("0".equals(changeOrAddMpFlag)) {
			this.updateFailureList(appNo, tmnlId, Globals.TRIGGER_EVENT, "0", "");
			this.updateFailureList(appNo, tmnlId, Globals.SEND_TMNL, "1", mpSnList);
			this.updateFailureList(appNo, tmnlId, Globals.RELAY_METER, "1", mpSnList);
			this.updateFailureList(appNo, tmnlId, Globals.SEND_TMNL_MP, "1", mpSnList);
			this.updateFailureList(appNo, tmnlId, Globals.SEND_TASK, "0", "");
		} else if ("1".equals(changeOrAddMpFlag)) {
			int sum = Integer.parseInt(marketTerminalConfigManager.getMpSnByTmnl(tmnlId));//总的测量点数量
			String mpSnAll = this.getMpSnsByTmnlAssetNo(tmnlId);//所有的测量点
			this.updateFailureList(appNo, tmnlId, Globals.TRIGGER_EVENT, "0", "");
			this.updateFailureList(appNo, tmnlId, Globals.SEND_TMNL, "1", mpSnList);
			this.updateFailureList(appNo, tmnlId, Globals.RELAY_METER, "1", mpSnList);
			this.updateFailureList(appNo, tmnlId, Globals.SEND_TMNL_MP, "1", mpSnList);
			this.updateFailureList(appNo, tmnlId, Globals.SEND_TASK, "1", mpSnAll+","+mpSnList);
		} else {
			this.updateFailureList(appNo, tmnlId, Globals.TRIGGER_EVENT, "0", "");
			this.updateFailureList(appNo, tmnlId, Globals.SEND_TMNL, "0", "");
			this.updateFailureList(appNo, tmnlId, Globals.RELAY_METER, "0", "");
			this.updateFailureList(appNo, tmnlId, Globals.SEND_TMNL_MP, "1", mpSnList);
			this.updateFailureList(appNo, tmnlId, Globals.SEND_TASK, "0", "");
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
	
	/**
	 * 分析终端地址是否变更
	 * @param tmnlAssetNo
	 * @return
	 */
	private String analyseTmnlAddr(String tmnlAssetNo) {
		//测量点数据主表
		List<?> eDataLst = marketTerminalConfigManager.getEDataMpByTmnl(tmnlAssetNo);
		List<?> rTmnlLst = marketTerminalConfigManager.getRTmnlRunByAssetNo(tmnlAssetNo);
		String tmnlAddr1 = "";
		String tmnlAddr2 = "";
		String changeTmnlAddr = "0";
		if (rTmnlLst != null && rTmnlLst.size() > 0) {
			Map<?, ?> rTmnlmap = (Map<?,?>) rTmnlLst.get(0);
			tmnlAddr1 = StringUtil.removeNull(rTmnlmap.get("TERMINAL_ADDR"));
		}
		if (eDataLst != null && eDataLst.size() > 0) {
			Map<?, ?> eDataMpmap = (Map<?,?>) eDataLst.get(0);
			tmnlAddr2 = StringUtil.removeNull(eDataMpmap.get("TERMINAL_ADDR"));
		}
		if (!tmnlAddr1.equals(tmnlAddr2)) {
			changeTmnlAddr = "1";
		}
		return changeTmnlAddr;
	}
	
	/**
	 * @param tmnlAssetNo
	 */
	private void regEDataMp(String tmnlAssetNo){
		IStatement statement = CoherenceStatement.getSharedInstance();	
		
		List<?> eDataMpLst = marketTerminalConfigManager.getALLEDataMpByTmnl(tmnlAssetNo);
		
		for (int i = 0; i < eDataMpLst.size(); i++) {
			Map<?,?> eDataMpMap = (Map<?,?>) eDataMpLst.get(i);
			String mpSn = StringUtil.removeNull(eDataMpMap.get("MP_SN"));
			String dataSrc = StringUtil.removeNull(eDataMpMap.get("DATA_SRC"));
			String id = StringUtil.removeNull(eDataMpMap.get("ID"));
			String orgNo = StringUtil.removeNull(eDataMpMap.get("ORG_NO"));
			String areaNo = StringUtil.removeNull(eDataMpMap.get("AREA_NO"));
			String consNo = StringUtil.removeNull(eDataMpMap.get("CONS_NO"));
			String assetNo = StringUtil.removeNull(eDataMpMap.get("ASSET_NO"));
			String mpType = StringUtil.removeNull(eDataMpMap.get("MP_TYPE"));
			String collObjId = StringUtil.removeNull(eDataMpMap.get("COLL_OBJ_ID"));
			String pt = StringUtil.removeNull(eDataMpMap.get("PT"));
			String ct = StringUtil.removeNull(eDataMpMap.get("CT"));
			String selfFactor = StringUtil.removeNull(eDataMpMap.get("SELF_FACTOR"));
			String calcMode = StringUtil.removeNull(eDataMpMap.get("CALC_MODE"));
			String commAddr = StringUtil.removeNull(eDataMpMap.get("COMM_ADDR"));
			String meterId = StringUtil.removeNull(eDataMpMap.get("METER_ID"));
			
			EDataMp eDataMp = new EDataMp();
			//EDataMpId
			EDataMpId eDataMpId = new EDataMpId();
			eDataMpId.setTmnlAssetNo(tmnlAssetNo);
			eDataMpId.setMpSn((short) Long.parseLong(mpSn));
			eDataMpId.setDataSrc(dataSrc);
			eDataMp.setMpId(eDataMpId);
			eDataMp.setId(Long.parseLong(id));
			eDataMp.setOrgNo(orgNo);
			eDataMp.setAreaNo(areaNo);
			eDataMp.setConsNo(consNo);
			eDataMp.setAssetNo(assetNo);
			eDataMp.setMpType(Byte.valueOf(mpType));
			if(collObjId != null && !"".equals(collObjId)){
				eDataMp.setCollObjId(Long.parseLong(collObjId));
			}
			eDataMp.setPt(Integer.parseInt(pt));
			eDataMp.setCt(Integer.parseInt(ct));
			eDataMp.setSelfFactor(Integer.parseInt("".equals(selfFactor)?"1":selfFactor));
			eDataMp.setCalcMode(Byte.valueOf(calcMode));
			eDataMp.setCommAddr(commAddr);
			eDataMp.setDisableDate(null);
			eDataMp.setIsValid(true);
			
			//更新分布式缓存，TMNL_ASSET_NO，DATA_SRC，MP_SN为主键，备注：分布式缓存只关注IS_VALID=1
			try {
				//调用分布式缓存
				statement.executeUpdate(EDataMp.class, eDataMp.getMpId(), eDataMp);
				log.debug("【分布式缓存注册E_DATA_MP结束】tmnlAssetNo: " + tmnlAssetNo+" meterId: "+ meterId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 主表是否存在
	 * @param tmnlAssetNo
	 * @param k
	 * @return
	 */
	private int checkMainMeterIsHaved(String tmnlAssetNo, int k, int w) {
		return marketTerminalConfigManager.checkMainMeterIsHaved(tmnlAssetNo, k, w);
	}
}