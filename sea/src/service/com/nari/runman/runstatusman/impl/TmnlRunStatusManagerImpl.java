package com.nari.runman.runstatusman.impl;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.runman.runstatusman.TmnlRunStatusDao;
import com.nari.runman.runstatusman.TmnlRunStatusManager;
import com.nari.statreport.TmnlFactory;
import com.nari.statreport.TmnlRunRemark;
import com.nari.statreport.TmnlRunStatus;
import com.nari.statreport.TmnlRunStatusRun;
import com.nari.statreport.TmnlTypeCode;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class TmnlRunStatusManagerImpl implements TmnlRunStatusManager {
	
	private TmnlRunStatusDao tmnlRunStatusDao;
	
	public void setTmnlRunStatusDao(TmnlRunStatusDao tmnlRunStatusDao) {
		this.tmnlRunStatusDao = tmnlRunStatusDao;
	}
	
	//备注window
	@Override
	public Page<TmnlRunRemark> findRemark(String consNo, long start, int limit)
	throws DBAccessException{
		try{
			return this.tmnlRunStatusDao.findRemark(consNo, start, limit);
		}catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
	}
}
	
	//备注信息保存
	@Override
	public void updateRemark(Long exceptionId, String remark)
	throws DBAccessException{
		try{
			this.tmnlRunStatusDao.updateRemark(exceptionId, remark);
		}catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
	}
}
	
	//**********异常工况页面 查询
	@Override
	public Page<TmnlRunStatus> findOrgNo(String orgNo, String orgType, Date startDate, Date endDate,String tmnlTypeCode,
			long start, int limit) throws DBAccessException {
		try {
			return this.tmnlRunStatusDao.findOrgNo(orgNo, orgType, startDate, endDate,tmnlTypeCode,start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<TmnlRunStatus> findUsr(String consNo, Date startDate, Date endDate,String tmnlTypeCode,long start, int limit)
			throws DBAccessException {
		try{
			return this.tmnlRunStatusDao.findUsr(consNo, startDate, endDate,tmnlTypeCode,start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public Page<TmnlRunStatus> findCgp(String groupNo, Date startDate, Date endDate,String tmnlTypeCode,long start, int limit)
			throws DBAccessException {
		try{
			return this.tmnlRunStatusDao.findCgp(groupNo, startDate, endDate,tmnlTypeCode,start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<TmnlRunStatus> findUgp(String groupNo, Date startDate, Date endDate,String tmnlTypeCode,long start, int limit)
			throws DBAccessException {
		try{
			return this.tmnlRunStatusDao.findUgp(groupNo, startDate, endDate,tmnlTypeCode,start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public Page<TmnlRunStatus> findLine(String lineId, Date startDate, Date endDate,String tmnlTypeCode,long start, int limit)
			throws DBAccessException {
		try{
			return this.tmnlRunStatusDao.findLine(lineId, startDate, endDate,tmnlTypeCode,start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<TmnlRunStatus> findSub(String subsId, Date startDate, Date endDate,String tmnlTypeCode,long start, int limit)
			throws DBAccessException {
		try{
			return this.tmnlRunStatusDao.findSub(subsId, startDate, endDate,tmnlTypeCode,start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	
	
	
	//************实时工况页面 查询
	
	@Override
	public Page<TmnlRunStatusRun> findOrgNoRun(String orgNo, String orgType, String isOnline,String tmnlTypeCode,
			String commCode,String tmnlFactory,long start, int limit) throws DBAccessException {
		try {
			return this.tmnlRunStatusDao.findOrgNoRun(orgNo, orgType, isOnline,tmnlTypeCode,commCode,tmnlFactory, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<TmnlRunStatusRun> findUsrRun(String consNo, String tmnlTypeCode,String isOnline,long start, int limit)
			throws DBAccessException {
		try{
			return this.tmnlRunStatusDao.findUsrRun(consNo,tmnlTypeCode,isOnline, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public Page<TmnlRunStatusRun> findCgpRun(String groupNo,String tmnlTypeCode,String isOnline,String commCode,String tmnlFactory, long start, int limit)
			throws DBAccessException {
		try{
			return this.tmnlRunStatusDao.findCgpRun(groupNo,tmnlTypeCode,isOnline,commCode,tmnlFactory, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<TmnlRunStatusRun> findUgpRun(String groupNo, String tmnlTypeCode,String isOnline,String commCode,String tmnlFactory,long start, int limit)
			throws DBAccessException {
		try{
			return this.tmnlRunStatusDao.findUgpRun(groupNo,tmnlTypeCode,isOnline,commCode,tmnlFactory, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public Page<TmnlRunStatusRun> findLineRun(String lineId, String tmnlTypeCode,String isOnline,String commCode,String tmnlFactory,long start, int limit)
			throws DBAccessException {
		try{
			return this.tmnlRunStatusDao.findLineRun(lineId,tmnlTypeCode,isOnline,commCode,tmnlFactory, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<TmnlRunStatusRun> findSubRun(String subsId,String tmnlTypeCode, String isOnline,String commCode,String tmnlFactory,long start, int limit)
			throws DBAccessException {
		try{
			return this.tmnlRunStatusDao.findSubRun(subsId,tmnlTypeCode,isOnline,commCode,tmnlFactory, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<TmnlRunStatusRun> findFacRun(String factoryCode,PSysUser user,String tmnlTypeCode,String isOnline, long start, int limit)
			throws DBAccessException {
		try{
			return this.tmnlRunStatusDao.findFacRun(factoryCode,user,tmnlTypeCode,isOnline, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * chenjg
	 * 查询所有生产厂商
	 * @return
	 * @throws Exception
	 */
	public List<TmnlFactory> getAllFactory() throws Exception{
		return tmnlRunStatusDao.findAllFactory();
	}
	
	public Page<TmnlRunStatus> queryPageTmnl(String nodeType, String nodeValue, String tmnlTypeCode, PSysUser userInfo, long start, long limit,
			Date startDate, Date endDate) throws Exception{
		try{
			return this.tmnlRunStatusDao.queryPageTmnl(nodeType,nodeValue,tmnlTypeCode,userInfo, start, limit,startDate,endDate);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<TmnlTypeCode> queryTmnlTypeCode() throws Exception {
		try{
			return this.tmnlRunStatusDao.findTmnlTypeCode();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

}
