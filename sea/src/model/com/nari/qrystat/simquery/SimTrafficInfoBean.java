package com.nari.qrystat.simquery;

public class SimTrafficInfoBean {
	private String orgNo;// 单位编号
	private String orgName;// 供电单位名称
	private String flowDate;// 查询时间
	private Float downFlow;// 下行流量
	private String downMsg;// 下行报文
	private Float upFlow;// 上行流量
	private String upMsg;// 上行报文
	private Float allFlow;// 全部流量
	private Float baseFlow;// 基准流量
	private Float overFlow;// 超流量
	private Float baseFee;// 基准费
	private Float overFee;// 超费用

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getFlowDate() {
		return flowDate;
	}

	public void setFlowDate(String flowDate) {
		this.flowDate = flowDate;
	}

	public Float getDownFlow() {
		return downFlow;
	}

	public void setDownFlow(Float downFlow) {
		this.downFlow = downFlow;
	}

	public String getDownMsg() {
		return downMsg;
	}

	public void setDownMsg(String downMsg) {
		this.downMsg = downMsg;
	}

	public Float getUpFlow() {
		return upFlow;
	}

	public void setUpFlow(Float upFlow) {
		this.upFlow = upFlow;
	}

	public String getUpMsg() {
		return upMsg;
	}

	public void setUpMsg(String upMsg) {
		this.upMsg = upMsg;
	}

	public Float getAllFlow() {
		return allFlow;
	}

	public void setAllFlow(Float allFlow) {
		this.allFlow = allFlow;
	}

	public Float getOverFlow() {
		return overFlow;
	}

	public void setOverFlow(Float overFlow) {
		this.overFlow = overFlow;
	}

	public Float getBaseFee() {
		return baseFee;
	}

	public void setBaseFee(Float baseFee) {
		this.baseFee = baseFee;
	}

	public Float getOverFee() {
		return overFee;
	}

	public void setOverFee(Float overFee) {
		this.overFee = overFee;
	}

	public Float getBaseFlow() {
		return baseFlow;
	}

	public void setBaseFlow(Float baseFlow) {
		this.baseFlow = baseFlow;
	}
}
