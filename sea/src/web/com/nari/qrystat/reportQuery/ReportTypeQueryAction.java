
package com.nari.qrystat.reportQuery;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.queryReport.IReportTypeQueryManager;
import com.nari.qrystat.queryReport.impl.ReportTypeQueryManagerImpl;

/**
 * 报表查询web类
 * 
 * @author chenguozhang
 * 
 */

public class ReportTypeQueryAction extends BaseAction {
	/**
	 * @description 以下三个变量是从左边数选取供电单位信息时传递的参数
	 */
	// 供电单位编号
	private String orgNo;
	// 供电单位名称
	private String orgName;
	// 供电单位类型
	private String orgType;
	// 从左边数查询出来的供电单位的信息
	private List<LeftTreeInfo> treeInfo;
	// 电压等级列表
	private List<VoltDegree> voltDegreeList;
	// 通信方式列表
	private List<CommMode> commModeList;
	// 日志属性
	private Logger logger = Logger.getLogger(this.getClass());
	// 注入报表查询服务类
	IReportTypeQueryManager iReportTypeQueryManager;
	// 返回确认
	public boolean success = true;

	private Date date;
	// 报表ID
	private int reportId;
	// 报表类型
	private String reportType;
	// 报表类型
	private String reportName;
	// 报表长度
	private long reportLen;
	// 报表内容
	private byte[] reportContent;
	// 报表参数
	private String reportParam;
	// 是否有效
	private boolean isValid;
	// 报表信息列表
	public List<ReportJdbc> reportList;
	// 用户列表
	public List<Integer> userList;
	// 保存文件流
	public ByteArrayInputStream bais;
	// 脚本内容
	public String scriptConent;
	// 运行容量列表
	private List<RunCap> runCapList;
	// 保存模板中的组织列表
	private String orgNoList;
	// 保存模板中的用户列表
	private String consNoList;
	// 模板列表
	private List<ReportTemplat> repTemList;
	// 报表原来的报表类型
	private String rawReportName;
	// 报表原来的报表名称
	private String rawReportType;
	// 表示GRID里用户信息的列表
	private List<ConsInfo> consInfoList;
	// 表示Grid里供电单位信息的列表
	private List<OrgInfo> orgNoInfoList;
	// 表示模板ID
	private int templateId;
	// 表示是否是自定义报表
	private int defBoolean;
	private byte[] jsFile;
	//保存报文件
	private File reportFile;
	//保存报文件
	private File jsFiles;
	//报表描述 
	private String reportDescription;
	//判断是否自定义报表
	private int defBool;

	public String getOrgNoList() {
		return orgNoList;
	}

	public void setOrgNoList(String orgNoList) {
		this.orgNoList = orgNoList;
	}

	public String getConsNoList() {
		return consNoList;
	}

	public void setConsNoList(String consNoList) {
		this.consNoList = consNoList;
	}

	public void setCommModeList(List<CommMode> commModeList) {
		this.commModeList = commModeList;
	}

	public List<RunCap> getRunCapList() {
		return runCapList;
	}

	public void setRunCapList(List<RunCap> runCapList) {
		this.runCapList = runCapList;
	}

