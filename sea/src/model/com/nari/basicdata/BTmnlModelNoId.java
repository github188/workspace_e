package com.nari.basicdata;

/**
 * BTmnlModelNoId entity. @author MyEclipse Persistence Tools
 */

public class BTmnlModelNoId implements java.io.Serializable {

	// Fields

	private String factoryCode;
	private String id;
	private String modelNo;

	// Constructors

	/** default constructor */
	public BTmnlModelNoId() {
	}

	/** minimal constructor */
	public BTmnlModelNoId(String id) {
		this.id = id;
	}

	/** full constructor */
	public BTmnlModelNoId(String factoryCode, String id, String modelNo) {
		this.factoryCode = factoryCode;
		this.id = id;
		this.modelNo = modelNo;
	}

	// Property accessors

	public String getFactoryCode() {
		return this.factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelNo() {
		return this.modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BTmnlModelNoId))
			return false;
		BTmnlModelNoId castOther = (BTmnlModelNoId) other;

		return ((this.getFactoryCode() == castOther.getFactoryCode()) || (this
				.getFactoryCode() != null
				&& castOther.getFactoryCode() != null && this.getFactoryCode()
				.equals(castOther.getFactoryCode())))
				&& ((this.getId() == castOther.getId()) || (this.getId() != null
						&& castOther.getId() != null && this.getId().equals(
						castOther.getId())))
				&& ((this.getModelNo() == castOther.getModelNo()) || (this
						.getModelNo() != null
						&& castOther.getModelNo() != null && this.getModelNo()
						.equals(castOther.getModelNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getFactoryCode() == null ? 0 : this.getFactoryCode()
						.hashCode());
		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getModelNo() == null ? 0 : this.getModelNo().hashCode());
		return result;
	}

}