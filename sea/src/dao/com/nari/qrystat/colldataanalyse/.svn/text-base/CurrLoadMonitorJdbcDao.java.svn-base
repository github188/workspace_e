package com.nari.qrystat.colldataanalyse;

import java.util.List;

import com.nari.privilige.PSysUser;

/**
 * 日用电负荷监测Jdbc之Dao接口
 * @author 姜炜超
 */
public interface CurrLoadMonitorJdbcDao {
	/**
	 * 根据条件查询实时日用电负荷监测信息
	 * @param orgNo
	 * @param orgType
	 * @param queryDate
	 * @param pSysUser
	 * @return Page<CurrLoadMonitorDto>
	 */
	public List<CurrLoadMonitorCurveBean> findCLMInfoByCond(String orgNo, String orgType, String queryDate, PSysUser pSysUser);
	
	/**
	 * 根据条件查询冻结日用电负荷监测信息
	 * @param orgNo
	 * @param orgType
	 * @param queryDate
	 * @param pSysUser
	 * @return Page<AOrgLoadStatDto>
	 */
	public List<AOrgLoadStatDto> findCLMLSTATInfoByCond(String orgNo, String orgType, String queryDate, PSysUser pSysUser);
	
	
	/**
	 * 根据供电单位查询该单位及子单位信息
	 * @param orgNo
	 * @param orgType
	 * @param pSysUser
	 * @return List<CurrLoadMonitorDto>
	 */
	public List<CurrLoadMonitorDto> findOrgInfo(String orgNo, String orgType, PSysUser pSysUser);
}
