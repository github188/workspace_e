package com.nari.elementsdata;

/**
 * EMpMonIstatLog entity. @author MyEclipse Persistence Tools
 */

public class EMpMonIstatLog implements java.io.Serializable {

	// Fields

	private EMpMonIstatLogId id;

	// Constructors

	/** default constructor */
	public EMpMonIstatLog() {
	}

	/** full constructor */
	public EMpMonIstatLog(EMpMonIstatLogId id) {
		this.id = id;
	}

	// Property accessors

	public EMpMonIstatLogId getId() {
		return this.id;
	}

	public void setId(EMpMonIstatLogId id) {
		this.id = id;
	}

}