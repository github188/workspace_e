package com.nari.basicdata;

/**
 * BSysDictionaryId entity. @author MyEclipse Persistence Tools
 */

public class BSysDictionaryId implements java.io.Serializable {

	// Fields

	private Long dictId;
	private String dictCatalog;
	private String dictCatalogName;
	private String dictNo;
	private String dictName;
	private String dictDesc;
	private String dictValue;
	private String minLimit;
	private String maxLimit;
	private Short dictSn;
	private Boolean isDefault;
	private Boolean isValid;
	private String orgNo;

	// Constructors

	/** default constructor */
	public BSysDictionaryId() {
	}

	/** minimal constructor */
	public BSysDictionaryId(Long dictId) {
		this.dictId = dictId;
	}

	/** full constructor */
	public BSysDictionaryId(Long dictId, String dictCatalog,
			String dictCatalogName, String dictNo, String dictName,
			String dictDesc, String dictValue, String minLimit,
			String maxLimit, Short dictSn, Boolean isDefault, Boolean isValid,
			String orgNo) {
		this.dictId = dictId;
		this.dictCatalog = dictCatalog;
		this.dictCatalogName = dictCatalogName;
		this.dictNo = dictNo;
		this.dictName = dictName;
		this.dictDesc = dictDesc;
		this.dictValue = dictValue;
		this.minLimit = minLimit;
		this.maxLimit = maxLimit;
		this.dictSn = dictSn;
		this.isDefault = isDefault;
		this.isValid = isValid;
		this.orgNo = orgNo;
	}

	// Property accessors

	public Long getDictId() {
		return this.dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	public String getDictCatalog() {
		return this.dictCatalog;
	}

	public void setDictCatalog(String dictCatalog) {
		this.dictCatalog = dictCatalog;
	}

	public String getDictCatalogName() {
		return this.dictCatalogName;
	}

	public void setDictCatalogName(String dictCatalogName) {
		this.dictCatalogName = dictCatalogName;
	}

	public String getDictNo() {
		return this.dictNo;
	}

	public void setDictNo(String dictNo) {
		this.dictNo = dictNo;
	}

	public String getDictName() {
		return this.dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictDesc() {
		return this.dictDesc;
	}

	public void setDictDesc(String dictDesc) {
		this.dictDesc = dictDesc;
	}

	public String getDictValue() {
		return this.dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getMinLimit() {
		return this.minLimit;
	}

	public void setMinLimit(String minLimit) {
		this.minLimit = minLimit;
	}

	public String getMaxLimit() {
		return this.maxLimit;
	}

	public void setMaxLimit(String maxLimit) {
		this.maxLimit = maxLimit;
	}

	public Short getDictSn() {
		return this.dictSn;
	}

	public void setDictSn(Short dictSn) {
		this.dictSn = dictSn;
	}

	public Boolean getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BSysDictionaryId))
			return false;
		BSysDictionaryId castOther = (BSysDictionaryId) other;

		return ((this.getDictId() == castOther.getDictId()) || (this
				.getDictId() != null
				&& castOther.getDictId() != null && this.getDictId().equals(
				castOther.getDictId())))
				&& ((this.getDictCatalog() == castOther.getDictCatalog()) || (this
						.getDictCatalog() != null
						&& castOther.getDictCatalog() != null && this
						.getDictCatalog().equals(castOther.getDictCatalog())))
				&& ((this.getDictCatalogName() == castOther
						.getDictCatalogName()) || (this.getDictCatalogName() != null
						&& castOther.getDictCatalogName() != null && this
						.getDictCatalogName().equals(
								castOther.getDictCatalogName())))
				&& ((this.getDictNo() == castOther.getDictNo()) || (this
						.getDictNo() != null
						&& castOther.getDictNo() != null && this.getDictNo()
						.equals(castOther.getDictNo())))
				&& ((this.getDictName() == castOther.getDictName()) || (this
						.getDictName() != null
						&& castOther.getDictName() != null && this
						.getDictName().equals(castOther.getDictName())))
				&& ((this.getDictDesc() == castOther.getDictDesc()) || (this
						.getDictDesc() != null
						&& castOther.getDictDesc() != null && this
						.getDictDesc().equals(castOther.getDictDesc())))
				&& ((this.getDictValue() == castOther.getDictValue()) || (this
						.getDictValue() != null
						&& castOther.getDictValue() != null && this
						.getDictValue().equals(castOther.getDictValue())))
				&& ((this.getMinLimit() == castOther.getMinLimit()) || (this
						.getMinLimit() != null
						&& castOther.getMinLimit() != null && this
						.getMinLimit().equals(castOther.getMinLimit())))
				&& ((this.getMaxLimit() == castOther.getMaxLimit()) || (this
						.getMaxLimit() != null
						&& castOther.getMaxLimit() != null && this
						.getMaxLimit().equals(castOther.getMaxLimit())))
				&& ((this.getDictSn() == castOther.getDictSn()) || (this
						.getDictSn() != null
						&& castOther.getDictSn() != null && this.getDictSn()
						.equals(castOther.getDictSn())))
				&& ((this.getIsDefault() == castOther.getIsDefault()) || (this
						.getIsDefault() != null
						&& castOther.getIsDefault() != null && this
						.getIsDefault().equals(castOther.getIsDefault())))
				&& ((this.getIsValid() == castOther.getIsValid()) || (this
						.getIsValid() != null
						&& castOther.getIsValid() != null && this.getIsValid()
						.equals(castOther.getIsValid())))
				&& ((this.getOrgNo() == castOther.getOrgNo()) || (this
						.getOrgNo() != null
						&& castOther.getOrgNo() != null && this.getOrgNo()
						.equals(castOther.getOrgNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDictId() == null ? 0 : this.getDictId().hashCode());
		result = 37
				* result
				+ (getDictCatalog() == null ? 0 : this.getDictCatalog()
						.hashCode());
		result = 37
				* result
				+ (getDictCatalogName() == null ? 0 : this.getDictCatalogName()
						.hashCode());
		result = 37 * result
				+ (getDictNo() == null ? 0 : this.getDictNo().hashCode());
		result = 37 * result
				+ (getDictName() == null ? 0 : this.getDictName().hashCode());
		result = 37 * result
				+ (getDictDesc() == null ? 0 : this.getDictDesc().hashCode());
		result = 37 * result
				+ (getDictValue() == null ? 0 : this.getDictValue().hashCode());
		result = 37 * result
				+ (getMinLimit() == null ? 0 : this.getMinLimit().hashCode());
		result = 37 * result
				+ (getMaxLimit() == null ? 0 : this.getMaxLimit().hashCode());
		result = 37 * result
				+ (getDictSn() == null ? 0 : this.getDictSn().hashCode());
		result = 37 * result
				+ (getIsDefault() == null ? 0 : this.getIsDefault().hashCode());
		result = 37 * result
				+ (getIsValid() == null ? 0 : this.getIsValid().hashCode());
		result = 37 * result
				+ (getOrgNo() == null ? 0 : this.getOrgNo().hashCode());
		return result;
	}

}