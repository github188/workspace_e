package com.nari.advapp.vipconsman;


import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 重点用户定义Dao
 * 
 * @author ChunMingLi
 * @since  2010-6-3
 *
 */
public interface VipConsManagerDao {
	/**
	 * 根据终端资产编号查询用户信息
	 * 
	 * @param tmnlAssetNo 终端资产编号
	 * @param start 开始页
	 * @param limit 每页显示数
	 * @return
	 */
	public Page<VipConsManagerDto> findVipConsDefineListByTmnlAssetNo(String tmnlAssetNo, long start, int limit) throws DBAccessException;
	
	/**
	 * 根据用户编号查询用户信息
	 * 
	 * @param consNo 用户编号
	 * @param start 开始页
	 * @param limit 每页显示数
	 * @return
	 * @throws DBAccessException
	 */
	public Page<VipConsManagerDto> findVipConsDefineListByConsNo(String consNo, long start, int limit) throws DBAccessException;

	/**
	 * 
	 * 根据供电单位编号查询用户信息
	 * 
	 * @param orgType  供电单位类型
	 * @param orgNo    供电单位编号
	 * @param pSysUser  操作员信息
	 * @param start 开始页
	 * @param limit 每页显示数
	 * @return
	 */
	public Page<VipConsManagerDto> findVipConsDefineListByOrgNo(String orgNo,String orgType,PSysUser pSysUser, long start, int limit)throws DBAccessException;
	
	/**
	 * 
	 * 根据线路Id查询用户信息
	 * 
	 * @param lineId 线路Id
	 * @param pSysUser  操作员信息
	 * @param start 开始页
	 * @param limit 每页显示数
	 * @return
	 */
	public Page<VipConsManagerDto> findVipConsDefineListByLine(String line, PSysUser pSysUser, long start, int limit)throws DBAccessException;
	
	/**
	 * 
	 * 根据普通群组编号查询用户信息
	 * 
	 * @param groupNo 普通群组编号
	 * @param start 开始页
	 * @param limit 每页显示数
	 * @return
	 */
	public Page<VipConsManagerDto> findVipConsDefineListByCommonGroupNo(String groupNo, long start, int limit)throws DBAccessException ;
	
	/**
	 * 
	 * 根据控制群组编号查询用户信息
	 * 
	 * @param groupNo  控制群组编号
	 * @param start 开始页
	 * @param limit 每页显示数
	 * @return
	 */
	public Page<VipConsManagerDto> findVipConsDefineListByCtrlGroupNo(String groupNo, long start, int limit)throws DBAccessException ;

	/**
	 * 
	 * 根据变电站标识查询用户信息
	 * 
	 * @param subsId 变电站标识
	 * @param pSysUser  操作员信息 
	 * @param start 开始页
	 * @param limit 每页显示数
	 * @return
	 */
	public Page<VipConsManagerDto> findVipConsDefineListBySub(String subId, PSysUser pSysUser, long start, int limit)throws DBAccessException;

	/**
	 * 根据操作员工号获得该操作员重点用户列表
	 * 
	 * @param staffNo  操作员工号
	 * @param start 开始页 
	 * @param limit 每页显示数 
	 * @return
	 */
	public Page<VipConsManagerDto> findVipConsListByStaffNo(String staffNo,long start, int limit)throws DBAccessException;
}
