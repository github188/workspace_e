package com.nari.terminalparam;


/**
 * 群组Dto
 * @author 姜海辉
 *
 */
public class TTmnlGroupDto {

	private String groupNo;
	private String groupName;
	private String orgName;
	private String ctrlSchemeType;
	private String isShare;
	private String staffNo;
	private String startDate;
	private String finishDate;
	//private String groupType;
	public String getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCtrlSchemeType() {
		return ctrlSchemeType;
	}
	public void setCtrlSchemeType(String ctrlSchemeType) {
		this.ctrlSchemeType = ctrlSchemeType;
	}
//	public String getGroupType() {
//		return groupType;
//	}
//	public void setGroupType(String groupType) {
//		this.groupType = groupType;
//	}
	public String getIsShare() {
		return isShare;
	}
	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}
	
}
