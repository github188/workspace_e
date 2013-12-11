package com.nari.baseapp.datagathorman;

import java.util.List;

import com.nari.baseapp.datagatherman.IllegalFrameQry;
import com.nari.baseapp.datagatherman.OrigFrameQryAsset;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 非法报文查询业务层接口
 * 
 * @author ChunMingLi
 * @version 2010-4-25
 * 
 */
public interface IllegalFrameQryManager {
	/**
	 * 
	 * 查询非法报文
	 * 
	 * @param terminalAddr 	终端地址码
	 * @param DateStart 	查询开始时间
	 * @param DateEnd  		查询结束时间
	 * @param start 		分页页数
	 * @param limit			每页显示数量
	 * @return				非法报文DTO
	 * @throws DBAccessException 数据库异常类
	 */
	public Page<IllegalFrameQry> findIllegalFrameQry(String terminalAddr,String DateStart,String DateEnd,long start, int limit) throws DBAccessException;
	/**
	 * 
	 * 查询用户的终端资产号
	 * @param origFrameQryID 	用户户号
	 * @return 用户的终端资产号
	 * @throws DBAccessException
	 */
	public List<OrigFrameQryAsset> findOrigFrameQryAsset(String origFrameQryID)throws DBAccessException;
	
}
