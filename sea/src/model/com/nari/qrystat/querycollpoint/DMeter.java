package com.nari.qrystat.querycollpoint;

import java.sql.Timestamp;

public class DMeter {
	private String assetNo;
	private String barCode;
	private String lotNo;
	private String madeNo;
	private String sortCode;
	private String typeCode;
	private String modelCode;
	private String wiringMode;
	private String voltCode;
	private String ratedCurrent;
	private String overloadFactor;
	private String constCode;
	private String manufacturer;
	private String madeDate;
	private String meterUsage;//使用用途
	private String freqCode;
	private String pulseConstantCode;
	private String measTheory;
	private String ci;
	private String consNo;
	private String consName;
	private String mpNo;
	private String mpName;
	private long ct;
	private long pt;
	private long tFactor;
	private float meterDigit;
	private int selfFactor;
	private String bothWayCal;
	private String repayFlag;
	private String multiRateFlag;
	
	
	

	public String getMadeDate() {
		return madeDate;
	}
	public void setMadeDate(String madeDate) {
		this.madeDate = madeDate;
	}
	public long getCt() {
		return ct;
	}
	public void setCt(long ct) {
		this.ct = ct;
	}
	public long getPt() {
		return pt;
	}
	public void setPt(long pt) {
		this.pt = pt;
	}
	public long gettFactor() {
		return tFactor;
	}
	public void settFactor(long tFactor) {
		this.tFactor = tFactor;
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
	//当前状态。。。。。。。
	/**
	 * @return the assetNo
	 */
	public String getAssetNo() {
		return assetNo;
	}
	/**
	 * @param assetNo the assetNo to set
	 */
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	/**
	 * @return the barCode
	 */
	public String getBarCode() {
		return barCode;
	}
	/**
	 * @param barCode the barCode to set
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	/**
	 * @return the lotNo
	 */
	public String getLotNo() {
		return lotNo;
	}
	/**
	 * @param lotNo the lotNo to set
	 */
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	/**
	 * @return the madeNo
	 */
	public String getMadeNo() {
		return madeNo;
	}
	/**
	 * @param madeNo the madeNo to set
	 */
	public void setMadeNo(String madeNo) {
		this.madeNo = madeNo;
	}
	/**
	 * @return the sortCode
	 */
	public String getSortCode() {
		return sortCode;
	}
	/**
	 * @param sortCode the sortCode to set
	 */
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}
	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}
	/**
	 * @param typeCode the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	/**
	 * @return the modelCode
	 */
	public String getModelCode() {
		return modelCode;
	}
	/**
	 * @param modelCode the modelCode to set
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	/**
	 * @return the wiringMode
	 */
	public String getWiringMode() {
		return wiringMode;
	}
	/**
	 * @param wiringMode the wiringMode to set
	 */
	public void setWiringMode(String wiringMode) {
		this.wiringMode = wiringMode;
	}
	/**
	 * @return the voltCode
	 */
	public String getVoltCode() {
		return voltCode;
	}
	/**
	 * @param voltCode the voltCode to set
	 */
	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}
	/**
	 * @return the ratedCurrent
	 */
	public String getRatedCurrent() {
		return ratedCurrent;
	}
	/**
	 * @param ratedCurrent the ratedCurrent to set
	 */
	public void setRatedCurrent(String ratedCurrent) {
		this.ratedCurrent = ratedCurrent;
	}
	/**
	 * @return the overloadFactor
	 */
	public String getOverloadFactor() {
		return overloadFactor;
	}
	/**
	 * @param overloadFactor the overloadFactor to set
	 */
	public void setOverloadFactor(String overloadFactor) {
		this.overloadFactor = overloadFactor;
	}
	/**
	 * @return the constCode
	 */
	public String getConstCode() {
		return constCode;
	}
	/**
	 * @param constCode the constCode to set
	 */
	public void setConstCode(String constCode) {
		this.constCode = constCode;
	}
	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the meterUsage
	 */
	public String getMeterUsage() {
		return meterUsage;
	}
	/**
	 * @param meterUsage the meterUsage to set
	 */
	public void setMeterUsage(String meterUsage) {
		this.meterUsage = meterUsage;
	}
	/**
	 * @return the freqCode
	 */
	public String getFreqCode() {
		return freqCode;
	}
	/**
	 * @param freqCode the freqCode to set
	 */
	public void setFreqCode(String freqCode) {
		this.freqCode = freqCode;
	}
	/**
	 * @return the pulseConstantCode
	 */
	public String getPulseConstantCode() {
		return pulseConstantCode;
	}
	/**
	 * @param pulseConstantCode the pulseConstantCode to set
	 */
	public void setPulseConstantCode(String pulseConstantCode) {
		this.pulseConstantCode = pulseConstantCode;
	}
	/**
	 * @return the measTheory
	 */
	public String getMeasTheory() {
		return measTheory;
	}
	/**
	 * @param measTheory the measTheory to set
	 */
	public void setMeasTheory(String measTheory) {
		this.measTheory = measTheory;
	}
	/**
	 * @return the ci
	 */
	public String getCi() {
		return ci;
	}
	/**
	 * @param ci the ci to set
	 */
	public void setCi(String ci) {
		this.ci = ci;
	}
	public float getMeterDigit() {
		return meterDigit;
	}
	public void setMeterDigit(float meterDigit) {
		this.meterDigit = meterDigit;
	}
	public int getSelfFactor() {
		return selfFactor;
	}
	public void setSelfFactor(int selfFactor) {
		this.selfFactor = selfFactor;
	}
	public String getBothWayCal() {
		return bothWayCal;
	}
	public void setBothWayCal(String bothWayCal) {
		this.bothWayCal = bothWayCal;
	}
	public String getRepayFlag() {
		return repayFlag;
	}
	public void setRepayFlag(String repayFlag) {
		this.repayFlag = repayFlag;
	}
	public String getMultiRateFlag() {
		return multiRateFlag;
	}
	public void setMultiRateFlag(String multiRateFlag) {
		this.multiRateFlag = multiRateFlag;
	}
	
	
	

}
