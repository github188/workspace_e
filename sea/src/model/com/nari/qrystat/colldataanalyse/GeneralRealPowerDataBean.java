package com.nari.qrystat.colldataanalyse;

import java.io.Serializable;

public class GeneralRealPowerDataBean implements Serializable{
	private String time;
    private Double power;
    private Double powerA;
    private Double powerB;
    private Double powerC;
    private Boolean flagA;
    private Boolean flagB;
    private Boolean flagC;
    private Boolean flag;
    private Boolean beanFlag;//对于整个bean是否漏点的标识
    
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Double getPower() {
		return power;
	}
	public void setPower(Double power) {
		this.power = power;
	}
	public Double getPowerA() {
		return powerA;
	}
	public void setPowerA(Double powerA) {
		this.powerA = powerA;
	}
	public Double getPowerB() {
		return powerB;
	}
	public void setPowerB(Double powerB) {
		this.powerB = powerB;
	}
	public Double getPowerC() {
		return powerC;
	}
	public void setPowerC(Double powerC) {
		this.powerC = powerC;
	}
	public Boolean getFlagA() {
		return flagA;
	}
	public void setFlagA(Boolean flagA) {
		this.flagA = flagA;
	}
	public Boolean getFlagB() {
		return flagB;
	}
	public void setFlagB(Boolean flagB) {
		this.flagB = flagB;
	}
	public Boolean getFlagC() {
		return flagC;
	}
	public void setFlagC(Boolean flagC) {
		this.flagC = flagC;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public Boolean getBeanFlag() {
		return beanFlag;
	}
	public void setBeanFlag(Boolean beanFlag) {
		this.beanFlag = beanFlag;
	}
}
