package com.nari.advapp.vipconsman.impl;

import java.util.Date;
import java.util.List;

import com.nari.advapp.vipconsman.LogRelease;
import com.nari.advapp.vipconsman.LogReleaseQueryDao;
import com.nari.advapp.vipconsman.LogReleaseQueryManager;
import com.nari.privilige.PSysUser;
import common.Logger;

public class LogReleaseQueryManagerImpl implements LogReleaseQueryManager {
	private Logger log=Logger.getLogger(LogReleaseQueryManager.class);
	private LogReleaseQueryDao logReleaseQueryDao;
	
	public LogReleaseQueryDao getLogReleaseQueryDao() {
		return logReleaseQueryDao;
	}

	public void setLogReleaseQueryDao(LogReleaseQueryDao logReleaseQueryDao) {
		this.logReleaseQueryDao = logReleaseQueryDao;
	}

	/**
	 * @description 查询并统计日志发布结果
	 * @param pubDate,pSysUser
	 * @return logReleaseList
	 */
	public List<LogRelease> queryLogStastics(Date pubDate, PSysUser pSysUser)
	{
		List<LogRelease> lrList=null;
		try{
		lrList=logReleaseQueryDao.queryLogStastics(pubDate,pSysUser);
		}catch(Exception e)
		{
			log.debug("Services层查询日志出现错误");			
			e.printStackTrace();
		}
		return lrList;
	}
}
