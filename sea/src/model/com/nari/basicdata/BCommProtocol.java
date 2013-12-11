package com.nari.basicdata;

/**
 * BCommProtocol entity. @author MyEclipse Persistence Tools
 */

public class BCommProtocol implements java.io.Serializable {

	// Fields

	private String protocolNo;
	private String PProtocolNo;
	private String protocolName;
	private String shortName;
	private String clearProtNo;
	private String dataFormat;
	private String unit;
	private String bytes;
	private String dataStyle;
	private String defaultVaule;
	private Byte dataType;
	private Byte dataLevel;
	private String protocolCode;
	private Boolean isBlock;
	private Byte rwFlag;
	private Byte frozenType;

	// Constructors

	/** default constructor */
	public BCommProtocol() {
	}

	/** full constructor */
	public BCommProtocol(String PProtocolNo, String protocolName,
			String shortName, String clearProtNo, String dataFormat,
			String unit, String bytes, String dataStyle, String defaultVaule,
			Byte dataType, Byte dataLevel, String protocolCode,
			Boolean isBlock, Byte rwFlag, Byte frozenType) {
		this.PProtocolNo = PProtocolNo;
		this.protocolName = protocolName;
		this.shortName = shortName;
		this.clearProtNo = clearProtNo;
		this.dataFormat = dataFormat;
		this.unit = unit;
		this.bytes = bytes;
		this.dataStyle = dataStyle;
		this.defaultVaule = defaultVaule;
		this.dataType = dataType;
		this.dataLevel = dataLevel;
		this.protocolCode = protocolCode;
		this.isBlock = isBlock;
		this.rwFlag = rwFlag;
		this.frozenType = frozenType;
	}

	// Property accessors

	public String getProtocolNo() {
		return this.protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	public String getPProtocolNo() {
		return this.PProtocolNo;
	}

	public void setPProtocolNo(String PProtocolNo) {
		this.PProtocolNo = PProtocolNo;
	}

	public String getProtocolName() {
		return this.protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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

	public String getDataStyle() {
		return this.dataStyle;
	}

	public void setDataStyle(String dataStyle) {
		this.dataStyle = dataStyle;
	}

	public String getDefaultVaule() {
		return this.defaultVaule;
	}

	public void setDefaultVaule(String defaultVaule) {
		this.defaultVaule = defaultVaule;
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

	public Boolean getIsBlock() {
		return this.isBlock;
	}

	public void setIsBlock(Boolean isBlock) {
		this.isBlock = isBlock;
	}

	public Byte getRwFlag() {
		return this.rwFlag;
	}

	public void setRwFlag(Byte rwFlag) {
		this.rwFlag = rwFlag;
	}

	public Byte getFrozenType() {
		return this.frozenType;
	}

	public void setFrozenType(Byte frozenType) {
		this.frozenType = frozenType;
	}

}