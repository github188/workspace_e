package com.nari.terminalparam;

import java.util.Date;

/**
 * TTaskTemplate entity. @author MyEclipse Persistence Tools
 */

public class TTaskTemplate implements java.io.Serializable {

	// Fields

	private Long templateId;
	private String orgNo;
	private String templateName;
	private Byte dataType;
	private String taskProperty;
	private Short taskNo;
	private Short freezedDensity;
	private String reportCycleUnit;
	private Integer reportCycle;
	private String referenceTime;
	private Integer randomValue;
	private Short tmnlR;
	private Short masterR;
	private Short dataCount;
	private String recallPolicy;
	private Short samplingPoints;
	private Boolean isShare;
	private Boolean isCancel;
	private Date cancelDate;

	// Constructors

	/** default constructor */
	public TTaskTemplate() {
	}

	/** full constructor */
	public TTaskTemplate(String orgNo, String templateName, Byte dataType,
			String taskProperty, Short taskNo, Short freezedDensity,
			String reportCycleUnit, Integer reportCycle, String referenceTime,
			Integer randomValue, Short tmnlR, Short masterR, Short dataCount,
			String recallPolicy, Short samplingPoints, Boolean isShare,
			Boolean isCancel, Date cancelDate) {
		this.orgNo = orgNo;
		this.templateName = templateName;
		this.dataType = dataType;
		this.taskProperty = taskProperty;
		this.taskNo = taskNo;
		this.freezedDensity = freezedDensity;
		this.reportCycleUnit = reportCycleUnit;
		this.reportCycle = reportCycle;
		this.referenceTime = referenceTime;
		this.randomValue = randomValue;
		this.tmnlR = tmnlR;
		this.masterR = masterR;
		this.dataCount = dataCount;
		this.recallPolicy = recallPolicy;
		this.samplingPoints = samplingPoints;
		this.isShare = isShare;
		this.isCancel = isCancel;
		this.cancelDate = cancelDate;
	}

	// Property accessors

	public Long getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Byte getDataType() {
		return this.dataType;
	}

	public void setDataType(Byte dataType) {
		this.dataType = dataType;
	}

	public String getTaskProperty() {
		return this.taskProperty;
	}

	public void setTaskProperty(String taskProperty) {
		this.taskProperty = taskProperty;
	}

	public Short getTaskNo() {
		return this.taskNo;
	}

	public void setTaskNo(Short taskNo) {
		this.taskNo = taskNo;
	}

	public Short getFreezedDensity() {
		return this.freezedDensity;
	}

	public void setFreezedDensity(Short freezedDensity) {
		this.freezedDensity = freezedDensity;
	}

	public String getReportCycleUnit() {
		return this.reportCycleUnit;
	}

	public void setReportCycleUnit(String reportCycleUnit) {
		this.reportCycleUnit = reportCycleUnit;
	}

	public Integer getReportCycle() {
		return this.reportCycle;
	}

	public void setReportCycle(Integer reportCycle) {
		this.reportCycle = reportCycle;
	}

	public String getReferenceTime() {
		return this.referenceTime;
	}

	public void setReferenceTime(String referenceTime) {
		this.referenceTime = referenceTime;
	}

	public Integer getRandomValue() {
		return this.randomValue;
	}

	public void setRandomValue(Integer randomValue) {
		this.randomValue = randomValue;
	}

	public Short getTmnlR() {
		return this.tmnlR;
	}

	public void setTmnlR(Short tmnlR) {
		this.tmnlR = tmnlR;
	}

	public Short getMasterR() {
		return this.masterR;
	}

	public void setMasterR(Short masterR) {
		this.masterR = masterR;
	}

	public Short getDataCount() {
		return this.dataCount;
	}

	public void setDataCount(Short dataCount) {
		this.dataCount = dataCount;
	}

	public String getRecallPolicy() {
		return this.recallPolicy;
	}

	public void setRecallPolicy(String recallPolicy) {
		this.recallPolicy = recallPolicy;
	}

	public Short getSamplingPoints() {
		return this.samplingPoints;
	}

	public void setSamplingPoints(Short samplingPoints) {
		this.samplingPoints = samplingPoints;
	}

	public Boolean getIsShare() {
		return this.isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public Boolean getIsCancel() {
		return this.isCancel;
	}

	public void setIsCancel(Boolean isCancel) {
		this.isCancel = isCancel;
	}

	public Date getCancelDate() {
		return this.cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

}