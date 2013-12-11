package com.nari.runcontrol;

/**
 * RCp entity. @author MyEclipse Persistence Tools
 */

public class RCp implements java.io.Serializable {

	// Fields

	private String cpNo;
	private String name;
	private String cpTypeCode;
	private String statusCode;
	private String cpAddr;
	private String gpsLongitude;
	private String gpsLatitude;

	// Constructors

	/** default constructor */
	public RCp() {
	}

	/** minimal constructor */
	public RCp(String name, String cpTypeCode) {
		this.name = name;
		this.cpTypeCode = cpTypeCode;
	}

	/** full constructor */
	public RCp(String name, String cpTypeCode,
			String statusCode, String cpAddr, String gpsLongitude,
			String gpsLatitude) {
		this.name = name;
		this.cpTypeCode = cpTypeCode;
		this.statusCode = statusCode;
		this.cpAddr = cpAddr;
		this.gpsLongitude = gpsLongitude;
		this.gpsLatitude = gpsLatitude;
	}

	// Property accessors

	public String getCpNo() {
		return this.cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpTypeCode() {
		return this.cpTypeCode;
	}

	public void setCpTypeCode(String cpTypeCode) {
		this.cpTypeCode = cpTypeCode;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getCpAddr() {
		return this.cpAddr;
	}

	public void setCpAddr(String cpAddr) {
		this.cpAddr = cpAddr;
	}

	public String getGpsLongitude() {
		return this.gpsLongitude;
	}

	public void setGpsLongitude(String gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}

	public String getGpsLatitude() {
		return this.gpsLatitude;
	}

	public void setGpsLatitude(String gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}

}