package com.nari.mydesk;

import java.util.List;

import com.nari.privilige.PSysUser;

/**
 * 
 * 监视主页每日电量统计 dao
 * @author ChunMingLi
 * @since  2010-7-28
 *
 */
public interface EnergyStatDDao {
	/**
	 * 每日电量统计
	 * @param userInfo
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<EnergyStatDDto> findEnergyStatDay( String provinceName,PSysUser userInfo, String startDate, String endDate)throws Exception;
	
	/**
	 * 查找省级单位名称
	 * @return List
	 * @throws Exception
	 */
	public List<String>  queryProvinceName()throws Exception;
}
