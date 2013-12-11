package com.nari.device;

import java.util.Date;

/**
 * DIt entity. @author MyEclipse Persistence Tools
 */

public class DIt implements java.io.Serializable {

	// Fields

	private Long itId;
	private String prOrg;
	private String belongDept;
	private Long contractId;
	private Long rcvId;
	private String lotNo;
	private String barCode;
	private String assetNo;
	private String madeNo;
	private String sortCode;
	private String typeCode;
	private String modelCode;
	private String measPrincipleCode;
	private String insulationMode;
	private String windingConfigCode;
	private String manufacturer;
	private Date madeDate;
	private String rvCode;
	private String ratedFreqCode;
	private Double pf;
	private Double cap;
	private String rcRatioCode;
	private String sndRvCode;
	private String fstRcCode;
	private Double taRatedSndLoad;
	private Double taLightLoad;
	private String taPreCode;
	private Integer taTurns;
	private String voltRatioCode;
	private Double tvRatedSndLoad;
	private Double tvLightLoad;
	private String tvPreCode;
	private Integer tvTurns;
	private Double eqipPrc;
	private String docCreatorNo;
	private Date docCreateDate;
	private Date chkDate;
	private Integer rotateCycle;
	private String discardReason;
	private Date descardDate;
	private String prCode;
	private String newFlag;
	private String erpBatchNo;
	private String curStatusCode;
	private Long whId;
	private Long whAreaId;
	private Long storeAreaId;
	private Long storeLocId;
	private String boxBarCode;
	private String wiringMode;

	// Constructors

	/** default constructor */
	public DIt() {
	}

	/** minimal constructor */
	public DIt(String belongDept) {
		this.belongDept = belongDept;
	}

	/** full constructor */
	public DIt(String prOrg, String belongDept, Long contractId, Long rcvId,
			String lotNo, String barCode, String assetNo, String madeNo,
			String sortCode, String typeCode, String modelCode,
			String measPrincipleCode, String insulationMode,
			String windingConfigCode, String manufacturer, Date madeDate,
			String rvCode, String ratedFreqCode, Double pf, Double cap,
			String rcRatioCode, String sndRvCode, String fstRcCode,
			Double taRatedSndLoad, Double taLightLoad, String taPreCode,
			Integer taTurns, String voltRatioCode, Double tvRatedSndLoad,
			Double tvLightLoad, String tvPreCode, Integer tvTurns,
			Double eqipPrc, String docCreatorNo, Date docCreateDate,
			Date chkDate, Integer rotateCycle, String discardReason,
			Date descardDate, String prCode, String newFlag, String erpBatchNo,
			String curStatusCode, Long whId, Long whAreaId, Long storeAreaId,
			Long storeLocId, String boxBarCode, String wiringMode) {
		this.prOrg = prOrg;
		this.belongDept = belongDept;
		this.contractId = contractId;
		this.rcvId = rcvId;
		this.lotNo = lotNo;
		this.barCode = barCode;
		this.assetNo = assetNo;
		this.madeNo = madeNo;
		this.sortCode = sortCode;
		this.typeCode = typeCode;
		this.modelCode = modelCode;
		this.measPrincipleCode = measPrincipleCode;
		this.insulationMode = insulationMode;
		this.windingConfigCode = windingConfigCode;
		this.manufacturer = manufacturer;
		this.madeDate = madeDate;
		this.rvCode = rvCode;
		this.ratedFreqCode = ratedFreqCode;
		this.pf = pf;
		this.cap = cap;
		this.rcRatioCode = rcRatioCode;
		this.sndRvCode = sndRvCode;
		this.fstRcCode = fstRcCode;
		this.taRatedSndLoad = taRatedSndLoad;
		this.taLightLoad = taLightLoad;
		this.taPreCode = taPreCode;
		this.taTurns = taTurns;
		this.voltRatioCode = voltRatioCode;
		this.tvRatedSndLoad = tvRatedSndLoad;
		this.tvLightLoad = tvLightLoad;
		this.tvPreCode = tvPreCode;
		this.tvTurns = tvTurns;
		this.eqipPrc = eqipPrc;
		this.docCreatorNo = docCreatorNo;
		this.docCreateDate = docCreateDate;
		this.chkDate = chkDate;
		this.rotateCycle = rotateCycle;
		this.discardReason = discardReason;
		this.descardDate = descardDate;
		this.prCode = prCode;
		this.newFlag = newFlag;
		this.erpBatchNo = erpBatchNo;
		this.curStatusCode = curStatusCode;
		this.whId = whId;
		this.whAreaId = whAreaId;
		this.storeAreaId = storeAreaId;
		this.storeLocId = storeLocId;
		this.boxBarCode = boxBarCode;
		this.wiringMode = wiringMode;
	}

