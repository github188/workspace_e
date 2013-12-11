package com.nari.elementsdata;

/**
 * TmpMpFactorCurve entity. @author MyEclipse Persistence Tools
 */

public class TmpMpFactorCurve implements java.io.Serializable {

	// Fields

	private TmpMpFactorCurveId id;

	// Constructors

	/** default constructor */
	public TmpMpFactorCurve() {
	}

	/** full constructor */
	public TmpMpFactorCurve(TmpMpFactorCurveId id) {
		this.id = id;
	}

	// Property accessors

	public TmpMpFactorCurveId getId() {
		return this.id;
	}

	public void setId(TmpMpFactorCurveId id) {
		this.id = id;
	}

}