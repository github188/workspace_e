package com.nari.sysman.securityman.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PRole;
import com.nari.sysman.securityman.IPRolePrivRelaJdbcDao;
import com.nari.util.exception.DBAccessException;

public class PRolePrivRelaJdbcDaoImpl extends JdbcBaseDAOImpl implements
		IPRolePrivRelaJdbcDao {

	protected Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 根据角色id删除表中相关所有记录
	 * 
	 * @param roleId
	 *            角色id
	 * @throws DBAccessException
	 */
	public void deleteByRoleId(Long roleId) throws DBAccessException {
		String sql = "delete P_ROLE_PRIV_RELA where role_id = ?";
		try {
			this.getJdbcTemplate().update(sql, new Object[] { roleId });
		} catch (RuntimeException e) {
			this.logger.debug("根据角色ID删除角色所有关联菜单出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String updateRoleMenu(String menus, PRole pRole) throws Exception {
		List<String> sqls = new ArrayList<String>();
		List<List> args = new ArrayList<List>();

		if (pRole.getRoleId() == null || pRole.getRoleId() == 0) {
			List l = getJdbcTemplate().queryForList(
					" SELECT 1 FROM p_role pr WHERE pr.role_desc=?",
					new Object[] { pRole.getRoleDesc() });
			if (l.size() > 0)
				return "角色'" + pRole.getRoleDesc() + "'已存在 ！";

			sqls.add(insertSql(pRole));
			args.add(insertArgs(pRole));
		} else {
			List l = getJdbcTemplate()
					.queryForList(
							"select 1 from p_role pr where pr.role_desc=? and pr.role_id<>?",
							new Object[] { pRole.getRoleDesc(),
									pRole.getRoleId() });
			if (l.size() > 0)
				return "另一个名称为 '" + pRole.getRoleDesc() + "'的角色已存在 ";
			sqls.add(updateSql(pRole));
			args.add(updateArgs(pRole));

		}

		String[] menu = menus.split(",");
		// 对于新增的角色
		if (pRole.getRoleId() == null || pRole.getRoleId() == 0) {
			for (int i = 0; i < menu.length; i++) {
				if (!menu[i].isEmpty()) {
					List arg = new ArrayList();
					sqls
							.add("INSERT INTO p_role_priv_rela VALUES ( s_p_role_priv_rela.nextval,?,s_p_role.currval)");
					arg.add(menu[i]);
					args.add(arg);

				}
			}
		} else {
			// 对于已存在的角色
			List roleArg = new ArrayList();
			roleArg.add(pRole.getRoleId());

			sqls.add("delete FROM p_role_priv_rela p WHERE p.role_id=?");
			args.add(roleArg);

			for (int i = 0; i < menu.length; i++) {
				if (!menu[i].isEmpty()) {
					List arg = new ArrayList();
					sqls
							.add("INSERT INTO p_role_priv_rela VALUES ( s_p_role_priv_rela.nextval,?,?)");
					arg.add(menu[i]);
					arg.add(pRole.getRoleId());
					args.add(arg);

				}
			}
		}
		try {
			super.doTransaction(sqls, args, "角色保存失败");
		} catch (Exception e) {
			throw e;

		}

		return "success";

	}

	/**
	 * 方法 insertSql
	 * 
	 * @param pRole
	 * @return 插入角色信息的SQL
	 */
	private String insertSql(PRole pRole) {

		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("INSERT INTO P_ROLE\n");
		sb.append("  (ROLE_ID,\n");
		sb.append("   ROLE_DESC,\n");
		sb.append("   FIELD_TYPE_CODE,\n");
		sb.append("   ROLE_GRANT_FLAG,\n");
		sb.append("   RW_STATION,\n");
		sb.append("   RW_TMNL,\n");
		sb.append("   ROLE_LEVEL)\n");
		sb.append("VALUES\n");
		sb.append("  (s_p_role.nextval, --role_id,\n");
		sb.append("   ?, --role_desc,\n");
		sb.append("   ?, --field_type_code,\n");
		sb.append("   ?, -- role_grant_flag,\n");
		sb.append("   ?, --rw_station,\n");
		sb.append("   ?, --rw_tmnl,\n");
		sb.append("   ? -- role_level\n");
		sb.append("   )");

		String sql = sb.toString();

		return sql;

	}

	/**
	 * 方法 insertArgs
	 * 
	 * @param pRole
	 * @return 插入角色信息的参数
	 */
	private List<Object> insertArgs(PRole pRole) {
		List<Object> list = new ArrayList();
		list.add(pRole.getRoleDesc());
		list.add(pRole.getFieldTypeCode());
		list.add(pRole.getRoleGrantFlag());
		list.add(pRole.getRwStation());
		list.add(pRole.getRwTmnl());
		list.add(pRole.getRoleLevel());
		return list;
	}

	/**
	 * 方法 updateSql
	 * 
	 * @param pRole
	 * @return 更新角色的SQL语句
	 */
	private String updateSql(PRole pRole) {

		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("UPDATE P_ROLE\n");
		sb.append("   SET ROLE_DESC       = ?,\n");
		sb.append("       FIELD_TYPE_CODE = ?,\n");
		sb.append("       ROLE_GRANT_FLAG = ?,\n");
		sb.append("       RW_STATION      = ?,\n");
		sb.append("       RW_TMNL         = ?,\n");
		sb.append("       ROLE_LEVEL      = ?\n");
		sb.append(" WHERE ROLE_ID = ?");

		String sql = sb.toString();
		return sql;

	}

	/**
	 * 方法 updateArgs
	 * 
	 * @param pRole
	 * @return List<Object>更新角色的参数
	 */
	private List<Object> updateArgs(PRole pRole) {
		List<Object> list = new ArrayList();
		list.add(pRole.getRoleDesc());
		list.add(pRole.getFieldTypeCode());
		list.add(pRole.getRoleGrantFlag());
		list.add(pRole.getRwStation());
		list.add(pRole.getRwTmnl());
		list.add(pRole.getRoleLevel());
		list.add(pRole.getRoleId());
		return list;

	}

}
