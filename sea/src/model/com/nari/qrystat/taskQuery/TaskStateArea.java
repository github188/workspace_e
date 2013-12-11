package com.nari.qrystat.taskQuery;

/**
 * 
 * 工单查询统计DTO数据库返回对象类
 * 
 * @author ChunMingLi
 * 2010-4-28
 *
 */
public class TaskStateArea {

	// 供电单位编号
	private String orgNo;
	
	//供电单位名称
	private String orgName;
	
	//工单号
	private int eventID;
	
	/*
	 * 工单状态		
	 * 		00新异常、02营销处理中、03正常归档、04误报确认、06挂起、07本地处理中、08强制归档
	 */
	private String eventStatuCode;

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getEventStatuCode() {
		return eventStatuCode;
	}

	public void setEventStatuCode(String eventStatuCode) {
		this.eventStatuCode = eventStatuCode;
	}

}
