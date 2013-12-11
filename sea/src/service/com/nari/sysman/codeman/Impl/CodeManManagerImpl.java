package com.nari.sysman.codeman.Impl;

import org.springframework.dao.DataAccessException;

import com.nari.support.Page;
import com.nari.sysman.codeman.CodeManIn;
import com.nari.sysman.codeman.CodeManOut;
import com.nari.sysman.codeman.CodeManOutSub;
import com.nari.sysman.codeman.ICodeManDao;
import com.nari.sysman.codeman.ICodeManManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class CodeManManagerImpl implements ICodeManManager{
	private ICodeManDao codeManDao;
	


	public void setCodeManDao(ICodeManDao codeManDao) {
		this.codeManDao = codeManDao;
	}



	@Override
	public Page<CodeManOut> queryCodeManOut(long start, int limit)
			throws Exception {
		try {
			return codeManDao.queryCodeManOut(start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));			
		}
	}



	@Override
	public Page<CodeManOutSub> queryCodeManOutSub(String sortId, long start,
			int limit) throws Exception {
		try {
			return codeManDao.queryCodeManOutSub(sortId, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));			
		}
	}



	@Override
	public Page<CodeManIn> queryCodeManIn(long start, int limit)
			throws Exception {
		try {
			return codeManDao.queryCodeManIn(start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));			
		}
	}



	@Override
	public Page<CodeManIn> queryCodeManInSub(String sortId, long start,
			int limit) throws Exception {
		try {
			return codeManDao.queryCodeManInSub(sortId, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));			
		}
	}



	@Override
	public void CodeManSet(String sql) throws Exception {
		try {
			 codeManDao.CodeManSet(sql);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));			
		}
	}

}
