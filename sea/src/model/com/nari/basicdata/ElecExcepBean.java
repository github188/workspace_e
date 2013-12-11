package com.nari.basicdata;

public class ElecExcepBean {

	private String voltageLost;
    private String ammeterStop;
    private String ctShortAnalysis;
    private String voltageCut;
    private String dataExcep;
    private String overLoadRun;
    private String powerExcep;
    private String dayUseChange;
    private String eCurrentExcep;
    private String voltageOver;
    
    public ElecExcepBean() {
		this.voltageLost = "0";
		this.ammeterStop = "0";
		this.ctShortAnalysis = "0";
		this.voltageCut = "0";
		this.dataExcep = "0";
		this.overLoadRun = "0";
		this.powerExcep = "0";
		this.dayUseChange = "0";
		this.eCurrentExcep = "0";
		this.voltageOver = "0";
	}
    
	public String getVoltageLost() {
		return voltageLost;
	}
	public void setVoltageLost(String voltageLost) {
		this.voltageLost = voltageLost;
	}
	public String getAmmeterStop() {
		return ammeterStop;
	}
	public void setAmmeterStop(String ammeterStop) {
		this.ammeterStop = ammeterStop;
	}
	public String getCtShortAnalysis() {
		return ctShortAnalysis;
	}
	public void setCtShortAnalysis(String ctShortAnalysis) {
		this.ctShortAnalysis = ctShortAnalysis;
	}
	public String getVoltageCut() {
		return voltageCut;
	}
	public void setVoltageCut(String voltageCut) {
		this.voltageCut = voltageCut;
	}
	public String getDataExcep() {
		return dataExcep;
	}
	public void setDataExcep(String dataExcep) {
		this.dataExcep = dataExcep;
	}
	public String getOverLoadRun() {
		return overLoadRun;
	}
	public void setOverLoadRun(String overLoadRun) {
		this.overLoadRun = overLoadRun;
	}
	public String getPowerExcep() {
		return powerExcep;
	}
	public void setPowerExcep(String powerExcep) {
		this.powerExcep = powerExcep;
	}
	public String getDayUseChange() {
		return dayUseChange;
	}
	public void setDayUseChange(String dayUseChange) {
		this.dayUseChange = dayUseChange;
	}
	public String geteCurrentExcep() {
		return eCurrentExcep;
	}
	public void seteCurrentExcep(String eCurrentExcep) {
		this.eCurrentExcep = eCurrentExcep;
	}
	public String getVoltageOver() {
		return voltageOver;
	}
	public void setVoltageOver(String voltageOver) {
		this.voltageOver = voltageOver;
	}
}
