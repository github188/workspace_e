package com.nari.advapp.statanalyse;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
/**
 * 
 * 电量统计分析action
 * 
 * @author ChunMingLi
 * @since  2010-8-09
 *
 */
public class ElecStatisAnalyseAction extends BaseAction {
	//设置注入
	private ElecStatisAnalyseManager elecStatisAnalyseManager;

	/**
	 *  the elecStatisAnalyseManager to set
	 * @param elecStatisAnalyseManager the elecStatisAnalyseManager to set
	 */
	public void setElecStatisAnalyseManager(
			ElecStatisAnalyseManager elecStatisAnalyseManager) {
		this.elecStatisAnalyseManager = elecStatisAnalyseManager;
	}

	//记录日志
	private  Logger logger = Logger.getLogger(ElecStatisAnalyseAction.class);
	
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
	// 节点类型
	private String nodeType;
	// 节点值
	private String nodeValue;
	private String orgType;
	private int elecType;
	//集合
	private List<ChargeStatisAnalyseDTO> elecStatList;
	
	//集合map
	private Map<String, List<StatAnalyseCurveDto>> elecMap;
	
	/**
	 * 根据时间查询负荷统计分析
	 * @return
	 * @throws Exception
	 */
	public String queryElecStatAnalyse()throws Exception{

		//查询时间不可为空
		if(startDate == null || endDate == null){
			return null;
		}
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute("pSysUser");
		elecMap = elecStatisAnalyseManager.queryElecStatisAnalyseCurve(elecType,orgType, nodeType, nodeValue, userInfo, timeFlag, contrastFlag, startDate, endDate);
		return SUCCESS;
		
	}
	
	/**
	 * 查询比对负荷统计分析
	 * @return
	 * @throws Exception
	 */
	public String queryChargeStatisAnalyseList() throws Exception {
		// 查询时间不可为空
		if (startDate == null || endDate == null) {
			return null;
		}
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		Page<ChargeStatisAnalyseDTO> page = elecStatisAnalyseManager
				.queryElecStatisAnalyseList(timeFlag, orgType, nodeType,
						nodeValue, userInfo, start, limit, startDate, endDate);
		if (page != null) {
			setElecStatList(page.getResult());
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
	 *  the elecStatList
	 * @return the elecStatList
	 */
	public List<ChargeStatisAnalyseDTO> getElecStatList() {
		return elecStatList;
	}

	/**
	 *  the elecStatList to set
	 * @param elecStatList the elecStatList to set
	 */
	public void setElecStatList(List<ChargeStatisAnalyseDTO> elecStatList) {
		this.elecStatList = elecStatList;
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
	 *  the elecMap
	 * @return the elecMap
	 */
	public Map<String, List<StatAnalyseCurveDto>> getElecMap() {
		return elecMap;
	}

	/**
	 *  the elecMap to set
	 * @param elecMap the elecMap to set
	 */
	public void setElecMap(Map<String, List<StatAnalyseCurveDto>> elecMap) {
		this.elecMap = elecMap;
	}

	/**
	 *  the elecType
	 * @return the elecType
	 */
	public int getElecType() {
		return elecType;
	}

	/**
	 *  the elecType to set
	 * @param elecType the elecType to set
	 */
	public void setElecType(int elecType) {
		this.elecType = elecType;
	}
	
}
