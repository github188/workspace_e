package com.nari.orderlypower;

public class WFeeCtrlBean {
	private String orgName;//供电单位
	private String orgNo;//供电单位编号
	private String consNo;//用户编号
	private String consName;//用户名称
	private String elecAddr;//用户地址
	private String tmnlAddr;//终端地址
	private Short totalNo;//总加组
	private String leftPower;//剩余电量
	private String debugStatus;//调试状态
	private String keyId;//界面唯一标识，终端资产编号+总加组号
	private String tmnlAssetNo;//终端资产编号
	private String protocolCode;//通讯规约编码

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

	public String getElecAddr() {
		return elecAddr;
	}

	public void setElecAddr(String elecAddr) {
		this.elecAddr = elecAddr;
	}

	public Short getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(Short totalNo) {
		this.totalNo = totalNo;
	}

	public String getLeftPower() {
		return leftPower;
	}

	public void setLeftPower(String leftPower) {
		this.leftPower = leftPower;
	}

	public String getDebugStatus() {
		return debugStatus;
	}

	public void setDebugStatus(String debugStatus) {
		this.debugStatus = debugStatus;
	}

	public String getTmnlAddr() {
		return tmnlAddr;
	}

	public void setTmnlAddr(String tmnlAddr) {
		this.tmnlAddr = tmnlAddr;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}
}