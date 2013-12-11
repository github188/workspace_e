package com.nari.eventbean;

/**
 * 预购电控制服务事件
 */
public class PrepayCtrlEvent {

	private String org_no;
	private String cp_no;
	private String meter_id;
	private String prepaid_id;
	private String frst_buy_flag;
	private String cust_no;
	private String name;
	private String buy_date;
	private String paid_pq;
	private String warning_pq;
	private String stop_pq;
	private String plan_exec_date;
	private String rlt_flag;
	private String fail_memo;
	
	public PrepayCtrlEvent(String org_no,String cp_no,String meter_id,String prepaid_id,String frst_buy_flag,String cust_no,String name,
			String buy_date,String paid_pq,String warning_pq,String stop_pq,String plan_exec_date,String rlt_flag,String fail_memo) {
		super();
		this.org_no = org_no;
		this.cp_no = cp_no;
		this.meter_id = meter_id;
		this.prepaid_id = prepaid_id;
		this.frst_buy_flag = frst_buy_flag;
		this.cust_no = cust_no;
		this.name = name;
		this.buy_date = buy_date;
		this.paid_pq = paid_pq;
		this.warning_pq = warning_pq;
		this.stop_pq = stop_pq;
		this.plan_exec_date = plan_exec_date;
		this.rlt_flag = rlt_flag;
		this.fail_memo = fail_memo;
	}

	public String getOrg_no() {
		return org_no;
	}

	public void setOrg_no(String orgNo) {
		org_no = orgNo;
	}

	public String getCp_no() {
		return cp_no;
	}

	public void setCp_no(String cpNo) {
		cp_no = cpNo;
	}

	public String getMeter_id() {
		return meter_id;
	}

	public void setMeter_id(String meterId) {
		meter_id = meterId;
	}

	public String getPrepaid_id() {
		return prepaid_id;
	}

	public void setPrepaid_id(String prepaidId) {
		prepaid_id = prepaidId;
	}

	public String getFrst_buy_flag() {
		return frst_buy_flag;
	}

	public void setFrst_buy_flag(String frstBuyFlag) {
		frst_buy_flag = frstBuyFlag;
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

	public String getBuy_date() {
		return buy_date;
	}

	public void setBuy_date(String buyDate) {
		buy_date = buyDate;
	}

	public String getPaid_pq() {
		return paid_pq;
	}

	public void setPaid_pq(String paidPq) {
		paid_pq = paidPq;
	}

	public String getWarning_pq() {
		return warning_pq;
	}

	public void setWarning_pq(String warningPq) {
		warning_pq = warningPq;
	}

	public String getStop_pq() {
		return stop_pq;
	}

	public void setStop_pq(String stopPq) {
		stop_pq = stopPq;
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