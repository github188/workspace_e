package com.nari.runman.archivesman.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.runcontrol.CmeterJdbc;
import com.nari.runman.archivesman.IArchiveCmeterManDao;
import com.nari.runman.archivesman.IArchiveCmeterManManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 服务实现类 ArchiveCmeterManManagerImpl
 * 
 * @author zhangzhw
 * @describe 档案管理电能表管理业务实现
 */
public class ArchiveCmeterManManagerImpl implements IArchiveCmeterManManager {

	IArchiveCmeterManDao iArchiveCmeterManDao;

	/**
	 * 通过consNo查询　Cmeter 列表
	 */
	@Override
	public List<CmeterJdbc> queryCmeterByConsNo(String consNo) throws Exception {
		try {
			return iArchiveCmeterManDao.queryCmeterByConsNo(consNo);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANQUERYCMETERBYCONSEXCEPTION);
		}
	}

	/**
	 * 保存　Cmeter 列表
	 */
	@Override
	public boolean saveCmeter(CmeterJdbc[] cmeterJdbc) throws Exception {
		try {
			return iArchiveCmeterManDao.saveCmeter(cmeterJdbc);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANSAVECMETEREXCEPTION);
		}
	}

	// getters and setters
	public IArchiveCmeterManDao getiArchiveCmeterManDao() {
		return iArchiveCmeterManDao;
	}

	public void setiArchiveCmeterManDao(
			IArchiveCmeterManDao iArchiveCmeterManDao) {
		this.iArchiveCmeterManDao = iArchiveCmeterManDao;
	}

}
