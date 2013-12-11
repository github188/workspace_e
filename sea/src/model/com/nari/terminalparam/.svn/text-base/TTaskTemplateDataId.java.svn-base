package com.nari.terminalparam;

/**
 * TTaskTemplateDataId entity. @author MyEclipse Persistence Tools
 */

public class TTaskTemplateDataId implements java.io.Serializable {

	// Fields

	private Long templateId;
	private String protocolNo;

	// Constructors

	/** default constructor */
	public TTaskTemplateDataId() {
	}

	/** full constructor */
	public TTaskTemplateDataId(Long templateId, String protocolNo) {
		this.templateId = templateId;
		this.protocolNo = protocolNo;
	}

	// Property accessors

	public Long getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getProtocolNo() {
		return this.protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTaskTemplateDataId))
			return false;
		TTaskTemplateDataId castOther = (TTaskTemplateDataId) other;

		return ((this.getTemplateId() == castOther.getTemplateId()) || (this
				.getTemplateId() != null
				&& castOther.getTemplateId() != null && this.getTemplateId()
				.equals(castOther.getTemplateId())))
				&& ((this.getProtocolNo() == castOther.getProtocolNo()) || (this
						.getProtocolNo() != null
						&& castOther.getProtocolNo() != null && this
						.getProtocolNo().equals(castOther.getProtocolNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTemplateId() == null ? 0 : this.getTemplateId()
						.hashCode());
		result = 37
				* result
				+ (getProtocolNo() == null ? 0 : this.getProtocolNo()
						.hashCode());
		return result;
	}

}