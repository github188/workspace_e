package com.nari.mydesk.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.mydesk.AOrgStatD;
import com.nari.mydesk.AOrgStatDManager;
import com.nari.mydesk.ElecOrderDTO;
import com.nari.mydesk.MainCurve;

public class AOrgStatDDaoImpl extends JdbcBaseDAOImpl implements AOrgStatDManager {
	Logger logger = Logger.getLogger(AOrgStatDDaoImpl.class);
	private  AOrgStatD  aOrgStatD;
	private MainCurve mainCurve;
	
	
	public MainCurve getMainCurve() {
		return mainCurve;
	}

	public void setMainCurve(MainCurve mainCurve) {
		this.mainCurve = mainCurve;
	}

	public AOrgStatD getaOrgStatD() {
		return aOrgStatD;
	}

	public void setaOrgStatD(AOrgStatD aOrgStatD) {
		this.aOrgStatD = aOrgStatD;
	}

	@Override
	/**
	 * 昨日电量分布查询
	 */
	public List<AOrgStatD> getAOrgStatDByOrgNo(String orgNo, String orgType) {
		if(orgType != null && orgType.equals("02")){
			orgType = "03";
		}

		StringBuffer sb=new StringBuffer();
		sb.append("select a.STAT_DATE,\n");
		sb.append("       SUM(a.PAP_E) PAP_E,\n");
		sb.append("       SUM(a.pap_e1) PAP_E1,\n");
		sb.append("       SUM(a.pap_e2) PAP_E2,\n");
		sb.append("       SUM(a.pap_e3) PAP_E3,\n");
		sb.append("       SUM(a.pap_e4) PAP_E4\n");
		sb.append("  from a_org_stat_d a, O_ORG o\n");
		sb.append(" where a.org_no = o.org_no\n");
		sb.append(" and a.ORG_TYPE = ? and a.STAT_DATE between trunc(sysdate-1) and trunc(sysdate-1)\n");
		sb.append(" GROUP BY a.STAT_DATE");
		String sql=sb.toString();
		logger.debug("昨日电量：" +sql);
		return (List<AOrgStatD>) super.getJdbcTemplate().query(sql,
				new Object[] {orgType}, new AOrgStatDByOrgNoRowMapper());
	}

	@Override
	public List<AOrgStatD> getAOrgStatDBystaffNo(String staffNo,String orgType) {

		StringBuffer sb=new StringBuffer();
		sb.append("SELECT *\n");
		sb.append("  FROM p_sys_user p, a_org_stat_d a\n");
		sb.append(" WHERE p.org_no = a.org_no and a.STAT_DATE between trunc(sysdate-1) and trunc(sysdate-1)\n");
		sb.append("   AND p.staff_no = ? AND a.ORG_TYPE = ?");
		String sql=sb.toString();
		logger.debug(sql);
		return (List<AOrgStatD>) super.getJdbcTemplate().query(sql,
				new Object[] {staffNo,orgType}, new AOrgStatDByOrgNoRowMapper());
	}
	
	public List<AOrgStatD> getAOrgStatDOrgType(String orgNo) {


		StringBuffer sb=new StringBuffer();
		sb.append("select O.ORG_TYPE, O.ORG_NO\n");
		sb.append("  from O_ORG O, p_sys_user p\n");
		sb.append(" WHERE O.ORG_NO = P.ORG_NO AND P.ORG_NO = ?");
		String sql=sb.toString();
		logger.debug(sql);
		return (List<AOrgStatD>) super.getJdbcTemplate().query(sql,
				new Object[] {orgNo}, new AOrgStatDOrgTypeRowMapper());
	}
	
	class AOrgStatDByOrgNoRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		AOrgStatD aorgstatd = new AOrgStatD();
		 try { 
		aorgstatd.setStatDate(rs.getDate("STAT_DATE"));
		aorgstatd.setPapE(rs.getDouble("PAP_E"));
		aorgstatd.setPapE1(rs.getDouble("PAP_E1"));
		aorgstatd.setPapE2(rs.getDouble("PAP_E2"));
		aorgstatd.setPapE3(rs.getDouble("PAP_E3"));
		aorgstatd.setPapE4(rs.getDouble("PAP_E4"));
		return aorgstatd;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}
	
	class AOrgStatDOrgTypeRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		AOrgStatD aorgstatd = new AOrgStatD();
		 try { 
		aorgstatd.setOrgNo(rs.getString("ORG_NO"));
		aorgstatd.setOrgType(rs.getString("ORG_TYPE"));
		return aorgstatd;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}
	
