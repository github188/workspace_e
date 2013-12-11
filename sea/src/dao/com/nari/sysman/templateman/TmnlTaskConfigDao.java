package com.nari.sysman.templateman;

import java.util.List;

import com.nari.support.Page;
import com.nari.terminalparam.ITmnlProtSentSetupBean;
import com.nari.terminalparam.ITmnlTaskSetupBean;
import com.nari.terminalparam.TTaskTemplateBean;

/**
 * 接口 TmnlTaskConfigDao
 * 
 * @author mengyuan
 */
public interface TmnlTaskConfigDao {
	/**
	 * @return 终端任务上送方式配置 新增规则、保存规则
	 */
	public String saveOrUpdate(ITmnlProtSentSetupBean bean);
	
	/**
	 * @return 用户与终端任务配置  新增规则、保存规则
	 */
	public String saveOrUpdate_1(ITmnlTaskSetupBean beanUser);
	
	
	/**
	 * @param protSendSetupId
	 * @return 终端任务上送方式配置 删除规则
	 */
	public void deleteRule(String protSendSetupId);
	
	/**
	 * @param protSendSetupId
	 * @return 用户与终端任务配置 删除规则
	 */
	public void deleteRuleH(String taskSetupId);
	
	
	/**
	 * @return 终端任务上送方式配置grid展示
	 */
	public Page<ITmnlProtSentSetupBean> findTmnlTask(long start, int limit);

	/**
	 * @return 用户与终端任务配置grid展示
	 */
	public Page<ITmnlTaskSetupBean> findUserTask(long start, int limit);

	/**
	 * @return 终端任务模板
	 */
	public Page<TTaskTemplateBean> findTTaskT(long start, int limit);

	/**
	 * @return 终端厂家
	 */
	public List<ITmnlProtSentSetupBean> findTmnlFactory();

	/**
	 * @return 终端型号
	 */
	public List<ITmnlProtSentSetupBean> findTmnlModel();

	/**
	 * @return 终端类型
	 */
	public List<ITmnlProtSentSetupBean> findTmnlType();

	/**
	 * @return 采集方式
	 */
	public List<ITmnlProtSentSetupBean> findCollMode();

//	/**
//	 * @return 上送方式
//	 */
//	public List<ITmnlProtSentSetupBean> findSendUp();

	/**
	 * @return 通讯规约
	 */
	public List<ITmnlProtSentSetupBean> findProtocol();

	/**
	 * @return 用户类型
	 */
	public List<ITmnlTaskSetupBean> findConsType();

	/**
	 * @return 容量等级
	 */
	public List<ITmnlTaskSetupBean> findCapGrade();
}
