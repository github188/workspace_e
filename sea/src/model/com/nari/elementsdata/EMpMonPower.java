package com.nari.elementsdata;

/**
 * EMpMonPower entity. @author MyEclipse Persistence Tools
 */

public class EMpMonPower implements java.io.Serializable {

	// Fields

	private EMpMonPowerId id;

	// Constructors

	/** default constructor */
	public EMpMonPower() {
	}

	/** full constructor */
	public EMpMonPower(EMpMonPowerId id) {
		this.id = id;
	}

	// Property accessors

	public EMpMonPowerId getId() {
		return this.id;
	}

	public void setId(EMpMonPowerId id) {
		this.id = id;
	}

}