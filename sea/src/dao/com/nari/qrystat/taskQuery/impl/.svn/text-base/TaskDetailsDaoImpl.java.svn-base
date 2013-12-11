package com.nari.qrystat.taskQuery.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.qrystat.taskQuery.TaskDetailsDao;
import com.nari.qrystat.taskQuery.TaskDetailsDto;
import com.nari.qrystat.taskQuery.TaskStatSearchInfoBean;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class TaskDetailsDaoImpl extends JdbcBaseDAOImpl implements TaskDetailsDao {

	@Override
	public Page<TaskDetailsDto> getEventInfo(TaskStatSearchInfoBean eventInfo, long start, int limit) throws DBAccessException{
		List<Object> infoList = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT F.EVENT_ID,\n");
		sb.append("       O.ORG_NAME,\n");
		sb.append("       F.EVNET_TIME,\n");
		sb.append("       F.STAFF_NO,\n");
		sb.append("       O.ORG_NO,\n");
		sb.append("       F.FROM_ID,\n");
		sb.append("       F.EVENT_TYPE,\n");
		sb.append("       F.FLOW_STATUS_CODE,\n");
		sb.append("       F.EVENT_CODE,\n");
		sb.append("       F.EVENT_NAME,\n");
		sb.append("       F.EVENT_DATA\n");
		sb.append(" FROM F_EVENT_SRC F, O_ORG O   \n");
		sb.append("WHERE F.ORG_NO = O.ORG_NO  \n");
		sb.append("AND F.EVNET_TIME BETWEEN ? AND ?\n");
		infoList.add(eventInfo.getStartTime());
		infoList.add(eventInfo.getEndTime());
		if (null != eventInfo.getEventType()
				&& !"".equals(eventInfo.getEventType())) {
			sb.append("  AND F.EVENT_TYPE = ?\n");
			infoList.add(eventInfo.getEventType());
		}
		if (null != eventInfo.getFlowStatusCode()
				&& !"".equals(eventInfo.getFlowStatusCode())) {
			sb.append("  AND F.FLOW_STATUS_CODE = ?");
			infoList.add(eventInfo.getFlowStatusCode());
		}
		if (null != eventInfo.getOrgNo()
				&& !"".equals(eventInfo.getOrgNo())) {
			sb.append("  AND F.ORG_NO = ?");
			infoList.add(eventInfo.getOrgNo());
		}
//		sb.append(" ORDER BY EVENT_ID  \n");
		sb.append("ORDER BY O.ORG_NO, F.FLOW_STATUS_CODE  \n");

		String sql = sb.toString();
		this.logger.debug(sql);
		return super.pagingFind(sql, start, limit, new daRowMapper(), infoList.toArray());
	}
	
	/**
	 * 
	 * 工单明细映射类
	 * 
	 * @author ChunMingLi
	 * 2010-5-13
	 *
	 */
	class daRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TaskDetailsDto detailsDto = new TaskDetailsDto();
			try {
				detailsDto.setEventID(rs.getLong("EVENT_ID"));
				detailsDto.setEvnetTime(rs.getTimestamp("EVNET_TIME"));
				detailsDto.setStaffNo(rs.getString("STAFF_NO"));
				detailsDto.setOrgNo(rs.getString("ORG_NO"));
				detailsDto.setOrgName(rs.getString("ORG_NAME"));
				detailsDto.setFromId(rs.getString("FROM_ID"));
				detailsDto.setEventType(rs.getByte("EVENT_TYPE"));
				detailsDto.setFlowStatusCode(rs.getString("FLOW_STATUS_CODE"));
				detailsDto.setEventCode(rs.getString("EVENT_CODE"));
				detailsDto.setEventName(rs.getString("EVENT_NAME"));
				detailsDto.setEventData(rs.getString("EVENT_DATA"));
				return detailsDto;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
