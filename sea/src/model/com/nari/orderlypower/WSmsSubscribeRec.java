package com.nari.orderlypower;

import java.util.Date;

/**
 * WSmsSubscribeRec entity. @author MyEclipse Persistence Tools
 */

public class WSmsSubscribeRec implements java.io.Serializable {

	// Fields

	private Long subscribeRecId;
	private Long sendRecId;
	private String mobileNo;
	private String sendContent;
	private Date sendTime;
	private Byte sendMode;

	// Constructors

	/** default constructor */
	public WSmsSubscribeRec() {
	}

	/** minimal constructor */
	public WSmsSubscribeRec(Long sendRecId) {
		this.sendRecId = sendRecId;
	}

	/** full constructor */
	public WSmsSubscribeRec(Long sendRecId, String mobileNo,
			String sendContent, Date sendTime, Byte sendMode) {
		this.sendRecId = sendRecId;
		this.mobileNo = mobileNo;
		this.sendContent = sendContent;
		this.sendTime = sendTime;
		this.sendMode = sendMode;
	}

	// Property accessors

	public Long getSubscribeRecId() {
		return this.subscribeRecId;
	}

	public void setSubscribeRecId(Long subscribeRecId) {
		this.subscribeRecId = subscribeRecId;
	}

	public Long getSendRecId() {
		return this.sendRecId;
	}

	public void setSendRecId(Long sendRecId) {
		this.sendRecId = sendRecId;
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

}