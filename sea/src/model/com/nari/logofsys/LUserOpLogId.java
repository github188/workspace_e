package com.nari.logofsys;

import java.util.Date;

/**
 * LUserOpLogId entity. @author MyEclipse Persistence Tools
 */

public class LUserOpLogId implements java.io.Serializable {

	// Fields

	private String empNo;
	private String orgNo;
	private String opType;
	private Date opTime;
	private String opModule;
	private String opButton;
	private String opContent;
	private String ipAddr;

	// Constructors

	/** default constructor */
	public LUserOpLogId() {
	}

	/** minimal constructor */
	public LUserOpLogId(String empNo, String orgNo) {
		this.empNo = empNo;
		this.orgNo = orgNo;
	}

	/** full constructor */
	public LUserOpLogId(String empNo, String orgNo, String opType, Date opTime,
			String opModule, String opButton, String opContent, String ipAddr) {
		this.empNo = empNo;
		this.orgNo = orgNo;
		this.opType = opType;
		this.opTime = opTime;
		this.opModule = opModule;
		this.opButton = opButton;
		this.opContent = opContent;
		this.ipAddr = ipAddr;
	}

	// Property accessors

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOpType() {
		return this.opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
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

	public String getOpContent() {
		return this.opContent;
	}

	public void setOpContent(String opContent) {
		this.opContent = opContent;
	}

	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LUserOpLogId))
			return false;
		LUserOpLogId castOther = (LUserOpLogId) other;

		return ((this.getEmpNo() == castOther.getEmpNo()) || (this.getEmpNo() != null
				&& castOther.getEmpNo() != null && this.getEmpNo().equals(
				castOther.getEmpNo())))
				&& ((this.getOrgNo() == castOther.getOrgNo()) || (this
						.getOrgNo() != null
						&& castOther.getOrgNo() != null && this.getOrgNo()
						.equals(castOther.getOrgNo())))
				&& ((this.getOpType() == castOther.getOpType()) || (this
						.getOpType() != null
						&& castOther.getOpType() != null && this.getOpType()
						.equals(castOther.getOpType())))
				&& ((this.getOpTime() == castOther.getOpTime()) || (this
						.getOpTime() != null
						&& castOther.getOpTime() != null && this.getOpTime()
						.equals(castOther.getOpTime())))
				&& ((this.getOpModule() == castOther.getOpModule()) || (this
						.getOpModule() != null
						&& castOther.getOpModule() != null && this
						.getOpModule().equals(castOther.getOpModule())))
				&& ((this.getOpButton() == castOther.getOpButton()) || (this
						.getOpButton() != null
						&& castOther.getOpButton() != null && this
						.getOpButton().equals(castOther.getOpButton())))
				&& ((this.getOpContent() == castOther.getOpContent()) || (this
						.getOpContent() != null
						&& castOther.getOpContent() != null && this
						.getOpContent().equals(castOther.getOpContent())))
				&& ((this.getIpAddr() == castOther.getIpAddr()) || (this
						.getIpAddr() != null
						&& castOther.getIpAddr() != null && this.getIpAddr()
						.equals(castOther.getIpAddr())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEmpNo() == null ? 0 : this.getEmpNo().hashCode());
		result = 37 * result
				+ (getOrgNo() == null ? 0 : this.getOrgNo().hashCode());
		result = 37 * result
				+ (getOpType() == null ? 0 : this.getOpType().hashCode());
		result = 37 * result
				+ (getOpTime() == null ? 0 : this.getOpTime().hashCode());
		result = 37 * result
				+ (getOpModule() == null ? 0 : this.getOpModule().hashCode());
		result = 37 * result
				+ (getOpButton() == null ? 0 : this.getOpButton().hashCode());
		result = 37 * result
				+ (getOpContent() == null ? 0 : this.getOpContent().hashCode());
		result = 37 * result
				+ (getIpAddr() == null ? 0 : this.getIpAddr().hashCode());
		return result;
	}

}