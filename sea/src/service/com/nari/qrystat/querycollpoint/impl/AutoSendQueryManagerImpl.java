package com.nari.qrystat.querycollpoint.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.nari.qrystat.querycollpoint.AutoSendQuery;
import com.nari.qrystat.querycollpoint.IAutoSendQueryDao;
import com.nari.qrystat.querycollpoint.IAutoSendQueryManager;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class AutoSendQueryManagerImpl implements IAutoSendQueryManager {
	
	private IAutoSendQueryDao autoSendQueryDao;
	
	/**
	 * @param autoSendQueryDao the autoSendQueryDao to set
	 */
	public void setAutoSendQueryDao(IAutoSendQueryDao autoSendQueryDao) {
		this.autoSendQueryDao = autoSendQueryDao;
	}

	@Override
	public Page<AutoSendQuery> findASQuery(String tgId ,String orgType,String assetNo,String nodeType,String nodeValue,String consType, long start, int limit)throws DBAccessException {
		try {
			return autoSendQueryDao.findASQuery(tgId,orgType,assetNo,nodeType,nodeValue,consType, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));			
		}
	}

	@Override
	public List<AutoSendQuery> findtmnlAssetNo(String tgId)
			throws DBAccessException {
		try {
			return autoSendQueryDao.findtmnlAssetNo(tgId);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));			
		}
	}

}
