package com.nari.coherence;

import java.util.List;

/**
 * @author 陈建国
 *
 * @创建时间:2009-12-9 下午04:05:21
 *
 * @类描述: 操作缓存数据
 *	
 */
public interface ICoherenceDao {

	/**
	 * @描述 添加数据
	 * @param obj 实体对象
	 */
	public void add(Object obj,Object priKey) throws Exception;
	
	/**
	 * @描述 更新数据
	 * @param obj 实体对象
	 */
	public void update(Object obj,Object priKey) throws Exception;
	
	/**
	 * @描述 根据sql语句(sql="select * from BComputerGroup where computerStatus='false' ;")查询数据 
	 * @param sql
	 * @return 
	 */
	public List getAll(String sql) throws Exception;
}
