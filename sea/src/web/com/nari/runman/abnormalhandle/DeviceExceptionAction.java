package com.nari.runman.abnormalhandle;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.flowhandle.DeviceExceptionBean;
import com.nari.flowhandle.FEventSrc;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 现场设备异常查询Action
 * 
 * @author longkc
 */
public class DeviceExceptionAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());

	private boolean success = true;
	// 节点类型
	private String nodeType;
	// 节点值
	private String nodeValue;
	// 前台参数
	private long start = 0;
	private long totalCount;
	private long limit = Constans.DEFAULT_PAGE_SIZE;
	// 查询开始时间
	private Date startDate;
	// 查询结束时间
	private Date endDate;
	private List<DeviceExceptionBean> exceptionInfo;
	// 异常id
	private String tmnlExceptionId;
	// 事件类型
	private String eventCode;
	// 事件名称
	private String eventName;
	// 工单号
	private long eventNo;

	private DeviceExceptionManager deviceExceptionManager;

	public void setDeviceExceptionManager(
			DeviceExceptionManager deviceExceptionManager) {
		this.deviceExceptionManager = deviceExceptionManager;
	}

	/**
	 * 根据左边树节点和时间查询异常
	 * 
	 * @throws Exception
	 */
	public String queryDeException() throws Exception {
		this.logger.debug("queryDeException : 现场设备异常查询开始");
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		Page<DeviceExceptionBean> pageInfo = this.deviceExceptionManager
				.queryTmnlException(this.nodeType, this.nodeValue, userInfo,
						this.start, this.limit, this.startDate, this.endDate);
		this.setExceptionInfo(pageInfo.getResult());
		this.setTotalCount(pageInfo.getTotalCount());
		this.logger.debug("queryDeException : 现场设备异常查询结束");
		return SUCCESS;
	}

	/**
	 * 根据异常id查询异常
	 * 
	 * @throws Exception
	 */
	public String queryExceptionInfoById() throws Exception {
		this.logger.debug("getExceptionInfoById : 现场设备异常查询开始");
		// 根据异常id查询异常信息
		this.setExceptionInfo(this.deviceExceptionManager
				.queryDeviceException(this.tmnlExceptionId));
		this.logger.debug("getExceptionInfoById : 现场设备异常查询结束");
		return SUCCESS;
	}

	/**
	 * @desc 事件报警关注
	 * @return
	 */
	public String attentionEvent() throws Exception {

		PSysUser user = this.getPSysUser();
		// 创建工单所需信息
		FEventSrc fEventSrc = new FEventSrc();
		fEventSrc.setStaffNo(user.getStaffNo());
		fEventSrc.setOrgNo(user.getOrgNo());
		fEventSrc.setFromId(this.tmnlExceptionId);
		// 主站分析异常时eventType为3
		fEventSrc.setEventType(Byte.parseByte("3"));
		fEventSrc.setEventCode(this.eventCode);
		fEventSrc.setEventName(this.eventName);
		// 创建处理工单
		this.setEventNo(this.deviceExceptionManager.createEventNo(fEventSrc));
		return SUCCESS;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
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

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<DeviceExceptionBean> getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(List<DeviceExceptionBean> exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	public String getTmnlExceptionId() {
		return tmnlExceptionId;
	}

	public void setTmnlExceptionId(String tmnlExceptionId) {
		this.tmnlExceptionId = tmnlExceptionId;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public long getEventNo() {
		return eventNo;
	}

	public void setEventNo(long eventNo) {
		this.eventNo = eventNo;
	}

}
