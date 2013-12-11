package com.nari.baseapp.datagathorman;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.baseapp.datagatherman.GatherTaskCompileDto;
import com.nari.baseapp.datagatherman.TaskInfoDto;
import com.nari.coherence.TaskDeal;
import com.nari.privilige.PSysUser;

/**
 * 数据采集管理 Action
 * @author 余涛
 *
 */
public class GatherTaskCompileAction extends BaseAction{
	private Logger logger = Logger.getLogger(this.getClass());
	
	/* 自动注入业务层 */
	private GatherTaskCompileManager gatherTaskCompileManager;
	public void setGatherTaskCompileManager(
			GatherTaskCompileManager gatherTaskCompileManager) {
		this.gatherTaskCompileManager = gatherTaskCompileManager;
	}
	//分页入参
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private int totalCount;
	
	/* 需要返回的Json */
	private boolean success = true; // 是否成功
	private int taskSecond;	//任务执行时间
	private String consNo;
	private String nodeType;
	private String subsId;
	private String orgNo;
	private String groupNo;
	private String lineId;
	private List<GatherTaskCompileDto> gatherTaskCompileList;
	private List<String> pns;
	private Map<String,String> datas;
	private String templateId;
	private String taskNo;
	private String dataType;
	private String mpsn;
	private String taskModel;
	private String consNos;
	private String terminalAddrs;
	private String tmnlAssetNos;
	private String sendUpModes;
	private String protocolCodes;
	private String enableTaskNos;
	private String unableTaskNos;
	
	private String eventType;
	private String taskNos;
	private TaskInfoDto taskInfoDto;
	private String tmnlAssetNo;
	private String[] tmnlAssetNoArr;
	private String msg = "";
	/**
	 * 点击左边树节点生成grid数据
	 * @return
	 * @throws Exception
	 */
	public String generalGridByTree() throws Exception {
		this.logger.debug("查询信息放入Grid开始");
		PSysUser pSysUser = (PSysUser)this.getSession().getAttribute("pSysUser");
		this.gatherTaskCompileList = gatherTaskCompileManager.queryGatherTaskCompileByTree(pSysUser,this.consNo,
												this.orgNo,this.lineId,this.nodeType,this.groupNo,this.tmnlAssetNoArr);
		if(gatherTaskCompileList!=null){
			this.totalCount = gatherTaskCompileList.size();
		}
		this.logger.debug("查询信息放入Grid结束");
		return SUCCESS;
	}
	
	/**
	 * 下发任务
	 * @return
	 * @throws Exception
	 */
	public String sendOut() throws Exception {
		PSysUser pSysUser =(PSysUser) getSession().getAttribute("pSysUser");
		if(pSysUser == null){
			return SUCCESS;
		}
		if(!TaskDeal.isCacheRunning()){
			msg = "前置集群通信中断";
			return SUCCESS;
		}
		this.gatherTaskCompileList = this.gatherTaskCompileManager.sendOutAndQuery(this.taskNo,
							this.mpsn,this.taskModel,this.consNos,this.tmnlAssetNos,
							this.sendUpModes,this.protocolCodes,this.enableTaskNos,
							this.unableTaskNos,this.taskSecond, pSysUser.getStaffNo());
		
//		if(this.tmnlAssetNos!=null){
//			String staffNo = pSysUser.getStaffNo();
//			String orgNo = pSysUser.getOrgNo()==null?"":pSysUser.getOrgNo();
//			String ipAddr = getRequest().getRemoteAddr();
//			
//			String[] tmnlAssentNoArray = this.tmnlAssetNos.split("#");
////			for(int i=0;i<tmnlAssentNoArray.length;i++){
////				LOpTmnlLog lOpTmnlLog = new LOpTmnlLog();
////				lOpTmnlLog.setEmpNo(staffNo);
////				lOpTmnlLog.setOrgNo(orgNo);
////				lOpTmnlLog.setIpAddr(ipAddr);
////				lOpTmnlLog.setOpModule("采集任务编制");
////				lOpTmnlLog.setOpButton("下发");
////				lOpTmnlLog.setTmnlAssetNo(tmnlAssentNoArray[i]);
////				lOpTmnlLog.setProtItemNo(this.taskNo);
////				lOpTmnlLog.setCurValue(this.taskModel);
////				
////				this.addOpTmnlLog(lOpTmnlLog);
////			}
//		}
		return SUCCESS;
	}
	
	/**
	 * 启用、停用任务
	 * @return
	 * @throws Exception
	 */
	public String ynTaskStatus() throws Exception {
		if(!TaskDeal.isCacheRunning()){
			msg = "前置集群通信中断";
			return SUCCESS;
		}
		
		this.gatherTaskCompileList = this.gatherTaskCompileManager.ynTaskStatus(this.consNos,this.tmnlAssetNos,
								this.sendUpModes, this.taskNos, this.eventType,this.protocolCodes,this.enableTaskNos,
								this.unableTaskNos, this.taskSecond);
		return SUCCESS;
	}
	
