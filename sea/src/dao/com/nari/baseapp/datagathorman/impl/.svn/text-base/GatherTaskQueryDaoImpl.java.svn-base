package com.nari.baseapp.datagathorman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.datagatherman.TaskStatInfoBean;
import com.nari.baseapp.datagathorman.GatherTaskQueryDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;

public class GatherTaskQueryDaoImpl extends JdbcBaseDAOImpl implements
		GatherTaskQueryDao {

	@Override
	public Page<TaskStatInfoBean> queryGatherTaskStat(int taskType,
			String nodeType, String nodeValue, Date startTime, Date endTime,
			PSysUser userInfo, long start, long limit) throws DBAccessException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		List<Object> paraList = new ArrayList<Object>();
		paraList.add(taskType);
		paraList.add(startTime);
		paraList.add(endTime);
		sb.append("SELECT P.TASK_ID, R.CIS_ASSET_NO, TA.MASTER_R,\n");
		sb.append("  TA.TASK_NO, P.SUCESS_COUNT, p.FAILUE_COUNT, TP.TEMPLATE_NAME, \n");
		sb.append("  C.CONS_NAME, C.CONS_NO, O.ORG_NAME FROM\n");
		sb.append("  (SELECT A.TASK_ID, A.TMNL_ASSET_NO,\n");
		sb.append("		SUM(DECODE(A.TASK_FLAG, 0, 1, 0))AS SUCESS_COUNT,\n");
		sb.append("		SUM(DECODE(A.TASK_FLAG, 1, 1, 0)) AS FAILUE_COUNT\n");
		sb.append("		FROM A_STATION_TASK_STAT a\n");
		sb.append("     WHERE A.TASK_TYPE = ? AND A.CREATE_TIME BETWEEN ? AND ?\n");
		sb.append("     GROUP BY A.TMNL_ASSET_NO, A.TASK_ID) P,\n");
		sb.append("     T_TMNL_TASK TA, T_TASK_TEMPLATE TP, C_CONS C, O_ORG O,\n");
		sb.append("VW_TMNL_RUN R\n");
		if ("cgp".equals(nodeType)) {
			sb.append(",        T_TMNL_GROUP_DETAIL G\n");
		} else if ("ugp".equals(nodeType)) {
			sb.append(",        R_USER_GROUP_DETAIL G\n");
		}
		sb.append("  WHERE \n");
		
		if ("org".equals(nodeType)) {
			paraList.add(nodeValue + "%");
			sb.append("   C.ORG_NO like ?");
			sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
		} else if ("line".equals(nodeType)) {
			paraList.add(nodeValue);
			sb.append("   C.LINE_ID = ?");
			sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
		} else if ("ugp".equals(nodeType) || "cgp".equals(nodeType)) {
			paraList.add(nodeValue);
			sb.append("   G.CONS_NO = C.CONS_NO\n");
			sb.append("   AND G.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n");
			sb.append("   AND G.GROUP_NO = ? ");
			sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
		} else if ("usr".equals(nodeType)) {
			paraList.add(nodeValue);
			sb.append("   P.TMNL_ASSET_NO = ?");
		}
		sb.append("  AND P.TMNL_ASSET_NO = TA.TMNL_ASSET_NO\n");
		sb.append("  AND P.TASK_ID = TA.TEMPLATE_ID AND P.TASK_ID = TP.TEMPLATE_ID\n");
		sb.append("  AND C.AREA_NO = O.ORG_NO AND R.CONS_NO = C.CONS_NO\n");
		sb.append("  AND P.TMNL_ASSET_NO = R.TMNL_ASSET_NO");

		return this.pagingFind(sb.toString(), start, limit, new tsRowMap(),
				paraList.toArray());
	}
	
	class tsRowMap implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TaskStatInfoBean taskstatinfobean = new TaskStatInfoBean();
			try {
				taskstatinfobean.setTaskId(rs.getLong("TASK_ID"));
//				taskstatinfobean.setTaskType(rs.getLong("TASK_TYPE"));
				taskstatinfobean.setSucessCount(rs.getLong("SUCESS_COUNT"));
				taskstatinfobean.setFailueCount(rs.getLong("FAILUE_COUNT"));
				taskstatinfobean.setCisAssetNo(rs.getString("CIS_ASSET_NO"));
				taskstatinfobean.setMasterR(rs.getShort("MASTER_R"));
				taskstatinfobean.setTaskNo(rs.getShort("TASK_NO"));
				taskstatinfobean.setTemplateName(rs.getString("TEMPLATE_NAME"));
				taskstatinfobean.setConsName(rs.getString("CONS_NAME"));
				taskstatinfobean.setConsNo(rs.getString("CONS_NO"));
				taskstatinfobean.setOrgName(rs.getString("ORG_NAME"));
				return taskstatinfobean;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
