package com.nari.runman.abnormalhandle;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.flowhandle.EventSearchInfoBean;
import com.nari.flowhandle.FEventClose;
import com.nari.flowhandle.FEventCloseId;
import com.nari.flowhandle.FEventFlow;
import com.nari.flowhandle.FEventFlowId;
import com.nari.flowhandle.FEventInfoBean;
import com.nari.flowhandle.FEventSrc;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.DeviceAbnormalUtil;

/**
 * 现场设备异常处理Action
 * 
 * @author longkc
 */
public class DeviceAbnormalAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	private boolean success = true;
	// 工单信息
	private Map<String, String> infoMap;

	// 前台参数
	private long start = 0;
	private long totalCount;
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	// 检索开始时间
	private Date startDate;
	// 检索结束时间
	private Date endDate;
	// 工单状态
	private String eventStatus;
	// 异常类型
	private Byte eventType;
	// 本次操作人
	private String recStaffNo;
	// 工单号
	private long eventId;
	// 异常来源ID
	private String fromId;

	// 处理说明
	private String eventData;

	// 返回结果
	// 工单信息
	private List<FEventInfoBean> eventInfoList;

	// 注入service层实例
	DeviceAbnormalManager deviceAbnormalManager;

	public void setDeviceAbnormalManager(
			DeviceAbnormalManager deviceAbnormalManager) {
		this.deviceAbnormalManager = deviceAbnormalManager;
	}

	/**
	 * 派工进营销
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendToMarketing() throws Exception {
		this.logger.debug("派工进营销开始");
		// 工单源
		List<FEventSrc> fslist = this.deviceAbnormalManager
				.getEventInfo(this.eventId);
		FEventSrc feventSrc = null;
		if (fslist != null && fslist.size() != 0) {
			feventSrc = fslist.get(0);
		} else {
			return SUCCESS;
		}
		// 工单流水
		FEventFlow fEventFlow = new FEventFlow();
		FEventFlowId fEventFlowId = new FEventFlowId();
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		// 工单号设定
		feventSrc.setEventId(this.eventId);
		fEventFlowId.setEventId(this.eventId);
		// 操作状态设定
		feventSrc.setFlowStatusCode(DeviceAbnormalUtil.SEND_MARKETING);
		fEventFlowId.setFlowStatusCode(DeviceAbnormalUtil.SEND_MARKETING);
		// 营销处理意见设置
		fEventFlowId.setOpRemark(this.eventData);
		// 设置操作员
		fEventFlowId.setStaffNo(userInfo.getName());
		// 本次操作时间
		fEventFlowId.setOpTime(new Date());
		fEventFlow.setId(fEventFlowId);
		this.deviceAbnormalManager.getMaketingInf(feventSrc, fEventFlow);
		this.logger.debug("派工进营销结束");
		return SUCCESS;
	}

	/**
	 * 查询待处理工单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchEventInfo() throws Exception {
		this.logger.debug("查询待处理工单开始");
		// 工单查询条件
		EventSearchInfoBean searchInfo = new EventSearchInfoBean();
		searchInfo.setStartTime(this.startDate);
		searchInfo.setEndTime(this.endDate);
		// 异常类型输入时检索条件包含异常类型
		if (null != this.eventType && !"".equals(this.eventType)) {
			searchInfo.setEventType(this.eventType);
		}
		// 工单状态输入时检索条件包含工单状态
		if (null != this.eventStatus && !"".equals(this.eventStatus)) {
			searchInfo.setFlowStatusCode(this.eventStatus);
		}
		// 检索工单信息
		Page<FEventInfoBean> eventPageInfo = this.deviceAbnormalManager
				.getEventInfo(searchInfo, this.start, this.limit);
		// 设置总条数
		this.setTotalCount(eventPageInfo.getTotalCount());
		// 设置查询结果
		this.setEventInfoList(eventPageInfo.getResult());
		this.logger.debug("查询待处理工单结束");
		return SUCCESS;
	}

	/**
	 * 查询待处理工单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchEventInfoByType() throws Exception {
		this.logger.debug("查询待处理工单开始");
		// 设置查询结果
		this.setEventInfoList(this.deviceAbnormalManager.getEventInfo(
				this.fromId, this.eventType));
		this.logger.debug("查询待处理工单结束");
		return SUCCESS;
	}

	/**
	 * 现场本地处理
	 * 
	 * @return
	 * @throws Exception
	 */
	public String localHandle() throws Exception {
		this.logger.debug("现场本地处理开始");
		// 工单源
		List<FEventSrc> fslist = this.deviceAbnormalManager
				.getEventInfo(this.eventId);
		FEventSrc feventSrc = null;
		if (fslist != null && fslist.size() != 0) {
			feventSrc = fslist.get(0);
		} else {
			return SUCCESS;
		}
		// 工单流水
		FEventFlow fEventFlow = new FEventFlow();
		FEventFlowId fEventFlowId = new FEventFlowId();
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		// 工单号设定
		feventSrc.setEventId(this.eventId);
		fEventFlowId.setEventId(this.eventId);
		// 操作状态设定
		feventSrc.setFlowStatusCode(DeviceAbnormalUtil.LOCAL_HANDEL);
		fEventFlowId.setFlowStatusCode(DeviceAbnormalUtil.LOCAL_HANDEL);
		// 本地处理意见设置
		fEventFlowId.setOpRemark(this.eventData);
		// 设置操作员
		fEventFlowId.setStaffNo(userInfo.getName());
		// 本次操作时间
		fEventFlowId.setOpTime(new Date());
		fEventFlow.setId(fEventFlowId);
		this.deviceAbnormalManager.localHandle(feventSrc, fEventFlow);
		this.logger.debug("现场本地处理结束");
		return SUCCESS;
	}

	/**
	 * 归档处理
	 * 
	 * @return
	 * @throws Exception
	 */
	public String savaRecord() throws Exception {
		this.logger.debug("归档处理开始");
		// 归档工单
		FEventClose fEventClose = new FEventClose();
		FEventCloseId fEventCloseId = new FEventCloseId();
		// 工单流水
		FEventFlow fEventFlow = new FEventFlow();
		FEventFlowId fEventFlowId = new FEventFlowId();
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		// 工单号设定
		fEventCloseId.setEventId(this.eventId);
		fEventFlowId.setEventId(this.eventId);
		if (DeviceAbnormalUtil.MISS_REPORT.equals(this.eventStatus)) {
			// 操作状态设定：误报确认
			fEventFlowId.setFlowStatusCode(this.eventStatus);
			this.setEventStatus(DeviceAbnormalUtil.MISS_REPORT);
		} else if (DeviceAbnormalUtil.LOCAL_HANDEL.equals(this.eventStatus)) {
			// 操作状态设定：正常归档
			fEventFlowId
					.setFlowStatusCode(DeviceAbnormalUtil.NORMAL_SAVE_RECORD);
			this.setEventStatus(DeviceAbnormalUtil.NORMAL_SAVE_RECORD);
		} else {
			// 操作状态设定：强制归档
			fEventFlowId
					.setFlowStatusCode(DeviceAbnormalUtil.FORCE_SAVE_RECORD);
			this.setEventStatus(DeviceAbnormalUtil.FORCE_SAVE_RECORD);
		}

		// 归档意见设置
		fEventFlowId.setOpRemark(this.eventData);
		// 设置操作员
		fEventFlowId.setStaffNo(userInfo.getName());
		fEventCloseId.setSubmitStaffNo(userInfo.getName());
		fEventCloseId.setCloseStaffNo(userInfo.getName());
		// 本次操作时间
		fEventFlowId.setOpTime(new Date());
		fEventCloseId.setCloseDate(new Date());
		fEventClose.setId(fEventCloseId);
		fEventFlow.setId(fEventFlowId);
		this.deviceAbnormalManager.savaRecord(fEventClose, fEventFlow);
		this.logger.debug("归档处理结束");
		return SUCCESS;
	}

	/**
	 * 挂起处理
	 * 
	 * @return
	 * @throws Exception
	 */
	public String holdOn() throws Exception {
		this.logger.debug("挂起处理开始");
		// this.setPc(this.eventManageManager.getProCodeList());
		this.logger.debug("挂起处理结束");
		return SUCCESS;
	}

	/**
	 * 批量派工
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendToMarketBatch() throws Exception {
		this.logger.debug("批量派工开始");
		// this.setPc(this.eventManageManager.getProCodeList());
		this.logger.debug("批量派工结束");
		return SUCCESS;
	}

	/**
	 * 通知客户经理
	 * 
	 * @return
	 * @throws Exception
	 */
	public String reportManager() throws Exception {
		this.logger.debug("通知客户经理开始");
		// this.setPc(this.eventManageManager.getProCodeList());
		this.logger.debug("通知客户经理结束");
		return SUCCESS;
	}

	public Map<String, String> getInfoMap() {
		return infoMap;
	}

	public void setInfoMap(Map<String, String> infoMap) {
		this.infoMap = infoMap;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public Byte getEventType() {
		return eventType;
	}

	public void setEventType(Byte eventType) {
		this.eventType = eventType;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<FEventInfoBean> getEventInfoList() {
		return eventInfoList;
	}

	public void setEventInfoList(List<FEventInfoBean> eventInfoList) {
		this.eventInfoList = eventInfoList;
	}

	public String getRecStaffNo() {
		return recStaffNo;
	}

	public void setRecStaffNo(String recStaffNo) {
		this.recStaffNo = recStaffNo;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getEventData() {
		return eventData;
	}

	public void setEventData(String eventData) {
		this.eventData = eventData;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
}
