package com.nari.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nari.ami.database.map.basicdata.BClearProtocol;
import com.nari.ami.database.map.device.DMeter;
import com.nari.ami.database.map.runcontrol.RTmnlRun;
import com.nari.ami.database.map.terminalparam.TTmnlTask;
import com.nari.coherence.TaskDeal;
import com.nari.dao.jdbc.PublicDbDAO;
import com.nari.dao.jdbc.SynDataForMarketingDao;
import com.nari.dao.jdbc.impl.MarketTerminalConfigDAOJdbc;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.RealDataItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.model.CMeter;
import com.nari.model.Edatamp;
import com.nari.model.TbgTask;
import com.nari.model.TbgTaskDetail;
import com.nari.model.TtmnlParam;
import com.nari.service.MarketTerminalConfigManager;
import com.nari.util.StringUtil;

/**
 * 终端装接接口实现方法
 */
public class MarketTerminalConfigManagerImpl implements MarketTerminalConfigManager {

	MarketTerminalConfigDAOJdbc marketTerminalConfigDAOJdbc;
	PublicDbDAO publicDbDAO;
	TaskDeal taskDeal = new TaskDeal();
	SynDataForMarketingDao synDataForMarketingDao;

	public MarketTerminalConfigDAOJdbc getMarketTerminalConfigDAOJdbc() {
		return marketTerminalConfigDAOJdbc;
	}

	public void setMarketTerminalConfigDAOJdbc(
			MarketTerminalConfigDAOJdbc marketTerminalConfigDAOJdbc) {
		this.marketTerminalConfigDAOJdbc = marketTerminalConfigDAOJdbc;
	}

	public PublicDbDAO getPublicDbDAO() {
		return publicDbDAO;
	}

	public void setPublicDbDAO(PublicDbDAO publicDbDAO) {
		this.publicDbDAO = publicDbDAO;
	}
	
	public SynDataForMarketingDao getSynDataForMarketingDao() {
		return synDataForMarketingDao;
	}

	public void setSynDataForMarketingDao(
			SynDataForMarketingDao synDataForMarketingDao) {
		this.synDataForMarketingDao = synDataForMarketingDao;
	}
	
	public List<?> getRTmnlTaskSr(String appNo) {
		return publicDbDAO.getRTmnlTaskSr(appNo);
	}
	
	/**
	 * 电表变更，从中间库取数据
	 * @param appNo 申请编号
	 */
	public List<?> getRTmnlTaskSrByAppNo(String appNo) {
		return publicDbDAO.getRTmnlTaskSrByAppNo(appNo);
	}

	public Long insertRTmnlDebug(String terminalId) {
		String debugId = "";
		List<?> listRTmnlDebug = publicDbDAO.getRTmnlDebugByTmnlId(terminalId);
		if(listRTmnlDebug != null){
			Map<?, ?> mapRTmnlDebug = (Map<?, ?>) listRTmnlDebug.get(0);
			debugId = StringUtil.removeNull(mapRTmnlDebug.get("DEBUG_ID"));
		}else{
			debugId = publicDbDAO.getSeqREpctCommon();
			//写入中间库和主站数据库
			publicDbDAO.insertRTmnlDebug(debugId,terminalId);
		}
		return Long.parseLong(debugId);
	}

	public List<?> getRTmnlDebugByDebugId(Long debugId) {
		return publicDbDAO.getRTmnlDebugByDebugId(debugId);
	}
	
	public void updateRTmnlDebugTerminalFlag(Long debugId, String terminalFlag) {
		publicDbDAO.updateRTmnlDebugTerminalFlag(debugId, terminalFlag);
	}
	
	public void updateRTmnlDebugMasterCommFlag(Long debugId, String terminalFlag) {
		publicDbDAO.updateRTmnlDebugMasterCommFlag(debugId, terminalFlag);
	}
	
	public void updateRTmnlDebugMeterCommFlag(Long debugId, String terminalFlag) {
		publicDbDAO.updateRTmnlDebugMeterCommFlag(debugId, terminalFlag);
	}

	public String getPublicTmnlOrg(String consId) {
		String orgNo = "";
		List<?> tmnlList = publicDbDAO.getPublicTmnlOrg(consId);
		if(tmnlList != null && tmnlList.size() > 0){
			Map<?, ?> tmnlMap = (Map<?, ?>) tmnlList.get(0);
			orgNo = (String) tmnlMap.get("ORG_NO");
		}
		return orgNo;
	}
	
	public String getPublicTmnlTypeCodeById(String tmnlId) {
		String tmnlTypeCode = "";
		List<?> tmnlList = publicDbDAO.getPublicTmnlTypeCodeById(tmnlId);
		if(tmnlList != null && tmnlList.size() > 0){
			Map<?, ?> tmnlMap = (Map<?, ?>) tmnlList.get(0);
			tmnlTypeCode = (String) tmnlMap.get("TERMINAL_TYPE_CODE");
		}
		return tmnlTypeCode;
	}
	
	public List<?> getPublicTgOrg(String tgId) {
		return publicDbDAO.getPublicTgOrg(tgId);
	}
	
	public int updateCustomerStatus(String consNo, String status) {
		return marketTerminalConfigDAOJdbc.updateCustomerStatus(consNo, status) ;
	}

	public List<?> getAmeterInfo(String consNo) {
		return marketTerminalConfigDAOJdbc.getAmeterInfo(consNo);
	}

	public List<?> getTerminalInfo(String consNo) {
		return marketTerminalConfigDAOJdbc.getTerminalInfo(consNo);
	}

	/**
	 * 从中间库取得终端信息
	 * @param tmnlId
	 * @return
	 */
	public List<?> getYXTerminalById(String tmnlId) {
		return marketTerminalConfigDAOJdbc.getYXTerminalById(tmnlId);
	}
	
	public void updateTerminalMeasurePonitStatus(String zdjh, String status) {
		marketTerminalConfigDAOJdbc.updateTerminalMeasurePonitStatus(zdjh, status);
	}

	public void updateTerminalStatus(String zdjh, String status) {
		marketTerminalConfigDAOJdbc.updateTerminalStatus(zdjh, status);
	}

	public void updateBJStatus(String bjjh, String status) {
		marketTerminalConfigDAOJdbc.updateBJStatus(bjjh, status);
	}

	public void deleteQzByYh(String consNo) {
		marketTerminalConfigDAOJdbc.deleteQzByYh(consNo);
	}

	public void insertITmnlTask(Map<String, Object> map, String flag) {
		marketTerminalConfigDAOJdbc.insert(map, flag);
	}

	public void updateTestStatue(String appNo, String csjdStart) {
		marketTerminalConfigDAOJdbc.updateTestStatue(appNo, csjdStart);
	}
	/**
	 * 换终端
	 * @param tmnlId
	 * @param newTmnlId
	 */
	public void changeTmnl(String oldTmnlId, String newTmnlId) {
		//删除老终端
		marketTerminalConfigDAOJdbc.delTerminalRun(oldTmnlId);
		//终端资产
		marketTerminalConfigDAOJdbc.updateEquip(oldTmnlId);
		//终端用户联系表
		marketTerminalConfigDAOJdbc.delRConsTmnlRelaByConsNoByTmnlId(oldTmnlId);
		//运行电能表
		marketTerminalConfigDAOJdbc.updateCMeter(oldTmnlId, newTmnlId);
		//测量点数据主表
		marketTerminalConfigDAOJdbc.updateEDataMpByTmnlId(oldTmnlId, newTmnlId);
	}
	
	/**
	 * 删除关口联系表
	 * @param tmnlId
	 */
	public void deleteCMeterPbsRelaByTmnlId(String tmnlId) {
		marketTerminalConfigDAOJdbc.deleteCMeterPbsRelaByTmnlId(tmnlId);
	}
	
	/**
	 * 删除联系表
	 * @param tmnlId
	 */
	public void deleteGIoMpByTmnlId(String tmnlId) {
		marketTerminalConfigDAOJdbc.deleteGIoMpByTmnlId(tmnlId);
	}
	
	/**
	 * 关口表
	 * @param tmnlId
	 */
	public void insertCMeterPbsRela(String tmnlId) {
		marketTerminalConfigDAOJdbc.insertCMeterPbsRela(tmnlId);
	}
	
	/**
	 * 删除终端实时工况
	 * @param tmnlId
	 * @return
	 */
	public void removeATmnlRealTime(String tmnlId) {
		marketTerminalConfigDAOJdbc.removeATmnlRealTime(tmnlId);
	}
	
	/**
	 * 更新考核单元状态
	 * @param tgId
	 */
	public void updateGChkUnitStatus(String tgId) {
		marketTerminalConfigDAOJdbc.updateGChkUnitStatus(tgId);
	}
	

	/**
	 * 增加终端实时工况
	 * @param tmnlId
	 */
	public void addATmnlRealTime(String tmnlId) {
		marketTerminalConfigDAOJdbc.addATmnlRealTime(tmnlId);
	}
	
	/**
	 * 根据采集点编号取得采集点编号
	 * @param cpNo
	 * @return
	 */
	public List<?> getRcpBycpNo(String cpNo) {
		return marketTerminalConfigDAOJdbc.getRcpBycpNo(cpNo);
	}
	
	/**
	 * 根据采集点编号取得采集点计量点关系
	 * @param cpNo
	 * @return
	 */
	public List<?> getCMpByCpNo(String cpNo){
		return marketTerminalConfigDAOJdbc.getCMpByCpNo(cpNo);
	}
	
	/**
	 * 根据采集点编号取得变电站线路关系
	 * @param cpNo
	 * @return
	 */
	public List<?> getGSubsLineRelaByCpNo(String cpNo) {
		return marketTerminalConfigDAOJdbc.getGSubsLineRelaByCpNo(cpNo);
	}
	
