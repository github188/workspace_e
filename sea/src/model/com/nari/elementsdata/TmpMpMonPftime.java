package com.nari.elementsdata;

/**
 * TmpMpMonPftime entity. @author MyEclipse Persistence Tools
 */

public class TmpMpMonPftime implements java.io.Serializable {

	// Fields

	private TmpMpMonPftimeId id;

	// Constructors

	/** default constructor */
	public TmpMpMonPftime() {
	}

	/** full constructor */
	public TmpMpMonPftime(TmpMpMonPftimeId id) {
		this.id = id;
	}

	// Property accessors

	public TmpMpMonPftimeId getId() {
		return this.id;
	}

	public void setId(TmpMpMonPftimeId id) {
		this.id = id;
	}

}