package com.nari.baseapp.datagatherman;

import java.io.Serializable;

public class TaskStatInfoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2699601680381092445L;
	// 任务ID
	private Long taskId;
	// 任务类型
	private Long taskType;
	// 成功次数
	private Long sucessCount;
	// 失败次数
	private Long failueCount;
	// 终端资产号
	private String cisAssetNo;
	// 每天应执行次数
	private Short masterR;
	// 任务编号
	private Short taskNo;
	// 任务名称
	private String templateName;
	// 用户名称
	private String consName;
	// 用户编号
	private String consNo;
	// 供电单位名称
	private String orgName;

	public TaskStatInfoBean(Long taskId, Long taskType, Long sucessCount,
			Long failueCount, String cisAssetNo, Short masterR, Short taskNo,
			String templateName, String consName, String consNo, String orgName) {
		super();
		this.taskId = taskId;
		this.taskType = taskType;
		this.sucessCount = sucessCount;
		this.failueCount = failueCount;
		this.cisAssetNo = cisAssetNo;
		this.masterR = masterR;
		this.taskNo = taskNo;
		this.templateName = templateName;
		this.consName = consName;
		this.consNo = consNo;
		this.orgName = orgName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cisAssetNo == null) ? 0 : cisAssetNo.hashCode());
		result = prime * result
				+ ((consName == null) ? 0 : consName.hashCode());
		result = prime * result + ((consNo == null) ? 0 : consNo.hashCode());
		result = prime * result
				+ ((failueCount == null) ? 0 : failueCount.hashCode());
		result = prime * result + ((masterR == null) ? 0 : masterR.hashCode());
		result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
		result = prime * result
				+ ((sucessCount == null) ? 0 : sucessCount.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		result = prime * result + ((taskNo == null) ? 0 : taskNo.hashCode());
		result = prime * result
				+ ((taskType == null) ? 0 : taskType.hashCode());
		result = prime * result
				+ ((templateName == null) ? 0 : templateName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskStatInfoBean other = (TaskStatInfoBean) obj;
		if (cisAssetNo == null) {
			if (other.cisAssetNo != null)
				return false;
		} else if (!cisAssetNo.equals(other.cisAssetNo))
			return false;
		if (consName == null) {
			if (other.consName != null)
				return false;
		} else if (!consName.equals(other.consName))
			return false;
		if (consNo == null) {
			if (other.consNo != null)
				return false;
		} else if (!consNo.equals(other.consNo))
			return false;
		if (failueCount == null) {
			if (other.failueCount != null)
				return false;
		} else if (!failueCount.equals(other.failueCount))
			return false;
		if (masterR == null) {
			if (other.masterR != null)
				return false;
		} else if (!masterR.equals(other.masterR))
			return false;
		if (orgName == null) {
			if (other.orgName != null)
				return false;
		} else if (!orgName.equals(other.orgName))
			return false;
		if (sucessCount == null) {
			if (other.sucessCount != null)
				return false;
		} else if (!sucessCount.equals(other.sucessCount))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		if (taskNo == null) {
			if (other.taskNo != null)
				return false;
		} else if (!taskNo.equals(other.taskNo))
			return false;
		if (taskType == null) {
			if (other.taskType != null)
				return false;
		} else if (!taskType.equals(other.taskType))
			return false;
		if (templateName == null) {
			if (other.templateName != null)
				return false;
		} else if (!templateName.equals(other.templateName))
			return false;
		return true;
	}

	public TaskStatInfoBean() {
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getSucessCount() {
		return sucessCount;
	}

	public Long getTaskType() {
		return taskType;
	}

	public void setTaskType(Long taskType) {
		this.taskType = taskType;
	}

	public void setSucessCount(Long sucessCount) {
		this.sucessCount = sucessCount;
	}

	public Long getFailueCount() {
		return failueCount;
	}

	public void setFailueCount(Long failueCount) {
		this.failueCount = failueCount;
	}

	public String getCisAssetNo() {
		return cisAssetNo;
	}

	public void setCisAssetNo(String cisAssetNo) {
		this.cisAssetNo = cisAssetNo;
	}

	public Short getMasterR() {
		return masterR;
	}

	public void setMasterR(Short masterR) {
		this.masterR = masterR;
	}

	public Short getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(Short taskNo) {
		this.taskNo = taskNo;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getConsName() {
		return consName;
	}

	public void setConsName(String consName) {
		this.consName = consName;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
