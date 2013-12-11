package com.nari.qrystat.colldataanalyse;

import java.util.Date;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 地区电量分布查询接口
 * @author 姜炜超
 */
public interface AreaPowerDistManager {
	/**
	 * 根据条件查询地区电量分布信息
	 * @param pSysUser 操作员信息
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param compareDate 对比起始日期
	 * @param start 
	 * @param limit
	 * @return Page<AreaPowerDistDto>
	 */
	public Page<AreaPowerDistDto> findAPDInfoByCond(PSysUser pSysUser , Date startDate, Date endDate,
			Date compareDate, long start, int limit) throws Exception;
}
