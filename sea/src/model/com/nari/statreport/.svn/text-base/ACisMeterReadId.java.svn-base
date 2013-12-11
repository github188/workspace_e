package com.nari.statreport;

import java.util.Date;

/**
 * ACisMeterReadId entity. @author MyEclipse Persistence Tools
 */

public class ACisMeterReadId implements java.io.Serializable {

	// Fields

	private String orgNo;
	private String consNo;
	private String assetNo;
	private String mrSectNo;
	private Date readDate;
	private Byte floatDays;
	private Date occurTime;

	// Constructors

	/** default constructor */
	public ACisMeterReadId() {
	}

	/** minimal constructor */
	public ACisMeterReadId(String mrSectNo) {
		this.mrSectNo = mrSectNo;
	}

	/** full constructor */
	public ACisMeterReadId(String orgNo, String consNo, String assetNo,
			String mrSectNo, Date readDate, Byte floatDays, Date occurTime) {
		this.orgNo = orgNo;
		this.consNo = consNo;
		this.assetNo = assetNo;
		this.mrSectNo = mrSectNo;
		this.readDate = readDate;
		this.floatDays = floatDays;
		this.occurTime = occurTime;
	}

	// Property accessors

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getConsNo() {
		return this.consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getAssetNo() {
		return this.assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public String getMrSectNo() {
		return this.mrSectNo;
	}

	public void setMrSectNo(String mrSectNo) {
		this.mrSectNo = mrSectNo;
	}

	public Date getReadDate() {
		return this.readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public Byte getFloatDays() {
		return this.floatDays;
	}

	public void setFloatDays(Byte floatDays) {
		this.floatDays = floatDays;
	}

	public Date getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ACisMeterReadId))
			return false;
		ACisMeterReadId castOther = (ACisMeterReadId) other;

		return ((this.getOrgNo() == castOther.getOrgNo()) || (this.getOrgNo() != null
				&& castOther.getOrgNo() != null && this.getOrgNo().equals(
				castOther.getOrgNo())))
				&& ((this.getConsNo() == castOther.getConsNo()) || (this
						.getConsNo() != null
						&& castOther.getConsNo() != null && this.getConsNo()
						.equals(castOther.getConsNo())))
				&& ((this.getAssetNo() == castOther.getAssetNo()) || (this
						.getAssetNo() != null
						&& castOther.getAssetNo() != null && this.getAssetNo()
						.equals(castOther.getAssetNo())))
				&& ((this.getMrSectNo() == castOther.getMrSectNo()) || (this
						.getMrSectNo() != null
						&& castOther.getMrSectNo() != null && this
						.getMrSectNo().equals(castOther.getMrSectNo())))
				&& ((this.getReadDate() == castOther.getReadDate()) || (this
						.getReadDate() != null
						&& castOther.getReadDate() != null && this
						.getReadDate().equals(castOther.getReadDate())))
				&& ((this.getFloatDays() == castOther.getFloatDays()) || (this
						.getFloatDays() != null
						&& castOther.getFloatDays() != null && this
						.getFloatDays().equals(castOther.getFloatDays())))
				&& ((this.getOccurTime() == castOther.getOccurTime()) || (this
						.getOccurTime() != null
						&& castOther.getOccurTime() != null && this
						.getOccurTime().equals(castOther.getOccurTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getOrgNo() == null ? 0 : this.getOrgNo().hashCode());
		result = 37 * result
				+ (getConsNo() == null ? 0 : this.getConsNo().hashCode());
		result = 37 * result
				+ (getAssetNo() == null ? 0 : this.getAssetNo().hashCode());
		result = 37 * result
				+ (getMrSectNo() == null ? 0 : this.getMrSectNo().hashCode());
		result = 37 * result
				+ (getReadDate() == null ? 0 : this.getReadDate().hashCode());
		result = 37 * result
				+ (getFloatDays() == null ? 0 : this.getFloatDays().hashCode());
		result = 37 * result
				+ (getOccurTime() == null ? 0 : this.getOccurTime().hashCode());
		return result;
	}

}