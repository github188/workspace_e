package com.nari.qrystat.querycollpoint;

public class RCP {
	private   String consNo;//用户编号
	private   String name;//采集点名称
	private   String cpTypeCode;//采集点类型
	private   String statusCode;//状态
	private   String   cpStatus;
	private   String cpAddr;//采集点地址
	private   String gpsLongitude;//GPS经度
	private   String gpsLatitude;//GPS纬度
	
	
	public String getCpStatus() {
		return cpStatus;
	}
	public void setCpStatus(String cpStatus) {
		this.cpStatus = cpStatus;
	}
	public String getConsNo() {
		return consNo;
	}
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCpTypeCode() {
		return cpTypeCode;
	}
	public void setCpTypeCode(String cpTypeCode) {
		this.cpTypeCode = cpTypeCode;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getCpAddr() {
		return cpAddr;
	}
	public void setCpAddr(String cpAddr) {
		this.cpAddr = cpAddr;
	}
	public String getGpsLongitude() {
		return gpsLongitude;
	}
	public void setGpsLongitude(String gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}
	public String getGpsLatitude() {
		return gpsLatitude;
	}
	public void setGpsLatitude(String gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}
	
}
