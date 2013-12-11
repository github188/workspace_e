package com.nari.baseapp.prepaidman;

import com.nari.fe.commdefine.param.ErrorCode;

/**
 * 预付费投入调试数据召测数据值对象
 * @author 姜炜超
 */
public class PPDebugCallDataBean {
    private String tmnlAssetNo;//终端资产编号
    private String item;//数据项编码
    private String itemName;//数据项
    private String value;//数据值
    private String time;//时间
    private int pn;//测量点or总加组
    private ErrorCode errorCode;//错误编码
    
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getPn() {
		return pn;
	}
	public void setPn(int pn) {
		this.pn = pn;
	}
	public ErrorCode getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
