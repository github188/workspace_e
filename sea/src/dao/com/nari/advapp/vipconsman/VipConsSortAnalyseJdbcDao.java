package com.nari.advapp.vipconsman;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 重点户排名分析查询Jdbc之Dao接口
 * @author 姜炜超
 */
public interface VipConsSortAnalyseJdbcDao {
	/**
	 * 根据条件查询用电排名分析信息
	 * @param pSysUser
	 * @param startDate
	 * @param endDate
	 * @param flag 1表示最大电能量，2表示最小电能量
	 * @param start
	 * @param limit
	 * @return Page<VipConsSortAnalyseBean>
	 */
	public Page<VipConsSortAnalyseBean> findVipConsPowerSort(PSysUser pSysUser, String startDate,
			String endDate, int flag, long start, int limit) throws DBAccessException;
	
	/**
	 * 根据条件查询负荷排名分析信息
	 * @param pSysUser
	 * @param startDate
	 * @param endDate
	 * @param flag 1表示最高负荷，2表示最低负荷
	 * @param start
	 * @param limit
	 * @return Page<VipConsSortAnalyseBean>
	 */
	public Page<VipConsSortAnalyseBean> findVipConsLoadSort(PSysUser pSysUser, String startDate,
			String endDate, int flag, long start, int limit) throws DBAccessException;
}