package com.nari.sysman.securityman;

import java.util.List;

import com.nari.basicdata.BProtocolEvent;
import com.nari.basicdata.SimpleBEventMark;
import com.nari.basicdata.SimpleEventDef;
import com.nari.elementsdata.ETmnlEventFull;
import com.nari.elementsdata.SimpleETmnlEvent;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.alertevent.TerminalAddr;

/**
 * DAO接口 IAlertEventDao
 * 
 * @author zhangzhw
 * @describe 处理报警事件的DAO接口
 */
public interface IAlertEventDao {

	/**
	 * 方法 querySimpleEtmnlEvent
	 * 
	 * @param start
	 * @param limit
	 * @param username
	 * @return Page<SimpleETmnlEvent> 事件列表
	 */
	public Page<SimpleETmnlEvent> querySimpleEtmnlEvent(long start, int limit,
			String alertLevel, String username, String consNo, String orgNo,
			String startDate, String endDate,PSysUser pSysUser);

	/**
	 * 方法 queryAlertEventCount
	 * 
	 * @return 统计报警数据
	 */
	public List<Object> queryAlertEventCount(PSysUser pSysUser);
	
	/**
	 * 方法　queryETmnlEventFull
	 * @param rd
	 * @return　查询到的事件数据
	 */
	public List<ETmnlEventFull>  queryETmnlEventFull(String orgNo,String rd,PSysUser pSysUser);
	
	/**
	 * 方法　queryEventDef
	 * @param eventNo
	 * @return　　查询到的事件定义　
	 */
	public List<SimpleEventDef> queryEventDef(String eventNo,String itemNos);
	
	/**
	 * 方法　queryEventMark
	 * @param eventNo
	 * @param dataN
	 * @return　事件标志
	 */
	public List<SimpleBEventMark> queryEventMark(String eventNo,int dataN);
	
	/**
	 * @desc 查询事件
	 * @param start
	 * @param limit
	 * @param type
	 * @param nodeId
	 * @param startDate
	 * @param endDate
	 * @return 事件列表
	 */
	public Page<SimpleETmnlEvent> querySimpleEtmnlEvent(long start , int limit, String type,String nodeId,String terminalAddr,String startDate,String endDate,String staffNo,String orgType,String consTYpe, String eventType);
	
	/**
	 * chenjg
	 * 更新flow_status_code
	 */
	public void update(String rowid,String statusCode);
	
	/**
	 * chenjg
	 * 根据rowid 查找
	 * @param rowId
	 * @return
	 */
	public ETmnlEventFull findByRowId(String rowId);
	
	/**
	 * 根据rowid 查询事件
	 * @param start
	 * @param limit
	 * @param rowid
	 * @return
	 */
	public Page<SimpleETmnlEvent> querySimpleEtmnlEvent(long start , int limit, String rowid);

	/**
	 * chenjg
	 * 根据用户号查询终端
	 * @param consNo
	 * @return
	 */
	public List<TerminalAddr> findTerminalAddrByConsNo(String consNo);
	
	/**
	 * longkc
	 * 查询事件类型
	 * @return
	 */
	public List<BProtocolEvent> findEventType();
}
