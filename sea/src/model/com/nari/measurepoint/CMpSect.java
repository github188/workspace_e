package com.nari.measurepoint;

import java.util.Date;

/**
 * CMpSect entity. @author MyEclipse Persistence Tools
 */

public class CMpSect implements java.io.Serializable {

	// Fields

	private Long mpSectId;
	private String mpSectNo;
	private String name;
	private String orgNo;
	private String mpSectAttr;
	private String groupNo;
	private String groupRemark;
	private String validFlag;
	private Date statusDate;

	// Constructors

	/** default constructor */
	public CMpSect() {
	}

	/** minimal constructor */
	public CMpSect(String mpSectNo) {
		this.mpSectNo = mpSectNo;
	}

	/** full constructor */
	public CMpSect(String mpSectNo, String name, String orgNo,
			String mpSectAttr, String groupNo, String groupRemark,
			String validFlag, Date statusDate) {
		this.mpSectNo = mpSectNo;
		this.name = name;
		this.orgNo = orgNo;
		this.mpSectAttr = mpSectAttr;
		this.groupNo = groupNo;
		this.groupRemark = groupRemark;
		this.validFlag = validFlag;
		this.statusDate = statusDate;
	}

	// Property accessors

	public Long getMpSectId() {
		return this.mpSectId;
	}

	public void setMpSectId(Long mpSectId) {
		this.mpSectId = mpSectId;
	}

	public String getMpSectNo() {
		return this.mpSectNo;
	}

	public void setMpSectNo(String mpSectNo) {
		this.mpSectNo = mpSectNo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getMpSectAttr() {
		return this.mpSectAttr;
	}

	public void setMpSectAttr(String mpSectAttr) {
		this.mpSectAttr = mpSectAttr;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupRemark() {
		return this.groupRemark;
	}

	public void setGroupRemark(String groupRemark) {
		this.groupRemark = groupRemark;
	}

	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public Date getStatusDate() {
		return this.statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

}