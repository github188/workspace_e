package com.nari.advapp;

import java.io.Serializable;

public class BItemCodeMappingBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2087293485774025842L;
	//数据来源表名
	private String tableName;
	//数据来源字段
	private String columnName;
	//数据名称
	private String itemName;
	//采集系统编码
	private String seaItemCode;
	//营销采集数据项
	private String collItemCode;
	//数据分类
	private String dataGroup;
	
	public BItemCodeMappingBean(){
	}
	
	public BItemCodeMappingBean(String tableName, String columnName,
			String itemName, String seaItemCode, String collItemCode,
			String dataGroup) {
		super();
		this.tableName = tableName;
		this.columnName = columnName;
		this.itemName = itemName;
		this.seaItemCode = seaItemCode;
		this.collItemCode = collItemCode;
		this.dataGroup = dataGroup;
	}
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSeaItemCode() {
		return seaItemCode;
	}

	public void setSeaItemCode(String seaItemCode) {
		this.seaItemCode = seaItemCode;
	}

	public String getCollItemCode() {
		return collItemCode;
	}

	public void setCollItemCode(String collItemCode) {
		this.collItemCode = collItemCode;
	}

	public String getDataGroup() {
		return dataGroup;
	}

	public void setDataGroup(String dataGroup) {
		this.dataGroup = dataGroup;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((collItemCode == null) ? 0 : collItemCode.hashCode());
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result
				+ ((dataGroup == null) ? 0 : dataGroup.hashCode());
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result
				+ ((seaItemCode == null) ? 0 : seaItemCode.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
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
		BItemCodeMappingBean other = (BItemCodeMappingBean) obj;
		if (collItemCode == null) {
			if (other.collItemCode != null)
				return false;
		} else if (!collItemCode.equals(other.collItemCode))
			return false;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (dataGroup == null) {
			if (other.dataGroup != null)
				return false;
		} else if (!dataGroup.equals(other.dataGroup))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (seaItemCode == null) {
			if (other.seaItemCode != null)
				return false;
		} else if (!seaItemCode.equals(other.seaItemCode))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}
}
