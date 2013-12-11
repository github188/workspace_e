package com.nari.runman.abnormalhandle.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.basicdata.ElecExcepBean;
import com.nari.basicdata.ExceptionChineseEnglishHashMap;
import com.nari.basicdata.FailureStatistic;
import com.nari.basicdata.LostMonitorBean;
import com.nari.basicdata.LostMonitorBean2;
import com.nari.basicdata.TerminalEventBean;
import com.nari.basicdata.TerminalExceptionWorkBean;
import com.nari.privilige.PSysUser;
import com.nari.runman.abnormalhandle.ExceptionMonitorDao;
import com.nari.runman.abnormalhandle.impl.EventInfoDaoImpl.peMapper;
import com.nari.sysman.securityman.impl.SysPrivilige;


public class ExceptionMonitorDaoImpl extends JdbcBaseDAOImpl implements ExceptionMonitorDao{
	@SuppressWarnings("unchecked")
	public List<TerminalExceptionWorkBean> findTewbData(PSysUser userInfo){
		StringBuffer sb = new StringBuffer();
		List<String> paraList = new ArrayList<String>();
       sb.append("select bpe.event_no as aaa, bpe.event_name as bbb,tmp.exceptionTerminalNum as ccc from \n")
       .append("(select ate.except_code as exception_code, count(ate.tmnl_asset_no) as exceptionTerminalNum from a_tmnl_exception ate\n");
       
       if(!userInfo.getOrgNo().equals("63101")){
   		sb.append("where ate.org_no like ?\n");
   		String userOrgNo = userInfo.getOrgNo()+"%";
   		paraList.add(userOrgNo);
   		}
       
       sb.append("group by ate.except_code) tmp,\n")
       .append("b_protocol_event bpe where bpe.event_no = tmp.exception_code");
        
		List<TerminalExceptionWorkBean> listtewb= this.getJdbcTemplate().query(sb.toString(),paraList.toArray(), new peMapper());
        
	    int listLength = listtewb.size();
		
		if(listtewb.size()==0) 
		{
		    List<TerminalExceptionWorkBean> l = new ArrayList<TerminalExceptionWorkBean>();
		    l.add(createTewb());
		    return l;
		}
		else
		return listtewb;
	}
	
	public TerminalExceptionWorkBean createTewb(){
		 TerminalExceptionWorkBean pe = new TerminalExceptionWorkBean();
		    try{
		    	
				String terminalMainNotCon="0";
			    String terminalNotReport="0";
			    String terminalAmmeterNotCon="0";
			    String terminalSimNotSame="0";
			    String terminalNotSend="0";
			    String simFlowOver="0";
			    String terminalWarn="0";
			    String stopTerminalHasFlow="0";
		    	
		    	pe.setSimFlowOver(terminalMainNotCon);
		    	pe.setStopTerminalHasFlow(terminalNotReport);
		    	pe.setTerminalAmmeterNotCon(terminalAmmeterNotCon);
		    	pe.setTerminalMainNotCon(terminalSimNotSame);
		    	pe.setTerminalNotReport(terminalNotSend);
		    	pe.setTerminalNotSend(simFlowOver);
		    	pe.setTerminalSimNotSame(terminalWarn);
		    	pe.setTerminalWarn(stopTerminalHasFlow);
		    	return pe;
		    }catch(Exception e){
		    	return null;
		    }
	}
	
	
	class peMapper implements RowMapper {

	  public TerminalExceptionWorkBean pe = new TerminalExceptionWorkBean();
	  public String terminalMainNotCon="0";
	  public  String terminalNotReport="0";
	  public  String terminalAmmeterNotCon="0";
	  public  String terminalSimNotSame="0";
	  public  String terminalNotSend="0";
	  public  String simFlowOver="0";
	  public  String terminalWarn="0";
	  public  String stopTerminalHasFlow="0";
		
		public Object mapRow(ResultSet rs,int paramInt)throws SQLException {
		
		    try{		    
		    	String s1=rs.getString("aaa");
		    	String s2=rs.getString("bbb");
		    	String s3=rs.getString("ccc");	
		    
		    	if(s2.equals("无上报任务")) terminalNotSend=s3;
			    if(s2.equals("终端告警过多")) terminalWarn=s3;
			    if(s2.equals("终端与表计无通讯")) terminalAmmeterNotCon=s3;
			    if(s2.equals("终端与主站无通讯")) terminalMainNotCon=s3;
			    if(s2.equals("终端未建档")) terminalNotReport=s3;
			    if(s2.equals("停运和拆除终端还有流量")) stopTerminalHasFlow=s3;
			    if(s2.equals("SIM卡号不一致")) terminalSimNotSame=s3;
		    	
		    	pe.setSimFlowOver(simFlowOver);
		    	pe.setStopTerminalHasFlow(stopTerminalHasFlow);
		    	pe.setTerminalAmmeterNotCon(terminalAmmeterNotCon);
		    	pe.setTerminalMainNotCon(terminalMainNotCon);
		    	pe.setTerminalNotReport(terminalNotReport);
		    	pe.setTerminalNotSend(terminalNotSend);
		    	pe.setTerminalSimNotSame(terminalSimNotSame);
		    	pe.setTerminalWarn(terminalWarn);
		    	return pe;
		    }catch(Exception e){
		    	return null;
		    }
		}
		
	}
	
