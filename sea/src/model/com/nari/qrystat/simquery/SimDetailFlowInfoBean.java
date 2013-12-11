package com.nari.qrystat.simquery;

import java.util.Date;

public class SimDetailFlowInfoBean {
	private String simNo;// SIM卡号
	private Date flowDate;// 查询时间
	private String downFlow;// 下行流量
	private String downMsg;// 下行报文
	private String upFlow;// 上行流量
	private String upMsg;// 上行报文
	private String allFlow;// 全部流量

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public Date getFlowDate() {
		return flowDate;
	}

	public void setFlowDate(Date flowDate) {
		this.flowDate = flowDate;
	}

	public String getDownFlow() {
		return downFlow;
	}

	public void setDownFlow(String downFlow) {
		this.downFlow = downFlow;
	}

	public String getDownMsg() {
		return downMsg;
	}

	public void setDownMsg(String downMsg) {
		this.downMsg = downMsg;
	}

	public String getUpFlow() {
		return upFlow;
	}

	public void setUpFlow(String upFlow) {
		this.upFlow = upFlow;
	}

	public String getUpMsg() {
		return upMsg;
	}

	public void setUpMsg(String upMsg) {
		this.upMsg = upMsg;
	}

	public String getAllFlow() {
		return allFlow;
	}

	public void setAllFlow(String allFlow) {
		this.allFlow = allFlow;
	}
}
