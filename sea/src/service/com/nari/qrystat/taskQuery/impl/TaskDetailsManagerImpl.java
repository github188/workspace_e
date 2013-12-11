package com.nari.qrystat.taskQuery.impl;

import com.nari.qrystat.taskQuery.TaskDetailsDao;
import com.nari.qrystat.taskQuery.TaskDetailsDto;
import com.nari.qrystat.taskQuery.TaskDetailsManager;
import com.nari.qrystat.taskQuery.TaskStatSearchInfoBean;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 工单明细service
 * 
 * @author ChunMingLi
 * @since  2010-5-13
 *
 */
public class TaskDetailsManagerImpl implements TaskDetailsManager {
	//设置注入DAO
	private TaskDetailsDao taskDetailsDao;

	/*
	 * (non-Javadoc)
	 * @see com.nari.qrystat.taskQuery.TaskDetailsManager#getEventInfo(com.nari.qrystat.taskQuery.TaskStatSearchInfoBean, long, int)
	 */
	@Override
	public Page<TaskDetailsDto> getEventInfo(TaskStatSearchInfoBean eventInfo,
			long start, int limit) throws DBAccessException {
		return this.taskDetailsDao.getEventInfo(eventInfo, start, limit);
	}

	public void setTaskDetailsDao(TaskDetailsDao taskDetailsDao) {
		this.taskDetailsDao = taskDetailsDao;
	}


}
