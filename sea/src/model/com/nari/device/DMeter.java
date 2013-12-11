package com.nari.device;

import java.util.Date;

/**
 * DMeter entity. @author MyEclipse Persistence Tools
 */

public class DMeter implements java.io.Serializable {

	// Fields

	private Long meterId;
	private String assetNo;
	private String areaCode;
	private String prOrg;
	private String belongDept;
	private Long contractId;
	private Long rcvId;
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
	private String apPreLevelCode;
	private String rpPreLevelCode;
	private Double meterDigits;
	private Double tsDigits;
	private String constCode;
	private String rpConstant;
	private String manufacturer;
	private Date madeDate;
	private Double eqipPrc;
	private Integer selfFactor;
	private String bothWayCalc;
	private String prepayFlag;
	private String multirateFalg;
	private String demandMeterFlag;
	private String harmonicMeasFalg;
	private String ccPreventFlag;
	private String pulseConstantCode;
	private String pulseAmplitudeCode;
	private String pulseSortCode;
	private String freqCode;
	private String conMode;
	private String readingTypeCode;
	private String meterUsage;
	private String measTheory;
	private String bearingStruc;
	private String ci;
	private String relayJoint;
	private String elecMeasDispFlag;
	private String vlFlag;
	private String clFlag;
	private String antiPhaseFlag;
	private String superPowerFlag;
	private String loadCurveFlag;
	private String poweroffMrFlag;
	private String infraredFlag;
	private String docTypeCode;
	private Date latestChkDate;
	private Date instDate;
	private Date rmvDate;
	private Integer rotateCycle;
	private String discardReason;
	private Date descardDate;
	private String prCode;
	private String handoverDept;
	private Date handoverDate;
	private String curStatusCode;
	private String borrowFlag;
	private String newFlag;
	private String erpBatchNo;
	private String remark;
	private Long whId;
	private Long whAreaId;
	private Long storeAreaId;
	private Long storeLocId;
	private String boxBarCode;
	private String docCreatorNo;
	private Date docCreateDate;
	private String baudrateCode;
	private String meterCloseMode;
	private String registerMode;
	private String dispMode;
	private String hardVer;
	private String softVer;
	private Integer rs485RouteQty;
	private String commProtCode;
	private String commMode;
	private String attachequipTypeCode;

	// Constructors

	/** default constructor */
	public DMeter() {
	}

	/** minimal constructor */
	public DMeter(String belongDept) {
		this.belongDept = belongDept;
	}

