package com.nari.runman.abnormalhandle.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.basicdata.BProtocolEvent;
import com.nari.runman.abnormalhandle.EventInfoDao;

public class EventInfoDaoImpl extends JdbcBaseDAOImpl implements EventInfoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<BProtocolEvent> findPEInfo(String proNo) {

		StringBuffer sb = new StringBuffer();
		String[] para = { proNo };
		sb.append("SELECT EVENT_NO, EVENT_NAME, EVENT_TYPE, EVENT_LEVEL, IS_REC\n")
				.append("  FROM B_PROTOCOL_EVENT\n").append(
						" WHERE PROTOCOL_CODE = ?\n").append(
						" ORDER BY EVENT_NO");

		return this.getJdbcTemplate()
				.query(sb.toString(), para, new peMapper());
	}

	// BProtocolEvent的RowMapper类
	class peMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			BProtocolEvent pe = new BProtocolEvent();
			try {
				pe.setEventNo(rs.getString("EVENT_NO"));
				pe.setEventName(rs.getString("EVENT_NAME"));
				pe.setEventType(rs.getString("EVENT_TYPE"));
				pe.setEventLevel(rs.getString("EVENT_LEVEL"));
				pe.setIsRec(rs.getByte("IS_REC"));
				return pe;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}

	@Override
	public void updateEventInof(final List<BProtocolEvent> eventInfo) {
		String sql = "UPDATE B_PROTOCOL_EVENT SET EVENT_LEVEL = ? , IS_REC = ? WHERE EVENT_NO = ?";

		this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				int i = 0;
				BProtocolEvent bpe = eventInfo.get(index);
				ps.setString(++i, bpe.getEventLevel());
				ps.setByte(++i, bpe.getIsRec());
				ps.setString(++i, bpe.getEventNo());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return eventInfo.size();
			}
		});
	
		
	}
}
