package com.nari.logofsys;

import java.util.Date;

/**
 * LDownCommMsgLogId entity. @author MyEclipse Persistence Tools
 */

public class LDownCommMsgLogId implements java.io.Serializable {

	// Fields

	private String terminalAddr;
	private String tmnlCode;
	private String ctrlCode;
	private String fromAddr;
	private String toAddr;
	private String commType;
	private Date commTime;
	private Short msgLen;
	private String message;

	// Constructors

	/** default constructor */
	public LDownCommMsgLogId() {
	}

	/** minimal constructor */
	public LDownCommMsgLogId(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	/** full constructor */
	public LDownCommMsgLogId(String terminalAddr, String tmnlCode,
			String ctrlCode, String fromAddr, String toAddr, String commType,
			Date commTime, Short msgLen, String message) {
		this.terminalAddr = terminalAddr;
		this.tmnlCode = tmnlCode;
		this.ctrlCode = ctrlCode;
		this.fromAddr = fromAddr;
		this.toAddr = toAddr;
		this.commType = commType;
		this.commTime = commTime;
		this.msgLen = msgLen;
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

	public String getFromAddr() {
		return this.fromAddr;
	}

	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}

	public String getToAddr() {
		return this.toAddr;
	}

	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}

	public String getCommType() {
		return this.commType;
	}

	public void setCommType(String commType) {
		this.commType = commType;
	}

	public Date getCommTime() {
		return this.commTime;
	}

	public void setCommTime(Date commTime) {
		this.commTime = commTime;
	}

	public Short getMsgLen() {
		return this.msgLen;
	}

	public void setMsgLen(Short msgLen) {
		this.msgLen = msgLen;
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
		if (!(other instanceof LDownCommMsgLogId))
			return false;
		LDownCommMsgLogId castOther = (LDownCommMsgLogId) other;

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
				&& ((this.getFromAddr() == castOther.getFromAddr()) || (this
						.getFromAddr() != null
						&& castOther.getFromAddr() != null && this
						.getFromAddr().equals(castOther.getFromAddr())))
				&& ((this.getToAddr() == castOther.getToAddr()) || (this
						.getToAddr() != null
						&& castOther.getToAddr() != null && this.getToAddr()
						.equals(castOther.getToAddr())))
				&& ((this.getCommType() == castOther.getCommType()) || (this
						.getCommType() != null
						&& castOther.getCommType() != null && this
						.getCommType().equals(castOther.getCommType())))
				&& ((this.getCommTime() == castOther.getCommTime()) || (this
						.getCommTime() != null
						&& castOther.getCommTime() != null && this
						.getCommTime().equals(castOther.getCommTime())))
				&& ((this.getMsgLen() == castOther.getMsgLen()) || (this
						.getMsgLen() != null
						&& castOther.getMsgLen() != null && this.getMsgLen()
						.equals(castOther.getMsgLen())))
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
				+ (getFromAddr() == null ? 0 : this.getFromAddr().hashCode());
		result = 37 * result
				+ (getToAddr() == null ? 0 : this.getToAddr().hashCode());
		result = 37 * result
				+ (getCommType() == null ? 0 : this.getCommType().hashCode());
		result = 37 * result
				+ (getCommTime() == null ? 0 : this.getCommTime().hashCode());
		result = 37 * result
				+ (getMsgLen() == null ? 0 : this.getMsgLen().hashCode());
		result = 37 * result
				+ (getMessage() == null ? 0 : this.getMessage().hashCode());
		return result;
	}

}