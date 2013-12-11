package com.nari.elementsdata;

/**
 * EMpMonEnergyLog entity. @author MyEclipse Persistence Tools
 */

public class EMpMonEnergyLog implements java.io.Serializable {

	// Fields

	private EMpMonEnergyLogId id;

	// Constructors

	/** default constructor */
	public EMpMonEnergyLog() {
	}

	/** full constructor */
	public EMpMonEnergyLog(EMpMonEnergyLogId id) {
		this.id = id;
	}

	// Property accessors

	public EMpMonEnergyLogId getId() {
		return this.id;
	}

	public void setId(EMpMonEnergyLogId id) {
		this.id = id;
	}

}