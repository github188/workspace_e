package com.nari.baseapp.datagatherman;

import com.nari.basicdata.BCommProtocol;
import com.nari.basicdata.BTmnlFactory;
import com.nari.customer.CCons;
import com.nari.orgnization.OOrg;

public class DataFetchTerminal {
	private String terminalId; // 终端ID
	private OOrg oOrg; // 供电单位
	private CCons cCons; // 用户
	private String terminalAddr; // 终端地址
	private BCommProtocol bCommProtocol; // 通信规约
	private BTmnlFactory bTmnlFactory; // 制造厂商

	public String getTerminalId() {
		return this.terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public OOrg getoOrg() {
		return this.oOrg;
	}
	public void setoOrg(OOrg oOrg) {
		this.oOrg = oOrg;
	}
	public CCons getcCons() {
		return this.cCons;
	}
	public void setcCons(CCons cCons) {
		this.cCons = cCons;
	}
	public String getTerminalAddr() {
		return this.terminalAddr;
	}
	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}
	public BCommProtocol getbCommProtocol() {
		return this.bCommProtocol;
	}
	public void setbCommProtocol(BCommProtocol bCommProtocol) {
		this.bCommProtocol = bCommProtocol;
	}
	public BTmnlFactory getbTmnlFactory() {
		return this.bTmnlFactory;
	}
	public void setbTmnlFactory(BTmnlFactory bTmnlFactory) {
		this.bTmnlFactory = bTmnlFactory;
	}

}
