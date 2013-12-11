package com.nari.elementsdata;

/**
 * EMpRdayDemandLog entity. @author MyEclipse Persistence Tools
 */

public class EMpRdayDemandLog implements java.io.Serializable {

	// Fields

	private EMpRdayDemandLogId id;

	// Constructors

	/** default constructor */
	public EMpRdayDemandLog() {
	}

	/** full constructor */
	public EMpRdayDemandLog(EMpRdayDemandLogId id) {
		this.id = id;
	}

	// Property accessors

	public EMpRdayDemandLogId getId() {
		return this.id;
	}

	public void setId(EMpRdayDemandLogId id) {
		this.id = id;
	}

}