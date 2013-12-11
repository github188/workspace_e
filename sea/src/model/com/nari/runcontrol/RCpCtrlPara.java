package com.nari.runcontrol;

import java.util.Date;

/**
 * RCpCtrlPara entity. @author MyEclipse Persistence Tools
 */

public class RCpCtrlPara implements java.io.Serializable {

	// Fields

	private Long controlParaId;
	private String cpNo;
	private Double safetyLoad;
	private String remoteFlag;
	private Date remoteBgnDate;
	private Date remoteEndDate;
	private String powerFlag;
	private Double powerValue;
	private Date powerBgnDate;
	private Date powerEndDate;
	private String holidayFlag;
	private String holiday;
	private Double holidayValue;
	private Date holidayBgnDate;
	private Date holidayEndDate;
	private String stopFlag;
	private Long stopValue;
	private Date stopBngDate;
	private Date stopEndDate;
	private String crFlag;
	private String crWarnFlag;
	private Date crDate;
	private String prepayCtrlFlag;
	private Long remainPq;
	private String turnsStatus;

	// Constructors

	/** default constructor */
	public RCpCtrlPara() {
	}

	/** minimal constructor */
	public RCpCtrlPara(String cpNo, Double safetyLoad, String remoteFlag,
			String powerFlag, String holidayFlag, String stopFlag,
			String crFlag, String crWarnFlag, String prepayCtrlFlag) {
		this.cpNo = cpNo;
		this.safetyLoad = safetyLoad;
		this.remoteFlag = remoteFlag;
		this.powerFlag = powerFlag;
		this.holidayFlag = holidayFlag;
		this.stopFlag = stopFlag;
		this.crFlag = crFlag;
		this.crWarnFlag = crWarnFlag;
		this.prepayCtrlFlag = prepayCtrlFlag;
	}

	/** full constructor */
	public RCpCtrlPara(String cpNo, Double safetyLoad, String remoteFlag,
			Date remoteBgnDate, Date remoteEndDate, String powerFlag,
			Double powerValue, Date powerBgnDate, Date powerEndDate,
			String holidayFlag, String holiday, Double holidayValue,
			Date holidayBgnDate, Date holidayEndDate, String stopFlag,
			Long stopValue, Date stopBngDate, Date stopEndDate, String crFlag,
			String crWarnFlag, Date crDate, String prepayCtrlFlag,
			Long remainPq, String turnsStatus) {
		this.cpNo = cpNo;
		this.safetyLoad = safetyLoad;
		this.remoteFlag = remoteFlag;
		this.remoteBgnDate = remoteBgnDate;
		this.remoteEndDate = remoteEndDate;
		this.powerFlag = powerFlag;
		this.powerValue = powerValue;
		this.powerBgnDate = powerBgnDate;
		this.powerEndDate = powerEndDate;
		this.holidayFlag = holidayFlag;
		this.holiday = holiday;
		this.holidayValue = holidayValue;
		this.holidayBgnDate = holidayBgnDate;
		this.holidayEndDate = holidayEndDate;
		this.stopFlag = stopFlag;
		this.stopValue = stopValue;
		this.stopBngDate = stopBngDate;
		this.stopEndDate = stopEndDate;
		this.crFlag = crFlag;
		this.crWarnFlag = crWarnFlag;
		this.crDate = crDate;
		this.prepayCtrlFlag = prepayCtrlFlag;
		this.remainPq = remainPq;
		this.turnsStatus = turnsStatus;
	}

	// Property accessors

	public Long getControlParaId() {
		return this.controlParaId;
	}

	public void setControlParaId(Long controlParaId) {
		this.controlParaId = controlParaId;
	}

	public String getCpNo() {
		return this.cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

	public Double getSafetyLoad() {
		return this.safetyLoad;
	}

	public void setSafetyLoad(Double safetyLoad) {
		this.safetyLoad = safetyLoad;
	}

	public String getRemoteFlag() {
		return this.remoteFlag;
	}

	public void setRemoteFlag(String remoteFlag) {
		this.remoteFlag = remoteFlag;
	}

	public Date getRemoteBgnDate() {
		return this.remoteBgnDate;
	}

	public void setRemoteBgnDate(Date remoteBgnDate) {
		this.remoteBgnDate = remoteBgnDate;
	}

	public Date getRemoteEndDate() {
		return this.remoteEndDate;
	}

	public void setRemoteEndDate(Date remoteEndDate) {
		this.remoteEndDate = remoteEndDate;
	}

	public String getPowerFlag() {
		return this.powerFlag;
	}

	public void setPowerFlag(String powerFlag) {
		this.powerFlag = powerFlag;
	}

	public Double getPowerValue() {
		return this.powerValue;
	}

	public void setPowerValue(Double powerValue) {
		this.powerValue = powerValue;
	}

	public Date getPowerBgnDate() {
		return this.powerBgnDate;
	}

	public void setPowerBgnDate(Date powerBgnDate) {
		this.powerBgnDate = powerBgnDate;
	}

	public Date getPowerEndDate() {
		return this.powerEndDate;
	}

	public void setPowerEndDate(Date powerEndDate) {
		this.powerEndDate = powerEndDate;
	}

	public String getHolidayFlag() {
		return this.holidayFlag;
	}

	public void setHolidayFlag(String holidayFlag) {
		this.holidayFlag = holidayFlag;
	}

	public String getHoliday() {
		return this.holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public Double getHolidayValue() {
		return this.holidayValue;
	}

	public void setHolidayValue(Double holidayValue) {
		this.holidayValue = holidayValue;
	}

	public Date getHolidayBgnDate() {
		return this.holidayBgnDate;
	}

	public void setHolidayBgnDate(Date holidayBgnDate) {
		this.holidayBgnDate = holidayBgnDate;
	}

	public Date getHolidayEndDate() {
		return this.holidayEndDate;
	}

	public void setHolidayEndDate(Date holidayEndDate) {
		this.holidayEndDate = holidayEndDate;
	}

	public String getStopFlag() {
		return this.stopFlag;
	}

	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
	}

	public Long getStopValue() {
		return this.stopValue;
	}

	public void setStopValue(Long stopValue) {
		this.stopValue = stopValue;
	}

	public Date getStopBngDate() {
		return this.stopBngDate;
	}

	public void setStopBngDate(Date stopBngDate) {
		this.stopBngDate = stopBngDate;
	}

	public Date getStopEndDate() {
		return this.stopEndDate;
	}

	public void setStopEndDate(Date stopEndDate) {
		this.stopEndDate = stopEndDate;
	}

	public String getCrFlag() {
		return this.crFlag;
	}

	public void setCrFlag(String crFlag) {
		this.crFlag = crFlag;
	}

	public String getCrWarnFlag() {
		return this.crWarnFlag;
	}

	public void setCrWarnFlag(String crWarnFlag) {
		this.crWarnFlag = crWarnFlag;
	}

	public Date getCrDate() {
		return this.crDate;
	}

	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}

	public String getPrepayCtrlFlag() {
		return this.prepayCtrlFlag;
	}

	public void setPrepayCtrlFlag(String prepayCtrlFlag) {
		this.prepayCtrlFlag = prepayCtrlFlag;
	}

	public Long getRemainPq() {
		return this.remainPq;
	}

	public void setRemainPq(Long remainPq) {
		this.remainPq = remainPq;
	}

	public String getTurnsStatus() {
		return this.turnsStatus;
	}

	public void setTurnsStatus(String turnsStatus) {
		this.turnsStatus = turnsStatus;
	}

}