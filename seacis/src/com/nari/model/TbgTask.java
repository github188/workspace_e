package com.nari.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*****
 * 后台任务
 * 
 * @author huangxuan *
 ****/
@Table(name = "t_bg_task")
public class TbgTask {
	@Id
	@GeneratedValue(generator = "s_t_bg_task")
	@Column(name = "task_id")
	private Integer taskId;
	@Column(name = "task_time")
	// 键的生成方式
	@GeneratedValue(generator = "sysdate")
	private Date taskTime;
	@Column(name = "staff_no")
	private String staffNo;
	@Column(name = "org_no")
	private String orgNo;
	@Column(name = "tmnl_asset_no")
	private String tmnlAssetNo;
	@Column(name = "obj_type")
	private Integer objType;
	@Column(name = "obj_list")
	private String objList;
	@Column(name = "data_item_cnt")
	private Integer dataItemCnt;
	@Column(name = "task_type")
	private Integer taskType;
	@Column(name = "task_name")
	private String taskName;
	@Column(name = "rw_flag")
	private Integer rwFlag;
	@Column(name = "max_try")
	private Integer maxTry;
	@Column(name = "send_times")
	private Integer sendTimes;
	@Column(name = "send_time")
	private Date sendTime;
	@Column(name = "next_send_time")
	private Date nextSendTime;
	@Column(name = "task_status")
	private String taskStatus;
	@Column(name = "start_time")
	private Date startTime;
	@Column(name = "end_time")
	private Date endTime;

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

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Date getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public Integer getObjType() {
		return objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public String getObjList() {
		return objList;
	}

	public void setObjList(String objList) {
		this.objList = objList;
	}

	public Integer getDataItemCnt() {
		return dataItemCnt;
	}

	public void setDataItemCnt(Integer dataItemCnt) {
		this.dataItemCnt = dataItemCnt;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getRwFlag() {
		return rwFlag;
	}

	public void setRwFlag(Integer rwFlag) {
		this.rwFlag = rwFlag;
	}

	public Integer getMaxTry() {
		return maxTry;
	}

	public void setMaxTry(Integer maxTry) {
		this.maxTry = maxTry;
	}

	public Integer getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getNextSendTime() {
		return nextSendTime;
	}

	public void setNextSendTime(Date nextSendTime) {
		this.nextSendTime = nextSendTime;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
}
