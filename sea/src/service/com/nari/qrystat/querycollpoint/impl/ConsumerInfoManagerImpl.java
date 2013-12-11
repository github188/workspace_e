package com.nari.qrystat.querycollpoint.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.qrystat.querycollpoint.Cmeter;
import com.nari.qrystat.querycollpoint.CmeterInfo;
import com.nari.qrystat.querycollpoint.ConsumerInfo;
import com.nari.qrystat.querycollpoint.IConsumerInfoDao;
import com.nari.qrystat.querycollpoint.IConsumerInfoManager;
import com.nari.qrystat.querycollpoint.NewMpDayPower;
import com.nari.qrystat.querycollpoint.Tmnlrun;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class ConsumerInfoManagerImpl implements IConsumerInfoManager {

		private IConsumerInfoDao consumerInfoDaoImpl;
	/**
		 * @param consumerInfoDao the consumerInfoDao to set
		 */
		public void setConsumerInfoDao(ConsumerInfoDaoImpl consumerInfoDaoImpl) {
			this.consumerInfoDaoImpl = consumerInfoDaoImpl;
		}
	@Override
	public List<ConsumerInfo> findConsumerInfo(String CONS_NO) throws DBAccessException {
		try {
			return consumerInfoDaoImpl.findConsumerInfo(CONS_NO);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
			
		}
	}
	@Override
	public List<Tmnlrun> findTmnlrun(String CONS_NO) throws DBAccessException {
		try {
			return consumerInfoDaoImpl.findTmnlrun(CONS_NO);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	@Override
	public Page<Cmeter> findCmeter(String CONS_NO,String TG_ID,long start, int limit) throws DBAccessException {
		try {
			return consumerInfoDaoImpl.findCmeter(CONS_NO,TG_ID,start,limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	@Override
	public Page<NewMpDayPower> findNewMpDayPower(String assetNo,String flagValue,String CONS_NO,
			String DateStart, String DateEnd, long start, int limit)
			throws DBAccessException {
		try {
			return consumerInfoDaoImpl.findNewMpDayPower(assetNo,flagValue,CONS_NO, DateStart, DateEnd,start,limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	@Override
	public Page<CmeterInfo> findCmeterInfo(String CONS_NO, long start, int limit)
			throws DBAccessException {
		try {
			return consumerInfoDaoImpl.findCmeterInfo(CONS_NO, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

}
