package com.nari.qrystat.colldataanalyse;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 线路用电损耗统计Jdbc之Dao接口
 * @author 姜炜超
 */
public interface LineLosePowerStatJdbcDao {
	/**
	 * 根据条件查询时间段内线路用电损耗统计信息
	 * @param nodeId 节点id
	 * @param nodeType 节点类型
	 * @param orgType 如果节点是供电单位，显示供电单位类型
	 * @param statFlag 02表示月，03表示季度，04表示年
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LineLosePowerBean>
	 */
	public Page<LineLosePowerBean> findLineLosePowerStat(String nodeId, String nodeType, String orgType, 
			String statFlag, Integer startDate, Integer endDate,long start, int limit, PSysUser pSysUser);
}
