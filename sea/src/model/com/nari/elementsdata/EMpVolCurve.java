package com.nari.elementsdata;

/**
 * EMpVolCurve entity. @author MyEclipse Persistence Tools
 */

public class EMpVolCurve implements java.io.Serializable {

	// Fields

	private EMpVolCurveId id;

	// Constructors

	/** default constructor */
	public EMpVolCurve() {
	}

	/** full constructor */
	public EMpVolCurve(EMpVolCurveId id) {
		this.id = id;
	}

	// Property accessors

	public EMpVolCurveId getId() {
		return this.id;
	}

	public void setId(EMpVolCurveId id) {
		this.id = id;
	}

}