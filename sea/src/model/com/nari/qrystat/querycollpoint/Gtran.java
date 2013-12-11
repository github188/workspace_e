package com.nari.qrystat.querycollpoint;

public class Gtran {
	private Long consId;//用户标识
	private String orgNo;//变压器管理单位 
	private String tranName;//设备的名称
	private Double plateCap;//铭牌容量
	private String typeCode;//设备类型
	private String instAddr;//安装地址
	private String instDate;//安装日期
	private String chgRemark;//变更说明
	private String runStatusCode;//运行状态
	private String pubPrivFlag;//公/专变标致
	private String madeDate;//出厂日期
	private String modelNo;//设备型号
	private String factoryName;//厂家名称
	private String madeNo;//出厂编号
	private String protectMode;//保护方式
	private String frstsideVoltCode;//一次侧电压
	private String sndsideVoltCode;//二次侧电压
	private String prCode;//产权
	private Double equipId;//设备标识 
	private String tsAlgFlag;
	private float kValue;


	public Double getEquipId() {
		return equipId;
	}
	public void setEquipId(Double equipId) {
		this.equipId = equipId;
	}
	public String getPrCode() {
		return prCode;
	}
	public void setPrCode(String prCode) {
		this.prCode = prCode;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public Long getConsId() {
		return consId;
	}
	public void setConsId(Long consId) {
		this.consId = consId;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getTranName() {
		return tranName;
	}

	public void setTranName(String tranName) {
		this.tranName = tranName;
	}

	public Double getPlateCap() {
		return plateCap;
	}

	public void setPlateCap(Double plateCap) {
		this.plateCap = plateCap;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getInstAddr() {
		return instAddr;
	}

	public void setInstAddr(String instAddr) {
		this.instAddr = instAddr;
	}



	public String getChgRemark() {
		return chgRemark;
	}

	public void setChgRemark(String chgRemark) {
		this.chgRemark = chgRemark;
	}

	public String getRunStatusCode() {
		return runStatusCode;
	}

	public void setRunStatusCode(String runStatusCode) {
		this.runStatusCode = runStatusCode;
	}

	public String getPubPrivFlag() {
		return pubPrivFlag;
	}

	public void setPubPrivFlag(String pubPrivFlag) {
		this.pubPrivFlag = pubPrivFlag;
	}

	public String getInstDate() {
		return instDate;
	}
	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}
	public String getMadeDate() {
		return madeDate;
	}
	public void setMadeDate(String madeDate) {
		this.madeDate = madeDate;
	}
	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getMadeNo() {
		return madeNo;
	}

	public void setMadeNo(String madeNo) {
		this.madeNo = madeNo;
	}

	public String getProtectMode() {
		return protectMode;
	}

	public void setProtectMode(String protectMode) {
		this.protectMode = protectMode;
	}

	public String getFrstsideVoltCode() {
		return frstsideVoltCode;
	}

	public void setFrstsideVoltCode(String frstsideVoltCode) {
		this.frstsideVoltCode = frstsideVoltCode;
	}

	public String getSndsideVoltCode() {
		return sndsideVoltCode;
	}

	public void setSndsideVoltCode(String sndsideVoltCode) {
		this.sndsideVoltCode = sndsideVoltCode;
	}
	public String getTsAlgFlag() {
		return tsAlgFlag;
	}
	public void setTsAlgFlag(String tsAlgFlag) {
		this.tsAlgFlag = tsAlgFlag;
	}
	public float getkValue() {
		return kValue;
	}
	public void setkValue(float kValue) {
		this.kValue = kValue;
	}
	

}
