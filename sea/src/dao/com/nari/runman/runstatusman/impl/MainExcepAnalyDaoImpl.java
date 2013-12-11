package com.nari.runman.runstatusman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.basicdata.MainExceptionCategoryBean;
import com.nari.basicdata.OrgNoNameBean;
import com.nari.basicdata.TerminalEventBean;
import com.nari.basicdata.VwProtocolCodeBean;
import com.nari.runman.runstatusman.MainExcepAnalyDao;

import com.nari.statreport.MainExceptionBean;
import com.nari.statreport.TmnlRunStatus;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;
import common.Logger;

public class MainExcepAnalyDaoImpl extends JdbcBaseDAOImpl implements MainExcepAnalyDao{
	Logger log=Logger.getLogger(MainExcepAnalyDaoImpl.class);
	public  List<MainExceptionCategoryBean> findMainExceptionList() throws DBAccessException{
		StringBuffer sb = new StringBuffer();
		sb.append("select alarm_code,alarm_name from vw_sea_alarm_code union select '000', '全部' from dual");
//		MainExceptionCategoryBean mecb = new MainExceptionCategoryBean();
//		mecb.setMainExcepCode("000");
//		mecb.setMainExcepName("全部");
		List<MainExceptionCategoryBean> mecblist=null;
//      mecblist.add(mecb);
        mecblist = this.getJdbcTemplate().query(sb.toString(), new MainExcepCategoryMapper());
		return mecblist;
		}
	public Page<MainExceptionBean> findOrgNo(String orgNo,String orgType,String startDate,String endDate,String mainExcepCode,long start,int limit) throws DBAccessException{
		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
			 sql.append("select distinct oo.org_name,asa.cons_no,cc.cons_name,vtr.terminal_addr,vsac.alarm_name,\n");
			 sql.append("asa.alarm_date,vtf.factory_name,cc.contract_cap,vvc.volt,vwm.wiring_mode_name,asa.alarm_param\n");
			 sql.append("from a_sea_alarm asa,o_org oo,c_cons cc,vw_tmnl_run vtr,vw_sea_alarm_code vsac,\n"); 
			 sql.append("vw_tmnl_factory vtf,d_meter dm,vw_wiring_mode vwm,vw_volt_code vvc\n");
			 sql.append("where asa.org_no=oo.org_no and asa.cons_no=cc.cons_no\n");
			 sql.append("and asa.tmnl_asset_no=vtr.tmnl_asset_no and asa.alarm_code=vsac.alarm_code\n");
			 sql.append("and vtr.factory_code=vtf.factory_code and asa.asset_no=dm.asset_no and\n");
			 sql.append("dm.wiring_mode=vwm.wiring_mode and cc.volt_code= vvc.volt_code\n");
			 log.debug(sql.toString());
		if(!orgNo.equals("63101")){
		if ("06".equals(orgType)|| "03".equals(orgType) || "04".equals(orgType)) {
			orgNo = orgNo + "%";
			sql.append("   and asa.org_no like ?\n");
			runList.add(orgNo);
		} 
		}
		if((!mainExcepCode.equals("000"))&&(!mainExcepCode.equals(""))){
			sql.append("and asa.alarm_code = ? \n ");
			runList.add(mainExcepCode);
		}
		sql.append("and asa.alarm_date > to_date(?,'yyyy-mm-dd') and asa.alarm_date < to_date(?,'yyyy-mm-dd')  \n");		
		runList.add(startDate);
		runList.add(endDate);
		logger.debug(sql.toString());
			 
		//有几个查询条件，{}中返回几个参数	单位公变类型时间等		
		return super.pagingFind(sql.toString(), start, limit, new MainExceptionAnalysisRowMapper(), runList.toArray());
		
	
	}
	
