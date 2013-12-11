package com.nari.runman.runstatusman.impl;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.runman.runstatusman.OnOffLineMonitorDto;
import com.nari.runman.runstatusman.OnlineMonitorDao;
import com.nari.runman.runstatusman.OnlineMonitorManager;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

public class OnlineMonitorManagerImpl implements OnlineMonitorManager {
	private Logger logger = Logger.getLogger(this.getClass());
	private OnlineMonitorDao onlineMonitorDao;
	
	public void setOnlineMonitorDao(OnlineMonitorDao onlineMonitorDao) {
		this.onlineMonitorDao = onlineMonitorDao;
	}

	@Override
	public Page<OnOffLineMonitorDto> findPSysUsers(long start, long limit)
			throws Exception {
		
		try {
			return this.onlineMonitorDao.findPSysUsers(start, limit);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("在线状态监测查询SQL查询操作出错");
		}
		
	}



}
