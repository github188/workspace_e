package com.nari.sysman.templateman;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.ami.database.map.terminalparam.TTaskTemplate;
import com.nari.basicdata.BClearProtocol;
import com.nari.coherence.TaskDeal;
import com.nari.privilige.PSysUser;
import com.nari.util.TreeNode;

/**
 * 终端任务模板Action
 * @author 余涛
 */
public class TaskModelAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	
	/* 自动注入业务层 */
	private TaskModelManager taskModelManager;
	public void setTaskModelManager(TaskModelManager taskModelManager) {
		this.taskModelManager = taskModelManager;
	}

	/* 需要返回的Json */
	public boolean success = true;
	public boolean isSuccess() {
		return this.success;
	}
	private List<TTaskTemplate> tTaskTemplateList;	//	任务模板列表
	public List<TTaskTemplate> gettTaskTemplateList() {
		return this.tTaskTemplateList;
	}
	private List<TreeNode> treeNodeList;
	public List<TreeNode> getTreeNodeList() {
		return treeNodeList;
	}
	private List<BClearProtocol> bClearProtocolList;	//透明规约列表
	public List<BClearProtocol> getBClearProtocolList(){
		return this.bClearProtocolList;
	}
	private TTaskTemplate tTaskTemplate;	//任务模板
	public TTaskTemplate getTTaskTemplate(){
		return this.tTaskTemplate;
	}
	private List<VwTaskProperty> tTaskPropertyList;	//任务属性
	public List<VwTaskProperty> gettTaskPropertyList() {
		return tTaskPropertyList;
	}

	/* 页面表单项 */
	private Long templateId;	//模板ID
	private String templateName;	//任务模板名称
	private String templateName1;	//增加任务模板名称时使用
	private Short taskNo;	//任务号
	private String reportCycleUnit1;	//采集周期单位
	private Integer reportCycle;	//采集周期
	private String referenceTime;	//采集基准时间
	private Integer randomValue;	//采集随机数
	private Short masterR;	//采集数据点数
	private String recallPolicy;	//补召策略
