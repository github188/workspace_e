package com.nari.bg;

/**
 * TBgTaskDetailId entity. @author MyEclipse Persistence Tools
 */

public class TBgTaskDetailId implements java.io.Serializable {

	// Fields

	private Long taskId;
	private String dataItemNo;
	private Byte dataType;

	// Constructors

	/** default constructor */
	public TBgTaskDetailId() {
	}

	/** minimal constructor */
	public TBgTaskDetailId(Long taskId) {
		this.taskId = taskId;
	}

	/** full constructor */
	public TBgTaskDetailId(Long taskId, String dataItemNo, Byte dataType) {
		this.taskId = taskId;
		this.dataItemNo = dataItemNo;
		this.dataType = dataType;
	}

	// Property accessors

	public Long getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getDataItemNo() {
		return this.dataItemNo;
	}

	public void setDataItemNo(String dataItemNo) {
		this.dataItemNo = dataItemNo;
	}

	public Byte getDataType() {
		return this.dataType;
	}

	public void setDataType(Byte dataType) {
		this.dataType = dataType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TBgTaskDetailId))
			return false;
		TBgTaskDetailId castOther = (TBgTaskDetailId) other;

		return ((this.getTaskId() == castOther.getTaskId()) || (this
				.getTaskId() != null
				&& castOther.getTaskId() != null && this.getTaskId().equals(
				castOther.getTaskId())))
				&& ((this.getDataItemNo() == castOther.getDataItemNo()) || (this
						.getDataItemNo() != null
						&& castOther.getDataItemNo() != null && this
						.getDataItemNo().equals(castOther.getDataItemNo())))
				&& ((this.getDataType() == castOther.getDataType()) || (this
						.getDataType() != null
						&& castOther.getDataType() != null && this
						.getDataType().equals(castOther.getDataType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTaskId() == null ? 0 : this.getTaskId().hashCode());
		result = 37
				* result
				+ (getDataItemNo() == null ? 0 : this.getDataItemNo()
						.hashCode());
		result = 37 * result
				+ (getDataType() == null ? 0 : this.getDataType().hashCode());
		return result;
	}

}