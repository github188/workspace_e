package com.nari.sysman.securityman;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.privilige.PMenu;
import com.nari.util.TreeNode;

public class GetTreeAction extends BaseAction {

	public List<TreeNode> tl;
	public Errors errors;
	public boolean success;

	public String nodeid;

	public IPMenuManager iPMenuManager;

	public String userTree() throws Exception {
		this.tl = new ArrayList<TreeNode>();

		List<PMenu> temp = this.iPMenuManager.findByUpid(this.getNodeid(),
				super.getPSysUser());
		for (int i = 0; i < temp.size(); i++) {
			TreeNode tn = new TreeNode();
			tn.setId(temp.get(i).getMenuNo());
			tn.setName(temp.get(i).getRemark());
			if (temp.get(i).getMenuFolderFlag().equals("1"))
				tn.setLeaf(false);
			else
				tn.setLeaf(true);
			this.tl.add(tn);
		}

		this.setSuccess(true);
		return "success";
	}

	public List<TreeNode> getTl() {
		return this.tl;
	}

	public void setTl(List<TreeNode> tl) {
		this.tl = tl;
	}

	public String getNodeid() {
		return this.nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public Errors getErrors() {
		return this.errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@JSON(serialize = false)
	public IPMenuManager getiPMenuManager() {
		return this.iPMenuManager;
	}

	public void setiPMenuManager(IPMenuManager iPMenuManager) {
		this.iPMenuManager = iPMenuManager;
	}

}
