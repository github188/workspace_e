package com.nari.sysman.securityman;

import com.nari.privilige.PSysUser;
import com.nari.privilige.PSysUserJdbc;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 接口 IOperatorsDao
 * 
 * @author zhangzhw
 * @describe operator DAO
 */
public interface IOperatorsDao {

	/**
	 * 方法 findPage
	 * 
	 * @param start
	 * @param limit
	 * @return 所有用户详细信息
	 */
	public Page<PSysUserJdbc> findPage(long start, long limit, String accessOrg);

	/**
	 * 方法 findPage
	 * 
	 * @param start
	 * @param limit
	 * @param pSysUserQuery
	 * @param accessOrg
	 * @return 条件查询的用户信息
	 */
	public Page<PSysUserJdbc> findPage(long start, long limit,
			PSysUser pSysUserQuery, String accessOrg);

	/**
	 * 方法 saveOrUpdate
	 * 
	 * @param pSysUser
	 * @param orgs
	 * @param facs
	 * @param roles
	 * @param consTypes
	 */
	public String saveOrUpdate(PSysUser pSysUser, String orgs, String facs,
			String roles, String consTypes);

	/**
	 * 方法 deletePSysUser
	 * 
	 * @param staffNo
	 * @return 删除用户信息及权限是否成功
	 */
	public boolean deletePSysUser(String staffNo);
}
