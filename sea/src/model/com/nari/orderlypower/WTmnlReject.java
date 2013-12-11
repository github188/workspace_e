package com.nari.orderlypower;

import java.util.Date;

/**
 * WTmnlReject entity. @author MyEclipse Persistence Tools
 */

public class WTmnlReject implements java.io.Serializable {

	// Fields

	private Long tmnlRejectId;
	private String orgNo;
	private String orgName;
	private String consNo;
	private String consName;
	private String tmnlAssetNo;
	private String terminalAddr;
	private Short totalNo;
	private Long ctrlSchemeId;
	private Boolean isReject;
	//private Boolean ctrlFlag;
	private Boolean isSendSms;
	private String statusCode;
	private String staffNo;
	private Date saveTime;
	private Date sendTime;
	private Date successTime;
	private String failureCode;
	private String protocolCode; 
	private String eliminateStatus;//控制状态
	//private String execStatus;//执行状态
	//private String message;
	

	// Constructors

	/** default constructor */
	public WTmnlReject() {
	}

	/** minimal constructor */
	public WTmnlReject(Long ctrlSchemeId) {
		this.ctrlSchemeId = ctrlSchemeId;
	}

	/** full constructor */
	public WTmnlReject(String orgNo, String consNo, String tmnlAssetNo,
			Short totalNo, Long ctrlSchemeId, Boolean isReject,
			Boolean ctrlFlag, Boolean isSendSms, String statusCode,
			String staffNo, Date saveTime, Date sendTime, Date successTime,
			String failureCode) {
		this.orgNo = orgNo;
		this.consNo = consNo;
		this.tmnlAssetNo = tmnlAssetNo;
		this.totalNo = totalNo;
		this.ctrlSchemeId = ctrlSchemeId;
		this.isReject = isReject;
		//this.ctrlFlag = ctrlFlag;
		this.isSendSms = isSendSms;
		this.statusCode = statusCode;
		this.staffNo = staffNo;
		this.saveTime = saveTime;
		this.sendTime = sendTime;
		this.successTime = successTime;
		this.failureCode = failureCode;
	}

	// Property accessors

	public Long getTmnlRejectId() {
		return this.tmnlRejectId;
	}

	public void setTmnlRejectId(Long tmnlRejectId) {
		this.tmnlRejectId = tmnlRejectId;
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

	public Boolean getIsReject() {
		return this.isReject;
	}

	public void setIsReject(Boolean isReject) {
		this.isReject = isReject;
	}

/*	public Boolean getCtrlFlag() {
		return this.ctrlFlag;
	}

	public void setCtrlFlag(Boolean ctrlFlag) {
		this.ctrlFlag = ctrlFlag;
	}*/

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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getConsName() {
		return consName;
	}

	public void setConsName(String consName) {
		this.consName = consName;
	}

	public String getTerminalAddr() {
		return terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getEliminateStatus() {
		return eliminateStatus;
	}

	public void setEliminateStatus(String eliminateStatus) {
		this.eliminateStatus = eliminateStatus;
	}

//	public String getExecStatus() {
//		return execStatus;
//	}
//
//	public void setExecStatus(String execStatus) {
//		this.execStatus = execStatus;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
    
}