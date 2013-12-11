package com.nari.logofsys;

import java.sql.Timestamp;

public class LMasterStationResId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ipAddr;
	private Timestamp monitorTime;

	
	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Timestamp getMonitorTime() {
		return monitorTime;
	}

	public void setMonitorTime(Timestamp monitorTime) {
		this.monitorTime = monitorTime;
	}

}