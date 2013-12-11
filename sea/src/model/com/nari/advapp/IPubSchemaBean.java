package com.nari.advapp;

import java.io.Serializable;

public class IPubSchemaBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4301752111281858971L;
	//发布计划号
	private String pubSchemaId;
	//用户类型
	private String consType;
	//数据类型
	private String dataType;
	//发布基准时间
	private String pubBaseTime;
	//发布周期
	private String pubCycle;
	//发布周期单位
	private String pubCycleUnit;
	//是否有效
	private String isvalid;
	public IPubSchemaBean() {
	}
	public IPubSchemaBean(String pubSchemaId, String consType, String dataType,
			String pubBaseTime, String pubCycle, String pubCycleUnit,
			String isvalid) {
		super();
		this.pubSchemaId = pubSchemaId;
		this.consType = consType;
		this.dataType = dataType;
		this.pubBaseTime = pubBaseTime;
		this.pubCycle = pubCycle;
		this.pubCycleUnit = pubCycleUnit;
		this.isvalid = isvalid;
	}
	public String getPubSchemaId() {
		return pubSchemaId;
	}
	public void setPubSchemaId(String pubSchemaId) {
		this.pubSchemaId = pubSchemaId;
	}
	public String getConsType() {
		return consType;
	}
	public void setConsType(String consType) {
		this.consType = consType;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getPubBaseTime() {
		return pubBaseTime;
	}
	public void setPubBaseTime(String pubBaseTime) {
		this.pubBaseTime = pubBaseTime;
	}
	public String getPubCycle() {
		return pubCycle;
	}
	public void setPubCycle(String pubCycle) {
		this.pubCycle = pubCycle;
	}
	public String getPubCycleUnit() {
		return pubCycleUnit;
	}
	public void setPubCycleUnit(String pubCycleUnit) {
		this.pubCycleUnit = pubCycleUnit;
	}
	public String getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((isvalid == null) ? 0 : isvalid.hashCode());
		result = prime * result
				+ ((consType == null) ? 0 : consType.hashCode());
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result
				+ ((pubBaseTime == null) ? 0 : pubBaseTime.hashCode());
		result = prime * result
				+ ((pubCycle == null) ? 0 : pubCycle.hashCode());
		result = prime * result
				+ ((pubCycleUnit == null) ? 0 : pubCycleUnit.hashCode());
		result = prime * result
				+ ((pubSchemaId == null) ? 0 : pubSchemaId.hashCode());
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
		IPubSchemaBean other = (IPubSchemaBean) obj;
		if (isvalid == null) {
			if (other.isvalid != null)
				return false;
		} else if (!isvalid.equals(other.isvalid))
			return false;
		if (consType == null) {
			if (other.consType != null)
				return false;
		} else if (!consType.equals(other.consType))
			return false;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (pubBaseTime == null) {
			if (other.pubBaseTime != null)
				return false;
		} else if (!pubBaseTime.equals(other.pubBaseTime))
			return false;
		if (pubCycle == null) {
			if (other.pubCycle != null)
				return false;
		} else if (!pubCycle.equals(other.pubCycle))
			return false;
		if (pubCycleUnit == null) {
			if (other.pubCycleUnit != null)
				return false;
		} else if (!pubCycleUnit.equals(other.pubCycleUnit))
			return false;
		if (pubSchemaId == null) {
			if (other.pubSchemaId != null)
				return false;
		} else if (!pubSchemaId.equals(other.pubSchemaId))
			return false;
		return true;
	}
}
