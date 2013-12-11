package com.nari.elementsdata;

/**
 * TmpMpDayPower entity. @author MyEclipse Persistence Tools
 */

public class TmpMpDayPower implements java.io.Serializable {

	// Fields

	private TmpMpDayPowerId id;

	// Constructors

	/** default constructor */
	public TmpMpDayPower() {
	}

	/** full constructor */
	public TmpMpDayPower(TmpMpDayPowerId id) {
		this.id = id;
	}

	// Property accessors

	public TmpMpDayPowerId getId() {
		return this.id;
	}

	public void setId(TmpMpDayPowerId id) {
		this.id = id;
	}

}