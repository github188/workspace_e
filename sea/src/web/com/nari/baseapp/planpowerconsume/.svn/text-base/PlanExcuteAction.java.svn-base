package com.nari.baseapp.planpowerconsume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;



import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author 黄轩
 * 
 * 
 * 
 * @类描述:有序用电任务执行
 * 
 */
public class PlanExcuteAction extends BaseAction {
	private List<WCtrlScheme> test;
	private boolean success = true;
	private long start;

	private int limit=Constans.DEFAULT_PAGE_SIZE;
	private long CtrlSchemeCount;
	// 人数的总数
	private long userCount;
	/**
	 * 记录某个方案的所有的用户
	 * 
	 * *
	 **/
	private List userList;

	/**
	 * 用来进来控制方案的输入参数
	 * **/
	private WCtrlScheme ws;
	/**
	 * 记录当前用户的所有的控制方案列表 *
	 **/
	private List ctrlSchemes;
	/***
	 * 控制方案列表的分页总数
	 * **/
	private Long totalCtrls;

	public List getUserList() {
		return userList;
	}

	public long getUserCount() {
		return userCount;
	}

	public void setUserCount(long userCount) {
		this.userCount = userCount;
	}

	public Long getTotalCtrls() {
		return totalCtrls;
	}

	public void setTotalCtrls(Long totalCtrls) {
		this.totalCtrls = totalCtrls;
	}

	// 错误信息
	private String errorMsg;

	public WCtrlScheme getWs() {
		return ws;
	}

	public List getCtrlSchemes() {
		return ctrlSchemes;
	}

	public void setWs(WCtrlScheme ws) {
		this.ws = ws;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	private IGroupSetManager iGroupSetManager;
	IPlanExcuteManager iPlanExcuteManager;
	// 控制方案公共处理类
	IWTmnlSchemePublicManager iWTmnlSchemePublicManager;

	public void setiWTmnlSchemePublicManager(
			IWTmnlSchemePublicManager iWTmnlSchemePublicManager) {
		this.iWTmnlSchemePublicManager = iWTmnlSchemePublicManager;
	}

	public void setiPlanExcuteManager(IPlanExcuteManager iPlanExcuteManager) {
		this.iPlanExcuteManager = iPlanExcuteManager;
	}

	/**
	 * 加载所有的控制方案
	 * 
	 * @return
	 */
	public String findCtrlSchemes() {
		Page page = new Page();
		page.setStartNo(start);
		page.setPageSize(limit);
		try {
			PSysUser user = (PSysUser) ActionContext.getContext().getSession()
					.get("pSysUser");
			page = iPlanExcuteManager.findSchemeNoNames(user.getStaffNo(),
					start, limit);
			ctrlSchemes = page.getResult();
			totalCtrls = page.getTotalCount();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			e.printStackTrace();
		}

		return "success";
	}


	public List<WCtrlScheme> getTest() {
		return test;
	}

	public void setTest(List<WCtrlScheme> test) {
		this.test = test;
	}

	/***
	 * 
	 * 查找某个控制方案之下的的所有的用户
	 * **/
	public String findAllSchemeUsers() {
		Page page = new Page();
		page.setStartNo(start);
		page.setPageSize(limit);
		try {
			PSysUser user = (PSysUser) ActionContext.getContext().getSession()
					.get("pSysUser");
			page = iWTmnlSchemePublicManager.findAllUsers(user.getStaffNo(), ws
					.getCtrlSchemeId(), start, limit);
			userList=page.getResult();
			userCount=page.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg = e.getMessage();
		}
		return SUCCESS;
	}

	public boolean isSuccess() throws Exception {
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

	public void setiGroupSetManager(IGroupSetManager iGroupSetManager) {
		this.iGroupSetManager = iGroupSetManager;
	}

}
