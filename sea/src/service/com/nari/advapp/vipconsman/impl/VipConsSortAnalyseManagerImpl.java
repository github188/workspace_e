package com.nari.advapp.vipconsman.impl;

import org.springframework.dao.DataAccessException;

import com.nari.advapp.vipconsman.VipConsSortAnalyseBean;
import com.nari.advapp.vipconsman.VipConsSortAnalyseJdbcDao;
import com.nari.advapp.vipconsman.VipConsSortAnalyseManager;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 重点户排名分析查询业务层实现类
 * @author 姜炜超
 */
public class VipConsSortAnalyseManagerImpl implements VipConsSortAnalyseManager{
	private VipConsSortAnalyseJdbcDao vipConsSortAnalyseJdbcDao;//重点户排名分析Dao对象
	
	public void setVipConsSortAnalyseJdbcDao(
			VipConsSortAnalyseJdbcDao vipConsSortAnalyseJdbcDao) {
		this.vipConsSortAnalyseJdbcDao = vipConsSortAnalyseJdbcDao;
	}

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
			String endDate, int flag, long start, int limit) throws Exception{
		if(null == startDate || null == endDate){
			return new Page<VipConsSortAnalyseBean>();
		}
		try{
			return vipConsSortAnalyseJdbcDao.findVipConsPowerSort(pSysUser, startDate, endDate, flag, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.ADE_QRY_VIP_POWER_SORT_ANALYSE);
		}
	}
	
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
			String endDate, int flag, long start, int limit) throws Exception{
		if(null == startDate || null == endDate){
			return new Page<VipConsSortAnalyseBean>();
		}
		try{
			return vipConsSortAnalyseJdbcDao.findVipConsLoadSort(pSysUser, startDate, endDate, flag, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.ADE_QRY_VIP_LOAD_SORT_ANALYSE);
		}
	}
}
