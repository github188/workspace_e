package com.nari.elementsdata;

/**
 * ETotalPowerCurve entity. @author MyEclipse Persistence Tools
 */

public class ETotalPowerCurve implements java.io.Serializable {

	// Fields

	private ETotalPowerCurveId id;

	// Constructors

	/** default constructor */
	public ETotalPowerCurve() {
	}

	/** full constructor */
	public ETotalPowerCurve(ETotalPowerCurveId id) {
		this.id = id;
	}

	// Property accessors

	public ETotalPowerCurveId getId() {
		return this.id;
	}

	public void setId(ETotalPowerCurveId id) {
		this.id = id;
	}

}