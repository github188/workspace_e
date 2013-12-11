/**
 * 
 */
package com.nari.advapp.statanalyse;

/**
 * 
 * 电量、负荷统计分析DTO
 * @author ChunMingLi
 * @since 2010-8-12
 *
 */
public class StatAnalyseCurveDto {
	//时间
	private String statDate;
	//负荷、电量数据
	private double curveDate;
	//正向有功PAP_E、
	private double PAP_E_DATA;
	//反向有功RAP_E、
	private double RAP_E_DATA;
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
	 *  the curveDate
	 * @return the curveDate
	 */
	public double getCurveDate() {
		return curveDate;
	}
	/**
	 *  the curveDate to set
	 * @param curveDate the curveDate to set
	 */
	public void setCurveDate(double curveDate) {
		this.curveDate = curveDate;
	}
	/**
	 *  the pAP_E_DATA
	 * @return the pAP_E_DATA
	 */
	public double getPAP_E_DATA() {
		return PAP_E_DATA;
	}
	/**
	 *  the pAP_E_DATA to set
	 * @param pAPEDATA the pAP_E_DATA to set
	 */
	public void setPAP_E_DATA(double pAPEDATA) {
		PAP_E_DATA = pAPEDATA;
	}
	/**
	 *  the rAP_E_DATA
	 * @return the rAP_E_DATA
	 */
	public double getRAP_E_DATA() {
		return RAP_E_DATA;
	}
	/**
	 *  the rAP_E_DATA to set
	 * @param rAPEDATA the rAP_E_DATA to set
	 */
	public void setRAP_E_DATA(double rAPEDATA) {
		RAP_E_DATA = rAPEDATA;
	}
}
