package com.nari.basicdata;

import java.util.List;

import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

public interface BClearProtocolDao {

	/**
	 * 查询全部对象列表，带排序。
	 * @param orderBy 排序字段，用逗号分割，数量必须和排序方向一致。
	 * @param order 排序方向，为 "asc" 或 "desc"，用逗号分割，数量必须和排序字段一致。
	 * @return 对象列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<BClearProtocol> findAll(String orderBy, String order) throws DBAccessException;

	/**
	 * 按属性过滤条件列表查找对象列表，带排序。
	 * @param filters 过滤条件列表
	 * @param orderBy 排序字段
	 * @param order 排序方向
	 * @return
	 * @throws DBAccessException
	 */
	public List<BClearProtocol> findBy(List<PropertyFilter> filters,String orderBy, String order) throws DBAccessException;
	/**
	 * 按属性过滤条件查找对象列表，匹配方式为相等，带排序。
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @param orderBy 排序字段，用逗号分割，数量必须和排序方向一致。
	 * @param order 排序方向，为 "asc" 或 "desc"，用逗号分割，数量必须和排序字段一致。
	 * @return 对象列表
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public List<BClearProtocol> findBy(String propertyName, Object value, String orderBy, String order) throws DBAccessException;
	/****
	 * 通过queryType来查找透明规约的组合
	 * ***/
	@SuppressWarnings("unchecked")
	public List<BClearProtocol> findByQueryType(final Integer queryType) throws DBAccessException;

}
