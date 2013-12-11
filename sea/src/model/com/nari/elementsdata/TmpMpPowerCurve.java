package com.nari.elementsdata;

/**
 * TmpMpPowerCurve entity. @author MyEclipse Persistence Tools
 */

public class TmpMpPowerCurve implements java.io.Serializable {

	// Fields

	private TmpMpPowerCurveId id;

	// Constructors

	/** default constructor */
	public TmpMpPowerCurve() {
	}

	/** full constructor */
	public TmpMpPowerCurve(TmpMpPowerCurveId id) {
		this.id = id;
	}

	// Property accessors

	public TmpMpPowerCurveId getId() {
		return this.id;
	}

	public void setId(TmpMpPowerCurveId id) {
		this.id = id;
	}

}