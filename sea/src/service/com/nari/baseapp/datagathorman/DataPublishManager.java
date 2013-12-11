package com.nari.baseapp.datagathorman;

import java.util.List;

import com.nari.baseapp.datagatherman.DataPublishManDto;
import com.nari.baseapp.datagatherman.TblDataDto;

public interface DataPublishManager {

	public String test(String s) throws Exception;
	
	/**
	 * 查询单位的突变数据和零数据
	 * @param orgNo 单位编码
	 * @param queryType 数据类型
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 */
	public List<DataPublishManDto> queryDataPublishInfo(String orgNo,String queryType,String startDate,String endDate) throws Exception;
	
	/**
	 * 查询突变数据、零数据
	 * @param orgNo 单位编码
	 * @param queryType 数据类型
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 * @throws Exception
	 */
	public List<TblDataDto> queryTblDataInfo(String orgNo,String queryType, String startDate,String endDate) throws Exception;
}
