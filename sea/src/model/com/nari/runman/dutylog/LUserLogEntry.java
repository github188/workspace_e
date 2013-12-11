package com.nari.runman.dutylog;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="L_USER_LOG_ENTRY")
public class LUserLogEntry {
	@Id
	@GeneratedValue(generator="S_L_USER_LOG_ENTRY")
	@Column(name="log_id")
	private Integer logId;
	@Column(name="org_no")
	private String orgNo;
	@Column(name="emp_no")
	private String empNo;
	@Column(name="counterpart_no")
	private String  counterpartNo;
	@Column(name="log_entry_date")
	private Date 	logEntryDate;
	@Column(name="logout_date")
	private Date logoutDate;
	@Column(name="weather")
	private String weather;
	@Column(name="wind_direction")
	private String windDirection;
	@Column(name="wind_force")
	private String windForce;
	@Column(name="top_temperature")
	private Double topTemperature;
	@Column(name="low_temperature")
	private Double lowTemperature;
	@Column(name="profile")
	private String profile;
	@Column(name="param_cnt")
	private Integer paramCnt ;
	@Column(name="ctrl_cnt")
	private Integer ctrlCnt;
	@Column(name="attach_tmnl")
	private Integer attachTmnl ;
	@Column(name="detach_tmnl")
	private Integer detachTmnl;
	@Column(name="attach_meter")
	private Integer attachMeter ;
	@Column(name="detach_meter")
	private Integer detachMeter ;
	@Column(name="attach_ac")
	private Integer attachAc;
	@Column(name="detach_ac")
	private Integer detachAc ;
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getCounterpartNo() {
		return counterpartNo;
	}
	public void setCounterpartNo(String counterpartNo) {
		this.counterpartNo = counterpartNo;
	}
	public Date getLogEntryDate() {
		return logEntryDate;
	}
	public void setLogEntryDate(Date logEntryDate) {
		this.logEntryDate = logEntryDate;
	}
	public Date getLogoutDate() {
		return logoutDate;
	}
	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	public String getWindForce() {
		return windForce;
	}
	public void setWindForce(String windForce) {
		this.windForce = windForce;
	}
	public Double getTopTemperature() {
		return topTemperature;
	}
	public void setTopTemperature(Double topTemperature) {
		this.topTemperature = topTemperature;
	}
	public Double getLowTemperature() {
		return lowTemperature;
	}
	public void setLowTemperature(Double lowTemperature) {
		this.lowTemperature = lowTemperature;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public Integer getParamCnt() {
		return paramCnt;
	}
	public void setParamCnt(Integer paramCnt) {
		this.paramCnt = paramCnt;
	}
	public Integer getCtrlCnt() {
		return ctrlCnt;
	}
	public void setCtrlCnt(Integer ctrlCnt) {
		this.ctrlCnt = ctrlCnt;
	}
	public Integer getAttachTmnl() {
		return attachTmnl;
	}
	public void setAttachTmnl(Integer attachTmnl) {
		this.attachTmnl = attachTmnl;
	}
	public Integer getDetachTmnl() {
		return detachTmnl;
	}
	public void setDetachTmnl(Integer detachTmnl) {
		this.detachTmnl = detachTmnl;
	}
	public Integer getAttachMeter() {
		return attachMeter;
	}
	public void setAttachMeter(Integer attachMeter) {
		this.attachMeter = attachMeter;
	}
	public Integer getDetachMeter() {
		return detachMeter;
	}
	public void setDetachMeter(Integer detachMeter) {
		this.detachMeter = detachMeter;
	}
	public Integer getAttachAc() {
		return attachAc;
	}
	public void setAttachAc(Integer attachAc) {
		this.attachAc = attachAc;
	}
	public Integer getDetachAc() {
		return detachAc;
	}
	public void setDetachAc(Integer detachAc) {
		this.detachAc = detachAc;
	}

	
	
}
