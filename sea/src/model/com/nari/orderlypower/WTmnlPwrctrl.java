package com.nari.orderlypower;

import java.util.Date;

/**
 * WTmnlPwrctrl entity. @author MyEclipse Persistence Tools
 */

public class WTmnlPwrctrl implements java.io.Serializable {

	// Fields

	private Long tmnlPwrctrlId;
	private String orgNo;
	private String consNo;
	private String tmnlAssetNo;
	private Integer totalNo;
	private Long ctrlSchemeId;
	private String cycMode;
	private String cycNo;
	private Date ctrlDateStart;
	private Date ctrlDateEnd;
	private Boolean isExec;
	private Short floatValue;
	private Byte curveNo;
	private Boolean ctrlFlag;
	private Boolean isSendSms;
	private String statusCode;
	private String staffNo;
	private Date saveTime;
	private Date sendTime;
	private Date successTime;
	private String failureCode;
	private String keyId;//界面唯一标识，终端资产编号+总加组号

	public Long getTmnlPwrctrlId() {
		return this.tmnlPwrctrlId;
	}

	public void setTmnlPwrctrlId(Long tmnlPwrctrlId) {
		this.tmnlPwrctrlId = tmnlPwrctrlId;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getConsNo() {
		return this.consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public Integer getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(Integer totalNo) {
		this.totalNo = totalNo;
	}

	public Long getCtrlSchemeId() {
		return this.ctrlSchemeId;
	}

	public void setCtrlSchemeId(Long ctrlSchemeId) {
		this.ctrlSchemeId = ctrlSchemeId;
	}

	public String getCycMode() {
		return this.cycMode;
	}

	public void setCycMode(String cycMode) {
		this.cycMode = cycMode;
	}

	public String getCycNo() {
		return this.cycNo;
	}

	public void setCycNo(String cycNo) {
		this.cycNo = cycNo;
	}

	public Date getCtrlDateStart() {
		return this.ctrlDateStart;
	}

	public void setCtrlDateStart(Date ctrlDateStart) {
		this.ctrlDateStart = ctrlDateStart;
	}

	public Date getCtrlDateEnd() {
		return this.ctrlDateEnd;
	}

	public void setCtrlDateEnd(Date ctrlDateEnd) {
		this.ctrlDateEnd = ctrlDateEnd;
	}

	public Boolean getIsExec() {
		return this.isExec;
	}

	public void setIsExec(Boolean isExec) {
		this.isExec = isExec;
	}

	public Short getFloatValue() {
		return this.floatValue;
	}

	public void setFloatValue(Short floatValue) {
		this.floatValue = floatValue;
	}

	public Byte getCurveNo() {
		return this.curveNo;
	}

	public void setCurveNo(Byte curveNo) {
		this.curveNo = curveNo;
	}

	public Boolean getCtrlFlag() {
		return this.ctrlFlag;
	}

	public void setCtrlFlag(Boolean ctrlFlag) {
		this.ctrlFlag = ctrlFlag;
	}

	public Boolean getIsSendSms() {
		return this.isSendSms;
	}

	public void setIsSendSms(Boolean isSendSms) {
		this.isSendSms = isSendSms;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
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

	public String getFailureCode() {
		return this.failureCode;
	}

	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
}