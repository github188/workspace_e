package com.nari.qrystat.querycollpoint;

import java.util.Date;

public class Cmeter {
	private String consNo;// 用户编号
	private String consName;// 客户名
	private String elecAddr;// 客户地址
	private Long meterId;// 电能表标识
	//private Long collObjId;// 采集对象标识
	private String assetNo;// 资产编号
	private String baudrate;// 波特率
	private String commNo;// 通讯规约
	private String commMode;// 通讯方式
	private String commAddr1;// 通讯地址1
	private String commAddr2;// 通讯地址2
	private String instLoc;// 安装位置
	private Date instDate;// 安装日期
	private Double tFactor;// 综合倍率
	private String tmnlAssetNo;// 终端资产编号
	private Byte regStatus;// 注册状态
	private Integer regSn;// 注册序号
	private String mpName;
	private String mpNo;
	

	public String getMpName() {
		return mpName;
	}

	public void setMpName(String mpName) {
		this.mpName = mpName;
	}

	public String getMpNo() {
		return mpNo;
	}

	public void setMpNo(String mpNo) {
		this.mpNo = mpNo;
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
	 * @return the meterId
	 */
	public Long getMeterId() {
		return meterId;
	}

	/**
	 * @param meterId
	 *            the meterId to set
	 */
	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

//	/**
//	 * @return the collObjId
//	 */
//	public Long getCollObjId() {
//		return collObjId;
//	}
//
//	/**
//	 * @param collObjId
//	 *            the collObjId to set
//	 */
//	public void setCollObjId(Long collObjId) {
//		this.collObjId = collObjId;
//	}

	/**
	 * @return the assetNo
	 */
	public String getAssetNo() {
		return assetNo;
	}

	/**
	 * @param assetNo
	 *            the assetNo to set
	 */
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
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
	 * @return the baudrate
	 */
	public String getBaudrate() {
		return baudrate;
	}

	/**
	 * @param baudrate
	 *            the baudrate to set
	 */
	public void setBaudrate(String baudrate) {
		this.baudrate = baudrate;
	}

	/**
	 * @return the commNo
	 */
	public String getCommNo() {
		return commNo;
	}

	/**
	 * @param commNo
	 *            the commNo to set
	 */
	public void setCommNo(String commNo) {
		this.commNo = commNo;
	}

	/**
	 * @return the commMode
	 */
	public String getCommMode() {
		return commMode;
	}

	/**
	 * @param commMode
	 *            the commMode to set
	 */
	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

	/**
	 * @return the commAddr1
	 */
	public String getCommAddr1() {
		return commAddr1;
	}

	/**
	 * @param commAddr1
	 *            the commAddr1 to set
	 */
	public void setCommAddr1(String commAddr1) {
		this.commAddr1 = commAddr1;
	}

	/**
	 * @return the commAddr2
	 */
	public String getCommAddr2() {
		return commAddr2;
	}

	/**
	 * @param commAddr2
	 *            the commAddr2 to set
	 */
	public void setCommAddr2(String commAddr2) {
		this.commAddr2 = commAddr2;
	}

	/**
	 * @return the instLoc
	 */
	public String getInstLoc() {
		return instLoc;
	}

	/**
	 * @param instLoc
	 *            the instLoc to set
	 */
	public void setInstLoc(String instLoc) {
		this.instLoc = instLoc;
	}

	/**
	 * @return the instDate
	 */
	public Date getInstDate() {
		return instDate;
	}

	/**
	 * @param instDate
	 *            the instDate to set
	 */
	public void setInstDate(Date instDate) {
		this.instDate = instDate;
	}

	/**
	 * @return the tFactor
	 */
	public Double gettFactor() {
		return tFactor;
	}

	/**
	 * @param tFactor
	 *            the tFactor to set
	 */
	public void settFactor(Double tFactor) {
		this.tFactor = tFactor;
	}

	/**
	 * @return the tmnlAssetNo
	 */
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	/**
	 * @param tmnlAssetNo
	 *            the tmnlAssetNo to set
	 */
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	/**
	 * @return the regStatus
	 */
	public Byte getRegStatus() {
		return regStatus;
	}

	/**
	 * @param regStatus
	 *            the regStatus to set
	 */
	public void setRegStatus(Byte regStatus) {
		this.regStatus = regStatus;
	}

	/**
	 * @return the regSn
	 */
	public Integer getRegSn() {
		return regSn;
	}

	/**
	 * @param regSn
	 *            the regSn to set
	 */
	public void setRegSn(Integer regSn) {
		this.regSn = regSn;
	}

}
