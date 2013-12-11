package com.nari.runcontrol;

import java.util.Date;

/**
 * RPatrolPlan entity. @author MyEclipse Persistence Tools
 */

public class RPatrolPlan implements java.io.Serializable {

	// Fields

	private Long siteinspPlanId;
	private String patrolCotent;
	private String safetyMeas;
	private Date planInspectDate;
	private String planInspectorNo;

	// Constructors

	/** default constructor */
	public RPatrolPlan() {
	}

	/** minimal constructor */
	public RPatrolPlan(String patrolCotent, String safetyMeas) {
		this.patrolCotent = patrolCotent;
		this.safetyMeas = safetyMeas;
	}

	/** full constructor */
	public RPatrolPlan(String patrolCotent, String safetyMeas,
			Date planInspectDate, String planInspectorNo) {
		this.patrolCotent = patrolCotent;
		this.safetyMeas = safetyMeas;
		this.planInspectDate = planInspectDate;
		this.planInspectorNo = planInspectorNo;
	}

	// Property accessors

	public Long getSiteinspPlanId() {
		return this.siteinspPlanId;
	}

	public void setSiteinspPlanId(Long siteinspPlanId) {
		this.siteinspPlanId = siteinspPlanId;
	}

	public String getPatrolCotent() {
		return this.patrolCotent;
	}

	public void setPatrolCotent(String patrolCotent) {
		this.patrolCotent = patrolCotent;
	}

	public String getSafetyMeas() {
		return this.safetyMeas;
	}

	public void setSafetyMeas(String safetyMeas) {
		this.safetyMeas = safetyMeas;
	}

	public Date getPlanInspectDate() {
		return this.planInspectDate;
	}

	public void setPlanInspectDate(Date planInspectDate) {
		this.planInspectDate = planInspectDate;
	}

	public String getPlanInspectorNo() {
		return this.planInspectorNo;
	}

	public void setPlanInspectorNo(String planInspectorNo) {
		this.planInspectorNo = planInspectorNo;
	}

}