//	private String isShare;	//是否共享
//	private Boolean isShare1 = false;
	private String orgNo;	//单位编码
	private Byte dataType; //数据类型编码
	private String taskProperty; //任务属性
	private String taskProperty1;	//任务属性combox提交值
	
	private String msg;	//出错信息
	private String useSign="0"; //任务模版是否在用 1--在用 0--不在用
	private String isExist="0";	//任务模版名称是否存在 1--存在0--不存在
	private String itemStr;	//对应模板的数据项编码字符串
		
	/**
	 * 创建任务模板
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	public String saveTaskTemplate() throws Exception {
		
		if(!TaskDeal.isCacheRunning()){
			msg = "前置集群通信中断";
			return SUCCESS;
		}
		
		PSysUser pSysUser =(PSysUser) getSession().getAttribute("pSysUser");
		if(pSysUser == null){
			return SUCCESS;
		}
		this.logger.debug("创建任务模板开始");
		
		List<TTaskTemplate> tList = this.taskModelManager.getAllTaskTemplateByName(templateName1);
		if(tList != null && tList.size()>0){
			msg = "任务模板名称已存在！";
			return SUCCESS;
		}
		
//		if(isShare!=null){
//			isShare1 = true;
//		}
		TTaskTemplate tTaskTemplate = new TTaskTemplate();
		tTaskTemplate.setOrgNo(pSysUser.getOrgNo());
		tTaskTemplate.setTemplateName(templateName1);
		tTaskTemplate.setTaskProperty(taskProperty1);
		tTaskTemplate.setDataType(dataType);
		tTaskTemplate.setDataCount((short)0);
		tTaskTemplate.setTaskNo(taskNo);
		tTaskTemplate.setReportCycle(reportCycle);
		tTaskTemplate.setReportCycleUnit(reportCycleUnit1);
		if("3".equals(reportCycleUnit1)||"2".equals(reportCycleUnit1)){
			tTaskTemplate.setSamplingPoints((short)1);
		}else if("0".equals(reportCycleUnit1)){
			int temp = 0;
			if(reportCycle!=null && reportCycle!=0)
				temp = 24*60/reportCycle;
			tTaskTemplate.setSamplingPoints((short)temp);
		}
		tTaskTemplate.setReferenceTime(referenceTime);
		tTaskTemplate.setRandomValue(randomValue);
		tTaskTemplate.setTmnlR((short)1);
		tTaskTemplate.setIsCancel(false);
		tTaskTemplate.setMasterR(masterR);
		if(!Pattern.matches("^([1-9]|10),([1-9]|10)$", recallPolicy)){
			recallPolicy = "";
		}
		tTaskTemplate.setRecallPolicy(recallPolicy);
		tTaskTemplate.setIsShare(false);
		this.taskModelManager.saveOrUpdateTaskTemplate(tTaskTemplate,itemStr);
		this.addUserLog("创建任务模板--"+templateName1, "任务模板管理", "02");
		
		this.logger.debug("创建任务模板结束");
		Thread.sleep(1010);
		return SUCCESS;
	}
	/**
	 * 修改任务模板信息保存
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	public String updateTaskTemplate() throws Exception {
		this.logger.debug("保存任务模板开始");
		
		if(!TaskDeal.isCacheRunning()){
			msg = "前置集群通信中断";
			return SUCCESS;
		}
		
		PSysUser pSysUser =(PSysUser) getSession().getAttribute("pSysUser");
		if(pSysUser == null){
			return SUCCESS;
		}
		
		List<TTaskTemplate> tList = this.taskModelManager.getAllTaskTemplateByNameId(templateName,templateId);
		if(tList != null && tList.size()>0){
			msg = "任务模板名称已存在！";
			return SUCCESS;
		}
		
//		if(isShare!=null){
//			isShare1 = true;
//		}
		
		short dataCount = 0;
		if(itemStr != null && !itemStr.equals("")){
			String[] itemArray = itemStr.split(",");
			dataCount = (short)itemArray.length;
		}
		
		TTaskTemplate tTaskTemplate = new TTaskTemplate();
		tTaskTemplate.setOrgNo(pSysUser.getOrgNo());
		tTaskTemplate.setTemplateId(templateId);
		tTaskTemplate.setTemplateName(templateName);
		tTaskTemplate.setTaskProperty(taskProperty1);
		tTaskTemplate.setDataType(dataType);
		tTaskTemplate.setTaskNo(taskNo);
		tTaskTemplate.setDataCount(dataCount);
		if("3".equals(reportCycleUnit1)||"2".equals(reportCycleUnit1)){
			tTaskTemplate.setSamplingPoints((short)1);
		}else if("0".equals(reportCycleUnit1)){
			int temp = 0;
			if(reportCycle!=null && reportCycle!=0)
				temp = 24*60/reportCycle;
			tTaskTemplate.setSamplingPoints((short)temp);
		}
		tTaskTemplate.setReportCycle(reportCycle);
		tTaskTemplate.setReportCycleUnit(reportCycleUnit1);
		tTaskTemplate.setReferenceTime(referenceTime);
		tTaskTemplate.setRandomValue(randomValue);
		tTaskTemplate.setTmnlR((short)1);
		tTaskTemplate.setIsCancel(false);
		tTaskTemplate.setMasterR(masterR);
		
		if(!Pattern.matches("^([1-9]|10),([1-9]|10)$", recallPolicy)){
			recallPolicy = "";
		}
		tTaskTemplate.setRecallPolicy(recallPolicy);
		
		tTaskTemplate.setIsShare(false);

		//任务模板信息修改
		this.taskModelManager.saveOrUpdateTaskTemplate(tTaskTemplate,itemStr);
		this.addUserLog("修改任务模板--"+templateName, "任务模板管理", "04");
		
		this.logger.debug("保存任务模板结束");
		
		Thread.sleep(1010);
		return SUCCESS;
	}

	/**
	 * 查询所有启用任务模板
	 * @return
	 * @throws Exception
	 */
	public String queryTaskTemplatesJson() throws Exception {
		this.logger.debug("查询任务模板列表");
		this.tTaskTemplateList = this.taskModelManager.getAllStartedTemplate();
		return SUCCESS;
	}
	
	/**
	 * 根据任务号查询任务模板
	 * @return
	 * @throws Exception
	 */
	public String queryTaskTemplatesByTaskNo() throws Exception{
		this.logger.debug("根据任务号查询任务模板");
		
		this.tTaskTemplateList = this.taskModelManager.getTemplateByTaskNo(this.taskNo.toString());
		return SUCCESS;
	}
	
	/**
	 * 查询所有任务属性
	 * @return
	 * @throws Exception
	 */
	public String queryTaskPropertyJson() throws Exception {
		this.logger.debug("查询任务属性列表");
		this.tTaskPropertyList = this.taskModelManager.getAllTaskProperty();
		return SUCCESS;
	}
	
	/**
	 * 根据模板id查询单个任务模板
	 * @return
	 * @throws Exception
	 */
	public String queryTaskTemplateJson() throws Exception {
		
		this.logger.debug("查询单个任务模板开始");
		
		if(!TaskDeal.isCacheRunning()){
			msg = "前置集群通信中断";
			return SUCCESS;
		}
		this.tTaskTemplate = this.taskModelManager.getTaskTemplateById(this.templateId);
		this.useSign = this.taskModelManager.queryTaskSign(this.templateId);
		this.logger.debug("查询模板关联的透明规约开始");
		this.bClearProtocolList = this.taskModelManager.getBClearProtocolByTemplateId(this.templateId);
		this.logger.debug("透明规约列表长度："+this.bClearProtocolList.size());
		return SUCCESS;
	}

	/**
	 * 查询透明规约类型树节点列表
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	public String queryAllBClearProtocolByNo() throws Exception {
		this.logger.debug("查询透明规约类型树节点列表开始");

		long timeStart = new Date().getTime();

		this.treeNodeList = this.taskModelManager.queryTreeNode(this.dataType);

		long timeEnd = new Date().getTime();
		this.logger.debug("查询透明规约类型树节点列表结束，所用时间为：" + (timeEnd - timeStart) + "毫秒。");

		return SUCCESS;
	}
	
	/**
	 * 注销模板
	 * @return
	 * @throws Exception
	 */
	public String cancelTaskTemplate()throws Exception{
		this.logger.debug("注销模板");
		
		if(!TaskDeal.isCacheRunning()){
			msg = "前置集群通信中断";
			return SUCCESS;
		}
		
		this.msg = this.taskModelManager.cancelTaskTemplate(this.templateId);
		this.addUserLog("注销任务模板--"+this.templateName1, "任务模板管理", "05");
		this.logger.debug(this.msg);
		return SUCCESS;
	}
	
	/**
	 * 查询任务模版是否存在 isExsit：1--存在 0--不存在
	 * @return
	 * @throws Exception
	 */
	public String queryIsExsit()throws Exception{
		List<TTaskTemplate> tList = this.taskModelManager.getAllTaskTemplateByName(templateName);
		if(tList != null && tList.size()>0){
			msg = "任务模板名称已存在！";
			this.isExist = "1";
		}
		return SUCCESS;
	}
	
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Short getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(Short taskNo) {
		this.taskNo = taskNo;
	}
	public String getReportCycleUnit1() {
		return reportCycleUnit1;
	}
	public void setReportCycleUnit1(String reportCycleUnit1) {
		this.reportCycleUnit1 = reportCycleUnit1;
	}
	public Integer getReportCycle() {
		return reportCycle;
	}
	public void setReportCycle(Integer reportCycle) {
		this.reportCycle = reportCycle;
	}
	public String getReferenceTime() {
		return referenceTime;
	}
	public void setReferenceTime(String referenceTime) {
		this.referenceTime = referenceTime;
	}
	public Integer getRandomValue() {
		return randomValue;
	}
	public void setRandomValue(Integer randomValue) {
		this.randomValue = randomValue;
	}
	public Short getMasterR() {
		return masterR;
	}
	public void setMasterR(Short masterR) {
		this.masterR = masterR;
	}
	public String getRecallPolicy() {
		return recallPolicy;
	}
	public void setRecallPolicy(String recallPolicy) {
		this.recallPolicy = recallPolicy;
	}
	public Byte getDataType() {
		return dataType;
	}
	public void setDataType(Byte dataType) {
		this.dataType = dataType;
	}
	public String getTaskProperty() {
		return taskProperty;
	}
	public void setTaskProperty(String taskProperty) {
		this.taskProperty = taskProperty;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getTemplateName1() {
		return templateName1;
	}
	public void setTemplateName1(String templateName1) {
		this.templateName1 = templateName1;
	}
	public String getItemStr() {
		return itemStr;
	}
	public void setItemStr(String itemStr) {
		this.itemStr = itemStr;
	}
	public String getTaskProperty1() {
		return taskProperty1;
	}
	public void setTaskProperty1(String taskProperty1) {
		this.taskProperty1 = taskProperty1;
	}
	public String getUseSign() {
		return useSign;
	}
	public void setUseSign(String useSign) {
		this.useSign = useSign;
	}
	public String getIsExist() {
		return isExist;
	}
	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}
}
