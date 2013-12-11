package com.nari.orderlypower;

/**
 * WFeectrlTemplateDetailId entity. @author MyEclipse Persistence Tools
 */

public class WFeectrlTemplateDetailId implements java.io.Serializable {

	// Fields

	private Long templateId;
	private Byte erateNo;
	private String sectionStart;
	private String sectionEnd;
	private Double fee;

	// Constructors

	/** default constructor */
	public WFeectrlTemplateDetailId() {
	}

	/** full constructor */
	public WFeectrlTemplateDetailId(Long templateId, Byte erateNo,
			String sectionStart, String sectionEnd, Double fee) {
		this.templateId = templateId;
		this.erateNo = erateNo;
		this.sectionStart = sectionStart;
		this.sectionEnd = sectionEnd;
		this.fee = fee;
	}

	// Property accessors

	public Long getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Byte getErateNo() {
		return this.erateNo;
	}

	public void setErateNo(Byte erateNo) {
		this.erateNo = erateNo;
	}

	public String getSectionStart() {
		return this.sectionStart;
	}

	public void setSectionStart(String sectionStart) {
		this.sectionStart = sectionStart;
	}

	public String getSectionEnd() {
		return this.sectionEnd;
	}

	public void setSectionEnd(String sectionEnd) {
		this.sectionEnd = sectionEnd;
	}

	public Double getFee() {
		return this.fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WFeectrlTemplateDetailId))
			return false;
		WFeectrlTemplateDetailId castOther = (WFeectrlTemplateDetailId) other;

		return ((this.getTemplateId() == castOther.getTemplateId()) || (this
				.getTemplateId() != null
				&& castOther.getTemplateId() != null && this.getTemplateId()
				.equals(castOther.getTemplateId())))
				&& ((this.getErateNo() == castOther.getErateNo()) || (this
						.getErateNo() != null
						&& castOther.getErateNo() != null && this.getErateNo()
						.equals(castOther.getErateNo())))
				&& ((this.getSectionStart() == castOther.getSectionStart()) || (this
						.getSectionStart() != null
						&& castOther.getSectionStart() != null && this
						.getSectionStart().equals(castOther.getSectionStart())))
				&& ((this.getSectionEnd() == castOther.getSectionEnd()) || (this
						.getSectionEnd() != null
						&& castOther.getSectionEnd() != null && this
						.getSectionEnd().equals(castOther.getSectionEnd())))
				&& ((this.getFee() == castOther.getFee()) || (this.getFee() != null
						&& castOther.getFee() != null && this.getFee().equals(
						castOther.getFee())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTemplateId() == null ? 0 : this.getTemplateId()
						.hashCode());
		result = 37 * result
				+ (getErateNo() == null ? 0 : this.getErateNo().hashCode());
		result = 37
				* result
				+ (getSectionStart() == null ? 0 : this.getSectionStart()
						.hashCode());
		result = 37
				* result
				+ (getSectionEnd() == null ? 0 : this.getSectionEnd()
						.hashCode());
		result = 37 * result
				+ (getFee() == null ? 0 : this.getFee().hashCode());
		return result;
	}

}