package com.nari.qrystat.queryReport;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.reportQuery.CommMode;
import com.nari.qrystat.reportQuery.ConsInfo;
import com.nari.qrystat.reportQuery.LeftTreeInfo;
import com.nari.qrystat.reportQuery.OrgInfo;
import com.nari.qrystat.reportQuery.ReportJdbc;
import com.nari.qrystat.reportQuery.ReportTemplat;
import com.nari.qrystat.reportQuery.RunCap;
import com.nari.qrystat.reportQuery.VoltDegree;

/**
 * 查询接口
 * 
 * @author ChenGuoZhang
 * @describe 报表类型查询接口
 */
public interface IReportTypeQueryManager {
	/**
	 * 方法queryReportByType
	 * 
	 * @param consNo
	 * @return　通过reportType 查询ReportJdbc列表
	 * @throws Exception
	 */
	public List<ReportJdbc> queryReportByType(String reportType)
			throws Exception;

	/**
	 * 查询报表类型
	 * 
	 * @param null
	 * @return 报表类型列表
	 */
	public List<ReportJdbc> queryReportTypeList() throws Exception;

	/**
	 * 查询具体报表
	 * 
	 * @param null
	 * @return 报表文件
	 */
	public ByteArrayInputStream queryReport(String reportType, String reportName);

	/**
	 * 查询每个报表类型对应的JS文件
	 * 
	 * @param reportType
	 * @param reportName
	 * @return 报表文件
	 */
	public ByteArrayInputStream queryJSFile(String reportType, String reportName);

	/**
	 * 查询每个报表类型对应的JS文件
	 * 
	 * @param reportId
	 * @return 报表文件
	 */
	public ByteArrayInputStream queryReport(int reportId);

	/**
	 * 报表中查询左边数 的信息，加入到GRID中
	 * 
	 * @param orgNo
	 * @param orgType
	 * @param pSysUser
	 * @return 报表文件
	 */
	public List<LeftTreeInfo> queryLeftTreeInfo(String orgNo, String orgType,
			PSysUser pSysUser);

	/**
	 * 查询电压等级
	 * 
	 * @param null
	 * @return 电压等级列表
	 */
	public List<VoltDegree> queryVoltDegree();

	/**
	 * 查询通信方式
	 * 
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
	 * 将用户列表保存到数据库
	 * 
	 * @param reportId
	 * @return
	 */
	public void saveConsNoListTemplate(int reportId, String rawReportType,
			String rawReportName, String reportType, String reportName,
			String consNoList, String reportParam);

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
	 * 
	 * @param reportType
	 *            ,reportName
	 * @return 报表文件
	 */
	public ByteArrayInputStream queryTemplateJSFile(String reportType,
			String reportName);

	/**
	 * 查询报表m模板中的用户信息
	 * 
	 * @param reportType
	 *            ,reportName
	 * @return 报表文件
	 */
	public List<ConsInfo> queryTemplateConsInfo(int templateId);

	/**
	 * 更新列表中的用户列表
	 * 
	 * @params templateId,reportType,reportName,consNoList,reportParam
	 */
	public void updateConsNoListTemplate(int templateId, String reportType,
			String reportName, String consNoList, String reportParam);

	/**
	 * 删除模板用户列表
	 * 
	 * @params templateId
	 */
	public void deleteConsNoListTemplate(int templateId);

	/**
	 * 查询报表m模板中的供电单位信息
	 * 
	 * @param reportType
	 *            ,reportName
	 * @return 报表文件
	 */
	public List<LeftTreeInfo> queryTemplateOrgInfo(int templateId);

	/**
	 * 删除模板供电单位
	 * 
	 * @params templateId
	 */
	public void deleteOrgNoListTemplate(int templateId);

	/**
	 * 将供电单位列表保存到数据库
	 * 
	 * @param reportId
	 * @params 
	 *         rawReportType,rawReportName,reportType,reportName,orgNoList,orgNoList
	 *         ,orgNoList,reportParam
	 */
	public void saveOrgInfoListTemplate(int reportId,
			final String rawReportType, final String rawReportName,
			final String reportType, final String reportName,
			final String orgNoList, final String reportParam);

	/**
	 * 保存模板用户或者供电单位的信息
	 * 
	 * @param jsFile
	 * @param reportContent
	 * @param defBoolean
	 * @return
	 */
	public void saveConsNoList(int reportId,String reportType, String reportName,
			String consNoList, String reportParam,  int defBoolean);

	/**
	 * 查询模板用户或者供电单位列表的信息
	 * 
	 * @return
	 */
	public List<ConsInfo> queryConsNoList(int reportId);

	/**
	 * 查询报表列表信息
	 * 
	 * @param reportType
	 * @return
	 */
	public List<ReportJdbc> queryReportInfoByType(String reportType);

	/**
	 *根据报表ID查询报表文件
	 * 
	 * @param reportId
	 * @return
	 */
	public ByteArrayInputStream queryJSFile(int reportId);

	/**
	 * 查询模板供電单位列表的信息
	 * 
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
	 * @param reportId
	 * @return
	 */
	public void deleteReport(int reportId);
	/**
	 * 上传文件
	 * @param reportType,reportName,reportDescription,reportFile,jsFiles
	 */
	public void saveReport(String reportType, String reportName,
			String reportDescription, File reportFile, File jsFiles);
	/**
	 * 修改文件
	 * @param reportId,reportType,reportName,reportDescription,reportFile,jsFiles
	 */
	public void updateReport(int reportId, String reportType,
			String reportName, String reportDescription, File reportFile,
			File jsFiles,int defBool);

}
