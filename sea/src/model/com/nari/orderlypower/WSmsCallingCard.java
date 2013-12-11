package com.nari.orderlypower;

/**
 * WSmsCallingCard entity. @author MyEclipse Persistence Tools
 */

public class WSmsCallingCard implements java.io.Serializable {

	// Fields

	private Long callingCardId;
	private String orgNo;
	private String staffName;
	private String mobileNo;
	private String position;

	// Constructors

	/** default constructor */
	public WSmsCallingCard() {
	}

	/** full constructor */
	public WSmsCallingCard(String orgNo, String staffName, String mobileNo,
			String position) {
		this.orgNo = orgNo;
		this.staffName = staffName;
		this.mobileNo = mobileNo;
		this.position = position;
	}

	// Property accessors

	public Long getCallingCardId() {
		return this.callingCardId;
	}

	public void setCallingCardId(Long callingCardId) {
		this.callingCardId = callingCardId;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getStaffName() {
		return this.staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}