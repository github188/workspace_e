package com.nari.statreport;

/**
 * ACisMeterRead entity. @author MyEclipse Persistence Tools
 */

public class ACisMeterRead implements java.io.Serializable {

	// Fields

	private ACisMeterReadId id;

	// Constructors

	/** default constructor */
	public ACisMeterRead() {
	}

	/** full constructor */
	public ACisMeterRead(ACisMeterReadId id) {
		this.id = id;
	}

	// Property accessors

	public ACisMeterReadId getId() {
		return this.id;
	}

	public void setId(ACisMeterReadId id) {
		this.id = id;
	}

}