	public List<TerminalEventBean> findTelcData(){
	       StringBuffer sb = new StringBuffer();
	       sb.append("SELECT COUNT(DECODE(EVENT_LEVEL, 3, 1, NULL)) LEVEL1COUNT, COUNT(DECODE(EVENT_LEVEL, 2, 1, NULL)) LEVEL2COUNT, COUNT(DECODE(EVENT_LEVEL, 1, 1, NULL)) LEVEL3COUNT\n")
	         .append("FROM E_TMNL_EVENT EV, B_PROTOCOL_EVENT BP, E_DATA_MP ED, O_ORG OO,C_CONS CONS\n")
	         .append("WHERE EV.EVENT_NO = BP.EVENT_NO\n")
	         .append("AND EV.ID = ED.ID\n")
	         .append("AND ED.ORG_NO = OO.ORG_NO\n")
	         .append("AND ED.CONS_NO = CONS.CONS_NO AND EV.EVENT_TIME=TRUNC(SYSDATE)");
	         return this.getJdbcTemplate().query(sb.toString(), new MapperTelc());
	}
	
	class MapperTelc implements RowMapper {	
		public Object mapRow(ResultSet rs,int paramInt)throws SQLException {
		  TerminalEventBean te = new TerminalEventBean();
		    try{
		  	    te.setLevel1count(rs.getString("LEVEL1COUNT"));
		  	    te.setLevel2count(rs.getString("LEVEL2COUNT"));
		  	    te.setLevel3count(rs.getString("LEVEL3COUNT"));
		    	return te;
		    }catch(Exception e){
		    	return null;
		    }
		}
	}
	
	public List<LostMonitorBean> findLmbData(){
		StringBuffer sb = new StringBuffer();
		sb.append("select tg_name aaa,trunc((ppq-tg_spq)/ppq*100,2) bbb,read_succ_rate ccc from a_tg_pq atp,g_tg gt \n")
		.append("where atp.tg_id=gt.tg_id and (ppq-tg_spq)/ppq*100>=20 and to_date(atp.stat_date,'yyyymmdd')=trunc(sysdate-1)");
		return this.getJdbcTemplate().query(sb.toString(),new MapperGstq());  
	}
	class MapperGstq implements RowMapper {	
		public Object mapRow(ResultSet rs,int paramInt)throws SQLException {
			LostMonitorBean te = new LostMonitorBean();
		    try{
		    	String lost = rs.getString("bbb")+"%";
		    	String succRate = rs.getString("ccc")+"%";
		  	    te.setTqName(rs.getString("aaa"));
		  	    te.setTqLostRate(lost);
		  	    te.setTqSuccRate(succRate);
		    	return te;
		    }catch(Exception e){
		    	return null;
		    }
		}
	}
	
	public List<LostMonitorBean2> findLmb2Data(){
		StringBuffer sb = new StringBuffer();
		sb.append("select line_name aaa,trunc((line_supply_pq-line_spq)/line_supply_pq*100,2) bbb,read_succ_rate ccc from a_line_pq alp,g_line gl\n")
		.append("where alp.line_id=gl.line_id and (line_supply_pq-line_spq)/line_supply_pq*100>=20 and to_date(alp.stat_date,'yyyymmdd')=trunc(sysdate-1)");
		return this.getJdbcTemplate().query(sb.toString(),new MapperGsxl());  
	}
	class MapperGsxl implements RowMapper {	
		public Object mapRow(ResultSet rs,int paramInt)throws SQLException {
			LostMonitorBean2 te = new LostMonitorBean2();
		    try{
		    	String lost = rs.getString("bbb")+"%";
		    	String succRate = rs.getString("ccc")+"%";
		  	    te.setXlName(rs.getString("aaa"));
		  	    te.setXlLostRate(lost);
		  	    te.setXlSuccRate(succRate);
		    	return te;
		    }catch(Exception e){
		    	return null;
		    }
		}
	}
	
