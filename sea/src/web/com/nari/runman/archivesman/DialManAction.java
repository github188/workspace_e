package com.nari.runman.archivesman;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.runcontrol.TDialChannel;

/**
 * Action 类　CollectChannelAction
 * 
 * @author zhangzhw 档案管理　通道管理　Action
 */
public class DialManAction extends BaseAction {

	// 注入类
	ICollectionChannelService iCollectionChannelService;
	// 返回确认
	public boolean success = true;
	public Errors errors;
	public String msg;

	// 前台参数
	public String nodeId;

	// 返回值
	public List<TDialChannel> listDial;

	//form表单
	private Long dialChannelId;
	private String tmnlAssetNo;
	private Long baudRate;
	private Integer parityBit;
	private Integer stopBit;
	private Long carrierCtrl;
	private Long txfifo;
	private String phoneCode;
	private Short tryTimes;
	private Short dialGroupNo;
	private Integer tsPort;
	private Integer serialPort;
	private String tsIp;
	private String protocolCode;
	private Short pri;
	
	/**
	 * 查询拨号通道
	 * @return
	 */
	public String queryDial() throws Exception {
		listDial = iCollectionChannelService.queryDialByTmnl(tmnlAssetNo);
		return SUCCESS;
	}
	
	/**
	 * 保存、新增拨号组
	 * @return 查询拨号组
	 */
	public String saveDial() throws Exception {
		TDialChannel t = this.genDial();
		this.msg = this.iCollectionChannelService.saveDial(t);
		return SUCCESS;
	}

	/**
	 * 删除拨号通道
	 * @return
	 * @throws Exception
	 */
	public String deleteDial() throws Exception {
		this.iCollectionChannelService.deleteDial(dialChannelId.toString());
		return SUCCESS;
	}
	
	
	private TDialChannel genDial(){
		TDialChannel t = new TDialChannel();
		t.setBaudRate(baudRate);
		t.setCarrierCtrl(carrierCtrl);
		t.setDialChannelId(dialChannelId);
		t.setDialGroupNo(dialGroupNo);
		t.setParityBit(parityBit);
		t.setPhoneCode(phoneCode);
		t.setPri(pri);
		t.setProtocolCode(protocolCode);
		t.setSerialPort(serialPort);
		t.setStopBit(stopBit);
		t.setTmnlAssetNo(tmnlAssetNo);
		t.setTryTimes(tryTimes);
		t.setTsIp(tsIp);
		t.setTsPort(tsPort);
		t.setTxfifo(txfifo);
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

	public Long getDialChannelId() {
		return dialChannelId;
	}

	public void setDialChannelId(Long dialChannelId) {
		this.dialChannelId = dialChannelId;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public Long getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(Long baudRate) {
		this.baudRate = baudRate;
	}

	public Integer getParityBit() {
		return parityBit;
	}

	public void setParityBit(Integer parityBit) {
		this.parityBit = parityBit;
	}

	public Integer getStopBit() {
		return stopBit;
	}

	public void setStopBit(Integer stopBit) {
		this.stopBit = stopBit;
	}

	public Long getCarrierCtrl() {
		return carrierCtrl;
	}

	public void setCarrierCtrl(Long carrierCtrl) {
		this.carrierCtrl = carrierCtrl;
	}

	public Long getTxfifo() {
		return txfifo;
	}

	public void setTxfifo(Long txfifo) {
		this.txfifo = txfifo;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public Short getTryTimes() {
		return tryTimes;
	}

	public void setTryTimes(Short tryTimes) {
		this.tryTimes = tryTimes;
	}

	public Short getDialGroupNo() {
		return dialGroupNo;
	}

	public void setDialGroupNo(Short dialGroupNo) {
		this.dialGroupNo = dialGroupNo;
	}

	public Integer getTsPort() {
		return tsPort;
	}

	public void setTsPort(Integer tsPort) {
		this.tsPort = tsPort;
	}

	public Integer getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(Integer serialPort) {
		this.serialPort = serialPort;
	}

	public String getTsIp() {
		return tsIp;
	}

	public void setTsIp(String tsIp) {
		this.tsIp = tsIp;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
