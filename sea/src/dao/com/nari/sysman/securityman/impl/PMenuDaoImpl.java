package com.nari.sysman.securityman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PMenu;
import com.nari.privilige.PSysUser;
import com.nari.sysman.securityman.IPMenuDao;
import com.nari.util.exception.DBAccessException;

public class PMenuDaoImpl extends JdbcBaseDAOImpl implements IPMenuDao {

	@SuppressWarnings("unchecked")
	public List<PMenu> findByUpid(String PMenuNo, PSysUser pSysUser) {

		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb
				.append("SELECT DISTINCT M.MENU_NO, M.TITLE, M.P_MENU_NO, M.MENU_FOLDER_FLAG, M.HANDLE_SORT, M.ICON_NAME, M.URL, M.PARA, M.SORT_NO, M.RIGHT_MENU_FLAG, M.SYS_NO, M.REMARK, M.IS_OPEN_TREE\n");
		sb
				.append("  FROM P_MENU M, P_ROLE_PRIV_RELA RP, P_USER_ROLE_RELA UR\n");
		sb.append(" WHERE M.MENU_NO = RP.MENU_NO\n");
		sb.append("   AND RP.ROLE_ID = UR.ROLE_ID\n");
		sb.append("   AND M.IS_VALID = '1'\n");
		sb.append("   AND M.P_MENU_NO = ?\n");
		sb.append("   AND UR.STAFF_NO = ?\n");
		sb.append(" ORDER BY SORT_NO");

		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql,
				new Object[] { PMenuNo, pSysUser.getStaffNo() },
				new PMenuRowMapper());
	}

	/**
	 * 取得所有菜单
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PMenu> findAllMenu() {

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT MENU_NO, TITLE, P_MENU_NO, MENU_FOLDER_FLAG, HANDLE_SORT, ICON_NAME, URL, PARA, SORT_NO, RIGHT_MENU_FLAG, SYS_NO, REMARK, IS_OPEN_TREE\n");
		sb.append("  FROM P_MENU\n");
		sb.append(" ORDER BY P_MENU_NO ASC,MENU_NO ASC");

		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql, new PMenuRowMapper());
	}

	/**
	 * 取得有权限的菜单
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PMenu> findByRole(String roleId) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT DISTINCT P.MENU_NO, P.TITLE, P.P_MENU_NO, P.MENU_FOLDER_FLAG, P.HANDLE_SORT, P.ICON_NAME, P.URL, P.PARA, P.SORT_NO, P.RIGHT_MENU_FLAG, P.SYS_NO, P.REMARK, P.IS_OPEN_TREE\n");
		sb.append("  FROM P_MENU P, P_ROLE_PRIV_RELA PR\n");
		sb.append(" WHERE P.MENU_NO = PR.MENU_NO\n");
		sb.append("   AND PR.ROLE_ID = ?\n");
		sb.append(" ORDER BY P.P_MENU_NO ASC,MENU_NO");

		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql, new Object[] { roleId },
				new PMenuRowMapper());

	}

	/**
	 * 内部类　PMenuRowMapper
	 * 
	 * @author zhangzhw
	 * @PMenu 的RowMapper映射
	 */
	class PMenuRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			PMenu pmenu = new PMenu();
			try {
				pmenu.setMenuNo(rs.getString("MENU_NO"));
				pmenu.setTitle(rs.getString("TITLE"));
				pmenu.setPMenuNo(rs.getString("P_MENU_NO"));
				pmenu.setMenuFolderFlag(rs.getString("MENU_FOLDER_FLAG"));
				pmenu.setHandleSort(rs.getString("HANDLE_SORT"));
				pmenu.setIconName(rs.getString("ICON_NAME"));
				pmenu.setUrl(rs.getString("URL"));
				pmenu.setPara(rs.getString("PARA"));
				pmenu.setSortNo(rs.getInt("SORT_NO"));
				pmenu.setRightMenuFlag(rs.getByte("RIGHT_MENU_FLAG"));
				pmenu.setSysNo(rs.getByte("SYS_NO"));
				pmenu.setRemark(rs.getString("REMARK"));
				pmenu.setIsOpenTree(rs.getByte("IS_OPEN_TREE") == 1);
				return pmenu;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
