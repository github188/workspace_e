package com.nari.basicdata;

/**
 * BCpCommScheme entity. @author MyEclipse Persistence Tools
 */

public class BCpCommScheme implements java.io.Serializable {

	// Fields

	private Long commSchemeId;
	private String commSchemeName;
	private String orgNo;
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
	private String smsNo;
	private String apn;

	// Constructors

	/** default constructor */
	public BCpCommScheme() {
	}

	/** full constructor */
	public BCpCommScheme(String commSchemeName, String orgNo, String channelNo,
			Integer rtsOn, Integer rtsOff, Integer transmitDelay,
			Integer respTimeout, String masterIp, Integer masterPort,
			String spareIpAddr, Integer sparePort, String gatewayIp,
			Integer gatewayPort, String proxyIpAddr, Integer proxyPort,
			String smsNo, String apn) {
		this.commSchemeName = commSchemeName;
		this.orgNo = orgNo;
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
		this.smsNo = smsNo;
		this.apn = apn;
	}

	// Property accessors

	public Long getCommSchemeId() {
		return this.commSchemeId;
	}

	public void setCommSchemeId(Long commSchemeId) {
		this.commSchemeId = commSchemeId;
	}

	public String getCommSchemeName() {
		return this.commSchemeName;
	}

	public void setCommSchemeName(String commSchemeName) {
		this.commSchemeName = commSchemeName;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
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

}