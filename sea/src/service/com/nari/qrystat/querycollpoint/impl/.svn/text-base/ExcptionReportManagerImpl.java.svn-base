package com.nari.qrystat.querycollpoint.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.querycollpoint.ExcptionReport;
import com.nari.qrystat.querycollpoint.ExcptionReportDao;
import com.nari.qrystat.querycollpoint.ExcptionReportManager;
import com.nari.qrystat.querycollpoint.VwConsType;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class ExcptionReportManagerImpl implements ExcptionReportManager {
	private ExcptionReportDaoImpl excptionReportDao; 

	public ExcptionReportDaoImpl getExcptionReportDao() {
		return excptionReportDao;
	}

	public void setExcptionReportDao(ExcptionReportDaoImpl excptionReportDao) {
		this.excptionReportDao = excptionReportDao;
	}

	@Override
	public Page<ExcptionReport> findExcptionCount(String treeType,String orgNo, String orgType,
			PSysUser pSysUser, String dateStart, String dateEnd, long start,
			int limit) throws DBAccessException {
		try {
			return excptionReportDao.findExcptionCount(treeType,orgNo, orgType, pSysUser, dateStart, dateEnd, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
			
		}
	}

	@Override
	public List<VwConsType> findConsType() throws DBAccessException {
		// TODO Auto-generated method stub
		try {
			return excptionReportDao.findConsType();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
			
		}
	}
	


}
