package com.nari.advapp.vipconsman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.springframework.jdbc.core.RowMapper;

import com.nari.advapp.VipConsPowerCurveBean;
import com.nari.advapp.VipConsPowerOverBean;
import com.nari.advapp.VipConsPowerVstatBean;
import com.nari.advapp.vipconsman.VipConsMonitorDao;
import com.nari.advapp.vipconsman.VipConsMonitorDto;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;

public class VipConsMonitorDaoImpl  extends JdbcBaseDAOImpl implements VipConsMonitorDao{
	
	/**
	 * 按日统计需用系数
	 * @param Day
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsMonitorDto> queryVipConsReqRatioByDay(String day, long start, long limit){
       StringBuffer sb=new StringBuffer();
       sb.append("SELECT O.ORG_NAME,\n")
	     .append("       CTD.CONS_NO,\n") 
	     .append("       C.CONS_NAME,\n") 
	     .append("       ROUND(CTD.MAX_P,2) MAX_P,\n") 
	     .append("       ROUND(CTD.CONTRACT_CAP,2) CONTRACT_CAP,\n") 
	     .append("       ROUND(CTD.MAX_P / CTD.CONTRACT_CAP *100,2) REQ_RATIO,\n") 
	     .append("       CTD.STAT_DATE\n") 
	     .append("  FROM A_CONS_TOTAL_D CTD, C_CONS_VIP CVIP, C_CONS C, O_Org O\n" )
	     .append(" WHERE O.ORG_NO = CTD.ORG_NO\n")
	     .append("   AND CTD.CONS_NO = C.CONS_NO\n") 
	     .append("   AND CTD.CONS_NO = CVIP.CONS_NO\n" ) 
	     .append("   AND CTD.STAT_DATE =to_date(?, 'yyyy-mm-dd')");
       try{
    	   return super.pagingFind(sb.toString(), start, limit, new VipConsReqRatioRowMapper(), new Object[]{day});
        } catch(RuntimeException e){
           this.logger.debug("按日统计需用系数出错！");
           throw e;
        }
	}
	
   /**
   * 按月统计需用系数
   * @param Month
   * @param start
   * @param limit
   * @return
   */
	public Page<VipConsMonitorDto> queryVipConsReqRatioByMonth(String month, long start, long limit){
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT O.ORG_NAME,\n")
			.append("       CTD.CONS_NO,\n") 
			.append("       C.CONS_NAME,\n") 
			.append("       CTD.MAX_P,\n") 
			.append("       CTD.CONTRACT_CAP,\n")
			.append("       CTD. REQ_RATIO,\n")
			.append("       CTD.STAT_DATE\n") 
		    .append("  FROM (SELECT ORG_NO,\n" )
			.append("               CONS_NO,\n") 
			.append("               ROUND(MAX_P,2) MAX_P,\n" ) 
			.append("               ROUND(CONTRACT_CAP,2) CONTRACT_CAP,\n" ) 
			.append("               STAT_DATE,\n")
			.append("               ROUND(MAX_P / CONTRACT_CAP * 100, 2) REQ_RATIO1,\n") 
			.append("               ROUND(MAX(MAX_P / CONTRACT_CAP)\n")
			.append("                     OVER(PARTITION BY CONS_NO) * 100,\n") 
			.append("                     2) REQ_RATIO\n") 
			.append("          FROM A_CONS_TOTAL_D\n") 
	        .append("		   WHERE STAT_DATE >= to_date(?||'-01', 'yyyy-mm-dd')\n")
        	.append("     			 AND STAT_DATE < last_day(to_date(?,'yyyy-mm'))+1) CTD,\n")
			.append("        C_CONS_VIP CVIP,C_CONS C,\n")
			.append("        O_Org O\n") 
			.append(" WHERE CTD. REQ_RATIO1 = CTD.REQ_RATIO\n") 
			.append("   AND CTD.ORG_NO = O.ORG_NO\n") 
			.append("   AND CTD.CONS_NO = C.CONS_NO\n") 
			.append("   AND CTD.CONS_NO = CVIP.CONS_NO\n");
		 try{
	    	   return super.pagingFind(sb.toString(), start, limit, new VipConsReqRatioRowMapper(), new Object[]{month,month});
	        } catch(RuntimeException e){
	           this.logger.debug("按月统计需用系数出错！");
	           throw e;
	        }
	}
	
