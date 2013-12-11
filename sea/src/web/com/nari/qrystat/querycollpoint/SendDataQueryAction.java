package com.nari.qrystat.querycollpoint;

import java.util.List;
import java.util.Map;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.GeneralDataBean;
import com.nari.qrystat.querycollpoint.impl.SendDataQueryManagerImpl;
import com.nari.support.Page;

public class SendDataQueryAction extends BaseAction {
	private SendDataQueryManagerImpl sendDataQueryManager;
	private List<SendDataQuery> SDQList;
	private List<SendDataQueryB> SDQListB;
	private List<SendDataQueryDay> SDQListDay;
	private List<SendDataQueryBFail> SDQListBFail;
	private List<SendDataQueryC> SDQListC;
	private List<SendDataQueryD> SDQListD;
	private List<SendDataQueryF> SDQListF;
	private Map<String,List<SendDataQueryC>> SDQMapD;
	private long totalCount;
	public int start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private boolean success = true;
	private String CONS_NO;	
	private String TG_ID;
	private String DateStart;
	private String DateEnd;
	private String CurDate;
	private String asserNo;
	private String dataDate;
	private List<GeneralDataBean> generalData;
	private PSysUser pSysUser;
	private String sendDQType;
	private String sendDQOrgType;
	private String addr;
	private String sendDataQueryFlag;
	private String sendDataQueryFail;
	private String MR_SECT_NO;
	 private String fieldConsNo;
	private String 	fieldassetNo;
	
	
	public String getFieldConsNo() {
		return fieldConsNo;
	}

	public void setFieldConsNo(String fieldConsNo) {
		this.fieldConsNo = fieldConsNo;
	}

	public String getFieldassetNo() {
		return fieldassetNo;
	}

	public void setFieldassetNo(String fieldassetNo) {
		this.fieldassetNo = fieldassetNo;
	}

	public List<SendDataQueryDay> getSDQListDay() {
		return SDQListDay;
	}

	public void setSDQListDay(List<SendDataQueryDay> sDQListDay) {
		SDQListDay = sDQListDay;
	}

	public String getMR_SECT_NO() {
		return MR_SECT_NO;
	}

	public void setMR_SECT_NO(String mRSECTNO) {
		MR_SECT_NO = mRSECTNO;
	}

	public String getSendDataQueryFail() {
		return sendDataQueryFail;
	}

	public void setSendDataQueryFail(String sendDataQueryFail) {
		this.sendDataQueryFail = sendDataQueryFail;
	}

	public List<SendDataQueryBFail> getSDQListBFail() {
		return SDQListBFail;
	}

	public void setSDQListBFail(List<SendDataQueryBFail> sDQListBFail) {
		SDQListBFail = sDQListBFail;
	}

	public String getSendDataQueryFlag() {
		return sendDataQueryFlag;
	}

