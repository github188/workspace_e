package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.logofsys.LOpTmnlLog;
import com.nari.baseapp.planpowerconsume.ILOpTmnlLogDao;
import com.nari.baseapp.planpowerconsume.ILOpTmnlLogManager;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2009-12-22 下午01:38:04 
 * 描述：
 */

public class LOpTmnlLogManagerImpl implements  ILOpTmnlLogManager{
	
	private ILOpTmnlLogDao iLOpTmnlLogDao;

	public ILOpTmnlLogDao getiLOpTmnlLogDao() {
		return iLOpTmnlLogDao;
	}

	public void setiLOpTmnlLogDao(ILOpTmnlLogDao iLOpTmnlLogDao) {
		this.iLOpTmnlLogDao = iLOpTmnlLogDao;
	}
	   
	 public void saveTmnlLog(LOpTmnlLog lOpTmnlLog) throws DBAccessException{
		try{
		  this.iLOpTmnlLogDao.save(lOpTmnlLog);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	 }
	public void updateTmnlLog(LOpTmnlLog lOpTmnlLog) throws DBAccessException{
		try {
		  this.iLOpTmnlLogDao.update(lOpTmnlLog);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
	 }
	public List<LOpTmnlLog>findTmnlLogBy(List<PropertyFilter> filters)throws DBAccessException{
		try {
			return this.iLOpTmnlLogDao.findBy(filters);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
	}
}
