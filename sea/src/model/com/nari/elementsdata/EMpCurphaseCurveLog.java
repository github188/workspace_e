package com.nari.elementsdata;

/**
 * EMpCurphaseCurveLog entity. @author MyEclipse Persistence Tools
 */

public class EMpCurphaseCurveLog implements java.io.Serializable {

	// Fields

	private EMpCurphaseCurveLogId id;

	// Constructors

	/** default constructor */
	public EMpCurphaseCurveLog() {
	}

	/** full constructor */
	public EMpCurphaseCurveLog(EMpCurphaseCurveLogId id) {
		this.id = id;
	}

	// Property accessors

	public EMpCurphaseCurveLogId getId() {
		return this.id;
	}

	public void setId(EMpCurphaseCurveLogId id) {
		this.id = id;
	}

}