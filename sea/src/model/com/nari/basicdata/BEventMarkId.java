package com.nari.basicdata;

/**
 * BEventMarkId entity. @author MyEclipse Persistence Tools
 */

public class BEventMarkId implements java.io.Serializable {

	// Fields

	private String eventNo;
	private Short protItemSn;
	private Short markNo;
	private String markName;
	private String mark0;
	private String mark1;

	// Constructors

	/** default constructor */
	public BEventMarkId() {
	}

	/** minimal constructor */
	public BEventMarkId(String eventNo) {
		this.eventNo = eventNo;
	}

	/** full constructor */
	public BEventMarkId(String eventNo, Short protItemSn, Short markNo,
			String markName, String mark0, String mark1) {
		this.eventNo = eventNo;
		this.protItemSn = protItemSn;
		this.markNo = markNo;
		this.markName = markName;
		this.mark0 = mark0;
		this.mark1 = mark1;
	}

	// Property accessors

	public String getEventNo() {
		return this.eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	public Short getProtItemSn() {
		return this.protItemSn;
	}

	public void setProtItemSn(Short protItemSn) {
		this.protItemSn = protItemSn;
	}

	public Short getMarkNo() {
		return this.markNo;
	}

	public void setMarkNo(Short markNo) {
		this.markNo = markNo;
	}

	public String getMarkName() {
		return this.markName;
	}

	public void setMarkName(String markName) {
		this.markName = markName;
	}

	public String getMark0() {
		return this.mark0;
	}

	public void setMark0(String mark0) {
		this.mark0 = mark0;
	}

	public String getMark1() {
		return this.mark1;
	}

	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BEventMarkId))
			return false;
		BEventMarkId castOther = (BEventMarkId) other;

		return ((this.getEventNo() == castOther.getEventNo()) || (this
				.getEventNo() != null
				&& castOther.getEventNo() != null && this.getEventNo().equals(
				castOther.getEventNo())))
				&& ((this.getProtItemSn() == castOther.getProtItemSn()) || (this
						.getProtItemSn() != null
						&& castOther.getProtItemSn() != null && this
						.getProtItemSn().equals(castOther.getProtItemSn())))
				&& ((this.getMarkNo() == castOther.getMarkNo()) || (this
						.getMarkNo() != null
						&& castOther.getMarkNo() != null && this.getMarkNo()
						.equals(castOther.getMarkNo())))
				&& ((this.getMarkName() == castOther.getMarkName()) || (this
						.getMarkName() != null
						&& castOther.getMarkName() != null && this
						.getMarkName().equals(castOther.getMarkName())))
				&& ((this.getMark0() == castOther.getMark0()) || (this
						.getMark0() != null
						&& castOther.getMark0() != null && this.getMark0()
						.equals(castOther.getMark0())))
				&& ((this.getMark1() == castOther.getMark1()) || (this
						.getMark1() != null
						&& castOther.getMark1() != null && this.getMark1()
						.equals(castOther.getMark1())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEventNo() == null ? 0 : this.getEventNo().hashCode());
		result = 37
				* result
				+ (getProtItemSn() == null ? 0 : this.getProtItemSn()
						.hashCode());
		result = 37 * result
				+ (getMarkNo() == null ? 0 : this.getMarkNo().hashCode());
		result = 37 * result
				+ (getMarkName() == null ? 0 : this.getMarkName().hashCode());
		result = 37 * result
				+ (getMark0() == null ? 0 : this.getMark0().hashCode());
		result = 37 * result
				+ (getMark1() == null ? 0 : this.getMark1().hashCode());
		return result;
	}

}