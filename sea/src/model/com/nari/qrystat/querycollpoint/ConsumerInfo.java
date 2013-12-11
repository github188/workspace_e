package com.nari.qrystat.querycollpoint;

import java.io.Serializable;

public class ConsumerInfo implements Serializable {
	private static final long serialVersionUID = -6658412540327842004L;
	private String consNo;
	private String consName;
	private String elecAddr;
	private String custNo;
	private Double contractCap;
	private Double runCap;
	private String tradeName;
	private String orgName;
	private String volt;
	private String elecType;
	private String consTypeName;
	private String subsName;
	private String lineName;
	private String cpNo;
	/**
	 * @return the subsName
	 */
	
	public String getSubsName() {
		return subsName;
	}

	/**
	 * @return the tradeName
	 */
	public String getTradeName() {
		return tradeName;
	}

	/**
	 * @param tradeName the tradeName to set
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	/**
	 * @param subsName the subsName to set
	 */
	public void setSubsName(String subsName) {
		this.subsName = subsName;
	}

	/**
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * @param lineName the lineName to set
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * @return the consNo
	 */
	public String getConsNo() {
		return consNo;
	}

	/**
	 * @param consNo
	 *            the consNo to set
	 */
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	/**
	 * @return the consName
	 */
	public String getConsName() {
		return consName;
	}

	/**
	 * @param consName
	 *            the consName to set
	 */
	public void setConsName(String consName) {
		this.consName = consName;
	}

	/**
	 * @return the elecAddr
	 */
	public String getElecAddr() {
		return elecAddr;
	}

	/**
	 * @param elecAddr
	 *            the elecAddr to set
	 */
	public void setElecAddr(String elecAddr) {
		this.elecAddr = elecAddr;
	}

	/**
	 * @return the custNo
	 */
	public String getCustNo() {
		return custNo;
	}

	/**
	 * @param custNo
	 *            the custNo to set
	 */
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	/**
	 * @return the contractCap
	 */
	public Double getContractCap() {
		return contractCap;
	}

	/**
	 * @param contractCap
	 *            the contractCap to set
	 */
	public void setContractCap(Double contractCap) {
		this.contractCap = contractCap;
	}

	/**
	 * @return the runCap
	 */
	public Double getRunCap() {
		return runCap;
	}

	/**
	 * @param runCap
	 *            the runCap to set
	 */
	public void setRunCap(Double runCap) {
		this.runCap = runCap;
	}


	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 *            the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the volt
	 */
	public String getVolt() {
		return volt;
	}

	/**
	 * @param volt
	 *            the volt to set
	 */
	public void setVolt(String volt) {
		this.volt = volt;
	}

	/**
	 * @return the elecType
	 */
	public String getElecType() {
		return elecType;
	}

	/**
	 * @param elecType
	 *            the elecType to set
	 */
	public void setElecType(String elecType) {
		this.elecType = elecType;
	}

	public String getConsTypeName() {
		return consTypeName;
	}

	public void setConsTypeName(String consTypeName) {
		this.consTypeName = consTypeName;
	}

	public String getCpNo() {
		return cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}
	



}
