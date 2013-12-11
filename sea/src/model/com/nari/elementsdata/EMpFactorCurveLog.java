package com.nari.elementsdata;

/**
 * EMpFactorCurveLog entity. @author MyEclipse Persistence Tools
 */

public class EMpFactorCurveLog implements java.io.Serializable {

	// Fields

	private EMpFactorCurveLogId id;

	// Constructors

	/** default constructor */
	public EMpFactorCurveLog() {
	}

	/** full constructor */
	public EMpFactorCurveLog(EMpFactorCurveLogId id) {
		this.id = id;
	}

	// Property accessors

	public EMpFactorCurveLogId getId() {
		return this.id;
	}

	public void setId(EMpFactorCurveLogId id) {
		this.id = id;
	}

}