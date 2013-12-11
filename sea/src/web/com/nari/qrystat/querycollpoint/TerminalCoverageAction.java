package com.nari.qrystat.querycollpoint;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;
import com.opensymphony.xwork2.ActionContext;

public class TerminalCoverageAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	private boolean success=true;
	private String message;
	private String node;
	private List<Map> resultList;
	private ITerminalCoverageManager terminalCoverageManager;
	/***按容量查询***/
	public String queryCoverageCap(){
		try {
			if(node.equals("node_discard")){
				return SUCCESS;
			}
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			resultList=terminalCoverageManager.findByCapacity(user, node);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getCause());
			this.message=e.getMessage();
		}
		return SUCCESS;
	}
	/***按地区查询***/
	public String queryCoverageArea(){
		try {
			if(node.equals("node_discard")){
				return SUCCESS;
			}
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			resultList=terminalCoverageManager.findByArea(user, node);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getCause());
			this.message=e.getMessage();
		}
		return SUCCESS;
	}
	
	public String getMessage() {
		return message;
	}

	public void setNode(String node) {
		this.node = node;
	}
	public boolean isSuccess() {
		return success;
	}
	public List<Map> getResultList() {
		return resultList;
	}
	public void setTerminalCoverageManager(
			ITerminalCoverageManager terminalCoverageManager) {
		this.terminalCoverageManager = terminalCoverageManager;
	}
}
