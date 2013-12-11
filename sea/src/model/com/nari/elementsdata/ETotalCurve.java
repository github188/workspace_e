package com.nari.elementsdata;

/**
 * ETotalCurve entity. @author MyEclipse Persistence Tools
 */

public class ETotalCurve implements java.io.Serializable {

	// Fields

	private ETotalCurveId id;

	// Constructors

	/** default constructor */
	public ETotalCurve() {
	}

	/** full constructor */
	public ETotalCurve(ETotalCurveId id) {
		this.id = id;
	}

	// Property accessors

	public ETotalCurveId getId() {
		return this.id;
	}

	public void setId(ETotalCurveId id) {
		this.id = id;
	}

}