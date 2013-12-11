package com.nari.elementsdata;

/**
 * EMpMonPowerLog entity. @author MyEclipse Persistence Tools
 */

public class EMpMonPowerLog implements java.io.Serializable {

	// Fields

	private EMpMonPowerLogId id;

	// Constructors

	/** default constructor */
	public EMpMonPowerLog() {
	}

	/** full constructor */
	public EMpMonPowerLog(EMpMonPowerLogId id) {
		this.id = id;
	}

	// Property accessors

	public EMpMonPowerLogId getId() {
		return this.id;
	}

	public void setId(EMpMonPowerLogId id) {
		this.id = id;
	}

}