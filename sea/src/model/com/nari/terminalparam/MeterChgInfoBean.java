package com.nari.terminalparam;

/**
 * 电能表变更信息数据值对象
 * @author 姜炜超
 */
public class MeterChgInfoBean {
	private String oldAssetNo;//原电能表资产号
	private String newAssetNo;//新电能表资产号
	private Double oldMRNum;//原抄见示数
	private Double newMRNum;//新抄见示数
	
	public String getOldAssetNo() {
		return oldAssetNo;
	}
	public void setOldAssetNo(String oldAssetNo) {
		this.oldAssetNo = oldAssetNo;
	}
	public String getNewAssetNo() {
		return newAssetNo;
	}
	public void setNewAssetNo(String newAssetNo) {
		this.newAssetNo = newAssetNo;
	}
	public Double getOldMRNum() {
		return oldMRNum;
	}
	public void setOldMRNum(Double oldMRNum) {
		this.oldMRNum = oldMRNum;
	}
	public Double getNewMRNum() {
		return newMRNum;
	}
	public void setNewMRNum(Double newMRNum) {
		this.newMRNum = newMRNum;
	}
}
