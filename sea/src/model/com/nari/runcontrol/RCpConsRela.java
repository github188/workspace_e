package com.nari.runcontrol;

/**
 * RCpConsRela entity. @author MyEclipse Persistence Tools
 */

public class RCpConsRela implements java.io.Serializable {

	// Fields

	private Long cpConsId;
	private Long consId;
	private String cpNo;

	// Constructors

	/** default constructor */
	public RCpConsRela() {
	}

	/** full constructor */
	public RCpConsRela(Long consId, String cpNo) {
		this.consId = consId;
		this.cpNo = cpNo;
	}

	// Property accessors

	public Long getCpConsId() {
		return this.cpConsId;
	}

	public void setCpConsId(Long cpConsId) {
		this.cpConsId = cpConsId;
	}

	public Long getConsId() {
		return this.consId;
	}

	public void setConsId(Long consId) {
		this.consId = consId;
	}

	public String getCpNo() {
		return this.cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

}