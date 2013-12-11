package com.nari.orderlypower;

import java.util.Date;

/**
 * WFactctrlTemplete entity. @author MyEclipse Persistence Tools
 */

public class WFactctrlTemplete implements java.io.Serializable {

	// Fields

	private long templateId;
	private String templateName;
	private String orgNo;
	private String ctrlTime;
	private byte continueHours;
	private String weekDays;
	private Integer factoryConst;
	private boolean isValid;
	private String staffNo;
	private Date createDate;

	// Constructors

	/** default constructor */
	public WFactctrlTemplete() {
	}

	/** full constructor */
	public WFactctrlTemplete(String templateName, String orgNo,
			String ctrlTime, byte continueHours, String weekDays,
			Integer factoryConst, boolean isValid, String staffNo,
			Date createDate) {
		this.templateName = templateName;
		this.orgNo = orgNo;
		this.ctrlTime = ctrlTime;
		this.continueHours = continueHours;
		this.weekDays = weekDays;
		this.factoryConst = factoryConst;
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

	public String getCtrlTime() {
		return this.ctrlTime;
	}

	public void setCtrlTime(String ctrlTime) {
		this.ctrlTime = ctrlTime;
	}

	public byte getContinueHours() {
		return this.continueHours;
	}

	public void setContinueHours(byte continueHours) {
		this.continueHours = continueHours;
	}

	public String getWeekDays() {
		return this.weekDays;
	}

	public void setWeekDays(String weekDays) {
		this.weekDays = weekDays;
	}

	public Integer getFactoryConst() {
		return this.factoryConst;
	}

	public void setFactoryConst(Integer factoryConst) {
		this.factoryConst = factoryConst;
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