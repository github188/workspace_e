package com.nari.orgnization;

import java.util.Date;

/**
 * OStaff entity. @author MyEclipse Persistence Tools
 */

public class OStaff implements java.io.Serializable {

	// Fields

	private String empNo;
	private String deptNo;
	private String staffNo;
	private String name;
	private String gender;
	private String posName;
	private String position;
	private String workTypeCode;
	private String techLevelCode;
	private Date ymd;
	private String degreeCode;
	private String mobile;
	private String officeTelNo;
	private String srvLevelCode;
	private String certFlag;
	private String fixedFlag;
	private String onPosFlag;
	private String professionCode;
	private Date professionBgnDate;
	private Date joinDate;
	private String titel;
	private String politicalStatusCode;
	private String titleLevelCode;
	private String statusCode;
	private String remark;

	// Constructors

	/** default constructor */
	public OStaff() {
	}

	/** minimal constructor */
	public OStaff(String deptNo) {
		this.deptNo = deptNo;
	}

	/** full constructor */
	public OStaff(String deptNo, String staffNo, String name, String gender,
			String posName, String position, String workTypeCode,
			String techLevelCode, Date ymd, String degreeCode, String mobile,
			String officeTelNo, String srvLevelCode, String certFlag,
			String fixedFlag, String onPosFlag, String professionCode,
			Date professionBgnDate, Date joinDate, String titel,
			String politicalStatusCode, String titleLevelCode,
			String statusCode, String remark) {
		this.deptNo = deptNo;
		this.staffNo = staffNo;
		this.name = name;
		this.gender = gender;
		this.posName = posName;
		this.position = position;
		this.workTypeCode = workTypeCode;
		this.techLevelCode = techLevelCode;
		this.ymd = ymd;
		this.degreeCode = degreeCode;
		this.mobile = mobile;
		this.officeTelNo = officeTelNo;
		this.srvLevelCode = srvLevelCode;
		this.certFlag = certFlag;
		this.fixedFlag = fixedFlag;
		this.onPosFlag = onPosFlag;
		this.professionCode = professionCode;
		this.professionBgnDate = professionBgnDate;
		this.joinDate = joinDate;
		this.titel = titel;
		this.politicalStatusCode = politicalStatusCode;
		this.titleLevelCode = titleLevelCode;
		this.statusCode = statusCode;
		this.remark = remark;
	}

	// Property accessors

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getStaffNo() {
		return this.staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPosName() {
		return this.posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getWorkTypeCode() {
		return this.workTypeCode;
	}

	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}

	public String getTechLevelCode() {
		return this.techLevelCode;
	}

	public void setTechLevelCode(String techLevelCode) {
		this.techLevelCode = techLevelCode;
	}

	public Date getYmd() {
		return this.ymd;
	}

	public void setYmd(Date ymd) {
		this.ymd = ymd;
	}

	public String getDegreeCode() {
		return this.degreeCode;
	}

	public void setDegreeCode(String degreeCode) {
		this.degreeCode = degreeCode;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOfficeTelNo() {
		return this.officeTelNo;
	}

	public void setOfficeTelNo(String officeTelNo) {
		this.officeTelNo = officeTelNo;
	}

	public String getSrvLevelCode() {
		return this.srvLevelCode;
	}

	public void setSrvLevelCode(String srvLevelCode) {
		this.srvLevelCode = srvLevelCode;
	}

	public String getCertFlag() {
		return this.certFlag;
	}

	public void setCertFlag(String certFlag) {
		this.certFlag = certFlag;
	}

	public String getFixedFlag() {
		return this.fixedFlag;
	}

	public void setFixedFlag(String fixedFlag) {
		this.fixedFlag = fixedFlag;
	}

	public String getOnPosFlag() {
		return this.onPosFlag;
	}

	public void setOnPosFlag(String onPosFlag) {
		this.onPosFlag = onPosFlag;
	}

	public String getProfessionCode() {
		return this.professionCode;
	}

	public void setProfessionCode(String professionCode) {
		this.professionCode = professionCode;
	}

	public Date getProfessionBgnDate() {
		return this.professionBgnDate;
	}

	public void setProfessionBgnDate(Date professionBgnDate) {
		this.professionBgnDate = professionBgnDate;
	}

	public Date getJoinDate() {
		return this.joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getTitel() {
		return this.titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getPoliticalStatusCode() {
		return this.politicalStatusCode;
	}

	public void setPoliticalStatusCode(String politicalStatusCode) {
		this.politicalStatusCode = politicalStatusCode;
	}

	public String getTitleLevelCode() {
		return this.titleLevelCode;
	}

	public void setTitleLevelCode(String titleLevelCode) {
		this.titleLevelCode = titleLevelCode;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}