package com.nari.advapp.vipconsman;

import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.advapp.VipConsPowerCurveBean;
import com.nari.advapp.VipConsPowerOverBean;
import com.nari.advapp.VipConsPowerVstatBean;
import com.nari.support.Page;

public class VipConsPowerOverAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	private boolean success = true;
	private long start = 0;
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;
	private Short queryType;
	private String day;
	private String month;
	private String year;

	private VipConsMonitorManager vipConsMonitorManager;

	// 返回前台列表
	private List<VipConsPowerOverBean> vipConsPowerOverList;
	private List<VipConsPowerCurveBean> vipConsPowerCurveList;
	private List<VipConsPowerVstatBean> vipConsPowerVstatList;
	
	public String statVipConsPowerOver() throws Exception {
		Page<VipConsPowerOverBean> page = vipConsMonitorManager
				.queryVipCOnsPowerOverByDay(queryType, day, month, year, start, limit);
		vipConsPowerOverList = page.getResult();
		totalCount = page.getTotalCount();
		return SUCCESS;
	}
	
	public String statVipConsPowerCurve() throws Exception {
		Page<VipConsPowerCurveBean> page = vipConsMonitorManager
				.queryVipCOnsPowerCurveByDay(queryType, day, month, year, start, limit);
		vipConsPowerCurveList = page.getResult();
		totalCount = page.getTotalCount();
		return SUCCESS;
	}
	
	public String statVipConsPowerVstat() throws Exception {
		Page<VipConsPowerVstatBean> page = vipConsMonitorManager
				.queryVipCOnsPowerVstatByDay(queryType, day, month, year, start, limit);
		vipConsPowerVstatList = page.getResult();
		totalCount = page.getTotalCount();
		return SUCCESS;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
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

	public List<VipConsPowerOverBean> getVipConsPowerOverList() {
		return vipConsPowerOverList;
	}

	public void setVipConsPowerOverList(
			List<VipConsPowerOverBean> vipConsPowerOverList) {
		this.vipConsPowerOverList = vipConsPowerOverList;
	}

	public void setVipConsMonitorManager(
			VipConsMonitorManager vipConsMonitorManager) {
		this.vipConsMonitorManager = vipConsMonitorManager;
	}

	public Short getQueryType() {
		return queryType;
	}

	public void setQueryType(Short queryType) {
		this.queryType = queryType;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<VipConsPowerCurveBean> getVipConsPowerCurveList() {
		return vipConsPowerCurveList;
	}

	public void setVipConsPowerCurveList(
			List<VipConsPowerCurveBean> vipConsPowerCurveList) {
		this.vipConsPowerCurveList = vipConsPowerCurveList;
	}

	public List<VipConsPowerVstatBean> getVipConsPowerVstatList() {
		return vipConsPowerVstatList;
	}

	public void setVipConsPowerVstatList(
			List<VipConsPowerVstatBean> vipConsPowerVstatList) {
		this.vipConsPowerVstatList = vipConsPowerVstatList;
	}
}
