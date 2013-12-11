package com.nari.statreport;

/**
 * ATmnlStatsD entity. @author MyEclipse Persistence Tools
 */

public class ATmnlStatsD implements java.io.Serializable {

	// Fields

	private ATmnlStatsDId id;

	// Constructors

	/** default constructor */
	public ATmnlStatsD() {
	}

	/** full constructor */
	public ATmnlStatsD(ATmnlStatsDId id) {
		this.id = id;
	}

	// Property accessors

	public ATmnlStatsDId getId() {
		return this.id;
	}

	public void setId(ATmnlStatsDId id) {
		this.id = id;
	}

}