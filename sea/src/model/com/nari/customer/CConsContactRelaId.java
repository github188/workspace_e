package com.nari.customer;

/**
 * CConsContactRelaId entity. @author MyEclipse Persistence Tools
 */

public class CConsContactRelaId implements java.io.Serializable {

	// Fields

	private Long consId;
	private Long contactId;

	// Constructors

	/** default constructor */
	public CConsContactRelaId() {
	}

	/** full constructor */
	public CConsContactRelaId(Long consId, Long contactId) {
		this.consId = consId;
		this.contactId = contactId;
	}

	// Property accessors

	public Long getConsId() {
		return this.consId;
	}

	public void setConsId(Long consId) {
		this.consId = consId;
	}

	public Long getContactId() {
		return this.contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CConsContactRelaId))
			return false;
		CConsContactRelaId castOther = (CConsContactRelaId) other;

		return ((this.getConsId() == castOther.getConsId()) || (this
				.getConsId() != null
				&& castOther.getConsId() != null && this.getConsId().equals(
				castOther.getConsId())))
				&& ((this.getContactId() == castOther.getContactId()) || (this
						.getContactId() != null
						&& castOther.getContactId() != null && this
						.getContactId().equals(castOther.getContactId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getConsId() == null ? 0 : this.getConsId().hashCode());
		result = 37 * result
				+ (getContactId() == null ? 0 : this.getContactId().hashCode());
		return result;
	}

}