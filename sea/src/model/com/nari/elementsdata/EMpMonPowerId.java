package com.nari.elementsdata;

import java.util.Date;

/**
 * EMpMonPowerId entity. @author MyEclipse Persistence Tools
 */

public class EMpMonPowerId implements java.io.Serializable {

	// Fields

	private Long dataId;
	private Date dataDate;
	private Double ct;
	private Double pt;
	private Boolean mark;
	private Double totalMaxP;
	private Date totalMaxPTime;
	private Double totalMaxPa;
	private Date totalMaxPaTime;
	private Double totalMaxPb;
	private Date totalMaxPbTime;
	private Double totalMaxPc;
	private Date totalMaxPcTime;
	private Integer totalPZero;
	private Integer totalPaZero;
	private Integer totalPbZero;
	private Integer totalPcZero;

	// Constructors

	/** default constructor */
	public EMpMonPowerId() {
	}

	/** minimal constructor */
	public EMpMonPowerId(Long dataId, Date dataDate) {
		this.dataId = dataId;
		this.dataDate = dataDate;
	}

	/** full constructor */
	public EMpMonPowerId(Long dataId, Date dataDate, Double ct, Double pt,
			Boolean mark, Double totalMaxP, Date totalMaxPTime,
			Double totalMaxPa, Date totalMaxPaTime, Double totalMaxPb,
			Date totalMaxPbTime, Double totalMaxPc, Date totalMaxPcTime,
			Integer totalPZero, Integer totalPaZero, Integer totalPbZero,
			Integer totalPcZero) {
		this.dataId = dataId;
		this.dataDate = dataDate;
		this.ct = ct;
		this.pt = pt;
		this.mark = mark;
		this.totalMaxP = totalMaxP;
		this.totalMaxPTime = totalMaxPTime;
		this.totalMaxPa = totalMaxPa;
		this.totalMaxPaTime = totalMaxPaTime;
		this.totalMaxPb = totalMaxPb;
		this.totalMaxPbTime = totalMaxPbTime;
		this.totalMaxPc = totalMaxPc;
		this.totalMaxPcTime = totalMaxPcTime;
		this.totalPZero = totalPZero;
		this.totalPaZero = totalPaZero;
		this.totalPbZero = totalPbZero;
		this.totalPcZero = totalPcZero;
	}

	// Property accessors

