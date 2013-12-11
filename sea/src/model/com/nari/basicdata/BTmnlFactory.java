package com.nari.basicdata;

/**
 * BTmnlFactory entity. @author MyEclipse Persistence Tools
 */

public class BTmnlFactory implements java.io.Serializable {

	// Fields

	private String factoryCode;
	private String factoryName;
	private String factoryFullName;

	// Constructors

	/** default constructor */
	public BTmnlFactory() {
	}

	/** full constructor */
	public BTmnlFactory(String factoryName, String factoryFullName) {
		this.factoryName = factoryName;
		this.factoryFullName = factoryFullName;
	}

	// Property accessors

	public String getFactoryCode() {
		return this.factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getFactoryName() {
		return this.factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getFactoryFullName() {
		return this.factoryFullName;
	}

	public void setFactoryFullName(String factoryFullName) {
		this.factoryFullName = factoryFullName;
	}

}