package com.nari.qrystat.colldataanalyse;

import java.util.Date;

/**
 * 线路线损数据值对象
 * @author 姜炜超
 */
public class LineLosePowerBean {
	private String lineId;//线路id
    private String lineNo;//线路编码
    private String lineName;//线路名
    private String orgName;//供电单位名称
    private String orgNo;//供电单位
    private Double supplypq;//该条线路所有供电点电量之和
    private Double spq;//线路售电量
    private Double llr;//有损线损率=（损失电量/合计供电量）*100% 
    private Double lpq;//损失电量= 供电量-售电量 
    private Double idx;//线损指标
    private Double diff;//指标比差异=线损率-线损率指标
    private String date;//统计日期
    private Double readSuccRate;//抄表成功率
    private Date startDate;//查询开始日期
    private Date endDate;//查询结束日期
    private Double readPpq;//供电量
    private Double settleSpq;//售电量
    
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Double getSupplypq() {
		return supplypq;
	}
	public void setSupplypq(Double supplypq) {
		this.supplypq = supplypq;
	}
	public Double getSpq() {
		return spq;
	}
	public void setSpq(Double spq) {
		this.spq = spq;
	}
	public Double getLlr() {
		return llr;
	}
	public void setLlr(Double llr) {
		this.llr = llr;
	}
	public Double getLpq() {
		return lpq;
	}
	public void setLpq(Double lpq) {
		this.lpq = lpq;
	}
	public Double getIdx() {
		return idx;
	}
	public void setIdx(Double idx) {
		this.idx = idx;
	}
	public Double getDiff() {
		return diff;
	}
	public void setDiff(Double diff) {
		this.diff = diff;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Double getReadSuccRate() {
		return readSuccRate;
	}
	public void setReadSuccRate(Double readSuccRate) {
		this.readSuccRate = readSuccRate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getReadPpq() {
		return readPpq;
	}
	public void setReadPpq(Double readPpq) {
		this.readPpq = readPpq;
	}
	public Double getSettleSpq() {
		return settleSpq;
	}
	public void setSettleSpq(Double settleSpq) {
		this.settleSpq = settleSpq;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
}
