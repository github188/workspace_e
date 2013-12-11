package com.nari.privilige;

/**
 * PAccessConsTypeId entity. @author MyEclipse Persistence Tools
 */

public class PAccessConsTypeId implements java.io.Serializable {

	// Fields

	private String staffNo;
	private Byte consType;

	// Constructors

	/** default constructor */
	public PAccessConsTypeId() {
	}

	/** minimal constructor */
	public PAccessConsTypeId(Byte consType) {
		this.consType = consType;
	}

	/** full constructor */
	public PAccessConsTypeId(String staffNo, Byte consType) {
		this.staffNo = staffNo;
		this.consType = consType;
	}

	// Property accessors

	public String getStaffNo() {
		return this.staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public Byte getConsType() {
		return this.consType;
	}

	public void setConsType(Byte consType) {
		this.consType = consType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PAccessConsTypeId))
			return false;
		PAccessConsTypeId castOther = (PAccessConsTypeId) other;

		return ((this.getStaffNo() == castOther.getStaffNo()) || (this
				.getStaffNo() != null
				&& castOther.getStaffNo() != null && this.getStaffNo().equals(
				castOther.getStaffNo())))
				&& ((this.getConsType() == castOther.getConsType()) || (this
						.getConsType() != null
						&& castOther.getConsType() != null && this
						.getConsType().equals(castOther.getConsType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getStaffNo() == null ? 0 : this.getStaffNo().hashCode());
		result = 37 * result
				+ (getConsType() == null ? 0 : this.getConsType().hashCode());
		return result;
	}

}