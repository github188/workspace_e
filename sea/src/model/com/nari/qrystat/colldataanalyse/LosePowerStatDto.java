package com.nari.qrystat.colldataanalyse;

import java.io.Serializable;
import java.util.Date;

/**
 * 台区线损数据值对象
 * @author 姜炜超
 */
public class LosePowerStatDto implements Serializable{
    private String tgId;//台区id
    private String tgNo;//台区编码
    private String tgName;//台区名
    private String orgName;//供电单位名称
	private String orgNo;//供电单位名称
    private String lineName;//线路名
    private Double ppq;//合计供电量
    private Double spq;//合计售电量
    private Double llr;//有损线损率=（损失电量/合计供电量）*100% 
    private Double lpq;//损失电量= 合计供电量-合计售电量 
    private Double idx;//线损指标
    private Double diff;//指标比差异=线损率-线损率指标
    private String date;//如果是主页面，则为电量明细，如果是弹出页面，则为查询日期或月份
    private Date startDate;//查询开始日期
    private Date endDate;//查询结束日期
    private String showDate;//界面曲线显示日期
    private Double readSuccRate;//抄表成功率
    private String consNo;//客户编号
    private String pubPrivFlag;//公专变标志
    private Double readPpq;//供电量
    private Double settleSpq;//售电量
    
	public String getTgId() {
		return tgId;
	}
	public void setTgId(String tgId) {
		this.tgId = tgId;
	}
	public String getTgNo() {
		return tgNo;
	}
	public void setTgNo(String tgNo) {
		this.tgNo = tgNo;
	}
	public String getTgName() {
		return tgName;
	}
	public void setTgName(String tgName) {
		this.tgName = tgName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public Double getPpq() {
		return ppq;
	}
	public void setPpq(Double ppq) {
		this.ppq = ppq;
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
	public String getShowDate() {
		return showDate;
	}
	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}
	public Double getReadSuccRate() {
		return readSuccRate;
	}
	public void setReadSuccRate(Double readSuccRate) {
		this.readSuccRate = readSuccRate;
	}
	public String getConsNo() {
		return consNo;
	}
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	public String getPubPrivFlag() {
		return pubPrivFlag;
	}
	public void setPubPrivFlag(String pubPrivFlag) {
		this.pubPrivFlag = pubPrivFlag;
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
