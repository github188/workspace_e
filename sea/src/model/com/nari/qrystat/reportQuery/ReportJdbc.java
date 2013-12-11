package com.nari.qrystat.reportQuery;

public class ReportJdbc  {
	private String 	reportId;
	private String  reportType;
	private String reportName;
	private long reportLen;
	private byte[] reportContent;
	private String reportParam;
	private boolean isValid;
	private byte[] jsFile;
	private byte[] templateJSFile;
	private int defBoolean;
	private byte[] consNoList;
	public byte[] getJsFile() {
		return jsFile;
	}
	public void setJsFile(byte[] jsFile) {
		this.jsFile = jsFile;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public long getReportLen() {
		return reportLen;
	}
	public void setReportLen(long reportLen) {
		this.reportLen = reportLen;
	}
	public byte[] getReportContent() {
		return reportContent;
	}
	public void setReportContent(byte[] reportContent) {
		this.reportContent = reportContent;
	}
	public String getReportParam() {
		return reportParam;
	}
	public void setReportParam(String reportParam) {
		this.reportParam = reportParam;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public byte[] getTemplateJSFile() {
		return templateJSFile;
	}
	public void setTemplateJSFile(byte[] templateJSFile) {
		this.templateJSFile = templateJSFile;
	}
	public int getDefBoolean() {
		return defBoolean;
	}
	public void setDefBoolean(int defBoolean) {
		this.defBoolean = defBoolean;
	}
	public byte[] getConsNoList() {
		return consNoList;
	}
	public void setConsNoList(byte[] consNoList) {
		this.consNoList = consNoList;
	}
	
}
	


