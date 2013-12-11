package com.nari.terminalparam;

/**
 * 终端安装数据值对象
 * @author 姜炜超
 */
public class ITmnlTask implements java.io.Serializable {
    private String orgNo;//供电单位编号
    private String statusCode;//状态编码
    private Double num;//总数
    
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
}
