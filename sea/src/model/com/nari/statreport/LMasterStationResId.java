package com.nari.statreport;

import java.util.Date;

/**
 * LMasterStationResId entity. @author MyEclipse Persistence Tools
 */

public class LMasterStationResId implements java.io.Serializable {

	// Fields

	private String ipAddr;
	private Date monitorTime;

	// Constructors

	/** default constructor */
	public LMasterStationResId() {
	}

	/** full constructor */
	public LMasterStationResId(String ipAddr, Date monitorTime) {
		this.ipAddr = ipAddr;
		this.monitorTime = monitorTime;
	}

	// Property accessors

	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Date getMonitorTime() {
		return this.monitorTime;
	}

	public void setMonitorTime(Date monitorTime) {
		this.monitorTime = monitorTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LMasterStationResId))
			return false;
		LMasterStationResId castOther = (LMasterStationResId) other;

		return ((this.getIpAddr() == castOther.getIpAddr()) || (this
				.getIpAddr() != null
				&& castOther.getIpAddr() != null && this.getIpAddr().equals(
				castOther.getIpAddr())))
				&& ((this.getMonitorTime() == castOther.getMonitorTime()) || (this
						.getMonitorTime() != null
						&& castOther.getMonitorTime() != null && this
						.getMonitorTime().equals(castOther.getMonitorTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIpAddr() == null ? 0 : this.getIpAddr().hashCode());
		result = 37
				* result
				+ (getMonitorTime() == null ? 0 : this.getMonitorTime()
						.hashCode());
		return result;
	}

}