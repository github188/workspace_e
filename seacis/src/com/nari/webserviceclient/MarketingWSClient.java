package com.nari.webserviceclient;

import java.util.List;

/**
 * 用电信息采集系统调用营销系统的WEBSERVICE客户端
 */
@SuppressWarnings("unchecked")
public interface MarketingWSClient {
	
	/**
	 * 代码分类实时查询
	 * 
	 * @param xml
	 * @return resultXml
	 * @throws Exception 
	 * 
	 */
	public List qryDataPcodeSort(String xml);
	
	/**
	 * 标准代码信息实时查询
	 * 
	 * @param xml
	 * @return resultXml
	 * @throws Exception 
	 * 
	 */
	public List qryDataPcode(String xml);
	
	/**
	 * 客户信息实时查询
	 * 
	 * @param xml
	 * @return resultXml
	 * @throws Exception 
	 * 
	 */
	public List qryDataCust(String xml);
	
	/**
	 * 用户基本信息实时查询
	 * 
	 * @param xml
	 * @return resultXml
	 * @throws Exception 
	 * 
	 */
	public List qryDataCons(String xml);
	
	/**
	 * 用户联系信息实时查询
	 * 
	 * @param xml
	 * @return resultXml
	 * @throws Exception 
	 * 
	 */
	public List qryDataContact(String xml);

	/**
	 * 调试结果信息反馈通知服务
	 * 
	 * @param xml
	 * @return resultXml
	 * 
	 */
	public int backFeedTmnlDebugInfo(String xmlString);
	
	/**
	 * 预购电控制执行结果服务
	 * 
	 * @param xml
	 * @return resultXml
	 * @throws Exception 
	 * 
	 */
	public int prepayForResult(String xml);
	
	/**
	 * 催费控制执行结果服务
	 * 
	 * @param xml
	 * @return resultXml
	 * @throws Exception 
	 * 
	 */
	public int crcyForResult(String xml);
	
	/**
	 * 营业报停执行结果服务
	 * 
	 * @param xml
	 * @return resultXml
	 * @throws Exception 
	 * 
	 */
	public int stopCtrlForResult(String xml);
	
	/**
	 * 采集任务执行信息通知服务
	 * 
	 * @param xml
	 * @return resultXml
	 * @throws Exception 
	 * 
	 */
	public int rTaskExec(String xml);
	
	/**
	 * 异常告警信息服务
	 * 
	 * @param xml
	 * @return resultXml
	 * @throws Exception 
	 * 
	 */
	public int rExcpAlarm(String xml);
	
	/**
	 * 采集点控制参数通知服务
	 * 
	 * @param xml
	 * @return resultXml
	 * @throws Exception 
	 * 
	 */
	public int cpCtrlPara(String xml);
}