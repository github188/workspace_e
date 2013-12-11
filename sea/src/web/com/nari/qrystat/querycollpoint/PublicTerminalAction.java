package com.nari.qrystat.querycollpoint;

import java.util.ArrayList;
import java.util.List;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.querycollpoint.impl.PublicTerminalManagerImpl;
import com.nari.support.Page;
//import com.nari.sysman.securityman.impl.SysPrivilige;

public class PublicTerminalAction extends BaseAction {
	private PublicTerminalManagerImpl publicTerminalManager;
	//返回List
	private List<PublicTerminal> publicTerminalList; 
	private List<PublicTerminal> privateTerminalList;
	private List<PublicTerminal> gateTerminalList;
	//分页参数
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;
	
	private boolean success = true;
	//页面传递的参数
	private String statusName;
	private String pbtype;
	private String pbid;
	private String prid;
	private String publicOrgType;
	private String consType;
	private PSysUser pSysUser;
	private String tmnlAssetNo;
	
	
	
	private PublicTerminal terminalStatus;
	

	public String queryPublicTerminal() throws Exception{
		try {
			pSysUser =(PSysUser)super.getSession().getAttribute("pSysUser");
			Page<PublicTerminal> PBTpage = this.getPublicTerminalManager()
					.findPBTerminal(consType,pbid,statusName,publicOrgType,pbtype, start, limit,pSysUser);
			publicTerminalList = PBTpage.getResult();
			totalCount = PBTpage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String queryPrivateTerminal() throws Exception{
		try {
			pSysUser =(PSysUser)super.getSession().getAttribute("pSysUser");
			Page<PublicTerminal> PRTpage = this.getPublicTerminalManager()
					.findPRTerminal(consType,prid,statusName,publicOrgType,pbtype, start, limit,pSysUser);
			privateTerminalList = PRTpage.getResult();
			totalCount = PRTpage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 终端状态
	 */
	public String queryTmnlStatus() throws Exception{
		try{
			privateTerminalList = new ArrayList<PublicTerminal>();
			terminalStatus = new PublicTerminal();
			terminalStatus.setStatusCode("00");
			terminalStatus.setStatusName("全部");
			privateTerminalList.add(terminalStatus);
			privateTerminalList.addAll(publicTerminalManager.findTmnlStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	
	public void setTerminalStatus(PublicTerminal terminalStatus) {
		this.terminalStatus = terminalStatus;
	}

	public PublicTerminal getTerminalStatus() {
		return terminalStatus;
	}
	
	
	public String getPublicOrgType() {
		return publicOrgType;
	}

	public void setPublicOrgType(String publicOrgType) {
		this.publicOrgType = publicOrgType;
	}

	public PSysUser getpSysUser() {
		return pSysUser;
	}

	public void setpSysUser(PSysUser pSysUser) {
		this.pSysUser = pSysUser;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the prid
	 */
	public String getPrid() {
		return prid;
	}

	/**
	 * @param prid the prid to set
	 */
	public void setPrid(String prid) {
		this.prid = prid;
	}

	/**
	 * @return the privateTerminalList
	 */
	public List<PublicTerminal> getPrivateTerminalList() {
		return privateTerminalList;
	}

	/**
	 * @param privateTerminalList the privateTerminalList to set
	 */
	public void setPrivateTerminalList(List<PublicTerminal> privateTerminalList) {
		this.privateTerminalList = privateTerminalList;
	}
	/**
	 * @return the pbtype
	 */
	public String getPbtype() {
		return pbtype;
	}

	/**
	 * @param pbtype the pbtype to set
	 */
	public void setPbtype(String pbtype) {
		this.pbtype = pbtype;
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
	 * @return the pbid
	 */
	public String getPbid() {
		return pbid;
	}

	/**
	 * @param pbid the pbid to set
	 */
	public void setPbid(String pbid) {
		this.pbid = pbid;
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
	 * @return the publicTerminalList
	 */
	public List<PublicTerminal> getPublicTerminalList() {
		return publicTerminalList;
	}

	/**
	 * @param publicTerminalList the publicTerminalList to set
	 */
	public void setPublicTerminalList(List<PublicTerminal> publicTerminalList) {
		this.publicTerminalList = publicTerminalList;
	}

	
	/**
	 * @return the publicTerminalManager
	 */
	public PublicTerminalManagerImpl getPublicTerminalManager() {
		return publicTerminalManager;
	}

	/**
	 * @param publicTerminalManager the publicTerminalManager to set
	 */
	public void setPublicTerminalManager(
			PublicTerminalManagerImpl publicTerminalManager) {
		this.publicTerminalManager = publicTerminalManager;
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
	 *  the gateTerminalList
	 * @return the gateTerminalList
	 */
	public List<PublicTerminal> getGateTerminalList() {
		return gateTerminalList;
	}
	/**
	 *  the gateTerminalList to set
	 * @param gateTerminalList the gateTerminalList to set
	 */
	public void setGateTerminalList(List<PublicTerminal> gateTerminalList) {
		this.gateTerminalList = gateTerminalList;
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
	
	
}
