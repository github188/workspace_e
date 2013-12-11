package com.nari.runman.runstatusman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.runman.runstatusman.ATmnlFlowMBeanDao;
import com.nari.statreport.ATmnlFlowMBean;
import com.nari.statreport.ATmnlFlowMBeanH;
import com.nari.statreport.SimFee;
import com.nari.statreport.TmnlFactory;
import com.nari.support.Page;

public class ATmnlFlowMBeanDaoImpl extends JdbcBaseDAOImpl implements
		ATmnlFlowMBeanDao {

	@SuppressWarnings("unchecked")
	public List<TmnlFactory> findTmnlFactory() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from vw_tmnl_factory vtf");
		return getJdbcTemplate().query(sql.toString(), new TmnlFactoryMapper());
	}

	class TmnlFactoryMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TmnlFactory tmnlfactory = new TmnlFactory();
			try {
				tmnlfactory.setFactoryCode(rs.getString("FACTORY_CODE"));
				tmnlfactory.setFactoryName(rs.getString("FACTORY_NAME"));
				return tmnlfactory;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SimFee> findSimFee() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select (select sf.param_item_val\n")
				.append("          from vw_sim_fee sf\n")
				.append("         where sf.param_item_no = 'GPRS') GPRS,\n")
				.append("       (select sf.param_item_val\n")
				.append("          from vw_sim_fee sf\n")
				.append("         where sf.param_item_no = 'SMS') SMS\n")
				.append("  from dual)");
		return super.getJdbcTemplate().query(sql.toString(), new SimFeeMapper());
	}
	
	
	class SimFeeMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			SimFee simfee = new SimFee();
			try {
				simfee.setGprs(rs.getString("GPRS"));
				simfee.setSms(rs.getString("SMS"));
				return simfee;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	@Override
	public Page<ATmnlFlowMBeanH> findChannelH(String sDate, String orgName,String orgType,
			String manufacture, long start, int limit) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select org_name, cons_no, cons_name, terminal_addr, sum_gprs, sum_sms, fee from (select org_name,\n")
				.append("       cons_no,\n")
				.append("       cons_name,\n")
				.append("       terminal_addr,\n")
				.append("       sum_gprs,\n")
				.append("       sum_sms,\n")
				.append("       case\n")
				.append("         when sum_gprs > (select param_item_val\n")
				.append("                            from vw_sim_fee\n")
				.append("                           where param_item_no = 'GPRS') * 1000 then\n")
				.append("          (sum_gprs - (select param_item_val\n")
				.append("                         from vw_sim_fee\n")
				.append("                        where param_item_no = 'GPRS') * 1000) *\n")
				.append("          (select param_item_val\n")
				.append("             from vw_sim_fee\n")
				.append("            where param_item_no = 'GPRS_UNIT') + 4\n")
				.append("         else\n")
				.append("          4\n")
				.append("       end + case\n")
				.append("         when sum_sms > (select param_item_val\n")
				.append("                           from vw_sim_fee\n")
				.append("                          where param_item_no = 'SMS') then\n")
				.append("          (sum_sms -\n")
				.append("          (select param_item_val from vw_sim_fee where param_item_no = 'SMS')) *\n")
				.append("          (select param_item_val\n")
				.append("             from vw_sim_fee\n")
				.append("            where param_item_no = 'SMS_UNIT') + 10\n")
				.append("         else\n")
				.append("          10\n")
				.append("       end fee\n")
				.append("  from (select max(tab.org_name) org_name,\n")
				.append("               max(tab.cons_no) cons_no,\n")
				.append("               max(tab.cons_name) cons_name,\n")
				.append("               max(tab.terminal_addr) terminal_addr,\n")
				.append("               max(tab.gprs) sum_gprs,\n")
				.append("               max(tab.sms) sum_sms\n")
				.append("          from (select o.org_name,\n")
				.append("                       c.cons_no,\n")
				.append("                       c.cons_name,\n")
				.append("                       atf.terminal_addr,\n")
				.append("                       atf.tmnl_asset_no,\n")
				.append("                       case\n")
				.append("                         when atf.comm_mode = '300' then\n")
				.append("                          atf.up_flow\n")
				.append("                         else\n")
				.append("                          0\n")
				.append("                       end sms,\n")
				.append("                       case\n")
				.append("                         when atf.comm_mode = '3000' then\n")
				.append("                          atf.down_flow + atf.up_flow\n")
				.append("                         else\n")
				.append("                          0\n")
				.append("                       end gprs\n")
				.append("                  from a_tmnl_flow_m atf, o_org o, c_cons c, r_tmnl_run r\n")
				.append("                 where atf.tmnl_asset_no = r.tmnl_asset_no\n")
				.append("                   and r.cons_no = c.cons_no\n")
				.append("                   and atf.org_no = o.org_no\n")
				.append("                   and to_char(atf.flow_date, 'yyyy-mm') = ?\n") ;
//					if("04".equals(orgType)){
						sql.append("   AND atf.ORG_NO = ?");
//					} else if("06".equals(orgType)) {
//						sql.append("   AND C.Bureau_No = ?");
//					} else {
//						return null;
//					}
				if(!"00".equals(manufacture)){
						sql.append("and r.factory_code = ?\n");
				}
				sql.append(") tab\n group by tab.tmnl_asset_no) last_tab\n")
				.append(" where (sum_sms >\n")
				.append("       (select param_item_val from vw_sim_fee where param_item_no = 'SMS') or\n")
				.append("       sum_gprs >\n")
				.append("       (select param_item_val from vw_sim_fee where param_item_no = 'GPRS') * 1000))\n")
				.append(" order by org_name, cons_no, terminal_addr");
		
		return super.pagingFind(sql.toString(), start, limit, new aTmnlFlowMBeanHMapper(), new Object[]{sDate, orgName, manufacture});
	}

	class aTmnlFlowMBeanHMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ATmnlFlowMBeanH atmnlflowmbeanh = new ATmnlFlowMBeanH();
			try {
				atmnlflowmbeanh.setOrgName(rs.getString("ORG_NAME"));
				atmnlflowmbeanh.setConsNo(rs.getString("CONS_NO"));
				atmnlflowmbeanh.setConsName(rs.getString("CONS_NAME"));
				atmnlflowmbeanh.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				atmnlflowmbeanh.setSumGprs(rs.getLong("SUM_GPRS"));
				atmnlflowmbeanh.setSumSms(rs.getLong("SUM_SMS"));
				atmnlflowmbeanh.setFee(rs.getLong("FEE"));
				return atmnlflowmbeanh;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	
	@Override
	public Page<ATmnlFlowMBeanH> findChannelHH(String sDate, String orgName,String orgType,
			String manufacture, long start, int limit) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from (select org_name,\n")
				.append("       cons_no,\n")
				.append("       cons_name,\n")
				.append("       terminal_addr,\n")
				.append("       sum_gprs,\n")
				.append("       sum_sms,\n")
				.append("       case\n")
				.append("         when sum_gprs > (select param_item_val\n")
				.append("                            from vw_sim_fee\n")
				.append("                           where param_item_no = 'GPRS') * 1000 then\n")
				.append("          (sum_gprs - (select param_item_val\n")
				.append("                         from vw_sim_fee\n")
				.append("                        where param_item_no = 'GPRS') * 1000) *\n")
				.append("          (select param_item_val\n")
				.append("             from vw_sim_fee\n")
				.append("            where param_item_no = 'GPRS_UNIT') + 4\n")
				.append("         else\n")
				.append("          4\n")
				.append("       end + case\n")
				.append("         when sum_sms > (select param_item_val\n")
				.append("                           from vw_sim_fee\n")
				.append("                          where param_item_no = 'SMS') then\n")
				.append("          (sum_sms -\n")
				.append("          (select param_item_val from vw_sim_fee where param_item_no = 'SMS')) *\n")
				.append("          (select param_item_val\n")
				.append("             from vw_sim_fee\n")
				.append("            where param_item_no = 'SMS_UNIT') + 10\n")
				.append("         else\n")
				.append("          10\n")
				.append("       end fee\n")
				.append("  from (select max(tab.org_name) org_name,\n")
				.append("               max(tab.cons_no) cons_no,\n")
				.append("               max(tab.cons_name) cons_name,\n")
				.append("               max(tab.terminal_addr) terminal_addr,\n")
				.append("               max(tab.gprs) sum_gprs,\n")
				.append("               max(tab.sms) sum_sms\n")
				.append("          from (select o.org_name,\n")
				.append("                       c.cons_no,\n")
				.append("                       c.cons_name,\n")
				.append("                       atf.terminal_addr,\n")
				.append("                       atf.tmnl_asset_no,\n")
				.append("                       case\n")
				.append("                         when atf.comm_mode = '300' then\n")
				.append("                          atf.up_flow\n")
				.append("                         else\n")
				.append("                          0\n")
				.append("                       end sms,\n")
				.append("                       case\n")
				.append("                         when atf.comm_mode = '3000' then\n")
				.append("                          atf.down_flow + atf.up_flow\n")
				.append("                         else\n")
				.append("                          0\n")
				.append("                       end gprs\n")
				.append("                  from a_tmnl_flow_m atf, o_org o, c_cons c, r_tmnl_run r\n")
				.append("                 where atf.tmnl_asset_no = r.tmnl_asset_no\n")
				.append("                   and r.cons_no = c.cons_no\n")
				.append("                   and atf.org_no = o.org_no\n")
				.append("                   and to_char(atf.flow_date, 'yyyy-mm') = ? \n") ;
//					if("04".equals(orgType)){
						sql.append("   AND atf.ORG_NO = ?");
//					} else if("06".equals(orgType)) {
//						sql.append("   AND C.Bureau_No = ?");
//					} else {
//						return null;
//					}
				if(!"00".equals(manufacture)){
						sql.append("and r.factory_code = ?\n");
				}
				sql.append(" ) tab\n  group by tab.tmnl_asset_no) last_tab)\n")
//				.append(" --where (sum_sms >\n")
//				.append("    --   (select param_item_val from vw_sim_fee where param_item_no = 'SMS') or\n")
//				.append("      -- sum_gprs >\n")
//				.append("       --(select param_item_val from vw_sim_fee where param_item_no = 'GPRS') * 1000)\n")
				.append(" order by org_name, cons_no, terminal_addr");
		
		return super.pagingFind(sql.toString(), start, limit, new aTmnlFlowMBeanHMapper(), new Object[]{sDate, orgName, manufacture});
	}

	
	
	@Override
	public Page<ATmnlFlowMBean> findChannelMonitor(String sDate, long start,
			int limit) {
		
		StringBuffer sql = new StringBuffer();
		
		sql	.append("select ot.org_no, ot.org_name,\n")
			.append("       case\n") 
			.append("         when st.sms_over_count is null or st.sms_over_count < 0 then\n") 
			.append("          0\n") 
			.append("         else\n") 
			.append("          st.sms_over_count\n") 
			.append("       end sms_over_count,\n") 
			.append("       case\n") 
			.append("         when st.sms_over_flow is null or st.sms_over_flow < 0 then\n") 
			.append("          0\n") 
			.append("         else\n") 
			.append("          st.sms_over_flow\n") 
			.append("       end sms_over_flow,\n") 
			.append("       case\n") 
			.append("         when gt.gprs_over_count is null or gt.gprs_over_count < 0 then\n") 
			.append("          0\n") 
			.append("         else\n") 
			.append("          gt.gprs_over_count\n") 
			.append("       end gprs_over_count,\n") 
			.append("       case\n") 
			.append("         when gt.gprs_over_flow is null or gt.gprs_over_flow < 0 then\n") 
			.append("          0\n") 
			.append("         else\n") 
			.append("          gt.gprs_over_flow\n") 
			.append("       end gprs_over_flow\n") 
			.append("  from\n") 
			.append(" --===================================================查询短信超用户数及超流量\n") 
			.append("        (select a.org_no,\n") 
			.append("                count(1) sms_over_count,\n") 
			.append("                (select sum(up_flow)\n") 
			.append("                   from A_TMNL_FLOW_M\n") 
			.append("                  where org_no = a.org_no\n") 
			.append("                    and comm_mode = '300'\n") 
			.append("                    and to_char(flow_date, 'yyyy-mm') = ?) -\n") 
			.append("                (select count(1)\n") 
			.append("                   from A_TMNL_FLOW_M\n") 
			.append("                  where org_no = a.org_no\n") 
			.append("                    and comm_mode = '300'\n") 
			.append("                    and to_char(flow_date, 'yyyy-mm') = ?) *\n") 
			.append("                (select param_item_val\n") 
			.append("                   from vw_sim_fee\n") 
			.append("                  where lower(param_item_no) = 'sms') sms_over_flow\n") 
			.append("           from A_TMNL_FLOW_M a\n") 
			.append("          where comm_mode = '300'\n") 
			.append("            and a.up_flow > (select param_item_val\n") 
			.append("                               from vw_sim_fee\n") 
			.append("                              where lower(param_item_no) = 'sms')\n") 
			.append("            and to_char(flow_date, 'yyyy-mm') = ?\n") 
			.append("          group by a.org_no\n") 
			.append("          order by a.org_no) st,\n") 
			.append(" --==============================================查询GPRS超用户数及超流量\n") 
			.append("       (select a.org_no,\n") 
			.append("               count(1) gprs_over_count,\n") 
			.append("               (select sum(up_flow + down_flow)\n") 
			.append("                  from A_TMNL_FLOW_M\n") 
			.append("                 where org_no = a.org_no\n") 
			.append("                   and comm_mode = '3000'\n") 
			.append("                   and to_char(flow_date, 'yyyy-mm') = ?) -\n") 
			.append("               (select count(1)\n") 
			.append("                  from A_TMNL_FLOW_M\n") 
			.append("                 where org_no = a.org_no\n") 
			.append("                   and comm_mode = '3000'\n") 
			.append("                   and to_char(flow_date, 'yyyy-mm') = ?) *\n") 
			.append("               (select param_item_val * 1000\n") 
			.append("                  from vw_sim_fee\n") 
			.append("                 where lower(param_item_no) = 'gprs') gprs_over_flow\n") 
			.append("          from A_TMNL_FLOW_M a\n") 
			.append("         where comm_mode = '3000'\n") 
			.append("           and a.up_flow + a.down_flow >\n") 
			.append("               (select param_item_val * 1000\n") 
			.append("                  from vw_sim_fee\n") 
			.append("                 where lower(param_item_no) = 'gprs')\n") 
			.append("           and to_char(flow_date, 'yyyy-mm') = ?\n") 
			.append("         group by a.org_no\n") 
			.append("         order by a.org_no) gt,\n") 
			.append(" --========================================== 查询 供电单位名称\n") 
			.append("       (select distinct a.org_no, o.org_name\n") 
			.append("          from A_TMNL_FLOW_M a, o_org o\n") 
			.append("         where a.org_no = o.org_no\n") 
			.append("           and to_char(flow_date, 'yyyy-mm') = ?\n") 
			.append("         order by org_no) ot\n") 
			.append(" where ot.org_no = st.org_no(+)\n") 
			.append("   and ot.org_no = gt.org_no(+)\n") 
			.append("   and (sms_over_count > 0 or gprs_over_count >0)\n")
			.append(" order by ot.org_name");
		
		return super.pagingFind(sql.toString(), start, limit, new aTmnlFlowMBeanMapper(), new Object[] {sDate,sDate,sDate,sDate,sDate,sDate,sDate});
	}

	class aTmnlFlowMBeanMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ATmnlFlowMBean atmnlflowmbean = new ATmnlFlowMBean();
			try {
				atmnlflowmbean.setOrgName(rs.getString("org_name"));
				atmnlflowmbean.setOrgNo(rs.getString("org_no"));
				atmnlflowmbean.setGprsover(rs.getLong("gprs_over_flow"));
				atmnlflowmbean.setGprsovercount(rs.getLong("gprs_over_count"));
				atmnlflowmbean.setSmsover(rs.getLong("sms_over_flow"));
				atmnlflowmbean.setSmsovercount(rs.getLong("sms_over_count"));
				return atmnlflowmbean;
			} catch (Exception e) {
				return null;
			}
		}
	}

}