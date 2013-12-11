package com.nari.baseapp.datagathorman;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.baseapp.datagatherman.TaskStatInfoBean;
import com.nari.support.Page;

public class GatherTaskQueryAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	private GatherTaskQueryManager gatherTaskQueryManager;
	private boolean success = true; // 是否成功
	private long start = 0;
	private long limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;
	private Date startDate;
	private Date endDate;
	private int taskType;
	private String nodeType;
	private String nodeValue;
	private List<TaskStatInfoBean> rs;

	public void setGatherTaskQueryManager(
			GatherTaskQueryManager gatherTaskQueryManager) {
		this.gatherTaskQueryManager = gatherTaskQueryManager;
	}

	public String queryGatherTaskInfo() throws Exception {
		this.logger.debug("queryGatherTaskInfo 采集任务查询开始");
		//取得查询结果
		Page<TaskStatInfoBean> pageRs = this.gatherTaskQueryManager
				.queryGatherTaskStat(this.taskType, this.nodeType,
						this.nodeValue, this.startDate, this.endDate, this
								.getPSysUser(), start, limit);
		//取得分页结果
		this.setRs(pageRs.getResult());
		//取得总条数
		this.setTotalCount(pageRs.getTotalCount());
		this.logger.debug("queryGatherTaskInfo 采集任务查询结束");
		return SUCCESS;
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

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
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

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public List<TaskStatInfoBean> getRs() {
		return rs;
	}

	public void setRs(List<TaskStatInfoBean> rs) {
		this.rs = rs;
	}
}
