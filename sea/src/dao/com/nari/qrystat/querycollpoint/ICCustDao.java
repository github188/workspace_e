package com.nari.qrystat.querycollpoint;

import java.util.List;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface ICCustDao {
	/**
	 * 
	 * @param custNo
	 * @return 客户信息
	 * @throws DBAccessException
	 */
	public List<CCust> findConsumerInfo(String custNo) throws DBAccessException;
	
	/**
	 * 
	 * @param CONS_NO
	 * @param start
	 * @param limit
	 * @return 联系信息
	 * @throws DBAccessException
	 */
	public Page<Ccontact> findCcontact(String custNo,long start,int limit)
	throws DBAccessException;
	
	/**
	 * 
	 * @param consNo
	 * @return 受电点和供电电源
	 * @throws DBAccessException
	 */
	public List<CSP> findCSP(String consNo)
	throws DBAccessException;
	
	/**
	 * 
	 * @param consNo
	 * @return 受电点和供电电源
	 * @throws DBAccessException
	 */
	public Page<CPS> findCPS(String consNo,long start,int limit)
	throws DBAccessException;
	
	/**
	 * 
	 * @param SIM_NO
	 * @return SIM卡流量
	 * @throws DBAccessException
	 */
	public Page<RSIMCharge> findSIM(String SIM_NO,long start,int limit)
	throws DBAccessException;
	
	/**
	 * 
	 * @param ASSET_NO
	 * @return 电能表资产信息
	 * @throws DBAccessException
	 */
	public List<DMeter> findDMeter(String consNo)
	throws DBAccessException;
	
	/**
	 * 
	 * @param tFactor
	 * @return 运行互感器
	 * @throws DBAccessException
	 */
	public Page<CITRun> findCITRun(String tFactor,long start,int limit)
	throws DBAccessException;
	
	/**
	 * 
	 * @param CONS_ID
	 * @return 用户变压器信息
	 * @throws DBAccessException
	 */
	public List<Gtran> findGtran(String CONS_ID,String consType)
	throws DBAccessException;


/**
 * 
 * @param CONS_ID
 * @return 用户变压器信息
 * @throws DBAccessException
 */
public Page<Gtran> findGtran(String CONS_ID,long start,int limit)
throws DBAccessException;

}