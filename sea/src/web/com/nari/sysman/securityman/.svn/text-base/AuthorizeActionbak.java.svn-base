package com.nari.sysman.securityman;

import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.orgnization.ODept;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.privilige.PSysUserJdbc;
import com.nari.support.Page;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.sysman.securityman.impl.AuthorizeManagerImpl;
import com.nari.util.TreeNodeChecked;

/**
 * 类 PSysUserAction
 * 
 * @author zhangzhw
 * @describe 系统用户列表的演示action
 */
public class AuthorizeActionbak extends BaseAction {

	// 要返回的值
	public List<PSysUserJdbc> root;
	public List<TreeNodeChecked> treeNodeList;
	public long totalCount;
	public String currUserDeptNo; // 当前用户所属部门号
	public String currUserDeptName; // 当前用户所属部门名

	// 返回值包括 供电单位列表 和 部门列表
	public List<OOrg> orgList;
	public List<ODept> depList;

	public boolean success = true;

	// 浏览器端请求的参数
	public long start;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
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
	// 动态注入的东西 实际开发的时候不允许直接使用DAO，需要从服务层取得数据
	IAuthorizeManager iAuthorizeManager;

	public String execute() throws Exception {
		try {
			this.setCurrUserDeptName("01");
			this.setCurrUserDeptNo("测试部门");
		} catch (Exception e) {
			// logger.error(e);

		}

		return "success";
	}

	/**
	 * 查询所有系统用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAllPSysUser() throws Exception {
		
		Page<PSysUserJdbc> pi = this.iAuthorizeManager.findPage(getStart(),getLimit());
		// List<PSysUser> list = pi.getResult();
		// PSysUser pSysUser = null;
		// ODept oDept = null;
		// OOrg oOrg = null;
		// for (int i = 0; i < list.size(); i++) {
		// pSysUser = list.get(i);
		// String curStatusCode = pSysUser.getCurStatusCode() == null ? ""
		// : pSysUser.getCurStatusCode();
		// // if("1".equals(curStatusCode)){
		// // pSysUser.setCurStatusCode("正常");
		// // }else{
		// // pSysUser.setCurStatusCode("锁定");
		// // }
		// oDept = this.iAuthorizeManager.getDeptById(pSysUser.getDeptNo());
		// pSysUser.setDeptNo(oDept.getName() + "," + oDept.getDeptNo());
		// oOrg = this.iAuthorizeManager.getOrgById(pSysUser.getOrgNo());
		// pSysUser.setOrgNo(oOrg.getOrgName() + "," + oOrg.getOrgNo());
		// }
		this.setRoot(pi.getResult());
		this.setTotalCount(pi.getTotalCount());		

		this.setCurrUserDeptName("01");
		this.setCurrUserDeptNo("测试部门");

		return "success";
	}

	/**
	 * 根据用户名模糊查询相关的用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAllPSysUserByName() throws Exception {
//		Page<PSysUser> page = new Page<PSysUser>(this.getStart(), this
//				.getLimit());
//		Page<PSysUser> pi = this.iAuthorizeManager.findPage(page, "name", "%"
//				+ this.name + "%", MatchType.LIKE);
//		List<PSysUser> list = pi.getResult();
		// PSysUser pSysUser = null;
		// ODept oDept = null;
		// OOrg oOrg = null;
		// for (int i = 0; i < list.size(); i++) {
		// pSysUser = list.get(i);
		// String curStatusCode = pSysUser.getCurStatusCode() == null ? ""
		// : pSysUser.getCurStatusCode();
		// if("1".equals(curStatusCode)){
		// pSysUser.setCurStatusCode("正常");
		// }else{
		// pSysUser.setCurStatusCode("锁定");
		// }
		// oDept = this.iAuthorizeManager.getDeptById(pSysUser.getDeptNo());
		// pSysUser.setDeptNo(oDept.getName() + "," + oDept.getDeptNo());
		// oOrg = this.iAuthorizeManager.getOrgById(pSysUser.getOrgNo());
		// pSysUser.setOrgNo(oOrg.getOrgName() + "," + oOrg.getOrgNo());
		// }
//		this.setRoot(pi.getResult());
//		this.setTotalCount(pi.getTotalCount());

		return "success";
	}

	/**
	 * 根据工号删除用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deletePSysUser() throws Exception {
		this.iAuthorizeManager.deletePSysUser(this.staffNo);
		return "success";
	}

	/**
	 * 修改系统用户信息
	 * 
	 * @param pSysUser
	 *            系统用户信息
	 * @throws Exception
	 */
	public String updatePSysUser() throws Exception {

		PSysUser pSysUser = this.getpSysUser();

		// System.out.println("ip------------------"+pSysUser.getIp());
		// System.out.println("name----------------"+pSysUser.getName());
		// System.out.println("staffNo--------------"+pSysUser.getStaffNo());
		// System.out.println("deptNo--------------"+pSysUser.getDeptNo());
		// System.out.println("orgNo--------------"+pSysUser.getOrgNo());
		// System.out.println("lockTime----------------"+this.lockTime);
		// System.out.println("pwdRemindTime----------------"+pSysUser.getPwdRemindTime());
		// System.out.println("planUnlockTime----------------"+this.planUnlockTime);
		// System.out.println("unlockTime----------------"+pSysUser.getUnlockTime());
		// System.out.println("lockIp----------------"+pSysUser.getLockIp());
		// System.out.println("lockReason------------"+pSysUser.getLockReason());
		// 没有用户编号视为新增
		if (this.getEmpNo() == null || this.getEmpNo().isEmpty())
			return addPSysUser();

		// 设置该用户的密码信息
		// PSysUser p = this.iAuthorizeManager
		// .findByStaffNo(pSysUser.getStaffNo());
		//
		// pSysUser.setPwd(p.getPwd());
		// String deptNo = pSysUser.getDeptNo().split(",")[1];
		// pSysUser.setDeptNo(deptNo);
		// String orgNo = pSysUser.getOrgNo().split(",")[1];
		// pSysUser.setOrgNo(orgNo);

		this.iAuthorizeManager.updatePSysUser(pSysUser);
		// 更新相关权限
		this.iAuthorizeManager.updatePriv(this.staffNo, this.roles, this.facs,
				this.consTypes, this.orgs);
		return "success";
	}

