package com.nari.statreport;

import java.util.Date;

/**
 * AEventStatDId entity. @author MyEclipse Persistence Tools
 */

public class AEventStatDId implements java.io.Serializable {

	// Fields

	private String orgNo;
	private String consNo;
	private String tmnlAssetNo;
	private String eventNo;
	private Byte isStart;
	private Date statDate;
	private Integer eventCnt;
	private Integer dayEvents;

	// Constructors

	/** default constructor */
	public AEventStatDId() {
	}

	/** full constructor */
	public AEventStatDId(String orgNo, String consNo, String tmnlAssetNo,
			String eventNo, Byte isStart, Date statDate, Integer eventCnt,
			Integer dayEvents) {
		this.orgNo = orgNo;
		this.consNo = consNo;
		this.tmnlAssetNo = tmnlAssetNo;
		this.eventNo = eventNo;
		this.isStart = isStart;
		this.statDate = statDate;
		this.eventCnt = eventCnt;
		this.dayEvents = dayEvents;
	}

	// Property accessors

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

	public String getEventNo() {
		return this.eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	public Byte getIsStart() {
		return this.isStart;
	}

	public void setIsStart(Byte isStart) {
		this.isStart = isStart;
	}

	public Date getStatDate() {
		return this.statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}

	public Integer getEventCnt() {
		return this.eventCnt;
	}

	public void setEventCnt(Integer eventCnt) {
		this.eventCnt = eventCnt;
	}

	public Integer getDayEvents() {
		return this.dayEvents;
	}

	public void setDayEvents(Integer dayEvents) {
		this.dayEvents = dayEvents;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AEventStatDId))
			return false;
		AEventStatDId castOther = (AEventStatDId) other;

		return ((this.getOrgNo() == castOther.getOrgNo()) || (this.getOrgNo() != null
				&& castOther.getOrgNo() != null && this.getOrgNo().equals(
				castOther.getOrgNo())))
				&& ((this.getConsNo() == castOther.getConsNo()) || (this
						.getConsNo() != null
						&& castOther.getConsNo() != null && this.getConsNo()
						.equals(castOther.getConsNo())))
				&& ((this.getTmnlAssetNo() == castOther.getTmnlAssetNo()) || (this
						.getTmnlAssetNo() != null
						&& castOther.getTmnlAssetNo() != null && this
						.getTmnlAssetNo().equals(castOther.getTmnlAssetNo())))
				&& ((this.getEventNo() == castOther.getEventNo()) || (this
						.getEventNo() != null
						&& castOther.getEventNo() != null && this.getEventNo()
						.equals(castOther.getEventNo())))
				&& ((this.getIsStart() == castOther.getIsStart()) || (this
						.getIsStart() != null
						&& castOther.getIsStart() != null && this.getIsStart()
						.equals(castOther.getIsStart())))
				&& ((this.getStatDate() == castOther.getStatDate()) || (this
						.getStatDate() != null
						&& castOther.getStatDate() != null && this
						.getStatDate().equals(castOther.getStatDate())))
				&& ((this.getEventCnt() == castOther.getEventCnt()) || (this
						.getEventCnt() != null
						&& castOther.getEventCnt() != null && this
						.getEventCnt().equals(castOther.getEventCnt())))
				&& ((this.getDayEvents() == castOther.getDayEvents()) || (this
						.getDayEvents() != null
						&& castOther.getDayEvents() != null && this
						.getDayEvents().equals(castOther.getDayEvents())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getOrgNo() == null ? 0 : this.getOrgNo().hashCode());
		result = 37 * result
				+ (getConsNo() == null ? 0 : this.getConsNo().hashCode());
		result = 37
				* result
				+ (getTmnlAssetNo() == null ? 0 : this.getTmnlAssetNo()
						.hashCode());
		result = 37 * result
				+ (getEventNo() == null ? 0 : this.getEventNo().hashCode());
		result = 37 * result
				+ (getIsStart() == null ? 0 : this.getIsStart().hashCode());
		result = 37 * result
				+ (getStatDate() == null ? 0 : this.getStatDate().hashCode());
		result = 37 * result
				+ (getEventCnt() == null ? 0 : this.getEventCnt().hashCode());
		result = 37 * result
				+ (getDayEvents() == null ? 0 : this.getDayEvents().hashCode());
		return result;
	}

}