	public List<FailureStatistic> findFailureStat(){
		StringBuffer sb = new StringBuffer();
		sb.append("select (SELECT(COUNT(TERMINAL_ID))FROM I_TMNL_TASK WHERE TERMINAL_TYPE_CODE=1 AND TMNL_TASK_TYPE=1 AND CONS_CHG_TYPE=1 AND DEBUG_STATUS_CODE=-9 and itt.debug_time>=trunc(sysdate-30)) AAA,\n")
		  .append("       (SELECT(COUNT(TERMINAL_ID))FROM I_TMNL_TASK WHERE TERMINAL_TYPE_CODE=5 AND TMNL_TASK_TYPE=1 AND CONS_CHG_TYPE=1 AND DEBUG_STATUS_CODE=-9 and itt.debug_time>=trunc(sysdate-30)) BBB,\n")
		  .append("       (SELECT(COUNT(TERMINAL_ID))FROM I_TMNL_TASK WHERE TMNL_TASK_TYPE=3 AND DEBUG_STATUS_CODE=-9 and itt.debug_time>=trunc(sysdate-30))CCC,\n")
		  .append("       (SELECT(COUNT(TERMINAL_ID))FROM I_TMNL_TASK WHERE TMNL_TASK_TYPE=2 AND DEBUG_STATUS_CODE=-9 and itt.debug_time>=trunc(sysdate-30))DDD,\n")
		  .append("       (SELECT(COUNT(TERMINAL_ID))FROM I_TMNL_TASK WHERE TMNL_TASK_TYPE=2 AND CONS_CHG_TYPE=1 AND DEBUG_STATUS_CODE=-9 and itt.debug_time>=trunc(sysdate-30))EEE,\n")
		  .append("       (SELECT(COUNT(TERMINAL_ID))FROM I_TMNL_TASK WHERE TMNL_TASK_TYPE=3 AND CONS_CHG_TYPE=1 AND DEBUG_STATUS_CODE=-9 and itt.debug_time>=trunc(sysdate-30))FFF\n")
		  .append("from I_TMNL_TASK itt WHERE ROWNUM=1");
		
		return this.getJdbcTemplate().query(sb.toString(),new FailureStaticMapper());
	}
	class FailureStaticMapper implements RowMapper{
		public Object mapRow(ResultSet rs,int paramInt)throws SQLException{
			FailureStatistic fs = new FailureStatistic();
			try{
			fs.setFkTmnNewF(rs.getString("AAA"));
			fs.setJcTmnNewF(rs.getString("BBB"));
			fs.setTmnChangeF(rs.getString("CCC"));
			fs.setRecordSynF("0");
			fs.setTmnBackoutF(rs.getString("DDD"));
			fs.setBackoutAmmeterF(rs.getString("EEE"));
			fs.setChangeAmmeterF(rs.getString("FFF"));
			return fs;
			}catch(Exception e){
		    	return null;
		    }
		}
	}
	
	public List<ElecExcepBean> findStoreElecExcep(PSysUser userInfo){
		StringBuffer sb = new StringBuffer();
		List<String> paraList = new ArrayList<String>();
		sb.append("select vsac.alarm_code as aaa, vsac.alarm_name as bbb,tmp.alarmNum as ccc\n")
		.append("from (select asa.alarm_code as alarm_code, count(asa.tmnl_asset_no) as alarmNum \n")
		.append("from a_sea_alarm asa\n");
		
		if(!userInfo.getOrgNo().equals("63101")){
		sb.append("where asa.org_no like ?\n");
		String userOrgNo = userInfo.getOrgNo()+"%";
		paraList.add(userOrgNo);
		}
		sb.append("group by asa.alarm_code) tmp,vw_sea_alarm_code vsac\n");
		sb.append("where vsac.alarm_code = tmp.alarm_code\n");

		logger.debug(sb.toString());
		List<ElecExcepBean> eebList = this.getJdbcTemplate().query(sb.toString(),paraList.toArray(),new ElecExcepMapper());
		if(eebList.size()==0) {
			ExceptionChineseEnglishHashMap ecehm = new ExceptionChineseEnglishHashMap();   
			eebList.add(ecehm.getEec());
		}
		return eebList;
	}
	class ElecExcepMapper implements RowMapper{
		  public ElecExcepBean eec = new ElecExcepBean();
		  public ExceptionChineseEnglishHashMap ecehm = new ExceptionChineseEnglishHashMap();   
		  HashMap<String,String> hm = ecehm.getHm();
			public Object mapRow(ResultSet rs,int paramInt)throws SQLException {
			
			    try{		    
			    	String s1=rs.getString("aaa");
			    	String s2=rs.getString("bbb");
			    	String s3=rs.getString("ccc");	
			    
			    	hm.put(s1,s3);

                    eec=ecehm.getEec();
			    	
			    	return eec;
			    }catch(Exception e){
			    	return null;
			    }
			}
			
		}
	
}
