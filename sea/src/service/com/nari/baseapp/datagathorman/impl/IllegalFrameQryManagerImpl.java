package com.nari.baseapp.datagathorman.impl;

import java.util.List;

import com.nari.baseapp.datagatherman.IllegalFrameQry;
import com.nari.baseapp.datagatherman.OrigFrameQryAsset;
import com.nari.baseapp.datagathorman.IllegalFrameQryDao;
import com.nari.baseapp.datagathorman.IllegalFrameQryManager;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class IllegalFrameQryManagerImpl implements IllegalFrameQryManager {
	
	//非法报文查询dao
	private IllegalFrameQryDao illegalFrameQryDao;
	@Override
	public Page<IllegalFrameQry> findIllegalFrameQry(String terminalAddr,
			String DateStart, String DateEnd, long start, int limit)
			throws DBAccessException {
		return this.illegalFrameQryDao.findIllegalFrameQry(terminalAddr, DateStart, DateEnd, start, limit);
	}

	@Override
	public List<OrigFrameQryAsset> findOrigFrameQryAsset(String illegalFrameQryID)
			throws DBAccessException {
		return this.illegalFrameQryDao.findIllegalFrameQryAsset(illegalFrameQryID);
	}

	public IllegalFrameQryDao getIllegalFrameQryDao() {
		return illegalFrameQryDao;
	}

	public void setIllegalFrameQryDao(IllegalFrameQryDao illegalFrameQryDao) {
		this.illegalFrameQryDao = illegalFrameQryDao;
	}


}
