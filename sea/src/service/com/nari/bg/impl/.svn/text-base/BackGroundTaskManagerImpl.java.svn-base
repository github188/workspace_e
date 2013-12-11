package com.nari.bg.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.bg.BackGroundTaskHibernateDao;
import com.nari.bg.BackGroundTaskJdbcDao;
import com.nari.bg.BackGroundTaskManager;
import com.nari.bg.TBgTask;
import com.nari.bg.TBgTaskDetail;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

public class BackGroundTaskManagerImpl implements BackGroundTaskManager {

	private BackGroundTaskJdbcDao backGroundTaskJdbcDao;

	private BackGroundTaskHibernateDao backGroundTaskHibernateDao;

	@Override
	public long getTaskId() throws Exception{
		try {
			return this.backGroundTaskJdbcDao.getTaskId();
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_GRIDTREEMANAGER);
		}
	}

	@Override
	public void saveTBgTaskDetail(List<TBgTaskDetail> tBgTaskDetail) throws Exception{
		try {
			this.backGroundTaskHibernateDao.saveTBgTaskDetail(tBgTaskDetail);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_GRIDTREEMANAGER);
		}
	}

	@Override
	public void saveTBgTaskInfo(List<TBgTask> tBgTask) throws Exception{
		try {
			this.backGroundTaskHibernateDao.saveTBgTaskInfo(tBgTask);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_GRIDTREEMANAGER);
		}

	}

	public void setBackGroundTaskJdbcDao(
			BackGroundTaskJdbcDao backGroundTaskJdbcDao) {
		this.backGroundTaskJdbcDao = backGroundTaskJdbcDao;
	}

	public void setBackGroundTaskHibernateDao(
			BackGroundTaskHibernateDao backGroundTaskHibernateDao) {
		this.backGroundTaskHibernateDao = backGroundTaskHibernateDao;
	}

}
