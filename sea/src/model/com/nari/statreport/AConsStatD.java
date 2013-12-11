package com.nari.statreport;

/**
 * AConsStatD entity. @author MyEclipse Persistence Tools
 */

public class AConsStatD implements java.io.Serializable {

	// Fields

	private AConsStatDId id;

	// Constructors

	/** default constructor */
	public AConsStatD() {
	}

	/** full constructor */
	public AConsStatD(AConsStatDId id) {
		this.id = id;
	}

	// Property accessors

	public AConsStatDId getId() {
		return this.id;
	}

	public void setId(AConsStatDId id) {
		this.id = id;
	}

}