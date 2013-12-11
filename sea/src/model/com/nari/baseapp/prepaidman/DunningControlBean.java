package com.nari.baseapp.prepaidman;

public class DunningControlBean {
	private String orgName;
	private String orgNo;
	private String consNo;
	private String consName;
	private String tmnlAssetNo;
	private String terminalAddr;// 终端地址
	private String tmnlPaulPower;// 保电
	private String ctrlFlag;// 控制状态
	private String execFlag;// 执行结果状态
	private String protocolCode;// 规约编码
	private String message;// 报文

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

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getTerminalAddr() {
		return terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	public String getTmnlPaulPower() {
		return tmnlPaulPower;
	}

	public void setTmnlPaulPower(String tmnlPaulPower) {
		this.tmnlPaulPower = tmnlPaulPower;
	}

	public String getCtrlFlag() {
		return ctrlFlag;
	}

	public void setCtrlFlag(String ctrlFlag) {
		this.ctrlFlag = ctrlFlag;
	}

	public String getExecFlag() {
		return execFlag;
	}

	public void setExecFlag(String execFlag) {
		this.execFlag = execFlag;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