	public Long getDataId() {
		return this.dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public Date getDataDate() {
		return this.dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Double getCt() {
		return this.ct;
	}

	public void setCt(Double ct) {
		this.ct = ct;
	}

	public Double getPt() {
		return this.pt;
	}

	public void setPt(Double pt) {
		this.pt = pt;
	}

	public Boolean getMark() {
		return this.mark;
	}

	public void setMark(Boolean mark) {
		this.mark = mark;
	}

	public Double getTotalMaxP() {
		return this.totalMaxP;
	}

	public void setTotalMaxP(Double totalMaxP) {
		this.totalMaxP = totalMaxP;
	}

	public Date getTotalMaxPTime() {
		return this.totalMaxPTime;
	}

	public void setTotalMaxPTime(Date totalMaxPTime) {
		this.totalMaxPTime = totalMaxPTime;
	}

	public Double getTotalMaxPa() {
		return this.totalMaxPa;
	}

	public void setTotalMaxPa(Double totalMaxPa) {
		this.totalMaxPa = totalMaxPa;
	}

	public Date getTotalMaxPaTime() {
		return this.totalMaxPaTime;
	}

	public void setTotalMaxPaTime(Date totalMaxPaTime) {
		this.totalMaxPaTime = totalMaxPaTime;
	}

	public Double getTotalMaxPb() {
		return this.totalMaxPb;
	}

	public void setTotalMaxPb(Double totalMaxPb) {
		this.totalMaxPb = totalMaxPb;
	}

	public Date getTotalMaxPbTime() {
		return this.totalMaxPbTime;
	}

	public void setTotalMaxPbTime(Date totalMaxPbTime) {
		this.totalMaxPbTime = totalMaxPbTime;
	}

	public Double getTotalMaxPc() {
		return this.totalMaxPc;
	}

	public void setTotalMaxPc(Double totalMaxPc) {
		this.totalMaxPc = totalMaxPc;
	}

	public Date getTotalMaxPcTime() {
		return this.totalMaxPcTime;
	}

	public void setTotalMaxPcTime(Date totalMaxPcTime) {
		this.totalMaxPcTime = totalMaxPcTime;
	}

	public Integer getTotalPZero() {
		return this.totalPZero;
	}

	public void setTotalPZero(Integer totalPZero) {
		this.totalPZero = totalPZero;
	}

	public Integer getTotalPaZero() {
		return this.totalPaZero;
	}

	public void setTotalPaZero(Integer totalPaZero) {
		this.totalPaZero = totalPaZero;
	}

	public Integer getTotalPbZero() {
		return this.totalPbZero;
	}

	public void setTotalPbZero(Integer totalPbZero) {
		this.totalPbZero = totalPbZero;
	}

	public Integer getTotalPcZero() {
		return this.totalPcZero;
	}

	public void setTotalPcZero(Integer totalPcZero) {
		this.totalPcZero = totalPcZero;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EMpMonPowerId))
			return false;
		EMpMonPowerId castOther = (EMpMonPowerId) other;

		return ((this.getDataId() == castOther.getDataId()) || (this
				.getDataId() != null
				&& castOther.getDataId() != null && this.getDataId().equals(
				castOther.getDataId())))
				&& ((this.getDataDate() == castOther.getDataDate()) || (this
						.getDataDate() != null
						&& castOther.getDataDate() != null && this
						.getDataDate().equals(castOther.getDataDate())))
				&& ((this.getCt() == castOther.getCt()) || (this.getCt() != null
						&& castOther.getCt() != null && this.getCt().equals(
						castOther.getCt())))
				&& ((this.getPt() == castOther.getPt()) || (this.getPt() != null
						&& castOther.getPt() != null && this.getPt().equals(
						castOther.getPt())))
				&& ((this.getMark() == castOther.getMark()) || (this.getMark() != null
						&& castOther.getMark() != null && this.getMark()
						.equals(castOther.getMark())))
				&& ((this.getTotalMaxP() == castOther.getTotalMaxP()) || (this
						.getTotalMaxP() != null
						&& castOther.getTotalMaxP() != null && this
						.getTotalMaxP().equals(castOther.getTotalMaxP())))
				&& ((this.getTotalMaxPTime() == castOther.getTotalMaxPTime()) || (this
						.getTotalMaxPTime() != null
						&& castOther.getTotalMaxPTime() != null && this
						.getTotalMaxPTime()
						.equals(castOther.getTotalMaxPTime())))
				&& ((this.getTotalMaxPa() == castOther.getTotalMaxPa()) || (this
						.getTotalMaxPa() != null
						&& castOther.getTotalMaxPa() != null && this
						.getTotalMaxPa().equals(castOther.getTotalMaxPa())))
				&& ((this.getTotalMaxPaTime() == castOther.getTotalMaxPaTime()) || (this
						.getTotalMaxPaTime() != null
						&& castOther.getTotalMaxPaTime() != null && this
						.getTotalMaxPaTime().equals(
								castOther.getTotalMaxPaTime())))
				&& ((this.getTotalMaxPb() == castOther.getTotalMaxPb()) || (this
						.getTotalMaxPb() != null
						&& castOther.getTotalMaxPb() != null && this
						.getTotalMaxPb().equals(castOther.getTotalMaxPb())))
				&& ((this.getTotalMaxPbTime() == castOther.getTotalMaxPbTime()) || (this
						.getTotalMaxPbTime() != null
						&& castOther.getTotalMaxPbTime() != null && this
						.getTotalMaxPbTime().equals(
								castOther.getTotalMaxPbTime())))
				&& ((this.getTotalMaxPc() == castOther.getTotalMaxPc()) || (this
						.getTotalMaxPc() != null
						&& castOther.getTotalMaxPc() != null && this
						.getTotalMaxPc().equals(castOther.getTotalMaxPc())))
				&& ((this.getTotalMaxPcTime() == castOther.getTotalMaxPcTime()) || (this
						.getTotalMaxPcTime() != null
						&& castOther.getTotalMaxPcTime() != null && this
						.getTotalMaxPcTime().equals(
								castOther.getTotalMaxPcTime())))
				&& ((this.getTotalPZero() == castOther.getTotalPZero()) || (this
						.getTotalPZero() != null
						&& castOther.getTotalPZero() != null && this
						.getTotalPZero().equals(castOther.getTotalPZero())))
				&& ((this.getTotalPaZero() == castOther.getTotalPaZero()) || (this
						.getTotalPaZero() != null
						&& castOther.getTotalPaZero() != null && this
						.getTotalPaZero().equals(castOther.getTotalPaZero())))
				&& ((this.getTotalPbZero() == castOther.getTotalPbZero()) || (this
						.getTotalPbZero() != null
						&& castOther.getTotalPbZero() != null && this
						.getTotalPbZero().equals(castOther.getTotalPbZero())))
				&& ((this.getTotalPcZero() == castOther.getTotalPcZero()) || (this
						.getTotalPcZero() != null
						&& castOther.getTotalPcZero() != null && this
						.getTotalPcZero().equals(castOther.getTotalPcZero())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDataId() == null ? 0 : this.getDataId().hashCode());
		result = 37 * result
				+ (getDataDate() == null ? 0 : this.getDataDate().hashCode());
		result = 37 * result + (getCt() == null ? 0 : this.getCt().hashCode());
		result = 37 * result + (getPt() == null ? 0 : this.getPt().hashCode());
		result = 37 * result
				+ (getMark() == null ? 0 : this.getMark().hashCode());
		result = 37 * result
				+ (getTotalMaxP() == null ? 0 : this.getTotalMaxP().hashCode());
		result = 37
				* result
				+ (getTotalMaxPTime() == null ? 0 : this.getTotalMaxPTime()
						.hashCode());
		result = 37
				* result
				+ (getTotalMaxPa() == null ? 0 : this.getTotalMaxPa()
						.hashCode());
		result = 37
				* result
				+ (getTotalMaxPaTime() == null ? 0 : this.getTotalMaxPaTime()
						.hashCode());
		result = 37
				* result
				+ (getTotalMaxPb() == null ? 0 : this.getTotalMaxPb()
						.hashCode());
		result = 37
				* result
				+ (getTotalMaxPbTime() == null ? 0 : this.getTotalMaxPbTime()
						.hashCode());
		result = 37
				* result
				+ (getTotalMaxPc() == null ? 0 : this.getTotalMaxPc()
						.hashCode());
		result = 37
				* result
				+ (getTotalMaxPcTime() == null ? 0 : this.getTotalMaxPcTime()
						.hashCode());
		result = 37
				* result
				+ (getTotalPZero() == null ? 0 : this.getTotalPZero()
						.hashCode());
		result = 37
				* result
				+ (getTotalPaZero() == null ? 0 : this.getTotalPaZero()
						.hashCode());
		result = 37
				* result
				+ (getTotalPbZero() == null ? 0 : this.getTotalPbZero()
						.hashCode());
		result = 37
				* result
				+ (getTotalPcZero() == null ? 0 : this.getTotalPcZero()
						.hashCode());
		return result;
	}

}