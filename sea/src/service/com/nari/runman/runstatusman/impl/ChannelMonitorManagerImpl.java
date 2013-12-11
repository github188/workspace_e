package com.nari.runman.runstatusman.impl;


import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.runman.runstatusman.ChannelMonitorConsTypeCollMode;
import com.nari.runman.runstatusman.ChannelMonitorDto;
import com.nari.runman.runstatusman.ChannelMonitorOrgNoDto;
import com.nari.runman.runstatusman.ChannelMonitorTerminalDto;
import com.nari.runman.runstatusman.ChannelMonitorDao;
import com.nari.runman.runstatusman.ChannelMonitorManager;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 * 
 * 通信信道监测 service
 * 
 * @author ChunMingLi
 * 2010-5-1
 *
 */
public class ChannelMonitorManagerImpl implements ChannelMonitorManager {
	//ChannelMonitorDao
	private ChannelMonitorDao channelMonitorDao;

	/*
	 * (non-Javadoc)
	 * @see com.nari.runman.runstatusman.ChannelMonitorManager#findChannelMonitorFacturerBean()
	 */
	@Override
	public Page<ChannelMonitorTerminalDto> findChannelMonitorFacturerBean(PSysUser pSysUser,long start,long limit)throws Exception {
		try {
			return this.channelMonitorDao.findChannelMonitorFacturerBean(pSysUser, start, limit);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("通信信道监测厂商查询分析SQL查询操作出错");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.nari.runman.runstatusman.ChannelMonitorManager#findChannelMonitorOrgNoBean()
	 */
	@Override
	public Page<ChannelMonitorOrgNoDto> findChannelMonitorOrgNoBean(PSysUser pSysUser,long start,long limit)throws Exception {
		try {
			return this.channelMonitorDao.findChannelMonitorOrgNoBean(pSysUser, start, limit);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("通信信道监测单位查询分析SQL查询操作出错");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.nari.runman.runstatusman.ChannelMonitorManager#findChannelMonitorBean(java.lang.String, java.lang.String)
	 */
	public Page<ChannelMonitorDto> findChannelMonitorBean(String onlineType , String statisticsType, String statisticFlag, long start, int limit ,PSysUser pSysUser)throws Exception {
		try {
		return this.channelMonitorDao.findChannelMonitor(onlineType, statisticsType, statisticFlag,  start,  limit , pSysUser);
	} catch (DataAccessException dbe) {
		throw new DBAccessException(BaseException.processDBException(dbe));
	} catch (Exception e) {
		throw new ServiceException("通信信道监测查询分析SQL查询操作出错");
	}
	}


	public void setChannelMonitorDao(ChannelMonitorDao channelMonitorDao) {
		this.channelMonitorDao = channelMonitorDao;
	}

	@Override
	public Page<ChannelMonitorConsTypeCollMode> findChannelMonitorCollectMode(
			PSysUser pSysUser, long start, long limit) throws Exception {
		try {
			return this.channelMonitorDao.findChannelMonitorCollectMode(pSysUser, start, limit);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("通信信道监测单位查询分析SQL查询操作出错");
		}
	}

	@Override
	public Page<ChannelMonitorConsTypeCollMode> findChannelMonitorConsType(
			PSysUser pSysUser, long start, long limit) throws Exception {
		try {
			return this.channelMonitorDao.findChannelMonitorConsType(pSysUser, start, limit);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("通信信道监测单位查询分析SQL查询操作出错");
		}
	}

}
