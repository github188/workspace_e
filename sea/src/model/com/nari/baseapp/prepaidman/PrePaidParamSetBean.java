package com.nari.baseapp.prepaidman;

public class PrePaidParamSetBean {
	private String appNo;// 购电单号
	private String orgNo;// 供电单位
	private String consNo;// 用户编号
	private String consName;// 用户名称
	private String realTimeStatus;// 终端实时工况
	private String tmnlPaulPower;// 保电情况
	private Byte buyFlag;//购电值单位标志：0-购电量;1-购电费
	private String statusCode;// 下发状态
	private String dataFetch;// 数据召测
	private Short totalNo;// 总加组
	private Integer alarmValue;// 报警门阀值
	private Byte alarmValueUnit;//报警门阀值单位:0-kWh、厘;1-MWh、元
	private String refreshFlag;// 追加/刷新标志
	private Integer buyValue;// 购电量（费）值
	private Byte buyValueUnit;//购电量（费）值单位:0-kWh、厘;1-MWh、元
	private Integer jumpValue;// 跳闸门限值
	private Byte jumpValueUnit;//跳闸门限值单位:0-kWh、厘;1-MWh、元
	private Float useValue;// 剩余电量
	private Integer meterId;//电能表标示
	private String tmnlAssetNo;// 终端资产编号
	private String terminalAddr;//终端地址
	private String protocolCode;// 通讯规约编码
	private String keyId;// bean唯一性标示, 取tmnlAssetNo + totalNo组成的字符串

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

	public String getConsName() {
		return consName;
	}

	public void setConsName(String consName) {
		this.consName = consName;
	}

	public Byte getBuyFlag() {
		return buyFlag;
	}

	public void setBuyFlag(Byte buyFlag) {
		this.buyFlag = buyFlag;
	}

	public String getRealTimeStatus() {
		return realTimeStatus;
	}

	public void setRealTimeStatus(String realTimeStatus) {
		this.realTimeStatus = realTimeStatus;
	}

	public String getTmnlPaulPower() {
		return tmnlPaulPower;
	}

	public void setTmnlPaulPower(String tmnlPaulPower) {
		this.tmnlPaulPower = tmnlPaulPower;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getDataFetch() {
		return dataFetch;
	}

	public void setDataFetch(String dataFetch) {
		this.dataFetch = dataFetch;
	}

	public Short getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(Short totalNo) {
		this.totalNo = totalNo;
	}

	public Float getUseValue() {
		return useValue;
	}

	public void setUseValue(Float useValue) {
		this.useValue = useValue;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getRefreshFlag() {
		return refreshFlag;
	}

	public void setRefreshFlag(String refreshFlag) {
		this.refreshFlag = refreshFlag;
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

	public String getTerminalAddr() {
		return terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	public Integer getMeterId() {
		return meterId;
	}

	public void setMeterId(Integer meterId) {
		this.meterId = meterId;
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
