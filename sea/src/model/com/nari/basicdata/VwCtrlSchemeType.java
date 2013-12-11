package com.nari.basicdata;

/**
 * VwCtrlSchemeType entity. @author MyEclipse Persistence Tools
 */

public class VwCtrlSchemeType implements java.io.Serializable {

	// Fields

	private VwCtrlSchemeTypeId id;

	// Constructors

	/** default constructor */
	public VwCtrlSchemeType() {
	}

	/** full constructor */
	public VwCtrlSchemeType(VwCtrlSchemeTypeId id) {
		this.id = id;
	}

	// Property accessors

	public VwCtrlSchemeTypeId getId() {
		return this.id;
	}

	public void setId(VwCtrlSchemeTypeId id) {
		this.id = id;
	}

}