package com.nari.sysman.securityman;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.action.baseaction.Errors;
import com.nari.basicdata.BProtocolEvent;
import com.nari.elementsdata.ETmnlEventFull;
import com.nari.elementsdata.SimpleETmnlEvent;
import com.nari.flowhandle.FEventSrc;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.alertevent.AlertDataItem;
import com.nari.sysman.alertevent.TerminalAddr;

/**
 * Action 类：AlertEventAction *
 * 
 * @author zhangzhw
 * @describe 处理报警事件的Action类
 */
public class AlertEventAction extends BaseAction {

	// 前台参数
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	public String eventId;
	public String rd;
	public String storeMode;
	public String alertLevel;
	public String consNo;
	public String orgNo;
	public String startDate;
	public String endDate;
	private String type;//节点类型
	private String nodeid;//节点id
	private String orgType ;//组织机构类型
	private String consType;//用户类型
	private String terminalAddr;
	private String eventType;

	//事件报警关注
	private String fromId;
	private String eventCode;
	private String eventName;
	private boolean isAttention;
	
	

	// 返回的结果
	public boolean success = true;
	public Errors errors;
	public long totalCount;

	public List<SimpleETmnlEvent> alertEventList;
	public List<AlertDataItem> alertDataItemList;
	public List<Object> alertEventCount;
	private List<TerminalAddr> terminalList;
	private List<BProtocolEvent> bpe;

	// 注入
	public IAlertEventManager alertEventManagerImpl;

	/**
	 * Action方法 alertEvent
	 * 
	 * @return success 通过JSON处理后返回前台
	 * @throws Exception
	 */
	public String alertEvent() throws Exception {
		Page<SimpleETmnlEvent> ps = null;
		
		if(null != fromId){//根据rowid查询事件报警
			ps = alertEventManagerImpl.querySimpleEtmnlEvent(start, limit, fromId);
		}else{
			if (null != type && !"".equals(type)){
				String staffNo = this.getPSysUser().getStaffNo();
				ps = alertEventManagerImpl.querySimpleEtmnlEvent(start, limit, type, nodeid, terminalAddr, startDate, endDate, staffNo,orgType,consType, this.eventType);
			}else{
				ps = alertEventManagerImpl.
				querySimpleEtmnlEvent(start, limit,alertLevel, null,consNo, orgNo, startDate, endDate,super.getPSysUser());
			}
		}
		if(ps==null){
			return SUCCESS;
		}
		alertEventList = ps.getResult();
		totalCount = ps.getTotalCount();
		
		return "success";
	}

	/**
	 * @desc 事件报警关注
	 * @return
	 */
	public String attentionEvent(){
		
		PSysUser user = this.getPSysUser();
		
		FEventSrc fEventSrc = new FEventSrc();
        fEventSrc.setStaffNo(user.getStaffNo());
        fEventSrc.setOrgNo(user.getOrgNo());
        fEventSrc.setFromId(fromId);
        fEventSrc.setEventType(Byte.parseByte("2"));
        fEventSrc.setEventCode(eventCode);
        fEventSrc.setEventName(eventName);
		
		try {
			alertEventManagerImpl.attentionEvent(fromId, fEventSrc);
		} catch (Exception e) {
			return ERROR;
//			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * chenjg
	 * 是否事件报警关注
	 * @return
	 */
	public String isAttentionEvent(){
		try {
			ETmnlEventFull eTmnlEventFull = alertEventManagerImpl.getEventByRowId(fromId);
			if(null == eTmnlEventFull){
				isAttention = true;
			}else{
				isAttention = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * Action 方法alertDataItem
	 * 
	 * @return 查询报警数事件数据项名细
	 * @throws Exception
	 */
	public String alertDataItem() throws Exception {

		setAlertDataItemList(alertEventManagerImpl.queryAlertDataItem(orgNo,rd,
				storeMode,super.getPSysUser()));
		return "success";
	}

	/**
	 * Action方法 alertEventCount
	 * 
	 * @return 报警事件统计
	 * @throws Exception
	 */
	public String alertEventCount() throws Exception {
		this.setAlertEventCount(alertEventManagerImpl
				.queryAlertEventCount(super.getPSysUser()));
		return "success";
	}

	/**
	 * chenjg
	 * 查询终端地址
	 */
	public String queryTerminalAddr() throws Exception{
		
		List<TerminalAddr> list = new ArrayList<TerminalAddr>();
		terminalList = new ArrayList<TerminalAddr>();
		list = alertEventManagerImpl.getTerminalAddrByConsNo(consNo);
		if(list.size()>1){
			TerminalAddr terminalAddr = new TerminalAddr();
			terminalAddr.setTerminalId("-1");
			terminalAddr.setTerminalAddr("全部");
			list.add(terminalAddr);
		}
		
		for(int i = list.size()-1 ; i >=0 ; i--){
			terminalList.add(list.get(i));
		}
		return "success";
	}
	
	/**
	 * 
	 * 查询事件类型
	 */
	public String queryEventType() throws Exception{
		this.setBpe(this.alertEventManagerImpl.findEventType());
		return SUCCESS;
	}
	
	// getters and setters
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	@JSON(serialize = false)
	public IAlertEventManager getAlertEventManagerImpl() {
		return alertEventManagerImpl;
	}

	public void setAlertEventManagerImpl(
			IAlertEventManager alertEventManagerImpl) {
		this.alertEventManagerImpl = alertEventManagerImpl;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<SimpleETmnlEvent> getAlertEventList() {
		return alertEventList;
	}

	public void setAlertEventList(List<SimpleETmnlEvent> alertEventList) {
		this.alertEventList = alertEventList;
	}

	public List<AlertDataItem> getAlertDataItemList() {
		return alertDataItemList;
	}

	public void setAlertDataItemList(List<AlertDataItem> alertDataItemList) {
		this.alertDataItemList = alertDataItemList;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getRd() {
		return rd;
	}

	public void setRd(String rd) {
		this.rd = rd;
	}

	public String getStoreMode() {
		return storeMode;
	}

	public void setStoreMode(String storeMode) {
		this.storeMode = storeMode;
	}

	public List<Object> getAlertEventCount() {
		return alertEventCount;
	}

	public void setAlertEventCount(List<Object> alertEventCount) {
		this.alertEventCount = alertEventCount;
	}

	public String getAlertLevel() {
		return alertLevel;
	}

	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
	}


	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
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

	public boolean isAttention() {
		return isAttention;
	}

	public void setAttention(boolean isAttention) {
		this.isAttention = isAttention;
	}

	public String getConsType() {
		return consType;
	}

	public void setConsType(String consType) {
		this.consType = consType;
	}

	public List<TerminalAddr> getTerminalList() {
		return terminalList;
	}

	public void setTerminalList(List<TerminalAddr> terminalList) {
		this.terminalList = terminalList;
	}

	public String getTerminalAddr() {
		return terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	public List<BProtocolEvent> getBpe() {
		return bpe;
	}

	public void setBpe(List<BProtocolEvent> bpe) {
		this.bpe = bpe;
	}
	
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
}