	/**
	 * 删除任务
	 * @return
	 * @throws Exception
	 */
	public String deleteTask() throws Exception {
		if(!TaskDeal.isCacheRunning()){
			msg = "前置集群通信中断";
			return SUCCESS;
		}
		this.gatherTaskCompileList = this.gatherTaskCompileManager.deleteTask(this.consNos,
									this.tmnlAssetNos,this.sendUpModes, this.taskNos,
									this.protocolCodes,this.enableTaskNos,
									this.unableTaskNos,this.taskSecond);
		return SUCCESS;
	}
	
	/**
	 * 查询任务明细信息
	 * @return
	 * @throws Exception
	 */
	public String queryTaskInfo() throws Exception{
		this.taskInfoDto = this.gatherTaskCompileManager.queryTaskInfo(this.tmnlAssetNo, this.taskNo);
		return SUCCESS;
	}
	
	/**
	 * 召测任务信息
	 * @return
	 * @throws Exception
	 */
	public String callTaskPara() throws Exception{
		if(!TaskDeal.isCacheRunning()){
			msg = "前置集群通信中断";
			return SUCCESS;
		}
		if(!TaskDeal.isFrontAlive()){
			msg = "前置集群服务中断";
			return SUCCESS;
		}
		
		this.taskInfoDto = this.gatherTaskCompileManager.callTaskPara(tmnlAssetNo, taskNo, dataType, taskSecond);
		return SUCCESS;
	}
	/**后台下发任务***/
	public String bgSendTask(){
		try {
			PSysUser user =(PSysUser) getSession().getAttribute("pSysUser");
			gatherTaskCompileManager.batchSendOut(user, taskNo, templateId, pns, datas);
		} catch (Exception e) {
			this.msg=e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public boolean isSuccess() {
		return this.success;
	}
	public String getConsNo() {
		return consNo;
	}
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getSubsId() {
		return subsId;
	}
	public void setSubsId(String subsId) {
		this.subsId = subsId;
	}
	public List<GatherTaskCompileDto> getGatherTaskCompileList() {
		return gatherTaskCompileList;
	}
	public void setGatherTaskCompileList(
			List<GatherTaskCompileDto> gatherTaskCompileList) {
		this.gatherTaskCompileList = gatherTaskCompileList;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getMpsn() {
		return mpsn;
	}
	public void setMpsn(String mpsn) {
		this.mpsn = mpsn;
	}
	public String getTaskModel() {
		return taskModel;
	}
	public void setTaskModel(String taskModel) {
		this.taskModel = taskModel;
	}
	public String getConsNos() {
		return consNos;
	}
	public void setConsNos(String consNos) {
		this.consNos = consNos;
	}
	public String getTerminalAddrs() {
		return terminalAddrs;
	}
	public void setTerminalAddrs(String terminalAddrs) {
		this.terminalAddrs = terminalAddrs;
	}
	public String getSendUpModes() {
		return sendUpModes;
	}
	public void setSendUpModes(String sendUpModes) {
		this.sendUpModes = sendUpModes;
	}
	public String getTmnlAssetNos() {
		return tmnlAssetNos;
	}
	public void setTmnlAssetNos(String tmnlAssetNos) {
		this.tmnlAssetNos = tmnlAssetNos;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getTaskNos() {
		return taskNos;
	}
	public void setTaskNos(String taskNos) {
		this.taskNos = taskNos;
	}
	public TaskInfoDto getTaskInfoDto() {
		return taskInfoDto;
	}
	public void setTaskInfoDto(TaskInfoDto taskInfoDto) {
		this.taskInfoDto = taskInfoDto;
	}
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getProtocolCodes() {
		return protocolCodes;
	}
	public void setProtocolCodes(String protocolCodes) {
		this.protocolCodes = protocolCodes;
	}
	public int getTaskSecond() {
		return taskSecond;
	}
	public void setTaskSecond(int taskSecond) {
		this.taskSecond = taskSecond;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getEnableTaskNos() {
		return enableTaskNos;
	}
	public void setEnableTaskNos(String enableTaskNos) {
		this.enableTaskNos = enableTaskNos;
	}
	public String getUnableTaskNos() {
		return unableTaskNos;
	}
	public void setUnableTaskNos(String unableTaskNos) {
		this.unableTaskNos = unableTaskNos;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, String> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, String> datas) {
		this.datas = datas;
	}

	public void setPns(List<String> pns) {
		this.pns = pns;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String[] getTmnlAssetNoArr() {
		return tmnlAssetNoArr;
	}

	public void setTmnlAssetNoArr(String[] tmnlAssetNoArr) {
		this.tmnlAssetNoArr = tmnlAssetNoArr;
	}
}
