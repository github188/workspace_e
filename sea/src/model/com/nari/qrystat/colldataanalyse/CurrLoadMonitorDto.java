package com.nari.qrystat.colldataanalyse;

import java.io.Serializable;

/**
 * 当日用电负荷监测数据值对象
 * @author 姜炜超
 */
public class CurrLoadMonitorDto implements Serializable{
    private String orgNo;//供电单位编号
    private String orgName;//供电单位名称
    private String orgType;//供电单位类型
    private Double maxp;//当日负荷峰值
    private String maxpTime;//峰值时间
    private Double minp;//当日负荷谷值
    private String minpTime;//谷值时间
    private Double mmr;//峰谷比
    private Double midp;//平均负荷
    private Double pr;//负荷率
    private Double mmsr;//峰谷差率
    private Boolean flag;
    
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public Double getMaxp() {
		return maxp;
	}
	public void setMaxp(Double maxp) {
		this.maxp = maxp;
	}
	public String getMaxpTime() {
		return maxpTime;
	}
	public void setMaxpTime(String maxpTime) {
		this.maxpTime = maxpTime;
	}
	public Double getMinp() {
		return minp;
	}
	public void setMinp(Double minp) {
		this.minp = minp;
	}
	public String getMinpTime() {
		return minpTime;
	}
	public void setMinpTime(String minpTime) {
		this.minpTime = minpTime;
	}
	public Double getMmr() {
		return mmr;
	}
	public void setMmr(Double mmr) {
		this.mmr = mmr;
	}
	public Double getMidp() {
		return midp;
	}
	public void setMidp(Double midp) {
		this.midp = midp;
	}
	public Double getPr() {
		return pr;
	}
	public void setPr(Double pr) {
		this.pr = pr;
	}
	public Double getMmsr() {
		return mmsr;
	}
	public void setMmsr(Double mmsr) {
		this.mmsr = mmsr;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}
