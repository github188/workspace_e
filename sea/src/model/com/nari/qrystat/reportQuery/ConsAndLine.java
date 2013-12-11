package com.nari.qrystat.reportQuery;
/**
 * @author sungang
 */

public class ConsAndLine {

	//表示用户编号
	private String cons_no;
	//表示用户名称
	private String cons_name;
	
	private String volt_code;
	
//	private String line_name;
	
//	private String run_status_code;
	
	public String getVolt_code() {
		return volt_code;
	}
	public void setVolt_code(String voltCode) {
		volt_code = voltCode;
	}
//	public String getLine_name() {
//		return line_name;
//	}
//	public void setLine_name(String lineName) {
//		line_name = lineName;
//	}
//	public String getRun_status_code() {
//		return run_status_code;
//	}
//	public void setRun_status_code(String runStatusCode) {
//		run_status_code = runStatusCode;
//	}
	public String getCons_no() {
		return cons_no;
	}
	public void setCons_name(String consName) {
		cons_name = consName;
	}
	public void setCons_no(String consNo) {
		cons_no = consNo;
	}
	public String getCons_name() {
		return cons_name;
	}

}
