package com.nari.elementsdata;

/**
 * EMpVolCurveLog entity. @author MyEclipse Persistence Tools
 */

public class EMpVolCurveLog implements java.io.Serializable {

	// Fields

	private EMpVolCurveLogId id;

	// Constructors

	/** default constructor */
	public EMpVolCurveLog() {
	}

	/** full constructor */
	public EMpVolCurveLog(EMpVolCurveLogId id) {
		this.id = id;
	}

	// Property accessors

	public EMpVolCurveLogId getId() {
		return this.id;
	}

	public void setId(EMpVolCurveLogId id) {
		this.id = id;
	}

}