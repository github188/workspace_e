package com.nari.baseapp.prepaidman.impl;

import java.util.List;

import com.nari.baseapp.prepaidman.IPrePaidStatDao;
import com.nari.baseapp.prepaidman.IPrePaidStatManager;
import com.nari.baseapp.prepaidman.PrePaidCtrlStatBean;
import com.nari.baseapp.prepaidman.PrePaidExecStatBean;
import com.nari.baseapp.prepaidman.TmnlPrePaidQueryBean;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class PrePaidStatManagerImpl implements IPrePaidStatManager {
    
	private IPrePaidStatDao iPrePaidStatDao;
	
	public void setiPrePaidStatDao(IPrePaidStatDao iPrePaidStatDao) {
		this.iPrePaidStatDao = iPrePaidStatDao;
	}
	/**
	 * 预购电控制类别统计
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws DBAccessException
	 */
	public List<PrePaidCtrlStatBean> ctrlStat(String orgNo,String orgType,String startDate,
			String endDate) throws DBAccessException {
		try{
			return this.iPrePaidStatDao.contrlTypeStat(orgNo, orgType, startDate, endDate);
		}catch(Exception e) {
			throw new DBAccessException("统计预付费控制类别出错！");
		} 
	}
	
	/**
	 * 预购电执行统计
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws DBAccessException
	 */
	public List<PrePaidExecStatBean> execStat(String orgNo, String orgType, String startDate,
			String endDate) throws DBAccessException {
		try{
			return this.iPrePaidStatDao.executeStat(orgNo, orgType, startDate, endDate);
		}catch(Exception e) {
			throw new DBAccessException("统计预付费执行出错！");
		} 
	}

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
			throws DBAccessException{
		try{
			return this.iPrePaidStatDao.executeStatQuery(orgNo, orgType, startDate, endDate, execStatus, start, limit);
		}catch(Exception e) {
			throw new DBAccessException("查询预付费执行情况明细出错！");
		} 
	}

}
