package com.nari.basicdata;

/**
 * VwProtocolCodeId entity. @author MyEclipse Persistence Tools
 */

public class VwProtocolCodeBean implements java.io.Serializable {

	// Fields

	//规约项代码
	private String protocolCode;
	//规约项名称 
	private String protocolName;
	//缺省值flag
	private int isDefault;

	// Constructors

	/** default constructor */
	public VwProtocolCodeBean() {
	}

	/** full constructor */
	public VwProtocolCodeBean(String protocolCode, String protocolName, int isDefault) {
		this.protocolCode = protocolCode;
		this.protocolName = protocolName;
		this.isDefault = isDefault;
	}

	// Property accessors

	public String getProtocolCode() {
		return this.protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getProtocolName() {
		return this.protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}


	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VwProtocolCodeBean other = (VwProtocolCodeBean) obj;
		if (isDefault != other.isDefault)
			return false;
		if (protocolCode == null) {
			if (other.protocolCode != null)
				return false;
		} else if (!protocolCode.equals(other.protocolCode))
			return false;
		if (protocolName == null) {
			if (other.protocolName != null)
				return false;
		} else if (!protocolName.equals(other.protocolName))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + isDefault;
		result = prime * result
				+ ((protocolCode == null) ? 0 : protocolCode.hashCode());
		result = prime * result
				+ ((protocolName == null) ? 0 : protocolName.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return "VwProtocolCodeBean [isDefault=" + isDefault + ", protocolCode="
				+ protocolCode + ", protocolName=" + protocolName + "]";
	}

}