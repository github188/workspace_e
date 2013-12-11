package com.nari.runman.archivesman;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.codehaus.xfire.client.Client;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.ws.ClientAuthenticationHandler;
import com.opensymphony.xwork2.ActionContext;

public class archivesmanAction extends BaseAction {
	private boolean success = true;
	// 返回同步错误信息的map
	private Map<String, String> errorMap;
	private List<String> consNos;
	private List<String> consIds;
	private List<String> cpNos;
	private List<String> tmnlIds;
	private List<String> tgIds;
	private IArchivesmanManager archivesmanManager;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setArchivesmanManager(IArchivesmanManager archivesmanManager) {
		this.archivesmanManager = archivesmanManager;
	}

	private List<Map> resultList;
	private int resultCount;
	// 是功控的类型

	private String node;

	public void setNode(String node) {
		this.node = node;
	}

	public List<Map> getResultList() {
		return resultList;
	}

	public int getResultCount() {
		return resultCount;
	}

	public boolean isSuccess() {
		return success;
	}

	/*** 点击左边树加载数据 ***/
	public String findClick() {
		try {

			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			Page page = archivesmanManager.findClick(user, node);
			resultList = page.getResult();
			if (resultList.size() > 500) {
				resultList.remove(resultList.size() - 1);
				throw new RuntimeException("此节点");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**** 档案同步执行 ***/

	@SuppressWarnings("unchecked")
	public String doArchive() {
		try {
			errorMap = archivesmanManager.addDoArchives(consNos, consIds,
					cpNos, tmnlIds, tgIds);
		} catch (Exception e) {
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	public void setConsNos(List<String> consNos) {
		this.consNos = consNos;
	}

	public void setConsIds(List<String> consIds) {
		this.consIds = consIds;
	}

	public void setCpNos(List<String> cpNos) {
		this.cpNos = cpNos;
	}

	public void setTmnlIds(List<String> tmnlIds) {
		this.tmnlIds = tmnlIds;
	}

	public void setTgIds(List<String> tgIds) {
		this.tgIds = tgIds;
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}
}
