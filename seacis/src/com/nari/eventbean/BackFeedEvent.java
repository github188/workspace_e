package com.nari.eventbean;

/**
 * 反馈营销
 */
public class BackFeedEvent {
	
	/* 申请编号 */
	private String appNo;

	/* 调试标识 */
	private Long debugId;

	public BackFeedEvent(String appNo, Long debugId) {
		super();
		this.appNo = appNo;
		this.debugId = debugId;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	
	/**
	 * @return the debugId
	 */
	public Long getDebugId() {
		return debugId;
	}

	/**
	 * @param debugId the debugId to set
	 */
	public void setDebugId(Long debugId) {
		this.debugId = debugId;
	}
}