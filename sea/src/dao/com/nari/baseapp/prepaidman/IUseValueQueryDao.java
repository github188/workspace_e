package com.nari.baseapp.prepaidman;

import java.util.Date;
import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface IUseValueQueryDao {
	
    /**
     * 剩余电量查询
     * @param orgName
     * @param consNo
     * @param appNo
     * @param terminalAddr
     * @param startDate
     * @param endDate
     * @param start
     * @param limit
     * @return
     * @throws DBAccessException
     */
	public Page<UseValueResult> valueQueryBy(String orgNo,String consNo,String appNo,
			 String terminalAddr,String startDate,String endDate, long start, long limit)throws DBAccessException;
	
	/**
	 * 剩余电量查询（不分页）
	 * @param orgName
	 * @param consNo
	 * @param appNo
	 * @param terminalAddr
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws DBAccessException
	 *//*
	public List<UseValueResult> valueListQueryBy(String orgName,String consNo,String appNo,
			 String terminalAddr,String startDate,String endDate)throws DBAccessException;*/
	
	/**
	 * 剩余值明细查询
	 * @param tmnlAssetNo
	 * @param totalNo
	 * @param orgName
	 * @param consNo
	 * @param appNo
	 * @param terminalAddr
	 * @param startDate
	 * @param endDate
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	public Page<UseValueResult> valueDetailQueryBy(String tmnlAssetNo,Short totalNo,String orgNo,String consNo,String appNo,
			 String terminalAddr,String startDate,String endDate, long start, long limit)throws DBAccessException;
	
	/**
	 * 获取终端Id
	 * @param TmnlAssetNo
	 * @return
	 * @throws DBAccessException
	 */
	public List<String> findTerminalId(String tmnlAssetNo)throws DBAccessException;
	
	/**
	 * 添加剩余值召测信息
	 * @param orgNo
	 * @param appNo
	 * @param consNo
	 * @param terminalId
	 * @param totalNo
	 * @param meterId
	 * @param UseValue
	 * @param statusCode
	 * @param opTime
	 * @param errCause
	 * @throws DBAccessException
	 */
	public void addUseValue(String orgNo, String appNo, String consNo, String terminalId,Short totalNo,
			String meterId, String UseValue, short statusCode, Date opTime,
			String errCause)throws DBAccessException;
	
}
