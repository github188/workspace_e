/**
 * 
 */
package com.nari.advapp.statanalyse;

/**
 * 
 * 负荷统计分析DTO
 * 
 * @author ChunMingLi
 * @since 2010-8-7
 *
 */
public class ChargeStatisAnalyseDTO {
	//供电单位
	private String orgName;
	
	//供电单位编码
	private String orgNo;
	
	//用户编号
	private String consNo;
	
	//用户名称
	private String consName;
	
	//合同容量
	private double contractCap;
	
	//统计时间
	private String statDate;
	
	//负荷峰值
	private double max_p;
	
	//发生时间
	private String max_p_time;
	
	//负荷谷值
	private double min_p;
	
	//发生时间
	private String min_p_time;
	
	//平均负荷
	private double avg_p;
	
	//负荷率
	private double chargeRate;
	
	//峰谷差率
	private double fengGuRate;
	
	

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
	 *  the consNo
	 * @return the consNo
	 */
	public String getConsNo() {
		return consNo;
	}

	/**
	 *  the consNo to set
	 * @param consNo the consNo to set
	 */
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	/**
	 *  the consName
	 * @return the consName
	 */
	public String getConsName() {
		return consName;
	}

	/**
	 *  the consName to set
	 * @param consName the consName to set
	 */
	public void setConsName(String consName) {
		this.consName = consName;
	}


	/**
	 *  the statDate
	 * @return the statDate
	 */
	public String getStatDate() {
		return statDate;
	}

	/**
	 *  the statDate to set
	 * @param statDate the statDate to set
	 */
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}

	/**
	 *  the contractCap
	 * @return the contractCap
	 */
	public double getContractCap() {
		return contractCap;
	}

	/**
	 *  the contractCap to set
	 * @param contractCap the contractCap to set
	 */
	public void setContractCap(double contractCap) {
		this.contractCap = contractCap;
	}

	/**
	 *  the max_p
	 * @return the max_p
	 */
	public double getMax_p() {
		return max_p;
	}

	/**
	 *  the max_p to set
	 * @param maxP the max_p to set
	 */
	public void setMax_p(double maxP) {
		max_p = maxP;
	}

	/**
	 *  the max_p_time
	 * @return the max_p_time
	 */
	public String getMax_p_time() {
		return max_p_time;
	}

	/**
	 *  the max_p_time to set
	 * @param maxPTime the max_p_time to set
	 */
	public void setMax_p_time(String maxPTime) {
		max_p_time = maxPTime;
	}

	/**
	 *  the min_p
	 * @return the min_p
	 */
	public double getMin_p() {
		return min_p;
	}

	/**
	 *  the min_p to set
	 * @param minP the min_p to set
	 */
	public void setMin_p(double minP) {
		min_p = minP;
	}

	/**
	 *  the min_p_time
	 * @return the min_p_time
	 */
	public String getMin_p_time() {
		return min_p_time;
	}

	/**
	 *  the min_p_time to set
	 * @param minPTime the min_p_time to set
	 */
	public void setMin_p_time(String minPTime) {
		min_p_time = minPTime;
	}

	/**
	 *  the avg_p
	 * @return the avg_p
	 */
	public double getAvg_p() {
		return avg_p;
	}

	/**
	 *  the avg_p to set
	 * @param avgP the avg_p to set
	 */
	public void setAvg_p(double avgP) {
		avg_p = avgP;
	}

	/**
	 *  the chargeRate
	 * @return the chargeRate
	 */
	public double getChargeRate() {
		return chargeRate;
	}

	/**
	 *  the chargeRate to set
	 * @param chargeRate the chargeRate to set
	 */
	public void setChargeRate(double chargeRate) {
		this.chargeRate = chargeRate;
	}

	/**
	 *  the fengGuRate
	 * @return the fengGuRate
	 */
	public double getFengGuRate() {
		return fengGuRate;
	}

	/**
	 *  the fengGuRate to set
	 * @param fengGuRate the fengGuRate to set
	 */
	public void setFengGuRate(double fengGuRate) {
		this.fengGuRate = fengGuRate;
	}



}
