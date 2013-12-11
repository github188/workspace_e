package com.nari.model;

import java.util.Date;

public class TtmnlParam {
	
	private String tmnlAssetNo;
	
	private String protItemNo;
	
	private String currentValue;
	
	private String historyValue;
	
	private String blockSn;
	
	private String innerBlockSn;
	
	private String statusCode;
	
	private String isValid;
	
	private String staffNo;
	
	private Date saveTime;
	
	private int resendCount;
	
	private Date sendTime;
	
	private Date successTime;
	
	private Date lastSendTime;
	
	private Date nextSendTime;
	
	private String failureCode;

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getProtItemNo() {
		return protItemNo;
	}

	public void setProtItemNo(String protItemNo) {
		this.protItemNo = protItemNo;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getHistoryValue() {
		return historyValue;
	}

	public void setHistoryValue(String historyValue) {
		this.historyValue = historyValue;
	}

	public String getBlockSn() {
		return blockSn;
	}

	public void setBlockSn(String blockSn) {
		this.blockSn = blockSn;
	}

	public String getInnerBlockSn() {
		return innerBlockSn;
	}

	public void setInnerBlockSn(String innerBlockSn) {
		this.innerBlockSn = innerBlockSn;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public Date getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

	public int getResendCount() {
		return resendCount;
	}

	public void setResendCount(int resendCount) {
		this.resendCount = resendCount;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}

	public Date getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	public Date getNextSendTime() {
		return nextSendTime;
	}

	public void setNextSendTime(Date nextSendTime) {
		this.nextSendTime = nextSendTime;
	}

	public String getFailureCode() {
		return failureCode;
	}

	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}

}
