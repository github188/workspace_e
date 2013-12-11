package com.nari.baseapp.prepaidman;

/**
 * 预付费投入调试用户操作终端记录数据值对象
 * @author 姜炜超
 */
public class PPDebugOperaBean {
    private String consNo;
    private String consName;
    private String tmnlAssetNo;
    private String tmnlAddr;
    private String operaDate;
    private String operaContent;
    private String status;
    
	public String getConsNo() {
		return consNo;
	}
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	public String getConsName() {
		return consName;
	}
	public void setConsName(String consName) {
		this.consName = consName;
	}
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	public String getTmnlAddr() {
		return tmnlAddr;
	}
	public void setTmnlAddr(String tmnlAddr) {
		this.tmnlAddr = tmnlAddr;
	}
	public String getOperaDate() {
		return operaDate;
	}
	public void setOperaDate(String operaDate) {
		this.operaDate = operaDate;
	}
	public String getOperaContent() {
		return operaContent;
	}
	public void setOperaContent(String operaContent) {
		this.operaContent = operaContent;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
