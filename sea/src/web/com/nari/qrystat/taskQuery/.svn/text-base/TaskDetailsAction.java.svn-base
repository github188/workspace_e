package com.nari.qrystat.taskQuery;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.support.Page;

/**
 * 
 * 工单明细列表Action
 * @author ChunMingLi
 * @since 2010-5-13
 *
 */
public class TaskDetailsAction extends BaseAction {
	//log初始化
	private static Logger logger = Logger.getLogger(TaskDetailsAction.class);
	
	//dto集合
	private List<TaskDetailsDto> eventInfoList;
	
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
	private Date startDate;
	
	//查询结束时间
	private Date endDate;
	
	// 工单状态
	private String eventStatus;
	
	// 异常类型
	private Byte eventType;
	
	// 本次操作人
	private String orgNo;
	
	// 工单号
	private long eventId;
	
	// 处理说明
	private String eventData;
	
	// 注入service层实例
	TaskDetailsManager detailsManager;
	
	public void setDetailsManager(TaskDetailsManager detailsManager) {
		this.detailsManager = detailsManager;
	}


	/**
	 * 
	 * 查询工单明细
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String searchEventDetailsInfo() throws Exception{
		
		logger.debug("查询工单明细开始");
		// 工单查询条件
		TaskStatSearchInfoBean searchInfoBean = new TaskStatSearchInfoBean();
		searchInfoBean.setStartTime(this.startDate);
		searchInfoBean.setEndTime(this.endDate);
		// 异常类型输入时检索条件包含异常类型
		if (null != this.eventType && !"".equals(this.eventType)) {
			searchInfoBean.setEventType(this.eventType);
		}
		// 工单状态输入时检索条件包含工单状态
		if (null != this.eventStatus && !"".equals(this.eventStatus)) {
			searchInfoBean.setFlowStatusCode(this.eventStatus);
		}
		// 工单状态输入时检索条件包含工单状态
		if (null != this.orgNo && !"".equals(this.orgNo)) {
			searchInfoBean.setOrgNo(this.orgNo);
		}
		// 检索工单信息
		Page<TaskDetailsDto> eventPageInfo = this.detailsManager.getEventInfo(searchInfoBean, start, limit);
		// 设置总条数
		this.setTotalCount(eventPageInfo.getTotalCount());
		// 设置查询结果
		this.setEventInfoList(eventPageInfo.getResult());
		logger.debug("查询工单明细结束");
		return SUCCESS;
	}


	public List<TaskDetailsDto> getEventInfoList() {
		return eventInfoList;
	}


	public void setEventInfoList(List<TaskDetailsDto> eventInfoList) {
		this.eventInfoList = eventInfoList;
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

	public long getTypeCount() {
		return typeCount;
	}

	public void setTypeCount(long typeCount) {
		this.typeCount = typeCount;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public Byte getEventType() {
		return eventType;
	}

	public void setEventType(Byte eventType) {
		this.eventType = eventType;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}


	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getEventData() {
		return eventData;
	}

	public void setEventData(String eventData) {
		this.eventData = eventData;
	}
}
