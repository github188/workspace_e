package com.nari.runman.runstatusman;

import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.runman.runstatusman.ChannelMonitorDto;
import com.nari.runman.runstatusman.ChannelMonitorOrgNoDto;
import com.nari.runman.runstatusman.ChannelMonitorTerminalDto;
import com.nari.support.Page;

/**
 * 
 * 通信信道监测 action
 * 
 * @author ChunMingLi 2010-5-1
 * 
 */
public class ChannelMonitorAction extends BaseAction {
	// 日志
	private Logger logger = Logger.getLogger(this.getClass());
	// 注入service
	private ChannelMonitorManager channelMonitorManager;
	// 终端监测集合
	private List<ChannelMonitorTerminalDto> channelMonitorTerminaList;
	// 厂商监测集合
	private List<ChannelMonitorOrgNoDto> channelMonitorOrgNoList;
	
	// 厂商监测集合
	private List<ChannelMonitorConsTypeCollMode> channelMonitorConsTypeCollModeList;
	// 监测明细集合
	private List<ChannelMonitorDto> channelMonitorList;
	// 执行标识
	private boolean success = true;
	//分页页码
	private long start = 0;
	//每页显示数
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	//页面总数
	private long totalCount;
	
	// 在线类型 分离线形式和在线形式
	private String onlineType;
	
	//统计类型 分供电单位统计和终端厂商
	private String statisticsType;
	
	//标记统计类型
	private String statisticFlag;
	
	/**
	 * 
	 * 通信信道監測頁面   获取終端廠商数据方法
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String queryChannelMonitorTerminal()throws Exception {
		logger.debug("Method queryChannelMonitorTerminal() start");
		PSysUser pSysUser=(PSysUser) getSession().getAttribute("pSysUser");
		Page<ChannelMonitorTerminalDto> page = channelMonitorManager.findChannelMonitorFacturerBean(pSysUser, start, limit);
		this.setChannelMonitorTerminaList(page.getResult()) ;
		this.setTotalCount(page.getTotalCount());
		logger.debug("Method queryChannelMonitorTerminal() end");
		return SUCCESS;
	}
	
	/**
	 * 
	 * 通信信道監測頁面   获取供電單位数据方法
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String queryChannelMonitorOrgNo()throws Exception {
		logger.debug("Method queryChannelMonitorOrgNo() start");
		PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
		Page<ChannelMonitorOrgNoDto> page = channelMonitorManager.findChannelMonitorOrgNoBean(pSysUser ,start, limit);
		this.setChannelMonitorOrgNoList(page.getResult());
		this.setTotalCount(page.getTotalCount());
		logger.debug("Method queryChannelMonitorOrgNo() end");
		return SUCCESS;
	}
	/**
	 * 
	 * 通信信道監測頁面   统计用户类别方法
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String queryChannelMonitorConsType()throws Exception {
		logger.debug("Method queryChannelMonitorConsType() start");
		PSysUser pSysUser=(PSysUser) getSession().getAttribute("pSysUser");
		Page<ChannelMonitorConsTypeCollMode> page = channelMonitorManager.findChannelMonitorConsType(pSysUser, start, limit);
		this.setChannelMonitorConsTypeCollModeList(page.getResult()) ;
		this.setTotalCount(page.getTotalCount());
		logger.debug("Method queryChannelMonitorConsType() end");
		return SUCCESS;
	}/**
	 * 
	 * 通信信道監測頁面   统计采集类别方法
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String queryChannelMonitorCollMode()throws Exception {
		logger.debug("Method queryChannelMonitorCollMode() start");
		PSysUser pSysUser=(PSysUser) getSession().getAttribute("pSysUser");
		Page<ChannelMonitorConsTypeCollMode> page = channelMonitorManager.findChannelMonitorCollectMode(pSysUser, start, limit);
		this.setChannelMonitorConsTypeCollModeList(page.getResult()) ;
		this.setTotalCount(page.getTotalCount());
		logger.debug("Method queryChannelMonitorCollMode() end");
		return SUCCESS;
	}
	/**
	 * 
	 * 通信信道監測頁面   获取供電單位数据方法
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String queryChannelMonitor()throws Exception {
		try {
			PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
			logger.debug("Method queryChannelMonitor() start;\n onlineType :" + onlineType + "  statisticsType : " + statisticsType + "  statisticFlag : " + statisticFlag + "  pSysUser  :"  + pSysUser.getName());
			Page<ChannelMonitorDto> cmPage = channelMonitorManager.findChannelMonitorBean(onlineType, statisticsType, statisticFlag, start, limit, pSysUser);
			channelMonitorList = cmPage.getResult();
			totalCount = cmPage.getTotalCount();
		} catch (Exception e) {
			logger.error(e);
		}
		logger.debug("Method queryChannelMonitor end");
		return SUCCESS;
	}


	public void setChannelMonitorManager(
			ChannelMonitorManager channelMonitorManager) {
		this.channelMonitorManager = channelMonitorManager;
	}

	public List<ChannelMonitorTerminalDto> getChannelMonitorTerminaList() {
		return channelMonitorTerminaList;
	}

	public void setChannelMonitorTerminaList(
			List<ChannelMonitorTerminalDto> channelMonitorTerminaList) {
		this.channelMonitorTerminaList = channelMonitorTerminaList;
	}

	public List<ChannelMonitorOrgNoDto> getChannelMonitorOrgNoList() {
		return channelMonitorOrgNoList;
	}

	public void setChannelMonitorOrgNoList(
			List<ChannelMonitorOrgNoDto> channelMonitorOrgNoList) {
		this.channelMonitorOrgNoList = channelMonitorOrgNoList;
	}

	public List<ChannelMonitorDto> getChannelMonitorList() {
		return channelMonitorList;
	}

	public void setChannelMonitorList(List<ChannelMonitorDto> channelMonitorList) {
		this.channelMonitorList = channelMonitorList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getStatisticFlag() {
		return statisticFlag;
	}

	public void setStatisticFlag(String statisticFlag) {
		this.statisticFlag = statisticFlag;
	}

	public String getOnlineType() {
		return onlineType;
	}

	public void setOnlineType(String onlineType) {
		this.onlineType = onlineType;
	}

	public String getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType;
	}

	/**
	 *  the channelMonitorConsTypeCollModeList
	 * @return the channelMonitorConsTypeCollModeList
	 */
	public List<ChannelMonitorConsTypeCollMode> getChannelMonitorConsTypeCollModeList() {
		return channelMonitorConsTypeCollModeList;
	}

	/**
	 *  the channelMonitorConsTypeCollModeList to set
	 * @param channelMonitorConsTypeCollModeList the channelMonitorConsTypeCollModeList to set
	 */
	public void setChannelMonitorConsTypeCollModeList(
			List<ChannelMonitorConsTypeCollMode> channelMonitorConsTypeCollModeList) {
		this.channelMonitorConsTypeCollModeList = channelMonitorConsTypeCollModeList;
	}

}
