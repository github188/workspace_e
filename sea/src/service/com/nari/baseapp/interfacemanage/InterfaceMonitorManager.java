package com.nari.baseapp.interfacemanage;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 
 * @author ChunMingLi
 * @since 2010-6-28
 * 
 */
public interface InterfaceMonitorManager {
	/**
	 * 
	 * 查询接口监视日志
	 * 
	 * @param monitorBean
	 *            查询条件bean
	 * @param userInfo
	 *            操作员信息
	 * @param start
	 *            开始页码
	 * @param limit
	 *            显示数量
	 * @return
	 * @throws DBAccessException
	 */
	public Page<InterfaceMonitorDto> queryInterfaceList(
			InterfaceMonitorBean monitorBean, PSysUser userInfo, long start,
			long limit) throws DBAccessException;

}
