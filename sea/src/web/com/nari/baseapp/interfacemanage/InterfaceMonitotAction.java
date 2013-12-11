package com.nari.baseapp.interfacemanage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 
 * 接口监视action
 * 
 * @author ChunMingLi
 * @since  2010-6-28
 *
 */
public class InterfaceMonitotAction extends BaseAction {
	//日志记录
	private Logger logger = Logger.getLogger(this.getClass());
	private boolean success = true;

	// 前台参数
	private long totalCount;
	private long start = 0;
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	// 检索开始时间
	private Date startDate;
	// 检索结束时间
	private Date endDate;
	
	//业务类型
	private	ArrayList<String> busType;
	//监测接口
	private String monitorType;
	
	//接口监视集合
	private List<InterfaceMonitorDto> interfaceMonitorList;
	
	//设置注入service层实例
	private InterfaceMonitorManager interfaceMonitorManager;
	
	
	/**
	 *  the interfaceMonitorManager to set
	 * @param interfaceMonitorManager the interfaceMonitorManager to set
	 */
	public void setInterfaceMonitorManager(
			InterfaceMonitorManager interfaceMonitorManager) {
		this.interfaceMonitorManager = interfaceMonitorManager;
	}

	/**
	 * 查询接口监视
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryInterfaceMonitor()throws Exception {
		this.logger.debug("查询接口监视开始 : queryInterfaceMonitor()");
		//监测查询条件
		InterfaceMonitorBean monitorBean = new InterfaceMonitorBean();
		monitorBean.setStartDate(startDate);
		monitorBean.setEndDate(endDate);
		monitorBean.setMonitorType(monitorType);
//		monitorBean.setBustype(busType);
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute("pSysUser");
		//监测查询
		Page<InterfaceMonitorDto> page = interfaceMonitorManager.queryInterfaceList(monitorBean, userInfo, start, limit);
		if (null != page) {
			// 设置总条数
			this.setTotalCount(page.getTotalCount());
			// 设置查询结果
			this.setInterfaceMonitorList(page.getResult());
		}else{
			success = false;
			return SUCCESS;
		}
		
		this.logger.debug("查询接口监视结束 : queryInterfaceMonitor()");
		return SUCCESS;
		
	}

	/**
	 *  the success
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 *  the success to set
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 *  the totalCount
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 *  the totalCount to set
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 *  the start
	 * @return the start
	 */
	public long getStart() {
		return start;
	}

	/**
	 *  the start to set
	 * @param start the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}

	/**
	 *  the limit
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 *  the limit to set
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 *  the startDate
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 *  the startDate to set
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 *  the endDate
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 *  the endDate to set
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 *  the interfaceMonitorList
	 * @return the interfaceMonitorList
	 */
	public List<InterfaceMonitorDto> getInterfaceMonitorList() {
		return interfaceMonitorList;
	}

	/**
	 *  the interfaceMonitorList to set
	 * @param interfaceMonitorList the interfaceMonitorList to set
	 */
	public void setInterfaceMonitorList(
			List<InterfaceMonitorDto> interfaceMonitorList) {
		this.interfaceMonitorList = interfaceMonitorList;
	}

	/**
	 *  the busType
	 * @return the busType
	 */
	public ArrayList<String> getBusType() {
		return busType;
	}

	/**
	 *  the busType to set
	 * @param busType the busType to set
	 */
	public void setBusType(ArrayList<String> busType) {
		this.busType = busType;
	}

	/**
	 *  the monitorType
	 * @return the monitorType
	 */
	public String getMonitorType() {
		return monitorType;
	}

	/**
	 *  the monitorType to set
	 * @param monitorType the monitorType to set
	 */
	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}
	
}
