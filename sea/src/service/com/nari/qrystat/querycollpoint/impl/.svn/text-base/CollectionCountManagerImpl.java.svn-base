package com.nari.qrystat.querycollpoint.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.querycollpoint.CollectionCountDTO;
import com.nari.qrystat.querycollpoint.CollectionCountDao;
import com.nari.qrystat.querycollpoint.ICollectionCountManager;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class CollectionCountManagerImpl implements ICollectionCountManager{
	private CollectionCountDao collectionCountDao;
	
	public void setCollectionCountDao(CollectionCountDao collectionCountDao) {
		this.collectionCountDao = collectionCountDao;
	}

	@Override
	public Page<CollectionCountDTO> findCollectionCount(String treeType,String orgNo,String consNo,
			long start, int limit,PSysUser pSysUser,String dateStart,String dateEnd) throws DBAccessException {
		try {
			return collectionCountDao.findCollectionCount(treeType,orgNo,consNo, start, limit,pSysUser,dateStart,dateEnd);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));			
		}
	}

	@Override
	public List<CollectionCountDTO> findCollectionCountSUM(String treeType,String orgNo,String consNo, PSysUser pSysUser,String dateStart,String dateEnd) throws DBAccessException {
		try {
			return collectionCountDao.findCollectionCountSUM(treeType,orgNo,consNo, pSysUser, dateStart,dateEnd);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));			
		}
	}
}
