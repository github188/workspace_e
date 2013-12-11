package com.nari.grid;

import java.util.Date;

/**
 * GTg entity. @author MyEclipse Persistence Tools
 */

public class GTg implements java.io.Serializable {

	// Fields

	private Long tgId;
	private String orgNo;
	private String tgNo;
	private String tgName;
	private Double tgCap;
	private String instAddr;
	private Date chgDate;
	private String pubPrivFlag;
	private String runStatusCode;

	// Constructors

	/** default constructor */
	public GTg() {
	}

	/** minimal constructor */
	public GTg(String orgNo, String tgNo, String tgName) {
		this.orgNo = orgNo;
		this.tgNo = tgNo;
		this.tgName = tgName;
	}

	/** full constructor */
	public GTg(String orgNo, String tgNo, String tgName, Double tgCap,
			String instAddr, Date chgDate, String pubPrivFlag,
			String runStatusCode) {
		this.orgNo = orgNo;
		this.tgNo = tgNo;
		this.tgName = tgName;
		this.tgCap = tgCap;
		this.instAddr = instAddr;
		this.chgDate = chgDate;
		this.pubPrivFlag = pubPrivFlag;
		this.runStatusCode = runStatusCode;
	}

	// Property accessors

	public Long getTgId() {
		return this.tgId;
	}

	public void setTgId(Long tgId) {
		this.tgId = tgId;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getTgNo() {
		return this.tgNo;
	}

	public void setTgNo(String tgNo) {
		this.tgNo = tgNo;
	}

	public String getTgName() {
		return this.tgName;
	}

	public void setTgName(String tgName) {
		this.tgName = tgName;
	}

	public Double getTgCap() {
		return this.tgCap;
	}

	public void setTgCap(Double tgCap) {
		this.tgCap = tgCap;
	}

	public String getInstAddr() {
		return this.instAddr;
	}

	public void setInstAddr(String instAddr) {
		this.instAddr = instAddr;
	}

	public Date getChgDate() {
		return this.chgDate;
	}

	public void setChgDate(Date chgDate) {
		this.chgDate = chgDate;
	}

	public String getPubPrivFlag() {
		return this.pubPrivFlag;
	}

	public void setPubPrivFlag(String pubPrivFlag) {
		this.pubPrivFlag = pubPrivFlag;
	}

	public String getRunStatusCode() {
		return this.runStatusCode;
	}

	public void setRunStatusCode(String runStatusCode) {
		this.runStatusCode = runStatusCode;
	}

}