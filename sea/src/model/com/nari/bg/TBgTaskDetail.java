package com.nari.bg;

/**
 * TBgTaskDetail entity. @author MyEclipse Persistence Tools
 */

public class TBgTaskDetail implements java.io.Serializable {

	// Fields

	private TBgTaskDetailId id;

	// Constructors

	/** default constructor */
	public TBgTaskDetail() {
	}

	/** full constructor */
	public TBgTaskDetail(TBgTaskDetailId id) {
		this.id = id;
	}

	// Property accessors

	public TBgTaskDetailId getId() {
		return this.id;
	}

	public void setId(TBgTaskDetailId id) {
		this.id = id;
	}

}