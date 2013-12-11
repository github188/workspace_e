package com.nari.eventbean;

/**
 * 营业报停服务事件
 */
public class StopCtrlEvent {

	private String busi_stop_id;
	private String cp_no;
	private String org_no;
	private String cust_no;
	private String name;
	private String type_code;
	private String pr_const_value;
	private String plan_exec_date;
	private String rlt_flag;
	private String fail_memo;
	
	public StopCtrlEvent(String busi_stop_id,String cp_no,String org_no,
					String cust_no,String name,String type_code,String pr_const_value,String plan_exec_date,String rlt_flag,String fail_memo) {
		super();
		this.busi_stop_id = busi_stop_id;
		this.cp_no = cp_no;
		this.org_no = org_no;
		this.cust_no = cust_no;
		this.name = name;
		this.type_code = type_code;
		this.pr_const_value = pr_const_value;
		this.plan_exec_date = plan_exec_date;
		this.rlt_flag = rlt_flag;
		this.fail_memo = fail_memo;
	}

	public String getBusi_stop_id() {
		return busi_stop_id;
	}

	public void setBusi_stop_id(String busiStopId) {
		busi_stop_id = busiStopId;
	}

	public String getCp_no() {
		return cp_no;
	}

	public void setCp_no(String cpNo) {
		cp_no = cpNo;
	}

	public String getOrg_no() {
		return org_no;
	}

	public void setOrg_no(String orgNo) {
		org_no = orgNo;
	}

	public String getCust_no() {
		return cust_no;
	}

	public void setCust_no(String custNo) {
		cust_no = custNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String typeCode) {
		type_code = typeCode;
	}

	public String getPr_const_value() {
		return pr_const_value;
	}

	public void setPr_const_value(String prConstValue) {
		pr_const_value = prConstValue;
	}

	public String getPlan_exec_date() {
		return plan_exec_date;
	}

	public void setPlan_exec_date(String planExecDate) {
		plan_exec_date = planExecDate;
	}

	public String getRlt_flag() {
		return rlt_flag;
	}

	public void setRlt_flag(String rltFlag) {
		rlt_flag = rltFlag;
	}

	public String getFail_memo() {
		return fail_memo;
	}

	public void setFail_memo(String failMemo) {
		fail_memo = failMemo;
	}
}