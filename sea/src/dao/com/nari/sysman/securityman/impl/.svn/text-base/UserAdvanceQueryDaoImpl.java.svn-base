package com.nari.sysman.securityman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.customer.CConsRtmnl;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.securityman.IUserAdvanceQueryDao;

/**
 * DAO类 UserAdvanceQueryDaoImpl
 * 
 * @author zhangzhw
 * @describe 用户列表高级查询 DAO
 */
public class UserAdvanceQueryDaoImpl extends JdbcBaseDAOImpl implements
		IUserAdvanceQueryDao {

	public Page<CConsRtmnl> findCustmerByCon(String con, long start, int limit,
			PSysUser username) {

		// 未添加权限控制
		// StringBuffer sb = new StringBuffer();
		// sb
		// .append("SELECT cons_id,cons_no,cons_name,cons_type,terminal_id,tmnl_asset_no,\n");
		// sb.append("       send_up_mode,terminal_type_code,cp_no,cp_name\n");
		// sb.append(" FROM vw_userListAdvquery WHERE 1=1");
		// sb.append(con);
		// String sql = sb.toString();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT cons.cons_id, cons.cons_no,\n");
		sb.append("       cons.cons_name,  cons.cons_type ,\n");
		sb.append("       tmnl.terminal_id,tmnl.tmnl_asset_no,\n");
		sb.append("       tmnl.terminal_addr,\n");
		sb.append("       tmnl.status_code,\n");
		sb.append("       tmnl.send_up_mode,tmnl.protocol_code,\n");
		sb.append("       tmnl.terminal_type_code, cp.cp_no,\n");
		sb.append("       cp.name cp_name\n");
		sb.append("  FROM c_cons cons, vw_tmnl_run tmnl, r_cp cp\n");
		sb.append(" WHERE cons.cons_no = tmnl.cons_no\n");
		sb.append("   AND tmnl.cp_no = cp.cp_no");

		sb.append(con);

		sb.append(SysPrivilige.addPri(username, "cons", "tmnl", 7));

		String sql = sb.toString();
		String user = username.getStaffNo();

		return super.pagingFind(sql, start, limit, new CConsMapper(),
				new Object[] { user, user, user });
	}

	/**
	 * 查询当前用户拥有权限的供电所
	 */
	/** TODO:修改SQL语句根据用户名取有权限的供电所 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<OOrg> findBureauList(String username) {

		StringBuffer sb = new StringBuffer()
				.append("SELECT org_no, org_name,org_type,area_code FROM O_ORG WHERE ORG_TYPE = '06'");

		return super.getJdbcTemplate().query(sb.toString(), new OrgMapper());
	}

	/**
	 * 查询当前用户拥有权限的供电单位
	 */
	/** TODO:修改SQL语句根据用户名取有权限的供电单位 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<OOrg> findOrgNoList(String username) {

		StringBuffer sb = new StringBuffer()
				.append("SELECT org_no, org_name,org_type,area_code FROM O_ORG WHERE ORG_TYPE = '06'");

		return super.getJdbcTemplate().query(sb.toString(), new OrgMapper());
	}

	/**
	 * action 方法 　 subIdList
	 * 
	 * @return 字典列表　变电站
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List subIdList(PSysUser pSysUser) throws Exception {

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT subs_id value,subs_name disp FROM g_subs subs  where 1=1");
		sb.append("AND EXISTS (SELECT 1\n"
				+ "        FROM P_ACCESS_ORG P_A, P_SYS_USER P_U\n"
				+ "       WHERE P_A.STAFF_NO = P_U.STAFF_NO\n"
				+ "         AND P_U.ACCESS_LEVEL = P_A.ORG_TYPE\n"
				+ "         AND P_A.ORG_NO =  subs.ORG_NO\n"
				+ "         AND P_A.STAFF_NO ='" + pSysUser.getStaffNo()
				+ "' )");
		String sql = sb.toString();

		return super.getJdbcTemplate().queryForList(sql);

	}

	/**
	 * action 方法 　 lineIdList
	 * 
	 * @return 字典列表　线路
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List lineIdList(PSysUser pSysUser) throws Exception {
		// TODO: 添加权限
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT line_id value,line_name disp from g_line where 1=1 ");
		sb.append("AND EXISTS (SELECT 1\n"
				+ "        FROM P_ACCESS_ORG P_A, P_SYS_USER P_U\n"
				+ "       WHERE P_A.STAFF_NO = P_U.STAFF_NO\n"
				+ "         AND P_U.ACCESS_LEVEL = P_A.ORG_TYPE\n"
				+ "         AND P_A.ORG_NO =  g_line.ORG_NO\n"
				+ "         AND P_A.STAFF_NO ='" + pSysUser.getStaffNo()
				+ "' )");
		String sql = sb.toString();

		return super.getJdbcTemplate().queryForList(sql);

	}

	/**
	 * action 方法 　 tradeCodeList
	 * 
	 * @return 字典列表　行业
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List tradeCodeList() throws Exception {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT trade_no value,trade_name FROM vw_trade");

		String sql = sb.toString();

		return super.getJdbcTemplate().queryForList(sql);

	}

	/**
	 * action 方法 　 consTypeList
	 * 
	 * @return 字典列表　用户类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List consTypeList() throws Exception {

		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb
				.append("SELECT dict_no value,dict_name disp FROM b_sys_dictionary WHERE dict_catalog='CONS_TYPE'");

		String sql = sb.toString();

		return super.getJdbcTemplate().queryForList(sql);

	}

	/**
	 * action 方法 　 elecTypeList
	 * 
	 * @return 字典列表　用电类别
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List elecTypeList() throws Exception {

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT t.ELEC_TYPE_CODE value, t.ELEC_TYPE disp from vw_elec_type_code t");

		String sql = sb.toString();

		return super.getJdbcTemplate().queryForList(sql);

	}

	/**
	 * action 方法 　 capGradeList
	 * 
	 * @return 字典列表　用电容量等级
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List capGradeList() throws Exception {

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT dict_no value,dict_name disp FROM b_sys_dictionary WHERE dict_catalog='CAP_GRADE'");

		String sql = sb.toString();

		return super.getJdbcTemplate().queryForList(sql);

	}

	/**
	 * action 方法 　 shiftNoList
	 * 
	 * @return 字典列表　班次
	 * @throws Exception
	 */
	public List shiftNoList() throws Exception {

		StringBuffer sb = new StringBuffer();
		sb.append("select t.shift_No value,t.shift_name from vw_shift t");

		String sql = sb.toString();

		return super.getJdbcTemplate().queryForList(sql);
		// return null;

	}

	/**
	 * action 方法 　 lodeAttrList
	 * 
	 * @return 字典列表　负荷性质
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List lodeAttrList() throws Exception {

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT t.lode_Attr_Code VALUE,t.lode_Attr_name from vw_lode_attr t");

		String sql = sb.toString();

		return super.getJdbcTemplate().queryForList(sql);
		// return null;

	}

	/**
	 * action 方法 　 tmnlTypeList
	 * 
	 * @return 字典列表　终端类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List tmnlTypeList() throws Exception {

		StringBuffer sb = new StringBuffer();
		sb
				.append("select t.TMNL_TYPE_CODE value,t.TMNL_TYPE disp from vw_tmnl_type_code t");

		String sql = sb.toString();

		return super.getJdbcTemplate().queryForList(sql);

	}

	/**
	 * action 方法 　 tmnlTypeList
	 * 
	 * @return 字典列表　采集方式
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List collTypeList() throws Exception {

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT t.COMM_MODE_CODE value,t.COMM_MODE disp from vw_comm_mode t");

		String sql = sb.toString();

		return super.getJdbcTemplate().queryForList(sql);

	}

	/**
	 * 内部类　CConsMapper
	 * 
	 * @author zhangzhw
	 * @describe CCons　RowMapper实现
	 */
	class CConsMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CConsRtmnl cons = new CConsRtmnl();
			try {
				cons.setConsId(rs.getLong("cons_id"));
				cons.setConsNo(rs.getString("cons_no"));
				cons.setConsName(rs.getString("cons_name"));
				cons.setConsType(rs.getString("cons_type"));
				cons.setTerminalId(rs.getString("terminal_id"));
				cons.setTmnlAssetNo(rs.getString("tmnl_asset_no"));
				cons.setSendUpMode(rs.getBoolean("send_up_mode"));
				cons.setTerminalTypeCode(rs.getString("terminal_type_code"));
				cons.setCpNo(rs.getString("cp_no"));
				cons.setCpName(rs.getString("cp_name"));
				cons.setTerminalAddr(rs.getString("terminal_addr"));

				return cons;
			} catch (Exception e) {
				UserAdvanceQueryDaoImpl.this.logger
						.error("取 CConsMapper 时出现错误！ ");
				return null;
			}
		}
	}

	/**
	 * 内部类 OrgMapper
	 * 
	 * @author zhangzhw
	 * @describe Org RowMapper 实现
	 */
	class OrgMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OOrg oOrg = new OOrg();
			try {
				oOrg.setOrgNo(rs.getString("org_no"));
				oOrg.setOrgName(rs.getString("org_name"));
				oOrg.setOrgType(rs.getString("org_type"));
				oOrg.setAreaCode(rs.getString("area_code"));
				return oOrg;
			} catch (Exception e) {
				// throw new DBAccessException("OrgMapper出现错误！");
				UserAdvanceQueryDaoImpl.this.logger
						.error("取 OrgMapper 时出现错误！ ");
				return null;
			}
		}
	}

}
