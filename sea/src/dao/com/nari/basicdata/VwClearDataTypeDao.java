package com.nari.basicdata;

import java.util.List;

import com.nari.basicdata.VwClearDataType;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.util.exception.DBAccessException;

/**
 * 透明规约类型视图 DAO 接口
 * @author 鲁兆淞
 */
public interface VwClearDataTypeDao {

	/**
	 * 查询全部透明规约类型列表。
	 * @return 透明规约类型列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<VwClearDataType> findAll() throws DBAccessException;

	/**
	 * 查询全部透明规约类型列表，带排序。
	 * @param orderBy 排序字段，用逗号分割，数量必须和排序方向一致。
	 * @param order 排序方向，为 "asc" 或 "desc"，用逗号分割，数量必须和排序字段一致。
	 * @return 透明规约类型列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<VwClearDataType> findAll(String orderBy, String order) throws DBAccessException;

	/**
	 * 按属性过滤条件查找透明规约类型列表，支持多种匹配方式，带排序。
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @param matchType 匹配方式，见 PropertyFilter 类。
	 * @param orderBy 排序字段，用逗号分割，数量必须和排序方向一致。
	 * @param order 排序方向，为 "asc" 或 "desc"，用逗号分割，数量必须和排序字段一致。
	 * @return 透明规约类型列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<VwClearDataType> findBy(String propertyName, Object value, MatchType matchType, String orderBy, String order) throws DBAccessException;

}
