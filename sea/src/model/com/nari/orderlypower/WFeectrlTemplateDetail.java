package com.nari.orderlypower;

/**
 * WFeectrlTemplateDetail entity. @author MyEclipse Persistence Tools
 */

public class WFeectrlTemplateDetail implements java.io.Serializable {

	// Fields

	private WFeectrlTemplateDetailId id;

	// Constructors

	/** default constructor */
	public WFeectrlTemplateDetail() {
	}

	/** full constructor */
	public WFeectrlTemplateDetail(WFeectrlTemplateDetailId id) {
		this.id = id;
	}

	// Property accessors

	public WFeectrlTemplateDetailId getId() {
		return this.id;
	}

	public void setId(WFeectrlTemplateDetailId id) {
		this.id = id;
	}

}