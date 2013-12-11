package com.nari.sysman.templateman;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.support.Page;
import com.nari.terminalparam.ITmnlProtSentSetupBean;
import com.nari.terminalparam.ITmnlTaskSetupBean;
import com.nari.terminalparam.TTaskTemplateBean;

public class TmnlTaskConfigAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());

	private TmnlTaskConfigManager tmnlTaskConfigManager;

	private List<ITmnlProtSentSetupBean> iTmnlProtSentSetupBeanList;
	private List<ITmnlTaskSetupBean> iTmnlTaskSetupBeanList;
	private List<TTaskTemplateBean> tTaskTemplateBeanList;

	private boolean success = true;
	private long totalCount;

	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	
	//前台参数
	private String protSendSetupId;//规约及上送方式ID 	唯一标识
	private String taskSetupId;//终端任务配置ID       唯一标识
	private String factoryName;//生产厂家
	private String modeName;// 终端型号
	private String tmnlType;// 终端类型
	private String protocolName;// 通讯规约
	private String commMode;// 采集方式
	private String tmnlAddrS;//终端地址从
	private String tmnlAddrE;//终端地址到
	private String sendUpMode;// 任务上送方式
	private String attachMeterFlag;// 是否网络表
	private String templateIdList;//任务模板列表
	private String consTypeName;//用户类型
	private String capGradeName;//容量等级
	
	private String tmnlResult;//终端页面返回显示---
	private ITmnlProtSentSetupBean bean;//终端任务上送方式----
	private ITmnlTaskSetupBean beanUser;//用户与终端任务配置----
	

	//用于combox中增加空记录
	//private ITmnlProtSentSetupBean tmnlFactory;//终端厂家
	private ITmnlProtSentSetupBean tmnlXh;//终端型号
	private ITmnlProtSentSetupBean tmnlLx;//终端类型
	private ITmnlProtSentSetupBean cjfs;//采集方式
	private ITmnlProtSentSetupBean ssfs;//上送方式
	private ITmnlProtSentSetupBean txgy;//通讯规约
	private ITmnlTaskSetupBean yhlx;//用户类型
	private ITmnlTaskSetupBean rldj;//容量等级
	
	/**
	 * @return 终端任务上送方式配置--保存规则按钮
	 * @throws Exception
	 */
	public String saveOrUpdateInfo() throws Exception{
		tmnlResult = tmnlTaskConfigManager.saveOrUpdate(bean);
		return SUCCESS;
	}
	
	/**
	 * @return 用户与终端终端任务配置--保存规则按钮
	 * @throws Exception
	 */
	public String saveOrUpdateInfo_1() throws Exception{
		tmnlResult = tmnlTaskConfigManager.saveOrUpdate_1(beanUser);
		return SUCCESS;
	}
	
	
	/**
	 * @return 终端任务上送方式配置--删除规则按钮
	 * @throws Exception
	 */
	public String deleteTmnlTaskSend()throws Exception{
		tmnlTaskConfigManager.deleteRule(this.getProtSendSetupId());
		return SUCCESS;
	}
	
	/**
	 * @return 用户与终端终端任务配置--删除规则按钮
	 * @throws Exception
	 */
	public String deleteUserSend()throws Exception{
		tmnlTaskConfigManager.deleteRuleH(this.getTaskSetupId());
		return SUCCESS;
	}
	
	/**
	 * 终端厂家
	 */
	public String queryTmnlFactory() throws Exception {
		iTmnlProtSentSetupBeanList = new ArrayList<ITmnlProtSentSetupBean>();
		ITmnlProtSentSetupBean tmnlFactory = new ITmnlProtSentSetupBean();//用于combo增加记录  例
		tmnlFactory.setFactoryCode("");
		tmnlFactory.setFactoryName("--请选择--");
		iTmnlProtSentSetupBeanList.add(tmnlFactory);
		iTmnlProtSentSetupBeanList.addAll(tmnlTaskConfigManager.findTmnlFactory());
//		iTmnlProtSentSetupBeanList = tmnlTaskConfigManager.findTmnlFactory();
		return SUCCESS;
	}
	
	/**
	 * 终端型号
	 */
	public String queryTmnlMode() throws Exception {
		iTmnlProtSentSetupBeanList = new ArrayList<ITmnlProtSentSetupBean>();
		tmnlXh = new ITmnlProtSentSetupBean();
		tmnlXh.setModeCode("");
		tmnlXh.setModeName("--请选择--");
		iTmnlProtSentSetupBeanList.add(tmnlXh);
		iTmnlProtSentSetupBeanList.addAll(tmnlTaskConfigManager.findTmnlModel());
		//iTmnlProtSentSetupBeanList = tmnlTaskConfigManager.findTmnlModel();
		return SUCCESS;
	}
	
	/**
	 * 终端类型
	 */
	public String queryTmnlType() throws Exception {
		iTmnlProtSentSetupBeanList = new ArrayList<ITmnlProtSentSetupBean>();
		tmnlLx = new ITmnlProtSentSetupBean();
		tmnlLx.setTmnlTypeCode("");
		tmnlLx.setTmnlType("--请选择--");
		iTmnlProtSentSetupBeanList.add(tmnlLx);
		iTmnlProtSentSetupBeanList.addAll(tmnlTaskConfigManager.findTmnlType());
		//iTmnlProtSentSetupBeanList = tmnlTaskConfigManager.findTmnlType();
		return SUCCESS;
	}
	
	/**
	 * 采集方式
	 */
	public String queryCommMode() throws Exception {
		iTmnlProtSentSetupBeanList = new ArrayList<ITmnlProtSentSetupBean>();
		cjfs = new ITmnlProtSentSetupBean();
		cjfs.setCommModeCode("");
		cjfs.setCommMode("--请选择--");
		iTmnlProtSentSetupBeanList.add(cjfs);
		iTmnlProtSentSetupBeanList.addAll(tmnlTaskConfigManager.findCollMode());
		//iTmnlProtSentSetupBeanList = tmnlTaskConfigManager.findCollMode();
		return SUCCESS;
	}
	
