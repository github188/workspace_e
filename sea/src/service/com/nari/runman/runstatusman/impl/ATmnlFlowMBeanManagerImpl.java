package com.nari.runman.runstatusman.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.nari.runman.runstatusman.ATmnlFlowMBeanDao;
import com.nari.runman.runstatusman.ATmnlFlowMBeanManager;
import com.nari.statreport.ATmnlFlowMBean;
import com.nari.statreport.ATmnlFlowMBeanH;
import com.nari.statreport.SimFee;
import com.nari.statreport.TmnlFactory;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class ATmnlFlowMBeanManagerImpl implements ATmnlFlowMBeanManager{
	
	private ATmnlFlowMBeanDao aTmnlFlowMBeanDao;

	public void setaTmnlFlowMBeanDao(ATmnlFlowMBeanDao aTmnlFlowMBeanDao) {
		this.aTmnlFlowMBeanDao = aTmnlFlowMBeanDao;
	}

	@Override
	public Page<ATmnlFlowMBean> findChannelMonitor(String sDate, long start, int limit) throws DBAccessException {
		try {
			return this.aTmnlFlowMBeanDao.findChannelMonitor(sDate, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public Page<ATmnlFlowMBeanH> findChannelH(String sDate, String orgName,String orgType,
			String over, String manufacture, long start, int limit)
			throws DBAccessException {
		try {
			if("true".equals(over)){
				return this.aTmnlFlowMBeanDao.findChannelH(sDate, orgName, orgType,manufacture, start, limit);
			} else {
				return this.aTmnlFlowMBeanDao.findChannelHH(sDate, orgName,orgType,manufacture, start, limit);
			}
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<SimFee> findSimFee()
			throws DBAccessException {
		try {
			return this.aTmnlFlowMBeanDao.findSimFee();
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public List<TmnlFactory> findTmnlFactory()
			throws DBAccessException {
		try {
			return this.aTmnlFlowMBeanDao.findTmnlFactory();
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
}