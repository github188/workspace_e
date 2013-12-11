package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.SuspensionControlDto;
import com.nari.orderlypower.WTmnlBusiness;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

/**
 * 营业报停控Jdbc之Dao接口
 * @author 姜炜超
 */
public interface ISuspensionControlJdbcDao {
	
	/**
	 * 保存或修改营业报停控明细表，主要是添加方案适用
	 * @param busi
	 * @return 
	 */
	public void saveOrUpdateByScheme(WTmnlBusiness busi) ;	
	
	/**
	 * 保存或修改营业报停控明细表，主要是针对参数下发
	 * @param busi
	 * @return 
	 */
	public void saveOrUpdateByParam(WTmnlBusiness busi) ;	
	
	/**
	 * 根据资产编号查询用户信息
	 * @param tmnlAssetNo
	 * @return 
	 */
	public List<SuspensionControlDto> findUserInfoByTmnlAssetNo(String tmnlAssetNo)throws DBAccessException;
	
//	/**
//	 * 根据用户号查询用户信息
//	 * @param consNo
//	 * @return 
//	 */
//	public List<SuspensionControlDto> findUserInfoByConsNo(String consNo);
	
	/**
	 * 根据供电单位号查询用户信息
	 * @param orgNo
	 * @return 
	 */
	public List<SuspensionControlDto> findUserInfoByOrgNo(String orgNo, String orgType,PSysUser pSysUser);
	
	/**
	 * 根据方案id查询用户信息
	 * @param schemeNo
	 * @return Page<SuspensionControlDto>
	 */
	public List<SuspensionControlDto> findUserInfoBySchemeId(Long schemeId);
	
	/**
	 * 根据线路Id查询用户信息
	 * @param lineId
	 * @return Page<SuspensionControlDto>
	 */
	public List<SuspensionControlDto> findUserInfoByLineId(String lineId,PSysUser pSysUser);
	
	/**
	 * 根据组号查询用户信息(普通群组）
	 * @param groupNo
	 * @return 
	 */
	public List<SuspensionControlDto> findUserInfoByCommonGroupNo(String groupNo);
	
	/**
	 * 根据组号查询用户信息（控制群组）
	 * @param groupNo
	 * @return Page<SuspensionControlDto>
	 */
	public List<SuspensionControlDto> findUserInfoByContrlGroupNo(String groupNo);
	
	/**
	 * 根据变电站标识查询用户信息
	 * @param subsId
	 * @return 
	 */
	public List<SuspensionControlDto> findUserInfoBySubsId(String subsId,PSysUser pSysUser);
	
	/**
	 * 更新营业报停控方案，用于操作后对表的某些字段ctrlFlag,sendTime的修改（投入、解除）
	 * @param busi
	 * @return 
	 * @throws DBAccessException 数据库异常
	 *//*
	public void updateBusiness(WTmnlBusiness busi);*/
	
	/**
	 * 更新营业报停控方案，用于操作后对表的状态字段statusCode的修改（下发、投入、解除）
	 * @param busiList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 *//*
	public void updateBusiStatus(WTmnlBusiness busi);*/
	
	 /**
     * 根据方案Id删除剔除信息
     * @param schemeId
     * @throws DBAccessException
     */
    public void deleteBySchemeId(Long schemeId) throws DBAccessException; 
}
