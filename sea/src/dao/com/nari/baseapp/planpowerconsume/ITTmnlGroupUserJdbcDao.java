package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.TTmnlGroupUserDto;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2010-1-9 上午10:49:52 
 * 描述：群组用户JdbcDao接口
 */

public interface ITTmnlGroupUserJdbcDao {
    /**
     * 根据终端资产编号查询用户信息
     * @param tmnlAssetNo 终端资产编号
     * @return
     * @throws DBAccessException
     */
	public List<TTmnlGroupUserDto> findTTmnlGroupUserByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException;
	
	/**
	 * 根据用户编号查询用户信息
	 * @param consNo 用户编号
	 * @return Page<TTmnlGroupUserDto>
	 */
	public List<TTmnlGroupUserDto> findTTmnlGroupUserByConsNo(String consNo) throws DBAccessException;
	
	/**
	 * 根据供电单位编号查询用户信息
	 * @param orgNo 单位编号
	 * @return 
	 */
	public List<TTmnlGroupUserDto> findTTmnlGroupUserByOrgNo(String orgNo,String orgType, PSysUser pSysUser) throws DBAccessException;
	
	/**
	 * 根据线路Id查询用户信息
	 * @param lineId  线路Id
	 * @return 
	 * @throws DBAccessException
	 */
	public List<TTmnlGroupUserDto> findTTmnlGroupUserByLineId(String lineId,PSysUser pSysUser) throws DBAccessException;
	
	/**
	 * 根据普通群组编号查询用户信息
	 * @param groupNo 群组编号
	 * @return 
	 * @throws DBAccessException
	 */
	public List<TTmnlGroupUserDto> findTTmnlGroupUserByCommonGroupNo(String groupNo) throws DBAccessException;
	
	/**
	 * 根据控制群组编号查询用户信息
	 * @param groupNo 群组编号
	 * @return 
	 * @throws DBAccessException
	 */
	public List<TTmnlGroupUserDto> findTTmnlGroupUserByCtrlGroupNo(String groupNo) throws DBAccessException;
	
	/**
	 * 根据变电站标识查询用户信息
	 * @param subsId 电站标识
	 * @return 
	 * @throws DBAccessException
	 */
	public List<TTmnlGroupUserDto> findTTmnlGroupUserBySubsId(String subsId,PSysUser pSysUser) throws DBAccessException;
}
