package com.nari.eventbean;

/**
 * 催费控制服务事件
 */
public class CrCyEvent {

	private String cr_id;
	private String cp_no;
	private String org_no;
	private String cust_no;
	private String name;
	private String type_code;
	private String plan_exec_date;
	private String rlt_flag;
	private String fail_memo;

	public CrCyEvent(String cr_id,String cp_no,String org_no,
					String cust_no,String name,String type_code,String plan_exec_date,String rlt_flag,String fail_memo) {
		super();
		this.cr_id = cr_id;
		this.cp_no = cp_no;
		this.org_no = org_no;
		this.cust_no = cust_no;
		this.name = name;
		this.type_code = type_code;
		this.plan_exec_date = plan_exec_date;
		this.rlt_flag = rlt_flag;
		this.fail_memo = fail_memo;
	}

	public String getCr_id() {
		return cr_id;
	}


	public void setCr_id(String crId) {
		cr_id = crId;
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