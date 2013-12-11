package com.nari.serviceimpl;

import java.util.List;

import com.nari.dao.jdbc.WsForMarketingDao;
import com.nari.service.WsForMarketingManager;

public class WsForMarketingManagerImpl implements WsForMarketingManager{
	
	private WsForMarketingDao wsForMarketingDao;
	
	public void setWsForMarketingDao(WsForMarketingDao wsForMarketingDao){
		this.wsForMarketingDao = wsForMarketingDao;
	}
	
	public boolean insertPrepayCtrl(String org_no,String cp_no,String meter_id,String frst_buy_flag,String cust_no,String name,
			String buy_date,String paid_pq,String warning_pq,String stop_pq,String plan_exec_date) {
		return this.wsForMarketingDao.insertPrepayCtrl(org_no,cp_no,meter_id,frst_buy_flag,cust_no,name,buy_date,paid_pq,warning_pq,stop_pq,plan_exec_date);
	}
	
	public boolean insertCtrlPara(String cp_no, String org_no,String cust_no,String name, String type_code, String plan_exec_date) {
		return this.wsForMarketingDao.insertCtrlPara(cp_no, org_no, cust_no, name, type_code, plan_exec_date);
	}
	
	public boolean insertStopCtrlPara(String cp_no,String org_no, String cust_no, String name, String type_code,String pr_const_value,String plan_exec_date) {
		return this.wsForMarketingDao.insertStopCtrlPara(cp_no, org_no, cust_no, name, type_code, pr_const_value, plan_exec_date);
	}
	
	public List<?> getCrcyById(String crId, String cpNo, String orgNo,String custNo, String name, String typeCode, String planExecDate,
			String rltFlag, String failMemo) {
		return null;
	}

	public List<?> getPrepayById(String orgNo, String cpNo, String meterId,String prepaidId, String frstBuyFlag, String custNo, String name,
			String buyDate, String paidPq, String warningPq, String stopPq,String planExecDate, String rltFlag, String failMemo) {
		return null;
	}

	public List<?> getStopCtrlById(String busiStopId, String cpNo,String orgNo, String custNo, String name, String typeCode,
			String prConstValue, String planExecDate, String rltFlag,String failMemo) {
		return null;
	} 
	
	public String WSExcpApprSr(String excpApprId, String apprType,
			String appNo, String consNo, String mpNo) {
		return this.wsForMarketingDao.WSExcpApprSr(excpApprId,apprType,appNo,consNo,mpNo);
	}
}