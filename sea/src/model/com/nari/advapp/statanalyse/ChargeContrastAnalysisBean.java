package com.nari.advapp.statanalyse;

import java.util.Date;

public class ChargeContrastAnalysisBean {
	private   String orgNo;
	private   String orgName;
	private Double avgP;
	private Date statDate;
	public String getOrgNo() {
		return orgNo;
	}
	public Double getAvgP() {
		return avgP;
	}
	public void setAvgP(Double avgP) {
		this.avgP = avgP;
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
	public Date getStatDate() {
		return statDate;
	}
	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}
}
