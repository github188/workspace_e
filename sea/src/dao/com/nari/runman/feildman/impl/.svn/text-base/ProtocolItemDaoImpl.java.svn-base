package com.nari.runman.feildman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.basicdata.BCommProtocolItem;
import com.nari.runman.feildman.ProtocolItemDao;

public class ProtocolItemDaoImpl extends JdbcBaseDAOImpl implements
		ProtocolItemDao {

	@Override
	public List<BCommProtocolItem> findType(String proNO) {
		StringBuffer sb = new StringBuffer();
		String[] para = { proNO };
		sb.append("select prot_item_no, prot_item_name, prot_item_sn,\n")
		.append("block_rule, unit, default_vaule ,\n")
		.append("bytes, data_style, occupy_bits, html_format, max_val, min_val\n")
		.append("from b_comm_protocol_item\n")
		.append("where protocol_no = ? order by prot_item_sn\n");
		return super
				.pagingFindList(sb.toString(), 0, 100, new cpMapper(), para);
	}

	// BCommProtocol的RowMapper类
	class cpMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			BCommProtocolItem cp = new BCommProtocolItem();
			try {
				cp.setProtItemNo(rs.getString("prot_item_no"));
				cp.setProtItemName(rs.getString("prot_item_name"));
				cp.setUnit(rs.getString("unit"));
				cp.setDefaultVaule(rs.getString("default_vaule"));
				cp.setProtItemSn(rs.getShort("prot_item_sn"));
				cp.setBlockRule(rs.getString("block_rule"));
				cp.setBytes(rs.getString("bytes"));
				cp.setOccupyBits(rs.getString("occupy_bits"));
				cp.setDataStyle(rs.getString("data_style"));
				cp.setMaxVal(rs.getString("max_val"));
				cp.setMinVal(rs.getString("min_val"));
				cp.setHtmlFormat(rs.getByte("html_format"));
				return cp;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}
}
