package com.nari.terminalparam;

import java.util.Date;

/**
 * TTmnlGroup entity. @author MyEclipse Persistence Tools
 */

public class TTmnlGroup implements java.io.Serializable {

	// Fields

	private String groupNo;
	private String groupName;
	private String orgNo;
	private String addr;
	private String groupTypeCode;
	private String ctrlSchemeType;
	private String releaseStatusCode;
	private Boolean isShare;
	private String staffNo;
	private Date createDate;
	private Short validDays;

	// Constructors

	/** default constructor */
	public TTmnlGroup() {
	}

	/** full constructor */
	public TTmnlGroup(String groupName, String orgNo, String addr,
			String groupTypeCode,String ctrlSchemeType,String releaseStatusCode, Boolean isShare,
			String staffNo, Date createDate, Short validDays) {
		this.groupName = groupName;
		this.orgNo = orgNo;
		this.addr = addr;
		this.groupTypeCode = groupTypeCode;
		this.ctrlSchemeType=ctrlSchemeType;
		this.releaseStatusCode = releaseStatusCode;
		this.isShare = isShare;
		this.staffNo = staffNo;
		this.createDate = createDate;
		this.validDays = validDays;
	}

	// Property accessors

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getGroupTypeCode() {
		return this.groupTypeCode;
	}

	public void setGroupTypeCode(String groupTypeCode) {
		this.groupTypeCode = groupTypeCode;
	}

	public String getReleaseStatusCode() {
		return this.releaseStatusCode;
	}

	public void setReleaseStatusCode(String releaseStatusCode) {
		this.releaseStatusCode = releaseStatusCode;
	}

	public Boolean getIsShare() {
		return this.isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
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

	public Short getValidDays() {
		return this.validDays;
	}

	public void setValidDays(Short validDays) {
		this.validDays = validDays;
	}

	public String getCtrlSchemeType() {
		return ctrlSchemeType;
	}

	public void setCtrlSchemeType(String ctrlSchemeType) {
		this.ctrlSchemeType = ctrlSchemeType;
	}
    
}