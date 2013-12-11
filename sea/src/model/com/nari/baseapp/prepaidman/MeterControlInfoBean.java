package com.nari.baseapp.prepaidman;

public class MeterControlInfoBean {
	private String consNO;
	private String tmnlAssetNo;
	private String consName;
	private String orgName;
	private String terminalAddr;
	private String protocolCode;
	private Long meterId;
	private String commAddr;
	private String keyId;
	private Short mpSn;
	private String switchStatus;
	private String dueStatus;
	private String dueDate;

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(String switchStatus) {
		this.switchStatus = switchStatus;
	}

	public String getDueStatus() {
		return dueStatus;
	}

	public void setDueStatus(String dueStatus) {
		this.dueStatus = dueStatus;
	}

	public String getConsNO() {
		return consNO;
	}

	public void setConsNO(String consNO) {
		this.consNO = consNO;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getConsName() {
		return consName;
	}

	public void setConsName(String consName) {
		this.consName = consName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getTerminalAddr() {
		return terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public String getCommAddr() {
		return commAddr;
	}

	public void setCommAddr(String commAddr) {
		this.commAddr = commAddr;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public Short getMpSn() {
		return mpSn;
	}

	public void setMpSn(Short mpSn) {
		this.mpSn = mpSn;
	}

}
