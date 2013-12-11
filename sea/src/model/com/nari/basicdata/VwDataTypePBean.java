package com.nari.basicdata;

/**
 * VwDataTypePId entity. @author MyEclipse Persistence Tools
 */

public class VwDataTypePBean implements java.io.Serializable {

	// Fields
	//数据类型代码
	private String dataType;
	//数据类型名称
	private String dataName;

	// Constructors

	/** default constructor */
	public VwDataTypePBean() {
	}

	/** full constructor */
	public VwDataTypePBean(String dataType, String dataName) {
		this.dataType = dataType;
		this.dataName = dataName;
	}

	// Property accessors

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataName() {
		return this.dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VwDataTypePBean))
			return false;
		VwDataTypePBean castOther = (VwDataTypePBean) other;

		return ((this.getDataType() == castOther.getDataType()) || (this
				.getDataType() != null
				&& castOther.getDataType() != null && this.getDataType()
				.equals(castOther.getDataType())))
				&& ((this.getDataName() == castOther.getDataName()) || (this
						.getDataName() != null
						&& castOther.getDataName() != null && this
						.getDataName().equals(castOther.getDataName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDataType() == null ? 0 : this.getDataType().hashCode());
		result = 37 * result
				+ (getDataName() == null ? 0 : this.getDataName().hashCode());
		return result;
	}

}