package com.nari.measurepoint;

import java.util.Date;

/**
 * CMp entity. @author MyEclipse Persistence Tools
 */

public class CMp implements java.io.Serializable {

	// Fields

	private Long mpId;
	private Long mpSectId;
	private String mpNo;
	private String mpName;
	private String orgNo;
	private Long consId;
	private String consNo;
	private Long lineId;
	private Long tgId;
	private String mrSectNo;
	private String mpAddr;
	private String typeCode;
	private String mpAttrCode;
	private String usageTypeCode;
	private String sideCode;
	private String voltCode;
	private Date appDate;
	private Date runDate;
	private String wiringMode;
	private String measMode;
	private String switchNo;
	private String exchgTypeCode;
	private String mdTypeCode;
	private Integer mrSn;
	private Integer mpSn;
	private String meterFlag;
	private String statusCode;
	private String lcFlag;
	private String earthMode;

	// Constructors

	/** default constructor */
	public CMp() {
	}

	/** minimal constructor */
	public CMp(String mpNo) {
		this.mpNo = mpNo;
	}

	/** full constructor */
	public CMp(Long mpSectId, String mpNo, String mpName, String orgNo,
			Long consId, String consNo, Long lineId, Long tgId,
			String mrSectNo, String mpAddr, String typeCode, String mpAttrCode,
			String usageTypeCode, String sideCode, String voltCode,
			Date appDate, Date runDate, String wiringMode, String measMode,
			String switchNo, String exchgTypeCode, String mdTypeCode,
			Integer mrSn, Integer mpSn, String meterFlag, String statusCode,
			String lcFlag, String earthMode) {
		this.mpSectId = mpSectId;
		this.mpNo = mpNo;
		this.mpName = mpName;
		this.orgNo = orgNo;
		this.consId = consId;
		this.consNo = consNo;
		this.lineId = lineId;
		this.tgId = tgId;
		this.mrSectNo = mrSectNo;
		this.mpAddr = mpAddr;
		this.typeCode = typeCode;
		this.mpAttrCode = mpAttrCode;
		this.usageTypeCode = usageTypeCode;
		this.sideCode = sideCode;
		this.voltCode = voltCode;
		this.appDate = appDate;
		this.runDate = runDate;
		this.wiringMode = wiringMode;
		this.measMode = measMode;
		this.switchNo = switchNo;
		this.exchgTypeCode = exchgTypeCode;
		this.mdTypeCode = mdTypeCode;
		this.mrSn = mrSn;
		this.mpSn = mpSn;
		this.meterFlag = meterFlag;
		this.statusCode = statusCode;
		this.lcFlag = lcFlag;
		this.earthMode = earthMode;
	}

	// Property accessors

	public Long getMpId() {
		return this.mpId;
	}

	public void setMpId(Long mpId) {
		this.mpId = mpId;
	}

	public Long getMpSectId() {
		return this.mpSectId;
	}

	public void setMpSectId(Long mpSectId) {
		this.mpSectId = mpSectId;
	}

	public String getMpNo() {
		return this.mpNo;
	}

	public void setMpNo(String mpNo) {
		this.mpNo = mpNo;
	}

	public String getMpName() {
		return this.mpName;
	}

	public void setMpName(String mpName) {
		this.mpName = mpName;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public Long getConsId() {
		return this.consId;
	}

	public void setConsId(Long consId) {
		this.consId = consId;
	}

	public String getConsNo() {
		return this.consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public Long getLineId() {
		return this.lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public Long getTgId() {
		return this.tgId;
	}

	public void setTgId(Long tgId) {
		this.tgId = tgId;
	}

	public String getMrSectNo() {
		return this.mrSectNo;
	}

	public void setMrSectNo(String mrSectNo) {
		this.mrSectNo = mrSectNo;
	}

	public String getMpAddr() {
		return this.mpAddr;
	}

	public void setMpAddr(String mpAddr) {
		this.mpAddr = mpAddr;
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getMpAttrCode() {
		return this.mpAttrCode;
	}

	public void setMpAttrCode(String mpAttrCode) {
		this.mpAttrCode = mpAttrCode;
	}

	public String getUsageTypeCode() {
		return this.usageTypeCode;
	}

	public void setUsageTypeCode(String usageTypeCode) {
		this.usageTypeCode = usageTypeCode;
	}

	public String getSideCode() {
		return this.sideCode;
	}

	public void setSideCode(String sideCode) {
		this.sideCode = sideCode;
	}

	public String getVoltCode() {
		return this.voltCode;
	}

	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}

	public Date getAppDate() {
		return this.appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public Date getRunDate() {
		return this.runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	public String getWiringMode() {
		return this.wiringMode;
	}

	public void setWiringMode(String wiringMode) {
		this.wiringMode = wiringMode;
	}

	public String getMeasMode() {
		return this.measMode;
	}

	public void setMeasMode(String measMode) {
		this.measMode = measMode;
	}

	public String getSwitchNo() {
		return this.switchNo;
	}

	public void setSwitchNo(String switchNo) {
		this.switchNo = switchNo;
	}

	public String getExchgTypeCode() {
		return this.exchgTypeCode;
	}

	public void setExchgTypeCode(String exchgTypeCode) {
		this.exchgTypeCode = exchgTypeCode;
	}

	public String getMdTypeCode() {
		return this.mdTypeCode;
	}

	public void setMdTypeCode(String mdTypeCode) {
		this.mdTypeCode = mdTypeCode;
	}

	public Integer getMrSn() {
		return this.mrSn;
	}

	public void setMrSn(Integer mrSn) {
		this.mrSn = mrSn;
	}

	public Integer getMpSn() {
		return this.mpSn;
	}

	public void setMpSn(Integer mpSn) {
		this.mpSn = mpSn;
	}

	public String getMeterFlag() {
		return this.meterFlag;
	}

	public void setMeterFlag(String meterFlag) {
		this.meterFlag = meterFlag;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getLcFlag() {
		return this.lcFlag;
	}

	public void setLcFlag(String lcFlag) {
		this.lcFlag = lcFlag;
	}

	public String getEarthMode() {
		return this.earthMode;
	}

	public void setEarthMode(String earthMode) {
		this.earthMode = earthMode;
	}

}