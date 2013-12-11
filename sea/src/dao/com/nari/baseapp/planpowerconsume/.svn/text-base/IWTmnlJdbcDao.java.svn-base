package com.nari.baseapp.planpowerconsume;


import java.util.List;

import com.nari.orderlypower.WTmnlDto;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2009-12-28 下午02:05:12 
 * 描述：
 */

public interface IWTmnlJdbcDao {
	
	/**
	 * 根据用户编号查询终端相关信息
	 * @param consNo 用户编号
	 * @param start
	 * @param limit 
	 * @return Page<WTmnlDto>
	 * @throws DBAccessException
	 */
	public Page<WTmnlDto> findTmnlByConsNo(String consNo, long start, int limit) throws DBAccessException;
	
	
	/**
	 * 根据终端资产号查询终端相关信息
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws DBAccessException
	 */
	public List<WTmnlDto> findTmnlByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException;
	
	/**
	 * 根据供电单位编号查询终端相关信息
	 * @param orgNo   供电单位编号
	 * @param orgType 供电单位类型
	 * @return 
	 * @throws DBAccessException
	 */
	public List<WTmnlDto> findTmnlByOrgNo(String orgNo, String orgType,PSysUser pSysUser) throws DBAccessException;
	
	/**
	 * 根据线路Id查询终端相关信息
	 * @param lineId 线路Id
	 * @return 
	 * @throws DBAccessException
	 */
	public List<WTmnlDto> findTmnlByLineId(String lineId,PSysUser pSysUser) throws DBAccessException;
	
	/**
	 * 根据普通群组编号查询终端相关信息
	 * @param groupNo  群组编号
	 * @return 
	 * @throws DBAccessException
	 */
	public List<WTmnlDto> findTmnlByCommonGroupNo(String groupNo) throws DBAccessException;
	

	/**
	 * 根据控制群组编号查询终端相关信息
	 * @param groupNo  群组编号
	 * @return 
	 * @throws DBAccessException
	 */
	public List<WTmnlDto> findTmnlByCtrlGroupNo(String groupNo) throws DBAccessException;
	
	/**
	 * 根据变电站查询终端相关信息
	 * @param subsId 变电站标识
	 * @return  
	 * @throws DBAccessException
	 */
	public List<WTmnlDto> findTmnlBySubsId(String subsId,PSysUser pSysUser) throws DBAccessException;
	
	
//	/**
//	 * 根据方案ID查询相关信息放入备选用户Grid中（终端保电）
//	 * @param schemeId 方案ID
//	 * @return 
//	 * @throws DBAccessException
//	 */
//	public List<WTmnlDto> findProtectTmnlBySchemeId(Long schemeId) throws DBAccessException;
//	
//	/**
//	 * 根据方案ID查询相关信息放入备选用户Grid中（终端剔除）
//	 * @param schemeId 方案ID
//	 * @return 
//	 * @throws DBAccessException
//	 */
//	public List<WTmnlDto> findEliminateTmnlBySchemeId(Long schemeId) throws DBAccessException;
	
//	/**
//	 * 根据用户查询相关信息放入备选用户Grid中（包括总加组）
//	 * @return
//	 * @throws DBAccessException
//	 */
//	public Page<WTmnlDto> findWTmnlByConsWithT(String consNo, long start, int limit) throws DBAccessException;
	
//	/**
//	 * 根据供电所查询相关信息放入备选用户Grid中（包括总加组）
//	 * @return
//	 * @throws DBAccessException
//	 */
//	public Page<WTmnlDto> findWTmnlByOrgWithT(String orgNo, long start, int limit) throws DBAccessException;
}
