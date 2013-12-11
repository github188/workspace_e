package com.nari.qrystat.querycollpoint;

public class ExcptionReport {
	private   String orgNo;
	private   Long exceptcount;
	private String orgName;
	private   Long ocount;
	private   Long fcount;
	private float fecount;//误报率


	public float getFecount() {
		return fecount;
	}
	public void setFecount(float fecount) {
		this.fecount = fecount;
	}
	public Long getOcount() {
		return ocount;
	}
	public void setOcount(Long ocount) {
		this.ocount = ocount;
	}
	public Long getFcount() {
		return fcount;
	}
	public void setFcount(Long fcount) {
		this.fcount = fcount;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public Long getExceptcount() {
		return exceptcount;
	}
	public void setExceptcount(Long exceptcount) {
		this.exceptcount = exceptcount;
	}
	
}
