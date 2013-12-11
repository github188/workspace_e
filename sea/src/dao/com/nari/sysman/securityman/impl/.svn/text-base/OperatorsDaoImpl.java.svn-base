package com.nari.sysman.securityman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.privilige.PSysUserJdbc;
import com.nari.support.Page;
import com.nari.sysman.securityman.IOperatorsDao;

/**
 * 实现类 OperatorsDaoImpl
 * 
 * @author zhangzhw
 * 
 */
public class OperatorsDaoImpl extends JdbcBaseDAOImpl implements IOperatorsDao {

	@Override
	public Page<PSysUserJdbc> findPage(long start, long limit, String accessOrg) {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT PS.EMP_NO,\n");
		sb.append("       PS.STAFF_NO,\n");
		sb.append("       PS.ORG_NO,\n");
		sb.append("       PS.DEPT_NO,\n");
		sb.append("       PS.NAME,\n");
		sb.append("       PS.PWD,\n");
		sb.append("       PS.IP,\n");
		sb.append("       PS.MAC,\n");
		sb.append("       PS.ACCESS_LEVEL,\n");
		sb.append("       PS.CUR_STATUS_CODE,\n");
		sb.append("       PS.PWD_REMIND_TIME,\n");
		sb.append("       PS.LOCK_TIME,\n");
		sb.append("       PS.PLAN_UNLOCK_TIME,\n");
		sb.append("       PS.UNLOCK_TIME,\n");
		sb.append("       PS.LOCK_IP,\n");
		sb.append("       PS.AUTO_UNLOCK_FLAG,\n");
		sb.append("       PS.LOCK_REASON,\n");
		sb.append("       PS.UNLOCK_EMP_NO,\n");
		sb.append("       OO.ORG_NAME,\n");
		sb.append("       OD.NAME DEPNAME\n");
		sb.append("  FROM P_SYS_USER PS,\n");
		sb.append("       O_ORG      OO,\n");
		sb.append("       O_DEPT     OD\n");
		sb.append(" WHERE PS.ORG_NO = OO.ORG_NO\n");
		sb.append("   AND PS.DEPT_NO = OD.DEPT_NO(+)\n");
		// 权限写死 与c_cons 访问权限不同

		sb.append("AND EXISTS (SELECT 1\n");
		sb.append("                FROM O_ORG OORG\n");
		sb.append("               WHERE OORG.ORG_NO = PS.ORG_NO\n");
		sb
				.append("              CONNECT BY PRIOR OORG.ORG_NO = OORG.P_ORG_NO\n");
		sb.append("               START WITH OORG.ORG_NO = ?)");

		String sql = sb.toString();

		return super.pagingFind(sql, start, limit, new PSysUserRowMapper(),
				new Object[] { accessOrg });
	}

