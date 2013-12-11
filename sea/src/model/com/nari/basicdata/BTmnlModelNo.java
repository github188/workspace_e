package com.nari.basicdata;

/**
 * BTmnlModelNo entity. @author MyEclipse Persistence Tools
 */

public class BTmnlModelNo implements java.io.Serializable {

	// Fields

	private BTmnlModelNoId id;

	// Constructors

	/** default constructor */
	public BTmnlModelNo() {
	}

	/** full constructor */
	public BTmnlModelNo(BTmnlModelNoId id) {
		this.id = id;
	}

	// Property accessors

	public BTmnlModelNoId getId() {
		return this.id;
	}

	public void setId(BTmnlModelNoId id) {
		this.id = id;
	}

}