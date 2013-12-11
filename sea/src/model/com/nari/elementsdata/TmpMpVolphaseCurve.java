package com.nari.elementsdata;

/**
 * TmpMpVolphaseCurve entity. @author MyEclipse Persistence Tools
 */

public class TmpMpVolphaseCurve implements java.io.Serializable {

	// Fields

	private TmpMpVolphaseCurveId id;

	// Constructors

	/** default constructor */
	public TmpMpVolphaseCurve() {
	}

	/** full constructor */
	public TmpMpVolphaseCurve(TmpMpVolphaseCurveId id) {
		this.id = id;
	}

	// Property accessors

	public TmpMpVolphaseCurveId getId() {
		return this.id;
	}

	public void setId(TmpMpVolphaseCurveId id) {
		this.id = id;
	}

}