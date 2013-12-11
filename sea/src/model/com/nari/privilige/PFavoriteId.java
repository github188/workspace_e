package com.nari.privilige;

/**
 * PFavoriteId entity. @author MyEclipse Persistence Tools
 */

public class PFavoriteId implements java.io.Serializable {

	// Fields

	private String menuNo;
	private String staffNo;
	private String linkAddr;
	private Integer sortNo;

	// Constructors

	/** default constructor */
	public PFavoriteId() {
	}

	/** minimal constructor */
	public PFavoriteId(String staffNo) {
		this.staffNo = staffNo;
	}

	/** full constructor */
	public PFavoriteId(String menuNo, String staffNo, String linkAddr,
			Integer sortNo) {
		this.menuNo = menuNo;
		this.staffNo = staffNo;
		this.linkAddr = linkAddr;
		this.sortNo = sortNo;
	}

	// Property accessors

	public String getMenuNo() {
		return this.menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public String getStaffNo() {
		return this.staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getLinkAddr() {
		return this.linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}

	public Integer getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PFavoriteId))
			return false;
		PFavoriteId castOther = (PFavoriteId) other;

		return ((this.getMenuNo() == castOther.getMenuNo()) || (this
				.getMenuNo() != null
				&& castOther.getMenuNo() != null && this.getMenuNo().equals(
				castOther.getMenuNo())))
				&& ((this.getStaffNo() == castOther.getStaffNo()) || (this
						.getStaffNo() != null
						&& castOther.getStaffNo() != null && this.getStaffNo()
						.equals(castOther.getStaffNo())))
				&& ((this.getLinkAddr() == castOther.getLinkAddr()) || (this
						.getLinkAddr() != null
						&& castOther.getLinkAddr() != null && this
						.getLinkAddr().equals(castOther.getLinkAddr())))
				&& ((this.getSortNo() == castOther.getSortNo()) || (this
						.getSortNo() != null
						&& castOther.getSortNo() != null && this.getSortNo()
						.equals(castOther.getSortNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMenuNo() == null ? 0 : this.getMenuNo().hashCode());
		result = 37 * result
				+ (getStaffNo() == null ? 0 : this.getStaffNo().hashCode());
		result = 37 * result
				+ (getLinkAddr() == null ? 0 : this.getLinkAddr().hashCode());
		result = 37 * result
				+ (getSortNo() == null ? 0 : this.getSortNo().hashCode());
		return result;
	}

}