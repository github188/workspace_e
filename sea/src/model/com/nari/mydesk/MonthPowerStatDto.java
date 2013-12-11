/**
 * 
 */
package com.nari.mydesk;

/**
 * 上月电量统计
 * 
 * @author ChunMingLi
 * @since 2010-8-20
 *
 */
public class MonthPowerStatDto {
	//用电类别
	private String elecType;
	//电量值
	private double energyVal;
	/**
	 *  the elecType
	 * @return the elecType
	 */
	public String getElecType() {
		return elecType;
	}
	/**
	 *  the elecType to set
	 * @param elecType the elecType to set
	 */
	public void setElecType(String elecType) {
		this.elecType = elecType;
	}
	/**
	 *  the energyVal
	 * @return the energyVal
	 */
	public double getEnergyVal() {
		return energyVal;
	}
	/**
	 *  the energyVal to set
	 * @param energyVal the energyVal to set
	 */
	public void setEnergyVal(double energyVal) {
		this.energyVal = energyVal;
	}
	
}
