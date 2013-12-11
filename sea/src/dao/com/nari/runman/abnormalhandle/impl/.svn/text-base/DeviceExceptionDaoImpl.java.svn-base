package com.nari.runman.abnormalhandle.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.flowhandle.DeviceExceptionBean;
import com.nari.privilige.PSysUser;
import com.nari.runman.abnormalhandle.DeviceExceptionDao;
import com.nari.support.Page;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;

public class DeviceExceptionDaoImpl extends JdbcBaseDAOImpl implements
		DeviceExceptionDao {

	@Override
	public Page<DeviceExceptionBean> queryTmnlException(
			DeviceExceptionBean infoBean) throws DBAccessException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT TE.*, B.EVENT_NAME,\n");
		sb.append("       C.CONS_NAME,\n");
		sb.append("       O.ORG_NAME\n");
		sb.append("  FROM A_TMNL_EXCEPTION TE, B_PROTOCOL_EVENT B\n");
		sb.append("  C_CONS C, O_ORG O, VW_TMNL_RUN R\n");
		sb.append(" WHERE TE.TMNL_EXCEPTION_ID = ?\n");
		sb.append("   AND B.EVENT_NO = TE.EXCEPT_CODE");

		String sql = sb.toString();

		// TODO Auto-generated method stub
		return this.pagingFind(sql, 0, 100, new deRowMap());
	}

	@Override
	public Page<DeviceExceptionBean> queryPageTmnlException(String nodeType,
			String nodeValue, PSysUser userInfo, long start, long limit,
			Date startDate, Date endDate) {
		StringBuffer sb = new StringBuffer();
		List<Object> paraList = new ArrayList<Object>();
		paraList.add(nodeValue + "%");
		sb.append("SELECT TE.*, B.EVENT_NAME,\n");
		sb.append("       C.CONS_NAME,\n");
		sb.append("       O.ORG_NAME\n");
		sb.append("  FROM C_CONS C, O_ORG O, A_TMNL_EXCEPTION TE, B_PROTOCOL_EVENT B, VW_TMNL_RUN R\n");
		if ("cgp".equals(nodeType)) {
			sb.append(",        T_TMNL_GROUP_DETAIL G\n");
		} else if ("ugp".equals(nodeType)) {
			sb.append(",        R_USER_GROUP_DETAIL G\n");
		}
		sb.append("WHERE \n");
		if ("org".equals(nodeType)) {
			sb.append("   C.ORG_NO like ?");
			sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
		} else if ("line".equals(nodeType)) {
			sb.append("   C.LINE_ID like ?");
			sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
		} else if ("ugp".equals(nodeType) || "cgp".equals(nodeType)) {
			sb.append("   G.CONS_NO = C.CONS_NO\n");
			sb.append("   AND G.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n");
			sb.append("   AND G.GROUP_NO like ? ");
			sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
		} else if ("usr".equals(nodeType)) {
			sb.append("   TE.TMNL_ASSET_NO like ?");
		}
		paraList.add(startDate);
		paraList.add(endDate);
		sb.append("  AND B.EVENT_NO = TE.EXCEPT_CODE\n");
		sb.append("   AND C.CONS_NO = TE.CONS_NO\n");
		sb.append("     AND C.AREA_NO = O.ORG_NO\n");
		sb.append("     AND TE.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n");
		sb.append("AND TE.EXCEPT_DATE BETWEEN ? AND ?");
		return this.pagingFind(sb.toString(), start, limit, new deRowMap(),
				paraList.toArray());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceExceptionBean> queryTmnlException(String tmnlExceptionId)
			throws DBAccessException {
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(tmnlExceptionId);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT TE.*, B.EVENT_NAME,\n");
		sb.append("       C.CONS_NAME,\n");
		sb.append("       O.ORG_NAME\n");
		sb.append("  FROM A_TMNL_EXCEPTION TE, B_PROTOCOL_EVENT B,\n");
		sb.append("  C_CONS C, O_ORG O\n");
		sb.append(" WHERE TE.TMNL_EXCEPTION_ID = ?\n");
		sb.append("   AND B.EVENT_NO = TE.EXCEPT_CODE\n");
		sb.append("AND C.CONS_NO = TE.CONS_NO AND O.ORG_NO = TE.ORG_NO");

		String sql = sb.toString();
		return this.getJdbcTemplate().query(sql, paramList.toArray(), new deRowMap());
	}
	
	@Override
	public void updateFlowStatusByExId(String excetpionId, String stauts)
			throws DBAccessException {
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(stauts);
		paramList.add(excetpionId);
		StringBuffer sb=new StringBuffer();
		sb.append("UPDATE A_TMNL_EXCEPTION TE SET TE.FLOW_STATUS_CODE = ? \n");
		sb.append("WHERE TE.TMNL_EXCEPTION_ID = ?");
		String sql=sb.toString();
		this.getJdbcTemplate().update(sql, paramList.toArray());
	}

	class deRowMap implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			DeviceExceptionBean deBean = new DeviceExceptionBean();
			try {
				deBean.setTmnlExceptionId(rs.getString("TMNL_EXCEPTION_ID"));
				deBean.setOrgNo(rs.getString("ORG_NO"));
				deBean.setConsNo(rs.getString("CONS_NO"));
				deBean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				deBean.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				deBean.setExceptDate(rs.getTimestamp("EXCEPT_DATE"));
				deBean.setExceptCode(rs.getString("EXCEPT_CODE"));
				deBean.setExceptOrigin(rs.getString("EXCEPT_ORIGIN"));
				deBean.setSimNo(rs.getString("SIM_NO"));
				deBean.setFactoryCode(rs.getString("FACTORY_CODE"));
				deBean.setRemark(rs.getString("REMARK"));
				deBean.setResumeDate(rs.getTimestamp("RESUME_DATE"));
				deBean.setFlowStatusCode(rs.getString("FLOW_STATUS_CODE"));
				deBean.setFlowStatusDetail(rs.getByte("FLOW_STATUS_DETAIL"));
				deBean.setConclusionNo(rs.getByte("CONCLUSION_NO"));
				deBean.setFlowFlag(rs.getByte("FLOW_FLAG"));
				deBean.setHandlerNo(rs.getString("HANDLER_NO"));
				deBean.setHandleDate(rs.getTimestamp("HANDLE_DATE"));
				deBean.setJobOrder(rs.getString("JOB_ORDER"));
				deBean.setDistributionNo(rs.getString("DISTRIBUTION_NO"));
				deBean
						.setDistributionDate(rs
								.getTimestamp("DISTRIBUTION_DATE"));
				deBean.setClosingDate(rs.getTimestamp("CLOSING_DATE"));
				deBean.setEventName(rs.getString("EVENT_NAME"));
				deBean.setConsName(rs.getString("CONS_NAME"));
				deBean.setOrgName(rs.getString("ORG_NAME"));
				return deBean;

			} catch (Exception e) {
				return null;
			}
		}
	}
}
