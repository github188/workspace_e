package com.nari.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nari.ami.database.map.basicdata.BClearProtocol;
import com.nari.ami.database.map.device.DMeter;
import com.nari.ami.database.map.runcontrol.RTmnlRun;
import com.nari.ami.database.map.terminalparam.TTmnlTask;
import com.nari.exception.DBAccessException;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.RealDataItem;
import com.nari.model.CMeter;
import com.nari.model.Edatamp;
import com.nari.model.TbgTask;
import com.nari.model.TbgTaskDetail;
import com.nari.model.TtmnlParam;

/**
 * 终端装接接口
 * 
 * @date 2010-3-15上午08:58:19
 */
public interface MarketTerminalConfigManager {
	
	/**
	 * 取得台区ID
	 * @param appNo
	 * @return
	 */
	public String getTgIdByAppNo(String appNo);
	
	/**
	 * 取得集抄居民任务模板
	 * @return
	 */
	public String getiTmnlTaskSetUp();
	
	/**
	 * 更新用户状态
	 * @param consNo 用户编号 
	 * @param status 用电状态
	 * 
	 */
	int updateCustomerStatus(String consNo, String status);
	
	/**
	 * 取终端局编号
	 * @param consNo 用户编号 		
	 */
	List<?> getTerminalInfo(String consNo);
	
	/**
	 * 取表计局编号
	 * @param consNo 用户编号 		
	 */
	List<?> getAmeterInfo(String consNo);
	
	/**
	 * 更新终端测量点状态
	 * @param ZDJH 终端局编号
	 * @param status 测量点是否有效
	 */
	void updateTerminalMeasurePonitStatus(String zdjh, String status);
	
	/**
	 * 检查是否抄表
	 * @param ameterId
	 * @return
	 */
	long checkMainMater(String ameterId);
	
	/**
	 * 更新终端运行状态
	 * @param ZDJH 终端局编号
	 * @param status 终端状态（-1拆除，006  待投，007 运行，008停运，009待检修）
	 */
	void updateTerminalStatus(String zdjh, String status);

	/**
	 * 更新表计状态
	 * @param JBJH 表计局编号
	 * @param status 表计状态（012 运行，015拆回待退）
	 */
	void updateBJStatus(String bjjh, String status);

	/**
	 * 更新表计状态
	 * @param consNo 户号
	 */
	void deleteQzByYh(String consNo);

	/**
	 * 插入webService:WS_TMNL_TASK_SR数据（终端运行管理）
	 * @param map XML 数据
	 */
	void insertITmnlTask(Map<String, Object> map, String flag);

	/**
	 * 电表变更，从中间库取数据
	 * @param appNo 申请编号
	 */
	List<?> getRTmnlTaskSr(String appNo);
	
	/**
	 * 电表变更，从中间库取数据
	 * @param appNo 申请编号
	 */
	List<?> getRTmnlTaskSrByAppNo(String appNo);

	/**
	 * 更新测试进度
	 * @param appNo 申请编号
	 */
	void updateTestStatue(String appNo, String csjdStart);
	
	/**
	 * 换终端
	 * @param tmnlId
	 * @param newTmnlId
	 */
	public void changeTmnl(String tmnlId, String newTmnlId);
	
	/**
	 * 删除关口联系表
	 * @param tmnlId
	 */
	public void deleteCMeterPbsRelaByTmnlId(String tmnlId);
	
	/**
	 * 删除联系表
	 * @param tmnlId
	 */
	public void deleteGIoMpByTmnlId(String tmnlId);
	
	/**
	 * 关口表
	 * @param tmnlId
	 */
	public void insertCMeterPbsRela(String tmnlId);
	
	/**
	 * 删除终端实时工况
	 * @param tmnlId
	 * @return
	 */
	public void removeATmnlRealTime(String tmnlId);
	
	/**
	 * 更新考核单元状态
	 * @param tgId
	 */
	public void updateGChkUnitStatus(String tgId);
	
	/**
	 * 增加终端实时工况
	 * @param tmnlId
	 */
	public void addATmnlRealTime(String tmnlId);
	
	/**
	 * 更新终端状态
	 * @param terminalId
	 */
	public void updateTmnlStatus(String terminalId);
	
	/**
	 * 取得测量点数据主表
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getALLEDataMpByTmnl(String tmnlAssetNo);
	
	/**
	 * 检查主表存在
	 * @param tmnlAssetNo
	 * @param k
	 * @return
	 */
	public int checkMainMeterIsHaved(String tmnlAssetNo, int k, int w);
	
