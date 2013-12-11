package com.nari.terminalparam;

import java.util.Date;

/**
 * TTmnlParam entity. @author MyEclipse Persistence Tools
 */

public class TTmnlParam implements java.io.Serializable {

	// Fields

	private TTmnlParamId id;
	private String currentValue;
	private String historyValue;
	private String statusCode;
	private Boolean isValid;
	private String staffNo;
	private Date saveTime;
	private Byte resendCount;
	private Date sendTime;
	private Date successTime;
	private Date lastSendTime;
	private Date nextSendTime;
	private String failureCode;
	private String callValue;

	// Constructors

	public String getCallValue() {
		return callValue;
	}

	public void setCallValue(String callValue) {
		this.callValue = callValue;
	}

	/** default constructor */
	public TTmnlParam() {
	}

	/** minimal constructor */
	public TTmnlParam(TTmnlParamId id) {
		this.id = id;
	}

	/** full constructor */
	public TTmnlParam(TTmnlParamId id, String currentValue,
			String historyValue, String statusCode, Boolean isValid,
			String staffNo, Date saveTime, Byte resendCount, Date sendTime,
			Date successTime, Date lastSendTime, Date nextSendTime,
			String failureCode) {
		this.id = id;
		this.currentValue = currentValue;
		this.historyValue = historyValue;
		this.statusCode = statusCode;
		this.isValid = isValid;
		this.staffNo = staffNo;
		this.saveTime = saveTime;
		this.resendCount = resendCount;
		this.sendTime = sendTime;
		this.successTime = successTime;
		this.lastSendTime = lastSendTime;
		this.nextSendTime = nextSendTime;
		this.failureCode = failureCode;
	}

	// Property accessors

	public TTmnlParamId getId() {
		return this.id;
	}

	public void setId(TTmnlParamId id) {
		this.id = id;
	}

	public String getCurrentValue() {
		return this.currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getHistoryValue() {
		return this.historyValue;
	}

	public void setHistoryValue(String historyValue) {
		this.historyValue = historyValue;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getStaffNo() {
		return this.staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public Date getSaveTime() {
		return this.saveTime;
	}

	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

	public Byte getResendCount() {
		return this.resendCount;
	}

	public void setResendCount(Byte resendCount) {
		this.resendCount = resendCount;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getSuccessTime() {
		return this.successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}

	public Date getLastSendTime() {
		return this.lastSendTime;
	}

	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	public Date getNextSendTime() {
		return this.nextSendTime;
	}

	public void setNextSendTime(Date nextSendTime) {
		this.nextSendTime = nextSendTime;
	}

	public String getFailureCode() {
		return this.failureCode;
	}

	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}

}