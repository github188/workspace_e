package com.nari.elementsdata;

/**
 * TmpMpDayPftime entity. @author MyEclipse Persistence Tools
 */

public class TmpMpDayPftime implements java.io.Serializable {

	// Fields

	private TmpMpDayPftimeId id;

	// Constructors

	/** default constructor */
	public TmpMpDayPftime() {
	}

	/** full constructor */
	public TmpMpDayPftime(TmpMpDayPftimeId id) {
		this.id = id;
	}

	// Property accessors

	public TmpMpDayPftimeId getId() {
		return this.id;
	}

	public void setId(TmpMpDayPftimeId id) {
		this.id = id;
	}

}