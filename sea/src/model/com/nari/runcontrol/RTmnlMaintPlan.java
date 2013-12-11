package com.nari.runcontrol;

/**
 * RTmnlMaintPlan entity. @author MyEclipse Persistence Tools
 */

public class RTmnlMaintPlan implements java.io.Serializable {

	// Fields

	private Long maintPlanId;
	private String chkContent;
	private String materialCode;
	private String planDate;
	private String empName;

	// Constructors

	/** default constructor */
	public RTmnlMaintPlan() {
	}

	/** minimal constructor */
	public RTmnlMaintPlan(String chkContent, String materialCode) {
		this.chkContent = chkContent;
		this.materialCode = materialCode;
	}

	/** full constructor */
	public RTmnlMaintPlan(String chkContent, String materialCode,
			String planDate, String empName) {
		this.chkContent = chkContent;
		this.materialCode = materialCode;
		this.planDate = planDate;
		this.empName = empName;
	}

	// Property accessors

	public Long getMaintPlanId() {
		return this.maintPlanId;
	}

	public void setMaintPlanId(Long maintPlanId) {
		this.maintPlanId = maintPlanId;
	}

	public String getChkContent() {
		return this.chkContent;
	}

	public void setChkContent(String chkContent) {
		this.chkContent = chkContent;
	}

	public String getMaterialCode() {
		return this.materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getPlanDate() {
		return this.planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}