package com.nari.privilige;

/**
 * PAccessTmnlFactory entity. @author MyEclipse Persistence Tools
 */

public class PAccessTmnlFactory implements java.io.Serializable {

	// Fields

	private PAccessTmnlFactoryId id;

	// Constructors

	/** default constructor */
	public PAccessTmnlFactory() {
	}

	/** full constructor */
	public PAccessTmnlFactory(PAccessTmnlFactoryId id) {
		this.id = id;
	}

	// Property accessors

	public PAccessTmnlFactoryId getId() {
		return this.id;
	}

	public void setId(PAccessTmnlFactoryId id) {
		this.id = id;
	}

}