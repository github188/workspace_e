package com.nari.runman.archivesman;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.runcontrol.TDialGroupChannel;

/**
 * Action 类　CollectChannelAction
 * 
 * @author zhangzhw 档案管理　通道管理　Action
 */
public class DialGroupManAction extends BaseAction {

	// 注入类
	ICollectionChannelService iCollectionChannelService;
	// 返回确认
	public boolean success = true;
	public Errors errors;
	public String msg;

	// 前台参数
	public String nodeId;

	// 返回值
	public List<TDialGroupChannel> listDialGroup;

	//form表单
	private Short dialGroupNo;
	private String dialGroupName;
	private Byte dialType;
	private String tsIp;
	private Integer tsPort;
	private Byte isUsed;
	private String usedTime;
	
	/**
	 * 查询拨号组
	 * @return
	 */
	public String queryDialGroup() throws Exception {
		listDialGroup = iCollectionChannelService.queryDialGroup();
		return SUCCESS;
	}
	
	/**
	 * 保存拨号组
	 * @return 查询拨号组
	 */
	public String saveDialGroup() throws Exception {
		TDialGroupChannel t = this.genDialGroup();
		this.msg = this.iCollectionChannelService.saveDialGroup(t);
		return SUCCESS;
	}

	/**
	 * 删除拨号组
	 * @return
	 * @throws Exception
	 */
	public String deleteDialGroup() throws Exception {
		this.iCollectionChannelService.deleteDialGroup(dialGroupNo.toString());
		return SUCCESS;
	}
	
	//组合前台参数
	private TDialGroupChannel genDialGroup(){
		TDialGroupChannel t = new TDialGroupChannel();
		t.setDialGroupName(dialGroupName);
		t.setDialGroupNo(dialGroupNo);
		t.setDialType(dialType);
		t.setIsUsed(isUsed);
		t.setTsIp(tsIp);
		t.setTsPort(tsPort);
		if(usedTime != null)
			t.setUsedTime(usedTime.substring(0, 10));
		return t;
	}
	
	// getters and setters
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

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public List<TDialGroupChannel> getListDialGroup() {
		return listDialGroup;
	}

	public void setListDialGroup(List<TDialGroupChannel> listDialGroup) {
		this.listDialGroup = listDialGroup;
	}

	public Short getDialGroupNo() {
		return dialGroupNo;
	}

	public void setDialGroupNo(Short dialGroupNo) {
		this.dialGroupNo = dialGroupNo;
	}

	public String getDialGroupName() {
		return dialGroupName;
	}

	public void setDialGroupName(String dialGroupName) {
		this.dialGroupName = dialGroupName;
	}

	public Byte getDialType() {
		return dialType;
	}

	public void setDialType(Byte dialType) {
		this.dialType = dialType;
	}

	public String getTsIp() {
		return tsIp;
	}

	public void setTsIp(String tsIp) {
		this.tsIp = tsIp;
	}

	public Integer getTsPort() {
		return tsPort;
	}

	public void setTsPort(Integer tsPort) {
		this.tsPort = tsPort;
	}

	public Byte getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Byte isUsed) {
		this.isUsed = isUsed;
	}

	public String getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(String usedTime) {
		this.usedTime = usedTime;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
