package com.nari.elementsdata;

/**
 * TmpTotalCurve entity. @author MyEclipse Persistence Tools
 */

public class TmpTotalCurve implements java.io.Serializable {

	// Fields

	private TmpTotalCurveId id;

	// Constructors

	/** default constructor */
	public TmpTotalCurve() {
	}

	/** full constructor */
	public TmpTotalCurve(TmpTotalCurveId id) {
		this.id = id;
	}

	// Property accessors

	public TmpTotalCurveId getId() {
		return this.id;
	}

	public void setId(TmpTotalCurveId id) {
		this.id = id;
	}

}