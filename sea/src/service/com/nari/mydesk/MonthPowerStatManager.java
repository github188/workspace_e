/**
 * 
 */
package com.nari.mydesk;

import java.util.List;

import com.nari.privilige.PSysUser;

/**
 * 
 * 上月售电统计
 * @author ChunMingLi
 * @since 2010-8-20
 *
 */
public interface MonthPowerStatManager {
	/**
	 * 上月售电统计
	 * @param userInfo  操作员信息
	 * @param queryDate 查询时间
	 * @return 上月售电集合
	 * @throws Exception 
	 */
	public List<MonthPowerStatDto> queryMonthPowerStat(PSysUser userInfo, String queryDate)throws Exception;
}
