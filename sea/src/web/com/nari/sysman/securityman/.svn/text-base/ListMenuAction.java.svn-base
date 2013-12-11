package com.nari.sysman.securityman;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.privilige.PMenu;
import com.nari.privilige.PSysUser;
import com.nari.util.TreeNodeChecked;

/**
 * 类 ListMenuAction
 * 
 * @author zhangzhw 处理主页面二三级菜单的方法
 */
public class ListMenuAction extends BaseAction {
	public boolean success = true;
	public Errors errors;

	// 客户端参数
	public String upid;

	// 返回值
	public List<PMenu> list;
	public List<TreeNodeChecked> treeNodeList;

	// spring
	public IPMenuManager iPMenuManager;

	public String execute() throws Exception {

		// List<PMenu> temp = iPMenuManager.findByUpid(getUpid());
		// list = new ArrayList<PMenu>();
		// for (PMenu p : temp) {
		// list.add(new PMenu(p));
		// }
		PSysUser pSysUser = super.getPSysUser();

		list = iPMenuManager.findByUpid(getUpid(), pSysUser);

		return "success";
	}

	/*
	 * @return 菜单树数据
	 */
	public String menuTree() throws Exception {
		String node = this.getRequest().getParameter("node");
		String roleId = this.getRequest().getParameter("roleId");
		PSysUser pSysUser = super.getPSysUser();

		this.treeNodeList = this.iPMenuManager.findMenu(node, roleId, pSysUser);
		return SUCCESS;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public String getUpid() {
		return upid;
	}

	public void setUpid(String upid) {
		this.upid = upid;
	}

	public List<PMenu> getList() {
		return list;
	}

	public void setList(List<PMenu> list) {
		this.list = list;
	}

	public List<TreeNodeChecked> getTreeNodeList() {
		return treeNodeList;
	}

	public void setTreeNodeList(List<TreeNodeChecked> treeNodeList) {
		this.treeNodeList = treeNodeList;
	}

	@JSON(serialize = false)
	public IPMenuManager getiPMenuManager() {
		return iPMenuManager;
	}

	public void setiPMenuManager(IPMenuManager iPMenuManager) {
		this.iPMenuManager = iPMenuManager;
	}

}
