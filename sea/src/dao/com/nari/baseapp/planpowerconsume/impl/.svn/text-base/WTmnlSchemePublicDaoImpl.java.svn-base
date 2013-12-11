package com.nari.baseapp.planpowerconsume.impl;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.StatementCallback;

import com.nari.baseapp.planpowerconsume.IWCtrlSchemeJdbcDao;
import com.nari.baseapp.planpowerconsume.IWTmnlSchemePublicDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/****
 * 对于七种控制方案，将他们的相同的操作在此方法中实现 统一操作接口
 * 
 * @author huangxuan *
 **/
public class WTmnlSchemePublicDaoImpl extends JdbcBaseDAOImpl implements
		IWTmnlSchemePublicDao {
	/***
	 * 依靠控制方案类做一些前置性的操作 *
	 **/
	private IWCtrlSchemeJdbcDao iWCtrlSchemeJdbcDao;

	public void setiWCtrlSchemeJdbcDao(IWCtrlSchemeJdbcDao iWCtrlSchemeJdbcDao) {
		this.iWCtrlSchemeJdbcDao = iWCtrlSchemeJdbcDao;
	}

	/***
	 * 使用一个map来对操作的关联进行配置,可以将配置的信息写在spring 配置文件中 *
	 **/
	private Map<String, String> noToTable = new HashMap<String, String>();

	public void setNoToTable(Map<String, String> noToTable) {
		this.noToTable = noToTable;
	}

	/***
	 * @param ctrlSchemeIds
	 * @throws DBAccessException
	 *             *
	 **/
	@SuppressWarnings("unchecked")
	public void updateAboutCtrlScheme(List ctrlSchemeIds)
			throws DBAccessException {
		for (Object obj : ctrlSchemeIds) {
			Long id = (Long) obj;
			String sql = 
				"update w_ctrl_scheme w\n" +
				"   set w.is_valid = (select case\n" + 
				"                              when t.is_valid = 0 then\n" + 
				"                               1\n" + 
				"                              else\n" + 
				"                               0\n" + 
				"                            end\n" + 
				"                       from w_ctrl_scheme t\n" + 
				"                      where t.ctrl_scheme_id = w.ctrl_scheme_id)\n" + 
				" where w.ctrl_scheme_id = ?";
			getJdbcTemplate().update(sql, new Object[] { id });
		}
	}

	/***
	 * 通过方案的id来找方案的的名称
	 * 
	 * @param ctrlSchemeId
	 *            对应的方案的id
	 * @return 方案的名称 *
	 **/
	public String findNameById(Long ctrlShemeId) {
		return (String) getJdbcTemplate()
				.queryForObject(
						"select CTRL_SCHEME_NAME from W_CTRL_SCHEME where CTRL_SCHEME_ID=? ",
						new Object[] { ctrlShemeId }, String.class);
	}

	/***
	 * 通过表名和控制方案id 找到终端资产号
	 * 
	 * @param tb
	 *            对应的表的名字
	 * @param ctrlSchemeId
	 *            方案的id
	 * @return 终端资产号 *
	 **/
	private String findTmnlAssetNo(String tb, Long ctrlSchemeId) {
		String sql = "select TMNL_ASSET_NO from " + tb
				+ " where CTRL_SCHEME_ID=? ";
		return (String) getJdbcTemplate().queryForObject(sql,
				new Object[] { ctrlSchemeId }, String.class);
	}

	/***
	 * 为提供对userNos有效性的代码，可以事先对userNos进行过滤 请保证userNos不加泛型
	 * 
	 * @param 用电用户的id列表
	 *            *
	 * @throws DBAccessException
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public void deleteCtrlSchemeUser(Long ctrlSchemeId, List userNos)
			throws DBAccessException {
		if (ctrlSchemeId == null) {
			throw new DBAccessException("控制方案id不能为空");
		}
		String tb = noToTable.get(findCtrlSchemeType(ctrlSchemeId));
		// 找到所有的不能删除的用户的列表，
		StringBuilder arrSql = new StringBuilder();
		for (Iterator iterator = userNos.iterator(); iterator.hasNext();) {
			iterator.next();
			if (iterator.hasNext()) {
				arrSql.append("?,");
			} else {
				arrSql.append("?");
			}
		}
		userNos.add(ctrlSchemeId);
		//查询待删除的列表中是不是存在不能被删除的项目，存在将不能删除项的信息抛出
		String noDelSql = "select c.cons_name from "
				+ tb
				+ " w left join c_cons c on (c.cons_no=w.cons_no) left join r_tmnl_run r on(r.tmnl_asset_no=w.tmnl_asset_no)"
				+ " where w.cons_no in ("
				+ arrSql.toString()
				+ ") and  w.CTRL_SCHEME_ID=? and ("
				+ "(ctrl_flag=0 and (failure_code<>'' and failure_code is not null)) or ctrl_flag=1)";
		List userList = getJdbcTemplate().queryForList(noDelSql,
				userNos.toArray(), String.class);
		if (userList != null && userList.size() != 0) {
			throw new DBAccessException("删除失败。原因："
					+ userList.toString().replace("[", "").replace("]", "")
					+ "的" + "用电方案必须要先解除");
		}
		StringBuilder sb = new StringBuilder("delete from ").append(tb).append(
				" where CONS_NO in  (");
		userNos.remove(userNos.size() - 1);
		for (Iterator iterator = userNos.iterator(); iterator.hasNext();) {
			iterator.next();
			if (iterator.hasNext()) {
				sb.append("?,");
			} else {
				sb.append("?)");
			}
		}

		sb.append(" and CTRL_SCHEME_ID=?  ");
		// sb.append("(");
		// // 添加其他的限制
		// sb
		// .append("ctrl_flag=0 and (failure_code<>'' and  failure_code is not null))");
		// sb.append("or (ctrl_flag=1)");
		// sb.append(")");
		// 将这个条件也添加到列表
		userNos.add(ctrlSchemeId);
		getJdbcTemplate().update(sb.toString(), userNos.toArray());
	}

	/***
	 * 找到所有的控制方案的用户 org_no,cons_no,cons_name,elec_addr,terminal_addr *
	 **/
	@Override
	public <T> Page<T> findCtrlSchemeUser(Long ctrlSchemeId, long start,
			int limit) {
		String tb = noToTable.get(findCtrlSchemeType(ctrlSchemeId));
		String sql = "select w.org_no,c.cons_no,c.cons_name,w.ctrl_flag,w.failure_code,c.elec_addr,r.terminal_addr from "
				+ tb
				+ " w left join c_cons c on (c.cons_no=w.cons_no) left join r_tmnl_run r on(r.tmnl_asset_no=w.tmnl_asset_no) where w.CTRL_SCHEME_ID=?";
		return pagingFind(sql, start, limit, new MapResultMapper("[*,l]"),
				ctrlSchemeId);
	}

	@Override
	public String findCtrlSchemeType(Long ctrlSchemeId) {
		String sql = "select CTRL_SCHEME_TYPE from W_CTRL_SCHEME where CTRL_SCHEME_ID=?";
		try {

			return (String) getJdbcTemplate().queryForObject(sql,
					new Object[] { ctrlSchemeId }, new RowMapper() {

						@Override
						public Object mapRow(ResultSet resultset, int i)
								throws SQLException {
							String s = resultset.getString("CTRL_SCHEME_TYPE");
							return s;
						}
					});
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/***
	 *以小写的格式返回结果
	 ***/
	public Page findAllUsers(String staffNo, Long ctrlSchemeId, long start,
			int limit) throws DBAccessException {
		// 通过id找到表名
		String tb = noToTable.get(findCtrlSchemeType(ctrlSchemeId));

		StringBuilder sql = new StringBuilder(
				"select o.org_name,w.ctrl_scheme_id,c.cons_no,c.cons_name,w.tmnl_asset_no,w.total_no,w.ctrl_flag from ");
		sql.append(tb);
		sql
				.append(" w left join o_org o on (w.org_no=o.org_no) left join c_cons c on(c.cons_no=w.cons_no) ");
		sql.append(" where w.ctrl_scheme_id=? and w.STAFF_NO=? ");
		return pagingFind(sql.toString(), start, limit, new MapResultMapper(
				"[*,l]"), new Object[] { ctrlSchemeId, staffNo });
	}

}
