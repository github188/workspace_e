package com.nari.elementsdata;

/**
 * EMpCurCurveLog entity. @author MyEclipse Persistence Tools
 */

public class EMpCurCurveLog implements java.io.Serializable {

	// Fields

	private EMpCurCurveLogId id;

	// Constructors

	/** default constructor */
	public EMpCurCurveLog() {
	}

	/** full constructor */
	public EMpCurCurveLog(EMpCurCurveLogId id) {
		this.id = id;
	}

	// Property accessors

	public EMpCurCurveLogId getId() {
		return this.id;
	}

	public void setId(EMpCurCurveLogId id) {
		this.id = id;
	}

}