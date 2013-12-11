package com.nari.baseapp.datagatherman;

import java.io.Serializable;

public class HeartBeat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgName;
	private String consNo;
	private String consName;
	private String tmnlAddr;
	private String tmnlAssetNo;
	private String isOnLine;
	private String onOffTime;
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getConsNo() {
		return consNo;
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
	public String getTmnlAddr() {
		return tmnlAddr;
	}
	public void setTmnlAddr(String tmnlAddr) {
		this.tmnlAddr = tmnlAddr;
	}	
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	public String getIsOnLine() {
		return isOnLine;
	}
	public void setIsOnLine(String isOnLine) {
		this.isOnLine = isOnLine;
	}
	public String getOnOffTime() {
		return onOffTime;
	}
	public void setOnOffTime(String onOffTime) {
		this.onOffTime = onOffTime;
	}
	
	
	

}
