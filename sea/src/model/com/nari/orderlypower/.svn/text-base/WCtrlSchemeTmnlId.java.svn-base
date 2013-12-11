package com.nari.orderlypower;

/**
 * WCtrlSchemeTmnlId entity. @author MyEclipse Persistence Tools
 */

public class WCtrlSchemeTmnlId implements java.io.Serializable {

	// Fields

	private Long ctrlSchemeId;
	private String protItemNo;
	private String paramValue;
	private Integer blockSn;
	private Integer innerBlockSn;

	// Constructors

	/** default constructor */
	public WCtrlSchemeTmnlId() {
	}

	/** minimal constructor */
	public WCtrlSchemeTmnlId(String protItemNo, Integer blockSn,
			Integer innerBlockSn) {
		this.protItemNo = protItemNo;
		this.blockSn = blockSn;
		this.innerBlockSn = innerBlockSn;
	}

	/** full constructor */
	public WCtrlSchemeTmnlId(Long ctrlSchemeId, String protItemNo,
			String paramValue, Integer blockSn, Integer innerBlockSn) {
		this.ctrlSchemeId = ctrlSchemeId;
		this.protItemNo = protItemNo;
		this.paramValue = paramValue;
		this.blockSn = blockSn;
		this.innerBlockSn = innerBlockSn;
	}

	// Property accessors

	public Long getCtrlSchemeId() {
		return this.ctrlSchemeId;
	}

	public void setCtrlSchemeId(Long ctrlSchemeId) {
		this.ctrlSchemeId = ctrlSchemeId;
	}

	public String getProtItemNo() {
		return this.protItemNo;
	}

	public void setProtItemNo(String protItemNo) {
		this.protItemNo = protItemNo;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public Integer getBlockSn() {
		return this.blockSn;
	}

	public void setBlockSn(Integer blockSn) {
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
		if (!(other instanceof WCtrlSchemeTmnlId))
			return false;
		WCtrlSchemeTmnlId castOther = (WCtrlSchemeTmnlId) other;

		return ((this.getCtrlSchemeId() == castOther.getCtrlSchemeId()) || (this
				.getCtrlSchemeId() != null
				&& castOther.getCtrlSchemeId() != null && this
				.getCtrlSchemeId().equals(castOther.getCtrlSchemeId())))
				&& ((this.getProtItemNo() == castOther.getProtItemNo()) || (this
						.getProtItemNo() != null
						&& castOther.getProtItemNo() != null && this
						.getProtItemNo().equals(castOther.getProtItemNo())))
				&& ((this.getParamValue() == castOther.getParamValue()) || (this
						.getParamValue() != null
						&& castOther.getParamValue() != null && this
						.getParamValue().equals(castOther.getParamValue())))
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
				+ (getCtrlSchemeId() == null ? 0 : this.getCtrlSchemeId()
						.hashCode());
		result = 37
				* result
				+ (getProtItemNo() == null ? 0 : this.getProtItemNo()
						.hashCode());
		result = 37
				* result
				+ (getParamValue() == null ? 0 : this.getParamValue()
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