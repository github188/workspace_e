package com.nari.qrystat.queryReport.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.queryReport.IReportTypeQueryManager;
import com.nari.qrystat.reportQuery.CommMode;
import com.nari.qrystat.reportQuery.ConsInfo;
import com.nari.qrystat.reportQuery.IReportTypeQueryManDao;
import com.nari.qrystat.reportQuery.LeftTreeInfo;
import com.nari.qrystat.reportQuery.OrgInfo;
import com.nari.qrystat.reportQuery.ReportJdbc;
import com.nari.qrystat.reportQuery.ReportTemplat;
import com.nari.qrystat.reportQuery.RunCap;
import com.nari.qrystat.reportQuery.VoltDegree;
import com.nari.qrystat.reportQuery.impl.ReportTypeQueryManDaoImpl;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;
/**
 * 服务实现类 ReportTypeQueryManagerImpl
 * 
 * @author chengzh
 * @describe 报表查询服务类实现
 */
public class ReportTypeQueryManagerImpl implements IReportTypeQueryManager {
	IReportTypeQueryManDao iReportTypeQueryManDao;
     public IReportTypeQueryManDao getiReportTypeQueryManDao() {
		return iReportTypeQueryManDao;
	}
	public void setiReportTypeQueryManDao(
			IReportTypeQueryManDao iReportTypeQueryManDao) {
		this.iReportTypeQueryManDao = iReportTypeQueryManDao;
	}
	/**
      * 通过reportType参数查询报表列表
      */
	@Override
	public List<ReportJdbc> queryReportByType(String reportType)
			throws Exception {
		try{
			return iReportTypeQueryManDao.queryReportListByType(reportType);
			
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.RUE_ARCHIVEMANQUERYCMETERBYCONSEXCEPTION);
		}
		
	}
	/**
	 * 查询报表类型
	 * @param null
	 * @return 报表类型列表
	 */
	public List<ReportJdbc> queryReportTypeList() throws Exception {
		// TODO Auto-generated method stub
		return iReportTypeQueryManDao.queryReportTypeList();
	}
	/**
	 * 查询具体报表
	 * @param null
	 * @return 报表文件
	 */
	@Override
	public ByteArrayInputStream queryReport(String reportType, String reportName) {
		// TODO Auto-generated method stub
		if(null == iReportTypeQueryManDao){
			iReportTypeQueryManDao = new ReportTypeQueryManDaoImpl();
		}
		return iReportTypeQueryManDao.queryReport(reportType, reportName);
	}
	/**
	 * 查询每个报表类型对应的JS文件
	 * @param null
	 * @return 报表文件
	 */	
	@Override
	public ByteArrayInputStream queryJSFile(String reportType, String reportName) {
		// TODO Auto-generated method stub
		return iReportTypeQueryManDao.queryJSFile(reportType, reportName);
	}
	
	public ByteArrayInputStream queryReport(int reportId){
		if(null == iReportTypeQueryManDao){
			iReportTypeQueryManDao = new ReportTypeQueryManDaoImpl();
		}
		return iReportTypeQueryManDao.queryReport(reportId);
	}
	/**
	 * 报表中查询左边数 的信息，加入到GRID中
	 * @param orgNo
	 * @param  orgType
	 * @param pSysUser
	 * @return 报表文件
	 */	
	@Override	
	public List<LeftTreeInfo> queryLeftTreeInfo(String orgNo, String orgType,
			PSysUser pSysUser) {
		// TODO Auto-generated method stub
		return iReportTypeQueryManDao.queryLeftTreeInfo(orgNo, orgType, pSysUser);
	}
	/**
	 * 查询电压等级
	 * @param null
	 * @return 电压等级列表
	 */	
	public List<VoltDegree> queryVoltDegree(){
		return iReportTypeQueryManDao.queryVoltDegree();
	}
	
	/**
	 * 查询通信方式
	 * @param null
	 * @return 通信方式列表
	 */	
	public List<CommMode> queryCommMode()
	{
		return iReportTypeQueryManDao.queryCommMode();
		
	}
	/**
	 * 查询运行容量
	 * 
	 * @param null
	 * @return 列表
	 */	
	public List<RunCap> queryRunCap()
	{
		return iReportTypeQueryManDao.queryRunCap();
	}
	/**
	 * 将用户列表保存到数据库 
	 * @return
	 */
	public void saveConsNoListTemplate(int reportId,String rawReportType,String rawReportName,String reportType,String reportName,String consNoList,String reportParam)
	{
		iReportTypeQueryManDao.saveConsNoListTemplate(reportId,rawReportType,rawReportName,reportType, reportName, consNoList, reportParam);
	}
	
	/**
	 * 更新列表中的用户列表
	 * @params templateId,reportType,reportName,consNoList,reportParam
	 */
	public void updateConsNoListTemplate(int templateId,String reportType, String reportName,
			String consNoList, String reportParam){
		iReportTypeQueryManDao.updateConsNoListTemplate(templateId,reportType, reportName, consNoList, reportParam);
	}
	/**
	 * 保存供电单位列表
	 * @params  rawReportType, rawReportName, reportType, reportName, orgNoList, reportParam
	 */
	@Override
	public void saveOrgInfoListTemplate(int reportId,String rawReportType,
			String rawReportName, String reportType, String reportName,
			String orgNoList, String reportParam) {
		iReportTypeQueryManDao.saveOrgInfoListTemplate(reportId,rawReportType, rawReportName, reportType, reportName, orgNoList, reportParam);// TODO Auto-generated method stub
		
	}
	/**
	 * 查询报表m模板中的供电单位信息
	 * @param reportType,reportName
	 * @return 报表文件
	 */	
	public List<LeftTreeInfo> queryTemplateOrgInfo(int templateId)
	{
		return iReportTypeQueryManDao.queryTemplateOrgInfo(templateId);
	}
	/**
	 * 删除模板供电单位
	 * @params templateId
	 */
	public void deleteOrgNoListTemplate(int templateId)
	{
		iReportTypeQueryManDao.deleteOrgNoListTemplate(templateId);
	}

	/**
	 * 根据传入的报表模板类型查询报表名称
	 * 
	 * @param reportType
	 * @return
	 */
	public List<ReportTemplat> queryReportTemplateByType(String reportType){
		return iReportTypeQueryManDao.queryReportTemplateByType(reportType);
		
	}
	/**
	 * 查询报表模板类型列表
	 * 
	 * @param reportType
	 * @return
	 */
	public List<ReportTemplat> queryReportTemplateList()
	{
		return iReportTypeQueryManDao.queryReportTemplateList();
	}
	/**
	 * 查询报表文件对应的参数页面JS代码
	 * @param reportType,reportName
	 * @return 报表文件
	 */	
	public ByteArrayInputStream queryTemplateJSFile(String reportType,String reportName){
		return iReportTypeQueryManDao.queryTemplateJSFile(reportType, reportName);
	}
	/**
	 * 查询报表m模板中的用户信息
	 * @param reportType,reportName
	 * @return 报表文件
	 */	
	public List<ConsInfo> queryTemplateConsInfo(int templateId){
		return iReportTypeQueryManDao.queryTemplateConsInfo(templateId);
	}
	/**
	 * 删除模板用户列表
	 * @params templateId
	 */
	public void deleteConsNoListTemplate(int templateId){
		iReportTypeQueryManDao.deleteConsNoListTemplate(templateId);
	}
	/**
	 * 保存模板用户或者供电单位的信息
	 * @return
	 */
	public void saveConsNoList(int reportId,String reportType, String reportName,
			String consNoList, String reportParam,  int defBoolean)
	{
		iReportTypeQueryManDao.saveConsNoList(reportId,reportType, reportName,consNoList,reportParam,defBoolean);
	}
	/**
	 * 查询模板用户或者供电单位列表的信息
	 * @return
	 */
	public List<ConsInfo> queryConsNoList(int reportId)
	{
		return iReportTypeQueryManDao.queryConsNoList(reportId);
	}
	/**
	 * 查询报表列表信息
	 * @param reportType
	 * @return
	 */

	public List<ReportJdbc> queryReportInfoByType(String reportType)
	{
		return iReportTypeQueryManDao.queryReportInfoByType(reportType);
	}
	/**
	 *根据报表ID查询报表文件
	 * @param reportId
	 * @return
	 */
	public ByteArrayInputStream queryJSFile(int reportId)
	{
		return iReportTypeQueryManDao.queryJSFile(reportId);
	}
	/**
	 * 查询模板供電单位列表的信息
	 * @return
	 */
	public List<LeftTreeInfo> queryOrgNoList(int reportId)
	{
		return iReportTypeQueryManDao.queryOrgNoList(reportId);
	}
	/**
	 * 更新模板用户或者供电单位列表的信息
	 * 
	 * @return
	 */	
	public void updateConsNoList(int reportId,String reportType, String reportName,
			String consNoList, String reportParam)
	{
		iReportTypeQueryManDao.updateConsNoList(reportId,reportType,reportName,consNoList,reportParam);
	}
	/**
	 * 删除报表或者报表模板
	 * @param reportId
	 * @return
	 */
	public void deleteReport(int reportId)
	{
		iReportTypeQueryManDao.deleteReport(reportId);
	}
	/**
	 * 上传文件
	 * @param reportType,reportName,reportDescription,reportFile,jsFiles
	 */
	public void saveReport(String reportType, String reportName,
			String reportDescription, File reportFile, File jsFiles)
	{
		iReportTypeQueryManDao.saveReport(reportType,reportName,reportDescription,reportFile,jsFiles);
	}
	/**
	 * 修改文件
	 * @param reportId,reportType,reportName,reportDescription,reportFile,jsFiles
	 */
	public void updateReport(int reportId, String reportType,
			String reportName, String reportDescription, File reportFile,
			File jsFiles,int defBool)
	{
		iReportTypeQueryManDao.updateReport(reportId,reportType,reportName,reportDescription,reportFile,jsFiles,defBool);
	}
}

