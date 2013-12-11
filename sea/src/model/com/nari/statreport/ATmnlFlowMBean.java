package com.nari.statreport;

public class ATmnlFlowMBean {
	// 私有private
	private String orgNo;
	private String orgName;
	private Long gprsover;
	private Long gprsovercount;
	private Long smsover;
	private Long smsovercount;

	// 生成get、set 方法
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getGprsover() {
		return gprsover;
	}

	public void setGprsover(Long gprsover) {
		this.gprsover = gprsover;
	}

	public Long getGprsovercount() {
		return gprsovercount;
	}

	public void setGprsovercount(Long gprsovercount) {
		this.gprsovercount = gprsovercount;
	}

	public Long getSmsover() {
		return smsover;
	}

	public void setSmsover(Long smsover) {
		this.smsover = smsover;
	}

	public Long getSmsovercount() {
		return smsovercount;
	}

	public void setSmsovercount(Long smsovercount) {
		this.smsovercount = smsovercount;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
}
