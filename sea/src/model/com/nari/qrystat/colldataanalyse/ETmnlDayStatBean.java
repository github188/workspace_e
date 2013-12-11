package com.nari.qrystat.colldataanalyse;

public class ETmnlDayStatBean implements java.io.Serializable {

	private String tmnlAssetNo;
	private String dataDate;
	private Integer daySuplyTime;
	private Integer dayResetNum;
	private Short dayEcjumpNum;
	private Short dayBcjumpNum;
	private Short dayPcjumpNum;
	private Short dayRcjumpNum;
	private Long dayComm;

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
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

	public Integer getDaySuplyTime() {
		return daySuplyTime;
	}

	public void setDaySuplyTime(Integer daySuplyTime) {
		this.daySuplyTime = daySuplyTime;
	}

	public Integer getDayResetNum() {
		return dayResetNum;
	}

	public void setDayResetNum(Integer dayResetNum) {
		this.dayResetNum = dayResetNum;
	}

	public Short getDayEcjumpNum() {
		return this.dayEcjumpNum;
	}

	public void setDayEcjumpNum(Short dayEcjumpNum) {
		this.dayEcjumpNum = dayEcjumpNum;
	}

	public Short getDayBcjumpNum() {
		return this.dayBcjumpNum;
	}

	public void setDayBcjumpNum(Short dayBcjumpNum) {
		this.dayBcjumpNum = dayBcjumpNum;
	}

	public Short getDayPcjumpNum() {
		return this.dayPcjumpNum;
	}

	public void setDayPcjumpNum(Short dayPcjumpNum) {
		this.dayPcjumpNum = dayPcjumpNum;
	}

	public Short getDayRcjumpNum() {
		return this.dayRcjumpNum;
	}

	public void setDayRcjumpNum(Short dayRcjumpNum) {
		this.dayRcjumpNum = dayRcjumpNum;
	}

	public Long getDayComm() {
		return this.dayComm;
	}

	public void setDayComm(Long dayComm) {
		this.dayComm = dayComm;
	}
}