	/**
	 * 修改系统用户信息
	 * 
	 * @param pSysUser
	 *            系统用户信息
	 * @throws Exception
	 */
	public String addPSysUser() throws Exception {

		PSysUser pSysUser = this.getpSysUser();

		// System.out.println("ip------------------"+pSysUser.getIp());
		// System.out.println("name----------------"+pSysUser.getName());
		// System.out.println("staffNo--------------"+pSysUser.getStaffNo());
		// System.out.println("deptNo--------------"+pSysUser.getDeptNo());
		// System.out.println("orgNo--------------"+pSysUser.getOrgNo());
		// System.out.println("lockTime----------------"+this.lockTime);
		// System.out.println("pwdRemindTime----------------"+pSysUser.getPwdRemindTime());
		// System.out.println("planUnlockTime----------------"+this.planUnlockTime);
		// System.out.println("unlockTime----------------"+pSysUser.getUnlockTime());
		// System.out.println("lockIp----------------"+pSysUser.getLockIp());
		// System.out.println("lockReason------------"+pSysUser.getLockReason());

		// 初始化密码
		pSysUser.setPwd("202CB962AC59075B964B07152D234B70");
		// String deptNo = pSysUser.getDeptNo().split(",")[1];
		// pSysUser.setDeptNo(deptNo);
		// String orgNo = pSysUser.getOrgNo().split(",")[1];
		// pSysUser.setOrgNo(orgNo);

		this.iAuthorizeManager.addPSysUser(pSysUser);
		// 更新相关权限
		this.iAuthorizeManager.updatePriv(this.staffNo, this.roles, this.facs,
				this.consTypes, this.orgs);

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
		String orgNo = "";
		if (this.orgNo != null && !this.orgNo.equals("")) {
			orgNo = this.orgNo.split(",")[1];
		}
		this.treeNodeList = this.iAuthorizeManager.findOrgTree(orgNo,
				this.staffNo);
		return SUCCESS;
	}

	/**
	 * Action 方法 orgList
	 * 
	 * @return 供电单位列表
	 * @throws Exception
	 */
	public String orgList() throws Exception {
		setOrgList(iAuthorizeManager.findAllOrg());

		return SUCCESS;
	}

	/**
	 * Action 方法 depList
	 * 
	 * @return 部门列表
	 * @throws Exception
	 */
	public String depList() throws Exception {

		setDepList(iAuthorizeManager.findAllDept());
		return SUCCESS;
	}

	

	public List<TreeNodeChecked> getTreeNodeList() {
		return treeNodeList;
	}

	public void setTreeNodeList(List<TreeNodeChecked> treeNodeList) {
		this.treeNodeList = treeNodeList;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getStart() {
		return this.start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLimit() {
		return this.limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getStaffNo() {
		return this.staffNo;
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

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getCurStatusCode() {
		return this.curStatusCode;
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
		return this.pwdRemindTime;
	}

	public void setPwdRemindTime(Date pwdRemindTime) {
		this.pwdRemindTime = pwdRemindTime;
	}

	public Date getLockTime() {
		return this.lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Date getPlanUnlockTime() {
		return this.planUnlockTime;
	}

	public void setPlanUnlockTime(Date planUnlockTime) {
		this.planUnlockTime = planUnlockTime;
	}

	public Date getUnlockTime() {
		return this.unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	public String getLockIp() {
		return this.lockIp;
	}

	public void setLockIp(String lockIp) {
		this.lockIp = lockIp;
	}

	public String getAutoUnlockFlag() {
		return this.autoUnlockFlag;
	}

	public void setAutoUnlockFlag(String autoUnlockFlag) {
		this.autoUnlockFlag = autoUnlockFlag;
	}

	public String getLockReason() {
		return this.lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}

	public String getUnlockEmpNo() {
		return this.unlockEmpNo;
	}

	public void setUnlockEmpNo(String unlockEmpNo) {
		this.unlockEmpNo = unlockEmpNo;
	}

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

	// 通过此种注释方式可以使生成json 时不生成这个变量
	@JSON(serialize = false)
	public void setiAuthorizeManager(AuthorizeManagerImpl iAuthorizeManager) {
		this.iAuthorizeManager = iAuthorizeManager;
	}

	public String getCurrUserDeptNo() {
		return currUserDeptNo;
	}

	public void setCurrUserDeptNo(String currUserDeptNo) {
		this.currUserDeptNo = currUserDeptNo;
	}

	public String getCurrUserDeptName() {
		return currUserDeptName;
	}

	public void setCurrUserDeptName(String currUserDeptName) {
		this.currUserDeptName = currUserDeptName;
	}

	public List<OOrg> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<OOrg> orgList) {
		this.orgList = orgList;
	}

	public List<ODept> getDepList() {
		return depList;
	}

	public void setDepList(List<ODept> depList) {
		this.depList = depList;
	}

	public List<PSysUserJdbc> getRoot() {
		return root;
	}

	public void setRoot(List<PSysUserJdbc> root) {
		this.root = root;
	}
	
	

}
