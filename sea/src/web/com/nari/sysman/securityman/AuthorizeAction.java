package com.nari.sysman.securityman;

import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.privilige.PSysUser;
import com.nari.privilige.PSysUserJdbc;
import com.nari.support.Page;
import com.nari.util.TreeNodeChecked;

public class AuthorizeAction extends BaseAction {

	public static String SUCCESS = "success";

	// 通用返回
	public boolean success = true;
	public Errors errors;
	public long start;
	public long limit;
	public long totalCount;

	// form 参数
	public String name; // 用户名
	public String staffNo; // 工号
	public String deptNo; // 部门编号
	public String orgNo; // 供电单位编号
	public String empNo; // 人员编号
	public String ip; // 绑定的IP
	public String mac; // 绑定的物理地址
	public String curStatusCode; // 当前状态
	public String accessLevel; // 访问级别
	public Date pwdRemindTime; // 密码强度提醒时间
	public Date lockTime; // 锁定时间
	public Date planUnlockTime; // 计划解锁时间
	public Date unlockTime; // 实际解锁时间
	public String lockIp; // 锁定IP
	public String autoUnlockFlag; // 自动解锁标志
	public String lockReason; // 锁定原因
	public String unlockEmpNo; // 解锁人员

	public String roles; // 选中的角色
	public String orgs; // 选中的角色
	public String consTypes; // 选中的用电用户类型
	public String facs; // 选中的厂家

	// action返回
	public List<PSysUserJdbc> root;
	public List<TreeNodeChecked> treeNodeList;

	// 注入
	public IOperatorsManager iOperatorsManager;
	public IAuthorizeManager iAuthorizeManager;

	/**
	 * action 方法 queryAllPSysUser
	 * 
	 * @return 查询所有系统用户
	 * @throws Exception
	 */
	public String queryAllPSysUser() throws Exception {

		Page<PSysUserJdbc> pi = this.iOperatorsManager.findPage(getStart(),
				getLimit(), super.getPSysUser());

		this.setRoot(pi.getResult());
		this.setTotalCount(pi.getTotalCount());

		// this.setCurrUserDeptName("01");
		// this.setCurrUserDeptNo("测试部门");

		return "success";
	}

	/**
	 * Action 方法 queryPSysUser
	 * 
	 * @return 查询特定用户
	 * @throws Exception
	 */
	public String queryPSysUser() throws Exception {

		Page<PSysUserJdbc> pi = this.iOperatorsManager.findPage(getStart(),
				getLimit(), this.getpSysUser(),super.getPSysUser());

		this.setRoot(pi.getResult());
		this.setTotalCount(pi.getTotalCount());

		// this.setCurrUserDeptName("01");
		// this.setCurrUserDeptNo("测试部门");

		return "success";
	}

	/* 厂家树 */
	public String facTree() throws Exception {
		this.treeNodeList = this.iAuthorizeManager.findFacTree(this.staffNo);
		return SUCCESS;
	}

	/* 用电用户类型树 */
	public String consTypeTree() throws Exception {
		this.treeNodeList = this.iAuthorizeManager
				.findConsTypeTree(this.staffNo);
		return SUCCESS;
	}

	/* 单位树 */
	public String orgTree() throws Exception {
		this.treeNodeList = this.iAuthorizeManager.findOrgTree(this.orgNo,
				this.staffNo);
		return SUCCESS;
	}

	// 保存用户信息和权限
	public String updatePSysUser() throws Exception {
		String msg = iOperatorsManager.saveOrUpdate(this.getpSysUser(), this
				.getOrgs(), this.getFacs(), this.getRoles(), this
				.getConsTypes());
		if (!msg.equals("success")) {
			setSuccess(false);
			errors = new Errors();
			errors.setTitle("保存失败");
			errors.setMsg("msg");

		} else if (getEmpNo() == null || getEmpNo().isEmpty()) {
			addUserLog("增加操作员－－操作员工号：" + this.getStaffNo(), "操作员管理", "02");

		} else {
			addUserLog("修改操作员－－操作员工号：" + this.getStaffNo(), "操作员管理", "04");
		}

		return SUCCESS;
	}

