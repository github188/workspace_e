package com.nari.orderlypower;

import java.util.Date;

/**
 * WPwrctrlTemplate entity. @author MyEclipse Persistence Tools
 */

public class WPwrctrlTemplate implements java.io.Serializable {

	// Fields

	private Long templateId;
	private String templateName;
	private String orgNo;
	private String cycMode;
	private String cycNo;
	private Boolean isExec;
	private Short floatValue;
	private Boolean isValid;
	private String staffNo;
	private Date createDate;

	// Constructors

	/** default constructor */
	public WPwrctrlTemplate() {
	}

	/** full constructor */
	public WPwrctrlTemplate(String templateName, String orgNo, String cycMode,
			String cycNo, Boolean isExec, Short floatValue, Boolean isValid,
			String staffNo, Date createDate) {
		this.templateName = templateName;
		this.orgNo = orgNo;
		this.cycMode = cycMode;
		this.cycNo = cycNo;
		this.isExec = isExec;
		this.floatValue = floatValue;
		this.isValid = isValid;
		this.staffNo = staffNo;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
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

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
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