	/**
	 * 更新测量点数据主表
	 * @param edataLst
	 */
	public void mergeEDataMp( List<Edatamp> edataLst);
	
	/**
	 * 批量更新终端参数表
	 * @param tTmnlParamLst
	 */
	public void mergeTTmnlParam(List<TtmnlParam> tTmnlParamLst);
	
	/**
	 * 根据采集点编号取得采集点编号
	 * @param cpNo
	 * @return
	 */
	public List<?> getRcpBycpNo(String cpNo);
	
	/**
	 * 根据采集点编号取得采集点计量点关系
	 * @param cpNo
	 * @return
	 */
	public List<?> getCMpByCpNo(String cpNo);
	
	/**
	 * 根据采集点编号取得变电站线路关系
	 * @param cpNo
	 * @return
	 */
	public List<?> getGSubsLineRelaByCpNo(String cpNo);
	
	/**
	 * 采集器变更
	 * @param appNo
	 */
	public void operateCollector(String appNo, String tmnlId, String cpNo, String tgId, String tmnlTaskType, String meterFlag);
	
	/**
	 * 删除表
	 * @param appNo
	 */
	public void onlyRemoveMeters(String appNo);
	
	/**
	 * 检验删表
	 * @param appNo
	 * @return
	 */
	public String checkOnlyRemoveMeter(String appNo);

	/**
	 * 根据终端编号取终端信息
	 * @param terminalId 终端ID
	 */
	List<?> getTerminalById(String terminalId);
	
	/**
	 * 根据表ID取得测量点数据主表
	 * @param meterId
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getEDataMpByMeterId(String tmnlAssetNo, String meterId);
	
	/**
	 * 根据表ID取得测量点数据主表
	 * @param meterId
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getEDataMpForChangeMeterByMeterId(String tmnlAssetNo, String oldMeterId);

	/**
	 * 更新测量点数据主表
	 * @param tmnlAssetNo
	 * @param oldMeterId
	 */
	public void updateEDataMpByTmnlMeterId(String tmnlAssetNo, String oldMeterId);
	
	/**
	 * 
	 * @param tmnlAssetNo
	 * @param ameterId
	 * @return
	 */
	public List<?> getRTmnlTaskSr(String tmnlAssetNo, String ameterId);
	
	/**
	 * 根据表ID取得表自身倍率
	 * @param meterId
	 * @return
	 */
	public String getSelfFactorMeterId(String meterId);
	
	/**
	 * 根据表ID取得表自身倍率
	 * @param meterId
	 * @return
	 */
	public List<?> getDmeterByMeterId(String meterId);
	
	/**
	 * 根据表ID取得表上级采集器的地址
	 * @param ameterId
	 * @return
	 */
	public List<?> getfrmAddr(String ameterId);
	
	/**
	 * 插入终端调试信息
	 * @param terminalId 终端ID
	 */
	Long insertRTmnlDebug(String terminalId);

	/**
	 * 根据调试编号取终端调试信息
	 * @param terminalId 终端ID
	 */
	List<?> getRTmnlDebugByDebugId(Long debugId);

	/**
	 * 根据ameterId取表计信息
	 * @param terminalId 终端ID
	 */
	List<?> getAmeterById(String ameterId);

	/**
	 * 根据tmnlAssetNo，ameterAssetNo取测量点信息
	 * @param tmnlAssetNo 终端资产号
	 * @param ameterAssetNo 表计资产号
	 */
	List<?> getMpSn(String tmnlAssetNo, String ameterAssetNo);

	/**
	 * 根据tmnlAssetNo取终端信息
	 * @param tmnlAssetNo 终端资产号
	 */
	RTmnlRun getTmnlByAssetNo(String tmnlAssetNo);
	
	/**
	 * 根据终端ID取得运行电能表数量
	 * @param terminalId
	 * @return
	 */
	public List<?> getCMeterByTmnlId(String terminalId);
	
	/**
	 * 根据终端局号取得运行终端
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getRTmnlRunByAssetNo(String tmnlAssetNo);

	/**
	 * 根据终端局号取得运行终端
	 * @param tmnlAssetNo
	 * @return
	 */
	public String getYXRTmnlRunByAssetNo(String tmnlAssetNo);
	
	/**
	 * 根据ameterAssetNo取表计信息
	 * @param ameterAssetNo 表计资产号
	 */
	CMeter getAmeterByAssetNo(String ameterId);

	/**
	 * 根据采集点编号取采集对象
	 * @param cpNo 采集点编号
	 */
	List<?> getCollObjByCpNo(String cpNo);

