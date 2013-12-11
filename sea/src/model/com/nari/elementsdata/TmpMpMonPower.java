package com.nari.elementsdata;

/**
 * TmpMpMonPower entity. @author MyEclipse Persistence Tools
 */

public class TmpMpMonPower implements java.io.Serializable {

	// Fields

	private TmpMpMonPowerId id;

	// Constructors

	/** default constructor */
	public TmpMpMonPower() {
	}

	/** full constructor */
	public TmpMpMonPower(TmpMpMonPowerId id) {
		this.id = id;
	}

	// Property accessors

	public TmpMpMonPowerId getId() {
		return this.id;
	}

	public void setId(TmpMpMonPowerId id) {
		this.id = id;
	}

}