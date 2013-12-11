package com.nari.qrystat.colldataanalyse;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 地区电量分布查询Jdbc之Dao接口
 * @author 姜炜超
 */
public interface AreaPowerDistJdbcDao {
	/**
	 * 根据条件查询地区电量分布信息
	 * @param pSysUser
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @param start
	 * @param limit
	 * @return Page<AreaPowerDistDto>
	 */
	public Page<AreaPowerDistDto> findAPDInfoByCond(PSysUser pSysUser, String orgType, String startDate, String endDate,long start, int limit);
	
	/**
	 * 根据供电单位查询该单位类型
	 * @param orgNo
	 * @return String
	 */
	public String findOrgType(String orgNo);
}
