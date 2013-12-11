package com.nari.dao.jdbc;

import java.util.List;

public interface WsForMarketingDao {
	
	/**
	 * 预购电控制服务将营销调用该服务传过来的数据存入中间库数据表
	 * @return boolean
	 */
	public boolean insertPrepayCtrl(String org_no,String cp_no,String meter_id,String frst_buy_flag,String cust_no,String name,
			String buy_date,String paid_pq,String warning_pq,String stop_pq,String plan_exec_date);
	
	/**
	 * 催费控制服务将营销调用该服务传过来的数据存入中间库数据表
	 * @return boolean
	 */
	public boolean insertCtrlPara(String cp_no, String org_no,String cust_no,String name, String type_code, String plan_exec_date);
	
	/**
	 * 营业报停服务将营销调用该服务传过来的数据存入中间库数据表
	 * @return boolean
	 */
	public boolean insertStopCtrlPara(String cp_no,String org_no, String cust_no, String name, String type_code,String pr_const_value,String plan_exec_date);
	
	/**
	 * 预购电控制服务业务逻辑处理以及与前置交互的处理
	 * @return 
	 */
	public List<?> getPrepayById(String org_no,String cp_no,String meter_id,String prepaid_id,String frst_buy_flag,String cust_no,String name,
			String buy_date,String paid_pq,String warning_pq,String stop_pq,String plan_exec_date,String rlt_flag,String fail_memo);
	
	/**
	 * 催费控制服务业务逻辑处理以及与前置交互的处理
	 * @return 
	 */
	public List<?> getCrcyById(String cr_id,String cp_no,String org_no,String cust_no,String name,String type_code,String plan_exec_date,
			String rlt_flag,String fail_memo);	
	
	/**
	 * 营业报停服务业务逻辑处理以及与前置交互的处理
	 * @return 
	 */
	public List<?> getStopCtrlById(String busi_stop_id,String cp_no,String org_no,String cust_no,String name,String type_code,
			String pr_const_value,String plan_exec_date,String rlt_flag,String fail_memo);

	/**
	 * 营销异常处理结果服务
	 * 
	 *@return String
	 */
	public String WSExcpApprSr(String excp_appr_id,String appr_type,String app_no,String cons_no,String mp_no);
}