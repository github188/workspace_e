package com.nari.qrystat.colldataanalyse;

import java.sql.Timestamp;

public class EMpDayVstatBean implements java.io.Serializable {

	private String tmnlAssetNo;
	private String dataDate;
	private Double pt;
	private Integer uaUupTime;
	private Integer uaLlwTime;
	private Integer uaUpTime;
	private Integer uaLowTime;
	private Integer uaNmlTime;
	private Integer ubUupTime;
	private Integer ubLlwTime;
	private Integer ubUpTime;
	private Integer ubLowTime;
	private Integer ubNmlTime;
	private Integer ucUupTime;
	private Integer ucLlwTime;
	private Integer ucUpTime;
	private Integer ucLowTime;
	private Integer ucNmlTime;
	private Double uaMax;
	private String uaMaxTime;
	private Double uaMin;
	private String uaMinTime;
	private Double ubMax;
	private String ubMaxTime;
	private Double ubMin;
	private String ubMinTime;
	private Double ucMax;
	private String ucMaxTime;
	private Double ucMin;
	private String ucMinTime;
	private Double uaAverage;
	private Double ubAverage;
	private Double ucAverage;
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

	public Double getPt() {
		return pt;
	}

	public void setPt(Double pt) {
		this.pt = pt;
	}

	public Integer getUaUupTime() {
		return uaUupTime;
	}

	public void setUaUupTime(Integer uaUupTime) {
		this.uaUupTime = uaUupTime;
	}

	public Integer getUaLlwTime() {
		return uaLlwTime;
	}

	public void setUaLlwTime(Integer uaLlwTime) {
		this.uaLlwTime = uaLlwTime;
	}

	public Integer getUaUpTime() {
		return uaUpTime;
	}

	public void setUaUpTime(Integer uaUpTime) {
		this.uaUpTime = uaUpTime;
	}

	public Integer getUaLowTime() {
		return uaLowTime;
	}

	public void setUaLowTime(Integer uaLowTime) {
		this.uaLowTime = uaLowTime;
	}

	public Integer getUaNmlTime() {
		return uaNmlTime;
	}

	public void setUaNmlTime(Integer uaNmlTime) {
		this.uaNmlTime = uaNmlTime;
	}

	public Integer getUbUupTime() {
		return ubUupTime;
	}

	public void setUbUupTime(Integer ubUupTime) {
		this.ubUupTime = ubUupTime;
	}

	public Integer getUbLlwTime() {
		return ubLlwTime;
	}

	public void setUbLlwTime(Integer ubLlwTime) {
		this.ubLlwTime = ubLlwTime;
	}

	public Integer getUbUpTime() {
		return ubUpTime;
	}

	public void setUbUpTime(Integer ubUpTime) {
		this.ubUpTime = ubUpTime;
	}

	public Integer getUbLowTime() {
		return ubLowTime;
	}

	public void setUbLowTime(Integer ubLowTime) {
		this.ubLowTime = ubLowTime;
	}

	public Integer getUbNmlTime() {
		return ubNmlTime;
	}

	public void setUbNmlTime(Integer ubNmlTime) {
		this.ubNmlTime = ubNmlTime;
	}

	public Integer getUcUupTime() {
		return ucUupTime;
	}

	public void setUcUupTime(Integer ucUupTime) {
		this.ucUupTime = ucUupTime;
	}

	public Integer getUcLlwTime() {
		return ucLlwTime;
	}

	public void setUcLlwTime(Integer ucLlwTime) {
		this.ucLlwTime = ucLlwTime;
	}

	public Integer getUcUpTime() {
		return ucUpTime;
	}

	public void setUcUpTime(Integer ucUpTime) {
		this.ucUpTime = ucUpTime;
	}

	public Integer getUcLowTime() {
		return ucLowTime;
	}

	public void setUcLowTime(Integer ucLowTime) {
		this.ucLowTime = ucLowTime;
	}

	public Integer getUcNmlTime() {
		return ucNmlTime;
	}

	public void setUcNmlTime(Integer ucNmlTime) {
		this.ucNmlTime = ucNmlTime;
	}

	public Double getUaMax() {
		return uaMax;
	}

	public void setUaMax(Double uaMax) {
		this.uaMax = uaMax;
	}

	public String getUaMaxTime() {
		return uaMaxTime;
	}

	public void setUaMaxTime(String uaMaxTime) {
		this.uaMaxTime = uaMaxTime;
	}

	public Double getUaMin() {
		return uaMin;
	}

	public void setUaMin(Double uaMin) {
		this.uaMin = uaMin;
	}

	public String getUaMinTime() {
		return uaMinTime;
	}

	public void setUaMinTime(String uaMinTime) {
		this.uaMinTime = uaMinTime;
	}

	public Double getUbMax() {
		return ubMax;
	}

	public void setUbMax(Double ubMax) {
		this.ubMax = ubMax;
	}

	public String getUbMaxTime() {
		return ubMaxTime;
	}

	public void setUbMaxTime(String ubMaxTime) {
		this.ubMaxTime = ubMaxTime;
	}

	public Double getUbMin() {
		return ubMin;
	}

	public void setUbMin(Double ubMin) {
		this.ubMin = ubMin;
	}

	public String getUbMinTime() {
		return ubMinTime;
	}

	public void setUbMinTime(String ubMinTime) {
		this.ubMinTime = ubMinTime;
	}

	public Double getUcMax() {
		return ucMax;
	}

	public void setUcMax(Double ucMax) {
		this.ucMax = ucMax;
	}

	public String getUcMaxTime() {
		return ucMaxTime;
	}

	public void setUcMaxTime(String ucMaxTime) {
		this.ucMaxTime = ucMaxTime;
	}

	public Double getUcMin() {
		return ucMin;
	}

	public void setUcMin(Double ucMin) {
		this.ucMin = ucMin;
	}

	public String getUcMinTime() {
		return ucMinTime;
	}

	public void setUcMinTime(String ucMinTime) {
		this.ucMinTime = ucMinTime;
	}

	public Double getUaAverage() {
		return uaAverage;
	}

	public void setUaAverage(Double uaAverage) {
		this.uaAverage = uaAverage;
	}

	public Double getUbAverage() {
		return ubAverage;
	}

	public void setUbAverage(Double ubAverage) {
		this.ubAverage = ubAverage;
	}

	public Double getUcAverage() {
		return ucAverage;
	}

	public void setUcAverage(Double ucAverage) {
		this.ucAverage = ucAverage;
	}
}