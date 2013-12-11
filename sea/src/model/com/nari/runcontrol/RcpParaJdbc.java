package com.nari.runcontrol;

import java.sql.Timestamp;
import java.util.Date;

public class RcpParaJdbc {
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

	public Long getCommParaId() {
		return commParaId;
	}

	public void setCommParaId(Long commParaId) {
		this.commParaId = commParaId;
	}

	public String getCpNo() {
		return cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
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

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public Integer getRtsOn() {
		return rtsOn;
	}

	public void setRtsOn(Integer rtsOn) {
		this.rtsOn = rtsOn;
	}

	public Integer getRtsOff() {
		return rtsOff;
	}

	public void setRtsOff(Integer rtsOff) {
		this.rtsOff = rtsOff;
	}

	public Integer getTransmitDelay() {
		return transmitDelay;
	}

	public void setTransmitDelay(Integer transmitDelay) {
		this.transmitDelay = transmitDelay;
	}

	public Integer getRespTimeout() {
		return respTimeout;
	}

	public void setRespTimeout(Integer respTimeout) {
		this.respTimeout = respTimeout;
	}

	public String getMasterIp() {
		return masterIp;
	}

	public void setMasterIp(String masterIp) {
		this.masterIp = masterIp;
	}

	public Integer getMasterPort() {
		return masterPort;
	}

	public void setMasterPort(Integer masterPort) {
		this.masterPort = masterPort;
	}

	public String getSpareIpAddr() {
		return spareIpAddr;
	}

	public void setSpareIpAddr(String spareIpAddr) {
		this.spareIpAddr = spareIpAddr;
	}

	public Integer getSparePort() {
		return sparePort;
	}

	public void setSparePort(Integer sparePort) {
		this.sparePort = sparePort;
	}

	public String getGatewayIp() {
		return gatewayIp;
	}

	public void setGatewayIp(String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}

	public Integer getGatewayPort() {
		return gatewayPort;
	}

	public void setGatewayPort(Integer gatewayPort) {
		this.gatewayPort = gatewayPort;
	}

	public String getProxyIpAddr() {
		return proxyIpAddr;
	}

	public void setProxyIpAddr(String proxyIpAddr) {
		this.proxyIpAddr = proxyIpAddr;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getGprsCode() {
		return gprsCode;
	}

	public void setGprsCode(String gprsCode) {
		this.gprsCode = gprsCode;
	}

	public String getSmsNo() {
		return smsNo;
	}

	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}

	public String getApn() {
		return apn;
	}

	public void setApn(String apn) {
		this.apn = apn;
	}

	public Integer getHeartbeatCycle() {
		return heartbeatCycle;
	}

	public void setHeartbeatCycle(Integer heartbeatCycle) {
		this.heartbeatCycle = heartbeatCycle;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getAlgNo() {
		if(null==algNo||algNo.isEmpty())
			return "99";
		return algNo;
	}

	public void setAlgNo(String algNo) {
		this.algNo = algNo;
	}

	public String getAlgKey() {
		if(null==algKey||algKey.isEmpty())
			return "0";
		return algKey;
	}

	public void setAlgKey(String algKey) {
		this.algKey = algKey;
	}

}