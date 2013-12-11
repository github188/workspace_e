package com.nari.baseapp.planpowerconsume;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.orderlypower.EnergyControlDto;
import com.nari.orderlypower.WTmnlMonPctrl;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

/**
 * 月电量定值控Jdbc之Dao接口
 * @author 姜炜超/姜海辉
 */
public interface IEnergyControlJdbcDao {
	/**
	 * 保存或修改月电量定值控明细表，主要是添加方案适用
	 * @param monPctrl
	 * @return 
	 */
	public void saveOrUpdateByScheme(WTmnlMonPctrl monPctrl) ;	
	
	/**
	 * 保存或修改月电量定值控明细表，主要是针对参数下发
	 * @param monPctrl
	 * @return 
	 */
	public void saveOrUpdateByParam(WTmnlMonPctrl monPctrl) ;	
	
	
	/**
	 * 根据终端资产号查询用户信息
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 */
	public List<EnergyControlDto> findUserInfoByTmnlAssetNo(String tmnlAssetNo)throws DBAccessException;
	
	/**
	 * 根据用户号查询用户信息
	 * @param consNo
	 * @return Page<EnergyControlDto>
	 */
	public List<EnergyControlDto> findUserInfoByConsNo(String consNo) throws DBAccessException;
	
	/**
	 * 根据供电单位号查询用户信息
	 * @param orgNo
	 * @param orgType
	 * @return P
	 */
	public List<EnergyControlDto> findUserInfoByOrgNo(String orgNo, String orgType,PSysUser pSysUser);
	
	/**
	 * 根据方案id查询用户信息
	 * @param schemeNo
	 * @return 
	 */
	public List<EnergyControlDto> findUserInfoBySchemeId(Long schemeId);
	
	/**
	 * 根据线路Id查询用户信息
	 * @param lineId
	 * @return 
	 */
	public List<EnergyControlDto> findUserInfoByLineId(String lineId,PSysUser pSysUser);
	
	/**
	 * 根据组号查询用户信息（普通群组）
	 * @param groupNo
	 * @return 
	 */
	public List<EnergyControlDto> findUserInfoByCommonGroupNo(String groupNo);
	
	/**
	 * 根据组号查询用户信息（控制群组）
	 * @param groupNo
	 * @return 
	 */
	public List<EnergyControlDto> findUserInfoByContrlGroupNo(String groupNo);
	
	/**
	 * 根据变电站标识查询用户信息
	 * @param subsId
	 * @return 
	 */
	public List<EnergyControlDto> findUserInfoBySubsId(String subsId,PSysUser pSysUser);
	
	/**
	 * 更新月电量定值控方案，用于操作后对表的某些字段ctrlFlag,sendTime的修改（投入、解除）
	 * @param monPctrl
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public void updateMonPctrl(WTmnlMonPctrl monPctrl);
	
	/**
	 * 更新月电量定值控方案，用于操作后对表的状态字段statusCode的修改（下发、投入、解除）
	 * @param monPctrl
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public void updateMonPctrlStatus(WTmnlMonPctrl monPctrl);
	
	/**
     * 根据方案Id删除剔除信息
     * @param schemeId
     * @throws DBAccessException
     */
    public void deleteBySchemeId(Long schemeId) throws DBAccessException; 
    
    /**
	 * 修改月电量定值控方案明细表
	 */
	public void updateMonPowerContrl(WTmnlMonPctrl wTmnlMonPctrl,short status) throws DataAccessException;
}
