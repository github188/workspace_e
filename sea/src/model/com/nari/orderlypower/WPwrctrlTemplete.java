package com.nari.orderlypower;

import java.util.Date;

/**
 * WPwrctrlTemplete entity. @author MyEclipse Persistence Tools
 */

public class WPwrctrlTemplete implements java.io.Serializable {

	// Fields

	private long templateId;
	private String templateName;
	private String orgNo;
	private String cycMode;
	private String cycNo;
	private boolean isExec;
	private short floatValue;
	private boolean isValid;
	private String staffNo;
	private Date createDate;

	// Constructors

	/** default constructor */
	public WPwrctrlTemplete() {
	}

	/** full constructor */
	public WPwrctrlTemplete(String templateName, String orgNo, String cycMode,
			String cycNo, boolean isExec, short floatValue, boolean isValid,
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

	public long getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(long templateId) {
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

	public boolean getIsExec() {
		return this.isExec;
	}

	public void setIsExec(boolean isExec) {
		this.isExec = isExec;
	}

	public short getFloatValue() {
		return this.floatValue;
	}

	public void setFloatValue(short floatValue) {
		this.floatValue = floatValue;
	}

	public boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(boolean isValid) {
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