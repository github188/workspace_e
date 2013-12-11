package com.nari.bg;

/**
 * TBgTaskResult entity. @author MyEclipse Persistence Tools
 */

public class TBgTaskResult implements java.io.Serializable {

	// Fields

	private TBgTaskResultId id;

	// Constructors

	/** default constructor */
	public TBgTaskResult() {
	}

	/** full constructor */
	public TBgTaskResult(TBgTaskResultId id) {
		this.id = id;
	}

	// Property accessors

	public TBgTaskResultId getId() {
		return this.id;
	}

	public void setId(TBgTaskResultId id) {
		this.id = id;
	}

}