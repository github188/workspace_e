package com.nari.elementsdata;

/**
 * TmpTotalPowerCurve entity. @author MyEclipse Persistence Tools
 */

public class TmpTotalPowerCurve implements java.io.Serializable {

	// Fields

	private TmpTotalPowerCurveId id;

	// Constructors

	/** default constructor */
	public TmpTotalPowerCurve() {
	}

	/** full constructor */
	public TmpTotalPowerCurve(TmpTotalPowerCurveId id) {
		this.id = id;
	}

	// Property accessors

	public TmpTotalPowerCurveId getId() {
		return this.id;
	}

	public void setId(TmpTotalPowerCurveId id) {
		this.id = id;
	}

}