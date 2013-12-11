package com.nari.runman.archivesman;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.runcontrol.TNetChannel;

/**
 * Action 类　CollectChannelAction
 * 
 * @author zhangzhw 档案管理　通道管理　Action
 */
public class NetManAction extends BaseAction {

	// 注入类
	ICollectionChannelService iCollectionChannelService;
	// 返回确认
	public boolean success = true;
	public Errors errors;
	public String msg;


	// 返回值
	public List<TNetChannel> listNet;
	
	//form表单
	private Long netChannelId;
	private String tmnlAssetNo;
	private String ip;
	private Integer port;
	private String protocolCode;
	private Short pri;
	

	/**
	 * 查询网络通道
	 * @return
	 */
	public String queryNet() throws Exception {
		listNet = iCollectionChannelService.queryNetByTmnl(this.tmnlAssetNo);
		return SUCCESS;
	}
	
	/**
	 * 保存网络通道
	 */
	public String saveNet() throws Exception {
		TNetChannel t = this.genNet();
		this.msg = this.iCollectionChannelService.saveNet(t);
		return SUCCESS;
	}

	/**
	 * 删除网络通道
	 * @return
	 * @throws Exception
	 */
	public String deleteNet() throws Exception {
		this.iCollectionChannelService.deleteNet(this.netChannelId.toString());
		return SUCCESS;
	}
	
	private TNetChannel genNet(){
		TNetChannel t = new TNetChannel();
		t.setIp(ip);
		t.setNetChannelId(netChannelId);
		t.setPort(port);
		t.setPri(pri);
		t.setProtocolCode(protocolCode);
		t.setTmnlAssetNo(tmnlAssetNo);
		return t;
	}
	
	@JSON(serialize = false)
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getNetChannelId() {
		return netChannelId;
	}

	public void setNetChannelId(Long netChannelId) {
		this.netChannelId = netChannelId;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public Short getPri() {
		return pri;
	}

	public void setPri(Short pri) {
		this.pri = pri;
	}

	public List<TNetChannel> getListNet() {
		return listNet;
	}

	public void setListNet(List<TNetChannel> listNet) {
		this.listNet = listNet;
	}



	
}
