package com.nari.baseapp.prepaidman;

public class PrePaidExecStatBean {
	private String orgType;             //供电单位类型
	private String orgNo;				//供电单位编号
	private String orgName;             //供电单位名称
	private long succFeeCnt;            //执行成功购电笔数
	private long failFeeCnt;            //执行失败购电笔数
    private long uncompleteFeeCnt;      //未完成执行购电笔数
	private float succRatio;            //执行成功率
	
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public long getSuccFeeCnt() {
		return succFeeCnt;
	}
	public void setSuccFeeCnt(long succFeeCnt) {
		this.succFeeCnt = succFeeCnt;
	}
	public long getFailFeeCnt() {
		return failFeeCnt;
	}
	public void setFailFeeCnt(long failFeeCnt) {
		this.failFeeCnt = failFeeCnt;
	}
	public long getUncompleteFeeCnt() {
		return uncompleteFeeCnt;
	}
	public void setUncompleteFeeCnt(long uncompleteFeeCnt) {
		this.uncompleteFeeCnt = uncompleteFeeCnt;
	}
	public float getSuccRatio() {
		return succRatio;
	}
	public void setSuccRatio(float succRatio) {
		this.succRatio = succRatio;
	}
    
}
