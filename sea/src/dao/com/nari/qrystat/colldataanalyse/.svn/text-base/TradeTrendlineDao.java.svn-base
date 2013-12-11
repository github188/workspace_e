package com.nari.qrystat.colldataanalyse;

import com.nari.privilige.PSysUser;

/**
 * 行业用电趋势分析DAO接口
 * @author jiangyike
 */
public interface TradeTrendlineDao {
	/**
	 * 根据日期查询行业用电信息，把操作员所在部门下的行业统计信息统计出来，(05，06权限用户忽略，菜单封闭)
	 * ps:a_trade_stat_d，供电单位为区县和地市都有
	 * @param pSysUser
	 * @param tradNo
	 * @param queryDate
	 * @return TradeTrendlineDataBean
	 */
	public TradeTrendlineDataBean findTradeTrendlineByDay(PSysUser pSysUser, String tradeNo, String queryDate);
	
	/**
	 * 根据月份查询行业用电信息，把操作员所在部门下的行业统计信息统计出来，(05，06权限用户忽略，菜单封闭)
	 * ps:a_trade_stat_d，供电单位为区县和地市都有
	 * @param pSysUser
	 * @param tradNo
	 * @param startDate
	 * @param endDate
	 * @return TradeTrendlineDataBean
	 */
	public TradeTrendlineDataBean findTradeTrendlineByMonth(PSysUser pSysUser, String tradeNo, String startDate, String endDate);
}
