
package com.nari.runman.dutylog;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nari.ami.database.map.basicdata.BComputerGroup;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 *创建人：陈建国
 *创建时间：20092009-12-8上午09:59:28	 
 *类描述：日志操作	
 */
public interface IUserLogManager {
	public void addUserLog(LUserOpLog log);
	
	public List getAll();
	
	public BComputerGroup getById(int id);
	/****
	 * 保存值班日志
	 * *
	 * @throws DBAccessException *****/
	public void saveLog(LUserLogEntry userLog, PSysUser user) throws DBAccessException;
	
	/*****
	 * 日志查询功能
	 * @param query 查询实体  
	 * @param start 开始页
	 * @param limit 
	 * @return 返回的分页结果
	 * *****/
	Page<Map> findTmnlOpLog(TmnlOpLogQuery query, PSysUser user, int start,
			int limit);

	Page<Map> findStaff(PSysUser user, int start, int limit, StaffFindBean find);
	/****
	 * 当前用户的上班时间
	 * *****/
	Date findWorkTime(PSysUser user) throws DBAccessException;
	/******
	 * 得到当天截止到当前时间的实时统计数据
	 * 
	 * 生成的结果中page中map的格式为骆驼命名法
	 * @param user 操作人员
	 *@param start 开始
	 *@param limit 每行记录的限制
	 *@return 分页后的结果
	 * ****/
	Page<Map> findOpTypeToday(PSysUser user, int start, int limit);
	/****查找天气的字典****/
	public List<Map> findWeatherDict() throws DBAccessException;
	/*****查找风向的字典****/
	public List<Map> findWindForceDict() throws DBAccessException;
	/***
	 * 查询用户操作日志
	 * 
	 * ***/
	Page<Map> findUserOpLog(PSysUser user, TmnlOpLogQuery query, long start,
			long limit);
	/**
	 * 查找终端的操作类型
	 * 
	 * @return
	 * @throws ServiceException
	 */
	List<Map> findTmnlOptype() throws ServiceException;
	/**
	 * 查询用户的操作类型
	 * 
	 * @return
	 * @throws ServiceException
	 */
	List<Map> findUserOptype() throws ServiceException;
}
