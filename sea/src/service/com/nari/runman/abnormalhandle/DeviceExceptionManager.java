package com.nari.runman.abnormalhandle;

import java.util.Date;
import java.util.List;

import com.nari.flowhandle.DeviceExceptionBean;
import com.nari.flowhandle.FEventSrc;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

public interface DeviceExceptionManager {

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
	 * @param startDate 查询开始时间
	 * @param endDate 查询结束时间
	 * @return 主站异常信息
	 * @throws Exception
	 */
	public Page<DeviceExceptionBean> queryTmnlException(String nodeType,
			String nodeValue, PSysUser userInfo, long start, long limit,
			Date startDate, Date endDate) throws Exception;

	/**
	 * 主站分析异常查询
	 * 
	 * @param tmnlExceptionId
	 *            主站异常ID
	 * @return 主站分析异常信息
	 * @throws Exception
	 */
	public List<DeviceExceptionBean> queryDeviceException(String tmnlExceptionId)
			throws Exception;
	
	public long createEventNo(FEventSrc fEventSrc) throws Exception;
}
