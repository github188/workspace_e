package com.nari.elementsdata;

/**
 * EMpDayDemand entity. @author MyEclipse Persistence Tools
 */

public class EMpDayDemand implements java.io.Serializable {

	// Fields

	private EMpDayDemandId id;

	// Constructors

	/** default constructor */
	public EMpDayDemand() {
	}

	/** full constructor */
	public EMpDayDemand(EMpDayDemandId id) {
		this.id = id;
	}

	// Property accessors

	public EMpDayDemandId getId() {
		return this.id;
	}

	public void setId(EMpDayDemandId id) {
		this.id = id;
	}

}