package com.nari.advapp.vipconsman.impl;

import com.nari.advapp.VipConsPowerCurveBean;
import com.nari.advapp.VipConsPowerOverBean;
import com.nari.advapp.VipConsPowerVstatBean;
import com.nari.advapp.vipconsman.VipConsMonitorDao;
import com.nari.advapp.vipconsman.VipConsMonitorDto;
import com.nari.advapp.vipconsman.VipConsMonitorManager;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class VipConsMonitorManagerImpl implements VipConsMonitorManager {
	private VipConsMonitorDao vipConsMonitorDao;

	public void setVipConsMonitorDao(VipConsMonitorDao vipConsMonitorDao) {
		this.vipConsMonitorDao = vipConsMonitorDao;
	}

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
	 * @throws DBAccessException 
	 */
	public Page<VipConsMonitorDto> queryVipConsReqRatio(
			Short queryType, String day,String month, String year, long start, long limit) throws DBAccessException{
		try{
			if(queryType == 0) {
				if(null==day||"".equals(day))
					return null;
				return vipConsMonitorDao.queryVipConsReqRatioByDay(day, start, limit);
			} else if(queryType ==1) {
				if(null==month||"".equals(month))
					return null;
				return vipConsMonitorDao.queryVipConsReqRatioByMonth(month, start, limit);
			} else if (queryType ==2) {
				if(null==year||"".equals(year))
					return null;
				return vipConsMonitorDao.queryVipConsReqRatioByYear(year, start, limit);
			} else {
				return null;
			}
		}catch(Exception e) {
			throw new DBAccessException("统计需用系数出错");
		}
	}
	
	@Override
	public Page<VipConsPowerOverBean> queryVipCOnsPowerOverByDay(
			Short queryType, String day, String month, String year, long start, int limit)  throws DBAccessException {
		try {
		if(queryType == 0) {
			return vipConsMonitorDao.queryVipCOnsPowerOverByDay(day, start, limit);
		} else if(queryType ==1) {
			return vipConsMonitorDao.queryVipCOnsPowerOverByMonth(month, start, limit);
		} else if (queryType ==2) {
			return vipConsMonitorDao.queryVipCOnsPowerOverByYear(year, start, limit);
		} else {
			return null;
		}
		} catch(Exception e) {
			throw new DBAccessException("查询负荷超容量统计出错");
		}
	}
	
	@Override
	public Page<VipConsPowerCurveBean> queryVipCOnsPowerCurveByDay(
			Short queryType, String day, String month, String year, long start, int limit)  throws DBAccessException {
		try {
		if(queryType == 0) {
			return vipConsMonitorDao.queryVipConsPowerCurveByDay(day, start, limit);
		} else if(queryType ==1) {
			return vipConsMonitorDao.queryVipConsPowerCurveByMonth(month, start, limit);
		} else if (queryType ==2) {
			return vipConsMonitorDao.queryVipConsPowerCurveByYear(year, start, limit);
		} else {
			return null;
		}
		} catch(Exception e) {
			throw new DBAccessException("查询功率因数越限统计出错");
		}
	}
	
	@Override
	public Page<VipConsPowerVstatBean> queryVipCOnsPowerVstatByDay(
			Short queryType, String day, String month, String year, long start, int limit)  throws DBAccessException {
		try {
		if(queryType == 0) {
			return vipConsMonitorDao.queryVipConsPowerVstatByDay(day, start, limit);
		} else if(queryType ==1) {
			return vipConsMonitorDao.queryVipConsPowerVstatByMonth(month, start, limit);
		} else if (queryType ==2) {
			return vipConsMonitorDao.queryVipConsPowerVstatByYear(year, start, limit);
		} else {
			return null;
		}
		} catch(Exception e) {
			throw new DBAccessException("查询电压合格率出错");
		}
	}
}
