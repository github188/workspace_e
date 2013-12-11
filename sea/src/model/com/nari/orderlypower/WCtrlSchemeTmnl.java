package com.nari.orderlypower;

/**
 * WCtrlSchemeTmnl entity. @author MyEclipse Persistence Tools
 */

public class WCtrlSchemeTmnl implements java.io.Serializable {

	// Fields

	private WCtrlSchemeTmnlId id;

	// Constructors

	/** default constructor */
	public WCtrlSchemeTmnl() {
	}

	/** full constructor */
	public WCtrlSchemeTmnl(WCtrlSchemeTmnlId id) {
		this.id = id;
	}

	// Property accessors

	public WCtrlSchemeTmnlId getId() {
		return this.id;
	}

	public void setId(WCtrlSchemeTmnlId id) {
		this.id = id;
	}

}