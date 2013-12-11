package com.nari.runman.abnormalhandle.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.basicdata.ElecExcepBean;
import com.nari.basicdata.FailureStatistic;
import com.nari.basicdata.LostMonitorBean;
import com.nari.basicdata.LostMonitorBean2;
import com.nari.basicdata.TerminalEventBean;
import com.nari.basicdata.TerminalExceptionWorkBean;
import com.nari.privilige.PSysUser;
import com.nari.runman.abnormalhandle.ExceptionMonitorDao;
import com.nari.runman.abnormalhandle.ExceptionMonitorManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

public class ExceptionMonitorManagerImpl implements ExceptionMonitorManager{
	private ExceptionMonitorDao exceptionMonitorDao;
	

	public void setExceptionMonitorDao(ExceptionMonitorDao exceptionMonitorDao) {
		this.exceptionMonitorDao = exceptionMonitorDao;
	}


	public List<TerminalExceptionWorkBean> getTewbData(PSysUser userInfo) throws Exception {
		try {
			return this.exceptionMonitorDao.findTewbData(userInfo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException("异常监测查询出错");
		}

	}
	
	public List<TerminalEventBean> getTelcData() throws Exception {
		try {
			return this.exceptionMonitorDao.findTelcData();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException("异常监测查询出错");
		}

	}
	public List<LostMonitorBean> getLmbData() throws Exception{
		try {
			return this.exceptionMonitorDao.findLmbData();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException("异常监测查询出错");
		}
	}
	public List<LostMonitorBean2> getLmb2Data() throws Exception{
		try {
			return this.exceptionMonitorDao.findLmb2Data();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException("异常监测查询出错");
		}
	}
	
	public List<ElecExcepBean> getStoreElecExcep(PSysUser userInfo) throws Exception{
		
		try {
			return this.exceptionMonitorDao.findStoreElecExcep(userInfo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException("异常监测查询出错");
		}
	}
	public List<FailureStatistic> getFailureStat() throws Exception{
		try {
			return this.exceptionMonitorDao.findFailureStat();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException("异常监测查询出错");
		}
	}
}
