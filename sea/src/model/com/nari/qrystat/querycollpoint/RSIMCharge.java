package com.nari.qrystat.querycollpoint;

public class RSIMCharge {
	private   Long mrSimChargeId;
	private   String simNo;//SIM卡号
	private   String gprsCode;//GPRS号码 
	private   String chargeDate;//费用日期
	private   Long overrunFlux;//超流量
	private   Long actualFlux;//实际流量
	private Double commCharge;//通讯费[元]
	/**
	 * @return the mrSimChargeId
	 */
	public Long getMrSimChargeId() {
		return mrSimChargeId;
	}
	/**
	 * @param mrSimChargeId the mrSimChargeId to set
	 */
	public void setMrSimChargeId(Long mrSimChargeId) {
		this.mrSimChargeId = mrSimChargeId;
	}
	/**
	 * @return the simNo
	 */
	public String getSimNo() {
		return simNo;
	}
	/**
	 * @param simNo the simNo to set
	 */
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}
	/**
	 * @return the gprsCode
	 */
	public String getGprsCode() {
		return gprsCode;
	}
	/**
	 * @param gprsCode the gprsCode to set
	 */
	public void setGprsCode(String gprsCode) {
		this.gprsCode = gprsCode;
	}
	/**
	 * @return the chargeDate
	 */
	public String getChargeDate() {
		return chargeDate;
	}
	/**
	 * @param chargeDate the chargeDate to set
	 */
	public void setChargeDate(String chargeDate) {
		this.chargeDate = chargeDate;
	}
	/**
	 * @return the overrunFlux
	 */
	public Long getOverrunFlux() {
		return overrunFlux;
	}
	/**
	 * @param overrunFlux the overrunFlux to set
	 */
	public void setOverrunFlux(Long overrunFlux) {
		this.overrunFlux = overrunFlux;
	}
	/**
	 * @return the actualFlux
	 */
	public Long getActualFlux() {
		return actualFlux;
	}
	/**
	 * @param actualFlux the actualFlux to set
	 */
	public void setActualFlux(Long actualFlux) {
		this.actualFlux = actualFlux;
	}
	/**
	 * @return the commCharge
	 */
	public Double getCommCharge() {
		return commCharge;
	}
	/**
	 * @param commCharge the commCharge to set
	 */
	public void setCommCharge(Double commCharge) {
		this.commCharge = commCharge;
	}
	
}
