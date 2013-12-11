package com.nari.coherence.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.coherence.ICoherenceDao;

/**
 * @author 陈建国
 *
 * @创建时间:2009-12-9 下午04:07:49
 *
 * @类描述:  操作缓存数据
 *	
 */
public class CoherenceDaoImpl implements ICoherenceDao {

	
	/**
	 * @描述 添加数据
	 * @param obj 实体对象
	 * @param priKey 实体对象的主键
	 * @throws Exception 
	 */
	public void add(Object obj,Object priKey) throws Exception{
		IStatement statement = CoherenceStatement.getSharedInstance();
		statement.executeUpdate(obj.getClass(),priKey,obj);
	}
	
	/**
	 * @描述 更新数据
	 * @param obj 实体对象
	 * @param priKey 实体对象的主键
	 * @throws Exception 
	 */
	public void update(Object obj,Object priKey) throws Exception{
		IStatement statement = CoherenceStatement.getSharedInstance();
		statement.executeUpdate(obj.getClass(), priKey, obj);
	}
	
	/**
	 * @描述 :根据sql语句查询数据 
	 *        sql 类似于普通的sql语句,如：(sql="select * from BComputerGroup")
	 *        也可以带简单的查询,如：sql="select * from BComputerGroup where computerStatus='false' ;")
	 * @param sql sql语句
	 * @return 
	 * @throws Exception 
	 */
	public List getAll(String sql) throws Exception{
		IStatement statement = CoherenceStatement.getSharedInstance();
		Collection col = statement.executeQuery(sql);
		return CollectionToList(col);
	}
	
	/**
	 * @描述： 将Collection 转换成 List
	 * @param col 
	 * @return
	 */
	public List CollectionToList(Collection col){
		List list = new ArrayList();
		for(Iterator i = col.iterator(); i.hasNext();){
			list.add(i.next());
		}
		
		return list;
	}
}
