package com.nari.qrystat.querycollpoint.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.querycollpoint.IpublicTerminalDao;
import com.nari.qrystat.querycollpoint.IpublicTerminalManager;
import com.nari.qrystat.querycollpoint.PublicTerminal;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class PublicTerminalManagerImpl implements IpublicTerminalManager {

	private IpublicTerminalDao publicTerminalDao; 

	/**
	 * @param publicTerminalDao the publicTerminalDao to set
	 */
	public void setPublicTerminalDao(IpublicTerminalDao publicTerminalDao) {
		this.publicTerminalDao = publicTerminalDao;
	}
	
	
	//终端状态
	@Override
	public List<PublicTerminal> findTmnlStatus()throws DBAccessException{
		try {
			return this.publicTerminalDao.findTmnlStatus();
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	

	@Override
	public Page<PublicTerminal> findPBTerminal(String consType,String pbid,String statusName,String publicOrgType,String pbtype, long start, int limit,PSysUser pSysUser) throws DBAccessException {
		try {
			return publicTerminalDao.findPBTerminal(consType,pbid,statusName,publicOrgType,pbtype, start, limit, pSysUser);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	@Override
	public Page<PublicTerminal> findPRTerminal(String consType,String pbid,String statusName,String publicOrgType,String pbtype, long start, int limit,PSysUser pSysUser) throws DBAccessException {
		try {
			return publicTerminalDao.findPRTerminal(consType,pbid,statusName,publicOrgType,pbtype, start, limit,pSysUser);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

}
