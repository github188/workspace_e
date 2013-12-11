package com.nari.baseapp.planpowerconsume;


import java.util.List;
import com.nari.support.PropertyFilter;
import com.nari.terminalparam.TTmnlGroup;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2010-1-11 上午09:11:00 
 * 描述：控制群组Dao接口
 */

public interface ITTmnlGroupDao {
	
	/**
	 * 添加控制群组
	 * @param tTmnlGroup 控制群组信息
	 * @throws DBAccessException 数据库异常
	 */
	public void save(TTmnlGroup tTmnlGroup) throws DBAccessException;
	
	/**
	 * 修改控制群组
	 * @param tTmnlGroup 控制群组信息
	 * @throws DBAccessException
	 */
	public void update(TTmnlGroup tTmnlGroup)throws DBAccessException;
	
	/**
	 * 删除控制群组
	 * @param groupNo  群组编号
	 * @throws DBAccessException
	 */
	public void delete(String groupNo)throws DBAccessException;
	
	
	/**
	 * 按属性过滤条件列表查找控制群组信息
	 * @param filters 过滤条件列表
	 * @return 群组信息列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<TTmnlGroup> findBy(List<PropertyFilter> filters) throws DBAccessException;
	
	
	/**
	 * 按属性过滤条件查找控制群组信息(jdbc)
	 * @param filters 过滤条件列表
	 * @return 群组信息列表
	 * @throws DBAccessException 数据库异常
	 */
	//public List<TTmnlGroup> queryBy(String groupName,Date startDate,Date finishDate) throws DBAccessException;
	
	
	
	/**
	 * 查询所有群组
	 * @throws DBAccessException 数据库异常
	 */
	public List<TTmnlGroup> findAll()throws DBAccessException;
	
	
}
