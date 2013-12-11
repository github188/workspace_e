package com.nari.statreport;

/**
 * ATmnlFlowDistributeD entity. @author MyEclipse Persistence Tools
 */

public class ATmnlFlowDistributeD implements java.io.Serializable {

	// Fields

	private ATmnlFlowDistributeDId id;

	// Constructors

	/** default constructor */
	public ATmnlFlowDistributeD() {
	}

	/** full constructor */
	public ATmnlFlowDistributeD(ATmnlFlowDistributeDId id) {
		this.id = id;
	}

	// Property accessors

	public ATmnlFlowDistributeDId getId() {
		return this.id;
	}

	public void setId(ATmnlFlowDistributeDId id) {
		this.id = id;
	}

}