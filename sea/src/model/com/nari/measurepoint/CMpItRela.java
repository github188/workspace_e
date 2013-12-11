package com.nari.measurepoint;

/**
 * CMpItRela entity. @author MyEclipse Persistence Tools
 */

public class CMpItRela implements java.io.Serializable {

	// Fields

	private Long itMpId;
	private Long mpId;
	private Long itId;

	// Constructors

	/** default constructor */
	public CMpItRela() {
	}

	/** full constructor */
	public CMpItRela(Long mpId, Long itId) {
		this.mpId = mpId;
		this.itId = itId;
	}

	// Property accessors

	public Long getItMpId() {
		return this.itMpId;
	}

	public void setItMpId(Long itMpId) {
		this.itMpId = itMpId;
	}

	public Long getMpId() {
		return this.mpId;
	}

	public void setMpId(Long mpId) {
		this.mpId = mpId;
	}

	public Long getItId() {
		return this.itId;
	}

	public void setItId(Long itId) {
		this.itId = itId;
	}

}