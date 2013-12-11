package com.nari.advapp.vipconsman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.advapp.vipconsman.LogRelease;
import com.nari.advapp.vipconsman.LogReleaseQueryDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import common.Logger;

public class LogReleaseQueryDaoImpl extends JdbcBaseDAOImpl implements LogReleaseQueryDao {
	private Logger log=Logger.getLogger(LogReleaseQueryDao.class);
	/**
	 * @description 查询并统计日志发布结果
	 * @param pubDate,pSysUser
	 * @return logReleaseListr
	 */	
	@SuppressWarnings("unchecked")
	public List<LogRelease> queryLogStastics(Date pubDate, PSysUser pSysUser)
	{
		List<LogRelease> logReList = null;
	try
	{
				
		StringBuilder sb=new StringBuilder();
		if("02".equals(pSysUser.getAccessLevel()))
		{
			String sql=
				"select tt.no as org_no,tt.successnum as successnum,tt.failnum as failnum, o.org_short_name ORG_NAME\n" +
				"  from (select no, sum(decode(err_no, 1, 1, 0)) as SUCCESSNUM,sum(decode(err_no, 2, 1,3,1,0)) as FAILNUM\n" + 
				"          from (select ip.*, substr(org_no, 1, 5) as no\n" + 
				"                  from i_pub_log ip where ip.pub_time=trunc(?)) tem\n" + 
				"          join o_org oo\n" + 
				"            on (tem.no = oo.org_no)\n" + 
				"         group by no) tt\n" + 
				"  join o_org o\n" + 
				"    on (tt.no = o.org_no)\n" + 
				"  join p_access_org pao\n" + 
				"    on (pao.org_no = tt.no)\n" + 
				" where pao.staff_no = ? and o.org_type='03'";
			logReList=super.getJdbcTemplate().query(sql, new Object[]{pubDate,pSysUser.getStaffNo()}, new LogReleaseMapper());		
	    }
		else if("03".equals(pSysUser.getAccessLevel()))
		{
			String sql=
				"select tt.no as org_no,tt.successnum as successnum,tt.failnum as failnum, o.org_short_name ORG_NAME\n" +
				"  from (select no, sum(decode(err_no, 1, 1, 0)) as SUCCESSNUM,sum(decode(err_no, 2, 1,3,1,0)) as FAILNUM\n" + 
				"          from (select ip.*, substr(org_no, 1, 5) as no\n" + 
				"                  from i_pub_log ip where ip.pub_time=trunc(?)) tem\n" + 
				"          join o_org oo\n" + 
				"            on (tem.no = oo.org_no)\n" + 
				"         group by no) tt\n" + 
				"  join o_org o\n" + 
				"    on (tt.no = o.org_no)\n" + 
				"  join p_access_org pao\n" + 
				"    on (pao.org_no = tt.no)\n" + 
				" where pao.staff_no = ? and o.org_type='04'";
			logReList=super.getJdbcTemplate().query(sql, new Object[]{pubDate,pSysUser.getStaffNo()}, new LogReleaseMapper());
		}
		else if("04".equals(pSysUser.getAccessLevel()))
		{
			String sql=
				"select tt.no as org_no,tt.successnum as successnum,tt.failnum as failnum, o.org_short_name ORG_NAME\n" +
				"  from (select no, sum(decode(err_no, 1, 1, 0)) as SUCCESSNUM,sum(decode(err_no, 2, 1,3,1,0)) as FAILNUM\n" + 
				"          from (select ip.*, substr(org_no, 1, 5) as no\n" + 
				"                  from i_pub_log ip where ip.pub_time=trunc(?)) tem\n" + 
				"          join o_org oo\n" + 
				"            on (tem.no = oo.org_no)\n" + 
				"         group by no) tt\n" + 
				"  join o_org o\n" + 
				"    on (tt.no = o.org_no)\n" + 
				"  join p_access_org pao\n" + 
				"    on (pao.org_no = tt.no)\n" + 
				" where pao.staff_no = ? and o.org_type='04'";
			logReList=super.getJdbcTemplate().query(sql, new Object[]{pubDate,pSysUser.getStaffNo()}, new LogReleaseMapper());
		}
		else if("06".equals(pSysUser.getAccessLevel()))
		{
			String sql=
				"select tt.no as org_no,tt.successnum as successnum,tt.failnum as failnum, o.org_short_name ORG_NAME\n" +
				"  from (select no, sum(decode(err_no, 1, 1, 0)) as SUCCESSNUM,sum(decode(err_no, 2, 1,3,1,0)) as FAILNUM\n" + 
				"          from (select ip.*, substr(org_no, 1, 5) as no\n" + 
				"                  from i_pub_log ip where ip.pub_time=trunc(?)) tem\n" + 
				"          join o_org oo\n" + 
				"            on (tem.no = oo.org_no)\n" + 
				"         group by no) tt\n" + 
				"  join o_org o\n" + 
				"    on (tt.no = o.org_no)\n" + 
				"  join p_access_org pao\n" + 
				"    on (pao.org_no = tt.no)\n" + 
				" where pao.staff_no = ? and o.org_type='06'";
			logReList=super.getJdbcTemplate().query(sql, new Object[]{pubDate,pSysUser.getStaffNo()}, new LogReleaseMapper());
		}
		return  logReList;
	}catch(Exception e)
	{
		log.debug("DAO层查询时出错");
		e.printStackTrace();
	}
	return  logReList;
}
	class LogReleaseMapper implements RowMapper
	{

		@Override
		public Object mapRow(ResultSet rs, int par) throws SQLException {
			LogRelease logRelease=new LogRelease();
			logRelease.setOrgNo(rs.getString("ORG_NO"));
			logRelease.setOrgName(rs.getString("ORG_NAME"));
//			logRelease.setPubDate(rs.getDate("PUB_TIME"));
			logRelease.setSuccessNum(rs.getInt("SUCCESSNUM"));
			logRelease.setFailNum(rs.getInt("FAILNUM"));
			return logRelease;
		}
		
	}
}
