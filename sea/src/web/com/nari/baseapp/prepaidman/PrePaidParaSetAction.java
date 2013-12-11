package com.nari.baseapp.prepaidman;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.coherence.TaskDeal;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.opensymphony.xwork2.ActionContext;

/**
 * 预付费控制参数下发
 * @author 杨传文
 */
public class PrePaidParaSetAction extends BaseAction {
	private static Logger logger = Logger.getLogger(PrePaidParaSetAction.class);
	
	private boolean success = true;
	private long totalCount;//总数
	private int second = 45;
	private long start;
	private long limit = Constans.DEFAULT_PAGE_SIZE;
	
	private String orgNo;
	private String buyFlag;//购电单位标志
	private String sendStatus;//流程状态
	private String appNo;//购电单号
	private String consNo;
	private String turnFlag;//购电轮次
	private List<String> tmnlPreParamSetList;
	
	private String cacheAndTmnlStatus="";
	private List<PrePaidParamSetBean>prePaidParamSetList;
	private List<PrePaidExecResultBean> resultList;
	private List<PrePaidParamSetTmnlBean> fetchList;
	
	private PrePaidManager prePaidManager;
	
	public String queryPrePaidParaSet() throws Exception {
		try {
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			Page<PrePaidParamSetBean> page = prePaidManager.queryPrePaidParamSetPage(user,
					orgNo, sendStatus, appNo, consNo,buyFlag, start, limit);
			prePaidParamSetList = page.getResult();
			totalCount = page.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 预付费控制参数后台下发
	 * @return
	 * @throws Exception
	 */
	public String prePaidParaSet() throws Exception {
		logger.debug("--预付费参数下发开始--");
		try {
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			
			List<PrePaidParamSetBean> tmnlList = prePaidManager.queryPrePaidParamSetList(orgNo, sendStatus, appNo, consNo);
			
			resultList = prePaidManager.prePaidParamSet(tmnlList,turnFlag, second, user, getLocalIp());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 预付费控制参数前台下发
	 * @return
	 * @throws Exception
	 */
	public String prePaidParamSetView() throws Exception {
		logger.debug("--预付费参数下发开始--");
		try {
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			
			List<PrePaidParamSetTmnlBean> tmnlList = prePaidParamSetTmnlList();
			
			for (int i = 0; i < tmnlList.size(); i++) {
				System.out.println(tmnlList.get(i).getAppNo());
			}
			
			resultList = prePaidManager.prePaidParamSetView(tmnlList, turnFlag, second, user, getLocalIp());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 预付费控制参数召测
	 * @return
	 * @throws Exception
	 */
	public String prePaidParamFetch() throws Exception {
		logger.debug("--预付费参数召测开始--");
		try {
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			
			List<PrePaidParamSetTmnlBean> tmnlList = prePaidParamFetchTmnlList();
			
			fetchList= prePaidManager.prePaidParamFetch(tmnlList, second);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 预付费剩余值召测
	 * @return
	 * @throws Exception
	 */
	public String prePaidParamLeftValue() throws Exception {
		logger.debug("--预付费剩余值召测--");
		try {
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			
//			List<PrePaidParamSetTmnlBean> tmnlList = prePaidParamFetchTmnlList();
//			
//			fetchList= prePaidManager.prePaidParamFetch(tmnlList, second);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
    /**
	 * 获取本机IP
	 * @return String 本地IP
	 * @throws Exception
	 */
    private String getLocalIp() throws Exception{
    	InetAddress addr = InetAddress.getLocalHost();
	    String ipAddr=addr.getHostAddress().toString();//获得本机IP
	    return ipAddr;
    }
    
	/**
	 * 将页面选择的总加组按所属终端分类排序
	 */
	public List<PrePaidParamSetTmnlBean> prePaidParamSetTmnlList() {
		List<PrePaidParamSetTmnlBean> tmnlList = new ArrayList<PrePaidParamSetTmnlBean>();
		for (int i = 0; i < tmnlPreParamSetList.size(); i++) {
			String[] obj = tmnlPreParamSetList.get(i).split("`");
			//前台传入的终端和总加组号，可能为空字符串或 undefined 应剔除
			if ("".equals(obj[2]) || "".equals(obj[3]) || "".equals(obj[4])
					|| "".equals(obj[5]) || "".equals(obj[6])
					|| "".equals(obj[7]) || "".equals(obj[8])
					|| "".equals(obj[9]) || "undefined".equals(obj[2])
					|| "undefined".equals(obj[3]) || "undefined".equals(obj[4])
					|| "undefined".equals(obj[5]) || "undefined".equals(obj[6])
					|| "undefined".equals(obj[7]) || "undefined".equals(obj[8])
					|| "undefined".equals(obj[9])) {
				continue;
			}
			PrePaidParamSetTmnlBean bean = new PrePaidParamSetTmnlBean();
			bean.setOrgNo(obj[0]);
			bean.setConsNo(obj[1]);
			bean.setTmnlAssetNo(obj[2]);
			bean.setTotalNo(Short.parseShort(obj[3]));
			bean.setProtocolCode(obj[4]);
			bean.setAppNo(obj[5]);
			bean.setRefreshFlag(obj[6]);
			bean.setBuyValue(Integer.parseInt(obj[7]));
			bean.setAlarmValue(Integer.parseInt(obj[8]));
			bean.setJumpValue(Integer.parseInt(obj[9]));
			
			tmnlList.add(bean);
		}
		//按终端资产编号排序
		Collections.sort(tmnlList, new Comparator<PrePaidParamSetTmnlBean>(){
			@Override
			public int compare(PrePaidParamSetTmnlBean o1, PrePaidParamSetTmnlBean o2) {
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		return tmnlList;
	}
	
	/**
	 * 将页面选择的总加组按所属终端分类排序
	 */
	public List<PrePaidParamSetTmnlBean> prePaidParamFetchTmnlList() {
		List<PrePaidParamSetTmnlBean> tmnlList = new ArrayList<PrePaidParamSetTmnlBean>();
		for (int i = 0; i < tmnlPreParamSetList.size(); i++) {
			String[] obj = tmnlPreParamSetList.get(i).split("`");
			//前台传入的终端和总加组号，可能为空字符串或 undefined 应剔除
			if("".equals(obj[0])||"".equals(obj[0])|| "".equals(obj[1])|| "undefined".equals(obj[1])
					|| "undefined".equals(obj[2])|| "undefined".equals(obj[2])){
				continue;
			}
			PrePaidParamSetTmnlBean bean = new PrePaidParamSetTmnlBean();
			bean.setTmnlAssetNo(obj[0]);
			bean.setTotalNo(Short.parseShort(obj[1]));
			bean.setProtocolCode(obj[2]);
			
			tmnlList.add(bean);
		}
		//按终端资产编号排序
		Collections.sort(tmnlList, new Comparator<PrePaidParamSetTmnlBean>(){
			@Override
			public int compare(PrePaidParamSetTmnlBean o1, PrePaidParamSetTmnlBean o2) {
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		return tmnlList;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getLimit() {
		return limit;
	}
	public void setLimit(long limit) {
		this.limit = limit;
	}
	public String getCacheAndTmnlStatus() {
		return cacheAndTmnlStatus;
	}
	public void setCacheAndTmnlStatus(String cacheAndTmnlStatus) {
		this.cacheAndTmnlStatus = cacheAndTmnlStatus;
	}

	public void setPrePaidParamSetList(List<PrePaidParamSetBean> prePaidParamSetList) {
		this.prePaidParamSetList = prePaidParamSetList;
	}

	public List<PrePaidParamSetBean> getPrePaidParamSetList() {
		return prePaidParamSetList;
	}

	public void setPrePaidManager(PrePaidManager prePaidManager) {
		this.prePaidManager = prePaidManager;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public List<PrePaidExecResultBean> getResultList() {
		return resultList;
	}

	public void setResultList(List<PrePaidExecResultBean> resultList) {
		this.resultList = resultList;
	}

	public List<String> getTmnlPreParamSetList() {
		return tmnlPreParamSetList;
	}

	public void setTmnlPreParamSetList(List<String> tmnlPreParamSetList) {
		this.tmnlPreParamSetList = tmnlPreParamSetList;
	}

	public List<PrePaidParamSetTmnlBean> getFetchList() {
		return fetchList;
	}

	public void setFetchList(List<PrePaidParamSetTmnlBean> fetchList) {
		this.fetchList = fetchList;
	}

	public String getBuyFlag() {
		return buyFlag;
	}

	public void setBuyFlag(String buyFlag) {
		this.buyFlag = buyFlag;
	}

	public String getTurnFlag() {
		return turnFlag;
	}

	public void setTurnFlag(String turnFlag) {
		this.turnFlag = turnFlag;
	}
}
