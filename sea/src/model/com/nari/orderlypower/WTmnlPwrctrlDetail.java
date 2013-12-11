package com.nari.orderlypower;

/**
 * WTmnlPwrctrlDetail entity. @author MyEclipse Persistence Tools
 */

public class WTmnlPwrctrlDetail implements java.io.Serializable {

	// Fields

	private WTmnlPwrctrlDetailId id;

	// Constructors

	/** default constructor */
	public WTmnlPwrctrlDetail() {
	}

	/** full constructor */
	public WTmnlPwrctrlDetail(WTmnlPwrctrlDetailId id) {
		this.id = id;
	}

	// Property accessors

	public WTmnlPwrctrlDetailId getId() {
		return this.id;
	}

	public void setId(WTmnlPwrctrlDetailId id) {
		this.id = id;
	}

}