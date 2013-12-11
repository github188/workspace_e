package com.nari.terminalparam;

/**
 * TTmnlTotalInfo entity. @author MyEclipse Persistence Tools
 */

public class TTmnlTotalInfo implements java.io.Serializable {

	// Fields

	private TTmnlTotalInfoId id;

	// Constructors

	/** default constructor */
	public TTmnlTotalInfo() {
	}

	/** full constructor */
	public TTmnlTotalInfo(TTmnlTotalInfoId id) {
		this.id = id;
	}

	// Property accessors

	public TTmnlTotalInfoId getId() {
		return this.id;
	}

	public void setId(TTmnlTotalInfoId id) {
		this.id = id;
	}

}