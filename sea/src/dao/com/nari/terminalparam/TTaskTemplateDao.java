package com.nari.terminalparam;

import java.util.List;

import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;
import com.nari.ami.database.map.terminalparam.TTaskTemplate;

/**
 * 任务模板DAO接口
 */
public interface TTaskTemplateDao {

	/**
	 * 新增或修改对象任务模板。
	 * @param tTaskTemplate 任务模板
	 * @throws DBAccessException 数据库异常
	 */
	public void saveOrUpdate(final TTaskTemplate tTaskTemplate) throws DBAccessException;
	
	/**
	 * 查询所有的任务模板
	 * @param orderBy 排序字段
	 * @param order 排序方式
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTaskTemplate> findAll(String orderBy, String order) throws DBAccessException;
	
	/**
	 * 根据任务模板id查询单个任务模板
	 * @param templateId 模板id
	 * @return
	 * @throws DBAccessException
	 */
	public TTaskTemplate findById(final Long templateId) throws DBAccessException;
	
	/**
	 * 根据任务模板名称查询任务模板是否存在
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTaskTemplate> findBy(String propertyName,Object value) throws DBAccessException;

	/**
	 * 查询所有启用的任务模板
	 * @param propertyName 参数名（是否启用）
	 * @param value	参数值（是）
	 * @param orderBy 排序字段
	 * @param order 排序方式
	 * @throws DBAccessException
	 */
	public List<TTaskTemplate> findBy(String propertyName,Object value,String orderBy,String order) throws DBAccessException;
	
	/**
	 * 按属性过滤条件列表查找对象列表，带排序。
	 * @param filters 过滤条件列表
	 */
	public List<TTaskTemplate> findBy(List<PropertyFilter> filters) throws DBAccessException;

}
