package com.nari.baseapp.prepaidman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.prepaidman.BuyExecStatusStatBean;
import com.nari.baseapp.prepaidman.IPrePaidStatusDao;
import com.nari.baseapp.prepaidman.PrePaidCtrlExecStatBean;
import com.nari.baseapp.prepaidman.TmnlPrePaidQueryBean;
import com.nari.baseapp.prepaidman.UrgeFeeBean;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class PrePaidStatusDaoImpl extends JdbcBaseDAOImpl implements IPrePaidStatusDao {

	/**
	 * 当日预付费控制执行统计(按地市）
	 * @param orgNo
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PrePaidCtrlExecStatBean> todayCtrlExecStatByCity(String orgNo,String ctrlDate)
			throws DBAccessException {
		    StringBuffer sb= new StringBuffer();
		    sb.append("SELECT MAX('04') ORG_TYPE,\n")
			  .append("       ORG.ORG_NO,\n") 
			  .append("       ORG.ORG_NAME,\n") 
			  .append("       SUM(TAB.CUIFEISUC) + SUM(TAB.CUIFEIFAIL) + SUM(TAB.TINGDIANSUC) +\n") 
			  .append("       SUM(TAB.TINGDIANFAIL) + SUM(TAB.FUDIANSUC) + SUM(TAB.FUDIANFAIL) PREPAID_CTRL_NUM,\n") 
			  .append("       SUM(TAB.TINGDIANSUC) SUCC_POWEROFF_CNT,\n") 
			  .append("       SUM(TAB.TINGDIANFAIL) FAIL_POWEROFF_CNT,\n") 
			  .append("       SUM(TAB.FUDIANSUC) SUCC_POWERRES_CNT,\n") 
			  .append("       SUM(TAB.FUDIANFAIL) FAIL_POWERRES_CNT,\n") 
			  .append("       SUM(TAB.CUIFEISUC) SUCC_URGEFEE_CNT,\n") 
			  .append("       SUM(TAB.CUIFEIFAIL) FAIL_URGEFEE_CNT\n") 
			  .append("  FROM (SELECT UFP.ORG_NO,\n") 
			  .append("               CASE\n") 
			  .append("                 WHEN UFP.CTRL_FLAG = 2 AND UFP.STATUS_CODE = 3 THEN\n") 
			  .append("                  1\n") 
			  .append("                 ELSE\n") 
			  .append("                  0\n") 
			  .append("               END TINGDIANSUC,\n") 
			  .append("               CASE\n") 
			  .append("                 WHEN UFP.CTRL_FLAG = 2 AND\n") 
			  .append("                      (UFP.STATUS_CODE <> 3 OR UFP.STATUS_CODE IS NULL) THEN\n") 
			  .append("                  1\n") 
			  .append("                 ELSE\n") 
			  .append("                  0\n") 
			  .append("               END TINGDIANFAIL,\n") 
			  .append("               CASE\n") 
			  .append("                 WHEN UFP.CTRL_FLAG = 3 AND UFP.STATUS_CODE = 3 THEN\n") 
			  .append("                  1\n") 
			  .append("                 ELSE\n") 
			  .append("                  0\n") 
			  .append("               END FUDIANSUC,\n") 
			  .append("               CASE\n") 
			  .append("                 WHEN UFP.CTRL_FLAG = 3 AND\n") 
			  .append("                      (UFP.STATUS_CODE <> 3 OR UFP.STATUS_CODE IS NULL) THEN\n") 
			  .append("                  1\n") 
			  .append("                 ELSE\n") 
			  .append("                  0\n") 
			  .append("               END FUDIANFAIL,\n") 
			  .append("               CASE\n") 
			  .append("                 WHEN UFP.CTRL_FLAG IN (0, 1) AND UFP.STATUS_CODE = 3 THEN\n") 
			  .append("                  1\n") 
			  .append("                 ELSE\n") 
			  .append("                  0\n") 
			  .append("               END CUIFEISUC,\n") 
			  .append("               CASE\n") 
			  .append("                 WHEN UFP.CTRL_FLAG IN (0, 1) AND\n") 
			  .append("                      (UFP.STATUS_CODE <> 3 OR UFP.STATUS_CODE IS NULL) THEN\n") 
			  .append("                  1\n") 
			  .append("                 ELSE\n") 
			  .append("                  0\n") 
			  .append("               END CUIFEIFAIL,\n") 
			  .append("               UFP.STATUS_CODE\n") 
			  .append("          FROM W_URGEFEE_PARAM UFP\n") 
			  .append("         WHERE UFP.ORG_NO IN\n") 
			  .append("               (SELECT O.ORG_NO FROM O_ORG O WHERE O.P_ORG_NO = ?)\n") 
			  .append("          AND UFP.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n") 
			  .append("          AND UFP.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd') + 1) TAB,\n") 
			  .append("       O_ORG ORG\n") 
			  .append(" WHERE TAB.ORG_NO = ORG.ORG_NO\n") 
			  .append(" GROUP BY ORG.ORG_NO, ORG.ORG_NAME\n") 
			  .append("UNION ALL\n") 
			  .append("SELECT MAX('03') ORG_TYPE,\n") 
			  .append("       MAX(?) ORG_NO,\n") 
			  .append("       MAX((SELECT ORG_NAME FROM O_ORG WHERE ORG_NO = ?)) ORG_NAME,\n") 
			  .append("       SUM(TAB.CUIFEISUC) + SUM(TAB.CUIFEIFAIL) + SUM(TAB.TINGDIANSUC) +\n") 
			  .append("       SUM(TAB.TINGDIANFAIL) + SUM(TAB.FUDIANSUC) + SUM(TAB.FUDIANFAIL) PREPAID_CTRL_NUM,\n") 
			  .append("       SUM(TAB.TINGDIANSUC) SUCC_POWEROFF_CNT,\n") 
			  .append("       SUM(TAB.TINGDIANFAIL) FAIL_POWEROFF_CNT,\n") 
			  .append("       SUM(TAB.FUDIANSUC) SUCC_POWERRES_CNT,\n") 
			  .append("       SUM(TAB.FUDIANFAIL) FAIL_POWERRES_CNT,\n") 
			  .append("       SUM(TAB.CUIFEISUC) SUCC_URGEFEE_CNT,\n") 
			  .append("       SUM(TAB.CUIFEIFAIL) FAIL_URGEFEE_CNT\n") 
			  .append("  FROM (SELECT  CASE\n") 
			  .append("                 WHEN UFP.CTRL_FLAG IN (0, 1) AND UFP.STATUS_CODE = 3 THEN\n") 
			  .append("                  1\n") 
			  .append("                 ELSE\n") 
			  .append("                  0\n") 
			  .append("               END CUIFEISUC,\n") 
			  .append("               CASE\n") 
			  .append("                 WHEN UFP.CTRL_FLAG IN (0, 1) AND\n") 
			  .append("                      (UFP.STATUS_CODE <> 3 OR UFP.STATUS_CODE IS NULL) THEN\n") 
			  .append("                  1\n") 
			  .append("                 ELSE\n") 
			  .append("                  0\n") 
			  .append("               END CUIFEIFAIL,\n") 
			  .append("               CASE\n") 
			  .append("                 WHEN UFP.CTRL_FLAG = 2 AND UFP.STATUS_CODE = 3 THEN\n") 
			  .append("                  1\n") 
			  .append("                 ELSE\n") 
			  .append("                  0\n") 
			  .append("               END TINGDIANSUC,\n") 
			  .append("               CASE\n") 
			  .append("                 WHEN UFP.CTRL_FLAG = 2 AND\n") 
			  .append("                      (UFP.STATUS_CODE <> 3 OR UFP.STATUS_CODE IS NULL) THEN\n") 
			  .append("                  1\n") 
			  .append("                 ELSE\n") 
			  .append("                  0\n") 
			  .append("               END TINGDIANFAIL,\n") 
			  .append("               CASE\n") 
			  .append("                 WHEN UFP.CTRL_FLAG = 3 AND UFP.STATUS_CODE = 3 THEN\n") 
			  .append("                  1\n") 
			  .append("                 ELSE\n") 
			  .append("                  0\n") 
			  .append("               END FUDIANSUC,\n") 
			  .append("               CASE\n") 
			  .append("                 WHEN UFP.CTRL_FLAG = 3 AND\n") 
			  .append("                      (UFP.STATUS_CODE <> 3 OR UFP.STATUS_CODE IS NULL) THEN\n") 
			  .append("                  1\n") 
			  .append("                 ELSE\n") 
			  .append("                  0\n") 
			  .append("               END FUDIANFAIL,\n") 
			  .append("               UFP.STATUS_CODE\n") 
			  .append("          FROM W_URGEFEE_PARAM UFP\n") 
			  .append("         WHERE (UFP.ORG_NO IN\n") 
			  .append("               (SELECT O.ORG_NO FROM O_ORG O WHERE O.P_ORG_NO = ?) OR\n") 
			  .append("               UFP.ORG_NO = ?)\n") 
			  .append("           AND UFP.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n") 
			  .append("           AND UFP.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd') + 1) TAB\n") 
		  	  .append("           HAVING MAX('03') IS NOT NULL\n");
		  //System.out.println(sb.toString());
		  try{
			return this.getJdbcTemplate().query(
							sb.toString(),
							new Object[] { orgNo, ctrlDate, ctrlDate, orgNo, orgNo,
							orgNo, orgNo, ctrlDate, ctrlDate },
							new TodayCtrlExecStatRowMapper());
	        }catch(RuntimeException e){
				this.logger.debug("统计当日预付费控制执行出错！");
				throw e;
		   }
	}
	
	/**
	 * 当日预付费控制执行统计(按区县）
	 * @param orgNo
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PrePaidCtrlExecStatBean> todayCtrlExecStatByDistrict(String orgNo,String ctrlDate)
			throws DBAccessException {
		StringBuffer sb= new StringBuffer();
	    sb.append("SELECT MAX('04') ORG_TYPE,\n")
		  .append("       MAX(?) ORG_NO,\n") 
		  .append("       MAX((SELECT ORG_NAME FROM O_ORG WHERE ORG_NO = ?)) ORG_NAME,\n") 
		  .append("       SUM(TAB.CUIFEISUC) + SUM(TAB.CUIFEIFAIL) + SUM(TAB.TINGDIANSUC) +\n") 
		  .append("       SUM(TAB.TINGDIANFAIL) + SUM(TAB.FUDIANSUC) + SUM(TAB.FUDIANFAIL) PREPAID_CTRL_NUM,\n") 
		  .append("       SUM(TAB.TINGDIANSUC) SUCC_POWEROFF_CNT,\n") 
		  .append("       SUM(TAB.TINGDIANFAIL) FAIL_POWEROFF_CNT,\n") 
		  .append("       SUM(TAB.FUDIANSUC) SUCC_POWERRES_CNT,\n") 
		  .append("       SUM(TAB.FUDIANFAIL) FAIL_POWERRES_CNT,\n") 
		  .append("       SUM(TAB.CUIFEISUC) SUCC_URGEFEE_CNT,\n") 
		  .append("       SUM(TAB.CUIFEIFAIL) FAIL_URGEFEE_CNT\n") 
		  .append("  FROM (SELECT UFP.ORG_NO,\n") 
		  .append("               CASE\n") 
		  .append("                 WHEN UFP.CTRL_FLAG = 2 AND UFP.STATUS_CODE = 3 THEN\n") 
		  .append("                  1\n") 
		  .append("                 ELSE\n") 
		  .append("                  0\n") 
		  .append("               END TINGDIANSUC,\n") 
		  .append("               CASE\n") 
		  .append("                 WHEN UFP.CTRL_FLAG = 2 AND\n") 
		  .append("                      (UFP.STATUS_CODE <> 3 OR UFP.STATUS_CODE IS NULL) THEN\n") 
		  .append("                  1\n") 
		  .append("                 ELSE\n") 
		  .append("                  0\n") 
		  .append("               END TINGDIANFAIL,\n") 
		  .append("               CASE\n") 
		  .append("                 WHEN UFP.CTRL_FLAG = 3 AND UFP.STATUS_CODE = 3 THEN\n") 
		  .append("                  1\n") 
		  .append("                 ELSE\n") 
		  .append("                  0\n") 
		  .append("               END FUDIANSUC,\n") 
		  .append("               CASE\n") 
		  .append("                 WHEN UFP.CTRL_FLAG = 3 AND\n") 
		  .append("                      (UFP.STATUS_CODE <> 3 OR UFP.STATUS_CODE IS NULL) THEN\n") 
		  .append("                  1\n") 
		  .append("                 ELSE\n") 
		  .append("                  0\n") 
		  .append("               END FUDIANFAIL,\n") 
		  .append("               CASE\n") 
		  .append("                 WHEN UFP.CTRL_FLAG IN (0, 1) AND UFP.STATUS_CODE = 3 THEN\n") 
		  .append("                  1\n") 
		  .append("                 ELSE\n") 
		  .append("                  0\n") 
		  .append("               END CUIFEISUC,\n") 
		  .append("               CASE\n") 
		  .append("                 WHEN UFP.CTRL_FLAG IN (0, 1) AND\n") 
		  .append("                      (UFP.STATUS_CODE <> 3 OR UFP.STATUS_CODE IS NULL) THEN\n") 
		  .append("                  1\n") 
		  .append("                 ELSE\n") 
		  .append("                  0\n") 
		  .append("               END CUIFEIFAIL,\n") 
		  .append("               UFP.STATUS_CODE\n") 
		  .append("          FROM W_URGEFEE_PARAM UFP\n") 
		  .append("         WHERE UFP.ORG_NO  = ?\n") 
		  .append("           AND UFP.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n") 
		  .append("           AND UFP.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd') + 1) TAB\n") 
		  .append("         HAVING MAX('04') IS NOT NULL\n");
	   try{
			return this.getJdbcTemplate().query(
							sb.toString(),
							new Object[] { orgNo, orgNo, orgNo, ctrlDate, ctrlDate},
							new TodayCtrlExecStatRowMapper());
	        }catch(RuntimeException e){
				this.logger.debug("统计当日预付费控制执行出错！");
				throw e;
		 }
	}
	
	/**
	 * 当日终端预购电执行情况统计（地市）
	 * @param orgNo
	 * @param busiType
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<BuyExecStatusStatBean> todayTmnlPrePaidExecStatusStatByCity(String orgNo,String execDate) throws DBAccessException {
		try{
			List<BuyExecStatusStatBean> buyExecStatusStatBeanList1= new  ArrayList<BuyExecStatusStatBean>();
			StringBuffer sb1= new StringBuffer();
	        sb1.append("SELECT MAX('04') ORG_TYPE,\n")
				.append("       ORG.ORG_NO,\n") 
				.append("       ORG.ORG_NAME,\n") 
				.append("       SUM(TAB.SUCCFEE) SUCC_FEE_CNT,\n") 
				.append("       SUM(TAB.SUCCRE) SUCC_RE_CNT,\n") 
				.append("       SUM(TAB.FAILFEE) FAIL_FEE_CNT,\n") 
				.append("       SUM(TAB.FAILRE) FAIL_RE_CNT,\n") 
				.append("       SUM(TAB.NOTRE) NOT_RE_CNT,\n") 
				.append("       SUM(TAB.ACTCTRL) ACC_CTRL_CNT\n") 
				.append("  FROM (SELECT FC.ORG_NO,\n") 
				.append("               CASE\n") 
				.append("                 WHEN WFF.FLOW_NO_CODE IN (15, 23) AND\n") 
				.append("                      WFF.FLOW_NODE_STATUS = 1 THEN\n") 
				.append("                  1\n") 
				.append("                 ELSE\n") 
				.append("                  0\n") 
				.append("               END SUCCFEE,\n") 
				.append("               CASE\n") 
				.append("                 WHEN WFF.FLOW_NO_CODE IN (16, 25) AND\n") 
				.append("                      WFF.FLOW_NODE_STATUS = 1 THEN\n") 
				.append("                  1\n") 
				.append("                 ELSE\n") 
				.append("                  0\n") 
				.append("               END SUCCRE,\n") 
				.append("               CASE\n") 
				.append("                 WHEN WFF.FLOW_NO_CODE IN (13, 14, 15, 23, 24) AND\n") 
				.append("                      WFF.FLOW_NODE_STATUS = 2 THEN\n") 
				.append("                  1\n") 
				.append("                 ELSE\n") 
				.append("                  0\n") 
				.append("               END FAILFEE,\n") 
				.append("               CASE\n") 
				.append("                 WHEN WFF.FLOW_NO_CODE IN (16, 25) AND\n") 
				.append("                      WFF.FLOW_NODE_STATUS = 2 THEN\n") 
				.append("                  1\n") 
				.append("                 ELSE\n") 
				.append("                  0\n") 
				.append("               END FAILRE,\n") 
				.append("               CASE\n") 
				.append("                 WHEN WFF.FLOW_NO_CODE IN (16, 25) AND\n") 
				.append("                      WFF.FLOW_NODE_STATUS = 0 THEN\n") 
				.append("                  1\n") 
				.append("                 ELSE\n") 
				.append("                  0\n") 
				.append("               END NOTRE,\n") 
				.append("               CASE\n") 
				.append("                 WHEN WFF.FLOW_NO_CODE IN (13, 14, 15, 23, 24) AND\n") 
				.append("                      WFF.FLOW_NODE_STATUS = 3 THEN\n") 
				.append("                  1\n") 
				.append("                 ELSE\n") 
				.append("                  0\n") 
				.append("               END ACTCTRL\n") 
				.append("          FROM W_FEECTRL_FLOW WFF, W_FEECTRL FC\n") 
				.append("         WHERE    FC.ORG_NO IN\n") 
				.append("                       (SELECT ORG.ORG_NO\n") 
				.append("                           FROM O_ORG ORG\n") 
				.append("                          WHERE ORG.P_ORG_NO = ?)\n") 
				.append("                   AND FC.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n") 
				.append("                   AND FC.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd') + 1\n") 
				.append("                   AND FC.BUSI_TYPE = '01'\n") 
				.append("           		AND WFF.APP_NO = FC.BUY_ORDER) TAB,\n") 
				.append("       O_ORG ORG\n") 
				.append(" WHERE TAB.ORG_NO = ORG.ORG_NO\n") 
				.append(" GROUP BY ORG.ORG_NO, ORG.ORG_NAME\n") 
				.append("UNION ALL\n") 
				.append("SELECT MAX('03') ORG_TYPE,\n") 
				.append("       MAX(?) ORG_NO,\n") 
				.append("       MAX((SELECT ORG_NAME FROM O_ORG WHERE ORG_NO = ?)) ORG_NAME,\n") 
				.append("       SUM(TAB.SUCCFEE) SUCC_FEE_CNT,\n") 
				.append("       SUM(TAB.SUCCRE) SUCC_RE_CNT,\n") 
				.append("       SUM(TAB.FAILFEE) FAIL_FEE_CNT,\n") 
				.append("       SUM(TAB.FAILRE) FAIL_RE_CNT,\n") 
				.append("       SUM(TAB.NOTRE) NOT_RE_CNT,\n") 
				.append("       SUM(TAB.ACTCTRL) ACC_CTRL_CNT\n") 
				.append("  FROM (SELECT CASE\n") 
				.append("                 WHEN WFF.FLOW_NO_CODE IN (15, 23) AND\n") 
				.append("                      WFF.FLOW_NODE_STATUS = 1 THEN\n") 
				.append("                  1\n") 
				.append("                 ELSE\n") 
				.append("                  0\n") 
				.append("               END SUCCFEE,\n") 
				.append("               CASE\n") 
				.append("                 WHEN WFF.FLOW_NO_CODE IN (16, 25) AND\n") 
				.append("                      WFF.FLOW_NODE_STATUS = 1 THEN\n") 
				.append("                  1\n") 
				.append("                 ELSE\n") 
				.append("                  0\n") 
				.append("               END SUCCRE,\n") 
				.append("               CASE\n") 
				.append("                 WHEN WFF.FLOW_NO_CODE IN (13, 14, 15, 23, 24) AND\n") 
				.append("                      WFF.FLOW_NODE_STATUS = 2 THEN\n") 
				.append("                  1\n") 
				.append("                 ELSE\n") 
				.append("                  0\n") 
				.append("               END FAILFEE,\n") 
				.append("               CASE\n") 
				.append("                 WHEN WFF.FLOW_NO_CODE IN (16, 25) AND\n") 
				.append("                      WFF.FLOW_NODE_STATUS = 2 THEN\n") 
				.append("                  1\n") 
				.append("                 ELSE\n") 
				.append("                  0\n") 
				.append("               END FAILRE,\n") 
				.append("               CASE\n") 
				.append("                 WHEN WFF.FLOW_NO_CODE IN (16, 25) AND\n") 
				.append("                      WFF.FLOW_NODE_STATUS = 0 THEN\n") 
				.append("                  1\n") 
				.append("                 ELSE\n") 
				.append("                  0\n") 
				.append("               END NOTRE,\n") 
				.append("               CASE\n") 
				.append("                 WHEN WFF.FLOW_NO_CODE IN (13, 14, 15, 23, 24) AND\n") 
				.append("                      WFF.FLOW_NODE_STATUS = 3 THEN\n") 
				.append("                  1\n") 
				.append("                 ELSE\n") 
				.append("                  0\n") 
				.append("               END ACTCTRL\n") 
				.append("          FROM W_FEECTRL_FLOW WFF, W_FEECTRL FC\n") 
				.append("          WHERE (FC.ORG_NO = ? OR\n") 
				.append("                       FC.ORG_NO IN\n") 
				.append("                       (SELECT ORG.ORG_NO\n") 
				.append("                           FROM O_ORG ORG\n") 
				.append("                          WHERE ORG.P_ORG_NO = ?))\n") 
				.append("                   AND FC.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n") 
				.append("                   AND FC.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd') + 1\n") 
				.append("                   AND FC.BUSI_TYPE = '01'\n") 
				.append("           AND WFF.APP_NO = FC.BUY_ORDER) TAB\n") 
				.append("HAVING MAX('03') IS NOT NULL");
			buyExecStatusStatBeanList1 = this.getJdbcTemplate().query(
					sb1.toString(),
					new Object[] { orgNo, execDate, execDate, orgNo, orgNo, orgNo,
							orgNo, execDate, execDate },
					new TmnlExecStatusRowMapper1());
			//待执行购电记录统计
	        List<BuyExecStatusStatBean> buyExecStatusStatBeanList2= new  ArrayList<BuyExecStatusStatBean>();
			StringBuffer sb2= new StringBuffer();
			sb2.append("SELECT MAX('04') ORG_TYPE, O.ORG_NO, O.ORG_NAME, COUNT(1) WAIT_ACC_CTRL_CNT\n")
				.append("  FROM W_FEECTRL FC, O_ORG O\n")
				.append(" WHERE FC.ORG_NO IN\n")
				.append("       (SELECT ORG.ORG_NO FROM O_ORG ORG WHERE ORG.P_ORG_NO = ?)\n")
				.append("   AND FC.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n")
				.append("   AND FC.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd')+1\n")
				.append("   AND FC.BUSI_TYPE = '01'\n")
				.append("   AND FC.STATUS_CODE = '03'\n")
				.append("   AND O.ORG_NO = FC.ORG_NO\n")
				.append(" GROUP BY O.ORG_NO, O.ORG_NAME\n")
				.append("UNION ALL\n")
				.append("\n")
				.append("SELECT MAX('03') ORG_TYPE,\n")
				.append("       MAX(?) ORG_NO,\n")
				.append("       MAX((SELECT ORG_NAME FROM O_ORG WHERE ORG_NO = ?)) ORG_NAME,\n")
				.append("       COUNT(1) WAIT_ACC_CTRL_CNT\n")
				.append("  FROM W_FEECTRL FC\n")
				.append(" WHERE (FC.ORG_NO = ?\n")
				.append("    OR FC.ORG_NO IN\n")
				.append("       (SELECT ORG.ORG_NO FROM O_ORG ORG WHERE ORG.P_ORG_NO = ?))\n")
				.append("   AND FC.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n")
				.append("   AND FC.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd')+1\n")
				.append("   AND FC.BUSI_TYPE = '01'\n")
				.append("   AND FC.STATUS_CODE = '03' HAVING MAX('03') IS NOT NULL");
			buyExecStatusStatBeanList2 = this.getJdbcTemplate().query(
					sb2.toString(),
					new Object[] { orgNo, execDate, execDate, orgNo, orgNo, orgNo, orgNo,
							execDate, execDate }, new TmnlExecStatusRowMapper2());
			 List<BuyExecStatusStatBean> buyExecStatusStatBeanList= new  ArrayList<BuyExecStatusStatBean>();
			 buyExecStatusStatBeanList = mergerBuyExecStatusStatBean(
					buyExecStatusStatBeanList1, buyExecStatusStatBeanList2);
			 if(null!=buyExecStatusStatBeanList&&0<buyExecStatusStatBeanList.size()){
				 for(BuyExecStatusStatBean buyBean:buyExecStatusStatBeanList){
					 if(buyBean.getOrgNo().equals(orgNo)){
						 buyExecStatusStatBeanList.remove(buyBean);
						 buyExecStatusStatBeanList.add(buyBean);
					     break;
					 }
				 }	 
			 }
			 return buyExecStatusStatBeanList;
		}catch(RuntimeException e){
			this.logger.debug("统计当日终端预购电执行情况出错！");
			throw e;
	   }
		 
	}
	
	/**
	 * 当日终端预购电执行情况统计（区县）
	 * @param orgNo
	 * @param execDate
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<BuyExecStatusStatBean> todayTmnlPrePaidExecStatusStatByDistrict(String orgNo,String execDate) throws DBAccessException {
		try{
			List<BuyExecStatusStatBean> buyExecStatusStatBeanList1= new  ArrayList<BuyExecStatusStatBean>();
			StringBuffer sb1= new StringBuffer();
	        sb1.append("SELECT MAX('04') ORG_TYPE,\n")
			   .append("       MAX(?) ORG_NO,\n")
		  	   .append("       MAX((SELECT ORG_NAME FROM O_ORG WHERE ORG_NO = ?)) ORG_NAME,\n")
			   .append("       SUM(TAB.SUCCFEE) SUCC_FEE_CNT,\n") 
			   .append("       SUM(TAB.SUCCRE) SUCC_RE_CNT,\n") 
			   .append("       SUM(TAB.FAILFEE) FAIL_FEE_CNT,\n") 
			   .append("       SUM(TAB.FAILRE) FAIL_RE_CNT,\n") 
			   .append("       SUM(TAB.NOTRE) NOT_RE_CNT,\n") 
			   .append("       SUM(TAB.ACTCTRL) ACC_CTRL_CNT\n") 
			   .append("  FROM (SELECT FC.ORG_NO,\n") 
			   .append("               CASE\n") 
			   .append("                 WHEN WFF.FLOW_NO_CODE IN (15, 23) AND\n") 
			   .append("                      WFF.FLOW_NODE_STATUS = 1 THEN\n") 
			   .append("                  1\n") 
			   .append("                 ELSE\n") 
			   .append("                  0\n") 
			   .append("               END SUCCFEE,\n") 
		   	   .append("               CASE\n") 
			   .append("                 WHEN WFF.FLOW_NO_CODE IN (16, 25) AND\n") 
			   .append("                      WFF.FLOW_NODE_STATUS = 1 THEN\n") 
			   .append("                  1\n") 
			   .append("                 ELSE\n") 
			   .append("                  0\n") 
			   .append("               END SUCCRE,\n") 
			   .append("               CASE\n") 
			   .append("                 WHEN WFF.FLOW_NO_CODE IN (13, 14, 15, 23, 24) AND\n") 
			   .append("                      WFF.FLOW_NODE_STATUS = 2 THEN\n") 
			   .append("                  1\n") 
			   .append("                 ELSE\n") 
			   .append("                  0\n") 
			   .append("               END FAILFEE,\n") 
			   .append("               CASE\n") 
			   .append("                 WHEN WFF.FLOW_NO_CODE IN (16, 25) AND\n") 
			   .append("                      WFF.FLOW_NODE_STATUS = 2 THEN\n") 
			   .append("                  1\n") 
			   .append("                 ELSE\n") 
			   .append("                  0\n") 
			   .append("               END FAILRE,\n") 
			   .append("               CASE\n") 
			   .append("                 WHEN WFF.FLOW_NO_CODE IN (16, 25) AND\n") 
			   .append("                      WFF.FLOW_NODE_STATUS = 0 THEN\n") 
			   .append("                  1\n") 
			   .append("                 ELSE\n") 
			   .append("                  0\n") 
			   .append("               END NOTRE,\n") 
			   .append("               CASE\n") 
			   .append("                 WHEN WFF.FLOW_NO_CODE IN (13, 14, 15, 23, 24) AND\n") 
			   .append("                      WFF.FLOW_NODE_STATUS = 3 THEN\n") 
			   .append("                  1\n") 
			   .append("                 ELSE\n") 
			   .append("                  0\n") 
			   .append("               END ACTCTRL\n") 
			   .append("          FROM W_FEECTRL_FLOW WFF, W_FEECTRL FC\n") 
			   .append("         WHERE     FC.ORG_NO = ?\n") 
			   .append("                   AND FC.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n") 
			   .append("                   AND FC.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd') + 1\n") 
			   .append("                   AND FC.BUSI_TYPE = '01'\n") 
			   .append("          		   AND WFF.APP_NO = FC.BUY_ORDER) TAB\n") 
			   .append("HAVING MAX('04') IS NOT NULL");
	        buyExecStatusStatBeanList1=this.getJdbcTemplate()
					.query(
							sb1.toString(),
							new Object[] {orgNo, orgNo, orgNo, execDate, execDate },
							new TmnlExecStatusRowMapper1());
	        //待执行购电记录统计
	        List<BuyExecStatusStatBean> buyExecStatusStatBeanList2= new  ArrayList<BuyExecStatusStatBean>();
			StringBuffer sb2= new StringBuffer();
			sb2.append("SELECT MAX('04') ORG_TYPE,\n")
				.append("       MAX(?) ORG_NO,\n")
				.append("       MAX((SELECT ORG_NAME FROM O_ORG WHERE ORG_NO = ?)) ORG_NAME,\n")
				.append("       COUNT(1) WAIT_ACC_CTRL_CNT\n") 
				.append("  FROM W_FEECTRL FC\n") 
				.append(" WHERE FC.ORG_NO =?\n") 
				.append("   AND FC.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n") 
				.append("   AND FC.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd')+1\n") 
				.append("   AND FC.BUSI_TYPE = '01'\n") 
				.append("   AND FC.STATUS_CODE = '03'\n") 
				.append("HAVING MAX('04') IS NOT NULL");
	        buyExecStatusStatBeanList2=this.getJdbcTemplate()
							.query(
									sb2.toString(),
									new Object[] {orgNo, orgNo, orgNo, execDate, execDate},
									new TmnlExecStatusRowMapper2());
	        return mergerBuyExecStatusStatBean(buyExecStatusStatBeanList1,buyExecStatusStatBeanList2);
        }catch(RuntimeException e){
			this.logger.debug("统计当日终端预购电执行情况出错！");
			throw e;
	   }
	}
	
	
	
	//合并当日终端预购电执行情况统计结果
	public List<BuyExecStatusStatBean> mergerBuyExecStatusStatBean(
			List<BuyExecStatusStatBean> buyExecStatusStatBeanList1,
			List<BuyExecStatusStatBean> buyExecStatusStatBeanList2) {
	    if(buyExecStatusStatBeanList1.size()==0 && buyExecStatusStatBeanList2.size()>0){
      		for(BuyExecStatusStatBean buyBean2:buyExecStatusStatBeanList2){
      			buyBean2.setBuyOrderNum(buyBean2.getWaitactCtrlCnt());
      			buyBean2.setSuccFeeCnt((long)0);
      			buyBean2.setSuccReCnt((long)0);
      			buyBean2.setFailFeeCnt((long)0);
      			buyBean2.setFailReCnt((long)0);
      			buyBean2.setNotReCnt((long)0);
      			buyBean2.setActCtrlCnt((long)0);
      		}
      		return buyExecStatusStatBeanList2;
	  	}
	      else if(buyExecStatusStatBeanList1.size()>0 && buyExecStatusStatBeanList2.size()==0){
	      	for(BuyExecStatusStatBean buyBean1:buyExecStatusStatBeanList1){
	      		buyBean1.setBuyOrderNum(
					 buyBean1.getSuccFeeCnt()
	      		+buyBean1.getSuccReCnt()
	      		+buyBean1.getFailFeeCnt()
	      		+buyBean1.getFailReCnt()
	      		+buyBean1.getNotReCnt()
	      		+buyBean1.getActCtrlCnt());
	      		buyBean1.setWaitactCtrlCnt((long)0);
	  		}
	  		return buyExecStatusStatBeanList1;
	      }
	      else if(buyExecStatusStatBeanList1.size()>0 && buyExecStatusStatBeanList2.size()>0){
	      	List<BuyExecStatusStatBean> buyList= new  ArrayList<BuyExecStatusStatBean>();
	      	int find =0;
	      	for(BuyExecStatusStatBean buyBean2:buyExecStatusStatBeanList2){
	      		for(BuyExecStatusStatBean buyBean1:buyExecStatusStatBeanList1){
	      			if(buyBean2.getOrgNo().equals(buyBean1.getOrgNo())){
	      				    find=1;
	      					buyBean1.setWaitactCtrlCnt(buyBean2.getWaitactCtrlCnt());
	      			        break;
	      			}
	      		}
	      		if(find==0){
	      			buyBean2.setSuccFeeCnt((long)0);
	      			buyBean2.setSuccReCnt((long)0);
	      			buyBean2.setFailFeeCnt((long)0);
	      			buyBean2.setFailReCnt((long)0);
	      			buyBean2.setNotReCnt((long)0);
	      			buyBean2.setActCtrlCnt((long)0);
	      			buyList.add(buyBean2);
	      		}
	      		find=0;
	      	}
	      	for(BuyExecStatusStatBean buyBean1:buyExecStatusStatBeanList1){
	      		if(null==buyBean1.getWaitactCtrlCnt())
	      			buyBean1.setWaitactCtrlCnt((long)0);
	      	}
	      	buyExecStatusStatBeanList1.addAll(buyList);
	      	for(BuyExecStatusStatBean buyBean1:buyExecStatusStatBeanList1){
	      		buyBean1.setBuyOrderNum(
					 buyBean1.getSuccFeeCnt()
	      		+buyBean1.getSuccReCnt()
	      		+buyBean1.getFailFeeCnt()
	      		+buyBean1.getFailReCnt()
	      		+buyBean1.getNotReCnt()
	      		+buyBean1.getActCtrlCnt()
	      		+buyBean1.getWaitactCtrlCnt());
	      	}
	//      	Collections.sort(buyExecStatusStatBeanList1, new Comparator<BuyExecStatusStatBean>(){
	//				@Override
	//				public int compare(BuyExecStatusStatBean o1,
	//						BuyExecStatusStatBean o2) {
	//					return o1.getOrgNo().compareTo(o2.getOrgNo());
	//				}
	//      		
	//      	    });
	      	return buyExecStatusStatBeanList1;
	      }
	      else{
	    	  List<BuyExecStatusStatBean> buyExecStatusStatBeanList= new  ArrayList<BuyExecStatusStatBean>();
	    	  return buyExecStatusStatBeanList;
	      }
	}
	
	/**
	 * 预付费控制执行查询
	 * @param orgNo
	 * @param orgType
	 * @param execDate
	 * @param ctrlType
	 * @param ctrlStatus
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	public Page<UrgeFeeBean> prePaidCtrlExecQueryBy(String orgNo,String orgType,
			String execDate,Integer ctrlType,Integer ctrlStatus, long start, long limit)
			throws DBAccessException{
		    Page<UrgeFeeBean> urgeFeeBeanPage =new Page<UrgeFeeBean>();
		    List<Object> list = new ArrayList<Object>();
		 	StringBuffer sb= new StringBuffer();
            sb.append("SELECT O.ORG_NAME,\n")
			  .append("       FC.BUY_ORDER,\n") 
			  .append("       C.CONS_NO,\n") 
			  .append("       C.CONS_NAME,\n") 
			  .append("       R.TERMINAL_ADDR,\n") 
			  .append("       UFP.CTRL_FLAG,\n") 
			  .append("       UFP.STATUS_CODE,\n") 
			  .append("       UFP.SEND_TIME\n") 
			  .append("  FROM W_URGEFEE_PARAM UFP, O_ORG O, C_CONS C, R_TMNL_RUN R, W_FEECTRL FC\n") 
			  .append(" WHERE O.ORG_NO = UFP.ORG_NO\n") 
			  .append("   AND FC.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n") 
			  .append("   AND FC.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd') + 1\n") 
			  .append("   AND FC.CONS_NO = UFP.CONS_NO\n") 
			  .append("   AND FC.TMNL_ASSET_NO = UFP.TMNL_ASSET_NO\n") 
			  .append("   AND FC.CTRL_FLAG = '1'\n") 
			  .append("   AND C.CONS_NO = UFP.CONS_NO\n") 
			  .append("   AND R.TMNL_ASSET_NO = UFP.TMNL_ASSET_NO\n") 
			  .append("   AND UFP.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n") 
			  .append("   AND UFP.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd') + 1\n");
            list.add(execDate);
            list.add(execDate);
            list.add(execDate);
            list.add(execDate);
		    if("03".equals(orgType)){
		    	sb.append(" AND (UFP.ORG_NO IN (SELECT O.ORG_NO FROM O_ORG O WHERE O.P_ORG_NO = ?) OR UFP.ORG_NO = ?)\n");
		    	list.add(orgNo);
		    	list.add(orgNo);
		    }
		    else if("04".equals(orgType)){
		    	sb.append(" AND UFP.ORG_NO = ?\n");
		    	list.add(orgNo);
		    }
		    else
		    	return urgeFeeBeanPage;
		    if(1==ctrlType)
		    	sb.append(" AND UFP.CTRL_FLAG IN (0,1)\n");
		    else if(2==ctrlType||3==ctrlType){
		    	sb.append(" AND UFP.CTRL_FLAG = ?\n");
		    	list.add(ctrlType);
		    }
	    	else
		    	return urgeFeeBeanPage;
		    if(0==ctrlStatus)
		    	sb.append(" AND (UFP.STATUS_CODE <> 3 OR UFP.STATUS_CODE IS NULL)\n");
		    else if(1==ctrlStatus)
		    	sb.append(" AND UFP.STATUS_CODE = 3 \n");
		    else
		    	return urgeFeeBeanPage;
		    //System.out.println("**********"+sb.toString());
		    try{
				return super.pagingFind(sb.toString(), start, limit,new PrePaidCtrlExecQueryRowMapper(),list.toArray());
		    }catch(RuntimeException e){
				this.logger.debug("查询预付费控制执行出错！");
				throw e;
		    }
	}
	
	/**
	 * 当日主站预购电执行情况统计
	 * @param orgNo
	 * @return
	 * @throws DBAccessException
	 */
	/*@SuppressWarnings("unchecked")
	public List<BuyExecStatusStatBean> todayStationPrePaidExecStatusStat(String orgNo,String execDate) throws DBAccessException {
		  StringBuffer sb= new StringBuffer();
		  //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");e
		  //String ctrlDate =sdf.format(new Date());
		  sb.append("SELECT O.ORG_NO,\n")
			  .append("       O.ORG_NAME,\n") 
			  .append("       BON.BUY_ORDER_NUM,\n")
			  .append("       AUC.ACT_URGEFEE_CNT,\n")
			  .append("       CUC.CIS_URGEFEE_CNT,\n")
			  .append("       SUC.STOP_URGEFEE_CNT,\n")
			  .append("       CSC.CIS_STOP_CNT\n")
			  .append("  FROM (SELECT COUNT(1) BUY_ORDER_NUM\n")
			  .append("          FROM W_FEECTRL FC\n")
			  .append("         WHERE FC.ORG_NO LIKE '%'||?\n")
			  .append("           AND FC.BUSI_TYPE = '01'\n")
			  .append("           AND TO_CHAR(FC.SEND_TIME, 'yyyy-mm-dd') = ?) BON,\n")
			  .append("       (SELECT COUNT(1) ACT_URGEFEE_CNT\n")
			  .append("          FROM W_FEECTRL FC\n")
			  .append("         WHERE FC.ORG_NO LIKE '%'||?\n")
			  .append("           AND FC.BUSI_TYPE = '01'\n")
			  .append("           AND TO_CHAR(FC.SEND_TIME, 'yyyy-mm-dd') = ?\n")
			  .append("           AND FC.STATUS_CODE = '1') AUC,\n")
			  .append("       (SELECT COUNT(1)  CIS_URGEFEE_CNT\n")
			  .append("          FROM W_FEECTRL FC\n")
			  .append("         WHERE FC.ORG_NO LIKE '%'||?\n")
			  .append("           AND FC.BUSI_TYPE = '01'\n")
			  .append("           AND TO_CHAR(FC.SEND_TIME, 'yyyy-mm-dd') = ?\n")
			  .append("           AND FC.STATUS_CODE = '2') CUC,\n")
			  .append("       (SELECT COUNT(1) STOP_URGEFEE_CNT\n")
			  .append("          FROM W_FEECTRL FC\n")
			  .append("         WHERE FC.ORG_NO LIKE '%'||?\n")
			  .append("           AND FC.BUSI_TYPE = '01'\n")
			  .append("           AND TO_CHAR(FC.SEND_TIME, 'yyyy-mm-dd') = ?\n")
			  .append("           AND FC.STATUS_CODE = '3') SUC,\n")
			  .append("       (SELECT COUNT(1) CIS_STOP_CNT\n")
			  .append("          FROM W_FEECTRL FC\n")
			  .append("         WHERE FC.ORG_NO LIKE '%'||?\n")
			  .append("           AND FC.BUSI_TYPE = '01'\n")
			  .append("           AND TO_CHAR(FC.SEND_TIME, 'yyyy-mm-dd') = ?\n")
			  .append("           AND FC.STATUS_CODE = '4') CSC,\n")
			  .append("       O_ORG O\n")
			  .append(" WHERE O.ORG_NO = ?");
		  try{
			return this.getJdbcTemplate()
					.query(
							sb.toString(),
							new Object[] { orgNo, execDate, orgNo, execDate,
									orgNo, execDate, orgNo, execDate, orgNo,
									execDate, orgNo},
							new StationExecStatusRowMapper());
		        }catch(RuntimeException e){
					this.logger.debug("统计当日终端预付费执行情况出错！");
					throw e;
			   }

	}*/
	
	/**
     * 终端预付费查询
     * @param orgNo
     * @param execDate
     * @param execStatus
     * @param appNo
     * @param consNo
     * @return
     * @throws DBAccessException
     */
	public Page<TmnlPrePaidQueryBean> terminalPrePaidQueryBy(String orgNo,String orgType,
			String execDate, Integer statType, long start, long limit)
			throws DBAccessException{
		 Page<TmnlPrePaidQueryBean> tmnlPrePaidQueryBeanPage =new Page<TmnlPrePaidQueryBean>();
	     List<Object> list = new ArrayList<Object>();
	     StringBuffer sb= new StringBuffer();
         sb.append("SELECT O.ORG_NAME,\n")
		     .append("       WFF.APP_NO,\n") 
		     .append("       C.CONS_NO,\n") 
		     .append("       C.CONS_NAME,\n") 
		     .append("       R.TERMINAL_ADDR,\n") 
		     .append("       D.ASSET_NO,\n") 
		     .append("       FC.SEND_TIME,\n") 
		     .append("       WFF.FLOW_NODE_STATUS,\n") 
		     .append("       WFF.FLOW_NO_CODE\n") 
		     .append("  FROM W_FEECTRL_FLOW WFF,\n") 
		     .append("       W_FEECTRL      FC,\n") 
		     .append("       O_ORG          O,\n") 
		     .append("       C_CONS         C,\n") 
		     .append("       R_TMNL_RUN     R,\n") 
		     .append("       D_METER        D\n") 
		     .append(" WHERE O.ORG_NO = FC.ORG_NO\n") 
		     .append("   AND C.CONS_NO = FC.CONS_NO\n") 
		     .append("   AND R.TMNL_ASSET_NO = FC.TMNL_ASSET_NO\n") 
		     .append("   AND D.METER_ID(+) = FC.METER_ID\n") 
		     .append("   AND WFF.APP_NO = FC.BUY_ORDER\n") 
		     .append("   AND FC.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n") 
		     .append("   AND FC.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd') + 1\n") 
		     .append("   AND FC.BUSI_TYPE = '01'\n");
         list.add(execDate);
         list.add(execDate);
         if("03".equals(orgType)){
        	 sb.append("AND (FC.ORG_NO = ? OR\n")
    			.append("      FC.ORG_NO IN\n") 
    			.append("      (SELECT ORG.ORG_NO FROM O_ORG ORG WHERE ORG.P_ORG_NO = ?))\n");
        	 list.add(orgNo);
             list.add(orgNo);
         }
         else if("04".equals(orgType)){
        	 sb.append("AND   FC.ORG_NO IN\n")
    			.append("     (SELECT ORG.ORG_NO FROM O_ORG ORG WHERE ORG.P_ORG_NO = ?))\n");
        	 list.add(orgNo);
         }
         else
        	 return tmnlPrePaidQueryBeanPage;
         
         if(1==statType){
        	 sb.append("AND WFF.FLOW_NO_CODE IN (15, 23)\n")
        		.append("AND WFF.FLOW_NODE_STATUS = 1");
         }
         else  if(2==statType){
        	 sb.append("AND WFF.FLOW_NO_CODE IN (16, 25)\n")
     		.append("AND WFF.FLOW_NODE_STATUS = 1");
         }
         else  if(3==statType){
        	 sb.append("AND WFF.FLOW_NO_CODE IN (13, 14, 15, 23, 24)\n")
     		.append("AND WFF.FLOW_NODE_STATUS = 2");
         }
         else  if(4==statType){
        	 sb.append("AND WFF.FLOW_NO_CODE IN (16, 25)\n")
     		.append("AND WFF.FLOW_NODE_STATUS = 2");
         }
         else  if(5==statType){
        	 sb.append("AND WFF.FLOW_NO_CODE IN (16, 25)\n")
     		.append("AND WFF.FLOW_NODE_STATUS = 0");
         }
         else  if(6==statType){
        	 sb.append("AND WFF.FLOW_NO_CODE IN (13, 14, 15, 23, 24)\n")
     		.append("AND WFF.FLOW_NODE_STATUS = 3");
         }
         else
        	 return tmnlPrePaidQueryBeanPage;
	     try{
				return super.pagingFind(sb.toString(), start, limit,new TmnlPrePaidQueryRowMapper1(),list.toArray());
		   }catch(RuntimeException e){
				this.logger.debug("查询终端预付费出错！");
				throw e;
		   }
	}
	
	/**
	 * 终端待执行预付费记录查询
	 * @param orgNo
	 * @param orgType
	 * @param execDate
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	public Page<TmnlPrePaidQueryBean> waitAccQueryBy(String orgNo,String orgType,
			String execDate, long start, long limit)
			throws DBAccessException{
		 Page<TmnlPrePaidQueryBean> tmnlPrePaidQueryBeanPage =new Page<TmnlPrePaidQueryBean>();
	     List<Object> list = new ArrayList<Object>();
	     StringBuffer sb= new StringBuffer();   
		sb.append("SELECT O.ORG_NAME,\n")
			.append("       FC.BUY_ORDER,\n") 
			.append("       C.CONS_NO,\n") 
			.append("       C.CONS_NAME,\n") 
			.append("       R.TERMINAL_ADDR,\n") 
			.append("       D.ASSET_NO,\n") 
			.append("       FC.SEND_TIME\n") 
			.append("  FROM W_FEECTRL FC, O_ORG O, C_CONS C, R_TMNL_RUN R, D_METER D\n") 
			.append(" WHERE O.ORG_NO = FC.ORG_NO\n") 
			.append("   AND C.CONS_NO = FC.CONS_NO\n") 
			.append("   AND R.TMNL_ASSET_NO = FC.TMNL_ASSET_NO\n") 
		    .append("   AND D.METER_ID(+) = FC.METER_ID\n") 
			.append("   AND FC.SEND_TIME >= TO_DATE(?, 'yyyy-mm-dd')\n") 
			.append("   AND FC.SEND_TIME < TO_DATE(?, 'yyyy-mm-dd') + 1\n") 
			.append("   AND FC.BUSI_TYPE = '01'\n") 
			.append("   AND FC.STATUS_CODE = '03'");
		list.add(execDate);
		list.add(execDate);
		if("03".equals(orgType)){
        	 sb.append("AND (FC.ORG_NO = ? OR\n")
    			.append("      FC.ORG_NO IN\n") 
    			.append("      (SELECT ORG.ORG_NO FROM O_ORG ORG WHERE ORG.P_ORG_NO = ?))\n");
        	 list.add(orgNo);
             list.add(orgNo);
         }
         else if("04".equals(orgType)){
        	 sb.append("AND   FC.ORG_NO IN\n")
    			.append("     (SELECT ORG.ORG_NO FROM O_ORG ORG WHERE ORG.P_ORG_NO = ?))\n");
        	 list.add(orgNo);
         }
         else
        	 return tmnlPrePaidQueryBeanPage;
		 try{
				return super.pagingFind(sb.toString(), start, limit,new TmnlPrePaidQueryRowMapper2(),list.toArray());
		   }catch(RuntimeException e){
				this.logger.debug("查询终端待执行预付费记录出错！");
				throw e;
		   }
	}

	class TodayCtrlExecStatRowMapper implements RowMapper{
    	public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
    		PrePaidCtrlExecStatBean prePaidCtrlExecStatBean=new PrePaidCtrlExecStatBean();
			try {
				prePaidCtrlExecStatBean.setOrgType(rs.getString("ORG_TYPE"));
				prePaidCtrlExecStatBean.setOrgNo(rs.getString("ORG_NO"));
				prePaidCtrlExecStatBean.setOrgName(rs.getString("ORG_NAME"));
				prePaidCtrlExecStatBean.setPrePaidCtrlNum(rs.getLong("PREPAID_CTRL_NUM"));
				prePaidCtrlExecStatBean.setSuccPoweroffCnt(rs.getLong("SUCC_POWEROFF_CNT"));
				prePaidCtrlExecStatBean.setFailPoweroffCnt(rs.getLong("FAIL_POWEROFF_CNT"));
				prePaidCtrlExecStatBean.setSuccPowerresCnt(rs.getLong("SUCC_POWERRES_CNT"));
				prePaidCtrlExecStatBean.setFailPowerresCnt(rs.getLong("FAIL_POWERRES_CNT"));
				prePaidCtrlExecStatBean.setSuccUrgefeeCnt(rs.getLong("SUCC_URGEFEE_CNT"));
				prePaidCtrlExecStatBean.setFailUrgefeeCnt(rs.getLong("FAIL_URGEFEE_CNT"));
				return prePaidCtrlExecStatBean;
			}catch (Exception e) {
				return null;
			}
    	}
    }
	
	class TmnlExecStatusRowMapper1 implements RowMapper{
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			try{
				BuyExecStatusStatBean buyExecStatusStatBean = new BuyExecStatusStatBean();
				buyExecStatusStatBean.setOrgType(rs.getString("ORG_TYPE"));
				buyExecStatusStatBean.setOrgNo(rs.getString("ORG_NO"));
				buyExecStatusStatBean.setOrgName(rs.getString("ORG_NAME"));
				buyExecStatusStatBean.setSuccFeeCnt(rs.getLong("SUCC_FEE_CNT"));
				buyExecStatusStatBean.setSuccReCnt(rs.getLong("SUCC_RE_CNT"));
				buyExecStatusStatBean.setFailFeeCnt(rs.getLong("FAIL_FEE_CNT"));
				buyExecStatusStatBean.setFailReCnt(rs.getLong("FAIL_RE_CNT"));
				buyExecStatusStatBean.setNotReCnt(rs.getLong("NOT_RE_CNT"));
				buyExecStatusStatBean.setActCtrlCnt(rs.getLong("ACC_CTRL_CNT"));
				//buyExecStatusStatBean.setWaitactCtrlCnt(rs.getLong("WAIT_ACC_CTRL_CNT"));
				return buyExecStatusStatBean;
			}catch (Exception e) {
				return null;
			}
		}
	}
	
	class TmnlExecStatusRowMapper2 implements RowMapper{
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			try{
				BuyExecStatusStatBean buyExecStatusStatBean = new BuyExecStatusStatBean();
				buyExecStatusStatBean.setOrgType(rs.getString("ORG_TYPE"));
				buyExecStatusStatBean.setOrgNo(rs.getString("ORG_NO"));
				buyExecStatusStatBean.setOrgName(rs.getString("ORG_NAME"));
				buyExecStatusStatBean.setWaitactCtrlCnt(rs.getLong("WAIT_ACC_CTRL_CNT"));
				return buyExecStatusStatBean;
			}catch (Exception e) {
				return null;
			}
		}
	}
	
	class  StationExecStatusRowMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			try{
				BuyExecStatusStatBean buyExecStatusStatBean = new BuyExecStatusStatBean();
				buyExecStatusStatBean.setOrgNo(rs.getString("ORG_NO"));
				buyExecStatusStatBean.setOrgName(rs.getString("ORG_NAME"));
				buyExecStatusStatBean.setBuyOrderNum(rs.getLong("BUY_ORDER_NUM"));
				buyExecStatusStatBean.setActUrgefeeCnt(rs.getLong("ACT_URGEFEE_CNT"));
				buyExecStatusStatBean.setCisUrgefeeCnt(rs.getLong("CIS_URGEFEE_CNT"));
				buyExecStatusStatBean.setStopUrgefeeCnt(rs.getLong("STOP_URGEFEE_CNT"));
				buyExecStatusStatBean.setCisStopCnt(rs.getLong("CIS_STOP_CNT"));
				return buyExecStatusStatBean;
			}catch (Exception e) {
				return null;
			}
		}
	}
	
	class TmnlPrePaidQueryRowMapper1 implements RowMapper{
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TmnlPrePaidQueryBean tmnlPrePaidQueryBean = new TmnlPrePaidQueryBean();
				tmnlPrePaidQueryBean.setOrgName(rs.getString("ORG_NAME"));
				tmnlPrePaidQueryBean.setAppNo(rs.getString("APP_NO"));
				tmnlPrePaidQueryBean.setConsNo(rs.getString("CONS_NO"));
				tmnlPrePaidQueryBean.setConsName(rs.getString("CONS_NAME"));
				tmnlPrePaidQueryBean.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				tmnlPrePaidQueryBean.setMeterAssetNo(rs.getString("ASSET_NO"));
				if(null!=rs.getString("SEND_TIME")&&!"".equals(rs.getString("SEND_TIME")))
					tmnlPrePaidQueryBean.setExecuteDate(sdf.format(rs.getDate("SEND_TIME")));
				if("0".equals(rs.getString("FLOW_NODE_STATUS")))
					tmnlPrePaidQueryBean.setExecuteStatus("没有开始");
				else if("1".equals(rs.getString("FLOW_NODE_STATUS")))
					tmnlPrePaidQueryBean.setExecuteStatus("成功");
				else if("2".equals(rs.getString("FLOW_NODE_STATUS")))
					tmnlPrePaidQueryBean.setExecuteStatus("失败");
				else if("3".equals(rs.getString("FLOW_NODE_STATUS")))
					tmnlPrePaidQueryBean.setExecuteStatus("调试中");
				if("13".equals(rs.getString("FLOW_NO_CODE")))
					tmnlPrePaidQueryBean.setFlowStatus("下发预付费参数（手工）");
				else if("14".equals(rs.getString("FLOW_NO_CODE")))
					tmnlPrePaidQueryBean.setFlowStatus("召测终端预付费参数进行比较（手工）");
				else if("15".equals(rs.getString("FLOW_NO_CODE")))
					tmnlPrePaidQueryBean.setFlowStatus("下发预付费方案（手工）");
				else if("16".equals(rs.getString("FLOW_NO_CODE")))
					tmnlPrePaidQueryBean.setFlowStatus("返回营销系统预付费执行信息（手工）");
				else if("23".equals(rs.getString("FLOW_NO_CODE")))
					tmnlPrePaidQueryBean.setFlowStatus("下发预付费参数（接口）");
				else if("24".equals(rs.getString("FLOW_NO_CODE")))
					tmnlPrePaidQueryBean.setFlowStatus("召测剩余值（接口）");
				else if("25".equals(rs.getString("FLOW_NO_CODE")))
					tmnlPrePaidQueryBean.setFlowStatus("返回营销系统预付费执行信息（接口）");
				return tmnlPrePaidQueryBean;
			}catch (Exception e) {
				return null;
			}
		}
		
	}
	
	class TmnlPrePaidQueryRowMapper2 implements RowMapper{
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TmnlPrePaidQueryBean tmnlPrePaidQueryBean = new TmnlPrePaidQueryBean();
				tmnlPrePaidQueryBean.setOrgName(rs.getString("ORG_NAME"));
				tmnlPrePaidQueryBean.setAppNo(rs.getString("BUY_ORDER"));
				tmnlPrePaidQueryBean.setConsNo(rs.getString("CONS_NO"));
				tmnlPrePaidQueryBean.setConsName(rs.getString("CONS_NAME"));
				tmnlPrePaidQueryBean.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				tmnlPrePaidQueryBean.setMeterAssetNo(rs.getString("ASSET_NO"));
				if(null!=rs.getString("SEND_TIME")&&!"".equals(rs.getString("SEND_TIME")))
					tmnlPrePaidQueryBean.setExecuteDate(sdf.format(rs.getDate("SEND_TIME")));
				tmnlPrePaidQueryBean.setExecuteStatus("成功");
				tmnlPrePaidQueryBean.setFlowStatus("等待执行");
				return tmnlPrePaidQueryBean;
			}catch (Exception e) {
				return null;
			}
		}
	}
	
	class  PrePaidCtrlExecQueryRowMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				UrgeFeeBean urgeFeeBean = new UrgeFeeBean();
				urgeFeeBean.setOrgName(rs.getString("ORG_NAME"));
				urgeFeeBean.setAppNo(rs.getString("BUY_ORDER"));
				urgeFeeBean.setConsNo(rs.getString("CONS_NO"));
				urgeFeeBean.setConsName(rs.getString("CONS_NAME"));
				urgeFeeBean.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
			    if("0".equals(rs.getString("CTRL_FLAG"))||"1".equals(rs.getString("CTRL_FLAG")))
			    	urgeFeeBean.setCtrlType("催费");
			    else if("2".equals(rs.getString("CTRL_FLAG")))
			    	urgeFeeBean.setCtrlType("停电");
			    else if("3".equals(rs.getString("CTRL_FLAG")))
			    	urgeFeeBean.setCtrlType("复电");
			    if("3".equals(rs.getString("STATUS_CODE")))
			    	urgeFeeBean.setCtrlStatus("成功");
			    else
			    	urgeFeeBean.setCtrlStatus("失败");
			    if(null!=rs.getString("SEND_TIME")||!"".equals(rs.getString("SEND_TIME")))
			    	urgeFeeBean.setCtrlTime(sdf.format(rs.getDate("SEND_TIME")));
				return urgeFeeBean;
			}catch (Exception e) {
				return null;
			}
		}
	}
}
