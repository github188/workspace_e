/**
 * 
 */
package com.nari.mydesk;

import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;

/**
 * 
 * 
 * @author ChunMingLi
 * @since 2010-8-20
 *
 */
public class MonthPowerStatAction extends BaseAction {
	/**
	 * 设置注入service
	 */
	private MonthPowerStatManager monthPowerStatManager;
	
	/**
	 *  the monthPowerStatManager to set
	 * @param monthPowerStatManager the monthPowerStatManager to set
	 */
	public void setMonthPowerStatManager(MonthPowerStatManager monthPowerStatManager) {
		this.monthPowerStatManager = monthPowerStatManager;
	}
	//记录日志
	private  Logger logger = Logger.getLogger(MonthPowerStatAction.class);
	private List<MonthPowerStatDto> upMonthPSList;
	private String staffNo;
	private String queryDate;
	private boolean success = true;
	
	/**
	 * 查询上月售电量统计
	 * @return success
	 * @throws Exception 
	 */
	public String queryMonthPowerStat() throws Exception{
		if(queryDate == null){
			return null;
		}
		PSysUser pSysUser = this.getPSysUser();
		staffNo = pSysUser.getStaffNo();
	    upMonthPSList = monthPowerStatManager.queryMonthPowerStat(pSysUser, queryDate);
		return SUCCESS;
	}
	/**
	 *  the upMonthPSList
	 * @return the upMonthPSList
	 */
	public List<MonthPowerStatDto> getUpMonthPSList() {
		return upMonthPSList;
	}
	/**
	 *  the upMonthPSList to set
	 * @param upMonthPSList the upMonthPSList to set
	 */
	public void setUpMonthPSList(List<MonthPowerStatDto> upMonthPSList) {
		this.upMonthPSList = upMonthPSList;
	}
	/**
	 *  the staffNo
	 * @return the staffNo
	 */
	public String getStaffNo() {
		return staffNo;
	}
	/**
	 *  the staffNo to set
	 * @param staffNo the staffNo to set
	 */
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
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
