package com.nari.statreport;

import java.util.Date;

/**
 * AEventClassStatDId entity. @author MyEclipse Persistence Tools
 */

public class AEventClassStatDId implements java.io.Serializable {

	// Fields

	private String orgNo;
	private Date statDate;
	private Byte eventType;
	private Byte eventLevel;
	private String factoryCode;
	private Integer eventCnt;

	// Constructors

	/** default constructor */
	public AEventClassStatDId() {
	}

	/** minimal constructor */
	public AEventClassStatDId(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	/** full constructor */
	public AEventClassStatDId(String orgNo, Date statDate, Byte eventType,
			Byte eventLevel, String factoryCode, Integer eventCnt) {
		this.orgNo = orgNo;
		this.statDate = statDate;
		this.eventType = eventType;
		this.eventLevel = eventLevel;
		this.factoryCode = factoryCode;
		this.eventCnt = eventCnt;
	}

	// Property accessors

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public Date getStatDate() {
		return this.statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}

	public Byte getEventType() {
		return this.eventType;
	}

	public void setEventType(Byte eventType) {
		this.eventType = eventType;
	}

	public Byte getEventLevel() {
		return this.eventLevel;
	}

	public void setEventLevel(Byte eventLevel) {
		this.eventLevel = eventLevel;
	}

	public String getFactoryCode() {
		return this.factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public Integer getEventCnt() {
		return this.eventCnt;
	}

	public void setEventCnt(Integer eventCnt) {
		this.eventCnt = eventCnt;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AEventClassStatDId))
			return false;
		AEventClassStatDId castOther = (AEventClassStatDId) other;

		return ((this.getOrgNo() == castOther.getOrgNo()) || (this.getOrgNo() != null
				&& castOther.getOrgNo() != null && this.getOrgNo().equals(
				castOther.getOrgNo())))
				&& ((this.getStatDate() == castOther.getStatDate()) || (this
						.getStatDate() != null
						&& castOther.getStatDate() != null && this
						.getStatDate().equals(castOther.getStatDate())))
				&& ((this.getEventType() == castOther.getEventType()) || (this
						.getEventType() != null
						&& castOther.getEventType() != null && this
						.getEventType().equals(castOther.getEventType())))
				&& ((this.getEventLevel() == castOther.getEventLevel()) || (this
						.getEventLevel() != null
						&& castOther.getEventLevel() != null && this
						.getEventLevel().equals(castOther.getEventLevel())))
				&& ((this.getFactoryCode() == castOther.getFactoryCode()) || (this
						.getFactoryCode() != null
						&& castOther.getFactoryCode() != null && this
						.getFactoryCode().equals(castOther.getFactoryCode())))
				&& ((this.getEventCnt() == castOther.getEventCnt()) || (this
						.getEventCnt() != null
						&& castOther.getEventCnt() != null && this
						.getEventCnt().equals(castOther.getEventCnt())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getOrgNo() == null ? 0 : this.getOrgNo().hashCode());
		result = 37 * result
				+ (getStatDate() == null ? 0 : this.getStatDate().hashCode());
		result = 37 * result
				+ (getEventType() == null ? 0 : this.getEventType().hashCode());
		result = 37
				* result
				+ (getEventLevel() == null ? 0 : this.getEventLevel()
						.hashCode());
		result = 37
				* result
				+ (getFactoryCode() == null ? 0 : this.getFactoryCode()
						.hashCode());
		result = 37 * result
				+ (getEventCnt() == null ? 0 : this.getEventCnt().hashCode());
		return result;
	}

}