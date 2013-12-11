package com.nari.qrystat.simquery;

/**
 * 类 SimInstall
 * 
 * @author zhangzhw
 * @describe Sim 安装查询类
 */
public class SimInstall {
	private String orgName;
	private String consNo;
	private String consName;
	private String factoryName;
	private String modeName;//终端型号
	private String simNo;
	private String terminalAddr;
	private String elecAddr;
	private Byte isOnline;

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

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public String getTerminalAddr() {
		return terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	public String getElecAddr() {
		return elecAddr;
	}

	public void setElecAddr(String elecAddr) {
		this.elecAddr = elecAddr;
	}

	public Byte getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Byte isOnline) {
		this.isOnline = isOnline;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}
}