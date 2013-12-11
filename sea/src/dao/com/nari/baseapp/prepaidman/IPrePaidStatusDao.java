package com.nari.baseapp.prepaidman;

import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface IPrePaidStatusDao {

	/**
	 * 当日预付费控制执行统计（按地市）
	 * @param orgNo
	 * @return
	 * @throws DBAccessException
	 */
	public List<PrePaidCtrlExecStatBean> todayCtrlExecStatByCity(String orgNo,String ctrlDate)throws DBAccessException;
	
	/**
	 * 当日预付费控制执行统计（按区县）
	 * @param orgNo
	 * @param ctrlDate
	 * @return
	 * @throws DBAccessException
	 */
	public List<PrePaidCtrlExecStatBean> todayCtrlExecStatByDistrict(String orgNo,String ctrlDate)throws DBAccessException;
	
	/**
	 * 当日主站预付费执行情况统计
	 * @param orgNo
	 * @return
	 * @throws DBAccessException
	 */
	//public List<BuyExecStatusStatBean> todayStationPrePaidExecStatusStat(String orgNo,String execDate)throws DBAccessException;
	
	/**
	 * 当日终端预购电执行情况统计（按区县）
	 * @param orgNo
	 * @param execDate
	 * @return
	 * @throws DBAccessException
	 */
	public List<BuyExecStatusStatBean> todayTmnlPrePaidExecStatusStatByDistrict(String orgNo,String execDate) throws DBAccessException;
	
	/**
	 * 当日终端预购电执行情况统计（按地市）
	 * @param orgNo
	 * @param execDate
	 * @return
	 * @throws DBAccessException
	 */
	public List<BuyExecStatusStatBean> todayTmnlPrePaidExecStatusStatByCity(String orgNo,String execDate) throws DBAccessException;
	
	
	/**
	 * 终端预付费查询
	 * @param orgNo
	 * @param orgType
	 * @param execDate
	 * @param statType
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	public Page<TmnlPrePaidQueryBean> terminalPrePaidQueryBy(String orgNo,String orgType,
			String execDate, Integer statType, long start, long limit)
			throws DBAccessException; 

	/**
	 * 终端待执行预付费记录查询
	 * @param orgNo
	 * @param orgType
	 * @param execDate
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	public Page<TmnlPrePaidQueryBean> waitAccQueryBy(String orgNo,String orgType,
			String execDate, long start, long limit)
			throws DBAccessException;
	
	/**
	 * 预付费控制执行查询
	 * @param orgNo
	 * @param orgType
	 * @param execDate
	 * @param ctrlType
	 * @param ctrlStatus
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	public Page<UrgeFeeBean> prePaidCtrlExecQueryBy(String orgNo,String orgType,
			String execDate,Integer ctrlType,Integer ctrlStatus, long start, long limit)
			throws DBAccessException;
}
