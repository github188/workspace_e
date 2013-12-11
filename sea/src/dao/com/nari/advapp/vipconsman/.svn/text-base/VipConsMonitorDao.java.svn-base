package com.nari.advapp.vipconsman;

import com.nari.advapp.VipConsPowerCurveBean;
import com.nari.advapp.VipConsPowerOverBean;
import com.nari.advapp.VipConsPowerVstatBean;
import com.nari.support.Page;

public interface VipConsMonitorDao {
	
	/**
	 * 按日统计需用系数
	 * @param Day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsMonitorDto> queryVipConsReqRatioByDay(String day, long start, long limit);
	
   /**
   * 按月统计需用系数
   * @param Month
   * @param start
   * @param limit
   * @return
   */
	public Page<VipConsMonitorDto> queryVipConsReqRatioByMonth(String month, long start, long limit);
	
	/**
	 * 按年统计需用系数
	 * @param Year
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsMonitorDto> queryVipConsReqRatioByYear(String year, long start, long  limit);

	
	/**
	 * 按日统计负荷超容量信息
	 * @param day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsPowerOverBean> queryVipCOnsPowerOverByDay(String day, long start, int limit);
	
	/**
	 * 按月统计负荷超容量信息
	 * @param day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsPowerOverBean> queryVipCOnsPowerOverByMonth(String month, long start, int limit);
	
	/**
	 * 按年统计负荷超容量信息
	 * @param day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsPowerOverBean> queryVipCOnsPowerOverByYear(String year, long start, int limit);
	
	/**
	 * 按日统计功率因数越限信息
	 * @param day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsPowerCurveBean> queryVipConsPowerCurveByDay(String day, long start, int limit);
	
	/**
	 * 按月统计功率因数越限信息
	 * @param day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsPowerCurveBean> queryVipConsPowerCurveByMonth(String month, long start, int limit);
	
	/**
	 * 按年统计功率因数越限信息
	 * @param day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsPowerCurveBean> queryVipConsPowerCurveByYear(String year, long start, int limit);
	
	/**
	 * 按日统计电压合格率信息
	 * @param day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsPowerVstatBean> queryVipConsPowerVstatByDay(String day, long start, int limit);
	
	/**
	 * 按月统计电压合格率信息
	 * @param day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsPowerVstatBean> queryVipConsPowerVstatByMonth(String month, long start, int limit);
	
	/**
	 * 按年统计电压合格率信息
	 * @param day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsPowerVstatBean> queryVipConsPowerVstatByYear(String year, long start, int limit);
}