	public void setSendDataQueryFlag(String sendDataQueryFlag) {
		this.sendDataQueryFlag = sendDataQueryFlag;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getSendDQType() {
		return sendDQType;
	}

	public void setSendDQType(String sendDQType) {
		this.sendDQType = sendDQType;
	}

	public String getSendDQOrgType() {
		return sendDQOrgType;
	}

	public void setSendDQOrgType(String sendDQOrgType) {
		this.sendDQOrgType = sendDQOrgType;
	}

	public PSysUser getpSysUser() {
		return pSysUser;
	}

	public void setpSysUser(PSysUser pSysUser) {
		this.pSysUser = pSysUser;
	}

	/**
	 * @return the sDQListF
	 */
	public List<SendDataQueryF> getSDQListF() {
		return SDQListF;
	}

	/**
	 * @param sDQListF the sDQListF to set
	 */
	public void setSDQListF(List<SendDataQueryF> sDQListF) {
		SDQListF = sDQListF;
	}

	/**
	 * @return the generalData
	 */
	public List<GeneralDataBean> getGeneralData() {
		return generalData;
	}

	/**
	 * @param generalData the generalData to set
	 */
	public void setGeneralData(List<GeneralDataBean> generalData) {
		this.generalData = generalData;
	}

	/**
	 * @return the asserNo
	 */
	public String getAsserNo() {
		return asserNo;
	}

	/**
	 * @param asserNo the asserNo to set
	 */
	public void setAsserNo(String asserNo) {
		this.asserNo = asserNo;
	}

	/**
	 * @return the dataDate
	 */
	public String getDataDate() {
		return dataDate;
	}

	/**
	 * @param dataDate the dataDate to set
	 */
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	/**
	 * @return the curDate
	 */
	public String getCurDate() {
		return CurDate;
	}

	/**
	 * @param curDate the curDate to set
	 */
	public void setCurDate(String curDate) {
		CurDate = curDate;
	}

	/**
	 * @return the sDQListD
	 */
	public List<SendDataQueryD> getSDQListD() {
		return SDQListD;
	}

	/**
	 * @param sDQListD the sDQListD to set
	 */
	public void setSDQListD(List<SendDataQueryD> sDQListD) {
		SDQListD = sDQListD;
	}

	/**
	 * @return the sDQMapD
	 */
	public Map<String, List<SendDataQueryC>> getSDQMapD() {
		return SDQMapD;
	}

	/**
	 * @param sDQMapD the sDQMapD to set
	 */
	public void setSDQMapD(Map<String, List<SendDataQueryC>> sDQMapD) {
		SDQMapD = sDQMapD;
	}

	/**
	 * @return the sDQListC
	 */
	public List<SendDataQueryC> getSDQListC() {
		return SDQListC;
	}

	/**
	 * @param sDQListC the sDQListC to set
	 */
	public void setSDQListC(List<SendDataQueryC> sDQListC) {
		SDQListC = sDQListC;
	}

	/**
	 * @return the sDQListB
	 */
	public List<SendDataQueryB> getSDQListB() {
		return SDQListB;
	}

	/**
	 * @param sDQListB the sDQListB to set
	 */
	public void setSDQListB(List<SendDataQueryB> sDQListB) {
		SDQListB = sDQListB;
	}

	
	/**
	 * @return the dateStart
	 */
	public String getDateStart() {
		return DateStart;
	}

	/**
	 * @param dateStart the dateStart to set
	 */
	public void setDateStart(String dateStart) {
		DateStart = dateStart;
	}

	/**
	 * @return the dateEnd
	 */
	public String getDateEnd() {
		return DateEnd;
	}

	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(String dateEnd) {
		DateEnd = dateEnd;
	}

	/**
	 * @return the sDQList
	 */
	public List<SendDataQuery> getSDQList() {
		return SDQList;
	}

	/**
	 * @param sDQList the sDQList to set
	 */
	public void setSDQList(List<SendDataQuery> sDQList) {
		SDQList = sDQList;
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
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
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
	 * @param sendDataQueryManager the sendDataQueryManager to set
	 */
	public void setSendDataQueryManager(
			SendDataQueryManagerImpl sendDataQueryManager) {
		this.sendDataQueryManager = sendDataQueryManager;
	}
	
	/**
	 * @return the sendDataQueryManager
	 */

	
	public String querySendDataQuery() throws Exception {
		try{
			pSysUser =super.getPSysUser();
			if(sendDataQueryFail.equals("成功")){
			Page<SendDataQuery> SDQPage = this.sendDataQueryManager.findSDQuery(MR_SECT_NO,sendDataQueryFlag,addr,sendDQOrgType,sendDQType,DateStart,DateEnd,CONS_NO, TG_ID, start, limit,pSysUser);
			SDQList = SDQPage.getResult();
			totalCount = SDQPage.getTotalCount();
			}else{
				SDQList = null;
				totalCount = 0;
			}
		}catch(Exception e){
		e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String querySendDataQueryB() throws Exception {
		try{
			pSysUser =super.getPSysUser();
			if(sendDataQueryFail.equals("成功")){
				Page<SendDataQueryB> SDQPageB = this.sendDataQueryManager.findSendDataQueryB(MR_SECT_NO,sendDataQueryFlag,addr,sendDQOrgType,sendDQType,DateStart,DateEnd,CONS_NO, TG_ID, start, limit,pSysUser);
				SDQListB = SDQPageB.getResult();
				totalCount = SDQPageB.getTotalCount();
			}else{
				Page<SendDataQueryBFail> SDQPageBFail = this.sendDataQueryManager.findSendDataQueryBFail(MR_SECT_NO,sendDataQueryFlag,addr,sendDQOrgType,sendDQType,DateStart,DateEnd,CONS_NO, TG_ID, start, limit,pSysUser);
				SDQListBFail = SDQPageBFail.getResult();
				totalCount = SDQPageBFail.getTotalCount();
			}
			
		}catch(Exception e){
		e.printStackTrace();
		}
		return SUCCESS;
	}
	//查询实时抄表数据
	public String querySendDataQueryDay() throws Exception {
		try{
			pSysUser =super.getPSysUser();
			if(sendDataQueryFail.equals("成功")){
				Page<SendDataQueryDay> SDQPageDay = this.sendDataQueryManager.findSendDataQueryDay(fieldConsNo,fieldassetNo,MR_SECT_NO, sendDataQueryFlag, addr, sendDQOrgType, sendDQType, DateStart, DateEnd, CONS_NO, TG_ID, start, limit, pSysUser);
				SDQListDay= SDQPageDay.getResult();
				totalCount = SDQPageDay.getTotalCount();
			}else{
				Page<SendDataQueryBFail> SDQPageBFail = this.sendDataQueryManager.findSendDataQueryBFail(MR_SECT_NO,sendDataQueryFlag,addr,sendDQOrgType,sendDQType,DateStart,DateEnd,CONS_NO, TG_ID, start, limit,pSysUser);
				SDQListBFail = SDQPageBFail.getResult();
				totalCount = SDQPageBFail.getTotalCount();
			}
			
		}catch(Exception e){
		e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String querySendDataQueryC() throws Exception {
		try{
			pSysUser =(PSysUser)super.getSession().getAttribute("pSysUser");
			this.setSDQMapD(this.sendDataQueryManager.findSendDataQueryD(DateStart, DateEnd, CONS_NO,pSysUser));
		}catch(Exception e){
		e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String querySendDataQueryD() throws Exception {
		try{
			pSysUser =(PSysUser)super.getSession().getAttribute("pSysUser");
			Page<SendDataQueryD> SDQDPage = this.sendDataQueryManager.findSendDataQueryD(CurDate, TG_ID,start,limit,pSysUser);
			SDQListD = SDQDPage.getResult();
			totalCount = SDQDPage.getTotalCount();
		}catch(Exception e){
		e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 测量点点能量曲线
	 * @return
	 * @throws Exception
	 */
	public String queryMpEnergyData() throws Exception {
		try{
			generalData = sendDataQueryManager.findSendDataQueryE(CONS_NO,asserNo,dataDate, pSysUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String querySendDataQueryF() throws Exception {
		try{
			pSysUser =(PSysUser)super.getSession().getAttribute("pSysUser");
			this.setSDQListF(this.sendDataQueryManager.findSendDataQueryF(DateStart, DateEnd, CONS_NO, TG_ID,pSysUser));
		}catch(Exception e){
		e.printStackTrace();
		}
		return SUCCESS;
	}
}
