package com.nari.qrystat.querycollpoint;

import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.support.Page;

public class RCPAction extends BaseAction {
	private static Logger logger = Logger.getLogger(RCPAction.class);
	private IRCPManager rcpManager;
	private String consNo;
	private String RcpCommPara;
	private List<RCP> rcpList;
	private boolean success = true;
	private long totalCount;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	public List<RCollObj> rCollObjList;
	public List<RcpCommPara> rcpCommParaList;
	
	

	public String getRcpCommPara() {
		return RcpCommPara;
	}

	public void setRcpCommPara(String rcpCommPara) {
		RcpCommPara = rcpCommPara;
	}

	public List<RcpCommPara> getRcpCommParaList() {
		return rcpCommParaList;
	}

	public void setRcpCommParaList(List<RcpCommPara> rcpCommParaList) {
		this.rcpCommParaList = rcpCommParaList;
	}

	public List<RCollObj> getrCollObjList() {
		return rCollObjList;
	}

	public void setrCollObjList(List<RCollObj> rCollObjList) {
		this.rCollObjList = rCollObjList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<RCP> getRcpList() {
		return rcpList;
	}
	public void setRcpList(List<RCP> rcpList) {
		this.rcpList = rcpList;
	}
	public String getConsNo() {
		return consNo;
	}
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	public void setRcpManager(IRCPManager rcpManager) {
		this.rcpManager = rcpManager;
	}
	
	public String queryRCP() throws Exception {
		
		try{
			this.setRcpList(this.rcpManager.findRCP(consNo));
			this.setRcpCommParaList(this.rcpManager.findRcpCommPara(consNo));
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
		}
		return SUCCESS;
	}
	
	public String queryRCollObj() throws Exception{
		try{
			Page<RCollObj> rCollObjPage = this.rcpManager.findRcpCharge(consNo, start, limit);
			rCollObjList = rCollObjPage.getResult();
			totalCount = rCollObjPage.getTotalCount();
		}catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
		}
		return SUCCESS;
	}
	
}
