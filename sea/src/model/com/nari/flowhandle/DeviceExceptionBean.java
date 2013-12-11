package com.nari.flowhandle;

import java.util.Date;

public class DeviceExceptionBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6391689343461319808L;
	private String tmnlExceptionId;
	private String orgNo;
	private String consNo;
	private String tmnlAssetNo;
	private String terminalAddr;
	private Date exceptDate;
	private String exceptCode;
	private String exceptOrigin;
	private String simNo;
	private String factoryCode;
	private String remark;
	private Date resumeDate;
	private String flowStatusCode;
	private Byte flowStatusDetail;
	private Byte conclusionNo;
	private Byte flowFlag;
	private String handlerNo;
	private Date handleDate;
	private String jobOrder;
	private String distributionNo;
	private Date distributionDate;
	private Date closingDate;
	private String eventName;
	private String consName;
	private String orgName;

	public String getConsName() {
		return consName;
	}

	public void setConsName(String consName) {
		this.consName = consName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getTmnlExceptionId() {
		return tmnlExceptionId;
	}

	public void setTmnlExceptionId(String tmnlExceptionId) {
		this.tmnlExceptionId = tmnlExceptionId;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getTerminalAddr() {
		return terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	public Date getExceptDate() {
		return exceptDate;
	}

	public void setExceptDate(Date exceptDate) {
		this.exceptDate = exceptDate;
	}

	public String getExceptCode() {
		return exceptCode;
	}

	public void setExceptCode(String exceptCode) {
		this.exceptCode = exceptCode;
	}

	public String getExceptOrigin() {
		return exceptOrigin;
	}

	public void setExceptOrigin(String exceptOrigin) {
		this.exceptOrigin = exceptOrigin;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getResumeDate() {
		return resumeDate;
	}

	public void setResumeDate(Date resumeDate) {
		this.resumeDate = resumeDate;
	}

	public String getFlowStatusCode() {
		return flowStatusCode;
	}

	public void setFlowStatusCode(String flowStatusCode) {
		this.flowStatusCode = flowStatusCode;
	}

	public Byte getFlowStatusDetail() {
		return flowStatusDetail;
	}

	public void setFlowStatusDetail(Byte flowStatusDetail) {
		this.flowStatusDetail = flowStatusDetail;
	}

	public Byte getConclusionNo() {
		return conclusionNo;
	}

	public void setConclusionNo(Byte conclusionNo) {
		this.conclusionNo = conclusionNo;
	}

	public Byte getFlowFlag() {
		return flowFlag;
	}

	public void setFlowFlag(Byte flowFlag) {
		this.flowFlag = flowFlag;
	}

	public String getHandlerNo() {
		return handlerNo;
	}

	public void setHandlerNo(String handlerNo) {
		this.handlerNo = handlerNo;
	}

	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

	public String getJobOrder() {
		return jobOrder;
	}

	public void setJobOrder(String jobOrder) {
		this.jobOrder = jobOrder;
	}

	public String getDistributionNo() {
		return distributionNo;
	}

	public void setDistributionNo(String distributionNo) {
		this.distributionNo = distributionNo;
	}

	public Date getDistributionDate() {
		return distributionDate;
	}

	public void setDistributionDate(Date distributionDate) {
		this.distributionDate = distributionDate;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((closingDate == null) ? 0 : closingDate.hashCode());
		result = prime * result
				+ ((conclusionNo == null) ? 0 : conclusionNo.hashCode());
		result = prime * result
				+ ((consName == null) ? 0 : consName.hashCode());
		result = prime * result + ((consNo == null) ? 0 : consNo.hashCode());
		result = prime
				* result
				+ ((distributionDate == null) ? 0 : distributionDate.hashCode());
		result = prime * result
				+ ((distributionNo == null) ? 0 : distributionNo.hashCode());
		result = prime * result
				+ ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result
				+ ((exceptCode == null) ? 0 : exceptCode.hashCode());
		result = prime * result
				+ ((exceptDate == null) ? 0 : exceptDate.hashCode());
		result = prime * result
				+ ((exceptOrigin == null) ? 0 : exceptOrigin.hashCode());
		result = prime * result
				+ ((factoryCode == null) ? 0 : factoryCode.hashCode());
		result = prime * result
				+ ((flowFlag == null) ? 0 : flowFlag.hashCode());
		result = prime * result
				+ ((flowStatusCode == null) ? 0 : flowStatusCode.hashCode());
		result = prime
				* result
				+ ((flowStatusDetail == null) ? 0 : flowStatusDetail.hashCode());
		result = prime * result
				+ ((handleDate == null) ? 0 : handleDate.hashCode());
		result = prime * result
				+ ((handlerNo == null) ? 0 : handlerNo.hashCode());
		result = prime * result
				+ ((jobOrder == null) ? 0 : jobOrder.hashCode());
		result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
		result = prime * result + ((orgNo == null) ? 0 : orgNo.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((resumeDate == null) ? 0 : resumeDate.hashCode());
		result = prime * result + ((simNo == null) ? 0 : simNo.hashCode());
		result = prime * result
				+ ((terminalAddr == null) ? 0 : terminalAddr.hashCode());
		result = prime * result
				+ ((tmnlAssetNo == null) ? 0 : tmnlAssetNo.hashCode());
		result = prime * result
				+ ((tmnlExceptionId == null) ? 0 : tmnlExceptionId.hashCode());
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
		DeviceExceptionBean other = (DeviceExceptionBean) obj;
		if (closingDate == null) {
			if (other.closingDate != null)
				return false;
		} else if (!closingDate.equals(other.closingDate))
			return false;
		if (conclusionNo == null) {
			if (other.conclusionNo != null)
				return false;
		} else if (!conclusionNo.equals(other.conclusionNo))
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
		if (distributionDate == null) {
			if (other.distributionDate != null)
				return false;
		} else if (!distributionDate.equals(other.distributionDate))
			return false;
		if (distributionNo == null) {
			if (other.distributionNo != null)
				return false;
		} else if (!distributionNo.equals(other.distributionNo))
			return false;
		if (eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!eventName.equals(other.eventName))
			return false;
		if (exceptCode == null) {
			if (other.exceptCode != null)
				return false;
		} else if (!exceptCode.equals(other.exceptCode))
			return false;
		if (exceptDate == null) {
			if (other.exceptDate != null)
				return false;
		} else if (!exceptDate.equals(other.exceptDate))
			return false;
		if (exceptOrigin == null) {
			if (other.exceptOrigin != null)
				return false;
		} else if (!exceptOrigin.equals(other.exceptOrigin))
			return false;
		if (factoryCode == null) {
			if (other.factoryCode != null)
				return false;
		} else if (!factoryCode.equals(other.factoryCode))
			return false;
		if (flowFlag == null) {
			if (other.flowFlag != null)
				return false;
		} else if (!flowFlag.equals(other.flowFlag))
			return false;
		if (flowStatusCode == null) {
			if (other.flowStatusCode != null)
				return false;
		} else if (!flowStatusCode.equals(other.flowStatusCode))
			return false;
		if (flowStatusDetail == null) {
			if (other.flowStatusDetail != null)
				return false;
		} else if (!flowStatusDetail.equals(other.flowStatusDetail))
			return false;
		if (handleDate == null) {
			if (other.handleDate != null)
				return false;
		} else if (!handleDate.equals(other.handleDate))
			return false;
		if (handlerNo == null) {
			if (other.handlerNo != null)
				return false;
		} else if (!handlerNo.equals(other.handlerNo))
			return false;
		if (jobOrder == null) {
			if (other.jobOrder != null)
				return false;
		} else if (!jobOrder.equals(other.jobOrder))
			return false;
		if (orgName == null) {
			if (other.orgName != null)
				return false;
		} else if (!orgName.equals(other.orgName))
			return false;
		if (orgNo == null) {
			if (other.orgNo != null)
				return false;
		} else if (!orgNo.equals(other.orgNo))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (resumeDate == null) {
			if (other.resumeDate != null)
				return false;
		} else if (!resumeDate.equals(other.resumeDate))
			return false;
		if (simNo == null) {
			if (other.simNo != null)
				return false;
		} else if (!simNo.equals(other.simNo))
			return false;
		if (terminalAddr == null) {
			if (other.terminalAddr != null)
				return false;
		} else if (!terminalAddr.equals(other.terminalAddr))
			return false;
		if (tmnlAssetNo == null) {
			if (other.tmnlAssetNo != null)
				return false;
		} else if (!tmnlAssetNo.equals(other.tmnlAssetNo))
			return false;
		if (tmnlExceptionId == null) {
			if (other.tmnlExceptionId != null)
				return false;
		} else if (!tmnlExceptionId.equals(other.tmnlExceptionId))
			return false;
		return true;
	}

}
