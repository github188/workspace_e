package com.nari.baseapp.prepaidman;

import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;

/**
 * 创建人：龙孔操
 * 创建时间：2009-11-19  15:52:38	 
 * 类描述：	催费控制
 * 修改人：杨传文
 */
public class DunningControlAction extends BaseAction {
	//log初始化
	private static Logger logger = Logger.getLogger(DunningControlAction.class);
	private List<DunningControlBean> dunningControlList;
	private DunningControlManager dunningControlManager;
	
	private boolean success = true;
	private int second = 45;
	private String cacheAndTmnlStatus = "";
	private String nodeId;

	public String init() {

		try {
			System.out.println("Just Test Class");
		} catch (Exception e) {
			logger.error(e);
		}
		return "success";
	}
	
	/**
	 * 查询催费用户终端列表
	 * @return
	 * @throws Exception
	 */
	public String queryDunningControlUser() throws Exception{
		logger.debug("------------------------查询催费控制用户开始------------------------");
		PSysUser user = getPSysUser();
		dunningControlList = dunningControlManager.queryDunningControlUser(nodeId, user);
		logger.debug("------------------------查询催费控制用户结束------------------------");
		return SUCCESS;
	}

	public List<DunningControlBean> getDunningControlList() {
		return dunningControlList;
	}

	public void setDunningControlList(List<DunningControlBean> dunningControlList) {
		this.dunningControlList = dunningControlList;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setDunningControlManager(DunningControlManager dunningControlManager) {
		this.dunningControlManager = dunningControlManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public String getCacheAndTmnlStatus() {
		return cacheAndTmnlStatus;
	}

	public void setCacheAndTmnlStatus(String cacheAndTmnlStatus) {
		this.cacheAndTmnlStatus = cacheAndTmnlStatus;
	}
}
