package com.nari.baseapp.datagathorman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.datagatherman.ATmnlCollQuality;
import com.nari.baseapp.datagatherman.CommMode;
import com.nari.baseapp.datagatherman.ConsType;
import com.nari.baseapp.datagatherman.GatherByHandDto;
import com.nari.baseapp.datagatherman.MissingTerminalDTO;
import com.nari.baseapp.datagatherman.TerminalTypeCode;
import com.nari.baseapp.datagatherman.TmnlModeCode;
import com.nari.baseapp.datagathorman.GatherQualityEvaluateJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.runman.feildman.TerminalInfoBean;
import com.nari.support.Page;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.NodeTypeUtils;
import com.nari.util.exception.DBAccessException;

public class GatherQualityEvaluateJdbcDaoImpl extends JdbcBaseDAOImpl implements
		GatherQualityEvaluateJdbcDao {

	/**
	 * 根据操作员单位编码（orgNO）和指定的时间段查询其下属单位的采集质量情况
	 * 
	 * @param orgType
	 *            操作员单位级别
	 * @param orgNo
	 *            操作员单位编码
	 * @param startDate
	 *            起始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<ATmnlCollQuality> findGatherQuality(PSysUser pSysUser,
			String orgType, String orgNo, String startDate, String endDate)
			throws DBAccessException {
		List<ATmnlCollQuality> list = new ArrayList<ATmnlCollQuality>();
		if (pSysUser == null)
			return list;
		StringBuffer sb = new StringBuffer();
		if ("02".equals(pSysUser.getAccessLevel())) {
			sb.append("SELECT ORG_NO,\n");
			sb.append("       ORG_SHORT_NAME,\n");
			sb.append("       (FIRST_TIMES01 + REPAIR_TIMES01) /\n");
			sb
					.append("       (DEMAND_TIMES01 - POWER_CUT_TIMES01) AS RATE1,\n");
			sb.append("       (FIRST_TIMES02 + REPAIR_TIMES02) /\n");
			sb
					.append("       (DEMAND_TIMES02 - POWER_CUT_TIMES02) AS RATE2,\n");
			sb.append("       (FIRST_TIMES03 + REPAIR_TIMES03) /\n");
			sb.append("       (DEMAND_TIMES03 - POWER_CUT_TIMES03) AS RATE3\n");
			sb.append("  FROM (SELECT ORG.ORG_NO,\n");
			sb.append("               ORG.ORG_SHORT_NAME,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '01', A.FIRST_TIMES, NULL)) AS FIRST_TIMES01,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '01', A.REPAIR_TIMES, NULL)) AS REPAIR_TIMES01,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '01', A.DEMAND_TIMES, NULL)) AS DEMAND_TIMES01,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '01', A.POWER_CUT_TIMES, NULL)) AS POWER_CUT_TIMES01,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '02', A.FIRST_TIMES, NULL)) AS FIRST_TIMES02,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '02', A.REPAIR_TIMES, NULL)) AS REPAIR_TIMES02,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '02', A.DEMAND_TIMES, NULL)) AS DEMAND_TIMES02,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '02', A.POWER_CUT_TIMES, NULL)) AS POWER_CUT_TIMES02,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '03', A.FIRST_TIMES, NULL)) AS FIRST_TIMES03,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '03', A.REPAIR_TIMES, NULL)) AS REPAIR_TIMES03,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '03', A.DEMAND_TIMES, NULL)) AS DEMAND_TIMES03,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '03', A.POWER_CUT_TIMES, NULL)) AS POWER_CUT_TIMES03\n");
			sb.append("          FROM A_TMNL_COLL_QUALITY A, O_ORG ORG\n");
			sb.append("         WHERE A.ORG_NO = ORG.ORG_NO\n");
			sb.append("           AND ORG.ORG_NO IN\n");
			sb.append("               (SELECT DISTINCT (PO.ORG_NO)\n");
			sb
					.append("                  FROM P_ACCESS_ORG PO, O_ORG O1, O_ORG O2, P_SYS_USER PS\n");
			sb.append("                 WHERE PO.STAFF_NO = PS.STAFF_NO\n");
			sb.append("                   AND PS.STAFF_NO = ?\n");
			sb.append("                   AND PO.ORG_TYPE = O1.ORG_TYPE\n");
			sb.append("                   AND O1.P_ORG_NO = O2.ORG_NO\n");
			sb
					.append("                   AND O2.ORG_TYPE = PS.ACCESS_LEVEL)\n");
			sb
					.append("           AND A.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n");
			sb.append("               TO_DATE(?, 'yyyy-MM-dd')\n");
			sb.append("         GROUP BY ORG.ORG_NO, ORG.ORG_SHORT_NAME)\n");
			sb.append(" ORDER BY ORG_NO");
		} else if ("03".equals(pSysUser.getAccessLevel())) {
			sb.append("SELECT T.P_ORG_NO AS ORG_NO,\n");
			sb.append("       O.ORG_SHORT_NAME,\n");
			sb.append("       (T.FIRST_TIMES01 + T.REPAIR_TIMES01) /\n");
			sb
					.append("       (T.DEMAND_TIMES01 - T.POWER_CUT_TIMES01) AS RATE1,\n");
			sb.append("       (T.FIRST_TIMES02 + T.REPAIR_TIMES02) /\n");
			sb
					.append("       (T.DEMAND_TIMES02 - T.POWER_CUT_TIMES02) AS RATE2,\n");
			sb.append("       (T.FIRST_TIMES03 + T.REPAIR_TIMES03) /\n");
			sb
					.append("       (T.DEMAND_TIMES03 - T.POWER_CUT_TIMES03) AS RATE3\n");
			sb.append("  FROM (SELECT ORG.P_ORG_NO,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '01', A.FIRST_TIMES, NULL)) AS FIRST_TIMES01,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '01', A.REPAIR_TIMES, NULL)) AS REPAIR_TIMES01,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '01', A.DEMAND_TIMES, NULL)) AS DEMAND_TIMES01,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '01', A.POWER_CUT_TIMES, NULL)) AS POWER_CUT_TIMES01,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '02', A.FIRST_TIMES, NULL)) AS FIRST_TIMES02,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '02', A.REPAIR_TIMES, NULL)) AS REPAIR_TIMES02,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '02', A.DEMAND_TIMES, NULL)) AS DEMAND_TIMES02,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '02', A.POWER_CUT_TIMES, NULL)) AS POWER_CUT_TIMES02,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '03', A.FIRST_TIMES, NULL)) AS FIRST_TIMES03,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '03', A.REPAIR_TIMES, NULL)) AS REPAIR_TIMES03,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '03', A.DEMAND_TIMES, NULL)) AS DEMAND_TIMES03,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '03', A.POWER_CUT_TIMES, NULL)) AS POWER_CUT_TIMES03\n");
			sb.append("          FROM A_TMNL_COLL_QUALITY A, O_ORG ORG\n");
			sb.append("         WHERE A.ORG_NO = ORG.ORG_NO\n");
			sb.append("           AND ORG.P_ORG_NO IN\n");
			sb.append("               (SELECT DISTINCT (PO.ORG_NO)\n");
			sb
					.append("                  FROM P_ACCESS_ORG PO, O_ORG O1, O_ORG O2, P_SYS_USER PS\n");
			sb.append("                 WHERE PO.STAFF_NO = PS.STAFF_NO\n");
			sb.append("                   AND PS.STAFF_NO = ?\n");
			sb.append("                   AND PO.ORG_TYPE = O1.ORG_TYPE\n");
			sb.append("                   AND O1.P_ORG_NO = O2.ORG_NO\n");
			sb
					.append("                   AND O2.ORG_TYPE = PS.ACCESS_LEVEL)\n");
			sb
					.append("           AND A.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n");
			sb.append("               TO_DATE(?, 'yyyy-MM-dd')\n");
			sb.append("         GROUP BY ORG.P_ORG_NO) T,\n");
			sb.append("       O_ORG O\n");
			sb.append(" WHERE O.ORG_NO = T.P_ORG_NO\n");
			sb.append(" ORDER BY T.P_ORG_NO");
		} else if ("04".equals(pSysUser.getAccessLevel())) {
			sb.append("SELECT ORG_NO,\n");
			sb.append("       ORG_SHORT_NAME,\n");
			sb.append("       (FIRST_TIMES01 + REPAIR_TIMES01) /\n");
			sb
					.append("       (DEMAND_TIMES01 - POWER_CUT_TIMES01) AS RATE1,\n");
			sb.append("       (FIRST_TIMES02 + REPAIR_TIMES02) /\n");
			sb
					.append("       (DEMAND_TIMES02 - POWER_CUT_TIMES02) AS RATE2,\n");
			sb.append("       (FIRST_TIMES03 + REPAIR_TIMES03) /\n");
			sb.append("       (DEMAND_TIMES03 - POWER_CUT_TIMES03) AS RATE3\n");
			sb.append("  FROM (SELECT ORG.ORG_NO,\n");
			sb.append("               ORG.ORG_SHORT_NAME,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '01', A.FIRST_TIMES, NULL)) AS FIRST_TIMES01,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '01', A.REPAIR_TIMES, NULL)) AS REPAIR_TIMES01,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '01', A.DEMAND_TIMES, NULL)) AS DEMAND_TIMES01,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '01', A.POWER_CUT_TIMES, NULL)) AS POWER_CUT_TIMES01,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '02', A.FIRST_TIMES, NULL)) AS FIRST_TIMES02,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '02', A.REPAIR_TIMES, NULL)) AS REPAIR_TIMES02,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '02', A.DEMAND_TIMES, NULL)) AS DEMAND_TIMES02,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '02', A.POWER_CUT_TIMES, NULL)) AS POWER_CUT_TIMES02,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '03', A.FIRST_TIMES, NULL)) AS FIRST_TIMES03,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '03', A.REPAIR_TIMES, NULL)) AS REPAIR_TIMES03,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '03', A.DEMAND_TIMES, NULL)) AS DEMAND_TIMES03,\n");
			sb
					.append("               SUM(DECODE(TASK_PROPERTY, '03', A.POWER_CUT_TIMES, NULL)) AS POWER_CUT_TIMES03\n");
			sb.append("          FROM A_TMNL_COLL_QUALITY A, O_ORG ORG\n");
			sb.append("         WHERE A.ORG_NO = ORG.ORG_NO\n");
			sb
					.append("           AND ORG.ORG_NO IN (SELECT DISTINCT (PO.ORG_NO)\n");
			sb.append("                                FROM P_ACCESS_ORG PO\n");
			sb.append("                               WHERE PO.STAFF_NO = ?\n");
			sb
					.append("                                 AND PO.ORG_TYPE = '04')\n");
			sb
					.append("           AND A.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n");
			sb.append("               TO_DATE(?, 'yyyy-MM-dd')\n");
			sb.append("         GROUP BY ORG.ORG_NO, ORG.ORG_SHORT_NAME)\n");
			sb.append(" ORDER BY ORG_NO");
		} else {
			return list;
		}
		try {
			this.logger.debug(sb.toString());
			list = this.getJdbcTemplate().query(sb.toString(),
					new Object[] { pSysUser.getStaffNo(), startDate, endDate },
					new gatherQualityRowMapper());
			return list;
		} catch (RuntimeException e) {
			this.logger.debug("查询采集质量成功率出错！");
			e.printStackTrace();
			throw e;
		}
	}

	class gatherQualityRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			try {
				DecimalFormat df = new DecimalFormat("#.#");
				ATmnlCollQuality aTmnlCollQuality = new ATmnlCollQuality();
				aTmnlCollQuality.setOrgNo(rs.getString("ORG_NO"));
				aTmnlCollQuality.setOrgName(rs.getString("ORG_SHORT_NAME"));
				String sucRate1 = rs.getString("RATE1");
				String sucRate2 = rs.getString("RATE2");
				String sucRate3 = rs.getString("RATE3");
				Double succRate1 = 100 * rs.getDouble("RATE1");
				Double succRate2 = 100 * rs.getDouble("RATE2");
				Double succRate3 = 100 * rs.getDouble("RATE3");
				aTmnlCollQuality.setSuccRate1(sucRate1 == null ? "" : df
						.format(succRate1));
				aTmnlCollQuality.setSuccRate2(sucRate2 == null ? "" : df
						.format(succRate2));
				aTmnlCollQuality.setSuccRate3(sucRate3 == null ? "" : df
						.format(succRate3));
				aTmnlCollQuality.setFailRate1(sucRate1 == null ? "" : df
						.format(100 - succRate1));
				aTmnlCollQuality.setFailRate2(sucRate2 == null ? "" : df
						.format(100 - succRate2));
				aTmnlCollQuality.setFailRate3(sucRate3 == null ? "" : df
						.format(100 - succRate3));
				return aTmnlCollQuality;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 根据用户查询未召信息
	 * 
	 * @param consNo
	 *            户号
	 * @param startDate
	 *            起始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 * @throws DBAccessException
	 */
	public Page<GatherByHandDto> findUnGatherInfoByUsr(long start, int limit,
			String consNo, String startDate, String endDate, String dataType)
			throws DBAccessException {
		Page<GatherByHandDto> pgb = new Page<GatherByHandDto>();
		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT * FROM (\n")
				.append("SELECT O.ORG_NAME,\n")
				.append("             C.CONS_NO,\n")
				.append("             R.TERMINAL_ADDR,  C.CONS_NAME ,\n")
				.append("             AT.IS_ONLINE, BP.PROTOCOL_NAME,\n")
				// .append("             C.CONS_NAME ,COUNT(T.TMNL_ASSET_NO) AS COUNTS\n")
				.append("		 T.ID, T.TMNL_ASSET_NO, T.DATA_SRC, T.MP_SN, \n")
				.append(
						"        T.IS_DENIZEN, T.TEMPLATE_ID, T.STAT_DATE, T.APPLY_CNT, \n")
				.append("        T.MISSING_CNT, T.PROT_NO_LIST, T.DENIZEN_MP\n")
				.append(
						"        FROM R_TMNL_MISSING_MARK T, O_ORG O, C_CONS C, E_DATA_MP E, R_TMNL_RUN R, \n")
				.append("               A_TMNL_REAL_TIME  AT,\n")
				.append("               B_CLEAR_PROTOCOL  BP,\n")
				.append("       T_TASK_TEMPLATE  TS\n")
				.append("       WHERE E.CONS_NO LIKE ?\n")
				.append("         AND C.CONS_NO = E.CONS_NO\n")
				.append("  AND T.PROT_NO_LIST = BP.PROTOCOL_NO\n")
				.append("  AND AT.TMNL_ASSET_NO = T.TMNL_ASSET_NO\n")
				.append("         AND T.DENIZEN_MP LIKE ?\n")
				.append("         AND T.ID = E.ID\n")
				.append(
						" AND T.TEMPLATE_ID = TS.TEMPLATE_ID AND TS.TASK_PROPERTY = ?")
				.append(" AND T.RECALL_CNT < ?")
				.append("         AND R.TMNL_ASSET_NO = T.TMNL_ASSET_NO\n")
				.append("         AND O.ORG_NO = T.ORG_NO\n")
				.append(
						"         AND T.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n")
				.append("             TO_DATE(?, 'yyyy-MM-dd')\n")
				.append(
						" ORDER BY AT.IS_ONLINE DESC, C.CONS_NO, T.TMNL_ASSET_NO, T.STAT_DATE, T.MP_SN)");
		// .append("       GROUP BY O.ORG_NAME, C.CONS_NO, C.CONS_NAME, R.TERMINAL_ADDR)");

		consNo = "%" + consNo + "%";
		try {
			this.logger.debug(sb.toString());
			pgb = this.pagingFind(sb.toString(), start, limit,
					new gatherByHandRowMapper(), new Object[] { consNo, "%0%",
							dataType, "4", startDate, endDate });
			return pgb;
		} catch (RuntimeException e) {
			this.logger.debug("根据用户查询未召信息出错！");
			throw e;
		}
	}

	/**
	 * 根据群组查询未召信息
	 * 
	 * @param groupNo
	 *            群组编号
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param groupType
	 *            群组类型
	 * @return
	 * @throws DBAccessException
	 */
	public Page<GatherByHandDto> findUnGatherInfoByGrp(long start, int limit,
			String groupNo, String startDate, String endDate, String groupType,
			String dataType) throws DBAccessException {
		Page<GatherByHandDto> pgb = new Page<GatherByHandDto>();
		try {
			StringBuffer sb = new StringBuffer();
			sb
					.append("SELECT *\n")
					.append("  FROM (SELECT O.ORG_NAME,\n")
					.append("               C.CONS_NO,\n")
					.append("               R.TERMINAL_ADDR,\n")
					.append("               C.CONS_NAME,\n")
					.append("             AT.IS_ONLINE, BP.PROTOCOL_NAME,\n")
					// .append("               COUNT(T.TMNL_ASSET_NO) AS COUNTS\n")
					.append("		 T.ID, T.TMNL_ASSET_NO, T.DATA_SRC, T.MP_SN, \n")
					.append(
							"        T.IS_DENIZEN, T.TEMPLATE_ID, T.STAT_DATE, T.APPLY_CNT, \n")
					.append(
							"        T.MISSING_CNT, T.PROT_NO_LIST, T.DENIZEN_MP\n")
					.append("          FROM R_TMNL_MISSING_MARK T,\n").append(
							"               O_ORG               O,\n").append(
							"               C_CONS              C,\n").append(
							"               R_TMNL_RUN          R,\n").append(
							"               E_DATA_MP           E,\n").append(
							"               A_TMNL_REAL_TIME  AT,\n").append(
							"               B_CLEAR_PROTOCOL  BP,\n").append(
							"       T_TASK_TEMPLATE  TS,\n");
			if (NodeTypeUtils.NODETYPE_CGP.equals(groupType)) {
				sb.append("               T_TMNL_GROUP_DETAIL G\n");
			} else if (NodeTypeUtils.NODETYPE_UGP.equals(groupType)) {
				sb.append("               R_USER_GROUP_DETAIL G\n");
			} else {
				return pgb;
			}
			sb
					.append("         WHERE G.GROUP_NO = ?\n")
					.append("	  		AND G.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n")
					.append("           AND C.CONS_NO = E.CONS_NO\n")
					.append("           AND T.ID = E.ID\n")
					.append("  AND T.PROT_NO_LIST = BP.PROTOCOL_NO\n")
					.append("  AND AT.TMNL_ASSET_NO = T.TMNL_ASSET_NO\n")
					.append("         AND T.DENIZEN_MP LIKE ?\n")
					.append(
							" AND T.TEMPLATE_ID = TS.TEMPLATE_ID AND TS.TASK_PROPERTY = ?")
					.append(" AND T.RECALL_CNT < ?")
					.append(
							"           AND R.TMNL_ASSET_NO = T.TMNL_ASSET_NO\n")
					.append("           AND O.ORG_NO = T.ORG_NO\n")
					.append(
							"           AND T.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n")
					.append("               TO_DATE(?, 'yyyy-MM-dd')\n")
					.append(
							" ORDER BY AT.IS_ONLINE DESC, C.CONS_NO, T.TMNL_ASSET_NO, T.STAT_DATE, T.MP_SN)");
			// .append("         GROUP BY O.ORG_NAME, C.CONS_NO, C.CONS_NAME, R.TERMINAL_ADDR)");

			this.logger.debug(sb.toString());
			pgb = this.pagingFind(sb.toString(), start, limit,
					new gatherByHandRowMapper(), new Object[] { groupNo, "%0%",
							dataType, "4", startDate, endDate });
			return pgb;
		} catch (RuntimeException e) {
			this.logger.debug("根据用户查询未召信息出错！");
			throw e;
		}
	}

	/**
	 * 根据线路或变电站查询未召信息
	 * 
	 * @param nodeValue
	 *            节点值（线路id/变电站id）
	 * @param nodeType
	 *            节点类型（线路/变电站）
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 * @throws DBAccessException
	 */
	public Page<GatherByHandDto> findUnGatherInfo(PSysUser pSysUser,
			long start, int limit, String nodeValue, String nodeType,
			String startDate, String endDate, String dataType)
			throws DBAccessException {
		Page<GatherByHandDto> pgb = new Page<GatherByHandDto>();
		List<Object> paraList = new ArrayList<Object>();
		try {

			StringBuffer sb = new StringBuffer();
			sb.append("SELECT O.ORG_NAME,\n");
			sb.append("       C.CONS_NO,\n");
			sb.append("       R.TERMINAL_ADDR,\n");
			sb.append("       C.CONS_NAME,\n");
			// sb.append("       CM.MP_NO,\n");
			// sb.append("       CM.MP_NAME,\n");
			sb.append("       E.ASSET_NO AS MP_NO,\n");
			sb.append("       AT.IS_ONLINE,\n");
			sb.append("       BP.PROTOCOL_NAME,\n");
			sb.append("       TMP.ID,\n");
			sb.append("       TMP.TMNL_ASSET_NO,\n");
			sb.append("       TMP.DATA_SRC,\n");
			sb.append("       TMP.MP_SN,\n");
			sb.append("       TMP.IS_DENIZEN,\n");
			sb.append("       TMP.TEMPLATE_ID,\n");
			sb.append("       TMP.STAT_DATE,\n");
			sb.append("       TMP.APPLY_CNT,\n");
			sb.append("       TMP.MISSING_CNT,\n");
			sb.append("       TMP.PROT_NO_LIST,\n");
			sb.append("       TMP.DENIZEN_MP\n");
			sb.append("  FROM O_ORG O,\n");
			if ("cgp".equals(nodeType)) {
				sb.append(",        T_TMNL_GROUP_DETAIL G\n");
			} else if ("ugp".equals(nodeType)) {
				sb.append(",        R_USER_GROUP_DETAIL G\n");
			}
			sb.append("       C_CONS C,\n");
			sb.append("       R_TMNL_RUN R,\n");
			// sb.append("       C_METER_MP_RELA CMMPR,\n");
			// sb.append("       C_MP CM,\n");
			sb.append("       A_TMNL_REAL_TIME AT,\n");
			sb.append("       B_CLEAR_PROTOCOL BP,\n");
			sb.append("       E_DATA_MP E,\n");
			sb.append("       (SELECT T.ID,\n");
			sb.append("               T.ORG_NO,\n");
			sb.append("               T.TMNL_ASSET_NO,\n");
			sb.append("               T.DATA_SRC,\n");
			sb.append("               T.MP_SN,\n");
			sb.append("               T.IS_DENIZEN,\n");
			sb.append("               T.TEMPLATE_ID,\n");
			sb.append("               T.STAT_DATE,\n");
			sb.append("               T.APPLY_CNT,\n");
			sb.append("               T.MISSING_CNT,\n");
			sb.append("               T.PROT_NO_LIST,\n");
			sb.append("               T.DENIZEN_MP\n");
			sb
					.append("          FROM R_TMNL_MISSING_MARK T, T_TASK_TEMPLATE TS\n");
			sb.append("         WHERE T.TEMPLATE_ID = TS.TEMPLATE_ID\n");
			sb.append("           AND T.DENIZEN_MP LIKE '%0%'\n");
			paraList.add(dataType);
			sb.append("           AND TS.TASK_PROPERTY = ?\n");
			sb.append("           AND T.RECALL_CNT < 4\n");
			paraList.add(startDate);
			sb
					.append("           AND T.STAT_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND\n");
			paraList.add(endDate);
			sb.append("               TO_DATE(?, 'YYYY-MM-DD')) TMP\n");
			// sb.append(" WHERE E.METER_ID = CMMPR.METER_ID\n");
			// sb.append("   AND CMMPR.MP_ID = CM.MP_ID\n");
			sb.append(" WHERE E.MP_SN = TMP.MP_SN\n");
			sb.append("   AND TMP.ID = E.ID\n");
			sb.append("   AND C.CONS_NO = E.CONS_NO\n");
			sb.append("   AND TMP.PROT_NO_LIST = BP.PROTOCOL_NO\n");
			sb.append("   AND R.TMNL_ASSET_NO = TMP.TMNL_ASSET_NO\n");
			sb.append("   AND AT.TMNL_ASSET_NO = TMP.TMNL_ASSET_NO\n");
			sb.append("   AND O.ORG_NO = TMP.ORG_NO\n");

			if ("org".equals(nodeType)) {
				paraList.add(nodeValue + "%");
				sb.append("  AND C.ORG_NO like ?");
				sb.append(SysPrivilige.addPri(pSysUser, "C", "R", 7));
				paraList.add(pSysUser.getStaffNo());
				paraList.add(pSysUser.getStaffNo());
				paraList.add(pSysUser.getStaffNo());
			} else if ("line".equals(nodeType)) {
				paraList.add(nodeValue);
				sb.append("  AND  C.LINE_ID = ?");
				sb.append(SysPrivilige.addPri(pSysUser, "C", "R", 7));
				paraList.add(pSysUser.getStaffNo());
				paraList.add(pSysUser.getStaffNo());
				paraList.add(pSysUser.getStaffNo());
			} else if ("ugp".equals(nodeType) || "cgp".equals(nodeType)) {
				paraList.add(nodeValue);
				sb.append("   AND G.CONS_NO = C.CONS_NO\n");
				sb.append("   AND G.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n");
				sb.append("   AND G.GROUP_NO = ?");
				sb.append(SysPrivilige.addPri(pSysUser, "C", "R", 7));
				paraList.add(pSysUser.getStaffNo());
				paraList.add(pSysUser.getStaffNo());
				paraList.add(pSysUser.getStaffNo());
			} else if ("usr".equals(nodeType)) {
				paraList.add(nodeValue);
				sb.append("   AND R.TMNL_ASSET_NO = ?");
			} else {
				pgb = new Page<GatherByHandDto>();
				return pgb;
			}
			sb
					.append(" ORDER BY AT.IS_ONLINE DESC, C.CONS_NO, TMP.TMNL_ASSET_NO, TMP.STAT_DATE, TMP.MP_SN");
			this.logger.debug(sb.toString());
			pgb = this.pagingFind(sb.toString(), start, limit,
					new gatherByHandRowMapper(), paraList.toArray());
			return pgb;
		} catch (RuntimeException e) {
			this.logger.debug("根据用户查询未召信息出错！");
			throw e;
		}
	}

	/**
	 * 根据供电所、区县查询未召信息
	 * 
	 * @param orgNo
	 *            单位编码
	 * @param orgType
	 *            单位级别
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 * @throws DBAccessException
	 */
	public Page<GatherByHandDto> findUnGatherInfoByOrg(PSysUser pSysUser,
			long start, int limit, String orgNo, String orgType,
			String startDate, String endDate, String dataType)
			throws DBAccessException {
		Page<GatherByHandDto> pgb = new Page<GatherByHandDto>();
		try {
			StringBuffer sb = new StringBuffer();
			if ("06".equals(orgType)) {
				sb
						.append("SELECT O.ORG_NAME,\n")
						.append("       C.CONS_NO,\n")
						.append("       R.TERMINAL_ADDR,\n")
						.append("       C.CONS_NAME,\n")
						.append("             CM.MP_NO, CM.MP_NAME,\n")
						.append(
								"             AT.IS_ONLINE, BP.PROTOCOL_NAME,\n")
						// .append("       COUNT(T.TMNL_ASSET_NO) AS COUNTS\n")
						.append(
								"		 T.ID, T.TMNL_ASSET_NO, T.DATA_SRC, T.MP_SN, \n")
						.append(
								"        T.IS_DENIZEN, T.TEMPLATE_ID, T.STAT_DATE, T.APPLY_CNT, \n")
						.append(
								"        T.MISSING_CNT, T.PROT_NO_LIST, T.DENIZEN_MP\n")
						.append(
								"  FROM R_TMNL_MISSING_MARK T, O_ORG O, C_CONS C, R_TMNL_RUN R, \n")
						.append("               A_TMNL_REAL_TIME  AT,\n")
						.append("               B_CLEAR_PROTOCOL  BP,\n")
						.append("   C_METER_MP_RELA CMMPR, C_MP CM,\n")
						.append("       T_TASK_TEMPLATE  TS,\n")
						.append("  E_DATA_MP           E\n")
						.append(" WHERE C.ORG_NO LIKE ?\n")
						.append("   AND C.CONS_NO = E.CONS_NO\n")
						.append("   AND T.ORG_NO = O.ORG_NO\n")
						.append("         AND T.DENIZEN_MP LIKE ?\n")
						.append("   AND T.ID = E.ID\n")
						.append("   AND R.TMNL_ASSET_NO = T.TMNL_ASSET_NO\n")
						.append("  AND T.PROT_NO_LIST = BP.PROTOCOL_NO\n")
						.append("  AND AT.TMNL_ASSET_NO = T.TMNL_ASSET_NO\n")
						.append(
								"AND E.METER_ID = CMMPR.METER_ID AND CMMPR.MP_ID = CM.MP_ID\n")
						.append(
								" AND T.TEMPLATE_ID = TS.TEMPLATE_ID AND TS.TASK_PROPERTY = ?")
						.append(" AND T.RECALL_CNT < ?")
						.append(
								"   AND T.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n")
						.append("       TO_DATE(?, 'yyyy-MM-dd')\n")
						.append(SysPrivilige.addPri(pSysUser, "C", "R", 7))
						.append(
								" ORDER BY AT.IS_ONLINE DESC, C.CONS_NO, T.TMNL_ASSET_NO, T.STAT_DATE, T.MP_SN");
				// .append(" GROUP BY O.ORG_NAME, C.CONS_NO, C.CONS_NAME, R.TERMINAL_ADDR");
			} else if ("04".equals(orgType)) {
				sb
						.append("SELECT O.ORG_NAME,\n")
						.append("       C.CONS_NO,\n")
						.append("       R.TERMINAL_ADDR,\n")
						.append("       C.CONS_NAME,\n")
						.append("             CM.MP_NO, CM.MP_NAME,\n")
						.append("       AT.IS_ONLINE, BP.PROTOCOL_NAME,\n")
						// .append("       COUNT(T.TMNL_ASSET_NO) AS COUNTS\n")
						.append(
								"		 T.ID, T.TMNL_ASSET_NO, T.DATA_SRC, T.MP_SN, \n")
						.append(
								"        T.IS_DENIZEN, T.TEMPLATE_ID, T.STAT_DATE, T.APPLY_CNT, \n")
						.append(
								"        T.MISSING_CNT, T.PROT_NO_LIST, T.DENIZEN_MP\n")
						.append(
								"  FROM R_TMNL_MISSING_MARK T, O_ORG O, C_CONS C, R_TMNL_RUN R, \n")
						.append("       T_TASK_TEMPLATE  TS,\n")
						.append("   C_METER_MP_RELA CMMPR, C_MP CM,\n")
						.append("               A_TMNL_REAL_TIME  AT,\n")
						.append("               B_CLEAR_PROTOCOL  BP,\n")
						.append("  E_DATA_MP           E\n")
						.append(" WHERE O.ORG_NO LIKE ?\n")
						.append("   AND O.ORG_NO = T.ORG_NO\n")
						.append("   AND T.ID = E.ID\n")
						.append("   AND C.CONS_NO = E.CONS_NO\n")
						.append("         AND T.DENIZEN_MP LIKE ?\n")
						.append("   AND R.TMNL_ASSET_NO = T.TMNL_ASSET_NO\n")
						.append("  AND T.PROT_NO_LIST = BP.PROTOCOL_NO\n")
						.append("  AND AT.TMNL_ASSET_NO = T.TMNL_ASSET_NO\n")
						.append(
								"AND E.METER_ID = CMMPR.METER_ID AND CMMPR.MP_ID = CM.MP_ID\n")
						.append(
								" AND T.TEMPLATE_ID = TS.TEMPLATE_ID AND TS.TASK_PROPERTY = ?")
						.append(" AND T.RECALL_CNT < ?")
						.append(
								"   AND T.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n")
						.append("       TO_DATE(?, 'yyyy-MM-dd')\n")
						.append(SysPrivilige.addPri(pSysUser, "C", "R", 7))
						.append(
								" ORDER BY AT.IS_ONLINE DESC, C.CONS_NO, T.TMNL_ASSET_NO, T.STAT_DATE, T.MP_SN");
				// .append(" GROUP BY O.ORG_NAME, C.CONS_NO, C.CONS_NAME, R.TERMINAL_ADDR");
			} else if ("03".equals(orgType)) {
				sb
						.append("SELECT O.ORG_NAME,\n")
						.append("       C.CONS_NO,\n")
						.append("       R.TERMINAL_ADDR,\n")
						.append("       C.CONS_NAME,\n")
						.append("             CM.MP_NO, CM.MP_NAME,\n")
						.append("       AT.IS_ONLINE, BP.PROTOCOL_NAME,\n")
						.append(
								"		 T.ID, T.TMNL_ASSET_NO, T.DATA_SRC, T.MP_SN, \n")
						.append(
								"        T.IS_DENIZEN, T.TEMPLATE_ID, T.STAT_DATE, T.APPLY_CNT, \n")
						.append(
								"        T.MISSING_CNT, T.PROT_NO_LIST, T.DENIZEN_MP\n")
						// .append("       COUNT(T.TMNL_ASSET_NO) AS COUNTS\n")
						.append(
								"  FROM R_TMNL_MISSING_MARK T, C_CONS C, O_ORG O, R_TMNL_RUN R, \n")
						.append("       T_TASK_TEMPLATE  TS,\n")
						.append("   C_METER_MP_RELA CMMPR, C_MP CM,\n")
						.append("               A_TMNL_REAL_TIME  AT,\n")
						.append("               B_CLEAR_PROTOCOL  BP,\n")
						.append("  E_DATA_MP           E\n")
						.append(" WHERE O.ORG_NO LIKE ?\n")
						.append("   AND O.ORG_NO = T.ORG_NO\n")
						.append("         AND T.DENIZEN_MP LIKE ?\n")
						.append("   AND T.ID = E.ID\n")
						.append("   AND C.CONS_NO = E.CONS_NO\n")
						.append("  AND T.PROT_NO_LIST = BP.PROTOCOL_NO\n")
						.append("  AND AT.TMNL_ASSET_NO = T.TMNL_ASSET_NO\n")
						.append(
								"AND E.METER_ID = CMMPR.METER_ID AND CMMPR.MP_ID = CM.MP_ID\n")
						.append(
								" AND T.TEMPLATE_ID = TS.TEMPLATE_ID AND TS.TASK_PROPERTY = ?")
						.append(" AND T.RECALL_CNT < ?")
						.append("   AND R.TMNL_ASSET_NO = T.TMNL_ASSET_NO\n")
						.append(
								"   AND T.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n")
						.append("       TO_DATE(?, 'yyyy-MM-dd')\n")
						.append(SysPrivilige.addPri(pSysUser, "C", "R", 7))
						.append(
								" ORDER BY AT.IS_ONLINE DESC, C.CONS_NO, T.TMNL_ASSET_NO, T.STAT_DATE, T.MP_SN");
				// .append(" GROUP BY O.ORG_NAME, C.CONS_NO, C.CONS_NAME, R.TERMINAL_ADDR");
			} else {
				return pgb;
			}

			this.logger.debug(sb.toString());
			pgb = this.pagingFind(sb.toString(), start, limit,
					new gatherByHandRowMapper(), new Object[] { orgNo + "%",
							"%0%", dataType, "4", startDate, endDate,
							pSysUser.getStaffNo(), pSysUser.getStaffNo(),
							pSysUser.getStaffNo() });
			return pgb;
		} catch (RuntimeException e) {
			this.logger.debug("根据用户查询未召信息出错！");
			throw e;
		}
	}

	class gatherByHandRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			try {
				GatherByHandDto gatherByHandDto = new GatherByHandDto();
				gatherByHandDto
						.setConsName(rs.getString("CONS_NAME") == null ? ""
								: rs.getString("CONS_NAME"));
				gatherByHandDto.setConsNo(rs.getString("CONS_NO") == null ? ""
						: rs.getString("CONS_NO"));
				gatherByHandDto
						.setOrgName(rs.getString("ORG_NAME") == null ? "" : rs
								.getString("ORG_NAME"));
				gatherByHandDto
						.setTerminalAddr(rs.getString("TERMINAL_ADDR") == null ? ""
								: rs.getString("TERMINAL_ADDR"));
				gatherByHandDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				gatherByHandDto.setStatDate(rs.getDate("STAT_DATE"));
				gatherByHandDto.setProtNoList(rs.getString("PROT_NO_LIST"));
				gatherByHandDto.setMpSn(rs.getShort("MP_SN"));
				// gatherByHandDto.setUnGatherPoints(rs.getInt("COUNTS"));
				gatherByHandDto.setDataId(rs.getLong("ID"));
				gatherByHandDto.setDataSrc(rs.getString("DATA_SRC"));
				gatherByHandDto.setTemplateId(rs.getLong("TEMPLATE_ID"));
				gatherByHandDto.setApplyCnt(rs.getShort("APPLY_CNT"));
				gatherByHandDto.setDenizenMp(rs.getString("DENIZEN_MP"));
				gatherByHandDto.setMissingCnt(rs.getShort("MISSING_CNT"));
				gatherByHandDto.setIsDenizen(rs.getBoolean("IS_DENIZEN"));
				gatherByHandDto.setOnline(rs.getBoolean("IS_ONLINE"));
				gatherByHandDto.setProtocolName(rs.getString("PROTOCOL_NAME"));
				gatherByHandDto.setMpNo(rs.getString("MP_NO"));
				// gatherByHandDto.setMpName(rs.getString("MP_NAME"));
				return gatherByHandDto;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * chenjg 查询全部用户类型
	 * 
	 * @return
	 */
	public List<ConsType> findAllConsType() {
		String sql = "SELECT  * FROM vw_cons_type ct UNION select '-1' AS cons_type,'---全部---' AS cons_type_name FROM vw_cons_type";
		logger.debug("\n 查询全部用户类型:" + sql);
		List<ConsType> list = getJdbcTemplate().query(sql,
				new ConsTypeRowMapper());
		return list;
	}

	class ConsTypeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paraInt) throws SQLException {
			ConsType consType = new ConsType();
			consType.setConsType(rs.getString("cons_type"));
			consType.setConsTypeName(rs.getString("cons_type_name"));
			return consType;
		}

	}

	/**
	 * chenjg 查询全部终端型号
	 * 
	 * @return
	 */
	public List<TmnlModeCode> findAllTmnlModeCode() {
		String sql = "SELECT * FROM vw_tmnl_mode_code UNION SELECT '-1' AS mode_code , '----全部----' AS mode_name FROM vw_tmnl_mode_code";
		logger.debug("\n 查询全部终端型号：" + sql);
		List<TmnlModeCode> list = getJdbcTemplate().query(sql,
				new TmnlModeCodeRowMapper());
		return list;
	}

	class TmnlModeCodeRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int paraInt) throws SQLException {
			TmnlModeCode tmnlModeCode = new TmnlModeCode();
			tmnlModeCode.setModeCode(rs.getString("mode_code"));
			tmnlModeCode.setModeName(rs.getString("mode_name"));

			return tmnlModeCode;
		}

	}

	/**
	 * chenjg 查询全部通信方式
	 * 
	 * @return
	 */
	public List<CommMode> findAllCommMode() {
		String sql = "SELECT * FROM vw_comm_mode UNION SELECT '-1' AS comm_mode_code, '---全部---' AS comm_mode FROM vw_comm_mode";
		List<CommMode> list = getJdbcTemplate().query(sql,
				new CommModeRowMapper());
		return list;
	}

	class CommModeRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int paraInt) throws SQLException {
			CommMode commMode = new CommMode();
			commMode.setCommModeCode(rs.getString("comm_mode_code"));
			commMode.setCommMode(rs.getString("comm_mode"));
			return commMode;
		}

	}

	/**
	 * chenjg 查询全部终端类型
	 * 
	 * @return
	 */
	public List<TerminalTypeCode> findAllTmnlType() {
		String sql = "SELECT '-1' AS TMNL_TYPE_CODE, '----全部----' AS TMNL_TYPE FROM VW_TMNL_TYPE_CODE UNION SELECT * FROM VW_TMNL_TYPE_CODE";
		List<TerminalTypeCode> list = getJdbcTemplate().query(sql,
				new TerminalTypeCodeRowMapper());
		return list;
	}

	class TerminalTypeCodeRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TerminalTypeCode terminalTypeCode = new TerminalTypeCode();
			terminalTypeCode.setTmnlTypeCode(rs.getString("tmnl_type_code"));
			terminalTypeCode.setTmnlType(rs.getString("tmnl_type"));
			return terminalTypeCode;
		}

	}

	/**
	 * chenjg
	 * 
	 * @param staffNo
	 *            工号
	 * @param orgNo
	 * @param consType
	 *            用户类型
	 * @param tmnlModeCode
	 *            终端型号
	 * @param commMode
	 *            通信方式
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 * @throws Exception
	 */
	public List<ATmnlCollQuality> queryGatherQuality(String staffNo,String orgType, String orgNo,String consType,String tmnlModeCode,String commMode,
			String startDate,String endDate){
		List<ATmnlCollQuality> list = new ArrayList<ATmnlCollQuality>();
		StringBuffer sb=new StringBuffer();
		List paraList = new ArrayList();
		
		sb.append("SELECT org.org_no,\n");
		sb.append("       org.org_short_name,\n");
		sb.append("       (T.FIRST_TIMES01 + T.REPAIR_TIMES01) / \n");
		sb.append("       (T.DEMAND_TIMES01 - T.POWER_CUT_TIMES01) AS RATE1,\n");
		sb.append("       (T.FIRST_TIMES02 + T.REPAIR_TIMES02) / \n");
		sb.append("       (T.DEMAND_TIMES02 - T.POWER_CUT_TIMES02) AS RATE2,\n");
		sb.append("       (T.FIRST_TIMES03 + T.REPAIR_TIMES03) / \n");
		sb.append("       (T.DEMAND_TIMES03 - T.POWER_CUT_TIMES03) AS RATE3\n");
		sb.append("  FROM (SELECT A.org_no,\n");
		sb.append("               SUM(DECODE(TASK_PROPERTY, '01', A.FIRST_TIMES, NULL)) AS FIRST_TIMES01,\n");
		sb.append("               SUM(DECODE(TASK_PROPERTY, '01', A.REPAIR_TIMES, NULL)) AS REPAIR_TIMES01,\n");
		sb.append("               SUM(DECODE(TASK_PROPERTY, '01', A.DEMAND_TIMES, NULL)) AS DEMAND_TIMES01,\n");
		sb.append("               SUM(DECODE(TASK_PROPERTY, '01', A.POWER_CUT_TIMES, NULL)) AS POWER_CUT_TIMES01,\n");
		sb.append("               SUM(DECODE(TASK_PROPERTY, '02', A.FIRST_TIMES, NULL)) AS FIRST_TIMES02,\n");
		sb.append("               SUM(DECODE(TASK_PROPERTY, '02', A.REPAIR_TIMES, NULL)) AS REPAIR_TIMES02,\n");
		sb.append("               SUM(DECODE(TASK_PROPERTY, '02', A.DEMAND_TIMES, NULL)) AS DEMAND_TIMES02,\n");
		sb.append("               SUM(DECODE(TASK_PROPERTY, '02', A.POWER_CUT_TIMES, NULL)) AS POWER_CUT_TIMES02,\n");
		sb.append("               SUM(DECODE(TASK_PROPERTY, '03', A.FIRST_TIMES, NULL)) AS FIRST_TIMES03,\n");
		sb.append("               SUM(DECODE(TASK_PROPERTY, '03', A.REPAIR_TIMES, NULL)) AS REPAIR_TIMES03,\n");
		sb.append("               SUM(DECODE(TASK_PROPERTY, '03', A.DEMAND_TIMES, NULL)) AS DEMAND_TIMES03,\n");
		sb.append("               SUM(DECODE(TASK_PROPERTY, '03', A.POWER_CUT_TIMES, NULL)) AS POWER_CUT_TIMES03\n");
		sb.append("          FROM A_TMNL_COLL_QUALITY A\n");
		sb.append("         WHERE A.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n");
		sb.append("               TO_DATE(?, 'yyyy-MM-dd')\n");
		
		paraList.add(startDate);
		paraList.add(endDate);
		
		if(null != consType && !"".equals(consType) && !"-1".equals(consType)){//用户类型
			sb.append("               AND a.cons_type = ? \n");
			paraList.add(consType);
		}
		if(null != tmnlModeCode && !"".equals(tmnlModeCode) && !"-1".equals(tmnlModeCode)){//终端型号
			sb.append("               AND a.model_code = ? \n");
			paraList.add(tmnlModeCode);
		}
		if(null != commMode && !"".equals(commMode) && !"-1".equals(commMode)){
			sb.append("               AND a.coll_mode = ? \n");
			paraList.add(commMode);
		}
		
		
		sb.append("         GROUP BY A.org_no) t,\n");
		
		if("04".equals(orgType)){
			sb.append("       (SELECT oo.org_no, oo.org_short_name\n");
			sb.append("          FROM P_ACCESS_ORG PO, o_org oo\n");
			sb.append("         WHERE po.org_no = oo.org_no\n");
			sb.append("           AND po.staff_no = ?\n");
			sb.append("           AND po.org_no = ?) org\n");
		}else{
			sb.append("       (SELECT oo.org_no, oo.org_short_name\n");
			sb.append("          FROM p_access_org po, o_org oo\n");
			sb.append("         WHERE po.org_no = oo.org_no\n");
			sb.append("           AND po.staff_no = ?\n");
			sb.append("           AND oo.p_org_no = ?) org\n");
		}
		
		sb.append(" WHERE t.org_no = org.org_no ORDER BY t.org_no\n");
		
		paraList.add(staffNo);
		paraList.add(orgNo);
		
		String sql = sb.toString();
		logger.debug("\n采集质量查询：" + sql);
		list = getJdbcTemplate().query(sql, paraList.toArray(),new gatherQualityRowMapper());
		return list;
	}

	/**
	 * 查询采集失败终端 chenjg
	 * 
	 * @param staffNo
	 * @param consType
	 * @param orgNo
	 * @param tmnlModeCode
	 * @param commMode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Page<MissingTerminalDTO> findMissingTermianl(long start, int limit,
			String staffNo, String consType, String orgNo, String tmnlModeCode,
			String commMode, String startDate, String endDate) {
		StringBuffer sb = new StringBuffer();
		Page<MissingTerminalDTO> page = new Page<MissingTerminalDTO>();
		List paramList = new ArrayList();

		sb.append("SELECT ORG.ORG_NAME,\n");
		sb.append("       CONS.CONS_NO,\n");
		sb.append("       CONS.CONS_NAME,\n");
		sb.append("       TMNL.TERMINAL_ADDR,\n");
		sb.append("       ATMNL.IS_ONLINE\n");
		sb.append("  FROM (SELECT TMNL_ASSET_NO, CONS_NO, TERMINAL_ADDR\n");
		sb.append("          FROM E_DATA_MP\n");
		sb.append("         WHERE IS_VALID = 1\n");
		sb
				.append("         GROUP BY TMNL_ASSET_NO, CONS_NO, TERMINAL_ADDR) DM,\n");
		sb.append("       A_TMNL_REAL_TIME ATMNL,\n");
		sb.append("       R_TMNL_RUN TMNL,\n");
		sb.append("       C_CONS CONS,\n");
		sb.append("       O_ORG ORG,\n");
		sb.append("       (SELECT TMM.TMNL_ASSET_NO\n");
		sb
				.append("          FROM A_TMNL_MISSING_MARK TMM, T_TASK_TEMPLATE TT\n");
		sb
				.append("         WHERE TMM.TEMPLATE_ID = TT.TEMPLATE_ID AND TT.TASK_PROPERTY = '01' AND TMM.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-mm-dd') AND\n");
		paramList.add(startDate);
		sb.append("               TO_DATE(?, 'yyyy-mm-dd')\n");
		paramList.add(endDate);
		sb.append("         GROUP BY TMM.TMNL_ASSET_NO) TT,\n");
		sb.append("       (SELECT ORG.ORG_NO, ORG.ORG_NAME\n");
		sb.append("          FROM O_ORG ORG\n");
		sb.append("         WHERE LENGTH(ORG.ORG_NO) = 7\n");
		sb.append("         START WITH ORG.ORG_NO = ?\n");
		paramList.add(orgNo);
		sb.append("        CONNECT BY PRIOR ORG.ORG_NO = ORG.P_ORG_NO) O\n");
		sb.append(" WHERE DM.TMNL_ASSET_NO = TT.TMNL_ASSET_NO\n");
		sb.append("   AND DM.TMNL_ASSET_NO = TMNL.TMNL_ASSET_NO\n");
		sb.append("   AND ATMNL.TMNL_ASSET_NO = TMNL.TMNL_ASSET_NO\n");
		sb.append("   AND DM.CONS_NO = CONS.CONS_NO\n");
		sb.append("   AND CONS.ORG_NO = ORG.ORG_NO\n");
		sb.append("   AND O.ORG_NO = CONS.AREA_NO\n");
		if (null != consType && !"".equals(consType) && !"-1".equals(consType)) {
			sb.append("   AND CONS.CONS_TYPE  = ?\n");
			paramList.add(consType);
		}
		if (null != tmnlModeCode && !"".equals(tmnlModeCode)
				&& !"-1".equals(tmnlModeCode)) {
			sb.append("   AND TMNL.ID  = ?\n");
			paramList.add(tmnlModeCode);
		}
		if (null != commMode && !"".equals(commMode) && !"-1".equals(commMode)) {
			sb.append("   AND TMNL.COLL_MODE  = ?\n");
			paramList.add(commMode);
		}

		String sql = sb.toString();
		logger.debug("\n查询采集失败终端：" + sql);

		page = this.pagingFind(sql, start, limit,
				new MissingTerminalRowMapper(), paramList.toArray());
		return page;
	}

	class MissingTerminalRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int paramList) throws SQLException {
			MissingTerminalDTO missingTerminalDTO = new MissingTerminalDTO();
			missingTerminalDTO.setConsName(rs.getString("CONS_NAME"));
			missingTerminalDTO.setConsNo(rs.getString("CONS_NO"));
			missingTerminalDTO.setIsOnline(rs.getInt("IS_ONLINE"));
			missingTerminalDTO.setOrgName(rs.getString("ORG_NAME"));
			missingTerminalDTO.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
			return missingTerminalDTO;
		}

	}
}
