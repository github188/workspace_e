package com.nari.elementsdata;

/**
 * ETmnlDayStatLog entity. @author MyEclipse Persistence Tools
 */

public class ETmnlDayStatLog implements java.io.Serializable {

	// Fields

	private ETmnlDayStatLogId id;

	// Constructors

	/** default constructor */
	public ETmnlDayStatLog() {
	}

	/** full constructor */
	public ETmnlDayStatLog(ETmnlDayStatLogId id) {
		this.id = id;
	}

	// Property accessors

	public ETmnlDayStatLogId getId() {
		return this.id;
	}

	public void setId(ETmnlDayStatLogId id) {
		this.id = id;
	}

}