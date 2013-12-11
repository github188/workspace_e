package com.nari.runcontrol;

/**
 * RTmnlApp entity. @author MyEclipse Persistence Tools
 */

public class RTmnlApp implements java.io.Serializable {

	// Fields

	private Long termApplyId;
	private Long irTaskId;
	private String terminalTypeCode;
	private String collMode;
	private Integer appCount;
	private String commMode;

	// Constructors

	/** default constructor */
	public RTmnlApp() {
	}

	/** minimal constructor */
	public RTmnlApp(Long irTaskId, String terminalTypeCode, String collMode,
			Integer appCount) {
		this.irTaskId = irTaskId;
		this.terminalTypeCode = terminalTypeCode;
		this.collMode = collMode;
		this.appCount = appCount;
	}

	/** full constructor */
	public RTmnlApp(Long irTaskId, String terminalTypeCode, String collMode,
			Integer appCount, String commMode) {
		this.irTaskId = irTaskId;
		this.terminalTypeCode = terminalTypeCode;
		this.collMode = collMode;
		this.appCount = appCount;
		this.commMode = commMode;
	}

	// Property accessors

	public Long getTermApplyId() {
		return this.termApplyId;
	}

	public void setTermApplyId(Long termApplyId) {
		this.termApplyId = termApplyId;
	}

	public Long getIrTaskId() {
		return this.irTaskId;
	}

	public void setIrTaskId(Long irTaskId) {
		this.irTaskId = irTaskId;
	}

	public String getTerminalTypeCode() {
		return this.terminalTypeCode;
	}

	public void setTerminalTypeCode(String terminalTypeCode) {
		this.terminalTypeCode = terminalTypeCode;
	}

	public String getCollMode() {
		return this.collMode;
	}

	public void setCollMode(String collMode) {
		this.collMode = collMode;
	}

	public Integer getAppCount() {
		return this.appCount;
	}

	public void setAppCount(Integer appCount) {
		this.appCount = appCount;
	}

	public String getCommMode() {
		return this.commMode;
	}

	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

}