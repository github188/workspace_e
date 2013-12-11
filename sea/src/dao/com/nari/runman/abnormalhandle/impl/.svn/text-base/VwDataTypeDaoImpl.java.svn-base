package com.nari.runman.abnormalhandle.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.basicdata.VwDataTypePBean;
import com.nari.runman.abnormalhandle.VwDataTypeDao;

public class VwDataTypeDaoImpl extends JdbcBaseDAOImpl implements VwDataTypeDao {

	@Override
	public List<VwDataTypePBean> findType(String proCode, String pproNO) {
		StringBuffer sb = new StringBuffer();
		String[] para = {"EVENT_PROT_NO", proCode };
		sb.append("SELECT\n")
		.append("  dict_no AS data_type,\n")
		.append("  dict_name AS data_name\n")
		.append("FROM\n")
		.append("  b_sys_dictionary\n")
		.append("WHERE\n")
		.append("  dict_catalog = ?\n")
		.append("  AND dict_value = ?\n");

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
