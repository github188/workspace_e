/**
 * 
 */
package com.nari.mydesk;


/**
 * 
 * 
 * @author ChunMingLi
 * @since 2010-8-18
 *
 */
public class LosePowerStatAnalyseDto implements Comparable<LosePowerStatAnalyseDto> {
	//台区、线路名称
	private String name;
	//线损率
	private double losePower;
	//抄表成功率
	private double readSuccRate;
	//单位名称
	private String orgName;
	//抄表总数
	private double readCnt;
	//抄表成功数
	private double readSuccCnt;
	//台区、线路ID
	private int losePId;
	
	/**
	 *  the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 *  the name to set
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *  the losePower
	 * @return the losePower
	 */
	public double getLosePower() {
		return losePower;
	}

	/**
	 *  the losePower to set
	 * @param losePower the losePower to set
	 */
	public void setLosePower(double losePower) {
		this.losePower = losePower;
	}

	/**
	 *  the readSuccRate
	 * @return the readSuccRate
	 */
	public double getReadSuccRate() {
		return readSuccRate;
	}

	/**
	 *  the readSuccRate to set
	 * @param readSuccRate the readSuccRate to set
	 */
	public void setReadSuccRate(double readSuccRate) {
		this.readSuccRate = readSuccRate;
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
	 *  the readCnt
	 * @return the readCnt
	 */
	public double getReadCnt() {
		return readCnt;
	}

	/**
	 *  the readCnt to set
	 * @param readCnt the readCnt to set
	 */
	public void setReadCnt(double readCnt) {
		this.readCnt = readCnt;
	}

	/**
	 *  the readSuccCnt
	 * @return the readSuccCnt
	 */
	public double getReadSuccCnt() {
		return readSuccCnt;
	}

	/**
	 *  the readSuccCnt to set
	 * @param readSuccCnt the readSuccCnt to set
	 */
	public void setReadSuccCnt(double readSuccCnt) {
		this.readSuccCnt = readSuccCnt;
	}

	/**
	 *  the losePId
	 * @return the losePId
	 */
	public int getLosePId() {
		return losePId;
	}

	/**
	 *  the losePId to set
	 * @param losePId the losePId to set
	 */
	public void setLosePId(int losePId) {
		this.losePId = losePId;
	}

	@Override
	public int compareTo(LosePowerStatAnalyseDto dto) {
		if(this.getLosePower() >= dto.getLosePower()){
			return -1;
		}else{
			return 1;
		}
	}
}
