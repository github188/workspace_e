package com.nari.qrystat.colldataanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.PowerSortAnalyseDto;
import com.nari.qrystat.colldataanalyse.PowerSortAnalyseJdbcDao;
import com.nari.support.Page;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.NodeTypeUtils;

/**
 * 用电客户排名分析Dao实现类
 * @author 姜炜超
 */
public class PowerSortAnalyseJdbcDaoImpl extends JdbcBaseDAOImpl implements
		PowerSortAnalyseJdbcDao {
	//定义日志
	private static final Logger logger = Logger.getLogger(PowerSortAnalyseJdbcDaoImpl.class);
	
	/**
	 * 根据条件查询用电客户排名分析信息，权限表中，02.03用户权限到04，04用户到05或06，05或06就是自己。
	 * ps: a_cons_stat_d中的orgno到区县。
	 * @param nodeId
	 * @param nodeType
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @param compStartDate
	 * @param compEndDate
	 * @param sort
	 * @param start
	 * @param limit
	 * @return Page<PowerSortAnalyseDto>
	 */
	public Page<PowerSortAnalyseDto> findPSAInfoByCond(String nodeId, String nodeType,
			String orgType, String startDate, String endDate, String compStartDate, 
			String compEndDate, int sort,long start, int limit, PSysUser user){
		
		Page<PowerSortAnalyseDto> psc = null;
		List<Object> obj = new ArrayList<Object>();
		String sql1 = 
			"select cons.cons_no,\n" +
			"       cons.cons_name,\n" + 
			"       cons.run_cap,\n" + 
			"       trade.trade_name,\n" + 
			"       volt.volt,\n" + 
			"       newstat.pap_e as bqyd,\n" + 
			"       oldstat.pap_e as tqyd,\n" + 
			"       newstat.rw\n" + 
			"  from c_cons cons,\n" ;
		
		String sql2 = 
			"       (select pap_e, cons_no, rownum rw\n" + 
			"          from (select stat.cons_no, sum(stat.pap_e) as pap_e\n";
		
		String sql3 = " group by stat.cons_no\n" + 
		    " order by sum(stat.pap_e) desc nulls last)\n";
		
		String sql4 = 
			"       (select pap_e, cons_no, rownum rw\n" + 
			"          from (select stat.cons_no, sum(stat.pap_e) as pap_e\n";
		
		String sql5 = " group by stat.cons_no\n" + 
		    " order by sum(stat.pap_e) desc nulls last)\n";
		
		String sql6 = 
			"vw_trade trade,\n" +
			"       vw_volt_code volt\n" + 
			" where cons.cons_no = newstat.cons_no\n" + 
			"   and newstat.cons_no = oldstat.cons_no(+)\n" + 
			"   and cons.trade_code = trade.trade_no(+)\n" + 
			"   and cons.volt_code = volt.volt_code(+)\n" +
			 "   order by bqyd desc ";

		StringBuffer sql = new StringBuffer();
		
		sql.append(sql1).append(sql2);
		
		if(NodeTypeUtils.NODETYPE_ORG.equals(nodeType)){
			if(null == orgType || "".equals(orgType)){
				return new Page<PowerSortAnalyseDto>();
			}
			if(NodeTypeUtils.NODETYPE_ORG02.equals(orgType)){
				sql.append(" from a_cons_stat_d stat, (select org_no, org_type from p_access_org where staff_no = ? and org_type=?) acc \n")
				   .append(" where stat.org_no = acc.org_no \n")
				   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd') \n")
				   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
				sql.append(sql3);
				sql.append("  where rownum <= ?) newstat, \n");
				sql.append(sql4);
				sql.append(" from a_cons_stat_d stat, (select org_no, org_type from p_access_org where staff_no = ? and org_type=?) acc  \n")
				   .append("  where stat.org_no = acc.org_no \n")
				   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd')  \n")
				   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
				sql.append(sql5);
				sql.append("   where rownum <= ?) oldstat, \n");
				sql.append(sql6);
				
				obj.add(user.getStaffNo());
				obj.add(NodeTypeUtils.NODETYPE_ORG04);
				obj.add(startDate);
				obj.add(endDate);
				obj.add(sort);
				obj.add(user.getStaffNo());
				obj.add(NodeTypeUtils.NODETYPE_ORG04);
				obj.add(compStartDate);
				obj.add(compEndDate);
				obj.add(sort);
				try{
					psc = this.pagingFind(sql.toString(), start, limit, new PowerSortAnalyseRowMapper(), obj.toArray());
				}catch(RuntimeException e){
					logger.debug("根据条件查询用电客户排名分析信息出错！");
					throw e;
				}
				
			}else if(NodeTypeUtils.NODETYPE_ORG03.equals(orgType)){
				nodeId = nodeId+"%";
				
				sql.append(" from a_cons_stat_d stat, (select org_no, org_type from p_access_org where staff_no = ? and org_type=? and org_no like ?) acc \n")
				   .append(" where stat.org_no = acc.org_no \n")
				   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd') \n")
				   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
				sql.append(sql3);
				sql.append("  where rownum <= ?) newstat, \n");
				sql.append(sql4);
				sql.append(" from a_cons_stat_d stat,(select org_no, org_type from p_access_org where staff_no = ? and org_type=? and org_no like ?) acc  \n")
				   .append(" where stat.org_no = acc.org_no \n")
				   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd')  \n")
				   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
				sql.append(sql5);
				sql.append("   where rownum <= ?) oldstat, \n");
				sql.append(sql6);
				
				obj.add(user.getStaffNo());
				obj.add(NodeTypeUtils.NODETYPE_ORG04);
				obj.add(nodeId);
				obj.add(startDate);
				obj.add(endDate);
				obj.add(sort);
				obj.add(user.getStaffNo());
				obj.add(NodeTypeUtils.NODETYPE_ORG04);
				obj.add(nodeId);
				obj.add(compStartDate);
				obj.add(compEndDate);
				obj.add(sort);
				
				try{
					psc = this.pagingFind(sql.toString(), start, limit, new PowerSortAnalyseRowMapper(), obj.toArray());
				}catch(RuntimeException e){
					logger.debug("根据条件查询用电客户排名分析信息出错！");
					throw e;
				}
				
			}else if(NodeTypeUtils.NODETYPE_ORG04.equals(orgType)){
				sql.append(" from a_cons_stat_d stat,(select org_no, org_type from p_access_org where staff_no = ?  and org_type = ? and org_no = ?) acc  \n")
				   .append(" where stat.org_no = acc.org_no \n")
				   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd') \n")
				   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
				sql.append(sql3);
				sql.append("  where rownum <= ?) newstat, \n");
				sql.append(sql4);
				sql.append(" from a_cons_stat_d stat,(select org_no, org_type from p_access_org where staff_no = ?  and org_type = ? and org_no = ?) acc  \n")
				   .append(" where stat.org_no = acc.org_no \n")
				   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd')  \n")
				   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
				sql.append(sql5);
				sql.append("   where rownum <= ?) oldstat, \n");
				sql.append(sql6);
				
				obj.add(user.getStaffNo());
				obj.add(NodeTypeUtils.NODETYPE_ORG04);
				obj.add(nodeId);
				obj.add(startDate);
				obj.add(endDate);
				obj.add(sort);
				obj.add(user.getStaffNo());
				obj.add(NodeTypeUtils.NODETYPE_ORG04);
				obj.add(nodeId);
				obj.add(compStartDate);
				obj.add(compEndDate);
				obj.add(sort);
				
				try{
					psc = this.pagingFind(sql.toString(), start, limit, new PowerSortAnalyseRowMapper(), obj.toArray());
				}catch(RuntimeException e){
					logger.debug("根据条件查询用电客户排名分析信息出错！");
					throw e;
				}
			}else if(NodeTypeUtils.NODETYPE_ORG05.equals(orgType) || NodeTypeUtils.NODETYPE_ORG06.equals(orgType)){
				sql.append(" from a_cons_stat_d stat, c_cons cons \n")
				   .append(" where stat.cons_no = cons.cons_no \n")
				   .append(" and cons.org_no = ? \n")
				   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd') \n")
				   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
				sql.append(SysPrivilige.addPri(user,"cons","",3));
				sql.append(sql3);
				sql.append("  where rownum <= ?) newstat, \n");
				sql.append(sql4);
				sql.append(" from a_cons_stat_d stat, c_cons cons \n")
				   .append(" where stat.cons_no = cons.cons_no \n")
				   .append(" and cons.org_no = ? \n")
				   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd')  \n")
				   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
				sql.append(SysPrivilige.addPri(user,"cons","",3));
				sql.append(sql5);
				sql.append("   where rownum <= ?) oldstat, \n");
				sql.append(sql6);
				
				try{
					psc = this.pagingFind(sql.toString(), start, limit, new PowerSortAnalyseRowMapper(), transData3(nodeId, startDate, endDate, compStartDate, compEndDate,sort, user));
				}catch(RuntimeException e){
					logger.debug("根据条件查询用电客户排名分析信息出错！");
					throw e;
				}
			}else{
				return new Page<PowerSortAnalyseDto>();
			}
			
		}else if(NodeTypeUtils.NODETYPE_TRADE.equals(nodeType)){
			sql.append(" from a_cons_stat_d stat, c_cons cons \n")
			   .append(" where stat.cons_no = cons.cons_no \n")
			   .append(" and cons.trade_code = ? \n")
			   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd') \n")
			   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
			sql.append(SysPrivilige.addPri(user,"cons","",3));
			sql.append(sql3);
			sql.append("  where rownum <= ?) newstat, \n");
			sql.append(sql4);
			sql.append(" from a_cons_stat_d stat, c_cons cons \n")
			   .append(" where stat.cons_no = cons.cons_no \n")
			   .append(" and cons.trade_code = ? \n")
			   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd')  \n")
			   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
			sql.append(SysPrivilige.addPri(user,"cons","",3));
			sql.append(sql5);
			sql.append("   where rownum <= ?) oldstat, \n");
			sql.append(sql6);
			
			try{
				psc = this.pagingFind(sql.toString(), start, limit, new PowerSortAnalyseRowMapper(), transData3(nodeId, startDate, endDate, compStartDate, compEndDate, sort, user));
			}catch(RuntimeException e){
				logger.debug("根据条件查询用电客户排名分析信息出错！");
				throw e;
			}
		}else if(NodeTypeUtils.NODETYPE_SUB.equals(nodeType)){
			sql.append(" from a_cons_stat_d stat, c_cons cons \n")
			   .append(" where stat.cons_no = cons.cons_no \n")
			   .append(" and cons.subs_id = ? \n")
			   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd') \n")
			   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1) \n");
			sql.append(SysPrivilige.addPri(user,"cons","",3));
			sql.append(sql3);
			sql.append("  where rownum <= ?) newstat, \n");
			sql.append(sql4);
			sql.append(" from a_cons_stat_d stat, c_cons cons \n")
			   .append(" where stat.cons_no = cons.cons_no \n")
			   .append(" and cons.subs_id = ? \n")
			   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd')  \n")
			   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
			sql.append(SysPrivilige.addPri(user,"cons","",3));
			sql.append(sql5);
			sql.append("   where rownum <= ?) oldstat, \n");
			sql.append(sql6);
			
			try{
				psc = this.pagingFind(sql.toString(), start, limit, new PowerSortAnalyseRowMapper(), transData3(nodeId, startDate, endDate, compStartDate, compEndDate, sort, user));
			}catch(RuntimeException e){
				logger.debug("根据条件查询用电客户排名分析信息出错！");
				throw e;
			}
		}else if(NodeTypeUtils.NODETYPE_LINE.equals(nodeType)){
			sql.append(" from a_cons_stat_d stat, c_cons cons \n")
			   .append(" where stat.cons_no = cons.cons_no \n")
			   .append(" and cons.line_id = ? \n")
			   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd') \n")
			   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
			sql.append(SysPrivilige.addPri(user,"cons","",3));
			sql.append(sql3);
			sql.append("  where rownum <= ?) newstat, \n");
			sql.append(sql4);
			sql.append(" from a_cons_stat_d stat, c_cons cons \n")
			   .append(" where stat.cons_no = cons.cons_no \n")
			   .append(" and cons.line_id = ? \n")
			   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd')  \n")
			   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
			sql.append(SysPrivilige.addPri(user,"cons","",3));
			sql.append(sql5);
			sql.append("   where rownum <= ?) oldstat, \n");
			sql.append(sql6);
			
			try{
				psc = this.pagingFind(sql.toString(), start, limit, new PowerSortAnalyseRowMapper(), transData3(nodeId, startDate, endDate, compStartDate, compEndDate, sort, user));
			}catch(RuntimeException e){
				logger.debug("根据条件查询用电客户排名分析信息出错！");
				throw e;
			}
		}else if(NodeTypeUtils.NODETYPE_TG.equals(nodeType)){
			sql.append(" from a_cons_stat_d stat, c_cons cons \n")
			   .append(" where stat.cons_no = cons.cons_no \n")
			   .append(" and cons.tg_id = ? \n")
			   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd') \n")
			   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
			sql.append(SysPrivilige.addPri(user,"cons","",3));
			sql.append(sql3);
			sql.append("  where rownum <= ?) newstat, \n");
			sql.append(sql4);
			sql.append(" from a_cons_stat_d stat, c_cons cons \n")
			   .append(" where stat.cons_no = cons.cons_no \n")
			   .append(" and cons.tg_id = ? \n")
			   .append(" and stat.stat_date >= to_date(?,'yyyy-MM-dd')  \n")
			   .append(" and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)  \n");
			sql.append(SysPrivilige.addPri(user,"cons","",3));
			sql.append(sql5);
			sql.append("   where rownum <= ?) oldstat, \n");
			sql.append(sql6);
			
			try{
				psc = this.pagingFind(sql.toString(), start, limit, new PowerSortAnalyseRowMapper(), transData3(nodeId, startDate, endDate, compStartDate, compEndDate, sort, user));
			}catch(RuntimeException e){
				logger.debug("根据条件查询用电客户排名分析信息出错！");
				throw e;
			}
		}
		else{
			return new Page<PowerSortAnalyseDto>();
		}
		if(null == psc){
			psc = new Page<PowerSortAnalyseDto>();
		}
		return psc;
	}
	
	/**
	 * 参数转换，用于权限限制操作
	 * @param staffNo
	 * @param queryDate
	 * @param compareDate
	 * @return Object[]
	 */
	private Object[] transData3(String strId, String queryDate1, String queryDate2, String compStartDate, String compEndDate, int sort, PSysUser user){
		Object[] results = new Object[12];		
		results[0] = strId;
		results[1] = queryDate1;
		results[2] = queryDate2;
		results[3] = user.getStaffNo();
		results[4] = user.getStaffNo();
		results[5] = sort;
		results[6] = strId;
		results[7] = compStartDate;
		results[8] = compEndDate;
		results[9] = user.getStaffNo();
		results[10] = user.getStaffNo();
		results[11] = sort;
		return results;
	}
	    
	/**
	 * 根据条件查询某用电单位某时期所有用户的总耗电量
	 * @param nodeId
	 * @param nodeType
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @param sort
	 * @return Double
	 */
	public Double queryPSATotalPower(String nodeId, String nodeType,
			String orgType, String startDate, String endDate, PSysUser user){
		String sql = "";
		Long pap = 0L;
		if(NodeTypeUtils.NODETYPE_ORG.equals(nodeType)){
			if(null == orgType || "".equals(orgType)){
				return 0.0;
			}
			if(NodeTypeUtils.NODETYPE_ORG02.equals(orgType)){
				sql = 
					"select sum(stat.pap_e) as pap_e\n" +
					"  from a_cons_stat_d stat, (select org_no, org_type from p_access_org where staff_no = ? and org_type=?) acc\n" + 
					" where stat.org_no = acc.org_no\n" + 
					"   and stat.stat_date >= to_date(?, 'yyyy-MM-dd')\n" + 
					"   and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)";
                logger.debug(sql);
				try{
					pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {user.getStaffNo(),NodeTypeUtils.NODETYPE_ORG04, startDate, endDate});
				}catch(RuntimeException e){
					logger.debug("根据条件查询某用电单位某时期总耗电量出错！");
					throw e;
				}
				
			}else if(NodeTypeUtils.NODETYPE_ORG03.equals(orgType)){
				nodeId = nodeId +"%";
				sql = 
					"select sum(stat.pap_e) as pap_e\n" +
					"  from a_cons_stat_d stat, (select org_no, org_type from p_access_org where staff_no = ? and org_type=? and org_no like ?) acc\n" + 
					" where stat.org_no = acc.org_no\n" + 
					"   and stat.stat_date >= to_date(?, 'yyyy-MM-dd')\n" + 
					"   and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)";
				logger.debug(sql);
				try{
					pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {user.getStaffNo(),NodeTypeUtils.NODETYPE_ORG04, nodeId, startDate, endDate});
				}catch(RuntimeException e){
					logger.debug("根据条件查询某用电单位某时期总耗电量出错！");
					throw e;
				}
			}else if(NodeTypeUtils.NODETYPE_ORG04.equals(orgType)){
				sql = 
					"select sum(stat.pap_e) as pap_e\n" +
					"  from a_cons_stat_d stat, (select org_no, org_type from p_access_org where staff_no = ? and org_type=? and org_no = ?) acc\n" + 
					" where stat.org_no = acc.org_no\n" + 
					"   and stat.stat_date >= to_date(?, 'yyyy-MM-dd')\n" + 
					"   and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)";
				logger.debug(sql);
				try{
					pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {user.getStaffNo(),NodeTypeUtils.NODETYPE_ORG04, nodeId, startDate, endDate});
				}catch(RuntimeException e){
					logger.debug("根据条件查询某用电单位某时期总耗电量出错！");
					throw e;
				}
			}else if(NodeTypeUtils.NODETYPE_ORG05.equals(orgType) || NodeTypeUtils.NODETYPE_ORG06.equals(orgType)){
				sql = 
					"select sum(stat.pap_e) as pap_e\n" +
					"  from a_cons_stat_d stat, c_cons cons\n" + 
					" where stat.cons_no = cons.cons_no\n" + 
					"   and cons.org_no = ?\n" + 
					"   and stat.stat_date >= to_date(?, 'yyyy-MM-dd')\n" + 
					"   and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)";
				sql += SysPrivilige.addPri(user,"cons","",3);
				logger.debug(sql);
				try{
					pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {nodeId, startDate, endDate, user.getStaffNo(), user.getStaffNo()});
				}catch(RuntimeException e){
					logger.debug("根据条件查询某用电单位某时期总耗电量出错！");
					throw e;
				}
			}else{
				return 0.0;
			}
		}else if(NodeTypeUtils.NODETYPE_TRADE.equals(nodeType)){
			sql = 
				"select sum(stat.pap_e) as pap_e\n" +
				"  from a_cons_stat_d stat, c_cons cons\n" + 
				" where stat.cons_no = cons.cons_no\n" + 
				"   and cons.trade_code = ?\n" + 
				"   and stat.stat_date >= to_date(?, 'yyyy-MM-dd')\n" + 
				"   and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)";
			sql += SysPrivilige.addPri(user,"cons","",3);
			logger.debug(sql);
			try{
				pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {nodeId, startDate, endDate, user.getStaffNo(), user.getStaffNo()});
			}catch(RuntimeException e){
				logger.debug("根据条件查询某用电单位某时期总耗电量出错！");
				throw e;
			}
		}else if(NodeTypeUtils.NODETYPE_SUB.equals(nodeType)){
			sql = 
				"select sum(stat.pap_e) as pap_e\n" +
				"  from a_cons_stat_d stat, c_cons cons\n" + 
				" where stat.cons_no = cons.cons_no\n" + 
				"   and cons.subs_id = ?\n" + 
				"   and stat.stat_date >= to_date(?, 'yyyy-MM-dd')\n" + 
				"   and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)";
			sql += SysPrivilige.addPri(user,"cons","",3);
			logger.debug(sql);
			try{
				pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {nodeId, startDate, endDate, user.getStaffNo(), user.getStaffNo()});
			}catch(RuntimeException e){
				logger.debug("根据条件查询某用电单位某时期总耗电量出错！");
				throw e;
			}
		}else if(NodeTypeUtils.NODETYPE_LINE.equals(nodeType)){
			sql = 
				"select sum(stat.pap_e) as pap_e\n" +
				"  from a_cons_stat_d stat, c_cons cons\n" + 
				" where stat.cons_no = cons.cons_no\n" + 
				"   and cons.line_id = ?\n" + 
				"   and stat.stat_date >= to_date(?, 'yyyy-MM-dd')\n" + 
				"   and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)";
			sql += SysPrivilige.addPri(user,"cons","",3);
			logger.debug(sql);
			try{
				pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {nodeId, startDate, endDate, user.getStaffNo(), user.getStaffNo()});
			}catch(RuntimeException e){
				logger.debug("根据条件查询某用电单位某时期总耗电量出错！");
				throw e;
			}
		}else if(NodeTypeUtils.NODETYPE_TG.equals(nodeType)){
			sql = 
				"select sum(stat.pap_e) as pap_e\n" +
				"  from a_cons_stat_d stat, c_cons cons\n" + 
				" where stat.cons_no = cons.cons_no\n" + 
				"   and cons.tg_id = ?\n" + 
				"   and stat.stat_date >= to_date(?, 'yyyy-MM-dd')\n" + 
				"   and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)";
			sql += SysPrivilige.addPri(user,"cons","",3);
			logger.debug(sql);
			try{
				pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {nodeId, startDate, endDate, user.getStaffNo(), user.getStaffNo()});
			}catch(RuntimeException e){
				logger.debug("根据条件查询某用电单位某时期总耗电量出错！");
				throw e;
			}
		}else{
			return 0.0;
		}
		return pap.doubleValue();
	}
	
	/**
	 * 根据条件查询某用电单位某时期排名前n位用户的总耗电量
	 * @param nodeId
	 * @param nodeType
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @param sort
	 * @return Double
	 */
	public Double queryPSASortPower(String nodeId, String nodeType,
			String orgType, String startDate, String endDate, int sort,PSysUser user){
		String sql = "";
		Long pap = 0L;
		if(NodeTypeUtils.NODETYPE_ORG.equals(nodeType)){
			if(null == orgType || "".equals(orgType)){
				return 0.0;
			}
			if(NodeTypeUtils.NODETYPE_ORG02.equals(orgType)){
				sql = 
					"select sum(pap_e) as pap_e\n" +
					"  from (select pap_e, cons_no, rownum rw\n" + 
					"          from (select stat.cons_no, sum(stat.pap_e) as pap_e\n" + 
					"                  from a_cons_stat_d stat, (select org_no, org_type from p_access_org where staff_no = ? and org_type=?) acc \n" + 
					"                 where stat.org_no = acc.org_no\n" + 
					"                   and stat.stat_date >=\n" + 
					"                       to_date(?, 'yyyy-MM-dd')\n" + 
					"                   and stat.stat_date <\n" + 
					"                       (to_date(?,'yyyy-MM-dd')+1)\n" + 
					"                 group by stat.cons_no\n" + 
					"                 order by sum(stat.pap_e) desc nulls last)\n" + 
					"         where rownum <= ?)";
				logger.debug(sql);
				try{
					pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {user.getStaffNo(),NodeTypeUtils.NODETYPE_ORG04, startDate, endDate, sort});
				}catch(RuntimeException e){
					logger.debug("根据条件查询某用电单位某月排名前n位用户的总耗电量出错！");
					throw e;
				}
			}else if(NodeTypeUtils.NODETYPE_ORG03.equals(orgType)){
				nodeId = nodeId +"%";
				sql =
					"select sum(pap_e) as pap_e\n" +
					"  from (select pap_e, cons_no, rownum rw\n" + 
					"          from (select stat.cons_no, sum(stat.pap_e) as pap_e\n" + 
					"                  from a_cons_stat_d stat, (select org_no, org_type from p_access_org where staff_no = ? and org_type=? and org_no like ?) acc\n" + 
					"                 where stat.org_no = acc.org_no\n" + 
					"                   and stat.stat_date >=\n" + 
					"                       to_date(?, 'yyyy-MM-dd')\n" + 
					"                   and stat.stat_date < (to_date(?,'yyyy-MM-dd')+1)\n" + 
					"                 group by stat.cons_no\n" + 
					"                 order by sum(stat.pap_e) desc nulls last)\n" + 
					"         where rownum <= ?)";
				logger.debug(sql);
				try{
					pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {user.getStaffNo(),NodeTypeUtils.NODETYPE_ORG04, nodeId, startDate, endDate, sort});
				}catch(RuntimeException e){
					logger.debug("根据条件查询某用电单位某月排名前n位用户的总耗电量出错！");
					throw e;
				}
			}else if(NodeTypeUtils.NODETYPE_ORG04.equals(orgType)){
				sql =
					"select sum(pap_e) as pap_e\n" +
					"  from (select pap_e, cons_no, rownum rw\n" + 
					"          from (select stat.cons_no, sum(stat.pap_e) as pap_e\n" + 
					"                  from a_cons_stat_d stat, (select org_no, org_type from p_access_org where staff_no = ? and org_type=? and org_no = ?) acc\n" + 
					"                 where stat.org_no = acc.org_no\n" + 
					"                   and stat.stat_date >=\n" + 
					"                       to_date(?, 'yyyy-MM-dd')\n" + 
					"                   and stat.stat_date <\n" + 
					"                       (to_date(?,'yyyy-MM-dd')+1)\n" + 
					"                 group by stat.cons_no\n" + 
					"                 order by sum(stat.pap_e) desc nulls last)\n" + 
					"         where rownum <= ?)";
				logger.debug(sql);
				try{
					pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {user.getStaffNo(),NodeTypeUtils.NODETYPE_ORG04, nodeId, startDate, endDate, sort});
				}catch(RuntimeException e){
					logger.debug("根据条件查询某用电单位某月排名前n位用户的总耗电量出错！");
					throw e;
				}
			}else if(NodeTypeUtils.NODETYPE_ORG05.equals(orgType) || NodeTypeUtils.NODETYPE_ORG06.equals(orgType)){
				sql =
					"select sum(pap_e) as pap_e\n" +
					"  from (select pap_e, cons_no, rownum rw\n" + 
					"          from (select stat.cons_no, sum(stat.pap_e) as pap_e\n" + 
					"                  from a_cons_stat_d stat, c_cons cons\n" + 
					"                 where stat.cons_no = cons.cons_no\n" + 
					"                   and cons.org_no = ?\n" + 
					"                   and stat.stat_date >=\n" + 
					"                       to_date(?, 'yyyy-MM-dd')\n" + 
					"                   and stat.stat_date <\n" + 
					"                       (to_date(?,'yyyy-MM-dd')+1)\n" ;
				sql += SysPrivilige.addPri(user,"cons","",3);
				sql +=
					"                 group by stat.cons_no\n" + 
					"                 order by sum(stat.pap_e) desc nulls last)\n" + 
					"         where rownum <= ?)";
				logger.debug(sql);
				try{
					pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {nodeId, startDate, endDate, user.getStaffNo(), user.getStaffNo(), sort});
				}catch(RuntimeException e){
					logger.debug("根据条件查询某用电单位某月排名前n位用户的总耗电量出错！");
					throw e;
				}
			}else{
				return 0.0;
			}
		}else if(NodeTypeUtils.NODETYPE_TRADE.equals(nodeType)){
			sql =
				"select sum(pap_e) as pap_e\n" +
				"  from (select pap_e, cons_no, rownum rw\n" + 
				"          from (select stat.cons_no, sum(stat.pap_e) as pap_e\n" + 
				"                  from a_cons_stat_d stat, c_cons cons\n" + 
				"                 where stat.cons_no = cons.cons_no\n" + 
				"                   and cons.trade_code = ?\n" + 
				"                   and stat.stat_date >=\n" + 
				"                       to_date(?, 'yyyy-MM-dd')\n" + 
				"                   and stat.stat_date <\n" + 
				"                       (to_date(?,'yyyy-MM-dd')+1)\n" ;
			sql += SysPrivilige.addPri(user,"cons","",3);
			sql +=
				"                 group by stat.cons_no\n" + 
				"                 order by sum(stat.pap_e) desc nulls last)\n" + 
				"         where rownum <= ?)";
			logger.debug(sql);
			try{
				pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {nodeId, startDate, endDate, user.getStaffNo(), user.getStaffNo(), sort});
			}catch(RuntimeException e){
				logger.debug("根据条件查询某用电单位某月排名前n位用户的总耗电量出错！");
				throw e;
			}
		}else if(NodeTypeUtils.NODETYPE_SUB.equals(nodeType)){
			sql =
				"select sum(pap_e) as pap_e\n" +
				"  from (select pap_e, cons_no, rownum rw\n" + 
				"          from (select stat.cons_no, sum(stat.pap_e) as pap_e\n" + 
				"                  from a_cons_stat_d stat, c_cons cons\n" + 
				"                 where stat.cons_no = cons.cons_no\n" + 
				"                   and cons.subs_id = ?\n" + 
				"                   and stat.stat_date >=\n" + 
				"                       to_date(?, 'yyyy-MM-dd')\n" + 
				"                   and stat.stat_date <\n" + 
				"                       (to_date(?,'yyyy-MM-dd')+1)\n" ;
			sql += SysPrivilige.addPri(user,"cons","",3);
			sql +=
				"                 group by stat.cons_no\n" + 
				"                 order by sum(stat.pap_e) desc nulls last)\n" + 
				"         where rownum <= ?)";
			logger.debug(sql);
			try{
				pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {nodeId, startDate, endDate, user.getStaffNo(), user.getStaffNo(), sort});
			}catch(RuntimeException e){
				logger.debug("根据条件查询某用电单位某月排名前n位用户的总耗电量出错！");
				throw e;
			}
		}else if(NodeTypeUtils.NODETYPE_LINE.equals(nodeType)){
			sql =
				"select sum(pap_e) as pap_e\n" +
				"  from (select pap_e, cons_no, rownum rw\n" + 
				"          from (select stat.cons_no, sum(stat.pap_e) as pap_e\n" + 
				"                  from a_cons_stat_d stat, c_cons cons\n" + 
				"                 where stat.cons_no = cons.cons_no\n" + 
				"                   and cons.line_id = ?\n" + 
				"                   and stat.stat_date >=\n" + 
				"                       to_date(?, 'yyyy-MM-dd')\n" + 
				"                   and stat.stat_date <\n" + 
				"                       (to_date(?,'yyyy-MM-dd')+1)\n" ;
			sql += SysPrivilige.addPri(user,"cons","",3);
			sql +=
				"                 group by stat.cons_no\n" + 
				"                 order by sum(stat.pap_e) desc nulls last)\n" + 
				"         where rownum <= ?)";
			logger.debug(sql);
			try{
				pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {nodeId, startDate, endDate, user.getStaffNo(), user.getStaffNo(), sort});
			}catch(RuntimeException e){
				logger.debug("根据条件查询某用电单位某月排名前n位用户的总耗电量出错！");
				throw e;
			}
		}else if(NodeTypeUtils.NODETYPE_TG.equals(nodeType)){
			sql =
				"select sum(pap_e) as pap_e\n" +
				"  from (select pap_e, cons_no, rownum rw\n" + 
				"          from (select stat.cons_no, sum(stat.pap_e) as pap_e\n" + 
				"                  from a_cons_stat_d stat, c_cons cons\n" + 
				"                 where stat.cons_no = cons.cons_no\n" + 
				"                   and cons.tg_id = ?\n" + 
				"                   and stat.stat_date >=\n" + 
				"                       to_date(?, 'yyyy-MM-dd')\n" + 
				"                   and stat.stat_date <\n" + 
				"                       (to_date(?,'yyyy-MM-dd')+1)\n" ;
			sql += SysPrivilige.addPri(user,"cons","",3);
			sql +=
				"                 group by stat.cons_no\n" + 
				"                 order by sum(stat.pap_e) desc nulls last)\n" + 
				"         where rownum <= ?)";
			logger.debug(sql);
			try{
				pap = this.getJdbcTemplate().queryForLong(sql, new Object[] {nodeId, startDate, endDate, user.getStaffNo(), user.getStaffNo(), sort});
			}catch(RuntimeException e){
				logger.debug("根据条件查询某用电单位某月排名前n位用户的总耗电量出错！");
				throw e;
			}
		}else{
			return 0.0;
		}
		return pap.doubleValue();
	}
	
	/**
	 * 自定义查询返回的值对象，用于用电客户排名分析的Grid显示
	 */
	class PowerSortAnalyseRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			PowerSortAnalyseDto powerSortAnalyseDto = new PowerSortAnalyseDto();
			try {
				powerSortAnalyseDto.setSort(rs.getInt("RW"));
				powerSortAnalyseDto.setConsNo(rs.getString("CONS_NO"));
				powerSortAnalyseDto.setConsName(rs.getString("CONS_NAME"));
				powerSortAnalyseDto.setRunCap((null != rs.getString("RUN_CAP"))? rs.getDouble("RUN_CAP") : null);
				powerSortAnalyseDto.setTradeName(rs.getString("TRADE_NAME"));
				powerSortAnalyseDto.setVoltGrade(rs.getString("VOLT"));
				powerSortAnalyseDto.setBqyd((null != rs.getString("BQYD"))? rs.getDouble("BQYD") : null);
				powerSortAnalyseDto.setTqyd((null != rs.getString("TQYD"))? rs.getDouble("TQYD") : null);
				
				return powerSortAnalyseDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
