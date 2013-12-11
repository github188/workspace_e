package com.nari.elementsdata;

/**
 * EMpVolphaseCurve entity. @author MyEclipse Persistence Tools
 */

public class EMpVolphaseCurve implements java.io.Serializable {

	// Fields

	private EMpVolphaseCurveId id;

	// Constructors

	/** default constructor */
	public EMpVolphaseCurve() {
	}

	/** full constructor */
	public EMpVolphaseCurve(EMpVolphaseCurveId id) {
		this.id = id;
	}

	// Property accessors

	public EMpVolphaseCurveId getId() {
		return this.id;
	}

	public void setId(EMpVolphaseCurveId id) {
		this.id = id;
	}

}