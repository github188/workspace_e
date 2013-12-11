package com.nari.baseapp.prepaidman;

import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface IPrePaidStatusManager {
	
	/**
	 * 当日预付费控制执行统计
	 * @param orgNo
	 * @return
	 * @throws DBAccessException
	 */
    public List<PrePaidCtrlExecStatBean> todayPerPaidCtrlExecStat(String orgNo,String ctrlDate,String orgType)throws DBAccessException;
    

	/**
	 * 当日购电执行情况统计
	 * @param orgNo
	 * @param busiType
	 * @return
	 * @throws DBAccessException
	 */
    public List<BuyExecStatusStatBean> todaybuyExecStatusStat(String orgNo,String orgType,String execDate)throws DBAccessException;
    
    
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
	public Page<TmnlPrePaidQueryBean> terminalPrePaidQuery(String orgNo,String orgType,
			String execDate, Integer statType, long start, long limit)
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
	public Page<UrgeFeeBean> prePaidCtrlExecQuery(String orgNo,String orgType,
			String execDate,Integer ctrlType,Integer ctrlStatus, long start, long limit)
			throws DBAccessException;
	
}
