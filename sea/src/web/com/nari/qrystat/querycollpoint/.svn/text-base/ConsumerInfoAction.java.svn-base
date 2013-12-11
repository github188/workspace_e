package com.nari.qrystat.querycollpoint;

import java.util.List;
import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.qrystat.querycollpoint.impl.ConsumerInfoManagerImpl;
import com.nari.support.Page;

public class ConsumerInfoAction extends BaseAction {
	/**
	 * consumerInfo 用电客户信息 cconsRtmnl 运行终端信息，这里对应多个运行终端，返回list
	 */
	private ConsumerInfoManagerImpl consumerInfoManager;
	private List<ConsumerInfo> consumerInfoList;
	private List<Tmnlrun> cconsRtmnlList;
	private List<Cmeter> cmeterList;
	private List<NewMpDayPower> newMpDayPowerList;
	private long totalCount;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private boolean success = true;
	private String CONS_NO;	
	private String TG_ID;
	private String DateStart;
	private String DateEnd;
	private List<CmeterInfo> cmeterInfoList;
	private String flagValue;
	private String assetNo;
	
	
	public String getAssetNo() {
		return assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public String getFlagValue() {
		return flagValue;
	}

	public void setFlagValue(String flagValue) {
		this.flagValue = flagValue;
	}

	public List<CmeterInfo> getCmeterInfoList() {
		return cmeterInfoList;
	}

	public void setCmeterInfoList(List<CmeterInfo> cmeterInfoList) {
		this.cmeterInfoList = cmeterInfoList;
	}

	public List<NewMpDayPower> getNewMpDayPowerList() {
		return newMpDayPowerList;
	}

	public void setNewMpDayPowerList(List<NewMpDayPower> newMpDayPowerList) {
		this.newMpDayPowerList = newMpDayPowerList;
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

	/**
	 * @return the tG_ID
	 */
	public String getTG_ID() {
		return TG_ID;
	}

	/**
	 * @param tGID the tG_ID to set
	 */
	public void setTG_ID(String tGID) {
		TG_ID = tGID;
	}

	/**
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}


	/**
	 * @return the start
	 */
	public long getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the cmeterList
	 */
	public List<Cmeter> getCmeterList() {
		return cmeterList;
	}

	/**
	 * @param cmeterList the cmeterList to set
	 */
	public void setCmeterList(List<Cmeter> cmeterList) {
		this.cmeterList = cmeterList;
	}


		/**
	 * @return the consumerInfoManager
	 */
	public ConsumerInfoManagerImpl getConsumerInfoManager() {
		return consumerInfoManager;
	}

	/**
	 * @param consumerInfoManager the consumerInfoManager to set
	 */
	public void setConsumerInfoManager(ConsumerInfoManagerImpl consumerInfoManager) {
		this.consumerInfoManager = consumerInfoManager;
	}

	/**
	 * @return the consumerInfoList
	 */
	public List<ConsumerInfo> getConsumerInfoList() {
		return consumerInfoList;
	}

	/**
	 * @param consumerInfoList the consumerInfoList to set
	 */
	public void setConsumerInfoList(List<ConsumerInfo> consumerInfoList) {
		this.consumerInfoList = consumerInfoList;
	}

	/**
	 * @return the cconsRtmnlList
	 */
	public List<Tmnlrun> getCconsRtmnlList() {
		return cconsRtmnlList;
	}

	/**
	 * @param cconsRtmnlList the cconsRtmnlList to set
	 */
	public void setCconsRtmnlList(List<Tmnlrun> cconsRtmnlList) {
		this.cconsRtmnlList = cconsRtmnlList;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the cONS_NO
	 */
	public String getCONS_NO() {
		return CONS_NO;
	}

	/**
	 * @param cONSNO the cONS_NO to set
	 */
	public void setCONS_NO(String cONSNO) {
		CONS_NO = cONSNO;
	}

		public String queryConsumerInfo() throws Exception {
		//this.addUserLog("客户综合查询", "客户综合查询", "01");
		try {
			this.setConsumerInfoList(this.consumerInfoManager
					.findConsumerInfo(CONS_NO));
			this.setCconsRtmnlList(this.consumerInfoManager
					.findTmnlrun(CONS_NO));
			//this.setCmeterList(this.consumerInfoManager
				//	.findCmeter(CONS_NO,start, limit));
			Page<Cmeter> CMPage = this.consumerInfoManager.findCmeter(CONS_NO,TG_ID,start,limit);
			cmeterList = CMPage.getResult();
			totalCount = CMPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
		
		public String queryNewMpDayPower() throws Exception {
			try {
				Page<NewMpDayPower> newMpDayPage= this.consumerInfoManager.findNewMpDayPower(assetNo,flagValue,CONS_NO, DateStart, DateEnd, start, limit);
				newMpDayPowerList = newMpDayPage.getResult();
				totalCount = newMpDayPage.getTotalCount();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
		}
		
		public String queryCmeterInfo() throws Exception {
			//this.addUserLog("客户综合查询", "客户综合查询", "01");
			try {
				Page<CmeterInfo> cmeterInfoPage = this.consumerInfoManager.findCmeterInfo(CONS_NO, start, limit);
				cmeterInfoList = cmeterInfoPage.getResult();
				totalCount = cmeterInfoPage.getTotalCount();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
		}	
}
