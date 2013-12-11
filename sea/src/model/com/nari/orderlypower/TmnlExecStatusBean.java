package com.nari.orderlypower;

public class TmnlExecStatusBean {
	private String keyId;// 记录主键
	private String execFlag = "-999";//0,解除，1投入，2参数下发成功，-1未控制
	
	public TmnlExecStatusBean() {
	}

	public TmnlExecStatusBean(String keyId, String execFlag) {
		this.keyId = keyId;
		this.execFlag = execFlag;
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
}
