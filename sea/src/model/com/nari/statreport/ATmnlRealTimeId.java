package com.nari.statreport;

import java.util.Date;

/**
 * ATmnlRealTimeId entity. @author MyEclipse Persistence Tools
 */

public class ATmnlRealTimeId implements java.io.Serializable {

	// Fields

	private String orgNo;
	private String consNo;
	private String tmnlAssetNo;
	private String commMode;
	private Date currentLoad;
	private Date loadTime;
	private String eventNo;
	private Date eventTime;
	private Double ct;
	private Double pt;
	private Date receiveTime;

	// Constructors

	/** default constructor */
	public ATmnlRealTimeId() {
	}

	/** full constructor */
	public ATmnlRealTimeId(String orgNo, String consNo, String tmnlAssetNo,
			String commMode, Date currentLoad, Date loadTime, String eventNo,
			Date eventTime, Double ct, Double pt, Date receiveTime) {
		this.orgNo = orgNo;
		this.consNo = consNo;
		this.tmnlAssetNo = tmnlAssetNo;
		this.commMode = commMode;
		this.currentLoad = currentLoad;
		this.loadTime = loadTime;
		this.eventNo = eventNo;
		this.eventTime = eventTime;
		this.ct = ct;
		this.pt = pt;
		this.receiveTime = receiveTime;
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

	public String getCommMode() {
		return this.commMode;
	}

	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

	public Date getCurrentLoad() {
		return this.currentLoad;
	}

	public void setCurrentLoad(Date currentLoad) {
		this.currentLoad = currentLoad;
	}

	public Date getLoadTime() {
		return this.loadTime;
	}

	public void setLoadTime(Date loadTime) {
		this.loadTime = loadTime;
	}

	public String getEventNo() {
		return this.eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	public Date getEventTime() {
		return this.eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public Double getCt() {
		return this.ct;
	}

	public void setCt(Double ct) {
		this.ct = ct;
	}

	public Double getPt() {
		return this.pt;
	}

	public void setPt(Double pt) {
		this.pt = pt;
	}

	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ATmnlRealTimeId))
			return false;
		ATmnlRealTimeId castOther = (ATmnlRealTimeId) other;

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
				&& ((this.getCommMode() == castOther.getCommMode()) || (this
						.getCommMode() != null
						&& castOther.getCommMode() != null && this
						.getCommMode().equals(castOther.getCommMode())))
				&& ((this.getCurrentLoad() == castOther.getCurrentLoad()) || (this
						.getCurrentLoad() != null
						&& castOther.getCurrentLoad() != null && this
						.getCurrentLoad().equals(castOther.getCurrentLoad())))
				&& ((this.getLoadTime() == castOther.getLoadTime()) || (this
						.getLoadTime() != null
						&& castOther.getLoadTime() != null && this
						.getLoadTime().equals(castOther.getLoadTime())))
				&& ((this.getEventNo() == castOther.getEventNo()) || (this
						.getEventNo() != null
						&& castOther.getEventNo() != null && this.getEventNo()
						.equals(castOther.getEventNo())))
				&& ((this.getEventTime() == castOther.getEventTime()) || (this
						.getEventTime() != null
						&& castOther.getEventTime() != null && this
						.getEventTime().equals(castOther.getEventTime())))
				&& ((this.getCt() == castOther.getCt()) || (this.getCt() != null
						&& castOther.getCt() != null && this.getCt().equals(
						castOther.getCt())))
				&& ((this.getPt() == castOther.getPt()) || (this.getPt() != null
						&& castOther.getPt() != null && this.getPt().equals(
						castOther.getPt())))
				&& ((this.getReceiveTime() == castOther.getReceiveTime()) || (this
						.getReceiveTime() != null
						&& castOther.getReceiveTime() != null && this
						.getReceiveTime().equals(castOther.getReceiveTime())));
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
				+ (getCommMode() == null ? 0 : this.getCommMode().hashCode());
		result = 37
				* result
				+ (getCurrentLoad() == null ? 0 : this.getCurrentLoad()
						.hashCode());
		result = 37 * result
				+ (getLoadTime() == null ? 0 : this.getLoadTime().hashCode());
		result = 37 * result
				+ (getEventNo() == null ? 0 : this.getEventNo().hashCode());
		result = 37 * result
				+ (getEventTime() == null ? 0 : this.getEventTime().hashCode());
		result = 37 * result + (getCt() == null ? 0 : this.getCt().hashCode());
		result = 37 * result + (getPt() == null ? 0 : this.getPt().hashCode());
		result = 37
				* result
				+ (getReceiveTime() == null ? 0 : this.getReceiveTime()
						.hashCode());
		return result;
	}

}