package com.nari.runman.archivesman;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.runcontrol.TDnnChannel;

/**
 * Action 类　CollectChannelAction
 * 
 * @author zhangzhw 档案管理　通道管理　Action
 */
public class DnnManAction extends BaseAction {

	// 注入类
	ICollectionChannelService iCollectionChannelService;
	// 返回确认
	public boolean success = true;
	public Errors errors;
	public String msg;

	// 返回值
	public List<TDnnChannel> listDnn;

	//form表单
	private Long dnnChannelId;
	private String tmnlAssetNo;
	private String command;
	private Integer port;
	private String protocolCode;
	private Short pri;
	
	/**
	 * 查询专线通道
	 * @return
	 */
	public String queryDnn() throws Exception {
		listDnn = iCollectionChannelService.queryDnnByTmnl(this.tmnlAssetNo);
		return SUCCESS;
	}
	
	/**
	 * 保存专线通道
	 */
	public String saveDnn() throws Exception {
		TDnnChannel t = this.genDnn();
		this.msg = this.iCollectionChannelService.saveDnn(t);
		return SUCCESS;
	}

	/**
	 * 删除专线通道
	 * @return
	 * @throws Exception
	 */
	public String deleteDnn() throws Exception {
		this.iCollectionChannelService.deleteDnn(this.dnnChannelId.toString());
		return SUCCESS;
	}
	
	private TDnnChannel genDnn(){
		TDnnChannel t = new TDnnChannel();
		t.setPort(port);
		t.setPri(pri);
		t.setProtocolCode(protocolCode);
		t.setTmnlAssetNo(tmnlAssetNo);
		t.setCommand(command);
		t.setDnnChannelId(dnnChannelId);
		return t;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getDnnChannelId() {
		return dnnChannelId;
	}

	public void setDnnChannelId(Long dnnChannelId) {
		this.dnnChannelId = dnnChannelId;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
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

	public List<TDnnChannel> getListDnn() {
		return listDnn;
	}

	public void setListDnn(List<TDnnChannel> listDnn) {
		this.listDnn = listDnn;
	}
}
