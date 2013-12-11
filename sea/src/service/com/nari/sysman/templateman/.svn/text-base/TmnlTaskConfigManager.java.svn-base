package com.nari.sysman.templateman;

import java.util.List;

import com.nari.support.Page;
import com.nari.terminalparam.ITmnlProtSentSetupBean;
import com.nari.terminalparam.ITmnlTaskSetupBean;
import com.nari.terminalparam.TTaskTemplateBean;
import com.nari.util.exception.DBAccessException;

public interface TmnlTaskConfigManager {
	/**
	 * @param start
	 * @param limit
	 * @return 终端任务上送方式配置
	 * @throws DBAccessException
	 */
	public Page<ITmnlProtSentSetupBean> findTmnlTask(long start, int limit)
			throws DBAccessException;
	/**
	 * @param start
	 * @param limit
	 * @return 用户与终端任务配置
	 * @throws DBAccessException
	 */
	public Page<ITmnlTaskSetupBean> findUserTask(long start, int limit)
			throws DBAccessException;
	
	
	/**
	 * @return 终端任务上送方式--增加、保存规则按钮
	 * @throws DBAccessException
	 */
	public String saveOrUpdate(ITmnlProtSentSetupBean bean) throws DBAccessException;
	
	/**
	 * @return 用户与终端任务配置  新增规则、保存规则
	 */
	public String saveOrUpdate_1(ITmnlTaskSetupBean beanUser) throws DBAccessException;
	
	
	/**
	 * @param protSendSetupId
	 * @return 终端任务上送方式--删除规则按钮
	 * @throws DBAccessException
	 */
	public void deleteRule(String protSendSetupId) throws DBAccessException;
	
	
	/**
	 * @param protSendSetupId
	 * @return 用户与终端任务配置--删除规则按钮
	 * @throws DBAccessException
	 */
	public void deleteRuleH(String taskSetupId) throws DBAccessException;
	
	
	/**
	 * @param start
	 * @param limit
	 * @return 终端任务模板
	 * @throws DBAccessException
	 */
	public Page<TTaskTemplateBean> findTTaskT(long start, int limit) throws DBAccessException;
	
	
	/**
	 * @return 终端厂家
	 */
	public List<ITmnlProtSentSetupBean> findTmnlFactory() throws DBAccessException;
	
	/**
	 * @return 终端型号
	 */
	public List<ITmnlProtSentSetupBean> findTmnlModel() throws DBAccessException;
	
	/**
	 * @return 终端类型
	 */
	public List<ITmnlProtSentSetupBean> findTmnlType() throws DBAccessException;
	
	/**
	 * @return 采集方式
	 */
	public List<ITmnlProtSentSetupBean> findCollMode() throws DBAccessException;
	
//	/**
//	 * @return 上送方式
//	 */
//	public List<ITmnlProtSentSetupBean> findSendUp() throws DBAccessException;
	
	/**
	 * @return 通讯规约
	 */
	public List<ITmnlProtSentSetupBean> findProtocol() throws DBAccessException;
	
	/**
	 * @return 用户类型
	 */
	public List<ITmnlTaskSetupBean> findConsType() throws DBAccessException;
	
	/**
	 * @return 容量等级
	 */
	public List<ITmnlTaskSetupBean> findCapGrade() throws DBAccessException;
	
	
	
	
}
