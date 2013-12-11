package com.nari.baseapp.datagathorman;

import java.util.List;

import com.nari.baseapp.datagatherman.HeartBeat;
import com.nari.baseapp.datagatherman.OnoffStat;
import com.nari.baseapp.datagatherman.OrigFrameQry;
import com.nari.baseapp.datagatherman.OrigFrameQryAsset;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 查询报文
 * @param terminal_addr 终端地址码
 * @throws DBAccessException
 * @return 报文DTO
 * @author zhaoliang
 *
 */
public interface OrigFrameQryDao {
	public Page<OrigFrameQry> findOrigFrameQry(String terminalAddr,String DateStart,String DateEnd,long start, int limit)
	throws DBAccessException ;

/**
	 * 查询用户的终端资产号
	 * @param origFrameQryID 用户号
	 * @throws DBAccessException
	 * @return 终端资产号
	 * @author zhaoliang
	 *
	 */
public List<OrigFrameQryAsset> findOrigFrameQryAsset(String origFrameQryID)throws DBAccessException;

/**
 * 查询用户的表计资产号
 * @param 终端地址 用户号
 * @throws DBAccessException
 * @return 表计资产号
 * @author zhaoliang
 *
 */
public List<OrigFrameQryAsset> findOrigFrameQryTmnl(String origFrameQryID)throws DBAccessException;
/**
 * 查询心跳信息
 * @param consNo,DateStart,DateEnd
 * @author 陈国章
 * @param limit 
 * @param start 
 * @param dateEnd2 
 * @return
 */
public Page<HeartBeat> queryHeartBeat(String consNo, String terminalAddr,String dateStart,
		String dateEnd,String protocolCode, long start, int limit);
/**
 * 查询心跳信息
 * @param consNo,DateStart,DateEnd
 * @author 陈国章
 * @return
 */
public List<OnoffStat> onoffStat(String consNo, String terminalAddr,
		String dateStart, String dateEnd);


}