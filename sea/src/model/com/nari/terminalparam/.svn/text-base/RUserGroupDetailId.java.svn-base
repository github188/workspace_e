package com.nari.terminalparam;

/**
 * RUserGroupDetailId entity. @author MyEclipse Persistence Tools
 */

public class RUserGroupDetailId implements java.io.Serializable {

	// Fields

	private String groupNo;
	private String tmnlAssetNo;
	private String consNo;

	// Constructors

	/** default constructor */
	public RUserGroupDetailId() {
	}

	/** full constructor */
	public RUserGroupDetailId(String groupNo, String tmnlAssetNo, String consNo) {
		this.groupNo = groupNo;
		this.tmnlAssetNo = tmnlAssetNo;
		this.consNo = consNo;
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

	public String getConsNo() {
		return this.consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RUserGroupDetailId))
			return false;
		RUserGroupDetailId castOther = (RUserGroupDetailId) other;

		return ((this.getGroupNo() == castOther.getGroupNo()) || (this
				.getGroupNo() != null
				&& castOther.getGroupNo() != null && this.getGroupNo().equals(
				castOther.getGroupNo())))
				&& ((this.getTmnlAssetNo() == castOther.getTmnlAssetNo()) || (this
						.getTmnlAssetNo() != null
						&& castOther.getTmnlAssetNo() != null && this
						.getTmnlAssetNo().equals(castOther.getTmnlAssetNo())))
				&& ((this.getConsNo() == castOther.getConsNo()) || (this
						.getConsNo() != null
						&& castOther.getConsNo() != null && this.getConsNo()
						.equals(castOther.getConsNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getGroupNo() == null ? 0 : this.getGroupNo().hashCode());
		result = 37
				* result
				+ (getTmnlAssetNo() == null ? 0 : this.getTmnlAssetNo()
						.hashCode());
		result = 37 * result
				+ (getConsNo() == null ? 0 : this.getConsNo().hashCode());
		return result;
	}

}