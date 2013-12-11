package com.nari.qrystat.simquery.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.simquery.SIMOverTraffic;
import com.nari.qrystat.simquery.SIMStatDao;
import com.nari.qrystat.simquery.SimDetailFlowInfoBean;
import com.nari.qrystat.simquery.SimInstall;
import com.nari.qrystat.simquery.SimTrafficInfoBean;
import com.nari.support.Page;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;

public class SIMStatDaoImpl extends JdbcBaseDAOImpl implements SIMStatDao {

	@Override
	public Page<SimTrafficInfoBean> findSimTrafficInfo(PSysUser user, String dateFrom,
			String dateTo,String orgNo, long start, int limit) throws DBAccessException {
		String sql = 
			"SELECT T.ORG_NO,\n" +
			"       T.ORG_NAME,\n" + 
			"       ROUND(SUM(T.DOWN_FLOW)/(1024*1024),2) DOWN_FLOW,\n" + 
			"       SUM(T.DOWN_MSG) DOWN_MSG,\n" + 
			"       ROUND(SUM(T.UP_FLOW)/(1024*1024),2) UP_FLOW,\n" + 
			"       SUM(T.UP_MSG) UP_MSG,\n" + 
			"       ROUND(SUM(T.ALL_FLOW)/(1024*1024),2) ALL_FLOW,\n" + 
			"       ROUND(SUM(T.ALL_FLOW)/(1024*1024),2)- ROUND(SUM(T.OVER_FLOW)/(1024*1024),2) BASE_FLOW,\n" + 
			"       ROUND(SUM(T.OVER_FLOW)/(1024*1024),2) OVER_FLOW,\n" + 
			"       COUNT(*)*4 BASE_FEE,--基本费用（元）\n" + 
			"       SUM(T.OVER_FEE) OVER_FEE--超流量费（元）\n" + 
			"  FROM (SELECT O.ORG_NO,\n" + 
			"               O.ORG_NAME,\n" + 
			"               F.FLOW_DATE,\n" + 
			"               F.DOWN_FLOW,\n" + 
			"               F.DOWN_MSG,\n" + 
			"               F.UP_FLOW,\n" + 
			"               F.UP_MSG,\n" + 
			"               (F.DOWN_FLOW + F.UP_FLOW) ALL_FLOW, --全部流量（字节）\n" + 
			"case when  ((F.DOWN_FLOW + F.UP_FLOW) - 8 * 1024 * 1024)<0 then 0\n" +
			"         else  ((F.DOWN_FLOW + F.UP_FLOW) - 8 * 1024 * 1024) end OVER_FLOW, --超流量（字节）\n" + 
			"case when ROUND(((F.DOWN_FLOW + F.UP_FLOW) - 8 * 1024 * 1024) / 1024 * 0.001,2)<0 then 0\n" + 
			"         else ROUND(((F.DOWN_FLOW + F.UP_FLOW) - 8 * 1024 * 1024) / 1024 * 0.001,2) end OVER_FEE --超费用（元）\n"+
			"          FROM O_ORG O, A_TMNL_FLOW_M F\n" + 
			"         WHERE O.ORG_NO = F.ORG_NO\n" + 
			"           AND F.COMM_MODE = '02'\n" + 
			"           AND F.FLOW_DATE>= TO_DATE(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
			"           AND F.FLOW_DATE <= TO_DATE(?,'yyyy-mm-dd hh24:mi:ss')\n" +
			"           AND O.P_ORG_NO = ?) T\n" + 
			"GROUP BY T.ORG_NO,T.ORG_NAME\n" +
			"ORDER BY T.ORG_NO";
		if(orgNo==null || "".equals(orgNo)) {
			orgNo = user.getOrgNo();
		}
			return super.pagingFind(sql, start, limit,new simTrafficInfoMapper(), new Object[]{dateFrom,dateTo, orgNo});
	}

