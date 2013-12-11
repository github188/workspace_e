package com.nari.flowhandle;

/**
 * FEventShield entity. @author MyEclipse Persistence Tools
 */

public class FEventShield implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5496514576285676627L;
	private FEventShieldId id;

	// Constructors

	/** default constructor */
	public FEventShield() {
	}

	/** full constructor */
	public FEventShield(FEventShieldId id) {
		this.id = id;
	}

	// Property accessors

	public FEventShieldId getId() {
		return this.id;
	}

	public void setId(FEventShieldId id) {
		this.id = id;
	}

}