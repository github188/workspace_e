package com.nari.runman.archivesman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.customer.CCons;
import com.nari.runman.archivesman.IArchieveManDao;

public class ArchieveManDaoImpl extends JdbcBaseDAOImpl implements
		IArchieveManDao {

	/**
	 * 通过ID取得用户信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CCons findCConsById(String nodeId) {

		StringBuffer sb = new StringBuffer();

		
		
		sb.append("SELECT c.CONS_ID,\n");
		sb.append("       c.CUST_ID,\n");
		sb.append("       c.ORG_NO,\n");
		sb.append("       o.org_name,\n");
		sb.append("       c.AREA_NO,\n");
		sb.append("       o.org_name as areaNoText,\n");
		sb.append("       c.CONS_NO,\n");
		sb.append("       c.CONS_NAME,\n");
		sb.append("       c.CUST_NO,\n");
		sb.append("       c.SUBS_ID,\n");
		sb.append("		  gs.subs_name,\n");
		sb.append("       c.TG_ID,\n");
		sb.append("       c.LINE_ID,\n");
		sb.append("       g.line_name as lineIdText,\n");
		sb.append("       c.CUST_QUERY_NO,\n");
		sb.append("       c.TMP_PAY_RELA_NO,\n");
		sb.append("       c.ORGN_CONS_NO,\n");
		sb.append("       c.CONS_SORT_CODE,\n");
		sb.append("       c.ELEC_ADDR,\n");
		sb.append("       c.TRADE_CODE,\n");
		sb.append("       t.TRADE_NAME,\n");
		sb.append("       c.CONS_TYPE,\n");
		sb.append("       c.ELEC_TYPE_CODE,\n");
		sb.append("       c.CONTRACT_CAP,\n");
		sb.append("       c.RUN_CAP,\n");
		sb.append("       c.CAP_GRADE_NO,\n");
		sb.append("       c.SHIFT_NO,\n");
		sb.append("       c.LODE_ATTR_CODE,\n");
		sb.append("       c.VOLT_CODE,\n");
		sb.append("       c.HEC_INDUSTRY_CODE,\n");
		sb.append("       c.HOLIDAY,\n");
		sb.append("       c.BUILD_DATE,\n");
		sb.append("       c.PS_DATE,\n");
		sb.append("       c.CANCEL_DATE,\n");
		sb.append("       c.DUE_DATE,\n");
		sb.append("       c.NOTIFY_MODE,\n");
		sb.append("       c.SETTLE_MODE,\n");
		sb.append("       c.STATUS_CODE,\n");
		sb.append("       c.RRIO_CODE,\n");
		sb.append("       c.CHK_CYCLE,\n");
		sb.append("       c.LAST_CHK_DATE,\n");
		sb.append("       c.CHECKER_NO,\n");
		sb.append("       c.POWEROFF_CODE,\n");
		sb.append("       c.TRANSFER_CODE,\n");
		sb.append("       c.MR_SECT_NO,\n");
		sb.append("       c.NOTE_TYPE_CODE,\n");
		sb.append("       c.TMP_FLAG,\n");
		sb.append("       c.TMP_DATE,\n");
		sb.append("       c.APPLY_NO,\n");
		sb.append("       c.APPLY_DATE\n");
		sb.append("  FROM C_CONS c " +
				"	left join sea.g_line g on c.line_id = g.line_id  " +
				"   left join sea.o_org o on c.area_no = o.org_no " +
				"	left join sea.vw_trade t on c.trade_code = t.TRADE_NO  " +
				"   left join sea.G_SUBS gs on c.subs_id = gs.subs_id \n");
		sb.append(" WHERE c.CONS_ID = ?");
		
		String sql = sb.toString();
		
		List<CCons> list = super.getJdbcTemplate().query(sql,
				new Object[] { nodeId }, new ConsRowMapper());

		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * 通过ID取得用户信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CCons findCConsByNo(String consNo) {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.CONS_ID,\n");
		sb.append("       c.CUST_ID,\n");
		sb.append("       c.ORG_NO,\n");
		sb.append("       o.org_name,\n");
		sb.append("       c.AREA_NO,\n");
		sb.append("       o.org_name as areaNoText,\n");
		sb.append("       c.CONS_NO,\n");
		sb.append("       c.CONS_NAME,\n");
		sb.append("       c.CUST_NO,\n");
		sb.append("       c.SUBS_ID,\n");
		sb.append("		  gs.subs_name,\n");
		sb.append("       c.TG_ID,\n");
		sb.append("       c.LINE_ID,\n");
		sb.append("       g.line_name as lineIdText,\n");
		sb.append("       c.CUST_QUERY_NO,\n");
		sb.append("       c.TMP_PAY_RELA_NO,\n");
		sb.append("       c.ORGN_CONS_NO,\n");
		sb.append("       c.CONS_SORT_CODE,\n");
		sb.append("       c.ELEC_ADDR,\n");
		sb.append("       c.TRADE_CODE,\n");
		sb.append("       t.TRADE_NAME,\n");
		sb.append("       c.CONS_TYPE,\n");
		sb.append("       c.ELEC_TYPE_CODE,\n");
		sb.append("       c.CONTRACT_CAP,\n");
		sb.append("       c.RUN_CAP,\n");
		sb.append("       c.CAP_GRADE_NO,\n");
		sb.append("       c.SHIFT_NO,\n");
		sb.append("       c.LODE_ATTR_CODE,\n");
		sb.append("       c.VOLT_CODE,\n");
		sb.append("       c.HEC_INDUSTRY_CODE,\n");
		sb.append("       c.HOLIDAY,\n");
		sb.append("       c.BUILD_DATE,\n");
		sb.append("       c.PS_DATE,\n");
		sb.append("       c.CANCEL_DATE,\n");
		sb.append("       c.DUE_DATE,\n");
		sb.append("       c.NOTIFY_MODE,\n");
		sb.append("       c.SETTLE_MODE,\n");
		sb.append("       c.STATUS_CODE,\n");
		sb.append("       c.RRIO_CODE,\n");
		sb.append("       c.CHK_CYCLE,\n");
		sb.append("       c.LAST_CHK_DATE,\n");
		sb.append("       c.CHECKER_NO,\n");
		sb.append("       c.POWEROFF_CODE,\n");
		sb.append("       c.TRANSFER_CODE,\n");
		sb.append("       c.MR_SECT_NO,\n");
		sb.append("       c.NOTE_TYPE_CODE,\n");
		sb.append("       c.TMP_FLAG,\n");
		sb.append("       c.TMP_DATE,\n");
		sb.append("       c.APPLY_NO,\n");
		sb.append("       c.APPLY_DATE\n");
		sb.append("  FROM C_CONS c " +
				"	left join sea.g_line g on c.line_id = g.line_id  " +
				"   left join sea.o_org o on c.area_no = o.org_no " +
				"	left join sea.vw_trade t on c.trade_code = t.TRADE_NO  " +
				"   left join sea.G_SUBS gs on c.subs_id = gs.subs_id \n");
		sb.append(" WHERE c.CONS_NO = ?");

		String sql = sb.toString();
		
		System.out.println("--\n\n\n"+sql.toString());
		List<CCons> list = super.getJdbcTemplate().query(sql,
				new Object[] { consNo }, new ConsRowMapper());

		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * 保存 CCons用户信息
	 */
	@Override
	public CCons saveOrUpdate(CCons ccons) {

		if (ccons.getConsId() == null || ccons.getConsId() == 0) {
			long seq = querySequece("S_C_CONS");
			saveCons(ccons, seq);
			ccons.setConsId(seq);
		} else {
			updateCons(ccons);
		}

		return findCConsByNo(ccons.getConsNo());

	}

	// 保存用户信息
	@SuppressWarnings("unchecked")
	private void saveCons(CCons ccons, long seq) {
		List<String> sqlList = new ArrayList<String>();
		List paramList = new ArrayList();

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO C_CONS\n");
		sb.append("  (CONS_ID,\n");
		sb.append("   CUST_ID,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   AREA_NO,\n");
		sb.append("   CONS_NO,\n");
		sb.append("   CONS_NAME,\n");
		sb.append("   CUST_NO,\n");
		sb.append("   SUBS_ID,\n");
		sb.append("   TG_ID,\n");
		sb.append("   LINE_ID,\n");
		sb.append("   CUST_QUERY_NO,\n");
		sb.append("   TMP_PAY_RELA_NO,\n");
		sb.append("   ORGN_CONS_NO,\n");
		sb.append("   CONS_SORT_CODE,\n");
		sb.append("   ELEC_ADDR,\n");
		sb.append("   TRADE_CODE,\n");
		sb.append("   CONS_TYPE,\n");
		sb.append("   ELEC_TYPE_CODE,\n");
		sb.append("   CONTRACT_CAP,\n");
		sb.append("   RUN_CAP,\n");
		sb.append("   CAP_GRADE_NO,\n");
		sb.append("   SHIFT_NO,\n");
		sb.append("   LODE_ATTR_CODE,\n");
		sb.append("   VOLT_CODE,\n");
		sb.append("   HEC_INDUSTRY_CODE,\n");
		sb.append("   HOLIDAY,\n");
		sb.append("   BUILD_DATE,\n");
		sb.append("   PS_DATE,\n");
		sb.append("   CANCEL_DATE,\n");
		sb.append("   DUE_DATE,\n");
		sb.append("   NOTIFY_MODE,\n");
		sb.append("   SETTLE_MODE,\n");
		sb.append("   STATUS_CODE,\n");
		sb.append("   RRIO_CODE,\n");
		sb.append("   CHK_CYCLE,\n");
		sb.append("   LAST_CHK_DATE,\n");
		sb.append("   CHECKER_NO,\n");
		sb.append("   POWEROFF_CODE,\n");
		sb.append("   TRANSFER_CODE,\n");
		sb.append("   MR_SECT_NO,\n");
		sb.append("   NOTE_TYPE_CODE,\n");
		sb.append("   TMP_FLAG,\n");
		sb.append("   TMP_DATE,\n");
		sb.append("   APPLY_NO,\n");
		sb.append("   APPLY_DATE)\n");
		sb.append("VALUES\n");
		sb.append("  (\n");
		sb.append("   ?, --cons_id,\n");
		sb.append("   ?, --   cust_id,\n");
		sb.append("   ?, --   org_no,\n");
		sb.append("   ?, --   area_no,\n");
		sb.append("   ?, --   cons_no,\n");
		sb.append("   ?, --   cons_name,\n");
		sb.append("   ?, --   cust_no,\n");
		sb.append("   ?, --   subs_id,\n");
		sb.append("   ?, --   tg_id,\n");
		sb.append("   ?, --   line_id,\n");
		sb.append("   ?, --   cust_query_no,\n");
		sb.append("   ?, --   tmp_pay_rela_no,\n");
		sb.append("   ?, --   orgn_cons_no,\n");
		sb.append("   ?, --   cons_sort_code,\n");
		sb.append("   ?, --   elec_addr,\n");
		sb.append("   ?, --   trade_code,\n");
		sb.append("   ?, --   cons_type,\n");
		sb.append("   ?, --   elec_type_code,\n");
		sb.append("   ?, --   contract_cap,\n");
		sb.append("   ?, --   run_cap,\n");
		sb.append("   ?, --   cap_grade_no,\n");
		sb.append("   ?, --   shift_no,\n");
		sb.append("   ?, --   lode_attr_code,\n");
		sb.append("   ?, --   volt_code,\n");
		sb.append("   ?, --   hec_industry_code,\n");
		sb.append("   ?, --   holiday,\n");
		sb.append("   ?, --   build_date,\n");
		sb.append("   ?, --   ps_date,\n");
		sb.append("   ?, --   cancel_date,\n");
		sb.append("   ?, --   due_date,\n");
		sb.append("   ?, --   notify_mode,\n");
		sb.append("   ?, --   settle_mode,\n");
		sb.append("   ?, --   status_code,\n");
		sb.append("   ?, --   rrio_code,\n");
		sb.append("   ?, --   chk_cycle,\n");
		sb.append("   ?, --   last_chk_date,\n");
		sb.append("   ?, --   checker_no,\n");
		sb.append("   ?, --   poweroff_code,\n");
		sb.append("   ?, --   transfer_code,\n");
		sb.append("   ?, --   mr_sect_no,\n");
		sb.append("   ?, --   note_type_code,\n");
		sb.append("   ?, --   tmp_flag,\n");
		sb.append("   ?, --   tmp_date,\n");
		sb.append("   ?, --   apply_no,\n");
		sb.append("   ? --   apply_date\n");
		sb.append("   )");
		String sql = sb.toString();

		List list = new ArrayList();

		list.add(seq);
		list.add("-1"); // TODO custId 外键设置 前台程序为 -1 时才允许保存
		list.add(ccons.getOrgNo());
		list.add(ccons.getAreaNo());
		list.add(ccons.getConsNo());
		list.add(ccons.getConsName());
		list.add(ccons.getCustNo());
		list.add(ccons.getSubsId());
		list.add(ccons.getTgId());
		list.add(ccons.getLineId());
		list.add(ccons.getCustQueryNo());
		list.add(ccons.getTmpPayRelaNo());
		list.add(ccons.getOrgnConsNo());
		list.add(ccons.getConsSortCode());
		list.add(ccons.getElecAddr());
		list.add(ccons.getTradeCode());
		list.add(ccons.getConsType());
		list.add(ccons.getElecTypeCode());
		list.add(ccons.getContractCap());
		list.add(ccons.getRunCap());
		list.add(ccons.getCapGradeNo());
		list.add(ccons.getShiftNo());
		list.add(ccons.getLodeAttrCode());
		list.add(ccons.getVoltCode());
		list.add(ccons.getHecIndustryCode());
		list.add(ccons.getHoliday());
		list.add(ccons.getBuildDate());
		list.add(ccons.getPsDate());
		list.add(ccons.getCancelDate());
		list.add(ccons.getDueDate());
		list.add(ccons.getNotifyMode());
		list.add(ccons.getSettleMode());
		list.add(ccons.getStatusCode());
		list.add(ccons.getRrioCode());
		list.add(ccons.getChkCycle());
		list.add(ccons.getLastChkDate());
		list.add(ccons.getCheckerNo());
		list.add(ccons.getPoweroffCode());
		list.add(ccons.getTransferCode());
		list.add(ccons.getMrSectNo());
		list.add(ccons.getNoteTypeCode());
		list.add(ccons.getTmpFlag());
		list.add(ccons.getTmpDate());
		list.add(ccons.getApplyNo());
		list.add(ccons.getApplyDate());

		sqlList.add(sql);
		paramList.add(list);

		// -------------------------------------------------
		// 以上用户信息已测试

		// 为保证通过左边树查询可以查询到，添加以下关联信息
		String rcpInsertSql = "INSERT INTO R_CP\n" + "    (CP_NO,\n"
				+ "     NAME  ,\n" + "     CP_TYPE_CODE)" + "  VALUES\n"
				+ "    (\n" + "     s_r_cp.nextval, --cp_no,\n"
				+ "     '系统建立关联采集点', --       name,\n" + "   '2'  )";

		sqlList.add(rcpInsertSql);
		paramList.add(null);

		// 
		String rcpConsRelaSql = "INSERT INTO R_CP_CONS_RELA\n"
				+ "  (CP_CONS_ID,\n" + "   CONS_ID,\n" + "   CP_NO)\n"
				+ "VALUES\n" + "  (\n"
				+ "   s_r_cp_cons_rela.nextval, --cp_cons_id,\n"
				+ "   ?, --       cons_id,\n"
				+ "   s_r_cp.Currval --       cp_no\n" + "   )";
		List rcpConsRelaList = new ArrayList();
		rcpConsRelaList.add(seq);

		sqlList.add(rcpConsRelaSql);
		paramList.add(rcpConsRelaList);

		// r_cp_comm_para
		String commParaSql = " INSERT INTO r_cp_comm_para\n" + " (\n"
				+ "  comm_para_id,\n" + "       cp_no,\n"
				+ "       terminal_addr,\n" + "       alg_no,\n"
				+ "       alg_key\n" + "\t\t\t )VALUES\n" + "\t\t\t (\n"
				+ "s_r_cp_cons_rela.nextval,--comm_para_id,\n"
				+ "s_r_cp.Currval,--      cp_no,\n"
				+ "s_r_tmnl_run.nextval,--      terminal_addr,\n"
				+ "99,--      alg_no, 加密算法待修改\n"
				+ "0--      alg_key  加密密钥待修改\n" + "\t\t\t )";

		sqlList.add(commParaSql);
		paramList.add(null);

		// 创建终端 r_tmnl_run
		String rTmnlRunSql = "INSERT INTO R_TMNL_RUN\n" + "  (TERMINAL_ID,\n"
				+ "   CP_NO,\n" + "   TMNL_ASSET_NO,\n" + "   TERMINAL_ADDR,\n"
				+ "   FACTORY_CODE,\n" + "   TERMINAL_TYPE_CODE,\n"
				+ "   COLL_MODE)\n" + "VALUES\n"
				+ "  (S_R_TMNL_RUN.CURRVAL, --terminal_id,\n"
				+ "   S_R_CP.CURRVAL, --       cp_no,\n"
				+ "   S_R_TMNL_RUN.CURRVAL, --       tmnl_asset_no,\n"
				+ "   S_R_TMNL_RUN.CURRVAL, --       terminal_addr,\n"
				+ "   '01', --       factory_code,\n"
				+ "   '01', --       terminal_type_code,\n"
				+ "   '01' --       coll_mode\n" + "   )";

		sqlList.add(rTmnlRunSql);
		paramList.add(null);

		// 创建 r_cons_tmnl_rela 关联
		String rConsTmnlRelaSql = "INSERT INTO R_CONS_TMNL_RELA\n"
				+ "  (ORG_NO,\n" + "   AREA_NO,\n" + "   CONS_NO,\n"
				+ "   TMNL_ASSET_NO)\n" + "VALUES\n" + "  (?, --org_no,\n"
				+ "   ?, --       area_no,\n" + "   ?, --       cons_no,\n"
				+ "   s_r_tmnl_run.Currval --       tmnl_asset_no\n" + "   )";
		List rConsTmnlRelaPara = new ArrayList();
		rConsTmnlRelaPara.add(ccons.getOrgNo());
		rConsTmnlRelaPara.add(ccons.getAreaNo());
		rConsTmnlRelaPara.add(ccons.getConsNo());

		sqlList.add(rConsTmnlRelaSql);
		paramList.add(rConsTmnlRelaPara);

		doTransaction(sqlList, paramList, "建立新用户及关联信息时出错");

		// super.getJdbcTemplate().update(sql, list.toArray());

	}

	// 更新用户信息
	@SuppressWarnings("unchecked")
	private void updateCons(CCons ccons) {

		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE c_cons SET\n");
		sb.append("   ORG_NO=?,\n");
		sb.append("   AREA_NO=?,\n");
		sb.append("   CONS_NO=?,\n");
		sb.append("   CONS_NAME=?,\n");
		sb.append("   CUST_NO=?,\n");
		sb.append("   SUBS_ID=?,\n");
		sb.append("   TG_ID=?,\n");
		sb.append("   LINE_ID=?,\n");
		sb.append("   CUST_QUERY_NO=?,\n");
		sb.append("   TMP_PAY_RELA_NO=?,\n");
		sb.append("   ORGN_CONS_NO=?,\n");
		sb.append("   CONS_SORT_CODE=?,\n");
		sb.append("   ELEC_ADDR=?,\n");
		sb.append("   TRADE_CODE=?,\n");
		sb.append("   CONS_TYPE=?,\n");
		sb.append("   ELEC_TYPE_CODE=?,\n");
		sb.append("   CONTRACT_CAP=?,\n");
		sb.append("   RUN_CAP=?,\n");
		sb.append("   CAP_GRADE_NO=?,\n");
		sb.append("   SHIFT_NO=?,\n");
		sb.append("   LODE_ATTR_CODE=?,\n");
		sb.append("   VOLT_CODE=?,\n");
		sb.append("   HEC_INDUSTRY_CODE=?,\n");
		sb.append("   HOLIDAY=?,\n");
		sb.append("   BUILD_DATE=?,\n");
		sb.append("   PS_DATE=?,\n");
		sb.append("   CANCEL_DATE=?,\n");
		sb.append("   DUE_DATE=?,\n");
		sb.append("   NOTIFY_MODE=?,\n");
		sb.append("   SETTLE_MODE=?,\n");
		sb.append("   STATUS_CODE=?,\n");
		sb.append("   RRIO_CODE=?,\n");
		sb.append("   CHK_CYCLE=?,\n");
		sb.append("   LAST_CHK_DATE=?,\n");
		sb.append("   CHECKER_NO=?,\n");
		sb.append("   POWEROFF_CODE=?,\n");
		sb.append("   TRANSFER_CODE=?,\n");
		sb.append("   MR_SECT_NO=?,\n");
		sb.append("   NOTE_TYPE_CODE=?,\n");
		sb.append("   TMP_FLAG=?,\n");
		sb.append("   TMP_DATE=?,\n");
		sb.append("   APPLY_NO=?,\n");
		sb.append("   APPLY_DATE=?\n");
		sb.append("WHERE cons_id=?");

		String sql = sb.toString();

		List list = new ArrayList();
		list.add(ccons.getOrgNo());
		list.add(ccons.getAreaNo());
		list.add(ccons.getConsNo());
		list.add(ccons.getConsName());
		list.add(ccons.getCustNo());
		list.add(ccons.getSubsId());
		list.add(ccons.getTgId());
		list.add(ccons.getLineId());
		list.add(ccons.getCustQueryNo());
		list.add(ccons.getTmpPayRelaNo());
		list.add(ccons.getOrgnConsNo());
		list.add(ccons.getConsSortCode());
		list.add(ccons.getElecAddr());
		list.add(ccons.getTradeCode());
		list.add(ccons.getConsType());
		list.add(ccons.getElecTypeCode());
		list.add(ccons.getContractCap());
		list.add(ccons.getRunCap());
		list.add(ccons.getCapGradeNo());
		list.add(ccons.getShiftNo());
		list.add(ccons.getLodeAttrCode());
		list.add(ccons.getVoltCode());
		list.add(ccons.getHecIndustryCode());
		list.add(ccons.getHoliday());
		list.add(ccons.getBuildDate());
		list.add(ccons.getPsDate());
		list.add(ccons.getCancelDate());
		list.add(ccons.getDueDate());
		list.add(ccons.getNotifyMode());
		list.add(ccons.getSettleMode());
		list.add(ccons.getStatusCode());
		list.add(ccons.getRrioCode());
		list.add(ccons.getChkCycle());
		list.add(ccons.getLastChkDate());
		list.add(ccons.getCheckerNo());
		list.add(ccons.getPoweroffCode());
		list.add(ccons.getTransferCode());
		list.add(ccons.getMrSectNo());
		list.add(ccons.getNoteTypeCode());
		list.add(ccons.getTmpFlag());
		list.add(ccons.getTmpDate());
		list.add(ccons.getApplyNo());
		list.add(ccons.getApplyDate());
		list.add(ccons.getConsId());

		super.getJdbcTemplate().update(sql, list.toArray());

	}

	// 通过序列取得主键
	@Override
	public long querySequece(String seqName) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append(seqName.trim());
		sb.append(".NEXTVAL\n");
		sb.append(" FROM DUAL");

		String sql = sb.toString();

		return super.getJdbcTemplate().queryForLong(sql);

	}

	/**
	 * ConsRowMapper
	 * 
	 * @author zhangzhw
	 * 
	 */
	class ConsRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CCons ccons = new CCons();
			try {
				ccons.setConsId(rs.getLong("CONS_ID"));
				ccons.setCustId(rs.getLong("CUST_ID"));
				ccons.setOrgNo(rs.getString("ORG_NO"));
				ccons.setAreaNo(rs.getString("AREA_NO"));
				ccons.setConsNo(rs.getString("CONS_NO"));
				ccons.setConsName(rs.getString("CONS_NAME"));
				ccons.setCustNo(rs.getString("CUST_NO"));
				ccons.setSubsId(rs.getLong("SUBS_ID"));
				ccons.setTgId(rs.getLong("TG_ID"));
				ccons.setLineId(rs.getLong("LINE_ID"));
				ccons.setCustQueryNo(rs.getString("CUST_QUERY_NO"));
				ccons.setTmpPayRelaNo(rs.getString("TMP_PAY_RELA_NO"));
				ccons.setOrgnConsNo(rs.getString("ORGN_CONS_NO"));
				ccons.setConsSortCode(rs.getString("CONS_SORT_CODE"));
				ccons.setElecAddr(rs.getString("ELEC_ADDR"));
				ccons.setTradeCode(rs.getString("TRADE_CODE"));
				ccons.setConsType(rs.getByte("CONS_TYPE"));
				ccons.setElecTypeCode(rs.getString("ELEC_TYPE_CODE"));
				ccons.setContractCap(rs.getDouble("CONTRACT_CAP"));
				ccons.setRunCap(rs.getDouble("RUN_CAP"));
				ccons.setCapGradeNo(rs.getString("CAP_GRADE_NO"));
				ccons.setShiftNo(rs.getString("SHIFT_NO"));
				ccons.setLodeAttrCode(rs.getString("LODE_ATTR_CODE"));
				ccons.setVoltCode(rs.getString("VOLT_CODE"));
				ccons.setHecIndustryCode(rs.getString("HEC_INDUSTRY_CODE"));
				ccons.setHoliday(rs.getString("HOLIDAY"));
				ccons.setBuildDate(rs.getTimestamp("BUILD_DATE"));
				ccons.setPsDate(rs.getTimestamp("PS_DATE"));
				ccons.setCancelDate(rs.getTimestamp("CANCEL_DATE"));
				ccons.setDueDate(rs.getTimestamp("DUE_DATE"));
				ccons.setNotifyMode(rs.getString("NOTIFY_MODE"));
				ccons.setSettleMode(rs.getString("SETTLE_MODE"));
				ccons.setStatusCode(rs.getString("STATUS_CODE"));
				ccons.setRrioCode(rs.getString("RRIO_CODE"));
				ccons.setChkCycle(rs.getInt("CHK_CYCLE"));
				ccons.setLastChkDate(rs.getTimestamp("LAST_CHK_DATE"));
				ccons.setCheckerNo(rs.getString("CHECKER_NO"));
				ccons.setPoweroffCode(rs.getString("POWEROFF_CODE"));
				ccons.setTransferCode(rs.getString("TRANSFER_CODE"));
				ccons.setMrSectNo(rs.getString("MR_SECT_NO"));
				ccons.setNoteTypeCode(rs.getString("NOTE_TYPE_CODE"));
				ccons.setTmpFlag(rs.getString("TMP_FLAG"));
				ccons.setTmpDate(rs.getTimestamp("TMP_DATE"));
				ccons.setApplyNo(rs.getString("APPLY_NO"));
				ccons.setApplyDate(rs.getTimestamp("APPLY_DATE"));
				ccons.setAreaNoText(rs.getString("areaNoText"));
				ccons.setLineIdText(rs.getString("lineIdText"));
				ccons.setOrgName(rs.getString("org_name"));
				ccons.setTradeName(rs.getString("TRADE_NAME"));
				ccons.setSubsName(rs.getString("subs_name"));
				return ccons;
			} catch (SQLException e) {
				throw e;
			}
		}
	}

}