	/**
	 * 采集器变更
	 * @param appNo
	 */
	public void operateCollector(String appNo, String tmnlId, String cpNo, String tgId, String tmnlTaskType, String meterFlag) {
		List<?> list = marketTerminalConfigDAOJdbc.getFrmCollector(appNo);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<?,?> map = (Map<?,?>) list.get(i);
				String collectorId = StringUtil.removeNull(map.get("OLD_COLLECTOR_ID"));
				//更新采集器资产状态
				marketTerminalConfigDAOJdbc.updateEquip(collectorId);
				//删除运行采集器信息
				marketTerminalConfigDAOJdbc.delRexecOtherDev(collectorId);
			}
		} 
	}
	
	/**
	 * 删除表
	 * @param appNo
	 */
	public void onlyRemoveMeters(String appNo) {
		List<?> list = this.getCancelRTmnlTaskSr(appNo);
		for (int i = 0; i < list.size(); i++) {
			Map<?,?> map = (Map<?,?>) list.get(i);
			String meterId = StringUtil.removeNull(map.get("OLD_METER_ID"));
			this.cancelMeter(meterId);
			System.out.println("旧表ID :"+meterId+"相关数据已经拆除");
		}
	}
	
	/**
	 * 检验删表
	 * @param appNo
	 * @return
	 */
	public String checkOnlyRemoveMeter(String appNo) {
		String flag = "1";//0：换新增   1：删除表
		List<?> list = marketTerminalConfigDAOJdbc.getRtmnlTaskSr(appNo);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<?,?> map = (Map<?,?>) list.get(i);
				String newMeterId = StringUtil.removeNull(map.get("NEW_METER_ID"));
				if (!"".equals(newMeterId)) {
					flag = "0";
					break;
				}	
			}	
		}
		return flag;
	}
	
	
	public List<?> getCancelRTmnlTaskSr(String appNo) {
		return marketTerminalConfigDAOJdbc.getCancelRTmnlTaskSr(appNo);
	}

	public List<?> getTerminalById(String terminalId) {
		return marketTerminalConfigDAOJdbc.getTerminalById(terminalId);
	}

	public List<?> getAmeterById(String ameterId) {
		return marketTerminalConfigDAOJdbc.getAmeterById(ameterId);
	}

	public List<?> getMpSn(String tmnlAssetNo, String ameterId) {
		return marketTerminalConfigDAOJdbc.getMpSn(tmnlAssetNo, ameterId);
	}

	public RTmnlRun getTmnlByAssetNo(String tmnlAssetNo) {
		return marketTerminalConfigDAOJdbc.getTmnlByAssetNo(tmnlAssetNo);
	}
	
	/**
	 * 根据终端ID取得运行电能表数量
	 * @param terminalId
	 * @return
	 */
	public List<?> getCMeterByTmnlId(String terminalId) {
		return marketTerminalConfigDAOJdbc.getCMeterByTmnlId(terminalId);
	}
	
	/**
	 * 根据终端局号取得运行终端
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getRTmnlRunByAssetNo(String tmnlAssetNo) {
		return marketTerminalConfigDAOJdbc.getRTmnlRunByAssetNo(tmnlAssetNo);
	}
	
	/**
	 * 根据终端局号取得运行终端
	 * @param tmnlAssetNo
	 * @return
	 */
	public String getYXRTmnlRunByAssetNo(String tmnlAssetNo) {
		String tmnlStatus = "";
		List<?> list = marketTerminalConfigDAOJdbc.getYXRTmnlRunByAssetNo(tmnlAssetNo);
		if (list != null && list.size() > 0) {
			Map<?,?> tmnlMap = (Map<?,?>) list.get(0);
			tmnlStatus = StringUtil.removeNull(tmnlMap.get("STATUS_CODE"));
		}
		return tmnlStatus;
	}
	
	public CMeter getAmeterByAssetNo(String ameterId) {
		return marketTerminalConfigDAOJdbc.getAmeterByAssetNo(ameterId);
	}

	public List<?> getCollObjByCpNo(String cpNo) {
		return marketTerminalConfigDAOJdbc.getCollObjByCpNo(cpNo);
	}

	public List<?> getCollObjByMeterId(Long meterId) {
		return marketTerminalConfigDAOJdbc.getCollObjByMeterId(meterId);
	}

	public List<?> getDataMpByParam(String tmnlAssetNo, String mpSn, String meterId) {
		return marketTerminalConfigDAOJdbc.getDataMpByParam(tmnlAssetNo, mpSn, meterId);
	}
	
	public List<?> getDataMpByTmnlNoMpSn(String tmnlAssetNo, String mpSn) {
		return marketTerminalConfigDAOJdbc.getDataMpByTmnlNoMpSn(tmnlAssetNo, mpSn);
	}
	
	public List<?> getDataMpByMpSnComm1(String mpSn, String comm1) {
		return marketTerminalConfigDAOJdbc.getDataMpByMpSnComm1(mpSn, comm1);
	}

	public void insertDataMp(String orgNo, String consNO, String tmnlAssetNo, String cisTmnlAssetNo, String terminalAddr, String aMeterId,
			String ameterAssetNo, String commAddr, String mpType, String collObj, String dataSrc, String mpSn,
			String pt, String ct, String selfFactor, String calcMode,
			String isValid, String areaCode) {
		marketTerminalConfigDAOJdbc.insertDataMp(orgNo, consNO, tmnlAssetNo, cisTmnlAssetNo, terminalAddr, aMeterId, ameterAssetNo, commAddr, mpType, collObj, dataSrc, mpSn, pt, ct, selfFactor, calcMode, isValid, areaCode);
	}
	
	public void updateDateMp(String dataId, String orgNo, String consNo, String tmnlAssetNo, String cisTmnlAssetNo, String terminalAddr, String aMeterId,
			String ameterAssetNo, String commAddr, String mpType, String collObj, String dataSrc, String mpSn,
			String pt, String ct, String selfFactor, String calcMode,
			String isValid) {
		marketTerminalConfigDAOJdbc.updateDateMp(dataId, orgNo, consNo, tmnlAssetNo, cisTmnlAssetNo, terminalAddr, aMeterId, ameterAssetNo, commAddr, mpType, collObj,dataSrc, mpSn, pt, ct, selfFactor, calcMode, isValid);
	}

	public List<?> getTmnlMpParamByIdAndProtNo(String dataId, String protItemNo) {
		return marketTerminalConfigDAOJdbc.getTmnlMpParamByIdAndProtNo(dataId, protItemNo);
	}

	public void insertTmnlMpParam(String dataId, String protItemNo,
			String currentValue, String blockSn, String innerBlockSn, String statusCode) {
		marketTerminalConfigDAOJdbc.insertTmnlMpParam(dataId, protItemNo, currentValue, blockSn, innerBlockSn, statusCode);
	}

	public void updateTmnlMpParam(String dataId, String protItemNo,
			String currentValue, String historyValue, String blockSn, String innerBlockSn, String statusCode, Date lastSendTime) {
		marketTerminalConfigDAOJdbc.updateTmnlMpParam(dataId, protItemNo, currentValue, historyValue, blockSn, innerBlockSn, statusCode, lastSendTime);
	}

	public List<BClearProtocol> findAllByTemplateId(long templateId) {
		return marketTerminalConfigDAOJdbc.findAllByTemplateId(templateId);
		}

	public TTmnlTask findTaskByNoId(String tmnlAssetNo, long templateId) {
		return marketTerminalConfigDAOJdbc.findTaskByNoId(tmnlAssetNo, templateId);
	}

	public DMeter getDmeterByAssetNo(String ameterId) {
		return marketTerminalConfigDAOJdbc.getDmeterByAssetNo(ameterId);
	}
	
	/**
	 * 根据表ID取得用户容量和计量方式
	 * @param meterId
	 * @return
	 */
	public List<?> getCapMeasMode(String meterId) {
		return marketTerminalConfigDAOJdbc.getCapMeasMode(meterId);
	}
	
	/**
	 * 取得额定电压电流
	 * @param contract_cap
	 * @param wiring_mode
	 * @param meas_mode
	 * @param ct
	 * @param pt
	 * @return
	 */
	public String getVoltCurr(final String contract_cap, final String wiring_mode,final String meas_mode, final String ct, final String pt) {
		return marketTerminalConfigDAOJdbc.getVoltCurr(contract_cap, wiring_mode, meas_mode, ct, pt);
	}

	public String getSelfFactorMeterId(String meterId) {
		return marketTerminalConfigDAOJdbc.getSelfFactorMeterId(meterId);
	}
	
	public List<?> getDmeterByMeterId(String meterId) {
		return marketTerminalConfigDAOJdbc.getDmeterByMeterId(meterId);
	}
	
	/**
	 * 根据表ID取得表上级采集器的地址
	 * @param ameterId
	 * @return
	 */
	public List<?> getfrmAddr(String ameterId) {
		return marketTerminalConfigDAOJdbc.getfrmAddr(ameterId);
	}
	
	/**
	 * 根据终端资产编号取是否附属电能表
	 * @param tmnlAssetNo 表计资产编号
	 * @return AttachMeterFlag
	 */
	public String getAttachMeterFlag(String tmnlAssetNo) {
		String attachMeterFlag = "";
		RTmnlRun rTmnlRun = new RTmnlRun();
		rTmnlRun = this.getTmnlByAssetNo(tmnlAssetNo);
		if(rTmnlRun != null){
			attachMeterFlag = rTmnlRun.getAttachMeterFlag() == null ? "" : rTmnlRun.getAttachMeterFlag();
		}
		return attachMeterFlag;
	}

	/**
	 * 根据规约项数据编码取块内序号
	 * @param protItemNo 规约项数据编码
	 * @return InnerBlockSn 块内序号
	 */
	public String getInnerBlockSnByProtItemNo(String protItemNo) {
		String innerBlockSn = "";
		List<?> tmnlProtocolItem = marketTerminalConfigDAOJdbc.getProtocolItemByProtItemNo(protItemNo);
		if(tmnlProtocolItem != null && tmnlProtocolItem.size() > 0){
			Map<?, ?> mapTmnlProtocolItem = (Map<?, ?>) tmnlProtocolItem.get(0);
			innerBlockSn = String.valueOf(mapTmnlProtocolItem.get("PROT_ITEM_SN"));
		}
		return innerBlockSn;
	}

	public List<?> getTmnlParamByTmnlAssetNoAndBlockSn(String tmnlAssetNo,
			String blockSn) {
		return marketTerminalConfigDAOJdbc.getTmnlParamByTmnlAssetNoAndBlockSn(tmnlAssetNo, blockSn);
	}
	
	/**
	 * 根据终端资产编号取得F10终端参数
	 * @param tmnlAssetNo 终端资产号
	 * @return blockSn 块序号
	 */
	public List<?> getF10TmnlParamByTmnlAssetNo(String tmnlAssetNo) {
		return marketTerminalConfigDAOJdbc.getF10TmnlParamByTmnlAssetNo(tmnlAssetNo);
	}

	public void updateTmnlParam(String tmnlAssetNo, String blockSn,
			String isValid) {
		marketTerminalConfigDAOJdbc.updateTmnlParam(tmnlAssetNo, blockSn, isValid);
	}

	public List<?> getTmnlParamByKey(String tmnlAssetNo, String protItemNo,
			String blockSn, String innerBlockSn) {
		return marketTerminalConfigDAOJdbc.getTmnlParamByKey(tmnlAssetNo, protItemNo, blockSn, innerBlockSn);
	}

	public void insertTmnlParam(String tmnlAssetNo, String protItemNo,
			String currentValue, String blockSn, String innerBlockSn, String statusCode) {
		marketTerminalConfigDAOJdbc.insertTmnlParam(tmnlAssetNo, protItemNo, currentValue, blockSn, innerBlockSn, statusCode);
	}

	public void updateTmnlParam(String tmnlAssetNo, String protItemNo,
			String currentValue, String historyValue, String blockSn,
			String innerBlockSn, String statusCode, String isValid, Date lastSendTime) {
		marketTerminalConfigDAOJdbc.updateTmnlParam(tmnlAssetNo, protItemNo, currentValue, historyValue, blockSn, innerBlockSn, statusCode, isValid, lastSendTime);
	}
	
	/**
	 * 取得终端事件分类 
	 * @param protocol
	 */
	public List<?> getLevel(String protocol) {
		return marketTerminalConfigDAOJdbc.getLevel(protocol);
	}

	public void insertTmnlDebug(String appNo, String terminalId,
			String ameterId, String debugProgress) {
		marketTerminalConfigDAOJdbc.insertTmnlDebug(appNo, terminalId, ameterId, debugProgress);
	}
	
	@Override
	public void insertTmnlDebug(String appNo, String terminalId,
			String ameterId, String debugProgress, String failCause) {
		marketTerminalConfigDAOJdbc.insertTmnlDebug(appNo, terminalId, ameterId, debugProgress, failCause);
	}

	public void writeLog(String logId, String sysNo, String interfaceType,
			String interfaceName, String interfaceContent, String respId) {
		marketTerminalConfigDAOJdbc.writeLog(logId, sysNo, interfaceType, interfaceName, interfaceContent, respId);
	}

	public String getStatusByTaskDeal(List<String> tmnlAssetNos,
			short setParameters, List<ParamItem> params) {
		String Status = "";
		List<Response> respList = null;
		if(!TaskDeal.isCacheRunning()){
			System.out.println("--------------应用服务器与缓存服务器通信中断--------------");
			Status = "4";
		}else if(!TaskDeal.isFrontAlive()){
			System.out.println("--------------前置集群服务通信中断-----------------------");
			Status = "4";
		}else{
			TaskDeal taskDeal = new TaskDeal();
			taskDeal.qstTermParamTaskResult(tmnlAssetNos,setParameters, params);
			respList = taskDeal.getAllResponse(this.getListenTime());
			if(respList != null && respList.size() > 0){
				Status = String.valueOf(respList.get(0).getTaskStatus());
				if ("1".equals(Status)) {
					Status = "1";
				} else {
					if (respList.get(0).getErrorCodeId() < 0) {
						Status = respList.get(0).getErrorCodeStr();
					} else {
						Status = "3";
					}
				}
			}
		}
		if("0".equals(Status)){
			System.out.println("--------------在规定的时间内缓存没有返回------------------");
		}else if("1".equals(Status)){
			System.out.println("--------------终端不在线---------------------------------");
		}else if("3".equals(Status)){
			System.out.println("--------------通讯成功-----------------------------------");
		}else if("4".equals(Status)){
			System.out.println("--------------通讯失败-----------------------------------");
		}
//		Status = "3";//程序调试的时候置状态为成功！TODU
		return Status;
	}

	
	public String getStatusByTaskDealCallData(List<String> tmnlAssetNos, List<RealDataItem> params) {
		String Status = "";
		List<Response> respList = null;
		if(!TaskDeal.isCacheRunning()){
			System.out.println("--------------应用服务器与缓存服务器通信中断--------------");
			Status = "4";
		}else if(!TaskDeal.isFrontAlive()){
			System.out.println("--------------前置集群服务通信中断-----------------------");
			Status = "4";
		}else{
			TaskDeal taskDeal = new TaskDeal();
			taskDeal.callRealData(tmnlAssetNos, params);
			respList = taskDeal.getAllResponse(this.getListenTime());
			if(respList != null && respList.size() > 0){
				Status = String.valueOf(respList.get(0).getTaskStatus());
			}
		}
		if("0".equals(Status)){
			System.out.println("--------------在规定的时间内缓存没有返回------------------");
		}else if("1".equals(Status)){
			System.out.println("--------------终端不在线---------------------------------");
		}else if("3".equals(Status)){
			System.out.println("--------------通讯成功-----------------------------------");
		}else if("4".equals(Status)){
			System.out.println("--------------通讯失败-----------------------------------");
		}
//		Status = "3";//程序调试的时候置状态为成功！TODU
		return Status;
	}
	
	public int getListenTime(){
		int listenTime = 30;
		List<?> listParam = this.getWSInfo();
		for (int i = 0; i <listParam.size(); i++) {
			Map<?, ?> mapTmnl = (Map<?, ?>) listParam.get(i);
			String itemNo = StringUtil.removeNull(mapTmnl.get("PARAM_ITEM_NO"));
			String itemVal = StringUtil.removeNull(mapTmnl.get("PARAM_ITEM_VAL"));
			if ("LISTEN_TIME".equals(itemNo)) {//接口与前置机通讯监听超时时间
				listenTime = Integer.parseInt(itemVal);
			}
		}
		System.out.println("请等待" + listenTime + "秒........");
		return listenTime;
	}
	
	//后台电表注册等待时间
	public int getFrmTmnlTime(){
		int listenTime = 30;
		List<?> listParam = this.getWSInfo();
		for (int i = 0; i <listParam.size(); i++) {
			Map<?, ?> mapTmnl = (Map<?, ?>) listParam.get(i);
			String itemNo = StringUtil.removeNull(mapTmnl.get("PARAM_ITEM_NO"));
			String itemVal = StringUtil.removeNull(mapTmnl.get("PARAM_ITEM_VAL"));
			if ("FRM_TMNL_TIME".equals(itemNo)) {
				listenTime = Integer.parseInt(itemVal);
			}
		}
		System.out.println("请等待" + listenTime + "秒........");
		return listenTime;
	}
	
	/**
	 * 根据终端ID取得采集点对象数量
	 * @param int
	 */
	public int getRcollObjNumByTmnlId(String terminalId) {
		return marketTerminalConfigDAOJdbc.getRcollObjNumByTmnlId(terminalId);
	}
	
	/**
	 * 根据终端ID取得采集点对象
	 * @param terminalId
	 */
	public List<?> getRcollObjByTmnlId(String terminalId) {
		return marketTerminalConfigDAOJdbc.getRcollObjByTmnlId(terminalId);
	}
	
	/**
	 * 更新终端状态
	 * @param terminalId
	 */
	public void updateTmnlStatus(String terminalId) {
		marketTerminalConfigDAOJdbc.updateTmnlStatus(terminalId);
	}
	
	/**
	 * 取得测量点数据主表
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getALLEDataMpByTmnl(String tmnlAssetNo) {
		return marketTerminalConfigDAOJdbc.getALLEDataMpByTmnl(tmnlAssetNo);
	}
	
	/**
	 * 检查主表存在
	 * @param tmnlAssetNo
	 * @param k
	 * @return
	 */
	public int checkMainMeterIsHaved(String tmnlAssetNo, int k, int w) {
		return marketTerminalConfigDAOJdbc.checkMainMeterIsHaved(tmnlAssetNo, k, w);
	}
	
	/**
	 * 更新测量点数据主表
	 * @param edataLst
	 */
	public void mergeEDataMp( List<Edatamp> edataLst) {
		marketTerminalConfigDAOJdbc.mergeEDataMp(edataLst);
	}
	
	/**
	 * 批量更新终端参数表
	 * @param tTmnlParamLst
	 */
	public void mergeTTmnlParam(List<TtmnlParam> tTmnlParamLst) {
		marketTerminalConfigDAOJdbc.mergeTTmnlParam(tTmnlParamLst);
	}
	
	/**
	 * 更新终端总加组参数表
	 * @param tmnlId
	 */
	public void updateTtmnlTotalInfo(String terminalId) {
		marketTerminalConfigDAOJdbc.updateTtmnlTotalInfo(terminalId);
	}
	
	/**
	 * 更新终端总加组主表
	 * @param terminalId
	 */
	public void updateEDataTotal(String terminalId){
		marketTerminalConfigDAOJdbc.updateEDataTotal(terminalId);
	}
	
	//任务下发等待时间
	public int getFrmTaskTime(){
		int listenTime = 30;
		List<?> listParam = this.getWSInfo();
		for (int i = 0; i <listParam.size(); i++) {
			Map<?, ?> mapTmnl = (Map<?, ?>) listParam.get(i);
			String itemNo = StringUtil.removeNull(mapTmnl.get("PARAM_ITEM_NO"));
			String itemVal = StringUtil.removeNull(mapTmnl.get("PARAM_ITEM_VAL"));
			if ("FRM_TASK_TIME".equals(itemNo)) {
				listenTime = Integer.parseInt(itemVal);
			}
		}
		System.out.println("请等待" + listenTime + "秒........");
		return listenTime;
	}
	
	public int getSleepTime() {
		int sleepTime = 0;
		List<?> listParam = this.getWSInfo();
		for (int i = 0; i <listParam.size(); i++) {
			Map<?, ?> mapTmnl = (Map<?, ?>) listParam.get(i);
			String itemNo = StringUtil.removeNull(mapTmnl.get("PARAM_ITEM_NO"));
			String itemVal = StringUtil.removeNull(mapTmnl.get("PARAM_ITEM_VAL"));
			if ("WAIT_TIME".equals(itemNo)) {//接口程序 等待时间
				sleepTime = Integer.parseInt(itemVal);
			}
		}
		System.out.println("请等待" + sleepTime + "秒........");
		return sleepTime;
	}

	public String getMpsnByTmnlAndMeter(String tmnlAssetNo, String ameterId) {
		String mpSn = "";
		List<?> dDateMpList = marketTerminalConfigDAOJdbc.getMpSn(tmnlAssetNo, ameterId);
		if(dDateMpList != null && dDateMpList.size() > 0){
			Map<?, ?> dDateMpMap = (Map<?, ?>) dDateMpList.get(0);
			mpSn = String.valueOf(dDateMpMap.get("MP_SN"));
		}
		return mpSn;
	}
	
	/**
	 * 根据终端资产号和测量点号更新测量点数据状态
	 * @param tmnlAssetNo
	 * @param mpSn 
	 */
	public void updateDataMpIsValid(String tmnlAssetNo, String mpSn) {
		marketTerminalConfigDAOJdbc.updateDataMpIsValid(tmnlAssetNo, mpSn);
	}

	public void updateMeterRegSn(String ameterId, String mpSn) {
		marketTerminalConfigDAOJdbc.updateMeterRegSn(ameterId, mpSn);
	}
	
	/**
	 * 更新终端的接线方式
	 * @param tmnlAssetNo
	 */
	public void updateWiringMode(String tmnlAssetNo) {
		marketTerminalConfigDAOJdbc.updateWiringMode(tmnlAssetNo);
	}
	
	/**
	 * 检查是主表
	 * @param ameterId
	 * @return
	 */
	public long checkMainMater(String ameterId) {
		//检查是否是主表
		return marketTerminalConfigDAOJdbc.checkMainMater(ameterId);
	}
	
	/**
	 * 移除终端以及相关的信息
	 * @param tmnlId 终端编号
	 * @param tmnlAssetNo 终端资产编号
	 * @param appNo 申请编号
	 * @param tgId  台区编号
	 * @param cpNo 采集点编号
	 * @param mpNo 计量点编号
	 */
	public void cancelTmnl(String tmnlId, String tmnlAssetNo, String appNo, String tgId, String cpNo) {
		
		//根据终端编号 删除 用户终端关系表
		marketTerminalConfigDAOJdbc.deleteRconsTmnlRela(tmnlId);
		//根据终端编号 删除 运行终端 
		marketTerminalConfigDAOJdbc.delTerminalRun(tmnlId);
		//删除分布式缓存中的运行终端信息

		
		//更新终端资产（负控设备信息）
		marketTerminalConfigDAOJdbc.updateEquip(tmnlId);
		
		List<?> list = marketTerminalConfigDAOJdbc.getCMeterIdBytmnlId(tmnlId);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Map<?, ?> dDateMpMap = (Map<?, ?>) list.get(0);
				String meterId = StringUtil.removeNull(dDateMpMap.get("METER_ID"));
				//删除运行电能表信息
				marketTerminalConfigDAOJdbc.deleteCMeterMpRela(meterId);
				marketTerminalConfigDAOJdbc.deleteCollObj(meterId);
				marketTerminalConfigDAOJdbc.deleteCMeter(meterId);
				
				//更新 电能表信息  当前状态
				marketTerminalConfigDAOJdbc.updateDMeterStatus(meterId);
			}
		}
		
		//根据终端资产编号 更新 测量点数据主表 的状态
		marketTerminalConfigDAOJdbc.updateDataMp(tmnlAssetNo);
		
		//删除 终端参数
		marketTerminalConfigDAOJdbc.deleteTmnlParam(tmnlId);
		
		//更新终端任务
		marketTerminalConfigDAOJdbc.updateTmnlTask(tmnlId);
		
		//更新计量点
		marketTerminalConfigDAOJdbc.updateCMp(cpNo);
		
		//删除采集计量关系
		marketTerminalConfigDAOJdbc.deleteRCpRela(cpNo);
		//删除采集点开关量参数
		marketTerminalConfigDAOJdbc.deleteRCpSwitchPara(cpNo);
		//删除采集点控制参数
		marketTerminalConfigDAOJdbc.deleteRCpCtrlPara(cpNo);
		//删除采集点通信参数
		marketTerminalConfigDAOJdbc.deleteRCpCommPara(cpNo);
		//删除用户关系
		marketTerminalConfigDAOJdbc.deleteRCpConsRela(cpNo);
		//删除采集点勘察位置图
