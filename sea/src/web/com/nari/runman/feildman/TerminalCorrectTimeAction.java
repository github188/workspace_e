package com.nari.runman.feildman;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.coherence.TaskDeal;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.TmnlExecStatusBean;
import com.nari.runman.dutylog.TmnlTime;

/**
 * 终端对时功能
 * @author 姜海辉
 *
 */
public class TerminalCorrectTimeAction extends BaseAction{
	//返回值
	public boolean success = true;
	
	private List<String> correctTimeTmnlList;  //终端列表
	private String msg;
	private List<TmnlExecStatusBean> tmnlExecStatusList;
	private List<TmnlTime> fetchResultList;
	private Integer taskSecond=30;               //任务执行时间
	
	private  ITerminalCorrectTimeManager iTerminalCorrectTimeManager;
	public void setiTerminalCorrectTimeManager(
			ITerminalCorrectTimeManager iTerminalCorrectTimeManager) {
		this.iTerminalCorrectTimeManager = iTerminalCorrectTimeManager;
	}

	public String correctTime() throws Exception{
		List<CtrlParamBean> tmnlList= parseTmnlList();
		if(null==tmnlList||0==tmnlList.size()){
			this.success=false;
			return SUCCESS;
		}	
		try{
			if(!TaskDeal.isCacheRunning()){
				this.msg="前置集群通信中断";
				return SUCCESS;
			}
			if(!TaskDeal.isFrontAlive()){
				this.msg="前置集群服务中断";
				return SUCCESS;
			}
			this.tmnlExecStatusList=this.iTerminalCorrectTimeManager.correctTmnlTime(tmnlList,this.taskSecond);
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 时钟召测
	 * @return
	 * @throws Exception
	 */
	public String fetchTime() throws Exception{
		List<String> tmnlList= parseFetchTmnlList();
		if(null==tmnlList||0==tmnlList.size()){
			this.success=false;
			return SUCCESS;
		}	
		try{
			if(!TaskDeal.isCacheRunning()){
				this.msg="前置集群通信中断";
				return SUCCESS;
			}
			if(!TaskDeal.isFrontAlive()){
				this.msg="前置集群服务中断";
				return SUCCESS;
			}
			this.fetchResultList=this.iTerminalCorrectTimeManager.fetchTmnlTime(tmnlList,this.taskSecond);
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 提取页面选择的终端
	 */
	public List<CtrlParamBean> parseTmnlList() {
		if(null==this.correctTimeTmnlList||0==this.correctTimeTmnlList.size())
			return null;
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < this.correctTimeTmnlList.size(); i++) {
			String[] tmnl = this.correctTimeTmnlList.get(i).split("`");
			if("".equals(tmnl[0])||"".equals(tmnl[1])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setTmnlAssetNo(tmnl[0]);
			bean.setProtocolCode(tmnl[1]);
			tmnlList.add(bean);
		}
		return tmnlList;
	}
	
	/**
	 * 提取页面选择的终端（用于召测）
	 */
	public List<String> parseFetchTmnlList() {
		if(null==this.correctTimeTmnlList||0==this.correctTimeTmnlList.size())
			return null;
		List<String> tmnlList=new ArrayList<String>();
		for (int i = 0; i < this.correctTimeTmnlList.size(); i++) {
			String tmnl = this.correctTimeTmnlList.get(i);
			if("".equals(tmnl)){
				continue;
			}
			tmnlList.add(tmnl);
		}
		return  tmnlList;
	}
	
	@JSON(serialize = false)
	public List<String> getCorrectTimeTmnlList() {
		return correctTimeTmnlList;
	}

	public void setCorrectTimeTmnlList(List<String> correctTimeTmnlList) {
		this.correctTimeTmnlList = correctTimeTmnlList;
	}
	
	public List<TmnlExecStatusBean> getTmnlExecStatusList() {
		return tmnlExecStatusList;
	}
    
	public void setTmnlExecStatusList(List<TmnlExecStatusBean> tmnlExecStatusList) {
		this.tmnlExecStatusList = tmnlExecStatusList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<TmnlTime> getFetchResultList() {
		return fetchResultList;
	}

	public void setFetchResultList(List<TmnlTime> fetchResultList) {
		this.fetchResultList = fetchResultList;
	}
	@JSON(serialize = false)
	public Integer getTaskSecond() {
		return taskSecond;
	}

	public void setTaskSecond(Integer taskSecond) {
		this.taskSecond = taskSecond;
	}
	
}
