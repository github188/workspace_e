package com.nari.model;

public class BClearProtocol implements java.io.Serializable {
	private static final long serialVersionUID = 4279054913425733504L;
	private String protocolNo;
	private String protocolName;
	private Byte dataType;
	private Integer dataGroup;
	public Integer getDataGroup() {
		return dataGroup;
	}
	public void setDataGroup(Integer dataGroup) {
		this.dataGroup = dataGroup;
	}
	public String getProtocolNo() {
		return this.protocolNo;
	}
	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}
	public String getProtocolName() {
		return this.protocolName;
	}
	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}
	public Byte getDataType() {
		return this.dataType;
	}
	public void setDataType(Byte dataType) {
		this.dataType = dataType;
	}

}