//		marketTerminalConfigDAOJdbc.deleteRCpSurveyDiagram(cpNo);
		//删除采集点勘察信息
		marketTerminalConfigDAOJdbc.deleteRCpSurvey(cpNo);
		//删除采集点现场消缺消息
		marketTerminalConfigDAOJdbc.deleteRSiteFaultRmv(cpNo);
		//删除采集点缺陷信息
		marketTerminalConfigDAOJdbc.deleteRCpFault(cpNo);
		//采集点对象
		marketTerminalConfigDAOJdbc.deleteRCollObj(cpNo);
		//删除采集点
		marketTerminalConfigDAOJdbc.deleteRCp(cpNo);
		
	}
	
	/**
	 * 销户
	 * @param consNo 用电客户编号
	 */
	public void cancelUser(String consNo) {
		//根据用户编号 更新 用电用户状态
		marketTerminalConfigDAOJdbc.updateCons(consNo);
	}
	
	/**
	 * 销户和销终端
	 * @param tmnlId 终端编号
	 * @param tmnlAssetNo 终端资产号
	 * @param tmnlType 终端变更标识
	 * @param consType 用户变更标识
	 * @param consNo   用电客户编号
	 * @param appNo 申请编号
	 */
	public void cancelUserTmnl(String tmnlId, String tmnlAssetNo, String tmnlType, String consType, 
			String consNo, String appNo, String tgId, String cpNo) {
		//用户销户
		if ("02".equals(consType)) {
			//销户
			cancelUser(consNo);
			//删除群组明细
			marketTerminalConfigDAOJdbc.deleteGroupDetail(consNo);
			//删除用户终端关系表
			marketTerminalConfigDAOJdbc.deleteRconsTmnlRela(consNo, tmnlAssetNo);
			//销终端
			cancelTmnl(tmnlId, tmnlAssetNo, appNo, tgId, cpNo);
			
		} else {
			//当用户变更标识不是02  只是销终端
			cancelTmnl(tmnlId, tmnlAssetNo, appNo, tgId,cpNo);
		}
	}
	
	/**
	 * 拆电能表
	 * @param meterId  电能表ID
	 */
	public void cancelMeter(String meterId) {
		if (!"".equals(meterId)) {
			//删除采集计量点关系
			marketTerminalConfigDAOJdbc.deleteRCpMpRelaByMeterId(meterId);
			//更新计量点
			marketTerminalConfigDAOJdbc.updateCMpByMeter(meterId);
			//删除表计计量点关系
			marketTerminalConfigDAOJdbc.deleteCMeterMpRela(meterId);
			//删除采集点对象关系
			marketTerminalConfigDAOJdbc.deleteCollObj(meterId);
			//删除运行电能表
			marketTerminalConfigDAOJdbc.deleteCMeter(meterId);
			//更新电能表资产信息
			marketTerminalConfigDAOJdbc.updateDMeterStatus(meterId);
			//更新测量点数据主表
			marketTerminalConfigDAOJdbc.updateDataMpBymeterId(meterId);
			//删除抄表明细
			marketTerminalConfigDAOJdbc.deleteImeterRead(meterId);
		}
	}
	
	/**
	 * 删除关口考核联系表
	 * @param oldMeterId
	 */
	public void deleteCMeterPbsRela(String oldMeterId) {
		marketTerminalConfigDAOJdbc.deleteCMeterPbsRela(oldMeterId);
	}
	
	/**
	 * 负控专变增加考核单元
	 * @param newMeterId
	 */
	public void insertGIoMpPerMeterId(String tmnlId, String newMeterId) {
		marketTerminalConfigDAOJdbc.insertGIoMpPerMeterId(tmnlId, newMeterId);
	}
	
	/**
	 * 插入关口联系表
	 * @param newMeterId
	 */
	public void insertCMeterPbsRelaPerMeter(String newMeterId) {
		marketTerminalConfigDAOJdbc.insertCMeterPbsRelaPerMeter(newMeterId);
	}
	
	/**
	 * 删除旧表流入流出数据
	 * @param oldMeterId
	 */
	public void delGIoMp(String oldMeterId) {
		marketTerminalConfigDAOJdbc.delGIoMp(oldMeterId);
	}
	
	/**
	 * 更新流入流出数据
	 * @param oldMeterId
	 * @param newMeterId
	 */
	public void updateGIoMp(String oldMeterId) {
		marketTerminalConfigDAOJdbc.updateGIoMp(oldMeterId);
	}
	
	/**
	 * 插入流入流出线损
	 * @param oldMeterId
	 * @param newMeterId
	 */
	public void insertGIoMpByMeterId(String tmnlAssetNo, String oldMeterId, String newMeterId) {
		marketTerminalConfigDAOJdbc.insertGIoMpByMeterId(tmnlAssetNo, oldMeterId, newMeterId);
	}
	
	/**
	 * 根据表ID取得流入流出线损
	 * @return
	 */
	public List<?> getGIoMpByMeterId(String meterId) {
		return marketTerminalConfigDAOJdbc.getGIoMpByMeterId(meterId);
	}
	
	/**
	 * 更换终端和表计
	 * @param tmnlId 终端编号
	 * @param tmnlAssetNo 终端资产号
	 * @param tmnlType 终端变更标识
	 * @param consType 用户变更标识
	 * @param consNo   用电客户编号
	 * @param appNo 申请编号
	 */
	public void ChangeTmnlMeter(String tmnlId, String tmnlAssetNo, String tmnlType, String consType, 
			String consNo, String appNo, String tgId, String cpNo, String mpNo) {
		
	} 
	
	/**
	 * 校验是否有重复的终端和电能表的地址
	 * @param tmnlId 终端编号
	 * @param cpNo 采集点编号
	 */
	public String checkTmnlMeter(String tmnlId, String cpNo, String appNo, String tmnlTaskType, String flag, String newTmnlId) {
		
		return publicDbDAO.checkTmnlMeter(tmnlId, cpNo, appNo, tmnlTaskType, flag, newTmnlId);		
	} 

	/**
	 * 根据终端ID取得电能表总个数
	 */
	public String getMpSnByTmnl(String tmnlAssetNo) {
		String mpSns = "";
		List<?> tmnlList = marketTerminalConfigDAOJdbc.getMpSnByTmnl(tmnlAssetNo);
		if(tmnlList != null){
			mpSns = String.valueOf(tmnlList.size());
		}
		return mpSns;
	}

	public int synData(String consNo,String consId,String cpNo, String tmnlId, String tgId, String appNo) {
		int flag = 0;
		boolean i = synDataForMarketingDao.synDataByService(consNo, consId,cpNo, tmnlId, tgId, appNo);
		if(i){
			flag = 1;
		}
		return flag;
	}
	
	public int synGateData(String subId, String tmnlId, String cpNo) {
		int flag = 0;
		boolean i = synDataForMarketingDao.synGateDataByService(subId, tmnlId, cpNo);
		if(i){
			flag = 1;
		}
		return flag;
	}
	
	/**
	 * 集抄时候同步中间库数据
	 * @param tmnlId
	 * @param cpNo
	 * @param tgId
	 */
	public int synOtherData(String tmnlId, String cpNo, String tgId, String appNo) {
		int flag = 0;
		boolean i = synDataForMarketingDao.synOtherDataByService(tmnlId, cpNo, tgId, appNo);
		if(i){
			flag = 1;
		}
		return flag;
	}
	
	/**
	 * 插入后台任务主表
	 * @param tbgTask
	 */
	public void addTbgTask(TbgTask tbgTask) {
		marketTerminalConfigDAOJdbc.addTbgTask(tbgTask);
	}
	
	/**
	 * 插入后台任务主表明细表
	 * @param tbgTaskDetail
	 */
	public void addTbgTaskDetail(TbgTaskDetail tbgTaskDetail) {
		marketTerminalConfigDAOJdbc.addTbgTaskDetail(tbgTaskDetail);
	}

	public List<?> getConsTmnlRelaByTmnlAssetNo(String tmnlAssetNo) {
		return marketTerminalConfigDAOJdbc.getConsTmnlRelaByTmnlAssetNo(tmnlAssetNo);
	}

	public void updateTmnlParamStatue(String tmnlAssetNo, String f9, String f10) {
		marketTerminalConfigDAOJdbc.updateTmnlParamStatue(tmnlAssetNo, f9, f10);
	}

	public List<?> getMpSnsByTmnl(String tmnlAssetNo) {
		return marketTerminalConfigDAOJdbc.getMpSnByTmnl(tmnlAssetNo);
	}
	
	public List<?> getFrmMpSnsByTmnl(String tmnlAssetNo) {
		return marketTerminalConfigDAOJdbc.getFrmMpSnByTmnl(tmnlAssetNo);
	}
	
	/**
	 * 根据终端ID取得明细
	 * @param terminld
	 */
	public List<?> getTmnlTaskByTmnlId(String terminalId) {
		return marketTerminalConfigDAOJdbc.getTmnlTaskByTmnlId(terminalId);
	}
	
	/**
	 * 根据采集点对象ID
	 * @param tgID
	 * @return
	 */
	public List<?> getChkUnit(String tgID) {
		return marketTerminalConfigDAOJdbc.getChkUnit(tgID);
	}
	
	/**
	 * 建立考核单元
	 * @param orgNo
	 * @param tgName
	 */
	public String insertChkUnit(String orgNo, String tgName, String tgId) {
		return marketTerminalConfigDAOJdbc.insertChkUnit(orgNo, tgName, tgId);
	}
	
	/**
	 * 根据终端ID取得用户
	 * @return
	 */
	public List<?> getConsCmpByTmnlId(String terminalId) {
		return marketTerminalConfigDAOJdbc.getConsCmpByTmnlId(terminalId);
	}
	
	/**
	 * 更新虚拟用户分类
	 * @param terminalId
	 */
	public void updateCyberConsSort(String terminalId){
		marketTerminalConfigDAOJdbc.updateCyberConsSort(terminalId);
	}
	
	/**
	 * 更新虚拟用户分类
	 * @param terminalId
	 */
	public void updateCyberConsSortForGate(String terminalId){
		marketTerminalConfigDAOJdbc.updateCyberConsSortForGate(terminalId);
	}
	
	/**
	 * 负控流入流出
	 * @param terminalId
	 */
	public void insertGIoMpForRtu(String terminalId) {
		String dataId = "";
		List<?> cmrLst = marketTerminalConfigDAOJdbc.getCMeterIdBytmnlId(terminalId);
		for (int i = 0; i < cmrLst.size(); i++) {
			Map<?,?> cmrMap = (Map<?,?>) cmrLst.get(i);
			String meterId = StringUtil.removeNull(cmrMap.get("METER_ID"));
			List<?> edataMpLst =  marketTerminalConfigDAOJdbc.getEDataMpByMeterId(terminalId, meterId);
			if (edataMpLst != null && edataMpLst.size() > 0) {
				Map<?,?> edataMpMap = (Map<?,?>) edataMpLst.get(0);
				dataId = StringUtil.removeNull(edataMpMap.get("ID"));
			}
			
			//计量点
			List<?> cmpLst = marketTerminalConfigDAOJdbc.getCMpByMeterId(meterId);
			for (int j = 0; j < cmpLst.size(); j++) {
				Map<?,?> cmpMap = (Map<?,?>) cmpLst.get(j);
				String lineId = StringUtil.removeNull(cmpMap.get("LINE_ID"));
				String orgNo = StringUtil.removeNull(cmpMap.get("ORG_NO"));
				if (!"".equals(lineId)) {
					//检验存在考核单元  线路
					List<?> lineLst = marketTerminalConfigDAOJdbc.getChkUnit(lineId);
					if (lineLst != null && lineLst.size() > 0) {
						String chkUnitId = StringUtil.removeNull(((Map<?,?>)lineLst.get(0)).get("CHKUNIT_ID"));
						marketTerminalConfigDAOJdbc.insertGIoMpPerMeter(chkUnitId, lineId, meterId, dataId);
					}
				}
				if (!"".equals(orgNo)) {
					//检验存在考核单元   部门
					List<?> orgNoLst = marketTerminalConfigDAOJdbc.getChkUnit(orgNo);
					if (orgNoLst != null && orgNoLst.size() > 0) {
						String chkUnitId = StringUtil.removeNull(((Map<?,?>)orgNoLst.get(0)).get("CHKUNIT_ID"));
						marketTerminalConfigDAOJdbc.insertGIoMpPerMeter(chkUnitId, orgNo, meterId, dataId);
					}
				}	
			}
		}
	}
	
	
	/**
	 * 关口流入流出
	 * @param terminalId
	 */
	public void insertGIoMpForGate(String terminalId) {
		
		
		
		
		
		
	}

	/**
	 * 取调试信息
	 * @param tmnlId 终端ID
	 */
	public List<?> getITmnlTaskByTerminalId(String tmnlId) {
		return  marketTerminalConfigDAOJdbc.getITmnlTaskByTerminalId(tmnlId);
	}
	
	/**
	 * 根据表ID取得计量点
	 * @param meterId
	 * @return
	 */
	public List<?> getCMpByMeterId(String meterId){
		return  marketTerminalConfigDAOJdbc.getCMpByMeterId(meterId);
	}
	
	/**
	 * 更新用户分类
	 * @param consId
	 * @param consSort
	 */
	public void updateCconsConsSort(String consId, String consSort) {
		marketTerminalConfigDAOJdbc.updateCconsConsSort(consId, consSort);
	}
	
	/**
	 * 考核单元副表
	 * @param chkUnitId
	 * @param tgID
	 */
	public void insertChkUnitComp(String chkUnitId, String tgID) {
		marketTerminalConfigDAOJdbc.insertChkUnitComp(chkUnitId, tgID);
	}
	
	/**
	 * 流入流出线损
	 * @param terminalId
	 * @param chkUnitId
	 */
	public void insertGIoMpByTmnlId(String terminalId, String chkUnitId, String tgId) {
		marketTerminalConfigDAOJdbc.insertGIoMpByTmnlId(terminalId, chkUnitId, tgId);
	}
	
	/**
	 * 其他分区考核单元
	 * @param terminalId
	 * @param tgId
	 * @param flowFlag
	 */
	public void otherChkUnit(String terminalId, String tgId, String flowFlag) {
		//台区
		if ("1".equals(flowFlag)) {
			//线路ID
			List<String> lineLst = marketTerminalConfigDAOJdbc.getLineByTgId(tgId);
			//部门编号
			List<String> orgLst = marketTerminalConfigDAOJdbc.getOrgNoByTgId(tgId);
			//台区对应
			List<?> tgChkUnitLst = marketTerminalConfigDAOJdbc.getChkUnit(tgId);
			String tgChkUnitId = "";
			if (tgChkUnitLst != null && tgChkUnitLst.size() > 0) {
				tgChkUnitId = StringUtil.removeNull(((Map<?,?>)tgChkUnitLst.get(0)).get("CHKUNIT_ID"));
			}
			
			for (int i = 0; i < lineLst.size(); i++) {
				String line = lineLst.get(i);
				List<?> result = marketTerminalConfigDAOJdbc.getChkUnit(line);
				if (result != null && result.size() > 0) {
					String chkUnitId = StringUtil.removeNull(((Map<?,?>)result.get(0)).get("CHKUNIT_ID"));
					String chkUnitTypeCode = StringUtil.removeNull(((Map<?,?>)result.get(0)).get("CHKUNIT_TYPE_CODE"));
					//关系表
					marketTerminalConfigDAOJdbc.insertGChkUnitLineRelaForFrm(chkUnitId, chkUnitTypeCode, tgChkUnitId);
				}	
			}
			for (int j = 0; j < orgLst.size(); j++) {
				String orgNo = orgLst.get(j);
				List<?> result = marketTerminalConfigDAOJdbc.getChkUnit(orgNo);
				if (result != null && result.size() > 0) {
					String chkUnitId = StringUtil.removeNull(((Map<?,?>)result.get(0)).get("CHKUNIT_ID"));
					String chkUnitTypeCode = StringUtil.removeNull(((Map<?,?>)result.get(0)).get("CHKUNIT_TYPE_CODE"));
					//关系表
					marketTerminalConfigDAOJdbc.insertGChkUnitLineRelaForFrm(chkUnitId, chkUnitTypeCode, tgChkUnitId);
				}
			}
			
			//检验一致性
			this.checkGIoMp(terminalId, tgId);
		}
	}
	
	/**
	 * 检验一致性
	 * @param terminalId
	 * @param tgId
	 */
	private void checkGIoMp(String terminalId, String tgId){
		
		String meterId = "";
		String dataId = "";
		List<?> list = marketTerminalConfigDAOJdbc.checkGIoMp(terminalId, tgId);
		if (list != null && list.size() > 0) {
			List<?> tgChkUnitLst = marketTerminalConfigDAOJdbc.getChkUnit(tgId);
			String tgChkUnitId = "";
			if (tgChkUnitLst != null && tgChkUnitLst.size() > 0) {
				tgChkUnitId = StringUtil.removeNull(((Map<?,?>)tgChkUnitLst.get(0)).get("CHKUNIT_ID"));
			}
			for (int i = 0; i < list.size(); i++) {
				Map<?,?> map = (Map<?,?>) list.get(i);
				meterId = StringUtil.removeNull(map.get("METER_ID"));
				dataId =  StringUtil.removeNull(map.get("ID"));
				//加入缺失的流入流出单位
				marketTerminalConfigDAOJdbc.insertGIoMpPerMeter(tgChkUnitId, tgId, meterId, dataId);
			}
		}
		
		List<?> list1 = marketTerminalConfigDAOJdbc.checkGIoMpExtra(terminalId, tgId);
		if (list1 != null && list1.size() > 0) {
			for (int i = 0; i < list1.size(); i++) {
				Map<?,?> map = (Map<?,?>) list1.get(i);
				meterId = StringUtil.removeNull(map.get("METER_ID"));
				dataId =  StringUtil.removeNull(map.get("ID"));
				//删除多余的流入流出
				marketTerminalConfigDAOJdbc.updateExtraGIoMp(meterId, dataId);
			}
		}
	}
	
	/**
	 * 关系表（关口）
	 * @param terminalId
	 */
	public void insertGChkUnitRelaAndGIoMpForGate(String terminalId) {
		//测量点数据主表
		List<?> edatampLst = marketTerminalConfigDAOJdbc.getEDataMpByTmnlId(terminalId);
		for (int i = 0; i < edatampLst.size(); i++) {
			Map<?,?> edataMap = (Map<?,?>) edatampLst.get(i);
			String dataId = StringUtil.removeNull(edataMap.get("ID"));
			String meterId = StringUtil.removeNull(edataMap.get("METER_ID"));
			
			/************************************流入流出******************************************/
			//计量点
			List<?> cmpLst = marketTerminalConfigDAOJdbc.getCMpByMeterId(meterId);
			for (int j = 0; j < cmpLst.size(); j++) {
				Map<?,?> cmpMap = (Map<?,?>) cmpLst.get(j);
				String lineId = StringUtil.removeNull(cmpMap.get("LINE_ID"));
				String orgNo = StringUtil.removeNull(cmpMap.get("ORG_NO"));
				String typeCode = StringUtil.removeNull(cmpMap.get("TYPE_CODE"));
				if (!"".equals(lineId)) {
					//检验存在考核单元  线路
					List<?> lineLst = marketTerminalConfigDAOJdbc.getChkUnit(lineId);
					if (lineLst != null && lineLst.size() > 0) {
						String chkUnitId = StringUtil.removeNull(((Map<?,?>)lineLst.get(0)).get("CHKUNIT_ID"));
						String chkUnitType = StringUtil.removeNull(((Map<?,?>)lineLst.get(0)).get("CHKUNIT_TYPE_CODE"));
						marketTerminalConfigDAOJdbc.insertGIoMpPerMeter(chkUnitId, lineId, meterId, dataId);
						marketTerminalConfigDAOJdbc.insertGChkUnitLineRelaForGate(chkUnitId, chkUnitType, meterId, typeCode); 
					}
				}
				if (!"".equals(orgNo)) {
					//检验存在考核单元   部门
					List<?> orgNoLst = marketTerminalConfigDAOJdbc.getChkUnit(orgNo);
					if (orgNoLst != null && orgNoLst.size() > 0) {
						String chkUnitId = StringUtil.removeNull(((Map<?,?>)orgNoLst.get(0)).get("CHKUNIT_ID"));
						String chkUnitType = StringUtil.removeNull(((Map<?,?>)orgNoLst.get(0)).get("CHKUNIT_TYPE_CODE"));
						marketTerminalConfigDAOJdbc.insertGIoMpPerMeter(chkUnitId, orgNo, meterId, dataId);
						marketTerminalConfigDAOJdbc.insertGChkUnitLineRelaForGate(chkUnitId, chkUnitType, meterId, typeCode); 
					}
				}	
			}	
		}	
	}
	
	
	public List<?> getMpSnsByTmnlSn(String tmnlAssetNo, String[] mpSnArrary) {
		return marketTerminalConfigDAOJdbc.getMpSnByTmnlSn(tmnlAssetNo, mpSnArrary);
	}

	public String getCpNoByAppNo(String appNo) {
		String cpNo = "";
		List<?> iImnlTaskList = marketTerminalConfigDAOJdbc.getITmnlTaskByAppNo(appNo);
		if(iImnlTaskList != null && iImnlTaskList.size() > 0){
			Map<?, ?> iImnlTaskMap = (Map<?, ?>) iImnlTaskList.get(0);
			cpNo = (String) iImnlTaskMap.get("CP_NO");
		}
		return cpNo;
	}

	public List<?> getMeterListByCpNo(String cpNo) {
		return marketTerminalConfigDAOJdbc.getCollObjByCpNo(cpNo);
	}
	
	/**
	 * 取得最大的测量点号
	 * @return
	 */
	public String getEDataMpMaxMpsn(String tmnlAssetNo) {
		return marketTerminalConfigDAOJdbc.getEDataMpMaxMpsn(tmnlAssetNo);
	}

	public String getTmnlTaskTypeByAppNo(String appNo) {
		String tmnlTaskType = "";
		List<?> iImnlTaskList = marketTerminalConfigDAOJdbc.getITmnlTaskByAppNo(appNo);
		if(iImnlTaskList != null && iImnlTaskList.size() > 0){
			Map<?, ?> iImnlTaskMap = (Map<?, ?>) iImnlTaskList.get(0);
			tmnlTaskType = (String) iImnlTaskMap.get("TMNL_TASK_TYPE");
		}
		return tmnlTaskType;
	}
	
	@Override
	public String getMeterFlagByAppNo(String appNo) {
		String meterFlag = "";
		List<?> iImnlTaskList = marketTerminalConfigDAOJdbc.getITmnlTaskByAppNo(appNo);
		if(iImnlTaskList != null && iImnlTaskList.size() > 0){
			Map<?, ?> iImnlTaskMap = (Map<?, ?>) iImnlTaskList.get(0);
			meterFlag = (String) iImnlTaskMap.get("METER_FLAG");
		}
		return meterFlag;
	}
	
	@Override
	public String getTgIdByAppNo(String appNo) {
		String tgId = "";
		List<?> iImnlTaskList = marketTerminalConfigDAOJdbc.getITmnlTaskByAppNo(appNo);
		if(iImnlTaskList != null && iImnlTaskList.size() > 0){
			Map<?, ?> iImnlTaskMap = (Map<?, ?>) iImnlTaskList.get(0);
			tgId = StringUtil.removeNull(iImnlTaskMap.get("TG_ID"));
		}
		return tgId;
	}

	public List<?> getEDataMpByTmnl(String tmnlAssetNo) {
		return marketTerminalConfigDAOJdbc.getMpSnByTmnl(tmnlAssetNo);
	}
	
	/**
	 * 根据表ID取得测量点数据主表
	 * @param meterId
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getEDataMpByMeterId(String tmnlAssetNo, String meterId) {
		return marketTerminalConfigDAOJdbc.getEDataMpByMeterId(tmnlAssetNo, meterId);
	}
	
	/**
	 * 根据表ID取得测量点数据主表
	 * @param meterId
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getEDataMpForChangeMeterByMeterId(String tmnlAssetNo, String oldMeterId) {
		return marketTerminalConfigDAOJdbc.getEDataMpForChangeMeterByMeterId(tmnlAssetNo, oldMeterId);
	}
	
	/**
	 * 更新测量点数据主表
	 * @param tmnlAssetNo
	 * @param oldMeterId
	 */
	public void updateEDataMpByTmnlMeterId(String tmnlAssetNo, String oldMeterId) {
		marketTerminalConfigDAOJdbc.updateEDataMpByTmnlMeterId(tmnlAssetNo, oldMeterId);
	}
	
	/**
	 * 去中间库去表变更
	 * @param appNo
	 * @param meterId
	 * @return
	 */
	public List<?> getRTmnlTaskSr(String appNo, String meterId) {
		return marketTerminalConfigDAOJdbc.getRTmnlTaskSr(appNo, meterId);
	}

	public void updateTmnlMpParamStatue(String dataId, String fn,
			String statusCode) {
		marketTerminalConfigDAOJdbc.updateTmnlMpParamStatue(dataId, fn, statusCode);
	}

	public List<?> getCConsByConsNo(String consNo) {
		return marketTerminalConfigDAOJdbc.getCConsByConsNo(consNo);
	}

	public List<?> getTmnlConsTaskByGapGradeNo(String consType) {
		return marketTerminalConfigDAOJdbc.getTmnlConsTaskByGapGradeNo(consType);
	}
	
	@Override
	public List<?> getTmnlConsTaskByGapGradeNo(String consType,
			String gapGradeNo) {
		return marketTerminalConfigDAOJdbc.getTmnlConsTaskByGapGradeNo(consType, gapGradeNo);
	}

	@Override
	public String getiTmnlTaskSetUp() {
		return marketTerminalConfigDAOJdbc.getiTmnlTaskSetUp();
	}
	
	/**
	 * 取得电能表通讯规约
	 * @param meterId
	 * @param protocolCode
	 * @return
	 */
	public String getCommNo(String meterId, String protocolCode) {
		return marketTerminalConfigDAOJdbc.getCommNo(meterId, protocolCode);
	}
	
	/**
	 * 根据表ID取得示数位数
	 * @param meterId 
	 */
	public String getMeterDigitsByMeterId(String meterId) {
		return marketTerminalConfigDAOJdbc.getMeterDigitsByMeterId(meterId);
	}
	
	/**
	 * 取得中间库采集对象
	 * @param meterId
	 * @return
	 */
	public String judgePort(String meterId) {
		return marketTerminalConfigDAOJdbc.judgePort(meterId);
	}
	
	/**
	 * 取得webservice连接信息
	 * @param tmnlId 终端编号
	 * @param cpNo
	 */
	public List<?> getWSInfo() {
		return marketTerminalConfigDAOJdbc.getWSInfo();
	}

	@Override
	public List<?> getOtherDev(String fmrAssetNo) {
		return marketTerminalConfigDAOJdbc.getOtherDev(fmrAssetNo);
	}

	@Override
	public List<?> getProtItemByProtocolNo(String protocolNo) {
		return marketTerminalConfigDAOJdbc.getProtItemByProtocolNo(protocolNo);
	}

	@Override
	public List<?> getITmnlTaskByAppNo(String appNo) {
		return marketTerminalConfigDAOJdbc.getITmnlTaskByAppNo(appNo);
	}
	
	/**
	 * 更新流程测试状态
	 * @param appNo
	 */
	public void updateItmnlTaskDebugStatus(String appNo) {
		marketTerminalConfigDAOJdbc.updateItmnlTaskDebugStatus(appNo);
	}

	@Override
	public List<?> getITmnlDebug(String appNo, String debugProgress) {
		return marketTerminalConfigDAOJdbc.getITmnlDebug(appNo, debugProgress);
	}

	@Override
	public void insertIMeterRead(String meterId, String mrFlag, String mrNum) {
		marketTerminalConfigDAOJdbc.insertIMeterRead(meterId, mrFlag, mrNum);
	}
	
	/**
	 * 取call数量
	 * @return
	 */
	public int getCallNum() {
		return marketTerminalConfigDAOJdbc.getCallNum();
	}
	
	/**
	 * 批量处理参数状态
	 * @param tmnlAssetNo
	 * @param mpSns
	 */
	public void batchUpdateTTmnlParamStatus(String tmnlAssetNo, String mpSns) {
		marketTerminalConfigDAOJdbc.batchUpdateTTmnlParamStatus(tmnlAssetNo, mpSns);
	}

	@Override
	public String getBgTaskId() {
		return marketTerminalConfigDAOJdbc.getSeqTbgTask();
	}
	

	/**
	 * 增加终端参数
	 * @param tmnlAssetNo
	 */
	public String extraTTmnlParam(String tmnlAssetNo){
		List<?> edataLst = marketTerminalConfigDAOJdbc.getEDataMpWithoutTmnlByTmnlId(tmnlAssetNo);
		String parmLst = marketTerminalConfigDAOJdbc.getTTmnlParmNumByAssetNo(tmnlAssetNo);

		if ((edataLst.size() - Integer.parseInt(parmLst)) > 0) {
			return "1";
		} else {
			return "0";
		}
	}
	
	public String getIinterfaceLogId() {
		return marketTerminalConfigDAOJdbc.getIinterfaceLog();
	}
	
	/**
	 * 中断的流程调试
	 * @param appNo
	 * @param tmnlId
	 */
	public void solveHalfOperation(String appNo, String tmnlId, String cpNo) {
		marketTerminalConfigDAOJdbc.solveHalfOperation(appNo, tmnlId, cpNo);
	}
	
	/**
	 * 插入中间异常信息
	 * @param orgNo
	 * @param collObjId
	 * @param alarmCode
	 * @param alarmDesc
	 * @param typeCode
	 * @param occurTime
	 * @param operator
	 */
	public void insertRExcpAlarm(String orgNo, String collObjId, String alarmCode, 
			String alarmDesc, String typeCode, String occurTime, String operator, String terminalId) {
		marketTerminalConfigDAOJdbc.insertRExcpAlarm(orgNo, collObjId, alarmCode, alarmDesc, typeCode, occurTime, operator, terminalId);
	}

	
	@Override               
	public String getStatusByTmnlParamStatue(String tmnlAssetNo) {
		
		String statue = "4";
		List<?> tmnlParamList = marketTerminalConfigDAOJdbc.getTmnlParamByTmnlAssetNo(tmnlAssetNo);
		if(tmnlParamList != null && tmnlParamList.size() > 0){
			int succFlag = 0;
			int failFlag = 0;
			int initFlag = 0;
			int otherFlag = 0;
			for(int i = 0; i < tmnlParamList.size(); i++){
				Map<?, ?> tmnlParamMap = (Map<?, ?>) tmnlParamList.get(i);
				String statusCode = StringUtil.removeNull(tmnlParamMap.get("STATUS_CODE"));
				if("04".equals(statusCode)){
					succFlag++;
				}else if("03".equals(statusCode)){
					initFlag++;
				}else if("05".equals(statusCode)){
					failFlag++;
				}else{
					otherFlag++;
				}
			}
			System.out.println("终端"+tmnlAssetNo+"参数下发统计-待下发"+initFlag+";成功："+succFlag+";失败："+failFlag+";其他"+otherFlag);
			if(succFlag > (initFlag + failFlag)){
				statue = "3";
			}else{
				statue = "4";
			}
		}
		return statue;
	}
	
	/**
	 * 电表注册 04F10 全新流程
	 * @param  appNo
	 * @param  tmnlAssetNo
	 * @param  flowNode
	 */
	public String getStatusByTmnlParamStatueForFrm(String appNo, String tmnlAssetNo, String flowNode) {
		String flowStatus = null;
		StringBuffer sf = new StringBuffer();
		List<?> tmnlParamList = marketTerminalConfigDAOJdbc.getTmnlParamByTmnlAssetNo(tmnlAssetNo);
		if(tmnlParamList != null && tmnlParamList.size() > 0){
			int succFlag = 0;
			int failFlag = 0;
			for(int i = 0; i < tmnlParamList.size(); i++){
				Map<?, ?> tmnlParamMap = (Map<?, ?>) tmnlParamList.get(i);
				String statusCode = StringUtil.removeNull(tmnlParamMap.get("STATUS_CODE"));
				String blockSn = StringUtil.removeNull(tmnlParamMap.get("BLOCK_SN"));
				if("04".equals(statusCode)){
					succFlag++;
				} else {
					//如果失败或者待下发 取出测量点号
					sf.append(blockSn+",");
					failFlag++;
				}
			}
			System.out.println("第一次电表注册....");
			System.out.println("终端 "+tmnlAssetNo+ "电表注册统计-成功："+succFlag+"; 失败："+failFlag);
			String mpSnList = "";
			if (failFlag > 0) {        //存在失败测量点
				flowStatus = "1";
				mpSnList = sf.substring(0, sf.length()-1);  
			} else {                   //成功
				flowStatus = "0";
			}
			String failCode = "";
			if ("1".equals(flowStatus)) {
				failCode = marketTerminalConfigDAOJdbc.getFailureCodeFromTTmnlParam(tmnlAssetNo);	
			}
			System.out.println("****************************************************************************************");
			System.out.println("***************异常信息: "+failCode);
			System.out.println("****************************************************************************************");
			//保存流程明细
			this.updateIFlowProcess(appNo, tmnlAssetNo, flowNode, flowStatus, mpSnList, failCode);
		}
		return flowStatus;
	}
	
	/**
	 * 根据tmnlAssetNo取对应的终端参数
	 * @param tmnlAssetNo
	 */
	public int getTmnlParamByTmnlAssetNo(String tmnlAssetNo) {
		List<?> tmnlParamSum = marketTerminalConfigDAOJdbc.getTmnlParamByTmnlAssetNo(tmnlAssetNo);
		return tmnlParamSum.size();
	}
	
	/**
	 * 取得结果状态（针对失败测量点重新设置）
	 * @param appNo
	 * @param tmnlAssetNo
	 * @param flowNode
	 * @param mpSnArrary
	 * @return
	 */
	public String getStatusByTmnlMpSnParamStatue(String appNo, String tmnlAssetNo, String flowNode, String[] mpSnArrary) {
		String flowStatus = null;
		StringBuffer sf = new StringBuffer();
		List<?> tmnlParamList = marketTerminalConfigDAOJdbc.getTmnlParamByTmnlAssetNoMpSn(tmnlAssetNo, mpSnArrary);
		if(tmnlParamList != null && tmnlParamList.size() > 0){
			int succFlag = 0;
			int failFlag = 0;
			for(int i = 0; i < tmnlParamList.size(); i++){
				Map<?, ?> tmnlParamMap = (Map<?, ?>) tmnlParamList.get(i);
				String statusCode = StringUtil.removeNull(tmnlParamMap.get("STATUS_CODE"));
				String blockSn = StringUtil.removeNull(tmnlParamMap.get("BLOCK_SN"));
				
				if("04".equals(statusCode)) {
					succFlag++;
				} else {
					sf.append(blockSn+",");
					failFlag++;
				}
			}
			System.out.println("对失败的测量点进行注册....");
			System.out.println("终端: "+tmnlAssetNo+"电表注册统计-成功："+succFlag+"; 失败："+failFlag);	
			String mpSnList = "";
			//存在失败的测量点
			if (failFlag > 0) {
				flowStatus = "1";
				if (sf.length() > 1) {                            
					mpSnList = sf.substring(0, sf.length()-1);
				} 
			} else {
				flowStatus = "0";
			}
			
			String failCode = "";
			if ("1".equals(flowStatus)) {
				failCode = marketTerminalConfigDAOJdbc.getFailureCodeFromTTmnlParam(tmnlAssetNo);	
			}
			System.out.println("****************************************************************************************");
			System.out.println("***************异常信息: "+failCode);
			System.out.println("****************************************************************************************");
			//保存流程明细
			this.updateIFlowProcess(appNo, tmnlAssetNo, flowNode, flowStatus, mpSnList, failCode);
		}
		return flowStatus;
	}
	
	/**
	 * 终端主动上送
	 * 取得任务返回状态
	 */
	public String getTaskStatusByTaskDeal(List<String> tmnlAssetNos,
			short setParameters, List<ParamItem> params, String mpSns) {
		String status = "";
		List<Response> respList = null;
		int length = mpSns.split(",").length; //参数测量点个数
		int times = this.getFrmTaskTime();    //取得下发时间
		if(!TaskDeal.isCacheRunning()){
			System.out.println("--------------应用服务器与缓存服务器通信中断--------------");
			status = "4";
		}else if(!TaskDeal.isFrontAlive()){
			System.out.println("--------------前置集群服务通信中断-----------------------");
			status = "4";
		}else{
			TaskDeal taskDeal = new TaskDeal();
			taskDeal.qstTermParamTaskResult(tmnlAssetNos,setParameters, params);
			int bgs = length/32 + 1;
			int time = bgs * times;//32个测量点为80秒
			System.out.println("---------------------------下发任务中,请等待"+bgs*times+"秒-----------------------------");
			respList = taskDeal.getAllResponse(time);
			for (int i = 0; i < respList.size(); i++) {
				status = String.valueOf(respList.get(i).getTaskStatus());
			}	
		}
		if("0".equals(status)){
			System.out.println("--------------在规定的时间内缓存没有返回------------------");
		}else if("1".equals(status)){
			System.out.println("--------------终端不在线---------------------------------");
		}else if("3".equals(status)){
			System.out.println("--------------通讯成功-----------------------------------");
		}else if("4".equals(status)){
			System.out.println("--------------通讯失败-----------------------------------");
		}
		return status;
	}
	
	/**
	 * 根据终端ID取得流程明细 
	 * @param appNo      申请号
	 * @param tmnlId     终端ID
	 * @param flowNode   节点标识
	 * @return
	 */
	public Map<?, ?> getIFlowProcess(String appNo, String tmnlId, String flowNode) {
		return marketTerminalConfigDAOJdbc.getIFlowProcess(appNo, tmnlId, flowNode);
	}
	
	//取得建档成功数量e_data_mp
	public int getEDataMpNumByCpNo(String cpNo) {
		return marketTerminalConfigDAOJdbc.getEDataMpNumByCpNo(cpNo);
	}
	
	//取得建档成功数量e_data_mp
	public void updateIFlowProcess(String appNo, String tmnlAssetNo, String flowNode, String flowStatus, String mpSnList, String failCode) {
		marketTerminalConfigDAOJdbc.updateIFlowProcess(appNo, tmnlAssetNo, flowNode, flowStatus, mpSnList, failCode);
	}
	
	//更新测量点明细
	public void updateUpdateMpSnList(String appNo, String terminalId, String flowNode, String sendTmnlFailure) {
		marketTerminalConfigDAOJdbc.updateUpdateMpSnList(appNo, terminalId, flowNode, sendTmnlFailure);
	}
	
	/**
	 * 查看流程是否成功
	 */
	public List<?> checkSuccessOrFaliure(String appNo, String tmnlAssetNo) {
		return marketTerminalConfigDAOJdbc.checkSuccessOrFaliure(appNo, tmnlAssetNo);
	}
	
	@Override
	public void updateTmnlParamOtherStatue(String tmnlAssetNo, String blockSn,
			String protItemNo) {
		// TODO Auto-generated method stub
		marketTerminalConfigDAOJdbc.updateTmnlParamOtherStatue(tmnlAssetNo, blockSn, protItemNo);
	}

	@Override
	public void updateMeterRegStatus(String tmnlAssetNo, String mpSn) {
		// TODO Auto-generated method stub
		marketTerminalConfigDAOJdbc.updateMeterRegStatus(tmnlAssetNo, mpSn);
	}

	@Override
	public List<?> getMpSnsByTmnlAndStatus(String tmnlAssetNo, String regStatus) {
		// TODO Auto-generated method stub
		return marketTerminalConfigDAOJdbc.getMpSnsByTmnlAndStatus(tmnlAssetNo, regStatus);
	}
	
	/**
	 * 取得任务号
	 * @param tmnlAssetNo
	 * @param templateId
	 * @return
	 */
	public List<?> getTTmnlTaskByTmnlNoTemplateId(String tmnlAssetNo, String templateId) {
		return marketTerminalConfigDAOJdbc.getTTmnlTaskByTmnlNoTemplateId(tmnlAssetNo, templateId);
	}
	
	/**
	 * 取得任务号
	 * @param tmnlAssetNo
	 * @param templateId
	 * @return
	 */
	public String getTaskNoByTmnlNo(String tmnlAssetNo) {
		return marketTerminalConfigDAOJdbc.getTaskNoByTmnlNo(tmnlAssetNo);
	}

	/**
	 * 删除群组明细
	 * @param consNo
	 */
	public void deleteGroupDetail(String consNo) {
		marketTerminalConfigDAOJdbc.deleteGroupDetail(consNo);
	}

	/**
	 * 删除用户终端关系表
	 * @param consNo
	 * @param tmnlAssetNo
	 */
	public void deleteRconsTmnlRela(String consNo, String tmnlAssetNo) {
		marketTerminalConfigDAOJdbc.deleteRconsTmnlRela(consNo, tmnlAssetNo);
	}

	@Override
	public List<?> getCancleConsNo() {
		return publicDbDAO.getCancleConsNo();
	}

	@Override
	public List<?> getChgAppNo() {
		return publicDbDAO.getChgAppNo();
	}

	@Override
	public List<?> getTmnlByConsNo(String consNo) {
		return marketTerminalConfigDAOJdbc.getTmnlByConsNo(consNo);
	}

	@Override
	public boolean getDBStatus() {
		return marketTerminalConfigDAOJdbc.getDBStatus();
	}

	@Override
	public boolean getMidDBStatus() {
		return marketTerminalConfigDAOJdbc.getMidDBStatus();
	}

	@Override
	public List<?> getMidRcollObjByCpNo(String cpNo) {
		return publicDbDAO.getMidRcollObjByCpNo(cpNo);
	}

	@Override
	public void updateEdataMpByTmnlIdAndMeterId(String collObjId,
			String meterId, String tmnlId) {
		marketTerminalConfigDAOJdbc.updateEdataMpByTmnlIdAndMeterId(collObjId, meterId, tmnlId);
	}

	@Override
	public void updateRcollObjByCpNoAndMeterId(String collObjId,
			String meterId, String cpNo) {
		marketTerminalConfigDAOJdbc.updateRcollObjByCpNoAndMeterId(collObjId, meterId, cpNo);
	}

	@Override
	public void updateWkstAppNo(String appNo, String flag) {
		marketTerminalConfigDAOJdbc.updateWkstAppNo(appNo, flag);
	}

	@Override
	public String getMaxMpSn(String tmnlAssetNo) {
		return marketTerminalConfigDAOJdbc.getMaxMpSn(tmnlAssetNo);
	}

	@Override
	public void delRConsTmnlRelaByConsNo(String consNo) {
		// TODO Auto-generated method stub
		marketTerminalConfigDAOJdbc.delRConsTmnlRelaByConsNo(consNo);
	}

	@Override
	public String getConsNoByMeterId(String meterId) {
		String consNo = "";
		List<?> list = marketTerminalConfigDAOJdbc.getConsNoByMeterId(meterId);
		if(list != null && list.size() > 0){
			Map<?, ?> map = (Map<?, ?>) list.get(0);
			consNo = (String) map.get("CONS_NO");
		}
		return consNo;
	}
}