/**
 *创建人：陈建国
 *创建时间：20092009-11-15下午01:28:16
 *描述：
 */
package com.nari.sysman.securityman;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.util.exception.DBAccessException;

/**
 * @author Administrator
 * 
 */
public interface IPSysUserDao {

	/**
	 * 增加系统用户信息
	 * 
	 * @param pSysUser
	 */
	public void save(PSysUser pSysUser) throws DBAccessException;

	/**
	 * 修改系统用户信息
	 * 
	 * @param pSysUser
	 */
	public void update(PSysUser pSysUser) throws DBAccessException;

	/**
	 * 删除系统用户信息
	 * 
	 * @param pSysUser
	 */
	public void delete(PSysUser pSysUser) throws DBAccessException;

	/**
	 * 根据工号删除用户信息
	 * 
	 * @param staffNo
	 */
	public void delete(String staffNo) throws DBAccessException;

	/**
	 * 查询所有用户信息
	 * 
	 * @return
	 */
	public List<PSysUser> findAll() throws DBAccessException;

	/**
	 * 根据工号查询用户信息
	 * 
	 * @param staffNo
	 * @return
	 */
	public PSysUser findById(String staffNo) throws DBAccessException;

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
	 * 根据用户名模糊查询所有的用户信息
	 * 
	 * @param name
	 *            用户名
	 * @return 用户信息的List
	 */
	public List<PSysUser> findAllByName(String name) throws DBAccessException;

	/**
	 * @描述 根据工号和密码查找系统用户
	 * @param staffNo
	 *            工号
	 * @param password
	 *            密码
	 * @return
	 * @throws DBAccessException
	 */
	public PSysUser findByStaffNoAndPWD(String staffNo, String password)
			throws DBAccessException;

	/**
	 * @描述 根据工号查找系统用户
	 * @param staffNo
	 *            工号
	 * @return
	 * @throws DBAccessException
	 */
	public PSysUser findByStaffNo(String staffNo) throws DBAccessException;

}
