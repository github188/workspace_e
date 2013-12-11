package com.nari.qrystat.querycollpoint;

import java.util.List;

import org.apache.log4j.Logger;
import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.support.Page;

public class ConsumerInfoSubAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ConsumerInfoSubAction.class);
	private ICCustManager ccustManager;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private boolean success = true;
	private String custNo;
	private long totalCount;
	private List<CCust> custList;
	private List<Ccontact> contactList;
	private List<CSP> cspList;
	private List<CPS> cpsList;
	private String consNo;
	private String SIM_NO;
	private List<RSIMCharge> simList;
	private String ASSET_NO;
	private List<DMeter> DMeterList;
	private List<CITRun> CITRunList;
	private String tFactor;
	private List<Gtran> gtranList;
	private String CONS_ID;

	private String consType;
	
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

	public List<Gtran> getGtranList() {
		return gtranList;
	}

	public void setGtranList(List<Gtran> gtranList) {
		this.gtranList = gtranList;
	}

	public String getCONS_ID() {
		return CONS_ID;
	}

	public void setCONS_ID(String cONSID) {
		CONS_ID = cONSID;
	}

	public String gettFactor() {
		return tFactor;
	}

	public void settFactor(String tFactor) {
		this.tFactor = tFactor;
	}

	public List<CITRun> getCITRunList() {
		return CITRunList;
	}

	public void setCITRunList(List<CITRun> cITRunList) {
		CITRunList = cITRunList;
	}

	/**
	 * @return the dMeterList
	 */
	public List<DMeter> getDMeterList() {
		return DMeterList;
	}

	/**
	 * @param dMeterList
	 *            the dMeterList to set
	 */
	public void setDMeterList(List<DMeter> dMeterList) {
		DMeterList = dMeterList;
	}

	/**
	 * @return the aSSET_NO
	 */
	public String getASSET_NO() {
		return ASSET_NO;
	}

	/**
	 * @param aSSETNO
	 *            the aSSET_NO to set
	 */
	public void setASSET_NO(String aSSETNO) {
		ASSET_NO = aSSETNO;
	}

	/**
	 * @return the sIM_NO
	 */
	public String getSIM_NO() {
		return SIM_NO;
	}

	/**
	 * @param sIMNO
	 *            the sIM_NO to set
	 */
	public void setSIM_NO(String sIMNO) {
		SIM_NO = sIMNO;
	}

	/**
	 * @return the simList
	 */
	public List<RSIMCharge> getSimList() {
		return simList;
	}

	/**
	 * @param simList
	 *            the simList to set
	 */
	public void setSimList(List<RSIMCharge> simList) {
		this.simList = simList;
	}

	/**
	 * @return the cpsList
	 */
	public List<CPS> getCpsList() {
		return cpsList;
	}

	/**
	 * @param cpsList
	 *            the cpsList to set
	 */
	public void setCpsList(List<CPS> cpsList) {
		this.cpsList = cpsList;
	}

	/**
	 * @return the consNo
	 */
	public String getConsNo() {
		return consNo;
	}

	/**
	 * @param consNo
	 *            the consNo to set
	 */
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	/**
	 * @return the cspList
	 */
	public List<CSP> getCspList() {
		return cspList;
	}

	/**
	 * @param cspList
	 *            the cspList to set
	 */
	public void setCspList(List<CSP> cspList) {
		this.cspList = cspList;
	}

	/**
	 * @return the contactList
	 */
	public List<Ccontact> getContactList() {
		return contactList;
	}

	/**
	 * @param contactList
	 *            the contactList to set
	 */
	public void setContactList(List<Ccontact> contactList) {
		this.contactList = contactList;
	}

	/**
	 * @return the start
	 */
	public long getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
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
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the custNo
	 */
	public String getCustNo() {
		return custNo;
	}

	/**
	 * @param custNo
	 *            the custNo to set
	 */
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	/**
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the custList
	 */
	public List<CCust> getCustList() {
		return custList;
	}

	/**
	 * @param custList
	 *            the custList to set
	 */
	public void setCustList(List<CCust> custList) {
		this.custList = custList;
	}

	//
	// /**
	// * @return the ccustManager
	// */
	// public ICCustManager getCcustManager() {
	// return ccustManager;
	// }

	/**
	 * @param ccustManager
	 *            the ccustManager to set
	 */
	public void setCcustManager(ICCustManager ccustManager) {
		this.ccustManager = ccustManager;
	}

	public String queryCust() throws Exception {

		try {
			this.setCustList(this.ccustManager.findCCust(custNo));
			// Page<Ccontact> contactPage =
			// this.ccustManager.findCcontact(custNo, start, limit);
			// contactList = contactPage.getResult();
			// totalCount = contactPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String queryCSP() throws Exception {

		try {
			this.setCspList(this.ccustManager.findCSP(consNo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String querycontact() throws Exception {

		try {
			Page<Ccontact> contactPage = this.ccustManager.findCcontact(custNo,
					start, limit);
			contactList = contactPage.getResult();
			totalCount = contactPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String queryCPS() throws Exception {
		try {
			Page<CPS> contactPage = this.ccustManager.findCPS(consNo, start,
					limit);
			cpsList = contactPage.getResult();
			totalCount = contactPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String querySIM() throws Exception {
		try {
			Page<RSIMCharge> SIMPage = this.ccustManager.findSIM(SIM_NO, start,
					limit);
			simList = SIMPage.getResult();
			totalCount = SIMPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String queryDMeter() throws Exception {

		try {
			this.setDMeterList(this.ccustManager.findDMeter(consNo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String queryCITRun() throws Exception {
		try {
			Page<CITRun> CITRunPage = this.ccustManager.findCITRun(tFactor,
					start, limit);
			CITRunList = CITRunPage.getResult();
			totalCount = CITRunPage.getTotalCount();

		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}
	
	public String queryGtran() throws Exception{
		try{
			this.setGtranList(this.ccustManager.findGtran(CONS_ID, consType));
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}
	
	public String queryGtranGrid() throws Exception {

		try {
			Page<Gtran> gtranPage = this.ccustManager.findGtran(CONS_ID, start, limit);
			gtranList = gtranPage.getResult();
			totalCount = gtranPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
