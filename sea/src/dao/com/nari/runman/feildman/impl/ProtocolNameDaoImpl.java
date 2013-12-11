package com.nari.runman.feildman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.basicdata.BCommProtocol;
import com.nari.runman.feildman.ProtocolNameDao;

public class ProtocolNameDaoImpl extends JdbcBaseDAOImpl implements
		ProtocolNameDao {

	@Override
	public List<BCommProtocol> findType(String dataType, String proCode) {
		StringBuffer sb = new StringBuffer();
		String[] para = {dataType, proCode};
//		String[] para = {dataType, proCode + "04"};
		sb.append("select protocol_no, protocol_name, short_name from b_comm_protocol\n")
		  .append("where data_type = ? and protocol_code = ?\n")
//		  .append("where data_type = ? and p_protocol_no = ?\n")
		  .append("order by protocol_no");

		return super.pagingFindList(sb.toString(), 0, 100,
				new dtMapper(), para);
	}
	
	// BCommProtocol的RowMapper类
	class dtMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			BCommProtocol bp = new BCommProtocol();
			try {
				bp.setProtocolNo(rs.getString("protocol_no"));
				bp.setProtocolName(rs.getString("protocol_name"));
				bp.setShortName(rs.getString("short_Name"));
				return bp;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}

}
