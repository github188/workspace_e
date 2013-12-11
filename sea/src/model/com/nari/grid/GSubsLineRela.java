package com.nari.grid;

/**
 * GSubsLineRela entity. @author MyEclipse Persistence Tools
 */

public class GSubsLineRela implements java.io.Serializable {

	// Fields

	private Long relaId;
	private Long subsId;
	private Long lineId;
	private String relaFlag;

	// Constructors

	/** default constructor */
	public GSubsLineRela() {
	}

	/** full constructor */
	public GSubsLineRela(Long subsId, Long lineId, String relaFlag) {
		this.subsId = subsId;
		this.lineId = lineId;
		this.relaFlag = relaFlag;
	}

	// Property accessors

	public Long getRelaId() {
		return this.relaId;
	}

	public void setRelaId(Long relaId) {
		this.relaId = relaId;
	}

	public Long getSubsId() {
		return this.subsId;
	}

	public void setSubsId(Long subsId) {
		this.subsId = subsId;
	}

	public Long getLineId() {
		return this.lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public String getRelaFlag() {
		return this.relaFlag;
	}

	public void setRelaFlag(String relaFlag) {
		this.relaFlag = relaFlag;
	}

}