package com.nari.qrystat.reportQuery;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import com.nari.privilige.PSysUser;

/**
 * 报表查询DAo
 * @author chengzh
 * @see  ReportTypeQueryManDaoImpl 
 */
public interface IReportTypeQueryManDao {
	/**
	 * 根据报表类型查询报表列表
	 * @param 报表类型
	 * @return 报表列表
	 */
	public List<ReportJdbc> queryReportListByType(String reportType);
	/**
	 * 查询报表类型
	 * @param null
	 * @return 报表类型列表
	 */
	public List<ReportJdbc> queryReportTypeList();
	/**
	 * 查询具体报表
	 * @param null
	 * @return 报表文件
	 */
	public ByteArrayInputStream queryReport(String reportType,String reportName);
	/**
	 * 查询每个报表类型对应的JS文件
	 * @param null
	 * @return 报表文件
	 */
	public ByteArrayInputStream queryJSFile(String reportType, String reportName);
	
	/**
	 * 查询具体报表
	 * @param null
	 * @return 报表文件
	 */
	public ByteArrayInputStream queryReport(int reportId);
	/**
	 * 报表中查询左边数 的信息，加入到GRID中
	 * @param null
	 * @return 报表文件
	 */
	public List<LeftTreeInfo> queryLeftTreeInfo(String orgNo, String orgType, PSysUser pSysUser);
	/**
	 * 查询电压等级
	 * @param null
	 * @return 电压等级列表
	 */	
	public List<VoltDegree> queryVoltDegree();
	/**
	 * 查询通信方式
	 * @param null
	 * @return 通信方式列表
	 */	
	public List<CommMode> queryCommMode();
	
	/**
	 * 查询运行容量
	 * 
	 * @param null
	 * @return 列表
	 */	
	public List<RunCap> queryRunCap();
	/**
	 * 把模板保存到数据库
	 * 
	 * @param null
	 * @return 列表
	 */	
	public void saveTemplate2DB();
	/**
	 * 将用户列表保存到数据库 
	 * @param reportId 
	 * @return
	 */
	public void saveConsNoListTemplate(int reportId, String rawReportType,String rawReportName,String reportType,String reportName,String consNoList,String reportParam);
	/**
	 * 将供电单位列表列表保存到数据库 
	 * @return
	 */
	public void saveOrgNoListTemplate(String rawReportType,String rawReportName,String reportType,String reportName,String orgNoList,String reportParam);
	/**
	 * 根据传入的报表模板类型查询报表名称
	 * 
	 * @param reportType
	 * @return
	 */
	public List<ReportTemplat> queryReportTemplateByType(String reportType);
	/**
	 * 查询报表模板类型列表
	 * 
	 * @param reportType
	 * @return
	 */
	public List<ReportTemplat> queryReportTemplateList();
	/**
	 * 查询报表文件对应的参数页面JS代码
	 * @param reportType,reportName
	 * @return 报表文件
	 */	
	public ByteArrayInputStream queryTemplateJSFile(String reportType,String reportName);
	/**
	 * 查询报表m模板中的用户信息
	 * @param reportType,reportName
	 * @return 报表文件
	 */
	//public List<ConsInfo> queryTemplateConsInfo(String consNoList);
	/**
	 * 查询报表m模板中的用户信息
	 * @param consNoList
	 * @return 报表文件
	 */
	public List<ConsInfo> queryTemplateConsInfo(int templateId);
	/**
	 * 更新列表中的用户列表
	 * @params templateId,reportType,reportName,consNoList,reportParam
	 */
	public void updateConsNoListTemplate(int templateId, String reportType,
			String reportName, String consNoList, String reportParam);
	/**
	 * 删除模板用户列表
	 * @params templateId
	 */
	public void deleteConsNoListTemplate(int templateId);
	/**
	 * 查询报表m模板中的供电单位信息
	 * @param reportType,reportName
	 * @return 报表文件
	 */	
	public List<LeftTreeInfo> queryTemplateOrgInfo(int templateId);
	/**
	 * 删除模板供电单位
	 * @params templateId
	 */
	public void deleteOrgNoListTemplate(int templateId);
	/**
	 * 将供电单位列表保存到数据库 
	 * @param reportId 
	 * @params rawReportType,rawReportName,reportType,reportName,orgNoList,orgNoList,orgNoList,reportParam
	 */
	public void saveOrgInfoListTemplate(int reportId, final String rawReportType,final String rawReportName,final String reportType,final String reportName,final String orgNoList,final String reportParam);
	/**
	 * 保存模板用户或者供电单位的信息
	 * @param jsFile 
	 * @param reportContent 
	 * @return
	 */
	public void saveConsNoList(int reportId,String reportType, String reportName,
			String consNoList, String reportParam,  int defBoolean);
	/**
	 * 查询模板用户或者供电单位列表的信息
	 * @return
	 */
	public List<ConsInfo> queryConsNoList(int reportId);
	/**
	 * 查询报表列表信息
	 * @param reportType
	 * @return
	 */
	public List<ReportJdbc> queryReportInfoByType(String reportType);
	/**
	 *根据报表ID查询报表文件
	 * @param reportId
	 * @return
	 */
	public ByteArrayInputStream queryJSFile(int reportId);
	/**
	 * 查询模板供電单位列表的信息
	 * @return
	 */
	public List<LeftTreeInfo> queryOrgNoList(int reportId);
	/**
	 * 更新模板用户或者供电单位列表的信息
	 * @param reportId 
	 * 
	 * @return
	 */
	public void updateConsNoList(int reportId, String reportType, String reportName,
			String consNoList, String reportParam);
	/**
	 * 删除报表或者报表模板
	 * 
	 * @return
	 */
	public void deleteReport(int reportId);
	/**
	 * 上传文件
	 * @param reportType,reportName,reportDescription,reportFile,jsFiles
	 */
	public void saveReport(String reportType, String reportName,
	
	/**
	* 修改文件
	* @param reportId,reportType,reportName,reportDescription,reportFile,jsFiles
	*/String reportDescription, File reportFile, File jsFiles);
	public void updateReport(int reportId, String reportType,
			String reportName, String reportDescription, File reportFile,
			File jsFiles,int defBool);
	
}