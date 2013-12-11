package com.nari.qrystat.colldataanalyse;
import java.util.Date;


/**
 * 
 * 线路电量查询DTO
 * @author ChunMingLi
 * @since  2010-6-23
 *
 */
public class LineEceltricQuantityDto {
	//供电单位编号
	private String orgNo;
	//供电单位
	private String orgName;
	//线路ID
	private String lineId;
	//线路名称
	private String lineName;
	//开始时间
	private Date statDate;
	//正向有功总电能量 
	private int pap_e;			
	//正向有功费率1电能量 
	private int pap_e1;
	//正向有功费率2电能量 
	private int pap_e2;
	//正向有功费率3电能量 
	private int pap_e3;
	//正向有功费率4电能量 
	private int pap_e4;
	//最大有功功率
	private int max_p;
	//最大有功功率发生时间
	private Date max_p_time;
	//最小有功功率
	private int min_p;
	//最小有功功率发生时间
	private Date min_p_time;
	//最大无功功率发生时间
	private int max_q;
	//最大无功功率发生时间
	private Date max_q_time;
	//最小无功功率发生时间
	private int min_q;
	//最小无功功率发生时间
	private Date min_q_time;
	
	//平均无功功率
	private int avg_q;
	
	//平均有功功率
	private int avg_p;
	
	
	/**
	 *  the avg_q
	 * @return the avg_q
	 */
	public int getAvg_q() {
		return avg_q;
	}
	/**
	 *  the avg_q to set
	 * @param avgQ the avg_q to set
	 */
	public void setAvg_q(int avgQ) {
		avg_q = avgQ;
	}
	/**
	 *  the avg_p
	 * @return the avg_p
	 */
	public int getAvg_p() {
		return avg_p;
	}
	/**
	 *  the avg_p to set
	 * @param avgP the avg_p to set
	 */
	public void setAvg_p(int avgP) {
		avg_p = avgP;
	}
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
	/**
	 *  the orgName
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 *  the orgName to set
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 *  the lineId
	 * @return the lineId
	 */
	public String getLineId() {
		return lineId;
	}
	/**
	 *  the lineId to set
	 * @param lineId the lineId to set
	 */
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	/**
	 *  the lineName
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
	}
	/**
	 *  the lineName to set
	 * @param lineName the lineName to set
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	/**
	 *  the statDate
	 * @return the statDate
	 */
	public Date getStatDate() {
		return statDate;
	}
	/**
	 *  the statDate to set
	 * @param statDate the statDate to set
	 */
	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}
	/**
	 *  the pap_e
	 * @return the pap_e
	 */
	public int getPap_e() {
		return pap_e;
	}
	/**
	 *  the pap_e to set
	 * @param papE the pap_e to set
	 */
	public void setPap_e(int papE) {
		pap_e = papE;
	}
	/**
	 *  the pap_e1
	 * @return the pap_e1
	 */
	public int getPap_e1() {
		return pap_e1;
	}
	/**
	 *  the pap_e1 to set
	 * @param papE1 the pap_e1 to set
	 */
	public void setPap_e1(int papE1) {
		pap_e1 = papE1;
	}
	/**
	 *  the pap_e2
	 * @return the pap_e2
	 */
	public int getPap_e2() {
		return pap_e2;
	}
	/**
	 *  the pap_e2 to set
	 * @param papE2 the pap_e2 to set
	 */
	public void setPap_e2(int papE2) {
		pap_e2 = papE2;
	}
	/**
	 *  the pap_e3
	 * @return the pap_e3
	 */
	public int getPap_e3() {
		return pap_e3;
	}
	/**
	 *  the pap_e3 to set
	 * @param papE3 the pap_e3 to set
	 */
	public void setPap_e3(int papE3) {
		pap_e3 = papE3;
	}
	/**
	 *  the pap_e4
	 * @return the pap_e4
	 */
	public int getPap_e4() {
		return pap_e4;
	}
	/**
	 *  the pap_e4 to set
	 * @param papE4 the pap_e4 to set
	 */
	public void setPap_e4(int papE4) {
		pap_e4 = papE4;
	}
	/**
	 *  the max_p
	 * @return the max_p
	 */
	public int getMax_p() {
		return max_p;
	}
	/**
	 *  the max_p to set
	 * @param maxP the max_p to set
	 */
	public void setMax_p(int maxP) {
		max_p = maxP;
	}
	/**
	 *  the max_p_time
	 * @return the max_p_time
	 */
	public Date getMax_p_time() {
		return max_p_time;
	}
	/**
	 *  the max_p_time to set
	 * @param maxPTime the max_p_time to set
	 */
	public void setMax_p_time(Date maxPTime) {
		max_p_time = maxPTime;
	}
	/**
	 *  the min_p
	 * @return the min_p
	 */
	public int getMin_p() {
		return min_p;
	}
	/**
	 *  the min_p to set
	 * @param minP the min_p to set
	 */
	public void setMin_p(int minP) {
		min_p = minP;
	}
	/**
	 *  the min_p_time
	 * @return the min_p_time
	 */
	public Date getMin_p_time() {
		return min_p_time;
	}
	/**
	 *  the min_p_time to set
	 * @param minPTime the min_p_time to set
	 */
	public void setMin_p_time(Date minPTime) {
		min_p_time = minPTime;
	}
	/**
	 *  the max_q
	 * @return the max_q
	 */
	public int getMax_q() {
		return max_q;
	}
	/**
	 *  the max_q to set
	 * @param maxQ the max_q to set
	 */
	public void setMax_q(int maxQ) {
		max_q = maxQ;
	}
	/**
	 *  the max_q_time
	 * @return the max_q_time
	 */
	public Date getMax_q_time() {
		return max_q_time;
	}
	/**
	 *  the max_q_time to set
	 * @param maxQTime the max_q_time to set
	 */
	public void setMax_q_time(Date maxQTime) {
		max_q_time = maxQTime;
	}
	/**
	 *  the min_q
	 * @return the min_q
	 */
	public int getMin_q() {
		return min_q;
	}
	/**
	 *  the min_q to set
	 * @param minQ the min_q to set
	 */
	public void setMin_q(int minQ) {
		min_q = minQ;
	}
	/**
	 *  the min_q_time
	 * @return the min_q_time
	 */
	public Date getMin_q_time() {
		return min_q_time;
	}
	/**
	 *  the min_q_time to set
	 * @param minQTime the min_q_time to set
	 */
	public void setMin_q_time(Date minQTime) {
		min_q_time = minQTime;
	}
	

}
