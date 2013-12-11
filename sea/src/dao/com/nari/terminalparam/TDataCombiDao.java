package com.nari.terminalparam;

import java.util.List;

import com.nari.support.PropertyFilter.MatchType;
import com.nari.util.exception.DBAccessException;

public interface TDataCombiDao {

	/**
	 * 按属性过滤条件查找召测组合列表，匹配方式为相等。
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @return 召测组合列表
	 * @throws DBAccessException 数据库列表
	 */
	public List<TDataCombi> findBy(String propertyName, Object value) throws DBAccessException;

	/**
	 * 查询全部召测组合列表，带排序。
	 * @param orderBy 排序字段
	 * @param order 排序方向
	 * @return 召测组合列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<TDataCombi> findAll(String orderBy, String order) throws DBAccessException;

	/**
	 * 新增召测组合列表
	 * @param entityList 召测组合列表
	 * @throws DBAccessException 数据库异常
	 */
	public void save(final List<TDataCombi> entityList) throws DBAccessException;
	/****
	 * 按照用户编号来查找所有的的召测组合
	 * @param staffNo 通过操作人员
	 * ****/
	public List<TDataCombi> findBy(String staffNo) throws DBAccessException; 
	/****
	 * 通过传进来的参数对按规则处理
	 * 
	 *格式：组合名称##clearNo##status##staffno 分割符号## 标示从数据库中得到的(不处理)<br>
	 * 2 del 标示会被删除<br>
	 * 3 edit 标示已经被标记过<br>
	 * 4 add 标示添加进来的树<br>
	 * 
	 * @param parseString
	 *            待处理数据，可以将它转化为实体bean 进行数据库操作<br>
	 *            *
	 **/
	public void parseEdit(List<String> parseString) throws DBAccessException;
	/****
	 * 
	 * 通过名称，透明编码编号，操作人员的编号来查找一个组合 如果clearNo为null，忽略clear添加进行判断
	 * 
	 * @param name
	 *            组合 名称
	 * @param clearNo
	 *            透明公约编码
	 * @param staffNo
	 *            工作人员的编号
	 * @return 如果clearNo为null 按照name和staffNo查找的第一个项<br>
	 *         如果clearNo不为null 按照三个项一起查的第一个项
	 ***/
	public TDataCombi findOneBy(final String name, final String clearNo,
			final String staffNo) throws DBAccessException ;
	/***
	 * 按照工作人员的编号来查找召测组合
	 * @param staffNo 操作人员编号
	 * @return 操作人员所对应的召测组合
	 * ****/
	//public void 
	@SuppressWarnings("unchecked")
	public List<TDataCombi> findByStaffNo(final String staffNo) throws DBAccessException;

}
