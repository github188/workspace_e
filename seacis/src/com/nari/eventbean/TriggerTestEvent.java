package com.nari.eventbean;

/**
 * 触发测试事件BEAN
 */
public class TriggerTestEvent {

	/* 申请编号 */
	private String appNo;

	/* 终端编号 */
	private String terminalId;

	/* 终端资产号 */
	private String tmnlAssetNo;
	
	/*流程标识*/
	private String flowFlag;
	

	public TriggerTestEvent(String appNo, String terminalId,
			String tmnlAssetNo, String flowFlag) {
		super();
		this.appNo = appNo;
		this.terminalId = terminalId;
		this.tmnlAssetNo = tmnlAssetNo;
		this.flowFlag = flowFlag;
	}
	
	public String getFlowFlag() {
		return flowFlag;
	}

	public void setFlowFlag(String flowFlag) {
		this.flowFlag = flowFlag;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
}