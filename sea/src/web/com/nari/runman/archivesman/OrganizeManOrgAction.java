package com.nari.runman.archivesman;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.orgnization.OOrg;
import com.nari.runcontrol.OOrgJdbc;

/**
 * @author yut
 * @describe 档案管理 组织机构 供电所维护 Action
 */
public class OrganizeManOrgAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	// 注入类
	IOrganizeManager iOrganizeManager;

	// 返回确认
	public boolean success = true;
	public Errors errors;
	public String msg;

	// 返回记录
	OOrg oOrg;
	public List<OOrgJdbc> orgList;

	// 查询参数
	public String nodeValue;

	// Form参数
	private   String hiddenOrgNo;
	private   String orgNo;
	private   String orgName;
	private   String orgShortName;
	private   String pOrgNo;
	private   String orgType;
	private   Integer sortNo;
	private   Byte isBulkSale;
	private   String tmnlCode;
	private   String areaCode;
	private   Byte isDirect;
	private   Byte calcOrder;
	private   Byte isStat;
	
	/**
	 * 查询供电所
	 */
	public String queryOrg() throws Exception {
		this.logger.debug("查询供电所");
		this.orgList = this.iOrganizeManager.findOrg(nodeValue);
		return SUCCESS;
	}
	
	/**
	 * action 方法
	 * 
	 * @return 保存供电所信息
	 */
	public String saveOrg() throws Exception {
		this.logger.debug("保存供电所信息");
		OOrgJdbc org = this.genOOrg();
		this.msg = this.iOrganizeManager.saveOrUpdateOrg(this.hiddenOrgNo,org);
		return SUCCESS;
	}
	
	/**
	 * action 方法
	 * 
	 * @return 保存供电所信息
	 */
	public String deleteOrg() throws Exception {
		this.logger.debug("保存供电所信息");
		this.iOrganizeManager.deleteOrg(this.orgNo);
		return SUCCESS;
	}

	// 组合前台参数为 CCons
	public OOrgJdbc genOOrg() {
		OOrgJdbc org = new OOrgJdbc();
		org.setAreaCode(areaCode);
		org.setCalcOrder(calcOrder);
		org.setIsBulkSale(isBulkSale);
		org.setIsDirect(isDirect);
		org.setIsStat(isStat);
		org.setOrgName(orgName);
		org.setOrgNo(orgNo);
		org.setpOrgNo(pOrgNo);
		org.setOrgShortName(orgShortName);
		org.setOrgType(orgType);
		org.setSortNo(sortNo);
		org.setTmnlCode(tmnlCode);
		return org;
	}

	// getters and setters

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

	public List<OOrgJdbc> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<OOrgJdbc> orgList) {
		this.orgList = orgList;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgShortName() {
		return orgShortName;
	}

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	public String getPOrgNo() {
		return pOrgNo;
	}

	public void setPOrgNo(String pOrgNo) {
		this.pOrgNo = pOrgNo;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Byte getIsBulkSale() {
		return isBulkSale;
	}

	public void setIsBulkSale(Byte isBulkSale) {
		this.isBulkSale = isBulkSale;
	}

	public String getTmnlCode() {
		return tmnlCode;
	}

	public void setTmnlCode(String tmnlCode) {
		this.tmnlCode = tmnlCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Byte getIsDirect() {
		return isDirect;
	}

	public void setIsDirect(Byte isDirect) {
		this.isDirect = isDirect;
	}

	public Byte getCalcOrder() {
		return calcOrder;
	}

	public void setCalcOrder(Byte calcOrder) {
		this.calcOrder = calcOrder;
	}

	public Byte getIsStat() {
		return isStat;
	}

	public void setIsStat(Byte isStat) {
		this.isStat = isStat;
	}

	public String getHiddenOrgNo() {
		return hiddenOrgNo;
	}

	public void setHiddenOrgNo(String hiddenOrgNo) {
		this.hiddenOrgNo = hiddenOrgNo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
