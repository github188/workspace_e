package com.nari.baseapp.datagathorman.impl;

import com.nari.baseapp.datagathorman.IBatchFetchJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.util.exception.DBAccessException;

public class BatchFetchJdbcDaoImpl extends JdbcBaseDAOImpl implements IBatchFetchJdbcDao{
		/*****
		 * 任务的启用
		 * *
		 * @throws DBAccessException ****/
	public void enableTask(String taskId) throws DBAccessException{
		try {
			setTaskEnable(true,taskId);
			
		} catch (Exception e) {
			throw new DBAccessException(e.getMessage());
		}
	}
	/***
	 * 任务的停用
	 * ***/
	public void disableTask(String taskId) throws DBAccessException{
		try {
			setTaskEnable(false,taskId);
		} catch (Exception e) {
			throw new DBAccessException(e.getMessage());
		}
	}
	
	private void setTaskEnable(boolean enable,String taskId){
		String sql=
			"update t_bg_task set task_status = ? where task_id = ?";
		String status=enable?"02":"01";
		getJdbcTemplate().update(sql, new Object[]{
				status,taskId
		});
	}
}
