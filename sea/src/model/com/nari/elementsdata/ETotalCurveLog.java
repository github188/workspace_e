package com.nari.elementsdata;

/**
 * ETotalCurveLog entity. @author MyEclipse Persistence Tools
 */

public class ETotalCurveLog implements java.io.Serializable {

	// Fields

	private ETotalCurveLogId id;

	// Constructors

	/** default constructor */
	public ETotalCurveLog() {
	}

	/** full constructor */
	public ETotalCurveLog(ETotalCurveLogId id) {
		this.id = id;
	}

	// Property accessors

	public ETotalCurveLogId getId() {
		return this.id;
	}

	public void setId(ETotalCurveLogId id) {
		this.id = id;
	}

}