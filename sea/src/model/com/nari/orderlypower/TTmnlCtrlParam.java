package com.nari.orderlypower;


/**
 * TTmnlCtrlParam entity. @author MyEclipse Persistence Tools
 */

public class TTmnlCtrlParam implements java.io.Serializable {

	// Fields

	private Long ctrlParamId;
	private String orgNo;
	private String consNo;
	private String tmnlAssetNo;
	private Short pn;
	private Byte pnType;
	private Boolean isValid;

	// Constructors

	/** default constructor */
	public TTmnlCtrlParam() {
	}

	/** full constructor */
	public TTmnlCtrlParam(String orgNo, String consNo, String tmnlAssetNo,
			Short pn, Byte pnType, Boolean isValid) {
		this.orgNo = orgNo;
		this.consNo = consNo;
		this.tmnlAssetNo = tmnlAssetNo;
		this.pn = pn;
		this.pnType = pnType;
		this.isValid = isValid;
	}

	// Property accessors

	public Long getCtrlParamId() {
		return this.ctrlParamId;
	}

	public void setCtrlParamId(Long ctrlParamId) {
		this.ctrlParamId = ctrlParamId;
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

	public Short getPn() {
		return this.pn;
	}

	public void setPn(Short pn) {
		this.pn = pn;
	}

	public Byte getPnType() {
		return this.pnType;
	}

	public void setPnType(Byte pnType) {
		this.pnType = pnType;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

}