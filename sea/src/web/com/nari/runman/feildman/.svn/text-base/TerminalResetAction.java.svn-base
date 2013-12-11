package com.nari.runman.feildman;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.coherence.TaskDeal;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.TmnlExecStatusBean;

/**
 * 终端复位功能
 * @author 姜海辉
 *
 */
public class TerminalResetAction extends BaseAction{
	//返回值
	public boolean success = true;

	private List<String> resetTmnlList;      //终端列表
	private String msg;                      //返回信息
	private Short resetType;                 //复位类型
	private List<TmnlExecStatusBean> tmnlExecStatusList;//终端执行状态列表
	private Integer taskSecond=30;               //任务执行时间	 
	//自动注入终端复位Manager类
	private ITerminalResetManager iTerminalResetManager;
	
	public void setiTerminalResetManager(ITerminalResetManager iTerminalResetManager) {
		this.iTerminalResetManager = iTerminalResetManager;
	}
	
	public String terminalReset() throws Exception{
		try{
			List<CtrlParamBean> tmnlList= parseTmnlList();
			if(null==tmnlList||0==tmnlList.size()||null==this.resetType){
				this.success=false;
				return SUCCESS;
			}	
			if(!TaskDeal.isCacheRunning()){
				this.msg="前置集群通信中断";
				return SUCCESS;
			}
			if(!TaskDeal.isFrontAlive()){
				this.msg="前置集群服务中断";
				return SUCCESS;
			}
			this.tmnlExecStatusList=this.iTerminalResetManager.tmnlReset(tmnlList, this.resetType,this.taskSecond);
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
		if(null==this.resetTmnlList||0==this.resetTmnlList.size())
			return null;
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < this.resetTmnlList.size(); i++) {
			String[] tmnl = this.resetTmnlList.get(i).split("`");
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
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@JSON(serialize = false)
	public List<String> getResetTmnlList() {
		return resetTmnlList;
	}
	
	public void setResetTmnlList(List<String> resetTmnlList) {
		this.resetTmnlList = resetTmnlList;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public List<TmnlExecStatusBean> getTmnlExecStatusList() {
		return tmnlExecStatusList;
	}
	
	public void setTmnlExecStatusList(List<TmnlExecStatusBean> tmnlExecStatusList) {
		this.tmnlExecStatusList = tmnlExecStatusList;
	}
	
	@JSON(serialize = false)
	public Short getResetType() {
		return resetType;
	}
	
	public void setResetType(Short resetType) {
		this.resetType = resetType;
	}
	
	//@JSON(serialize = false)
	public Integer getTaskSecond() {
		return taskSecond;
	}

	public void setTaskSecond(Integer taskSecond) {
		this.taskSecond = taskSecond;
	}
	

}
