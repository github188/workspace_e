package com.nari.runman.runstatusman.impl;

import java.util.Date;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.runman.runstatusman.AEventStatDBeanDao;
import com.nari.runman.runstatusman.AEventStatDBeanManager;
import com.nari.statreport.AEventStatDBean;
import com.nari.statreport.AEventStatDWindowDto;
import com.nari.statreport.AmmeterHDto;
import com.nari.statreport.AmmeterHWindowDto;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class AEventStatDBeanManagerImpl implements AEventStatDBeanManager {
	
	private AEventStatDBeanDao aEventStatDBeanDao;
		
	public void setaEventStatDBeanDao(AEventStatDBeanDao aEventStatDBeanDao) {
			this.aEventStatDBeanDao = aEventStatDBeanDao;
		}
	
	@Override
	public Page<AEventStatDBean> findAEventStatDBean(PSysUser user, Date startDate,
			Date endDate, long start, int limit) throws DBAccessException {
		try {
			return this.aEventStatDBeanDao.findAEventStatDBean(user, startDate, endDate, start, limit);
		} catch(DataAccessException e) {
			e.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<AmmeterHDto> queryAmmeterHDto(PSysUser user,String orgNo, String eventNo, Date startDate,
			Date endDate, long start, int limit) throws DBAccessException {
		try {
			return this.aEventStatDBeanDao.findAmmeterHDto(user,orgNo,eventNo, startDate,
					endDate, start, limit);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<AmmeterHWindowDto> queryAmmeterHWindowList(String eventId,
			String areaCode, String eventNo, long start, int limit)
			throws DBAccessException {
		try {
			return this.aEventStatDBeanDao.findAmmeterHWindowList(eventId, areaCode, eventNo, start, limit);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<AEventStatDWindowDto> queryAEventStatDWindow(String orgNo,
			String eventNo, Date startDate, Date endDate, long start, int limit)
			throws DBAccessException {
		try {
			return this.aEventStatDBeanDao.findAEventStatDWindow(orgNo, eventNo, startDate, endDate, start, limit);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

}
