package com.nari.flowhandle;

/**
 * FEventShieldId entity. @author MyEclipse Persistence Tools
 */

public class FEventShieldId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7595743159553206603L;
	private Long eventId;
	private Short shieldDays;

	// Constructors

	/** default constructor */
	public FEventShieldId() {
	}

	/** full constructor */
	public FEventShieldId(Long eventId, Short shieldDays) {
		this.eventId = eventId;
		this.shieldDays = shieldDays;
	}

	// Property accessors

	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Short getShieldDays() {
		return this.shieldDays;
	}

	public void setShieldDays(Short shieldDays) {
		this.shieldDays = shieldDays;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FEventShieldId))
			return false;
		FEventShieldId castOther = (FEventShieldId) other;

		return ((this.getEventId() == castOther.getEventId()) || (this
				.getEventId() != null
				&& castOther.getEventId() != null && this.getEventId().equals(
				castOther.getEventId())))
				&& ((this.getShieldDays() == castOther.getShieldDays()) || (this
						.getShieldDays() != null
						&& castOther.getShieldDays() != null && this
						.getShieldDays().equals(castOther.getShieldDays())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEventId() == null ? 0 : this.getEventId().hashCode());
		result = 37
				* result
				+ (getShieldDays() == null ? 0 : this.getShieldDays()
						.hashCode());
		return result;
	}

}