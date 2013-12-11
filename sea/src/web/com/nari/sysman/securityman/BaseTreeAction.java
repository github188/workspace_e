package com.nari.sysman.securityman;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.util.TreeNode;
import com.nari.util.exception.DBAccessException;

public class BaseTreeAction extends BaseAction{
	
	public List<TreeNode> treeNodeList;
	
	IAuthorizeManager iAuthorizeManager;
	
	public boolean success = true;
	
	/*
	 * @return 弹出单位树
	 */
	public String orgTreeWin() {
		String node = this.getRequest().getParameter("node");
		try {
			this.treeNodeList = this.iAuthorizeManager.findOrgByNode(node,super.getPSysUser());
		} catch (DBAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/*
	 * @return 弹出部門树
	 */
	public String deptTreeWin() {
		String node = this.getRequest().getParameter("node");
		try {
			this.treeNodeList = this.iAuthorizeManager.findDeptByNode(node);
		} catch (DBAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public List<TreeNode> getTreeNodeList() {
		return treeNodeList;
	}

	public void setTreeNodeList(List<TreeNode> treeNodeList) {
		this.treeNodeList = treeNodeList;
	}
	
	@JSON(serialize = false)
	public IAuthorizeManager getiAuthorizeManager() {
		return iAuthorizeManager;
	}

	public void setiAuthorizeManager(IAuthorizeManager iAuthorizeManager) {
		this.iAuthorizeManager = iAuthorizeManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
