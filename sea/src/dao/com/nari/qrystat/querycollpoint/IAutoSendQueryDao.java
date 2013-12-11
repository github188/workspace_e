package com.nari.qrystat.querycollpoint;

import java.util.List;

import com.nari.support.Page;

public interface IAutoSendQueryDao {
	/**
	 * 查找相关的居名集抄户信息
	 * @author zhaoliang
	 * @param asqid传入的查询id
	 * @param start
	 * @param limit
	 * @return 公变信息集合
	 */
		public Page<AutoSendQuery> findASQuery(String tgId ,String orgType,String assetNo,String nodeType,String nodeValue,String consType, long start, int limit); 
		

		public List<AutoSendQuery> findtmnlAssetNo(String tgId);
}
