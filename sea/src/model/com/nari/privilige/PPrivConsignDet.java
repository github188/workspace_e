package com.nari.privilige;

import java.util.Date;

/**
 * PPrivConsignDet entity. @author MyEclipse Persistence Tools
 */

public class PPrivConsignDet implements java.io.Serializable {

	// Fields

	private Long consignDetId;
	private Long userPrivRefId;
	private String consignAppNo;
	private String appEmpNo;
	private String appTypeCode;
	private String appReason;
	private String consigner;
	private String consigneeEmpNo;
	private Date bgnTime;
	private Date endTime;
	private Date deferEndTime;
	private Date actualEndTime;
	private String validFlag;
	private String consignMode;
	private String roleConsignFlag;
	private String privCode;

	// Constructors

	/** default constructor */
	public PPrivConsignDet() {
	}

	/** full constructor */
	public PPrivConsignDet(Long userPrivRefId, String consignAppNo,
			String appEmpNo, String appTypeCode, String appReason,
			String consigner, String consigneeEmpNo, Date bgnTime,
			Date endTime, Date deferEndTime, Date actualEndTime,
			String validFlag, String consignMode, String roleConsignFlag,
			String privCode) {
		this.userPrivRefId = userPrivRefId;
		this.consignAppNo = consignAppNo;
		this.appEmpNo = appEmpNo;
		this.appTypeCode = appTypeCode;
		this.appReason = appReason;
		this.consigner = consigner;
		this.consigneeEmpNo = consigneeEmpNo;
		this.bgnTime = bgnTime;
		this.endTime = endTime;
		this.deferEndTime = deferEndTime;
		this.actualEndTime = actualEndTime;
		this.validFlag = validFlag;
		this.consignMode = consignMode;
		this.roleConsignFlag = roleConsignFlag;
		this.privCode = privCode;
	}

	// Property accessors

	public Long getConsignDetId() {
		return this.consignDetId;
	}

	public void setConsignDetId(Long consignDetId) {
		this.consignDetId = consignDetId;
	}

	public Long getUserPrivRefId() {
		return this.userPrivRefId;
	}

	public void setUserPrivRefId(Long userPrivRefId) {
		this.userPrivRefId = userPrivRefId;
	}

	public String getConsignAppNo() {
		return this.consignAppNo;
	}

	public void setConsignAppNo(String consignAppNo) {
		this.consignAppNo = consignAppNo;
	}

	public String getAppEmpNo() {
		return this.appEmpNo;
	}

	public void setAppEmpNo(String appEmpNo) {
		this.appEmpNo = appEmpNo;
	}

	public String getAppTypeCode() {
		return this.appTypeCode;
	}

	public void setAppTypeCode(String appTypeCode) {
		this.appTypeCode = appTypeCode;
	}

	public String getAppReason() {
		return this.appReason;
	}

	public void setAppReason(String appReason) {
		this.appReason = appReason;
	}

	public String getConsigner() {
		return this.consigner;
	}

	public void setConsigner(String consigner) {
		this.consigner = consigner;
	}

	public String getConsigneeEmpNo() {
		return this.consigneeEmpNo;
	}

	public void setConsigneeEmpNo(String consigneeEmpNo) {
		this.consigneeEmpNo = consigneeEmpNo;
	}

	public Date getBgnTime() {
		return this.bgnTime;
	}

	public void setBgnTime(Date bgnTime) {
		this.bgnTime = bgnTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getDeferEndTime() {
		return this.deferEndTime;
	}

	public void setDeferEndTime(Date deferEndTime) {
		this.deferEndTime = deferEndTime;
	}

	public Date getActualEndTime() {
		return this.actualEndTime;
	}

	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getConsignMode() {
		return this.consignMode;
	}

	public void setConsignMode(String consignMode) {
		this.consignMode = consignMode;
	}

	public String getRoleConsignFlag() {
		return this.roleConsignFlag;
	}

	public void setRoleConsignFlag(String roleConsignFlag) {
		this.roleConsignFlag = roleConsignFlag;
	}

	public String getPrivCode() {
		return this.privCode;
	}

	public void setPrivCode(String privCode) {
		this.privCode = privCode;
	}

}