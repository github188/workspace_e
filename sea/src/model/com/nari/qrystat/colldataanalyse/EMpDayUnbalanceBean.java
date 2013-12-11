package com.nari.qrystat.colldataanalyse;

import java.sql.Timestamp;

public class EMpDayUnbalanceBean implements java.io.Serializable {

	private String tmnlAssetNo;
	private String dataDate;
	private Integer IUbTime;
	private Integer UUbTime;
	private Double IUbMaxVal;
	private String IUbMaxTime;
	private Double UUbMaxVal;
	private String UUbMaxTime;
	private String assetNo;
	private String mpNo;
	private String mpName;
	private String tmnlAddr;

	public String getTmnlAddr() {
		return tmnlAddr;
	}

	public void setTmnlAddr(String tmnlAddr) {
		this.tmnlAddr = tmnlAddr;
	}
	public String getMpNo() {
		return mpNo;
	}

	public void setMpNo(String mpNo) {
		this.mpNo = mpNo;
	}

	public String getMpName() {
		return mpName;
	}

	public void setMpName(String mpName) {
		this.mpName = mpName;
	}

	public String getAssetNo() {
		return assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	public String getDataDate() {
		return dataDate;
	}
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	public Integer getIUbTime() {
		return IUbTime;
	}
	public void setIUbTime(Integer ubTime) {
		IUbTime = ubTime;
	}
	public Integer getUUbTime() {
		return UUbTime;
	}
	public void setUUbTime(Integer ubTime) {
		UUbTime = ubTime;
	}
	public Double getIUbMaxVal() {
		return IUbMaxVal;
	}
	public void setIUbMaxVal(Double ubMaxVal) {
		IUbMaxVal = ubMaxVal;
	}
	public String getIUbMaxTime() {
		return IUbMaxTime;
	}
	public void setIUbMaxTime(String ubMaxTime) {
		IUbMaxTime = ubMaxTime;
	}
	public Double getUUbMaxVal() {
		return UUbMaxVal;
	}
	public void setUUbMaxVal(Double ubMaxVal) {
		UUbMaxVal = ubMaxVal;
	}
	public String getUUbMaxTime() {
		return UUbMaxTime;
	}
	public void setUUbMaxTime(String ubMaxTime) {
		UUbMaxTime = ubMaxTime;
	}
}