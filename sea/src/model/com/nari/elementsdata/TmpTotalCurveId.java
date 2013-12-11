package com.nari.elementsdata;

import java.util.Date;

/**
 * TmpTotalCurveId entity. @author MyEclipse Persistence Tools
 */

public class TmpTotalCurveId implements java.io.Serializable {

	// Fields

	private Long totalId;
	private Date dataTime;
	private Boolean mark;
	private Double p;
	private Double q;
	private Integer e;
	private Integer r;

	// Constructors

	/** default constructor */
	public TmpTotalCurveId() {
	}

	/** full constructor */
	public TmpTotalCurveId(Long totalId, Date dataTime, Boolean mark, Double p,
			Double q, Integer e, Integer r) {
		this.totalId = totalId;
		this.dataTime = dataTime;
		this.mark = mark;
		this.p = p;
		this.q = q;
		this.e = e;
		this.r = r;
	}

	// Property accessors

	public Long getTotalId() {
		return this.totalId;
	}

	public void setTotalId(Long totalId) {
		this.totalId = totalId;
	}

	public Date getDataTime() {
		return this.dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	public Boolean getMark() {
		return this.mark;
	}

	public void setMark(Boolean mark) {
		this.mark = mark;
	}

	public Double getP() {
		return this.p;
	}

	public void setP(Double p) {
		this.p = p;
	}

	public Double getQ() {
		return this.q;
	}

	public void setQ(Double q) {
		this.q = q;
	}

	public Integer getE() {
		return this.e;
	}

	public void setE(Integer e) {
		this.e = e;
	}

	public Integer getR() {
		return this.r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TmpTotalCurveId))
			return false;
		TmpTotalCurveId castOther = (TmpTotalCurveId) other;

		return ((this.getTotalId() == castOther.getTotalId()) || (this
				.getTotalId() != null
				&& castOther.getTotalId() != null && this.getTotalId().equals(
				castOther.getTotalId())))
				&& ((this.getDataTime() == castOther.getDataTime()) || (this
						.getDataTime() != null
						&& castOther.getDataTime() != null && this
						.getDataTime().equals(castOther.getDataTime())))
				&& ((this.getMark() == castOther.getMark()) || (this.getMark() != null
						&& castOther.getMark() != null && this.getMark()
						.equals(castOther.getMark())))
				&& ((this.getP() == castOther.getP()) || (this.getP() != null
						&& castOther.getP() != null && this.getP().equals(
						castOther.getP())))
				&& ((this.getQ() == castOther.getQ()) || (this.getQ() != null
						&& castOther.getQ() != null && this.getQ().equals(
						castOther.getQ())))
				&& ((this.getE() == castOther.getE()) || (this.getE() != null
						&& castOther.getE() != null && this.getE().equals(
						castOther.getE())))
				&& ((this.getR() == castOther.getR()) || (this.getR() != null
						&& castOther.getR() != null && this.getR().equals(
						castOther.getR())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTotalId() == null ? 0 : this.getTotalId().hashCode());
		result = 37 * result
				+ (getDataTime() == null ? 0 : this.getDataTime().hashCode());
		result = 37 * result
				+ (getMark() == null ? 0 : this.getMark().hashCode());
		result = 37 * result + (getP() == null ? 0 : this.getP().hashCode());
		result = 37 * result + (getQ() == null ? 0 : this.getQ().hashCode());
		result = 37 * result + (getE() == null ? 0 : this.getE().hashCode());
		result = 37 * result + (getR() == null ? 0 : this.getR().hashCode());
		return result;
	}

}