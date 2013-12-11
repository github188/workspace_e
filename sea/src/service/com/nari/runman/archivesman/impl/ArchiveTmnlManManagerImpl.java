package com.nari.runman.archivesman.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RtmnlRunJdbc;
import com.nari.runman.archivesman.IArchiveTmnlManDao;
import com.nari.runman.archivesman.IArchiveTmnlManManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 接口实现类 ArchiveTmnlManManagerImpl
 * 
 * @author zhangzhw
 * @describe 档案管理终端管理接口实现类
 */
public class ArchiveTmnlManManagerImpl implements IArchiveTmnlManManager {

	IArchiveTmnlManDao iArchiveTmnlManDao;

	/**
	 * 通过ConsNo 查询终端列表
	 */
	@Override
	public List<RtmnlRunJdbc> queryTmnlByConsNo(String consNo) throws Exception {
		try {
			return iArchiveTmnlManDao.queryTmnlByConsNo(consNo);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANQUERYTMNLBYCONS);
		}
	}

	/**
	 * 保存Tmnl 列表
	 */
	@Override
	public boolean saveTmnls(String consNo,RtmnlRunJdbc[] rtmnlRunJdbc,RcpParaJdbc[] rcpParaJdbc) throws Exception {
		try {
			return iArchiveTmnlManDao.saveTmnls(consNo,rtmnlRunJdbc,rcpParaJdbc);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANSAVETMNLS);
		}
	}

	/**
	 * 删除终端信息
	 * @param tmnladdr 要进行删除的终端地址
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTmnl(String tmnladdr) throws Exception{
		return iArchiveTmnlManDao.deleteTmnl(tmnladdr) == 1?true:false;
	}
	
	/**
	 * 根据终端地址信息查询终端详细参数信息
	 * @param tmnladdrs 终端地址
	 * @return
	 */
	@Override
	public List<RcpParaJdbc> queryTmnlParams(String tmnladdrs){
		return iArchiveTmnlManDao.queryTmnlParams(tmnladdrs);
	}
	// getters and setters
	public IArchiveTmnlManDao getiArchiveTmnlManDao() {
		return iArchiveTmnlManDao;
	}

	public void setiArchiveTmnlManDao(IArchiveTmnlManDao iArchiveTmnlManDao) {
		this.iArchiveTmnlManDao = iArchiveTmnlManDao;
	}

}
