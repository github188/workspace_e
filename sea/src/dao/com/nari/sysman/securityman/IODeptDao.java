package com.nari.sysman.securityman;

import java.util.List;

import com.nari.orgnization.ODept;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.util.exception.DBAccessException;

/**
 * 作者: 姜海辉
 * 创建时间：2009-11-17 下午07:54:58
 * 描述：
 */
public interface IODeptDao {

	/**
	 * 增加部门信息
	 * @param oDept
	 */
	public void save(ODept oDept) throws DBAccessException;

	/**
	 * 修改部门信息
	 * @param oDept
	 */
	public void update(ODept oDept) throws DBAccessException;

	/**
	 * 删除部门信息
	 * @param oDept
	 */
	public void delete(ODept oDept) throws DBAccessException;

	/**
	 * 根据部门编号删除部门信息
	 * @param deptNo
	 */
	public void delete(String deptNo) throws DBAccessException;

	/**
	 * 查询所有部门信息
	 * @return
	 */
	public List<ODept> findAll() throws DBAccessException;

	/**
	 * 根据部门编号查询部门信息
	 * @param roleId
	 * @return
	 */
	public ODept findById(String deptNo) throws DBAccessException;

	/**
	 * 分页查询所有部门
	 * @param page 分页参数
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	public Page<ODept> findPage(final Page<ODept> page) throws DBAccessException;

	/**
	 * 按属性过滤条件分页查找对象，支持多种匹配方式。
	 * @param page 分页参数
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @param matchType 匹配方式，见 PropertyFilter 类。
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	public Page<ODept> findPage(final Page<ODept> page, final String propertyName, final Object value, final MatchType matchType) throws DBAccessException;

	/**
	 * 按属性过滤条件列表分页查找对象。
	 * @param page 分页参数
	 * @param filters 过滤条件列表
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	public Page<ODept> findPage(final Page<ODept> page, final List<PropertyFilter> filters) throws DBAccessException;

	/**
	 * 按上级节点查询。
	 * @param node 上级节点
	 * @throws DBAccessException 数据库异常
	 */
	public List<ODept> findByPId(String node) throws DBAccessException;

}