	/**
	 * 根据电能表标识取采集对象 
	 * @param meterId 电能表标识
	 */
	List<?> getCollObjByMeterId(Long meterId);

	/**
	 * 取测量点数据主表数据
	 * @param tmnlAssetNo 终端资产编号
	 * @param mpSn 测量点号
	 * @param meterId
	 */
	List<?> getDataMpByParam(String tmnlAssetNo, String mpSn, String meterId);
	
	/**
	 * 
	 * @param tmnlAssetNo
	 * @param mpSn
	 * @return
	 */
	List<?> getDataMpByTmnlNoMpSn(String tmnlAssetNo, String mpSn);
	
	/**
	 * 根据表通讯地址和测量点号取得e_data_mp
	 * @param mpSn
	 * @param comm1
	 * @return
	 */
	public List<?> getDataMpByMpSnComm1(String mpSn, String comm1);
	
	/**
	 * 批量处理参数状态
	 * @param tmnlAssetNo
	 * @param mpSns
	 */
	public void batchUpdateTTmnlParamStatus(String tmnlAssetNo, String mpSns);

	/**
	 * 插入测量点数据
	 * @param orgNo 供电单位编号
	 * @param 
	 */
	void insertDataMp(String orgNo, String consNO, String tmnlAssetNo, String cisTmnlAssetNo, String terminalAddr, String aMeterId,
			String ameterAssetNo, String commAddr, String mpType, String collObj,String dataSrc, String mpSn,
			String pt, String ct, String selfFactor, String calcMode,
			String isValid, String areaCode);
	
	/**
	 * 更新测量点数据主表
	 * @param isValid2 
	 */
	void updateDateMp(String dataId, String orgNo, String consNo, String tmnlAssetNo, String cisTmnlAssetNo, String terminalAddr, String aMeterId,
			String ameterAssetNo, String commAddr, String mpType, String collObj, String dataSrc, String mpSn,
			String pt, String ct, String selfFactor, String calcMode,
			String isValid);

	/**
	 * 取测量点参数
	 * @param dataId 测量点id
	 * @param protItemNo 规约项数据编码
	 */
	List<?> getTmnlMpParamByIdAndProtNo(String dataId, String protItemNo);

	/**
	 * 插入终端测量点参数
	 * @param dataId 测量点id
	 * @param protItemNo 规约项数据编码
	 * @param currentValue 参数当前值
	 */
	void insertTmnlMpParam(String dataId, String protItemNo, String currentValue , String blockSn, String innerBlockSn, String statusCode);

	/**
	 * 更新终端测量点参数
	 * @param dataId 测量点id
	 * @param protItemNo 规约项数据编码
	 * @param currentValue 参数当前值
	 * @param historyValue 参数历史值
	 * @param lastSendTime 上次下发时间
	 */
	void updateTmnlMpParam(String dataId, String protItemNo,
			String currentValue, String historyValue, String blockSn, String innerBlockSn, String statusCode, Date lastSendTime);
	

	/**
	 * 根据任务模板ID取透明规约
	 * @param templateId 任务模板ID
	 */
	List<BClearProtocol> findAllByTemplateId(long templateId);

	/**
	 * 根据终端资产编号和任务模板id确定任务是否存在
	 * @param tmnlAssetNo 终端资产编号
	 * @param templateId 任务模板id
	 * @return TTmnlTask
	 * @throws DBAccessException
	 */
	TTmnlTask findTaskByNoId(String tmnlAssetNo, long parseLong);

	/**
	 * 根据表计资产编号取表计档案
	 * @param ameterAssetNo 表计资产编号
	 * @return DMeter
	 * @throws DBAccessException
	 */
	DMeter getDmeterByAssetNo(String ameterId);
	
	/**
	 * 根据表ID取得用户容量和计量方式
	 * @param meterId
	 * @return
	 */
	List<?> getCapMeasMode(String meterId);

	/**
	 * 根据终端资产编号取是否附属电能表
	 * @param tmnlAssetNo 表计资产编号
	 * @return AttachMeterFlag
	 */
	String getAttachMeterFlag(String tmnlAssetNo);
	
	/**
	 * 取得额定电压电流
	 * @param contract_cap
	 * @param wiring_mode
	 * @param meas_mode
	 * @param ct
	 * @param pt
	 * @return
	 */
	public String getVoltCurr(final String contract_cap, final String wiring_mode,final String meas_mode, final String ct, final String pt);