	/**
	 * 根据前台条件查询用户
	 */
	@Override
	public Page<PSysUserJdbc> findPage(long start, long limit, PSysUser query,
			String accessOrg) {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT PS.EMP_NO,\n");
		sb.append("       PS.STAFF_NO,\n");
		sb.append("       PS.ORG_NO,\n");
		sb.append("       PS.DEPT_NO,\n");
		sb.append("       PS.NAME,\n");
		sb.append("       PS.PWD,\n");
		sb.append("       PS.IP,\n");
		sb.append("       PS.MAC,\n");
		sb.append("       PS.ACCESS_LEVEL,\n");
		sb.append("       PS.CUR_STATUS_CODE,\n");
		sb.append("       PS.PWD_REMIND_TIME,\n");
		sb.append("       PS.LOCK_TIME,\n");
		sb.append("       PS.PLAN_UNLOCK_TIME,\n");
		sb.append("       PS.UNLOCK_TIME,\n");
		sb.append("       PS.LOCK_IP,\n");
		sb.append("       PS.AUTO_UNLOCK_FLAG,\n");
		sb.append("       PS.LOCK_REASON,\n");
		sb.append("       PS.UNLOCK_EMP_NO,\n");
		sb.append("       OO.ORG_NAME,\n");
		sb.append("       OD.NAME DEPNAME\n");
		sb.append("  FROM P_SYS_USER PS,\n");
		sb.append("       O_ORG      OO,\n");
		sb.append("       O_DEPT     OD\n");
		sb.append(" WHERE PS.ORG_NO = OO.ORG_NO\n");
		sb.append("   AND PS.DEPT_NO = OD.DEPT_NO(+)\n");
		// 权限写死 与c_cons 访问权限不同

		List<Object> list = new ArrayList<Object>();

		if (query.getStaffNo() != null && !query.getStaffNo().isEmpty()) {
			sb.append(" AND PS.STAFF_NO LIKE '%'||?||'%' \n");
			list.add(query.getStaffNo());
		}

		if (query.getName() != null && !query.getName().isEmpty()) {
			sb.append(" AND PS.NAME LIKE '%'||?||'%' \n");
			list.add(query.getName());
		}

		sb.append("AND EXISTS (SELECT 1\n");
		sb.append("                FROM O_ORG OORG\n");
		sb.append("               WHERE OORG.ORG_NO = PS.ORG_NO\n");
		sb
				.append("              CONNECT BY PRIOR OORG.ORG_NO = OORG.P_ORG_NO\n");
		sb.append("               START WITH OORG.ORG_NO = ?)");

		list.add(accessOrg);
		String sql = sb.toString();

		return super.pagingFind(sql, start, limit, new PSysUserRowMapper(),
				list.toArray());
	}

	@Override
	public boolean deletePSysUser(String staffNo) {
		List<String> sqls = new ArrayList<String>();
		List<List> args = new ArrayList<List>();

		List user = new ArrayList();
		user.add(staffNo);
		// 删除用户
		sqls.add(" delete from p_sys_user p where p.staff_no=?");
		args.add(user);

		// 删除权限
		sqls.add("DELETE FROM p_access_org WHERE staff_no=?");
		args.add(user);

		sqls.add("DELETE FROM p_access_tmnl_factory WHERE staff_no=?");
		args.add(user);

		sqls.add(" DELETE FROM p_user_role_rela WHERE staff_no=?");
		args.add(user);

		sqls.add("DELETE FROM p_access_cons_type WHERE staff_no=?");
		args.add(user);

		doTransaction(sqls, args, "删除操作员失败");
		return true;
	}

