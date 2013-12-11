package com.nari.runman.dutylog.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nari.ami.database.map.basicdata.BComputerGroup;
import com.nari.baseapp.datagathorman.SqlData;
import com.nari.baseapp.datagathorman.impl.LeftTreeDaoImpl;
import com.nari.baseapp.planpowerconsume.impl.MapResultMapper;
import com.nari.privilige.PSysUser;
import com.nari.runman.dutylog.IUserLogDao;
import com.nari.runman.dutylog.IUserLogManager;
import com.nari.runman.dutylog.LUserLogEntry;
import com.nari.runman.dutylog.LUserOpLog;
import com.nari.runman.dutylog.StaffFindBean;
import com.nari.runman.dutylog.TmnlOpLogQuery;
import com.nari.support.Page;
import com.nari.util.CreateInsert;
import com.nari.util.ResultParse;
import com.nari.util.SelectSqlCreator;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 *创建人：陈建国 创建时间：20092009-12-8上午10:00:11 类描述： 日志操作
 */
public class UserLogManagerImpl implements IUserLogManager {

	IUserLogDao iUserLogDao;

	public void setiUserLogDao(IUserLogDao iUserLogDao) {
		this.iUserLogDao = iUserLogDao;
	}

	public List getAll() {
		try {
			return iUserLogDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public BComputerGroup getById(int id) {
		try {
			return iUserLogDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addUserLog(LUserOpLog log) {
		try {
			iUserLogDao.save(log);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/*****
	 * 日志查询功能
	 * 
	 * @param query
	 *            查询实体
	 * @param start
	 *            开始页
	 * @param limit
	 * @return 返回的分页结果 *
	 ****/
	@SuppressWarnings("unchecked")
	public Page<Map> findTmnlOpLog(TmnlOpLogQuery query, PSysUser user,
			int start, int limit) {
		LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
		SqlData sd = SqlData.getOne();
		// 使用语句生成器来生成语句
		StringBuilder sb = new StringBuilder(sd.logQuery_base);
		ResultParse rp = SelectSqlCreator.getSelectSql(query);
		if (!rp.getSql().trim().equals("")) {
			sb.append(" where ");
			sb.append(rp.getSql());
		}

		return tree.pagingFind(sb.toString(), start, limit,
				new MapResultMapper("[*,c]"), rp.getArgs().toArray());
	}

	/****
	 * 保存值班日志 *
	 * 
	 * @throws DBAccessException
	 *             *
	 ****/
	public void saveLog(LUserLogEntry userLog, PSysUser user)
			throws DBAccessException {
		// 通过日志找到用户的上班时间

		Date d = iUserLogDao.findTodayLogin(user);
		d = new Date();
		if (d == null) {
			throw new RuntimeException("今天尚未登陆");
		}
		userLog.setLogEntryDate(d);
		userLog.setOrgNo(iUserLogDao.findOrgByStaff(user));
		// 找到用户当前
		userLog.setEmpNo(user.getStaffNo());

		userLog.setAttachAc(1);
		userLog.setAttachMeter(1);
		userLog.setAttachTmnl(8);
		userLog.setCtrlCnt(9);
		userLog.setDetachAc(7);
		userLog.setDetachMeter(8);
		userLog.setDetachTmnl(6);
		userLog.setParamCnt(99);
		CreateInsert c = new CreateInsert(true);
		c.save(userLog);
		System.out.println(c.getId());
		System.out.println(userLog.getLogId());
		System.out.println(userLog.getLogId());
	}

	/*****
	 * 
	 * 通过左边树来加载用户
	 * 
	 * *
	 ****/
	@SuppressWarnings("unchecked")
	public List<Map> findStaffLeft(PSysUser user, String nodeData) {
		LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
		SqlData sd = SqlData.getOne();
		return null;
	}

	/******
	 * 通过查询条件对操作人员进行查询 返回的结果转化为骆驼命名法
	 * 
	 * @param find
	 *            查询条件构成的bean
	 * @param user
	 * @return 通过find 条件查找到的结果 *
	 **/
	@SuppressWarnings("unchecked")
	public Page<Map> findStaff(PSysUser user, int start, int limit,
			StaffFindBean find) {
		LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
		SqlData sd = SqlData.getOne();
		String sql = sd.logQuery_findStaff;
		List list = new ArrayList();
		list.add(user.getOrgNo()+"%");
		list.add(user.getOrgNo());
		if (find != null) {
			ResultParse r = SelectSqlCreator.getSelectSql(find);
			if (!r.getSql().trim().equals("")) {
				sql += " where " + r.getSql();
				list.addAll(r.getArgs());
			}
		}
		return tree.pagingFind(sql, start, limit, new MapResultMapper("[*,c]"),
				list.toArray());
	}

	/****
	 * 查询当前用户的上面时间
	 * 
	 * @throws DBAccessException
	 *             *
	 **/
	public Date findWorkTime(PSysUser user) throws DBAccessException {
		return iUserLogDao.findTodayLogin(user);
	}

	/******
	 * 得到当天截止到当前时间的实时统计数据
	 * 
	 * 生成的结果中page中map的格式为骆驼命名法
	 * 
	 * @param user
	 *            操作人员
	 *@param start
	 *            开始
	 *@param limit
	 *            每行记录的限制
	 *@return 分页后的结果 *
	 ***/
	@SuppressWarnings("unchecked")
	public Page<Map> findOpTypeToday(PSysUser user, int start, int limit) {
		return iUserLogDao.findOpTypeToday(user, start, limit);
	}

	@Override
	public List<Map> findWeatherDict() throws DBAccessException {
		return iUserLogDao.findWeatherDict();
	}

	@Override
	public List<Map> findWindForceDict() throws DBAccessException {
		return iUserLogDao.findWindForceDict();
	}

	/**
	 * 查询用户的操作类型
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findUserOptype() throws ServiceException {
		try {
			LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
			SqlData sd = SqlData.getOne();
			MapResultMapper mrp=new MapResultMapper("[*,c]");
		return	tree.getJdbcTemplate().query(sd.log_user_op_type, mrp);
		} catch (Exception e) {
			throw new ServiceException(e, e.getMessage());
		}
	}

	/**
	 * 查找终端的操作类型
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findTmnlOptype() throws ServiceException {
		try {
			LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
			SqlData sd = SqlData.getOne();
			MapResultMapper mrp=new MapResultMapper("[*,c]");
			return	tree.getJdbcTemplate().query(sd.log_tmnl_op_type, mrp);
		} catch (Exception e) {
			throw new ServiceException(e, e.getMessage());
		}
	}

	/****
	 * 
	 * 查询用户操作日志。 *
	 **/
	@SuppressWarnings("unchecked")
	public Page<Map> findUserOpLog(PSysUser user, TmnlOpLogQuery query,
			long start, long limit) {
		LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
		SqlData sd = SqlData.getOne();
		ResultParse rp = SelectSqlCreator.getSelectSql(query);
		if (rp.getSql().trim().equals("")) {
			return tree.pagingFind(sd.logQuery_userOpLog, start, limit,
					new MapResultMapper("[*,c]"), new Object[] {});
		} else {
			return tree.pagingFind(sd.logQuery_userOpLog + " where "
					+ rp.getSql(), start, limit, new MapResultMapper("[*,c]"),
					rp.getArgs().toArray());
		}
	}
}
