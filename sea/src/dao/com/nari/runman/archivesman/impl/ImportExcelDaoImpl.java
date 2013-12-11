package com.nari.runman.archivesman.impl;

import java.util.ArrayList;
import java.util.List;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.customer.CCons;
import com.nari.grid.GTg;
import com.nari.measurepoint.CMeterMpRela;
import com.nari.runcontrol.CmeterJdbc;
import com.nari.runcontrol.CmpJdbc;
import com.nari.runcontrol.RConsTmnlRela;
import com.nari.runcontrol.RCpConsRela;
import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RcpRunJdbc;
import com.nari.runcontrol.RtmnlRunJdbc;
import com.nari.runman.archivesman.IImportExcelDao;
import com.nari.util.exception.DBAccessException;

/**
 * 处理档案导入DAO的类
 * 
 * @author zhangzhw 需要根据以下需求建立新表和序列 集抄已调试。 专变待集抄确定以后复制 --一、创建 g_tg c_cons
 *         r_tmnl_run c_mp c_meter 导入临时表 --二、创建　导入日志表
 * 
 *         -----------------------------------------------------------------
 *         --一、 create table G_TG_IMP ( TG_ID NUMBER(16) not null, ORG_NO
 *         VARCHAR2(16 CHAR) not null, TG_NO VARCHAR2(16 CHAR) not null, TG_NAME
 *         VARCHAR2(256 CHAR) not null, TG_CAP NUMBER(16,6), INST_ADDR
 *         VARCHAR2(256 CHAR), CHG_DATE DATE, PUB_PRIV_FLAG VARCHAR2(8 CHAR),
 *         RUN_STATUS_CODE VARCHAR2(8 CHAR) ) tablespace SEA pctfree 10 initrans
 *         1 maxtrans 255 storage ( initial 3 minextents 1 maxextents unlimited
 *         );
 * 
 *         -- -- Create table create table C_CONS_IMP ( CONS_ID NUMBER(16) not
 *         null, CUST_ID NUMBER(16), ORG_NO VARCHAR2(16 CHAR), AREA_NO
 *         VARCHAR2(16 CHAR), CONS_NO VARCHAR2(16 CHAR), CONS_NAME VARCHAR2(256
 *         CHAR), CUST_NO VARCHAR2(16 CHAR), SUBS_ID NUMBER(16), TG_ID
 *         NUMBER(16), LINE_ID NUMBER(16), CUST_QUERY_NO VARCHAR2(32 CHAR),
 *         TMP_PAY_RELA_NO VARCHAR2(16 CHAR), ORGN_CONS_NO VARCHAR2(16 CHAR),
 *         CONS_SORT_CODE VARCHAR2(8 CHAR), ELEC_ADDR VARCHAR2(256 CHAR),
 *         TRADE_CODE VARCHAR2(8 CHAR), CONS_TYPE NUMBER(2), ELEC_TYPE_CODE
 *         VARCHAR2(8 CHAR), CONTRACT_CAP NUMBER(16,6), RUN_CAP NUMBER(16,6),
 *         CAP_GRADE_NO VARCHAR2(2 CHAR), SHIFT_NO VARCHAR2(8 CHAR),
 *         LODE_ATTR_CODE VARCHAR2(8 CHAR), VOLT_CODE VARCHAR2(8 CHAR),
 *         HEC_INDUSTRY_CODE VARCHAR2(8 CHAR), HOLIDAY VARCHAR2(32 CHAR),
 *         BUILD_DATE DATE, PS_DATE DATE, CANCEL_DATE DATE, DUE_DATE DATE,
 *         NOTIFY_MODE VARCHAR2(8 CHAR), SETTLE_MODE VARCHAR2(8 CHAR),
 *         STATUS_CODE VARCHAR2(8 CHAR), RRIO_CODE VARCHAR2(8 CHAR), CHK_CYCLE
 *         NUMBER(5), LAST_CHK_DATE DATE, CHECKER_NO VARCHAR2(16 CHAR),
 *         POWEROFF_CODE VARCHAR2(8 CHAR), TRANSFER_CODE VARCHAR2(8 CHAR),
 *         MR_SECT_NO VARCHAR2(16 CHAR), NOTE_TYPE_CODE VARCHAR2(8 CHAR),
 *         TMP_FLAG VARCHAR2(8 CHAR), TMP_DATE DATE, APPLY_NO VARCHAR2(8 CHAR),
 *         APPLY_DATE DATE, CONS_SORT VARCHAR2(8) ) tablespace SEA pctfree 10
 *         initrans 1 maxtrans 255 storage ( initial 257 minextents 1 maxextents
 *         unlimited );
 * 
 *         ------------------------ create table R_TMNL_RUN_IMP ( TERMINAL_ID
 *         VARCHAR2(16 CHAR) not null, CP_NO VARCHAR2(16 CHAR) not null,
 *         TMNL_ASSET_NO VARCHAR2(32 CHAR) not null, TERMINAL_ADDR VARCHAR2(32
 *         CHAR) not null, CIS_ASSET_NO VARCHAR2(32 CHAR), SIM_NO VARCHAR2(32
 *         CHAR), ID VARCHAR2(8 CHAR), FACTORY_CODE VARCHAR2(16 CHAR) not null,
 *         ATTACH_METER_FLAG VARCHAR2(8 CHAR) default '0', TERMINAL_TYPE_CODE
 *         VARCHAR2(8 CHAR) not null, COLL_MODE VARCHAR2(8 CHAR) not null,
 *         PROTOCOL_CODE VARCHAR2(8 CHAR), COMM_PASSWORD VARCHAR2(16 CHAR),
 *         RUN_DATE DATE, STATUS_CODE VARCHAR2(8 CHAR), HARMONIC_DEV_FLAG
 *         VARCHAR2(8 CHAR), PS_ENSURE_FLAG VARCHAR2(8 CHAR), AC_SAMPLING_FLAG
 *         VARCHAR2(8 CHAR), ELIMINATE_FLAG VARCHAR2(8 CHAR), GATE_ATTR_FLAG
 *         VARCHAR2(8 CHAR), PRIO_PS_MODE VARCHAR2(8 CHAR), FREEZE_MODE
 *         VARCHAR2(8 CHAR), FREEZE_CYCLE_NUM NUMBER(5), MAX_LOAD_CURVE_DAYS
 *         NUMBER(5), PS_LINE_LEN NUMBER(5), WORK_PS VARCHAR2(256 CHAR),
 *         SPEAKER_FLAG VARCHAR2(8 CHAR), SPEAKER_DIST NUMBER(5), SEND_UP_MODE
 *         NUMBER(1) default 0, COMM_MODE VARCHAR2(8 CHAR), FRAME_NUMBER
 *         NUMBER(4) default 0, POWER_CUT_DATE DATE, INSTL_LOC_DIAGRAM BLOB,
 *         WIRING_DIAGRAM BLOB ) tablespace SEA pctfree 10 initrans 1 maxtrans
 *         255 storage ( initial 2 minextents 1 maxextents unlimited );
 * 
 *         -- create table C_MP_IMP ( MP_ID NUMBER(16) not null, MP_SECT_ID
 *         NUMBER(16), SP_ID NUMBER(16), MP_NO VARCHAR2(16 CHAR) not null,
 *         MP_NAME VARCHAR2(256 CHAR), ORG_NO VARCHAR2(16 CHAR), CONS_ID
 *         NUMBER(16), CONS_NO VARCHAR2(16 CHAR), TG_ID NUMBER(16), LINE_ID
 *         NUMBER(16), MR_SECT_NO VARCHAR2(16 CHAR), MP_ADDR VARCHAR2(256 CHAR),
 *         TYPE_CODE VARCHAR2(8 CHAR), MP_ATTR_CODE VARCHAR2(8 CHAR),
 *         USAGE_TYPE_CODE VARCHAR2(8 CHAR), SIDE_CODE VARCHAR2(8 CHAR),
 *         VOLT_CODE VARCHAR2(8 CHAR), APP_DATE DATE, RUN_DATE DATE, WIRING_MODE
 *         VARCHAR2(8 CHAR), MEAS_MODE VARCHAR2(8 CHAR), SWITCH_NO VARCHAR2(32
 *         CHAR), EXCHG_TYPE_CODE VARCHAR2(8 CHAR), MD_TYPE_CODE VARCHAR2(8
 *         CHAR), MR_SN NUMBER(5), MP_SN NUMBER(5), METER_FLAG VARCHAR2(8 CHAR),
 *         STATUS_CODE VARCHAR2(8 CHAR), LC_FLAG VARCHAR2(8 CHAR), EARTH_MODE
 *         VARCHAR2(8 CHAR) ) tablespace SEA pctfree 10 initrans 1 maxtrans 255
 *         storage ( initial 208 minextents 1 maxextents unlimited );
 * 
 *         -- create table C_METER_IMP ( METER_ID NUMBER(16) not null, ASSET_NO
 *         VARCHAR2(32 CHAR), MP_ID NUMBER(16), ORG_NO VARCHAR2(16 CHAR),
 *         AREA_NO VARCHAR2(16 CHAR), CONS_NO VARCHAR2(16 CHAR), BAUDRATE
 *         VARCHAR2(16 CHAR), COMM_NO VARCHAR2(8 CHAR), COMM_ADDR1 VARCHAR2(16
 *         CHAR), COMM_ADDR2 VARCHAR2(16 CHAR), COMM_MODE VARCHAR2(8 CHAR),
 *         INST_LOC VARCHAR2(256 CHAR), INST_DATE DATE, T_FACTOR NUMBER(10,2),
 *         REF_METER_FLAG VARCHAR2(8 CHAR), REF_METER_ID NUMBER(16),
 *         VALIDATE_CODE VARCHAR2(32 CHAR), MODULE_NO VARCHAR2(32 CHAR),
 *         MR_FACTOR VARCHAR2(8 CHAR), LAST_CHK_DATE DATE, ROTATE_CYCLE
 *         NUMBER(5), ROTATE_VALID_DATE DATE, CHK_CYCLE NUMBER(5), TMNL_ASSET_NO
 *         VARCHAR2(32 CHAR), FMR_ASSET_NO VARCHAR2(32 CHAR), REG_STATUS
 *         NUMBER(1) default 0, REG_SN NUMBER(5) ) tablespace SEA pctfree 10
 *         initrans 1 maxtrans 255 storage ( initial 160 minextents 1 maxextents
 *         unlimited );
 * 
 *         --------------------下面为创建导入记录表 -- Create table create table
 *         archieve_implog ( id varchar2(19) not null, obj_type varchar2(20),
 *         obj_id varchar2(20), imp_time date, memo varchar2(40) ) ; -- Add
 *         comments to the columns comment on column archieve_implog.obj_type is
 *         '导入表的类型　c_cons r_tmnl_run g_tg c_mp c_meter '; comment on column
 *         archieve_implog.obj_id is '导入表的主键'; comment on column
 *         archieve_implog.imp_time is '导入时间'; comment on column
 *         archieve_implog.memo is '补充注释';
 * 
 * 
 *         --创建一个　sequence -- Create sequence create sequence s_archieve_implog
 *         minvalue 1 maxvalue 99999999999999999 start with 1 increment by 1;
 * 
 *         -- Create sequence create sequence s_meter_mp_id minvalue 1 maxvalue
 *         99999999999999 start with 100000001 increment by 1;
 * 
 * 
 * 
 * 
 *         
 *         ----------------------------------------------------------------------
 *         ----- --补充 通道管理 sequence -- Create sequence create sequence
 *         s_t_dnn_channel minvalue 1 maxvalue 9999999999999999 start with 1
 *         increment by 1;
 */
