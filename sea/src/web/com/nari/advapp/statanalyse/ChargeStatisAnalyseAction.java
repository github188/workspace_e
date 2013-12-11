package com.nari.advapp.statanalyse;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
/**
 * 
 * 负荷统计分析action
 * 
 * @author ChunMingLi
 * @since  2010-8-07
 *
 */
public class ChargeStatisAnalyseAction extends BaseAction {
	//设置注入
	private ChargeStatisAnalyseManager statisAnalyseManager;

	/**
	 *  the statisAnalyseManager to set
	 * @param statisAnalyseManager the statisAnalyseManager to set
	 */
	public void setStatisAnalyseManager(
			ChargeStatisAnalyseManager statisAnalyseManager) {
		this.statisAnalyseManager = statisAnalyseManager;
	}

	//记录日志
	private  Logger logger = Logger.getLogger(ChargeStatisAnalyseAction.class);
	
	//分页参数
	private long totalCount;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	//请求标识
	private boolean success = true;
	
	//查询开始时间
	private Date startDate;
	
	//查询结束时间
	private Date endDate;
	//时间标识
	private int timeFlag; // 1标识日曲线,2标识月曲线,3标识年曲线
	//比对标识
	private int contrastFlag; //0环比标识, 1同比标识
	private int checkboxFlag;
	// 节点类型
	private String nodeType;
	// 节点值
	private String nodeValue;
	
	private String orgType;
	
	//统计集合
	private List<ChargeStatisAnalyseDTO> chargeStatList;
	//集合map
	private Map<String, List<StatAnalyseCurveDto>> chargeMap;
	
	/**
	 * 根据时间查询负荷统计分析
	 * @return
	 * @throws Exception
	 */
	public String queryChargeStatAnalyse()throws Exception{
		//查询时间不可为空
		if(startDate == null || endDate == null){
			return null;
		}
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute("pSysUser");
		chargeMap = statisAnalyseManager.queryChargeStatisAnalyseCurve(orgType, nodeType, nodeValue, userInfo, timeFlag, contrastFlag, startDate, endDate);
		return SUCCESS;
		
	}
	
	/**
	 * 查询负荷统计分析明细
	 * @return
	 * @throws Exception
	 */
	public String queryChargeStatAnalyseDemandDate() throws Exception{
		//查询时间不可为空
		if(startDate == null || endDate == null){
			return null;
		}
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute("pSysUser");
		Page<ChargeStatisAnalyseDTO> page = statisAnalyseManager.queryChargeStatisAnalyseList(timeFlag, orgType, nodeType, nodeValue,
																	userInfo, start, limit, startDate, endDate);
		if(page != null){
			setChargeStatList(page.getResult());
			setTotalCount(page.getTotalCount());
		}
		
		return SUCCESS;
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
	 *  the startDate
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 *  the startDate to set
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 *  the endDate
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 *  the endDate to set
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 *  the timeFlag
	 * @return the timeFlag
	 */
	public int getTimeFlag() {
		return timeFlag;
	}

	/**
	 *  the timeFlag to set
	 * @param timeFlag the timeFlag to set
	 */
	public void setTimeFlag(int timeFlag) {
		this.timeFlag = timeFlag;
	}

	/**
	 *  the contrastFlag
	 * @return the contrastFlag
	 */
	public int getContrastFlag() {
		return contrastFlag;
	}

	/**
	 *  the contrastFlag to set
	 * @param contrastFlag the contrastFlag to set
	 */
	public void setContrastFlag(int contrastFlag) {
		this.contrastFlag = contrastFlag;
	}

	/**
	 *  the chargeStatList
	 * @return the chargeStatList
	 */
	public List<ChargeStatisAnalyseDTO> getChargeStatList() {
		return chargeStatList;
	}

	/**
	 *  the chargeStatList to set
	 * @param chargeStatList the chargeStatList to set
	 */
	public void setChargeStatList(List<ChargeStatisAnalyseDTO> chargeStatList) {
		this.chargeStatList = chargeStatList;
	}

	/**
	 *  the chargeMap
	 * @return the chargeMap
	 */
	public Map<String, List<StatAnalyseCurveDto>> getChargeMap() {
		return chargeMap;
	}

	/**
	 *  the chargeMap to set
	 * @param chargeMap the chargeMap to set
	 */
	public void setChargeMap(Map<String, List<StatAnalyseCurveDto>> chargeMap) {
		this.chargeMap = chargeMap;
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

	/**
	 *  the checkboxFlag
	 * @return the checkboxFlag
	 */
	public int getCheckboxFlag() {
		return checkboxFlag;
	}

	/**
	 *  the checkboxFlag to set
	 * @param checkboxFlag the checkboxFlag to set
	 */
	public void setCheckboxFlag(int checkboxFlag) {
		this.checkboxFlag = checkboxFlag;
	}
	
}
