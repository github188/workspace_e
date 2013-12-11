package com.nari.qrystat.colldataanalyse;

public class EDataTotalBean implements java.io.Serializable {
	
	private Long totalId;
	private Integer rowNum;
	private String tmnlAssetNo;
	private Short totalNo;
	private int freezeCycleNum;//冻结天数 

	public Long getTotalId() {
		return totalId;
	}

	public void setTotalId(Long totalId) {
		this.totalId = totalId;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public Short getTotalNo() {
		return this.totalNo;
	}

	public void setTotalNo(Short totalNo) {
		this.totalNo = totalNo;
	}

	public int getFreezeCycleNum() {
		return freezeCycleNum;
	}

	public void setFreezeCycleNum(int freezeCycleNum) {
		this.freezeCycleNum = freezeCycleNum;
	}
}