/**
 * 
 */
package com.nari.mydesk;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;

/**
 * 
 * 线损统计
 * @author ChunMingLi
 * @since 2010-8-18
 *
 */
public class LosePowerStatAnalyseAction extends BaseAction {
	private LosePowerStatAnalyseManager losePowerStatAnalyseManager;
	
	/**
	 *  the losePowerStatAnalyseManager to set
	 * @param losePowerStatAnalyseManager the losePowerStatAnalyseManager to set
	 */
	public void setLosePowerStatAnalyseManager(
			LosePowerStatAnalyseManager losePowerStatAnalyseManager) {
		this.losePowerStatAnalyseManager = losePowerStatAnalyseManager;
	}
	//记录日志
	private  Logger logger = Logger.getLogger(LosePowerStatAnalyseAction.class);
	//台区集合
	private List<LosePowerStatAnalyseDto> losePowerStatATGList;
	//线路集合
	private List<LosePowerStatAnalyseDto> losePowerStatALineList;
	private long totalCount;
	private long start = 0;
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	private String orgNo;
	private String orgType;
	//查询时间
	private String queryDate;
	private boolean success = true;
	
	/**
	 * 按照台区查询线损率
	 * @return
	 */
	public String queryTGLosePowerStatA()throws Exception{
		if(queryDate == null){
			return null;
		}
		PSysUser pSysUser = getPSysUser();
		losePowerStatATGList = losePowerStatAnalyseManager.queryTGLosePowerStatA(queryDate, orgNo, orgType, pSysUser);
		Collections.sort(losePowerStatATGList);
		return SUCCESS;
		
	}
	
	/**
	 * 按照线路查询线损率
	 * @return
	 */
	public String queryLineLosePowerStatA()throws Exception{
		if(queryDate == null){
			return null;
		}
		PSysUser pSysUser = getPSysUser();
		losePowerStatALineList = losePowerStatAnalyseManager.queryLineLosePowerStatA(queryDate, orgNo, orgType, pSysUser);
		Collections.sort(losePowerStatALineList);
		return SUCCESS;
		
	}
	
	/**
	 *  the losePowerStatATGList
	 * @return the losePowerStatATGList
	 */
	public List<LosePowerStatAnalyseDto> getLosePowerStatATGList() {
		return losePowerStatATGList;
	}

	/**
	 *  the losePowerStatATGList to set
	 * @param losePowerStatATGList the losePowerStatATGList to set
	 */
	public void setLosePowerStatATGList(
			List<LosePowerStatAnalyseDto> losePowerStatATGList) {
		this.losePowerStatATGList = losePowerStatATGList;
	}

	/**
	 *  the losePowerStatALineList
	 * @return the losePowerStatALineList
	 */
	public List<LosePowerStatAnalyseDto> getLosePowerStatALineList() {
		return losePowerStatALineList;
	}

	/**
	 *  the losePowerStatALineList to set
	 * @param losePowerStatALineList the losePowerStatALineList to set
	 */
	public void setLosePowerStatALineList(
			List<LosePowerStatAnalyseDto> losePowerStatALineList) {
		this.losePowerStatALineList = losePowerStatALineList;
	}

	/**
	 *  the totalCount
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}
	/**
	 *  the totalCount to set
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 *  the start
	 * @return the start
	 */
	public long getStart() {
		return start;
	}
	/**
	 *  the start to set
	 * @param start the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}
	/**
	 *  the limit
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}
	/**
	 *  the limit to set
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
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
	 *  the queryDate
	 * @return the queryDate
	 */
	public String getQueryDate() {
		return queryDate;
	}

	/**
	 *  the queryDate to set
	 * @param queryDate the queryDate to set
	 */
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	/**
	 *  the success
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 *  the success to set
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
