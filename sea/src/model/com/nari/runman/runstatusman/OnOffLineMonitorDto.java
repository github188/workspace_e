package com.nari.runman.runstatusman;

public class OnOffLineMonitorDto {

	// 供电单位
	private String orgName;

	private String staffNo;

	private String staffName;

	private String isOnline;

	private String boundIp;

	public String getBoundIp() {
		return boundIp;
	}

	public void setBoundIp(String boundIp) {
		this.boundIp = boundIp;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}



}
