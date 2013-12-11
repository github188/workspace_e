package com.nari.elementsdata;

/**
 * EMpDayPftime entity. @author MyEclipse Persistence Tools
 */

public class EMpDayPftime implements java.io.Serializable {

	// Fields

	private EMpDayPftimeId id;

	// Constructors

	/** default constructor */
	public EMpDayPftime() {
	}

	/** full constructor */
	public EMpDayPftime(EMpDayPftimeId id) {
		this.id = id;
	}

	// Property accessors

	public EMpDayPftimeId getId() {
		return this.id;
	}

	public void setId(EMpDayPftimeId id) {
		this.id = id;
	}

}