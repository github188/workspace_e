package com.nari.sysman.securityman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.basicdata.BTrade;
import com.nari.customer.CConsRtmnl;
import com.nari.grid.GLine;
import com.nari.grid.GSubs;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.alertevent.CpTypeCode;
import com.nari.sysman.securityman.IGenralTreeDao;
import com.nari.sysman.securityman.UserGridBean;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.terminalparam.RUserGroup;
import com.nari.terminalparam.RUserGroupDetailId;
import com.nari.terminalparam.TTmnlGroup;
import com.nari.terminalparam.TTmnlGroupDetailId;

/**
 * 类 GenralTreeDaoImpl
 * 
 * @author zhangzhw
 * @describe 树节点处理DAO
 */
public class GenralTreeDaoImpl extends JdbcBaseDAOImpl implements
		IGenralTreeDao {
	// protected Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 通过用户名和上级单位名称查询下级单位分页信息
	 */
	@Override
	public Page<OOrg> findOrgByUpNode(PSysUser user, String unitType,
			String upnode, long start, int limit) {
		// String sql =
		// "SELECT org.org_no, org.org_name, org.org_type, org.area_code\n"
		// + "FROM p_access_org ao, o_org org\n"
		// + "WHERE ao.org_no = org.org_no\n"
		// + "      AND ao.staff_no = ?\n" + "ORDER BY org.sort_no";
		// 暂时使用此sql查询，权限使用以上sql
		String sql = "SELECT org.org_no, org.org_name, org.org_type, org.area_code "
				+ "FROM  o_org org where org.p_org_no=? ";

		sql += SysPrivilige.addOrgPriOrg(user, unitType, "org");

		sql += " order by org_no asc ";

		return super.pagingFind(sql, start, limit, new OrgMapper(), upnode);
	}

	/**
	 * @return 通过用户名查询其拥有权限的供电单位
	 * @describe 单位权限按照 p_access_org 表来处理
	 */
	@Override
	public Page<OOrg> findOrgByUsername(PSysUser username, long start, int limit) {
		String sql = "SELECT org.org_no, org.org_name, org.org_type, org.area_code "
				+ "FROM  o_org org where 1=1 ";
		// 添加纯单位权限
		sql += SysPrivilige.addOrgPriOrg(username, "00", "org");
		sql += " order by org_no asc ";

		return super.pagingFind(sql, start, limit, new OrgMapper());
	}

	/**
	 * 通过单位编码查询其下属变电站
	 */
	@Override
	public Page<GSubs> findSubByOrg(String orgNo, long start, long limit) {
		String sql = "SELECT sub.subs_id, sub.subs_no, sub.subs_name, vc.volt as volt_code,sub.org_no,\n "
			+ "sub.subs_addr,sub.mt_num, sub.mt_cap, sub.chg_date ,vs.run_status_name as run_status_code\n"
			+ "  FROM g_subs sub, vw_volt_code vc, vw_run_status_code vs\n"
			+ " WHERE sub.volt_code = vc.volt_code and sub.org_no = ? and sub.run_status_code = vs.run_status_code\n"
			+ "  ORDER BY VC.VOLT_CODE DESC";


		return super.pagingFind(sql, start, limit, new GSubMapper(), orgNo);
	}

	/**
	 * 通过变电站查询其下属线路
	 */
	@Override
	public Page<GLine> findLineBySub(String subNo, long start, int limit) {
		/*
		 * String sql =
		 * "SELECT line.line_id, line.line_no, line.line_name, line.volt_code\n"
		 * + "  FROM g_subs_line_rela rela, g_line line\n" +
		 * " WHERE rela.line_id = line.line_id\n" + "   AND rela.sub_id = ?";
		 */
		// String sql =
		// " select line_id,line_no,line_name,volt_code from g_line where ";
		StringBuffer sb = new StringBuffer();
		sb
				.append(
						"SELECT LINE.LINE_ID, LINE.LINE_NO, LINE.LINE_NAME, vc.volt as volt_code\n")
				.append("  FROM G_SUBS_LINE_RELA RELA, G_LINE LINE, vw_volt_code vc \n").append(
						" WHERE RELA.LINE_ID = LINE.LINE_ID AND LINE.volt_code = vc.volt_code\n").append(
						"   AND RELA.SUBS_ID = ? ORDER BY VC.VOLT_CODE DESC");

		// return super.pagingFind(sql, start, limit, new GLineMapper(), subNo);
		return super.pagingFind(sb.toString(), start, limit, new GLineMapper(),
				new Object[] { subNo });
	}

	/**
	 * 通过线路查询其下属台区和专变用户
	 */
	@Override
	public Page<CConsRtmnl> findCustmerByLine(String lineId, long start,
			int limit, PSysUser pSysUser) {
		String sql = "SELECT cons.cons_id,\n"
				+ "cons.cust_id,       cons.cons_no, cons.tg_id,\n"
				+ "       cons.cons_name, \n cons.cons_type ,cons.cons_sort_code, \n"
				+ "       tmnl.terminal_id,\n" + "       tmnl.tmnl_asset_no,\n"
				+ "       tmnl.terminal_addr,\n"
				+ "       tmnl.status_code, tmnl.cis_asset_no,\n"
				+ "       tmnl.send_up_mode,tmnl.protocol_code,\n"
				+ "       tmnl.terminal_type_code,\n" + "       cp.cp_no,\n"
				+ "       cp.name cp_name,\n "
				+ "       '' asset_no \n"
				+ "  FROM c_cons cons, VW_TMNL_RUN tmnl, r_cp cp\n"
				+ " WHERE cons.cons_no = tmnl.cons_no\n"
				+ "   AND tmnl.cp_no = cp.cp_no\n" + "   AND cons.line_id = ? \n";
		sql += SysPrivilige.addPri(pSysUser, "cons", "tmnl", 6);
		sql += " order by cons.cons_name ";
		String staffNo = pSysUser.getStaffNo();
		// '334010201001'
		return super.pagingFind(sql, start, limit, new CConsMapper(),
				new Object[] { lineId, staffNo, staffNo });
	}

	/**
	 * 通过行业查询用户
	 */
	@Override
	public Page<CConsRtmnl> findCustmerByTrade(String tradeNo, long start,
			int limit, PSysUser pSysUser) {
		String sql = "SELECT cons.cons_id,\n"
				+ "cons.cust_id,       cons.cons_no, cons.tg_id,\n"
				+ "       cons.cons_name, \n cons.cons_type ,cons.cons_sort_code,\n"
				+ "       tmnl.terminal_id,\n" + "       tmnl.tmnl_asset_no,\n"
				+ "       tmnl.terminal_addr,\n"
				+ "       tmnl.status_code, tmnl.cis_asset_no,\n"
				+ "       tmnl.send_up_mode,tmnl.protocol_code,\n"
				+ "       tmnl.terminal_type_code,\n" + "       cp.cp_no,\n"
				+ "       cp.name cp_name,\n "
				+ "       '' asset_no \n"
				+ "  FROM c_cons cons, VW_TMNL_RUN tmnl, r_cp cp\n"
				+ " WHERE cons.cons_no = tmnl.cons_no\n"
				+ "   AND tmnl.cp_no = cp.cp_no\n"
				+ "   AND cons.trade_code = ?";
		sql += SysPrivilige.addPri(pSysUser, "cons", "tmnl", 6);
		sql += " order by cons.cons_name ";
		String staffNo = pSysUser.getStaffNo();
		// '334010201001'
		return super.pagingFind(sql, start, limit, new CConsMapper(),
				new Object[] { tradeNo, staffNo, staffNo });
	}

	/**
	 * 通过供电所查询到下属台区和专变用户
	 */
	public Page<CConsRtmnl> findCustmerByOrg(String orgNo, long start,
			int limit, PSysUser pSysUser) {
		String sql = "SELECT cons.cons_id,cons.cust_id, cons.cons_no, cons.tg_id,\n"
				+ " cons.cons_name, cons.cons_type ,cons.cons_sort_code,\n"
				+ "       tmnl.terminal_id,tmnl.terminal_addr, tmnl.tmnl_asset_no,tmnl.protocol_code,\n"
				+ "       tmnl.status_code, tmnl.send_up_mode,tmnl.cis_asset_no,\n"
				+ "       tmnl.terminal_type_code,\n"
				+ "       cp.cp_no, cp.name AS cp_name,\n"
				+ "       '' asset_no \n"
				+ "FROM c_cons cons, VW_TMNL_RUN tmnl, r_cp cp\n"
				+ "WHERE cons.cons_no = tmnl.cons_no\n"
				+ "      AND tmnl.cp_no = cp.cp_no\n"
				+ "      AND cons.org_no = ? \n";

		sql += SysPrivilige.addPri(pSysUser, "cons", "tmnl", 6);
		sql += " order by cons.cons_name ";
		String staffNo = pSysUser.getStaffNo();

		return super.pagingFind(sql, start, limit, new CConsMapper(),
				new Object[] { orgNo, staffNo, staffNo });

	}

	/**
	 * findCustmerBycitizen
	 * 
	 * @param ugb
	 * @param start
	 * @param limit
	 * @param pSysUser
	 * @return 查询居民户信息
	 */
	public Page<CConsRtmnl> findCustmerBycitizen(UserGridBean ugb, long start,
			int limit, PSysUser pSysUser) {
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		sb.append("SELECT CONS.CONS_ID,\n");
		sb.append("                  CONS.CUST_ID,\n");
		sb.append("                  CONS.CONS_NO,\n");
		sb.append("                  CONS.TG_ID,\n");
		sb.append("                  CONS.CONS_NAME,\n");
		sb.append("                  CONS.CONS_TYPE,\n");
		sb.append("                  cons.cons_sort_code, \n");
		if(!nullString(ugb.getMeterAssetNo())){
			sb.append("                  METER.ASSET_NO, \n");
		}else{
			sb.append("                  '' ASSET_NO, \n");
		}
		sb.append("                  NULL           TERMINAL_ID,\n");
		sb.append("                  NULL           TERMINAL_ADDR,\n");
		sb.append("                  NULL           TMNL_ASSET_NO,\n");
		sb.append("                  NULL           STATUS_CODE,\n");
		sb.append("                  NULL           CIS_ASSET_NO,\n");
		sb.append("                  NULL           SEND_UP_MODE,\n");
		sb.append("                  NULL           PROTOCOL_CODE,\n");
		sb.append("                  NULL           TERMINAL_TYPE_CODE,\n");
		sb.append("                  NULL           CP_NO,\n");
		sb.append("                  NULL           CP_NAME,\n");
		sb.append("       ORG.ORG_NO,\n");
		sb.append("       ORG.ORG_NAME\n");
		sb.append("             FROM P_ACCESS_ORG PAG,\n");
		sb.append("					 P_ACCESS_CONS_TYPE PACT,\n");
		if(!nullString(ugb.getTgNo()) || !nullString(ugb.getTgName())){
			sb.append("		G_TG GTG,\n");
		}
		if(!nullString(ugb.getMeterAssetNo())){
			sb.append("		C_METER METER,\n");
		}
		
		sb.append("		C_CONS CONS,\n");
		sb.append("       O_ORG        ORG\n");
		sb.append("            WHERE CONS.CONS_TYPE = '5' \n");
		sb.append("		         AND CONS.AREA_NO = PAG.ORG_NO \n");
		sb.append("		         AND CONS.CONS_TYPE = PACT.CONS_TYPE \n");
		sb.append("              AND CONS.ORG_NO = ORG.ORG_NO \n");
		sb.append("		         AND PAG.STAFF_NO = ? \n");
		sb.append("		         AND PACT.STAFF_NO = ? \n");
		list.add(pSysUser.getStaffNo());
		list.add(pSysUser.getStaffNo());
		if(!nullString(ugb.getTgNo()) || !nullString(ugb.getTgName())){
			sb.append("		AND CONS.TG_ID = GTG.TG_ID \n");
		}
		if(!nullString(ugb.getTgNo())){
			sb.append("		AND GTG.TG_NO LIKE ? \n");
			list.add("%"+ugb.getTgNo()+"%");
		}
		if(!nullString(ugb.getTgName())){
			sb.append("		AND GTG.TG_NAME LIKE ? \n");
			list.add("%"+ugb.getTgName()+"%");
		}
		if(!nullString(ugb.getMrSectNo())){
			sb.append("		AND CONS.MR_SECT_NO LIKE ? \n");
			list.add("%"+ugb.getMrSectNo()+"%");
		}
		if(!nullString(ugb.getMeterAssetNo())){
			sb.append("		AND METER.CONS_NO = CONS.CONS_NO \n");
			sb.append("		AND METER.ASSET_NO LIKE ? \n");
			list.add("%"+ugb.getMeterAssetNo()+"%");
		}
		if(!nullString(ugb.getConsName())){
			sb.append("		AND CONS.CONS_NAME LIKE ?\n");
			list.add("%"+ugb.getConsName()+"%");
		}
		if(!nullString(ugb.getConsNo())){
			sb.append("		AND CONS.CONS_NO LIKE ?\n");
			list.add("%"+ugb.getConsNo()+"%");
		}
		
		return super.pagingFind(sb.toString(), start, limit, new CsConsMapper(),
				list.toArray());

	}

	/**
	 * 通过条件查询台区用户
	 */
	public Page<CConsRtmnl> findCustmerByTg(UserGridBean ugb, long start,
			int limit, PSysUser pSysUser) {
		List<Object> list = new ArrayList<Object>();

		// 　原终端地址　terminal_id　改为　terminal_addr　以别名方式修改

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CONS.CONS_ID,\n");
		sb.append("       CUST_ID,\n");
		sb.append("       CONS.CONS_NO,\n");
		sb.append("       CONS.TG_ID,\n");
		sb.append("       CONS.CONS_NAME,\n");
		sb.append("       CONS.CONS_TYPE,\n");
		sb.append("       cons.cons_sort_code,\n");
		sb.append("       TMNL.TERMINAL_ID,\n");
		sb.append("       TMNL.TERMINAL_ADDR,\n");
		sb.append("       TMNL.TMNL_ASSET_NO,\n");
		sb.append("       TMNL.STATUS_CODE,\n");
		if(!nullString(ugb.getMeterAssetNo())){
			sb.append("                  METER.ASSET_NO, \n");
		}else{
			sb.append("                  '' ASSET_NO, \n");
		}
		sb.append("       TMNL.SEND_UP_MODE,\n");
		sb.append("       TMNL.CIS_ASSET_NO,\n");
		sb.append("       TMNL.TERMINAL_TYPE_CODE,\n");
		sb.append("       TMNL.PROTOCOL_CODE,\n");
		sb.append("       CP.CP_NO,\n");
		sb.append("       CP.NAME                 AS CP_NAME,\n");
		sb.append("       ORG.ORG_NO,\n");
		sb.append("       ORG.ORG_NAME \n");
		sb.append("  FROM C_CONS      CONS,\n");
		sb.append("       VW_TMNL_RUN TMNL,\n");
		sb.append("       R_CP        CP,\n");
		if(!nullString(ugb.getMeterAssetNo())){
			sb.append("       C_METER        METER,\n");
		}
		sb.append("       G_TG        GTG, \n");
		sb.append("       O_ORG        ORG\n");
		sb.append(" WHERE CONS.CONS_NO = TMNL.CONS_NO\n");
		sb.append("   AND TMNL.CP_NO = CP.CP_NO \n ");
		sb.append("   AND CONS.TG_ID=GTG.TG_ID \n");
		sb.append("   AND CONS.CONS_TYPE='2' \n");
		sb.append("   AND CONS.ORG_NO = ORG.ORG_NO \n");

		sb.append(SysPrivilige.addPri(pSysUser, "cons", "tmnl", 7));
		for (int i = 0; i < 3; i++) {
			list.add(pSysUser.getStaffNo());
		}
		
		if(!nullString(ugb.getCisAssetNo())){
			sb.append("   AND TMNL.CIS_ASSET_NO LIKE ? \n");
			list.add("%"+ugb.getCisAssetNo()+"%");
		}
		if(!nullString(ugb.getMeterAssetNo())){
			sb.append("   AND CONS.CONS_NO = METER.CONS_NO \n ");
			sb.append("   AND METER.ASSET_NO LIKE ? \n ");
			list.add("%"+ugb.getMeterAssetNo()+"%");
		}
		if (!nullString(ugb.getTgId())) {
			sb.append(" AND gtg.tg_no like '%'||?||'%' \n");
			list.add(ugb.getTgId());
		}
		if (!nullString(ugb.getTgName())) {
			sb.append(" AND gtg.tg_name like '%'||?||'%' \n");
			list.add(ugb.getTgName());
		}
		if (!nullString(ugb.getTerminalId())) {
			sb.append(" AND tmnl.terminal_addr like '%'||?||'%' \n");
			list.add(ugb.getTerminalId());
		}
		if (!nullString(ugb.getTmnlAssetNo())) {
			sb.append(" AND tmnl.tmnl_asset_no like '%'||?||'%' \n");
			list.add(ugb.getTmnlAssetNo());
		}

		if (!nullString(ugb.getPotocolCode())) {
			sb.append(" AND tmnl.protocol_code =? \n");
			list.add(ugb.getPotocolCode());
		}
		
		if(!nullString(ugb.getCpName())){
			sb.append(" AND CP.NAME LIKE ? ");
			list.add("%"+ugb.getCpName()+"%");
		}

//		if (!nullString(ugb.getMeterAssetNo())) {
//			sb.append("  AND EXISTS (SELECT 1 FROM C_METER M\n");
//			sb.append(" WHERE M.CONS_NO=CONS.CONS_NO AND M.ASSET_NO=? )\n");
//			list.add(ugb.getMeterAssetNo());
//		}

		sb.append("  ORDER BY  CONS_NAME  ");
		
		logger.debug("\n台区查询：" + sb.toString());
		return super.pagingFind(sb.toString(), start, limit, new CsConsMapper(),
				list.toArray());

	}

	/**
	 * 通过条件查询用户
	 */
	public Page<CConsRtmnl> findCustmerByOrg(UserGridBean ugb, long start,
			int limit, PSysUser pSysUser) {
		List<Object> list = new ArrayList<Object>();

		// 　原终端地址　terminal_id　改为　terminal_addr　以别名方式修改

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CONS.CONS_ID,\n");
		sb.append("       CUST_ID,\n");
		sb.append("       CONS.CONS_NO,\n");
		sb.append("       CONS.TG_ID,\n");
		sb.append("       CONS.CONS_NAME,\n");
		sb.append("       CONS.CONS_TYPE,\n");
		sb.append("       cons.cons_sort_code,\n");
		sb.append("       TMNL.TERMINAL_ADDR,\n");
		sb.append("       TMNL.TERMINAL_ID,\n");
		sb.append("       TMNL.TMNL_ASSET_NO,\n");
		sb.append("       TMNL.STATUS_CODE,\n");
		sb.append("       TMNL.SEND_UP_MODE,\n");
		sb.append("       TMNL.CIS_ASSET_NO,\n");
		sb.append("       TMNL.TERMINAL_TYPE_CODE,\n");
		sb.append("       TMNL.PROTOCOL_CODE,\n");
		if (!nullString(ugb.getMeterAssetNo())) {
			sb.append("       M.ASSET_NO,\n");
		}else{
			sb.append("       '' ASSET_NO,\n");
		}
		sb.append("       CP.CP_NO,\n");
		sb.append("       CP.NAME                 AS CP_NAME,\n");
		sb.append("       ORG.ORG_NO                 AS ORG_NO,\n");
		sb.append("       ORG.ORG_NAME                 AS ORG_NAME\n");
		sb.append("  FROM C_CONS      CONS,\n");
		sb.append("       VW_TMNL_RUN TMNL,\n");
		if (!nullString(ugb.getMeterAssetNo())) {
			sb.append("       C_METER M,\n");
		}
		sb.append("       R_CP        CP,\n");
		sb.append("       O_ORG        ORG\n");
		sb.append(" WHERE CONS.CONS_NO = TMNL.CONS_NO\n");
		sb.append("   AND TMNL.CP_NO = CP.CP_NO \n ");
		sb.append("   AND CONS.CONS_TYPE='1' \n");
		sb.append("   AND CONS.ORG_NO = ORG.ORG_NO \n");
		
		sb.append(SysPrivilige.addPri(pSysUser, "cons", "tmnl", 7));
		for (int i = 0; i < 3; i++) {
			list.add(pSysUser.getStaffNo());
		}

		if (!nullString(ugb.getConsNo())) {
			sb.append(" AND cons.cons_no like '%'||?||'%' ");
			list.add(ugb.getConsNo());
		}
		if (!nullString(ugb.getConsName())) {
			sb.append(" AND cons.cons_name like '%'||?||'%' ");
			list.add(ugb.getConsName());
		}
		if (!nullString(ugb.getTerminalAddr())) {
			sb.append(" AND tmnl.terminal_addr like '%'||?||'%' ");
			list.add(ugb.getTerminalAddr());
		}
		if (!nullString(ugb.getCisAssetNo())) {
			sb.append(" AND tmnl.cis_asset_no like '%'||?||'%' ");
			list.add(ugb.getCisAssetNo());
		}
		if (!nullString(ugb.getPotocolCode())) {
			sb.append(" AND tmnl.protocol_code =? ");
			list.add(ugb.getPotocolCode());
		}
		if (!nullString(ugb.getBureauNo())) {
			sb.append(" AND cons.org_no=? ");
			list.add(ugb.getBureauNo());
		}

		if(!nullString(ugb.getCpNo())){
			sb.append(" AND CP.CP_NO LIKE ? ");
			list.add("%"+ugb.getCpNo()+"%");
		}
		
		if(!nullString(ugb.getCpName())){
			sb.append(" AND CP.NAME LIKE ? ");
			list.add("%"+ugb.getCpName()+"%");
		}
		if(!nullString(ugb.getCpAddr())){
			sb.append(" AND CP.CP_ADDR LIKE ? ");
			list.add("%"+ugb.getCpAddr()+"%");
		}
		if(!"-1".equals(ugb.getCpTypeCode()) && !nullString(ugb.getCpTypeCode())){
			sb.append(" AND CP.CP_TYPE_CODE=? ");
			list.add(ugb.getCpTypeCode());
		}
		
		if (!nullString(ugb.getMeterAssetNo())) {
			sb.append(" AND M.CONS_NO=CONS.CONS_NO AND M.ASSET_NO LIKE ? \n");
			list.add("%"+ugb.getMeterAssetNo()+"%");
		}
		
		if(!nullString(ugb.getMrSectNo())){
			sb.append("		AND CONS.MR_SECT_NO LIKE ? \n");
			list.add("%"+ugb.getMrSectNo()+"%");
		}

		sb.append("  ORDER BY  CONS_NAME  ");
		
		logger.debug("\n查询用户 : "+sb.toString());
//		Page page = super.pagingFind(sb.toString(), start, limit, new CConsMapper(),
//				list.toArray());
		
		return super.pagingFind(sb.toString(), start, limit, new CsConsMapper(),
				list.toArray());

	}

	/**
	 * 通过上级行业查询当前行业
	 */
	@Override
	public Page<BTrade> findTradeByUpTrade(String uptrade, long start, int limit) {
				
		String sql = "select trade_no, p_trade_no, trade_name\n"
				+ "  from vw_trade b\n" + " where 1=1 \n";
		
		if (uptrade == null || uptrade.equals("")) {
			Object provinceCode = this.getJdbcTemplate().query("SELECT param_item_val FROM  B_SYS_PARAMETER  t where t.PARAM_ITEM_NO = 'SYS_PROVINCE'", 
					new ResultSetExtractor(){
						public Object extractData(ResultSet rs)
								throws SQLException, DataAccessException {
							if(null == rs){
								return null;
							}
							Object obj = null;
							while(rs.next()){
								obj = rs.getObject("param_item_val");
							}
							return obj;
						}
			});
			
			Object rootValue = this.getJdbcTemplate().query("SELECT param_item_val FROM  B_SYS_PARAMETER  t where t.PARAM_ID = 7", 
					new ResultSetExtractor(){
						public Object extractData(ResultSet rs)
								throws SQLException, DataAccessException {
							if(null == rs){
								return null;
							}
							Object obj = null;
							while(rs.next()){
								obj = rs.getObject("param_item_val");
							}
							return obj;
						}
			});
			
			if(1 == Long.valueOf(provinceCode.toString())){//青海代码特殊处理
				sql += "and b.trade_no != 'AAAA'\n";
			}
			if(null == rootValue || "".equals(rootValue.toString())){
				sql += " and b.p_trade_no is null";
				return super.pagingFind(sql, start, limit, new BTradeMapper());
			}else{
				sql += " and b.p_trade_no = ? ";
				return super.pagingFind(sql, start, limit, new BTradeMapper(), rootValue);
			}
		} else {
			sql += " and b.p_trade_no =?";
			return super.pagingFind(sql, start, limit, new BTradeMapper(),
					uptrade);
		}
	}

	/**
	 * 查询当前用户拥有权限的供电所
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OOrg> findBureauList(PSysUser username) {

		StringBuffer sb = new StringBuffer()
				.append("SELECT org_no, org_name,org_type,area_code FROM O_ORG WHERE ORG_TYPE = '06'");
		if (Integer.valueOf(username.getAccessLevel()) >= 4)
			sb.append(SysPrivilige.addOrgPriOrg(username, "04", "o_org"));
		else
			return null;
		return super.getJdbcTemplate().query(sb.toString(), new OrgMapper());
	}

	/**
	 * 查询规约列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VwProtocolCode> findProtocolList() {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT protocol_code, protocol_name FROM VW_PROTOCOL_CODE");

		String sql = sb.toString();
		return super.getJdbcTemplate().query(sql, new VwProtocolMapper());
	}

	/**
	 * 查询当前用户拥有权限的备选用户群组
	 */
	@Override
	public Page<RUserGroup> findBackUserByName(String username, long start,
			int limit) {

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT * FROM R_USER_GROUP WHERE 1=1 AND (STAFF_NO = ? OR IS_SHARE = 1) AND (CREATE_DATE > SYSDATE - VALID_DAYS OR IS_SHARE = 1) "); // 临时语句，待与用户关联
		
		return super
				.pagingFind(sb.toString(), start, limit, new RUGRowMapper(),username);
	}

	/**
	 * 查询某备选群组详细信息
	 */
	@Override
	public Page<CConsRtmnl> findBackUserDetail(String groupNo, long start,
			int limit, PSysUser username) {
		// RT.TERMINAL_ADDR 显示为 TMNL_ASSET_NO
		// StringBuffer sb = new StringBuffer();
		// sb.append("SELECT GROUP_NO,\n");
		// sb.append("       RT.TERMINAL_ADDR TMNL_ASSET_NO,\n");
		// sb.append("       CONS_NO\n");
		// sb.append("  FROM R_USER_GROUP_DETAIL R,\n");
		// sb.append("       R_TMNL_RUN          RT\n");
		// sb.append(" WHERE R.TMNL_ASSET_NO = RT.TMNL_ASSET_NO\n");
		// sb.append("   AND R.GROUP_NO = ?");
		//
		// String sql = sb.toString();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CONS.CONS_ID,\n");
		sb.append("       CONS.CUST_ID,\n");
		sb.append("       CONS.CONS_NO,\n");
		sb.append("       CONS.CONS_NAME,\n");
		sb.append("       CONS.CONS_TYPE,\n");
		sb.append("       cons.cons_sort_code,");
		sb.append("       CONS.TG_ID, \n");
		sb.append("       TMNL.TERMINAL_ID,\n");
		sb.append("       TMNL.TERMINAL_ADDR,\n");
		sb.append("       TMNL.TMNL_ASSET_NO,\n");
		sb.append("       TMNL.STATUS_CODE,\n");
		sb.append("       TMNL.CIS_ASSET_NO,\n");
		sb.append("       TMNL.SEND_UP_MODE,\n");
		sb.append("       TMNL.PROTOCOL_CODE,\n");
		sb.append("       TMNL.TERMINAL_TYPE_CODE,\n");
		sb.append("       CP.CP_NO,\n");
		sb.append("       '' ASSET_NO,\n");
		sb.append("       CP.NAME CP_NAME\n");
		sb.append("  FROM C_CONS               CONS,\n");
		sb.append("       VW_TMNL_RUN          TMNL,\n");
		sb.append("       R_CP                 CP,\n");
		sb.append("       R_USER_GROUP_DETAIL  UG\n");
		sb.append(" WHERE CONS.CONS_NO = TMNL.CONS_NO\n");
		sb.append("   AND TMNL.CP_NO = CP.CP_NO\n");
		sb.append("   AND UG.TMNL_ASSET_NO = TMNL.TMNL_ASSET_NO ");
		sb.append("   AND TMNL.CONS_NO = UG.CONS_NO\n");
		sb.append("   AND UG.GROUP_NO=? ");
		sb.append(" order by cons.cons_name ");
		String sql = sb.toString();

		return super.pagingFind(sql, start, limit, new CConsMapper(), groupNo);
	}

	/**
	 * 查询当前用户拥有权限的控制群组
	 */
	@Override
	public Page<TTmnlGroup> findControlGroupByName(String username, long start,
			int limit) {

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT * FROM t_tmnl_group where 1=1 AND (STAFF_NO = ? OR IS_SHARE = 1) AND (CREATE_DATE > SYSDATE - VALID_DAYS OR IS_SHARE = 1)");

		String sql = sb.toString();

		return super.pagingFind(sql, start, limit, new TTGRowMapper(),username);
	}

	/**
	 * 查询某控制群组详细信息
	 */
	@Override
	public Page<CConsRtmnl> findControlGroupDetail(String groupNo, long start,
			int limit, PSysUser username) {

		// StringBuffer sb = new StringBuffer();
		// sb.append("\n");
		// sb.append("SELECT GROUP_NO,\n");
		// sb.append("       T.CP_NO,\n");
		// sb.append("       CONS_NO,\n");
		// sb.append("       R.TERMINAL_ADDR TMNL_ASSET_NO\n");
		// sb.append("  FROM T_TMNL_GROUP_DETAIL T,\n");
		// sb.append("       R_TMNL_RUN          R\n");
		// sb.append(" WHERE T.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n");
		// sb.append("   AND T.GROUP_NO = ?");
		//
		// String sql = sb.toString();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CONS.CONS_ID,\n");
		sb.append("       CONS.CUST_ID,\n");
		sb.append("       CONS.CONS_NO,\n");
		sb.append("       CONS.CONS_NAME,\n");
		sb.append("       CONS.CONS_TYPE,\n");
		sb.append("       cons.cons_sort_code,\n");
		sb.append("       CONS.TG_ID, \n");
		sb.append("       TMNL.TERMINAL_ID,\n");
		sb.append("       TMNL.TERMINAL_ADDR,\n");
		sb.append("       TMNL.TMNL_ASSET_NO,\n");
		sb.append("       TMNL.STATUS_CODE,\n");
		sb.append("       TMNL.CIS_ASSET_NO,\n");
		sb.append("       TMNL.SEND_UP_MODE,\n");
		sb.append("       TMNL.PROTOCOL_CODE,\n");
		sb.append("       TMNL.TERMINAL_TYPE_CODE,\n");
		sb.append("       CP.CP_NO,\n");
		sb.append("       '' ASSET_NO,\n");
		sb.append("       CP.NAME CP_NAME\n");
		sb.append("  FROM C_CONS              CONS,\n");
		sb.append("       VW_TMNL_RUN         TMNL,\n");
		sb.append("       R_CP                CP,\n");
		sb.append("       T_TMNL_GROUP_DETAIL UG \n");
		sb.append(" WHERE CONS.CONS_NO = TMNL.CONS_NO\n");
		sb.append("   AND TMNL.CP_NO = CP.CP_NO\n");
		sb.append("   AND TMNL.CONS_NO = UG.CONS_NO\n");
		sb.append("   AND UG.TMNL_ASSET_NO = TMNL.TMNL_ASSET_NO ");
		sb.append("   AND UG.GROUP_NO = ? ");
		sb.append(" order by cons.cons_name ");
		String sql = sb.toString();

		return super.pagingFind(sql, start, limit, new CConsMapper(), groupNo);
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	private boolean nullString(String str) {
		if (str == null || str.trim().isEmpty())
			return true;
		else
			return false;
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
				GenralTreeDaoImpl.this.logger.error("取 OrgMapper 时出现错误！ ");
				return null;
			}
		}
	}

	class VwProtocolMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VwProtocolCode vwp = new VwProtocolCode();
			try {
				vwp.setProtocolCode(rs.getString("protocol_code"));
				vwp.setProtocolName(rs.getString("protocol_name"));
				return vwp;
			} catch (Exception e) {
				// throw new DBAccessException("OrgMapper出现错误！");
				GenralTreeDaoImpl.this.logger
						.error("取 VwProtocolMapper 时出现错误！ ");
				return null;
			}
		}

	}

	/**
	 * 内部类 SubMapper
	 * 
	 * @author zhangzhw
	 *@describe GSubs RowMapper 实现
	 */
	class GSubMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			GSubs sub = new GSubs();
			try {
				sub.setSubsId(rs.getLong("subs_id"));
				sub.setSubsNo(rs.getString("subs_no"));
				sub.setSubsName(rs.getString("subs_name"));
				sub.setVoltCode(rs.getString("volt_code"));
				sub.setOrgNo(rs.getString("org_no"));
				sub.setChgDate(rs.getDate("chg_date"));
				sub.setMtCap(rs.getLong("mt_cap"));
				sub.setMtNum(rs.getInt("mt_num"));
				sub.setRunStatusCode(rs.getString("run_status_code"));
				sub.setSubsAddr(rs.getString("subs_addr"));
				return sub;
			} catch (Exception e) {
				// throw new DBAccessException("OrgMapper出现错误！");
				GenralTreeDaoImpl.this.logger.error("取 SubMapper 时出现错误！ ");
				return null;
			}
		}
	}

	/**
	 * 内部类　GLineMapper
	 * 
	 * @author zhangzhw
	 *@describe GLine　RowMapper实现
	 */
	class GLineMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			GLine line = new GLine();
			try {
				line.setLineId(rs.getLong("line_id"));
				line.setLineNo(rs.getString("line_no"));
				line.setLineName(rs.getString("line_name"));
				line.setVoltCode(rs.getString("volt_code"));

				return line;
			} catch (Exception e) {
				GenralTreeDaoImpl.this.logger.error("取 GlineMapper 时出现错误！ ");
				return null;
			}
		}
	}
	
	/**
	 * 内部类　CConsMapper 为增加orgno添加这个mapper
	 * 
	 * @author jiangwch
	 * @describe CCons　RowMapper实现
	 */
	class CsConsMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CConsRtmnl cons = new CConsRtmnl();
			try {
				cons.setConsId(rs.getLong("cons_id"));
				cons.setCustId(rs.getLong("cust_id"));
				cons.setConsNo(rs.getString("cons_no"));
				cons.setConsName(rs.getString("cons_name"));
				cons.setConsType(rs.getString("cons_type"));
				cons.setConsSortCode(rs.getString("cons_sort_code"));
				cons.setTerminalId(rs.getString("terminal_id"));
				cons.setTgId(rs.getString("tg_id"));
				cons.setCisAssetNo(rs.getString("cis_asset_no"));
				cons.setTmnlAssetNo(rs.getString("tmnl_asset_no"));
				cons.setProtocolCode(rs.getString("protocol_code"));
				cons.setStatusCode(rs.getString("status_code"));
				cons.setSendUpMode(rs.getBoolean("send_up_mode"));
				cons.setTerminalTypeCode(rs.getString("terminal_type_code"));
				cons.setCpNo(rs.getString("cp_no"));
				cons.setCpName(rs.getString("cp_name"));
				cons.setTerminalAddr(rs.getString("terminal_addr"));
				cons.setAssetNo(rs.getString("asset_no"));
				cons.setOrgName(rs.getString("org_name"));
				cons.setOrgNo(rs.getString("org_no"));
				return cons;
			} catch (Exception e) {
				GenralTreeDaoImpl.this.logger.error("取 CConsMapper 时出现错误！ ");
				return null;
			}
		}
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
				cons.setCustId(rs.getLong("cust_id"));
				cons.setConsNo(rs.getString("cons_no"));
				cons.setConsName(rs.getString("cons_name"));
				cons.setConsType(rs.getString("cons_type"));
				cons.setConsSortCode(rs.getString("cons_sort_code"));
				cons.setTerminalId(rs.getString("terminal_id"));
				cons.setTgId(rs.getString("tg_id"));
				cons.setCisAssetNo(rs.getString("cis_asset_no"));
				cons.setTmnlAssetNo(rs.getString("tmnl_asset_no"));
				cons.setProtocolCode(rs.getString("protocol_code"));
				cons.setStatusCode(rs.getString("status_code"));
				cons.setSendUpMode(rs.getBoolean("send_up_mode"));
				cons.setTerminalTypeCode(rs.getString("terminal_type_code"));
				cons.setCpNo(rs.getString("cp_no"));
				cons.setCpName(rs.getString("cp_name"));
				cons.setTerminalAddr(rs.getString("terminal_addr"));
				cons.setAssetNo(rs.getString("asset_no"));
				return cons;
			} catch (Exception e) {
				GenralTreeDaoImpl.this.logger.error("取 CConsMapper 时出现错误！ ");
				return null;
			}
		}
	}

	/**
	 * 内部类　　
	 * 
	 * @author zhangzhw
	 * 
	 */
	class BTradeMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			BTrade bTrade = new BTrade();
			try {
				bTrade.setTradeNo(rs.getString("trade_no"));
				bTrade.setTradeName(rs.getString("trade_name"));
				bTrade.setPTradeNo(rs.getString("p_trade_no"));
				return bTrade;
			} catch (Exception e) {
				GenralTreeDaoImpl.this.logger.error("取 BTradeMapper 时出现错误！ ");
				return null;
			}
		}
	}

	/**
	 * 内部类 RUGRowMapper
	 * 
	 * @author zhangzhw
	 * 
	 */
	class RUGRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RUserGroup rusergroup = new RUserGroup();
			try {
				rusergroup.setGroupNo(rs.getString("GROUP_NO"));
				rusergroup.setGroupName(rs.getString("GROUP_NAME"));
				rusergroup.setIsShare(rs.getBoolean("IS_SHARE"));
				rusergroup.setStaffNo(rs.getString("STAFF_NO"));
				rusergroup.setCreateDate(rs.getDate("CREATE_DATE"));
				rusergroup.setValidDays(rs.getShort("VALID_DAYS"));
				return rusergroup;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 内部类　　RUGDRowMapper
	 * 
	 * @author zhangzhw 　　　　　用户群组明细映射类
	 */
	class RUGDRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RUserGroupDetailId rusergroupdetail = new RUserGroupDetailId();
			try {
				rusergroupdetail.setGroupNo(rs.getString("GROUP_NO"));
				rusergroupdetail.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				rusergroupdetail.setConsNo(rs.getString("CONS_NO"));
				return rusergroupdetail;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 内部类　TTGRowMapper
	 * 
	 * @author zhangzhw 　控制群组映射类
	 */
	class TTGRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TTmnlGroup ttmnlgroup = new TTmnlGroup();
			try {
				ttmnlgroup.setGroupNo(rs.getString("GROUP_NO"));
				ttmnlgroup.setGroupName(rs.getString("GROUP_NAME"));
				ttmnlgroup.setOrgNo(rs.getString("ORG_NO"));
				ttmnlgroup.setAddr(rs.getString("ADDR"));
				ttmnlgroup.setGroupTypeCode(rs.getString("GROUP_TYPE_CODE"));
				ttmnlgroup.setReleaseStatusCode(rs
						.getString("RELEASE_STATUS_CODE"));
				ttmnlgroup.setIsShare(rs.getBoolean("IS_SHARE"));
				ttmnlgroup.setStaffNo(rs.getString("STAFF_NO"));
				ttmnlgroup.setCreateDate(rs.getDate("CREATE_DATE"));
				ttmnlgroup.setValidDays(rs.getShort("VALID_DAYS"));
				return ttmnlgroup;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 内部类　TTGDRowMapper
	 * 
	 * @author zhangzhw 　控制群组明细映射类
	 */
	class TTGDRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TTmnlGroupDetailId ttmnlgroupdetailid = new TTmnlGroupDetailId();
			try {
				ttmnlgroupdetailid.setGroupNo(rs.getString("GROUP_NO"));
				ttmnlgroupdetailid.setConsNo(rs.getString("CONS_NO"));
				ttmnlgroupdetailid
						.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				return ttmnlgroupdetailid;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 查询采集点类型
	 * chenjg
	 * @return
	 */
	public List <CpTypeCode> findAllCpTypeCode(){
		String sql = "select * from vw_cp_type_code union select '-1' as cp_type_code ,'全部' as cp_type from vw_cp_type_code";
		return this.getJdbcTemplate().query(sql, new CpTypeCodeRowMapper());
	}
	
	class CpTypeCodeRowMapper implements RowMapper{

		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CpTypeCode code = new CpTypeCode();
			code.setCpTypeCode(rs.getString("cp_type_code"));
			code.setCpType(rs.getString("cp_type"));
			return code;
		}
		
	}
	
	/**
	 * chenjg
	 * 变电站查询用户
	 * @param ugb
	 * @param start
	 * @param limit
	 * @param pSysUser
	 * @return
	 */
	public Page<CConsRtmnl> findCustmerBySubs(UserGridBean ugb, long start,
			int limit, PSysUser pSysUser){
		List<Object> list = new ArrayList<Object>();

		// 　原终端地址　terminal_id　改为　terminal_addr　以别名方式修改

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CONS.CONS_ID,\n");
		sb.append("       CUST_ID,\n");
		sb.append("       CONS.CONS_NO,\n");
		sb.append("       CONS.TG_ID,\n");
		sb.append("       CONS.CONS_NAME,\n");
		sb.append("       CONS.CONS_TYPE,\n");
		sb.append("       cons.cons_sort_code,\n");
		sb.append("       TMNL.TERMINAL_ADDR,\n");
		sb.append("       TMNL.TERMINAL_ID,\n");
		sb.append("       TMNL.TMNL_ASSET_NO,\n");
		sb.append("       TMNL.STATUS_CODE,\n");
		sb.append("       TMNL.SEND_UP_MODE,\n");
		sb.append("       TMNL.CIS_ASSET_NO,\n");
		sb.append("       TMNL.TERMINAL_TYPE_CODE,\n");
		sb.append("       TMNL.PROTOCOL_CODE,\n");
		sb.append("       CP.CP_NO,\n");
		if (!nullString(ugb.getMeterAssetNo())) {
			sb.append("       M.ASSET_NO,\n");
		}else{
			sb.append("       '' ASSET_NO,\n");
		}
		sb.append("       CP.NAME                 AS CP_NAME,\n");
		sb.append("       ORG.ORG_NO,\n");
		sb.append("       ORG.ORG_NAME \n");
		sb.append("  FROM C_CONS      CONS,\n");
		sb.append("       G_SUBS SUBS,\n");
		if (!nullString(ugb.getMeterAssetNo())) {
			sb.append("        C_METER M,\n");
		}
		sb.append("       G_SUBS_LINE_RELA GSLR,\n");
		sb.append("       G_LINE LINE,\n");
		sb.append("       VW_TMNL_RUN TMNL,\n");
		sb.append("       R_CP        CP,\n");
		sb.append("       O_ORG        ORG\n");
		sb.append(" WHERE CONS.CONS_NO = TMNL.CONS_NO\n");
		sb.append("   AND TMNL.CP_NO = CP.CP_NO \n ");
		sb.append("   AND GSLR.SUBS_ID = SUBS.SUBS_ID \n ");
		sb.append("   AND GSLR.LINE_ID = LINE.LINE_ID \n ");
		sb.append("   AND CONS.LINE_ID = LINE.LINE_ID \n ");
		sb.append("   AND CONS.CONS_TYPE='1' \n");
		sb.append("   AND CONS.ORG_NO = ORG.ORG_NO \n");
		
		if(!nullString(ugb.getSubName())){
			sb.append("   AND SUBS.SUBS_NAME LIKE ? \n ");
			list.add("%"+ugb.getSubName()+"%");
		}
		if(!nullString(ugb.getLineName())){
			sb.append("   AND LINE.LINE_NAME LIKE ? \n ");
			list.add("%"+ugb.getLineName()+"%");
		}
		
		
		sb.append(SysPrivilige.addPri(pSysUser, "cons", "tmnl", 7));
		for (int i = 0; i < 3; i++) {
			list.add(pSysUser.getStaffNo());
		}

		if (!nullString(ugb.getConsNo())) {
			sb.append(" AND cons.cons_no like '%'||?||'%' ");
			list.add(ugb.getConsNo());
		}
		if (!nullString(ugb.getConsName())) {
			sb.append(" AND cons.cons_name like '%'||?||'%' ");
			list.add(ugb.getConsName());
		}
		if (!nullString(ugb.getTerminalAddr())) {
			sb.append(" AND tmnl.terminal_addr like '%'||?||'%' ");
			list.add(ugb.getTerminalAddr());
		}
		if (!nullString(ugb.getCisAssetNo())) {
			sb.append(" AND tmnl.cis_asset_no like '%'||?||'%' ");
			list.add(ugb.getCisAssetNo());
		}
		if (!nullString(ugb.getPotocolCode())) {
			sb.append(" AND tmnl.protocol_code =? ");
			list.add(ugb.getPotocolCode());
		}
		if (!nullString(ugb.getBureauNo())) {
			sb.append(" AND cons.org_no=? ");
			list.add(ugb.getBureauNo());
		}

		if(!nullString(ugb.getCpNo())){
			sb.append(" AND CP.CP_NO LIKE ? ");
			list.add("%"+ugb.getCpNo()+"%");
		}
		
		if(!nullString(ugb.getCpName())){
			sb.append(" AND CP.NAME LIKE ? ");
			list.add("%"+ugb.getCpName()+"%");
		}
		if(!nullString(ugb.getCpAddr())){
			sb.append(" AND CP.CP_ADDR LIKE ? ");
			list.add("%"+ugb.getCpAddr()+"%");
		}
		if(!"-1".equals(ugb.getCpTypeCode()) && !nullString(ugb.getCpTypeCode())){
			sb.append(" AND CP.CP_TYPE_CODE=? ");
			list.add(ugb.getCpTypeCode());
		}
		
		if (!nullString(ugb.getMeterAssetNo())) {
			sb.append(" AND M.CONS_NO=CONS.CONS_NO AND M.ASSET_NO LIKE ? \n");
			list.add("%"+ugb.getMeterAssetNo()+"%");
		}

		sb.append("  ORDER BY  CONS_NAME  ");
		
		logger.debug("\n查询用户 : "+sb.toString());
//		Page page = super.pagingFind(sb.toString(), start, limit, new CConsMapper(),
//				list.toArray());
		
		return super.pagingFind(sb.toString(), start, limit, new CsConsMapper(),
				list.toArray());
	}
}
