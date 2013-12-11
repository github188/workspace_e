package com.nari.eventbean;

/**
 * 下发任务参数
 */
public class SendTaskParamEvent {

	/* 申请编号 */
	private String appNo;
	
	/* 终端ID */
	private String terminalId;

	/* 终端资产号 */
	private String tmnlAssetNo;
	
	/* 测试流水号 */
	private Long debugId;

	public SendTaskParamEvent(String appNo, String terminalId,
			String tmnlAssetNo, Long debugId) {
		super();
		this.appNo = appNo;
		this.terminalId = terminalId;
		this.tmnlAssetNo = tmnlAssetNo;
		this.debugId = debugId;
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