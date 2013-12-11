package com.nari.baseapp.prepaidman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.prepaidman.IPrePaidStatDao;
import com.nari.baseapp.prepaidman.PrePaidCtrlStatBean;
import com.nari.baseapp.prepaidman.PrePaidExecStatBean;
import com.nari.baseapp.prepaidman.TmnlPrePaidQueryBean;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class PrePaidStatDaoImpl extends JdbcBaseDAOImpl implements IPrePaidStatDao {

	/**
	 * 统计预付费控制类别
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PrePaidCtrlStatBean> contrlTypeStat(String orgNo,String orgType,String startDate,
			String endDate) throws DBAccessException {
		 List<PrePaidCtrlStatBean>  prePaidCtrlStatBeanList =new ArrayList<PrePaidCtrlStatBean>();
		 List<Object> list = new ArrayList<Object>();
		 StringBuffer sb= new StringBuffer();
		 sb.append("SELECT MAX(?) ORG_NO,\n")
			 .append("       MAX((SELECT ORG_NAME FROM O_ORG WHERE ORG_NO = ?)) ORG_NAME,\n") 
			 .append("       SUM(TAB.TERMINAL) TMNL_PREPAID_NUM,\n")
			 .append("       SUM(TAB.STATION) STATION_PREPAID_NUM,\n") 
			 .append("       SUM(TAB.METER) METER_PREPAID_NUM\n") 
			 .append("  FROM (SELECT CASE\n") 
			 .append("                 WHEN CS.BUSI_TYPE = '01' THEN\n") 
			 .append("                  CS.SUCC_POWERCUT_CNT + CS.FAIL_POWERCUT_CNT +\n") 
			 .append("                  CS.SUCC_POWERRES_CNT + CS.FAIL_POWERRES_CNT +\n") 
			 .append("                  CS.SUCC_URGEFEE_CNT + CS.FAIL_URGEFEE_CNT\n") 
			 .append("                 ELSE\n") 
			 .append("                  0\n") 
			 .append("               END TERMINAL,\n") 
			 .append("               CASE\n") 
			 .append("                 WHEN CS.BUSI_TYPE = '03' THEN\n") 
			 .append("                  CS.SUCC_POWERCUT_CNT + CS.FAIL_POWERCUT_CNT +\n") 
			 .append("                  CS.SUCC_POWERRES_CNT + CS.FAIL_POWERRES_CNT +\n") 
			 .append("                  CS.SUCC_URGEFEE_CNT + CS.FAIL_URGEFEE_CNT\n") 
			 .append("                 ELSE\n") 
			 .append("                  0\n") 
			 .append("               END STATION,\n") 
			 .append("               CASE\n") 
			 .append("                 WHEN CS.BUSI_TYPE = '02' THEN\n") 
			 .append("                  CS.SUCC_POWERCUT_CNT + CS.FAIL_POWERCUT_CNT +\n") 
			 .append("                  CS.SUCC_POWERRES_CNT + CS.FAIL_POWERRES_CNT +\n") 
			 .append("                  CS.SUCC_URGEFEE_CNT + CS.FAIL_URGEFEE_CNT\n") 
			 .append("                 ELSE\n") 
			 .append("                  0\n") 
			 .append("               END METER\n") 
			 .append("          FROM A_FEECTRL_STAT_D CS\n"); 
			 list.add(orgNo);
			 list.add(orgNo);
			 String sqlwhere;
			 if("03".equals(orgType)){
				 sqlwhere ="         WHERE (CS.ORG_NO = ? OR  CS.ORG_NO IN (SELECT O.ORG_NO FROM O_ORG O WHERE O.P_ORG_NO = ?))\n";
				 list.add(orgNo);
				 list.add(orgNo);
			 }
			 else if("04".equals(orgType)){
				 sqlwhere ="         WHERE CS.ORG_NO = ?\n";
				 list.add(orgNo);
			 }
			 else 
				 return prePaidCtrlStatBeanList;
			 sb.append(sqlwhere) 
				.append("            AND CS.STAT_DATE >= TO_DATE(?, 'yyyy-mm-dd')\n") 
				.append("            AND CS.STAT_DATE < TO_DATE(?, 'yyyy-mm-dd')+1) TAB\n") 
				.append("HAVING MAX(?) IS NOT NULL");
			 list.add(startDate);
			 list.add(endDate);
			 list.add(orgNo);
		//System.out.println(sb.toString());
        try{
        	prePaidCtrlStatBeanList=this.getJdbcTemplate().query(
					sb.toString(),
					list.toArray(),
					new ContrlStatRowMapper());
        	return  prePaidCtrlStatBeanList;
        }catch(RuntimeException e){
			this.logger.debug("统计预付费控制类别出错！");
			throw e;
	   }
	}
	
	/**
	 * 统计预付费执行情况
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PrePaidExecStatBean> executeStat(String orgNo,String orgType,String startDate,String endDate)throws DBAccessException{
		List<PrePaidExecStatBean> prePaidExecStatBeanList =new ArrayList<PrePaidExecStatBean>();
		List<Object> list = new ArrayList<Object>();
		StringBuffer sb= new StringBuffer();
        sb.append("SELECT  MAX(?) ORG_TYPE,\n")
        	.append("       MAX(?) ORG_NO,\n")
			.append("       MAX((SELECT ORG_NAME FROM O_ORG WHERE ORG_NO = ?)) ORG_NAME,\n") 
			.append("       SUM(CE.SUCC_FEE_CNT) SUCC_FEE_CNT,\n") 
			.append("       SUM(CE.FAIL_FEE_CNT) FAIL_FEE_CNT,\n") 
			.append("       SUM(CE.FAIL_CIS_CNT + CE.NOT_CIS_CNT + CE.ACT_CTRL_CNT +\n") 
			.append("           CE.WAITACT_CTRL_CNT) UNCOMPLETE_FEE_CNT,\n") 
			.append("       ROUND(SUM(CE.SUCC_FEE_CNT) * 100 /\n") 
			.append("             SUM(CE.SUCC_FEE_CNT + CE.FAIL_FEE_CNT + CE.ACT_CTRL_CNT +\n") 
			.append("                 CE.WAITACT_CTRL_CNT),\n") 
			.append("             2) SUCC_RATIO\n") 
			.append("  FROM A_FEECTRL_EXEC_D CE\n"); 
         list.add(orgType);
         list.add(orgNo);
		 list.add(orgNo);
		 if("03".equals(orgType)){
			 sb.append(" WHERE (CE.ORG_NO = ? OR CE.ORG_NO IN (SELECT O.ORG_NO FROM O_ORG O WHERE O.P_ORG_NO = ?))\n");
			 list.add(orgNo);
			 list.add(orgNo);
		 }
		 else if("04".equals(orgType)){
			 sb.append(" WHERE CE.ORG_NO = ?\n");
			 list.add(orgNo);
		 }
		 else 
			 return prePaidExecStatBeanList;
	     sb.append("   AND CE.STAT_DATE >= TO_DATE(?,'yyyy-mm-dd')\n") 
		   .append("   AND CE.STAT_DATE < TO_DATE(?,'yyyy-mm-dd')+1\n")
		   .append("HAVING MAX(?) IS NOT NULL");
		 list.add(startDate);
		 list.add(endDate);
		 list.add(orgNo);
		//System.out.println(sb.toString());
		try{
			prePaidExecStatBeanList=this.getJdbcTemplate().query(
						sb.toString(),
						list.toArray(),
						new ExecStatRowMapper());
			return prePaidExecStatBeanList;
        }catch(RuntimeException e){
			this.logger.debug("统计预付费执行出错！");
			throw e;
	   }
	}
	
	/**
	 * 查询预付费执行情况明细
	 * @param orgNo
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @param execStatus
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	public Page<TmnlPrePaidQueryBean> executeStatQuery(String orgNo,
			String orgType, String startDate, String endDate,
			Integer execStatus, long start, long limit)
			throws DBAccessException {
		  Page<TmnlPrePaidQueryBean> tmnlPrePaidQueryBeanPage=new  Page<TmnlPrePaidQueryBean>(); 
		  List<Object> list = new ArrayList<Object>();
		  StringBuffer sb= new StringBuffer();
          sb.append("SELECT O.ORG_NAME,\n")
			.append("       FC.BUY_ORDER,\n") 
			.append("       C.CONS_NO,\n") 
			.append("       C.CONS_NAME,\n") 
			.append("       R.TERMINAL_ADDR,\n") 
			.append("       FC.TOTAL_NO,\n") 
			.append("       D.ASSET_NO,\n") 
			.append("       FC.SEND_TIME,\n") 
			.append("       FC.STATUS_CODE\n") 
			.append("  FROM W_FEECTRL FC, O_ORG O, C_CONS C, R_TMNL_RUN R, D_METER D\n") 
			.append(" WHERE O.ORG_NO = FC.ORG_NO\n") 
			.append("   AND C.CONS_NO = FC.CONS_NO\n") 
			.append("   AND R.TMNL_ASSET_NO = FC.TMNL_ASSET_NO\n") 
			.append("   AND D.METER_ID(+) = FC.METER_ID\n") 
			.append("   AND FC.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n") 
			.append("   AND FC.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd') + 1\n");
          list.add(startDate);
          list.add(endDate);
          if("03".equals(orgType)){
        	  sb.append(" AND (FC.ORG_NO = ? OR FC.ORG_NO IN (SELECT ORG.ORG_NO FROM O_ORG ORG WHERE ORG.P_ORG_NO = ?))\n");
        	  list.add(orgNo);
        	  list.add(orgNo);
          }
          else if("04".equals(orgType)){
        	  sb.append(" AND FC.ORG_NO = ?\n");
        	  list.add(orgNo);
          }
          else
        	  return tmnlPrePaidQueryBeanPage;
          if(1==execStatus)
        	  sb.append("AND FC.STATUS_CODE = '14'");
          else if(2==execStatus)  
        	  sb.append("AND (FC.STATUS_CODE NOT IN ('01','02','03','07','08','11','14') OR FC.STATUS_CODE IS NULL)");
          else if(3==execStatus)  
        	  sb.append("AND FC.STATUS_CODE IN ('01', '02', '03', '07', '08', '11')");
          else
        	  return tmnlPrePaidQueryBeanPage;
          try{
        	  tmnlPrePaidQueryBeanPage=super.pagingFind(sb.toString(), start, limit,new ExecuteStatQueryRowMapper(),list.toArray());
        	  return tmnlPrePaidQueryBeanPage;
		  }catch(RuntimeException e){
				this.logger.debug("查询预付费执行情况明细！");
				throw e;
		  }
	}
	
	class ContrlStatRowMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			PrePaidCtrlStatBean prePaidCtrlStatBean=new PrePaidCtrlStatBean();
			try {
				prePaidCtrlStatBean.setOrgNo(rs.getString("ORG_NO"));
				prePaidCtrlStatBean.setOrgName(rs.getString("ORG_NAME"));
				prePaidCtrlStatBean.setStationPrePaidNum(rs.getLong("STATION_PREPAID_NUM"));
				prePaidCtrlStatBean.setTmnlPrePaidNum(rs.getLong("TMNL_PREPAID_NUM"));
				prePaidCtrlStatBean.setMeterPrePaidNum(rs.getLong("METER_PREPAID_NUM"));
				prePaidCtrlStatBean.setPrePaidNum(rs
						.getLong("STATION_PREPAID_NUM")
						+ rs.getLong("TMNL_PREPAID_NUM")
						+ rs.getLong("METER_PREPAID_NUM"));
				return prePaidCtrlStatBean;
			}catch (Exception e) {
				return null;
			}
		}
	}
	
	class ExecStatRowMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			PrePaidExecStatBean prePaidExecStatBean=new PrePaidExecStatBean();
			try {
				prePaidExecStatBean.setOrgType(rs.getString("ORG_TYPE"));
				prePaidExecStatBean.setOrgNo(rs.getString("ORG_NO"));
				prePaidExecStatBean.setOrgName(rs.getString("ORG_NAME"));
				prePaidExecStatBean.setSuccFeeCnt(rs.getLong("SUCC_FEE_CNT"));
				prePaidExecStatBean.setFailFeeCnt(rs.getLong("FAIL_FEE_CNT"));
				prePaidExecStatBean.setUncompleteFeeCnt(rs.getLong("UNCOMPLETE_FEE_CNT"));
				/*if(0!=(rs.getLong("SUCC_FEE_CNT")+rs.getLong("FAIL_FEE_CNT"))){
					float f=rs.getLong("SUCC_FEE_CNT")*100/(rs.getLong("SUCC_FEE_CNT") + rs.getLong("FAIL_FEE_CNT"));
					if(String.valueOf(f).length()<String.valueOf(f).indexOf(".")+3)
						prePaidExecStatBean.setSuccRatio(String.valueOf(f));
					else	
					    prePaidExecStatBean.setSuccRatio(String.valueOf(f).substring(0,String.valueOf(f).indexOf(".")+3));
				}*/
				prePaidExecStatBean.setSuccRatio(rs.getFloat("SUCC_RATIO"));
				return prePaidExecStatBean;
			}catch (Exception e) {
				return null;
			}
		}
	}
	
	class ExecuteStatQueryRowMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			try{
				SimpleDateFormat  sdf =new  SimpleDateFormat("yyyy-MM-dd");
				TmnlPrePaidQueryBean tmnlPrePaidQueryBean = new TmnlPrePaidQueryBean();
				tmnlPrePaidQueryBean.setOrgName(rs.getString("ORG_NAME"));
				tmnlPrePaidQueryBean.setAppNo(rs.getString("BUY_ORDER"));
				tmnlPrePaidQueryBean.setConsNo(rs.getString("CONS_NO"));
				tmnlPrePaidQueryBean.setConsName(rs.getString("CONS_NAME"));
				tmnlPrePaidQueryBean.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				if(null!=rs.getString("TOTAL_NO")&&!"".equals(rs.getString("TOTAL_NO")))
					tmnlPrePaidQueryBean.setTotalNo(rs.getShort("TOTAL_NO"));
				if(null!=rs.getString("SEND_TIME")&&!"".equals(rs.getString("SEND_TIME")))
					tmnlPrePaidQueryBean.setExecuteDate(sdf.format(rs.getDate("SEND_TIME")));
				if("14".equals(rs.getString("STATUS_CODE")))
					tmnlPrePaidQueryBean.setExecuteStatus("成功");
				else if ("01".equals(rs.getString("STATUS_CODE"))
						|| "02".equals(rs.getString("STATUS_CODE"))
						|| "03".equals(rs.getString("STATUS_CODE"))
						|| "07".equals(rs.getString("STATUS_CODE"))
						|| "08".equals(rs.getString("STATUS_CODE"))
						|| "11".equals(rs.getString("STATUS_CODE")))
					tmnlPrePaidQueryBean.setExecuteStatus("未完成");
				else
					tmnlPrePaidQueryBean.setExecuteStatus("失败");
				return tmnlPrePaidQueryBean;
			}catch (Exception e) {
				return null;
			}
		}
	}
}
