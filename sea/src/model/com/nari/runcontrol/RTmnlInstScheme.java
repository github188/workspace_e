package com.nari.runcontrol;

import java.util.Date;

/**
 * RTmnlInstScheme entity. @author MyEclipse Persistence Tools
 */

public class RTmnlInstScheme implements java.io.Serializable {

	// Fields

	private Long instSchemeId;
	private Long designSchemeId;
	private String cpNo;
	private String terminalLoc;
	private String commMode;
	private Integer downlinkIntensity;
	private Integer uplinkIntensity;
	private Date fixDate;
	private Date finishTime;
	private String terminalFlag;

	// Constructors

	/** default constructor */
	public RTmnlInstScheme() {
	}

	/** minimal constructor */
	public RTmnlInstScheme(Long designSchemeId, String cpNo, Date fixDate) {
		this.designSchemeId = designSchemeId;
		this.cpNo = cpNo;
		this.fixDate = fixDate;
	}

	/** full constructor */
	public RTmnlInstScheme(Long designSchemeId, String cpNo,
			String terminalLoc, String commMode, Integer downlinkIntensity,
			Integer uplinkIntensity, Date fixDate, Date finishTime,
			String terminalFlag) {
		this.designSchemeId = designSchemeId;
		this.cpNo = cpNo;
		this.terminalLoc = terminalLoc;
		this.commMode = commMode;
		this.downlinkIntensity = downlinkIntensity;
		this.uplinkIntensity = uplinkIntensity;
		this.fixDate = fixDate;
		this.finishTime = finishTime;
		this.terminalFlag = terminalFlag;
	}

	// Property accessors

	public Long getInstSchemeId() {
		return this.instSchemeId;
	}

	public void setInstSchemeId(Long instSchemeId) {
		this.instSchemeId = instSchemeId;
	}

	public Long getDesignSchemeId() {
		return this.designSchemeId;
	}

	public void setDesignSchemeId(Long designSchemeId) {
		this.designSchemeId = designSchemeId;
	}

	public String getCpNo() {
		return this.cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

	public String getTerminalLoc() {
		return this.terminalLoc;
	}

	public void setTerminalLoc(String terminalLoc) {
		this.terminalLoc = terminalLoc;
	}

	public String getCommMode() {
		return this.commMode;
	}

	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

	public Integer getDownlinkIntensity() {
		return this.downlinkIntensity;
	}

	public void setDownlinkIntensity(Integer downlinkIntensity) {
		this.downlinkIntensity = downlinkIntensity;
	}

	public Integer getUplinkIntensity() {
		return this.uplinkIntensity;
	}

	public void setUplinkIntensity(Integer uplinkIntensity) {
		this.uplinkIntensity = uplinkIntensity;
	}

	public Date getFixDate() {
		return this.fixDate;
	}

	public void setFixDate(Date fixDate) {
		this.fixDate = fixDate;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getTerminalFlag() {
		return this.terminalFlag;
	}

	public void setTerminalFlag(String terminalFlag) {
		this.terminalFlag = terminalFlag;
	}

}