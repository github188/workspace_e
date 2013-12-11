package com.nari.basicdata;

/**
 * VwCtrlSchemeTypeId entity. @author MyEclipse Persistence Tools
 */

public class VwCtrlSchemeTypeId implements java.io.Serializable {

	// Fields

	private String ctrlSchemeNo;
	private String ctrlSchemeType;

	// Constructors

	/** default constructor */
	public VwCtrlSchemeTypeId() {
	}

	/** full constructor */
	public VwCtrlSchemeTypeId(String ctrlSchemeNo, String ctrlSchemeType) {
		this.ctrlSchemeNo = ctrlSchemeNo;
		this.ctrlSchemeType = ctrlSchemeType;
	}

	// Property accessors

	public String getCtrlSchemeNo() {
		return this.ctrlSchemeNo;
	}

	public void setCtrlSchemeNo(String ctrlSchemeNo) {
		this.ctrlSchemeNo = ctrlSchemeNo;
	}

	public String getCtrlSchemeType() {
		return this.ctrlSchemeType;
	}

	public void setCtrlSchemeType(String ctrlSchemeType) {
		this.ctrlSchemeType = ctrlSchemeType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VwCtrlSchemeTypeId))
			return false;
		VwCtrlSchemeTypeId castOther = (VwCtrlSchemeTypeId) other;

		return ((this.getCtrlSchemeNo() == castOther.getCtrlSchemeNo()) || (this
				.getCtrlSchemeNo() != null
				&& castOther.getCtrlSchemeNo() != null && this
				.getCtrlSchemeNo().equals(castOther.getCtrlSchemeNo())))
				&& ((this.getCtrlSchemeType() == castOther.getCtrlSchemeType()) || (this
						.getCtrlSchemeType() != null
						&& castOther.getCtrlSchemeType() != null && this
						.getCtrlSchemeType().equals(
								castOther.getCtrlSchemeType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCtrlSchemeNo() == null ? 0 : this.getCtrlSchemeNo()
						.hashCode());
		result = 37
				* result
				+ (getCtrlSchemeType() == null ? 0 : this.getCtrlSchemeType()
						.hashCode());
		return result;
	}

}