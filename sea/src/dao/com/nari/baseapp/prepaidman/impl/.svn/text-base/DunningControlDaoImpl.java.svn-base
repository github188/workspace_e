package com.nari.baseapp.prepaidman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.prepaidman.DunningControlBean;
import com.nari.baseapp.prepaidman.DunningControlDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;

public class DunningControlDaoImpl extends JdbcBaseDAOImpl implements
		DunningControlDao {

	@Override
	public List<DunningControlBean> queryDunningControlUser(String nodeId,
			PSysUser user) {
		String sql = 
			"SELECT O.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.PS_ENSURE_FLAG TMNL_PAUL_POWER,\n" + 
			"       R.PROTOCOL_CODE\n" + 
			"  FROM W_URGEFEE_PARAM W, C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			" WHERE W.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n" + 
			"   AND R.CONS_NO = C.CONS_NO\n" + 
			"   AND R.ORG_NO = O.ORG_NO";
//		List<Object> args = new ArrayList<Object>();
		return getJdbcTemplate().query(sql, new dunningControlRowMapper());
	}
	
	class dunningControlRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			DunningControlBean dunningControlBean = new DunningControlBean();
			try {
				dunningControlBean.setOrgName(rs.getString("ORG_NAME"));
				dunningControlBean.setOrgNo(rs.getString("ORG_NO"));
				dunningControlBean.setConsNo(rs.getString("CONS_NO"));
				dunningControlBean.setConsName(rs.getString("CONS_NAME"));
				dunningControlBean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				dunningControlBean.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				dunningControlBean.setTmnlPaulPower(rs.getString("TMNL_PAUL_POWER"));
				dunningControlBean.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				
				return dunningControlBean;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
