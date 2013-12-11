package com.nari.runcontrol;

import java.sql.Timestamp;

public class GLineJdbc {
	private   Long lineId;
	private   String lineNo;
	private   String lineName;
	private   String orgNo;
	private   String voltCode;
	private   String wireSpecCode;
	private Double wireLen;
	private   String sublineFlag;
	private Timestamp chgDate;
	private   String lnFlag;
	private   String ruralGridFlag;
	private   String runStatusCode;
	private   String llCalcMode;
	private Double apLlValue;
	private Double rpLlValue;
	private Double unitResi;
	private Double unitReac;
	private   Long consId;
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getVoltCode() {
		return voltCode;
	}
	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}
	public String getWireSpecCode() {
		return wireSpecCode;
	}
	public void setWireSpecCode(String wireSpecCode) {
		this.wireSpecCode = wireSpecCode;
	}
	public Double getWireLen() {
		return wireLen;
	}
	public void setWireLen(Double wireLen) {
		this.wireLen = wireLen;
	}
	public String getSublineFlag() {
		return sublineFlag;
	}
	public void setSublineFlag(String sublineFlag) {
		this.sublineFlag = sublineFlag;
	}
	public Timestamp getChgDate() {
		return chgDate;
	}
	public void setChgDate(Timestamp chgDate) {
		this.chgDate = chgDate;
	}
	public String getLnFlag() {
		return lnFlag;
	}
	public void setLnFlag(String lnFlag) {
		this.lnFlag = lnFlag;
	}
	public String getRuralGridFlag() {
		return ruralGridFlag;
	}
	public void setRuralGridFlag(String ruralGridFlag) {
		this.ruralGridFlag = ruralGridFlag;
	}
	public String getRunStatusCode() {
		return runStatusCode;
	}
	public void setRunStatusCode(String runStatusCode) {
		this.runStatusCode = runStatusCode;
	}
	public String getLlCalcMode() {
		return llCalcMode;
	}
	public void setLlCalcMode(String llCalcMode) {
		this.llCalcMode = llCalcMode;
	}
	public Double getApLlValue() {
		return apLlValue;
	}
	public void setApLlValue(Double apLlValue) {
		this.apLlValue = apLlValue;
	}
	public Double getRpLlValue() {
		return rpLlValue;
	}
	public void setRpLlValue(Double rpLlValue) {
		this.rpLlValue = rpLlValue;
	}
	public Double getUnitResi() {
		return unitResi;
	}
	public void setUnitResi(Double unitResi) {
		this.unitResi = unitResi;
	}
	public Double getUnitReac() {
		return unitReac;
	}
	public void setUnitReac(Double unitReac) {
		this.unitReac = unitReac;
	}
	public Long getConsId() {
		return consId;
	}
	public void setConsId(Long consId) {
		this.consId = consId;
	}
}
