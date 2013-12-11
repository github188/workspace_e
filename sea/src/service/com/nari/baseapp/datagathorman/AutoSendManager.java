package com.nari.baseapp.datagathorman;

import java.util.List;

import com.nari.baseapp.datagatherman.AutoSendDto;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 低压远程抄表 业务层接口
 * @author 余涛
 *
 */
public interface AutoSendManager {

	/**
	 * 查询居民集抄用户信息
	 * @param consId 用户id
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	public Page<AutoSendDto> findAsQueryInfo(String consId,String queryTmnlNo, long start, int limit) throws DBAccessException;
	
	/**
	 * 实时抄表
	 * @param tmnlAssetNo 集中器终端资产号
	 * @param regSnArray 注册序号
	 * @return
	 * @throws Exception
	 */
	public List<AutoSendDto> queryMeterInfo(String tmnlAssetNo,String[] regSnArray) throws Exception;
}
