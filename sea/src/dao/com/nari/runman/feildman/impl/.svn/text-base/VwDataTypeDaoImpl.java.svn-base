package com.nari.runman.feildman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.basicdata.VwDataTypePBean;
import com.nari.runman.feildman.VwDataTypeDao;

public class VwDataTypeDaoImpl extends JdbcBaseDAOImpl implements VwDataTypeDao {

	@Override
	public List<VwDataTypePBean> findType(String proCode, String pproNO) {
		StringBuffer sb = new StringBuffer();
		String[] para = {"DATA_TYPE", proCode };
		sb.append("SELECT\n")
		.append("  param_item_no AS data_type,\n")
		.append("  param_item_name AS data_name\n")
		.append("FROM\n")
		.append("  b_sys_parameter a\n")
		.append("WHERE\n")
		.append("  PARAM_NO = ?\n")
		.append("  AND param_item_val = ?\n")
		.append("  ORDER BY a.PARAM_ITEM_SN");

		return super.pagingFindList(sb.toString(), 0, 100, new dtMapper(), para);
	}

	// VwProtocolCodeBean的RowMapper类
	class dtMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VwDataTypePBean dt = new VwDataTypePBean();
			try {
				dt.setDataName(rs.getString("data_name"));
				dt.setDataType(rs.getString("data_type"));
				return dt;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}

}
