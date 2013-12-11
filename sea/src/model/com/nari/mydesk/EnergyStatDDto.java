package com.nari.mydesk;

/**
 * 
 * 监视主页每日电量统计dto
 * @author ChunMingLi
 * @since  2010-7-28
 *
 */
public class EnergyStatDDto {
private String orgNo;
private String orgType;
private String orgName;
private long upEnergy;//上网电量
private long ppq;//供电量
private long spq;//售电量
private long mutEnergy;//互供电量

private double dataValue; //数据值
private String etdFlag;  // 标记记录省级列别电量



/**
 *  the etdFlag
 * @return the etdFlag
 */
public String getEtdFlag() {
	return etdFlag;
}
/**
 *  the etdFlag to set
 * @param etdFlag the etdFlag to set
 */
public void setEtdFlag(String etdFlag) {
	this.etdFlag = etdFlag;
}
/**
 *  the dataValue
 * @return the dataValue
 */
public double getDataValue() {
	return dataValue;
}
/**
 *  the dataValue to set
 * @param dataValue the dataValue to set
 */
public void setDataValue(double dataValue) {
	this.dataValue = dataValue;
}
/**
 *  the orgType
 * @return the orgType
 */
public String getOrgType() {
	return orgType;
}
/**
 *  the orgType to set
 * @param orgType the orgType to set
 */
public void setOrgType(String orgType) {
	this.orgType = orgType;
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
 *  the upEnergy
 * @return the upEnergy
 */
public long getUpEnergy() {
	return upEnergy;
}
/**
 *  the upEnergy to set
 * @param upEnergy the upEnergy to set
 */
public void setUpEnergy(long upEnergy) {
	this.upEnergy = upEnergy;
}
/**
 *  the ppq
 * @return the ppq
 */
public long getPpq() {
	return ppq;
}
/**
 *  the ppq to set
 * @param ppq the ppq to set
 */
public void setPpq(long ppq) {
	this.ppq = ppq;
}
/**
 *  the spq
 * @return the spq
 */
public long getSpq() {
	return spq;
}
/**
 *  the spq to set
 * @param spq the spq to set
 */
public void setSpq(long spq) {
	this.spq = spq;
}
/**
 *  the mutEnergy
 * @return the mutEnergy
 */
public long getMutEnergy() {
	return mutEnergy;
}
/**
 *  the mutEnergy to set
 * @param mutEnergy the mutEnergy to set
 */
public void setMutEnergy(long mutEnergy) {
	this.mutEnergy = mutEnergy;
}

}


