package com.nari.elementsdata;

/**
 * ETmnlDayStat entity. @author MyEclipse Persistence Tools
 */

public class ETmnlDayStat implements java.io.Serializable {

	// Fields

	private ETmnlDayStatId id;

	// Constructors

	/** default constructor */
	public ETmnlDayStat() {
	}

	/** full constructor */
	public ETmnlDayStat(ETmnlDayStatId id) {
		this.id = id;
	}

	// Property accessors

	public ETmnlDayStatId getId() {
		return this.id;
	}

	public void setId(ETmnlDayStatId id) {
		this.id = id;
	}

}