package com.nari.orderlypower;

import java.util.Date;

/**
 * WSmsSendRec entity. @author MyEclipse Persistence Tools
 */

public class WSmsSendRec implements java.io.Serializable {

	// Fields

	private Long sendRecId;
	private String sendStaffNo;
	private String orgNo;
	private String consNo;
	private String contactMode;
	private String userName;
	private String mobileNo;
	private String sendContent;
	private Date sendTime;
	private Byte sendMode;
	private Byte sendStatus;
	private Short sendCounts;

	// Constructors

	/** default constructor */
	public WSmsSendRec() {
	}

	/** full constructor */
	public WSmsSendRec(String sendStaffNo, String orgNo, String consNo,
			String contactMode, String userName, String mobileNo,
			String sendContent, Date sendTime, Byte sendMode, Byte sendStatus,
			Short sendCounts) {
		this.sendStaffNo = sendStaffNo;
		this.orgNo = orgNo;
		this.consNo = consNo;
		this.contactMode = contactMode;
		this.userName = userName;
		this.mobileNo = mobileNo;
		this.sendContent = sendContent;
		this.sendTime = sendTime;
		this.sendMode = sendMode;
		this.sendStatus = sendStatus;
		this.sendCounts = sendCounts;
	}

	// Property accessors

	public Long getSendRecId() {
		return this.sendRecId;
	}

	public void setSendRecId(Long sendRecId) {
		this.sendRecId = sendRecId;
	}

	public String getSendStaffNo() {
		return this.sendStaffNo;
	}

	public void setSendStaffNo(String sendStaffNo) {
		this.sendStaffNo = sendStaffNo;
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

	public String getContactMode() {
		return this.contactMode;
	}

	public void setContactMode(String contactMode) {
		this.contactMode = contactMode;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getSendContent() {
		return this.sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Byte getSendMode() {
		return this.sendMode;
	}

	public void setSendMode(Byte sendMode) {
		this.sendMode = sendMode;
	}

	public Byte getSendStatus() {
		return this.sendStatus;
	}

	public void setSendStatus(Byte sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Short getSendCounts() {
		return this.sendCounts;
	}

	public void setSendCounts(Short sendCounts) {
		this.sendCounts = sendCounts;
	}

}