package com.nari.dao.jdbc;

import java.util.List;

public interface PublicDbDAO{

	List<?> getRTmnlTaskSr(String appNo);

	String getSeqREpctCommon();
	

	/**
	 * 电表变更，从中间库取数据
	 * @param appNo 申请编号
	 */
	public List<?> getRTmnlTaskSrByAppNo(String appNo);

	void insertRTmnlDebug(String debugId, String terminalId);

	List<?> getRTmnlDebugByDebugId(Long debugId);

	void updateRTmnlDebugTerminalFlag(Long debugId, String terminalFlag);

	List<?> getPublicTmnlOrg(String consId);
	
	List<?> getPublicTmnlTypeCodeById(String tmnlId);

	List<?> getRTmnlDebugByTmnlId(String terminalId);

	String checkTmnlMeter(String tmnlId, String cpNo, String appNo, String tmnlTaskType, String flag, String newTmnlId);

	List<?> getPublicTgOrg(String tgId);

	void updateRTmnlDebugMasterCommFlag(Long debugId, String terminalFlag);

	void updateRTmnlDebugMeterCommFlag(Long debugId, String terminalFlag);

	List<?> getCancleConsNo();

	List<?> getChgAppNo();

	List<?> getMidRcollObjByCpNo(String cpNo);
	
}