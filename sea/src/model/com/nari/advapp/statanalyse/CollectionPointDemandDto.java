package com.nari.advapp.statanalyse;

import java.util.Date;

/**
 * 
 * 测量点需量分析DTO
 * 
 * @author ChunMingLi
 * @since  2010-7-27
 *
 */
public class CollectionPointDemandDto {
	private String orgNo;
	private String orgName;
	private String consNo;
	private String consName;
	private String tmnlAssetNo;
	private String assetNo;
	private String terminalAddr;
	private Date dataTime;  //数据日期
	private Date colTime;   //终端抄表时间
	private double multiple;    //ct*pt 综合倍率
	private String contractCap;    //合同容量
	private String mark;  //补全标记
	private String papDemand;
	private Date  papDemandTime;
	private String prpDemand;
	private Date  prpDemandTime;
	
	private String rapDemand;
	private Date  rapDemandTime;
	
	private String rrpDemand;
	private Date  rrpDemandTime;
	
	/**
	 *  the orgNo
	 * @return the orgNo
	 */
	public String getOrgNo() {
		return orgNo;
	}
	/**
	 *  the orgNo to set
	 * @param orgNo the orgNo to set
	 */
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	/**
	 *  the orgName
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 *  the orgName to set
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 *  the consNo
	 * @return the consNo
	 */
	public String getConsNo() {
		return consNo;
	}
	/**
	 *  the consNo to set
	 * @param consNo the consNo to set
	 */
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	/**
	 *  the consName
	 * @return the consName
	 */
	public String getConsName() {
		return consName;
	}
	/**
	 *  the consName to set
	 * @param consName the consName to set
	 */
	public void setConsName(String consName) {
		this.consName = consName;
	}
	/**
	 *  the tmnlAssetNo
	 * @return the tmnlAssetNo
	 */
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	/**
	 *  the tmnlAssetNo to set
	 * @param tmnlAssetNo the tmnlAssetNo to set
	 */
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	/**
	 *  the dataTime
	 * @return the dataTime
	 */
	public Date getDataTime() {
		return dataTime;
	}
	/**
	 *  the dataTime to set
	 * @param dataTime the dataTime to set
	 */
	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
	/**
	 *  the colTime
	 * @return the colTime
	 */
	public Date getColTime() {
		return colTime;
	}
	/**
	 *  the colTime to set
	 * @param colTime the colTime to set
	 */
	public void setColTime(Date colTime) {
		this.colTime = colTime;
	}
	/**
	 *  the multiple
	 * @return the multiple
	 */
	public double getMultiple() {
		return multiple;
	}
	/**
	 *  the multiple to set
	 * @param multiple the multiple to set
	 */
	public void setMultiple(double multiple) {
		this.multiple = multiple;
	}
	/**
	 *  the contractCap
	 * @return the contractCap
	 */
	public String getContractCap() {
		return contractCap;
	}
	/**
	 *  the contractCap to set
	 * @param contractCap the contractCap to set
	 */
	public void setContractCap(String contractCap) {
		this.contractCap = contractCap;
	}
	/**
	 *  the mark
	 * @return the mark
	 */
	public String getMark() {
		return mark;
	}
	/**
	 *  the mark to set
	 * @param mark the mark to set
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}
	/**
	 *  the papDemand
	 * @return the papDemand
	 */
	public String getPapDemand() {
		return papDemand;
	}
	/**
	 *  the papDemand to set
	 * @param papDemand the papDemand to set
	 */
	public void setPapDemand(String papDemand) {
		this.papDemand = papDemand;
	}
	/**
	 *  the papDemandTime
	 * @return the papDemandTime
	 */
	public Date getPapDemandTime() {
		return papDemandTime;
	}
	/**
	 *  the papDemandTime to set
	 * @param papDemandTime the papDemandTime to set
	 */
	public void setPapDemandTime(Date papDemandTime) {
		this.papDemandTime = papDemandTime;
	}
	/**
	 *  the prpDemand
	 * @return the prpDemand
	 */
	public String getPrpDemand() {
		return prpDemand;
	}
	/**
	 *  the prpDemand to set
	 * @param prpDemand the prpDemand to set
	 */
	public void setPrpDemand(String prpDemand) {
		this.prpDemand = prpDemand;
	}
	/**
	 *  the prpDemandTime
	 * @return the prpDemandTime
	 */
	public Date getPrpDemandTime() {
		return prpDemandTime;
	}
	/**
	 *  the prpDemandTime to set
	 * @param prpDemandTime the prpDemandTime to set
	 */
	public void setPrpDemandTime(Date prpDemandTime) {
		this.prpDemandTime = prpDemandTime;
	}
	/**
	 *  the rapDemand
	 * @return the rapDemand
	 */
	public String getRapDemand() {
		return rapDemand;
	}
	/**
	 *  the rapDemand to set
	 * @param rapDemand the rapDemand to set
	 */
	public void setRapDemand(String rapDemand) {
		this.rapDemand = rapDemand;
	}
	/**
	 *  the rapDemandTime
	 * @return the rapDemandTime
	 */
	public Date getRapDemandTime() {
		return rapDemandTime;
	}
	/**
	 *  the rapDemandTime to set
	 * @param rapDemandTime the rapDemandTime to set
	 */
	public void setRapDemandTime(Date rapDemandTime) {
		this.rapDemandTime = rapDemandTime;
	}
	/**
	 *  the rrpDemand
	 * @return the rrpDemand
	 */
	public String getRrpDemand() {
		return rrpDemand;
	}
	/**
	 *  the rrpDemand to set
	 * @param rrpDemand the rrpDemand to set
	 */
	public void setRrpDemand(String rrpDemand) {
		this.rrpDemand = rrpDemand;
	}
	/**
	 *  the rrpDemandTime
	 * @return the rrpDemandTime
	 */
	public Date getRrpDemandTime() {
		return rrpDemandTime;
	}
	/**
	 *  the rrpDemandTime to set
	 * @param rrpDemandTime the rrpDemandTime to set
	 */
	public void setRrpDemandTime(Date rrpDemandTime) {
		this.rrpDemandTime = rrpDemandTime;
	}
	/**
	 *  the assetNo
	 * @return the assetNo
	 */
	public String getAssetNo() {
		return assetNo;
	}
	/**
	 *  the assetNo to set
	 * @param assetNo the assetNo to set
	 */
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	/**
	 *  the terminalAddr
	 * @return the terminalAddr
	 */
	public String getTerminalAddr() {
		return terminalAddr;
	}
	/**
	 *  the terminalAddr to set
	 * @param terminalAddr the terminalAddr to set
	 */
	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}
}
