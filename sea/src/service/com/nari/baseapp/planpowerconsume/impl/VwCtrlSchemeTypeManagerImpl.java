package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.planpowerconsume.IVwCtrlSchemeTypeManager;
import com.nari.basicdata.VwCtrlSchemeType;
import com.nari.basicdata.VwCtrlSchemeTypeDao;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class VwCtrlSchemeTypeManagerImpl implements IVwCtrlSchemeTypeManager {
	private  VwCtrlSchemeTypeDao vwCtrlSchemeTypeDao;

	public void setVwCtrlSchemeTypeDao(VwCtrlSchemeTypeDao vwCtrlSchemeTypeDao) {
		this.vwCtrlSchemeTypeDao = vwCtrlSchemeTypeDao;
	}
	public List<VwCtrlSchemeType>  queryCtrlSchemeType()throws DBAccessException{
		try{
			return this.vwCtrlSchemeTypeDao.findAll();
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
	}

}