	@Override
	public String saveOrUpdate(PSysUser pSysUser, String orgs, String facs,
			String roles, String consTypes) {
		List<String> sqls = new ArrayList<String>();
		List<List> args = new ArrayList<List>();

		if (pSysUser.getEmpNo() == null || pSysUser.getEmpNo().isEmpty()) {
			List l = super.getJdbcTemplate().queryForList(
					"select 1 from p_sys_user p where p.staff_no= ?",
					new Object[] { pSysUser.getStaffNo() });
			if (l.size() > 0)
				return "工号" + pSysUser.getStaffNo() + "已存在";
			sqls.add(insertSql(pSysUser));
			args.add(insertArgs(pSysUser));

		} else {
			List l = super
					.getJdbcTemplate()
					.queryForList(
							"select 1 from p_sys_user p where p.emp_no<>? and  p.staff_no= ?",
							new Object[] { pSysUser.getEmpNo(),
									pSysUser.getStaffNo() });
			if (l.size() > 0)
				return "工号" + pSysUser.getStaffNo() + "已存在";
			sqls.add(updateSql(pSysUser));
			args.add(updateArgs(pSysUser));

		}

		// 删除当前用户的所有权限信息
		List staffNO = new ArrayList();
		staffNO.add(pSysUser.getStaffNo());

		sqls.add("DELETE FROM p_access_org WHERE staff_no=?");
		args.add(staffNO);

		sqls.add("DELETE FROM p_access_tmnl_factory WHERE staff_no=?");
		args.add(staffNO);

		sqls.add(" DELETE FROM p_user_role_rela WHERE staff_no=?");
		args.add(staffNO);

		sqls.add("DELETE FROM p_access_cons_type WHERE staff_no=?");
		args.add(staffNO);

		// 添加当前用户的所有权限信息
		addOrgPri(pSysUser, sqls, args, orgs);

		// 添加厂商权限
		String[] pfacs = facs.split(",");
		for (int i = 0; i < pfacs.length; i++) {
			if (!pfacs[i].isEmpty()) {
				sqls.add("insert into p_access_tmnl_factory values(?,?) ");
				List pfacList = new ArrayList();
				pfacList.add(pSysUser.getStaffNo());
				pfacList.add(pfacs[i]);
				args.add(pfacList);
			}
		}

		// 添加角色权限
		String[] proles = roles.split(",");
		for (int i = 0; i < proles.length; i++) {
			if (!proles[i].isEmpty()) {
				sqls
						.add("INSERT INTO p_user_role_rela VALUES (s_p_user_role_rela.nextval,?,?,NULL) ");
				List prolesList = new ArrayList();
				prolesList.add(proles[i]);
				prolesList.add(pSysUser.getStaffNo());

				args.add(prolesList);
			}
		}

		// 添加用户类型权限
		String[] pcons = consTypes.split(",");
		for (int i = 0; i < pcons.length; i++) {
			if (!pcons[i].isEmpty()) {
				sqls.add("INSERT INTO p_access_cons_type VALUES (?,?)");
				List pconsList = new ArrayList();
				pconsList.add(pSysUser.getStaffNo());
				pconsList.add(pcons[i]);
				args.add(pconsList);
			}
		}

		doTransaction(sqls, args, "保存操作员信息失败");
		logger.debug("--------------------------------");
		logger.debug("更新operators 信息成功");
		return "success";

	}

	/**
	 * 访问供电单位的保存
	 * 
	 * @param pSysUser
	 * @param sqls
	 * @param args
	 * @param orgs
	 */
	@SuppressWarnings("unchecked")
	private void addOrgPri(PSysUser pSysUser, List<String> sqls,
			List<List> args, String orgs) {
		String[] porg = orgs.split(",");

		// 添加最上级供电单位权限
		if (!porg[0].isEmpty()) {
			sqls
					.add("INSERT INTO p_access_org (SELECT ?,p_org_no,? FROM o_org WHERE org_no=?)");
			List pporg = new ArrayList();
			pporg.add(pSysUser.getStaffNo());
			pporg.add(pSysUser.getAccessLevel());

			pporg.add(porg[0]);
			args.add(pporg);
		}
		// 添加选中的权限和区县权限
		for (int i = 0; i < porg.length; i++) {
			if (!porg[i].isEmpty()) {
				sqls
						.add("INSERT INTO p_access_org ( SELECT ?,org_no,org_type FROM o_org WHERE org_no=? OR (p_org_no=? AND org_type='04') )");
				List porgList = new ArrayList();
				porgList.add(pSysUser.getStaffNo());
				porgList.add(porg[i]);
				porgList.add(porg[i]);
				args.add(porgList);
			}else{
				sqls.add("INSERT INTO P_ACCESS_ORG (STAFF_NO,ORG_NO,ORG_TYPE) VALUES(?,?,?)");
				List porgList = new ArrayList();
				porgList.add(pSysUser.getStaffNo());
				porgList.add(pSysUser.getOrgNo());
				porgList.add(pSysUser.getAccessLevel());
				args.add(porgList);
			}
		}

	}

