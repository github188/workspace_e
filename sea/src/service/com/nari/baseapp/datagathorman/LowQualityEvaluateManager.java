package com.nari.baseapp.datagathorman;

import java.util.List;

import com.nari.baseapp.datagatherman.LowQualityEvaluate;
import com.nari.privilige.PSysUser;

public interface LowQualityEvaluateManager {

	/**
	 * 根据左边树节点查询集抄采集成功率
	 * @param pSysUser
	 * @param startDate
	 * @param endDate
	 * @param qualityValue 供电所orgNo或线路id
	 * @param type org--供电所   line--线路
	 * @return
	 * @throws Exception
	 */
	public List<LowQualityEvaluate> getRateByNode(PSysUser pSysUser,String startDate,String endDate,
						String qualityValue,String type ,int myDays) throws Exception;
	
	/**
	 * 根据终端资产号查询一段时期内每天的采集成功率
	 * @param pSysUser
	 * @param startDate
	 * @param endDate
	 * @param tmnlAssetNo 终端资产号
	 * @param consNo 用户号
	 * @return
	 * @throws Exception
	 */
	public List<LowQualityEvaluate> getRateByTmnl(PSysUser pSysUser,String startDate,String endDate,
						String tmnlAssetNo,String consNo,String consName,Long totalNum) throws Exception;
	
	/**
	 * 查询低压抄表失败的数据
	 * @param data 查询日期
	 * @param tmnlNo 终端资产号
	 */
	public List<LowQualityEvaluate> findFail(String data,String tmnlNo) throws Exception;
}