	/**
	 * 根据规约项数据编码取块内序号
	 * @param protItemNo 规约项数据编码
	 * @return InnerBlockSn 块内序号
	 */
	String getInnerBlockSnByProtItemNo(String protItemNo);
	
	/**
	 * 根据终端资产编号 和 块序号判断所属的规约项编码是否存在
	 * @param tmnlAssetNo 终端资产号
	 * @return blockSn 块序号
	 */
	List<?> getTmnlParamByTmnlAssetNoAndBlockSn(String tmnlAssetNo,
			String blockSn);

	/**
	 * 根据终端资产编号取得F10终端参数
	 * @param tmnlAssetNo 终端资产号
	 * @return blockSn 块序号
	 */
	public List<?> getF10TmnlParamByTmnlAssetNo(String tmnlAssetNo);
	
	/**
	 * 置终端参数无效
	 * @param tmnlAssetNo 终端资产号
	 * @param blockSn 块序号
	 * @param isValid 是否有效
	 */
	void updateTmnlParam(String tmnlAssetNo, String blockSn, String isValid);

	/**
	 * 取终端参数
	 * @param tmnlAssetNo 终端资产号
	 * @param protItemNo 规约项数据编码
	 * @param blockSn 块序号
	 * @param innerBlockSn 块内序号
	 */
	List<?> getTmnlParamByKey(String tmnlAssetNo, String protItemNo,
			String blockSn, String innerBlockSn);

	/**
	 * 更新终端参数
	 * @param statusCode 
	 */
	void updateTmnlParam(String tmnlAssetNo, String protItemNo,
			String currentValue, String historyValue, String blockSn,
			String innerBlockSn, String statusCode, String isValid, Date lastSendTime);

	/**
	 * 取得终端事件分类 
	 * @param protocol
	 */
	List<?> getLevel(String protocol);

	
	/**
	 * 插入终端参数
	 * @param statusCode 
	 */
	void insertTmnlParam(String tmnlAssetNo, String protItemNo,
			String currentValue, String blockSn, String innerBlockSn, String statusCode);

	/**
	 * 插入调试明细
	 * @param appNo 申请单号
	 * @param terminalId 终端编号
	 * @param ameterId 电能表标识
	 * @param debugStart 调试进度
	 */
	void insertTmnlDebug(String appNo, String terminalId, String ameterId,
			String debugProgress);
	
	/**
	 * 插入调试明细
	 * @param appNo 申请单号
	 * @param terminalId 终端编号
	 * @param ameterId 电能表标识
	 * @param debugStart 调试进度
	 * @param failCause 失败原因
	 */
	void insertTmnlDebug(String appNo, String terminalId, String ameterId,
			String debugProgress, String failCause);

	/**
	 * 插入日志
	 * @param logId logId
	 * @param sysNo 系统编号
	 * @param interfaceType 接口类型
	 * @param interfaceName 接口名称
	 * @param interfaceContent 接口内容
	 * @param respId 出错编码
	 */
	void writeLog(String logId, String sysNo, String interfaceType, String interfaceName,
			String interfaceContent, String respId);

	/**
	 * 获取调用前置机的状态-参数设置与参数召测
	 * @param params 
	 * @param setParameters 
	 * @param tmnlAssetNos 
	 */
	String getStatusByTaskDeal(List<String> tmnlAssetNos, short setParameters, List<ParamItem> params);
	
	/**
	 * 根据终端ID取得采集点对象数量
	 * @param int
	 */
	public int getRcollObjNumByTmnlId(String terminalId);
	
	/**
	 * 根据终端ID取得采集点对象
	 * @param int
	 */
	public List<?> getRcollObjByTmnlId(String terminalId);
	
	/**
	 * 更新终端总加组参数表
	 * @param terminalId
	 */
	public void updateTtmnlTotalInfo(String terminalId);
	
	/**
	 * 更新终端总加组主表
	 * @param terminalId
	 */
	public void updateEDataTotal(String terminalId);

	/**
	 * 获取调用前置机的状态-数据召测
	 * @param params 
	 * @param setParameters 
	 * @param tmnlAssetNos 
	 */
	String getStatusByTaskDealCallData(List<String> tmnlAssetNos, List<RealDataItem> params);

	/**
	 * 根据终端和原来表计取测量点号
	 * @param tmnlAssetNo 
	 * @param ameterAssetNo 
	 */
	String getMpsnByTmnlAndMeter(String tmnlAssetNo, String ameterAssetNo);

