package com.nari.baseapp.datagathorman;

import com.nari.util.exception.DBAccessException;

public interface IBatchFetchJdbcDao  {
	/*****
	 * 任务的启用
	 * @param taskId 任务的id
	 * *
	 * @throws DBAccessException ****/
	void enableTask(String taskId) throws DBAccessException;
	/***
	 * 任务的停用
	 * @param  taskId 任务的id
	 * ***/
	void disableTask(String taskId) throws DBAccessException;

}
