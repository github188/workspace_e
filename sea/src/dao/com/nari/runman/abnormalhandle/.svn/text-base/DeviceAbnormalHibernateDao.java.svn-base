package com.nari.runman.abnormalhandle;

import com.nari.flowhandle.FEventClose;
import com.nari.flowhandle.FEventFlow;
import com.nari.flowhandle.FEventShield;
import com.nari.flowhandle.FEventSrc;
import com.nari.util.exception.DBAccessException;

public interface DeviceAbnormalHibernateDao {
	
	/**
	 * 更新工单源状态
	 * 
	 * @param fEventSrc 工单源信息
	 * @throws DBAccessException
	 */
	public void updateEventSrc(FEventSrc fEventSrc) throws DBAccessException;
	
	/**
	 * 插入工单操作流水
	 * 
	 * @param fEventFlow 工单流水信息
	 * @throws DBAccessException
	 */
	public void insertEventFlow(FEventFlow fEventFlow) throws DBAccessException;
	
	/**
	 * 插入归档工单信息
	 * 
	 * @param fEventClose 归档工单信息
	 * @throws DBAccessException
	 */
	public void insertEventClose(FEventClose fEventClose) throws DBAccessException;
	
	/**
	 * 插入屏蔽周期
	 * 
	 * @param fEventShield 屏蔽周期信息
	 * @throws DBAccessException
	 */
	public void insertEventShield(FEventShield fEventShield) throws DBAccessException;
}
