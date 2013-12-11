package com.nari.basicdata;

/**
 * BCommProtItemList entity. @author MyEclipse Persistence Tools
 */

public class BCommProtItemList implements java.io.Serializable {

	// Fields

	private BCommProtItemListId id;

	// Constructors

	/** default constructor */
	public BCommProtItemList() {
	}

	/** full constructor */
	public BCommProtItemList(BCommProtItemListId id) {
		this.id = id;
	}

	// Property accessors

	public BCommProtItemListId getId() {
		return this.id;
	}

	public void setId(BCommProtItemListId id) {
		this.id = id;
	}

}