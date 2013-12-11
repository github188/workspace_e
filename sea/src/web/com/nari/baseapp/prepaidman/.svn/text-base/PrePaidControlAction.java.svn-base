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

public class PrePaidControlAction extends BaseAction {
	private static Logger logger = Logger.getLogger(PrePaidControlAction.class);

	private boolean success = true;
	private long totalCount;// 总数
	private int second = 45;
	private long start;
	private long limit = Constans.DEFAULT_PAGE_SIZE;

	private String orgNo;
	private String appNo;// 购电单号
	private String consNo;
	private String tmnlAssetNo;

	private String cacheAndTmnlStatus = "";
	private List<PrePaidControlBean> prePaidControlList;
	private List<PrePaidExecResultBean> resultList;
	private List<String> tmnlPreControlList;

	private PrePaidManager prePaidManager;

	public String queryPrePaidControl() throws Exception {
		try {
			PSysUser user = (PSysUser) ActionContext.getContext().getSession().get("pSysUser");
			if (user == null) {
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			Page<PrePaidControlBean> page = prePaidManager
					.queryPrePaidCtrlPage(user,orgNo, appNo, consNo, tmnlAssetNo, start, limit);
			prePaidControlList = page.getResult();
			totalCount = page.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 预付费控制后台下发
	 * @return
	 * @throws Exception
	 */
	public String prePaidControl() throws Exception {
		logger.debug("--预付费控制开始--");
		try {
			if (!TaskDeal.isCacheRunning()) {
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}

			if (!TaskDeal.isFrontAlive()) {
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}

			PSysUser user = (PSysUser) ActionContext.getContext().getSession()
					.get("pSysUser");
			if (user == null) {
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}

			List<PrePaidControlBean> list = prePaidManager
					.queryPrePaidCtrlList(orgNo, appNo, consNo, tmnlAssetNo);

			resultList = prePaidManager.prePaidControl(list, second, user,
					getLocalIp());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 预付费控制前台下发
	 * 
	 * @return
	 * @throws Exception
	 */
	public String prePaidControlView() throws Exception {
		logger.debug("--预付费控制开始--");
		try {
			if (!TaskDeal.isCacheRunning()) {
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			if (!TaskDeal.isFrontAlive()) {
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}

			PSysUser user = (PSysUser) ActionContext.getContext().getSession().get("pSysUser");
			if (user == null) {
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}

			List<PrePaidControlTmnlBean> tmnlList = prePaidCtrlTmnlList();

			resultList = prePaidManager.prePaidControlView(tmnlList, second, user, getLocalIp());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 预付费控制解除后台下发
	 * @return
	 * @throws Exception
	 */
	public String prePaidRelease() throws Exception {
		logger.debug("--预付费控制后台解除开始--");
		try {
			if (!TaskDeal.isCacheRunning()) {
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}

			if (!TaskDeal.isFrontAlive()) {
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}

			PSysUser user = (PSysUser) ActionContext.getContext().getSession()
					.get("pSysUser");
			if (user == null) {
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}

			List<PrePaidControlBean> list = prePaidManager
					.queryPrePaidCtrlList(orgNo, appNo, consNo, tmnlAssetNo);

			resultList = prePaidManager.prePaidRelease(list, second, user, getLocalIp());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 预付费控制解除前台下发
	 * @return
	 * @throws Exception
	 */
	public String prePaidReleaseView() throws Exception {
		logger.debug("--预付费控制解除开始--");
		try {
			if (!TaskDeal.isCacheRunning()) {
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			if (!TaskDeal.isFrontAlive()) {
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}

			PSysUser user = (PSysUser) ActionContext.getContext().getSession().get("pSysUser");
			if (user == null) {
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			
			List<PrePaidControlTmnlBean> tmnlList = prePaidCtrlTmnlList();

			resultList = prePaidManager.prePaidReleaseView(tmnlList, second, user, getLocalIp());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 获取本机IP
	 * 
	 * @return String 本地IP
	 * @throws Exception
	 */
	private String getLocalIp() throws Exception {
		InetAddress addr = InetAddress.getLocalHost();
		String ipAddr = addr.getHostAddress().toString();// 获得本机IP
		return ipAddr;
	}
	
	/**
	 * 将页面选择的总加组按所属终端分类排序
	 */
	public List<PrePaidControlTmnlBean> prePaidCtrlTmnlList() {
		List<PrePaidControlTmnlBean> tmnlList = new ArrayList<PrePaidControlTmnlBean>();
		for (int i = 0; i < tmnlPreControlList.size(); i++) {
			String[]  obj = tmnlPreControlList.get(i).split("`");
			//前台传入的终端和总加组号，可能为空字符串或 undefined 应剔除
			if("".equals(obj[2])||"".equals(obj[3])|| "".equals(obj[4])|| "undefined".equals(obj[2])
					|| "undefined".equals(obj[3])|| "undefined".equals(obj[4])){
				continue;
			}
			PrePaidControlTmnlBean bean = new PrePaidControlTmnlBean();
			bean.setOrgNo(obj[0]);
			bean.setConsNo(obj[1]);
			bean.setTmnlAssetNo(obj[2]);
			bean.setTotalNo(Short.parseShort(obj[3]));
			bean.setProtocolCode(obj[4]);
			
			tmnlList.add(bean);
		}
		//按终端资产编号排序
		Collections.sort(tmnlList, new Comparator<PrePaidControlTmnlBean>(){
			@Override
			public int compare(PrePaidControlTmnlBean o1, PrePaidControlTmnlBean o2) {
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

	public void setPrePaidManager(PrePaidManager prePaidManager) {
		this.prePaidManager = prePaidManager;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
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

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public List<PrePaidControlBean> getPrePaidControlList() {
		return prePaidControlList;
	}

	public void setPrePaidControlList(
			List<PrePaidControlBean> prePaidControlList) {
		this.prePaidControlList = prePaidControlList;
	}

	public List<PrePaidExecResultBean> getResultList() {
		return resultList;
	}

	public void setResultList(List<PrePaidExecResultBean> resultList) {
		this.resultList = resultList;
	}

	public List<String> getTmnlPreControlList() {
		return tmnlPreControlList;
	}

	public void setTmnlPreControlList(List<String> tmnlPreControlList) {
		this.tmnlPreControlList = tmnlPreControlList;
	}
}
