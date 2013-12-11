package com.nari.elementsdata;

/**
 * TmpMpDayUnbalance entity. @author MyEclipse Persistence Tools
 */

public class TmpMpDayUnbalance implements java.io.Serializable {

	// Fields

	private TmpMpDayUnbalanceId id;

	// Constructors

	/** default constructor */
	public TmpMpDayUnbalance() {
	}

	/** full constructor */
	public TmpMpDayUnbalance(TmpMpDayUnbalanceId id) {
		this.id = id;
	}

	// Property accessors

	public TmpMpDayUnbalanceId getId() {
		return this.id;
	}

	public void setId(TmpMpDayUnbalanceId id) {
		this.id = id;
	}

}