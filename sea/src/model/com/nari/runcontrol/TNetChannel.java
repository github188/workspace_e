package com.nari.runcontrol;


public class TNetChannel {
	private Long netChannelId;
	private String tmnlAssetNo;
	private String ip;
	private Integer port;
	private String protocolCode;
	private Short pri;

	public Long getNetChannelId() {
		return netChannelId;
	}

	public void setNetChannelId(Long netChannelId) {
		this.netChannelId = netChannelId;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public Short getPri() {
		return pri;
	}

	public void setPri(Short pri) {
		this.pri = pri;
	}

	

}