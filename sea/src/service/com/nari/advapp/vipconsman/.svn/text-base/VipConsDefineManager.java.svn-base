package com.nari.advapp.vipconsman;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 重点用户定义service
 * 
 * @author ChunMingLi
 * @since  2010-6-3
 *
 */
public interface VipConsDefineManager {
	/**
	 * 
	 * @param tmnlAssetNo
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsManagerDto> queryVipConsDefineListByTmnlAssetNo(String tmnlAssetNo, long start, int limit)throws DBAccessException ;

	/**
	 * 
	 * @param orgNo
	 * @param orgType
	 * @param pSysUser
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsManagerDto> queryVipConsDefineListByOrgNo(String orgNo,String orgType,PSysUser pSysUser, long start, int limit)throws DBAccessException ;
	
	/**
	 * 
	 * @param line
	 * @param pSysUser
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsManagerDto> queryVipConsDefineListByLine(String line, PSysUser pSysUser, long start, int limit)throws DBAccessException ;
	
	/**
	 * 
	 * @param groupNo
	 * @param nodeType
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsManagerDto> queryVipConsDefineListByCommonGroupNo(String groupNo, long start, int limit)throws DBAccessException ;

	/**
	 * 
	 * @param groupNo
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsManagerDto> queryVipConsDefineListByCtrlGroupNo(String groupNo, long start, int limit)throws DBAccessException ;
	/**
	 * 
	 * @param sub
	 * @param pSysUser
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsManagerDto> queryVipConsDefineListBySub(String sub, PSysUser pSysUser, long start, int limit)throws DBAccessException ;

	/**
	 * 根据用户编号查询用户信息
	 * 
	 * @param consNo 用户编号
	 * @param start 开始页
	 * @param limit 每页显示数
	 * @return
	 * @throws DBAccessException
	 */
	public Page<VipConsManagerDto> queryVipConsDefineListByConsNo(String consNo, long start, int limit) throws DBAccessException;
	
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
