package com.nari.basicdata;

/**
 * BCommProtocolItem entity. @author MyEclipse Persistence Tools
 */

public class BCommProtocolItem implements java.io.Serializable {

	// Fields

	private String protItemNo;
	private String protocolNo;
	private String protItemName;
	private String clearProtNo;
	private String dataFormat;
	private String unit;
	private String bytes;
	private String defaultVaule;
	private String dataStyle;
	private String occupyBits;
	private String parseDirect;
	private Byte dataType;
	private Byte dataLevel;
	private String protocolCode;
	private Short protItemSn;
	private String blockRule;
	private Byte rwFlag;
	private Byte htmlFormat;
	private String maxVal;
	private String minVal;
	private String dataRemark;

	// Constructors

	/** default constructor */
	public BCommProtocolItem() {
	}

	/** full constructor */
	public BCommProtocolItem(String protocolNo, String protItemName,
			String clearProtNo, String dataFormat, String unit, String bytes,
			String defaultVaule, String dataStyle, Byte dataType,
			Byte dataLevel, String protocolCode, Short protItemSn,
			String blockRule, Byte rwFlag, Byte htmlFormat, String maxVal,
			String minVal, String dataRemark, String occupyBits,
			String parseDirect) {
		this.protocolNo = protocolNo;
		this.protItemName = protItemName;
		this.clearProtNo = clearProtNo;
		this.dataFormat = dataFormat;
		this.unit = unit;
		this.bytes = bytes;
		this.defaultVaule = defaultVaule;
		this.dataStyle = dataStyle;
		this.dataType = dataType;
		this.dataLevel = dataLevel;
		this.protocolCode = protocolCode;
		this.protItemSn = protItemSn;
		this.blockRule = blockRule;
		this.rwFlag = rwFlag;
		this.htmlFormat = htmlFormat;
		this.maxVal = maxVal;
		this.minVal = minVal;
		this.dataRemark = dataRemark;
		this.occupyBits = occupyBits;
		this.parseDirect = parseDirect;
	}

	// Property accessors

	public String getProtItemNo() {
		return this.protItemNo;
	}

	public void setProtItemNo(String protItemNo) {
		this.protItemNo = protItemNo;
	}

	public String getOccupyBits() {
		return occupyBits;
	}

	public void setOccupyBits(String occupyBits) {
		this.occupyBits = occupyBits;
	}

	public String getParseDirect() {
		return parseDirect;
	}

	public void setParseDirect(String parseDirect) {
		this.parseDirect = parseDirect;
	}

	public String getProtocolNo() {
		return this.protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	public String getProtItemName() {
		return this.protItemName;
	}

	public void setProtItemName(String protItemName) {
		this.protItemName = protItemName;
	}

	public String getClearProtNo() {
		return this.clearProtNo;
	}

	public void setClearProtNo(String clearProtNo) {
		this.clearProtNo = clearProtNo;
	}

	public String getDataFormat() {
		return this.dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getBytes() {
		return this.bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
	}

	public String getDefaultVaule() {
		return this.defaultVaule;
	}

	public void setDefaultVaule(String defaultVaule) {
		this.defaultVaule = defaultVaule;
	}

	public String getDataStyle() {
		return this.dataStyle;
	}

	public void setDataStyle(String dataStyle) {
		this.dataStyle = dataStyle;
	}

	public Byte getDataType() {
		return this.dataType;
	}

	public void setDataType(Byte dataType) {
		this.dataType = dataType;
	}

	public Byte getDataLevel() {
		return this.dataLevel;
	}

	public void setDataLevel(Byte dataLevel) {
		this.dataLevel = dataLevel;
	}

	public String getProtocolCode() {
		return this.protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public Short getProtItemSn() {
		return this.protItemSn;
	}

	public void setProtItemSn(Short protItemSn) {
		this.protItemSn = protItemSn;
	}

	public String getBlockRule() {
		return this.blockRule;
	}

	public void setBlockRule(String blockRule) {
		this.blockRule = blockRule;
	}

	public Byte getRwFlag() {
		return this.rwFlag;
	}

	public void setRwFlag(Byte rwFlag) {
		this.rwFlag = rwFlag;
	}

	public Byte getHtmlFormat() {
		return this.htmlFormat;
	}

	public void setHtmlFormat(Byte htmlFormat) {
		this.htmlFormat = htmlFormat;
	}

	public String getMaxVal() {
		return this.maxVal;
	}

	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

	public String getMinVal() {
		return this.minVal;
	}

	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	public String getDataRemark() {
		return this.dataRemark;
	}

	public void setDataRemark(String dataRemark) {
		this.dataRemark = dataRemark;
	}

}