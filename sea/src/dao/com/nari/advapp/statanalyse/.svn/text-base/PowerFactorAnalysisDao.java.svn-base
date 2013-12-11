package com.nari.advapp.statanalyse;

import java.util.Date;
import java.util.List;

import com.nari.support.Page;

public interface PowerFactorAnalysisDao {
	/**
	 * 查詢電壓等級
	 * @parameters consNo
	 * @return volt
	 */
	public List<VoltDegree> queryVoltByConsNo(String consNo,String type);
	/**
	 * 查詢功率因素列表
	 * @param type 
	 * @param voltCode,consNo,startTime,endTime
	 * @return list
	 */
	public Page<PowerFactor> queryPowerFactorList(String consNo,
			String voltCode, Date pfaTime, String type,long start,long limit);

}
