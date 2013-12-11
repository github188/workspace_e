package com.nari.baseapp.planpowerconsume;


import java.util.List;

import com.nari.orderlypower.TTmnlGroupUserDto;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2010-1-9 下午02:28:00 
 * 描述：从左边树加载群组管理所需的终端信息接口
 */

public interface ITTmnlGroupUserManager {

	/**
	 * 根据终端资产编号查询新增群组Grid数据
	 * @param tmnlAssetNo 终端资产编号
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTmnlGroupUserDto> queryTTmnlGroupUserByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException;
	
	/**
	 * 根据用户编号查询新增群组Grid数据分页列表
	 * @param consNo 用户编号
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<TTmnlGroupUserDto> queryTTmnlGroupUserByConsNo(String consNo) throws DBAccessException;
	
	/**
	 * 根据供电单位编号查询新增群组Grid数据分页列表
	 * @param orgNo 供电单位编号
	 * @param orgType  供电单位类型
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<TTmnlGroupUserDto> queryTTmnlGroupUserByOrgNo(String orgNo,String orgType,PSysUser pSysUser) throws DBAccessException;
	
	/**
	 * 根据线路Id查询新增群组Grid数据分页列表
	 * @param lineId 线路Id
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<TTmnlGroupUserDto> queryTTmnlGroupUserByLineId(String lineId,PSysUser pSysUser) throws DBAccessException;
	
	/**
	 * 根据群组编号查询新增群组Grid数据分页列表
	 * @param nodeType 树节点类型
	 * @param groupNo  群组编号
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTmnlGroupUserDto> queryTTmnlGroupUserByGroupNo(String nodeType,String groupNo) throws DBAccessException;
	
	/**
	 * 根据变电站标识查询新增群组Grid数据分页列表
	 * @param subsId 变电站标识
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTmnlGroupUserDto> queryTTmnlGroupUserBySubsId(String subsId,PSysUser pSysUser) throws DBAccessException;
}
