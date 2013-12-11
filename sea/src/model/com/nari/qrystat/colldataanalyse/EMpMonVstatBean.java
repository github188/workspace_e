package com.nari.qrystat.colldataanalyse;

import java.sql.Timestamp;

public class EMpMonVstatBean implements java.io.Serializable {
	
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
		return this.pt;
	}

	public void setPt(Double pt) {
		this.pt = pt;
	}

	public Integer getUaUupTime() {
		return this.uaUupTime;
	}

	public void setUaUupTime(Integer uaUupTime) {
		this.uaUupTime = uaUupTime;
	}

	public Integer getUaLlwTime() {
		return this.uaLlwTime;
	}

	public void setUaLlwTime(Integer uaLlwTime) {
		this.uaLlwTime = uaLlwTime;
	}

	public Integer getUaUpTime() {
		return this.uaUpTime;
	}

	public void setUaUpTime(Integer uaUpTime) {
		this.uaUpTime = uaUpTime;
	}

	public Integer getUaLowTime() {
		return this.uaLowTime;
	}

	public void setUaLowTime(Integer uaLowTime) {
		this.uaLowTime = uaLowTime;
	}

	public Integer getUaNmlTime() {
		return this.uaNmlTime;
	}

	public void setUaNmlTime(Integer uaNmlTime) {
		this.uaNmlTime = uaNmlTime;
	}

	public Integer getUbUupTime() {
		return this.ubUupTime;
	}

	public void setUbUupTime(Integer ubUupTime) {
		this.ubUupTime = ubUupTime;
	}

	public Integer getUbLlwTime() {
		return this.ubLlwTime;
	}

	public void setUbLlwTime(Integer ubLlwTime) {
		this.ubLlwTime = ubLlwTime;
	}

	public Integer getUbUpTime() {
		return this.ubUpTime;
	}

	public void setUbUpTime(Integer ubUpTime) {
		this.ubUpTime = ubUpTime;
	}

	public Integer getUbLowTime() {
		return this.ubLowTime;
	}

	public void setUbLowTime(Integer ubLowTime) {
		this.ubLowTime = ubLowTime;
	}

	public Integer getUbNmlTime() {
		return this.ubNmlTime;
	}

	public void setUbNmlTime(Integer ubNmlTime) {
		this.ubNmlTime = ubNmlTime;
	}

	public Integer getUcUupTime() {
		return this.ucUupTime;
	}

	public void setUcUupTime(Integer ucUupTime) {
		this.ucUupTime = ucUupTime;
	}

	public Integer getUcLlwTime() {
		return this.ucLlwTime;
	}

	public void setUcLlwTime(Integer ucLlwTime) {
		this.ucLlwTime = ucLlwTime;
	}

	public Integer getUcUpTime() {
		return this.ucUpTime;
	}

	public void setUcUpTime(Integer ucUpTime) {
		this.ucUpTime = ucUpTime;
	}

	public Integer getUcLowTime() {
		return this.ucLowTime;
	}

	public void setUcLowTime(Integer ucLowTime) {
		this.ucLowTime = ucLowTime;
	}

	public Integer getUcNmlTime() {
		return this.ucNmlTime;
	}

	public void setUcNmlTime(Integer ucNmlTime) {
		this.ucNmlTime = ucNmlTime;
	}

	public Double getUaMax() {
		return this.uaMax;
	}

	public void setUaMax(Double uaMax) {
		this.uaMax = uaMax;
	}

	public String getUaMaxTime() {
		return this.uaMaxTime;
	}

	public void setUaMaxTime(String uaMaxTime) {
		this.uaMaxTime = uaMaxTime;
	}

	public Double getUaMin() {
		return this.uaMin;
	}

	public void setUaMin(Double uaMin) {
		this.uaMin = uaMin;
	}

	public String getUaMinTime() {
		return this.uaMinTime;
	}

	public void setUaMinTime(String uaMinTime) {
		this.uaMinTime = uaMinTime;
	}

	public Double getUbMax() {
		return this.ubMax;
	}

	public void setUbMax(Double ubMax) {
		this.ubMax = ubMax;
	}

	public String getUbMaxTime() {
		return this.ubMaxTime;
	}

	public void setUbMaxTime(String ubMaxTime) {
		this.ubMaxTime = ubMaxTime;
	}

	public Double getUbMin() {
		return this.ubMin;
	}

	public void setUbMin(Double ubMin) {
		this.ubMin = ubMin;
	}

	public String getUbMinTime() {
		return this.ubMinTime;
	}

	public void setUbMinTime(String ubMinTime) {
		this.ubMinTime = ubMinTime;
	}

	public Double getUcMax() {
		return this.ucMax;
	}

	public void setUcMax(Double ucMax) {
		this.ucMax = ucMax;
	}

	public String getUcMaxTime() {
		return this.ucMaxTime;
	}

	public void setUcMaxTime(String ucMaxTime) {
		this.ucMaxTime = ucMaxTime;
	}

	public Double getUcMin() {
		return this.ucMin;
	}

	public void setUcMin(Double ucMin) {
		this.ucMin = ucMin;
	}

	public String getUcMinTime() {
		return this.ucMinTime;
	}

	public void setUcMinTime(String ucMinTime) {
		this.ucMinTime = ucMinTime;
	}

	public Double getUaAverage() {
		return this.uaAverage;
	}

	public void setUaAverage(Double uaAverage) {
		this.uaAverage = uaAverage;
	}

	public Double getUbAverage() {
		return this.ubAverage;
	}

	public void setUbAverage(Double ubAverage) {
		this.ubAverage = ubAverage;
	}

	public Double getUcAverage() {
		return this.ucAverage;
	}

	public void setUcAverage(Double ucAverage) {
		this.ucAverage = ucAverage;
	}
}