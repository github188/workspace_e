package com.nari.bg;

/**
 * TBgTaskSendId entity. @author MyEclipse Persistence Tools
 */

public class TBgTaskSendId implements java.io.Serializable {

	// Fields

	private String tmnlAssetNo;
	private Short taskNo;
	private String dataItemNo;
	private String dataValue;

	// Constructors

	/** default constructor */
	public TBgTaskSendId() {
	}

	/** minimal constructor */
	public TBgTaskSendId(String tmnlAssetNo, Short taskNo) {
		this.tmnlAssetNo = tmnlAssetNo;
		this.taskNo = taskNo;
	}

	/** full constructor */
	public TBgTaskSendId(String tmnlAssetNo, Short taskNo, String dataItemNo,
			String dataValue) {
		this.tmnlAssetNo = tmnlAssetNo;
		this.taskNo = taskNo;
		this.dataItemNo = dataItemNo;
		this.dataValue = dataValue;
	}

	// Property accessors

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public Short getTaskNo() {
		return this.taskNo;
	}

	public void setTaskNo(Short taskNo) {
		this.taskNo = taskNo;
	}

	public String getDataItemNo() {
		return this.dataItemNo;
	}

	public void setDataItemNo(String dataItemNo) {
		this.dataItemNo = dataItemNo;
	}

	public String getDataValue() {
		return this.dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TBgTaskSendId))
			return false;
		TBgTaskSendId castOther = (TBgTaskSendId) other;

		return ((this.getTmnlAssetNo() == castOther.getTmnlAssetNo()) || (this
				.getTmnlAssetNo() != null
				&& castOther.getTmnlAssetNo() != null && this.getTmnlAssetNo()
				.equals(castOther.getTmnlAssetNo())))
				&& ((this.getTaskNo() == castOther.getTaskNo()) || (this
						.getTaskNo() != null
						&& castOther.getTaskNo() != null && this.getTaskNo()
						.equals(castOther.getTaskNo())))
				&& ((this.getDataItemNo() == castOther.getDataItemNo()) || (this
						.getDataItemNo() != null
						&& castOther.getDataItemNo() != null && this
						.getDataItemNo().equals(castOther.getDataItemNo())))
				&& ((this.getDataValue() == castOther.getDataValue()) || (this
						.getDataValue() != null
						&& castOther.getDataValue() != null && this
						.getDataValue().equals(castOther.getDataValue())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTmnlAssetNo() == null ? 0 : this.getTmnlAssetNo()
						.hashCode());
		result = 37 * result
				+ (getTaskNo() == null ? 0 : this.getTaskNo().hashCode());
		result = 37
				* result
				+ (getDataItemNo() == null ? 0 : this.getDataItemNo()
						.hashCode());
		result = 37 * result
				+ (getDataValue() == null ? 0 : this.getDataValue().hashCode());
		return result;
	}

}