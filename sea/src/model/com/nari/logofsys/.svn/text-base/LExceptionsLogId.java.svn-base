package com.nari.logofsys;

import java.util.Date;

/**
 * LExceptionsLogId entity. @author MyEclipse Persistence Tools
 */

public class LExceptionsLogId implements java.io.Serializable {

	// Fields

	private String menuNo;
	private String excepTypeCode;
	private String excepNo;
	private String callMethod;
	private String excepContent;
	private Date occurTime;

	// Constructors

	/** default constructor */
	public LExceptionsLogId() {
	}

	/** minimal constructor */
	public LExceptionsLogId(String menuNo) {
		this.menuNo = menuNo;
	}

	/** full constructor */
	public LExceptionsLogId(String menuNo, String excepTypeCode,
			String excepNo, String callMethod, String excepContent,
			Date occurTime) {
		this.menuNo = menuNo;
		this.excepTypeCode = excepTypeCode;
		this.excepNo = excepNo;
		this.callMethod = callMethod;
		this.excepContent = excepContent;
		this.occurTime = occurTime;
	}

	// Property accessors

	public String getMenuNo() {
		return this.menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public String getExcepTypeCode() {
		return this.excepTypeCode;
	}

	public void setExcepTypeCode(String excepTypeCode) {
		this.excepTypeCode = excepTypeCode;
	}

	public String getExcepNo() {
		return this.excepNo;
	}

	public void setExcepNo(String excepNo) {
		this.excepNo = excepNo;
	}

	public String getCallMethod() {
		return this.callMethod;
	}

	public void setCallMethod(String callMethod) {
		this.callMethod = callMethod;
	}

	public String getExcepContent() {
		return this.excepContent;
	}

	public void setExcepContent(String excepContent) {
		this.excepContent = excepContent;
	}

	public Date getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LExceptionsLogId))
			return false;
		LExceptionsLogId castOther = (LExceptionsLogId) other;

		return ((this.getMenuNo() == castOther.getMenuNo()) || (this
				.getMenuNo() != null
				&& castOther.getMenuNo() != null && this.getMenuNo().equals(
				castOther.getMenuNo())))
				&& ((this.getExcepTypeCode() == castOther.getExcepTypeCode()) || (this
						.getExcepTypeCode() != null
						&& castOther.getExcepTypeCode() != null && this
						.getExcepTypeCode()
						.equals(castOther.getExcepTypeCode())))
				&& ((this.getExcepNo() == castOther.getExcepNo()) || (this
						.getExcepNo() != null
						&& castOther.getExcepNo() != null && this.getExcepNo()
						.equals(castOther.getExcepNo())))
				&& ((this.getCallMethod() == castOther.getCallMethod()) || (this
						.getCallMethod() != null
						&& castOther.getCallMethod() != null && this
						.getCallMethod().equals(castOther.getCallMethod())))
				&& ((this.getExcepContent() == castOther.getExcepContent()) || (this
						.getExcepContent() != null
						&& castOther.getExcepContent() != null && this
						.getExcepContent().equals(castOther.getExcepContent())))
				&& ((this.getOccurTime() == castOther.getOccurTime()) || (this
						.getOccurTime() != null
						&& castOther.getOccurTime() != null && this
						.getOccurTime().equals(castOther.getOccurTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMenuNo() == null ? 0 : this.getMenuNo().hashCode());
		result = 37
				* result
				+ (getExcepTypeCode() == null ? 0 : this.getExcepTypeCode()
						.hashCode());
		result = 37 * result
				+ (getExcepNo() == null ? 0 : this.getExcepNo().hashCode());
		result = 37
				* result
				+ (getCallMethod() == null ? 0 : this.getCallMethod()
						.hashCode());
		result = 37
				* result
				+ (getExcepContent() == null ? 0 : this.getExcepContent()
						.hashCode());
		result = 37 * result
				+ (getOccurTime() == null ? 0 : this.getOccurTime().hashCode());
		return result;
	}

}