package com.nari.qrystat.reportQuery;

import java.io.Serializable;

public class VoltDegree implements Serializable {
	/**
	 * 电压等级类
	 */
	private static final long serialVersionUID = 1L;
	private String voltDegree;
	private String volt;
	public String getVoltDegree() {
		return voltDegree;
	}
	public void setVoltDegree(String voltDegree) {
		this.voltDegree = voltDegree;
	}
	public String getVolt() {
		return volt;
	}
	public void setVolt(String volt) {
		this.volt = volt;
	}

}
