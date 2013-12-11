package com.nari.baseapp.interfacemanage.impl;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.interfacemanage.InterfaceMonitorBean;
import com.nari.baseapp.interfacemanage.InterfaceMonitorDao;
import com.nari.baseapp.interfacemanage.InterfaceMonitorDto;
import com.nari.baseapp.interfacemanage.InterfaceMonitorManager;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 
 * @author ChunMingLi
 * @since  2010-6-28
 *
 */
public class InterfaceMonitorManagerImpl implements InterfaceMonitorManager {
	//设置注入DAO
	private InterfaceMonitorDao interfaceMonitorDao;
	
	/**
	 *  the interfaceMonitorDao to set
	 * @param interfaceMonitorDao the interfaceMonitorDao to set
	 */
	public void setInterfaceMonitorDao(InterfaceMonitorDao interfaceMonitorDao) {
		this.interfaceMonitorDao = interfaceMonitorDao;
	}

	@Override
	public Page<InterfaceMonitorDto> queryInterfaceList(
			InterfaceMonitorBean monitorBean, PSysUser userInfo, long start,
			long limit)  throws DBAccessException{
		try {
			return interfaceMonitorDao.findInterfaceList(monitorBean, userInfo, start, limit);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

}
