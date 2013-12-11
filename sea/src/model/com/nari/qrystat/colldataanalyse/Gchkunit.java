package com.nari.qrystat.colldataanalyse;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 通过此实体向g_chkunit中插入数据
 * @author huangxuan
 *
 */
@Table(name="g_chkunit")
public class Gchkunit {
	@Id
	@Column(name="chkunit_id")
	@GeneratedValue(generator="s_g_chkunit")
	private Integer chkunitId;
	@Column(name="chkunit_type_code")
	private String chkunitTypeCode;
	@Column(name="chkunit_name")
	private String chkunitName;
	@Column(name="org_no")
	private String orgNo;
	@Column(name="exam_flag")
	private String examFlag;
	@Column(name="link_flag")
	private String linkFlag;
	@Column(name="resp_emp_no")
	private String respEmpNo;
	@Column(name="chk_cycle")
	private String chkCycle;
	@Column(name="det_sort_code")
	private String detSortCode;
	@Column(name="data_src")
	private String dataSrc;
	@Column(name="STATUS_CODE")
	private String statusCode;
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public Integer getChkunitId() {
		return chkunitId;
	}
	public void setChkunitId(Integer chkunitId) {
		this.chkunitId = chkunitId;
	}
	public String getChkunitTypeCode() {
		return chkunitTypeCode;
	}
	public void setChkunitTypeCode(String chkunitTypeCode) {
		this.chkunitTypeCode = chkunitTypeCode;
	}
	public String getChkunitName() {
		return chkunitName;
	}
	public void setChkunitName(String chkunitName) {
		this.chkunitName = chkunitName;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getExamFlag() {
		return examFlag;
	}
	public void setExamFlag(String examFlag) {
		this.examFlag = examFlag;
	}
	public String getLinkFlag() {
		return linkFlag;
	}
	public void setLinkFlag(String linkFlag) {
		this.linkFlag = linkFlag;
	}
	public String getRespEmpNo() {
		return respEmpNo;
	}
	public void setRespEmpNo(String respEmpNo) {
		this.respEmpNo = respEmpNo;
	}
	public String getChkCycle() {
		return chkCycle;
	}
	public void setChkCycle(String chkCycle) {
		this.chkCycle = chkCycle;
	}
	public String getDetSortCode() {
		return detSortCode;
	}
	public void setDetSortCode(String detSortCode) {
		this.detSortCode = detSortCode;
	}
	public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	
}
