package com.nari.advapp.statanalyse;
/**
 * @category 负荷统计Bean
 * @author 陈国章
 *
 */

public class LoadStat {
	private String orgName;
	private String lineName;
	private String volt;
	private String statDate;
	private float maxP;
	private String maxTime;
	private float minP;
	private String minTime;
	private float pvRatio;
	private float loadRatio;
	private float pape2;
	private float pape4;
	private float avgP;
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
	public String getVolt() {
		return volt;
	}
	public void setVolt(String volt) {
		this.volt = volt;
	}
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	public float getMaxP() {
		return maxP;
	}
	public void setMaxP(float maxP) {
		this.maxP = maxP;
	}
	public String getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}
	public float getMinP() {
		return minP;
	}
	public void setMinP(float minP) {
		this.minP = minP;
	}
	public String getMinTime() {
		return minTime;
	}
	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}
	public float getPvRatio() {
		return pvRatio;
	}
	public void setPvRatio(float pvRatio) {
		this.pvRatio = pvRatio;
	}
	public float getLoadRatio() {
		return loadRatio;
	}
	public void setLoadRatio(float loadRatio) {
		this.loadRatio = loadRatio;
	}
	public float getPape2() {
		return pape2;
	}
	public void setPape2(float pape2) {
		this.pape2 = pape2;
	}
	public float getPape4() {
		return pape4;
	}
	public void setPape4(float pape4) {
		this.pape4 = pape4;
	}
	public float getAvgP() {
		return avgP;
	}
	public void setAvgP(float avgP) {
		this.avgP = avgP;
	}
	public void computeLoadRatio()
	{
		if(this.maxP==0)
		{
			this.loadRatio=0;
		}else{
			this.loadRatio=this.avgP/this.maxP;			
		}
	}
	public void computePvRatio()
	{
		if(this.pape4==0)
		{
			this.loadRatio=0;
		}else{
			this.loadRatio=(this.pape2-this.pape4)/this.pape2;			
		}
	}
	
}
