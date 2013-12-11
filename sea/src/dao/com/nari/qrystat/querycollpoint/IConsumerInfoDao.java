package com.nari.qrystat.querycollpoint;
import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface IConsumerInfoDao {
	/**
	 * 
	 * @param CONS_NO
	 * @return 客户信息
	 * @throws DBAccessException
	 */
	public List<ConsumerInfo> findConsumerInfo(String CONS_NO) throws DBAccessException;
	
	/**
	 * 查询运行终端信息
	 * @param CONS_NO用户编号
	 * @return 终端资产编号不同，返回多个终端信息
	 * @throws DBAccessException
	 */
	public List<Tmnlrun> findTmnlrun(String CONS_NO) throws DBAccessException;
	
	/**
	 * 查询运行电能表信息
	 * @param CONS_NO
	 * @return 电能表信息list
	 * @throws DBAccessException
	 */
	public Page<Cmeter> findCmeter(String CONS_NO,String TG_ID,long start, int limit) throws DBAccessException;
	
	/**
	 * 查询当日实时负荷
	 * @param CONS_NO
	 * @return 当日实时负荷list
	 * @throws DBAccessException
	 */
	public Page<NewMpDayPower> findNewMpDayPower(String assetNo,String flagValue,String CONS_NO,String DateStart,String DateEnd, long start, int limit) throws DBAccessException;

/**
 * 采集点信息
 * @param CONS_NO
 * @return 采集点信息list
 * @throws DBAccessException
 */
public Page<CmeterInfo> findCmeterInfo(String CONS_NO,long start, int limit) throws DBAccessException;
}