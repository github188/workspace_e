package com.nari.elementsdata;

/**
 * TmpMpMonEnergy entity. @author MyEclipse Persistence Tools
 */

public class TmpMpMonEnergy implements java.io.Serializable {

	// Fields

	private TmpMpMonEnergyId id;

	// Constructors

	/** default constructor */
	public TmpMpMonEnergy() {
	}

	/** full constructor */
	public TmpMpMonEnergy(TmpMpMonEnergyId id) {
		this.id = id;
	}

	// Property accessors

	public TmpMpMonEnergyId getId() {
		return this.id;
	}

	public void setId(TmpMpMonEnergyId id) {
		this.id = id;
	}

}