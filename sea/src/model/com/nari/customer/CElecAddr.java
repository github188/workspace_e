package com.nari.customer;

/**
 * CElecAddr entity. @author MyEclipse Persistence Tools
 */

public class CElecAddr implements java.io.Serializable {

	// Fields

	private Long csaId;
	private Long consId;
	private String provinceCode;
	private String cityCode;
	private String countyCode;
	private String streetCode;
	private String villageCode;
	private String roadCode;
	private String communityCode;
	private String plateNo;

	// Constructors

	/** default constructor */
	public CElecAddr() {
	}

	/** minimal constructor */
	public CElecAddr(Long consId) {
		this.consId = consId;
	}

	/** full constructor */
	public CElecAddr(Long consId, String provinceCode, String cityCode,
			String countyCode, String streetCode, String villageCode,
			String roadCode, String communityCode, String plateNo) {
		this.consId = consId;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.countyCode = countyCode;
		this.streetCode = streetCode;
		this.villageCode = villageCode;
		this.roadCode = roadCode;
		this.communityCode = communityCode;
		this.plateNo = plateNo;
	}

	// Property accessors

	public Long getCsaId() {
		return this.csaId;
	}

	public void setCsaId(Long csaId) {
		this.csaId = csaId;
	}

	public Long getConsId() {
		return this.consId;
	}

	public void setConsId(Long consId) {
		this.consId = consId;
	}

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return this.countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getStreetCode() {
		return this.streetCode;
	}

	public void setStreetCode(String streetCode) {
		this.streetCode = streetCode;
	}

	public String getVillageCode() {
		return this.villageCode;
	}

	public void setVillageCode(String villageCode) {
		this.villageCode = villageCode;
	}

	public String getRoadCode() {
		return this.roadCode;
	}

	public void setRoadCode(String roadCode) {
		this.roadCode = roadCode;
	}

	public String getCommunityCode() {
		return this.communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}

	public String getPlateNo() {
		return this.plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

}