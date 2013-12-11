package com.nari.advapp.vipconsman;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;
/**
 * 日志发布结果统计及查询
 * @author 陈国章
 *
 */

public class LogReleaseQueryAction extends BaseAction {
	
	private LogReleaseQueryManager logReleaseQueryManager;	
	private Logger log=Logger.getLogger(LogReleaseQueryAction.class);
	private Date pubDate;
	public List<LogRelease> logReleaseList;
	private boolean success=true;
	
	
//	public LogReleaseQueryManager getLogReleaseQueryManager() {
//		return logReleaseQueryManager;
//	}

	public void setLogReleaseQueryManager(
			LogReleaseQueryManager logReleaseQueryManager) {
		this.logReleaseQueryManager = logReleaseQueryManager;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public List<LogRelease> getLogReleaseList() {
		return logReleaseList;
	}

	public void setLogReleaseList(List<LogRelease> logReleaseList) {
		this.logReleaseList = logReleaseList;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	

	/**
	 * @description 查询并统计日志发布结果
	 * @param pubDate,pSysUser
	 * @return logReleaseList
	 */

	public String queryLogStastics()
	{
		log.debug("queryLogStastics begin!");
		PSysUser pSysUser=getPSysUser();
		try{
		this.logReleaseList=logReleaseQueryManager.queryLogStastics(pubDate,pSysUser);
		}
		catch(Exception e)
		{
			log.debug("Action中查询日志时错误");
			e.printStackTrace();
		}
		log.debug("queryLogStastics end!");
		return SUCCESS;
	}


}