	@Override
	public Page<SimInstall> findSim(String psPart,String simNo, long start, int limit,
			PSysUser pSysUser) {
		Page<SimInstall> page = null;
		List<Object> args = new ArrayList<Object>();
		if(simNo==null) {
			simNo = "";
		}
		simNo += "%"; 
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DISTINCT O.ORG_NAME,\n");
		sb.append("                C.CONS_NO,\n");
		sb.append("                C.CONS_NAME,\n");
		sb.append("                V.SIM_NO,\n");
		sb.append("                V.TERMINAL_ADDR,\n");
		sb.append("                VF.FACTORY_NAME,\n");
		sb.append("                VM.MODE_NAME,\n");
		sb.append("                C.ELEC_ADDR,\n");
		sb.append("                A.IS_ONLINE\n");
		sb.append("  FROM VW_TMNL_RUN      V,\n");
		sb.append("       C_CONS           C,\n");
		sb.append("       VW_TMNL_FACTORY VF,\n");
		sb.append("       VW_TMNL_MODE_CODE VM,\n");
		sb.append("       A_TMNL_REAL_TIME A,\n");
		sb.append("       O_ORG            O\n");
		sb.append(" WHERE V.CONS_NO = C.CONS_NO\n");
		sb.append("   AND A.TMNL_ASSET_NO = V.TMNL_ASSET_NO\n");
		sb.append("   AND C.ORG_NO = O.ORG_NO\n");
		sb.append("   AND VF.FACTORY_CODE = V.FACTORY_CODE\n");
		sb.append("   AND VM.MODE_CODE = V.TERMINAL_TYPE_CODE\n");
		sb.append("   AND V.SIM_NO LIKE ?");
		args.add(simNo);
		String groupTableName = "";//群组表名，区别普通群组和控制群组
		String[] params = psPart.split("_");
		if(params[0].equals("ugp")){
			groupTableName = "       R_USER_GROUP_DETAIL GP,\n";
		} else if(params[0].equals("cgp")){
			groupTableName = "       T_TMNL_GROUP_DETAIL GP,\n";
		}
		if (params[0].equals("org")) {
			sb.append("   AND EXISTS (SELECT 1\n");
			sb.append("          FROM O_ORG OO\n");
			sb.append("         WHERE OO.ORG_NO = C.ORG_NO\n");
			sb.append("        CONNECT BY PRIOR OO.ORG_NO = OO.P_ORG_NO\n");
			sb.append("         START WITH OO.ORG_NO = ?)");
			args.add(params[1]);
		} else if (params[0].equals("sub")) {
			sb.append("   AND C.SUBS_ID=? ");
			args.add(params[1]);
		} else if(params[0].equals("line")){
			sb.append(" AND C.LINE_ID=? ");
			args.add(params[1]);
		} else if(params[0].equals("cgp")|| params[0].equals("ugp")){
			sb = new StringBuffer();
			sb.append("SELECT DISTINCT O.ORG_NAME,\n");
			sb.append("                C.CONS_NO,\n");
			sb.append("                C.CONS_NAME,\n");
			sb.append("                V.SIM_NO,\n");
			sb.append("                V.TERMINAL_ADDR,\n");
			sb.append("                C.ELEC_ADDR,\n");
			sb.append("                A.IS_ONLINE\n");
			sb.append("  FROM VW_TMNL_RUN      V,\n");
			sb.append("       C_CONS           C,\n");
			sb.append("       A_TMNL_REAL_TIME A,\n");
			sb.append(groupTableName);
			sb.append("       O_ORG            O\n");
			sb.append(" WHERE V.CONS_NO = C.CONS_NO\n");
			sb.append("   AND A.TMNL_ASSET_NO = V.TMNL_ASSET_NO\n");
			sb.append("   AND V.TMNL_ASSET_NO = GP.TMNL_ASSET_NO(+)");
			sb.append("   AND C.ORG_NO = O.ORG_NO\n");
			sb.append("   AND V.SIM_NO LIKE ?");
			sb.append("   and gp.group_no = ?");
			
			args = new ArrayList<Object>();
			args.add(simNo);
			args.add(params[1]);
		} else {
			return null;
		}

		sb.append(SysPrivilige.addPri(pSysUser, "C", "V", 7));
		args.add(pSysUser.getStaffNo());
		args.add(pSysUser.getStaffNo());
		args.add(pSysUser.getStaffNo());

		String sql = sb.toString();

		page = pagingFind(sql, start, limit, new SimInstallRowMapper(), args
				.toArray());
		return page;
	}

	@Override
	public Page<SIMOverTraffic> findSimOverTraffic(String psPart,
			String chargeDateFrom, String chargeDateTo, String runner,
			long start, int limit) {
		String sql =
			"SELECT F.ORG_NO,\n" +
			"		 TO_CHAR(F.FLOW_DATE,'yyyy-mm') FLOW_DATE,\n" +
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       T.SIM_NO,\n" + 
			"       T.CIS_ASSET_NO,\n" + 
			"       T.TERMINAL_ADDR,\n" + 
			"       TF.FACTORY_NAME,\n" + 
			"       ROUND((F.DOWN_FLOW + F.UP_FLOW)/(1024 * 1024),2) ALL_FLOW, --全部流量（M）\n" + 
			"       case when ROUND(((F.DOWN_FLOW + F.UP_FLOW)/(1024 * 1024) - 8),2)<0 then 0\n" + 
			"            else ROUND(((F.DOWN_FLOW + F.UP_FLOW)/(1024 * 1024) - 8),2) end OVER_FLOW, --超流量（M）\n" + 
			"       case when ROUND(((F.DOWN_FLOW + F.UP_FLOW) - 8 * 1024 * 1024) / 1024 * 0.001,2)<0 then 0\n" + 
			"            else ROUND(((F.DOWN_FLOW + F.UP_FLOW) - 8 * 1024 * 1024) / 1024 * 0.001,2) end OVER_FEE --超费用（元）\n" + 
			"  FROM C_CONS C, VW_TMNL_RUN T, A_TMNL_FLOW_M F, VW_TMNL_FACTORY TF\n" + 
			" WHERE C.CONS_NO = T.CONS_NO\n" + 
			"   AND T.TMNL_ASSET_NO = F.TMNL_ASSET_NO\n" + 
			"   AND F.COMM_MODE = '02'\n" + 
			"   AND T.FACTORY_CODE=TF.FACTORY_CODE\n" + 
			"   AND F.FLOW_DATE>= TO_DATE(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
			"   AND F.FLOW_DATE <= TO_DATE(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
			"   AND F.ORG_NO=?\n" + 
			" ORDER BY C.CONS_NO,F.FLOW_DATE";

		return super.pagingFind(sql, start, limit, new simOverTrafficMapper(), new Object[]{chargeDateFrom,chargeDateTo, psPart});
	}

