package com.nari.privilige;

/**
 * PAccessTmnlFactoryId entity. @author MyEclipse Persistence Tools
 */

public class PAccessTmnlFactoryId implements java.io.Serializable {

	// Fields

	private String staffNo;
	private String factoryCode;

	// Constructors

	/** default constructor */
	public PAccessTmnlFactoryId() {
	}

	/** minimal constructor */
	public PAccessTmnlFactoryId(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	/** full constructor */
	public PAccessTmnlFactoryId(String staffNo, String factoryCode) {
		this.staffNo = staffNo;
		this.factoryCode = factoryCode;
	}

	// Property accessors

	public String getStaffNo() {
		return this.staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getFactoryCode() {
		return this.factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PAccessTmnlFactoryId))
			return false;
		PAccessTmnlFactoryId castOther = (PAccessTmnlFactoryId) other;

		return ((this.getStaffNo() == castOther.getStaffNo()) || (this
				.getStaffNo() != null
				&& castOther.getStaffNo() != null && this.getStaffNo().equals(
				castOther.getStaffNo())))
				&& ((this.getFactoryCode() == castOther.getFactoryCode()) || (this
						.getFactoryCode() != null
						&& castOther.getFactoryCode() != null && this
						.getFactoryCode().equals(castOther.getFactoryCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getStaffNo() == null ? 0 : this.getStaffNo().hashCode());
		result = 37
				* result
				+ (getFactoryCode() == null ? 0 : this.getFactoryCode()
						.hashCode());
		return result;
	}

}