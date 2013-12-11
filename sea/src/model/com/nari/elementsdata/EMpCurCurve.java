package com.nari.elementsdata;

/**
 * EMpCurCurve entity. @author MyEclipse Persistence Tools
 */

public class EMpCurCurve implements java.io.Serializable {

	// Fields

	private EMpCurCurveId id;

	// Constructors

	/** default constructor */
	public EMpCurCurve() {
	}

	/** full constructor */
	public EMpCurCurve(EMpCurCurveId id) {
		this.id = id;
	}

	// Property accessors

	public EMpCurCurveId getId() {
		return this.id;
	}

	public void setId(EMpCurCurveId id) {
		this.id = id;
	}

}