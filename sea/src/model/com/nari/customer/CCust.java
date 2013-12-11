package com.nari.customer;

/**
 * CCust entity. @author MyEclipse Persistence Tools
 */

public class CCust implements java.io.Serializable {

	// Fields

	private Long custId;
	private String custNo;
	private String name;
	private String economyTypeCode;
	private Double annualGp;
	private String creditLevelCode;
	private String valueLevelCode;
	private String riskLevelCode;
	private String vipFlag;
	private String queryPwd;
	private String entepriseWebsite;
	private String entepriseScale;
	private String brief;
	private Double regCapital;
	private Double TCaptal;
	private String legalPerson;
	private String operScope;
	private String mainProduct;
	private String produceTech;
	private String output;
	private String vipLevel;
	private String mainMaterial;
	private String supplySrc;
	private Double salesAmt;
	private String salesRegion;
	private String psEnsurePrj;
	private Double powerCostRatio;
	private String industryCode;

	// Constructors

	/** default constructor */
	public CCust() {
	}

	/** full constructor */
	public CCust(String custNo, String name, String economyTypeCode,
			Double annualGp, String creditLevelCode, String valueLevelCode,
			String riskLevelCode, String vipFlag, String queryPwd,
			String entepriseWebsite, String entepriseScale, String brief,
			Double regCapital, Double TCaptal, String legalPerson,
			String operScope, String mainProduct, String produceTech,
			String output, String vipLevel, String mainMaterial,
			String supplySrc, Double salesAmt, String salesRegion,
			String psEnsurePrj, Double powerCostRatio, String industryCode) {
		this.custNo = custNo;
		this.name = name;
		this.economyTypeCode = economyTypeCode;
		this.annualGp = annualGp;
		this.creditLevelCode = creditLevelCode;
		this.valueLevelCode = valueLevelCode;
		this.riskLevelCode = riskLevelCode;
		this.vipFlag = vipFlag;
		this.queryPwd = queryPwd;
		this.entepriseWebsite = entepriseWebsite;
		this.entepriseScale = entepriseScale;
		this.brief = brief;
		this.regCapital = regCapital;
		this.TCaptal = TCaptal;
		this.legalPerson = legalPerson;
		this.operScope = operScope;
		this.mainProduct = mainProduct;
		this.produceTech = produceTech;
		this.output = output;
		this.vipLevel = vipLevel;
		this.mainMaterial = mainMaterial;
		this.supplySrc = supplySrc;
		this.salesAmt = salesAmt;
		this.salesRegion = salesRegion;
		this.psEnsurePrj = psEnsurePrj;
		this.powerCostRatio = powerCostRatio;
		this.industryCode = industryCode;
	}

	// Property accessors

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEconomyTypeCode() {
		return this.economyTypeCode;
	}

	public void setEconomyTypeCode(String economyTypeCode) {
		this.economyTypeCode = economyTypeCode;
	}

	public Double getAnnualGp() {
		return this.annualGp;
	}

	public void setAnnualGp(Double annualGp) {
		this.annualGp = annualGp;
	}

	public String getCreditLevelCode() {
		return this.creditLevelCode;
	}

	public void setCreditLevelCode(String creditLevelCode) {
		this.creditLevelCode = creditLevelCode;
	}

	public String getValueLevelCode() {
		return this.valueLevelCode;
	}

	public void setValueLevelCode(String valueLevelCode) {
		this.valueLevelCode = valueLevelCode;
	}

	public String getRiskLevelCode() {
		return this.riskLevelCode;
	}

	public void setRiskLevelCode(String riskLevelCode) {
		this.riskLevelCode = riskLevelCode;
	}

	public String getVipFlag() {
		return this.vipFlag;
	}

	public void setVipFlag(String vipFlag) {
		this.vipFlag = vipFlag;
	}

	public String getQueryPwd() {
		return this.queryPwd;
	}

	public void setQueryPwd(String queryPwd) {
		this.queryPwd = queryPwd;
	}

	public String getEntepriseWebsite() {
		return this.entepriseWebsite;
	}

	public void setEntepriseWebsite(String entepriseWebsite) {
		this.entepriseWebsite = entepriseWebsite;
	}

	public String getEntepriseScale() {
		return this.entepriseScale;
	}

	public void setEntepriseScale(String entepriseScale) {
		this.entepriseScale = entepriseScale;
	}

	public String getBrief() {
		return this.brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public Double getRegCapital() {
		return this.regCapital;
	}

	public void setRegCapital(Double regCapital) {
		this.regCapital = regCapital;
	}

	public Double getTCaptal() {
		return this.TCaptal;
	}

	public void setTCaptal(Double TCaptal) {
		this.TCaptal = TCaptal;
	}

	public String getLegalPerson() {
		return this.legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getOperScope() {
		return this.operScope;
	}

	public void setOperScope(String operScope) {
		this.operScope = operScope;
	}

	public String getMainProduct() {
		return this.mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getProduceTech() {
		return this.produceTech;
	}

	public void setProduceTech(String produceTech) {
		this.produceTech = produceTech;
	}

	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getVipLevel() {
		return this.vipLevel;
	}

	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getMainMaterial() {
		return this.mainMaterial;
	}

	public void setMainMaterial(String mainMaterial) {
		this.mainMaterial = mainMaterial;
	}

	public String getSupplySrc() {
		return this.supplySrc;
	}

	public void setSupplySrc(String supplySrc) {
		this.supplySrc = supplySrc;
	}

	public Double getSalesAmt() {
		return this.salesAmt;
	}

	public void setSalesAmt(Double salesAmt) {
		this.salesAmt = salesAmt;
	}

	public String getSalesRegion() {
		return this.salesRegion;
	}

	public void setSalesRegion(String salesRegion) {
		this.salesRegion = salesRegion;
	}

	public String getPsEnsurePrj() {
		return this.psEnsurePrj;
	}

	public void setPsEnsurePrj(String psEnsurePrj) {
		this.psEnsurePrj = psEnsurePrj;
	}

	public Double getPowerCostRatio() {
		return this.powerCostRatio;
	}

	public void setPowerCostRatio(Double powerCostRatio) {
		this.powerCostRatio = powerCostRatio;
	}

	public String getIndustryCode() {
		return this.industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

}