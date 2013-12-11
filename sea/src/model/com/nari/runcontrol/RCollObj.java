package com.nari.runcontrol;

/**
 * RCollObj entity. @author MyEclipse Persistence Tools
 */

public class RCollObj implements java.io.Serializable {

	// Fields

	private Long collObjId;
	private Long meterId;
	private String cpNo;
	private Integer collPort;
	private Integer ctRatio;
	private Integer ptRatio;
	private Long TFactor;
	private Integer meterConst;
	private Integer portNo;
	private String pulseAttr;

	// Constructors

	/** default constructor */
	public RCollObj() {
	}

	/** minimal constructor */
	public RCollObj(Long meterId, String cpNo, Integer collPort) {
		this.meterId = meterId;
		this.cpNo = cpNo;
		this.collPort = collPort;
	}

	/** full constructor */
	public RCollObj(Long meterId, String cpNo, Integer collPort,
			Integer ctRatio, Integer ptRatio, Long TFactor, Integer meterConst,
			Integer portNo, String pulseAttr) {
		this.meterId = meterId;
		this.cpNo = cpNo;
		this.collPort = collPort;
		this.ctRatio = ctRatio;
		this.ptRatio = ptRatio;
		this.TFactor = TFactor;
		this.meterConst = meterConst;
		this.portNo = portNo;
		this.pulseAttr = pulseAttr;
	}

	// Property accessors

	public Long getCollObjId() {
		return this.collObjId;
	}

	public void setCollObjId(Long collObjId) {
		this.collObjId = collObjId;
	}

	public Long getMeterId() {
		return this.meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public String getCpNo() {
		return this.cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

	public Integer getCollPort() {
		return this.collPort;
	}

	public void setCollPort(Integer collPort) {
		this.collPort = collPort;
	}

	public Integer getCtRatio() {
		return this.ctRatio;
	}

	public void setCtRatio(Integer ctRatio) {
		this.ctRatio = ctRatio;
	}

	public Integer getPtRatio() {
		return this.ptRatio;
	}

	public void setPtRatio(Integer ptRatio) {
		this.ptRatio = ptRatio;
	}

	public Long getTFactor() {
		return this.TFactor;
	}

	public void setTFactor(Long TFactor) {
		this.TFactor = TFactor;
	}

	public Integer getMeterConst() {
		return this.meterConst;
	}

	public void setMeterConst(Integer meterConst) {
		this.meterConst = meterConst;
	}

	public Integer getPortNo() {
		return this.portNo;
	}

	public void setPortNo(Integer portNo) {
		this.portNo = portNo;
	}

	public String getPulseAttr() {
		return this.pulseAttr;
	}

	public void setPulseAttr(String pulseAttr) {
		this.pulseAttr = pulseAttr;
	}

}