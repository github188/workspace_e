package com.nari.mydesk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;

/**
 * 
 * 监视主页每日电量统计
 * 
 * @author ChunMingLi
 * @since  2010-7-28
 *
 */
public class EnergyStatDAction extends BaseAction {
	private EnergyStatDManager energyStatDManager;
	
	/**
	 *  the energyStatDManager to set
	 * @param energyStatDManager the energyStatDManager to set
	 */
	public void setEnergyStatDManager(EnergyStatDManager energyStatDManager) {
		this.energyStatDManager = energyStatDManager;
	}
	//记录日志
	private  Logger logger = Logger.getLogger(EnergyStatDAction.class);
	private EnergyStatDDto energyStatDDto;
	private List<EnergyStatDDto> energyStatDList;
	private String orgNo;
	private String orgType;
	private String staffNo;
	private Date startDate;
	private Date endDate;
	private boolean success = true;
	/**
	 * 
	 * 查询主页每日电量统计
	 * @return success
	 */
	public String queryEnergyStatDay(){
		logger.debug("queryEnergyStatDay : 查询主页每日电量统计开始");
		// 获取操作员信息
		PSysUser pSysUser = (PSysUser)super.getSession().getAttribute("pSysUser");
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		String statTime = sdf.format(startDate);
		String endTime = sdf.format(endDate);
		try {
			setEnergyStatDList(energyStatDManager.queryEnergyStatDay(pSysUser, statTime, endTime));
		} catch (Exception e) {
			success = true;
			logger.error("查询主页每日电量统计 操作出错.  \n" + e);
		}
		logger.debug("queryEnergyStatDay : 查询主页每日电量统计结束");
		return SUCCESS;
		
	}
	/**
	 *  the energyStatDDto
	 * @return the energyStatDDto
	 */
	public EnergyStatDDto getEnergyStatDDto() {
		return energyStatDDto;
	}
	/**
	 *  the energyStatDDto to set
	 * @param energyStatDDto the energyStatDDto to set
	 */
	public void setEnergyStatDDto(EnergyStatDDto energyStatDDto) {
		this.energyStatDDto = energyStatDDto;
	}
	/**
	 *  the energyStatDList
	 * @return the energyStatDList
	 */
	public List<EnergyStatDDto> getEnergyStatDList() {
		return energyStatDList;
	}
	/**
	 *  the energyStatDList to set
	 * @param energyStatDList the energyStatDList to set
	 */
	public void setEnergyStatDList(List<EnergyStatDDto> energyStatDList) {
		this.energyStatDList = energyStatDList;
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
	/**
	 *  the startDate
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 *  the startDate to set
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 *  the endDate
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 *  the endDate to set
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
