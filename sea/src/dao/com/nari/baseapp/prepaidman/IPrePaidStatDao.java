package com.nari.baseapp.prepaidman;

import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface IPrePaidStatDao {

	/**
	 * 统计预付费控制类别
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws DBAccessException
	 */
	public List<PrePaidCtrlStatBean> contrlTypeStat(String orgNo,String orgType,String startDate,String endDate)throws DBAccessException;
	
	/**
	 * 统计预付费执行情况
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws DBAccessException
	 */
	public List<PrePaidExecStatBean> executeStat(String orgNo,String orgType,String startDate,String endDate)throws DBAccessException;
	
	/**
	 * 查询预付费执行情况明细
	 * @param orgNo
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @param execStatus
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	public Page<TmnlPrePaidQueryBean> executeStatQuery(String orgNo,
			String orgType, String startDate, String endDate,
			Integer execStatus, long start, long limit)
			throws DBAccessException; 
}
