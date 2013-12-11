package com.nari.orgnization;

/**
 * OStaffPhotoId entity. @author MyEclipse Persistence Tools
 */

public class OStaffPhotoId implements java.io.Serializable {

	// Fields

	private String empNo;
	private String photo;

	// Constructors

	/** default constructor */
	public OStaffPhotoId() {
	}

	/** full constructor */
	public OStaffPhotoId(String empNo, String photo) {
		this.empNo = empNo;
		this.photo = photo;
	}

	// Property accessors

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OStaffPhotoId))
			return false;
		OStaffPhotoId castOther = (OStaffPhotoId) other;

		return ((this.getEmpNo() == castOther.getEmpNo()) || (this.getEmpNo() != null
				&& castOther.getEmpNo() != null && this.getEmpNo().equals(
				castOther.getEmpNo())))
				&& ((this.getPhoto() == castOther.getPhoto()) || (this
						.getPhoto() != null
						&& castOther.getPhoto() != null && this.getPhoto()
						.equals(castOther.getPhoto())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpNo() == null ? 0 : this.getEmpNo().hashCode());
		result = 37 * result
				+ (getPhoto() == null ? 0 : this.getPhoto().hashCode());
		return result;
	}

}