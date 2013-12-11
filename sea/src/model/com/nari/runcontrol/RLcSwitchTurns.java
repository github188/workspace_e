package com.nari.runcontrol;

/**
 * RLcSwitchTurns entity. @author MyEclipse Persistence Tools
 */

public class RLcSwitchTurns implements java.io.Serializable {

	// Fields

	private Long turnsId;
	private String cpNo;
	private Integer rotateNo;
	private String swithType;
	private String switchConnFlag;
	private Double percentage;

	// Constructors

	/** default constructor */
	public RLcSwitchTurns() {
	}

	/** minimal constructor */
	public RLcSwitchTurns(String cpNo, Integer rotateNo, String switchConnFlag) {
		this.cpNo = cpNo;
		this.rotateNo = rotateNo;
		this.switchConnFlag = switchConnFlag;
	}

	/** full constructor */
	public RLcSwitchTurns(String cpNo, Integer rotateNo, String swithType,
			String switchConnFlag, Double percentage) {
		this.cpNo = cpNo;
		this.rotateNo = rotateNo;
		this.swithType = swithType;
		this.switchConnFlag = switchConnFlag;
		this.percentage = percentage;
	}

	// Property accessors

	public Long getTurnsId() {
		return this.turnsId;
	}

	public void setTurnsId(Long turnsId) {
		this.turnsId = turnsId;
	}

	public String getCpNo() {
		return this.cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

	public Integer getRotateNo() {
		return this.rotateNo;
	}

	public void setRotateNo(Integer rotateNo) {
		this.rotateNo = rotateNo;
	}

	public String getSwithType() {
		return this.swithType;
	}

	public void setSwithType(String swithType) {
		this.swithType = swithType;
	}

	public String getSwitchConnFlag() {
		return this.switchConnFlag;
	}

	public void setSwitchConnFlag(String switchConnFlag) {
		this.switchConnFlag = switchConnFlag;
	}

	public Double getPercentage() {
		return this.percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

}