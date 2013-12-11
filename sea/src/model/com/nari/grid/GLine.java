package com.nari.grid;

import java.util.Date;

/**
 * GLine entity. @author MyEclipse Persistence Tools
 */

public class GLine implements java.io.Serializable {

	// Fields

	private Long lineId;
	private String lineNo;
	private String lineName;
	private String orgNo;
	private String voltCode;
	private String wireSpecCode;
	private Double wireLen;
	private String sublineFlag;
	private Date chgDate;
	private String lnFlag;
	private String ruralGridFlag;
	private String runStatusCode;
	private String llCalcMode;
	private Double apLlValue;
	private Double rpLlValue;
	private Double unitResi;
	private Double unitReac;

	// Constructors

	/** default constructor */
	public GLine() {
	}

	/** minimal constructor */
	public GLine(String lineName, String orgNo, String voltCode, String lnFlag) {
		this.lineName = lineName;
		this.orgNo = orgNo;
		this.voltCode = voltCode;
		this.lnFlag = lnFlag;
	}

	/** full constructor */
	public GLine(String lineNo, String lineName, String orgNo, String voltCode,
			String wireSpecCode, Double wireLen, String sublineFlag,
			Date chgDate, String lnFlag, String ruralGridFlag,
			String runStatusCode, String llCalcMode, Double apLlValue,
			Double rpLlValue, Double unitResi, Double unitReac) {
		this.lineNo = lineNo;
		this.lineName = lineName;
		this.orgNo = orgNo;
		this.voltCode = voltCode;
		this.wireSpecCode = wireSpecCode;
		this.wireLen = wireLen;
		this.sublineFlag = sublineFlag;
		this.chgDate = chgDate;
		this.lnFlag = lnFlag;
		this.ruralGridFlag = ruralGridFlag;
		this.runStatusCode = runStatusCode;
		this.llCalcMode = llCalcMode;
		this.apLlValue = apLlValue;
		this.rpLlValue = rpLlValue;
		this.unitResi = unitResi;
		this.unitReac = unitReac;
	}

	// Property accessors

	public Long getLineId() {
		return this.lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public String getLineNo() {
		return this.lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getLineName() {
		return this.lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getVoltCode() {
		return this.voltCode;
	}

	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}

	public String getWireSpecCode() {
		return this.wireSpecCode;
	}

	public void setWireSpecCode(String wireSpecCode) {
		this.wireSpecCode = wireSpecCode;
	}

	public Double getWireLen() {
		return this.wireLen;
	}

	public void setWireLen(Double wireLen) {
		this.wireLen = wireLen;
	}

	public String getSublineFlag() {
		return this.sublineFlag;
	}

	public void setSublineFlag(String sublineFlag) {
		this.sublineFlag = sublineFlag;
	}

	public Date getChgDate() {
		return this.chgDate;
	}

	public void setChgDate(Date chgDate) {
		this.chgDate = chgDate;
	}

	public String getLnFlag() {
		return this.lnFlag;
	}

	public void setLnFlag(String lnFlag) {
		this.lnFlag = lnFlag;
	}

	public String getRuralGridFlag() {
		return this.ruralGridFlag;
	}

	public void setRuralGridFlag(String ruralGridFlag) {
		this.ruralGridFlag = ruralGridFlag;
	}

	public String getRunStatusCode() {
		return this.runStatusCode;
	}

	public void setRunStatusCode(String runStatusCode) {
		this.runStatusCode = runStatusCode;
	}

	public String getLlCalcMode() {
		return this.llCalcMode;
	}

	public void setLlCalcMode(String llCalcMode) {
		this.llCalcMode = llCalcMode;
	}

	public Double getApLlValue() {
		return this.apLlValue;
	}

	public void setApLlValue(Double apLlValue) {
		this.apLlValue = apLlValue;
	}

	public Double getRpLlValue() {
		return this.rpLlValue;
	}

	public void setRpLlValue(Double rpLlValue) {
		this.rpLlValue = rpLlValue;
	}

	public Double getUnitResi() {
		return this.unitResi;
	}

	public void setUnitResi(Double unitResi) {
		this.unitResi = unitResi;
	}

	public Double getUnitReac() {
		return this.unitReac;
	}

	public void setUnitReac(Double unitReac) {
		this.unitReac = unitReac;
	}

}