package com.nari.service;

import java.util.Date;

/**
 * 在这个接口中定义档案数据同步处理
 * @author wx
 */
public interface SynDataForMarketingManager {
	
	/**
	 * 根据参数来读取采集数据库中的数据然后同步到中间库中
	 * @return boolean
	 */
	public boolean synData(String tableName);	
	
	/**
	 * 终端自动装接处理流程中根据参数来同步用户信息类、运行终端信息类的表数据到主站数据库
	 * @return boolean
	 */
	public boolean synDataByService(String cons_no,String cons_id,String meter_id, String tmnl_id, String tgId, String appNo);
	
	/**
	 * 集抄同步中间库数据
	 * @return boolean
	 */
	public boolean synOtherDataByService(String tmnlId, String cpNo, String tgId, String appNo);
	
	/**
	 * 自动化抄表数据上送到中间库中
	 * @return boolean
	 */
	public boolean synDataCollect(Date sDate);
	
	

	/**
	 * 四川盘龙同步中间库欠费用户信息数据
	 * @return boolean
	 */
	public boolean synDueDataByService();
	/**
	 * 四川盘龙同步中间库缴费用户信息数据
	 * @return boolean
	 */
	public boolean synPayDataByService(String userID);
}