package com.nari.model;

import java.util.Date;

@SuppressWarnings("serial")
public class InterfaceLog implements java.io.Serializable {
	
	/*日志ID*/
	private String LOG_ID;
	/*系统编码*/
	private String SYS_NO;
	/*接口类型*/
	private String JK_TYPE;
	/*接口名称*/
	private String JK_NAME;
	/*接口关键内容*/
	private String JK_VALUE;
	/*出错编码*/
	private String ERR_NO;
	/*操作日期*/
	private Date OP_TIME;
	
	public String getLOG_ID() {
		return LOG_ID;
	}
	public void setLOG_ID(String lOGID) {
		LOG_ID = lOGID;
	}
	public String getSYS_NO() {
		return SYS_NO;
	}
	public void setSYS_NO(String sYSNO) {
		SYS_NO = sYSNO;
	}
	public String getJK_TYPE() {
		return JK_TYPE;
	}
	public void setJK_TYPE(String jKTYPE) {
		JK_TYPE = jKTYPE;
	}
	public String getJK_NAME() {
		return JK_NAME;
	}
	public void setJK_NAME(String jKNAME) {
		JK_NAME = jKNAME;
	}
	public String getJK_VALUE() {
		return JK_VALUE;
	}
	public void setJK_VALUE(String jKVALUE) {
		JK_VALUE = jKVALUE;
	}
	public String getERR_NO() {
		return ERR_NO;
	}
	public void setERR_NO(String eRRNO) {
		ERR_NO = eRRNO;
	}
	public Date getOP_TIME() {
		return OP_TIME;
	}
	public void setOP_TIME(Date oPTIME) {
		OP_TIME = oPTIME;
	}
}