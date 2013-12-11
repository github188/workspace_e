/**
 *创建人：陈建国
 *创建时间：20092009-11-15下午02:09:40
 *类描述：
 */
package com.nari.sysman.securityman;

import java.util.List;

import com.nari.orgnization.ODept;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.privilige.PSysUserJdbc;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.util.TreeNode;
import com.nari.util.TreeNodeChecked;
import com.nari.util.exception.DBAccessException;

/**
 * @author Administrator
 * 
 */
public interface IAuthorizeManager {

	/**
	 * 添加用户信息
	 * 
	 * @param pSysUser
	 */
	public void addPSysUser(PSysUser pSysUser) throws Exception;

	/**
	 * 查询所有用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<PSysUser> getAllPSysUser() throws Exception;

	/**
	 * 分页查询所有用户
	 * 
	 * @param page
	 *            分页参数
	 * @return 分页查询结果
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public Page<PSysUser> findPage(final Page<PSysUser> page)
			throws DBAccessException;

	/**
	 * 按属性过滤条件分页查找对象，支持多种匹配方式。
	 * 
	 * @param page
	 *            分页参数
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            比较值
	 * @param matchType
	 *            匹配方式，见 PropertyFilter 类。
	 * @return 分页查询结果
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public Page<PSysUser> findPage(final Page<PSysUser> page,
			final String propertyName, final Object value,
			final MatchType matchType) throws DBAccessException;

	/**
	 * 按属性过滤条件列表分页查找对象。
	 * 
	 * @param page
	 *            分页参数
	 * @param filters
	 *            过滤条件列表
	 * @return 分页查询结果
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public Page<PSysUser> findPage(final Page<PSysUser> page,
			final List<PropertyFilter> filters) throws DBAccessException;

	/**
	 * 方法 findPage
	 * 
	 * @param filters
	 * @return 用户列表的分页
	 * @throws DBAccessException
	 */
	public Page<PSysUserJdbc> findPage(long start, int limit)
			throws DBAccessException;

	/**
	 * 根据用户名模糊查询所有的用户信息
	 * 
	 * @param name
	 *            用户名
	 * @return 用户信息的List
	 */
	public List<PSysUser> getAllPsPSysUserByName(String name)
			throws DBAccessException;

	/**
	 * 根据工号删除系统用户
	 * 
	 * @param staffNo
	 *            工号
	 */
	public void deletePSysUser(String staffNo) throws DBAccessException;

	/**
	 * 保存用户信息
	 * 
	 * @param pSysUser
	 *            用户信息
	 */
	public void updatePSysUser(PSysUser pSysUser) throws DBAccessException;

	/**
	 * 厂家树
	 * 
	 * @param staffNo
	 */
	public List<TreeNodeChecked> findFacTree(String staffNo)
			throws DBAccessException;

	/**
	 * 更新相关权限
	 * 
	 * @param staffNo
	 * @param roles
	 *            选中的角色
	 * @param facs
	 *            选中的厂家
	 * @param consTypes
	 * @param orgs
	 */
	public void updatePriv(String staffNo, String roles, String facs,
			String consTypes, String orgs) throws DBAccessException;

	/**
	 * 用电用电类型树
	 * 
	 * @param staffNo
	 */
	public List<TreeNodeChecked> findConsTypeTree(String staffNo)
			throws DBAccessException;

	/**
	 * 单位树
	 * 
	 * @param staffNo
	 */
	public List<TreeNodeChecked> findOrgTree(String orgNo, String staffNo)
			throws DBAccessException;

	/**
	 * 查询用户信息by StaffNO
	 * 
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public PSysUser findByStaffNo(String string) throws DBAccessException;

	/**
	 * 全部单位树
	 * 
	 * @param staffNo
	 * @throws DBAccessException
	 */
	public List<TreeNode> findOrgByNode(String node,PSysUser pSysUser) throws DBAccessException;

	/**
	 * 根据部门id获取部门
	 * 
	 * @param deptNo
	 * @throws DBAccessException
	 */
	public ODept getDeptById(String deptNo) throws DBAccessException;

	/**
	 * 方法 findAllDept
	 * 
	 * @return 所有部门列表
	 * @throws DBAccessException
	 */
	public List<ODept> findAllDept() throws DBAccessException;

	/**
	 * 根据机构id获取机构信息
	 * 
	 * @param orgNo
	 * @throws DBAccessException
	 */
	public OOrg getOrgById(String orgNo) throws DBAccessException;

	/**
	 * 方法 findAllOrg
	 * 
	 * @return 所有供电单位列表
	 * @throws DBAccessException
	 * @author zhangzhw
	 */
	public List<OOrg> findAllOrg() throws DBAccessException;

	/**
	 * 全部部門树
	 * 
	 * @param staffNo
	 * @throws DBAccessException
	 */
	public List<TreeNode> findDeptByNode(String node) throws DBAccessException;

}
