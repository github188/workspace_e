package com.nari.qrystat.colldataanalyse;

import java.util.Date;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 
 * 线路电量查询service接口
 * @author ChunMingLi
 * @since  2010-6-23
 *
 */
public interface LineEceltricQuantityManager {

	/**
	 * 左边树加载线路电量
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
	public Page<LineEceltricQuantityDto> queryPageLineEcel(String nodeType,
			String nodeValue, PSysUser userInfo, long start, long limit,
			Date startDate, Date endDate)throws Exception;
	/**
	 * 
	 * 线路电量用户查询
	 * 
	 * @param lineID
	 * 			  线路ID
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
	public Page<LineEcelUserDto> queryPageLineEcelUser(String lineId,
			 PSysUser userInfo, long start, long limit,
			Date startDate, Date endDate)throws Exception;
}
