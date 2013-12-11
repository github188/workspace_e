package com.nari.privilige;

/**
 * PAccessOrgId entity. @author MyEclipse Persistence Tools
 */

public class PAccessOrgId implements java.io.Serializable {

	// Fields

	private String staffNo;
	private String orgNo;
	private String orgType;

	// Constructors

	/** default constructor */
	public PAccessOrgId() {
	}

	/** minimal constructor */
	public PAccessOrgId(String orgNo) {
		this.orgNo = orgNo;
	}

	/** full constructor */
	public PAccessOrgId(String staffNo, String orgNo, String orgType) {
		this.staffNo = staffNo;
		this.orgNo = orgNo;
		this.orgType = orgType;
	}

	// Property accessors

	public String getStaffNo() {
		return this.staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PAccessOrgId))
			return false;
		PAccessOrgId castOther = (PAccessOrgId) other;

		return ((this.getStaffNo() == castOther.getStaffNo()) || (this
				.getStaffNo() != null
				&& castOther.getStaffNo() != null && this.getStaffNo().equals(
				castOther.getStaffNo())))
				&& ((this.getOrgNo() == castOther.getOrgNo()) || (this
						.getOrgNo() != null
						&& castOther.getOrgNo() != null && this.getOrgNo()
						.equals(castOther.getOrgNo())))
				&& ((this.getOrgType() == castOther.getOrgType()) || (this
						.getOrgType() != null
						&& castOther.getOrgType() != null && this.getOrgType()
						.equals(castOther.getOrgType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getStaffNo() == null ? 0 : this.getStaffNo().hashCode());
		result = 37 * result
				+ (getOrgNo() == null ? 0 : this.getOrgNo().hashCode());
		result = 37 * result
				+ (getOrgType() == null ? 0 : this.getOrgType().hashCode());
		return result;
	}

}