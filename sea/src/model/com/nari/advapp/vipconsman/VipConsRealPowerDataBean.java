package com.nari.advapp.vipconsman;

import java.io.Serializable;

/**
 * 重点用户监测曲线实时负荷数据之数据值对象
 * @author 姜炜超
 */
public class VipConsRealPowerDataBean implements Serializable{
	private String time;
    private Double p;//有功功率
    private Double pA;//A相有功功率
    private Double pB;//B相有功功率
    private Double pC;//C相有功功率
    private Boolean flagPA;
    private Boolean flagPB;
    private Boolean flagPC;
    private Boolean flagP;
    private Double q;//无功功率
    private Double qA;//A相无功功率
    private Double qB;//B相无功功率
    private Double qC;//C相无功功率
    private Boolean flagQA;
    private Boolean flagQB;
    private Boolean flagQC;
    private Boolean flagQ;
    private Boolean beanFlag;//对于整个bean是否漏点的标识
    
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Double getP() {
		return p;
	}
	public void setP(Double p) {
		this.p = p;
	}
	public Double getpA() {
		return pA;
	}
	public void setpA(Double pA) {
		this.pA = pA;
	}
	public Double getpB() {
		return pB;
	}
	public void setpB(Double pB) {
		this.pB = pB;
	}
	public Double getpC() {
		return pC;
	}
	public void setpC(Double pC) {
		this.pC = pC;
	}
	public Boolean getFlagPA() {
		return flagPA;
	}
	public void setFlagPA(Boolean flagPA) {
		this.flagPA = flagPA;
	}
	public Boolean getFlagPB() {
		return flagPB;
	}
	public void setFlagPB(Boolean flagPB) {
		this.flagPB = flagPB;
	}
	public Boolean getFlagPC() {
		return flagPC;
	}
	public void setFlagPC(Boolean flagPC) {
		this.flagPC = flagPC;
	}
	public Boolean getFlagP() {
		return flagP;
	}
	public void setFlagP(Boolean flagP) {
		this.flagP = flagP;
	}
	public Double getQ() {
		return q;
	}
	public void setQ(Double q) {
		this.q = q;
	}
	public Double getqA() {
		return qA;
	}
	public void setqA(Double qA) {
		this.qA = qA;
	}
	public Double getqB() {
		return qB;
	}
	public void setqB(Double qB) {
		this.qB = qB;
	}
	public Double getqC() {
		return qC;
	}
	public void setqC(Double qC) {
		this.qC = qC;
	}
	public Boolean getFlagQA() {
		return flagQA;
	}
	public void setFlagQA(Boolean flagQA) {
		this.flagQA = flagQA;
	}
	public Boolean getFlagQB() {
		return flagQB;
	}
	public void setFlagQB(Boolean flagQB) {
		this.flagQB = flagQB;
	}
	public Boolean getFlagQC() {
		return flagQC;
	}
	public void setFlagQC(Boolean flagQC) {
		this.flagQC = flagQC;
	}
	public Boolean getFlagQ() {
		return flagQ;
	}
	public void setFlagQ(Boolean flagQ) {
		this.flagQ = flagQ;
	}
	public Boolean getBeanFlag() {
		return beanFlag;
	}
	public void setBeanFlag(Boolean beanFlag) {
		this.beanFlag = beanFlag;
	}
}
