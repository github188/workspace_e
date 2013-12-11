package com.nari.baseapp.planpowerconsume;


import java.util.List;

import com.nari.orderlypower.WTmnlDto;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2009-12-28 下午03:37:08 
 * 描述：从左边树获取终端信息接口
 */

public interface IWTmnlManager {

	/**
	 * 根据用户编号查询终端信息
	 * @param consNo 用户编号
	 * @param start
	 * @param limit
	 * @return Page<WTmnlDto>
	 */
	public Page<WTmnlDto> queryTmnlByConsNo(String consNo,int  start, int limit) throws DBAccessException;
	
	
	
	/**
	 * 根据终端资产号号查询终端信息
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws DBAccessException
	 */
	public List<WTmnlDto> queryTmnlByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException;
	
	
	/**
	 * 根据供电单位编号查询终端信息
	 * @param orgNo   供电单位编号
	 * @param orgType 供电单位类型
	 * @param start
	 * @param limit
	 * @return 
	 * @throws DBAccessException
	 */
     public List<WTmnlDto> queryTmnlByOrgNo(String orgNo,String orgType,PSysUser pSysUser) throws DBAccessException;
	
     /**
      * 根据线路ID查询终端信息
      * @param lineId 线路ID
      * @return 
      * @throws DBAccessException
      */
     public List<WTmnlDto> queryTmnlByLineId(String lineId,PSysUser pSysUser) throws DBAccessException;
     
     
     /**
      * 根据群组编号查询终端信息
      * @param nodeType 左边节点类型
      * @param groupNo  群组编号
      * @return 
      * @throws DBAccessException
      */
     public List<WTmnlDto> queryTmnlByGroupNo(String nodeType,String groupNo) throws DBAccessException;
     
     /**
      * 根据变电站标识查询终端信息
      * @param subsId 变电站标识
      * @return 
      * @throws DBAccessException
      */
     public List<WTmnlDto> queryTmnlBySubsId(String subsId,PSysUser pSysUser) throws DBAccessException;
     
//     /**
//      * 根据方案ID查询终端信息
//      * @param schemeId  方案ID
//      * @return
//      * @throws DBAccessException
//      */
//     public List<WTmnlDto> queryTmnlBySchemeId(String schemeType,Long schemeId) throws DBAccessException;
     
//	/**
//	 * 根据用户查询相关信息放入备选用户Grid中（包括总加组）
//	 * @return
//	 * @throws DBAccessException
//	 */
//	public Page<WTmnlDto> queryWTmnlByWithT(String consNo,String orgNo,Long schemeId,String nodeType,long start, int limit) throws DBAccessException;
}
