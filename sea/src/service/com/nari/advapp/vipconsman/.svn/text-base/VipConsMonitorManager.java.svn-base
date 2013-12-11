package com.nari.advapp.vipconsman;


import com.nari.advapp.VipConsPowerCurveBean;
import com.nari.advapp.VipConsPowerOverBean;
import com.nari.advapp.VipConsPowerVstatBean;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface VipConsMonitorManager {
	
	/**
	 * 统计需用系数
	 * @param queryType
	 * @param day
	 * @param month
	 * @param year
	 * @param quotientQueryStartDay
	 * @param quotientQueryEndDay
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsMonitorDto> queryVipConsReqRatio(
			Short queryType, String day,String month, String year, long start, long limit)throws DBAccessException;

	/**
	 * 统计负荷超容量信息
	 * @param day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsPowerOverBean> queryVipCOnsPowerOverByDay(
			Short queryType, String day, String month, String year, long start, int limit) throws DBAccessException;
	
	/**
	 * 统计功率因数越限信息
	 * @param day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsPowerCurveBean> queryVipCOnsPowerCurveByDay(
			Short queryType, String day, String month, String year, long start, int limit) throws DBAccessException;
	
	/**
	 * 统计电压合格率信息
	 * @param day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsPowerVstatBean> queryVipCOnsPowerVstatByDay(
			Short queryType, String day, String month, String year, long start, int limit) throws DBAccessException;
}
