package com.nari.baseapp.prepaidman;

import java.util.List;

import com.nari.util.exception.DBAccessException;
import com.nari.support.Page;

public interface IUseValueQueryManager {
     /**
      * 查询剩余电量
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
	 public Page<UseValueResult> valueQuery(
			 String orgName,String consNo,String appNo,
			 String terminalAddr,String startDate,String endDate, long start, long limit)throws DBAccessException;
	
	 /**
	 *  查询剩余值明细
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
	 public Page<UseValueResult> valueDetailQuery(
			 String tmnlAssetNo,Short totalNo,
			 String orgName,String consNo,String appNo,
			 String terminalAddr,String startDate,String endDate, long start, long limit)throws DBAccessException;
	 
	 /**
	  * 召测剩余值
	  * @param tmnlList
	  * @param taskSecond
	  * @return
	  * @throws DBAccessException
	  */
	 public List<UseValueResult> fetchUseValue(List<UseValueResult> tmnlList,Integer taskSecond)throws Exception;
	 
	/* *//**
	  * 召测剩余值（全选）
	  * @param tmnlList
	  * @param taskSecond
	  * @return
	  * @throws DBAccessException
	  *//*
	 public void  fetchAllUseValue(String orgName,String consNo,String appNo,
			 String terminalAddr,String startDate,String endDate,Integer taskSecond)throws Exception;*/
}
