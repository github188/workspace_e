package com.nari.advapp.statanalyse.impl;

import java.util.Date;
import java.util.List;

import com.nari.advapp.statanalyse.EnergyStat;
import com.nari.advapp.statanalyse.LineStatAnalysisDao;
import com.nari.advapp.statanalyse.LineStatAnalysisManager;
import com.nari.advapp.statanalyse.LoadStat;
import com.nari.advapp.statanalyse.VoltDegree;
import com.nari.support.Page;

import common.Logger;

public class LineStatAnalysisManagerImpl implements LineStatAnalysisManager {
	private LineStatAnalysisDao lineStatAnalysisDao;
	
	public LineStatAnalysisDao getLineStatAnalysisDao() {
		return lineStatAnalysisDao;
	}
	public void setLineStatAnalysisDao(LineStatAnalysisDao lineStatAnalysisDao) {
		this.lineStatAnalysisDao = lineStatAnalysisDao;
	}
	private Logger log=Logger.getLogger(LineStatAnalysisManagerImpl.class);
	/**
	 * 根据供电单位查询
	 * @param orgNo
	 * @return voltList
	 */
	@Override
	public List<VoltDegree> queryVoltByNodeNo(String type, String nodeNo) {
		List<VoltDegree> voltList=lineStatAnalysisDao.queryVoltByNodeNo(type, nodeNo);
		// TODO Auto-generated method stub
		return voltList;
	}
	/**
	 * 查询电量统计列表
	 * @param type,nodeNo,volt,startDate,endDate
	 * @return esList
	 */
	public Page<EnergyStat> queryEnergyStat(String type, String nodeNo,
			String voltCode, Date startDate, Date endDate,int start,int limit){
		Page<EnergyStat> esList=lineStatAnalysisDao.queryEnergyStat(type,nodeNo,voltCode,startDate,endDate,start,limit);
		return esList;
	}
	/**
	 * 查询电量统计列表
	 * @param orgNo,lineId,statDate
	 * @return esList
	 */
	public List<EnergyStat> queryLineEnergy(String orgNo,String voltCode, String lineId,
			Date statDate)
	{
		List<EnergyStat> esList=lineStatAnalysisDao.queryLineEnergy(orgNo,voltCode,lineId,statDate);
		return esList;
	}
	/**
	 * 查询负荷统计
	 * @param type,nodeNo,volt,startDate,endDate
	 * @return esList
	 */
	public Page<LoadStat> queryLoadStat(String type, String nodeNo,
			String voltCode, Date startDate, Date endDate,int start,int limit)
	{
		Page<LoadStat> lsList=lineStatAnalysisDao.queryLoadStat(type,nodeNo,voltCode,startDate,endDate,start,limit);
	    return lsList;
	}

}
