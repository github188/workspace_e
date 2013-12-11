package com.nari.runman.dutylog;

import java.util.Date;

/**
 * @author 陈建国
 *
 * @创建时间:2009-12-8 上午10:21:03
 *
 * @类描述:用户操作日志
 *	
 */
public class LUserOpLog {

	private String empNo;
	private String orgNo;
	private String menuNo;
	private String opType;
	private Date opTime;
	private String opModule;
	private String opContent;
	private String ipAddr;
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public String getOpModule() {
		return opModule;
	}
	public void setOpModule(String opModule) {
		this.opModule = opModule;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getOpContent() {
		return opContent;
	}
	public void setOpContent(String opContent) {
		this.opContent = opContent;
	}
	
}
