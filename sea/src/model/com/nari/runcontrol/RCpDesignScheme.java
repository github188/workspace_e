package com.nari.runcontrol;

import java.util.Date;

/**
 * RCpDesignScheme entity. @author MyEclipse Persistence Tools
 */

public class RCpDesignScheme implements java.io.Serializable {

	// Fields

	private Long designSchemeId;
	private String appNo;
	private Long assetId;
	private String assetTypeCode;
	private String chgMode;
	private String terminalTypeCode;
	private String collMode;
	private String aidNodeCode;
	private String qlfFlag;
	private String disqlfContent;
	private String verifierNo;
	private Date chkDate;
	private String schemaSrc;

	// Constructors

	/** default constructor */
	public RCpDesignScheme() {
	}

	/** minimal constructor */
	public RCpDesignScheme(String terminalTypeCode, String collMode,
			String qlfFlag, String verifierNo, Date chkDate) {
		this.terminalTypeCode = terminalTypeCode;
		this.collMode = collMode;
		this.qlfFlag = qlfFlag;
		this.verifierNo = verifierNo;
		this.chkDate = chkDate;
	}

	/** full constructor */
	public RCpDesignScheme(String appNo, Long assetId, String assetTypeCode,
			String chgMode, String terminalTypeCode, String collMode,
			String aidNodeCode, String qlfFlag, String disqlfContent,
			String verifierNo, Date chkDate, String schemaSrc) {
		this.appNo = appNo;
		this.assetId = assetId;
		this.assetTypeCode = assetTypeCode;
		this.chgMode = chgMode;
		this.terminalTypeCode = terminalTypeCode;
		this.collMode = collMode;
		this.aidNodeCode = aidNodeCode;
		this.qlfFlag = qlfFlag;
		this.disqlfContent = disqlfContent;
		this.verifierNo = verifierNo;
		this.chkDate = chkDate;
		this.schemaSrc = schemaSrc;
	}

	// Property accessors

	public Long getDesignSchemeId() {
		return this.designSchemeId;
	}

	public void setDesignSchemeId(Long designSchemeId) {
		this.designSchemeId = designSchemeId;
	}

	public String getAppNo() {
		return this.appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
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

	public String getCollMode() {
		return this.collMode;
	}

	public void setCollMode(String collMode) {
		this.collMode = collMode;
	}

	public String getAidNodeCode() {
		return this.aidNodeCode;
	}

	public void setAidNodeCode(String aidNodeCode) {
		this.aidNodeCode = aidNodeCode;
	}

	public String getQlfFlag() {
		return this.qlfFlag;
	}

	public void setQlfFlag(String qlfFlag) {
		this.qlfFlag = qlfFlag;
	}

	public String getDisqlfContent() {
		return this.disqlfContent;
	}

	public void setDisqlfContent(String disqlfContent) {
		this.disqlfContent = disqlfContent;
	}

	public String getVerifierNo() {
		return this.verifierNo;
	}

	public void setVerifierNo(String verifierNo) {
		this.verifierNo = verifierNo;
	}

	public Date getChkDate() {
		return this.chkDate;
	}

	public void setChkDate(Date chkDate) {
		this.chkDate = chkDate;
	}

	public String getSchemaSrc() {
		return this.schemaSrc;
	}

	public void setSchemaSrc(String schemaSrc) {
		this.schemaSrc = schemaSrc;
	}

}