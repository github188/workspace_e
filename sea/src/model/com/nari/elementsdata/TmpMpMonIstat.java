package com.nari.elementsdata;

/**
 * TmpMpMonIstat entity. @author MyEclipse Persistence Tools
 */

public class TmpMpMonIstat implements java.io.Serializable {

	// Fields

	private TmpMpMonIstatId id;

	// Constructors

	/** default constructor */
	public TmpMpMonIstat() {
	}

	/** full constructor */
	public TmpMpMonIstat(TmpMpMonIstatId id) {
		this.id = id;
	}

	// Property accessors

	public TmpMpMonIstatId getId() {
		return this.id;
	}

	public void setId(TmpMpMonIstatId id) {
		this.id = id;
	}

}