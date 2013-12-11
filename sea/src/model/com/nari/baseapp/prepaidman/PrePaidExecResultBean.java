package com.nari.baseapp.prepaidman;

public class PrePaidExecResultBean {
	private String keyId;//终端资产编号
	private String execFlag = "-999";
	private String statusCode;
	
	public PrePaidExecResultBean() {
		super();
	}

	public PrePaidExecResultBean(String keyId, String execFlag, String statusCode) {
		super();
		this.keyId = keyId;
		this.execFlag = execFlag;
		this.statusCode = statusCode;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getExecFlag() {
		return execFlag;
	}

	public void setExecFlag(String execFlag) {
		this.execFlag = execFlag;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
}
