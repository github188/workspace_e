package com.nari.elementsdata;

/**
 * EMpAnalogCurve entity. @author MyEclipse Persistence Tools
 */

public class EMpAnalogCurve implements java.io.Serializable {

	// Fields

	private EMpAnalogCurveId id;

	// Constructors

	/** default constructor */
	public EMpAnalogCurve() {
	}

	/** full constructor */
	public EMpAnalogCurve(EMpAnalogCurveId id) {
		this.id = id;
	}

	// Property accessors

	public EMpAnalogCurveId getId() {
		return this.id;
	}

	public void setId(EMpAnalogCurveId id) {
		this.id = id;
	}

}