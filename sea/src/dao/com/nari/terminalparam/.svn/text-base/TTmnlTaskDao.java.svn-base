package com.nari.terminalparam;

import java.util.List;

import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

public interface TTmnlTaskDao {

	/**
	 * 新增或修改对象任务。
	 * @param tmnlTask 终端任务
	 * @throws DBAccessException 数据库异常
	 */
	public void saveOrUpdate(final TTmnlTask tmnlTask) throws DBAccessException;

	/**
	 * 删除终端任务
	 * @param tmnlTask
	 * @throws DBAccessException
	 */
	public void delete(final TTmnlTask tmnlTask) throws DBAccessException;
	
	/**
	 * 根据条件列表查询终端任务列表
	 * @param filters 条件列表
	 * @return 
	 * @throws DBAccessException
	 */
	public List<TTmnlTask> findBy(List<PropertyFilter> filters) throws DBAccessException;
	
	/**
	 * 根据id查询终端任务
	 * @param tTmnlTaskId
	 * @return
	 * @throws DBAccessException
	 */
	public TTmnlTask findById(TTmnlTaskId tTmnlTaskId) throws DBAccessException;
	
	/**
	 * 根据id删除任务
	 * @param tTmnlTaskId 任务id
	 * @throws DBAccessException
	 */
	public void delete(TTmnlTaskId tTmnlTaskId) throws DBAccessException;
}
