package com.nari.terminalparam;

import java.util.Date;

import com.nari.basicdata.BClearProtocol;

public class TDataCombi implements java.io.Serializable {
	private static final long serialVersionUID = 878051142162434870L;

	private Long combiId;
	private String combiName;
	private String clearProtNo;
	private String staffNo;
	private Date createDate;
	private Integer validDays;
	private Boolean isShare;
	private BClearProtocol bClearProtocol;

	public Long getCombiId() {
		return this.combiId;
	}
	public void setCombiId(Long combiId) {
		this.combiId = combiId;
	}
	public String getCombiName() {
		return this.combiName;
	}
	public void setCombiName(String combiName) {
		this.combiName = combiName;
	}
	public String getClearProtNo() {
		return this.clearProtNo;
	}
	public void setClearProtNo(String clearProtNo) {
		this.clearProtNo = clearProtNo;
	}
	public String getStaffNo() {
		return this.staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	public Date getCreateDate() {
		return this.createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getValidDays() {
		return this.validDays;
	}
	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}
	public Boolean getIsShare() {
		return this.isShare;
	}
	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}
	public BClearProtocol getbClearProtocol() {
		return this.bClearProtocol;
	}
	public void setbClearProtocol(BClearProtocol bClearProtocol) {
		this.bClearProtocol = bClearProtocol;
	}

}