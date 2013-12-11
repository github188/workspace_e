package com.nari.runman.archivesman;

import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.action.baseaction.Errors;
import com.nari.customer.CCons;
import com.nari.runman.runstatusman.RemoteDebugDto;
import com.nari.support.Page;

/**
 * 远程调试Action
 * @author Taoshide
 *
 */
public class RemoteDebugAction extends BaseAction {

	// 注入类
	IRemoteDebugManager remoteDebugManager;

	//分类处理参数
	private long start = 0;
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;

	// 返回确认
	public boolean success = true;
	public Errors errors;

	//返回到前台的数据
	public List<RemoteDebugDto> remotedebuglist;
	
	


	/**
	 * 查询远程调试列表
	 * @return
	 * @throws Exception
	 */
	public String queryRemoteDebugList() throws Exception{
		String cpNo = super.getRequest().getParameter("cpNo");
		Page<RemoteDebugDto> pages = remoteDebugManager.queryRemoteDebugList(cpNo,start,limit);
		if(null!=pages && pages.getTotalCount()>0){
			totalCount=pages.getTotalCount();
			remotedebuglist = pages.getResult();
		}
		return SUCCESS;
	}
	
	
	

	public List<RemoteDebugDto> getRemotedebuglist() {
		return remotedebuglist;
	}

	public void setRemotedebuglist(List<RemoteDebugDto> remotedebuglist) {
		this.remotedebuglist = remotedebuglist;
	}

	@JSON(serialize = false)
	public IRemoteDebugManager getRemoteDebugManager() {
		return remoteDebugManager;
	}
	public void setRemoteDebugManager(IRemoteDebugManager remoteDebugManager) {
		this.remoteDebugManager = remoteDebugManager;
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

	
	
}
