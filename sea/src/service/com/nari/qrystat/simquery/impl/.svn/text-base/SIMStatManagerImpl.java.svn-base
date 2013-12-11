package com.nari.qrystat.simquery.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.simquery.SIMOverTraffic;
import com.nari.qrystat.simquery.SIMStatDao;
import com.nari.qrystat.simquery.SIMStatManager;
import com.nari.qrystat.simquery.SimDetailFlowInfoBean;
import com.nari.qrystat.simquery.SimInstall;
import com.nari.qrystat.simquery.SimTrafficInfoBean;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.tangosol.coherence.demo.component.gUI.constraints.namedConstraints.Center;

public class SIMStatManagerImpl implements SIMStatManager {

	private SIMStatDao sIMStatDao;

	public void setsIMStatDao(SIMStatDao sIMStatDao) {
		this.sIMStatDao = sIMStatDao;
	}

	@Override
	public Page<SimInstall> findSim(String psPart,String simNo, long start, int limit,PSysUser pSysUser)
			throws DBAccessException {
		try {
			return sIMStatDao.findSim(psPart,simNo, start, limit, pSysUser);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<SIMOverTraffic> findSimOverTraffic(String psPart,
			String chargeDateFrom, String chargeDateTo, String runner,
			long start, int limit) throws DBAccessException {
		try {
			chargeDateFrom = chargeDateFrom+"-01 00:00:00";
			chargeDateTo = chargeDateTo +"-01 00:00:00";
			return this.sIMStatDao.findSimOverTraffic(psPart, chargeDateFrom,
					chargeDateTo, runner, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<SimDetailFlowInfoBean> findSimDetailFlowInfo(String simNo,
			Date chargeDateFrom, Date chargeDateTo, long start, int limit)  throws DBAccessException {
		try {
			return this.sIMStatDao.findSimDetailFlowInfo(simNo, chargeDateFrom,
					chargeDateTo, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<SimTrafficInfoBean> findSimTrafficInfo(PSysUser user,String dateFrom,
			String dateTo,String orgNo, long start, int limit) throws DBAccessException {
		try {
			dateFrom = dateFrom+"-01 00:00:00";
			dateTo = dateTo +"-01 00:00:00";
			Page<SimTrafficInfoBean> page = sIMStatDao.findSimTrafficInfo(user, dateFrom, dateTo, orgNo, start, limit);
			List<SimTrafficInfoBean> list = page.getResult();
			for (int i = 0; i < list.size(); i++) {
				SimTrafficInfoBean bean = list.get(i);
				bean.setFlowDate(dateFrom.substring(0,7)+"~" +dateTo.substring(0,7));
			}
			return page;
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
}
