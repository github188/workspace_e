package com.nari.qrystat.querycollpoint.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.qrystat.querycollpoint.CCust;
import com.nari.qrystat.querycollpoint.CITRun;
import com.nari.qrystat.querycollpoint.CPS;
import com.nari.qrystat.querycollpoint.CSP;
import com.nari.qrystat.querycollpoint.Ccontact;
import com.nari.qrystat.querycollpoint.DMeter;
import com.nari.qrystat.querycollpoint.Gtran;
import com.nari.qrystat.querycollpoint.ICCustDao;
import com.nari.qrystat.querycollpoint.ICCustManager;
import com.nari.qrystat.querycollpoint.RSIMCharge;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class CCustManagerImpl implements ICCustManager {
	private ICCustDao ccustDao;

	/**
	 * @return the ccustDao
	 */
	public ICCustDao getCcustDao() {
		return ccustDao;
	}

	/**
	 * @param ccustDao
	 *            the ccustDao to set
	 */
	public void setCcustDao(ICCustDao ccustDao) {
		this.ccustDao = ccustDao;
	}

	@Override
	public List<CCust> findCCust(String custNo) throws DBAccessException {
		try {
			return this.ccustDao.findConsumerInfo(custNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<Ccontact> findCcontact(String custNo, long start, int limit)
			throws DBAccessException {
		try {
			return this.ccustDao.findCcontact(custNo, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<CSP> findCSP(String consNo) throws DBAccessException {
		try {
			return this.ccustDao.findCSP(consNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<CPS> findCPS(String consNo, long start, int limit)
			throws DBAccessException {
		try {
			return this.ccustDao.findCPS(consNo, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<RSIMCharge> findSIM(String SIM_NO, long start, int limit)
			throws DBAccessException {
		try {
			return this.ccustDao.findSIM(SIM_NO, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<DMeter> findDMeter(String consNo) throws DBAccessException {
		try {
			return this.ccustDao.findDMeter(consNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<CITRun> findCITRun(String tFactor, long start, int limit)
			throws DBAccessException {
		try {
			return this.ccustDao.findCITRun(tFactor, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<Gtran> findGtran(String CONS_ID,String consType) throws DBAccessException {
		try {
			return this.ccustDao.findGtran(CONS_ID,consType);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<Gtran> findGtran(String CONS_ID, long start, int limit)
			throws DBAccessException {
		try{
			return this.ccustDao.findGtran(CONS_ID, start, limit);
		}catch(DataAccessException e){
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
}
