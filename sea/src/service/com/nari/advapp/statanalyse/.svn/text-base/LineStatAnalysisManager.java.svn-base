package com.nari.advapp.statanalyse;

import java.util.Date;
import java.util.List;

import com.nari.support.Page;

public interface LineStatAnalysisManager {
	/**
	 * 根据供电单位查询
	 * @param orgNo
	 * @return voltList
	 */
	public List<VoltDegree> queryVoltByNodeNo(String type,String nodeNo);
	/**
	 * 查询电量统计列表
	 * @param limit 
	 * @param start 
	 * @param type,nodeNo,volt,startDate,endDate
	 * @return esList
	 */
	public Page<EnergyStat> queryEnergyStat(String type, String nodeNo,
			String voltCode, Date startDate, Date endDate, int start, int limit);
	/**
	 * 查询电量统计列表
	 * @param orgNo,lineId,statDate
	 * @return esList
	 */
	public List<EnergyStat> queryLineEnergy(String orgNo, String voltCode,String lineId,
			Date statDate);
	/**
	 * 查询负荷统计
	 * @param limit 
	 * @param start 
	 * @param type,nodeNo,volt,startDate,endDate
	 * @return esList
	 */
	public Page<LoadStat> queryLoadStat(String type, String nodeNo,
			String voltCode, Date startDate, Date endDate, int start, int limit);

}
