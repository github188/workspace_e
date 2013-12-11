package com.nari.mydesk;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;
import com.opensymphony.xwork2.ActionContext;

public class MyDeskAction extends BaseAction {
	private final static Logger log = Logger.getLogger(MyDeskAction.class);

	private IMyDeskManager iMyDeskManager ;
	private boolean success = true;
	
	private List<ElecOrderDTO> ls = null;//用电大户排名
	private List<CommFlowDTO> commFlowList;//通信流量
	private List<Model> modelList;//监视主页 配置的portlet
	private String date ;

	/**
	 * @DESC 监视主页-用电大户排名
	 * @return
	 */
	public String queryElecOrder(){
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		try {
			ls = iMyDeskManager.getElecOrder(user.getStaffNo(), date);
		} catch (Exception e) {
			log.debug("获取用电大户排名失败!");
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @desc 监视主页-通信流量
	 * @return
	 */
	public String queryCommFlow(){
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		try {
			commFlowList = iMyDeskManager.getCommFlow(user.getStaffNo());
		} catch (Exception e) {
			log.debug("通信流量查询失败!");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * desc 获取portal初始化portlet
	 * @return
	 */
	public String queryModel(){
//		modelList = new ArrayList<Model>();
//		Model model1 = new Model();
//		model1.setName("昨日电量分布");
//		model1.setId("1");
//		model1.setPath("mydesk/yestPowerDist.jsp");
//		model1.setImage("mydesk/images/t1.gif");
//		modelList.add(model1);
//		
//		Model model2 = new Model();
//		model2.setName("当日负荷监视(万kW)");
//		model2.setId("2");
//		model2.setPath("mydesk/dayElecLoadMonitor.jsp");
//		model2.setImage("mydesk/images/t1.gif");
//		modelList.add(model2);
//		
//		Model model3 = new Model();
//		model3.setName("昨日采集成功率");
//		model3.setId("3");
//		model3.setPath("mydesk/yestCollectionRate.jsp");
//		model3.setImage("mydesk/images/t1.gif");
//		modelList.add(model3);
//		
//		Model model4 = new Model();
//		model4.setName("上月地区用电大户排名(TOP10)");
//		model4.setId("4");
//		model4.setPath("mydesk/areaElecLargeRank.jsp");
//		model4.setImage("mydesk/images/t1.gif");
//		modelList.add(model4);
//		
//		Model model5 = new Model();
//		model5.setName("通信流量");
//		model5.setId("5");
//		model5.setPath("mydesk/commTraffic.jsp");
//		model5.setImage("mydesk/images/t1.gif");
//		modelList.add(model5);
//		
//		Model model6 = new Model();
//		model6.setName("通信信道监测");
//		model6.setId("6");
//		model6.setPath("mydesk/channelMonitor.jsp");
//		model6.setImage("mydesk/images/t1.gif");
//		modelList.add(model6);
		try {
			modelList = iMyDeskManager.getAllModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public void setiMyDeskManager(IMyDeskManager iMyDeskManager) {
		this.iMyDeskManager = iMyDeskManager;
	}

	public List<ElecOrderDTO> getLs() {
		return ls;
	}

	public void setLs(List<ElecOrderDTO> ls) {
		this.ls = ls;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<CommFlowDTO> getCommFlowList() {
		return commFlowList;
	}

	public void setCommFlowList(List<CommFlowDTO> commFlowList) {
		this.commFlowList = commFlowList;
	}

	public List<Model> getModelList() {
		return modelList;
	}

	public void setModelList(List<Model> modelList) {
		this.modelList = modelList;
	}
	
}