	/**
	 * 更新运行电能表c_meter中的，注册序号REG_SN
	 * @param ameterAssetNo 
	 * @param mpSn 
	 */
	void updateMeterRegSn(String ameterId, String mpSn);
	
	/**
	 * 更新终端的接线方式
	 * @param tmnlAssetNo
	 */
	void updateWiringMode(String tmnlAssetNo);
	
	/**
	 * 销户销终端
	 * @param tmnlId
	 * @param tmnlAssetNo
	 * @param tmnlType
	 * @param consType
	 * @param appNo
	 * @param tgId
	 * @param cpNo
	 */
	public void cancelUserTmnl(String tmnlId, String tmnlAssetNo, String tmnlType, String consType, String consNo, String appNo, String tgId, String cpNo);

	/**
	 * 负控终端工作正常标志
	 * @param debugId
	 * @param string
	 */
	void updateRTmnlDebugTerminalFlag(Long debugId, String string);

	/**
	 * 根据consId从中间库取得单位代码
	 * @param consId
	 */
	String getPublicTmnlOrg(String consId);
	
	/**
	 * 从中间库取得终端信息
	 * @param tmnlId
	 * @return
	 */
	public List<?> getYXTerminalById(String tmnlId);
	
	/**
	 * 根据consId从中间库取得TmnlTypeCode
	 * @param tmnlId
	 */
	String getPublicTmnlTypeCodeById(String tmnlId);

	/**
	 * 根据终端资产号tmnlAssetNo取表计测量点数量
	 * @param tmnlId
	 */
	String getMpSnByTmnl(String tmnlAssetNo);

	/**
	 * 根据终端资产号，取户号和单位
	 * @param tmnlAssetNo
	 */
	List<?> getConsTmnlRelaByTmnlAssetNo(String tmnlAssetNo);

	/**
	 * 更新终端参数状态
	 * @param tmnlAssetNo
	 */
	void updateTmnlParamStatue(String tmnlAssetNo, String f9, String f10);

	/**
	 * 根据终端资产号取表计测量点
	 * @param tmnlAssetNo
	 */
	List<?> getMpSnsByTmnl(String tmnlAssetNo);
	
	/**
	 * 根据终端资产号取表计测量点(集抄)
	 * @param tmnlAssetNo
	 */
	public List<?> getFrmMpSnsByTmnl(String tmnlAssetNo);
	
	/**
	 * 根据终端ID取得明细
	 * @param terminld
	 */
	public List<?> getTmnlTaskByTmnlId(String terminalId);
	
	/**
	 * 根据采集点对象ID
	 * @param tgID
	 * @return
	 */
	public List<?> getChkUnit(String tgID);
	
	/**
	 * 建立考核单元
	 * @param orgNo
	 * @param tgName
	 */
	public String insertChkUnit(String orgNo, String tgName, String tgId);
	
	/**
	 * 根据终端ID取得用户
	 * @return
	 */
	public List<?> getConsCmpByTmnlId(String terminalId);
	
	/**
	 * 更新虚拟用户分类
	 * @param terminalId
	 */
	public void updateCyberConsSort(String terminalId);
	
	/**
	 * 更新虚拟用户分类
	 * @param terminalId
	 */
	public void updateCyberConsSortForGate(String terminalId);
	
	/**
	 * 负控专变流入流出
	 * @param terminalId
	 */
	public void insertGIoMpForRtu(String terminalId);
	
	/**
	 * 关口流入流出
	 * @param terminalId
	 */
	public void insertGIoMpForGate(String terminalId);
	
	/**
	 * 考核单元关系（关口）
	 * @param terminalId
	 */
	public void insertGChkUnitRelaAndGIoMpForGate(String terminalId);
	
	/**
	 * 更新用户分类
	 * @param consId
	 * @param consSort
	 */
	public void updateCconsConsSort(String consId, String consSort);

	/**
	 * 考核单元副表
	 * @param chkUnitId
	 * @param tgID
	 */
	public void insertChkUnitComp(String chkUnitId, String tgID);
	
	/**
	 * 流入流出线损
	 * @param terminalId
	 * @param chkUnitId
	 */
	public void insertGIoMpByTmnlId(String terminalId, String chkUnitId, String tgId);
	
	/**
	 * 其他分区考核单元
	 * @param terminalId
	 * @param tgId
	 * @param flowFlag
	 */
	public void otherChkUnit(String terminalId, String tgId, String flowFlag);
	
	/**
	 * 根据appNo取终端变更TMNL_TASK_TYPE
	 * @param appNo
	 */
	String getTmnlTaskTypeByAppNo(String appNo);

