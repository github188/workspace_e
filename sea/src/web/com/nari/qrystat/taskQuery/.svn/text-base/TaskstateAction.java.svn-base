package com.nari.qrystat.taskQuery;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;

/**
 * 
 * 工单查询统计action
 * 
 * @author ChunMingLi
 * @since 2010-5-8
 */
public class TaskstateAction extends BaseAction {
	//log初始化
	private static Logger logger = Logger.getLogger(TaskstateAction.class);
	
	private TaskStateManager taskStateManager;
	
	//dto集合
	private List<TaskStatDto> taskStatList;
	
	//typeDTO集合
	private List<TaskStatTypeDto> typeList;
	
	// 执行标识
	private boolean success = true;
	
	//分页页码
	private long start = 0;
	
	//每页显示数
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	
	//页面总数地区
	private long totalCount;
	
	//类型总数 typeCount
	private long typeCount;
	
	//查询开始时间
	private Date dateFrom;
	
	//查询结束时间
	private Date dateTo;
	
	/**
	 * Action 方法queryTaskStateByArea
	 * @return 查询工单明细 按地区
	 * @throws Exception
	 */
	public String queryTaskStateByArea()throws Exception{
		List<String> taskStatOrgNameList;
		
		//查询集合
		List<TaskStateArea> taskStateQryList;
		try {
			logger.debug("Method queryTaskStateByArea()start.");
			//供电单位集合
			taskStatOrgNameList = taskStateManager.findTaskOrgNo(dateFrom, dateTo);
			
//			this.setTaskStatOrgNameList(taskStateManager.findTaskOrgNo());
			taskStateQryList = taskStateManager.findTaskArea(dateFrom, dateTo);
			
			//获得dto集合
			this.setTaskStatList(taskStateManager.processTaskStateList(taskStatOrgNameList, taskStateQryList));
			//设置集合总数
			this.setTotalCount(taskStatList.size());
		} catch (Exception e) {
			logger.debug(e);
		}
		logger.debug("Method queryTaskStateByType()end.");
		return SUCCESS;
	}
	
	public String queryTaskStateByType()throws Exception{
		try {
			logger.debug("Method queryTaskStateByType() start.");
			this.setTypeList(this.taskStateManager.findTaskType(dateFrom, dateTo));
			//设置集合总数
			this.setTypeCount(this.typeList.size());
		} catch (Exception e) {
			logger.error(e);
		}
		logger.debug("Method queryTaskStateByType() end.");
		return SUCCESS;
	}

	public void setTaskStateManager(TaskStateManager taskStateManager) {
		this.taskStateManager = taskStateManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

//	public List<String> getTaskStatOrgNameList() {
//		return taskStatOrgNameList;
//	}
//
//	public void setTaskStatOrgNameList(List<String> taskStatOrgNameList) {
//		this.taskStatOrgNameList = taskStatOrgNameList;
//	}

	public List<TaskStatDto> getTaskStatList() {
		return taskStatList;
	}

	public void setTaskStatList(List<TaskStatDto> taskStatList) {
		this.taskStatList = taskStatList;
	}

	public List<TaskStatTypeDto> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<TaskStatTypeDto> typeList) {
		this.typeList = typeList;
	}

	public long getTypeCount() {
		return typeCount;
	}

	public void setTypeCount(long typeCount) {
		this.typeCount = typeCount;
	}

//	public List<TaskStateQry> getTaskStateQryList() {
//		return taskStateQryList;
//	}
//
//	public void setTaskStateQryList(List<TaskStateQry> taskStateQryList) {
//		this.taskStateQryList = taskStateQryList;
//	}

}
