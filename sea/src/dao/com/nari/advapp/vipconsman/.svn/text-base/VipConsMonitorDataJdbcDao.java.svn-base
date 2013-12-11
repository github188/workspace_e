package com.nari.advapp.vipconsman;
import java.util.List;

import com.nari.elementsdata.EDataMp;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.EMpEnergyCurveBean;
import com.nari.qrystat.colldataanalyse.EMpPowerCurveBean;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 重点用户监测数据查询Jdbc之Dao接口
 * @author 姜炜超
 */
public interface VipConsMonitorDataJdbcDao {
	/**
	 * 根据条件查询重点用户信息
	 * @param pSysUser
	 * @param start
	 * @param limit
	 * @return Page<VipConsMonitorDataBean>
	 */
	public Page<VipConsMonitorDataBean> findVipConsMonitorData(PSysUser pSysUser, long start, int limit) throws DBAccessException;
	
	/**
	 * 查询某用户下的表计信息
	 * @param consNo
	 * @return List<EDataMp>
	 */
	public List<EDataMp> findConsAssetInfo(String consNo) throws DBAccessException;
	
	/**
	 * 查询某用户下的表计对应的测量点，目前青海只考虑485口
	 * @param consNo
	 * @param assetNo
	 * @param dataSrc
	 * @return mpId
	 */
	public Long findMpId(String consNo, String assetNo, int dataSrc) throws DBAccessException;
	
	/**
	 * 按测量点ID和日期查询日测量点功率曲线(冻结数据)
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return EMpPowerCurveBean
	 */
	public EMpPowerCurveBean findEMpPowerCurveByDate(Long mpId, String dataDate, int dataType, String consNo) throws DBAccessException;
	
	/**
	 * 按测量点ID和日期查询日测量点电能量曲线(冻结数据)
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return EMpEnergyCurveBean
	 */
	public EMpEnergyCurveBean findEMpEnergyCurveByDate(Long mpId, String dataDate, int dataType, String consNo) throws DBAccessException;
	
	/**
	 * 按测量点ID和日期查询日测量点实时功率曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @return List<VipConsRealPowerDataBean>
	 */
	public List<VipConsRealPowerDataBean> findEMpRealPowerCurveByDate(Long mpId, String dataDate, String consNo) throws DBAccessException;
	
	/**
	 * 查询表计查询其挂名下的终端的冻结周期
	 * @param assetNo
	 * @return freezeCycleNum
	 */
	public int findFreezeCycleNum(String assetNo) throws DBAccessException;
}
