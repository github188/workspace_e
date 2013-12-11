
package com.nari.mydesk.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.mydesk.LosePowerStatAnalyseDao;
import com.nari.mydesk.LosePowerStatAnalyseDto;
import com.nari.mydesk.LosePowerStatAnalyseManager;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 * 
 * 线损统计manager
 * @author ChunMingLi
 * @since 2010-8-18
 *
 */
public class LosePowerStatAnalyseManagerImpl implements
		LosePowerStatAnalyseManager {
	private LosePowerStatAnalyseDao losePowerStatAnalyseDao;

	/**
	 *  the losePowerStatAnalyseDao to set
	 * @param losePowerStatAnalyseDao the losePowerStatAnalyseDao to set
	 */
	public void setLosePowerStatAnalyseDao(
			LosePowerStatAnalyseDao losePowerStatAnalyseDao) {
		this.losePowerStatAnalyseDao = losePowerStatAnalyseDao;
	}

	/* (non-Javadoc)
	 * @see com.nari.mydesk.LosePowerStatAnalyseManager#queryLosePowerStatA(java.util.Date, java.lang.String, java.lang.String, com.nari.privilige.PSysUser)
	 */
	@Override
	public List<LosePowerStatAnalyseDto> queryTGLosePowerStatA(String queryDate,
			String orgNo, String orgType, PSysUser pSysUser) throws Exception {
		try {
			List<LosePowerStatAnalyseDto> tempList = losePowerStatAnalyseDao.queryTGLosePowerStatA(queryDate, orgNo, orgType, pSysUser);
			List<LosePowerStatAnalyseDto> analyseTGList = new ArrayList<LosePowerStatAnalyseDto>();
			for(LosePowerStatAnalyseDto dto : tempList){
				if(dto.getLosePower() != 0){
					analyseTGList.add(dto);
				}
			}
			return analyseTGList;
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询线损统计分析按台区SQL查询操作出错");
		}
	}

	@Override
	public List<LosePowerStatAnalyseDto> queryLineLosePowerStatA(
			String queryDate, String orgNo, String orgType, PSysUser pSysUser)
			throws Exception {
		try {
			List<LosePowerStatAnalyseDto> tempList = losePowerStatAnalyseDao.queryLineLosePowerStatA(queryDate, orgNo, orgType, pSysUser);
			List<LosePowerStatAnalyseDto> analyseLineList = new ArrayList<LosePowerStatAnalyseDto>();
			for(LosePowerStatAnalyseDto dto : tempList){
				if(dto.getLosePower() != 0){
					analyseLineList.add(dto);
				}
			}
			return analyseLineList;
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询线损统计分析按线路SQL查询操作出错");
		}
	}

}
