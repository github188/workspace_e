package com.nari.terminalparam;

/**
 * TTmnlParamId entity. @author MyEclipse Persistence Tools
 */

public class TTmnlParamId implements java.io.Serializable {

	// Fields

	private String tmnlAssetNo;
	private String protItemNo;
	private String blockSn;
	private Integer innerBlockSn;

	// Constructors

	/** default constructor */
	public TTmnlParamId() {
	}

	/** full constructor */
	public TTmnlParamId(String tmnlAssetNo, String protItemNo, String blockSn,
			Integer innerBlockSn) {
		this.tmnlAssetNo = tmnlAssetNo;
		this.protItemNo = protItemNo;
		this.blockSn = blockSn;
		this.innerBlockSn = innerBlockSn;
	}

	// Property accessors

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getProtItemNo() {
		return this.protItemNo;
	}

	public void setProtItemNo(String protItemNo) {
		this.protItemNo = protItemNo;
	}

	public String getBlockSn() {
		return this.blockSn;
	}

	public void setBlockSn(String blockSn) {
		this.blockSn = blockSn;
	}

	public Integer getInnerBlockSn() {
		return this.innerBlockSn;
	}

	public void setInnerBlockSn(Integer innerBlockSn) {
		this.innerBlockSn = innerBlockSn;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTmnlParamId))
			return false;
		TTmnlParamId castOther = (TTmnlParamId) other;

		return ((this.getTmnlAssetNo() == castOther.getTmnlAssetNo()) || (this
				.getTmnlAssetNo() != null
				&& castOther.getTmnlAssetNo() != null && this.getTmnlAssetNo()
				.equals(castOther.getTmnlAssetNo())))
				&& ((this.getProtItemNo() == castOther.getProtItemNo()) || (this
						.getProtItemNo() != null
						&& castOther.getProtItemNo() != null && this
						.getProtItemNo().equals(castOther.getProtItemNo())))
				&& ((this.getBlockSn() == castOther.getBlockSn()) || (this
						.getBlockSn() != null
						&& castOther.getBlockSn() != null && this.getBlockSn()
						.equals(castOther.getBlockSn())))
				&& ((this.getInnerBlockSn() == castOther.getInnerBlockSn()) || (this
						.getInnerBlockSn() != null
						&& castOther.getInnerBlockSn() != null && this
						.getInnerBlockSn().equals(castOther.getInnerBlockSn())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTmnlAssetNo() == null ? 0 : this.getTmnlAssetNo()
						.hashCode());
		result = 37
				* result
				+ (getProtItemNo() == null ? 0 : this.getProtItemNo()
						.hashCode());
		result = 37 * result
				+ (getBlockSn() == null ? 0 : this.getBlockSn().hashCode());
		result = 37
				* result
				+ (getInnerBlockSn() == null ? 0 : this.getInnerBlockSn()
						.hashCode());
		return result;
	}

}