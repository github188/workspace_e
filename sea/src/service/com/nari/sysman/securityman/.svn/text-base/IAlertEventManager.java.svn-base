package com.nari.sysman.securityman;

import java.util.List;

import com.nari.basicdata.BProtocolEvent;
import com.nari.elementsdata.ETmnlEventFull;
import com.nari.elementsdata.SimpleETmnlEvent;
import com.nari.flowhandle.FEventSrc;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.alertevent.AlertDataItem;
import com.nari.sysman.alertevent.TerminalAddr;

/**
 * Manager 接口 IAlertEventManager
 * 
 * @author zhangzhw
 * @describe 处理报警事件的服务接口
 */
public interface IAlertEventManager {

	/**
	 * 方法 querySimpleEtmnlEvent
	 * 
	 * @param start
	 * @param limit
	 * @param username
	 * @param alertLevel
	 * @param consNo
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @return Page<SimpleETmnlEvent> 事件列表
	 */
	public Page<SimpleETmnlEvent> querySimpleEtmnlEvent(long start, int limit,
			String alertLevel, String username, String consNo, String orgNo,
			String startDate, String endDate,PSysUser pSysUser) throws Exception;

	/**
	 * 方法 queryAlertDataItem
	 * 
	 * @param eventId
	 * @return List<AlertDataItem> 报警数据项明细
	 */
	public List<AlertDataItem> queryAlertDataItem(String orgNo,String rd, String storeMode,PSysUser pSysUser) throws Exception;

	/**
	 * 方法 queryAlertEventCount
	 * 
	 * @param pSysUser
	 * @return 当前用户权限下的事件数量
	 * @throws Exception
	 */
	public List<Object> queryAlertEventCount(PSysUser pSysUser)
			throws Exception;
	
	/**
	 * chenjg
	 * @desc 事件查询
	 * @param start
	 * @param limit
	 * @param type
	 * @param nodeId
	 * @param startDate
	 * @param endDate
	 * @param staffNo
	 * @return
	 * @throws Exception
	 */
	public Page<SimpleETmnlEvent> querySimpleEtmnlEvent(long start , int limit, String type,String nodeId,String terminalAddr,String startDate,String endDate,String staffNo,String orgType,String consTYpe, String eventType) throws Exception;
	
	
	/**
	 * chenjg
	 * @desc 事件报警关注
	 * @throws Exception
	 */
	public void attentionEvent(String rowid,FEventSrc fEventSrc) throws Exception;
	
	public ETmnlEventFull getEventByRowId(String rowid) throws Exception;
	
	/**
	 * 根据rowid查询事件
	 * @param start
	 * @param limit
	 * @param rowid
	 * @return
	 * @throws Exception
	 */
	public Page<SimpleETmnlEvent> querySimpleEtmnlEvent(long start , int limit, String rowid) throws Exception;
	
	/**
	 * chenjg
	 * 根据用户号查询终端
	 * @param consNo
	 * @return
	 * @throws Exception
	 */
	public List <TerminalAddr> getTerminalAddrByConsNo(String consNo) throws Exception;
	
	/**
	 * longkc
	 * 查询事件类型
	 * @return
	 * @throws Exception
	 */
	public List<BProtocolEvent> findEventType() throws Exception;

}
