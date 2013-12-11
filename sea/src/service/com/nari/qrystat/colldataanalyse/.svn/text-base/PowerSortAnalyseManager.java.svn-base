package com.nari.qrystat.colldataanalyse;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 用电客户排名分析Service接口
 * @author 姜炜超
 */
public interface PowerSortAnalyseManager {
	/**
	 * 根据条件查询用电客户排名分析信息，目前本期只考虑一个时间段，不单独考虑月，周
	 * @param nodeId 节点id
	 * @param nodeType 节点类型
	 * @param orgType 如果节点是供电单位，显示供电单位类型
	 * @param startDate
	 * @param endDate
	 * @param sort
	 * @param start
	 * @param limit
	 * @param user
	 * @return Page<PowerSortAnalyseDto>
	 */
	public Page<PowerSortAnalyseDto> findPSAInfoByCond(String nodeId, String nodeType, String orgType, String startDate,
			String endDate, int sort,long start, int limit, PSysUser user) throws Exception;
	
	/**
	 * 根据条件查询某用电单位某时间段所有用户的总耗电量
	 * @param nodeId
	 * @param nodeType
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @return Double
	 */
	public Double queryPSATotalPower(String nodeId, String nodeType, String orgType,
			String startDate, String endDate, PSysUser user) throws Exception;
	
	/**
	 * 根据条件查询某用电单位某时间段排名前n位用户的总耗电量
	 * @param nodeId
	 * @param nodeType
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @param sort
	 * @param user
	 * @return Double
	 */
	public Double queryPSASortPower(String nodeId, String nodeType, String orgType,
			String startDate, String endDate, int sort,PSysUser user) throws Exception;
}