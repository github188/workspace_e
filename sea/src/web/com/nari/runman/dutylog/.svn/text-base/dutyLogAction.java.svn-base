package com.nari.runman.dutylog;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.json.JSONResult;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.opensymphony.xwork2.ActionContext;

public class dutyLogAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	private TmnlOpLogQuery query;
	private LUserLogEntry userLog;
	private List<Map> resultList;
	// 查询的类型
	private String queryType;

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	private Date workTime;

	public Date getWorkTime() {
		return workTime;
	}

	private boolean success = true;
	// 操作人员选择器
	private StaffFindBean staffFindBean;

	public void setStaffFindBean(StaffFindBean staffFindBean) {

		this.staffFindBean = staffFindBean;
	}

	public StaffFindBean getStaffFindBean() {
		return staffFindBean;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	private long totalCount;

	public List<Map> getResultList() {
		return resultList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	private int start;
	private int limit;

	public void setStart(int start) {
		this.start = start;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	private String message;

	public void setQuery(TmnlOpLogQuery query) {
		this.query = query;
	}

	public void setUserLogEntry(LUserLogEntry userLogEntry) {
		this.userLog = userLogEntry;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setUserLogManager(IUserLogManager userLogManager) {
		this.iUserLogManager = userLogManager;
	}

	private IUserLogManager iUserLogManager;

	/*** 保存一个值班日志 ***/
	public String saveLog() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			iUserLogManager.saveLog(userLog, user);
		} catch (Exception e) {
			e.printStackTrace();
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	public LUserLogEntry getUserLog() {
		return userLog;
	}

	public void setUserLog(LUserLogEntry userLog) {
		this.userLog = userLog;
	}

	public String getMessage() {
		return message;
	}

	/***** 通过条件来查找终端操作日志或者用户操作日志来查找 ****/
	public String findTmnlOpLog() {
		try {
			Map session = ActionContext.getContext().getSession();
			Page<Map> page = new Page<Map>(start, limit);
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			if (queryType.equals("0")) {
				page = iUserLogManager.findTmnlOpLog(query, user, start, limit);

				resultList = page.getResult();
				totalCount = page.getTotalCount();
			} else if (queryType.equals("1")) {
				page = iUserLogManager.findUserOpLog(user, query, start, limit);
				resultList = page.getResult();
				totalCount = page.getTotalCount();
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	public TmnlOpLogQuery getQuery() {
		return query;
	}

	public void setiUserLogManager(IUserLogManager iUserLogManager) {
		this.iUserLogManager = iUserLogManager;
	}

	/******* 通过条件来查找操作人员 *******/
	public String findStaff() {
		try {
			Page<Map> page = new Page<Map>(start, limit);
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			page = iUserLogManager.findStaff(user, start, limit, staffFindBean);
			resultList = page.getResult();
			totalCount = page.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	/******* 当前用户的上班时间 *******/
	public String findWorkTime() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			workTime = iUserLogManager.findWorkTime(user);
		} catch (Exception e) {
			e.printStackTrace();
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	/***** 得到截止到现在，今天的操作类型的汇总数据 *****/
	public String findOpTypeData() {
		try {
			Page page = new Page(start, limit);
			JSONResult js = new JSONResult();
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			page = iUserLogManager.findOpTypeToday(user, start, limit);
			resultList = page.getResult();
			totalCount = page.getTotalCount();
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查找终端的操作类型
	 * 
	 * @return
	 */
	public String findTmnlOptype() {
		try {
			resultList=iUserLogManager.findTmnlOptype();
		} catch (Exception e) {
		}
		return SUCCESS;
	}

	/**
	 * 查询用户的操作类型
	 * 
	 * @return
	 */
	public String findUserOptype() {
		try {
			resultList=iUserLogManager.findUserOptype();
		} catch (Exception e) {
		}
		return SUCCESS;
	}

	/*****
	 * 查找天气的列表 *
	 ***/
	public String findWeather() {
		try {
			resultList = iUserLogManager.findWeatherDict();
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*** 查找风向的列表 *****/
	public String findWindDirection() {
		try {

			resultList = iUserLogManager.findWindForceDict();
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public static void main(String[] args) {
		java.sql.Date d = new java.sql.Date(54452323);
		Date dd = new Date();
		System.out.println(dd.toString());
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		System.out.println(c.getTime());
		System.out.println(d);
	}
}
