package com.nari.elementsdata;

/**
 * ETotalDayStat entity. @author MyEclipse Persistence Tools
 */

public class ETotalDayStat implements java.io.Serializable {

	// Fields

	private ETotalDayStatId id;

	// Constructors

	/** default constructor */
	public ETotalDayStat() {
	}

	/** full constructor */
	public ETotalDayStat(ETotalDayStatId id) {
		this.id = id;
	}

	// Property accessors

	public ETotalDayStatId getId() {
		return this.id;
	}

	public void setId(ETotalDayStatId id) {
		this.id = id;
	}

}