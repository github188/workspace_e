package com.nari.baseapp.prepaidman;

import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface IPrePaidStatManager {

	/**
	 * 预购电控制类别统计
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws DBAccessException
	 */
	public List<PrePaidCtrlStatBean> ctrlStat(String orgNo,String orgType,String startDate,String endDate)throws DBAccessException;
	
	/**
	 * 预购电执行统计
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws DBAccessException
	 */
	public List<PrePaidExecStatBean> execStat(String orgNo,String orgType,String startDate,String endDate)throws DBAccessException;
	
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
	public Page<TmnlPrePaidQueryBean> execStatQuery(String orgNo,
			String orgType, String startDate, String endDate,
			Integer execStatus, long start, long limit)
			throws DBAccessException;
}
