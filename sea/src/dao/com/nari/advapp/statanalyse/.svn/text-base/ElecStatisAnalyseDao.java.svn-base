/**
 * 
 */
package com.nari.advapp.statanalyse;

import java.util.Date;
import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 
 * 负荷统计分析DAO
 * @author ChunMingLi
 * @since 2010-8-7
 *
 */
public interface ElecStatisAnalyseDao {
	/**
	 * 查询负荷统计分析
	 * 
	 * @param nodeType
	 *            节点类型
	 * @param nodeValue
	 *            节点值
	 * @param userInfo
	 *            用户信息
	 * @param start
	 *            页开始数量
	 * @param limit
	 *            页限制数量
	 * @param startDate
	 *            查询开始时间
	 * @param endDate
	 *            查询结束时间
	 * @return page 
	 * @throws Exception
	 */
	public List<StatAnalyseCurveDto> findElecStatisAnalyseCurve(int elecType,int timeFlag,String orgType,
			String nodeType, String nodeValue, PSysUser userInfo, String startDate, String endDate) throws Exception;

	/**
	 * 比对查询负荷统计分析
	 * 
	 * @param nodeType
	 *            节点类型
	 * @param nodeValue
	 *            节点值
	 * @param userInfo
	 *            用户信息
	 * @param start
	 *            页开始数量
	 * @param limit
	 *            页限制数量
	 * @param startDate
	 *            查询开始时间
	 * @param endDate
	 *            查询结束时间
	 * @param contraStartDate
	 * 			      查询比对开始时间
	 * @param contraEndDate
	 * 			      查询比对结束时间
	 * @return  page
	 * 
	 * @throws Exception
	 */
	public Page<ChargeStatisAnalyseDTO> findElecStatisAnalyseList(int timeFlag,String orgType, String nodeType, String nodeValue, PSysUser userInfo, long start,
			long limit, String startDate, String endDate) throws Exception;

}
