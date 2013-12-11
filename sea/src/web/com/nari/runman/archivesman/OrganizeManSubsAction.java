package com.nari.runman.archivesman;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.orgnization.OOrg;
import com.nari.runcontrol.GSubsJdbc;
import com.nari.runcontrol.VwRunStatus;

/**
 * @author yut
 * @describe 档案管理 组织机构 变电站维护 Action
 */
public class OrganizeManSubsAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	// 注入类
	IOrganizeManager iOrganizeManager;

	// 返回确认
	public boolean success = true;
	public Errors errors;

	// 返回记录
	OOrg oOrg;
	public List<GSubsJdbc> subsList;
	public List<VwRunStatus> runStatusList;

	// 查询参数
	public String nodeValue;
	public String msg;

	// Form参数
	private Long subsId;
	private String subsName;
	private String subsNo;
	private String voltCode;
	private String subsAddr;
	private Integer mtNum;
	private Long mtCap;
	private String orgNo;
	private String inlineId;
	private String runStatusCode;
	
	/**
	 * 查询变电站
	 */
	public String querySubs() throws Exception {
		this.logger.debug("查询变电站");
		this.subsList = this.iOrganizeManager.findSubs(nodeValue);
		return SUCCESS;
	}
	
	/**
	 * 保存变电站
	 * @return
	 * @throws Exception
	 */
	public String saveSubs()throws Exception{
		GSubsJdbc subsJdbc = this.genGSubs();
		this.msg = this.iOrganizeManager.saveOrUpdateSubs(subsJdbc);
		return SUCCESS;
	}
	
	/**
	 * 查询变电站运行状态
	 * @return
	 * @throws Exception
	 */
	public String queryStatusJson() throws Exception{
		this.runStatusList = this.iOrganizeManager.findRunStatusList();
		return SUCCESS;
	}
	
	/**
	 * 删除变电站
	 * @return
	 * @throws Exception
	 */
	public String deleteSubs() throws Exception{
		this.iOrganizeManager.deleteSubs(subsId.toString());
		return SUCCESS;
	}
	// 组合前台参数为 CCons
	public GSubsJdbc genGSubs() {
		GSubsJdbc gSubsJdbc = new GSubsJdbc();
		gSubsJdbc.setSubsId(subsId);
		gSubsJdbc.setChgDate(new Timestamp(new Date().getTime()));
		gSubsJdbc.setInlineId(inlineId);
		gSubsJdbc.setMtCap(mtCap);
		gSubsJdbc.setMtNum(mtNum);
		gSubsJdbc.setOrgNo(orgNo);
		gSubsJdbc.setRunStatusCode(runStatusCode);
		gSubsJdbc.setSubsAddr(subsAddr);
		gSubsJdbc.setSubsName(subsName);
		gSubsJdbc.setSubsNo(subsNo);
		gSubsJdbc.setVoltCode(voltCode);
		return gSubsJdbc;
	}
	
	@JSON(serialize = false)
	public void setiOrganizeManager(IOrganizeManager iOrganizeManager) {
		this.iOrganizeManager = iOrganizeManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public OOrg getoOrg() {
		return oOrg;
	}

	public void setoOrg(OOrg oOrg) {
		this.oOrg = oOrg;
	}

	public List<GSubsJdbc> getSubsList() {
		return subsList;
	}

	public void setSubsList(List<GSubsJdbc> subsList) {
		this.subsList = subsList;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public Long getSubsId() {
		return subsId;
	}

	public void setSubsId(Long subsId) {
		this.subsId = subsId;
	}

	public String getSubsName() {
		return subsName;
	}

	public void setSubsName(String subsName) {
		this.subsName = subsName;
	}

	public String getSubsNo() {
		return subsNo;
	}

	public void setSubsNo(String subsNo) {
		this.subsNo = subsNo;
	}

	public String getVoltCode() {
		return voltCode;
	}

	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}

	public String getSubsAddr() {
		return subsAddr;
	}

	public void setSubsAddr(String subsAddr) {
		this.subsAddr = subsAddr;
	}

	public Integer getMtNum() {
		return mtNum;
	}

	public void setMtNum(Integer mtNum) {
		this.mtNum = mtNum;
	}

	public Long getMtCap() {
		return mtCap;
	}

	public void setMtCap(Long mtCap) {
		this.mtCap = mtCap;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getInlineId() {
		return inlineId;
	}

	public void setInlineId(String inlineId) {
		this.inlineId = inlineId;
	}

	public String getRunStatusCode() {
		return runStatusCode;
	}

	public void setRunStatusCode(String runStatusCode) {
		this.runStatusCode = runStatusCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<VwRunStatus> getRunStatusList() {
		return runStatusList;
	}

	public void setRunStatusList(List<VwRunStatus> runStatusList) {
		this.runStatusList = runStatusList;
	}
	
}
