package com.nari.advapp.statanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.advapp.statanalyse.EnergyStat;
import com.nari.advapp.statanalyse.LineStatAnalysisDao;
import com.nari.advapp.statanalyse.LoadStat;
import com.nari.advapp.statanalyse.VoltDegree;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;
import com.nari.util.DateUtil;
import com.nari.util.NodeTypeUtils;

import common.Logger;

public class LineStatAnalysisDaoImpl extends JdbcBaseDAOImpl implements LineStatAnalysisDao {
	Logger log=Logger.getLogger(LineStatAnalysisDaoImpl.class);
	/**
	 * 根据供电单位查询
	 * @param orgNo
	 * @return voltList
	 */
	@SuppressWarnings("unchecked")
	public List<VoltDegree> queryVoltByNodeNo(String type,String nodeNo){
		String sql=null;
		List<VoltDegree> voltList = null;
		if(type.equals(NodeTypeUtils.NODETYPE_ORG))
		{
			nodeNo+="%";
			StringBuilder sb = new StringBuilder();
			sb.append("select distinct vvc.VOLT_CODE,vvc.VOLT from vw_volt_code vvc,c_cons c,o_org o\n");
			sb.append("where vvc.VOLT_CODE=c.volt_code and o.org_no=c.org_no and o.org_no like ?\n");
			sql=sb.toString();
			log.debug(sql);
			voltList=super.getJdbcTemplate().query(sql, new Object[]{nodeNo},new VoltMapper());
		}else if(type.equals(NodeTypeUtils.NODETYPE_LINE))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("select distinct vvc.VOLT_CODE,vvc.VOLT from vw_volt_code vvc,c_cons c,g_line g\n");
			sb.append("where c.cons_id=g.cons_id and vvc.VOLT_CODE=c.volt_code and g.line_id=? \n");
			sql=sb.toString();
			voltList=super.getJdbcTemplate().query(sql, new Object[]{nodeNo},new VoltMapper());		
			log.debug(sql);
		}else if(type.equals(NodeTypeUtils.NODETYPE_SUB))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("select vvc.VOLT_CODE,vvc.VOLT from vw_volt_code vvc,c_cons c,g_line g,\n");
			sb.append("g_subs gs,g_subs_line_rela gl where\n");
			sb.append("c.cons_id=g.cons_id and vvc.VOLT_CODE=c.volt_code and\n");
			sb.append("g.line_id=gl.line_id and gs.subs_id=gl.subs_id and gs.subs_id=?\n");
			sql=sb.toString();
			voltList=super.getJdbcTemplate().query(sql, new Object[]{nodeNo},new VoltMapper());	
			log.debug(sql);
		}
		if(voltList.equals(null))
		{
			voltList=new ArrayList<VoltDegree>();
		}
		return voltList;
	}
	/**
	 * 查询电量统计列表
	 * @param type,nodeNo,volt,startDate,endDate
	 * @return esList
	 */
	@SuppressWarnings("unchecked")
	public Page<EnergyStat> queryEnergyStat(String type, String nodeNo,
			String voltCode, Date startDate, Date endDate, int start, int limit){
		Page<EnergyStat> esList=null;//new ArrayList<EnergyStat>();
		String sql = null;
		if((NodeTypeUtils.NODETYPE_ORG).equals(type))
		{
			StringBuilder sb=new StringBuilder();
			nodeNo+="%";
			sb.append("select distinct o.org_no as org_no, o.org_name as org_name,g.line_id as line_id,g.line_name as line_name,vvc.VOLT as volt,als.stat_date as stat_date,als.pap_e,als.pap_e1,\n");
			sb.append("als.pap_e2,als.pap_e3,als.pap_e4 from  (select * from a_line_stat_d where stat_date>=? and stat_date<=?) als,\n");
			sb.append("(select * from o_org where org_no like ? ) o,c_cons c,\n");
			sb.append("(select * from vw_volt_code where volt_code=?) vvc,g_line g where\n");
			sb.append("vvc.volt_code=c.volt_code and o.org_no=als.org_no and g.line_id=als.line_id and c.line_id=als.line_id order by als.stat_date\n");		
			sql=sb.toString();
			log.debug(sql);
 			esList=super.pagingFind(sql, start, limit,new EnergyStatMapper(), new Object[]{startDate,endDate,nodeNo,voltCode});
// 				super.getJdbcTemplate().query(sql, new Object[]{startDate,endDate,nodeNo,voltCode},new EnergyStatMapper());
		}else if((NodeTypeUtils.NODETYPE_LINE).equals(type)){
			StringBuilder sb=new StringBuilder();
			sb.append("select distinct o.org_no,o.org_name,g.line_id,g.line_name,vvc.volt,als.stat_date,als.pap_e,als.pap_e1,als.pap_e2,\n");
			sb.append("als.pap_e3,als.pap_e4 from (select * from g_line g where g.line_id=?)  g,\n");
			sb.append("(select * from a_line_stat_d where stat_date>=?\n");
			sb.append("and stat_date<=?)  als,c_cons c,o_org o,\n");
			sb.append("(select * from vw_volt_code where volt_code=?)  vvc where\n");
			sb.append("vvc.volt_code=c.volt_code and o.org_no=als.org_no and  c.line_id=als.line_id\n");
			sb.append("and g.line_id=als.line_id order by als.stat_date");
			log.debug(sql);
			sql=sb.toString();
			esList=super.pagingFind(sql, start, limit,new EnergyStatMapper(), new Object[]{nodeNo,startDate,endDate,voltCode});
//			esList=super.getJdbcTemplate().query(sql, new Object[]{nodeNo,startDate,endDate,voltCode},new EnergyStatMapper());
		}else if((NodeTypeUtils.NODETYPE_SUB).equals(type)){
			StringBuilder sb=new StringBuilder();
			sb.append("select  distinct o.org_no, o.org_name,g.line_id,g.line_name, vvc.volt,als.pap_e,als.pap_e1,pap_e2,als.pap_e3,pap_e4 from\n");
			sb.append("g_subs_line_rela gsl,g_line g,(select * from g_subs where subs_id=?) gs,\n");
			sb.append("(select * from a_line_stat_d where stat_date>=? and\n");
			sb.append("stat_date<=?)  als,c_cons c,o_org o,(select * from vw_volt_code \n");
			sb.append(" where volt_code=?)vvc where vvc.volt_code=c.volt_code and o.org_no=als.org_no and  c.line_id=als.line_id\n");
			sb.append("  and g.line_id=als.line_id  and  gsl.subs_id=gs.subs_id and gsl.line_id=als.line_id");
			sql=sb.toString();
			log.debug(sql);
			esList=super.pagingFind(sql, start, limit,new EnergyStatMapper(), new Object[]{nodeNo,startDate,endDate,voltCode});
//			esList=super.getJdbcTemplate().query(sql, new Object[]{nodeNo,startDate,endDate,voltCode},new EnergyStatMapper());			
		}
		if(esList.equals(null))
		{
			esList=new Page<EnergyStat>();
		}
		return esList;
	}
	/**
	 * 查询电量统计列表
	 * @param orgNo,lineId,statDate
	 * @return esList
	 */
	@SuppressWarnings("unchecked")
	public List<EnergyStat> queryLineEnergy(String orgNo, String voltCode,String lineId,
			Date statDate)
	{
		List<EnergyStat> esList=null;		
		String sql;
		StringBuilder sb=new StringBuilder();		
		sb.append(" select distinct o.org_no, o.org_name as org_name,g.line_id,g.line_name as line_name,\n");
		sb.append(" vvc.VOLT as volt,als.stat_date as stat_date,als.pap_e,als.pap_e1,als.pap_e2,als.pap_e3,\n");
		sb.append(" als.pap_e4 from (select * from o_org where org_no=?) o,(select * from vw_volt_code\n");
		sb.append("where volt_code=?) vvc,(select * from a_line_stat_d where to_char(stat_date,'YYYY-MM')=\n");
		sb.append("to_char(?,'YYYY-MM')) als,(select * from g_line where line_id=?) g,\n");
		sb.append("c_cons c where vvc.volt_code=c.volt_code and o.org_no=als.org_no and g.line_id=als.line_id and\n");
		sb.append(" c.line_id=als.line_id order by als.stat_date desc");
		sql=sb.toString();		
		esList=super.getJdbcTemplate().query(sql, new Object[]{orgNo,voltCode,statDate,lineId}, new EnergyStatMapper());
		if(null==esList)
		{
			esList=new ArrayList<EnergyStat>();
		}
		return esList;
		
	}
	/**
	 * 查询负荷统计
	 * @param type,nodeNo,volt,startDate,endDate
	 * @return esList
	 */
	@SuppressWarnings("unchecked")
	public Page<LoadStat> queryLoadStat(String type, String nodeNo,
			String voltCode, Date startDate, Date endDate, int start, int limit)
	{
		Page<LoadStat> lsList=null;//new ArrayList<EnergyStat>();
		String sql = null;
		if((NodeTypeUtils.NODETYPE_ORG).equals(type))
		{
			StringBuilder sb=new StringBuilder();
			nodeNo+="%";
			sb.append("select distinct o.org_name,g.line_name,vvc.VOLT,als.stat_date,als.max_p,als.max_p_time,\n");
			sb.append("als.min_p,als.min_p_time,als.pap_e2,als.pap_e4,als.avg_p  from  (select * from a_line_stat_d where\n");
			sb.append("stat_date>=? and stat_date<=?) als,\n");
			sb.append("(select * from o_org where org_no like ? ) o,c_cons c,\n");
			sb.append("(select * from vw_volt_code where volt_code=?) vvc,g_line g where\n");
			sb.append("vvc.volt_code=c.volt_code and o.org_no=als.org_no and g.line_id=als.line_id\n");		
			sb.append("and c.line_id=als.line_id");
			sql=sb.toString();
			log.debug(sql);
			lsList=super.pagingFind(sql, start, limit, new LoadMapper(),new Object[]{startDate,endDate,nodeNo,voltCode});
		}else if((NodeTypeUtils.NODETYPE_LINE).equals(type)){
			StringBuilder sb=new StringBuilder();
			sb.append("select distinct o.org_name,g.line_name,vvc.VOLT,als.stat_date,als.max_p,als.max_p_time,\n");
			sb.append("als.min_p,als.min_p_time,als.pap_e2,als.pap_e4,als.avg_p from\n");
			sb.append("(select * from g_line g where g.line_id=?)  g,\n");
			sb.append("(select * from a_line_stat_d where stat_date>=?\n");
			sb.append("and stat_date<=?)  als,c_cons c,o_org o,\n");
			sb.append("(select * from vw_volt_code where volt_code=?')  vvc where\n");
			sb.append("vvc.volt_code=c.volt_code and o.org_no=als.org_no and  c.line_id=als.line_id\n");
			sb.append("and g.line_id=als.line_id");
			log.debug(sql);
			sql=sb.toString();
			lsList=super.pagingFind(sql, start, limit, new LoadMapper(),new Object[]{nodeNo,startDate,endDate,voltCode});
		}else if((NodeTypeUtils.NODETYPE_SUB).equals(type)){
			StringBuilder sb=new StringBuilder();
			sb.append("select o.org_name,g.line_name,vvc.VOLT,als.stat_date,als.max_p,als.max_p_time,\n");
			sb.append("als.min_p,als.min_p_time,als.pap_e2,als.pap_e4,als.avg_p from\n");
			sb.append("g_subs_line_rela gsl,g_line g,(select * from g_subs where subs_id=?) gs,\n");
			sb.append("(select * from a_line_stat_d where stat_date>=? and\n");
			sb.append("stat_date<=?)  als,c_cons c,o_org o,\n");
			sb.append("(select * from vw_volt_code where volt_code=?)vvc where\n");
			sb.append("vvc.volt_code=c.volt_code  and o.org_no=als.org_no and  c.line_id=als.line_id\n");
			sb.append("and g.line_id=als.line_id  and  gsl.subs_id=gs.subs_id and gsl.line_id=als.line_id");
			sql=sb.toString();
			log.debug(sql);
			lsList=super.pagingFind(sql, start, limit, new LoadMapper(),new Object[]{nodeNo,startDate,endDate,voltCode});
//			lsList=super.getJdbcTemplate().query(sql, new Object[]{nodeNo,startDate,endDate,voltCode},new LoadMapper());			
		}
		if(lsList.equals(null))
		{
			lsList=new Page<LoadStat>();
		}
		return lsList;
	}
	/**
	 * 电压等级映射类
	 * @author chengzh
	 *
	 */
	class VoltMapper implements RowMapper
	{
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			VoltDegree vd=new VoltDegree();
			vd.setVolt(rs.getString("VOLT"));
			vd.setVoltCode(rs.getString("VOLT_CODE"));
			return vd;
		}		
	}
	/**
	 * 查询结果映射类
	 * @author chengzh
	 */
	class EnergyStatMapper implements RowMapper
	{
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			EnergyStat es=new EnergyStat();
			try{	
			es.setOrgNo(rs.getString("ORG_NO"));
			es.setOrgName(rs.getString("ORG_NAME"));
			es.setLineId(rs.getString("LINE_ID"));
			es.setLineName(rs.getString("LINE_NAME"));
			es.setVolt(rs.getString("VOLT"));
			es.setStatDate(DateUtil.dateToString(rs.getDate("STAT_DATE")));
			es.setPape(rs.getFloat("PAP_E"));	
			es.setPape1(rs.getFloat("PAP_E1"));
			es.setPape2(rs.getFloat("PAP_E2"));
			es.setPape3(rs.getFloat("PAP_E3"));
			es.setPape4(rs.getFloat("PAP_E4"));
			return es;
			}catch(Exception e)
			{
				log.debug("实体映射时出错！");
				return null;
				
			}
			
		}		
	}
	class LoadMapper implements RowMapper
	{

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			LoadStat ls=new LoadStat();
			ls.setOrgName(rs.getString("ORG_NAME"));
			ls.setLineName(rs.getString("LINE_NAME"));
			ls.setVolt(rs.getString("VOLT"));
			ls.setStatDate(DateUtil.dateToString(rs.getDate("STAT_DATE")));
			ls.setMaxP(rs.getFloat("MAX_P"));
			ls.setMaxTime(DateUtil.timeStampToStr(rs.getTimestamp("MAX_P_TIME")));
			ls.setMinP(rs.getFloat("MIN_P"));
			ls.setMinTime(DateUtil.timeStampToStr(rs.getTimestamp("MIN_P_TIME")));
			ls.setAvgP(rs.getFloat("AVG_P"));
			ls.setPape2(rs.getFloat("PAP_E2"));
			ls.setPape4(rs.getFloat("PAP_E4"));
			ls.computeLoadRatio();
			ls.computePvRatio();
			return ls;
		}
		
	}

}
