package com.nari.qrystat.colldataanalyse;

import java.util.Date;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 台区用电损耗统计接口
 * @author 姜炜超
 */
public interface TGLosePowerStatManager {
	/**
	 * 根据条件查询时间段内台区用电损耗统计信息
	 * @param nodeId 节点id
	 * @param nodeType 节点类型
	 * @param orgType 如果节点是供电单位，显示供电单位类型
	 * @param statFlag 02表示月，03表示季度，04表示年
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LosePowerStatDto>
	 */
	public Page<LosePowerStatDto> findLosePowerStat(String nodeId, String nodeType, String orgType, 
			String statFlag, Date startDate, Date endDate,long start, int limit, PSysUser pSysUser)throws Exception;
}
