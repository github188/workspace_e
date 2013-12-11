package com.nari.elementsdata;

/**
 * EMpDayCompLog entity. @author MyEclipse Persistence Tools
 */

public class EMpDayCompLog implements java.io.Serializable {

	// Fields

	private EMpDayCompLogId id;

	// Constructors

	/** default constructor */
	public EMpDayCompLog() {
	}

	/** full constructor */
	public EMpDayCompLog(EMpDayCompLogId id) {
		this.id = id;
	}

	// Property accessors

	public EMpDayCompLogId getId() {
		return this.id;
	}

	public void setId(EMpDayCompLogId id) {
		this.id = id;
	}

}