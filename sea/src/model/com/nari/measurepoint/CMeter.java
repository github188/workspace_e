package com.nari.measurepoint;

import java.util.Date;

/**
 * CMeter entity. @author MyEclipse Persistence Tools
 */

public class CMeter implements java.io.Serializable {

	// Fields

	private Long meterId;
	private Long collObjId;
	private String assetNo;
	private Long mpId;
	private String orgNo;
	private String consNo;
	private String baudrate;
	private String commNo;
	private String commAddr1;
	private String commAddr2;
	private String commMode;
	private String instLoc;
	private Date instDate;
	private Double TFactor;
	private String refMeterFlag;
	private Long refMeterId;
	private String validateCode;
	private String moduleNo;
	private String mrFactor;
	private Date lastChkDate;
	private Integer rotateCycle;
	private Date rotateValidDate;
	private Integer chkCycle;
	private String tmnlAssetNo;
	private String fmrAssetNo;
	private Boolean regStatus;
	private Integer regSn;

	// Constructors

	/** default constructor */
	public CMeter() {
	}

	/** full constructor */
	public CMeter(Long collObjId, String assetNo, Long mpId, String orgNo,
			String consNo, String baudrate, String commNo, String commAddr1,
			String commAddr2, String commMode, String instLoc, Date instDate,
			Double TFactor, String refMeterFlag, Long refMeterId,
			String validateCode, String moduleNo, String mrFactor,
			Date lastChkDate, Integer rotateCycle, Date rotateValidDate,
			Integer chkCycle, String tmnlAssetNo, String fmrAssetNo,
			Boolean regStatus, Integer regSn) {
		this.collObjId = collObjId;
		this.assetNo = assetNo;
		this.mpId = mpId;
		this.orgNo = orgNo;
		this.consNo = consNo;
		this.baudrate = baudrate;
		this.commNo = commNo;
		this.commAddr1 = commAddr1;
		this.commAddr2 = commAddr2;
		this.commMode = commMode;
		this.instLoc = instLoc;
		this.instDate = instDate;
		this.TFactor = TFactor;
		this.refMeterFlag = refMeterFlag;
		this.refMeterId = refMeterId;
		this.validateCode = validateCode;
		this.moduleNo = moduleNo;
		this.mrFactor = mrFactor;
		this.lastChkDate = lastChkDate;
		this.rotateCycle = rotateCycle;
		this.rotateValidDate = rotateValidDate;
		this.chkCycle = chkCycle;
		this.tmnlAssetNo = tmnlAssetNo;
		this.fmrAssetNo = fmrAssetNo;
		this.regStatus = regStatus;
		this.regSn = regSn;
	}

	// Property accessors

	public Long getMeterId() {
		return this.meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public Long getCollObjId() {
		return this.collObjId;
	}

	public void setCollObjId(Long collObjId) {
		this.collObjId = collObjId;
	}

	public String getAssetNo() {
		return this.assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public Long getMpId() {
		return this.mpId;
	}

	public void setMpId(Long mpId) {
		this.mpId = mpId;
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

	public String getBaudrate() {
		return this.baudrate;
	}

	public void setBaudrate(String baudrate) {
		this.baudrate = baudrate;
	}

	public String getCommNo() {
		return this.commNo;
	}

	public void setCommNo(String commNo) {
		this.commNo = commNo;
	}

	public String getCommAddr1() {
		return this.commAddr1;
	}

	public void setCommAddr1(String commAddr1) {
		this.commAddr1 = commAddr1;
	}

	public String getCommAddr2() {
		return this.commAddr2;
	}

	public void setCommAddr2(String commAddr2) {
		this.commAddr2 = commAddr2;
	}

	public String getCommMode() {
		return this.commMode;
	}

	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

	public String getInstLoc() {
		return this.instLoc;
	}

	public void setInstLoc(String instLoc) {
		this.instLoc = instLoc;
	}

	public Date getInstDate() {
		return this.instDate;
	}

	public void setInstDate(Date instDate) {
		this.instDate = instDate;
	}

	public Double getTFactor() {
		return this.TFactor;
	}

	public void setTFactor(Double TFactor) {
		this.TFactor = TFactor;
	}

	public String getRefMeterFlag() {
		return this.refMeterFlag;
	}

	public void setRefMeterFlag(String refMeterFlag) {
		this.refMeterFlag = refMeterFlag;
	}

	public Long getRefMeterId() {
		return this.refMeterId;
	}

	public void setRefMeterId(Long refMeterId) {
		this.refMeterId = refMeterId;
	}

	public String getValidateCode() {
		return this.validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getModuleNo() {
		return this.moduleNo;
	}

	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

	public String getMrFactor() {
		return this.mrFactor;
	}

	public void setMrFactor(String mrFactor) {
		this.mrFactor = mrFactor;
	}

	public Date getLastChkDate() {
		return this.lastChkDate;
	}

	public void setLastChkDate(Date lastChkDate) {
		this.lastChkDate = lastChkDate;
	}

	public Integer getRotateCycle() {
		return this.rotateCycle;
	}

	public void setRotateCycle(Integer rotateCycle) {
		this.rotateCycle = rotateCycle;
	}

	public Date getRotateValidDate() {
		return this.rotateValidDate;
	}

	public void setRotateValidDate(Date rotateValidDate) {
		this.rotateValidDate = rotateValidDate;
	}

	public Integer getChkCycle() {
		return this.chkCycle;
	}

	public void setChkCycle(Integer chkCycle) {
		this.chkCycle = chkCycle;
	}

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getFmrAssetNo() {
		return this.fmrAssetNo;
	}

	public void setFmrAssetNo(String fmrAssetNo) {
		this.fmrAssetNo = fmrAssetNo;
	}

	public Boolean getRegStatus() {
		return this.regStatus;
	}

	public void setRegStatus(Boolean regStatus) {
		this.regStatus = regStatus;
	}

	public Integer getRegSn() {
		return this.regSn;
	}

	public void setRegSn(Integer regSn) {
		this.regSn = regSn;
	}

}