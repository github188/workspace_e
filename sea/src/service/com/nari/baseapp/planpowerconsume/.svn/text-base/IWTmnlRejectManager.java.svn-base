package com.nari.baseapp.planpowerconsume;

import java.util.List;


import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlReject;
import com.nari.privilige.PSysUser;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者:姜海辉
 * 创建时间：2009-12-23 下午05:37:27 
 * 描述：
 */

public interface IWTmnlRejectManager {
     /**
      * 添加终端剔除信息
      * @param wTmnlReject
      * @throws DBAccessException
      */
	 public void saveTmnlRej(WTmnlReject wTmnlReject)throws DBAccessException;
	 /**
	  * 修改终端剔除信息
	  * @param wTmnlReject
	  * @throws DBAccessException
	  */
	 public void updateTmnlRej(WTmnlReject wTmnlReject)throws DBAccessException;
	 
	 /**
      * 添加修改终端剔除信息
      * @param wTmnlReject
      * @throws DBAccessException
      */
	 public void saveOrUpdateTmnlRej(WTmnlReject wTmnlReject)throws DBAccessException;
	 
	 /**
	  * 根据条件查询终端剔除信息
	  * @param filters
	  * @return
	  * @throws DBAccessException
	  */
	 public List<WTmnlReject>findTmnlRejBy(List<PropertyFilter> filters)throws DBAccessException; 
	 
	/**
	 * 另存为方案
	 * @param wCtrlScheme 方案
	 * @param rejectList  方案明细
	 * @throws DBAccessException
	 */
	 public Boolean saveScheme(WCtrlScheme wCtrlScheme,List<WTmnlReject> rejectList)throws DBAccessException;
	 
	 /**
	 * 修改方案，针对保存方案的业务处理，写入方案主表和终端剔除表
	 * @param wCtrlScheme
	 * @param rejectList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public Boolean updateScheme(WCtrlScheme wCtrlScheme,List<WTmnlReject> rejectList)throws DBAccessException;
	
	/**
	 * 根据终端资产号号查询终端信息
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws DBAccessException
	 */
	public List<WTmnlReject> queryTmnlByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException;
	
	/**
	 * 根据供电单位编号查询终端信息
	 * @param orgNo   供电单位编号
	 * @param orgType 供电单位类型
	 * @param start
	 * @param limit
	 * @return 
	 * @throws DBAccessException
	 */
     public List<WTmnlReject> queryTmnlByOrgNo(String orgNo,String orgType,PSysUser pSysUser) throws DBAccessException;
	
     /**
      * 根据线路ID查询终端信息
      * @param lineId 线路ID
      * @return 
      * @throws DBAccessException
      */
     public List<WTmnlReject> queryTmnlByLineId(String lineId,PSysUser pSysUser) throws DBAccessException;
     
     
     /**
      * 根据群组编号查询终端信息
      * @param nodeType 左边节点类型
      * @param groupNo  群组编号
      * @return 
      * @throws DBAccessException
      */
     public List<WTmnlReject> queryTmnlByGroupNo(String nodeType,String groupNo) throws DBAccessException;
     
     /**
      * 根据变电站标识查询终端信息
      * @param subsId 变电站标识
      * @return 
      * @throws DBAccessException
      */
     public List<WTmnlReject> queryTmnlBySubsId(String subsId,PSysUser pSysUser) throws DBAccessException;
     
     /**
      * 根据方案ID查询终端信息
      * @param schemeId  方案ID
      * @return
      * @throws DBAccessException
      */
     public List<WTmnlReject> queryTmnlBySchemeId(Long schemeId) throws DBAccessException;
     
     /**
 	 * 更新剔除标识
 	 * @throws DBAccessException
 	 */
 	 public void  updateEliminateFlag(String tmnlAssetNo,Integer flag)throws DBAccessException;
 	
 	
}
