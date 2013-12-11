package com.nari.elementsdata;

/**
 * EMpDayUnbalance entity. @author MyEclipse Persistence Tools
 */

public class EMpDayUnbalance implements java.io.Serializable {

	// Fields

	private EMpDayUnbalanceId id;

	// Constructors

	/** default constructor */
	public EMpDayUnbalance() {
	}

	/** full constructor */
	public EMpDayUnbalance(EMpDayUnbalanceId id) {
		this.id = id;
	}

	// Property accessors

	public EMpDayUnbalanceId getId() {
		return this.id;
	}

	public void setId(EMpDayUnbalanceId id) {
		this.id = id;
	}

}