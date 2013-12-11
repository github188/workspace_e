package com.nari.elementsdata;

/**
 * EMpDayPowerLog entity. @author MyEclipse Persistence Tools
 */

public class EMpDayPowerLog implements java.io.Serializable {

	// Fields

	private EMpDayPowerLogId id;

	// Constructors

	/** default constructor */
	public EMpDayPowerLog() {
	}

	/** full constructor */
	public EMpDayPowerLog(EMpDayPowerLogId id) {
		this.id = id;
	}

	// Property accessors

	public EMpDayPowerLogId getId() {
		return this.id;
	}

	public void setId(EMpDayPowerLogId id) {
		this.id = id;
	}

}