public class ImportExcelDaoImpl extends JdbcBaseDAOImpl implements
		IImportExcelDao {

	/**
	 * 保存专变用户的档案信息
	 * 
	 * @param cconsList
	 *            用户
	 * @param rcpList
	 *            采集点
	 * @param rcpparaList
	 *            采集点参数
	 * @param tmnlList
	 *            终端
	 * @param cmpList
	 *            计量点
	 * @param cmeterList
	 *            电表
	 * @param rCpConsRelaList
	 *            采集点和用户关联
	 * @param rConsTmnlRelaList
	 *            用户和终端关联
	 */
	@SuppressWarnings("unchecked")
	public void savePrivacyExcel(List<CCons> cconsList,
			List<RcpRunJdbc> rcpList, List<RcpParaJdbc> rcpparaList,
			List<RtmnlRunJdbc> tmnlList, List<CmpJdbc> cmpList,
			List<CmeterJdbc> cmeterList, List<RCpConsRela> rCpConsRelaList,
			List<RConsTmnlRela> rConsTmnlRelaList) throws DBAccessException {
		List<String> sqlList = new ArrayList<String>();
		List<List> paramList = new ArrayList<List>();

		for (CCons cCons : cconsList) {
			sqlList.add(saveConsSql());
			paramList.add(saveConsParams(cCons));
		}

		for (RcpRunJdbc rcpRunJdbc : rcpList) {
			sqlList.add(saveRcpSql());
			paramList.add(saveRcpParams(rcpRunJdbc));
		}

		for (RcpParaJdbc rcpParaJdbc : rcpparaList) {
			sqlList.add(saveRcpParaSql());
			paramList.add(saveRcpParaParams(rcpParaJdbc));
		}

		for (RtmnlRunJdbc rtmnlRunJdbc : tmnlList) {
			sqlList.add(saveTmnlSql());
			paramList.add(saveTmnlParams(rtmnlRunJdbc));
		}

		for (RCpConsRela rCpConsRela : rCpConsRelaList) {
			sqlList.add(saveRCpConsSql());
			paramList.add(saveRCpConsParams(rCpConsRela));
		}

		for (RConsTmnlRela rConsTmnlRela : rConsTmnlRelaList) {
			sqlList.add(saveRConsTmnlSql());
			paramList.add(saveRConsTmnlParams(rConsTmnlRela));
		}

		for (CmpJdbc cmpJdbc : cmpList) {
			sqlList.add(saveCmpSql());
			paramList.add(saveCmpParams(cmpJdbc));
		}

		for (CmeterJdbc cmeterJdbc : cmeterList) {
			sqlList.add(saveCmeterSql());
			paramList.add(saveCmeterParams(cmeterJdbc));
		}

		sqlList.add(this.copyCcons());
		paramList.add(null);

		sqlList.add(this.copyRtmnlrun());
		paramList.add(null);

		sqlList.add(this.copyCmp());
		paramList.add(null);

		sqlList.add(this.copyCmeter());
		paramList.add(null);

		// ----------------更新单位编码
		sqlList.add(updateOrg());
		paramList.add(null);

		// ------------------造R_Coll_obj
		sqlList.add(this.createRCollObj());
		paramList.add(null);

		// -----------------造 E_data_mp
		sqlList.add(this.createEDataMp());
		paramList.add(null);

		// ---------------添加导入日志

		sqlList.add(this.logCcons());
		paramList.add(null);

		sqlList.add(this.logRtmnlrun());
		paramList.add(null);

		sqlList.add(this.logCmp());
		paramList.add(null);

		sqlList.add(this.logCmeter());
		paramList.add(null);

		// -----清除临时表

		sqlList.add(" DELETE FROM  C_CONS_IMP ");
		paramList.add(null);

		sqlList.add(" DELETE FROM  R_TMNL_RUN_IMP ");
		paramList.add(null);

		sqlList.add(" DELETE FROM  C_MP_IMP ");
		paramList.add(null);

		sqlList.add(" DELETE FROM  C_METER_IMP ");
		paramList.add(null);

		super.doTransaction(sqlList, paramList, "保存档案信息时出错");
	}

	/**
	 * 保存公变用户的档案信息 (方法名未按规则）
	 * 
	 * @param gtgList
	 *            台区
	 * @param cconsList
	 *            用户
	 * @param rcpList
	 *            采集点
	 * @param rcpparaList
	 *            采集点参数
	 * @param tmnlList
	 *            终端
	 * @param cmpList
	 *            计量点
	 * @param cmeterList
	 *            电表
	 * @param rCpConsRelaList
	 *            采集点和用户关联
	 * @param rConsTmnlRelaList
	 *            用户和终端关联
	 */
	@SuppressWarnings("unchecked")
	public void savePrivacyExcel(List<GTg> gtgList, List<CCons> cconsList,
			List<RcpRunJdbc> rcpList, List<RcpParaJdbc> rcpparaList,
			List<RtmnlRunJdbc> tmnlList, List<CmpJdbc> cmpList,
			List<CmeterJdbc> cmeterList, List<RCpConsRela> rCpConsRelaList,
			List<RConsTmnlRela> rConsTmnlRelaList,
			List<CMeterMpRela> cMeterMpRelaList) throws DBAccessException {
		List<String> sqlList = new ArrayList<String>();
		List<List> paramList = new ArrayList<List>();

		// ----------主要表记录先导入临时表
		for (GTg gtg : gtgList) {
			String sql = "SELECT COUNT(*) FROM G_TG WHERE TG_ID=? ";
			long l = super.getJdbcTemplate().queryForLong(sql,
					new Object[] { gtg.getTgId() });
			if (l < 1) {
				sqlList.add(saveGtgSql());
				paramList.add(saveGtgParams(gtg));
			}
		}

		for (CCons cCons : cconsList) {
			String sql = "SELECT COUNT(*) FROM C_CONS WHERE CONS_NO=? ";
			long l = super.getJdbcTemplate().queryForLong(sql,
					new Object[] { cCons.getConsNo() });
			if (l < 1) {
				sqlList.add(saveConsSql());
				paramList.add(saveConsParams(cCons));
			}
		}

		for (RcpRunJdbc rcpRunJdbc : rcpList) {
			String sql = "SELECT COUNT(*) FROM R_CP WHERE CP_NO=? ";
			long l = super.getJdbcTemplate().queryForLong(sql,
					new Object[] { rcpRunJdbc.getCpNo() });
			if (l < 1) {
				sqlList.add(saveRcpSql());
				paramList.add(saveRcpParams(rcpRunJdbc));
			}
		}

		for (RcpParaJdbc rcpParaJdbc : rcpparaList) {
			String sql = "SELECT COUNT(*) FROM R_CP_COMM_PARA WHERE CP_NO=? ";
			long l = super.getJdbcTemplate().queryForLong(sql,
					new Object[] { rcpParaJdbc.getCpNo() });
			if (l < 1) {
				sqlList.add(saveRcpParaSql());
				paramList.add(saveRcpParaParams(rcpParaJdbc));
			}
		}

		for (RtmnlRunJdbc rtmnlRunJdbc : tmnlList) {
			String sql = "SELECT COUNT(*) FROM R_TMNL_RUN WHERE TMNL_ASSET_NO=? ";
			long l = super.getJdbcTemplate().queryForLong(sql,
					new Object[] { rtmnlRunJdbc.getTmnlAssetNo() });
			if (l < 1) {
				sqlList.add(saveTmnlSql());
				paramList.add(saveTmnlParams(rtmnlRunJdbc));
			}
		}

		for (RCpConsRela rCpConsRela : rCpConsRelaList) {

			sqlList.add(saveRCpConsSql());
			paramList.add(saveRCpConsParams(rCpConsRela));
		}

		for (RConsTmnlRela rConsTmnlRela : rConsTmnlRelaList) {
			String sql = " SELECT COUNT(*) FROM R_CONS_TMNL_RELA R WHERE R.TMNL_ASSET_NO=? AND R.CONS_NO =? ";
			long l = super.getJdbcTemplate().queryForLong(
					sql,
					new Object[] { rConsTmnlRela.getTmnlAssetNo(),
							rConsTmnlRela.getConsNo() });
			if (l < 1) {
				sqlList.add(saveRConsTmnlSql());
				paramList.add(saveRConsTmnlParams(rConsTmnlRela));
			}
		}

		for (CmpJdbc cmpJdbc : cmpList) {
			String sql = "SELECT COUNT(*) FROM C_MP WHERE MP_ID=? ";
			long l = super.getJdbcTemplate().queryForLong(sql,
					new Object[] { cmpJdbc.getMpId() });
			if (l < 1) {
				sqlList.add(saveCmpSql());
				paramList.add(saveCmpParams(cmpJdbc));
			}
		}

		for (CmeterJdbc cmeterJdbc : cmeterList) {
			String sql = "SELECT COUNT(*) FROM C_METER WHERE METER_ID=? ";
			long l = super.getJdbcTemplate().queryForLong(sql,
					new Object[] { cmeterJdbc.getMeterId() });
			if (l < 1) {
				sqlList.add(saveCmeterSql());
				paramList.add(saveCmeterParams(cmeterJdbc));
			}
		}

		for (CMeterMpRela cmetermp : cMeterMpRelaList) {
			String sql = "SELECT COUNT(*) FROM C_METER_MP_RELA WHERE METER_ID=? AND MP_ID=? ";
			long l = super.getJdbcTemplate().queryForLong(sql,
					new Object[] { cmetermp.getMeterId(), cmetermp.getMpId() });

			if (l < 1) {
				sqlList.add(saveCmeterMpSql());
				paramList.add(saveCmeterMpParams(cmetermp));
			}

		}

		// --------------从临时表导入正式表
		sqlList.add(this.copyGtg());
		paramList.add(null);

		sqlList.add(this.copyCcons());
		paramList.add(null);

		sqlList.add(this.copyRtmnlrun());
		paramList.add(null);

		sqlList.add(this.copyCmp());
		paramList.add(null);
		
		//更新一下c_meter的 orgNo然后拷贝
		sqlList.add(updateMeterSql());
		paramList.add(null);

		sqlList.add(this.copyCmeter());
		paramList.add(null);

		// ----------------更新单位编码
		sqlList.add(updateOrg());
		paramList.add(null);

		// ------------------造R_Coll_obj
		sqlList.add(this.createRCollObj());
		paramList.add(null);

		// -----------------造 E_data_mp
		sqlList.add(this.createEDataMp());
		paramList.add(null);
		
		sqlList.add(tmnlEDataSql());
		paramList.add(null);
		
		// ---------------添加导入日志
		sqlList.add(this.logGtg());
		paramList.add(null);

		sqlList.add(this.logCcons());
		paramList.add(null);

		sqlList.add(this.logRtmnlrun());
		paramList.add(null);

		sqlList.add(this.logCmp());
		paramList.add(null);

		sqlList.add(this.logCmeter());
		paramList.add(null);

		// -----清除临时表
		sqlList.add(" DELETE FROM  G_TG_IMP ");
		paramList.add(null);

		sqlList.add(" DELETE FROM  C_CONS_IMP ");
		paramList.add(null);

		sqlList.add(" DELETE FROM  R_TMNL_RUN_IMP ");
		paramList.add(null);

		sqlList.add(" DELETE FROM  C_MP_IMP ");
		paramList.add(null);

		sqlList.add(" DELETE FROM  C_METER_IMP ");
		paramList.add(null);

		super.doTransaction(sqlList, paramList, "保存档案信息时出错!");
	}

	/**
	 * 
	 * 保存台区信息SQL
	 */
	private String saveGtgSql() {
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into g_tg_imp\n");
		sb.append("  (tg_id,\n");
		sb.append("       org_no,\n");
		sb.append("       tg_no,\n");
		sb.append("       tg_name,\n");
		sb.append("       tg_cap,\n");
		sb.append("       inst_addr,\n");
		sb.append("       chg_date,\n");
		sb.append("       pub_priv_flag,\n");
		sb.append("       run_status_code\n");
		sb.append("  )\n");
		sb.append("  values\n");
		sb.append("  (\n");
		sb.append("?,--tg_id,\n");
		sb.append("?,--       org_no,\n");
		sb.append("?,--       tg_no,\n");
		sb.append("?,--       tg_name,\n");
		sb.append("?,--       tg_cap,\n");
		sb.append("?,--       inst_addr,\n");
		sb.append("?,--       chg_date,\n");
		sb.append("?,--       pub_priv_flag,\n");
		sb.append("?--       run_status_code\n");
		sb.append("  )");

		String sql = sb.toString();
		return sql;
	}

	/**
	 * saveGtgParams
	 * 
	 * @param gtg
	 * @return 保存台区的SQL参数
	 */
	private List<Object> saveGtgParams(GTg gtg) {
		List<Object> list = new ArrayList<Object>();
		list.add(gtg.getTgId());
		list.add(gtg.getOrgNo());
		list.add(gtg.getTgNo());
		list.add(gtg.getTgName());
		list.add(gtg.getTgCap());
		list.add(gtg.getInstAddr());
		list.add(gtg.getChgDate());
		list.add(gtg.getPubPrivFlag());
		list.add(gtg.getRunStatusCode());

		return list;

	}

	/**
	 * 保存用户信息sql
	 */
	private String saveConsSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO C_CONS_IMP\n");
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
		return sql;
	}

	/**
	 * 保存用户信息的参数
	 */
	private List<Object> saveConsParams(CCons ccons) {
		List<Object> list = new ArrayList<Object>();
		list.add(ccons.getConsId());
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
		return list;
	}

	/**
	 * 保存采集点sql
	 */
	private String saveRcpSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO R_CP\n");
		sb.append("  (CP_NO,\n");
		sb.append("   NAME,\n");
		sb.append("   CP_TYPE_CODE,\n");
		sb.append("   STATUS_CODE,\n");
		sb.append("   CP_ADDR,\n");
		sb.append("   GPS_LONGITUDE,\n");
		sb.append("   GPS_LATITUDE)\n");
		sb.append("VALUES\n");
		sb.append("  (?, --CP_NO,\n"); // 对应参数也要改
		sb.append("   ?, --       NAME,\n");
		sb.append("   ?, --       CP_TYPE_CODE,\n");
		sb.append("   ?, --       STATUS_CODE,\n");
		sb.append("   ?, --       CP_ADDR,\n");
		sb.append("   ?, --       GPS_LONGITUDE,\n");
		sb.append("   ? --       GPS_LATITUDE\n");
		sb.append("   )");
		String sql = sb.toString();
		return sql;
	}

	/**
	 * 保存采集点参数
	 */
	private List<Object> saveRcpParams(RcpRunJdbc rcprunjdbc) {
		List<Object> list = new ArrayList<Object>();
		list.add(rcprunjdbc.getCpNo());
		list.add(rcprunjdbc.getName());
		list.add(rcprunjdbc.getCpTypeCode());
		list.add(rcprunjdbc.getStatusCode());
		list.add(rcprunjdbc.getCpAddr());
		list.add(rcprunjdbc.getGpsLongitude());
		list.add(rcprunjdbc.getGpsLatitude());
		return list;
	}

	/**
	 * 保存采集点参数sql
	 */
	private String saveRcpParaSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO R_CP_COMM_PARA\n");
		sb.append("  (COMM_PARA_ID,\n");
		sb.append("   CP_NO,\n");
		sb.append("   TERMINAL_ADDR,\n");
		sb.append("   PROTOCOL_TYPE_CODE,\n");
		sb.append("   CHANNEL_NO,\n");
		sb.append("   RTS_ON,\n");
		sb.append("   RTS_OFF,\n");
		sb.append("   TRANSMIT_DELAY,\n");
		sb.append("   RESP_TIMEOUT,\n");
		sb.append("   MASTER_IP,\n");
		sb.append("   MASTER_PORT,\n");
		sb.append("   SPARE_IP_ADDR,\n");
		sb.append("   SPARE_PORT,\n");
		sb.append("   GATEWAY_IP,\n");
		sb.append("   GATEWAY_PORT,\n");
		sb.append("   PROXY_IP_ADDR,\n");
		sb.append("   PROXY_PORT,\n");
		sb.append("   GPRS_CODE,\n");
		sb.append("   SMS_NO,\n");
		sb.append("   APN,\n");
		sb.append("   HEARTBEAT_CYCLE,\n");
		sb.append("   START_DATE,\n");
		sb.append("   ALG_NO,\n");
		sb.append("   ALG_KEY)\n");
		sb.append("VALUES\n");
		sb.append("  (?, --       COMM_PARA_ID,\n");
		sb.append("   ?, --       CP_NO,\n");
		sb.append("   ?, --       TERMINAL_ADDR,\n");
		sb.append("   ?, --       PROTOCOL_CODE,\n");
		sb.append("   ?, --       CHANNEL_NO,\n");
		sb.append("   ?, --       RTS_ON,\n");
		sb.append("   ?, --       RTS_OFF,\n");
		sb.append("   ?, --       TRANSMIT_DELAY,\n");
		sb.append("   ?, --       RESP_TIMEOUT,\n");
		sb.append("   ?, --       MASTER_IP,\n");
		sb.append("   ?, --       MASTER_PORT,\n");
		sb.append("   ?, --       SPARE_IP_ADDR,\n");
		sb.append("   ?, --       SPARE_PORT,\n");
		sb.append("   ?, --       GATEWAY_IP,\n");
		sb.append("   ?, --       GATEWAY_PORT,\n");
		sb.append("   ?, --       PROXY_IP_ADDR,\n");
		sb.append("   ?, --       PROXY_PORT,\n");
		sb.append("   ?, --       GPRS_CODE,\n");
		sb.append("   ?, --       SMS_NO,\n");
		sb.append("   ?, --       APN,\n");
		sb.append("   ?, --       HEARTBEAT_CYCLE,\n");
		sb.append("   ?, --       START_DATE,\n");
		sb.append("   ?, --       ALG_NO,\n");
		sb.append("   ? --       ALG_KEY\n");
		sb.append("   )");
		String sql = sb.toString();
		return sql;
	}

	/**
	 * 保存采集点参数
	 */
	private List<Object> saveRcpParaParams(RcpParaJdbc rcpParaJdbc) {
		List<Object> list = new ArrayList<Object>();
		list.add(rcpParaJdbc.getCommParaId());
		list.add(rcpParaJdbc.getCpNo());
		list.add(rcpParaJdbc.getTerminalAddr());
		list.add(rcpParaJdbc.getProtocolCode());
		list.add(rcpParaJdbc.getChannelNo());
		list.add(rcpParaJdbc.getRtsOn());
		list.add(rcpParaJdbc.getRtsOff());
		list.add(rcpParaJdbc.getTransmitDelay());
		list.add(rcpParaJdbc.getRespTimeout());
		list.add(rcpParaJdbc.getMasterIp());
		list.add(rcpParaJdbc.getMasterPort());
		list.add(rcpParaJdbc.getSpareIpAddr());
		list.add(rcpParaJdbc.getSparePort());
		list.add(rcpParaJdbc.getGatewayIp());
		list.add(rcpParaJdbc.getGatewayPort());
		list.add(rcpParaJdbc.getProxyIpAddr());
		list.add(rcpParaJdbc.getProxyPort());
		list.add(rcpParaJdbc.getGprsCode());
		list.add(rcpParaJdbc.getSmsNo());
		list.add(rcpParaJdbc.getApn());
		list.add(rcpParaJdbc.getHeartbeatCycle());
		list.add(rcpParaJdbc.getStartDate());
		list.add(rcpParaJdbc.getAlgNo());
		list.add(rcpParaJdbc.getAlgKey());
		return list;
	}

	/**
	 * 保存终端sql
	 */
	private String saveTmnlSql() {
		String sql = "INSERT INTO  R_TMNL_RUN_IMP\n" + "(TERMINAL_ID,\n"
				+ "       CP_NO,\n" + "       TMNL_ASSET_NO,\n"
				+ "       TERMINAL_ADDR,\n" + "       CIS_ASSET_NO,\n"
				+ "       SIM_NO,\n" + "       ID,\n"
				+ "       FACTORY_CODE,\n" + "       ATTACH_METER_FLAG,\n"
				+ "       TERMINAL_TYPE_CODE,\n" + "       COLL_MODE,\n"
				+ "       PROTOCOL_CODE,\n" + "       COMM_PASSWORD,\n"
				+ "       RUN_DATE,\n" + "       STATUS_CODE,\n"
				+ "       HARMONIC_DEV_FLAG,\n" + "       PS_ENSURE_FLAG,\n"
				+ "       AC_SAMPLING_FLAG,\n" + "       ELIMINATE_FLAG,\n"
				+ "       GATE_ATTR_FLAG,\n" + "       PRIO_PS_MODE,\n"
				+ "       FREEZE_MODE,\n" + "       FREEZE_CYCLE_NUM,\n"
				+ "       MAX_LOAD_CURVE_DAYS,\n" + "       PS_LINE_LEN,\n"
				+ "       WORK_PS,\n" + "       SPEAKER_FLAG,\n"
				+ "       SPEAKER_DIST,\n" + "       SEND_UP_MODE,\n"
				+ "       COMM_MODE,\n" + "       FRAME_NUMBER,\n"
				+ "       POWER_CUT_DATE\n" + ")\n" + "VALUES\n" + "(\n"
				+ "?,--TERMINAL_ID,\n" + "?,--       CP_NO,\n"
				+ "?,--       TMNL_ASSET_NO,\n" + "?,--       TERMINAL_ADDR,\n"
				+ "?,--       CIS_ASSET_NO,\n" + "?,--       SIM_NO,\n"
				+ "?,--       ID,\n" + "?,--       FACTORY_CODE,\n"
				+ "?,--       ATTACH_METER_FLAG,\n"
				+ "?,--       TERMINAL_TYPE_CODE,\n"
				+ "?,--       COLL_MODE,\n" + "?,--       PROTOCOL_CODE,\n"
				+ "?,--       COMM_PASSWORD,\n" + "?,--       RUN_DATE,\n"
				+ "?,--       STATUS_CODE,\n"
				+ "?,--       HARMONIC_DEV_FLAG,\n"
				+ "?,--       PS_ENSURE_FLAG,\n"
				+ "?,--       AC_SAMPLING_FLAG,\n"
				+ "?,--       ELIMINATE_FLAG,\n"
				+ "?,--       GATE_ATTR_FLAG,\n" + "?,--       PRIO_PS_MODE,\n"
				+ "?,--       FREEZE_MODE,\n"
				+ "?,--       FREEZE_CYCLE_NUM,\n"
				+ "?,--       MAX_LOAD_CURVE_DAYS,\n"
				+ "?,--       PS_LINE_LEN,\n" + "?,--       WORK_PS,\n"
				+ "?,--       SPEAKER_FLAG,\n" + "?,--       SPEAKER_DIST,\n"
				+ "?,--       SEND_UP_MODE,\n" + "?,--       COMM_MODE,\n"
				+ "?,--       FRAME_NUMBER,\n" + "?--       POWER_CUT_DATE\n"
				+ ")";
		return sql;
	}

	/**
	 * 保存终端的SQL参数
	 */
	private List<Object> saveTmnlParams(RtmnlRunJdbc tmnl) {
		List<Object> list = new ArrayList<Object>();
		list.add(tmnl.getTerminalId());
		list.add(tmnl.getCpNo());
		list.add(tmnl.getTmnlAssetNo());
		list.add(tmnl.getTerminalAddr());
		list.add(tmnl.getCisAssetNo());
		list.add(tmnl.getSimNo());
		list.add(tmnl.getId());
		list.add(tmnl.getFactoryCode());
		list.add(tmnl.getAttachMeterFlag());
		list.add(tmnl.getTerminalTypeCode());
		list.add(tmnl.getCollMode());
		list.add(tmnl.getProtocolCode());
		list.add(tmnl.getCommPassword());
		list.add(tmnl.getRunDate());
		list.add(tmnl.getStatusCode());
		list.add(tmnl.getHarmonicDevFlag());
		list.add(tmnl.getPsEnsureFlag());
		list.add(tmnl.getAcSamplingFlag());
		list.add(tmnl.getEliminateFlag());
		list.add(tmnl.getGateAttrFlag());
		list.add(tmnl.getPrioPsMode());
		list.add(tmnl.getFreezeMode());
		list.add(tmnl.getFreezeCycleNum());
		list.add(tmnl.getMaxLoadCurveDays());
		list.add(tmnl.getPsLineLen());
		list.add(tmnl.getWorkPs());
		list.add(tmnl.getSpeakerFlag());
		list.add(tmnl.getSpeakerDist());
		list.add(tmnl.getSendUpMode());
		list.add(tmnl.getCommMode());
		list.add(tmnl.getFrameNumber());
		list.add(tmnl.getPowerCutDate());
		return list;
	}

	/**
	 * 保存计量点sql
	 */
	private String saveCmpSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO C_MP_IMP\n");
		sb.append("  (MP_ID,\n");
		sb.append("   MP_SECT_ID,\n");
		sb.append("   SP_ID,\n");
		sb.append("   MP_NO,\n");
		sb.append("   MP_NAME,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   CONS_ID,\n");
		sb.append("   CONS_NO,\n");
		sb.append("   TG_ID,\n");
		sb.append("   LINE_ID,\n");
		sb.append("   MR_SECT_NO,\n");
		sb.append("   MP_ADDR,\n");
		sb.append("   TYPE_CODE,\n");
		sb.append("   MP_ATTR_CODE,\n");
		sb.append("   USAGE_TYPE_CODE,\n");
		sb.append("   SIDE_CODE,\n");
		sb.append("   VOLT_CODE,\n");
		sb.append("   APP_DATE,\n");
		sb.append("   RUN_DATE,\n");
		sb.append("   WIRING_MODE,\n");
		sb.append("   MEAS_MODE,\n");
		sb.append("   SWITCH_NO,\n");
		sb.append("   EXCHG_TYPE_CODE,\n");
		sb.append("   MD_TYPE_CODE,\n");
		sb.append("   MR_SN,\n");
		sb.append("   MP_SN,\n");
		sb.append("   METER_FLAG,\n");
		sb.append("   STATUS_CODE,\n");
		sb.append("   LC_FLAG,\n");
		sb.append("   EARTH_MODE)\n");
		sb.append("VALUES\n");
		sb.append("  (?, --MP_ID,\n");
		sb.append("   ?, --     MP_SECT_ID,\n");
		sb.append("   ?, --     SP_ID,\n");
		sb.append("   ?, --     MP_NO,\n");
		sb.append("   ?, --     MP_NAME,\n");
		sb.append("   ?, --     ORG_NO,\n");
		sb.append("   ?, --     CONS_ID,\n");
		sb.append("   ?, --     CONS_NO,\n");
		sb.append("   ?, --     TG_ID,\n");
		sb.append("   ?, --     LINE_ID,\n");
		sb.append("   ?, --     MR_SECT_NO,\n");
		sb.append("   ?, --     MP_ADDR,\n");
		sb.append("   ?, --     TYPE_CODE,\n");
		sb.append("   ?, --     MP_ATTR_CODE,\n");
		sb.append("   ?, --     USAGE_TYPE_CODE,\n");
		sb.append("   ?, --     SIDE_CODE,\n");
		sb.append("   ?, --     VOLT_CODE,\n");
		sb.append("   ?, --     APP_DATE,\n");
		sb.append("   ?, --     RUN_DATE,\n");
		sb.append("   ?, --     WIRING_MODE,\n");
		sb.append("   ?, --     MEAS_MODE,\n");
		sb.append("   ?, --     SWITCH_NO,\n");
		sb.append("   ?, --     EXCHG_TYPE_CODE,\n");
		sb.append("   ?, --     MD_TYPE_CODE,\n");
		sb.append("   ?, --     MR_SN,\n");
		sb.append("   ?, --     MP_SN,\n");
		sb.append("   ?, --     METER_FLAG,\n");
		sb.append("   ?, --     STATUS_CODE,\n");
		sb.append("   ?, --     LC_FLAG,\n");
		sb.append("   ? --     EARTH_MODE\n");
		sb.append("   )");
		String sql = sb.toString();
		return sql;
	}

	/**
	 * 保存计量点参数
	 */
	private List<Object> saveCmpParams(CmpJdbc cmpjdbc) {
		List<Object> list = new ArrayList<Object>();
		list.add(cmpjdbc.getMpId());
		list.add(cmpjdbc.getMpSectId());
		list.add(cmpjdbc.getSpId());

		list.add(cmpjdbc.getMpNo());
		list.add(cmpjdbc.getMpName());
		list.add(cmpjdbc.getOrgNo());
		list.add(cmpjdbc.getConsId());
		list.add(cmpjdbc.getConsNo());
		list.add(cmpjdbc.getTgId());
		list.add(cmpjdbc.getLineId());
		list.add(cmpjdbc.getMrSectNo());
		list.add(cmpjdbc.getMpAddr());
		list.add(cmpjdbc.getTypeCode());
		list.add(cmpjdbc.getMpAttrCode());
		list.add(cmpjdbc.getUsageTypeCode());
		list.add(cmpjdbc.getSideCode());
		list.add(cmpjdbc.getVoltCode());
		list.add(cmpjdbc.getAppDate());
		list.add(cmpjdbc.getRunDate());
		list.add(cmpjdbc.getWiringMode());
		list.add(cmpjdbc.getMeasMode());
		list.add(cmpjdbc.getSwitchNo());
		list.add(cmpjdbc.getExchgTypeCode());
		list.add(cmpjdbc.getMdTypeCode());
		list.add(cmpjdbc.getMrSn());
		list.add(cmpjdbc.getMpSn());
		list.add(cmpjdbc.getMeterFlag());
		list.add(cmpjdbc.getStatusCode());
		list.add(cmpjdbc.getLcFlag());
		list.add(cmpjdbc.getEarthMode());
		return list;
	}

	/**
	 * 保存电表的sql
	 */
	private String saveCmeterSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO C_METER_IMP\n");
		sb.append("  (METER_ID,\n");
		sb.append("   ASSET_NO,\n");
		sb.append("   MP_ID,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   AREA_NO,\n");
		sb.append("   CONS_NO,\n");
		sb.append("   BAUDRATE,\n");
		sb.append("   COMM_NO,\n");
		sb.append("   COMM_ADDR1,\n");
		sb.append("   COMM_ADDR2,\n");
		sb.append("   COMM_MODE,\n");
		sb.append("   INST_LOC,\n");
		sb.append("   INST_DATE,\n");
		sb.append("   T_FACTOR,\n");
		sb.append("   REF_METER_FLAG,\n");
		sb.append("   REF_METER_ID,\n");
		sb.append("   VALIDATE_CODE,\n");
		sb.append("   MODULE_NO,\n");
		sb.append("   MR_FACTOR,\n");
		sb.append("   LAST_CHK_DATE,\n");
		sb.append("   ROTATE_CYCLE,\n");
		sb.append("   ROTATE_VALID_DATE,\n");
		sb.append("   CHK_CYCLE,\n");
		sb.append("   TMNL_ASSET_NO,\n");
		sb.append("   FMR_ASSET_NO,\n");
		sb.append("   REG_STATUS,\n");
		sb.append("   REG_SN)\n");
		sb.append("VALUES\n");
		sb.append("(    ?, --       METER_ID,\n");
		sb.append("   ?, --       ASSET_NO,\n");
		sb.append("   ?, --       MP_ID,\n");
		sb.append("   ?, --       ORG_NO,\n");
		sb.append("   ?, --       AREA_NO,\n");
		sb.append("   ?, --       CONS_NO,\n");
		sb.append("   ?, --       BAUDRATE,\n");
		sb.append("   ?, --       COMM_NO,\n");
		sb.append("   ?, --       COMM_ADDR1,\n");
		sb.append("   ?, --       COMM_ADDR2,\n");
		sb.append("   ?, --       COMM_MODE,\n");
		sb.append("   ?, --       INST_LOC,\n");
		sb.append("   ?, --       INST_DATE,\n");
		sb.append("   ?, --       T_FACTOR,\n");
		sb.append("   ?, --       REF_METER_FLAG,\n");
		sb.append("   ?, --       REF_METER_ID,\n");
		sb.append("   ?, --       VALIDATE_CODE,\n");
		sb.append("   ?, --       MODULE_NO,\n");
		sb.append("   ?, --       MR_FACTOR,\n");
		sb.append("   ?, --       LAST_CHK_DATE,\n");
		sb.append("   ?, --       ROTATE_CYCLE,\n");
		sb.append("   ?, --       ROTATE_VALID_DATE,\n");
		sb.append("   ?, --       CHK_CYCLE,\n");
		sb.append("   ?, --       TMNL_ASSET_NO,\n");
		sb.append("   ?, --       FMR_ASSET_NO,\n");
		sb.append("   ?, --       REG_STATUS,\n");
		sb.append("   ? --       REG_SN\n");
		sb.append("   )");
		String sql = sb.toString();
		return sql;
	}

	/**
	 * 保存电表的 SQL 参数
	 */
	private List<Object> saveCmeterParams(CmeterJdbc cmeterjdbc) {
		List<Object> list = new ArrayList<Object>();
		list.add(cmeterjdbc.getMeterId());
		list.add(cmeterjdbc.getAssetNo());
		list.add(cmeterjdbc.getMpId());
		list.add(cmeterjdbc.getOrgNo());
		list.add(cmeterjdbc.getAreaNo());
		list.add(cmeterjdbc.getConsNo());
		list.add(cmeterjdbc.getBaudrate());
		list.add(cmeterjdbc.getCommNo());
		list.add(cmeterjdbc.getCommAddr1());
		list.add(cmeterjdbc.getCommAddr2());
		list.add(cmeterjdbc.getCommMode());
		list.add(cmeterjdbc.getInstLoc());
		list.add(cmeterjdbc.getInstDate());
		list.add(cmeterjdbc.gettFactor());
		list.add(cmeterjdbc.getRefMeterFlag());
		list.add(cmeterjdbc.getRefMeterId());
		list.add(cmeterjdbc.getValidateCode());
		list.add(cmeterjdbc.getModuleNo());
		list.add(cmeterjdbc.getMrFactor());
		list.add(cmeterjdbc.getLastChkDate());
		list.add(cmeterjdbc.getRotateCycle());
		list.add(cmeterjdbc.getRotateValidDate());
		list.add(cmeterjdbc.getChkCycle());
		list.add(cmeterjdbc.getTmnlAssetNo());
		list.add(cmeterjdbc.getFmrAssetNo());
		list.add(cmeterjdbc.getRegStatus());
		list.add(cmeterjdbc.getRegSn());
		return list;
	}

	/**
	 * 保存采集点和用户关联的sql
	 */
	private String saveRCpConsSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO R_CP_CONS_RELA\n");
		sb.append("  (CP_CONS_ID, CONS_ID, CP_NO)\n");
		sb.append("VALUES\n");
		sb.append("  (S_R_CP_CONS_RELA.NEXTVAL, ?, ?)");
		String sql = sb.toString();
		return sql;
	}

	/**
	 * 保存采集点和用户关联的sql参数
	 */
	private List<Object> saveRCpConsParams(RCpConsRela rCpConsRela) {
		List<Object> list = new ArrayList<Object>();
		list.add(rCpConsRela.getConsId());
		list.add(rCpConsRela.getCpNo());
		return list;
	}

	/**
	 * 保存用户和终端关联的sql
	 */
	private String saveRConsTmnlSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO R_CONS_TMNL_RELA\n");
		sb.append("  (ORG_NO, AREA_NO, CONS_NO, TMNL_ASSET_NO)\n");
		sb.append("VALUES\n");
		sb.append("  ('1', '1', ?, ?)");
		String sql = sb.toString();
		return sql;
	}

	/**
	 * 保存用户和终端关联的sql参数
	 */
	private List<Object> saveRConsTmnlParams(RConsTmnlRela rConsTmnlRela) {
		List<Object> list = new ArrayList<Object>();
		list.add(rConsTmnlRela.getConsNo());
		list.add(rConsTmnlRela.getTmnlAssetNo());
		return list;
	}

	private String saveCmeterMpSql() {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO C_METER_MP_RELA\n");
		sb.append("  (METER_MP_ID,\n");
		sb.append("   METER_ID,\n");
		sb.append("   MP_ID)\n");
		sb.append("VALUES\n");
		sb.append("  (S_METER_MP_ID.NEXTVAL,\n");
		sb.append("   ?,\n");
		sb.append("   ?)");

		String sql = sb.toString();

		return sql;
	}

	private List<Object> saveCmeterMpParams(CMeterMpRela cmetermp) {
		List<Object> list = new ArrayList<Object>();
		list.add(cmetermp.getMeterId());
		list.add(cmetermp.getMpId());
		return list;
	}

	/**
	 * copyGtg
	 * 
	 * @return 复制临时表到GTG的SQL
	 */
	private String copyGtg() {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO G_TG\n");
		sb.append("  (TG_ID,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   TG_NO,\n");
		sb.append("   TG_NAME,\n");
		sb.append("   TG_CAP,\n");
		sb.append("   INST_ADDR,\n");
		sb.append("   CHG_DATE,\n");
		sb.append("   PUB_PRIV_FLAG,\n");
		sb.append("   RUN_STATUS_CODE)\n");
		sb.append("  SELECT TG_ID,\n");
		sb.append("         ORG_NO,\n");
		sb.append("         TG_NO,\n");
		sb.append("         TG_NAME,\n");
		sb.append("         TG_CAP,\n");
		sb.append("         INST_ADDR,\n");
		sb.append("         CHG_DATE,\n");
		sb.append("         PUB_PRIV_FLAG,\n");
		sb.append("         RUN_STATUS_CODE\n");
		sb.append("    FROM G_TG_IMP");

		String sql = sb.toString();
		return sql;

	}

	/**
	 * copyCcons
	 * 
	 * @return 复制临时表到CCons的SQL
	 */
	private String copyCcons() {

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
		sb.append("   APPLY_DATE,\n");
		sb.append("   CONS_SORT)\n");
		sb.append("  SELECT CONS_ID,\n");
		sb.append("         CUST_ID,\n");
		sb.append("         ORG_NO,\n");
		sb.append("         AREA_NO,\n");
		sb.append("         CONS_NO,\n");
		sb.append("         CONS_NAME,\n");
		sb.append("         CUST_NO,\n");
		sb.append("         SUBS_ID,\n");
		sb.append("         TG_ID,\n");
		sb.append("         LINE_ID,\n");
		sb.append("         CUST_QUERY_NO,\n");
		sb.append("         TMP_PAY_RELA_NO,\n");
		sb.append("         ORGN_CONS_NO,\n");
		sb.append("         CONS_SORT_CODE,\n");
		sb.append("         ELEC_ADDR,\n");
		sb.append("         TRADE_CODE,\n");
		sb.append("         CONS_TYPE,\n");
		sb.append("         ELEC_TYPE_CODE,\n");
		sb.append("         CONTRACT_CAP,\n");
		sb.append("         RUN_CAP,\n");
		sb.append("         CAP_GRADE_NO,\n");
		sb.append("         SHIFT_NO,\n");
		sb.append("         LODE_ATTR_CODE,\n");
		sb.append("         VOLT_CODE,\n");
		sb.append("         HEC_INDUSTRY_CODE,\n");
		sb.append("         HOLIDAY,\n");
		sb.append("         BUILD_DATE,\n");
		sb.append("         PS_DATE,\n");
		sb.append("         CANCEL_DATE,\n");
		sb.append("         DUE_DATE,\n");
		sb.append("         NOTIFY_MODE,\n");
		sb.append("         SETTLE_MODE,\n");
		sb.append("         STATUS_CODE,\n");
		sb.append("         RRIO_CODE,\n");
		sb.append("         CHK_CYCLE,\n");
		sb.append("         LAST_CHK_DATE,\n");
		sb.append("         CHECKER_NO,\n");
		sb.append("         POWEROFF_CODE,\n");
		sb.append("         TRANSFER_CODE,\n");
		sb.append("         MR_SECT_NO,\n");
		sb.append("         NOTE_TYPE_CODE,\n");
		sb.append("         TMP_FLAG,\n");
		sb.append("         TMP_DATE,\n");
		sb.append("         APPLY_NO,\n");
		sb.append("         APPLY_DATE,\n");
		sb.append("         CONS_SORT\n");
		sb.append("    FROM C_CONS_IMP");

		String sql = sb.toString();
		return sql;
	}

	/**
	 * copyRtmnlrun
	 * 
	 * @return 复制临时表到Rtmnlrun的SQL
	 */
	private String copyRtmnlrun() {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO R_TMNL_RUN\n");
		sb.append("  (TERMINAL_ID,\n");
		sb.append("   CP_NO,\n");
		sb.append("   TMNL_ASSET_NO,\n");
		sb.append("   TERMINAL_ADDR,\n");
		sb.append("   CIS_ASSET_NO,\n");
		sb.append("   SIM_NO,\n");
		sb.append("   ID,\n");
		sb.append("   FACTORY_CODE,\n");
		sb.append("   ATTACH_METER_FLAG,\n");
		sb.append("   TERMINAL_TYPE_CODE,\n");
		sb.append("   COLL_MODE,\n");
		sb.append("   PROTOCOL_CODE,\n");
		sb.append("   COMM_PASSWORD,\n");
		sb.append("   RUN_DATE,\n");
		sb.append("   STATUS_CODE,\n");
		sb.append("   HARMONIC_DEV_FLAG,\n");
		sb.append("   PS_ENSURE_FLAG,\n");
		sb.append("   AC_SAMPLING_FLAG,\n");
		sb.append("   ELIMINATE_FLAG,\n");
		sb.append("   GATE_ATTR_FLAG,\n");
		sb.append("   PRIO_PS_MODE,\n");
		sb.append("   FREEZE_MODE,\n");
		sb.append("   FREEZE_CYCLE_NUM,\n");
		sb.append("   MAX_LOAD_CURVE_DAYS,\n");
		sb.append("   PS_LINE_LEN,\n");
		sb.append("   WORK_PS,\n");
		sb.append("   SPEAKER_FLAG,\n");
		sb.append("   SPEAKER_DIST,\n");
		sb.append("   SEND_UP_MODE,\n");
		sb.append("   COMM_MODE,\n");
		sb.append("   FRAME_NUMBER,\n");
		sb.append("   POWER_CUT_DATE,\n");
		sb.append("   INSTL_LOC_DIAGRAM,\n");
		sb.append("   WIRING_DIAGRAM)\n");
		sb.append("  SELECT TERMINAL_ID,\n");
		sb.append("         CP_NO,\n");
		sb.append("         TMNL_ASSET_NO,\n");
		sb.append("         TERMINAL_ADDR,\n");
		sb.append("         CIS_ASSET_NO,\n");
		sb.append("         SIM_NO,\n");
		sb.append("         ID,\n");
		sb.append("         FACTORY_CODE,\n");
		sb.append("         ATTACH_METER_FLAG,\n");
		sb.append("         TERMINAL_TYPE_CODE,\n");
		sb.append("         COLL_MODE,\n");
		sb.append("         PROTOCOL_CODE,\n");
		sb.append("         COMM_PASSWORD,\n");
		sb.append("         RUN_DATE,\n");
		sb.append("         STATUS_CODE,\n");
		sb.append("         HARMONIC_DEV_FLAG,\n");
		sb.append("         PS_ENSURE_FLAG,\n");
		sb.append("         AC_SAMPLING_FLAG,\n");
		sb.append("         ELIMINATE_FLAG,\n");
		sb.append("         GATE_ATTR_FLAG,\n");
		sb.append("         PRIO_PS_MODE,\n");
		sb.append("         FREEZE_MODE,\n");
		sb.append("         FREEZE_CYCLE_NUM,\n");
		sb.append("         MAX_LOAD_CURVE_DAYS,\n");
		sb.append("         PS_LINE_LEN,\n");
		sb.append("         WORK_PS,\n");
		sb.append("         SPEAKER_FLAG,\n");
		sb.append("         SPEAKER_DIST,\n");
		sb.append("         SEND_UP_MODE,\n");
		sb.append("         COMM_MODE,\n");
		sb.append("         FRAME_NUMBER,\n");
		sb.append("         POWER_CUT_DATE,\n");
		sb.append("         INSTL_LOC_DIAGRAM,\n");
		sb.append("         WIRING_DIAGRAM\n");
		sb.append("    FROM R_TMNL_RUN_IMP");

		String sql = sb.toString();
		return sql;
	}

	/**
	 * copyCmp
	 * 
	 * @return 从临时表复制CMP
	 */
	private String copyCmp() {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO C_MP\n");
		sb.append("  (MP_ID,\n");
		sb.append("   MP_SECT_ID,\n");
		sb.append("   SP_ID,\n");
		sb.append("   MP_NO,\n");
		sb.append("   MP_NAME,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   CONS_ID,\n");
		sb.append("   CONS_NO,\n");
		sb.append("   TG_ID,\n");
		sb.append("   LINE_ID,\n");
		sb.append("   MR_SECT_NO,\n");
		sb.append("   MP_ADDR,\n");
		sb.append("   TYPE_CODE,\n");
		sb.append("   MP_ATTR_CODE,\n");
		sb.append("   USAGE_TYPE_CODE,\n");
		sb.append("   SIDE_CODE,\n");
		sb.append("   VOLT_CODE,\n");
		sb.append("   APP_DATE,\n");
		sb.append("   RUN_DATE,\n");
		sb.append("   WIRING_MODE,\n");
		sb.append("   MEAS_MODE,\n");
		sb.append("   SWITCH_NO,\n");
		sb.append("   EXCHG_TYPE_CODE,\n");
		sb.append("   MD_TYPE_CODE,\n");
		sb.append("   MR_SN,\n");
		sb.append("   MP_SN,\n");
		sb.append("   METER_FLAG,\n");
		sb.append("   STATUS_CODE,\n");
		sb.append("   LC_FLAG,\n");
		sb.append("   EARTH_MODE)\n");
		sb.append("  SELECT MP_ID,\n");
		sb.append("         MP_SECT_ID,\n");
		sb.append("         SP_ID,\n");
		sb.append("         MP_NO,\n");
		sb.append("         MP_NAME,\n");
		sb.append("         ORG_NO,\n");
		sb.append("         CONS_ID,\n");
		sb.append("         CONS_NO,\n");
		sb.append("         TG_ID,\n");
		sb.append("         LINE_ID,\n");
		sb.append("         MR_SECT_NO,\n");
		sb.append("         MP_ADDR,\n");
		sb.append("         TYPE_CODE,\n");
		sb.append("         MP_ATTR_CODE,\n");
		sb.append("         USAGE_TYPE_CODE,\n");
		sb.append("         SIDE_CODE,\n");
		sb.append("         VOLT_CODE,\n");
		sb.append("         APP_DATE,\n");
		sb.append("         RUN_DATE,\n");
		sb.append("         WIRING_MODE,\n");
		sb.append("         MEAS_MODE,\n");
		sb.append("         SWITCH_NO,\n");
		sb.append("         EXCHG_TYPE_CODE,\n");
		sb.append("         MD_TYPE_CODE,\n");
		sb.append("         MR_SN,\n");
		sb.append("         MP_SN,\n");
		sb.append("         METER_FLAG,\n");
		sb.append("         STATUS_CODE,\n");
		sb.append("         LC_FLAG,\n");
		sb.append("         EARTH_MODE\n");
		sb.append("    FROM C_MP_IMP");

		String sql = sb.toString();
		return sql;
	}

	/**
	 * copyCmeter
	 * 
	 * @return 复制临时表到Cmeter
	 */
	private String copyCmeter() {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO C_METER\n");
		sb.append("  (METER_ID,\n");
		sb.append("   ASSET_NO,\n");
		sb.append("   MP_ID,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   AREA_NO,\n");
		sb.append("   CONS_NO,\n");
		sb.append("   BAUDRATE,\n");
		sb.append("   COMM_NO,\n");
		sb.append("   COMM_ADDR1,\n");
		sb.append("   COMM_ADDR2,\n");
		sb.append("   COMM_MODE,\n");
		sb.append("   INST_LOC,\n");
		sb.append("   INST_DATE,\n");
		sb.append("   T_FACTOR,\n");
		sb.append("   REF_METER_FLAG,\n");
		sb.append("   REF_METER_ID,\n");
		sb.append("   VALIDATE_CODE,\n");
		sb.append("   MODULE_NO,\n");
		sb.append("   MR_FACTOR,\n");
		sb.append("   LAST_CHK_DATE,\n");
		sb.append("   ROTATE_CYCLE,\n");
		sb.append("   ROTATE_VALID_DATE,\n");
		sb.append("   CHK_CYCLE,\n");
		sb.append("   TMNL_ASSET_NO,\n");
		sb.append("   FMR_ASSET_NO,\n");
		sb.append("   REG_STATUS,\n");
		sb.append("   REG_SN)\n");
		sb.append("  SELECT METER_ID,\n");
		sb.append("         ASSET_NO,\n");
		sb.append("         MP_ID,\n");
		sb.append("         ORG_NO,\n");
		sb.append("         AREA_NO,\n");
		sb.append("         CONS_NO,\n");
		sb.append("         BAUDRATE,\n");
		sb.append("         COMM_NO,\n");
		sb.append("         COMM_ADDR1,\n");
		sb.append("         COMM_ADDR2,\n");
		sb.append("         COMM_MODE,\n");
		sb.append("         INST_LOC,\n");
		sb.append("         INST_DATE,\n");
		sb.append("         T_FACTOR,\n");
		sb.append("         REF_METER_FLAG,\n");
		sb.append("         REF_METER_ID,\n");
		sb.append("         VALIDATE_CODE,\n");
		sb.append("         MODULE_NO,\n");
		sb.append("         MR_FACTOR,\n");
		sb.append("         LAST_CHK_DATE,\n");
		sb.append("         ROTATE_CYCLE,\n");
		sb.append("         ROTATE_VALID_DATE,\n");
		sb.append("         CHK_CYCLE,\n");
		sb.append("         TMNL_ASSET_NO,\n");
		sb.append("         FMR_ASSET_NO,\n");
		sb.append("         REG_STATUS,\n");
		sb.append("         REG_SN\n");
		sb.append("    FROM C_METER_IMP");

		String sql = sb.toString();
		return sql;
	}

	/**
	 * updateOrg
	 * 
	 * @return 更新关联表的org_no 和 areaNo
	 */
	private String updateOrg() {

		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE R_CONS_TMNL_RELA R\n");
		sb.append("   SET (R.ORG_NO,\n");
		sb.append("        R.AREA_NO) =\n");
		sb.append("       (SELECT C.ORG_NO,\n");
		sb.append("               C.AREA_NO\n");
		sb.append("          FROM C_CONS_IMP C\n");
		sb.append("         WHERE C.CONS_ID = R.CONS_NO)\n");
		sb
				.append(" WHERE EXISTS (SELECT 1 FROM C_CONS_IMP CI WHERE CI.CONS_ID = R.CONS_NO)");

		// 由于台区用户前加了T，因此用CONS_ID关联(已去掉T)

		String sql = sb.toString();
		return sql;
	}

	/**
	 * logGtg
	 * 
	 * @return 记录g_tg 导入日志
	 */
	private String logGtg() {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ARCHIEVE_IMPLOG\n");
		sb.append("  (ID,\n");
		sb.append("   OBJ_TYPE,\n");
		sb.append("   OBJ_ID,\n");
		sb.append("   IMP_TIME,\n");
		sb.append("   MEMO)\n");
		sb.append("  SELECT S_ARCHIEVE_IMPLOG.NEXTVAL, --id,\n");
		sb.append("         'g_tg', --      obj_type,\n");
		sb.append("         TG_ID, --      obj_id,\n");
		sb.append("         SYSDATE, --      imp_time,\n");
		sb.append("         'tg_id' --      memo\n");
		sb.append("    FROM G_TG_IMP");

		String sql = sb.toString();
		return sql;
	}

	/**
	 * logCcons
	 * 
	 * @return 记录ccons 导入日志
	 */
	private String logCcons() {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ARCHIEVE_IMPLOG\n");
		sb.append("  (ID,\n");
		sb.append("   OBJ_TYPE,\n");
		sb.append("   OBJ_ID,\n");
		sb.append("   IMP_TIME,\n");
		sb.append("   MEMO)\n");
		sb.append("  SELECT S_ARCHIEVE_IMPLOG.NEXTVAL, --id,\n");
		sb.append("         'c_cons', --      obj_type,\n");
		sb.append("         CONS_NO, --      obj_id,\n");
		sb.append("         SYSDATE, --      imp_time,\n");
		sb.append("         'cons_no' --      memo\n");
		sb.append("    FROM C_CONS_IMP");

		String sql = sb.toString();
		return sql;
	}

	/**
	 * logRtmnlrun
	 * 
	 * @return 记录rtmnlrun导入日志
	 */
	private String logRtmnlrun() {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ARCHIEVE_IMPLOG\n");
		sb.append("  (ID,\n");
		sb.append("   OBJ_TYPE,\n");
		sb.append("   OBJ_ID,\n");
		sb.append("   IMP_TIME,\n");
		sb.append("   MEMO)\n");
		sb.append("  SELECT S_ARCHIEVE_IMPLOG.NEXTVAL, --id,\n");
		sb.append("         'r_tmnl_run', --      obj_type,\n");
		sb.append("         TMNL_ASSET_NO, --      obj_id,\n");
		sb.append("         SYSDATE, --      imp_time,\n");
		sb.append("         'tmnl_asset_no' --      memo\n");
		sb.append("    FROM R_TMNL_RUN_IMP");

		String sql = sb.toString();
		return sql;
	}

	/**
	 * logCmp
	 * 
	 * @return cmp导入日志SQL
	 */
	private String logCmp() {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ARCHIEVE_IMPLOG\n");
		sb.append("  (ID,\n");
		sb.append("   OBJ_TYPE,\n");
		sb.append("   OBJ_ID,\n");
		sb.append("   IMP_TIME,\n");
		sb.append("   MEMO)\n");
		sb.append("  SELECT S_ARCHIEVE_IMPLOG.NEXTVAL, --id,\n");
		sb.append("         'c_mp', --      obj_type,\n");
		sb.append("         mp_no, --      obj_id,\n");
		sb.append("         SYSDATE, --      imp_time,\n");
		sb.append("         'mp_no' --      memo\n");
		sb.append("    FROM C_MP_IMP");

		String sql = sb.toString();
		return sql;
	}

	/**
	 * logCmeter
	 * 
	 * @return c_meter导入日志SQL
	 */
	private String logCmeter() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ARCHIEVE_IMPLOG\n");
		sb.append("  (ID,\n");
		sb.append("   OBJ_TYPE,\n");
		sb.append("   OBJ_ID,\n");
		sb.append("   IMP_TIME,\n");
		sb.append("   MEMO)\n");
		sb.append("  SELECT S_ARCHIEVE_IMPLOG.NEXTVAL, --id,\n");
		sb.append("         'c_meter', --      obj_type,\n");
		sb.append("         meter_id, --      obj_id,\n");
		sb.append("         SYSDATE, --      imp_time,\n");
		sb.append("         'meter_id' --      memo\n");
		sb.append("    FROM C_METER_IMP");

		String sql = sb.toString();
		return sql;
	}
	
	/**
	 * 更新 Cmeter 表的 orgNo 的 areaNo
	 * @return 
	 * @describe  更新电能表 org_no 和areaNo为测量点 org_no
	 *            在将 c_meter_imp 更新到 c_meter 前操作
	 */
	private String updateMeterSql()
	{
		StringBuffer sb=new StringBuffer();
		sb.append("UPDATE C_METER_IMP M\n");
		sb.append("   SET (M.ORG_NO,\n");
		sb.append("        M.AREA_NO,\n");
		sb.append("        M.CONS_NO) =\n");
		sb.append("       (SELECT MP.ORG_NO,\n");
		sb.append("               SUBSTR(MP.ORG_NO,\n");
		sb.append("                      0,\n");
		sb.append("                      LENGTH(MP.ORG_NO) - 2),\n");
		sb.append("               MP.CONS_NO\n");
		sb.append("          FROM C_MP MP\n");
		sb.append("         WHERE MP.MP_ID = M.MP_ID)");

		String sql=sb.toString();		
		return sql;
	}

	/**
	 * createRCollObj
	 * 
	 * @return 造RCollObj数据的SQL
	 */
	private String createRCollObj() {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO R_COLL_OBJ\n");
		sb.append("  (COLL_OBJ_ID,\n");
		sb.append("   METER_ID,\n");
		sb.append("   CP_NO,\n");
		sb.append("   COLL_PORT,\n");
		sb.append("   CT_RATIO,\n");
		sb.append("   PT_RATIO,\n");
		sb.append("   T_FACTOR,\n");
		sb.append("   METER_CONST,\n");
		sb.append("   PORT_NO,\n");
		sb.append("   PULSE_ATTR)\n");
		sb.append("  SELECT S_E_DATA_MP.NEXTVAL, --coll_obj_id,\n");
		sb.append("         C.METER_ID, --       meter_id,\n");
		sb.append("         R.CP_NO, --       cp_no,\n");
		sb.append("         ROWNUM, --       coll_port,\n");
		sb.append("         NULL, --       ct_ratio,\n");
		sb.append("         NULL, --       pt_ratio,\n");
		sb.append("         NULL, --       t_factor,\n");
		sb.append("         NULL, --       meter_const,\n");
		sb.append("         NULL, --       port_no,\n");
		sb.append("         NULL --       pulse_attr\n");
		sb.append("    FROM C_METER    C,\n");
		sb.append("         R_TMNL_RUN R\n");
		sb.append("   WHERE C.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n");
		sb.append("     AND EXISTS\n");
		sb
				.append("   (SELECT 1 FROM C_METER_IMP CMI WHERE CMI.METER_ID = C.METER_ID)");

		String sql = sb.toString();

		return sql;
	}

	/**
	 * createEDatamp
	 * 
	 * @return 造EDataMp 数据的SQL
	 */
	private String createEDataMp() {

		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("INSERT INTO E_DATA_MP\n");
		sb.append("  (ID,\n");
		sb.append("   AREA_CODE,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   AREA_NO,\n");
		sb.append("   CONS_NO,\n");
		sb.append("   TMNL_ASSET_NO,\n");
		sb.append("   CIS_TMNL_ASSET_NO,\n");
		sb.append("   TERMINAL_ADDR,\n");
		sb.append("   METER_ID,\n");
		sb.append("   ASSET_NO,\n");
		sb.append("   COMM_ADDR,\n");
		sb.append("   MP_TYPE,\n");
		sb.append("   COLL_OBJ_ID,\n");
		sb.append("   DATA_SRC,\n");
		sb.append("   MP_SN,\n");
		sb.append("   PT,\n");
		sb.append("   CT,\n");
		sb.append("   SELF_FACTOR,\n");
		sb.append("   CALC_MODE,\n");
		sb.append("   DISABLE_DATE,\n");
		sb.append("   IS_VALID)\n");
		sb.append("  SELECT S_E_DATA_MP.NEXTVAL, --id,\n");
		sb.append("         '03', --       area_code,\n");
		sb.append("         VW.ORG_NO, --       org_no,\n");
		sb.append("         VW.AREA_NO, --       area_no,\n");
		sb.append("         VW.CONS_NO, --       cons_no,\n");
		sb.append("         VW.TMNL_ASSET_NO, --       tmnl_asset_no,\n");
		sb.append("         VW.CIS_ASSET_NO, --       cis_tmnl_asset_no,\n");
		sb.append("         VW.TERMINAL_ADDR, --       terminal_addr,\n");
		sb.append("         C.METER_ID, --       meter_id,\n");
		sb.append("         C.ASSET_NO, --       asset_no,\n");
		sb.append("         C.COMM_ADDR1, --       comm_addr,\n");
		sb.append("         NULL, --       mp_type,\n");
		sb.append("         R.COLL_OBJ_ID, --       coll_obj_id,\n");
		sb.append("         C.REG_STATUS, --       data_src,\n");
		sb.append("         C.REG_SN, --       mp_sn,m.mp_sn\n");
		sb.append("         R.PT_RATIO, --       pt,\n");
		sb.append("         R.CT_RATIO, --       ct,\n");
		sb.append("         NULL, --       self_factor,\n");
		sb.append("         NULL, --       calc_mode,\n");
		sb.append("         NULL, --       disable_date,\n");
		sb.append("         '1' --       is_valid\n");
		sb.append("\n");
		sb.append("    FROM C_METER     C,\n");
		sb.append("         VW_TMNL_RUN VW,\n");
		sb.append("         R_COLL_OBJ  R,\n");
		sb.append("         C_MP        M\n");
		sb.append("   WHERE C.TMNL_ASSET_NO = VW.TMNL_ASSET_NO\n");
		sb.append("     AND C.METER_ID = R.METER_ID\n");
		sb.append("     AND C.MP_ID = M.MP_ID\n");
		sb.append("     AND EXISTS\n");
		sb
				.append("   (SELECT 1 FROM C_METER_IMP CMI WHERE CMI.METER_ID = C.METER_ID)");

		String sql = sb.toString();
		return sql;
	}
	
	
	/**
	 * tmnlEDataSql
	 * @return 造终端E_DATA_MP
	 */
	private String tmnlEDataSql()
	{


		StringBuffer sb=new StringBuffer();
		sb.append("INSERT INTO E_DATA_MP\n");
		sb.append("  (ID,\n");
		sb.append("   AREA_CODE,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   AREA_NO,\n");
		sb.append("   CONS_NO,\n");
		sb.append("   TMNL_ASSET_NO,\n");
		sb.append("   CIS_TMNL_ASSET_NO,\n");
		sb.append("   TERMINAL_ADDR,\n");
		sb.append("   METER_ID,\n");
		sb.append("   ASSET_NO,\n");
		sb.append("   COMM_ADDR,\n");
		sb.append("   MP_TYPE,\n");
		sb.append("   COLL_OBJ_ID,\n");
		sb.append("   DATA_SRC,\n");
		sb.append("   MP_SN,\n");
		sb.append("   PT,\n");
		sb.append("   CT,\n");
		sb.append("   SELF_FACTOR,\n");
		sb.append("   CALC_MODE,\n");
		sb.append("   DISABLE_DATE,\n");
		sb.append("   IS_VALID)\n");
		sb.append("  SELECT S_E_DATA_MP.NEXTVAL, --id,\n");
		sb.append("         '03', --       area_code,\n");
		sb.append("         VW.ORG_NO, --       org_no,\n");
		sb.append("         VW.AREA_NO, --       area_no,\n");
		sb.append("         VW.CONS_NO, --       cons_no,\n");
		sb.append("         VW.TMNL_ASSET_NO, --       tmnl_asset_no,\n");
		sb.append("         VW.CIS_ASSET_NO, --       cis_tmnl_asset_no,\n");
		sb.append("         VW.TERMINAL_ADDR, --       terminal_addr,\n");
		sb.append("         '', --       meter_id,\n");
		sb.append("         VW.TMNL_ASSET_NO, --       asset_no,\n");
		sb.append("         '', --       comm_addr,\n");
		sb.append("         NULL, --       mp_type,\n");
		sb.append("         S_E_DATA_MP.CURRVAL, --       coll_obj_id,\n");
		sb.append("         '01', --       data_src,\n");
		sb.append("         0, --       mp_sn,m.mp_sn\n");
		sb.append("         1, --       pt,\n");
		sb.append("         1, --       ct,\n");
		sb.append("         NULL, --       self_factor,\n");
		sb.append("         NULL, --       calc_mode,\n");
		sb.append("         NULL, --       disable_date,\n");
		sb.append("         '1' --       is_valid\n");
		sb.append("    FROM VW_TMNL_RUN VW\n");
		sb.append("   WHERE EXISTS (SELECT * \n");
		sb.append("            FROM R_TMNL_RUN_IMP RI\n");
		sb.append("           WHERE RI.TERMINAL_ID = VW.TERMINAL_ID)");

		String sql=sb.toString();

		return sql;
	}

}
