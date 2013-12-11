package com.nari.advapp.statanalyse.impl;

import java.util.Date;

import org.springframework.dao.DataAccessException;

import com.nari.advapp.statanalyse.CollectionPointDemandDao;
import com.nari.advapp.statanalyse.CollectionPointDemandDto;
import com.nari.advapp.statanalyse.CollectionPointDemandManager;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 * 
 * 测量点需量分析 manager实现类
 * 
 * @author ChunMingLi
 * @since 2010-7-27
 * 
 */
public class CollectionPointDemandManagerImpl implements
		CollectionPointDemandManager {
	// dao注入
	private CollectionPointDemandDao collectionPointDemandDao;

	/**
	 * 设值注入
	 * 
	 * @param collectionPointDemandDao
	 *            the collectionPointDemandDao to set
	 */
	public void setCollectionPointDemandDao(
			CollectionPointDemandDao collectionPointDemandDao) {
		this.collectionPointDemandDao = collectionPointDemandDao;
	}

	@Override
	public Page<CollectionPointDemandDto> queryCollectionPointDemandDate(String orgType,
			String nodeType, String nodeValue, PSysUser userInfo, long start,
			long limit, Date startDate, Date endDate) throws Exception {
		try {
			return collectionPointDemandDao.findCollectionPointDemandDate(orgType,
					nodeType, nodeValue, userInfo, start, limit, startDate,
					endDate);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("查询按日测量点需量分析SQL查询操作出错");
		}
	}

	@Override
	public Page<CollectionPointDemandDto> queryCollectionPointDemandMonth(String orgType,
			String nodeType, String nodeValue, PSysUser userInfo, long start,
			long limit, Date startDate, Date endDate) throws Exception {
		try {
			return collectionPointDemandDao.findCollectionPointDemandMonth(orgType,
					nodeType, nodeValue, userInfo, start, limit, startDate,
					endDate);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("查询按月测量点需量分析SQL查询操作出错");
		}
	}

}
