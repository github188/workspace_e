package com.nari.runman.abnormalhandle;

import java.util.Date;
import java.util.List;

import com.nari.flowhandle.DeviceExceptionBean;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface DeviceExceptionDao {
	/**
	 * 主站分析异常查询
	 * 
	 * @param infoBean
	 *            查询条件
	 * @return 主站分析异常信息
	 * @throws DBAccessException
	 */
	public Page<DeviceExceptionBean> queryTmnlException(
			DeviceExceptionBean infoBean) throws DBAccessException;

	/**
	 * 主站分析异常查询
	 * 
	 * @param tmnlExceptionId
	 *            主站异常ID
	 * @return 主站分析异常信息
	 * @throws DBAccessException
	 */
	public List<DeviceExceptionBean> queryTmnlException(String tmnlExceptionId)
			throws DBAccessException;

	/**
	 * 左边树加载用户异常
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
	 * @return
	 * @throws DBAccessException
	 */
	public Page<DeviceExceptionBean> queryPageTmnlException(String nodeType,
			String nodeValue, PSysUser userInfo, long start, long limit,
			Date startDate, Date endDate) throws DBAccessException;
	
	/**
	 * 更新已处理异常的状态
	 * @param excetpionId 主站分析异常id
	 * @param stauts 异常处理状态
	 * @throws DBAccessException
	 */
	public void updateFlowStatusByExId(String excetpionId, String stauts) throws DBAccessException;
}
