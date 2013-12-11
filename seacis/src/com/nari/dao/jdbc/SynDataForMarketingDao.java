package com.nari.dao.jdbc;

import java.util.Date;

/**
 * 此接口用于同步数据
 */
public interface SynDataForMarketingDao {

	/**
	 * 根据表名同步中间库中的数据到采集库
	 * 
	 * @param tableName
	 * @return boolean true or false
	 */
	public boolean synData(String tableName);

	/**
	 * 终端自动装接处理流程中根据参数来同步用户信息类、运行终端信息类的表数据到主站数据库
	 * 
	 * @param cons_no
	 * @param cons_id
	 * @param cp_no
	 * @param tmnl_id
	 * @param tg_id
	 * @return boolean true or false
	 */
	public boolean synDataByService(String cons_no, String cons_id,
			String cp_no, String tmnl_id, String tg_id, String appNo);

	/**
	 * 终端自动装接处理流程中根据参数来同步用户信息类、运行终端信息类的表数据到主站数据库
	 * 
	 * @param subId
	 * @param cp_no
	 * @param tmnl_id
	 * @return boolean true or false
	 */
	public boolean synGateDataByService(String subId, String tmnlId, String cpNo);

	/**
	 * 集抄同步中间库数据
	 * 
	 * @param tmnlId
	 * @param cpNo
	 * @param tgId
	 * @return boolean true or false
	 */
	public boolean synOtherDataByService(String tmnlId, String cpNo,
			String tgId, String appNo);

	/**
	 * 自动化抄表数据上送到中间库中
	 * 
	 * @param sDate
	 * @return boolean true or false
	 */
	public boolean synDataCollect(Date sDate);

	/**
	 * 四川盘龙同步中间库欠费用户信息数据
	 * 
	 * @return boolean
	 */

	public boolean synDueDataByService();
	/**
	 * 四川盘龙同步中间库缴费用户信息数据
	 * 
	 * @return boolean
	 */
	public boolean synPayDataByService(String userID);
}