	public String deletePSysUser() throws Exception {
		iOperatorsManager.deletePSysUser(this.getStaffNo());
		super.addUserLog("删除操作员－－操作员工号：" + this.getStaffNo(), "操作员管理", "03");
		return SUCCESS;
	}

	// form 构造
	public PSysUser getpSysUser() {
		PSysUser pSysUser = new PSysUser();
		pSysUser.setAutoUnlockFlag(this.autoUnlockFlag);
		pSysUser.setCurStatusCode(this.curStatusCode);
		pSysUser.setAccessLevel(this.accessLevel);
		pSysUser.setEmpNo(this.empNo);
		pSysUser.setIp(this.ip);
		pSysUser.setLockIp(this.lockIp);
		pSysUser.setLockReason(this.lockReason);
		pSysUser.setLockTime(this.lockTime);
		pSysUser.setMac(this.mac);
		pSysUser.setName(this.name);
		pSysUser.setDeptNo(this.deptNo);
		pSysUser.setOrgNo(this.orgNo);
		pSysUser.setPlanUnlockTime(this.planUnlockTime);
		pSysUser.setPwdRemindTime(this.pwdRemindTime);
		pSysUser.setStaffNo(this.staffNo);
		pSysUser.setUnlockEmpNo(this.unlockEmpNo);
		pSysUser.setUnlockTime(this.planUnlockTime);

		return pSysUser;
	}

	// getters and setters

	@JSON(serialize = false)
	public IOperatorsManager getiOperatorsManager() {
		return iOperatorsManager;
	}

	public void setiOperatorsManager(IOperatorsManager iOperatorsManager) {
		this.iOperatorsManager = iOperatorsManager;
	}

	@JSON(serialize = false)
	public IAuthorizeManager getiAuthorizeManager() {
		return iAuthorizeManager;
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

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getCurStatusCode() {
		return curStatusCode;
	}

	public void setCurStatusCode(String curStatusCode) {
		this.curStatusCode = curStatusCode;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Date getPwdRemindTime() {
		return pwdRemindTime;
	}

	public void setPwdRemindTime(Date pwdRemindTime) {
		this.pwdRemindTime = pwdRemindTime;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Date getPlanUnlockTime() {
		return planUnlockTime;
	}

	public void setPlanUnlockTime(Date planUnlockTime) {
		this.planUnlockTime = planUnlockTime;
	}

	public Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	public String getLockIp() {
		return lockIp;
	}

	public void setLockIp(String lockIp) {
		this.lockIp = lockIp;
	}

	public String getAutoUnlockFlag() {
		return autoUnlockFlag;
	}

	public void setAutoUnlockFlag(String autoUnlockFlag) {
		this.autoUnlockFlag = autoUnlockFlag;
	}

	public String getLockReason() {
		return lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}

	public String getUnlockEmpNo() {
		return unlockEmpNo;
	}

	public void setUnlockEmpNo(String unlockEmpNo) {
		this.unlockEmpNo = unlockEmpNo;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getOrgs() {
		return orgs;
	}

	public void setOrgs(String orgs) {
		this.orgs = orgs;
	}

	public String getConsTypes() {
		return consTypes;
	}

	public void setConsTypes(String consTypes) {
		this.consTypes = consTypes;
	}

	public String getFacs() {
		return facs;
	}

	public void setFacs(String facs) {
		this.facs = facs;
	}

	public List<PSysUserJdbc> getRoot() {
		return root;
	}

	public void setRoot(List<PSysUserJdbc> root) {
		this.root = root;
	}

	public List<TreeNodeChecked> getTreeNodeList() {
		return treeNodeList;
	}

	public void setTreeNodeList(List<TreeNodeChecked> treeNodeList) {
		this.treeNodeList = treeNodeList;
	}

	public void setiAuthorizeManager(IAuthorizeManager iAuthorizeManager) {
		this.iAuthorizeManager = iAuthorizeManager;
	}

}