	@Override
	public Page<SimDetailFlowInfoBean> findSimDetailFlowInfo(String simNo,
			Date chargeDateFrom, Date chargeDateTo, long start, int limit)
			throws DBAccessException {
		String sql = 
			"select t.sim_no,\n" +
			"       t.flow_date,\n" + 
			"       t.down_flow,\n" + 
			"       t.down_msg,\n" + 
			"       t.up_flow,\n" + 
			"       t.up_msg,\n" + 
			"       t.down_flow + t.up_flow as all_flow\n" + 
			"  from a_tmnl_flow_d t\n" + 
			" where t.sim_no = ?\n" + 
			"   and t.flow_date between ? and ?\n" +
			"   order by t.flow_date";
		return super.pagingFind(sql, start, limit, new simDetailFlowInfoMapper(), 
				new Object []{simNo, chargeDateFrom, chargeDateTo});
	}

	class SimInstallRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			SimInstall siminstall = new SimInstall();
			try {
				siminstall.setOrgName(rs.getString("ORG_NAME"));
				siminstall.setConsNo(rs.getString("CONS_NO"));
				siminstall.setConsName(rs.getString("CONS_NAME"));
				siminstall.setSimNo(rs.getString("SIM_NO"));
				siminstall.setFactoryName(rs.getString("FACTORY_NAME"));
				siminstall.setModeName(rs.getString("MODE_NAME"));
				siminstall.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				siminstall.setElecAddr(rs.getString("ELEC_ADDR"));
				siminstall.setIsOnline(rs.getByte("IS_ONLINE"));
				return siminstall;
			} catch (Exception e) {
				return null;
			}
		}
	}

	class simOverTrafficMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			SIMOverTraffic sot = new SIMOverTraffic();
			try {
				sot.setOrgNo(rs.getString("org_no"));
				sot.setFlowDate(rs.getString("flow_date"));
				sot.setConsNo(rs.getString("cons_no"));
				sot.setConsName(rs.getString("cons_name"));
				sot.setSimNo(rs.getString("sim_no"));
				sot.setCisAssetNo(rs.getString("cis_asset_no"));
				sot.setTerminalAddr(rs.getString("terminal_addr"));
				sot.setFactoryName(rs.getString("factory_name"));
				sot.setAllFlow(rs.getFloat("all_flow"));
				sot.setOverFlow(rs.getFloat("over_flow"));
				sot.setOverFee(rs.getFloat("over_fee"));
				return sot;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	//sim卡流量信息Mapper
	class simDetailFlowInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			SimDetailFlowInfoBean sot = new SimDetailFlowInfoBean();
			try {
				sot.setAllFlow(rs.getString("all_flow"));
				sot.setDownFlow(rs.getString("down_flow"));
				sot.setDownMsg(rs.getString("down_msg"));
				sot.setFlowDate(rs.getDate("flow_date"));
				sot.setUpFlow(rs.getString("up_flow"));
				sot.setUpMsg(rs.getString("up_msg"));
				sot.setSimNo(rs.getString("sim_no"));
				return sot;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	//SIM卡流量分析Mapper
	class simTrafficInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			SimTrafficInfoBean sot = new SimTrafficInfoBean();
			try {
				sot.setAllFlow(rs.getFloat("all_flow"));
				sot.setBaseFlow(rs.getFloat("base_flow"));
				sot.setOverFlow(rs.getFloat("over_flow"));
				sot.setDownFlow(rs.getFloat("down_flow"));
				sot.setDownMsg(rs.getString("down_msg"));
				sot.setUpFlow(rs.getFloat("up_flow"));
				sot.setUpMsg(rs.getString("up_msg"));
				sot.setBaseFee(rs.getFloat("base_fee"));
				sot.setOverFee(rs.getFloat("over_fee"));
				sot.setOrgName(rs.getString("org_name"));
				sot.setOrgNo(rs.getString("org_no"));
				return sot;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
