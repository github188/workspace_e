package com.nari.elementsdata;

/**
 * EMpMonEnergy entity. @author MyEclipse Persistence Tools
 */

public class EMpMonEnergy implements java.io.Serializable {

	// Fields

	private EMpMonEnergyId id;

	// Constructors

	/** default constructor */
	public EMpMonEnergy() {
	}

	/** full constructor */
	public EMpMonEnergy(EMpMonEnergyId id) {
		this.id = id;
	}

	// Property accessors

	public EMpMonEnergyId getId() {
		return this.id;
	}

	public void setId(EMpMonEnergyId id) {
		this.id = id;
	}

}