package com.nari.baseapp.prepaidman;

public class PrePaidParamSetTmnlBean {
	private String appNo;// 购电单号
	private String orgNo;
	private String consNo;// 用户编号
	private String tmnlAssetNo;// 终端资产编号
	private Short totalNo;// 总加组
	private String protocolCode;// 通讯规约编码
	private Integer alarmValue;// 报警门阀值
	private String refreshFlag;// 追加/刷新标志
	private Integer buyValue;// 购电量（费）值
	private Integer jumpValue;// 跳闸门限值
	private Byte buyFlag;//购电值单位标志：0-购电量;1-购电费
	private Byte alarmValueUnit;//报警门阀值单位:0-kWh、厘;1-MWh、元
	private Byte buyValueUnit;//购电量（费）值单位:0-kWh、厘;1-MWh、元
	private Byte jumpValueUnit;//跳闸门限值单位:0-kWh、厘;1-MWh、元
	private String keyId;//主键，由tmnlAssetNo+totalNo组成的字符串

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
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

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getRefreshFlag() {
		return refreshFlag;
	}

	public void setRefreshFlag(String refreshFlag) {
		this.refreshFlag = refreshFlag;
	}

	public Byte getBuyFlag() {
		return buyFlag;
	}

	public void setBuyFlag(Byte buyFlag) {
		this.buyFlag = buyFlag;
	}

	public Byte getAlarmValueUnit() {
		return alarmValueUnit;
	}

	public void setAlarmValueUnit(Byte alarmValueUnit) {
		this.alarmValueUnit = alarmValueUnit;
	}

	public Byte getBuyValueUnit() {
		return buyValueUnit;
	}

	public void setBuyValueUnit(Byte buyValueUnit) {
		this.buyValueUnit = buyValueUnit;
	}

	public Byte getJumpValueUnit() {
		return jumpValueUnit;
	}

	public void setJumpValueUnit(Byte jumpValueUnit) {
		this.jumpValueUnit = jumpValueUnit;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public Integer getAlarmValue() {
		return alarmValue;
	}

	public void setAlarmValue(Integer alarmValue) {
		this.alarmValue = alarmValue;
	}

	public Integer getBuyValue() {
		return buyValue;
	}

	public void setBuyValue(Integer buyValue) {
		this.buyValue = buyValue;
	}

	public Integer getJumpValue() {
		return jumpValue;
	}

	public void setJumpValue(Integer jumpValue) {
		this.jumpValue = jumpValue;
	}
	
}
