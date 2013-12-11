package com.nari.logofsys;

/**
 * LLowlessMsgLog entity. @author MyEclipse Persistence Tools
 */

public class LLowlessMsgLog implements java.io.Serializable {

	// Fields

	private LLowlessMsgLogId id;

	// Constructors

	/** default constructor */
	public LLowlessMsgLog() {
	}

	/** full constructor */
	public LLowlessMsgLog(LLowlessMsgLogId id) {
		this.id = id;
	}

	// Property accessors

	public LLowlessMsgLogId getId() {
		return this.id;
	}

	public void setId(LLowlessMsgLogId id) {
		this.id = id;
	}

}