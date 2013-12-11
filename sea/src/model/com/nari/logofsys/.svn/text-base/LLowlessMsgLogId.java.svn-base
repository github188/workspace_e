package com.nari.logofsys;

import java.util.Date;

/**
 * LLowlessMsgLogId entity. @author MyEclipse Persistence Tools
 */

public class LLowlessMsgLogId implements java.io.Serializable {

	// Fields

	private String terminalAddr;
	private String tmnlCode;
	private String ctrlCode;
	private Date commTime;
	private String message;

	// Constructors

	/** default constructor */
	public LLowlessMsgLogId() {
	}

	/** full constructor */
	public LLowlessMsgLogId(String terminalAddr, String tmnlCode,
			String ctrlCode, Date commTime, String message) {
		this.terminalAddr = terminalAddr;
		this.tmnlCode = tmnlCode;
		this.ctrlCode = ctrlCode;
		this.commTime = commTime;
		this.message = message;
	}

	// Property accessors

	public String getTerminalAddr() {
		return this.terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	public String getTmnlCode() {
		return this.tmnlCode;
	}

	public void setTmnlCode(String tmnlCode) {
		this.tmnlCode = tmnlCode;
	}

	public String getCtrlCode() {
		return this.ctrlCode;
	}

	public void setCtrlCode(String ctrlCode) {
		this.ctrlCode = ctrlCode;
	}

	public Date getCommTime() {
		return this.commTime;
	}

	public void setCommTime(Date commTime) {
		this.commTime = commTime;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LLowlessMsgLogId))
			return false;
		LLowlessMsgLogId castOther = (LLowlessMsgLogId) other;

		return ((this.getTerminalAddr() == castOther.getTerminalAddr()) || (this
				.getTerminalAddr() != null
				&& castOther.getTerminalAddr() != null && this
				.getTerminalAddr().equals(castOther.getTerminalAddr())))
				&& ((this.getTmnlCode() == castOther.getTmnlCode()) || (this
						.getTmnlCode() != null
						&& castOther.getTmnlCode() != null && this
						.getTmnlCode().equals(castOther.getTmnlCode())))
				&& ((this.getCtrlCode() == castOther.getCtrlCode()) || (this
						.getCtrlCode() != null
						&& castOther.getCtrlCode() != null && this
						.getCtrlCode().equals(castOther.getCtrlCode())))
				&& ((this.getCommTime() == castOther.getCommTime()) || (this
						.getCommTime() != null
						&& castOther.getCommTime() != null && this
						.getCommTime().equals(castOther.getCommTime())))
				&& ((this.getMessage() == castOther.getMessage()) || (this
						.getMessage() != null
						&& castOther.getMessage() != null && this.getMessage()
						.equals(castOther.getMessage())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTerminalAddr() == null ? 0 : this.getTerminalAddr()
						.hashCode());
		result = 37 * result
				+ (getTmnlCode() == null ? 0 : this.getTmnlCode().hashCode());
		result = 37 * result
				+ (getCtrlCode() == null ? 0 : this.getCtrlCode().hashCode());
		result = 37 * result
				+ (getCommTime() == null ? 0 : this.getCommTime().hashCode());
		result = 37 * result
				+ (getMessage() == null ? 0 : this.getMessage().hashCode());
		return result;
	}

}