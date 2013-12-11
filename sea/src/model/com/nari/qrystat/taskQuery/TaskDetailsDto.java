package com.nari.qrystat.taskQuery;

import java.util.Date;

/**
 * 
 * 工单明细查询DTO
 * 
 * @author ChunMingLi 2010-5-13
 * 
 */
public class TaskDetailsDto {
	//工单号
	private Long eventID;
	
	// 供电单位编号
	private String orgNo;
	
	//供电单位名称
	private String orgName;

	//发生时间
	private Date evnetTime;
	
	//工号
	private String staffNo;
	
	//来源ID
	private String fromId;
	
	//业务类型
	private Byte eventType;
	
	//流程状态
	private String flowStatusCode;
	
	//业务编码
	private String eventCode;
	
	//业务名称
	private String eventName;
	
	//附加信息
	private String eventData;

	public Long getEventID() {
		return eventID;
	}

	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getEvnetTime() {
		return evnetTime;
	}

	public void setEvnetTime(Date evnetTime) {
		this.evnetTime = evnetTime;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
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

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventData() {
		return eventData;
	}

	public void setEventData(String eventData) {
		this.eventData = eventData;
	}

}
