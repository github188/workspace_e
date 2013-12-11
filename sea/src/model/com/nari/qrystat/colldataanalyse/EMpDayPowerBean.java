package com.nari.qrystat.colldataanalyse;

import java.sql.Timestamp;

public class EMpDayPowerBean implements java.io.Serializable {

	private String tmnlAssetNo;
	private String dataDate;
	private Double ct;
	private Double pt;
	private Double totalMaxP;
	private String totalMaxPTime;
	private Double totalMaxPa;
	private String totalMaxPaTime;
	private Double totalMaxPb;
	private String totalMaxPbTime;
	private Double totalMaxPc;
	private String totalMaxPcTime;
	private Integer totalPZero;
	private Integer totalPaZero;
	private Integer totalPbZero;
	private Integer totalPcZero;
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

	public Double getCt() {
		return ct;
	}

	public void setCt(Double ct) {
		this.ct = ct;
	}

	public Double getPt() {
		return pt;
	}

	public void setPt(Double pt) {
		this.pt = pt;
	}

	public Double getTotalMaxP() {
		return totalMaxP;
	}

	public void setTotalMaxP(Double totalMaxP) {
		this.totalMaxP = totalMaxP;
	}

	public String getTotalMaxPTime() {
		return totalMaxPTime;
	}

	public void setTotalMaxPTime(String totalMaxPTime) {
		this.totalMaxPTime = totalMaxPTime;
	}

	public Double getTotalMaxPa() {
		return totalMaxPa;
	}

	public void setTotalMaxPa(Double totalMaxPa) {
		this.totalMaxPa = totalMaxPa;
	}

	public String getTotalMaxPaTime() {
		return totalMaxPaTime;
	}

	public void setTotalMaxPaTime(String totalMaxPaTime) {
		this.totalMaxPaTime = totalMaxPaTime;
	}

	public Double getTotalMaxPb() {
		return totalMaxPb;
	}

	public void setTotalMaxPb(Double totalMaxPb) {
		this.totalMaxPb = totalMaxPb;
	}

	public String getTotalMaxPbTime() {
		return totalMaxPbTime;
	}

	public void setTotalMaxPbTime(String totalMaxPbTime) {
		this.totalMaxPbTime = totalMaxPbTime;
	}

	public Double getTotalMaxPc() {
		return totalMaxPc;
	}

	public void setTotalMaxPc(Double totalMaxPc) {
		this.totalMaxPc = totalMaxPc;
	}

	public String getTotalMaxPcTime() {
		return totalMaxPcTime;
	}

	public void setTotalMaxPcTime(String totalMaxPcTime) {
		this.totalMaxPcTime = totalMaxPcTime;
	}

	public Integer getTotalPZero() {
		return totalPZero;
	}

	public void setTotalPZero(Integer totalPZero) {
		this.totalPZero = totalPZero;
	}

	public Integer getTotalPaZero() {
		return totalPaZero;
	}

	public void setTotalPaZero(Integer totalPaZero) {
		this.totalPaZero = totalPaZero;
	}

	public Integer getTotalPbZero() {
		return totalPbZero;
	}

	public void setTotalPbZero(Integer totalPbZero) {
		this.totalPbZero = totalPbZero;
	}

	public Integer getTotalPcZero() {
		return totalPcZero;
	}

	public void setTotalPcZero(Integer totalPcZero) {
		this.totalPcZero = totalPcZero;
	}
}