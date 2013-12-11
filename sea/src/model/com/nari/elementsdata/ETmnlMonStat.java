package com.nari.elementsdata;

/**
 * ETmnlMonStat entity. @author MyEclipse Persistence Tools
 */

public class ETmnlMonStat implements java.io.Serializable {

	// Fields

	private ETmnlMonStatId id;

	// Constructors

	/** default constructor */
	public ETmnlMonStat() {
	}

	/** full constructor */
	public ETmnlMonStat(ETmnlMonStatId id) {
		this.id = id;
	}

	// Property accessors

	public ETmnlMonStatId getId() {
		return this.id;
	}

	public void setId(ETmnlMonStatId id) {
		this.id = id;
	}

}