	/** full constructor */
	public DMeter(String assetNo, String areaCode, String prOrg,
			String belongDept, Long contractId, Long rcvId, String barCode,
			String lotNo, String madeNo, String sortCode, String typeCode,
			String modelCode, String wiringMode, String voltCode,
			String ratedCurrent, String overloadFactor, String apPreLevelCode,
			String rpPreLevelCode, Double meterDigits, Double tsDigits,
			String constCode, String rpConstant, String manufacturer,
			Date madeDate, Double eqipPrc, Integer selfFactor,
			String bothWayCalc, String prepayFlag, String multirateFalg,
			String demandMeterFlag, String harmonicMeasFalg,
			String ccPreventFlag, String pulseConstantCode,
			String pulseAmplitudeCode, String pulseSortCode, String freqCode,
			String conMode, String readingTypeCode, String meterUsage,
			String measTheory, String bearingStruc, String ci,
			String relayJoint, String elecMeasDispFlag, String vlFlag,
			String clFlag, String antiPhaseFlag, String superPowerFlag,
			String loadCurveFlag, String poweroffMrFlag, String infraredFlag,
			String docTypeCode, Date latestChkDate, Date instDate,
			Date rmvDate, Integer rotateCycle, String discardReason,
			Date descardDate, String prCode, String handoverDept,
			Date handoverDate, String curStatusCode, String borrowFlag,
			String newFlag, String erpBatchNo, String remark, Long whId,
			Long whAreaId, Long storeAreaId, Long storeLocId,
			String boxBarCode, String docCreatorNo, Date docCreateDate,
			String baudrateCode, String meterCloseMode, String registerMode,
			String dispMode, String hardVer, String softVer,
			Integer rs485RouteQty, String commProtCode, String commMode,
			String attachequipTypeCode) {
		this.assetNo = assetNo;
		this.areaCode = areaCode;
		this.prOrg = prOrg;
		this.belongDept = belongDept;
		this.contractId = contractId;
		this.rcvId = rcvId;
		this.barCode = barCode;
		this.lotNo = lotNo;
		this.madeNo = madeNo;
		this.sortCode = sortCode;
		this.typeCode = typeCode;
		this.modelCode = modelCode;
		this.wiringMode = wiringMode;
		this.voltCode = voltCode;
		this.ratedCurrent = ratedCurrent;
		this.overloadFactor = overloadFactor;
		this.apPreLevelCode = apPreLevelCode;
		this.rpPreLevelCode = rpPreLevelCode;
		this.meterDigits = meterDigits;
		this.tsDigits = tsDigits;
		this.constCode = constCode;
		this.rpConstant = rpConstant;
		this.manufacturer = manufacturer;
		this.madeDate = madeDate;
		this.eqipPrc = eqipPrc;
		this.selfFactor = selfFactor;
		this.bothWayCalc = bothWayCalc;
		this.prepayFlag = prepayFlag;
		this.multirateFalg = multirateFalg;
		this.demandMeterFlag = demandMeterFlag;
		this.harmonicMeasFalg = harmonicMeasFalg;
		this.ccPreventFlag = ccPreventFlag;
		this.pulseConstantCode = pulseConstantCode;
		this.pulseAmplitudeCode = pulseAmplitudeCode;
		this.pulseSortCode = pulseSortCode;
		this.freqCode = freqCode;
		this.conMode = conMode;
		this.readingTypeCode = readingTypeCode;
		this.meterUsage = meterUsage;
		this.measTheory = measTheory;
		this.bearingStruc = bearingStruc;
		this.ci = ci;
		this.relayJoint = relayJoint;
		this.elecMeasDispFlag = elecMeasDispFlag;
		this.vlFlag = vlFlag;
		this.clFlag = clFlag;
		this.antiPhaseFlag = antiPhaseFlag;
		this.superPowerFlag = superPowerFlag;
		this.loadCurveFlag = loadCurveFlag;
		this.poweroffMrFlag = poweroffMrFlag;
		this.infraredFlag = infraredFlag;
		this.docTypeCode = docTypeCode;
		this.latestChkDate = latestChkDate;
		this.instDate = instDate;
		this.rmvDate = rmvDate;
		this.rotateCycle = rotateCycle;
		this.discardReason = discardReason;
		this.descardDate = descardDate;
		this.prCode = prCode;
		this.handoverDept = handoverDept;
		this.handoverDate = handoverDate;
		this.curStatusCode = curStatusCode;
		this.borrowFlag = borrowFlag;
		this.newFlag = newFlag;
		this.erpBatchNo = erpBatchNo;
		this.remark = remark;
		this.whId = whId;
		this.whAreaId = whAreaId;
		this.storeAreaId = storeAreaId;
		this.storeLocId = storeLocId;
		this.boxBarCode = boxBarCode;
		this.docCreatorNo = docCreatorNo;
		this.docCreateDate = docCreateDate;
		this.baudrateCode = baudrateCode;
		this.meterCloseMode = meterCloseMode;
		this.registerMode = registerMode;
		this.dispMode = dispMode;
		this.hardVer = hardVer;
		this.softVer = softVer;
		this.rs485RouteQty = rs485RouteQty;
		this.commProtCode = commProtCode;
		this.commMode = commMode;
		this.attachequipTypeCode = attachequipTypeCode;
	}

	// Property accessors

