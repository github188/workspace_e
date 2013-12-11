package com.nari.baseapp.datagathorman;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.support.Page;
import com.nari.util.TreeNode;

public class DataFetchLoneAction extends BaseAction {
	public boolean success = true;
	private Logger logger = Logger.getLogger(this.getClass());
	private List<Map> resultList;
	private Long resultCount;
	public String message;
	private long start;
	private int limit;
	private Map beanMap;
	private String id;
	private String tmnlAssetNo;
	private DataFetchLoneManager dataFetchLoneManager;
	public Page<TreeNode> pageInfo;
	private List<TreeNode> treeNodeList;
	public String node;

	// 生成计量点想关联的树
	public String pointRefTree() {
		try {
			pageInfo = dataFetchLoneManager.findPointTree(node, start, limit);
		} catch (Exception e) {
			logger.error(e.getMessage());
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	// 通过一个id来找到相关系的数据
	public String findDataById() {
		try {
			beanMap = dataFetchLoneManager.findDataById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	/*** 通过字符串的类型来处理规约树 **/
	public String dealClearTree() {
		try {
			treeNodeList = dataFetchLoneManager.findClearTree(node);
		} catch (Exception e) {
			logger.error(e.getMessage());
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	/**** 通过不同的节点id字符串来处理按不同类型的用户树 ***/
	public String createTypeTree() {
		try {
			treeNodeList=dataFetchLoneManager.findTypeTree(node);
		} catch (Exception e) {
			logger.error(e.getMessage());
			this.message = e.getMessage();
		}
		return SUCCESS;

	}
	/**找到用户的总数***/
	public String findUserCnt() {
		try {
			resultCount=dataFetchLoneManager.findCoutUser(tmnlAssetNo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	public boolean isSuccess() {
		return success;
	}

	public List<Map> getResultList() {
		return resultList;
	}

	public Long getResultCount() {
		return resultCount;
	}

	public Page<TreeNode> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(Page<TreeNode> pageInfo) {
		this.pageInfo = pageInfo;
	}

	public void setDataFetchLoneManager(
			DataFetchLoneManager dataFetchLoneManager) {
		this.dataFetchLoneManager = dataFetchLoneManager;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Map getBeanMap() {
		return beanMap;
	}

	public void setBeanMap(Map beanMap) {
		this.beanMap = beanMap;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TreeNode> getTreeNodeList() {
		return treeNodeList;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	

}
