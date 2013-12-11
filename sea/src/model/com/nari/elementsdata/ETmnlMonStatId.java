package com.nari.elementsdata;

import java.util.Date;

/**
 * ETmnlMonStatId entity. @author MyEclipse Persistence Tools
 */

public class ETmnlMonStatId implements java.io.Serializable {

	// Fields

	private String tmnlAssetNo;
	private Date dataDate;
	private Boolean mark;
	private Integer suplyTime;
	private Integer resetNum;
	private Short monEcjumpNum;
	private Short monBcjumpNum;
	private Short monPcjumpNum;
	private Short monRcjumpNum;
	private Long monComm;

	// Constructors

	/** default constructor */
	public ETmnlMonStatId() {
	}

	/** full constructor */
	public ETmnlMonStatId(String tmnlAssetNo, Date dataDate, Boolean mark,
			Integer suplyTime, Integer resetNum, Short monEcjumpNum,
			Short monBcjumpNum, Short monPcjumpNum, Short monRcjumpNum,
			Long monComm) {
		this.tmnlAssetNo = tmnlAssetNo;
		this.dataDate = dataDate;
		this.mark = mark;
		this.suplyTime = suplyTime;
		this.resetNum = resetNum;
		this.monEcjumpNum = monEcjumpNum;
		this.monBcjumpNum = monBcjumpNum;
		this.monPcjumpNum = monPcjumpNum;
		this.monRcjumpNum = monRcjumpNum;
		this.monComm = monComm;
	}

	// Property accessors

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public Date getDataDate() {
		return this.dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Boolean getMark() {
		return this.mark;
	}

	public void setMark(Boolean mark) {
		this.mark = mark;
	}

	public Integer getSuplyTime() {
		return this.suplyTime;
	}

	public void setSuplyTime(Integer suplyTime) {
		this.suplyTime = suplyTime;
	}

	public Integer getResetNum() {
		return this.resetNum;
	}

	public void setResetNum(Integer resetNum) {
		this.resetNum = resetNum;
	}

	public Short getMonEcjumpNum() {
		return this.monEcjumpNum;
	}

	public void setMonEcjumpNum(Short monEcjumpNum) {
		this.monEcjumpNum = monEcjumpNum;
	}

	public Short getMonBcjumpNum() {
		return this.monBcjumpNum;
	}

	public void setMonBcjumpNum(Short monBcjumpNum) {
		this.monBcjumpNum = monBcjumpNum;
	}

	public Short getMonPcjumpNum() {
		return this.monPcjumpNum;
	}

	public void setMonPcjumpNum(Short monPcjumpNum) {
		this.monPcjumpNum = monPcjumpNum;
	}

	public Short getMonRcjumpNum() {
		return this.monRcjumpNum;
	}

	public void setMonRcjumpNum(Short monRcjumpNum) {
		this.monRcjumpNum = monRcjumpNum;
	}

	public Long getMonComm() {
		return this.monComm;
	}

	public void setMonComm(Long monComm) {
		this.monComm = monComm;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ETmnlMonStatId))
			return false;
		ETmnlMonStatId castOther = (ETmnlMonStatId) other;

		return ((this.getTmnlAssetNo() == castOther.getTmnlAssetNo()) || (this
				.getTmnlAssetNo() != null
				&& castOther.getTmnlAssetNo() != null && this.getTmnlAssetNo()
				.equals(castOther.getTmnlAssetNo())))
				&& ((this.getDataDate() == castOther.getDataDate()) || (this
						.getDataDate() != null
						&& castOther.getDataDate() != null && this
						.getDataDate().equals(castOther.getDataDate())))
				&& ((this.getMark() == castOther.getMark()) || (this.getMark() != null
						&& castOther.getMark() != null && this.getMark()
						.equals(castOther.getMark())))
				&& ((this.getSuplyTime() == castOther.getSuplyTime()) || (this
						.getSuplyTime() != null
						&& castOther.getSuplyTime() != null && this
						.getSuplyTime().equals(castOther.getSuplyTime())))
				&& ((this.getResetNum() == castOther.getResetNum()) || (this
						.getResetNum() != null
						&& castOther.getResetNum() != null && this
						.getResetNum().equals(castOther.getResetNum())))
				&& ((this.getMonEcjumpNum() == castOther.getMonEcjumpNum()) || (this
						.getMonEcjumpNum() != null
						&& castOther.getMonEcjumpNum() != null && this
						.getMonEcjumpNum().equals(castOther.getMonEcjumpNum())))
				&& ((this.getMonBcjumpNum() == castOther.getMonBcjumpNum()) || (this
						.getMonBcjumpNum() != null
						&& castOther.getMonBcjumpNum() != null && this
						.getMonBcjumpNum().equals(castOther.getMonBcjumpNum())))
				&& ((this.getMonPcjumpNum() == castOther.getMonPcjumpNum()) || (this
						.getMonPcjumpNum() != null
						&& castOther.getMonPcjumpNum() != null && this
						.getMonPcjumpNum().equals(castOther.getMonPcjumpNum())))
				&& ((this.getMonRcjumpNum() == castOther.getMonRcjumpNum()) || (this
						.getMonRcjumpNum() != null
						&& castOther.getMonRcjumpNum() != null && this
						.getMonRcjumpNum().equals(castOther.getMonRcjumpNum())))
				&& ((this.getMonComm() == castOther.getMonComm()) || (this
						.getMonComm() != null
						&& castOther.getMonComm() != null && this.getMonComm()
						.equals(castOther.getMonComm())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTmnlAssetNo() == null ? 0 : this.getTmnlAssetNo()
						.hashCode());
		result = 37 * result
				+ (getDataDate() == null ? 0 : this.getDataDate().hashCode());
		result = 37 * result
				+ (getMark() == null ? 0 : this.getMark().hashCode());
		result = 37 * result
				+ (getSuplyTime() == null ? 0 : this.getSuplyTime().hashCode());
		result = 37 * result
				+ (getResetNum() == null ? 0 : this.getResetNum().hashCode());
		result = 37
				* result
				+ (getMonEcjumpNum() == null ? 0 : this.getMonEcjumpNum()
						.hashCode());
		result = 37
				* result
				+ (getMonBcjumpNum() == null ? 0 : this.getMonBcjumpNum()
						.hashCode());
		result = 37
				* result
				+ (getMonPcjumpNum() == null ? 0 : this.getMonPcjumpNum()
						.hashCode());
		result = 37
				* result
				+ (getMonRcjumpNum() == null ? 0 : this.getMonRcjumpNum()
						.hashCode());
		result = 37 * result
				+ (getMonComm() == null ? 0 : this.getMonComm().hashCode());
		return result;
	}

}