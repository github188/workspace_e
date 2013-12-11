package com.nari.runman.abnormalhandle;

import java.util.List;

import com.nari.flowhandle.EventSearchInfoBean;
import com.nari.flowhandle.FEventInfoBean;
import com.nari.flowhandle.FEventSrc;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 设备异常处理DAO接口
 * @author lkc
 *
 */
public interface DeviceAbnormalDao {
	
	/**
	 * 检索异常工单信息
	 * 
	 * @param eventInfo 检索条件
	 * @param start     分页开始条数
	 * @param limit     分页每页条数
	 * @return  分页工单信息list
	 */
	public Page<FEventInfoBean> getEventInfo(EventSearchInfoBean eventInfo, long start, int limit) throws DBAccessException;

	/**
	 * 检索异常工单信息
	 * 
	 * @param eventId 工单号
	 * @return  工单信息
	 */
	public List<FEventSrc> getEventInfo(long eventId) throws DBAccessException;

	
	/**
	 * 删除屏蔽周期
	 * 
	 * @param eventId 工单号
	 * @throws DBAccessException
	 */
	public void deleteEventShield(long eventId) throws DBAccessException;
	
	/**
	 * 删除归档工单的工单源信息
	 * 
	 * @param eventId 工单号
	 * @throws DBAccessException
	 */
	public void deleteEventSrc(long eventId) throws DBAccessException;
	
	/**
	 * 根据异常来源ID和异常类型查询工单信息
	 * @param fromId 异常来源ID
	 * @param eventType 异常类型
	 * @return
	 * @throws DBAccessException
	 */
	public List<FEventInfoBean> getEventInfo(String fromId, Byte eventType) throws DBAccessException;
	
}
