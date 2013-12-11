
package com.nari.runman.dutylog;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.ami.database.map.basicdata.BComputerGroup;
import com.nari.baseapp.planpowerconsume.impl.MapResultMapper;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;
/**
 * @创建人：陈建国
 * @创建时间：20092009-12-8上午09:55:30	 
 * @类描述：日志操作	
 */
public class UserLogDaoImpl extends JdbcBaseDAOImpl implements IUserLogDao {
	
	private Object[] objToArray(LUserOpLog log) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		String[] fields = new String[]{"empNo","orgNo","opType","opModule","opContent","ipAddr"};
		Object[] results = new Object[fields.length];
		for(int i = 0 ; i < fields.length ; i++){
			results[i] = BeanUtils.getProperty(log, fields[i]);
		}
		return results;
	}
	
	public void save(LUserOpLog log) throws DataAccessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		String sql = "insert into l_user_op_log(emp_no,org_no,op_type,op_time,op_module,op_content,ip_addr)" +
				"values(?,?,?,sysdate,?,?,?)";
		
		this.getJdbcTemplate().update(sql ,objToArray(log));
	}

	/**
	 * 缓存测试
	 */
	public List findAll() throws Exception{
		IStatement statement = CoherenceStatement.getSharedInstance();
		Collection col = statement.executeQuery("select * from BComputerGroup;");
		List list = new ArrayList();
		for(Iterator i = col.iterator();i.hasNext();){
			list.add(i.next());
		}
		return list;
	}
	
	/**
	 * 缓存测试
	 */
	public BComputerGroup findById (int id)throws Exception{
		IStatement statement = CoherenceStatement.getSharedInstance();
		BComputerGroup bcg =(BComputerGroup) statement.getValueByKey(BComputerGroup.class, id);
		return bcg;
	}
	/*****
	 * 
	 * 通过用户编号找到用户今天上班的时间
	 * @param user 
	 * return 用户今天的最早登陆时间
	 * *
	 * @throws DBAccessException ***/
	public Date findTodayLogin(PSysUser user) throws DBAccessException{
		String  sql=
			"select aaa_.op_time from( select l.op_time from \tL_USER_OP_LOG l  where l.op_type='05' and l.emp_no=?\n" +
			"  and l.op_time>trunc(sysdate)  order by op_time asc ) aaa_ where rownum=1";
		Date date=null;
		try {
		date=(Date) getJdbcTemplate().queryForObject(sql, new Object[]{user.getStaffNo()}, Date.class);
			
		} catch (Exception e) {
			return null;
		}
		return date;
	}
	/*****
	 * 查找某个用户的供电单位
	 * ****/
	public String findOrgByStaff(PSysUser user){
		String sql="select p.org_no from p_sys_user p where p.staff_no=?";
		try {
		return	(String) getJdbcTemplate().queryForObject(sql, new Object[]{user.getStaffNo()}, String.class);
		} catch (Exception e) {
			return null;
		}
	}
	/****
	 * 统计今天到目前为止的操作类型数据的统计数据
	 * 生成的结果中page中map的格式为骆驼命名法
	 * @param user 操作人员
	 *@param start 开始
	 *@param limit 每行记录的限制
	 *@return 分页后的结果
	 * ****/
	@SuppressWarnings("unchecked")
	public Page<Map> findOpTypeToday(PSysUser user,long start,int limit){
		String sql=
			"select op.org_no,o.org_name,\n" +
			"sum(decode(op.op_type,1,1,0)) as PARAM_CNT ,\n" + 
			"sum(decode(op.op_type,2,1,0))  as CTRL_CNT,\n" + 
			"sum(decode(op.op_type,3,1,0)) as ATTACH_TMNL,\n" + 
			"sum(decode(op.op_type,4,1,0))  as DETACH_TMNL\n" + 
			"from  l_op_tmnl_log op\n" + 
			"join o_org o on(op.org_no=o.org_no)\n" + 
			"join vw_op_type opt on(op.op_type=opt.op_type)\n" + 
			"join vw_op_obj opo on(opo.op_obj=op.op_obj)\n" + 
			"where op.op_time>trunc(sysdate)\n" + 
			" group by op.org_no,o.org_name ";
		return pagingFind(sql, start, limit, new MapResultMapper("[*,c]"), new Object[]{});
	}
	/****查找天气的字典****/
	@SuppressWarnings("unchecked")
	public List<Map> findWeatherDict() throws DBAccessException{
		String sql=
			"select bsd.dict_name as name from b_sys_dictionary bsd where bsd.dict_catalog = 'WEATHER'";

		return getJdbcTemplate().query(sql, new Object[]{}, new MapResultMapper("[*,l]"));
	}
	/*****查找风力的字典****/
	@SuppressWarnings("unchecked")
	public List<Map> findWindForceDict() throws DBAccessException{
		String sql=
			"select bsd.dict_name as name from b_sys_dictionary bsd where bsd.dict_catalog = 'WIND_DIRECTION'";

		return getJdbcTemplate().query(sql, new Object[]{}, new MapResultMapper("[*,l]"));
	}
}
