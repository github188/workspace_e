package com.nari.eventbean.hb;

/**
 * 终端抄读电表数据
 */
public class HBRelayMeterReadingEvent {

	/* 申请编号 */
	private String appNo;
	
	/* 终端ID */
	private String terminalId;
	

	/* 终端资产号 */
	private String tmnlAssetNo;

	
	/* 测试流水号 */
	private Long debugId;
	

	/*流程标识*/
	private String flowFlag;
	

	/*换表标识*/
	private String meterFlag;

	
	public HBRelayMeterReadingEvent(String appNo, String terminalId,
			String tmnlAssetNo, Long debugId, String flowFlag, String meterFlag) {
		super();
		this.appNo = appNo;
		this.terminalId = terminalId;
		this.tmnlAssetNo = tmnlAssetNo;
		this.debugId = debugId;
		this.flowFlag = flowFlag;
		this.meterFlag = meterFlag;
	}

	public String getMeterFlag() {
		return meterFlag;
	}

	public void setMeterFlag(String meterFlag) {
		this.meterFlag = meterFlag;
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

	public Long getDebugId() {
		return debugId;
	}

	public void setDebugId(Long debugId) {
		this.debugId = debugId;
	}
}