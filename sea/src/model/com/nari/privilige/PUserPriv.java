package com.nari.privilige;

/**
 * PUserPriv entity. @author MyEclipse Persistence Tools
 */

public class PUserPriv implements java.io.Serializable {

	// Fields

	private Long userPrivRefId;
	private String menuNo;
	private Long roleId;
	private String staffNo;
	private String grantFlag;

	// Constructors

	/** default constructor */
	public PUserPriv() {
	}

	/** minimal constructor */
	public PUserPriv(String staffNo) {
		this.staffNo = staffNo;
	}

	/** full constructor */
	public PUserPriv(String menuNo, Long roleId, String staffNo,
			String grantFlag) {
		this.menuNo = menuNo;
		this.roleId = roleId;
		this.staffNo = staffNo;
		this.grantFlag = grantFlag;
	}

	// Property accessors

	public Long getUserPrivRefId() {
		return this.userPrivRefId;
	}

	public void setUserPrivRefId(Long userPrivRefId) {
		this.userPrivRefId = userPrivRefId;
	}

	public String getMenuNo() {
		return this.menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getStaffNo() {
		return this.staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getGrantFlag() {
		return this.grantFlag;
	}

	public void setGrantFlag(String grantFlag) {
		this.grantFlag = grantFlag;
	}

}