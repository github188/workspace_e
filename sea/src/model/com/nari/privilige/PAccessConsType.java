package com.nari.privilige;

/**
 * PAccessConsType entity. @author MyEclipse Persistence Tools
 */

public class PAccessConsType implements java.io.Serializable {

	// Fields

	private PAccessConsTypeId id;

	// Constructors

	/** default constructor */
	public PAccessConsType() {
	}

	/** full constructor */
	public PAccessConsType(PAccessConsTypeId id) {
		this.id = id;
	}

	// Property accessors

	public PAccessConsTypeId getId() {
		return this.id;
	}

	public void setId(PAccessConsTypeId id) {
		this.id = id;
	}

}