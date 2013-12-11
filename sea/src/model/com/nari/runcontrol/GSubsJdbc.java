package com.nari.runcontrol;

import java.sql.Timestamp;


public class GSubsJdbc {
	private   Long subsId;
	private   String subsName;
	private   String subsNo;
	private   String voltCode;
	private   String subsAddr;
	private   Integer mtNum;
	private   Long mtCap;
	private   String orgNo;
	private   String inlineId;
	private   Timestamp chgDate;
	private   String runStatusCode;
	public Long getSubsId() {
		return subsId;
	}
	public void setSubsId(Long subsId) {
		this.subsId = subsId;
	}
	public String getSubsName() {
		return subsName;
	}
	public void setSubsName(String subsName) {
		this.subsName = subsName;
	}
	public String getSubsNo() {
		return subsNo;
	}
	public void setSubsNo(String subsNo) {
		this.subsNo = subsNo;
	}
	public String getVoltCode() {
		return voltCode;
	}
	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}
	public String getSubsAddr() {
		return subsAddr;
	}
	public void setSubsAddr(String subsAddr) {
		this.subsAddr = subsAddr;
	}
	public Integer getMtNum() {
		return mtNum;
	}
	public void setMtNum(Integer mtNum) {
		this.mtNum = mtNum;
	}
	public Long getMtCap() {
		return mtCap;
	}
	public void setMtCap(Long mtCap) {
		this.mtCap = mtCap;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getInlineId() {
		return inlineId;
	}
	public void setInlineId(String inlineId) {
		this.inlineId = inlineId;
	}
	public Timestamp getChgDate() {
		return chgDate;
	}
	public void setChgDate(Timestamp chgDate) {
		this.chgDate = chgDate;
	}
	public String getRunStatusCode() {
		return runStatusCode;
	}
	public void setRunStatusCode(String runStatusCode) {
		this.runStatusCode = runStatusCode;
	}
	
}
