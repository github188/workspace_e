package com.nari.baseapp.datagatherman;
/**
 * 采集失败终端
 * @author chenjg
 *
 */
public class MissingTerminalDTO {

	private String orgName;
	private String consNo;
	private String consName;
	private String TerminalAddr;
	private int isOnline;
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public String getTerminalAddr() {
		return TerminalAddr;
	}
	public void setTerminalAddr(String terminalAddr) {
		TerminalAddr = terminalAddr;
	}
	public int getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}
	
}