	class AOrgStatDByStaffRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		AOrgStatD aorgstatd = new AOrgStatD();
		 try { 
		aorgstatd.setEmpNo(rs.getString("EMP_NO"));
		aorgstatd.setStaffNo(rs.getString("STAFF_NO"));
		aorgstatd.setOrgNo(rs.getString("ORG_NO"));
		aorgstatd.setDeptNo(rs.getString("DEPT_NO"));
		aorgstatd.setName(rs.getString("NAME"));
		aorgstatd.setPwd(rs.getString("PWD"));
		aorgstatd.setIp(rs.getString("IP"));
		aorgstatd.setMac(rs.getString("MAC"));
		aorgstatd.setAccessLevel(rs.getString("ACCESS_LEVEL"));
		aorgstatd.setCurStatusCode(rs.getString("CUR_STATUS_CODE"));
		aorgstatd.setPwdRemindTime(rs.getDate("PWD_REMIND_TIME"));
		aorgstatd.setLockTime(rs.getDate("LOCK_TIME"));
		aorgstatd.setPlanUnlockTime(rs.getDate("PLAN_UNLOCK_TIME"));
		aorgstatd.setUnlockTime(rs.getDate("UNLOCK_TIME"));
		aorgstatd.setLockIp(rs.getString("LOCK_IP"));
		aorgstatd.setAutoUnlockFlag(rs.getString("AUTO_UNLOCK_FLAG"));
		aorgstatd.setLockReason(rs.getString("LOCK_REASON"));
		aorgstatd.setUnlockEmpNo(rs.getString("UNLOCK_EMP_NO"));
		aorgstatd.setStaffNo(rs.getString("STAFF_NO"));
		aorgstatd.setOrgNo(rs.getString("ORG_NO"));
		aorgstatd.setOrgType(rs.getString("ORG_TYPE"));
		return aorgstatd;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}

	/**
	 * 当日用电负荷监测
	 * @author zhaoliang
	 *
	 */
	class MainCurveRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		MainCurve maincurve = new MainCurve();
		 try { 
			 maincurve.setOrgName(rs.getString("ORG_NAME"));
		maincurve.setOrgNo(rs.getString("ORG_NO"));
		maincurve.setOrgType(rs.getString("ORG_TYPE"));
		maincurve.setConsType(rs.getByte("CONS_TYPE"));
		maincurve.setCurveDate(rs.getDate("CURVE_DATE"));
		maincurve.setTimePoint(rs.getString("TIME_POINT"));
		Double p = rs.getDouble("P");
		maincurve.setP(p/10000);
		maincurve.setCtrlP(rs.getDouble("CTRL_P"));
		return maincurve;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}
	
	public List<MainCurve> getMainCurve(String orgNo,String orgType){
		 if(orgType != null && orgType.equals("01")){
			orgType = "02";
		}else if(orgType != null && orgType.equals("02")){
			orgType = "03";
			//orgType = "04";//测试用，修改
		}else if(orgType != null && orgType.equals("03")){
			orgType = "04";
		}else if(orgType != null && orgType.equals("04")){
			orgType = "05";
		}else if(orgType != null && orgType.equals("05")){
			orgType = "06";
		}
		StringBuffer sb=new StringBuffer();
		sb.append("\n");
		sb.append("select a.ORG_NO,\n");
		sb.append("       o.ORG_NAME,\n");
		sb.append("       a.ORG_TYPE,\n");
		sb.append("       a.CONS_TYPE,\n");
		sb.append("       a.CURVE_DATE,\n");
		sb.append("       a.TIME_POINT,\n");
		sb.append("       a.P,\n");
		sb.append("       a.CTRL_P\n");
		sb.append("  from a_main_page_curve a, O_ORG o\n");
		sb.append(" where a.org_no = o.org_no\n");
		sb.append("   and a.CURVE_DATE between trunc(sysdate) and trunc(sysdate + 1)\n");
		sb.append("   and a.ORG_TYPE = ?\n");
		sb.append("   and to_date(a.TIME_POINT, 'hh24:mi:ss') <\n");
		sb.append("       TRUNC(SYSDATE,'HH') - 1 / 24\n");
		sb.append("");
		String sql=sb.toString();
		logger.debug(sql);
		return (List<MainCurve>) super.getJdbcTemplate().query(sql,
				new Object[] {orgType}, new MainCurveRowMapper());
	}
	
	


}
