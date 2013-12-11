package com.nari.qrystat.querycollpoint;

import java.util.List;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.support.Page;

public class AutoSendQueryAction extends BaseAction {
	private IAutoSendQueryManager autoSendQueryManager;
	
	/**
	 *  the autoSendQueryManager to set
	 * @param autoSendQueryManager the autoSendQueryManager to set
	 */
	public void setAutoSendQueryManager(IAutoSendQueryManager autoSendQueryManager) {
		this.autoSendQueryManager = autoSendQueryManager;
	}

	private List<AutoSendQuery> ASQList;
	private long totalCount;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private boolean success = true;
	private String consType;
	private String nodeType;
	private String nodeValue;
	private String tgId;
	private String tmnlAssetNo;
	private String orgType;
	
	
	public String queryAutoSendQuery() throws Exception{
		try {														
			Page<AutoSendQuery> ASQPage = this.autoSendQueryManager.findASQuery(tgId,orgType,tmnlAssetNo,nodeType,nodeValue,consType, start, limit);
			ASQList = ASQPage.getResult();
			totalCount = ASQPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	} 
	
	public String querytmnlAssetNo() throws Exception{
		try {
			ASQList = this.autoSendQueryManager.findtmnlAssetNo(tgId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 *  the aSQList
	 * @return the aSQList
	 */
	public List<AutoSendQuery> getASQList() {
		return ASQList;
	}

	/**
	 *  the aSQList to set
	 * @param aSQList the aSQList to set
	 */
	public void setASQList(List<AutoSendQuery> aSQList) {
		ASQList = aSQList;
	}

	/**
	 *  the totalCount
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 *  the totalCount to set
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 *  the start
	 * @return the start
	 */
	public long getStart() {
		return start;
	}

	/**
	 *  the start to set
	 * @param start the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}

	/**
	 *  the limit
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 *  the limit to set
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 *  the success
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 *  the success to set
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 *  the consType
	 * @return the consType
	 */
	public String getConsType() {
		return consType;
	}

	/**
	 *  the consType to set
	 * @param consType the consType to set
	 */
	public void setConsType(String consType) {
		this.consType = consType;
	}

	/**
	 *  the nodeType
	 * @return the nodeType
	 */
	public String getNodeType() {
		return nodeType;
	}

	/**
	 *  the nodeType to set
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 *  the nodeValue
	 * @return the nodeValue
	 */
	public String getNodeValue() {
		return nodeValue;
	}

	/**
	 *  the nodeValue to set
	 * @param nodeValue the nodeValue to set
	 */
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	/**
	 *  the tgId
	 * @return the tgId
	 */
	public String getTgId() {
		return tgId;
	}

	/**
	 *  the tgId to set
	 * @param tgId the tgId to set
	 */
	public void setTgId(String tgId) {
		this.tgId = tgId;
	}

	/**
	 *  the tmnlAssetNo
	 * @return the tmnlAssetNo
	 */
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	/**
	 *  the tmnlAssetNo to set
	 * @param tmnlAssetNo the tmnlAssetNo to set
	 */
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	/**
	 *  the orgType
	 * @return the orgType
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 *  the orgType to set
	 * @param orgType the orgType to set
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

}
