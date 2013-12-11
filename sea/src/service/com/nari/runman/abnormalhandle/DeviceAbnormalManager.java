package com.nari.runman.abnormalhandle;

import java.util.List;
import java.util.Map;

import com.nari.flowhandle.EventSearchInfoBean;
import com.nari.flowhandle.FEventClose;
import com.nari.flowhandle.FEventFlow;
import com.nari.flowhandle.FEventInfoBean;
import com.nari.flowhandle.FEventShield;
import com.nari.flowhandle.FEventSrc;
import com.nari.support.Page;

public interface DeviceAbnormalManager {
	/**
	 * 派工进营销
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getMaketingInf(FEventSrc fEventSrc, FEventFlow fEventFlow)
			throws Exception;

	/**
	 * 工单查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public Page<FEventInfoBean> getEventInfo(EventSearchInfoBean eventInfoBean,
			long start, int limit) throws Exception;

	/**
	 * 工单查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<FEventSrc> getEventInfo(long eventId) throws Exception;
	
	/**
	 * 根据异常来源ID和异常类型查询工单信息
	 * @param fromId 异常来源ID
	 * @param eventType 异常类型
	 * @return
	 * @throws Exception
	 */
	public List<FEventInfoBean> getEventInfo(String fromId, Byte eventType) throws Exception;
	
	/**
	 * 现场本地处理
	 * 
	 * @return
	 * @throws Exception
	 */
	public void localHandle(FEventSrc feventSrc, FEventFlow fEventFlow)
			throws Exception;

	/**
	 * 归档处理
	 * 
	 * @return
	 * @throws Exception
	 */
	public void savaRecord(FEventClose fEventClose, FEventFlow fEventFlow)
			throws Exception;

	/**
	 * 挂起处理
	 * 
	 * @return
	 * @throws Exception
	 */
	public void holdOn(FEventSrc fEventSrc, FEventFlow fEventFlow,
			FEventShield fEventShield) throws Exception;

	/**
	 * 批量派工
	 * 
	 * @return
	 * @throws Exception
	 */
	public int sendToMarketBatch(Map<String, String> paramMap) throws Exception;

	/**
	 * 通知客户经理
	 * 
	 * @return
	 * @throws Exception
	 */
	public int reportManager(Map<String, String> paramMap) throws Exception;
}
