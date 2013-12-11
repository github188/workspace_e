package com.nari.baseapp.prepaidman;

import java.util.Date;

/**
 * 预付费投入调试费控流程表数据值对象
 * @author 姜炜超
 */
public class PPDebugWFeectrlFlowBean {
	private String appNo;
	private String terminalId;
	private Long meterId;
	private Boolean firstFlag;
	private String flowNoCode;
	private String flowNodeStatus;
	private Date opTime;
	private String errCause;
	
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public Long getMeterId() {
		return meterId;
	}
	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}
	public Boolean getFirstFlag() {
		return firstFlag;
	}
	public void setFirstFlag(Boolean firstFlag) {
		this.firstFlag = firstFlag;
	}
	public String getFlowNoCode() {
		return flowNoCode;
	}
	public void setFlowNoCode(String flowNoCode) {
		this.flowNoCode = flowNoCode;
	}
	public String getFlowNodeStatus() {
		return flowNodeStatus;
	}
	public void setFlowNodeStatus(String flowNodeStatus) {
		this.flowNodeStatus = flowNodeStatus;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public String getErrCause() {
		return errCause;
	}
	public void setErrCause(String errCause) {
		this.errCause = errCause;
	}
}
