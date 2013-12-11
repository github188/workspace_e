package com.nari.statreport;

import java.sql.Timestamp;

public class TmnlRunStatusRun {
	private String orgName;
	private String consNo;
	private String consName;
	private String elecAddr;
	private String tmnlAssetNo;
	private String terminalAddr;
	private String commMode;
	private String isOnline;
	private Double currentLoad;
	private Timestamp loadTime;
	private String gateIp;
	private String gatePort;
	//终端状态类型
	private String tmnlType;

	//持续时间.当前时间-在线离线时间
	private String lastTime;

	public String getTmnlType() {
		return tmnlType;
	}

	public void setTmnlType(String tmnlType) {
		this.tmnlType = tmnlType;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getGateIp() {
		return gateIp;
	}

	public void setGateIp(String gateIp) {
		this.gateIp = gateIp;
	}

	public String getGatePort() {
		return gatePort;
	}

	public void setGatePort(String gatePort) {
		this.gatePort = gatePort;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getCommMode() {
		return commMode;
	}

	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public Double getCurrentLoad() {
		return currentLoad;
	}

	public void setCurrentLoad(Double currentLoad) {
		this.currentLoad = currentLoad;
	}

	public Timestamp getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(Timestamp loadTime) {
		this.loadTime = loadTime;
	}

	public String getTerminalAddr() {
		return terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

}