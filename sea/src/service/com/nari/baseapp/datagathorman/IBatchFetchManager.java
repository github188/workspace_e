package com.nari.baseapp.datagathorman;

import java.util.List;
import java.util.Map;

import com.nari.baseapp.datagatherman.TbgTask;
import com.nari.baseapp.datagatherman.TbgTaskDetail;
import com.nari.baseapp.datagatherman.TbgTaskFinder;
import com.nari.baseapp.datagatherman.TbgTaskStatisticsFinder;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

public interface IBatchFetchManager {
	/***得到一个召测组合的所有的小项
	 * @param user 登陆用户
	 * @param combiId 召测组合的id
	 * @param 返回一个召测组合所含有的召测组合项
	 * *****/
	List<Map> findComItem(PSysUser user, String combiId);
	/***得到一个用户的所有的召测组合
	 * @param user 登陆用户
	 * @param type 召测组合的类别
	 * @return 返回所有的召测组合
	 * ****/
	List<Map> findAllCombi(PSysUser user, String type);
	/***通过用户来找到其所对应的召测组合的名字****/
	String findCombiNameById(PSysUser user, String combiId);
	List<String> findAndGetFather(PSysUser user, String combiId)
			throws ServiceException;
	/****
	 * 通过必要的数据来更新两个表t_bg_task和t_bg_task_detail
	 * @param tt 后台任务主表
	 * @param ttds 后台任务从表
	 * @return
	 * ****/
	
	void addBgTask(List<TbgTask> tts, List<TbgTaskDetail> ttds);
	Page<Map> findTbgTask(PSysUser user, long start, int limit,TbgTaskFinder finder)
			throws ServiceException;
	
	/*****查询后台任务的结果
	 * @param user 登陆用户  以后备用
	 * @param taskId  任务id
	 * @return 查找后来的结果
	 * @throws ServiceException ******/
	@SuppressWarnings("unchecked")
	List<Map> findTbgTaskResult(PSysUser user, String taskId)
			throws ServiceException;
	/***
	 * 任务的停用
	 * @param taskId 任务id
	 * *
	 * @throws ServiceException **/
	void disableTask(String taskId) throws ServiceException;
	/*****
	 * 任务的启用
	 * * @param taskId 任务id
	 * @throws ServiceException 
	 * @throws DBAccessException ****/
	void enableTask(String taskId) throws ServiceException;
	
	/**
	 * 查找 后台任务的统计相关的信息
	 * @param user
	 * @param start
	 * @param limit
	 * @param finder
	 * @return
	 * @throws ServiceException
	 */
	Page<Map> findStatistics(PSysUser user, long start, int limit,
			TbgTaskStatisticsFinder finder) throws ServiceException;
	

}
