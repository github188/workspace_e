package com.nari.terminalparam;

/**
 * TTmnlGroupDetailId entity. @author MyEclipse Persistence Tools
 */

public class TTmnlGroupDetailId implements java.io.Serializable {

	// Fields

	private String groupNo;
	private String consNo;
	private String tmnlAssetNo;

	// Constructors

	/** default constructor */
	public TTmnlGroupDetailId() {
	}

	/** full constructor */
	public TTmnlGroupDetailId(String groupNo, String consNo, String tmnlAssetNo) {
		this.groupNo = groupNo;
		this.consNo = consNo;
		this.tmnlAssetNo = tmnlAssetNo;
	}

	// Property accessors

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTmnlGroupDetailId))
			return false;
		TTmnlGroupDetailId castOther = (TTmnlGroupDetailId) other;

		return ((this.getGroupNo() == castOther.getGroupNo()) || (this
				.getGroupNo() != null
				&& castOther.getGroupNo() != null && this.getGroupNo().equals(
				castOther.getGroupNo())))
				&& ((this.getConsNo() == castOther.getConsNo()) || (this.getConsNo() != null
						&& castOther.getConsNo() != null && this.getConsNo()
						.equals(castOther.getConsNo())))
				&& ((this.getTmnlAssetNo() == castOther.getTmnlAssetNo()) || (this
						.getTmnlAssetNo() != null
						&& castOther.getTmnlAssetNo() != null && this
						.getTmnlAssetNo().equals(castOther.getTmnlAssetNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getGroupNo() == null ? 0 : this.getGroupNo().hashCode());
		result = 37 * result
				+ (getConsNo() == null ? 0 : this.getConsNo().hashCode());
		result = 37
				* result
				+ (getTmnlAssetNo() == null ? 0 : this.getTmnlAssetNo()
						.hashCode());
		return result;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

}