	public Long getMeterId() {
		return this.meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public String getAssetNo() {
		return this.assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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

	public String getBarCode() {
		return this.barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getLotNo() {
		return this.lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
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

	public String getWiringMode() {
		return this.wiringMode;
	}

	public void setWiringMode(String wiringMode) {
		this.wiringMode = wiringMode;
	}

	public String getVoltCode() {
		return this.voltCode;
	}

	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}

	public String getRatedCurrent() {
		return this.ratedCurrent;
	}

	public void setRatedCurrent(String ratedCurrent) {
		this.ratedCurrent = ratedCurrent;
	}

	public String getOverloadFactor() {
		return this.overloadFactor;
	}

	public void setOverloadFactor(String overloadFactor) {
		this.overloadFactor = overloadFactor;
	}

	public String getApPreLevelCode() {
		return this.apPreLevelCode;
	}

	public void setApPreLevelCode(String apPreLevelCode) {
		this.apPreLevelCode = apPreLevelCode;
	}

	public String getRpPreLevelCode() {
		return this.rpPreLevelCode;
	}

	public void setRpPreLevelCode(String rpPreLevelCode) {
		this.rpPreLevelCode = rpPreLevelCode;
	}

	public Double getMeterDigits() {
		return this.meterDigits;
	}

	public void setMeterDigits(Double meterDigits) {
		this.meterDigits = meterDigits;
	}

	public Double getTsDigits() {
		return this.tsDigits;
	}

	public void setTsDigits(Double tsDigits) {
		this.tsDigits = tsDigits;
	}

	public String getConstCode() {
		return this.constCode;
	}

	public void setConstCode(String constCode) {
		this.constCode = constCode;
	}

	public String getRpConstant() {
		return this.rpConstant;
	}

	public void setRpConstant(String rpConstant) {
		this.rpConstant = rpConstant;
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

	public Double getEqipPrc() {
		return this.eqipPrc;
	}

	public void setEqipPrc(Double eqipPrc) {
		this.eqipPrc = eqipPrc;
	}

	public Integer getSelfFactor() {
		return this.selfFactor;
	}

	public void setSelfFactor(Integer selfFactor) {
		this.selfFactor = selfFactor;
	}

	public String getBothWayCalc() {
		return this.bothWayCalc;
	}

	public void setBothWayCalc(String bothWayCalc) {
		this.bothWayCalc = bothWayCalc;
	}

	public String getPrepayFlag() {
		return this.prepayFlag;
	}

	public void setPrepayFlag(String prepayFlag) {
		this.prepayFlag = prepayFlag;
	}

	public String getMultirateFalg() {
		return this.multirateFalg;
	}

	public void setMultirateFalg(String multirateFalg) {
		this.multirateFalg = multirateFalg;
	}

	public String getDemandMeterFlag() {
		return this.demandMeterFlag;
	}

	public void setDemandMeterFlag(String demandMeterFlag) {
		this.demandMeterFlag = demandMeterFlag;
	}

	public String getHarmonicMeasFalg() {
		return this.harmonicMeasFalg;
	}

	public void setHarmonicMeasFalg(String harmonicMeasFalg) {
		this.harmonicMeasFalg = harmonicMeasFalg;
	}

	public String getCcPreventFlag() {
		return this.ccPreventFlag;
	}

	public void setCcPreventFlag(String ccPreventFlag) {
		this.ccPreventFlag = ccPreventFlag;
	}

	public String getPulseConstantCode() {
		return this.pulseConstantCode;
	}

	public void setPulseConstantCode(String pulseConstantCode) {
		this.pulseConstantCode = pulseConstantCode;
	}

	public String getPulseAmplitudeCode() {
		return this.pulseAmplitudeCode;
	}

	public void setPulseAmplitudeCode(String pulseAmplitudeCode) {
		this.pulseAmplitudeCode = pulseAmplitudeCode;
	}

	public String getPulseSortCode() {
		return this.pulseSortCode;
	}

	public void setPulseSortCode(String pulseSortCode) {
		this.pulseSortCode = pulseSortCode;
	}

	public String getFreqCode() {
		return this.freqCode;
	}

	public void setFreqCode(String freqCode) {
		this.freqCode = freqCode;
	}

	public String getConMode() {
		return this.conMode;
	}

	public void setConMode(String conMode) {
		this.conMode = conMode;
	}

	public String getReadingTypeCode() {
		return this.readingTypeCode;
	}

	public void setReadingTypeCode(String readingTypeCode) {
		this.readingTypeCode = readingTypeCode;
	}

	public String getMeterUsage() {
		return this.meterUsage;
	}

	public void setMeterUsage(String meterUsage) {
		this.meterUsage = meterUsage;
	}

	public String getMeasTheory() {
		return this.measTheory;
	}

	public void setMeasTheory(String measTheory) {
		this.measTheory = measTheory;
	}

	public String getBearingStruc() {
		return this.bearingStruc;
	}

	public void setBearingStruc(String bearingStruc) {
		this.bearingStruc = bearingStruc;
	}

	public String getCi() {
		return this.ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getRelayJoint() {
		return this.relayJoint;
	}

	public void setRelayJoint(String relayJoint) {
		this.relayJoint = relayJoint;
	}

	public String getElecMeasDispFlag() {
		return this.elecMeasDispFlag;
	}

	public void setElecMeasDispFlag(String elecMeasDispFlag) {
		this.elecMeasDispFlag = elecMeasDispFlag;
	}

	public String getVlFlag() {
		return this.vlFlag;
	}

	public void setVlFlag(String vlFlag) {
		this.vlFlag = vlFlag;
	}

	public String getClFlag() {
		return this.clFlag;
	}

	public void setClFlag(String clFlag) {
		this.clFlag = clFlag;
	}

	public String getAntiPhaseFlag() {
		return this.antiPhaseFlag;
	}

	public void setAntiPhaseFlag(String antiPhaseFlag) {
		this.antiPhaseFlag = antiPhaseFlag;
	}

	public String getSuperPowerFlag() {
		return this.superPowerFlag;
	}

	public void setSuperPowerFlag(String superPowerFlag) {
		this.superPowerFlag = superPowerFlag;
	}

	public String getLoadCurveFlag() {
		return this.loadCurveFlag;
	}

	public void setLoadCurveFlag(String loadCurveFlag) {
		this.loadCurveFlag = loadCurveFlag;
	}

	public String getPoweroffMrFlag() {
		return this.poweroffMrFlag;
	}

	public void setPoweroffMrFlag(String poweroffMrFlag) {
		this.poweroffMrFlag = poweroffMrFlag;
	}

	public String getInfraredFlag() {
		return this.infraredFlag;
	}

	public void setInfraredFlag(String infraredFlag) {
		this.infraredFlag = infraredFlag;
	}

	public String getDocTypeCode() {
		return this.docTypeCode;
	}

	public void setDocTypeCode(String docTypeCode) {
		this.docTypeCode = docTypeCode;
	}

	public Date getLatestChkDate() {
		return this.latestChkDate;
	}

	public void setLatestChkDate(Date latestChkDate) {
		this.latestChkDate = latestChkDate;
	}

	public Date getInstDate() {
		return this.instDate;
	}

	public void setInstDate(Date instDate) {
		this.instDate = instDate;
	}

	public Date getRmvDate() {
		return this.rmvDate;
	}

	public void setRmvDate(Date rmvDate) {
		this.rmvDate = rmvDate;
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

	public String getHandoverDept() {
		return this.handoverDept;
	}

	public void setHandoverDept(String handoverDept) {
		this.handoverDept = handoverDept;
	}

	public Date getHandoverDate() {
		return this.handoverDate;
	}

	public void setHandoverDate(Date handoverDate) {
		this.handoverDate = handoverDate;
	}

	public String getCurStatusCode() {
		return this.curStatusCode;
	}

	public void setCurStatusCode(String curStatusCode) {
		this.curStatusCode = curStatusCode;
	}

	public String getBorrowFlag() {
		return this.borrowFlag;
	}

	public void setBorrowFlag(String borrowFlag) {
		this.borrowFlag = borrowFlag;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getBaudrateCode() {
		return this.baudrateCode;
	}

	public void setBaudrateCode(String baudrateCode) {
		this.baudrateCode = baudrateCode;
	}

	public String getMeterCloseMode() {
		return this.meterCloseMode;
	}

	public void setMeterCloseMode(String meterCloseMode) {
		this.meterCloseMode = meterCloseMode;
	}

	public String getRegisterMode() {
		return this.registerMode;
	}

	public void setRegisterMode(String registerMode) {
		this.registerMode = registerMode;
	}

	public String getDispMode() {
		return this.dispMode;
	}

	public void setDispMode(String dispMode) {
		this.dispMode = dispMode;
	}

	public String getHardVer() {
		return this.hardVer;
	}

	public void setHardVer(String hardVer) {
		this.hardVer = hardVer;
	}

	public String getSoftVer() {
		return this.softVer;
	}

	public void setSoftVer(String softVer) {
		this.softVer = softVer;
	}

	public Integer getRs485RouteQty() {
		return this.rs485RouteQty;
	}

	public void setRs485RouteQty(Integer rs485RouteQty) {
		this.rs485RouteQty = rs485RouteQty;
	}

	public String getCommProtCode() {
		return this.commProtCode;
	}

	public void setCommProtCode(String commProtCode) {
		this.commProtCode = commProtCode;
	}

	public String getCommMode() {
		return this.commMode;
	}

	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

	public String getAttachequipTypeCode() {
		return this.attachequipTypeCode;
	}

	public void setAttachequipTypeCode(String attachequipTypeCode) {
		this.attachequipTypeCode = attachequipTypeCode;
	}

}