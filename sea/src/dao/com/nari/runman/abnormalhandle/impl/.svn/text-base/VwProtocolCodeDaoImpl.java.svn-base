package com.nari.runman.abnormalhandle.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.basicdata.VwProtocolCodeBean;
import com.nari.runman.abnormalhandle.VwProtocolCodeDao;

/**
 * 类 TerminalParaSetDaoImpl
 * 
 * @author longkc
 * @describe 终端参数设定规约参数取得
 */
public class VwProtocolCodeDaoImpl extends JdbcBaseDAOImpl implements
		VwProtocolCodeDao {

	/**
	 * 
	 */
	@Override
	public List<VwProtocolCodeBean> findType() {
		String sql = "SELECT protocol_code, protocol_name, is_default\n"
				+ "FROM vw_protocol_code ORDER BY is_default Desc, protocol_code asc";

		return super.pagingFindList(sql, 0,20,new ProMapper());
	}

	// VwProtocolCodeBean的RowMapper类
	class ProMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VwProtocolCodeBean pro = new VwProtocolCodeBean();
			try {
				pro.setProtocolCode(rs.getString("protocol_code"));
				pro.setProtocolName(rs.getString("protocol_name"));
				pro.setIsDefault(rs.getInt("is_default"));
				return pro;
			} catch (Exception e) {
				// throw new DBAccessException("ProMapper出现错误！");
				return null;
			}
		}

	}
}
