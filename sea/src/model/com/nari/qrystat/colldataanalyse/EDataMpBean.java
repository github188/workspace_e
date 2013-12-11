package com.nari.qrystat.colldataanalyse;

public class EDataMpBean implements java.io.Serializable {

	private Long dataId;
	private String tmnlAddr;
	private String meterNo;
	private String dataSrc;
	private int freezeCycleNum;//冻结天数 
	private String mpNo;//计量点编号 
	private String mpName;//计量点名称

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public String getTmnlAddr() {
		return tmnlAddr;
	}

	public void setTmnlAddr(String tmnlAddr) {
		this.tmnlAddr = tmnlAddr;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public int getFreezeCycleNum() {
		return freezeCycleNum;
	}

	public void setFreezeCycleNum(int freezeCycleNum) {
		this.freezeCycleNum = freezeCycleNum;
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
}