package com.nari.baseapp.planpowerconsume;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.nari.orderlypower.WTmnlPaulPower;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;


/**
 * 终端保电JdbcDao接口
 * @author 姜海辉
 */
public interface IWTmnlPaulPowerJdbcDao {

	/**
	 * 保存或修改终端保电表,用于保存参数
	 * @param wTmnlPaulPower
	 * @throws DataAccessException
	 */
	public void saveOrUpdateByParam(WTmnlPaulPower wTmnlPaulPower) throws DataAccessException;
	
	/**
	 * 保存或修改终端保电表，主要是添加方案适用
	 * @param wTmnlPaulPower
	 * @return 
	 */
	public void saveOrUpdateByScheme(WTmnlPaulPower wTmnlPaulPower) throws DataAccessException;
	
	/**
	 * 根据终端资产号查询终端相关信息
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws DBAccessException
	 */
	public List<WTmnlPaulPower> findTmnlByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException;
	
	/**
	 * 根据供电单位编号查询终端相关信息
	 * @param orgNo   供电单位编号
	 * @param orgType 供电单位类型
	 * @return 
	 * @throws DBAccessException
	 */
	public List<WTmnlPaulPower> findTmnlByOrgNo(String orgNo, String orgType,PSysUser pSysUser) throws DBAccessException;
	
	/**
	 * 根据线路Id查询终端相关信息
	 * @param lineId 线路Id
	 * @return 
	 * @throws DBAccessException
	 */
	public List<WTmnlPaulPower> findTmnlByLineId(String lineId,PSysUser pSysUser) throws DBAccessException;
	
	/**
	 * 根据普通群组编号查询终端相关信息
	 * @param groupNo  群组编号
	 * @return 
	 * @throws DBAccessException
	 */
	public List<WTmnlPaulPower> findTmnlByCommonGroupNo(String groupNo) throws DBAccessException;
	

	/**
	 * 根据控制群组编号查询终端相关信息
	 * @param groupNo  群组编号
	 * @return 
	 * @throws DBAccessException
	 */
	public List<WTmnlPaulPower> findTmnlByCtrlGroupNo(String groupNo) throws DBAccessException;
	
	/**
	 * 根据变电站查询终端相关信息
	 * @param subsId 变电站标识
	 * @return  
	 * @throws DBAccessException
	 */
	public List<WTmnlPaulPower> findTmnlBySubsId(String subsId,PSysUser pSysUser) throws DBAccessException;
	
	
	/**
	 * 根据方案ID查询相关信息放入备选用户Grid中
	 * @param schemeId 方案ID
	 * @return 
	 * @throws DBAccessException
	 */
    public List<WTmnlPaulPower> findProtectTmnlBySchemeId(Long schemeId) throws DBAccessException;
	
    /**
     * 根据方案Id删除保电信息
     * @param schemeId
     * @throws DBAccessException
     */
    public void deleteBySchemeId(Long schemeId) throws DBAccessException; 
	
}
