package com.nari.orderlypower;

import java.util.List;

public class TmnlPwrctrlUserBean {

	private String tmnlAssetNo;		//终端资产号
	private Short totalNo;				//总加组号
	private Float floatValue;			//浮动系数
	private String execFlag ="-999" ;//参数或控制投入状态标示
	private String keyId;					//界面唯一标识，终端资产编号+总加组号
	
	private List<PwrctrlTempDetailBean> detailList;//明细列表
	
	public TmnlPwrctrlUserBean() {
	}

	public TmnlPwrctrlUserBean(String keyId,String execFlag) {
		this.keyId = keyId;
		this.execFlag = execFlag;
	}

	@Override
	public String toString() {
		return "tmnlAssetNo:" + tmnlAssetNo + ", totalNo:" + totalNo + ", floatValue:" +floatValue + ", detailList:" +detailList;
	}

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

	public Float getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(Float floatValue) {
		this.floatValue = floatValue;
	}

	public String getExecFlag() {
		return execFlag;
	}

	public void setExecFlag(String execFlag) {
		this.execFlag = execFlag;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public List<PwrctrlTempDetailBean> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PwrctrlTempDetailBean> detailList) {
		this.detailList = detailList;
	}
}