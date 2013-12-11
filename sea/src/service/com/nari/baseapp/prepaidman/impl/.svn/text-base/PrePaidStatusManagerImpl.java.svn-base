package com.nari.baseapp.prepaidman.impl;

import java.util.ArrayList;
import java.util.List;

import com.nari.baseapp.prepaidman.BuyExecStatusStatBean;
import com.nari.baseapp.prepaidman.IPrePaidStatusDao;
import com.nari.baseapp.prepaidman.IPrePaidStatusManager;
import com.nari.baseapp.prepaidman.PrePaidCtrlExecStatBean;
import com.nari.baseapp.prepaidman.TmnlPrePaidQueryBean;
import com.nari.baseapp.prepaidman.UrgeFeeBean;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class PrePaidStatusManagerImpl implements IPrePaidStatusManager{
	
	private IPrePaidStatusDao iPrePaidStatusDao;

	public void setiPrePaidStatusDao(IPrePaidStatusDao iPrePaidStatusDao) {
		this.iPrePaidStatusDao = iPrePaidStatusDao;
	}

	/**
	 * 当日预付费控制执行统计
	 * @param orgNo
	 * @return
	 * @throws DBAccessException
	 */
	public List<PrePaidCtrlExecStatBean> todayPerPaidCtrlExecStat(String orgNo,String ctrlDate,String orgType)
			throws DBAccessException {
		try{
			List<PrePaidCtrlExecStatBean> prePaidCtrlExecStatBeanList=new ArrayList<PrePaidCtrlExecStatBean>();
			if("03".equals(orgType))
				prePaidCtrlExecStatBeanList=this.iPrePaidStatusDao.todayCtrlExecStatByCity(orgNo,ctrlDate);
			else if("04".equals(orgType))
				prePaidCtrlExecStatBeanList=this.iPrePaidStatusDao.todayCtrlExecStatByDistrict(orgNo, ctrlDate);
			return prePaidCtrlExecStatBeanList;
		}catch(Exception e) {
			throw new DBAccessException("统计当日预付费控制执行出错！");
		} 
	}

	/**
	 * 当日终端预购电执行情况统计
	 * @param orgNo
	 * @param busiType
	 * @return
	 * @throws DBAccessException
	 */
	public List<BuyExecStatusStatBean> todaybuyExecStatusStat(String orgNo, String orgType,String execDate) throws DBAccessException {
		try{
			List<BuyExecStatusStatBean> buyExecStatusStatBeanList=new ArrayList<BuyExecStatusStatBean>();
			if("03".equals(orgType))
				buyExecStatusStatBeanList =  this.iPrePaidStatusDao.todayTmnlPrePaidExecStatusStatByCity(orgNo, execDate);
			else if("04".equals(orgType))
				buyExecStatusStatBeanList =this.iPrePaidStatusDao.todayTmnlPrePaidExecStatusStatByDistrict(orgNo, execDate);
			return buyExecStatusStatBeanList;
		}catch(Exception e) {
			throw new DBAccessException("统计当日终端预购电执行情况出错！");
		} 
	}

	
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
			throws DBAccessException {
		try{
			Page<TmnlPrePaidQueryBean> tmnlPrePaidQueryBeanPage= new Page<TmnlPrePaidQueryBean>();
			if(statType>=1 && statType<=6 )
				tmnlPrePaidQueryBeanPage=this.iPrePaidStatusDao.terminalPrePaidQueryBy(orgNo, orgType, execDate, statType, start, limit);
			else if(statType==7)
				tmnlPrePaidQueryBeanPage=this.iPrePaidStatusDao.waitAccQueryBy(orgNo, orgType, execDate, start, limit);
			return tmnlPrePaidQueryBeanPage;
		}catch(Exception e) {
			throw new DBAccessException("查询终端预付费出错！");
		} 
	}
	
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
			throws DBAccessException{
		try{
			return  this.iPrePaidStatusDao.prePaidCtrlExecQueryBy(orgNo, orgType, execDate, ctrlType, ctrlStatus, start, limit);
		}catch(Exception e) {
			throw new DBAccessException("查询 预付费控制执行出错！");
		} 
	}

}
