package com.nari.runman.feildman;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.terminalparam.MeterChgInfoBean;
import com.nari.terminalparam.MeterCommBean;
import com.nari.terminalparam.MeterMaintainInfoBean;
import com.nari.terminalparam.TerminalChgInfoBean;
import com.nari.terminalparam.TerminalDebugInfoBean;
import com.nari.terminalparam.TerminalInstallBean;
import com.nari.terminalparam.TerminalInstallDetailBean;
import com.nari.terminalparam.TerminalRmvAndChgBean;
import com.nari.util.DateUtil;

/**
 * 终端安装Action处理类
 * @author 姜炜超
 */
public class TerminalInstallAction extends BaseAction{
	//定义日志
	private static final Logger logger = Logger.getLogger(TerminalInstallAction.class);
	
	private TerminalInstallManager terminalInstallManager;
	//成功返回标记
	public boolean success = true;
	
	private Date startDate;//开始日期
	private Date endDate;//结束日期
	private String interType;//接口类别
	private String orgNo;//供电单位编号
	private String consNo;//用电客户编号
	private Integer consType;//用户类别
	private String debugStatus;//调试状态
	private Date debugStartDate;//开始日期
	private Date debugEndDate;//结束日期
	private List<TerminalInstallBean> tiList;//终端装接列表
	private List<TerminalRmvAndChgBean> tiList2;//终端拆换列表
	
	//分页总条数
	public long totalCount;
	//分页入参
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private List<TerminalInstallDetailBean> tmnlInstDetList;//终端装接调试信息详细列表
	private List<TerminalDebugInfoBean> tmnlDebugInfoList;//终端调试信息列表
	private List<VwProtocolCode>  protocolList;//规约列表
	private List<TerminalChgInfoBean> tmnlChgInfoList;//终端变更信息列表
	private List<MeterChgInfoBean> meterChgInfoList;//电能表变更信息列表
	private List<MeterMaintainInfoBean> meterMaintainInfoList;//电能表维护信息列表
	private List<MeterCommBean> meterCommInfoList;//电能表通讯信息列表
	
	private String appNo;//申请号
	private int succFlag;//是否成功 1表示成功，0表示失败，2表示全部
	private String tmnlId;//终端id
	private String protocolCode;//规约号
	private String sendUpMode;//上送方式
	private List<String>  appList;//前台获取的终端列表
	private List<TerminalInstallBean> tiStatusList;//终端装接列表
	
	/**
	 * 终端地址码  删除标记 @author chunmingli
	 */
	//终端地址
	private String tmnlAddr;
	//流程节点
	private String flowNode;
	//删除标记
	private int flag;

	private String[] appNos;
	
	/**
	 *  the flowNode
	 * @return the flowNode
	 */
	public String getFlowNode() {
		return flowNode;
	}

	/**
	 *  the flowNode to set
	 * @param flowNode the flowNode to set
	 */
	public void setFlowNode(String flowNode) {
		this.flowNode = flowNode;
	}

	/**
	 *  the flag
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 *  the flag to set
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 *  the tmnlAddr
	 * @return the tmnlAddr
	 */
	public String getTmnlAddr() {
		return tmnlAddr;
	}

	/**
	 *  the tmnlAddr to set
	 * @param tmnlAddr the tmnlAddr to set
	 */
	public void setTmnlAddr(String tmnlAddr) {
		this.tmnlAddr = tmnlAddr;
	}

