package com.nari.basicdata;

/**
 * BAnalyticalParam entity. @author MyEclipse Persistence Tools
 */

public class BAnalyticalParam implements java.io.Serializable {

	// Fields

	private BAnalyticalParamId id;

	// Constructors

	/** default constructor */
	public BAnalyticalParam() {
	}

	/** full constructor */
	public BAnalyticalParam(BAnalyticalParamId id) {
		this.id = id;
	}

	// Property accessors

	public BAnalyticalParamId getId() {
		return this.id;
	}

	public void setId(BAnalyticalParamId id) {
		this.id = id;
	}

}