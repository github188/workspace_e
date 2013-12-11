package com.nari.advapp.statanalyse.impl;

import java.util.Date;
import java.util.List;
import common.Logger;

import com.nari.advapp.statanalyse.PowerFactor;
import com.nari.advapp.statanalyse.PowerFactorAnalysisDao;
import com.nari.advapp.statanalyse.PowerFactorAnalysisManager;
import com.nari.advapp.statanalyse.VoltDegree;
import com.nari.support.Page;

public class PowerFactorAnalysisManagerImpl implements
		PowerFactorAnalysisManager {
	private PowerFactorAnalysisDao powerFactorAnalysisDao;
	private Logger log=Logger.getLogger(PowerFactorAnalysisManagerImpl.class);
	
//	public PowerFactorAnalysisDao getPowerFactorAnalysisDao() {
//		return powerFactorAnalysisDao;
//	}

	public void setPowerFactorAnalysisDao(
			PowerFactorAnalysisDao powerFactorAnalysisDao) {
		this.powerFactorAnalysisDao = powerFactorAnalysisDao;
	}

	/**
	 * 查詢電壓等級
	 * @parameters consNo
	 * @return volt
	 */
	@Override
	public List<VoltDegree> queryVoltByConsNo(String consNo,String type) {
		// TODO Auto-generated method stub
		return powerFactorAnalysisDao.queryVoltByConsNo(consNo,type);
	}
	/**
	 * 查詢功率因素列表
	 * @param voltCode,consNo,startTime,endTime
	 * @return list
	 */
	public Page<PowerFactor> queryPowerFactorList(String consNo,
			String voltCode, Date pfaTime,String type,long start,long limit){
		 Page<PowerFactor> pfList = null;
		try
		{
		  pfList=powerFactorAnalysisDao.queryPowerFactorList(consNo,voltCode,pfaTime,type,start,limit);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.debug("manager:queryPowerFactorList方法出错！");
		}
		return pfList;
	}

}
