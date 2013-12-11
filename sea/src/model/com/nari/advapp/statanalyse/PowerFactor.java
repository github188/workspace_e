package com.nari.advapp.statanalyse;

import java.util.Date;
/**
 * 
 * @author 陳國章
 *
 */

public class PowerFactor {
	private String orgName;
	private String consNo;
	private String consName;
	private float contractCap;
	private String volt;
	private float maxFactor;
	private String maxTime;
	private  float minFactor;
	public String getConsNo() {
		return consNo;
	}
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	public String getConsName() {
		return consName;
	}
	public void setConsName(String consName) {
		this.consName = consName;
	}
	
	
	public float getContractCap() {
		return contractCap;
	}
	public void setContractCap(float contractCap) {
		this.contractCap = contractCap;
	}
	public String getVolt() {
		return volt;
	}
	public void setVolt(String volt) {
		this.volt = volt;
	}
	public float getMaxFactor() {
		return maxFactor;
	}
	public void setMaxFactor(float maxFactor) {
		this.maxFactor = maxFactor;
	}
	public String getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}
	public float getMinFactor() {
		return minFactor;
	}
	public void setMinFactor(float minFactor) {
		this.minFactor = minFactor;
	}
	
}
