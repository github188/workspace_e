package com.nari.privilige;

/**
 * PDeptAccessOrgId entity. @author MyEclipse Persistence Tools
 */

public class PDeptAccessOrgId implements java.io.Serializable {

	// Fields

	private String orgNo;
	private String deptNo;

	// Constructors

	/** default constructor */
	public PDeptAccessOrgId() {
	}

	/** full constructor */
	public PDeptAccessOrgId(String orgNo, String deptNo) {
		this.orgNo = orgNo;
		this.deptNo = deptNo;
	}

	// Property accessors

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PDeptAccessOrgId))
			return false;
		PDeptAccessOrgId castOther = (PDeptAccessOrgId) other;

		return ((this.getOrgNo() == castOther.getOrgNo()) || (this.getOrgNo() != null
				&& castOther.getOrgNo() != null && this.getOrgNo().equals(
				castOther.getOrgNo())))
				&& ((this.getDeptNo() == castOther.getDeptNo()) || (this
						.getDeptNo() != null
						&& castOther.getDeptNo() != null && this.getDeptNo()
						.equals(castOther.getDeptNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getOrgNo() == null ? 0 : this.getOrgNo().hashCode());
		result = 37 * result
				+ (getDeptNo() == null ? 0 : this.getDeptNo().hashCode());
		return result;
	}

}