package com.nari.runman.feildman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;

import com.nari.basicdata.BCommProtItemListId;

import com.nari.runman.feildman.ItemListDao;

public class ItemListDaoImpl extends JdbcBaseDAOImpl implements ItemListDao {

	@Override
	public List<BCommProtItemListId> findItemList(String proItemNO) {
		StringBuffer sb = new StringBuffer();
		String[] para = { proItemNO };
		sb.append("select prot_item_no, data_name, data_value, disp_sn ,is_default\n")
		  .append("from b_comm_prot_item_list\n")
		  .append("where prot_item_no = ?\n");

		return super.pagingFindList(sb.toString(), 0, 100, new bpiMapper(),
				para);
	}

	// BCommProtItemList的RowMapper类
	class bpiMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			BCommProtItemListId bpiList = new BCommProtItemListId();
			try {
				bpiList.setProtItemNo(rs.getString("prot_item_no"));
				bpiList.setDataValue(rs.getString("data_value"));
				bpiList.setDataName(rs.getString("data_name"));
				bpiList.setDispSn(rs.getShort("disp_sn"));
				bpiList.setIsDefault(rs.getBoolean("is_default"));
				return bpiList;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}
}
