package com.nari.statreport;

/**
 * ATmnlDayFlow entity. @author MyEclipse Persistence Tools
 */

public class ATmnlDayFlow implements java.io.Serializable {

	// Fields

	private ATmnlDayFlowId id;

	// Constructors

	/** default constructor */
	public ATmnlDayFlow() {
	}

	/** full constructor */
	public ATmnlDayFlow(ATmnlDayFlowId id) {
		this.id = id;
	}

	// Property accessors

	public ATmnlDayFlowId getId() {
		return this.id;
	}

	public void setId(ATmnlDayFlowId id) {
		this.id = id;
	}

}