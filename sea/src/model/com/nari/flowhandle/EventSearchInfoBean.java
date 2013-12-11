package com.nari.flowhandle;

import java.util.Date;

public class EventSearchInfoBean {
	private Long eventId;
	private Date startTime;
	private Date endTime;
	private Byte eventType;
	private String flowStatusCode;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
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
