package com.nari.runman.archivesman.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.runcontrol.CmpJdbc;
import com.nari.runman.archivesman.IArchiveCmpManDao;
import com.nari.runman.archivesman.IArchiveCmpManManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 服务实现类 ArchiveCmpManManagerImpl
 * 
 * @author zhangzhw
 * @describe 档案管理计量点管理业务实现
 */
public class ArchiveCmpManManagerImpl implements IArchiveCmpManManager {

	IArchiveCmpManDao iArchiveCmpManDao;

	/**
	 * 通过consNo查询 Cmp 列表
	 */
	@Override
	public List<CmpJdbc> queryCmpByConsNo(String consNo) throws Exception {
		try {
			return iArchiveCmpManDao.queryCmpByConsNo(consNo);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANQUERYCMPBYCONS);
		}
	}

	/**
	 * 保存Cmp 列表
	 */
	@Override
	public boolean saveCmp(CmpJdbc[] cmpJdbc) throws Exception {
		try {
			return iArchiveCmpManDao.saveCmp(cmpJdbc);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANSAVECMPBYCONS);
		}
	}

	// getters and setters
	public IArchiveCmpManDao getiArchiveCmpManDao() {
		return iArchiveCmpManDao;
	}

	public void setiArchiveCmpManDao(IArchiveCmpManDao iArchiveCmpManDao) {
		this.iArchiveCmpManDao = iArchiveCmpManDao;
	}

}
