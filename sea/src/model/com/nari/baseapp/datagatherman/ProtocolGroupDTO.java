package com.nari.baseapp.datagatherman;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 透明规约数据项组合
 * @author 鲁兆淞
 */
public class ProtocolGroupDTO {
	private Long combiId; // 组合ID
	private String combiName; // 组合名称
	private String staffNo; // 创建人工号
	private Date createDate; // 创建时间
	private Integer validDays; // 有效天数
	private Boolean isShare; // 是否共享
	Map<String, String> combiMap; // 透明规约数据项

	public ProtocolGroupDTO() {
		this.combiMap = new LinkedHashMap<String, String>();
	}

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
	public Map<String, String> getCombiMap() {
		return this.combiMap;
	}
	public void setCombiMap(Map<String, String> combiMap) {
		this.combiMap = combiMap;
	}

}
