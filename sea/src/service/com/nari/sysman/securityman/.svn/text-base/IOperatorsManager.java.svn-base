package com.nari.sysman.securityman;

import com.nari.privilige.PSysUser;
import com.nari.privilige.PSysUserJdbc;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 接口 IOperatorsManager
 * 
 * @author zhangzhw
 * @describe 操作员管理Jdbc版
 */
public interface IOperatorsManager {

	/**
	 * 方法 findPage
	 * 
	 * @param start
	 * @param limit
	 * @return 所有用户详细信息
	 * @throws DBAccessException
	 */
	public Page<PSysUserJdbc> findPage(long start, long limit, PSysUser pSysUser)
			throws Exception;

	/**
	 * 方法 findPage
	 * 
	 * @param start
	 * @param limit
	 * @param pSysUserquery
	 * @param pSysUser
	 * @return 某些用户详细信息
	 * @throws Exception
	 */
	public Page<PSysUserJdbc> findPage(long start, long limit,
			PSysUser pSysUserquery, PSysUser pSysUser) throws Exception;

	/**
	 * 方法 saveOrUpdate
	 * 
	 * @param pSysUser
	 * @param orgs
	 * @param facs
	 * @param roles
	 * @param consTypes
	 * @throws DBAccessException
	 * @describe 保存或新增用户 及权限信息
	 */
	public String saveOrUpdate(PSysUser pSysUser, String orgs, String facs,
			String roles, String consTypes) throws Exception;

	/**
	 * 方法 deletePSysUser
	 * 
	 * @param staffNo
	 * @return 删除用户信息及权限是否成功
	 * @throws DBAccessException
	 */
	public boolean deletePSysUser(String staffNo) throws Exception;

}