	public Page<MainExceptionBean> findUsr(String consNo,String startDate,String endDate,String mainExcepCode,long start,int limit) throws DBAccessException{

		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
		sql.append("select distinct oo.org_name,asa.cons_no,cc.cons_name,vtr.terminal_addr,vsac.alarm_name,\n");
		 sql.append("asa.alarm_date,vtf.factory_name,cc.contract_cap,vvc.volt,vwm.wiring_mode_name,asa.alarm_param\n");
		 sql.append("from a_sea_alarm asa,o_org oo,c_cons cc,vw_tmnl_run vtr,vw_sea_alarm_code vsac,\n"); 
		 sql.append("vw_tmnl_factory vtf,d_meter dm,vw_wiring_mode vwm,vw_volt_code vvc\n");
		 sql.append("where asa.org_no=oo.org_no and asa.cons_no=cc.cons_no\n");
		 sql.append("and asa.tmnl_asset_no=vtr.tmnl_asset_no and asa.alarm_code=vsac.alarm_code\n");
		 sql.append("and vtr.factory_code=vtf.factory_code and asa.asset_no=dm.asset_no and\n");
		 sql.append("dm.wiring_mode=vwm.wiring_mode and cc.volt_code= vvc.volt_code\n");
			 
			sql.append("and asa.cons_no = ?\n");
			runList.add(consNo);
		
		if((!mainExcepCode.equals("000"))&&(!mainExcepCode.equals(""))){
			sql.append("and asa.alarm_code = ? \n ");
			runList.add(mainExcepCode);
		}
		sql.append("and asa.alarm_date >= to_date(?,'yyyy-mm-dd') and asa.alarm_date <= to_date(?,'yyyy-mm-dd')  \n");
		runList.add(startDate);
		runList.add(endDate);
		logger.debug(sql.toString());
			 
		//有几个查询条件，{}中返回几个参数	单位公变类型时间等		
		return super.pagingFind(sql.toString(), start, limit, new MainExceptionAnalysisRowMapper(), runList.toArray());
	
	
	}
	
	public Page<MainExceptionBean> findLine(String lineId,String startDate,String endDate,String mainExcepCode,long start,int limit) throws DBAccessException{


		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
		sql.append("select distinct oo.org_name,asa.cons_no,cc.cons_name,vtr.terminal_addr,vsac.alarm_name,\n");
		 sql.append("asa.alarm_date,vtf.factory_name,cc.contract_cap,vvc.volt,vwm.wiring_mode_name,asa.alarm_param\n");
		 sql.append("from a_sea_alarm asa,o_org oo,c_cons cc,vw_tmnl_run vtr,vw_sea_alarm_code vsac,\n"); 
		 sql.append("vw_tmnl_factory vtf,d_meter dm,vw_wiring_mode vwm,vw_volt_code vvc\n");
		 sql.append("where asa.org_no=oo.org_no and asa.cons_no=cc.cons_no\n");
		 sql.append("and asa.tmnl_asset_no=vtr.tmnl_asset_no and asa.alarm_code=vsac.alarm_code\n");
		 sql.append("and vtr.factory_code=vtf.factory_code and asa.asset_no=dm.asset_no and\n");
		 sql.append("dm.wiring_mode=vwm.wiring_mode and cc.volt_code= vvc.volt_code\n");
			 
			sql.append("and cc.line_id = ?\n");
			runList.add(lineId);
		
		if((!mainExcepCode.equals("000"))&&(!mainExcepCode.equals(""))){
			sql.append("and asa.alarm_code = ? \n ");
			runList.add(mainExcepCode);
		}
		sql.append("and asa.alarm_date >= to_date(?,'yyyy-mm-dd') and asa.alarm_date <= to_date(?,'yyyy-mm-dd')  \n");
		runList.add(startDate);
		runList.add(endDate);
		logger.debug(sql.toString());
			 
		//有几个查询条件，{}中返回几个参数	单位公变类型时间等		
		return super.pagingFind(sql.toString(), start, limit, new MainExceptionAnalysisRowMapper(), runList.toArray());
	
	
	
	}
    public Page<MainExceptionBean> findSub(String subsId,String startDate,String endDate,String mainExcepCode,long start,int limit) throws DBAccessException{


		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
		sql.append("select distinct oo.org_name,asa.cons_no,cc.cons_name,vtr.terminal_addr,vsac.alarm_name,\n");
		 sql.append("asa.alarm_date,vtf.factory_name,cc.contract_cap,vvc.volt,vwm.wiring_mode_name,asa.alarm_param\n");
		 sql.append("from a_sea_alarm asa,o_org oo,c_cons cc,vw_tmnl_run vtr,vw_sea_alarm_code vsac,\n"); 
		 sql.append("vw_tmnl_factory vtf,d_meter dm,vw_wiring_mode vwm,vw_volt_code vvc\n");
		 sql.append("where asa.org_no=oo.org_no and asa.cons_no=cc.cons_no\n");
		 sql.append("and asa.tmnl_asset_no=vtr.tmnl_asset_no and asa.alarm_code=vsac.alarm_code\n");
		 sql.append("and vtr.factory_code=vtf.factory_code and asa.asset_no=dm.asset_no and\n");
		 sql.append("dm.wiring_mode=vwm.wiring_mode and cc.volt_code= vvc.volt_code\n");
		    subsId = "%"+subsId+"%";	 
			sql.append("and cc.subs_id like ?\n");
			runList.add(subsId);
		
		if((!mainExcepCode.equals("000"))&&(!mainExcepCode.equals(""))){
			sql.append("and asa.alarm_code = ? \n ");
			runList.add(mainExcepCode);
		}
		sql.append("and asa.alarm_date >= to_date(?,'yyyy-mm-dd') and asa.alarm_date <= to_date(?,'yyyy-mm-dd')  \n");
		runList.add(startDate);
		runList.add(endDate);
		logger.debug(sql.toString());
			 
		//有几个查询条件，{}中返回几个参数	单位公变类型时间等		
		return super.pagingFind(sql.toString(), start, limit, new MainExceptionAnalysisRowMapper(), runList.toArray());
	
	
	
    }
    
