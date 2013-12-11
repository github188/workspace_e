package com.nari.statreport;

import java.util.Date;

/**
 * ATmnlFlowMId entity. @author MyEclipse Persistence Tools
 */

public class ATmnlFlowMId implements java.io.Serializable {

	// Fields

	private String orgNo;
	private String tmnlAssetNo;
	private String terminalAddr;
	private Date flowDate;
	private String commMode;
	private Integer downFlow;
	private Integer downMsg;
	private Integer upFlow;
	private Integer upMsg;

	// Constructors

	/** default constructor */
	public ATmnlFlowMId() {
	}

	/** minimal constructor */
	public ATmnlFlowMId(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	/** full constructor */
	public ATmnlFlowMId(String orgNo, String tmnlAssetNo, String terminalAddr,
			Date flowDate, String commMode, Integer downFlow, Integer downMsg,
			Integer upFlow, Integer upMsg) {
		this.orgNo = orgNo;
		this.tmnlAssetNo = tmnlAssetNo;
		this.terminalAddr = terminalAddr;
		this.flowDate = flowDate;
		this.commMode = commMode;
		this.downFlow = downFlow;
		this.downMsg = downMsg;
		this.upFlow = upFlow;
		this.upMsg = upMsg;
	}

	// Property accessors

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getTmnlAssetNo() {
		return this.tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getTerminalAddr() {
		return this.terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	public Date getFlowDate() {
		return this.flowDate;
	}

	public void setFlowDate(Date flowDate) {
		this.flowDate = flowDate;
	}

	public String getCommMode() {
		return this.commMode;
	}

	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

	public Integer getDownFlow() {
		return this.downFlow;
	}

	public void setDownFlow(Integer downFlow) {
		this.downFlow = downFlow;
	}

	public Integer getDownMsg() {
		return this.downMsg;
	}

	public void setDownMsg(Integer downMsg) {
		this.downMsg = downMsg;
	}

	public Integer getUpFlow() {
		return this.upFlow;
	}

	public void setUpFlow(Integer upFlow) {
		this.upFlow = upFlow;
	}

	public Integer getUpMsg() {
		return this.upMsg;
	}

	public void setUpMsg(Integer upMsg) {
		this.upMsg = upMsg;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ATmnlFlowMId))
			return false;
		ATmnlFlowMId castOther = (ATmnlFlowMId) other;

		return ((this.getOrgNo() == castOther.getOrgNo()) || (this.getOrgNo() != null
				&& castOther.getOrgNo() != null && this.getOrgNo().equals(
				castOther.getOrgNo())))
				&& ((this.getTmnlAssetNo() == castOther.getTmnlAssetNo()) || (this
						.getTmnlAssetNo() != null
						&& castOther.getTmnlAssetNo() != null && this
						.getTmnlAssetNo().equals(castOther.getTmnlAssetNo())))
				&& ((this.getTerminalAddr() == castOther.getTerminalAddr()) || (this
						.getTerminalAddr() != null
						&& castOther.getTerminalAddr() != null && this
						.getTerminalAddr().equals(castOther.getTerminalAddr())))
				&& ((this.getFlowDate() == castOther.getFlowDate()) || (this
						.getFlowDate() != null
						&& castOther.getFlowDate() != null && this
						.getFlowDate().equals(castOther.getFlowDate())))
				&& ((this.getCommMode() == castOther.getCommMode()) || (this
						.getCommMode() != null
						&& castOther.getCommMode() != null && this
						.getCommMode().equals(castOther.getCommMode())))
				&& ((this.getDownFlow() == castOther.getDownFlow()) || (this
						.getDownFlow() != null
						&& castOther.getDownFlow() != null && this
						.getDownFlow().equals(castOther.getDownFlow())))
				&& ((this.getDownMsg() == castOther.getDownMsg()) || (this
						.getDownMsg() != null
						&& castOther.getDownMsg() != null && this.getDownMsg()
						.equals(castOther.getDownMsg())))
				&& ((this.getUpFlow() == castOther.getUpFlow()) || (this
						.getUpFlow() != null
						&& castOther.getUpFlow() != null && this.getUpFlow()
						.equals(castOther.getUpFlow())))
				&& ((this.getUpMsg() == castOther.getUpMsg()) || (this
						.getUpMsg() != null
						&& castOther.getUpMsg() != null && this.getUpMsg()
						.equals(castOther.getUpMsg())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getOrgNo() == null ? 0 : this.getOrgNo().hashCode());
		result = 37
				* result
				+ (getTmnlAssetNo() == null ? 0 : this.getTmnlAssetNo()
						.hashCode());
		result = 37
				* result
				+ (getTerminalAddr() == null ? 0 : this.getTerminalAddr()
						.hashCode());
		result = 37 * result
				+ (getFlowDate() == null ? 0 : this.getFlowDate().hashCode());
		result = 37 * result
				+ (getCommMode() == null ? 0 : this.getCommMode().hashCode());
		result = 37 * result
				+ (getDownFlow() == null ? 0 : this.getDownFlow().hashCode());
		result = 37 * result
				+ (getDownMsg() == null ? 0 : this.getDownMsg().hashCode());
		result = 37 * result
				+ (getUpFlow() == null ? 0 : this.getUpFlow().hashCode());
		result = 37 * result
				+ (getUpMsg() == null ? 0 : this.getUpMsg().hashCode());
		return result;
	}

}