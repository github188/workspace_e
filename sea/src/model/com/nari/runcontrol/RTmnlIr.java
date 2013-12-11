package com.nari.runcontrol;

import java.util.Date;

/**
 * RTmnlIr entity. @author MyEclipse Persistence Tools
 */

public class RTmnlIr implements java.io.Serializable {

	// Fields

	private Long termIrId;
	private Long irTaskId;
	private String cpNo;
	private String irFlag;
	private Date assignDate;
	private Date irDate;
	private String empNo;
	private Date finishDate;

	// Constructors

	/** default constructor */
	public RTmnlIr() {
	}

	/** minimal constructor */
	public RTmnlIr(Long irTaskId, String cpNo, Date assignDate, Date irDate,
			String empNo) {
		this.irTaskId = irTaskId;
		this.cpNo = cpNo;
		this.assignDate = assignDate;
		this.irDate = irDate;
		this.empNo = empNo;
	}

	/** full constructor */
	public RTmnlIr(Long irTaskId, String cpNo, String irFlag, Date assignDate,
			Date irDate, String empNo, Date finishDate) {
		this.irTaskId = irTaskId;
		this.cpNo = cpNo;
		this.irFlag = irFlag;
		this.assignDate = assignDate;
		this.irDate = irDate;
		this.empNo = empNo;
		this.finishDate = finishDate;
	}

	// Property accessors

	public Long getTermIrId() {
		return this.termIrId;
	}

	public void setTermIrId(Long termIrId) {
		this.termIrId = termIrId;
	}

	public Long getIrTaskId() {
		return this.irTaskId;
	}

	public void setIrTaskId(Long irTaskId) {
		this.irTaskId = irTaskId;
	}

	public String getCpNo() {
		return this.cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

	public String getIrFlag() {
		return this.irFlag;
	}

	public void setIrFlag(String irFlag) {
		this.irFlag = irFlag;
	}

	public Date getAssignDate() {
		return this.assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public Date getIrDate() {
		return this.irDate;
	}

	public void setIrDate(Date irDate) {
		this.irDate = irDate;
	}

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

}