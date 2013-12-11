package com.nari.runman.archivesman.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.cp.REODev;
import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RcpRunJdbc;
import com.nari.runman.archivesman.IArchiveRcpManDao;
import com.nari.runman.archivesman.IArchiveRcpManManager;
import com.nari.runman.runstatusman.CMeterDto;
import com.nari.runman.runstatusman.RCollObj;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 服务实现类 ArchiveRcpManManagerImpl
 * 
 * @author zhangzhw
 * @describe 档案管理采集点管理业务实现
 */
public class ArchiveRcpManManagerImpl implements IArchiveRcpManManager {

	IArchiveRcpManDao iArchiveRcpManDao;

	/**
	 * 通过consId查询 Rcp参数
	 */
	@Override
	public List<RcpRunJdbc> queryRcpByConsId(String consId) throws Exception {
		try {
			return iArchiveRcpManDao.queryRcpByconsId(consId);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANQUERYRCPBYCONS);
		}
	}

	/**
	 * 保存Rcp 和 Rcp 参数
	 */
	@Override
	public boolean saveRcps(RcpRunJdbc[] rcpRunJdbc, RcpParaJdbc[] rcpParaJdbc,String consId)
			throws Exception {
		try {
			return iArchiveRcpManDao.saveRcps(rcpRunJdbc, rcpParaJdbc, consId);

		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANSAVERCPS);
		}
	}

	@Override
	public boolean deleteRcp(String cpNo) throws Exception{
		if(iArchiveRcpManDao.deleteRcp(cpNo)==1){
			return true;
		}
		return false;
	}
	
	/**
	 * 保存RCP值
	 * @param rcpRunJdbc 组成SQL语句的RCP值
	 * @param consId 当前的用电用户ID
	 * @return
	 */
	@Override
	public boolean saveRcps(RcpRunJdbc[] rcpRunJdbc,String consId) throws Exception {
		try {
			return iArchiveRcpManDao.saveRcps(rcpRunJdbc, consId);

		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANSAVERCPS);
		}
	}
	
	
	
	/**
	 * 通过consId查询Rcp
	 */
	@Override
	public List<RcpParaJdbc> queryRcpParaByConsId(String consId)
			throws Exception {
		try {
			return iArchiveRcpManDao.queryRcpParaByconsId(consId);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANQUERYRCPPARABYCONS);
		}
	}

	/**
	 * 根据采集点标识查询此采集点下的所有采集器信息
	 * @param cpno 采集点标识
	 * @return 此采集点下所有采集器List
	 */
	public List<REODev> queryREODev(String cpno) throws Exception{
		try {
			return iArchiveRcpManDao.queryREODev(cpno);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANQUERYRCPPARABYCONS);
		}
	}
	
	/**
	 * 删除采集器信息
	 * @return
	 * @throws Exception
	 */
	public boolean deleteREODev(String collId) throws Exception{
		return iArchiveRcpManDao.deleteREODev(collId) == 1?true:false;
	}
	
	/**
	 * 保存采集器
	 * @return
	 * @throws Exception
	 */
	public boolean saveREODev(REODev reodev) throws Exception{
		return iArchiveRcpManDao.saveREODev(reodev)==1?true:false;
	}
	
	
	
	@Override
	public boolean updateREODev(REODev[] reodev) {
		return iArchiveRcpManDao.updateREODev(reodev)==1?true:false;
	}

	/**
	 * 查询待挂电能表信息
	 * @param cpNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<CMeterDto> queryCmeterIsHang(String cpNo,long start,int limit) throws Exception {
		return iArchiveRcpManDao.queryCmeterIsHang(cpNo,start,limit);
	}
	
	@Override
	public Page<CMeterDto> queryCmeterIsNotHang(String cpNo,long start,int limit) throws Exception{
		return iArchiveRcpManDao.queryCmeterIsNotHang(cpNo,start,limit);
	}
	/**
	 * 保存采集对象信息
	 * @param rco 采集对象
	 * @return 成功返回采集对象标识 失败返回为空
	 * @throws Exception
	 */
	@Override
	public String saveCmeterHangInfo(RCollObj rco) throws Exception {
		return iArchiveRcpManDao.saveCmeterHangInfo(rco);
	}
	
	@Override
	public boolean deleteCmeterHang(String collobjid) throws Exception {
		return iArchiveRcpManDao.deleteCmeterHang(collobjid)==1?true:false;
		
	};
	// getters and setters
	public IArchiveRcpManDao getiArchiveRcpManDao() {
		return iArchiveRcpManDao;
	}

	public void setiArchiveRcpManDao(IArchiveRcpManDao iArchiveRcpManDao) {
		this.iArchiveRcpManDao = iArchiveRcpManDao;
	}
}