	/**
	 * 按年统计需用系数
	 * @param Year
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<VipConsMonitorDto> queryVipConsReqRatioByYear(String year, long start, long  limit){
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT O.ORG_NAME,\n")
			.append("       CTD.CONS_NO,\n") 
			.append("       C.CONS_NAME,\n") 
			.append("       CTD.MAX_P,\n") 
			.append("       CTD.CONTRACT_CAP,\n")
			.append("       CTD. REQ_RATIO,\n")
			.append("       CTD.STAT_DATE\n") 
		    .append("  FROM (SELECT ORG_NO,\n" )
			.append("               CONS_NO,\n") 
			.append("               ROUND(MAX_P,2) MAX_P,\n" ) 
			.append("               ROUND(CONTRACT_CAP,2) CONTRACT_CAP,\n" ) 
			.append("               STAT_DATE,\n")
			.append("               ROUND(MAX_P / CONTRACT_CAP * 100, 2) REQ_RATIO1,\n") 
			.append("               ROUND(MAX(MAX_P / CONTRACT_CAP)\n")
			.append("                     OVER(PARTITION BY CONS_NO) * 100,\n") 
			.append("                     2) REQ_RATIO\n") 
			.append("          FROM A_CONS_TOTAL_D\n")
			.append("		   WHERE STAT_DATE >= to_date(?||'-01-01', 'yyyy-mm-dd')\n")
        	.append("     		 AND STAT_DATE < to_date(?||'-12-31','yyyy-mm-dd')+1) CTD,\n")
			.append("       C_CONS_VIP CVIP,C_CONS C,\n")
			.append("       O_Org O\n") 
			.append(" WHERE CTD. REQ_RATIO1 = CTD.REQ_RATIO\n") 
			.append("   AND CTD.ORG_NO = O.ORG_NO\n") 
			.append("   AND CTD.CONS_NO = C.CONS_NO\n") 
			.append("   AND CTD.CONS_NO = CVIP.CONS_NO\n");
		 try{
	    	   return super.pagingFind(sb.toString(), start, limit, new VipConsReqRatioRowMapper(), new Object[]{year,year});
	        } catch(RuntimeException e){
	           this.logger.debug("按年统计需用系数出错！");
	           throw e;
	        }
	}
	
//-----------------------------------------------------------------统计负荷超容量开始-----------------------------------------------------------------------
	
	@Override
	public Page<VipConsPowerOverBean> queryVipCOnsPowerOverByDay(String day,
			long start, int limit) {
		String sb=
			"select o.org_name,\n" +
			"       c.cons_no,\n" + 
			"       c.cons_name,\n" + 
			"       a.contract_cap,\n" + 
			"       a.avg_p,\n" + 
			"       case when a.avg_p>a.contract_cap then '是' else '否' end avg_p_over,\n" + 
			"       a.up_cap_cnt\n" + 
			"  from a_cons_total_d a, c_cons c, o_org o, c_cons_vip vip\n" + 
			" where a.org_no = o.org_no\n" + 
			"   and a.cons_no = c.cons_no\n" + 
			"   and c.cons_no = vip.cons_no\n" + 
			"   and a.stat_date = to_date(?, 'yyyy-mm-dd')";
	       try{
	    	   return super.pagingFind(sb, start, limit, new VipConsPowerOverDayRowMapper(), new Object[]{day});
	        } catch(RuntimeException e){
	           this.logger.debug("按日统计负荷超容量出错！");
	           throw e;
	        }
	}

	@Override
	public Page<VipConsPowerOverBean> queryVipCOnsPowerOverByMonth(
			String month, long start, int limit) {
		String sql = 
			"select o.org_name,\n" +
			"     a.cons_no,\n" + 
			"     max(c.cons_name) cons_name,\n" + 
			"     min(a.contract_cap) contract_cap,\n" + 
			"	  decode(sum(a.avg_p),\n" + 
			"              null,\n" + 
			"              null,\n" + 
			"              round(sum(a.avg_p) /\n" + 
			"                    (last_day(to_date(?, 'yyyy-mm')) -\n" + 
			"                     last_day(add_months(to_date(?, 'yyyy-mm'), -1))),\n" + 
			"                    2)) avg_p,\n" +
			"     max(a.up_cap_cnt) up_cap_cnt\n" + 
			"from a_cons_total_d a, c_cons c, o_org o, c_cons_vip vip\n" + 
			"where a.org_no = o.org_no\n" + 
			" and a.cons_no = c.cons_no\n" + 
			" and c.cons_no = vip.cons_no\n" + 
			" and a.stat_date >= to_date(?||'-01', 'yyyy-mm-dd')\n" + 
			" and a.stat_date < last_day(to_date(?,'yyyy-mm'))+1\n" + 
			" group by o.org_name, a.cons_no";
		try{
	    	   return super.pagingFind(sql, start, limit, new VipConsPowerOverOtherRowMapper(), new Object[]{month,month,month,month});
	        } catch(RuntimeException e){
	           this.logger.debug("按月统计负荷超容量出错！");
	           throw e;
	        }
	}

	@Override
	public Page<VipConsPowerOverBean> queryVipCOnsPowerOverByYear(String year,
			long start, int limit) {
		String sql = "select o.org_name,\n"
				+ "     a.cons_no,\n"
				+ "     max(c.cons_name) cons_name,\n"
				+ "     min(a.contract_cap) contract_cap,\n"
				+ "     decode(sum(a.avg_p),null,null,\n"
				+ "     round(sum(a.avg_p)/ (last_day(to_date(?||'-12-31','yyyy-mm-dd')) - last_day(add_months(to_date(?||'-12-31','yyyy-mm-dd'),-12))),2)) avg_p,\n"
				+ "     max(a.up_cap_cnt) up_cap_cnt\n"
				+ "from a_cons_total_d a, c_cons c, o_org o, c_cons_vip vip\n"
				+ "where a.org_no = o.org_no\n"
				+ " and a.cons_no = c.cons_no\n"
				+ " and c.cons_no = vip.cons_no\n"
				+ " and a.stat_date >= to_date(?||'-01-01', 'yyyy-mm-dd')\n"
				+ " and a.stat_date < to_date(?||'-12-31','yyyy-mm-dd')+1\n"
				+ " group by o.org_name, a.cons_no";
		try {
			return super.pagingFind(sql, start, limit,
					new VipConsPowerOverOtherRowMapper(), new Object[] { year,year, year, year });
		} catch (RuntimeException e) {
			this.logger.debug("按月统计负荷超容量出错！");
			throw e;
		}
	}
//-----------------------------------------------------------------统计负荷超容量结束-----------------------------------------------------------------------
//-----------------------------------------------------------------统计功率因数越限开始-----------------------------------------------------------------------
	@Override
	public Page<VipConsPowerCurveBean> queryVipConsPowerCurveByDay(String day,
			long start, int limit) {
		String sb=
			"select o.org_name,\n" +
			"       c.cons_name,\n" + 
			"       em.cons_no,\n" + 
			"       max(ec.f) max_curve,\n" + 
			"       min(ec.f) min_curve\n" + 
			"  from e_mp_curve ec, e_data_mp em, c_cons c, o_org o\n" + 
			" where ec.id = em.id\n" + 
			"   and ec.area_code = em.area_code\n" + 
			"   and em.cons_no = c.cons_no\n" + 
			"   and c.org_no = o.org_no\n" + 
			"   and ec.data_time >= to_date(?, 'yyyy-mm-dd')\n" + 
			"   and ec.data_time < to_date(?, 'yyyy-mm-dd') + 1\n" + 
			" group by o.org_name, c.cons_name, em.cons_no\n" + 
			" order by o.org_name, c.cons_name";
	       try{
	    	   return super.pagingFind(sb, start, limit, new VipConsPowerCurveDayRowMapper(), new Object[]{day,day});
	        } catch(RuntimeException e){
	           this.logger.debug("按日统计功率因数越限出错！");
	           throw e;
	        }
	}

	@Override
	public Page<VipConsPowerCurveBean> queryVipConsPowerCurveByMonth(
			String month, long start, int limit) {
		String sql = 
			"select o.org_name,\n" +
			"       c.cons_name,\n" + 
			"       em.cons_no,\n" + 
			"       max(ec.f) max_curve,\n" + 
			"       min(ec.f) min_curve\n" + 
			"  from e_mp_curve ec, e_data_mp em, c_cons c, o_org o\n" + 
			" where ec.id = em.id\n" + 
			"   and ec.area_code = em.area_code\n" + 
			"   and em.cons_no = c.cons_no\n" + 
			"   and c.org_no = o.org_no\n" + 
			"   and ec.data_time >= to_date(?||'-01', 'yyyy-mm-dd')\n" + 
			"   and ec.data_time < last_day(to_date(?, 'yyyy-mm'))+1\n" + 
			" group by o.org_name, c.cons_name, em.cons_no\n" + 
			" order by o.org_name, c.cons_name";
		try{
	    	   return super.pagingFind(sql, start, limit, new VipConsPowerCurveDayRowMapper(), new Object[]{month,month});
	        } catch(RuntimeException e){
	           this.logger.debug("按月统计功率因数越限出错！");
	           throw e;
	        }
	}

	@Override
	public Page<VipConsPowerCurveBean> queryVipConsPowerCurveByYear(String year,
			long start, int limit) {
		String sql = "select o.org_name,\n" +
		"       c.cons_name,\n" + 
		"       em.cons_no,\n" + 
		"       max(ec.f) max_curve,\n" + 
		"       min(ec.f) min_curve\n" + 
		"  from e_mp_curve ec, e_data_mp em, c_cons c, o_org o\n" + 
		" where ec.id = em.id\n" + 
		"   and ec.area_code = em.area_code\n" + 
		"   and em.cons_no = c.cons_no\n" + 
		"   and c.org_no = o.org_no\n" + 
		"   and ec.data_time >= to_date(?||'-01-01', 'yyyy-mm-dd')\n" + 
		"   and ec.data_time < to_date(?||'-12-31', 'yyyy-mm-dd')+1\n" + 
		" group by o.org_name, c.cons_name, em.cons_no\n" + 
		" order by o.org_name, c.cons_name";
		try {
			return super.pagingFind(sql, start, limit,
					new VipConsPowerCurveDayRowMapper(), new Object[] { year,year });
		} catch (RuntimeException e) {
			this.logger.debug("按月统计功率因数越限出错！");
			throw e;
		}
	}
//-----------------------------------------------------------------统计功率因数越限结束-----------------------------------------------------------------------	
//-----------------------------------------------------------------统计电压合格率开始-----------------------------------------------------------------------
	@Override
	public Page<VipConsPowerVstatBean> queryVipConsPowerVstatByDay(String day,
			long start, int limit) {
		String sb=
			"select o.org_name, c.cons_no,c.cons_name,\n" +
			"  ev.UA_UP_TIME,\n" + 
			"       ev.UA_LOW_TIME,\n" + 
			"       round((ev.UA_UP_TIME * 100) / (24 * 60), 2) ua_up_ratio,\n" + 
			"       round((ev.UA_LOW_TIME * 100) / (24 * 60), 2) ua_low_ratio,\n" + 
			"       round(100 - (ev.UA_UP_TIME * 100) / (24 * 60) -\n" + 
			"             (ev.UA_LOW_TIME * 100) / (24 * 60),2) el_ratio\n" + 
			"  from e_mp_day_vstat ev, e_data_mp em, c_cons c, o_org o\n" + 
			" where ev.id = em.id\n" + 
			"   and ev.area_code = em.area_code\n" + 
			"   and em.cons_no = c.cons_no\n" + 
			"   and c.org_no = o.org_no\n" +
			"   and ev.data_date >= to_date(?, 'yyyy-mm-dd')\n" + 
			"   and ev.data_date < to_date(?, 'yyyy-mm-dd') + 1\n" + 
			" order by o.org_name, c.cons_name";
	       try{
	    	   return super.pagingFind(sb, start, limit, new VipConsPowerVstatDayRowMapper(), new Object[]{day,day});
	        } catch(RuntimeException e){
	           this.logger.debug("按日统计电压合格率出错！");
	           throw e;
	        }
	}

	@Override
	public Page<VipConsPowerVstatBean> queryVipConsPowerVstatByMonth(
			String month, long start, int limit) {
		String sql = 
			"select o.org_name,\n" +
			"       c.cons_no,\n" + 
			"       c.cons_name,\n" + 
			"       sum(ev.UA_UP_TIME) UA_UP_TIME,\n" + 
			"       sum(ev.UA_LOW_TIME) UA_LOW_TIME,\n" + 
			"       round((sum(ev.UA_UP_TIME * 100)) /\n" + 
			"             (24 * 60 * ((last_day(to_date(?, 'yyyy-mm')) + 1) -\n" + 
			"             to_date(? || '-01', 'yyyy-mm-dd'))),\n" + 
			"             2) ua_up_ratio,\n" + 
			"       round((sum(ev.UA_LOW_TIME * 100)) /\n" + 
			"             (24 * 60 * ((last_day(to_date(?, 'yyyy-mm')) + 1) -\n" + 
			"             to_date(? || '-01', 'yyyy-mm-dd'))),\n" + 
			"             2) ua_low_ratio,\n" + 
			"       round(100 - (sum(ev.UA_UP_TIME * 100)) /\n" + 
			"             (24 * 60 * ((last_day(to_date(?, 'yyyy-mm')) + 1) -\n" + 
			"             to_date(? || '-01', 'yyyy-mm-dd'))) -\n" + 
			"             (sum(ev.UA_LOW_TIME * 100)) / (24 * 60*\n" + 
			"             ((last_day(to_date(?, 'yyyy-mm')) + 1) -\n" + 
			"             to_date(? || '-01', 'yyyy-mm-dd'))),\n" + 
			"             2) el_ratio\n" + 
			"  from e_mp_day_vstat ev, e_data_mp em, c_cons c, o_org o\n" + 
			" where ev.id = em.id\n" + 
			"   and ev.area_code = em.area_code\n" + 
			"   and em.cons_no = c.cons_no\n" + 
			"   and c.org_no = o.org_no\n" + 
			"   and ev.data_date >= to_date(? || '-01', 'yyyy-mm-dd')\n" + 
			"   and ev.data_date < last_day(to_date(?, 'yyyy-mm')) + 1\n" + 
			" group by o.org_name, c.cons_name, c.cons_no\n" + 
			" order by o.org_name, c.cons_name";
		try{
	    	   return super.pagingFind(sql, start, limit, new VipConsPowerVstatDayRowMapper(), new Object[]{month,month,month,month,month,month,month,month,month,month});
	        } catch(RuntimeException e){
	           this.logger.debug("按月统计电压合格率出错！");
	           throw e;
	        }
	}

	@Override
	public Page<VipConsPowerVstatBean> queryVipConsPowerVstatByYear(String year,
			long start, int limit) {
		String sql = 
			"select o.org_name,\n" +
			"       c.cons_no,\n" + 
			"       c.cons_name,\n" + 
			"       sum(ev.UA_UP_TIME) UA_UP_TIME,\n" + 
			"       sum(ev.UA_LOW_TIME) UA_LOW_TIME,\n" + 
			"       round((sum(ev.UA_UP_TIME * 100)) /\n" + 
			"             (24 * 60 * ((to_date(?||'-12-31', 'yyyy-mm-dd')+1) - to_date(?||'-01-01', 'yyyy-mm-dd'))),\n" + 
			"             2) ua_up_ratio,\n" + 
			"       round((sum(ev.UA_LOW_TIME * 100)) /\n" + 
			"             (24 * 60 * ((to_date(?||'-12-31', 'yyyy-mm-dd')+1) - to_date(?||'-01-01', 'yyyy-mm-dd'))),\n" + 
			"             2) ua_low_ratio,\n" + 
			"       round(100 - (sum(ev.UA_UP_TIME * 100)) /\n" + 
			"             (24 * 60 * ((to_date(?||'-12-31', 'yyyy-mm-dd')+1) - to_date(?||'-01-01', 'yyyy-mm-dd'))) -\n" + 
			"             (sum(ev.UA_LOW_TIME * 100)) / (24 * 60 *\n" + 
			"             ((to_date(?||'-12-31', 'yyyy-mm-dd')+1) - to_date(?||'-01-01', 'yyyy-mm-dd'))),2) el_ratio\n" + 
			"  from e_mp_day_vstat ev, e_data_mp em, c_cons c, o_org o\n" + 
			" where ev.id = em.id\n" + 
			"   and ev.area_code = em.area_code\n" + 
			"   and em.cons_no = c.cons_no\n" + 
			"   and c.org_no = o.org_no\n" + 
			"   and ev.data_date >= to_date(?||'-01-01', 'yyyy-mm-dd')\n" + 
			"\t and ev.data_date < to_date(?||'-12-31', 'yyyy-mm-dd')+1\n" + 
			" group by o.org_name, c.cons_name, c.cons_no\n" + 
			" order by o.org_name, c.cons_name";
		try {
			return super.pagingFind(sql, start, limit,
					new VipConsPowerVstatDayRowMapper(), new Object[] { year,year, year, year, year, year, year, year, year, year });
		} catch (RuntimeException e) {
			this.logger.debug("按月统计电压合格率出错！");
			throw e;
		}
	}
//-----------------------------------------------------------------统计电压合格率结束-----------------------------------------------------------------------		
}


class VipConsReqRatioRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
		VipConsMonitorDto vipConsMonitorDto  = new VipConsMonitorDto();
		try {
			vipConsMonitorDto.setOrgName(rs.getString("ORG_NAME"));
			vipConsMonitorDto.setConsNo(rs.getString("CONS_NO"));
			vipConsMonitorDto.setConsName(rs.getString("CONS_NAME"));
			if(null!=rs.getString("MAX_P")&&!"".equals(rs.getString("MAX_P")))
				vipConsMonitorDto.setMaxP(rs.getFloat("MAX_P"));
			if(null!=rs.getString("CONTRACT_CAP")&&!"".equals(rs.getString("CONTRACT_CAP")))
				vipConsMonitorDto.setContractCap(rs.getFloat("CONTRACT_CAP"));
			if(null!=rs.getString("REQ_RATIO")&&!"".equals(rs.getString("REQ_RATIO")))
				vipConsMonitorDto.setReqRatio(rs.getFloat("REQ_RATIO"));
			if(null!=rs.getString("STAT_DATE")&&!"".equals(rs.getString("STAT_DATE"))){
			    SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
				vipConsMonitorDto.setStatDate(sdf.format(rs.getDate("STAT_DATE")));
			}
			return vipConsMonitorDto;
		} catch (Exception e) {
			return null;
		}
	}
}

class VipConsPowerOverDayRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
		VipConsPowerOverBean bean  = new VipConsPowerOverBean();
		try {
			bean.setOrgName(rs.getString("ORG_NAME"));
			bean.setConsNo(rs.getString("CONS_NO"));
			bean.setConsName(rs.getString("CONS_NAME"));
			if(null!=rs.getString("AVG_P")&&!"".equals(rs.getString("AVG_P")))
				bean.setAvgP(rs.getFloat("AVG_P"));
			if(null!=rs.getString("CONTRACT_CAP")&&!"".equals(rs.getString("CONTRACT_CAP")))
				bean.setContractCap(rs.getFloat("CONTRACT_CAP"));
			bean.setAvgPOver(rs.getString("AVG_P_OVER"));
			if(null!=rs.getString("UP_CAP_CNT")&&!"".equals(rs.getString("UP_CAP_CNT")))
				bean.setUpCapCnt(rs.getShort("UP_CAP_CNT"));
			return bean;
		} catch (Exception e) {
			return null;
		}
	}
}

class VipConsPowerOverOtherRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
		VipConsPowerOverBean bean  = new VipConsPowerOverBean();
		try {
			bean.setOrgName(rs.getString("ORG_NAME"));
			bean.setConsNo(rs.getString("CONS_NO"));
			bean.setConsName(rs.getString("CONS_NAME"));
			if(null!=rs.getString("AVG_P")&&!"".equals(rs.getString("AVG_P")))
				bean.setAvgP(rs.getFloat("AVG_P"));
			if(null!=rs.getString("CONTRACT_CAP")&&!"".equals(rs.getString("CONTRACT_CAP")))
				bean.setContractCap(rs.getFloat("CONTRACT_CAP"));
			if(bean.getAvgP() !=null &&bean.getAvgP()> bean.getContractCap()) {
				bean.setAvgPOver("是");
			} else {
				bean.setAvgPOver("否");
			}
			if(null!=rs.getString("UP_CAP_CNT")&&!"".equals(rs.getString("UP_CAP_CNT")))
				bean.setUpCapCnt(rs.getShort("UP_CAP_CNT"));
			return bean;
		} catch (Exception e) {
			return null;
		}
	}
}

class VipConsPowerCurveDayRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
		VipConsPowerCurveBean bean  = new VipConsPowerCurveBean();
		try {
			bean.setOrgName(rs.getString("ORG_NAME"));
			bean.setConsNo(rs.getString("CONS_NO"));
			bean.setConsName(rs.getString("CONS_NAME"));
			
			String maxCurve = rs.getString("MAX_CURVE");
			String minCurve = rs.getString("MIN_CURVE");
			if(maxCurve != null){
				bean.setMaxCurve(maxCurve.startsWith(".")?"0"+maxCurve:maxCurve);
			} else {
				bean.setMaxCurve("");
			}
			if(minCurve != null){
				bean.setMinCurve(minCurve.startsWith(".")?"0"+minCurve:minCurve);
			} else {
				bean.setMinCurve("");
			}
			return bean;
		} catch (Exception e) {
			return null;
		}
	}
}

class VipConsPowerVstatDayRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
		VipConsPowerVstatBean bean  = new VipConsPowerVstatBean();
		try {
			bean.setOrgName(rs.getString("ORG_NAME"));
			bean.setConsNo(rs.getString("CONS_NO"));
			bean.setConsName(rs.getString("CONS_NAME"));
			bean.setUaUpTime(rs.getString("UA_UP_TIME"));
			bean.setUaLowTime(rs.getString("UA_LOW_TIME"));
			
			String uaUpRatio = rs.getString("UA_UP_RATIO");
			String uaLowRatio = rs.getString("UA_LOW_RATIO");
			String elRatio = rs.getString("EL_RATIO");
			
			if(uaUpRatio != null ) {
				bean.setUaUpRatio(uaUpRatio.startsWith(".") ? "0" + uaUpRatio : uaUpRatio);
			} else {
				bean.setUaUpRatio("");
			}
			if(uaLowRatio != null ) {	
				bean.setUaLowRatio(uaLowRatio.startsWith(".") ? "0" + uaLowRatio : uaLowRatio);
			} else {
				bean.setUaLowRatio("");
			}
			if(elRatio != null ) {
				bean.setElRatio(elRatio.startsWith(".") ? "0" + elRatio : elRatio);
			} else {
				bean.setElRatio("");
			}
			return bean;
		} catch (Exception e) {
			return null;
		}
	}
}