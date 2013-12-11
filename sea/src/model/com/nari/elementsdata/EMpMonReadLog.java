package com.nari.elementsdata;

/**
 * EMpMonReadLog entity. @author MyEclipse Persistence Tools
 */

public class EMpMonReadLog implements java.io.Serializable {

	// Fields

	private EMpMonReadLogId id;

	// Constructors

	/** default constructor */
	public EMpMonReadLog() {
	}

	/** full constructor */
	public EMpMonReadLog(EMpMonReadLogId id) {
		this.id = id;
	}

	// Property accessors

	public EMpMonReadLogId getId() {
		return this.id;
	}

	public void setId(EMpMonReadLogId id) {
		this.id = id;
	}

}