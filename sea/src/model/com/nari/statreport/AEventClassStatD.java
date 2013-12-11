package com.nari.statreport;

/**
 * AEventClassStatD entity. @author MyEclipse Persistence Tools
 */

public class AEventClassStatD implements java.io.Serializable {

	// Fields

	private AEventClassStatDId id;

	// Constructors

	/** default constructor */
	public AEventClassStatD() {
	}

	/** full constructor */
	public AEventClassStatD(AEventClassStatDId id) {
		this.id = id;
	}

	// Property accessors

	public AEventClassStatDId getId() {
		return this.id;
	}

	public void setId(AEventClassStatDId id) {
		this.id = id;
	}

}