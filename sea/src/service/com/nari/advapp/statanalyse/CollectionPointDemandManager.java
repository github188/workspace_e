package com.nari.advapp.statanalyse;

import java.util.Date;

import com.nari.advapp.statanalyse.CollectionPointDemandDto;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 
 * 测量点需量分析 manager
 * @author ChunMingLi
 * @since  2010-7-27
 *
 */
public interface CollectionPointDemandManager {

	/**
	 * 查询按日测量点需量分析
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
	public Page<CollectionPointDemandDto> queryCollectionPointDemandDate(String orgType, String nodeType,
			String nodeValue, PSysUser userInfo, long start, long limit,
			Date startDate, Date endDate) throws Exception;
	/**
	 * 查询按月测量点需量分析
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
	public Page<CollectionPointDemandDto> queryCollectionPointDemandMonth(String orgType,String nodeType,
			String nodeValue, PSysUser userInfo, long start, long limit,
			Date startDate, Date endDate) throws Exception;
}
