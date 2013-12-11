package com.nari.elementsdata;

/**
 * TmpMpReadCurve entity. @author MyEclipse Persistence Tools
 */

public class TmpMpReadCurve implements java.io.Serializable {

	// Fields

	private TmpMpReadCurveId id;

	// Constructors

	/** default constructor */
	public TmpMpReadCurve() {
	}

	/** full constructor */
	public TmpMpReadCurve(TmpMpReadCurveId id) {
		this.id = id;
	}

	// Property accessors

	public TmpMpReadCurveId getId() {
		return this.id;
	}

	public void setId(TmpMpReadCurveId id) {
		this.id = id;
	}

}