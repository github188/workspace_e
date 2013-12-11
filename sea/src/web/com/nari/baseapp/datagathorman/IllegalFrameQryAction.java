package com.nari.baseapp.datagathorman;

import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.baseapp.datagatherman.IllegalFrameQry;
import com.nari.baseapp.datagatherman.OrigFrameQryAsset;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 非法报文查询action
 * 
 * @author ChunMingLi 2010-4-25
 * 
 */
public class IllegalFrameQryAction extends BaseAction {
	// logger 日志
	private Logger logger = Logger.getLogger(this.getClass());
	// 非法报文管理
	private IllegalFrameQryManager illegalFrameQryManager;
	// 报文逻辑地址
	private String terminalAddr;
	// 查询开始时间
	private String DateStart;
	// 查询结束时间
	private String DateEnd;
	// 分页页码 默认为零
	public long start = 0;
	// 分页显示数量 默认为 Constans.DEFAULT_PAGE_SIZE
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	// 查询总数
	private long totalCount;
	// 报文用户号
	private String illegalFrameQryID;
	// 非法报文集合
	private List<IllegalFrameQry> illegalFrameQryList;
	// 逻辑地址集合
	private List<OrigFrameQryAsset> illegalFrameQryAssetList;
	//success
	private boolean success = true; // 是否成功

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public IllegalFrameQryManager getIllegalFrameQryManager() {
		return illegalFrameQryManager;
	}

	public void setIllegalFrameQryManager(
			IllegalFrameQryManager illegalFrameQryManager) {
		this.illegalFrameQryManager = illegalFrameQryManager;
	}

	public String getTerminalAddr() {
		return terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
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

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public String getIllegalFrameQryID() {
		return illegalFrameQryID;
	}

	public void setIllegalFrameQryID(String illegalFrameQryID) {
		this.illegalFrameQryID = illegalFrameQryID;
	}

	public List<IllegalFrameQry> getIllegalFrameQryList() {
		return illegalFrameQryList;
	}

	public void setIllegalFrameQryList(List<IllegalFrameQry> illegalFrameQryList) {
		this.illegalFrameQryList = illegalFrameQryList;
	}

	public List<OrigFrameQryAsset> getIllegalFrameQryAssetList() {
		return illegalFrameQryAssetList;
	}

	public void setIllegalFrameQryAssetList(
			List<OrigFrameQryAsset> illegalFrameQryAssetList) {
		this.illegalFrameQryAssetList = illegalFrameQryAssetList;
	}

	public String queryIllegalFrameQry() throws DBAccessException {
		try {
			Page<IllegalFrameQry> illegalFrameQryPage = this.illegalFrameQryManager
					.findIllegalFrameQry(terminalAddr, DateStart, DateEnd,
							start, limit);
			illegalFrameQryList = illegalFrameQryPage.getResult();
			totalCount = illegalFrameQryPage.getTotalCount();
			logger.debug("非法报文查询");
			return SUCCESS;
		} catch (Exception e) {
			logger.debug(e);
		}
		return SUCCESS;
	}

	public String queryIllegalFrameQryAsset() throws DBAccessException {
		this.illegalFrameQryAssetList = this.illegalFrameQryManager
				.findOrigFrameQryAsset(illegalFrameQryID);
		logger.debug("非法报文查询");
		return SUCCESS;
	}

}