	/**
	 * 
	 * @param pSysUser
	 * @return 插入PSysUser的sql
	 */
	private String insertSql(PSysUser pSysUser) {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO P_SYS_USER\n");
		sb.append("  (EMP_NO,\n");
		sb.append("   STAFF_NO,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   DEPT_NO,\n");
		sb.append("   NAME,\n");
		sb.append("   PWD,\n");
		sb.append("   IP,\n");
		sb.append("   MAC,\n");
		sb.append("   ACCESS_LEVEL,\n");
		sb.append("   CUR_STATUS_CODE,\n");
		sb.append("   PWD_REMIND_TIME,\n");
		sb.append("   LOCK_TIME,\n");
		sb.append("   PLAN_UNLOCK_TIME,\n");
		sb.append("   UNLOCK_TIME,\n");
		sb.append("   LOCK_IP,\n");
		sb.append("   AUTO_UNLOCK_FLAG,\n");
		sb.append("   LOCK_REASON,\n");
		sb.append("   UNLOCK_EMP_NO)\n");
		sb.append("VALUES\n");
		sb.append("  (S_P_SYS_USER.NEXTVAL, --emp_no,\n");
		sb.append("   ?, -- staff_no,\n");
		sb.append("   ?, --org_no,\n");
		sb.append("   ?, --dept_no,\n");
		sb.append("   ?, --name,\n");
		sb.append("   ?, -- pwd,\n");
		sb.append("   ?, --ip,\n");
		sb.append("   ?, -- mac,\n");
		sb.append("   ?, -- access_level,\n");
		sb.append("   ?, -- cur_status_code,\n");
		sb.append("   ?, -- pwd_remind_time,\n");
		sb.append("   ?, --lock_time,\n");
		sb.append("   ?, -- plan_unlock_time,\n");
		sb.append("   ?, -- unlock_time,\n");
		sb.append("   ?, -- lock_ip,\n");
		sb.append("   ?, -- auto_unlock_flag,\n");
		sb.append("   ?, -- lock_reason,\n");
		sb.append("   ? -- unlock_emp_no\n");
		sb.append("   )");

		String sql = sb.toString();

		return sql;
	}

	/**
	 * 设置insert PSysUser 的参数
	 * 
	 * @param pSysUser
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List insertArgs(PSysUser pSysUser) {
		List arg = new ArrayList();
		arg.add(pSysUser.getStaffNo());
		arg.add(pSysUser.getOrgNo());
		arg.add(pSysUser.getDeptNo());
		arg.add(pSysUser.getName());
		arg.add("202CB962AC59075B964B07152D234B70");
		arg.add(pSysUser.getIp());
		arg.add(pSysUser.getMac());
		arg.add(pSysUser.getAccessLevel());
		arg.add(pSysUser.getCurStatusCode());
		arg.add(pSysUser.getPwdRemindTime());
		arg.add(pSysUser.getLockTime());
		arg.add(pSysUser.getPlanUnlockTime());
		arg.add(pSysUser.getUnlockTime());
		arg.add(pSysUser.getLockIp());
		arg.add(pSysUser.getAutoUnlockFlag());
		arg.add(pSysUser.getLockReason());
		arg.add(pSysUser.getUnlockEmpNo());

		return arg;
	}

	/**
	 * 方法
	 * 
	 * @param pSysUser
	 * @return 更新PSysUser 的sql
	 */
	private String updateSql(PSysUser pSysUser) {

		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE P_SYS_USER\n");
		sb.append("    SET STAFF_NO         = ?,\n");
		sb.append("        ORG_NO           = ?,\n");
		sb.append("        DEPT_NO          = ?,\n");
		sb.append("        NAME             = ?,\n");
		// sb.append("        PWD              = ?,\n");
		sb.append("        IP               = ?,\n");
		sb.append("        MAC              = ?,\n");
		sb.append("        ACCESS_LEVEL     = ?,\n");
		sb.append("        CUR_STATUS_CODE  = ?,\n");
		sb.append("        PWD_REMIND_TIME  = ?,\n");
		sb.append("        LOCK_TIME        = ?,\n");
		sb.append("        PLAN_UNLOCK_TIME = ?,\n");
		sb.append("        UNLOCK_TIME      = ?,\n");
		sb.append("        LOCK_IP          = ?,\n");
		sb.append("        AUTO_UNLOCK_FLAG = ?,\n");
		sb.append("        LOCK_REASON      = ?,\n");
		sb.append("        UNLOCK_EMP_NO    = ?\n");
		sb.append("  WHERE EMP_NO = ?");

		String sql = sb.toString();
		return sql;
	}

