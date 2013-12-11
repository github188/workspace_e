package com.nari.elementsdata;

/**
 * EMpAnalogCurveLog entity. @author MyEclipse Persistence Tools
 */

public class EMpAnalogCurveLog implements java.io.Serializable {

	// Fields

	private EMpAnalogCurveLogId id;

	// Constructors

	/** default constructor */
	public EMpAnalogCurveLog() {
	}

	/** full constructor */
	public EMpAnalogCurveLog(EMpAnalogCurveLogId id) {
		this.id = id;
	}

	// Property accessors

	public EMpAnalogCurveLogId getId() {
		return this.id;
	}

	public void setId(EMpAnalogCurveLogId id) {
		this.id = id;
	}

}