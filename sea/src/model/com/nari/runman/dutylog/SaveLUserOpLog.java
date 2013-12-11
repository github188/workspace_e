package com.nari.runman.dutylog;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author huangxuan
 *
 *
 *	
 */
@Table(name="l_user_op_log")
public class SaveLUserOpLog {
	@Column(name="EMP_NO")
	private String empNo;
	@Column(name="ORG_NO")
	private String orgNo;
	@Column(name="OP_TYPE")
	private String opType;
	@Column(name="OP_TIME")
	private Date opTime;
	@Column(name="OP_MODULE")
	private String opModule;
	@Column(name="OP_CONTENT")
	private String opContent;
	@Column(name="OP_BUTTON")
	private String opButton;
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
	public String getOpContent() {
		return opContent;
	}
	public void setOpContent(String opContent) {
		this.opContent = opContent;
	}
	public String getOpButton() {
		return opButton;
	}
	public void setOpButton(String opButton) {
		this.opButton = opButton;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	@Column(name="IP_ADDR")
	private String ipAddr;
	
}
