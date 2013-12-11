package com.nari.eventbean;

/**
 * 广播抄表
 */
public class BroadcastMeterReadingEvent {

	/* 申请编号 */
	private String appNo;

	/* 终端资产号 */
	private String tmnlAssetNo;

	/* 表计资产号 */
	private String ameterAssetNo;
	
	/* 测量点号 */
	private String mpSn;
	
	/* 测试流水号 */
	private Long debugId;

	public BroadcastMeterReadingEvent(String appNo, String tmnlAssetNo,
			String ameterAssetNo, String mpSn, Long debugId) {
		super();
		this.appNo = appNo;
		this.tmnlAssetNo = tmnlAssetNo;
		this.ameterAssetNo = ameterAssetNo;
		this.mpSn = mpSn;
		this.debugId = debugId;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getAmeterAssetNo() {
		return ameterAssetNo;
	}

	public void setAmeterAssetNo(String ameterAssetNo) {
		this.ameterAssetNo = ameterAssetNo;
	}

	public String getMpSn() {
		return mpSn;
	}

	public void setMpSn(String mpSn) {
		this.mpSn = mpSn;
	}

	public Long getDebugId() {
		return debugId;
	}

	public void setDebugId(Long debugId) {
		this.debugId = debugId;
	}
}