//	/**
//	 * 上送方式
//	 */
//	public String querySentUpMode() throws Exception {
//		iTmnlProtSentSetupBeanList = new ArrayList<ITmnlProtSentSetupBean>();
//		ssfs = new ITmnlProtSentSetupBean();
//		ssfs.setSendUpMode("--请选择--");
//		iTmnlProtSentSetupBeanList.add(ssfs);
//		iTmnlProtSentSetupBeanList.addAll(tmnlTaskConfigManager.findSendUp());
//		//iTmnlProtSentSetupBeanList = tmnlTaskConfigManager.findSendUp();
//		return SUCCESS;
//	}
	
	/**
	 * 通讯规约
	 */
	public String queryProtocolCode() throws Exception {
		iTmnlProtSentSetupBeanList = new ArrayList<ITmnlProtSentSetupBean>();
		txgy = new ITmnlProtSentSetupBean();
		txgy.setProtocolCode("");
		txgy.setProtocolName("--请选择--");
		iTmnlProtSentSetupBeanList.add(txgy);
		iTmnlProtSentSetupBeanList.addAll(tmnlTaskConfigManager.findProtocol());
		//iTmnlProtSentSetupBeanList = tmnlTaskConfigManager.findProtocol();
		return SUCCESS;
	}
	
	/**
	 * 用户类型
	 */
	public String queryConsType() throws Exception {
		iTmnlTaskSetupBeanList = new ArrayList<ITmnlTaskSetupBean>();
		yhlx = new ITmnlTaskSetupBean();
		yhlx.setConsType("");
		yhlx.setConsTypeName("--请选择--");
		iTmnlTaskSetupBeanList.add(yhlx);
		iTmnlTaskSetupBeanList.addAll(tmnlTaskConfigManager.findConsType());
		//iTmnlTaskSetupBeanList = tmnlTaskConfigManager.findConsType();
		return SUCCESS;
	}
	
	/**
	 * 容量等级
	 */
	public String queryCapGrade() throws Exception {
		iTmnlTaskSetupBeanList = new ArrayList<ITmnlTaskSetupBean>();
		rldj = new ITmnlTaskSetupBean();
		rldj.setCapGradeNo("");
		rldj.setCapGradeName("--请选择--");
		iTmnlTaskSetupBeanList.add(rldj);
		iTmnlTaskSetupBeanList.addAll(tmnlTaskConfigManager.findCapGrade());
		//iTmnlTaskSetupBeanList = tmnlTaskConfigManager.findCapGrade();
		return SUCCESS;
	}
	
	
	
	/**
	 * 终端任务上送方式配置
	 */
	public String queryTmnlTaskConfig() throws Exception {
			Page<ITmnlProtSentSetupBean> zdrw = null;
			zdrw = tmnlTaskConfigManager.findTmnlTask(start, limit);
			if (null != zdrw) {
				iTmnlProtSentSetupBeanList = zdrw.getResult();
				totalCount = zdrw.getTotalCount();
			}
		return SUCCESS;
	}

	/**
	 * 用户与终端任务配置
	 */
	public String queryUserTmnlConfig() throws Exception {
		Page<ITmnlTaskSetupBean> yhzd = null;
		yhzd = tmnlTaskConfigManager.findUserTask(start, limit);
		if (null != yhzd) {
			iTmnlTaskSetupBeanList = yhzd.getResult();
			totalCount = yhzd.getTotalCount();
		}
		return SUCCESS;
	}

	/**
	 * @return 终端任务模板
	 * @throws Exception
	 */
	public String queryTTaskT() throws Exception {
		Page<TTaskTemplateBean> rwmb = null;
		rwmb = tmnlTaskConfigManager.findTTaskT(start, limit);
		if (null != rwmb) {
			tTaskTemplateBeanList = rwmb.getResult();
			totalCount = rwmb.getTotalCount();
		}
		return SUCCESS;
	}
	

	
	
	
	

	public ITmnlTaskSetupBean getBeanUser() {
		return beanUser;
	}


	public void setBeanUser(ITmnlTaskSetupBean beanUser) {
		this.beanUser = beanUser;
	}


	public List<ITmnlTaskSetupBean> getiTmnlTaskSetupBeanList() {
		return iTmnlTaskSetupBeanList;
	}

	public void setiTmnlTaskSetupBeanList(
			List<ITmnlTaskSetupBean> iTmnlTaskSetupBeanList) {
		this.iTmnlTaskSetupBeanList = iTmnlTaskSetupBeanList;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public TmnlTaskConfigManager getTmnlTaskConfigManager() {
		return tmnlTaskConfigManager;
	}

	public void setTmnlTaskConfigManager(
			TmnlTaskConfigManager tmnlTaskConfigManager) {
		this.tmnlTaskConfigManager = tmnlTaskConfigManager;
	}

	public List<ITmnlProtSentSetupBean> getiTmnlProtSentSetupBeanList() {
		return iTmnlProtSentSetupBeanList;
	}

	public void setiTmnlProtSentSetupBeanList(
			List<ITmnlProtSentSetupBean> iTmnlProtSentSetupBeanList) {
		this.iTmnlProtSentSetupBeanList = iTmnlProtSentSetupBeanList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public List<TTaskTemplateBean> gettTaskTemplateBeanList() {
		return tTaskTemplateBeanList;
	}

	public void settTaskTemplateBeanList(
			List<TTaskTemplateBean> tTaskTemplateBeanList) {
		this.tTaskTemplateBeanList = tTaskTemplateBeanList;
	}
	
	public String getProtSendSetupId() {
		return protSendSetupId;
	}

	public void setProtSendSetupId(String protSendSetupId) {
		this.protSendSetupId = protSendSetupId;
	}

	public String getTaskSetupId() {
		return taskSetupId;
	}

	public void setTaskSetupId(String taskSetupId) {
		this.taskSetupId = taskSetupId;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public String getTmnlType() {
		return tmnlType;
	}

	public void setTmnlType(String tmnlType) {
		this.tmnlType = tmnlType;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public String getCommMode() {
		return commMode;
	}

	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

	public String getTmnlAddrS() {
		return tmnlAddrS;
	}

	public void setTmnlAddrS(String tmnlAddrS) {
		this.tmnlAddrS = tmnlAddrS;
	}

	public String getTmnlAddrE() {
		return tmnlAddrE;
	}

	public void setTmnlAddrE(String tmnlAddrE) {
		this.tmnlAddrE = tmnlAddrE;
	}

	public String getSendUpMode() {
		return sendUpMode;
	}

	public void setSendUpMode(String sendUpMode) {
		this.sendUpMode = sendUpMode;
	}

	public String getAttachMeterFlag() {
		return attachMeterFlag;
	}

	public void setAttachMeterFlag(String attachMeterFlag) {
		this.attachMeterFlag = attachMeterFlag;
	}

	public String getTemplateIdList() {
		return templateIdList;
	}

	public void setTemplateIdList(String templateIdList) {
		this.templateIdList = templateIdList;
	}

	public String getConsTypeName() {
		return consTypeName;
	}

	public void setConsTypeName(String consTypeName) {
		this.consTypeName = consTypeName;
	}

	public String getCapGradeName() {
		return capGradeName;
	}

	public void setCapGradeName(String capGradeName) {
		this.capGradeName = capGradeName;
	}
	
	public ITmnlProtSentSetupBean getTmnlXh() {
		return tmnlXh;
	}

	public void setTmnlXh(ITmnlProtSentSetupBean tmnlXh) {
		this.tmnlXh = tmnlXh;
	}

	public ITmnlProtSentSetupBean getTmnlLx() {
		return tmnlLx;
	}

	public void setTmnlLx(ITmnlProtSentSetupBean tmnlLx) {
		this.tmnlLx = tmnlLx;
	}

	public ITmnlProtSentSetupBean getCjfs() {
		return cjfs;
	}

	public void setCjfs(ITmnlProtSentSetupBean cjfs) {
		this.cjfs = cjfs;
	}


	public ITmnlProtSentSetupBean getSsfs() {
		return ssfs;
	}

	public void setSsfs(ITmnlProtSentSetupBean ssfs) {
		this.ssfs = ssfs;
	}

	public ITmnlProtSentSetupBean getTxgy() {
		return txgy;
	}

	public void setTxgy(ITmnlProtSentSetupBean txgy) {
		this.txgy = txgy;
	}
	
	public ITmnlTaskSetupBean getYhlx() {
		return yhlx;
	}

	public void setYhlx(ITmnlTaskSetupBean yhlx) {
		this.yhlx = yhlx;
	}

	public ITmnlTaskSetupBean getRldj() {
		return rldj;
	}

	public void setRldj(ITmnlTaskSetupBean rldj) {
		this.rldj = rldj;
	}
	
	public ITmnlProtSentSetupBean getBean() {
		return bean;
	}

	public void setBean(ITmnlProtSentSetupBean bean) {
		this.bean = bean;
	}
	
	public String getTmnlResult() {
		return tmnlResult;
	}

	public void setTmnlResult(String tmnlResult) {
		this.tmnlResult = tmnlResult;
	}
}
