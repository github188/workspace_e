package com.nari.statreport;

import java.util.Date;

/**
 * AMainPageCurveId entity. @author MyEclipse Persistence Tools
 */

public class AMainPageCurveId implements java.io.Serializable {

	// Fields

	private String orgNo;
	private Date curveDate;
	private String timePoint;
	private Byte consType;
	private Double p;
	private Double ctrlP;

	// Constructors

	/** default constructor */
	public AMainPageCurveId() {
	}

	/** full constructor */
	public AMainPageCurveId(String orgNo, Date curveDate, String timePoint,
			Byte consType, Double p, Double ctrlP) {
		this.orgNo = orgNo;
		this.curveDate = curveDate;
		this.timePoint = timePoint;
		this.consType = consType;
		this.p = p;
		this.ctrlP = ctrlP;
	}

	// Property accessors

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public Date getCurveDate() {
		return this.curveDate;
	}

	public void setCurveDate(Date curveDate) {
		this.curveDate = curveDate;
	}

	public String getTimePoint() {
		return this.timePoint;
	}

	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}

	public Byte getConsType() {
		return this.consType;
	}

	public void setConsType(Byte consType) {
		this.consType = consType;
	}

	public Double getP() {
		return this.p;
	}

	public void setP(Double p) {
		this.p = p;
	}

	public Double getCtrlP() {
		return this.ctrlP;
	}

	public void setCtrlP(Double ctrlP) {
		this.ctrlP = ctrlP;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AMainPageCurveId))
			return false;
		AMainPageCurveId castOther = (AMainPageCurveId) other;

		return ((this.getOrgNo() == castOther.getOrgNo()) || (this.getOrgNo() != null
				&& castOther.getOrgNo() != null && this.getOrgNo().equals(
				castOther.getOrgNo())))
				&& ((this.getCurveDate() == castOther.getCurveDate()) || (this
						.getCurveDate() != null
						&& castOther.getCurveDate() != null && this
						.getCurveDate().equals(castOther.getCurveDate())))
				&& ((this.getTimePoint() == castOther.getTimePoint()) || (this
						.getTimePoint() != null
						&& castOther.getTimePoint() != null && this
						.getTimePoint().equals(castOther.getTimePoint())))
				&& ((this.getConsType() == castOther.getConsType()) || (this
						.getConsType() != null
						&& castOther.getConsType() != null && this
						.getConsType().equals(castOther.getConsType())))
				&& ((this.getP() == castOther.getP()) || (this.getP() != null
						&& castOther.getP() != null && this.getP().equals(
						castOther.getP())))
				&& ((this.getCtrlP() == castOther.getCtrlP()) || (this
						.getCtrlP() != null
						&& castOther.getCtrlP() != null && this.getCtrlP()
						.equals(castOther.getCtrlP())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getOrgNo() == null ? 0 : this.getOrgNo().hashCode());
		result = 37 * result
				+ (getCurveDate() == null ? 0 : this.getCurveDate().hashCode());
		result = 37 * result
				+ (getTimePoint() == null ? 0 : this.getTimePoint().hashCode());
		result = 37 * result
				+ (getConsType() == null ? 0 : this.getConsType().hashCode());
		result = 37 * result + (getP() == null ? 0 : this.getP().hashCode());
		result = 37 * result
				+ (getCtrlP() == null ? 0 : this.getCtrlP().hashCode());
		return result;
	}

}