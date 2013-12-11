package com.nari.terminalparam;

/**
 * TTmnlTaskId entity. @author MyEclipse Persistence Tools
 */

public class TTmnlTaskId implements java.io.Serializable {

	// Fields

	private String tmnlAssetNo;
	private Short taskNo;

	// Constructors

	/** default constructor */
	public TTmnlTaskId() {
	}

	/** full constructor */
	public TTmnlTaskId(String tmnlAssetNo, Short taskNo) {
		this.tmnlAssetNo = tmnlAssetNo;
		this.taskNo = taskNo;
	}

	// Property accessors

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public Short getTaskNo() {
		return this.taskNo;
	}

	public void setTaskNo(Short taskNo) {
		this.taskNo = taskNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTmnlTaskId))
			return false;
		TTmnlTaskId castOther = (TTmnlTaskId) other;

		return ((this.getTmnlAssetNo() == castOther.getTmnlAssetNo()) || (this
				.getTmnlAssetNo() != null
				&& castOther.getTmnlAssetNo() != null && this.getTmnlAssetNo()
				.equals(castOther.getTmnlAssetNo())))
				&& ((this.getTaskNo() == castOther.getTaskNo()) || (this
						.getTaskNo() != null
						&& castOther.getTaskNo() != null && this.getTaskNo()
						.equals(castOther.getTaskNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTmnlAssetNo() == null ? 0 : this.getTmnlAssetNo()
						.hashCode());
		result = 37 * result
				+ (getTaskNo() == null ? 0 : this.getTaskNo().hashCode());
		return result;
	}

}