package com.nari.runcontrol;

import java.util.Date;

/**
 * RCpCommPara entity. @author MyEclipse Persistence Tools
 */

public class RCpCommPara implements java.io.Serializable {

	// Fields

	private Long commParaId;
	private String cpNo;
	private String terminalAddr;
	private String protocolCode;
	private String channelNo;
	private Integer rtsOn;
	private Integer rtsOff;
	private Integer transmitDelay;
	private Integer respTimeout;
	private String masterIp;
	private Integer masterPort;
	private String spareIpAddr;
	private Integer sparePort;
	private String gatewayIp;
	private Integer gatewayPort;
	private String proxyIpAddr;
	private Integer proxyPort;
	private String gprsCode;
	private String smsNo;
	private String apn;
	private Integer heartbeatCycle;
	private Date startDate;
	private String algNo;
	private String algKey;

	// Constructors

	/** default constructor */
	public RCpCommPara() {
	}

	/** minimal constructor */
	public RCpCommPara(String cpNo, String terminalAddr, String protocolCode,
			String algNo, String algKey) {
		this.cpNo = cpNo;
		this.terminalAddr = terminalAddr;
		this.protocolCode = protocolCode;
		this.algNo = algNo;
		this.algKey = algKey;
	}

	/** full constructor */
	public RCpCommPara(String cpNo, String terminalAddr, String protocolCode,
			String channelNo, Integer rtsOn, Integer rtsOff,
			Integer transmitDelay, Integer respTimeout, String masterIp,
			Integer masterPort, String spareIpAddr, Integer sparePort,
			String gatewayIp, Integer gatewayPort, String proxyIpAddr,
			Integer proxyPort, String gprsCode, String smsNo, String apn,
			Integer heartbeatCycle, Date startDate, String algNo, String algKey) {
		this.cpNo = cpNo;
		this.terminalAddr = terminalAddr;
		this.protocolCode = protocolCode;
		this.channelNo = channelNo;
		this.rtsOn = rtsOn;
		this.rtsOff = rtsOff;
		this.transmitDelay = transmitDelay;
		this.respTimeout = respTimeout;
		this.masterIp = masterIp;
		this.masterPort = masterPort;
		this.spareIpAddr = spareIpAddr;
		this.sparePort = sparePort;
		this.gatewayIp = gatewayIp;
		this.gatewayPort = gatewayPort;
		this.proxyIpAddr = proxyIpAddr;
		this.proxyPort = proxyPort;
		this.gprsCode = gprsCode;
		this.smsNo = smsNo;
		this.apn = apn;
		this.heartbeatCycle = heartbeatCycle;
		this.startDate = startDate;
		this.algNo = algNo;
		this.algKey = algKey;
	}

	// Property accessors

	public Long getCommParaId() {
		return this.commParaId;
	}

	public void setCommParaId(Long commParaId) {
		this.commParaId = commParaId;
	}

	public String getCpNo() {
		return this.cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

	public String getTerminalAddr() {
		return this.terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	public String getProtocolCode() {
		return this.protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getChannelNo() {
		return this.channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public Integer getRtsOn() {
		return this.rtsOn;
	}

	public void setRtsOn(Integer rtsOn) {
		this.rtsOn = rtsOn;
	}

	public Integer getRtsOff() {
		return this.rtsOff;
	}

	public void setRtsOff(Integer rtsOff) {
		this.rtsOff = rtsOff;
	}

	public Integer getTransmitDelay() {
		return this.transmitDelay;
	}

	public void setTransmitDelay(Integer transmitDelay) {
		this.transmitDelay = transmitDelay;
	}

	public Integer getRespTimeout() {
		return this.respTimeout;
	}

	public void setRespTimeout(Integer respTimeout) {
		this.respTimeout = respTimeout;
	}

	public String getMasterIp() {
		return this.masterIp;
	}

	public void setMasterIp(String masterIp) {
		this.masterIp = masterIp;
	}

	public Integer getMasterPort() {
		return this.masterPort;
	}

	public void setMasterPort(Integer masterPort) {
		this.masterPort = masterPort;
	}

	public String getSpareIpAddr() {
		return this.spareIpAddr;
	}

	public void setSpareIpAddr(String spareIpAddr) {
		this.spareIpAddr = spareIpAddr;
	}

	public Integer getSparePort() {
		return this.sparePort;
	}

	public void setSparePort(Integer sparePort) {
		this.sparePort = sparePort;
	}

	public String getGatewayIp() {
		return this.gatewayIp;
	}

	public void setGatewayIp(String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}

	public Integer getGatewayPort() {
		return this.gatewayPort;
	}

	public void setGatewayPort(Integer gatewayPort) {
		this.gatewayPort = gatewayPort;
	}

	public String getProxyIpAddr() {
		return this.proxyIpAddr;
	}

	public void setProxyIpAddr(String proxyIpAddr) {
		this.proxyIpAddr = proxyIpAddr;
	}

	public Integer getProxyPort() {
		return this.proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getGprsCode() {
		return this.gprsCode;
	}

	public void setGprsCode(String gprsCode) {
		this.gprsCode = gprsCode;
	}

	public String getSmsNo() {
		return this.smsNo;
	}

	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}

	public String getApn() {
		return this.apn;
	}

	public void setApn(String apn) {
		this.apn = apn;
	}

	public Integer getHeartbeatCycle() {
		return this.heartbeatCycle;
	}

	public void setHeartbeatCycle(Integer heartbeatCycle) {
		this.heartbeatCycle = heartbeatCycle;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getAlgNo() {
		return this.algNo;
	}

	public void setAlgNo(String algNo) {
		this.algNo = algNo;
	}

	public String getAlgKey() {
		return this.algKey;
	}

	public void setAlgKey(String algKey) {
		this.algKey = algKey;
	}

}