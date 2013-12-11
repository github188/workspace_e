package com.nari.elementsdata;

/**
 * EMpDayVstat entity. @author MyEclipse Persistence Tools
 */

public class EMpDayVstat implements java.io.Serializable {

	// Fields

	private EMpDayVstatId id;

	// Constructors

	/** default constructor */
	public EMpDayVstat() {
	}

	/** full constructor */
	public EMpDayVstat(EMpDayVstatId id) {
		this.id = id;
	}

	// Property accessors

	public EMpDayVstatId getId() {
		return this.id;
	}

	public void setId(EMpDayVstatId id) {
		this.id = id;
	}

}