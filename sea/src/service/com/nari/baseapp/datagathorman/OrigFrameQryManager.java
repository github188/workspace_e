package com.nari.baseapp.datagathorman;

import java.util.List;

import com.nari.baseapp.datagatherman.HeartBeat;
import com.nari.baseapp.datagatherman.OnoffStat;
import com.nari.baseapp.datagatherman.OrigFrameQry;
import com.nari.baseapp.datagatherman.OrigFrameQryAsset;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 原始报文业务层接口
 * @author zhaoliang
 */
public interface OrigFrameQryManager {
	public Page<OrigFrameQry> findOrigFrameQry(String terminalAddr,String DateStart,String DateEnd,long start, int limit)
	throws DBAccessException;
	public List<OrigFrameQryAsset> findOrigFrameQryAsset(String origFrameQryID)throws DBAccessException;
	public List<OrigFrameQryAsset> findOrigFrameQryTmnl(String terminalAddr)
	throws DBAccessException;
	/**
	 * 查询心跳信息
	 * @param consNo,DateStart,DateEnd
	 * @author 陈国章
	 * @param limit 
	 * @param start 
	 * @return
	 */
	public Page<HeartBeat> queryHeartBeat(String consNo,String terminalAddr,String dateStart,
			String dateEnd,String protocolCode, long start, int limit);
	/**
	 * 查询在线离线数量
	 * @params
	 */
	public List<OnoffStat> onoffStat(String consNo, String terminalAddr,
			String dateStart, String dateEnd);
}
