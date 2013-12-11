package com.nari.qrystat.querycollpoint.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.querycollpoint.ExcptionReport;
import com.nari.qrystat.querycollpoint.ExcptionReportDao;
import com.nari.qrystat.querycollpoint.VwConsType;
import com.nari.support.Page;

public class ExcptionReportDaoImpl extends JdbcBaseDAOImpl implements
		ExcptionReportDao {
	@Override
	public Page<ExcptionReport> findExcptionCount(String treeType,String orgNo, String orgType,
			PSysUser pSysUser, String dateStart, String dateEnd, long start, int limit) {
		StringBuffer sb = new StringBuffer();
		String addString = "";
		String[] param = {dateStart,dateEnd,orgType,orgNo};
		if(treeType.equals("04")){
			addString = "O.P_ORG_NO=?";
		}else if(treeType.equals("03")){
			addString = "O.P_ORG_NO in (select ORG_NO from o_org where P_ORG_NO=?)";
		}else{
			addString = "D.ORG_NO = ?";
		}
		sb.append(
				"SELECT D.ORG_NO,O.ORG_NAME, COUNT(1) as exceptCount," +
				"COUNT(CASE WHEN F.FLOW_STATUS_CODE <> '04' AND F.FLOW_STATUS_CODE <> '06' THEN 0 ELSE  NULL  END) as OCount," +
				"COUNT(CASE WHEN F.FLOW_STATUS_CODE = '04' THEN 0 ELSE  NULL  END) as FCount" +
				"  FROM E_TMNL_EVENT EV, E_DATA_MP D,C_CONS C,O_ORG O,F_EVENT_SRC F\n" + 
				" WHERE EV.ID = D.ID AND D.CONS_NO = C.CONS_NO AND O.ORG_NO= D.ORG_NO\n" + 
				"   AND EV.EVENT_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n" + 
				"   AND EV.EVENT_TIME <= TO_DATE(?, 'yyyy-mm-dd')\n" + 
				"   AND C.CONS_TYPE = ? AND "+addString+"\n" + 
				" GROUP BY D.ORG_NO,O.ORG_NAME");
		logger.debug(sb.toString());
		// return (List<Cmeter>)
		// super.getJdbcTemplate().query(sb.toString().toUpperCase(),
		// new Object[] { CONS_NO }, new CMRowMapper());
		return super.pagingFind(sb.toString(), start, limit, new ExcptionReportRowMapper(),
				param);
	}
	
	public List<VwConsType> findConsType() {

		StringBuffer sb=new StringBuffer();
		sb.append(" select cons_type,cons_type_name from vw_cons_type\n");

		String sql=sb.toString();
		logger.debug(sb.toString());
		return super.getJdbcTemplate().query(sql, new Object[]{},
				new VwConsTypeRowMapper());
	}

}
class ExcptionReportRowMapper implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
	ExcptionReport excptionreport = new ExcptionReport();
	 try { 
	excptionreport.setOrgNo(rs.getString("ORG_NO"));
	excptionreport.setOrgName(rs.getString("ORG_NAME"));
	excptionreport.setExceptcount(rs.getLong("EXCEPTCOUNT"));
	excptionreport.setOcount(rs.getLong("OCOUNT"));
	excptionreport.setFcount(rs.getLong("FCOUNT"));
	long ecount = rs.getLong("EXCEPTCOUNT");
	long fcount = rs.getLong("FCOUNT");
	float fecount = fcount*100/ecount;
	excptionreport.setFecount(fecount);
	return excptionreport;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}


class VwConsTypeRowMapper implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
	VwConsType vwconstype = new VwConsType();
	 try { 
	vwconstype.setConsType(rs.getString("CONS_TYPE"));
	vwconstype.setConsTypeName(rs.getString("CONS_TYPE_NAME"));
	return vwconstype;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}