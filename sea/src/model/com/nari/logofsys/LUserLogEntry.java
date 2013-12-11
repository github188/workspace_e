package com.nari.logofsys;

import java.util.Date;

/**
 * LUserLogEntry entity. @author MyEclipse Persistence Tools
 */

public class LUserLogEntry implements java.io.Serializable {

	// Fields

	private Long logId;
	private String orgNo;
	private String empNo;
	private String counterpartNo;
	private Date logEntryDate;
	private String weather;
	private String windDirection;
	private String windForce;
	private String topTemperature;
	private Double lowTemperature;
	private String profile;
	private Integer paramCnt;
	private Integer ctrlCnt;
	private Integer attachTmnl;
	private Integer detachTmnl;
	private Integer attachMeter;
	private Integer detachMeter;
	private Integer attachAc;
	private Integer detachAc;

	// Constructors

	/** default constructor */
	public LUserLogEntry() {
	}

	/** minimal constructor */
	public LUserLogEntry(String orgNo, String empNo) {
		this.orgNo = orgNo;
		this.empNo = empNo;
	}

	/** full constructor */
	public LUserLogEntry(String orgNo, String empNo, String counterpartNo,
			Date logEntryDate, String weather, String windDirection,
			String windForce, String topTemperature, Double lowTemperature,
			String profile, Integer paramCnt, Integer ctrlCnt,
			Integer attachTmnl, Integer detachTmnl, Integer attachMeter,
			Integer detachMeter, Integer attachAc, Integer detachAc) {
		this.orgNo = orgNo;
		this.empNo = empNo;
		this.counterpartNo = counterpartNo;
		this.logEntryDate = logEntryDate;
		this.weather = weather;
		this.windDirection = windDirection;
		this.windForce = windForce;
		this.topTemperature = topTemperature;
		this.lowTemperature = lowTemperature;
		this.profile = profile;
		this.paramCnt = paramCnt;
		this.ctrlCnt = ctrlCnt;
		this.attachTmnl = attachTmnl;
		this.detachTmnl = detachTmnl;
		this.attachMeter = attachMeter;
		this.detachMeter = detachMeter;
		this.attachAc = attachAc;
		this.detachAc = detachAc;
	}

	// Property accessors

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getCounterpartNo() {
		return this.counterpartNo;
	}

	public void setCounterpartNo(String counterpartNo) {
		this.counterpartNo = counterpartNo;
	}

	public Date getLogEntryDate() {
		return this.logEntryDate;
	}

	public void setLogEntryDate(Date logEntryDate) {
		this.logEntryDate = logEntryDate;
	}

	public String getWeather() {
		return this.weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWindDirection() {
		return this.windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getWindForce() {
		return this.windForce;
	}

	public void setWindForce(String windForce) {
		this.windForce = windForce;
	}

	public String getTopTemperature() {
		return this.topTemperature;
	}

	public void setTopTemperature(String topTemperature) {
		this.topTemperature = topTemperature;
	}

	public Double getLowTemperature() {
		return this.lowTemperature;
	}

	public void setLowTemperature(Double lowTemperature) {
		this.lowTemperature = lowTemperature;
	}

	public String getProfile() {
		return this.profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Integer getParamCnt() {
		return this.paramCnt;
	}

	public void setParamCnt(Integer paramCnt) {
		this.paramCnt = paramCnt;
	}

	public Integer getCtrlCnt() {
		return this.ctrlCnt;
	}

	public void setCtrlCnt(Integer ctrlCnt) {
		this.ctrlCnt = ctrlCnt;
	}

	public Integer getAttachTmnl() {
		return this.attachTmnl;
	}

	public void setAttachTmnl(Integer attachTmnl) {
		this.attachTmnl = attachTmnl;
	}

	public Integer getDetachTmnl() {
		return this.detachTmnl;
	}

	public void setDetachTmnl(Integer detachTmnl) {
		this.detachTmnl = detachTmnl;
	}

	public Integer getAttachMeter() {
		return this.attachMeter;
	}

	public void setAttachMeter(Integer attachMeter) {
		this.attachMeter = attachMeter;
	}

	public Integer getDetachMeter() {
		return this.detachMeter;
	}

	public void setDetachMeter(Integer detachMeter) {
		this.detachMeter = detachMeter;
	}

	public Integer getAttachAc() {
		return this.attachAc;
	}

	public void setAttachAc(Integer attachAc) {
		this.attachAc = attachAc;
	}

	public Integer getDetachAc() {
		return this.detachAc;
	}

	public void setDetachAc(Integer detachAc) {
		this.detachAc = detachAc;
	}

}