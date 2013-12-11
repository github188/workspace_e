package com.nari.elementsdata;

/**
 * TmpMpRdayRead entity. @author MyEclipse Persistence Tools
 */

public class TmpMpRdayRead implements java.io.Serializable {

	// Fields

	private TmpMpRdayReadId id;

	// Constructors

	/** default constructor */
	public TmpMpRdayRead() {
	}

	/** full constructor */
	public TmpMpRdayRead(TmpMpRdayReadId id) {
		this.id = id;
	}

	// Property accessors

	public TmpMpRdayReadId getId() {
		return this.id;
	}

	public void setId(TmpMpRdayReadId id) {
		this.id = id;
	}

}