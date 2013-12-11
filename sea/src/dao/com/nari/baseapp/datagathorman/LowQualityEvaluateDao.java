package com.nari.baseapp.datagathorman;
import java.util.List;

import com.nari.baseapp.datagatherman.LowQualityEvaluate;
import com.nari.privilige.PSysUser;

public interface LowQualityEvaluateDao {

	/**
	 * 查询线路或供电所 下属台区的抄表情况
	 * @param assetNo 终端资产号
	 * @param startDate 查询起始日期
	 * @param endDate 查询截止日期
	 * @param start 
	 * @param limit
	 * @return
	 */
	public List<LowQualityEvaluate> findLowQE(PSysUser pSysUser,String qulityValue,String type,String startDate,String endDate); 

	/**
	 * 查询线路或供电所 下属台区的总表数量
	 * @param qulityValue 线路Id或供电站orgNo
	 * @param type line/org
	 */
	public List<LowQualityEvaluate> findTotalLowQE(String qualityValue,String type);
	
	/**
	 * 查询低压抄表成功与否的字符串
	 * @param data 查询日期
	 * @param tmnlNo 终端资产号
	 */
	public List<LowQualityEvaluate> findFailA(String data,String tmnlNo);

	/**
	 * 查询低压抄表所有应有数据
	 * @param data 查询日期
	 * @param tmnlNo 终端资产号
	 */
	public List<LowQualityEvaluate> findFailB(String data,String tmnlNo);
	
	/**
	 * 根据终端查询一段时间内每天的采集成功个数
	 * @param pSysUser
	 * @param tmnlAssetNo
	 * @param consNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<LowQualityEvaluate> findRateGbyDate(PSysUser pSysUser, String tmnlAssetNo,String consNo,String startDate,String endDate);
}
