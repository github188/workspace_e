package com.nari.baseapp.datagathorman.impl;

import java.util.List;

import com.nari.baseapp.datagatherman.HeartBeat;
import com.nari.baseapp.datagatherman.OnoffStat;
import com.nari.baseapp.datagatherman.OrigFrameQry;
import com.nari.baseapp.datagatherman.OrigFrameQryAsset;
import com.nari.baseapp.datagathorman.OrigFrameQryDao;
import com.nari.baseapp.datagathorman.OrigFrameQryManager;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class OrigFrameQryManagerImpl  implements OrigFrameQryManager{
	private OrigFrameQryDao origFrameQryDao;
	
	public OrigFrameQryDao getOrigFrameQryDao() {
		return origFrameQryDao;
	}

	public void setOrigFrameQryDao(OrigFrameQryDao origFrameQryDao) {
		this.origFrameQryDao = origFrameQryDao;
	}

	@Override
	public Page<OrigFrameQry> findOrigFrameQry(String terminalAddr,String DateStart,String DateEnd,long start, int limit)
	throws DBAccessException {
		return this.getOrigFrameQryDao().findOrigFrameQry(terminalAddr, DateStart, DateEnd, start, limit);
	}
	
	public List<OrigFrameQryAsset> findOrigFrameQryAsset(String origFrameQryID)throws DBAccessException{
		return this.getOrigFrameQryDao().findOrigFrameQryAsset(origFrameQryID);
	}

	@Override
	public List<OrigFrameQryAsset> findOrigFrameQryTmnl(String terminalAddr)
			throws DBAccessException {
		return this.getOrigFrameQryDao().findOrigFrameQryTmnl(terminalAddr);
	}
	/**
	 * 查询心跳信息
	 * @param consNo,DateStart,DateEnd
	 * @author 陈国章
	 * @return
	 */
	public Page<HeartBeat> queryHeartBeat(String consNo,String terminalAddr, String dateStart,
			String dateEnd,String protocolCode,long start, int limit){
		return this.origFrameQryDao.queryHeartBeat(consNo,terminalAddr,dateStart,dateEnd,protocolCode,start,limit);
	}
	/**
	 * 查询心跳信息
	 * @param consNo,DateStart,DateEnd
	 * @author 陈国章
	 * @return
	 */
	public List<OnoffStat> onoffStat(String consNo, String terminalAddr,
			String dateStart, String dateEnd){
		return this.origFrameQryDao.onoffStat(consNo,terminalAddr,dateStart,dateEnd);
	}


}