	// Property accessors

	public Long getItId() {
		return this.itId;
	}

	public void setItId(Long itId) {
		this.itId = itId;
	}

	public String getPrOrg() {
		return this.prOrg;
	}

	public void setPrOrg(String prOrg) {
		this.prOrg = prOrg;
	}

	public String getBelongDept() {
		return this.belongDept;
	}

	public void setBelongDept(String belongDept) {
		this.belongDept = belongDept;
	}

	public Long getContractId() {
		return this.contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getRcvId() {
		return this.rcvId;
	}

	public void setRcvId(Long rcvId) {
		this.rcvId = rcvId;
	}

	public String getLotNo() {
		return this.lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getBarCode() {
		return this.barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getAssetNo() {
		return this.assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public String getMadeNo() {
		return this.madeNo;
	}

	public void setMadeNo(String madeNo) {
		this.madeNo = madeNo;
	}

	public String getSortCode() {
		return this.sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getModelCode() {
		return this.modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getMeasPrincipleCode() {
		return this.measPrincipleCode;
	}

	public void setMeasPrincipleCode(String measPrincipleCode) {
		this.measPrincipleCode = measPrincipleCode;
	}

	public String getInsulationMode() {
		return this.insulationMode;
	}

	public void setInsulationMode(String insulationMode) {
		this.insulationMode = insulationMode;
	}

	public String getWindingConfigCode() {
		return this.windingConfigCode;
	}

	public void setWindingConfigCode(String windingConfigCode) {
		this.windingConfigCode = windingConfigCode;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Date getMadeDate() {
		return this.madeDate;
	}

	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}

	public String getRvCode() {
		return this.rvCode;
	}

	public void setRvCode(String rvCode) {
		this.rvCode = rvCode;
	}

	public String getRatedFreqCode() {
		return this.ratedFreqCode;
	}

	public void setRatedFreqCode(String ratedFreqCode) {
		this.ratedFreqCode = ratedFreqCode;
	}

	public Double getPf() {
		return this.pf;
	}

	public void setPf(Double pf) {
		this.pf = pf;
	}

	public Double getCap() {
		return this.cap;
	}

	public void setCap(Double cap) {
		this.cap = cap;
	}

	public String getRcRatioCode() {
		return this.rcRatioCode;
	}

	public void setRcRatioCode(String rcRatioCode) {
		this.rcRatioCode = rcRatioCode;
	}

	public String getSndRvCode() {
		return this.sndRvCode;
	}

	public void setSndRvCode(String sndRvCode) {
		this.sndRvCode = sndRvCode;
	}

	public String getFstRcCode() {
		return this.fstRcCode;
	}

	public void setFstRcCode(String fstRcCode) {
		this.fstRcCode = fstRcCode;
	}

	public Double getTaRatedSndLoad() {
		return this.taRatedSndLoad;
	}

	public void setTaRatedSndLoad(Double taRatedSndLoad) {
		this.taRatedSndLoad = taRatedSndLoad;
	}

	public Double getTaLightLoad() {
		return this.taLightLoad;
	}

	public void setTaLightLoad(Double taLightLoad) {
		this.taLightLoad = taLightLoad;
	}

	public String getTaPreCode() {
		return this.taPreCode;
	}

	public void setTaPreCode(String taPreCode) {
		this.taPreCode = taPreCode;
	}

	public Integer getTaTurns() {
		return this.taTurns;
	}

	public void setTaTurns(Integer taTurns) {
		this.taTurns = taTurns;
	}

	public String getVoltRatioCode() {
		return this.voltRatioCode;
	}

	public void setVoltRatioCode(String voltRatioCode) {
		this.voltRatioCode = voltRatioCode;
	}

	public Double getTvRatedSndLoad() {
		return this.tvRatedSndLoad;
	}

	public void setTvRatedSndLoad(Double tvRatedSndLoad) {
		this.tvRatedSndLoad = tvRatedSndLoad;
	}

	public Double getTvLightLoad() {
		return this.tvLightLoad;
	}

	public void setTvLightLoad(Double tvLightLoad) {
		this.tvLightLoad = tvLightLoad;
	}

	public String getTvPreCode() {
		return this.tvPreCode;
	}

	public void setTvPreCode(String tvPreCode) {
		this.tvPreCode = tvPreCode;
	}

	public Integer getTvTurns() {
		return this.tvTurns;
	}

	public void setTvTurns(Integer tvTurns) {
		this.tvTurns = tvTurns;
	}

	public Double getEqipPrc() {
		return this.eqipPrc;
	}

	public void setEqipPrc(Double eqipPrc) {
		this.eqipPrc = eqipPrc;
	}

	public String getDocCreatorNo() {
		return this.docCreatorNo;
	}

	public void setDocCreatorNo(String docCreatorNo) {
		this.docCreatorNo = docCreatorNo;
	}

	public Date getDocCreateDate() {
		return this.docCreateDate;
	}

	public void setDocCreateDate(Date docCreateDate) {
		this.docCreateDate = docCreateDate;
	}

	public Date getChkDate() {
		return this.chkDate;
	}

	public void setChkDate(Date chkDate) {
		this.chkDate = chkDate;
	}

	public Integer getRotateCycle() {
		return this.rotateCycle;
	}

	public void setRotateCycle(Integer rotateCycle) {
		this.rotateCycle = rotateCycle;
	}

	public String getDiscardReason() {
		return this.discardReason;
	}

	public void setDiscardReason(String discardReason) {
		this.discardReason = discardReason;
	}

	public Date getDescardDate() {
		return this.descardDate;
	}

	public void setDescardDate(Date descardDate) {
		this.descardDate = descardDate;
	}

	public String getPrCode() {
		return this.prCode;
	}

	public void setPrCode(String prCode) {
		this.prCode = prCode;
	}

	public String getNewFlag() {
		return this.newFlag;
	}

	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}

	public String getErpBatchNo() {
		return this.erpBatchNo;
	}

	public void setErpBatchNo(String erpBatchNo) {
		this.erpBatchNo = erpBatchNo;
	}

	public String getCurStatusCode() {
		return this.curStatusCode;
	}

	public void setCurStatusCode(String curStatusCode) {
		this.curStatusCode = curStatusCode;
	}

	public Long getWhId() {
		return this.whId;
	}

	public void setWhId(Long whId) {
		this.whId = whId;
	}

	public Long getWhAreaId() {
		return this.whAreaId;
	}

	public void setWhAreaId(Long whAreaId) {
		this.whAreaId = whAreaId;
	}

	public Long getStoreAreaId() {
		return this.storeAreaId;
	}

	public void setStoreAreaId(Long storeAreaId) {
		this.storeAreaId = storeAreaId;
	}

	public Long getStoreLocId() {
		return this.storeLocId;
	}

	public void setStoreLocId(Long storeLocId) {
		this.storeLocId = storeLocId;
	}

	public String getBoxBarCode() {
		return this.boxBarCode;
	}

	public void setBoxBarCode(String boxBarCode) {
		this.boxBarCode = boxBarCode;
	}

	public String getWiringMode() {
		return this.wiringMode;
	}

	public void setWiringMode(String wiringMode) {
		this.wiringMode = wiringMode;
	}

}