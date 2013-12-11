package com.nari.baseapp.planpowerconsume;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.nari.orderlypower.WTmnlReject;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

/**
 * 终端剔除JdbcDao
 * @author 姜海辉
 *
 */
public interface IWTmnlRejectJdbcDao {
	
	/**
	 * 保存或修改终端剔除表，主要是保存参数适用
	 * @param busi
	 * @return 
	 */
	public void saveOrUpdateByParam(WTmnlReject wt) throws DataAccessException;

	/**
	 * 保存或修改终端剔除表，主要是添加方案适用
	 * @param busi
	 * @return 
	 */
	public void saveOrUpdateByScheme(WTmnlReject wt) throws DataAccessException;
	

	/**
	 * 根据终端资产号查询终端相关信息
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws DBAccessException
	 */
	public List<WTmnlReject> findTmnlByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException;
	
	/**
	 * 根据供电单位编号查询终端相关信息
	 * @param orgNo   供电单位编号
	 * @param orgType 供电单位类型
	 * @return 
	 * @throws DBAccessException
	 */
	public List<WTmnlReject> findTmnlByOrgNo(String orgNo, String orgType,PSysUser pSysUser) throws DBAccessException;
	
	/**
	 * 根据线路Id查询终端相关信息
	 * @param lineId 线路Id
	 * @return 
	 * @throws DBAccessException
	 */
	public List<WTmnlReject> findTmnlByLineId(String lineId,PSysUser pSysUser) throws DBAccessException;
	
	/**
	 * 根据普通群组编号查询终端相关信息
	 * @param groupNo  群组编号
	 * @return 
	 * @throws DBAccessException
	 */
	public List<WTmnlReject> findTmnlByCommonGroupNo(String groupNo) throws DBAccessException;
	

	/**
	 * 根据控制群组编号查询终端相关信息
	 * @param groupNo  群组编号
	 * @return 
	 * @throws DBAccessException
	 */
	public List<WTmnlReject> findTmnlByCtrlGroupNo(String groupNo) throws DBAccessException;
	
	/**
	 * 根据变电站查询终端相关信息
	 * @param subsId 变电站标识
	 * @return  
	 * @throws DBAccessException
	 */
	public List<WTmnlReject> findTmnlBySubsId(String subsId,PSysUser pSysUser) throws DBAccessException;
	
	
	/**
	 * 更新终端剔除状态
	 * @param tTmnlAssetNo
	 * @param type
	 * @throws DBAccessException
	 */
	public void updateEliminate(String tmnlAssetNo,Integer type)throws DBAccessException;
	

	/**
	 * 根据方案ID查询终端剔除信息
	 * @param schemeId 方案ID
	 * @return
	 * @throws DBAccessException
	 */
	public List<WTmnlReject> findTmnlBySchemeId(Long schemeId) throws DBAccessException;
	
	 /**
     * 根据方案Id删除剔除信息
     * @param schemeId
     * @throws DBAccessException
     */
    public void deleteBySchemeId(Long schemeId) throws DBAccessException; 

}
