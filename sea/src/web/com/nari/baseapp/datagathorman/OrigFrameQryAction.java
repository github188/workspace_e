package com.nari.baseapp.datagathorman;

import java.util.List;
import org.apache.log4j.Logger;
import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.baseapp.datagatherman.HeartBeat;
import com.nari.baseapp.datagatherman.OnoffStat;
import com.nari.baseapp.datagatherman.OrigFrameQry;
import com.nari.baseapp.datagatherman.OrigFrameQryAsset;
import com.nari.exbase.ExResult;
import com.nari.exmain.MsgExMain;
import com.nari.support.Page;

public class OrigFrameQryAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	private OrigFrameQryManager origFrameQryManager;
	private String terminalAddr;
	private String protocolCode;
	private String message;
	private String messageInfo;
	private String tmnlAssetNo;
	
	
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	public String getMessageInfo() {
		return messageInfo;
	}
	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getProtocolCode() {
		return protocolCode;
	}
	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	private boolean success = true; // 是否成功
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;
	private String DateStart;
	private String DateEnd;
	private List<OrigFrameQry> origFrameQryList;
	private String origFrameQryID;
	private List<OrigFrameQryAsset> origFrameQryAssetList;
	private List<OrigFrameQryAsset> origFrameQryAssetList2;
	private List<HeartBeat> hbList;
	private String consNo;
	private String terminalId;
	private List<OnoffStat> oosList;
	private int onNum;
	private int offNum;
	private Page<HeartBeat> hbPage;
	
	public List<OrigFrameQryAsset> getOrigFrameQryAssetList2() {
		return origFrameQryAssetList2;
	}
	public void setOrigFrameQryAssetList2(
			List<OrigFrameQryAsset> origFrameQryAssetList2) {
		this.origFrameQryAssetList2 = origFrameQryAssetList2;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public String getDateStart() {
		return DateStart;
	}
	public void setDateStart(String dateStart) {
		DateStart = dateStart;
	}
	public String getDateEnd() {
		return DateEnd;
	}
	public void setDateEnd(String dateEnd) {
		DateEnd = dateEnd;
	}
	public String getOrigFrameQryID() {
		return origFrameQryID;
	}
	public void setOrigFrameQryID(String origFrameQryID) {
		this.origFrameQryID = origFrameQryID;
	}
	public List<OrigFrameQryAsset> getOrigFrameQryAssetList() {
		return origFrameQryAssetList;
	}
	public void setOrigFrameQryAssetList(
			List<OrigFrameQryAsset> origFrameQryAssetList) {
		this.origFrameQryAssetList = origFrameQryAssetList;
	}
	public OrigFrameQryManager getOrigFrameQryManager() {
		return origFrameQryManager;
	}
	public void setOrigFrameQryManager(OrigFrameQryManager origFrameQryManager) {
		this.origFrameQryManager = origFrameQryManager;
	}
	public String getTerminalAddr() {
		return terminalAddr;
	}
	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
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

	public List<OrigFrameQry> getOrigFrameQryList() {
		return origFrameQryList;
	}
	public void setOrigFrameQryList(List<OrigFrameQry> origFrameQryList) {
		this.origFrameQryList = origFrameQryList;
	}
	
	public List<HeartBeat> getHbList() {
		return hbList;
	}
	public void setHbList(List<HeartBeat> hbList) {
		this.hbList = hbList;
	}
	
	public String getConsNo() {
		return consNo;
	}
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	
	public List<OnoffStat> getOosList() {
		return oosList;
	}
	public void setOosList(List<OnoffStat> oosList) {
		this.oosList = oosList;
	}
	
	public int getOnNum() {
		return onNum;
	}
	public void setOnNum(int onNum) {
		this.onNum = onNum;
	}
	public int getOffNum() {
		return offNum;
	}
	public void setOffNum(int offNum) {
		this.offNum = offNum;
	}
	public String queryorigFrameQry() throws Exception{
		try{
		Page<OrigFrameQry> origFrameQryPage = this.origFrameQryManager.findOrigFrameQry(terminalAddr, DateStart, DateEnd, start, limit);
		origFrameQryList = origFrameQryPage.getResult();
		totalCount = origFrameQryPage.getTotalCount();
		this.logger.debug("报文查询");
		return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	} 
	
	public String queryOrigFrameQryAsset() throws Exception{
		this.origFrameQryAssetList = this.origFrameQryManager.findOrigFrameQryAsset(origFrameQryID);
		this.logger.debug("报文查询");
		return SUCCESS;
	}
	
	
	public String queryOrigFrameQryTmnl() throws Exception{
		this.origFrameQryAssetList2 = this.origFrameQryManager.findOrigFrameQryTmnl(terminalAddr);
		this.logger.debug("客户综合查询");
		return SUCCESS;
	}
	
	
	public String queryOrigFrameQryMessage() throws Exception{
		MsgExMain msgMain = MsgExMain.getInstance();
		ExResult result = msgMain.getExResult(message, protocolCode);
		if(result != null){
			this.setMessageInfo(result.toString());
			this.logger.debug("报文解析");
		}else{
			this.setMessageInfo("");
		}
		
		return SUCCESS;
	}
	
	public Page<HeartBeat> getHbPage() {
		return hbPage;
	}
	public void setHbPage(Page<HeartBeat> hbPage) {
		this.hbPage = hbPage;
	}
	/**
	 * 查询心跳信息
	 * @param consNo,DateStart,DateEnd
	 * @author 陈国章
	 * @return
	 */
	public String queryHeartBeat()
	{
		this.logger.debug("Action:心跳查询开始");
		this.hbPage=this.origFrameQryManager.queryHeartBeat(consNo,terminalAddr,DateStart, DateEnd,protocolCode,start,limit);
		totalCount=hbPage.getTotalCount();
		this.hbList=hbPage.getResult();		
		this.logger.debug("Action:心跳查询结束");
		return SUCCESS;
	}
	/**
	 * 查询在线离线数量
	 * @params
	 */
	public String onoffStat()
	{
		this.logger.debug("Action:离线数查询开始");
		this.oosList=this.origFrameQryManager.onoffStat(consNo,terminalAddr,DateStart,DateEnd);
		for(OnoffStat oos:oosList)
		{
			this.onNum=oos.getOnNum();
			this.offNum=oos.getOffNum();
		}
		this.logger.debug("Action:离线数查询结束");
		return SUCCESS;
	}		

}
