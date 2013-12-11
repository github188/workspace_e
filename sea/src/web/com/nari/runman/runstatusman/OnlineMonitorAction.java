package com.nari.runman.runstatusman;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

public class OnlineMonitorAction extends BaseAction implements
		HttpSessionListener {

	// 日志
	private Logger logger = Logger.getLogger(this.getClass());

	private OnlineMonitorManager onlineMonitorManager;

	// 在线用户信息
	private List<OnOffLineMonitorDto> onofflineConsList;

	private boolean success = true;
	// 分页页码
	private long start = 0;
	// 每页显示数
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	// 页面总数
	private long totalCount;

	public String queryOnOffLineCons() throws Exception {
		logger.debug("在线监测开始");
		try {
			// 获得所有的PSysUser信息
			Page<OnOffLineMonitorDto> totalCons = this.onlineMonitorManager.findPSysUsers(start, limit);

			ServletContext application = super.getServletContext();
			Map onLineUsers = (Map) application.getAttribute("onLineUsers");

			for (OnOffLineMonitorDto onoffLineDto : totalCons.getResult()) {
				if (null == onLineUsers.get(onoffLineDto.getStaffNo())) {
					onoffLineDto.setIsOnline("离线");

				} else {
					onoffLineDto.setIsOnline("在线");

				}
			}
			this.setOnofflineConsList(totalCons.getResult());
			this.setTotalCount(totalCons.getTotalCount());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 在上下文中取得在线列表
		logger.debug("在线监测结束");
		return SUCCESS;
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

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void setOnlineMonitorManager(
			OnlineMonitorManager onlineMonitorManager) {
		this.onlineMonitorManager = onlineMonitorManager;
	}

	public List<OnOffLineMonitorDto> getOnofflineConsList() {
		return onofflineConsList;
	}

	public void setOnofflineConsList(List<OnOffLineMonitorDto> onofflineConsList) {
		this.onofflineConsList = onofflineConsList;
	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
