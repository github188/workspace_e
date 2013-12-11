package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.RemoteControlDto;
import com.nari.orderlypower.WTmnlTurn;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

/**
 * 遥控Jdbc之Dao接口
 * @author 姜炜超
 */
public interface IRemoteControlJdbcDao {
	/**
	 * 保存或修改遥控明细表，主要是添加方案适用
	 * @param turn
	 * @return 
	 */
	public void saveOrUpdateByScheme(WTmnlTurn turn) ;	
	
	/**
	 * 根据终端资产编号查询用户信息
	 * @param consNo
	 * @return List<RemoteControlDto>
	 */
	public List<RemoteControlDto> findUserInfoByTmnlAssetNo(String tmnlAssetNo, String flag);
	
	/**
	 * 根据供电单位号查询用户信息
	 * @param orgNo
	 * @return List<RemoteControlDto>
	 */
	public List<RemoteControlDto> findUserInfoByOrgNo(String orgNo, String orgType, PSysUser user, String flag);
	
	/**
	 * 根据方案id查询用户信息
	 * @param schemeNo
	 * @return List<RemoteControlDto>
	 */
	public List<RemoteControlDto> findUserInfoBySchemeNo(Long schemeNo, String flag);
	
	/**
	 * 根据线路Id查询用户信息
	 * @param lineId
	 * @return List<EnergyControlDto>
	 */
	public List<RemoteControlDto> findUserInfoByLineId(String lineId, PSysUser user, String flag);
	
	/**
	 * 根据组号查询用户信息
	 * @param groupNo
	 * @return List<EnergyControlDto>
	 */
	public List<RemoteControlDto> findUserInfoByGroupNo(String groupNo, String flag);
	
	/**
	 * 根据普通群组号查询用户信息
	 * @param groupNo
	 * @return List<EnergyControlDto>
	 */
	public List<RemoteControlDto> findUserInfoByUgpGroupNo(String groupNo, String flag);
	
	/**
	 * 根据变电站标识查询用户信息
	 * @param subsId
	 * @return List<EnergyControlDto>
	 */
	public List<RemoteControlDto> findUserInfoBySubsId(String subsId, PSysUser user, String flag);
	
	/**
	 * 更新遥控方案，用于操作后对表的某些字段ctrlFlag,sendTime的修改（投入、解除）
	 * @param turn
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public void updateTurn(WTmnlTurn turn);

	/**
	 * 按终端规约和FN号查询规约项数据编码列表
	 * @param turnType 遥测类型
	 * @return
	 */
	public List<String> findProItemNo(String turnType);

	/**
	 * 按方案ID删除方案明细
	 * @param ctrlSchemeId
	 */
	public void deleteBySchemeId(Long ctrlSchemeId);
}
