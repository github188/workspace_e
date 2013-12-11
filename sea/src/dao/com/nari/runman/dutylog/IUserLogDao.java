
package com.nari.runman.dutylog;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nari.ami.database.map.basicdata.BComputerGroup;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 *@创建人：陈建国
 *@创建时间：20092009-12-8上午09:54:33	 
 *@类描述：用户日志操作	
 */
public interface IUserLogDao {

	public void save(LUserOpLog log)throws DataAccessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	public List findAll()throws Exception;
	
	public BComputerGroup findById (int id)throws Exception;
	/*****
	 * 
	 * 通过用户编号找到用户今天上班的时间
	 * @param user 
	 * return 用户今天的最早登陆时间
	 * *
	 * @throws DBAccessException ***/
	public Date findTodayLogin(PSysUser user) throws DBAccessException;

	public String findOrgByStaff(PSysUser user);
	/****
	 * 统计每天的操作类型数据
	 * 生成的结果中page中map的格式为骆驼命名法
	 * @param user 操作人员
	 *@param start 开始
	 *@param limit 每行记录的限制
	 *@return 分页后的结果
	 * ****/
	public Page<Map> findOpTypeToday(PSysUser user, long start, int limit);
	/****查找天气的字典****/
	public List<Map> findWeatherDict() throws DBAccessException;
	/*****查找风力的字典****/
	public List<Map> findWindForceDict() throws DBAccessException;

}