	/**
	 * 加载终端装接数据
	 * @return String 
	 * @throws Exception
	 */
    public String loadGridData() throws Exception{
    	logger.debug("查询终端装接开始");
    	PSysUser pSysUser = getPSysUser();
    	if(null == pSysUser || null == startDate || null == endDate){
    		return SUCCESS;
    	}
    	tiList = terminalInstallManager.findTmnlInstallInfo(pSysUser, DateUtil.dateToString(startDate), 
    			DateUtil.dateToString(endDate), interType);
    	tiList2 = terminalInstallManager.findTmnlRmvAndChgInfo(pSysUser, DateUtil.dateToString(startDate), 
    			DateUtil.dateToString(endDate), interType);
    	tiStatusList = new ArrayList<TerminalInstallBean>();
    	//获取成功，失败等统计值
    	if(null != tiList && 0 < tiList.size()){
    		Long succ = 0L;
    		Long fail = 0L;
    		Long init = 0L;
    		Long dealing = 0L;
    		Long synFailed = 0L;
    		for(int i = 0; i < tiList.size(); i++){
    			if(null != tiList.get(i).getSuccNum()){
    			    succ += tiList.get(i).getSuccNum();
    			}
    			if(null != tiList.get(i).getFailedNum()){
    			    fail += tiList.get(i).getFailedNum();
    			}
    			if(null != tiList.get(i).getInitNum()){
    			    init += tiList.get(i).getInitNum();
    			}
    			if(null != tiList.get(i).getDealingNum()){
    			    dealing += tiList.get(i).getDealingNum();
    			}
    			if(null != tiList.get(i).getSynFailedNum()){
    			    synFailed += tiList.get(i).getSynFailedNum();
    			}
    		}
    		if(0 != succ){
    			TerminalInstallBean bean = new TerminalInstallBean();
    			bean.setStatusName("成功");
    			bean.setStatusNum(succ);
    			tiStatusList.add(bean);
    		}
    		if(0 != fail){
    			TerminalInstallBean bean = new TerminalInstallBean();
    			bean.setStatusName("失败");
    			bean.setStatusNum(fail);
    			tiStatusList.add(bean);
    		}
    		if(0 != init){
    			TerminalInstallBean bean = new TerminalInstallBean();
    			bean.setStatusName("未测试");
    			bean.setStatusNum(init);
    			tiStatusList.add(bean);
    		}
    		if(0 != dealing){
    			TerminalInstallBean bean = new TerminalInstallBean();
    			bean.setStatusName("处理中");
    			bean.setStatusNum(dealing);
    			tiStatusList.add(bean);
    		}
    		if(0 != synFailed){
    			TerminalInstallBean bean = new TerminalInstallBean();
    			bean.setStatusName("建档失败");
    			bean.setStatusNum(synFailed);
    			tiStatusList.add(bean);
    		}
    	}
    	logger.debug("查询终端装接结束");
    	return SUCCESS;
    }
    
    /**
	 * 加载终端装接调试详细数据，弹出tab页调用
	 * @return String 
	 * @throws Exception
	 */
    public String loadDetailGridData() throws Exception{
    	logger.debug("查询终端装接调试开始");
    	if(null == orgNo || null == debugStartDate || null == debugEndDate || null == interType){
    		return SUCCESS;
    	}
    	tmnlInstDetList = terminalInstallManager.findConsTmnlDebugInfo(orgNo, 
    			DateUtil.dateToString(debugStartDate), DateUtil.dateToString(debugEndDate), 
    			consNo, debugStatus, appNo, interType);
    	if(null != tmnlInstDetList ){
    	    totalCount = tmnlInstDetList.size();
    	}else{
    		totalCount = 0;
    	}
    	logger.debug("查询终端装接调试结束");
    	return SUCCESS;
    }
    
    /**
	 * 加载终端调试信息，弹出窗口调用
	 * @return String 
	 * @throws Exception
	 */
    public String loadTmnlDebugInfoGridData() throws Exception{
    	logger.debug("查询终端调试信息开始");
    	if(null == appNo){
    		return SUCCESS;
    	}
    	Page<TerminalDebugInfoBean> psc= terminalInstallManager.findTmnlDebugInfo(appNo, consType, start ,limit);
    	tmnlDebugInfoList = psc.getResult();
    	totalCount = psc.getTotalCount();
    	logger.debug("查询终端调试信息结束");
    	return SUCCESS;
    }
    /**
     * 删除弹出窗口终端调试信息终端
     * @return
     * @throws Exception
     */
	public String delTmnlDebugInfo() throws Exception {
		logger.debug("delTmnlDebugInfo()  删除弹出窗口终端调试信息终端开始");
//		if (null == tmnlAddr || flowNode == null) {
//			return SUCCESS;
//		}
		try {
			terminalInstallManager.delTmnlDebugInfo(tmnlAddr,flowNode);
			this.flag = 1;
		} catch (Exception e) {
			this.flag = 0;
			logger.error("delTmnlDebugInfo()  删除弹出窗口终端调试信息终端操作错误  \n ");
			throw e;
		}
		logger.debug("delTmnlDebugInfo()  删除弹出窗口终端调试信息终端结束");
		return SUCCESS;
	}
    
