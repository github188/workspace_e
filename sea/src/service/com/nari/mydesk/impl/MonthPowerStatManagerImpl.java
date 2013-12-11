package com.nari.mydesk.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.mydesk.LosePowerStatAnalyseDto;
import com.nari.mydesk.MonthPowerStatDao;
import com.nari.mydesk.MonthPowerStatDto;
import com.nari.mydesk.MonthPowerStatManager;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 * 
 * 上月售电统计
 * @author ChunMingLi
 * @since 2010-8-20
 *
 */
public class MonthPowerStatManagerImpl implements MonthPowerStatManager {
	/**
	 * 注入Dao
	 */
	private MonthPowerStatDao monthPowerStatDao;
	
	/**
	 *  the monthPowerStatDao to set
	 * @param monthPowerStatDao the monthPowerStatDao to set
	 */
	public void setMonthPowerStatDao(MonthPowerStatDao monthPowerStatDao) {
		this.monthPowerStatDao = monthPowerStatDao;
	}

	/* (non-Javadoc)
	 * @see com.nari.mydesk.MonthPowerStatManager#queryMonthPowerStat(com.nari.privilige.PSysUser, java.lang.String)
	 */
	@Override
	public List<MonthPowerStatDto> queryMonthPowerStat(PSysUser userInfo,
			String queryDate) throws Exception {
		try {
			List<MonthPowerStatDto> tempList = monthPowerStatDao.findMonthPowerStat(userInfo, queryDate);
			return tempList;
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询上月售电统计分析按线路SQL查询操作出错");
		}
	}

}
