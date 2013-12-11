package com.nari.grid;

import java.util.Date;

/**
 * GSubs entity. @author MyEclipse Persistence Tools
 */

public class GSubs implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Long subsId;
	private String subsName;
	private String subsNo;
	private String voltCode;
	private String subsAddr;
	private Integer mtNum;
	private Long mtCap;
	private String orgNo;
	private String inlineId;
	private Date chgDate;
	private String runStatusCode;

	// Constructors

	/** default constructor */
	public GSubs() {
	}

	/** minimal constructor */
	public GSubs(String subsName, String subsNo, String voltCode, String orgNo,
			String runStatusCode) {
		this.subsName = subsName;
		this.subsNo = subsNo;
		this.voltCode = voltCode;
		this.orgNo = orgNo;
		this.runStatusCode = runStatusCode;
	}

	/** full constructor */
	public GSubs(String subsName, String subsNo, String voltCode,
			String subsAddr, Integer mtNum, Long mtCap, String orgNo,
			String inlineId, Date chgDate, String runStatusCode) {
		this.subsName = subsName;
		this.subsNo = subsNo;
		this.voltCode = voltCode;
		this.subsAddr = subsAddr;
		this.mtNum = mtNum;
		this.mtCap = mtCap;
		this.orgNo = orgNo;
		this.inlineId = inlineId;
		this.chgDate = chgDate;
		this.runStatusCode = runStatusCode;
	}

	// Property accessors

	public Long getSubsId() {
		return this.subsId;
	}

	public void setSubsId(Long subsId) {
		this.subsId = subsId;
	}

	public String getSubsName() {
		return this.subsName;
	}

	public void setSubsName(String subsName) {
		this.subsName = subsName;
	}

	public String getSubsNo() {
		return this.subsNo;
	}

	public void setSubsNo(String subsNo) {
		this.subsNo = subsNo;
	}

	public String getVoltCode() {
		return this.voltCode;
	}

	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}

	public String getSubsAddr() {
		return this.subsAddr;
	}

	public void setSubsAddr(String subsAddr) {
		this.subsAddr = subsAddr;
	}

	public Integer getMtNum() {
		return this.mtNum;
	}

	public void setMtNum(Integer mtNum) {
		this.mtNum = mtNum;
	}

	public Long getMtCap() {
		return this.mtCap;
	}

	public void setMtCap(Long mtCap) {
		this.mtCap = mtCap;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getInlineId() {
		return this.inlineId;
	}

	public void setInlineId(String inlineId) {
		this.inlineId = inlineId;
	}

	public Date getChgDate() {
		return this.chgDate;
	}

	public void setChgDate(Date chgDate) {
		this.chgDate = chgDate;
	}

	public String getRunStatusCode() {
		return this.runStatusCode;
	}

	public void setRunStatusCode(String runStatusCode) {
		this.runStatusCode = runStatusCode;
	}

}