	/**
	 * 根据appNo取CP_NO
	 * @param appNo
	 */
	String getCpNoByAppNo(String appNo);

	/**
	 * 根据CP_NO取METER_ID
	 * @param cpNo
	 */
	List<?> getMeterListByCpNo(String cpNo);
	
	/**
	 * 取得最大的测量点号
	 * @return
	 */
	public String getEDataMpMaxMpsn(String tmnlAssetNo);

	/**
	 * 根据tmnlAssetNo取EDataMp
	 * @param tmnlAssetNo
	 */
	List<?> getEDataMpByTmnl(String tmnlAssetNo);

	/**
	 * 更新测量点参数状态为下发成功
	 * @param dataId
	 * @param fn
	 * @param statusCode
	 */
	void updateTmnlMpParamStatue(String dataId, String fn, String statusCode);

	/**
	 * 根据consNo取C_Cons
	 * @param consNo
	 */
	List<?> getCConsByConsNo(String consNo);

	/**
	 * 根据用户类型取任务模板
	 * @param consType
	 */
	List<?> getTmnlConsTaskByGapGradeNo(String consType);
	
	/**
	 * 根据用户类型，容量等级取任务模板
	 * @param consType
	 * @param gapGradeNo
	 */
	List<?> getTmnlConsTaskByGapGradeNo(String consType, String gapGradeNo);
	
	/**
	 * 数据档案同步
	 * @param consNo
	 * @param consId
	 * @param cpNo
	 * @param tmnlAssetNo
	 * @return
	 */
	int synData(String consNo, String consId, String cpNo, String tmnlId, String tgId, String appNo);
	
	/**
	 * 关口数据档案同步
	 * @param subId
	 * @param tmnlId
	 * @param cpNo
	 * @return
	 */
	int synGateData(String subId, String tmnlId, String cpNo);

	/**
	 * 拆终端
	 * @param tmnlId
	 * @param tmnlAssetNo
	 * @param appNo
	 * @param tgId
	 * @param cpNo
	 */
	void cancelTmnl(String tmnlId, String tmnlAssetNo, String appNo,
			String tgId, String cpNo);
	
	/**
	 * 拆表计
	 * @param meterId
	 */
	public void cancelMeter(String meterId);
	
	/**
	 * 删除关口考核联系表
	 * @param oldMeterId
	 */
	public void deleteCMeterPbsRela(String oldMeterId);
	
	/**
	 * 负控专变增加考核单元
	 * @param newMeterId
	 */
	public void insertGIoMpPerMeterId(String tmnlId, String newMeterId);
	
	/**
	 * 插入关口联系表
	 * @param newMeterId
	 */
	public void insertCMeterPbsRelaPerMeter(String newMeterId);
	
	/**
	 * 删除旧表流入流出数据
	 * @param oldMeterId
	 */
	public void delGIoMp(String oldMeterId);
	
	/**
	 * 更新流入流出数据
	 * @param oldMeterId
	 */
	public void updateGIoMp(String oldMeterId);
	
	/**
	 * 插入流入流出线损
	 * @param oldMeterId
	 * @param newMeterId
	 */
	public void insertGIoMpByMeterId(String tmnlAssetNo, String oldMeterId, String newMeterId);
	
	/**
	 * 根据表ID取得流入流出线损
	 * @return
	 */
	public List<?> getGIoMpByMeterId(String meterId);
	
	/**
	 * 校验是否有重复的终端和电能表的地址
	 * @param tmnlId 终端编号
	 * @param cpNo
	 * @param appNo
	 * @param tmnlTaskType
	 */
	public String checkTmnlMeter(String tmnlId, String cpNo, String appNo, String tmnlTaskType, String flag, String newTmnlId);
	
	/**
	 * 集抄同步数据
	 * @param tmnlId 终端编号
	 * @param cpNo
	 */
	public int synOtherData(String tmnlId, String cpNo, String tgId, String appNo);
	
	/**
	 * 取得webservice连接信息
	 * @param tmnlId 终端编号
	 * @param cpNo
	 */
	public List<?> getWSInfo();

	/**
	 * 取通信规约小项
	 * @param protocolNo 终端编号
	 */
	List<?> getProtItemByProtocolNo(String protocolNo);

	/**
	 * 取集抄设备
	 * @param fmrAssetNo 集抄设备ID
	 */
	List<?> getOtherDev(String fmrAssetNo);

	/**
	 * 取调试信息
	 * @param appNo 申请编号
	 */
	List<?> getITmnlTaskByAppNo(String appNo);
	
