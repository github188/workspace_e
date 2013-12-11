package com.nari.baseapp.datagathorman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.hibernate.property.MapAccessor.MapGetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.nari.privilige.PSysUser;
import com.nari.ami.database.map.terminalparam.TTmnlTask;
import com.nari.ami.database.map.terminalparam.TTmnlTaskId;
import com.nari.baseapp.datagatherman.GatherTaskCompileDto;
import com.nari.baseapp.datagatherman.TaskInfoDto;
import com.nari.baseapp.datagatherman.TbgTaskSend;
import com.nari.baseapp.datagathorman.GatherTaskCompileJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.NamedCreateInsert;
import com.nari.util.exception.DBAccessException;

public class GatherTaskCompileJdbcDaoImpl extends JdbcBaseDAOImpl implements
		GatherTaskCompileJdbcDao {

	/**
	 * 根据用户查询相关信息放入备选用户Grid中（单条记录）
	 * 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<GatherTaskCompileDto> findGatherTaskCompileByCons(String[] csNo,
			boolean isConsNo) throws DBAccessException {
		List<String> paraList = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT C.AREA_NO,\n")
				.append("       C.CONS_NO,\n")
				.append("       R.TMNL_ASSET_NO,\n")
				.append("       C.CONS_NAME,\n")
				.append("       R.PROTOCOL_CODE,\n")
				.append("       O.ORG_NAME,\n")
				.append("       R.TERMINAL_ADDR,\n")
				.append("       R.SEND_UP_MODE,\n")
				.append("       V.FACTORY_NAME,\n")
				.append("       P.Y_TASK_NO, --启用 [1][2][3]\n")
				.append("       P.Y_MP_SN, --各任务之间的测量点以逗号隔开 12,12,22\n")
				.append("       P.N_TASK_NO, --停用 [4]\n")
				.append("       P.N_MP_SN --12\n")
				.append("  FROM C_CONS C,\n")
				.append("       O_ORG O,\n")
				.append("       VW_TMNL_RUN R,\n")
				.append("       VW_TMNL_FACTORY V,\n")
				.append("       (SELECT TMNL_ASSET_NO,\n")
				.append(
						"               MAX(DECODE(IS_ENABLE, '1', TASK_NO, NULL)) Y_TASK_NO,\n")
				.append(
						"               MAX(DECODE(IS_ENABLE, '0', TASK_NO, NULL)) N_TASK_NO,\n")
				.append(
						"               MAX(DECODE(IS_ENABLE, '1', MP_SN, NULL)) Y_MP_SN,\n")
				.append(
						"               MAX(DECODE(IS_ENABLE, '0', MP_SN, NULL)) N_MP_SN\n")
				.append("          FROM (SELECT TMNL_ASSET_NO,\n")
				.append("                       IS_ENABLE,\n")
				.append(
						"                       MAX(SUBSTR(SYS_CONNECT_BY_PATH(TASK_NO, ','), 2)) TASK_NO,\n")
				.append(
						"                       MAX(SUBSTR(SYS_CONNECT_BY_PATH(MP_SN, '#'), 2)) MP_SN\n")
				.append("                  FROM (SELECT TMNL_ASSET_NO,\n")
				.append("                               TASK_NO,\n")
				.append("                               MP_SN,\n")
				.append("                               IS_ENABLE,\n")
				.append(
						"                               ROW_NUMBER() OVER(PARTITION BY TMNL_ASSET_NO, IS_ENABLE ORDER BY TMNL_ASSET_NO, IS_ENABLE, TASK_NO) RN\n")
				.append("                          FROM T_TMNL_TASK T\n")
				.append("                         WHERE STATUS_CODE = '04'\n")
				.append(
						"                         ORDER BY TMNL_ASSET_NO, IS_ENABLE DESC, TASK_NO)\n")
				.append("                 START WITH RN = 1\n")
				.append("                CONNECT BY RN = PRIOR RN + 1\n")
				.append(
						"                       AND TMNL_ASSET_NO = PRIOR TMNL_ASSET_NO\n")
				.append(
						"                       AND IS_ENABLE = PRIOR IS_ENABLE\n")
				.append("                 GROUP BY TMNL_ASSET_NO, IS_ENABLE\n")
				.append(
						"                 ORDER BY TMNL_ASSET_NO, IS_ENABLE DESC)\n")
				.append("         GROUP BY TMNL_ASSET_NO) P\n").append(
						" WHERE O.ORG_NO = C.AREA_NO\n").append(
						"   AND R.CONS_NO = C.CONS_NO\n").append(
						"   AND V.FACTORY_CODE = R.FACTORY_CODE\n").append(
						"   AND P.TMNL_ASSET_NO (+)= R.TMNL_ASSET_NO\n");

		if (isConsNo) {
			sb.append("   AND C.CONS_NO = in (");
			for (int i = 0; i < csNo.length; i++) {
				paraList.add(csNo[i]);
				if ( i == csNo.length -1) {
					sb.append("?");
				} else {
					sb.append("?,");
				}
			}
			sb.append(")\n");
		} else {
			sb.append("  AND R.TMNL_ASSET_NO in (");
			for (int i = 0; i < csNo.length; i++) {
				paraList.add(csNo[i]);
				if ( i == csNo.length -1) {
					sb.append("?");
				} else {
					sb.append("?,");
				}
			}
			sb.append(")\n");
		}
		try {
			this.logger.debug(sb.toString());
			List<GatherTaskCompileDto> list = new ArrayList<GatherTaskCompileDto>();
			list = this.getJdbcTemplate().query(sb.toString(),
					paraList.toArray(), new GatherTaskCompileRowMapper());
			return list;
		} catch (RuntimeException e) {
			this.logger.debug("根据用户查询相关信息放入备选用户Grid出错！");
			throw e;
		}
	}

	/**
	 * 根据供电单位、群组、线路查询相关信息放入备选用户Grid中（单条记录）
	 * 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<GatherTaskCompileDto> findGatherTaskCompileByOrg(PSysUser ps,
			String csNo, String nodeType) throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		List<String> paraList = new ArrayList<String>();
		sb.append("SELECT C.AREA_NO,\n").append("       C.CONS_NO,\n").append(
				"       R.TMNL_ASSET_NO,\n").append("       C.CONS_NAME,\n")
				.append("       O.ORG_NAME,\n").append(
						"       R.TERMINAL_ADDR,\n").append(
						"       R.SEND_UP_MODE,\n").append(
						"       R.PROTOCOL_CODE,\n").append(
						"       V.FACTORY_NAME,\n").append(
						"       P.Y_TASK_NO, --启用 [1][2][3]\n").append(
						"       P.Y_MP_SN, --各任务之间的测量点以逗号隔开 12,12,22\n")
				.append("       P.N_TASK_NO, --停用 [4]\n").append(
						"       P.N_MP_SN --12\n").append("  FROM C_CONS C,\n")
				.append("       O_ORG O,\n").append("       VW_TMNL_RUN R,\n")
				.append("       VW_TMNL_FACTORY V,\n");
		if ("cgp".equals(nodeType)) {
			sb.append("        T_TMNL_GROUP_DETAIL G,\n");
		} else if ("ugp".equals(nodeType)) {
			sb.append("        R_USER_GROUP_DETAIL G,\n");
		}
		sb
				.append("       (SELECT TMNL_ASSET_NO,\n")
				.append(
						"               MAX(DECODE(IS_ENABLE, '1', TASK_NO, NULL)) Y_TASK_NO,\n")
				.append(
						"               MAX(DECODE(IS_ENABLE, '0', TASK_NO, NULL)) N_TASK_NO,\n")
				.append(
						"               MAX(DECODE(IS_ENABLE, '1', MP_SN, NULL)) Y_MP_SN,\n")
				.append(
						"               MAX(DECODE(IS_ENABLE, '0', MP_SN, NULL)) N_MP_SN\n")
				.append("          FROM (SELECT TMNL_ASSET_NO,\n")
				.append("                       IS_ENABLE,\n")
				.append(
						"                       MAX(SUBSTR(SYS_CONNECT_BY_PATH(TASK_NO, ','), 2)) TASK_NO,\n")
				.append(
						"                       MAX(SUBSTR(SYS_CONNECT_BY_PATH(MP_SN, '#'), 2)) MP_SN\n")
				.append("                  FROM (SELECT TMNL_ASSET_NO,\n")
				.append("                               TASK_NO,\n")
				.append("                               MP_SN,\n")
				.append("                               IS_ENABLE,\n")
				.append(
						"                               ROW_NUMBER() OVER(PARTITION BY TMNL_ASSET_NO, IS_ENABLE ORDER BY TMNL_ASSET_NO, IS_ENABLE, TASK_NO) RN\n")
				.append("                          FROM T_TMNL_TASK T\n")
				.append("                         WHERE STATUS_CODE = '04'\n")
				.append(
						"                         ORDER BY TMNL_ASSET_NO, IS_ENABLE DESC, TASK_NO)\n")
				.append("                 START WITH RN = 1\n")
				.append("                CONNECT BY RN = PRIOR RN + 1\n")
				.append(
						"                       AND TMNL_ASSET_NO = PRIOR TMNL_ASSET_NO\n")
				.append(
						"                       AND IS_ENABLE = PRIOR IS_ENABLE\n")
				.append("                 GROUP BY TMNL_ASSET_NO, IS_ENABLE\n")
				.append(
						"                 ORDER BY TMNL_ASSET_NO, IS_ENABLE DESC)\n")
				.append("         GROUP BY TMNL_ASSET_NO) P\n").append(
						" WHERE O.ORG_NO = C.AREA_NO\n").append(
						"   AND R.CONS_NO = C.CONS_NO\n").append(
						"   AND V.FACTORY_CODE = R.FACTORY_CODE\n").append(
						"   AND P.TMNL_ASSET_NO (+)= R.TMNL_ASSET_NO\n");

		if ("org".equals(nodeType)) {
			paraList.add(csNo + "%");
			sb.append("   AND C.ORG_NO like ? ");
			sb.append(SysPrivilige.addPri(ps, "C", "R", 7));
			paraList.add(ps.getStaffNo());
			paraList.add(ps.getStaffNo());
			paraList.add(ps.getStaffNo());
		} else if ("line".equals(nodeType)) {
			paraList.add(csNo);
			sb.append("   AND C.LINE_ID = ? ");
			sb.append(SysPrivilige.addPri(ps, "C", "R", 7));
			paraList.add(ps.getStaffNo());
			paraList.add(ps.getStaffNo());
			paraList.add(ps.getStaffNo());
		} else if ("ugp".equals(nodeType) || "cgp".equals(nodeType)) {
			paraList.add(csNo);
			sb.append("	  AND G.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n");
			sb.append("   AND G.GROUP_NO = ? ");
			paraList.add(ps.getStaffNo());
			paraList.add(ps.getStaffNo());
			paraList.add(ps.getStaffNo());
		} else {
			List<GatherTaskCompileDto> list = new ArrayList<GatherTaskCompileDto>();
			return list;
		}
		try {
			this.logger.debug(sb.toString());
			return this.getJdbcTemplate().query(sb.toString(), paraList.toArray(),
					new GatherTaskCompileRowMapper());
		} catch (RuntimeException e) {
			this.logger.debug("根据用户查询相关信息放入备选用户Grid出错！");
			throw e;
		}
	}

	/**
	 * 根据终端资产编号和任务号查询任务信息
	 * 
	 * @param tmnlAssetNo
	 *            终端资产编号
	 * @param taskNo
	 *            任务号
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public TaskInfoDto findTaskInfoById(String tmnlAssetNo, String taskNo)
			throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT T.TMNL_ASSET_NO,\n");
		sb.append("       T.TEMPLATE_ID,\n");
		sb.append("       E.DATA_TYPE,\n");
		sb.append("       T.TASK_PROPERTY,\n");
		sb.append("       T.TASK_NO,\n");
		sb.append("       T.MP_SN,\n");
		sb.append("       E.TMNL_R,\n");
		sb.append("       T.REFERENCE_TIME,\n");
		sb.append("       T.MASTER_R,\n");
		sb.append("       T.SEND_TIME,\n");
		sb.append("       E.TEMPLATE_NAME,\n");
		sb.append("       E.REPORT_CYCLE,\n");
		sb.append("       E.REPORT_CYCLE_UNIT,\n");
		sb.append("       V.TASK_PROPERTY_NAME\n");
		sb
				.append("  FROM T_TMNL_TASK T, T_TASK_TEMPLATE E, VW_TASK_PROPERTY V\n");
		sb.append(" WHERE T.TEMPLATE_ID = E.TEMPLATE_ID\n");
		sb.append("   AND V.TASK_PROPERTY = T.TASK_PROPERTY\n");
		sb.append("   AND T.TMNL_ASSET_NO = ?\n");
		sb.append("   AND T.TASK_NO = ?");

		try {
			List<TaskInfoDto> list = new ArrayList<TaskInfoDto>();
			list = this.getJdbcTemplate().query(sb.toString(),
					new Object[] { tmnlAssetNo, taskNo },
					new taskInfoRowMapper());
			if (list != null && list.size() > 0)
				return list.get(0);
		} catch (RuntimeException e) {
			this.logger.debug("根据终端资产编号和任务号查询任务信息出错！");
			throw e;
		}
		return null;
	}

	/**
	 * 根据终端资产编号和任务模板id确定任务是否存在
	 * 
	 * @param tmnlAssetNo
	 *            终端资产编号
	 * @param templateId
	 *            任务模板id
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public TTmnlTask findTaskByNoId(String tmnlAssetNo, Long templateId)
			throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb
				.append(
						"SELECT T.TMNL_ASSET_NO, T.TEMPLATE_ID, T.TASK_PROPERTY, T.MP_SN, T.TASK_NO\n")
				.append("  FROM T_TMNL_TASK T\n").append(
						" WHERE T.TMNL_ASSET_NO = ?\n").append(
						"   AND T.TEMPLATE_ID = ?");
		try {
			List<TTmnlTask> list = new ArrayList<TTmnlTask>();
			list = this.getJdbcTemplate().query(sb.toString(),
					new Object[] { tmnlAssetNo, templateId },
					new tTmnlTaskRowMapper());
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (RuntimeException e) {
			this.logger.debug("根据终端资产编号和任务模板id确定任务是否存在出错！");
			throw e;
		}
		return null;
	}

	/*** 向t_bg_task_send表中插入一条数据,如果有重复更新 
	 * @param t 插入的一条后台待下发任务
	 * ***/
	public void mergeBgTaskSend(TbgTaskSend t) throws DBAccessException {
		try {
			// 写法一
			String sql = "merge into t_bg_task_send t using\n"
					+ "(select :tmnlAssetNo as tmnl_asset_no\n"
					+ ":taskNo as task_no,\n"
					+ ":dataItemNo as data_item_no,\n"
					+ ":dataValue as data_value\n"
					+ " from dual)  t_t\n"
					+ "on(t.tmnl_asset_no =t_t.tmnl_asset_no and t.task_no=t_t.task_no and t.data_item_no=t_t.data_item_no)\n"
					+ "when matched then\n"
					+ "update set\n"
					+ "t.data_item_no=t_t.data_item_no\n"
					+ "when not matched then\n"
					+ "insert values(t_t.tmnl_asset_no,t_t.task_no,t_t.data_item_no,t_t.data_value)";
			// 对于程序而言，使用下面的写法更好
			String sql1 = "merge into t_bg_task_send t using\n"
					+ "(select 1\n"
					+ " from dual)  t_t\n"
					+ "on(t.tmnl_asset_no =:tmnlAssetNo and t.task_no=:taskNo and t.data_item_no=:dataItemNo)\n"
					+ "when matched then\n"
					+ "update set\n"
					+ "t.data_value=:dataValue\n"
					+ "when not matched then\n" 
					+ "insert values(:tmnlAssetNo,:taskNo,:dataItemNo,:dataValue)";
			NamedCreateInsert nc = new NamedCreateInsert();
			SqlParameterSource sp = new MapSqlParameterSource(NamedCreateInsert
					.mapFromBean(t));
			nc.getNamedParameterJdbcTemplate().update(sql1, sp);
		} catch (Exception e) {
			throw new DBAccessException(e.getMessage());
		}
	}

	class GatherTaskCompileRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			GatherTaskCompileDto gatherTaskCompileDto = new GatherTaskCompileDto();
			try {
				gatherTaskCompileDto.setAreaNo(rs.getString("AREA_NO"));
				gatherTaskCompileDto.setConsNo(rs.getString("CONS_NO"));
				gatherTaskCompileDto.setTmnlAssetNo(rs
						.getString("TMNL_ASSET_NO"));
				gatherTaskCompileDto.setProtocolCode(rs
						.getString("PROTOCOL_CODE"));
				gatherTaskCompileDto.setConsName(rs.getString("CONS_NAME"));
				gatherTaskCompileDto.setOrgName(rs.getString("ORG_NAME"));
				gatherTaskCompileDto.setTerminalAddr(rs
						.getString("TERMINAL_ADDR"));
				gatherTaskCompileDto.setSendUpMode(rs.getByte("SEND_UP_MODE"));
				gatherTaskCompileDto.setFactoryName(rs
						.getString("FACTORY_NAME"));
				gatherTaskCompileDto
						.setyTaskNo(rs.getString("Y_TASK_NO") == null ? "" : rs
								.getString("Y_TASK_NO"));
				gatherTaskCompileDto
						.setyMpSn(rs.getString("Y_MP_SN") == null ? "" : rs
								.getString("Y_MP_SN"));
				gatherTaskCompileDto
						.setnTaskNo(rs.getString("N_TASK_NO") == null ? "" : rs
								.getString("N_TASK_NO"));
				gatherTaskCompileDto
						.setnMpSn(rs.getString("N_MP_SN") == null ? "" : rs
								.getString("N_MP_SN"));
				return gatherTaskCompileDto;
			} catch (Exception e) {
				return null;
			}
		}
	}

	class taskInfoRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TaskInfoDto taskInfoDto = new TaskInfoDto();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				taskInfoDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				taskInfoDto.setTemplateId(rs.getLong("TEMPLATE_ID"));
				taskInfoDto.setTaskProperty(rs.getString("TASK_PROPERTY"));
				taskInfoDto.setReportCycle(rs.getString("REPORT_CYCLE"));
				taskInfoDto.setReportCycleUnit(rs
						.getString("REPORT_CYCLE_UNIT"));
				taskInfoDto.setTmnlR(rs.getShort("TMNL_R"));
				taskInfoDto.setTaskNo(rs.getShort("TASK_NO"));
				taskInfoDto.setDataType(rs.getByte("DATA_TYPE"));
				taskInfoDto.setMpSn(rs.getString("MP_SN"));
				taskInfoDto.setReferenceTime(rs.getString("REFERENCE_TIME"));
				taskInfoDto.setMasterR(rs.getShort("MASTER_R"));
				if (rs.getString("SEND_TIME") != null) {
					taskInfoDto.setSendTime(sdf
							.parse(rs.getString("SEND_TIME")));
				}
				taskInfoDto.setTemplateName(rs.getString("TEMPLATE_NAME"));
				taskInfoDto.setTaskPropertyName(rs
						.getString("TASK_PROPERTY_NAME"));
				return taskInfoDto;
			} catch (Exception e) {
				return null;
			}
		}
	}

	class tTmnlTaskRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TTmnlTask tTmnlTask = new TTmnlTask();
			TTmnlTaskId tTmnlTaskId = new TTmnlTaskId();
			try {
				tTmnlTaskId.setTaskNo(rs.getShort("TASK_NO"));
				tTmnlTaskId.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				tTmnlTask.setId(tTmnlTaskId);
				tTmnlTask.setTemplateId(rs.getLong("TEMPLATE_ID"));
				tTmnlTask.setMpSn(rs.getString("MP_SN"));
				tTmnlTask.setTaskProperty(rs.getString("TASK_PROPERTY"));
				return tTmnlTask;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
