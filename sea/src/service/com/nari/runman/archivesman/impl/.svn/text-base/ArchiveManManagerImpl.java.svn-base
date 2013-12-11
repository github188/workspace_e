package com.nari.runman.archivesman.impl;

import org.springframework.dao.DataAccessException;

import com.nari.customer.CCons;
import com.nari.runman.archivesman.IArchieveManDao;
import com.nari.runman.archivesman.IArchiveManManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 类 ArchiveManManagerImpl
 * 
 * @author zhangzhw
 * @describe 档案管理实现
 */
public class ArchiveManManagerImpl implements IArchiveManManager {

	// DAO接口
	IArchieveManDao archieveManDao;

	/**
	 * 通过用户节点名查询用户信息
	 */
	@Override
	public CCons queryArchive(String nodeId) throws Exception {
		try {
			String[] nodes = nodeId.split("_");
			if (nodes.length > 1)
				return archieveManDao.findCConsById(nodes[1]);
			return null;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANQUERYCONS);
		}

	}

	/**
	 * 保存CCons 信息
	 */
	@Override
	public CCons saveArchive(CCons ccons) throws Exception {
		CCons cons;
		cons = archieveManDao.findCConsByNo(ccons.getConsNo());
		if (ccons.getConsId() == null || ccons.getConsId() == 0) {
			if (cons != null)
				throw new DBAccessException("用户编号不能重复！");
		} else {
			cons = archieveManDao.findCConsById(cons.getConsId().toString());
			if (!cons.getConsNo().equals(ccons.getConsNo()))
				throw new DBAccessException("用户编号不允许修改");
			// if (cons == null)
			// throw new DBAccessException("用户已不存在，无法更新！");
			// else if (!cons.getConsId().equals(ccons.getConsId()))
			// throw new DBAccessException("用户编号与另一用户重复！");
		}

		try {
			cons = archieveManDao.saveOrUpdate(ccons);
			return cons;
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANSAVECONS);
		}
	}

	
	
	// getters and setters
	public IArchieveManDao getArchieveManDao() {
		return archieveManDao;
	}

	public void setArchieveManDao(IArchieveManDao archieveManDao) {
		this.archieveManDao = archieveManDao;
	}

}
