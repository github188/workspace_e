package com.nari.eventbean;

public class CancelTmnlUserEvent {
	
	private String tmnlId;
	private String tmnlAssetNo;
	private String tmnlType;
	private String consType;
	private String consNo;
	private String appNo;
	private String tgId;
	private String cpNo;
	private String mpNo;
	
	public CancelTmnlUserEvent( String tmnlId, String tmnlAssetNo, String tmnlType, String consType, String consNo, String appNo, String tgId, String cpNo, String mpNo) {
		super();
		this.tmnlId = tmnlId;
		this.tmnlAssetNo = tmnlAssetNo;
		this.tmnlType = tmnlType;
		this.consType = consType;
		this.consNo = consNo;
		this.appNo = appNo;
		this.tgId = tgId;
		this.cpNo = cpNo;
		this.mpNo = mpNo;
	}

	public String getCpNo() {
		return cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

	public String getMpNo() {
		return mpNo;
	}

	public void setMpNo(String mpNo) {
		this.mpNo = mpNo;
	}

	public String getTmnlId() {
		return tmnlId;
	}

	public void setTmnlId(String tmnlId) {
		this.tmnlId = tmnlId;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getTmnlType() {
		return tmnlType;
	}

	public void setTmnlType(String tmnlType) {
		this.tmnlType = tmnlType;
	}

	public String getConsType() {
		return consType;
	}

	public void setConsType(String consType) {
		this.consType = consType;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getTgId() {
		return tgId;
	}

	public void setTgId(String tgId) {
		this.tgId = tgId;
	}
}