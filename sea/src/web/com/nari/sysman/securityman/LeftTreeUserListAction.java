package com.nari.sysman.securityman;

import java.util.List;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.action.baseaction.Errors;
import com.nari.customer.CConsRtmnl;
import com.nari.orgnization.OOrg;
import com.nari.support.Page;

public class LeftTreeUserListAction extends BaseAction {
	public boolean success = true;
	public Errors errors;

	// 前端参数
	public String con;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;

	// 返回值
	public List<CConsRtmnl> userGrid;
	public long totalCount;

	// 字典表返回
	public List<OOrg> bureauList;
	public List<OOrg> orgNoList;
	public List valueList;

	// 注入值
	public IUserAdvanceQueryManager iUserAdvanceQueryManager;

	public String userlist() throws Exception {

		Page<CConsRtmnl> pcr = iUserAdvanceQueryManager.findUserGrid(super
				.getPSysUser(), start, limit, con);
		userGrid = pcr.getResult();
		totalCount = pcr.getTotalCount();

		return "success";
	}

	/**
	 * 方法 bureauList
	 * 
	 * @return 该用户有权限的供电所列表
	 * @throws Exception
	 */
	public String bureauList() throws Exception {
		String username = (String) super.getSession().getAttribute("username");
		bureauList = iUserAdvanceQueryManager.findBureauList(username);
		return "success";
	}

	/**
	 * 方法 orgNoList
	 * 
	 * @return 该用户有权限的供电单位列表
	 * @throws Exception
	 */
	public String orgNoList() throws Exception {
		String username = (String) super.getSession().getAttribute("username");
		bureauList = iUserAdvanceQueryManager.findOrgNoList(username);
		return "success";
	}

	/**
	 * action 方法 　 subIdList
	 * 
	 * @return 字典列表　变电站
	 * @throws Exception
	 */
	public String subIdList() throws Exception {

		valueList = iUserAdvanceQueryManager.subIdList(super.getPSysUser());
		return "success";
	}

	/**
	 * action 方法 　 lineIdList
	 * 
	 * @return 字典列表　线路
	 * @throws Exception
	 */
	public String lineIdList() throws Exception {

		valueList = iUserAdvanceQueryManager.lineIdList(super.getPSysUser());
		return "success";
	}

	/**
	 * action 方法 　 tradeCodeList
	 * 
	 * @return 字典列表　行业
	 * @throws Exception
	 */
	public String tradeCodeList() throws Exception {

		valueList = iUserAdvanceQueryManager.tradeCodeList();
		return "success";
	}

	/**
	 * action 方法 　 consTypeList
	 * 
	 * @return 字典列表　用户类型
	 * @throws Exception
	 */
	public String consTypeList() throws Exception {

		valueList = iUserAdvanceQueryManager.consTypeList();
		return "success";
	}

	/**
	 * action 方法 　 elecTypeList
	 * 
	 * @return 字典列表　用电类别
	 * @throws Exception
	 */
	public String elecTypeList() throws Exception {

		valueList = iUserAdvanceQueryManager.elecTypeList();
		return "success";
	}

	/**
	 * action 方法 　 capGradeList
	 * 
	 * @return 字典列表　用电容量等级
	 * @throws Exception
	 */
	public String capGradeList() throws Exception {

		valueList = iUserAdvanceQueryManager.capGradeList();
		return "success";
	}

	/**
	 * action 方法 　 shiftNoList
	 * 
	 * @return 字典列表　班次
	 * @throws Exception
	 */
	public String shiftNoList() throws Exception {

		valueList = iUserAdvanceQueryManager.shiftNoList();
		return "success";
	}

	/**
	 * action 方法 　 lodeAttrList
	 * 
	 * @return 字典列表　负荷性质
	 * @throws Exception
	 */
	public String lodeAttrList() throws Exception {

		valueList = iUserAdvanceQueryManager.lodeAttrList();
		return "success";
	}

	/**
	 * action 方法 　 tmnlTypeList
	 * 
	 * @return 字典列表　终端类型
	 * @throws Exception
	 */
	public String tmnlTypeList() throws Exception {

		valueList = iUserAdvanceQueryManager.tmnlTypeList();
		return "success";
	}

	/**
	 * action 方法 　 tmnlTypeList
	 * 
	 * @return 字典列表　采集方式
	 * @throws Exception
	 */
	public String collTypeList() throws Exception {

		valueList = iUserAdvanceQueryManager.collTypeList();
		return "success";
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

	public String getCon() {
		return con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	public List<CConsRtmnl> getUserGrid() {
		return userGrid;
	}

	public void setUserGrid(List<CConsRtmnl> userGrid) {
		this.userGrid = userGrid;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void setiUserAdvanceQueryManager(
			IUserAdvanceQueryManager iUserAdvanceQueryManager) {
		this.iUserAdvanceQueryManager = iUserAdvanceQueryManager;
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

	public List<OOrg> getBureauList() {
		return bureauList;
	}

	public List<OOrg> getOrgNoList() {
		return orgNoList;
	}

	public List getValueList() {
		return valueList;
	}

	public void setValueList(List valueList) {
		this.valueList = valueList;
	}

	public void setBureauList(List<OOrg> bureauList) {
		this.bureauList = bureauList;
	}

	public void setOrgNoList(List<OOrg> orgNoList) {
		this.orgNoList = orgNoList;
	}

}
