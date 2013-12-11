package com.nari.runman.runstatusman;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.statreport.AEventStatDBean;
import com.nari.statreport.AEventStatDWindowDto;
import com.nari.statreport.AmmeterHDto;
import com.nari.statreport.AmmeterHWindowDto;
import com.nari.support.Page;

/**
 * 
 * 电能表运行状态和下钻异常
 * @modified ChunMingLi
 * @author 
 * @since  2010-7-02
 *
 */
public class AEventStatDBeanAction extends BaseAction {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private AEventStatDBeanManager aEventStatDBeanManager;
	
	private boolean success = true;
	private long totalCount;
	private List<AEventStatDBean> aEventStatDBeanList;
	
	//电能表异常
	private List<AmmeterHDto> ammeterExceptionList;
	
	/*异常页面参数 */
	private String orgNo;  //供电单位编号
	private String eventNo; //异常事件编号
	
	//电能表异常窗口列表
	private List<AmmeterHWindowDto> ammeterHWindowList;
	
	//电能表异常每日统计
	private List<AEventStatDWindowDto> aEventWindowList;
	
	private String orgName;
	
	/*异常窗口参数 */
	private String eventId;
	private String areaCode;
	
	public Date startDate;
	public Date endDate;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	
	
	
	
	/**
	 * 电能表运行状态
	 * @return success
	 * @throws Exception
	 */
	public String queryAEventStatDBean() throws Exception{
		  PSysUser user = getPSysUser();
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
		try{
//			String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
//			String endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
			
			Page<AEventStatDBean> aEventStatDBean = aEventStatDBeanManager.findAEventStatDBean(user, startDate, endDate, start, limit);
			aEventStatDBeanList = aEventStatDBean.getResult();
			
//			for (int i = 0; i < aEventStatDBeanList.size(); i++) {
//				AEventStatDBean bean = aEventStatDBeanList.get(i);
//				System.out.println(bean.getOrgName()+":"+bean.getCbsb()+"-"+bean.getDbgz()+"-"+bean.getDnbcc()+"-"+bean.getDnbfz()+"-"+bean.getDnbtz()+"-"+bean.getSdxj());
//			}
			
			totalCount = aEventStatDBean.getTotalCount();
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}
	
	/**
	 * 电能表 异常下钻
	 * @author ChunMingLi
	 * @return
	 * @throws Exception
	 */
	public String queryAmmeterException() throws Exception{
		this.logger.debug("下钻电能表异常查询开始：queryAmmeterException()");
		PSysUser user = getPSysUser();
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			this.logger.debug("下钻电能表异常参数：orgNo:" + user.getOrgNo() + "eventNo :" + eventNo + "startDate :" + startDate + "endDate:" + endDate);
		try{
			Page<AmmeterHDto> ammeterHDto = aEventStatDBeanManager.queryAmmeterHDto(user,orgNo, eventNo, startDate, endDate, start, limit);
			this.setAmmeterExceptionList(ammeterHDto.getResult());
			this.setTotalCount(ammeterHDto.getTotalCount());
			
		}catch(Exception e) {
			e.printStackTrace();
			success = false;
			logger.error("下钻电能表异常查询操作错误  \n " + e);
		}
		this.logger.debug("下钻电能表异常查询结束：queryAmmeterException()");
		return SUCCESS;
	}
	
	
	public String queryAmmeterHWindowList() throws Exception {
		this.logger.debug("电能表异常窗口查询开始：queryAmmeterHWindowList");
		PSysUser user = getPSysUser();
		if (user == null) {
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		try{
			Page<AmmeterHWindowDto> ahwPage = aEventStatDBeanManager.queryAmmeterHWindowList(eventId, areaCode, eventNo, start, limit);
			this.setAmmeterHWindowList(ahwPage.getResult());
			this.setTotalCount(ahwPage.getTotalCount());
			
		}catch(Exception e) {
			e.printStackTrace();
			success = false;
			logger.error("电能表异常窗口查询操作错误  \n " + e);
		}
		this.logger.debug("电能表异常窗口查询结束：queryAmmeterHWindowList()");
		return SUCCESS;
	}
	
	/**
	 * 统计每日异常
	 * @return success
	 * @throws Exception
	 */
	public String queryAEventStatWindow() throws Exception {
		this.logger.debug("统计每日异常开始：queryAEventStatWindow");
		try{
			Page<AEventStatDWindowDto> awPage = aEventStatDBeanManager.queryAEventStatDWindow(orgNo, eventNo, startDate, endDate, start, limit);
			this.setaEventWindowList(awPage.getResult());
			List<AEventStatDWindowDto> windowList = new ArrayList<AEventStatDWindowDto>();
			for(AEventStatDWindowDto windowDto :aEventWindowList){
				windowDto.setEventNo(eventNo);
				windowDto.setOrgNo(orgNo);
				windowDto.setOrgName(orgName);
				windowList.add(windowDto);
			}
			this.setaEventWindowList(windowList);
		}catch(Exception e) {
			success = false;
			logger.error("统计每日异常开始操作错误  \n " + e);
		}
		this.logger.debug("统计每日异常开始：queryAEventStatWindow()");
		return SUCCESS;
		
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public AEventStatDBeanManager getaEventStatDBeanManager() {
		return aEventStatDBeanManager;
	}
	public void setaEventStatDBeanManager(
			AEventStatDBeanManager aEventStatDBeanManager) {
		this.aEventStatDBeanManager = aEventStatDBeanManager;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public List<AEventStatDBean> getaEventStatDBeanList() {
		return aEventStatDBeanList;
	}
	public void setaEventStatDBeanList(List<AEventStatDBean> aEventStatDBeanList) {
		this.aEventStatDBeanList = aEventStatDBeanList;
	}

	/**
	 *  the ammeterExceptionList
	 * @return the ammeterExceptionList
	 */
	public List<AmmeterHDto> getAmmeterExceptionList() {
		return ammeterExceptionList;
	}

	/**
	 *  the ammeterExceptionList to set
	 * @param ammeterExceptionList the ammeterExceptionList to set
	 */
	public void setAmmeterExceptionList(List<AmmeterHDto> ammeterExceptionList) {
		this.ammeterExceptionList = ammeterExceptionList;
	}

	/**
	 *  the orgNo
	 * @return the orgNo
	 */
	public String getOrgNo() {
		return orgNo;
	}

	/**
	 *  the orgNo to set
	 * @param orgNo the orgNo to set
	 */
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	/**
	 *  the eventNo
	 * @return the eventNo
	 */
	public String getEventNo() {
		return eventNo;
	}

	/**
	 *  the eventNo to set
	 * @param eventNo the eventNo to set
	 */
	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	/**
	 *  the ammeterHWindowList
	 * @return the ammeterHWindowList
	 */
	public List<AmmeterHWindowDto> getAmmeterHWindowList() {
		return ammeterHWindowList;
	}

	/**
	 *  the ammeterHWindowList to set
	 * @param ammeterHWindowList the ammeterHWindowList to set
	 */
	public void setAmmeterHWindowList(List<AmmeterHWindowDto> ammeterHWindowList) {
		this.ammeterHWindowList = ammeterHWindowList;
	}

	/**
	 *  the eventId
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 *  the eventId to set
	 * @param eventId the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 *  the areaCode
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 *  the areaCode to set
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 *  the aEventWindowList
	 * @return the aEventWindowList
	 */
	public List<AEventStatDWindowDto> getaEventWindowList() {
		return aEventWindowList;
	}

	/**
	 *  the aEventWindowList to set
	 * @param aEventWindowList the aEventWindowList to set
	 */
	public void setaEventWindowList(List<AEventStatDWindowDto> aEventWindowList) {
		this.aEventWindowList = aEventWindowList;
	}

	/**
	 *  the orgName
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 *  the orgName to set
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	
}
