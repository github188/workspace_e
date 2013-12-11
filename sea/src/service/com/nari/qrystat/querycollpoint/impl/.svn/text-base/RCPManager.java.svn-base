package com.nari.qrystat.querycollpoint.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.qrystat.querycollpoint.IRCPDao;
import com.nari.qrystat.querycollpoint.IRCPManager;
import com.nari.qrystat.querycollpoint.RCP;
import com.nari.qrystat.querycollpoint.RCollObj;
import com.nari.qrystat.querycollpoint.RcpCommPara;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class RCPManager implements IRCPManager {
	private IRCPDao rcpDao;
	
	public IRCPDao getRcpDao() {
		return rcpDao;
	}

	public void setRcpDao(IRCPDao rcpDao) {
		this.rcpDao = rcpDao;
	}

	@Override
	public List<RCP> findRCP(String consNo) throws DBAccessException {
		try{
			return this.rcpDao.findRCP(consNo);
		}catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
	}

	public Page<RCollObj> findRcpCharge(String consNo,long start, int limit)
	throws DBAccessException{
		try{
			return this.rcpDao.findRcpCharge(consNo, start, limit);
		}catch(DataAccessException e){
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	public List<RcpCommPara> findRcpCommPara(String consNo)
	throws DBAccessException{
		try {
			return this.rcpDao.findRcpCommPara(consNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
	}
}
