package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlPaulPower;
import com.nari.privilige.PSysUser;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2009-12-15 上午09:47:09 
 * 描述：
 */

public interface IWTmnlPaulPowerManager {
	/**
	 * 新增终端保电信息
	 * @param wTmnlPaulPower
	 * @throws DBAccessException
	 */
	public void savePaulPower(WTmnlPaulPower wTmnlPaulPower)  throws DBAccessException;
	
	/**
	 * 修改终端保电信息
	 * @param wTmnlPaulPower
	 * @throws DBAccessException
	 */
	public void updatePaulPower(WTmnlPaulPower wTmnlPaulPower)  throws DBAccessException;
	
	/**
	 * 新增或修改终端保电信息
	 * @param WTmnlPaulPower 终端保电信息
	 * @throws DBAccessException 数据库异常
	 */
	public void saveOrUpdatePaulPower(WTmnlPaulPower wTmnlPaulPower)  throws DBAccessException;
	

	/**
	 * 新增方案，针对新增加方案的业务处理，写入方案主表和终端保电表
	 * @param wCtrlScheme
	 * @param PaulPowerList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	
	public Boolean saveScheme(WCtrlScheme wCtrlScheme,List<WTmnlPaulPower> PaulPowerList)throws DBAccessException;
	
	/**
	 * 修改方案，针对保存方案的业务处理，写入方案主表和终端保电表
	 * @param wCtrlScheme
	 * @param PaulPowerList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public Boolean updateScheme(WCtrlScheme wCtrlScheme,List<WTmnlPaulPower> PaulPowerList)throws DBAccessException;
	
	/**
	 * 新增或修改方案，针对新增加方案的业务处理，写入方案主表和终端保电表
	 * @param wCtrlScheme
	 * @param PaulPowerList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	
	public void saveOrUpdateScheme(WCtrlScheme wCtrlScheme,List<WTmnlPaulPower> PaulPowerList)throws DBAccessException;
	
	/**
	 * 按属性过滤条件列表查找终端保电信息
	 * @param filters 过滤条件列表
	 * @return 终端保电信息列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<WTmnlPaulPower> findPaulPowerBy(List<PropertyFilter> filters) throws DBAccessException;
	
	/**
	 * 更新保电标识
	 * @throws DBAccessException
	 */
	public void  updateProtectFlag(String tmnlAssetNo,Integer flag)throws DBAccessException;
	
	/**
	 * 根据终端资产号号查询终端信息
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws DBAccessException
	 */
	public List<WTmnlPaulPower> queryTmnlByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException;
	
	
	/**
	 * 根据供电单位编号查询终端信息
	 * @param orgNo   供电单位编号
	 * @param orgType 供电单位类型
	 * @param start
	 * @param limit
	 * @return 
	 * @throws DBAccessException
	 */
     public List<WTmnlPaulPower> queryTmnlByOrgNo(String orgNo,String orgType,PSysUser pSysUser) throws DBAccessException;
	
     /**
      * 根据线路ID查询终端信息
      * @param lineId 线路ID
      * @return 
      * @throws DBAccessException
      */
     public List<WTmnlPaulPower> queryTmnlByLineId(String lineId,PSysUser pSysUser) throws DBAccessException;
     
     
     /**
      * 根据群组编号查询终端信息
      * @param nodeType 左边节点类型
      * @param groupNo  群组编号
      * @return 
      * @throws DBAccessException
      */
     public List<WTmnlPaulPower> queryTmnlByGroupNo(String nodeType,String groupNo) throws DBAccessException;
     
     /**
      * 根据变电站标识查询终端信息
      * @param subsId 变电站标识
      * @return 
      * @throws DBAccessException
      */
     public List<WTmnlPaulPower> queryTmnlBySubsId(String subsId,PSysUser pSysUser) throws DBAccessException;
     
     /**
      * 根据方案ID查询终端信息
      * @param schemeId  方案ID
      * @return
      * @throws DBAccessException
      */
   public List<WTmnlPaulPower> queryTmnlBySchemeId(Long schemeId) throws DBAccessException;
     

}
