package com.nari.device;

/**
 * DMeasBox entity. @author MyEclipse Persistence Tools
 */

public class DMeasBox implements java.io.Serializable {

	// Fields

	private Long id;
	private String assetNo;
	private String sortCode;
	private Integer boxCols;
	private Integer boxRows;
	private String deptNo;
	private String statusCode;
	private String instLoc;
	private String dataTypeCode;
	private String prOrgNo;

	// Constructors

	/** default constructor */
	public DMeasBox() {
	}

	/** full constructor */
	public DMeasBox(String assetNo, String sortCode, Integer boxCols,
			Integer boxRows, String deptNo, String statusCode, String instLoc,
			String dataTypeCode, String prOrgNo) {
		this.assetNo = assetNo;
		this.sortCode = sortCode;
		this.boxCols = boxCols;
		this.boxRows = boxRows;
		this.deptNo = deptNo;
		this.statusCode = statusCode;
		this.instLoc = instLoc;
		this.dataTypeCode = dataTypeCode;
		this.prOrgNo = prOrgNo;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssetNo() {
		return this.assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public String getSortCode() {
		return this.sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public Integer getBoxCols() {
		return this.boxCols;
	}

	public void setBoxCols(Integer boxCols) {
		this.boxCols = boxCols;
	}

	public Integer getBoxRows() {
		return this.boxRows;
	}

	public void setBoxRows(Integer boxRows) {
		this.boxRows = boxRows;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getInstLoc() {
		return this.instLoc;
	}

	public void setInstLoc(String instLoc) {
		this.instLoc = instLoc;
	}

	public String getDataTypeCode() {
		return this.dataTypeCode;
	}

	public void setDataTypeCode(String dataTypeCode) {
		this.dataTypeCode = dataTypeCode;
	}

	public String getPrOrgNo() {
		return this.prOrgNo;
	}

	public void setPrOrgNo(String prOrgNo) {
		this.prOrgNo = prOrgNo;
	}

}