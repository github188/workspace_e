package com.nari.elementsdata;

/**
 * EMpMonUnbalance entity. @author MyEclipse Persistence Tools
 */

public class EMpMonUnbalance implements java.io.Serializable {

	// Fields

	private EMpMonUnbalanceId id;

	// Constructors

	/** default constructor */
	public EMpMonUnbalance() {
	}

	/** full constructor */
	public EMpMonUnbalance(EMpMonUnbalanceId id) {
		this.id = id;
	}

	// Property accessors

	public EMpMonUnbalanceId getId() {
		return this.id;
	}

	public void setId(EMpMonUnbalanceId id) {
		this.id = id;
	}

}