package com.nari.qrystat.colldataanalyse;

import java.io.Serializable;

public class GeneralRealCurDataBean implements Serializable{
	private String time;
    private Double curA;
    private Double curB;
    private Double curC;
    private Double cur0;
    private Boolean flag0;
    private Boolean flagA;
    private Boolean flagB;
    private Boolean flagC;
    private Boolean beanFlag;//对于整个bean是否漏点的标识
    
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Double getCurA() {
		return curA;
	}
	public void setCurA(Double curA) {
		this.curA = curA;
	}
	public Double getCurB() {
		return curB;
	}
	public void setCurB(Double curB) {
		this.curB = curB;
	}
	public Double getCurC() {
		return curC;
	}
	public void setCurC(Double curC) {
		this.curC = curC;
	}
	public Double getCur0() {
		return cur0;
	}
	public void setCur0(Double cur0) {
		this.cur0 = cur0;
	}
	public Boolean getFlag0() {
		return flag0;
	}
	public void setFlag0(Boolean flag0) {
		this.flag0 = flag0;
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
	public Boolean getBeanFlag() {
		return beanFlag;
	}
	public void setBeanFlag(Boolean beanFlag) {
		this.beanFlag = beanFlag;
	}
}
