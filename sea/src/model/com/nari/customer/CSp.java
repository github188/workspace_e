package com.nari.customer;

/**
 * CSp entity. @author MyEclipse Persistence Tools
 */

public class CSp implements java.io.Serializable {

	// Fields

	private Long spId;
	private Long consId;
	private String spName;
	private String typeCode;
	private String interlockMode;
	private String equipLoc;
	private String psSwitchMode;
	private String spRemark;
	private String psNumCode;
	private String lockMode;
	private Double sparePowerCap;
	private String sparePowerFlag;

	// Constructors

	/** default constructor */
	public CSp() {
	}

	/** full constructor */
	public CSp(Long consId, String spName, String typeCode,
			String interlockMode, String equipLoc, String psSwitchMode,
			String spRemark, String psNumCode, String lockMode,
			Double sparePowerCap, String sparePowerFlag) {
		this.consId = consId;
		this.spName = spName;
		this.typeCode = typeCode;
		this.interlockMode = interlockMode;
		this.equipLoc = equipLoc;
		this.psSwitchMode = psSwitchMode;
		this.spRemark = spRemark;
		this.psNumCode = psNumCode;
		this.lockMode = lockMode;
		this.sparePowerCap = sparePowerCap;
		this.sparePowerFlag = sparePowerFlag;
	}

	// Property accessors

	public Long getSpId() {
		return this.spId;
	}

	public void setSpId(Long spId) {
		this.spId = spId;
	}

	public Long getConsId() {
		return this.consId;
	}

	public void setConsId(Long consId) {
		this.consId = consId;
	}

	public String getSpName() {
		return this.spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getInterlockMode() {
		return this.interlockMode;
	}

	public void setInterlockMode(String interlockMode) {
		this.interlockMode = interlockMode;
	}

	public String getEquipLoc() {
		return this.equipLoc;
	}

	public void setEquipLoc(String equipLoc) {
		this.equipLoc = equipLoc;
	}

	public String getPsSwitchMode() {
		return this.psSwitchMode;
	}

	public void setPsSwitchMode(String psSwitchMode) {
		this.psSwitchMode = psSwitchMode;
	}

	public String getSpRemark() {
		return this.spRemark;
	}

	public void setSpRemark(String spRemark) {
		this.spRemark = spRemark;
	}

	public String getPsNumCode() {
		return this.psNumCode;
	}

	public void setPsNumCode(String psNumCode) {
		this.psNumCode = psNumCode;
	}

	public String getLockMode() {
		return this.lockMode;
	}

	public void setLockMode(String lockMode) {
		this.lockMode = lockMode;
	}

	public Double getSparePowerCap() {
		return this.sparePowerCap;
	}

	public void setSparePowerCap(Double sparePowerCap) {
		this.sparePowerCap = sparePowerCap;
	}

	public String getSparePowerFlag() {
		return this.sparePowerFlag;
	}

	public void setSparePowerFlag(String sparePowerFlag) {
		this.sparePowerFlag = sparePowerFlag;
	}

}