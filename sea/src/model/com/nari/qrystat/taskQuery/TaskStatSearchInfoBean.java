package com.nari.qrystat.taskQuery;

import java.util.Date;

public class TaskStatSearchInfoBean {
	//工单号
	private String orgNo;
	//开始时间
	private Date startTime;
	//结束时间
	private Date endTime;
	//查询类型
	private Byte eventType;
	//流程状态
	private String flowStatusCode;

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Byte getEventType() {
		return eventType;
	}

	public void setEventType(Byte eventType) {
		this.eventType = eventType;
	}

	public String getFlowStatusCode() {
		return flowStatusCode;
	}

	public void setFlowStatusCode(String flowStatusCode) {
		this.flowStatusCode = flowStatusCode;
	}
}
