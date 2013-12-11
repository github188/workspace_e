package com.nari.basicdata;

/**
 * BTrade entity. @author MyEclipse Persistence Tools
 */

public class BTrade implements java.io.Serializable {

	// Fields

	private String tradeNo;
	private String PTradeNo;
	private String tradeName;

	// Constructors

	/** default constructor */
	public BTrade() {
	}

	/** full constructor */
	public BTrade(String PTradeNo, String tradeName) {
		this.PTradeNo = PTradeNo;
		this.tradeName = tradeName;
	}

	// Property accessors

	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPTradeNo() {
		return this.PTradeNo;
	}

	public void setPTradeNo(String PTradeNo) {
		this.PTradeNo = PTradeNo;
	}

	public String getTradeName() {
		return this.tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

}