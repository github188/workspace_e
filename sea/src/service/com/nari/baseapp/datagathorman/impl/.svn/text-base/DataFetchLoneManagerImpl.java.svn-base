package com.nari.baseapp.datagathorman.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.baseapp.datagathorman.DataFetchLoneDao;
import com.nari.baseapp.datagathorman.DataFetchLoneManager;
import com.nari.support.Page;
import com.nari.util.TreeNode;
import com.nari.util.exception.ServiceException;

public class DataFetchLoneManagerImpl implements DataFetchLoneManager {
	private Logger logger = Logger.getLogger(this.getClass());
	private DataFetchLoneDao dataFetchLoneDao;
	public void setDataFetchLoneDao(DataFetchLoneDao dataFetchLoneDao) {
		this.dataFetchLoneDao = dataFetchLoneDao;
	}
	@Override
	public Page<Map> findFrmUnderTmnl(String tmnlAssetNo, long start, long limit)
			throws ServiceException {
		try {
			return dataFetchLoneDao.findFrmUnderTmnl(tmnlAssetNo, start, limit);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException();
		}
	}

	@Override
	public Page<Map> findMeterUnderFrm(String tmnlAssetNo, String frmId,
			long start, long limit) throws ServiceException {
		try {
			return dataFetchLoneDao.findMeterUnderFrm(tmnlAssetNo, frmId,
					start, limit);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException();
		}
	}

	@Override
	public Page<Map> findUnderUser(String tmnlAssetNo, long start, int limit)
			throws ServiceException {
		try {
			return dataFetchLoneDao.findUnderUser(tmnlAssetNo, start, limit);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException();
		}
	}

	@Override
	public Page<TreeNode> findPointTree(String nodeStr, long start, int limit)
			throws ServiceException {
		try {
			return dataFetchLoneDao.generalPointTree(nodeStr, start, limit);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map findDataById(String id) throws ServiceException {
		try {
			return dataFetchLoneDao.findDataById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException();
		}
	}

	

	@Override
	public List<TreeNode> findClearTree(String nodeStr) throws ServiceException {
		try {
			return dataFetchLoneDao.dealClearTree(nodeStr);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException();
		}
	}
	@Override
	public List<TreeNode> findTypeTree(String nodeStr) throws ServiceException {
		try {
			return dataFetchLoneDao.createTypeTree(nodeStr);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException();
		}
	}
	@Override
	public Long findCoutUser(String tmnlAssetNo) throws ServiceException {
		try {
			return dataFetchLoneDao.findCoutUser(tmnlAssetNo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException();
		}
		
	}

	

}
