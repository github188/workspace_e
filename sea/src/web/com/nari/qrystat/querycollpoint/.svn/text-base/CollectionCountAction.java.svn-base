package com.nari.qrystat.querycollpoint;
import java.util.List;

import org.apache.log4j.Logger;

import sun.rmi.runtime.Log;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

public class CollectionCountAction extends BaseAction{
	private long totalCount;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private boolean success = true;
	private List<CollectionCountDTO> collectionCountList;
	private List<CollectionCountDTO> collectionCountSUMList;
	private Logger logger = Logger.getLogger(this.getClass());
	private ICollectionCountManager collectionCountManager;
	private String consNo;
	private PSysUser pSysUser;
	private String dateStart;
	private String dateEnd;
	private String orgNo;
	private String treeType;
	
	
	public String getTreeType() {
		return treeType;
	}



	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}



	public String getOrgNo() {
		return orgNo;
	}



	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}



	public String getDateStart() {
		return dateStart;
	}



	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}



	public String getDateEnd() {
		return dateEnd;
	}



	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}



	public List<CollectionCountDTO> getCollectionCountSUMList() {
		return collectionCountSUMList;
	}



	public void setCollectionCountSUMList(
			List<CollectionCountDTO> collectionCountSUMList) {
		this.collectionCountSUMList = collectionCountSUMList;
	}



	public PSysUser getpSysUser() {
		return pSysUser;
	}



	public void setpSysUser(PSysUser pSysUser) {
		this.pSysUser = pSysUser;
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



	public List<CollectionCountDTO> getCollectionCountList() {
		return collectionCountList;
	}



	public void setCollectionCountList(List<CollectionCountDTO> collectionCountList) {
		this.collectionCountList = collectionCountList;
	}



	public String getConsNo() {
		return consNo;
	}



	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}



	public void setCollectionCountManager(
			ICollectionCountManager collectionCountManager) {
		this.collectionCountManager = collectionCountManager;
	}
	
	
	/**
	 * 采集点统计
	 * @return
	 * @throws Exception
	 */
	public String queryCollectionCount() throws Exception {
		try {
			this.logger.debug("采集点统计");
			pSysUser =super.getPSysUser();
			Page<CollectionCountDTO> collectionCountDTOPage = this.collectionCountManager.findCollectionCount(treeType,orgNo,consNo, start, limit,pSysUser,dateStart,dateEnd);
			if(collectionCountDTOPage != null){
				collectionCountList = collectionCountDTOPage.getResult();
				totalCount = collectionCountDTOPage.getTotalCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 采集点统计饼图
	 * @return
	 * @throws Exception
	 */
	public String queryCollectionCountSUM() throws Exception {
		try {
			this.logger.debug("采集点统计饼图");
			pSysUser =super.getPSysUser();
			collectionCountSUMList = this.collectionCountManager.findCollectionCountSUM(treeType,orgNo,consNo,pSysUser,dateStart,dateEnd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
