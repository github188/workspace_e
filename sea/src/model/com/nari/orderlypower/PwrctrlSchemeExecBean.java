package com.nari.orderlypower;

public class PwrctrlSchemeExecBean {
	private String keyId;
	private String orgName;
	private String orgNo;
	private String consNo;
	private String consName;
	private String terminalAddr;
	private String tmnlAssetNo;
	private Short totalNo;
	private String tmnlPaulPower;
	private String ctrlFlag="-1";
	private String message="";
	private String protocolCode;
	private String execFlag;

	public String getCtrlFlag() {
		return ctrlFlag;
	}

	public void setCtrlFlag(String ctrlFlag) {
		this.ctrlFlag = ctrlFlag;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getConsName() {
		return consName;
	}

	public void setConsName(String consName) {
		this.consName = consName;
	}

	public String getTerminalAddr() {
		return terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	public Short getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(Short totalNo) {
		this.totalNo = totalNo;
	}

	public String getTmnlPaulPower() {
		return tmnlPaulPower;
	}

	public void setTmnlPaulPower(String tmnlPaulPower) {
		this.tmnlPaulPower = tmnlPaulPower;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getExecFlag() {
		return execFlag;
	}

	public void setExecFlag(String execFlag) {
		this.execFlag = execFlag;
	}
}
