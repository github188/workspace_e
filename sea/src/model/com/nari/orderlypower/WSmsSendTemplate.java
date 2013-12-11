package com.nari.orderlypower;

/**
 * WSmsSendTemplate entity. @author MyEclipse Persistence Tools
 */

public class WSmsSendTemplate implements java.io.Serializable {

	// Fields

	private Long sendTemplateId;
	private String templateName;
	private String templateContent;
	private String orgNo;
	private String contactModeList;

	// Constructors

	/** default constructor */
	public WSmsSendTemplate() {
	}

	/** full constructor */
	public WSmsSendTemplate(String templateName, String templateContent,
			String orgNo, String contactModeList) {
		this.templateName = templateName;
		this.templateContent = templateContent;
		this.orgNo = orgNo;
		this.contactModeList = contactModeList;
	}

	// Property accessors

	public Long getSendTemplateId() {
		return this.sendTemplateId;
	}

	public void setSendTemplateId(Long sendTemplateId) {
		this.sendTemplateId = sendTemplateId;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateContent() {
		return this.templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getContactModeList() {
		return this.contactModeList;
	}

	public void setContactModeList(String contactModeList) {
		this.contactModeList = contactModeList;
	}

}