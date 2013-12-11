package com.nari.baseapp.interfacemanage;


import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 接口监测dao接口
 * 
 * @author ChunMingLi
 * @since  2010-6-22
 *
 */
public interface InterfaceMonitorDao {
	/**
	 * 
	 * 查询接口监视日志
	 * 
	 * @param monitorBean  	查询条件bean
	 * @param userInfo	   	操作员信息
	 * @param start		 	开始页码
	 * @param limit			显示数量
	 * @return
	 */
	public Page<InterfaceMonitorDto> findInterfaceList(InterfaceMonitorBean monitorBean ,PSysUser userInfo, long start, long limit);

}
