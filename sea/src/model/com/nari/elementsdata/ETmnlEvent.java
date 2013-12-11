package com.nari.elementsdata;

/**
 * ETmnlEvent entity. @author MyEclipse Persistence Tools
 */

public class ETmnlEvent implements java.io.Serializable {

	// Fields

	private ETmnlEventId id;

	// Constructors

	/** default constructor */
	public ETmnlEvent() {
	}

	/** full constructor */
	public ETmnlEvent(ETmnlEventId id) {
		this.id = id;
	}

	// Property accessors

	public ETmnlEventId getId() {
		return this.id;
	}

	public void setId(ETmnlEventId id) {
		this.id = id;
	}

}