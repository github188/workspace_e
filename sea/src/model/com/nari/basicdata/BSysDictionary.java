package com.nari.basicdata;

/**
 * BSysDictionary entity. @author MyEclipse Persistence Tools
 */

public class BSysDictionary implements java.io.Serializable {

	// Fields

	private BSysDictionaryId id;

	// Constructors

	/** default constructor */
	public BSysDictionary() {
	}

	/** full constructor */
	public BSysDictionary(BSysDictionaryId id) {
		this.id = id;
	}

	// Property accessors

	public BSysDictionaryId getId() {
		return this.id;
	}

	public void setId(BSysDictionaryId id) {
		this.id = id;
	}

}