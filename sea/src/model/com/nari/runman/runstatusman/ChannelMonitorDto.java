package com.nari.runman.runstatusman;

import java.util.Date;

/**
 * 
 * 终端统计Dto
 * 
 * @author ChunMingLi
 * 2010-5-1
 *
 */
public class ChannelMonitorDto {
	
	//供电单位
	private String orgNo;
	
	//户号
	private String consNo;
	
	//户名
	private String consName;
	
	//终端地址
	private String terminalAddr;
	
	//终端状态
	private String terminalstate;
	
	//离线时间
	private Date offTime;
	
	//网关地址
	private String gateIp;
	
	//网关端口
	private String gatePort;
	
	//规约类型
	private String ruleType;
	
	//终端类型
	private String terminalType;

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

	public String getTerminalstate() {
		return terminalstate;
	}

	public void setTerminalstate(String terminalstate) {
		this.terminalstate = terminalstate;
	}

	public Date getOffTime() {
		return offTime;
	}

	public void setOffTime(Date offTime) {
		this.offTime = offTime;
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

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	

}
