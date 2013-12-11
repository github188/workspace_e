package com.nari.baseapp.datagathorman.impl;

import java.util.Date;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.datagatherman.TaskStatInfoBean;
import com.nari.baseapp.datagathorman.GatherTaskQueryDao;
import com.nari.baseapp.datagathorman.GatherTaskQueryManager;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

public class GatherTaskQueryManagerImpl implements GatherTaskQueryManager {

	private GatherTaskQueryDao gatherTaskQueryDao;

	public void setGatherTaskQueryDao(GatherTaskQueryDao gatherTaskQueryDao) {
		this.gatherTaskQueryDao = gatherTaskQueryDao;
	}

	@Override
	public Page<TaskStatInfoBean> queryGatherTaskStat(int taskType,
			String nodeType, String nodeValue, Date startTime, Date endTime,
			PSysUser userInfo, long start, long limit) throws Exception {
		try {
			return this.gatherTaskQueryDao.queryGatherTaskStat(taskType, nodeType,
					nodeValue, startTime, endTime, userInfo, start, limit);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("采集任务执行情况查询出错");
		}
	}

}