	/**
	 * 方法 updateArgs
	 * 
	 * @param pSysUser
	 * @return update PSysUser 的参数列表
	 */
	@SuppressWarnings("unchecked")
	private List updateArgs(PSysUser pSysUser) {
		List arg = new ArrayList();
		arg.add(pSysUser.getStaffNo());
		arg.add(pSysUser.getOrgNo());
		arg.add(pSysUser.getDeptNo());
		arg.add(pSysUser.getName());
		// arg.add("202CB962AC59075B964B07152D234B70");
		arg.add(pSysUser.getIp());
		arg.add(pSysUser.getMac());
		arg.add(pSysUser.getAccessLevel());
		arg.add(pSysUser.getCurStatusCode());
		arg.add(pSysUser.getPwdRemindTime());
		arg.add(pSysUser.getLockTime());
		arg.add(pSysUser.getPlanUnlockTime());
		arg.add(pSysUser.getUnlockTime());
		arg.add(pSysUser.getLockIp());
		arg.add(pSysUser.getAutoUnlockFlag());
		arg.add(pSysUser.getLockReason());
		arg.add(pSysUser.getUnlockTime());
		arg.add(pSysUser.getEmpNo());

		return arg;
	}

	/**
	 * 内部类 PSysUserRowMapper
	 * 
	 * @author zhangzhw
	 * @describe 详细的PSysUserRowMapper
	 */
	class PSysUserRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			PSysUserJdbc psysuserjdbc = new PSysUserJdbc();
			try {
				psysuserjdbc.setEmpNo(rs.getString("EMP_NO"));
				psysuserjdbc.setStaffNo(rs.getString("STAFF_NO"));
				psysuserjdbc.setOrgNo(rs.getString("ORG_NO"));
				psysuserjdbc.setDeptNo(rs.getString("DEPT_NO"));
				psysuserjdbc.setName(rs.getString("NAME"));
				psysuserjdbc.setPwd(rs.getString("PWD"));
				psysuserjdbc.setIp(rs.getString("IP"));
				psysuserjdbc.setMac(rs.getString("MAC"));
				psysuserjdbc.setAccessLevel(rs.getString("ACCESS_LEVEL"));
				psysuserjdbc.setCurStatusCode(rs.getString("CUR_STATUS_CODE"));
				psysuserjdbc.setPwdRemindTime(rs
						.getTimestamp("PWD_REMIND_TIME"));
				psysuserjdbc.setLockTime(rs.getTimestamp("LOCK_TIME"));
				psysuserjdbc.setPlanUnlockTime(rs
						.getTimestamp("PLAN_UNLOCK_TIME"));
				psysuserjdbc.setUnlockTime(rs.getTimestamp("UNLOCK_TIME"));
				psysuserjdbc.setLockIp(rs.getString("LOCK_IP"));
				psysuserjdbc
						.setAutoUnlockFlag(rs.getString("AUTO_UNLOCK_FLAG"));
				psysuserjdbc.setLockReason(rs.getString("LOCK_REASON"));
				psysuserjdbc.setUnlockEmpNo(rs.getString("UNLOCK_EMP_NO"));
				psysuserjdbc.setOrgName(rs.getString("ORG_NAME"));
				psysuserjdbc.setDepname(rs.getString("DEPNAME"));
				return psysuserjdbc;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
