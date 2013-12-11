package com.nari.qrystat.querycollpoint;

import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface IRCPDao {
	/**
	 * 
	 * @param consNo 客户号
	 * @return 采集点
	 * @throws DBAccessException
	 */
	public List<RCP> findRCP(String consNo) throws DBAccessException;
	
	/**
	 * 
	 * @param consNo 客户号
	 * @return 采集对象列表
	 * @throws DBAccessException
	 */
	public Page<RCollObj> findRcpCharge(String consNo,long start, int limit)
	throws DBAccessException;
	
	/**
	 * 
	 * @param consNo 客户号
	 * @return 采集点通讯参数
	 * @throws DBAccessException
	 */
	public List<RcpCommPara> findRcpCommPara(String consNo)
	throws DBAccessException;
}
