package com.nari.basicdata;

/**
 * BEventDataDefId entity. @author MyEclipse Persistence Tools
 */

public class BEventDataDefId implements java.io.Serializable {

	// Fields

	private String eventNo;
	private Short datan;

	// Constructors

	/** default constructor */
	public BEventDataDefId() {
	}

	/** full constructor */
	public BEventDataDefId(String eventNo, Short datan) {
		this.eventNo = eventNo;
		this.datan = datan;
	}

	// Property accessors

	public String getEventNo() {
		return this.eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	public Short getDatan() {
		return this.datan;
	}

	public void setDatan(Short datan) {
		this.datan = datan;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BEventDataDefId))
			return false;
		BEventDataDefId castOther = (BEventDataDefId) other;

		return ((this.getEventNo() == castOther.getEventNo()) || (this
				.getEventNo() != null
				&& castOther.getEventNo() != null && this.getEventNo().equals(
				castOther.getEventNo())))
				&& ((this.getDatan() == castOther.getDatan()) || (this
						.getDatan() != null
						&& castOther.getDatan() != null && this.getDatan()
						.equals(castOther.getDatan())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEventNo() == null ? 0 : this.getEventNo().hashCode());
		result = 37 * result
				+ (getDatan() == null ? 0 : this.getDatan().hashCode());
		return result;
	}

}