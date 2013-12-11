package com.nari.flowhandle;

import java.util.Date;

/**
 * FEventCloseId entity. @author MyEclipse Persistence Tools
 */

public class FEventCloseId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8917551786690705049L;
	private Long eventId;
	private String orgNo;
	private Date evnetTime;
	private String submitStaffNo;
	private String tmnlAssetNo;
	private String assetNo;
	private Byte eventType;
	private String eventCode;
	private String eventName;
	private String eventData;
	private String closeStaffNo;
	private Date closeDate;
	private String closeRemark;
	private String remark;

	// Constructors

	/** default constructor */
	public FEventCloseId() {
	}

	/** full constructor */
	public FEventCloseId(Long eventId, String orgNo, Date evnetTime,
			String submitStaffNo, String tmnlAssetNo, String assetNo,
			Byte eventType, String eventCode, String eventName,
			String eventData, String closeStaffNo, Date closeDate,
			String closeRemark, String remark) {
		this.eventId = eventId;
		this.orgNo = orgNo;
		this.evnetTime = evnetTime;
		this.submitStaffNo = submitStaffNo;
		this.tmnlAssetNo = tmnlAssetNo;
		this.assetNo = assetNo;
		this.eventType = eventType;
		this.eventCode = eventCode;
		this.eventName = eventName;
		this.eventData = eventData;
		this.closeStaffNo = closeStaffNo;
		this.closeDate = closeDate;
		this.closeRemark = closeRemark;
		this.remark = remark;
	}

	// Property accessors

	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public Date getEvnetTime() {
		return this.evnetTime;
	}

	public void setEvnetTime(Date evnetTime) {
		this.evnetTime = evnetTime;
	}

	public String getSubmitStaffNo() {
		return this.submitStaffNo;
	}

	public void setSubmitStaffNo(String submitStaffNo) {
		this.submitStaffNo = submitStaffNo;
	}

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getAssetNo() {
		return this.assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public Byte getEventType() {
		return this.eventType;
	}

	public void setEventType(Byte eventType) {
		this.eventType = eventType;
	}

	public String getEventCode() {
		return this.eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventData() {
		return this.eventData;
	}

	public void setEventData(String eventData) {
		this.eventData = eventData;
	}

	public String getCloseStaffNo() {
		return this.closeStaffNo;
	}

	public void setCloseStaffNo(String closeStaffNo) {
		this.closeStaffNo = closeStaffNo;
	}

	public Date getCloseDate() {
		return this.closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getCloseRemark() {
		return this.closeRemark;
	}

	public void setCloseRemark(String closeRemark) {
		this.closeRemark = closeRemark;
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
		if (!(other instanceof FEventCloseId))
			return false;
		FEventCloseId castOther = (FEventCloseId) other;

		return ((this.getEventId() == castOther.getEventId()) || (this
				.getEventId() != null
				&& castOther.getEventId() != null && this.getEventId().equals(
				castOther.getEventId())))
				&& ((this.getOrgNo() == castOther.getOrgNo()) || (this
						.getOrgNo() != null
						&& castOther.getOrgNo() != null && this.getOrgNo()
						.equals(castOther.getOrgNo())))
				&& ((this.getEvnetTime() == castOther.getEvnetTime()) || (this
						.getEvnetTime() != null
						&& castOther.getEvnetTime() != null && this
						.getEvnetTime().equals(castOther.getEvnetTime())))
				&& ((this.getSubmitStaffNo() == castOther.getSubmitStaffNo()) || (this
						.getSubmitStaffNo() != null
						&& castOther.getSubmitStaffNo() != null && this
						.getSubmitStaffNo()
						.equals(castOther.getSubmitStaffNo())))
				&& ((this.getTmnlAssetNo() == castOther.getTmnlAssetNo()) || (this
						.getTmnlAssetNo() != null
						&& castOther.getTmnlAssetNo() != null && this
						.getTmnlAssetNo().equals(castOther.getTmnlAssetNo())))
				&& ((this.getAssetNo() == castOther.getAssetNo()) || (this
						.getAssetNo() != null
						&& castOther.getAssetNo() != null && this.getAssetNo()
						.equals(castOther.getAssetNo())))
				&& ((this.getEventType() == castOther.getEventType()) || (this
						.getEventType() != null
						&& castOther.getEventType() != null && this
						.getEventType().equals(castOther.getEventType())))
				&& ((this.getEventCode() == castOther.getEventCode()) || (this
						.getEventCode() != null
						&& castOther.getEventCode() != null && this
						.getEventCode().equals(castOther.getEventCode())))
				&& ((this.getEventName() == castOther.getEventName()) || (this
						.getEventName() != null
						&& castOther.getEventName() != null && this
						.getEventName().equals(castOther.getEventName())))
				&& ((this.getEventData() == castOther.getEventData()) || (this
						.getEventData() != null
						&& castOther.getEventData() != null && this
						.getEventData().equals(castOther.getEventData())))
				&& ((this.getCloseStaffNo() == castOther.getCloseStaffNo()) || (this
						.getCloseStaffNo() != null
						&& castOther.getCloseStaffNo() != null && this
						.getCloseStaffNo().equals(castOther.getCloseStaffNo())))
				&& ((this.getCloseDate() == castOther.getCloseDate()) || (this
						.getCloseDate() != null
						&& castOther.getCloseDate() != null && this
						.getCloseDate().equals(castOther.getCloseDate())))
				&& ((this.getCloseRemark() == castOther.getCloseRemark()) || (this
						.getCloseRemark() != null
						&& castOther.getCloseRemark() != null && this
						.getCloseRemark().equals(castOther.getCloseRemark())))
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
				+ (getOrgNo() == null ? 0 : this.getOrgNo().hashCode());
		result = 37 * result
				+ (getEvnetTime() == null ? 0 : this.getEvnetTime().hashCode());
		result = 37
				* result
				+ (getSubmitStaffNo() == null ? 0 : this.getSubmitStaffNo()
						.hashCode());
		result = 37
				* result
				+ (getTmnlAssetNo() == null ? 0 : this.getTmnlAssetNo()
						.hashCode());
		result = 37 * result
				+ (getAssetNo() == null ? 0 : this.getAssetNo().hashCode());
		result = 37 * result
				+ (getEventType() == null ? 0 : this.getEventType().hashCode());
		result = 37 * result
				+ (getEventCode() == null ? 0 : this.getEventCode().hashCode());
		result = 37 * result
				+ (getEventName() == null ? 0 : this.getEventName().hashCode());
		result = 37 * result
				+ (getEventData() == null ? 0 : this.getEventData().hashCode());
		result = 37
				* result
				+ (getCloseStaffNo() == null ? 0 : this.getCloseStaffNo()
						.hashCode());
		result = 37 * result
				+ (getCloseDate() == null ? 0 : this.getCloseDate().hashCode());
		result = 37
				* result
				+ (getCloseRemark() == null ? 0 : this.getCloseRemark()
						.hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
		return result;
	}

}