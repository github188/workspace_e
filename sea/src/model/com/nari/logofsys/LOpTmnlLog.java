package com.nari.logofsys;

import java.util.Date;

public class LOpTmnlLog implements java.io.Serializable {

	// Fields

	private Long logId;
	private String orgNo;
	private String empNo;
	private String ipAddr;
	private String opModule;
	private String opButton;
	private String tmnlAssetNo;
	private String protItemNo;
	private String curValue;
	private String orgValue;
	private String blockSn;
	private Integer innerBlockSn;
	private String curStatus;
	private String orgStatus;
	private Date opTime;
	private String protocolNo;
	private Short opType;
	private Short opObj;
	private Long opObjNo;
	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getOpModule() {
		return this.opModule;
	}

	public void setOpModule(String opModule) {
		this.opModule = opModule;
	}

	public String getOpButton() {
		return this.opButton;
	}

	public void setOpButton(String opButton) {
		this.opButton = opButton;
	}

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getProtItemNo() {
		return this.protItemNo;
	}

	public void setProtItemNo(String protItemNo) {
		this.protItemNo = protItemNo;
	}

	public String getCurValue() {
		return this.curValue;
	}

	public void setCurValue(String curValue) {
		this.curValue = curValue;
	}

	public String getOrgValue() {
		return this.orgValue;
	}

	public void setOrgValue(String orgValue) {
		this.orgValue = orgValue;
	}

	public String getBlockSn() {
		return blockSn;
	}

	public void setBlockSn(String blockSn) {
		this.blockSn = blockSn;
	}

	public Integer getInnerBlockSn() {
		return innerBlockSn;
	}

	public void setInnerBlockSn(Integer innerBlockSn) {
		this.innerBlockSn = innerBlockSn;
	}

	public String getCurStatus() {
		return this.curStatus;
	}

	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	public String getOrgStatus() {
		return this.orgStatus;
	}

	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
	}

	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public String getProtocolNo() {
		return protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	public Short getOpType() {
		return opType;
	}

	public void setOpType(Short opType) {
		this.opType = opType;
	}

	public Short getOpObj() {
		return opObj;
	}

	public void setOpObj(Short opObj) {
		this.opObj = opObj;
	}

	public Long getOpObjNo() {
		return opObjNo;
	}

	public void setOpObjNo(Long opObjNo) {
		this.opObjNo = opObjNo;
	}

	
}