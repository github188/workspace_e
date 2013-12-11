package com.nari.orderlypower;

/**
 * WSmsSend entity. @author MyEclipse Persistence Tools
 */

public class WSmsSend implements java.io.Serializable {

	// Fields

	private Long recordId;
	private Long sendTemplateId;
	private Byte objectType;
	private String subscribeObjId;

	// Constructors

	/** default constructor */
	public WSmsSend() {
	}

	/** full constructor */
	public WSmsSend(Long sendTemplateId, Byte objectType, String subscribeObjId) {
		this.sendTemplateId = sendTemplateId;
		this.objectType = objectType;
		this.subscribeObjId = subscribeObjId;
	}

	// Property accessors

	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Long getSendTemplateId() {
		return this.sendTemplateId;
	}

	public void setSendTemplateId(Long sendTemplateId) {
		this.sendTemplateId = sendTemplateId;
	}

	public Byte getObjectType() {
		return this.objectType;
	}

	public void setObjectType(Byte objectType) {
		this.objectType = objectType;
	}

	public String getSubscribeObjId() {
		return this.subscribeObjId;
	}

	public void setSubscribeObjId(String subscribeObjId) {
		this.subscribeObjId = subscribeObjId;
	}

}