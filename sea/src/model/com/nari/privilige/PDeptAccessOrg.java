package com.nari.privilige;

/**
 * PDeptAccessOrg entity. @author MyEclipse Persistence Tools
 */

public class PDeptAccessOrg implements java.io.Serializable {

	// Fields

	private PDeptAccessOrgId id;

	// Constructors

	/** default constructor */
	public PDeptAccessOrg() {
	}

	/** full constructor */
	public PDeptAccessOrg(PDeptAccessOrgId id) {
		this.id = id;
	}

	// Property accessors

	public PDeptAccessOrgId getId() {
		return this.id;
	}

	public void setId(PDeptAccessOrgId id) {
		this.id = id;
	}

}