	public void setVoltDegreeList(List<VoltDegree> voltDegreeList) {
		this.voltDegreeList = voltDegreeList;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getScriptConent() {
		return scriptConent;
	}

	public void setScriptConent(String scriptConent) {
		this.scriptConent = scriptConent;
	}

	public List<Integer> getUserList() {
		return userList;
	}

	public void setUserList(List<Integer> userList) {
		this.userList = userList;
	}

	public ByteArrayInputStream getBais() {
		return bais;
	}

	public void setBais(ByteArrayInputStream bais) {
		this.bais = bais;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public List<CommMode> getCommModeList() {
		return commModeList;
	}

	public void setCommMode(List<CommMode> commModeList) {
		this.commModeList = commModeList;
	}


	public List<LeftTreeInfo> getTreeInfo() {
		return treeInfo;
	}

	public void setTreeInfo(List<LeftTreeInfo> treeInfo) {
		this.treeInfo = treeInfo;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
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

	@JSON(serialize = false)
	public IReportTypeQueryManager getiReportTypeQueryManager() {
		return iReportTypeQueryManager;
	}

	public void setiReportTypeQueryManager(
			IReportTypeQueryManager iReportTypeQueryManager) {
		this.iReportTypeQueryManager = iReportTypeQueryManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<ReportJdbc> getReportList() {
		return reportList;
	}

	public void setReportList(List<ReportJdbc> reportList) {
		this.reportList = reportList;
	}

	public List<ReportTemplat> getRepTemList() {
		return repTemList;
	}

	public void setRepTemList(List<ReportTemplat> repTemList) {
		this.repTemList = repTemList;
	}

	public String getRawReportName() {
		return rawReportName;
	}

	public void setRawReportName(String rawReportName) {
		this.rawReportName = rawReportName;
	}

	public String getRawReportType() {
		return rawReportType;
	}

	public void setRawReportType(String rawReportType) {
		this.rawReportType = rawReportType;
	}

	public List<ConsInfo> getConsInfoList() {
		return consInfoList;
	}

	public void setConsInfoList(List<ConsInfo> consInfoList) {
		this.consInfoList = consInfoList;
	}

	public List<OrgInfo> getOrgNoInfoList() {
		return orgNoInfoList;
	}

	public void setOrgNoInfoList(List<OrgInfo> orgNoInfoList) {
		this.orgNoInfoList = orgNoInfoList;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getDefBoolean() {
		return defBoolean;
	}

	public void setDefBoolean(int defBoolean) {
		this.defBoolean = defBoolean;
	}

	public byte[] getJsFile() {
		return jsFile;
	}

	public void setJsFile(byte[] jsFile) {
		this.jsFile = jsFile;
	}
	


	public File getReportFile() {
		return reportFile;
	}

	public void setReportFile(File reportFile) {
		this.reportFile = reportFile;
	}

	public File getJsFiles() {
		return jsFiles;
	}

	public void setJsFiles(File jsFiles) {
		this.jsFiles = jsFiles;
	}
	

	public String getReportDescription() {
		return reportDescription;
	}

	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}
	

	public List<VoltDegree> getVoltDegreeList() {
		return voltDegreeList;
	}
	

	public int getDefBool() {
		return defBool;
	}

	public void setDefBool(int defBool) {
		this.defBool = defBool;
	}

	/**
	 * ------------------------------------报表模板----------------------------------------------- */
	/**
	 * 根据传入的报表模板类型查询报表名称
	 * 
	 * @param reportType
	 * @return
	 */
	public String queryReportTemplateList() {
		try {
			logger.debug("queryReportTemplateList start");
			repTemList = iReportTypeQueryManager.queryReportTemplateList();
			logger.debug("queryReportTemplateList start");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 根据传入的报表模板类型查询报表名称
	 * 
	 * @param reportType
	 * @return
	 */
	public String queryReportTemplateByType() {
		try {
			logger.debug("queryReportTemplateByType start");
			repTemList = iReportTypeQueryManager
					.queryReportTemplateByType(this.reportType);
			logger.debug("queryReportTemplateByType start");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**--------------------------------------------------报表文件上传------------------------------------------------*/
	/**
	 * 上传文件
	 */
	public String saveReport()
	{
		logger.debug("saveReport start");
		iReportTypeQueryManager
		.saveReport(this.reportType,reportName,reportDescription,reportFile,jsFiles);
		
		logger.debug("saveReport end");
		return SUCCESS;
	}
	/**
	 * 修改文件
	 */
	public String updateReport()
	{
		logger.debug("updateReport start");
		iReportTypeQueryManager
		.updateReport(this.reportId,this.reportType,reportName,reportDescription,reportFile,jsFiles,defBool);		
		logger.debug("updateReport end");
		return SUCCESS;
	}

	/*----------------------------------------------------------------------------------------------------------------------------------*/
	
	
	
	/**
	 * 保存模板用户或者供电单位的信息
	 * 
	 * @return
	 */

	public String saveConsNoList() {
		try {
			logger.debug("saveConsList start");
			iReportTypeQueryManager.saveConsNoList(reportId,reportType, reportName,
					consNoList, reportParam, defBoolean);
			logger.debug("saveConsList start");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 删除报表或者报表模板
	 * 
	 * @return
	 */
	public String deleteReport()
	{
		logger.debug("deleteReport start");
		iReportTypeQueryManager.deleteReport(reportId);
		logger.debug("deleteReport start");
		return SUCCESS;
		
	}

	/**
	 * 查询模板用户或者供电单位列表的信息
	 * 
	 * @return
	 */
	public String queryConsNoList() {
		try {
			logger.debug("queryConsNoList start");
			consInfoList = iReportTypeQueryManager.queryConsNoList(reportId);
			logger.debug("queryConsNoList start");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查询模板用户或者供电单位列表的信息
	 * 
	 * @return
	 */
	public String updateConsNoList()
{
	logger.debug("updateConsNoList start");
	iReportTypeQueryManager.updateConsNoList(reportId,reportType, reportName,consNoList, reportParam);
	logger.debug("updateConsNoList start");
	return SUCCESS;
	
}

	/**
	 * 查询报表列表信息
	 * 
	 * @param reportType
	 * @return
	 */
	public String queryReportInfoByType() {
		logger.debug("queryReportInfoByType start");
		reportList = iReportTypeQueryManager.queryReportInfoByType(reportType);
		logger.debug("queryReportInfoByType start");
		return SUCCESS;
	}

	/**
	 *根据报表ID查询报表文件
	 * 
	 * @param reportId
	 * @return
	 */
	public String queryJSFileById() {
		this.setBais(iReportTypeQueryManager.queryJSFile(reportId));
		int length = this.bais.available();
		byte[] byt = new byte[length];

		try {
			this.bais.read(byt);
			this.scriptConent = new String(byt,"UTF-8");
			logger.info(scriptConent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查询模板供電单位列表的信息
	 * 
	 * @return
	 */
	public String queryOrgNoList() {
		logger.debug("queryOrgNoList start");
		this.treeInfo = iReportTypeQueryManager.queryOrgNoList(this.reportId);
		logger.debug("queryOrgNoList end");
		return SUCCESS;
	}

	/*------------------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * 将供电单位保存到数据库
	 * 
	 * @param rawReportType
	 *            ,rawReportName,reportType, reportName, orgNoList, reportParam
	 * @return
	 */
	public String saveOrgNoListTemplate() {
		logger.debug("saveOrgNoListTemplate start");
		iReportTypeQueryManager.saveOrgInfoListTemplate(reportId,
				rawReportType, rawReportName, reportType, reportName,
				orgNoList, reportParam);
		logger.debug("saveOrgNoListTemplate start");
		return SUCCESS;

	}

	/**
	 * 查询报表m模板中的供电单位信息
	 * 
	 * @param reportType
	 *            ,reportName
	 * @return 报表文件
	 */
	public String queryTemplateOrgInfo() {
		logger.debug("queryTemplateOrgInfo start");
		this.setTreeInfo(iReportTypeQueryManager
				.queryTemplateOrgInfo(this.templateId));
		logger.debug("queryTemplateOrgInfo start");
		return SUCCESS;
	}

	/**
	 * 删除模板供电单位
	 * 
	 * @params templateId
	 */
	public String deleteOrgNoListTemplate(int templateId) {
		logger.debug("deleteOrgNoListTemplate start");
		iReportTypeQueryManager.deleteOrgNoListTemplate(templateId);
		logger.debug("deleteOrgNoListTemplate start");
		return SUCCESS;
	}

	/**
	 * 将供电单位列表保存到数据库
	 * 
	 * @params 
	 *         rawReportType,rawReportName,reportType,reportName,orgNoList,orgNoList
	 *         ,orgNoList,reportParam
	 */

	/**
	 * 将用户列表保存到数据库
	 * 
	 * @return
	 */
	public String saveConsNoListTemplate() {
		logger.debug("saveOrgNoListTemplate start");
		iReportTypeQueryManager.saveConsNoListTemplate(reportId, rawReportType,
				rawReportName, reportType, reportName, consNoList, reportParam);
		logger.debug("saveOrgNoListTemplate start");
		return SUCCESS;

	}

	/**
	 * 更新列表中的用户列表
	 * 
	 * @params templateId,reportType,reportName,consNoList,reportParam
	 */
	public String updateConsNoListTemplate() {
		logger.debug("updateConsNoListTemplate start");
		iReportTypeQueryManager.updateConsNoListTemplate(templateId,
				reportType, reportName, consNoList, reportParam);
		logger.debug("updateConsNoListTemplate start");
		return SUCCESS;

	}

	/**
	 * 删除模板用户列表
	 * 
	 * @params templateId
	 */
	public String deleteConsNoListTemplate() {
		logger.debug("deleteConsNoListTemplate start");
		iReportTypeQueryManager.deleteConsNoListTemplate(templateId);
		logger.debug("deleteConsNoListTemplate start");
		return SUCCESS;
	}

	/**
	 * 查询报表文件对应的参数页面JS代码
	 * 
	 * @param reportType
	 *            ,reportName
	 * @return 报表文件
	 */
	public String queryTemplateJSFile() {
		this.setBais(iReportTypeQueryManager.queryTemplateJSFile(reportType,
				reportName));
		int length = this.bais.available();
		byte[] byt = new byte[length];

		try {
			this.bais.read(byt);
			this.scriptConent = new String(byt, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查询报表m模板中的用户信息
	 * 
	 * @param reportType
	 *            ,reportName
	 * @return 报表文件
	 */
	public String queryTemplateConsInfo() {
		this.setConsInfoList(iReportTypeQueryManager
				.queryTemplateConsInfo(this.templateId));
		return SUCCESS;
	}

	/**
	 * ---------------------------------------------报表--------------------------
	 * -----------------------------------------------
	 */

	/**
	 * 根据传入的报表类型查询报表名称
	 * 
	 * @param reportType
	 * @return
	 */
	public String queryReportByType() {
		try {
			logger.debug("queryReportByType start");
			reportList = iReportTypeQueryManager.queryReportByType(reportType);
			logger.debug("queryReportByType start");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;

	}

	/**
	 * 模板保存到数据库
	 * 
	 * @param reportType
	 * @return
	 */
	public String saveTemplate2DB() {
		try {
			logger.debug("queryReportByType start");
			// reportList = iReportTypeQueryManager.saveTemplate2DB();
			logger.debug("queryReportByType start");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查询报表类型
	 * 
	 * @param reportType
	 * @return
	 */
	public String queryReportTypeList() throws Exception {
		logger.debug("queryReportTypeList start");
		this.setReportList(iReportTypeQueryManager.queryReportTypeList());
		logger.debug("queryReportTypeList end");
		return SUCCESS;
	}

	/**
	 * 查询具体报表
	 * 
	 * @param null
	 * @return 报表文件
	 */
	public String queryReport() {
		// TODO Auto-generated method stub
		logger.debug("queryReport start");
		if (null == iReportTypeQueryManager) {
			iReportTypeQueryManager = new ReportTypeQueryManagerImpl();
		}
		this.setBais(iReportTypeQueryManager.queryReport(reportId));
		logger.debug("queryReport end");
		return SUCCESS;
	}

	/**
	 * 报表中查询左边数的信息，加入到GRID中
	 * 
	 * @param null
	 * @return 报表文件
	 */
	public String queryLeftTreeInfo() {
		// TODO Auto-generated method stub
		logger.debug("queryLeftTreeInfo start");
		if (null == orgNo || "".equals(orgNo)) {
			return SUCCESS;
		}
		PSysUser pSysUser = getPSysUser();
		if (null == pSysUser) {
			return SUCCESS;
		}
		this.treeInfo = iReportTypeQueryManager.queryLeftTreeInfo(orgNo,
				orgType, pSysUser);
		// this.setTreeInfo(iReportTypeQueryManager.queryLeftTreeInfo(orgNo,
		// orgType, pSysUser));
		logger.debug("queryLeftTreeInfo end");
		return SUCCESS;
	}

	/**
	 * 查询报表对应的报表文件，并转换成字节流。
	 * 
	 * @param reportType
	 *            ,reportName
	 * @return 报表文件
	 */
	public String queryReportFile() {
		this.setBais(iReportTypeQueryManager
				.queryReport(reportType, reportName));
		return SUCCESS;
	}

	/**
	 * 查询报表文件对应的参数页面JS代码
	 * 
	 * @param reportType
	 *            ,reportName
	 * @return 报表文件
	 */
	public String queryJSFile() {
		this.setBais(iReportTypeQueryManager
				.queryJSFile(reportType, reportName));
		int length = this.bais.available();
		byte[] byt = new byte[length];

		try {
			this.bais.read(byt);
			this.scriptConent = new String(byt,"UTF-8");
			logger.debug(scriptConent);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查询电压等级
	 * 
	 * @param null
	 * @return 电压等级列表
	 */
	public String queryVoltDegree() {
		this.voltDegreeList=iReportTypeQueryManager.queryVoltDegree();
		return SUCCESS;
	}

	/**
	 * 查询通信方式
	 * 
	 * @param null
	 * @return 通信方式列表
	 */
	public String queryCommMode() {
		this.setCommMode(iReportTypeQueryManager.queryCommMode());
		return SUCCESS;
	}

	/**
	 * 查询运行容量
	 * 
	 * @param null
	 * @return 列表
	 */
	public String queryRunCap() {
		this.setRunCapList(iReportTypeQueryManager.queryRunCap());
		return SUCCESS;
	}

	/**
	 * @Override
	 */
	/**
	 * 报表生成的方法
	 * 
	 */
	/**
	 * public Report createReport(ReportletRequest req) throws
	 * ReportletException { //
	 * this.setBais(iReportTypeQueryManager.queryReport(reportType,
	 * reportName)); TemplateImporter templateImporter = new TemplateImporter();
	 * WorkBook workBook = null; try { workBook = (WorkBook)
	 * templateImporter.generate(bais); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } Parameter[] pars =
	 * workBook.getParameters(); for (int i = 0; i < pars.length; i++) {
	 * Parameter par = pars[i]; if (par.getName() == "day") {
	 * par.setValue(date); } if (par.getName() == "userList") {
	 * par.setValue(userList); } } return (Report) workBook; }
	 */

}