    @SuppressWarnings("unchecked")
	public String findOrgName(String orgNo) throws DBAccessException{
    	StringBuffer sb = new StringBuffer();
    	List<Object> runList = new ArrayList<Object>();
    	String orgName=null;
    	if(!orgNo.equals("63101")){
    	sb.append("select org_no,org_name from vw_org_all voa where voa.org_no = ?\n");
    	runList.add(orgNo);
    	List<OrgNoNameBean>  orgList=this.getJdbcTemplate().query(sb.toString(), runList.toArray(), new OrgNameMapper());
    	log.debug(sb.toString());
    	      for(OrgNoNameBean o: orgList )
    	      {
    		     orgName=o.getUserInfoName();
    	      }
    	}
    	else
    	orgName="青海省电力公司";
    	return orgName;
    }

	}
	
	class MainExcepCategoryMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			MainExceptionCategoryBean mecb = new MainExceptionCategoryBean();
			try {
				mecb.setMainExcepCode(rs.getString("alarm_code"));
				mecb.setMainExcepName(rs.getString("alarm_name"));
				return mecb;
			} catch (Exception e) {
				
				return null;
			}
		}

	}
	
	class MainExceptionAnalysisRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			MainExceptionBean mainExcepbean = new MainExceptionBean();
			try {
//				mainExcepbean.setTmnlExceptionId(rs.getString("alarm_id"));
				mainExcepbean.setOrgName(rs.getString("org_name"));
				mainExcepbean.setConsNo(rs.getString("cons_no"));
				mainExcepbean.setConsName(rs.getString("cons_name"));
				mainExcepbean.setTerminalAddr(rs.getString("terminal_addr"));
				mainExcepbean.setExceptName(rs.getString("alarm_name"));
				mainExcepbean.setExceptDate(rs.getString("alarm_date"));
				mainExcepbean.setFactoryName(rs.getString("factory_name"));
				mainExcepbean.setContractCap(rs.getString("contract_cap"));
				mainExcepbean.setVoltageGrade(rs.getString("volt"));
				mainExcepbean.setWiringMode(rs.getString("wiring_mode_name"));
				mainExcepbean.setRemark(rs.getString("alarm_param"));
				return mainExcepbean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	class OrgNameMapper implements RowMapper{

		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OrgNoNameBean onnb = new OrgNoNameBean();
			try {
				onnb.setUserInfoNo(rs.getString("org_no"));
				onnb.setUserInfoName(rs.getString("org_name"));
				return onnb;
				
			} catch (Exception e) {
				return null;
			}
			
		}
	
	}

