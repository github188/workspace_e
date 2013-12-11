package com.nari.orderlypower;

import java.util.Date;

/**
 * WTmnlFactctrl entity. @author MyEclipse Persistence Tools
 */

public class WTmnlFactctrl implements java.io.Serializable {

	// Fields

	private Long tmnlFactctrlId;
	private String orgNo;
	private String consNo;
	private String tmnlAssetNo;
	private Short totalNo;
	private Long ctrlSchemeId;
	private String ctrlTime;
	private Byte continueHours;
	private String weekDays;
	private Float factoryConst;
	private Boolean ctrlFlag;
	private Boolean isSendSms;
	private String statusCode;
	private String staffNo;
	private Date saveTime;
	private Date sendTime;
	private Date successTime;
	private String failureCode;
	private String keyId;//界面唯一标识，终端资产编号+总加组号
	private String execFlag="-999";//召测执行状态（显示召测是否成功）
	
	public WTmnlFactctrl() {
	}

	public WTmnlFactctrl(String keyId) {
		this.keyId = keyId;
	}

	public Long getTmnlFactctrlId() {
		return this.tmnlFactctrlId;
	}

	public void setTmnlFactctrlId(Long tmnlFactctrlId) {
		this.tmnlFactctrlId = tmnlFactctrlId;
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

	public Short getTotalNo() {
		return this.totalNo;
	}

	public void setTotalNo(Short totalNo) {
		this.totalNo = totalNo;
	}

	public Long getCtrlSchemeId() {
		return this.ctrlSchemeId;
	}

	public void setCtrlSchemeId(Long ctrlSchemeId) {
		this.ctrlSchemeId = ctrlSchemeId;
	}

	public String getCtrlTime() {
		return this.ctrlTime;
	}

	public void setCtrlTime(String ctrlTime) {
		this.ctrlTime = ctrlTime;
	}

	public Byte getContinueHours() {
		return this.continueHours;
	}

	public void setContinueHours(Byte continueHours) {
		this.continueHours = continueHours;
	}

	public String getWeekDays() {
		return this.weekDays;
	}

	public void setWeekDays(String weekDays) {
		this.weekDays = weekDays;
	}

	public Float getFactoryConst() {
		return factoryConst;
	}

	public void setFactoryConst(Float factoryConst) {
		this.factoryConst = factoryConst;
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

	public String getExecFlag() {
		return execFlag;
	}

	public void setExecFlag(String execFlag) {
		this.execFlag = execFlag;
	}
}