	/**
	 * 取调试信息
	 * @param tmnlId 终端ID
	 */
	List<?> getITmnlTaskByTerminalId(String tmnlId);
	
	/**
	 * 更新流程测试状态
	 * @param appNo
	 */
	public void updateItmnlTaskDebugStatus(String appNo);

	/**
	 * 根据tgId从中间库取得单位代码
	 * @param tgId
	 */
	List<?> getPublicTgOrg(String tgId);

	/**
	 * 根据申请编号和调试进度取得调试进度
	 * @param debugProgress 
	 * @param appNo
	 */
	List<?> getITmnlDebug(String appNo, String debugProgress);

	/**
	 * 跟新终端与主站通讯状态
	 * @param debugId 
	 * @param string
	 */
	void updateRTmnlDebugMasterCommFlag(Long debugId, String string);

	/**
	 * 跟新终端与电能表通讯状态
	 * @param debugId 
	 * @param string
	 */
	void updateRTmnlDebugMeterCommFlag(Long debugId, String string);

	/**
	 * 取接口等待时间
	 */
	int getSleepTime();

	/**
	 * 取表变更
	 */
	String getMeterFlagByAppNo(String appNo);

	/**
	 * 取监听时间
	 */
	int getListenTime();
	
	//后台电表注册等待时间
	public int getFrmTmnlTime();
	
	//任务下发等待时间
	public int getFrmTaskTime();

	/**
	 * 插入接口抄表明细
	 */
	void insertIMeterRead(String meterId, String mrFlag, String mrNum);
	
	/**
	 * 取call数量
	 * @return
	 */
	public int getCallNum();

	/**
	 * 获取后台下发TASKID
	 */
	String getBgTaskId();
	
	/**
	 * 增加终端参数
	 * @param tmnlAssetNo
	 */
	public String extraTTmnlParam(String tmnlAssetNo);
	
	/**
	 * 获取INTERFACE_LOG的sequence
	 * @return
	 */
	public String getIinterfaceLogId();
	
	/**
	 * 中断的流程调试
	 * @param appNo
	 * @param tmnlId
	 */
	public void solveHalfOperation(String appNo, String tmnlId, String cpNo);
	
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
			String alarmDesc, String typeCode, String occurTime, String operator, String terminalId);
	
	/**
	 * 插入后台任务主表
	 * @param tbgTask
	 */
	public void addTbgTask(TbgTask tbgTask);
	
	/**
	 * 插入后台任务主表明细表
	 * @param tbgTaskDetail
	 */
	public void addTbgTaskDetail(TbgTaskDetail tbgTaskDetail);

	/**
	 * 根据终端参数状态判断是否下发成功
	 * @param tmnlAssetNo
	 */
	String getStatusByTmnlParamStatue(String tmnlAssetNo);
	
	
	String getStatusByTmnlParamStatueForFrm(String appNo, String tmnlAssetNo, String flowNode);
	/**
	 * 根据终端参数状态判断是否下发成功
	 * @param tmnlAssetNo
	 * @mpSnArrary
	 */
	public String getStatusByTmnlMpSnParamStatue(String appNo, String tmnlAssetNo, String flowNode, String[] mpSnArrary);
	
	/**
	 * 取得电能表通讯规约
	 * @return
	 */
	public String getCommNo(String meterId, String protocolCode);
	
	/**
	 * 根据表ID取得示数位数
	 * @param meterId 
	 */
	public String getMeterDigitsByMeterId(String meterId);
	
	/**
	 * 取得中间库采集对象
	 * @return
	 */
	public String judgePort(String meterId);
	
	
	/**
	 * 根据终端ID取得流程明细 
	 * @param appNo      申请号
	 * @param tmnlId     终端ID
	 * @param flowNode   节点标识
	 * @return
	 */
	public Map<?, ?> getIFlowProcess(String appNo, String tmnlId, String flowNode);
	
	/**
	 * 取得测量点参数主表总数
	 * @param cpNo
	 * @return
	 */
	public int getEDataMpNumByCpNo(String cpNo);
	

	/**
	 * 更新终端流程
	 * @param appNo
	 * @param tmnlAssetNo
	 * @param flowNode
	 * @param flowStatus
	 * @param mpSnList
	 */
	public void updateIFlowProcess(String appNo, String tmnlAssetNo, String flowNode, String flowStatus, String mpSnList, String failCode);

	/**
	 * 更新失败测量点明细
	 * @param appNo
	 * @param terminalId
	 * @param flowNode
	 * @param sendTmnlFailure
	 */
	public void updateUpdateMpSnList(String appNo, String terminalId, String flowNode, String sendTmnlFailure);
	
	/**
	 * 根据终端ID和测量点号取得测量点数据
	 * @param tmnlAssetNo
	 * @param mpSnArrary
	 * @return
	 */
	public List<?> getMpSnsByTmnlSn(String tmnlAssetNo, String[] mpSnArrary);
	
	/**
	 * 取得集抄任务返回状态
	 * @param tmnlAssetNos
	 * @param setParameters
	 * @param params
	 * @param mpSns
	 * @param appNo
	 * @param status
	 * @return
	 */
	public String getTaskStatusByTaskDeal(List<String> tmnlAssetNos,
			short setParameters, List<ParamItem> params, String mpSns);
	
	/**
	 * 验证流程是否全部成功
	 */
	public List<?> checkSuccessOrFaliure(String appNo, String tmnlAssetNo);
	

	/**
	 * 根据tmnlAssetNo取对应的终端参数
	 * @param tmnlAssetNo
	 */
	public int getTmnlParamByTmnlAssetNo(String tmnlAssetNo);
	
	/**
	 * 根据终端资产号和测量点号更新测量点数据状态
	 * @param tmnlAssetNo
	 * @param mpSn 
	 */
	public void updateDataMpIsValid(String tmnlAssetNo, String mpSn);

