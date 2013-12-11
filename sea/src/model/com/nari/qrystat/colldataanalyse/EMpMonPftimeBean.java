package com.nari.qrystat.colldataanalyse;

public class EMpMonPftimeBean implements java.io.Serializable {

	private String tmnlAssetNo;
	private String dataDate;
	private Integer sumTime1;
	private Integer sumTime2;
	private Integer sumTime3;
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

	public Integer getSumTime1() {
		return this.sumTime1;
	}

	public void setSumTime1(Integer sumTime1) {
		this.sumTime1 = sumTime1;
	}

	public Integer getSumTime2() {
		return this.sumTime2;
	}

	public void setSumTime2(Integer sumTime2) {
		this.sumTime2 = sumTime2;
	}

	public Integer getSumTime3() {
		return this.sumTime3;
	}

	public void setSumTime3(Integer sumTime3) {
		this.sumTime3 = sumTime3;
	}
}