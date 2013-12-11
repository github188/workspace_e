package com.nari.orderlypower;

import java.util.Date;

/**
 * WSmsSubscribe entity. @author MyEclipse Persistence Tools
 */

public class WSmsSubscribe implements java.io.Serializable {

	// Fields

	private Long subscribeId;
	private String orgNo;
	private String subscribeName;
	private String eventNo;
	private Date startDate;
	private Date endDate;
	private String sendTimeS;
	private String sendTimeE;
	private String staffNo;
	private Date createDate;

	// Constructors

	/** default constructor */
	public WSmsSubscribe() {
	}

	/** minimal constructor */
	public WSmsSubscribe(String eventNo) {
		this.eventNo = eventNo;
	}

	/** full constructor */
	public WSmsSubscribe(String orgNo, String subscribeName, String eventNo,
			Date startDate, Date endDate, String sendTimeS, String sendTimeE,
			String staffNo, Date createDate) {
		this.orgNo = orgNo;
		this.subscribeName = subscribeName;
		this.eventNo = eventNo;
		this.startDate = startDate;
		this.endDate = endDate;
		this.sendTimeS = sendTimeS;
		this.sendTimeE = sendTimeE;
		this.staffNo = staffNo;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getSubscribeId() {
		return this.subscribeId;
	}

	public void setSubscribeId(Long subscribeId) {
		this.subscribeId = subscribeId;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getSubscribeName() {
		return this.subscribeName;
	}

	public void setSubscribeName(String subscribeName) {
		this.subscribeName = subscribeName;
	}

	public String getEventNo() {
		return this.eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSendTimeS() {
		return this.sendTimeS;
	}

	public void setSendTimeS(String sendTimeS) {
		this.sendTimeS = sendTimeS;
	}

	public String getSendTimeE() {
		return this.sendTimeE;
	}

	public void setSendTimeE(String sendTimeE) {
		this.sendTimeE = sendTimeE;
	}

	public String getStaffNo() {
		return this.staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}