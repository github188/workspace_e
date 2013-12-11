package com.nari.advapp;

import java.io.Serializable;

public class IPubDetailBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5090588055907050959L;
	//发布计划号
	private String pubSchemaId;
	//数据项编码
	private String seaItemCode;
	//数据项名称
	private String itemName;

	public IPubDetailBean() {
	}
	
	public IPubDetailBean(String pubSchemaId, String seaItemCode, String itemName) {
		super();
		this.pubSchemaId = pubSchemaId;
		this.seaItemCode = seaItemCode;
		this.itemName = itemName;
	}
	public String getPubSchemaId() {
		return pubSchemaId;
	}

	public void setPubSchemaId(String pubSchemaId) {
		this.pubSchemaId = pubSchemaId;
	}

	public String getSeaItemCode() {
		return seaItemCode;
	}

	public void setSeaItemCode(String seaItemCode) {
		this.seaItemCode = seaItemCode;
	}
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pubSchemaId == null) ? 0 : pubSchemaId.hashCode());
		result = prime * result
				+ ((seaItemCode == null) ? 0 : seaItemCode.hashCode());
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
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
		IPubDetailBean other = (IPubDetailBean) obj;
		if (pubSchemaId == null) {
			if (other.pubSchemaId != null)
				return false;
		} else if (!pubSchemaId.equals(other.pubSchemaId))
			return false;
		if (seaItemCode == null) {
			if (other.seaItemCode != null)
				return false;
		} else if (!seaItemCode.equals(other.seaItemCode))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		return true;
	}
}
