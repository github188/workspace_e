package com.nari.flowhandle;

import java.util.Date;

/**
 * FEventFlowId entity. @author MyEclipse Persistence Tools
 */

public class FEventFlowId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2107406717010705995L;
	private Long eventId;
	private Byte opType;
	private String staffNo;
	private String opRemark;
	private Date opTime;
	private String flowStatusCode;
	private String recStaffNo;
	private Date expiryDate;
	private String remark;

	// Constructors

	/** default constructor */
	public FEventFlowId() {
	}

	/** full constructor */
	public FEventFlowId(Long eventId, Byte opType, String staffNo,
			String opRemark, Date opTime, String flowStatusCode,
			String recStaffNo, Date expiryDate, String remark) {
		this.eventId = eventId;
		this.opType = opType;
		this.staffNo = staffNo;
		this.opRemark = opRemark;
		this.opTime = opTime;
		this.flowStatusCode = flowStatusCode;
		this.recStaffNo = recStaffNo;
		this.expiryDate = expiryDate;
		this.remark = remark;
	}

	// Property accessors

	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Byte getOpType() {
		return this.opType;
	}

	public void setOpType(Byte opType) {
		this.opType = opType;
	}

	public String getStaffNo() {
		return this.staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getOpRemark() {
		return this.opRemark;
	}

	public void setOpRemark(String opRemark) {
		this.opRemark = opRemark;
	}

	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public String getFlowStatusCode() {
		return this.flowStatusCode;
	}

	public void setFlowStatusCode(String flowStatusCode) {
		this.flowStatusCode = flowStatusCode;
	}

	public String getRecStaffNo() {
		return this.recStaffNo;
	}

	public void setRecStaffNo(String recStaffNo) {
		this.recStaffNo = recStaffNo;
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FEventFlowId))
			return false;
		FEventFlowId castOther = (FEventFlowId) other;

		return ((this.getEventId() == castOther.getEventId()) || (this
				.getEventId() != null
				&& castOther.getEventId() != null && this.getEventId().equals(
				castOther.getEventId())))
				&& ((this.getOpType() == castOther.getOpType()) || (this
						.getOpType() != null
						&& castOther.getOpType() != null && this.getOpType()
						.equals(castOther.getOpType())))
				&& ((this.getStaffNo() == castOther.getStaffNo()) || (this
						.getStaffNo() != null
						&& castOther.getStaffNo() != null && this.getStaffNo()
						.equals(castOther.getStaffNo())))
				&& ((this.getOpRemark() == castOther.getOpRemark()) || (this
						.getOpRemark() != null
						&& castOther.getOpRemark() != null && this
						.getOpRemark().equals(castOther.getOpRemark())))
				&& ((this.getOpTime() == castOther.getOpTime()) || (this
						.getOpTime() != null
						&& castOther.getOpTime() != null && this.getOpTime()
						.equals(castOther.getOpTime())))
				&& ((this.getFlowStatusCode() == castOther.getFlowStatusCode()) || (this
						.getFlowStatusCode() != null
						&& castOther.getFlowStatusCode() != null && this
						.getFlowStatusCode().equals(
								castOther.getFlowStatusCode())))
				&& ((this.getRecStaffNo() == castOther.getRecStaffNo()) || (this
						.getRecStaffNo() != null
						&& castOther.getRecStaffNo() != null && this
						.getRecStaffNo().equals(castOther.getRecStaffNo())))
				&& ((this.getExpiryDate() == castOther.getExpiryDate()) || (this
						.getExpiryDate() != null
						&& castOther.getExpiryDate() != null && this
						.getExpiryDate().equals(castOther.getExpiryDate())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null
						&& castOther.getRemark() != null && this.getRemark()
						.equals(castOther.getRemark())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEventId() == null ? 0 : this.getEventId().hashCode());
		result = 37 * result
				+ (getOpType() == null ? 0 : this.getOpType().hashCode());
		result = 37 * result
				+ (getStaffNo() == null ? 0 : this.getStaffNo().hashCode());
		result = 37 * result
				+ (getOpRemark() == null ? 0 : this.getOpRemark().hashCode());
		result = 37 * result
				+ (getOpTime() == null ? 0 : this.getOpTime().hashCode());
		result = 37
				* result
				+ (getFlowStatusCode() == null ? 0 : this.getFlowStatusCode()
						.hashCode());
		result = 37
				* result
				+ (getRecStaffNo() == null ? 0 : this.getRecStaffNo()
						.hashCode());
		result = 37
				* result
				+ (getExpiryDate() == null ? 0 : this.getExpiryDate()
						.hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
		return result;
	}

}