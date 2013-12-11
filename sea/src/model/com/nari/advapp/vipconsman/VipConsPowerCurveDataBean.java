package com.nari.advapp.vipconsman;

import java.io.Serializable;

/**
 * 重点用户监测Grid负荷数据之数据值对象
 * @author 姜炜超
 */
public class VipConsPowerCurveDataBean implements Serializable {
	private String time;//时间点
	private String assetNo;//表计编号，扩展用
	private Double statP;//冻结有功功率
	private Double statPa;//冻结A相有功功率
	private Double statPb;//冻结B相有功功率
	private Double statPc;//冻结C相有功功率
	private Double statQ;//冻结无功功率
	private Double statQa;//冻结A相无功功率
	private Double statQb;//冻结B相无功功率
	private Double statQc;//冻结C相无功功率
	private Double realP;//实时有功功率
	private Double realPa;//实时A相有功功率
	private Double realPb;//实时B相有功功率
	private Double realPc;//实时C相有功功率
	private Double realQ;//实时无功功率
	private Double realQa;//实时A相无功功率
	private Double realQb;//实时B相无功功率
	private Double realQc;//实时C相无功功率
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public Double getStatP() {
		return statP;
	}
	public void setStatP(Double statP) {
		this.statP = statP;
	}
	public Double getStatPa() {
		return statPa;
	}
	public void setStatPa(Double statPa) {
		this.statPa = statPa;
	}
	public Double getStatPb() {
		return statPb;
	}
	public void setStatPb(Double statPb) {
		this.statPb = statPb;
	}
	public Double getStatPc() {
		return statPc;
	}
	public void setStatPc(Double statPc) {
		this.statPc = statPc;
	}
	public Double getStatQ() {
		return statQ;
	}
	public void setStatQ(Double statQ) {
		this.statQ = statQ;
	}
	public Double getStatQa() {
		return statQa;
	}
	public void setStatQa(Double statQa) {
		this.statQa = statQa;
	}
	public Double getStatQb() {
		return statQb;
	}
	public void setStatQb(Double statQb) {
		this.statQb = statQb;
	}
	public Double getStatQc() {
		return statQc;
	}
	public void setStatQc(Double statQc) {
		this.statQc = statQc;
	}
	public Double getRealP() {
		return realP;
	}
	public void setRealP(Double realP) {
		this.realP = realP;
	}
	public Double getRealPa() {
		return realPa;
	}
	public void setRealPa(Double realPa) {
		this.realPa = realPa;
	}
	public Double getRealPb() {
		return realPb;
	}
	public void setRealPb(Double realPb) {
		this.realPb = realPb;
	}
	public Double getRealPc() {
		return realPc;
	}
	public void setRealPc(Double realPc) {
		this.realPc = realPc;
	}
	public Double getRealQ() {
		return realQ;
	}
	public void setRealQ(Double realQ) {
		this.realQ = realQ;
	}
	public Double getRealQa() {
		return realQa;
	}
	public void setRealQa(Double realQa) {
		this.realQa = realQa;
	}
	public Double getRealQb() {
		return realQb;
	}
	public void setRealQb(Double realQb) {
		this.realQb = realQb;
	}
	public Double getRealQc() {
		return realQc;
	}
	public void setRealQc(Double realQc) {
		this.realQc = realQc;
	}
	
}
