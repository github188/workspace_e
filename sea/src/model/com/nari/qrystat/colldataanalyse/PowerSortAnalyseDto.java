package com.nari.qrystat.colldataanalyse;

public class PowerSortAnalyseDto {
    private int sort;//排名
    private String consNo;//用户编号
    private String consName;//用户名称
    private Double runCap;//运行容量
    private String tradeName;//行业名称
    private String voltGrade;//电压等级
    private Double bqyd;//本期用电
    private Double tqyd;//同期用电
    private Double bqtb;//本期同比=（本期用电-同期用电）/同期用电
    private Double powerRate;//本期用电占用比率=本期用电/总用电量
    
    public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
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
	public Double getRunCap() {
		return runCap;
	}
	public void setRunCap(Double runCap) {
		this.runCap = runCap;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getVoltGrade() {
		return voltGrade;
	}
	public void setVoltGrade(String voltGrade) {
		this.voltGrade = voltGrade;
	}
	public Double getPowerRate() {
		return powerRate;
	}
	public void setPowerRate(Double powerRate) {
		this.powerRate = powerRate;
	}
	public Double getBqyd() {
		return bqyd;
	}
	public void setBqyd(Double bqyd) {
		this.bqyd = bqyd;
	}
	public Double getBqtb() {
		return bqtb;
	}
	public void setBqtb(Double bqtb) {
		this.bqtb = bqtb;
	}
	public Double getTqyd() {
		return tqyd;
	}
	public void setTqyd(Double tqyd) {
		this.tqyd = tqyd;
	}
}
