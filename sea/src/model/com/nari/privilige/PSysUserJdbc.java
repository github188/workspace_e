package com.nari.privilige;

import java.sql.Timestamp;

/**
 * 查询用户的 POJO
 * 
 * @author zhangzhw
 * 
 */
public class PSysUserJdbc {
	private String empNo;
	private String staffNo;
	private String orgNo;
	private String deptNo;
	private String name;
	private String pwd;
	private String ip;
	private String mac;
	private String accessLevel;
	private String curStatusCode;
	private Timestamp pwdRemindTime;
	private Timestamp lockTime;
	private Timestamp planUnlockTime;
	private Timestamp unlockTime;
	private String lockIp;
	private String autoUnlockFlag;
	private String lockReason;
	private String unlockEmpNo;
	private String orgName;
	private String depname;

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getCurStatusCode() {
		return curStatusCode;
	}

	public void setCurStatusCode(String curStatusCode) {
		this.curStatusCode = curStatusCode;
	}

	public Timestamp getPwdRemindTime() {
		return pwdRemindTime;
	}

	public void setPwdRemindTime(Timestamp pwdRemindTime) {
		this.pwdRemindTime = pwdRemindTime;
	}

	public Timestamp getLockTime() {
		return lockTime;
	}

	public void setLockTime(Timestamp lockTime) {
		this.lockTime = lockTime;
	}

	public Timestamp getPlanUnlockTime() {
		return planUnlockTime;
	}

	public void setPlanUnlockTime(Timestamp planUnlockTime) {
		this.planUnlockTime = planUnlockTime;
	}

	public Timestamp getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Timestamp unlockTime) {
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDepname() {
		return depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

}