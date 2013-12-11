package com.nari.elementsdata;

/**
 * EMpMonIstat entity. @author MyEclipse Persistence Tools
 */

public class EMpMonIstat implements java.io.Serializable {

	// Fields

	private EMpMonIstatId id;

	// Constructors

	/** default constructor */
	public EMpMonIstat() {
	}

	/** full constructor */
	public EMpMonIstat(EMpMonIstatId id) {
		this.id = id;
	}

	// Property accessors

	public EMpMonIstatId getId() {
		return this.id;
	}

	public void setId(EMpMonIstatId id) {
		this.id = id;
	}

}