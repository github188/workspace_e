package com.nari.runman.archivesman;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.runcontrol.TDialChannel;
import com.nari.runcontrol.TDialGroupChannel;
import com.nari.runcontrol.TDnnChannel;
import com.nari.runcontrol.TNetChannel;
import com.nari.sysman.templateman.VwProtocolCode;

/**
 * Action 类　CollectChannelAction
 * 
 * @author zhangzhw 档案管理　采集通道维护　Action
 */
public class CollectChannelManAction extends BaseAction {

	// 注入类
	ICollectionChannelService iCollectionChannelService;
	// 返回确认
	public boolean success = true;
	public Errors errors;

	// 前台参数
	public String nodeId;

	// 返回值
	public List<TDialChannel> listDial;
	public List<TDialGroupChannel> listDialGroup;
	public List<TDnnChannel> listDnn;
	public List<TNetChannel> listNet;
	
	public List<VwProtocolCode> listProtocol;
	

	/**
	 * ACTION 方法 queryTerminal
	 * 
	 * @return 通过终端查询通道信息
	 */
	public String queryTerminal() throws Exception{
		String nodes[] = nodeId.split("_");
		String assetNo = "";

		if (nodes.length > 1)
			assetNo = nodes[2];

		listDial = iCollectionChannelService.queryDialByTmnl(assetNo);
		listDnn = iCollectionChannelService.queryDnnByTmnl(assetNo);
		listNet = iCollectionChannelService.queryNetByTmnl(assetNo);
		return SUCCESS;
	}

	
	public String queryAllProtocol() throws Exception{
		listProtocol = iCollectionChannelService.queryAllProtocol();
		return SUCCESS;
	}
	
	 @JSON(serialize = false)
	public ICollectionChannelService getiCollectionChannelService() {
		return iCollectionChannelService;
	}

	public void setiCollectionChannelService(
			ICollectionChannelService iCollectionChannelService) {
		this.iCollectionChannelService = iCollectionChannelService;
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

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public List<TDialChannel> getListDial() {
		return listDial;
	}

	public void setListDial(List<TDialChannel> listDial) {
		this.listDial = listDial;
	}

	public List<TDialGroupChannel> getListDialGroup() {
		return listDialGroup;
	}

	public void setListDialGroup(List<TDialGroupChannel> listDialGroup) {
		this.listDialGroup = listDialGroup;
	}

	public List<TDnnChannel> getListDnn() {
		return listDnn;
	}

	public void setListDnn(List<TDnnChannel> listDnn) {
		this.listDnn = listDnn;
	}

	public List<TNetChannel> getListNet() {
		return listNet;
	}

	public void setListNet(List<TNetChannel> listNet) {
		this.listNet = listNet;
	}

	public List<VwProtocolCode> getListProtocol() {
		return listProtocol;
	}

	public void setListProtocol(List<VwProtocolCode> listProtocol) {
		this.listProtocol = listProtocol;
	}
}
