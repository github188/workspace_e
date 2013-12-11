package com.nari.runcontrol;

/**
 * RTmnlIrTask entity. @author MyEclipse Persistence Tools
 */

public class RTmnlIrTask implements java.io.Serializable {

	// Fields

	private Long irTaskId;
	private String appNo;
	private String cpNo;
	private Long assetId;
	private String assetTypeCode;
	private String chgMode;
	private String terminalTypeCode;
	private String objectNo;
	private String cpTypeCode;
	private String status;

	// Constructors

	/** default constructor */
	public RTmnlIrTask() {
	}

	/** minimal constructor */
	public RTmnlIrTask(String cpNo, String terminalTypeCode, String cpTypeCode) {
		this.cpNo = cpNo;
		this.terminalTypeCode = terminalTypeCode;
		this.cpTypeCode = cpTypeCode;
	}

	/** full constructor */
	public RTmnlIrTask(String appNo, String cpNo, Long assetId,
			String assetTypeCode, String chgMode, String terminalTypeCode,
			String objectNo, String cpTypeCode, String status) {
		this.appNo = appNo;
		this.cpNo = cpNo;
		this.assetId = assetId;
		this.assetTypeCode = assetTypeCode;
		this.chgMode = chgMode;
		this.terminalTypeCode = terminalTypeCode;
		this.objectNo = objectNo;
		this.cpTypeCode = cpTypeCode;
		this.status = status;
	}

	// Property accessors

	public Long getIrTaskId() {
		return this.irTaskId;
	}

	public void setIrTaskId(Long irTaskId) {
		this.irTaskId = irTaskId;
	}

	public String getAppNo() {
		return this.appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getCpNo() {
		return this.cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

	public Long getAssetId() {
		return this.assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public String getAssetTypeCode() {
		return this.assetTypeCode;
	}

	public void setAssetTypeCode(String assetTypeCode) {
		this.assetTypeCode = assetTypeCode;
	}

	public String getChgMode() {
		return this.chgMode;
	}

	public void setChgMode(String chgMode) {
		this.chgMode = chgMode;
	}

	public String getTerminalTypeCode() {
		return this.terminalTypeCode;
	}

	public void setTerminalTypeCode(String terminalTypeCode) {
		this.terminalTypeCode = terminalTypeCode;
	}

	public String getObjectNo() {
		return this.objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public String getCpTypeCode() {
		return this.cpTypeCode;
	}

	public void setCpTypeCode(String cpTypeCode) {
		this.cpTypeCode = cpTypeCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}