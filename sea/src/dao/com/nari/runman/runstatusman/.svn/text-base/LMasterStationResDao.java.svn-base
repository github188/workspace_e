package com.nari.runman.runstatusman;

import java.util.Date;
import java.util.List;

import com.nari.fe.commdefine.task.TermTaskInfo;
import com.nari.logofsys.LMasterStationResBean;
import com.nari.util.exception.DBAccessException;


public interface LMasterStationResDao {
	/**
	 * 查询主站运行状态
	 * @param msDate 监控日期
	 * @return 主站运行状态
	 * @throws DBAccessException
	 */
	public List<LMasterStationResBean> findLMasterStationRes(Date msDate,String clusterType)throws DBAccessException;
	
	/**
	 * @desc 查询Web任务 
	 * @return
	 * @throws Exception 
	 */
	public List<Object> findTermTaskInfo() throws Exception;

	
}
