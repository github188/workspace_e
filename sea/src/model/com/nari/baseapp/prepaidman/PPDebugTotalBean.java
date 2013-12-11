package com.nari.baseapp.prepaidman;

/**
 * 预付费投入调试总加组数据值对象
 * @author 姜炜超
 */
public class PPDebugTotalBean {
    private String tmnlAssetNo;//终端资产编号
    private Short totalNo;//总加组编号
    private String totalName;//总加组名称
    
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	public Short getTotalNo() {
		return totalNo;
	}
	public void setTotalNo(Short totalNo) {
		this.totalNo = totalNo;
	}
	public String getTotalName() {
		return totalName;
	}
	public void setTotalName(String totalName) {
		this.totalName = totalName;
	}
}