    /**
	 * 查询规约信息，弹出窗口调用
	 * @return String 
	 * @throws Exception
	 */
	public String findProtocalCodeInfo() throws Exception{
		logger.debug("查询规约信息开始");
		protocolList = terminalInstallManager.findProtocalCodeInfo();
    	logger.debug("查询规约信息结束");
    	return SUCCESS;
	}
	
	/**
	 * 更新终端信息，弹出窗口调用
	 * @return String 
	 * @throws Exception
	 */
	public String saveTmnlInfo() throws Exception{
		logger.debug("更新终端信息开始");
		if(null == tmnlId){
			return SUCCESS;
		}
		super.addUserLog("修改终端信息", "终端信息维护", "04");
		terminalInstallManager.saveTmnlInfo(tmnlId,protocolCode,sendUpMode);
    	logger.debug("更新终端信息结束");
    	return SUCCESS;
	}
	
    /**
	 * 加载终端变更信息，弹出窗口调用
	 * @return String 
	 * @throws Exception
	 */
    public String loadTmnlChgInfoGridData() throws Exception{
    	logger.debug("查询终端变更信息开始");
    	if(null == appNo){
    		return SUCCESS;
    	}
    	tmnlChgInfoList = terminalInstallManager.findTmnlChgInfo(appNo);
    	
    	logger.debug("查询终端变更信息结束");
    	return SUCCESS;
    }
    
    /**
	 * 加载电能表变更信息，弹出窗口调用
	 * @return String 
	 * @throws Exception
	 */
    public String loadMeterChgInfoGridData() throws Exception{
    	logger.debug("查询电能表变更信息开始");
    	if(null == appNo){
    		return SUCCESS;
    	}
    	meterChgInfoList = terminalInstallManager.findMeterChgInfo(appNo);
    	
    	logger.debug("查询电能表变更信息结束");
    	return SUCCESS;
    }
    
    /**
	 * 手工触发
	 * @return String 
	 * @throws Exception
	 */
    public String handEvent() throws Exception{
    	logger.debug("手工触发开始");
    	if(null == appList || 0 == appList.size()){
    		return SUCCESS;
    	}
    	for(int i =0 ;i < appList.size(); i++){
    		System.out.println(appList.get(i));
    	}
    	int flag = terminalInstallManager.handEvent(appList);
    	logger.debug("手工触发结束");
    	return SUCCESS;
    }
    
    /**
	 * 加载电能表变更信息，弹出窗口调用
	 * @return String 
	 * @throws Exception
	 */
    public String loadMeterMaintainInfoData() throws Exception{
    	logger.debug("查询电能表维护信息开始");
    	if(null == appNo){
    		return SUCCESS;
    	}
    	Page<MeterMaintainInfoBean> psc = terminalInstallManager.findMeterMaintainInfo(appNo,succFlag,start,limit);
    	meterMaintainInfoList = psc.getResult();
    	totalCount = psc.getTotalCount();
    	logger.debug("查询电能表维护信息结束");
    	return SUCCESS;
    }
    
