package com.nari.flowhandle;

/**
 * FEventClose entity. @author MyEclipse Persistence Tools
 */

public class FEventClose implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8791834898859664781L;
	private FEventCloseId id;

	// Constructors

	/** default constructor */
	public FEventClose() {
	}

	/** full constructor */
	public FEventClose(FEventCloseId id) {
		this.id = id;
	}

	// Property accessors

	public FEventCloseId getId() {
		return this.id;
	}

	public void setId(FEventCloseId id) {
		this.id = id;
	}

}