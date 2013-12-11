/**
 * 
 */

package com.nari.statreport;

public class AEventStatDBean {
	private String orgName;
	private String total;
	private String cbsb;
	private String dnbtz;
	private String dnbfz;
	private String dnbcc;
	private String sdxj;
	private String dbgz;
	private String sjcc;
	
	//添加orgNo记录  ----LCM alter
	private String  orgNo;
	
	/**
	 *  the orgNo
	 * @return the orgNo
	 */
	public String getOrgNo() {
		return orgNo;
	}
	/**
	 *  the orgNo to set
	 * @param orgNo the orgNo to set
	 */
	
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCbsb() {
		return cbsb;
	}
	public void setCbsb(String cbsb) {
		this.cbsb = cbsb;
	}
	public String getDnbtz() {
		return dnbtz;
	}
	public void setDnbtz(String dnbtz) {
		this.dnbtz = dnbtz;
	}
	public String getDnbfz() {
		return dnbfz;
	}
	public void setDnbfz(String dnbfz) {
		this.dnbfz = dnbfz;
	}
	public String getDnbcc() {
		return dnbcc;
	}
	public void setDnbcc(String dnbcc) {
		this.dnbcc = dnbcc;
	}
	public String getSdxj() {
		return sdxj;
	}
	public void setSdxj(String sdxj) {
		this.sdxj = sdxj;
	}
	public String getDbgz() {
		return dbgz;
	}
	public void setDbgz(String dbgz) {
		this.dbgz = dbgz;
	}
	public String getSjcc() {
		return sjcc;
	}
	public void setSjcc(String sjcc) {
		this.sjcc = sjcc;
	}
}
