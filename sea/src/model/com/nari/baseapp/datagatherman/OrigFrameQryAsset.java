package com.nari.baseapp.datagatherman;
/**
 * 原始报文查询终端资产
 * @author zhaoliang
 *
 */
public class OrigFrameQryAsset {
	private   String terminalAddr;
	private   String consNo;
	private String protocolCode;
	private String tmnlAssetNo;
	
	
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
	public String getTerminalAddr() {
		return terminalAddr;
	}
	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}
	public String getConsNo() {
		return consNo;
	}
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	
}
