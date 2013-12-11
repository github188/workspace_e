package com.nari.qrystat.colldataanalyse;

import java.io.Serializable;

public class GeneralRealFactorDataBean implements Serializable{
	private String time;
    private Double factorA;
    private Double factorB;
    private Double factorC;
    private Double factor;
    private Boolean flag;
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
	public Double getFactorA() {
		return factorA;
	}
	public void setFactorA(Double factorA) {
		this.factorA = factorA;
	}
	public Double getFactorB() {
		return factorB;
	}
	public void setFactorB(Double factorB) {
		this.factorB = factorB;
	}
	public Double getFactorC() {
		return factorC;
	}
	public void setFactorC(Double factorC) {
		this.factorC = factorC;
	}
	public Double getFactor() {
		return factor;
	}
	public void setFactor(Double factor) {
		this.factor = factor;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
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
