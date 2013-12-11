package com.nari.qrystat.colldataanalyse;

import java.sql.Timestamp;

public class SameTradeCCons implements java.io.Serializable {

	private Long rowindex;
	private String orgName;
	private String consNo;
	private String consName;
	private String elecAddr;
	private Double runCap;
	private String tradeName;
	private String volt;
	private String elecType;
	private String tmnlAssetNo;
	private String assetNo;
	private Double papE;
	private Double maxP;
	private String maxPTime;
	private Double minP;
	private String minPTime;
	private String consId;
	public Long getRowindex() {
		return rowindex;
	}
	public void setRowindex(Long rowindex) {
		this.rowindex = rowindex;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getConsNo() {
		return consNo;
	}
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	public String getConsName() {
		return consName;
	}
	public void setConsName(String consName) {
		this.consName = consName;
	}
	public String getElecAddr() {
		return elecAddr;
	}
	public void setElecAddr(String elecAddr) {
		this.elecAddr = elecAddr;
	}
	public Double getRunCap() {
		return runCap;
	}
	public void setRunCap(Double runCap) {
		this.runCap = runCap;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getVolt() {
		return volt;
	}
	public void setVolt(String volt) {
		this.volt = volt;
	}
	public String getElecType() {
		return elecType;
	}
	public void setElecType(String elecType) {
		this.elecType = elecType;
	}
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public Double getPapE() {
		return papE;
	}
	public void setPapE(Double papE) {
		this.papE = papE;
	}
	public Double getMaxP() {
		return maxP;
	}
	public void setMaxP(Double maxP) {
		this.maxP = maxP;
	}
	public String getMaxPTime() {
		return maxPTime;
	}
	public void setMaxPTime(String maxPTime) {
		this.maxPTime = maxPTime;
	}
	public Double getMinP() {
		return minP;
	}
	public void setMinP(Double minP) {
		this.minP = minP;
	}
	public String getMinPTime() {
		return minPTime;
	}
	public void setMinPTime(String minPTime) {
		this.minPTime = minPTime;
	}
	public String getConsId() {
		return consId;
	}
	public void setConsId(String consId) {
		this.consId = consId;
	}
}