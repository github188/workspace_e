package com.nari.qrystat.colldataanalyse;

import java.util.Date;
import java.util.List;

import com.nari.privilige.PSysUser;

/**
 * 行业用电趋势分析Service接口
 * @author jiangyike
 */
public interface TradeTrendlineManager {
	/**
	 * 查询行业用电对比信息，用于曲线显示
	 * @param pSysUser
	 * @param tradeList
	 * @param interval
	 * @param rg 判断是否分月还是分日
	 * @param queryDate
	 * @param compareDate
	 * @return List<TradeTrendlineDto>
	 */
	public List<TradeTrendlineBean> findTradeTrendline(PSysUser pSysUser, List<TradeTrendlineDto> tradeList , 
			int rg, int interval, Date startDate, Date endDate) throws Exception; 
	
	/**
	 * 查询行业信息，用于Grid显示
	 * @param pSysUser
	 * @param tradeList
	 * @param interval
	 * @param rg 判断是否分月还是分日
	 * @param startDate
	 * @param endData
	 * @return List<TradeTrendlineDto>
	 */
	public List<TradeTrendlineDto> findTradeTrendline4Grid(PSysUser pSysUser, List<TradeTrendlineDto> tradeList, 
			int rg, int interval, Date startDate, Date endDate) throws Exception ;
}
