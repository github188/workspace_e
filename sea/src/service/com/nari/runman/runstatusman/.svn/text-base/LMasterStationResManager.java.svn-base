package com.nari.runman.runstatusman;

import java.util.Date;
import java.util.List;

import com.nari.fe.commdefine.task.TermTaskInfo;
import com.nari.logofsys.LMasterStationResBean;
import com.nari.util.exception.DBAccessException;

public interface LMasterStationResManager {
	
	
	/**
	 * 
	 * @param msDate
	 * @return 网关机
	 * @throws DBAccessException
	 */
	public List<LMasterStationResBean> findLMasterStationResWgj(Date msDate)
			throws DBAccessException;
	/**
	 * 
	 * @param msDate
	 * @return 采集机
	 * @throws DBAccessException
	 */
	public List<LMasterStationResBean> findLMasterStationResCjj(Date msDate)
			throws DBAccessException;
	/**
	 * 
	 * @param msDate
	 * @return web
	 * @throws DBAccessException
	 */
	public List<LMasterStationResBean> findLMasterStationResWeb(Date msDate)
			throws DBAccessException;
	/**
	 * 
	 * @param msDate
	 * @return 接口服务
	 * @throws DBAccessException
	 */
	public List<LMasterStationResBean> findLMasterStationResJkfw(Date msDate)
	throws DBAccessException;
	/**
	 * 
	 * @param msDate
	 * @return 数据库
	 * @throws DBAccessException
	 */
	public List<LMasterStationResBean> findLMasterStationResSjk(Date msDate)
			throws DBAccessException;
	
	
	/**
	 * @desc 查询web任务
	 * @return
	 */
	public List <Object> getTermTaskInfo();
}