/**
	 * 更改东软规约参数下发状态
	 * @return
	 */
	public void updateTmnlParamOtherStatue(String tmnlAssetNo, String blockSn,
			String protItemNo);

	/**
	 * 更新运行电能表c_meter中的，注册状态REG_STATUE
	 * @param tmnlAssetNo 
	 * @param mpSn 
	 */
	public void updateMeterRegStatus(String tmnlAssetNo, String mpSn);

	/**
	 * 查询出未注册成功电表
	 * @param tmnlAssetNo 
	 * @param regStatus 
	 */
	public List<?> getMpSnsByTmnlAndStatus(String tmnlAssetNo, String regStatus);
	
	/**
	 * 取得任务号
	 * @param tmnlAssetNo
	 * @param templateId
	 * @return
	 */
	public List<?> getTTmnlTaskByTmnlNoTemplateId(String tmnlAssetNo, String templateId);
	
	/**
	 * 取得任务号
	 * @param tmnlAssetNo
	 * @return
	 */
	public String getTaskNoByTmnlNo(String tmnlAssetNo);

	/**
	 * 更新用户状态：销户
	 * @param consNo
	 * @return
	 */
	public void cancelUser(String consNo);

	/**
	 * 删除群组
	 * @param consNo
	 */
	public void deleteGroupDetail(String consNo);

	/**
	 * 删除用户终端关系
	 * @param consNo
	 * @param tmnlAssetNo
	 */
	public void deleteRconsTmnlRela(String consNo, String tmnlAssetNo);

	/**
	 * 每日取销户信息
	 * @param consNo
	 * @return
	 */
	public List<?> getCancleConsNo();

	/**
	 * 每日取变更申请信息
	 * @param consNo
	 * @return
	 */
	public List<?> getChgAppNo();

	/**
	 * 根据用户去终端信息
	 * @param consNo
	 * @return
	 */
	public List<?> getTmnlByConsNo(String consNo);

	/**
	 * 取采集库连接状态
	 * @return
	 */
	public boolean getDBStatus();

	/**
	 * 取中间库连接状态
	 * @return
	 */
	public boolean getMidDBStatus();

	/**
	 * 根据采集点编号取中间库的采集点对象
	 * @return
	 */
	public List<?> getMidRcollObjByCpNo(String cpNo);

	/**
	 * 更新r_coll_obj
	 * @return
	 */
	public void updateRcollObjByCpNoAndMeterId(String collObjId,
			String meterId, String cpNo);

	/**
	 * 更新e_data_mp
	 * @return
	 */
	public void updateEdataMpByTmnlIdAndMeterId(String collObjId,
			String meterId, String tmnlId);

	/**
	 * 更新WkstAppNo
	 * @return
	 */
	public void updateWkstAppNo(String appNo, String flag);

	/**
	 * 取得最大的测量点号（现有的最大测量点号+1）
	 * @param tmnlAssetNo
	 * @return
	 */
	public String getMaxMpSn(String tmnlAssetNo);

	public void delRConsTmnlRelaByConsNo(String consNo);
	
	public String getConsNoByMeterId(String oldMeterId);
	
}