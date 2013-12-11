package com.nari.statreport;

import java.util.Date;

/**
 * 
 * 电能表异常每日统计
 * 
 * @author ChunMingLi
 * @since  2010-7-14
 *
 */
public class AEventStatDWindowDto {
	private Date statDate;
	private String eventCnt;
	private String orgNo;
	private String eventNo;
	private String orgName;
	private String orgType;
	private String levelNo;
	

	/**
	 *  the orgType
	 * @return the orgType
	 */
	public String getOrgType() {
		return orgType;
	}
	/**
	 *  the orgType to set
	 * @param orgType the orgType to set
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	/**
	 *  the statDate
	 * @return the statDate
	 */
	public Date getStatDate() {
		return statDate;
	}
	/**
	 *  the statDate to set
	 * @param statDate the statDate to set
	 */
	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}
	/**
	 *  the eventCnt
	 * @return the eventCnt
	 */
	public String getEventCnt() {
		return eventCnt;
	}
	/**
	 *  the eventCnt to set
	 * @param eventCnt the eventCnt to set
	 */
	public void setEventCnt(String eventCnt) {
		this.eventCnt = eventCnt;
	}
	/**
	 *  the orgNo
	 * @return the orgNo
	 */
	public String getOrgNo() {
		return orgNo;
	}
	/**
	 *  the orgNo to set
	 * @param orgNo the orgNo to set
	 */
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	/**
	 *  the eventNo
	 * @return the eventNo
	 */
	public String getEventNo() {
		return eventNo;
	}
	/**
	 *  the eventNo to set
	 * @param eventNo the eventNo to set
	 */
	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}
	/**
	 *  the orgName
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 *  the orgName to set
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 *  the levelNo
	 * @return the levelNo
	 */
	public String getLevelNo() {
		return levelNo;
	}
	/**
	 *  the levelNo to set
	 * @param levelNo the levelNo to set
	 */
	public void setLevelNo(String levelNo) {
		this.levelNo = levelNo;
	}
	
}
