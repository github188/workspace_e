package com.nari.mydesk;

import java.util.List;

import com.nari.privilige.PSysUser;

/**
 * 
 * 监视主页每日电量统计 manager
 * @author ChunMingLi
 * @since  2010-7-28
 *
 */
public interface EnergyStatDManager {
	/**
	 * 每日电量统计
	 * @param userInfo
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<EnergyStatDDto> queryEnergyStatDay( PSysUser userInfo, String startDate, String endDate)throws Exception;
}
