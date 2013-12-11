package com.nari.qrystat.colldataanalyse;

import java.io.Serializable;

/**
 * 地区电量分布数据值对象
 * @author 姜炜超
 */
public class AreaPowerDistDto implements Serializable{
	private String orgName;//供电单位名称
	private String orgNo;
	private String orgType;
	private Double papEBasic;//基准日电量
	private Double pr;//电量占比
	private Double papEComp;//对比日电量
	private Double par;//电量增长率
	private Double paq;//电量增长量
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Double getPapEBasic() {
		return papEBasic;
	}
	public void setPapEBasic(Double papEBasic) {
		this.papEBasic = papEBasic;
	}
	public Double getPr() {
		return pr;
	}
	public void setPr(Double pr) {
		this.pr = pr;
	}
	public Double getPapEComp() {
		return papEComp;
	}
	public void setPapEComp(Double papEComp) {
		this.papEComp = papEComp;
	}
	public Double getPar() {
		return par;
	}
	public void setPar(Double par) {
		this.par = par;
	}
	public Double getPaq() {
		return paq;
	}
	public void setPaq(Double paq) {
		this.paq = paq;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
}
