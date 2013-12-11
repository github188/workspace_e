package com.nari.qrystat.colldataanalyse;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 用电客户排名分析Dao接口
 * @author 姜炜超
 */
public interface PowerSortAnalyseJdbcDao {
	/**
	 * 根据条件查询用电客户排名分析信息
	 * @param nodeId
	 * @param nodeType
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @param compStartDate
	 * @param compEndDate
	 * @param sort
	 * @param start
	 * @param limit
	 * @return Page<PowerSortAnalyseDto>
	 */
	public Page<PowerSortAnalyseDto> findPSAInfoByCond(String nodeId, String nodeType,
			String orgType, String startDate, String endDate, String compStartDate, 
			String compEndDate, int sort,long start, int limit, PSysUser user);
	
	/**
	 * 根据条件查询某用电单位某时期所有用户的总耗电量
	 * @param nodeId
	 * @param nodeType
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @return Double
	 */
	public Double queryPSATotalPower(String nodeId, String nodeType,
			String orgType, String startDate, String endDate, PSysUser user);
	
	/**
	 * 根据条件查询某用电单位某时期排名前n位用户的总耗电量
	 * @param nodeId
	 * @param nodeType
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @param sort
	 * @return Double
	 */
	public Double queryPSASortPower(String nodeId, String nodeType,
			String orgType, String startDate, String endDate, int sort,PSysUser user);
}