    /**
	 * 加载电能表变更通讯信息，弹出窗口调用
	 * @return String 
	 * @throws Exception
	 */
    public String loadMeterCommInfoData() throws Exception{
    	logger.debug("查询电能表通讯信息开始");
    	meterCommInfoList = terminalInstallManager.findMeterCommInfo();
    	logger.debug("查询电能表通讯信息结束");
    	return SUCCESS;
    }
    /**黄轩加，重置状态**/
    public String resetState(){
    	try {
			terminalInstallManager.updateResetState(getPSysUser(), appNos);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    	return SUCCESS;
    }
    
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setTerminalInstallManager(
			TerminalInstallManager terminalInstallManager) {
		this.terminalInstallManager = terminalInstallManager;
	}

	public List<TerminalInstallBean> getTiList() {
		return tiList;
	}

	public void setTiList(List<TerminalInstallBean> tiList) {
		this.tiList = tiList;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getDebugStatus() {
		return debugStatus;
	}

	public void setDebugStatus(String debugStatus) {
		this.debugStatus = debugStatus;
	}

	public Date getDebugStartDate() {
		return debugStartDate;
	}

	public void setDebugStartDate(Date debugStartDate) {
		this.debugStartDate = debugStartDate;
	}

	public Date getDebugEndDate() {
		return debugEndDate;
	}

	public void setDebugEndDate(Date debugEndDate) {
		this.debugEndDate = debugEndDate;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<TerminalInstallDetailBean> getTmnlInstDetList() {
		return tmnlInstDetList;
	}

	public void setTmnlInstDetList(List<TerminalInstallDetailBean> tmnlInstDetList) {
		this.tmnlInstDetList = tmnlInstDetList;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public List<TerminalDebugInfoBean> getTmnlDebugInfoList() {
		return tmnlDebugInfoList;
	}

	public void setTmnlDebugInfoList(List<TerminalDebugInfoBean> tmnlDebugInfoList) {
		this.tmnlDebugInfoList = tmnlDebugInfoList;
	}

	public List<VwProtocolCode> getProtocolList() {
		return protocolList;
	}

	public void setProtocolList(List<VwProtocolCode> protocolList) {
		this.protocolList = protocolList;
	}

	public String getTmnlId() {
		return tmnlId;
	}

	public void setTmnlId(String tmnlId) {
		this.tmnlId = tmnlId;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getSendUpMode() {
		return sendUpMode;
	}

	public void setSendUpMode(String sendUpMode) {
		this.sendUpMode = sendUpMode;
	}

	public List<TerminalChgInfoBean> getTmnlChgInfoList() {
		return tmnlChgInfoList;
	}

	public void setTmnlChgInfoList(List<TerminalChgInfoBean> tmnlChgInfoList) {
		this.tmnlChgInfoList = tmnlChgInfoList;
	}

	public List<MeterChgInfoBean> getMeterChgInfoList() {
		return meterChgInfoList;
	}

	public void setMeterChgInfoList(List<MeterChgInfoBean> meterChgInfoList) {
		this.meterChgInfoList = meterChgInfoList;
	}

	public List<String> getAppList() {
		return appList;
	}

	public void setAppList(List<String> appList) {
		this.appList = appList;
	}

	public List<MeterMaintainInfoBean> getMeterMaintainInfoList() {
		return meterMaintainInfoList;
	}

	public void setMeterMaintainInfoList(
			List<MeterMaintainInfoBean> meterMaintainInfoList) {
		this.meterMaintainInfoList = meterMaintainInfoList;
	}

	public List<MeterCommBean> getMeterCommInfoList() {
		return meterCommInfoList;
	}

	public void setMeterCommInfoList(List<MeterCommBean> meterCommInfoList) {
		this.meterCommInfoList = meterCommInfoList;
	}

	public List<TerminalInstallBean> getTiStatusList() {
		return tiStatusList;
	}

	public void setTiStatusList(List<TerminalInstallBean> tiStatusList) {
		this.tiStatusList = tiStatusList;
	}

	public int isSuccFlag() {
		return succFlag;
	}

	public void setSuccFlag(int succFlag) {
		this.succFlag = succFlag;
	}

	public Integer getConsType() {
		return consType;
	}

	public void setConsType(Integer consType) {
		this.consType = consType;
	}

	public String getInterType() {
		return interType;
	}

	public void setInterType(String interType) {
		this.interType = interType;
	}

	public void setAppNos(String[] appNos) {
		this.appNos = appNos;
	}

	public List<TerminalRmvAndChgBean> getTiList2() {
		return tiList2;
	}

	public void setTiList2(List<TerminalRmvAndChgBean> tiList2) {
		this.tiList2 = tiList2;
	}
}
