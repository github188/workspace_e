
/**
 * 作者:姜海辉
 * 创建时间：2009-11-16 上午10:48:37
 * 描述：
 */
package com.nari.sysman.securityman;
import java.util.List;

import com.nari.privilige.PRole;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.util.exception.DBAccessException;

public interface  IPRoleDao {

	/**
	 * 增加角色信息
	 * @param pRole
	 */
	public void save(PRole pRole) throws DBAccessException;

	/**
	 * 修改角色信息
	 * @param pRole
	 */
	public void update(PRole pRole) throws DBAccessException;

	/**
	 * 删除角色信息
	 * @param pRole
	 */
	public void delete(PRole pRole) throws DBAccessException;

	/**
	 * 根据角色标识删除角色信息
	 * @param roleId
	 */
	public void delete(Long roleId) throws DBAccessException;

	/**
	 * 查询所有角色信息
	 * @return
	 */
	public List<PRole> findAll() throws DBAccessException;

	/**
	 * 根据角色标识查询用户信息
	 * @param roleId
	 * @return
	 */
	public PRole findById(Long roleId) throws DBAccessException;

	/**
	 * 分页查询所有角色
	 * @param page 分页参数
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	public Page<PRole> findPage(final Page<PRole> page) throws DBAccessException;

	/**
	 * 按属性过滤条件分页查找对象，支持多种匹配方式。
	 * @param page 分页参数
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @param matchType 匹配方式，见 PropertyFilter 类。
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	public Page<PRole> findPage(final Page<PRole> page, final String propertyName, final Object value, final MatchType matchType) throws DBAccessException;

	/**
	 * 按属性过滤条件列表分页查找对象。
	 * @param page 分页参数
	 * @param filters 过滤条件列表
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	public Page<PRole> findPage(final Page<PRole> page, final List<PropertyFilter> filters) throws DBAccessException;

	/**
	 * 根据角色描述查询用户信息
	 * @param roleDesc
	 * @return
	 */
	public List<PRole> findByRoleDesc(String roleDesc) throws DBAccessException;

}
