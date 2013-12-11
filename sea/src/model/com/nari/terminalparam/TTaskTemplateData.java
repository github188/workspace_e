package com.nari.terminalparam;

/**
 * TTaskTemplateData entity. @author MyEclipse Persistence Tools
 */

public class TTaskTemplateData implements java.io.Serializable {

	// Fields

	private TTaskTemplateDataId id;
	private Short protocolSn;

	// Constructors

	/** default constructor */
	public TTaskTemplateData() {
	}

	/** minimal constructor */
	public TTaskTemplateData(TTaskTemplateDataId id) {
		this.id = id;
	}

	/** full constructor */
	public TTaskTemplateData(TTaskTemplateDataId id, Short protocolSn) {
		this.id = id;
		this.protocolSn = protocolSn;
	}

	// Property accessors

	public TTaskTemplateDataId getId() {
		return this.id;
	}

	public void setId(TTaskTemplateDataId id) {
		this.id = id;
	}

	public Short getProtocolSn() {
		return this.protocolSn;
	}

	public void setProtocolSn(Short protocolSn) {
		this.protocolSn = protocolSn;
	}

}