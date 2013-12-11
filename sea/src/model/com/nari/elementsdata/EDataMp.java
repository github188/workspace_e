package com.nari.elementsdata;

/**
 * EDataMp entity. @author MyEclipse Persistence Tools
 */

public class EDataMp implements java.io.Serializable {

	// Fields

	private Long dataId;
	private String orgNo;
	private String consNo;
	private String tmnlAssetNo;
	private String assetNo;
	private Byte mpType;
	private String dataSrc;
	private String mpSn;
	private Integer ptRatio;
	private Integer ctRatio;
	private Integer selfFactor;
	private Byte calcMode;
	private Integer disableDate;
	private Boolean isValid;

	// Constructors

	/** default constructor */
	public EDataMp() {
	}

	/** minimal constructor */
	public EDataMp(String orgNo) {
		this.orgNo = orgNo;
	}

	/** full constructor */
	public EDataMp(String orgNo, String consNo, String tmnlAssetNo,
			String assetNo, Byte mpType, String dataSrc, String mpSn,
			Integer ptRatio, Integer ctRatio, Integer selfFactor,
			Byte calcMode, Integer disableDate, Boolean isValid) {
		this.orgNo = orgNo;
		this.consNo = consNo;
		this.tmnlAssetNo = tmnlAssetNo;
		this.assetNo = assetNo;
		this.mpType = mpType;
		this.dataSrc = dataSrc;
		this.mpSn = mpSn;
		this.ptRatio = ptRatio;
		this.ctRatio = ctRatio;
		this.selfFactor = selfFactor;
		this.calcMode = calcMode;
		this.disableDate = disableDate;
		this.isValid = isValid;
	}

	// Property accessors

	public Long getDataId() {
		return this.dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getConsNo() {
		return this.consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getAssetNo() {
		return this.assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public Byte getMpType() {
		return this.mpType;
	}

	public void setMpType(Byte mpType) {
		this.mpType = mpType;
	}

	public String getDataSrc() {
		return this.dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public String getMpSn() {
		return this.mpSn;
	}

	public void setMpSn(String mpSn) {
		this.mpSn = mpSn;
	}

	public Integer getPtRatio() {
		return this.ptRatio;
	}

	public void setPtRatio(Integer ptRatio) {
		this.ptRatio = ptRatio;
	}

	public Integer getCtRatio() {
		return this.ctRatio;
	}

	public void setCtRatio(Integer ctRatio) {
		this.ctRatio = ctRatio;
	}

	public Integer getSelfFactor() {
		return this.selfFactor;
	}

	public void setSelfFactor(Integer selfFactor) {
		this.selfFactor = selfFactor;
	}

	public Byte getCalcMode() {
		return this.calcMode;
	}

	public void setCalcMode(Byte calcMode) {
		this.calcMode = calcMode;
	}

	public Integer getDisableDate() {
		return this.disableDate;
	}

	public void setDisableDate(Integer disableDate) {
		this.disableDate = disableDate;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

}