package com.nari.baseapp.datagathorman;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.support.Page;

/*** 
 * 一个简单的接口，提供jdbcbasedao里面的个别方法，业务层实现这个接口即可自动添加
 * <br>在左边树中要使用的方法   结合使用MapResultMapper可以减少左边树的操作
 * @author huangxuan
 * **/
public interface ILeftTreeDao {
	/**
	 * 方法 pagingFind
	 * 
	 * @param sql
	 * @param start
	 * @param limit
	 * @param rowMapper
	 * @return 分页查询返回PageInfo (分页内的数据对象List 和 总数据数)
	 */
	public <X> Page<X> pagingFind(String sql, long start, int limit,
			RowMapper rowMapper);
	/**
	 * 方法 pagingFind
	 * 
	 * @param sql
	 * @param start
	 * @param limit
	 * @param rowMapper
	 * @param obj
	 * @return 单条件分页查询返回PageInfo (分页内的数据对象List 和 总数据数)
	 */
	@SuppressWarnings("unchecked")
	public <X> Page<X> pagingFind(String sql, long start, int limit,
			RowMapper rowMapper, Object obj) ;
	/**
	 * 方法 pagingFind
	 * 
	 * @param sql
	 * @param start
	 * @param limit
	 * @param rowMapper
	 * @param obj
	 * @return 按条件分页查询返回PageInfo (分页内的数据对象List 和 总数据数)
	 */
	@SuppressWarnings("unchecked")
	public <X> Page<X> pagingFind(String sql, long start, int limit,
			RowMapper rowMapper, Object[] obj);
	/**
	 * 方法 pagingFindList
	 * 
	 * @param sql
	 * @param start
	 * @param limit
	 * @param rowMapper
	 * @return 分页查询的记录对象
	 */
	public <X> List<X> pagingFindList(String sql, long start, int limit,
			RowMapper rowMapper);
	
	/**
	 * 
	 * @param sql
	 * @param start
	 * @param limit
	 * @param rowMapper
	 * @param obj
	 * @return 分页查询的记录对象(单一条件查询)
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> pagingFindList(String sql, long start, int limit,
			RowMapper rowMapper, Object obj);
	/**
	 * 方法 pagingFindList
	 * 
	 * @param sql
	 * @param start
	 * @param limit
	 * @param rowMapper
	 * @param obj
	 * @return 条件查询分页查询的记录对象
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> pagingFindList(String sql, long start, int limit,
			RowMapper rowMapper, Object[] obj);
}
