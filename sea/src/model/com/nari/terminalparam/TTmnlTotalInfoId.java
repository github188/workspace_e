package com.nari.terminalparam;

/**
 * TTmnlTotalInfoId entity. @author MyEclipse Persistence Tools
 */

public class TTmnlTotalInfoId implements java.io.Serializable {

	// Fields

	private String orgNo;
	private String cpNo;
	private String consNo;
	private String tmnlAssetNo;
	private String meterAssetNo;
	private String mpSn;
	private String pulseAttr;
	private Boolean calcFlag;
	private Short totalNo;

	// Constructors

	/** default constructor */
	public TTmnlTotalInfoId() {
	}

	/** full constructor */
	public TTmnlTotalInfoId(String orgNo, String cpNo, String consNo,
			String tmnlAssetNo, String meterAssetNo, String mpSn,
			String pulseAttr, Boolean calcFlag, Short totalNo) {
		this.orgNo = orgNo;
		this.cpNo = cpNo;
		this.consNo = consNo;
		this.tmnlAssetNo = tmnlAssetNo;
		this.meterAssetNo = meterAssetNo;
		this.mpSn = mpSn;
		this.pulseAttr = pulseAttr;
		this.calcFlag = calcFlag;
		this.totalNo = totalNo;
	}

	// Property accessors

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getCpNo() {
		return this.cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

	public String getConsNo() {
		return this.consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getMeterAssetNo() {
		return this.meterAssetNo;
	}

	public void setMeterAssetNo(String meterAssetNo) {
		this.meterAssetNo = meterAssetNo;
	}

	public String getMpSn() {
		return this.mpSn;
	}

	public void setMpSn(String mpSn) {
		this.mpSn = mpSn;
	}

	public String getPulseAttr() {
		return this.pulseAttr;
	}

	public void setPulseAttr(String pulseAttr) {
		this.pulseAttr = pulseAttr;
	}

	public Boolean getCalcFlag() {
		return this.calcFlag;
	}

	public void setCalcFlag(Boolean calcFlag) {
		this.calcFlag = calcFlag;
	}

	public Short getTotalNo() {
		return this.totalNo;
	}

	public void setTotalNo(Short totalNo) {
		this.totalNo = totalNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTmnlTotalInfoId))
			return false;
		TTmnlTotalInfoId castOther = (TTmnlTotalInfoId) other;

		return ((this.getOrgNo() == castOther.getOrgNo()) || (this.getOrgNo() != null
				&& castOther.getOrgNo() != null && this.getOrgNo().equals(
				castOther.getOrgNo())))
				&& ((this.getCpNo() == castOther.getCpNo()) || (this.getCpNo() != null
						&& castOther.getCpNo() != null && this.getCpNo()
						.equals(castOther.getCpNo())))
				&& ((this.getConsNo() == castOther.getConsNo()) || (this
						.getConsNo() != null
						&& castOther.getConsNo() != null && this.getConsNo()
						.equals(castOther.getConsNo())))
				&& ((this.getTmnlAssetNo() == castOther.getTmnlAssetNo()) || (this
						.getTmnlAssetNo() != null
						&& castOther.getTmnlAssetNo() != null && this
						.getTmnlAssetNo().equals(castOther.getTmnlAssetNo())))
				&& ((this.getMeterAssetNo() == castOther.getMeterAssetNo()) || (this
						.getMeterAssetNo() != null
						&& castOther.getMeterAssetNo() != null && this
						.getMeterAssetNo().equals(castOther.getMeterAssetNo())))
				&& ((this.getMpSn() == castOther.getMpSn()) || (this.getMpSn() != null
						&& castOther.getMpSn() != null && this.getMpSn()
						.equals(castOther.getMpSn())))
				&& ((this.getPulseAttr() == castOther.getPulseAttr()) || (this
						.getPulseAttr() != null
						&& castOther.getPulseAttr() != null && this
						.getPulseAttr().equals(castOther.getPulseAttr())))
				&& ((this.getCalcFlag() == castOther.getCalcFlag()) || (this
						.getCalcFlag() != null
						&& castOther.getCalcFlag() != null && this
						.getCalcFlag().equals(castOther.getCalcFlag())))
				&& ((this.getTotalNo() == castOther.getTotalNo()) || (this
						.getTotalNo() != null
						&& castOther.getTotalNo() != null && this.getTotalNo()
						.equals(castOther.getTotalNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getOrgNo() == null ? 0 : this.getOrgNo().hashCode());
		result = 37 * result
				+ (getCpNo() == null ? 0 : this.getCpNo().hashCode());
		result = 37 * result
				+ (getConsNo() == null ? 0 : this.getConsNo().hashCode());
		result = 37
				* result
				+ (getTmnlAssetNo() == null ? 0 : this.getTmnlAssetNo()
						.hashCode());
		result = 37
				* result
				+ (getMeterAssetNo() == null ? 0 : this.getMeterAssetNo()
						.hashCode());
		result = 37 * result
				+ (getMpSn() == null ? 0 : this.getMpSn().hashCode());
		result = 37 * result
				+ (getPulseAttr() == null ? 0 : this.getPulseAttr().hashCode());
		result = 37 * result
				+ (getCalcFlag() == null ? 0 : this.getCalcFlag().hashCode());
		result = 37 * result
				+ (getTotalNo() == null ? 0 : this.getTotalNo().hashCode());
		return result;
	}

}