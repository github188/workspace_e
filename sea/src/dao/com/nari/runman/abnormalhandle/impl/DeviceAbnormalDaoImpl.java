package com.nari.runman.abnormalhandle.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.flowhandle.EventSearchInfoBean;
import com.nari.flowhandle.FEventInfoBean;
import com.nari.flowhandle.FEventSrc;
import com.nari.runman.abnormalhandle.DeviceAbnormalDao;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class DeviceAbnormalDaoImpl extends JdbcBaseDAOImpl implements
		DeviceAbnormalDao {

	@Override
	public Page<FEventInfoBean> getEventInfo(EventSearchInfoBean eventInfo,
			long start, int limit) throws DBAccessException {
		List<Object> infoList = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT FS.EVENT_ID,\n");
		sb.append("       FS.EVNET_TIME,\n");
		sb.append("       FS.STAFF_NO,\n");
		sb.append("       FS.ORG_NO,\n");
		sb.append("       O.ORG_NAME,\n");
		sb.append("       FS.FROM_ID,\n");
		sb.append("       FS.EVENT_TYPE,\n");
		sb.append("       FS.FLOW_STATUS_CODE,\n");
		sb.append("       FS.EVENT_CODE,\n");
		sb.append("       FS.EVENT_NAME,\n");
		sb.append("       FS.EVENT_DATA\n");
		sb.append("  FROM F_EVENT_SRC FS, O_ORG O\n");
		sb.append("WHERE FS.EVNET_TIME BETWEEN ? AND ?\n");
		sb.append("AND O.ORG_NO = FS.ORG_NO\n");
		infoList.add(eventInfo.getStartTime());
		infoList.add(eventInfo.getEndTime());
		if (null != eventInfo.getEventType()
				&& !"".equals(eventInfo.getEventType())) {
			sb.append("  AND FS.EVENT_TYPE = ?\n");
			infoList.add(eventInfo.getEventType());
		} else {
			sb.append("  AND FS.EVENT_TYPE in(2,3)\n");
		}
		if (null != eventInfo.getFlowStatusCode()
				&& !"".equals(eventInfo.getFlowStatusCode())) {
			sb.append("  AND FS.FLOW_STATUS_CODE = ?");
			infoList.add(eventInfo.getFlowStatusCode());
		}

		sb.append(" ORDER BY EVENT_ID");

		String sql = sb.toString();
		return super.pagingFind(sql, start, limit, new fibRowMapper(), infoList
				.toArray());
	}
	
	class daRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			FEventSrc siminstall = new FEventSrc();
			try {
				siminstall.setEventId(rs.getLong("EVENT_ID"));
				siminstall.setEvnetTime(rs.getTimestamp("EVNET_TIME"));
				siminstall.setStaffNo(rs.getString("STAFF_NO"));
				siminstall.setOrgNo(rs.getString("ORG_NO"));
				siminstall.setFromId(rs.getString("FROM_ID"));
				siminstall.setEventType(rs.getByte("EVENT_TYPE"));
				siminstall.setFlowStatusCode(rs.getString("FLOW_STATUS_CODE"));
				siminstall.setEventCode(rs.getString("EVENT_CODE"));
				siminstall.setEventName(rs.getString("EVENT_NAME"));
				siminstall.setEventData(rs.getString("EVENT_DATA"));
				return siminstall;
			} catch (Exception e) {
				return null;
			}
		}
	}

	class fibRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			FEventInfoBean siminstall = new FEventInfoBean();
			try {
				siminstall.setEventId(rs.getLong("EVENT_ID"));
				siminstall.setEvnetTime(rs.getTimestamp("EVNET_TIME"));
				siminstall.setStaffNo(rs.getString("STAFF_NO"));
				siminstall.setOrgNo(rs.getString("ORG_NO"));
				siminstall.setOrgName(rs.getString("ORG_NAME"));
				siminstall.setFromId(rs.getString("FROM_ID"));
				siminstall.setEventType(rs.getByte("EVENT_TYPE"));
				siminstall.setFlowStatusCode(rs.getString("FLOW_STATUS_CODE"));
				siminstall.setEventCode(rs.getString("EVENT_CODE"));
				siminstall.setEventName(rs.getString("EVENT_NAME"));
				siminstall.setEventData(rs.getString("EVENT_DATA"));
				return siminstall;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	@Override
	public void deleteEventShield(long eventId) throws DBAccessException {

		StringBuffer sb=new StringBuffer();
		sb.append("DELETE FROM F_EVENT_SHIELD FS WHERE FS.EVENT_ID = ?");

		String sql=sb.toString();
		this.getJdbcTemplate().update(sql, new Object[] { eventId });
		
	}

	@Override
	public void deleteEventSrc(long eventId) throws DBAccessException {
		StringBuffer sb=new StringBuffer();
		sb.append("DELETE FROM F_EVENT_SRC FS WHERE FS.EVENT_ID = ?");

		String sql=sb.toString();
		this.getJdbcTemplate().update(sql, new Object[] { eventId });
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FEventSrc> getEventInfo(long eventId) throws DBAccessException {
		String[] para = { String.valueOf(eventId) };
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT EVENT_ID,\n");
		sb.append("       EVNET_TIME,\n");
		sb.append("       STAFF_NO,\n");
		sb.append("       ORG_NO,\n");
		sb.append("       FROM_ID,\n");
		sb.append("       EVENT_TYPE,\n");
		sb.append("       FLOW_STATUS_CODE,\n");
		sb.append("       EVENT_CODE,\n");
		sb.append("       EVENT_NAME,\n");
		sb.append("       EVENT_DATA\n");
		sb.append("  FROM F_EVENT_SRC FS\n");
		sb.append("WHERE FS.EVENT_ID = ?");
		return this.getJdbcTemplate().query(sb.toString(), para, new daRowMapper());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FEventInfoBean> getEventInfo(String fromId, Byte eventType) throws DBAccessException {
		List<Object> paraList = new ArrayList<Object>();
		paraList.add(fromId);
		paraList.add(eventType);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT FS.EVENT_ID,\n");
		sb.append("       FS.EVNET_TIME,\n");
		sb.append("       FS.STAFF_NO,\n");
		sb.append("       FS.ORG_NO,\n");
		sb.append("       O.ORG_NAME,\n");
		sb.append("       FS.FROM_ID,\n");
		sb.append("       FS.EVENT_TYPE,\n");
		sb.append("       FS.FLOW_STATUS_CODE,\n");
		sb.append("       FS.EVENT_CODE,\n");
		sb.append("       FS.EVENT_NAME,\n");
		sb.append("       FS.EVENT_DATA\n");
		sb.append("  FROM F_EVENT_SRC FS, O_ORG O\n");
		sb.append("WHERE FS.FROM_ID = ?\n");
		sb.append("AND O.ORG_NO = FS.ORG_NO\n");
		sb.append("AND FS.EVENT_TYPE = ?");
		return this.getJdbcTemplate().query(sb.toString(), paraList.toArray(), new fibRowMapper());
	}

}
