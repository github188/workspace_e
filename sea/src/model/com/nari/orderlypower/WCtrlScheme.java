package com.nari.orderlypower;

import java.util.Date;

/**
 * WCtrlScheme entity. @author MyEclipse Persistence Tools
 */

public class WCtrlScheme implements java.io.Serializable {

	// Fields

	private Long ctrlSchemeId;
	private String orgNo;
	private String ctrlSchemeName;
	private String ctrlSchemeType;
	
	private Date ctrlDateStart;
	private Date ctrlDateEnd;
	private Double ctrlLoad;
	private String limitType;
	private Long isValid;
	
	private String staffNo;
	private String schemeRemark;
	private Date createDate;

	// Constructors

	/** default constructor */
	public WCtrlScheme() {
	}

	/** full constructor */
	public WCtrlScheme(String orgNo, String ctrlSchemeName,
			String ctrlSchemeType, Date ctrlDateStart, Date ctrlDateEnd, 
			Double ctrlLoad, String limitType,Long isValid,String staffNo, String schemeRemark,
			Date createDate) {
		this.orgNo = orgNo;
		this.ctrlSchemeName = ctrlSchemeName;
		this.ctrlSchemeType = ctrlSchemeType;
		this.ctrlDateStart = ctrlDateStart;
		this.ctrlDateEnd = ctrlDateEnd;
		this.ctrlLoad = ctrlLoad;
		this.limitType = limitType;
		this.isValid = isValid;
		this.staffNo = staffNo;
		this.schemeRemark = schemeRemark;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getCtrlSchemeId() {
		return this.ctrlSchemeId;
	}

	public void setCtrlSchemeId(Long ctrlSchemeId) {
		this.ctrlSchemeId = ctrlSchemeId;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getCtrlSchemeName() {
		return this.ctrlSchemeName;
	}

	public void setCtrlSchemeName(String ctrlSchemeName) {
		this.ctrlSchemeName = ctrlSchemeName;
	}

	public String getCtrlSchemeType() {
		return this.ctrlSchemeType;
	}

	public void setCtrlSchemeType(String ctrlSchemeType) {
		this.ctrlSchemeType = ctrlSchemeType;
	}

	public String getStaffNo() {
		return this.staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getSchemeRemark() {
		return this.schemeRemark;
	}

	public void setSchemeRemark(String schemeRemark) {
		this.schemeRemark = schemeRemark;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCtrlDateStart() {
		return ctrlDateStart;
	}

	public void setCtrlDateStart(Date ctrlDateStart) {
		this.ctrlDateStart = ctrlDateStart;
	}

	public Date getCtrlDateEnd() {
		return ctrlDateEnd;
	}

	public void setCtrlDateEnd(Date ctrlDateEnd) {
		this.ctrlDateEnd = ctrlDateEnd;
	}
	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public Double getCtrlLoad() {
		return ctrlLoad;
	}

	public void setCtrlLoad(Double ctrlLoad) {
		this.ctrlLoad = ctrlLoad;
	}

	public Long getIsValid() {
		return isValid;
	}

	public void setIsValid(Long isValid) {
		this.isValid = isValid;
	}
	
}