package com.nari.mydesk.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.mydesk.CommFlowDTO;
import com.nari.mydesk.ElecOrderDTO;
import com.nari.mydesk.IMyDeskDao;

public class MyDeskDaoImpl extends JdbcBaseDAOImpl implements IMyDeskDao {
	
	private final static Logger log = Logger.getLogger(MyDeskDaoImpl.class);

	class ElecOrderRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ElecOrderDTO elecOrder = new ElecOrderDTO();
			elecOrder.setOrder(rs.getInt("rownum"));
			elecOrder.setCons_no(rs.getString("cons_no"));
			elecOrder.setCons_name(rs.getString("cons_name"));
			elecOrder.setOrg_name(rs.getString("org_name"));
			elecOrder.setPap_e(rs.getDouble("pap_e"));
			return elecOrder;
		}
		
	}
	
	/**
	 * @desc 地区用电大户排名
	 * @param staffNo
	 * @param date
	 * @return
	 */
	public List <ElecOrderDTO> findAllOrder(String staff_no,String date){
		String sql = "SELECT ROWNUM,tt.*" +
				"  FROM (SELECT c.cons_no, c.cons_name, o.org_name, stat.pap_e" +
				"          FROM (SELECT SUM(a.pap_e) AS pap_e, a.cons_no" +
				"                  FROM a_cons_stat_d a" +
				"                 WHERE a.pap_e IS NOT NULL" +
				"                   AND to_char(a.stat_date, 'yyyy-mm') = ?" +
				"                   GROUP BY a.cons_no) stat," +
				"                 p_access_org p," +
				"                 c_cons c," +
				"                 o_org o" +
				"         WHERE p.staff_no = ?" +
				"           AND p.org_no = c.area_no" +
				"           AND o.org_no = p.org_no" +
				"           AND stat.cons_no = c.cons_no" +
				"         ORDER BY stat.pap_e DESC) tt WHERE ROWNUM < 11";
		log.debug("地区用电大户排名:" +sql);
		List<ElecOrderDTO> ls = this.getJdbcTemplate().query(sql, new Object[]{date,staff_no},new ElecOrderRowMapper());
		return ls;
	}

	class CommFlowRowMapper implements RowMapper{

		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CommFlowDTO commFlow = new CommFlowDTO();
			commFlow.setFlow(rs.getDouble("sumflow"));
			commFlow.setFlow_date(rs.getString("flow_date"));
			return commFlow;
		}
		
	}
	
	/**
	 * 通信流量查询
	 */
	public List<CommFlowDTO> findAllCommFlow(String staffNo){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT *\n");
		sql.append("  FROM (SELECT SUM(tt.flow) AS sumflow,\n");
		sql.append("               to_char(tt.flow_date, 'yyyy-mm-dd') AS flow_date\n");
		sql.append("          FROM (SELECT (a.down_flow + a.up_flow) AS flow, a.flow_date\n");
		sql.append("                  FROM a_tmnl_flow_d a, vw_tmnl_run t, p_access_org pa\n");
		sql.append("                 WHERE a.org_no = t.org_no\n");
		sql.append("                   AND t.area_no = pa.org_no\n");
		sql.append("                   AND pa.staff_no = ?\n");
		sql.append("                   AND a.flow_date BETWEEN TRUNC(SYSDATE - 14) AND SYSDATE) tt\n");
		sql.append("         GROUP BY tt.flow_date)\n");
		sql.append(" ORDER BY flow_date ASC\n");
		log.debug("通信流量查询：\n" + sql.toString());
		
		List<CommFlowDTO> ls = this.getJdbcTemplate().query(sql.toString(),new Object[]{staffNo}, new CommFlowRowMapper());
		return ls;
	}
}
