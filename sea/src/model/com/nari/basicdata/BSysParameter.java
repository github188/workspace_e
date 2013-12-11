package com.nari.basicdata;

/**
 * BSysParameter entity. @author MyEclipse Persistence Tools
 */

public class BSysParameter implements java.io.Serializable {

	// Fields

	private BSysParameterId id;

	// Constructors

	/** default constructor */
	public BSysParameter() {
	}

	/** full constructor */
	public BSysParameter(BSysParameterId id) {
		this.id = id;
	}

	// Property accessors

	public BSysParameterId getId() {
		return this.id;
	}

	public void setId(BSysParameterId id) {
		this.id = id;
	}

}