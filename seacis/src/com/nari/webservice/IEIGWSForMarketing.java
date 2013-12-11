package com.nari.webservice;

/**
 * 用电信息采集系统提供给营销系统的WEBSERVICE
 */
public interface IEIGWSForMarketing {

	/**
	 * 服务名称： 四川盘龙欠费用户提醒服务
	 * 
	 * @param 无
	 */
	public String WS_CONS_OVERDUE();

	/**
	 * 服务名称： 四川盘龙已缴费用户提醒服务
	 * 
	 * @param 无
	 */
	public String WS_CONS_PAY(String xmlInfo);
}