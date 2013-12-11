package com.nari.orderlypower;

import java.util.Date;

/**
 * WFactctrlTemplate entity. @author MyEclipse Persistence Tools
 */

public class WFactctrlTemplate implements java.io.Serializable {

	// Fields

	private Long templateId;
	private String templateName;
	private String orgNo;
	private String ctrlTime;
	private Byte continueHours;
	private String weekDays;
	private Float factoryConst;
	private Boolean isValid;
	private String staffNo;
	private Date createDate;

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

	public String getCtrlTime() {
		return this.ctrlTime;
	}

	public void setCtrlTime(String ctrlTime) {
		this.ctrlTime = ctrlTime;
	}

	public Byte getContinueHours() {
		return this.continueHours;
	}

	public void setContinueHours(Byte continueHours) {
		this.continueHours = continueHours;
	}

	public String getWeekDays() {
		return this.weekDays;
	}

	public void setWeekDays(String weekDays) {
		this.weekDays = weekDays;
	}

	public Float getFactoryConst() {
		return factoryConst;
	}

	public void setFactoryConst(Float factoryConst) {
		this.factoryConst = factoryConst;
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

	@Override
	public String toString() {
		return "限电起始时间:" + ctrlTime +", 限电延续时间:" +continueHours +", 每周限电日:" + weekDays +", 厂休控定值:"+ factoryConst;
	}
}