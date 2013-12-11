package com.nari.dao.jdbc.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.nari.dao.BaseDao;
import com.nari.dao.jdbc.WsForMarketingDao;

@SuppressWarnings("unused")
public class WsForMarketingDaoImpl extends BaseDao implements WsForMarketingDao {

	private static Logger logger = Logger.getLogger(WsForMarketingDaoImpl.class);

	public boolean insertPrepayCtrl(String org_no,String cp_no,String meter_id,String frst_buy_flag,String cust_no,String name,
			String buy_date,String paid_pq,String warning_pq,String stop_pq,String plan_exec_date) {
		String sql="insert into I_PREPAY_CTRL(org_no,cp_no,meter_id,prepaid_id,frst_buy_flag,cust_no,name,buy_date,paid_pq,warning_pq,stop_pq,plan_exec_date,rlt_flag,fail_memo) " +
					"values" +
					" ("+org_no+",'"+cp_no+"','"+meter_id+"',S_QUENCE.NEXTVAL,'"+frst_buy_flag+"','"+cust_no+"','"+name+"','"+buy_date+"','"+paid_pq+"','"+warning_pq+"','"+stop_pq+"','"+plan_exec_date+"','1','')";		
		int result = getJdbcDAO().update(sql);
		boolean flag = false;
		if (result == 1) {
			flag = true;
		}
		return flag;
	}

	public boolean insertCtrlPara(String cp_no, String org_no,String cust_no,String name, String type_code, String plan_exec_date){
		String sql="insert into I_CR_CTRL(cr_id,cp_no,org_no,cust_no,name,type_code,plan_exec_date,rlt_flag,fail_memo) " +
					"values" +
					" (S_QUENCE.NEXTVAL,'"+cp_no+"','"+org_no+"','"+cust_no+"','"+name+"','"+type_code+"','"+plan_exec_date+"','1','')";
		int result = getJdbcDAO().update(sql);
		boolean flag = false;
		if (result == 1) {
			flag = true;
		}
		return flag;
	}

	public boolean insertStopCtrlPara(String cp_no,String org_no, String cust_no, String name, String type_code,String pr_const_value,String plan_exec_date){
		String sql="insert into I_BUSI_STOP_CTRL(busi_stop_id,cp_no,org_no,cust_no,name,type_code,pr_const_value,plan_exec_date,rlt_flag,fail_memo) " +
					"values" +
					" (S_QUENCE.NEXTVAL,'"+cp_no+"','"+org_no+"','"+cust_no+"','"+name+"','"+type_code+"','"+pr_const_value+"','"+plan_exec_date+"','1','')";
		int result = getJdbcDAO().update(sql);
		boolean flag = false;
		if (result == 1) {
			flag = true;
		}
		return flag;
	}

	public List<?> getCrcyById(String crId, String cpNo, String orgNo,
			String custNo, String name, String typeCode, String planExecDate,
			String rltFlag, String failMemo) {
		return null;
	}

	public List<?> getPrepayById(String orgNo, String cpNo, String meterId,
			String prepaidId, String frstBuyFlag, String custNo, String name,
			String buyDate, String paidPq, String warningPq, String stopPq,
			String planExecDate, String rltFlag, String failMemo) {
		return null;
	}

	public List<?> getStopCtrlById(String busiStopId, String cpNo,
			String orgNo, String custNo, String name, String typeCode,
			String prConstValue, String planExecDate, String rltFlag,
			String failMemo) {
		return null;
	}
	
	public String WSExcpApprSr(String excpApprId, String apprType,
			String appNo, String consNo, String mpNo) {
		return null;
	}
}