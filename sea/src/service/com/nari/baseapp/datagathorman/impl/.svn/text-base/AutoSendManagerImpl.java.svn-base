package com.nari.baseapp.datagathorman.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.datagatherman.AutoSendDto;
import com.nari.baseapp.datagathorman.AutoSendJdbcDao;
import com.nari.baseapp.datagathorman.AutoSendManager;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class AutoSendManagerImpl implements AutoSendManager {

	private AutoSendJdbcDao autoSendJdbcDao;
	public void setAutoSendJdbcDao(AutoSendJdbcDao autoSendJdbcDao) {
		this.autoSendJdbcDao = autoSendJdbcDao;
	}

	/**
	 * 查询居民集抄用户信息
	 * @param consId 用户id
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	public Page<AutoSendDto> findAsQueryInfo(String consId,String queryTmnlNo, long start, int limit)
			throws DBAccessException {
		try {
			return autoSendJdbcDao.findASQuery(consId,queryTmnlNo, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));			
		}
	}
	
	public List<AutoSendDto> queryMeterInfo(String tmnlAssetNo,String[] regSnArray) throws Exception{
		return null;
	}
}
