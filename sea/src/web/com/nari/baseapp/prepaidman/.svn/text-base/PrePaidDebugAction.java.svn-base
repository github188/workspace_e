/**
 * author MY修改
 * author jiangyike modify on 2010-5-14
 */
package com.nari.baseapp.prepaidman;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.baseapp.datagathorman.DataFetchManager;
import com.nari.baseapp.planpowerconsume.RemoteCtrlManager;
import com.nari.coherence.TaskDeal;
import com.nari.elementsdata.EDataTotal;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.DataCode;
import com.nari.fe.commdefine.task.DbData;
import com.nari.fe.commdefine.task.HisDataItem;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.RealDataItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.RemoteControlDto;
import com.nari.orderlypower.RmtCtrlSwitchDto;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.DateUtil;
import com.nari.util.FlowCodeNodeUtil;
import com.nari.util.SendStatusCodeUtil;
import com.nari.util.TreeNodeChecked;

/**
 * 作者: jiangyike
 * 描述：预付费投入调试Action
 */
public class PrePaidDebugAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(PrePaidDebugAction.class);
	private PrePaidDebugManager prePaidDebugManager;
	private RemoteCtrlManager remoteCtrlManager;//自动注入遥控业务层 
	private PrePaidManager prePaidManager;
	/* 自动注入业务层 */
	private DataFetchManager dataFetchManager;
	
	private List<PPDebugUserInfoBean> userInfoList;//用户信息
	private boolean success = true;
	
	private Integer control;//控制方式
	private String orgNo;//供电单位编号
	private String tmnlAddr;//终端地址
	private String consNo;//用户编号
	private Date startDate;//开始日期
	private Date endDate;//结束日期
	private String cacheAndTmnlStatus;         //提示信息
	private List<String>  terminalList; //前台获取的终端列表
	private Integer taskSecond=45;    
	private List<PPDebugCallDataBean> readList = new ArrayList<PPDebugCallDataBean>();//召测返回结果

	private List<PPDebugOperaBean> ppDeugOpList;//用户终端操作记录
	private long totalCount;//总数
	private List<String> prepaidDebugList;//前台选中的数据列表

	private String debugStatus;//调试状态
	
	public List<TreeNodeChecked> treeNodeList;//供电单位树
	private BigDecimal feeCtrlId;//费控id
	private PPDebugUserInfoBean ppdParamInfoBean;//预付费参数信息bean
	private PPDebugTmnlInfoBean ppdTmnlInfoBean;//预付费终端工况信息bean
	private List<PPDebugUserStatusInfoBean> ppdUserStatusInfoList;//预付费用户流程状态信息列表
	private List<EDataTotal> ppdParamTotalList;//总加组列表
	private Integer jumpValue;//跳闸电量
	private Integer alarmValue;//告警电量
	private Integer buyValue;//购买电量
	private String turnFlag;//轮次
    private String terminalId;//终端id
    private String meterId;//电表id
    private String appNo;//购电单号
    private String tmnlAssetNo;//终端资产编号
    private Integer totalNo;//总加组号
    private String totalName;//总加组名称
    private String protocolCode;//协议编码
	private String refreshFlag;// 追加/刷新标志
	private Byte alarmValueUnit;//报警门阀值单位:0-kWh、厘;1-MWh、元
	private Byte buyValueUnit;//购电量（费）值单位:0-kWh、厘;1-MWh、元
	private Byte jumpValueUnit;//跳闸门限值单位:0-kWh、厘;1-MWh、元
    
    private List<RmtCtrlSwitchDto> switchList;//遥测开关执行命令后的返回结果
    private List<RemoteControlDto> resultList;//拉闸、合闸执行命令后的返回结果
    private List<PrePaidExecResultBean> paramResultList;//参数下发、方案下发的返回结果
    private List<RmtCtrlSwitchDto> turnStatusList;//召测开关状态返回结果
    private List<PrePaidParamSetTmnlBean> ppdParamList;//预付费参数召测Bean
    private String status;//返回状态
	private long start = 0;//分页参数
	private int limit = Constans.DEFAULT_PAGE_SIZE;//分页参数    
	
	private String realFns;//实时数据召测fn项
	private String statFns;//冻结数据召测fn项
	private Boolean checkAll;//是否全选
	private Boolean totalNoAll;//总加组设置是否全部显示测量点标识
	private List<PPDebugTotalBean> ppdTotalSetList;//总加组设置查询列表
	private List<PPDebugTotalMpBean> ppdTotalMpSetList;//总加组下属测量点设置查询列表
	private String areaNo;//上级供电单位
	private String cisTmnlAssetNo;//真实终端资产编号，营销过来
	private List<String> mpList;//测量点数组
	private String cpNo;//采集点编号
	private Boolean newFlag;//true表示新建，false表示修改
	private int totalRecord;//终端下某个总加组已经投入的记录条数
	private int operCode;//写表返回值，0 表示失败，1表示成功
	
	/**
	 * 预付费投入调试，加载grid用户数据
	 * @return String
	 * @throws Exception 
	 */
	public String queryUserInfo() throws Exception {
		logger.info("============预付费投入调试加载用户信息开始============");
    	PSysUser user = (PSysUser) getSession().getAttribute("pSysUser");
        
    	Page<PPDebugUserInfoBean> page = prePaidDebugManager.findUserInfo(control, appNo, DateUtil.dateToString(startDate), 
    			DateUtil.dateToString(endDate), consNo, orgNo, tmnlAddr, debugStatus, user, start, limit);
    	userInfoList = page.getResult();
    	totalCount = page.getTotalCount();
		logger.info("============预付费投入调试加载用户信息结束============");
		return SUCCESS;
	}
	
	/**
	 * 获取供电单位信息，并组装成树结构
	 */
	public String queryOrgTree() throws Exception {
		logger.info("============查询供电单位树开始============");
		PSysUser pSysUser = getPSysUser();
		if (null == pSysUser) {
			return ERROR;
		}
		this.treeNodeList = this.prePaidDebugManager.findOrgTree(pSysUser);
		logger.info("============查询供电单位树结束============");
		return SUCCESS;
	}
	
	/**
	 * 根据购电单号查询预付费参数详细信息
	 */
	public String queryOrderParamInfo() throws Exception {
		logger.info("============根据购电单号查询预付费参数详细信息开始============");
		this.ppdParamInfoBean = this.prePaidDebugManager.findOrderParamInfo(feeCtrlId.longValue(), appNo);
		logger.info("============根据购电单号查询预付费参数详细信息结束============");
		return SUCCESS;
	}
	
	/**
	 * 根据终端查询总加组列表
	 */
	public String queryParamTotalInfo() throws Exception {
		logger.info("============根据终端查询总加组列表开始============");
		this.ppdParamTotalList = this.prePaidDebugManager.findParamTotalInfo(tmnlAssetNo);
		logger.info("============根据终端查询总加组列表结束============");
		return SUCCESS;
	}
	
	/**
	 * 更新预付费参数
	 */
	public String updatePPDPara() throws Exception {
		logger.info("============更新预付费参数开始============");
		this.prePaidDebugManager.updatePPDPara(feeCtrlId.longValue(), appNo, jumpValue, buyValue, 
				alarmValue, turnFlag, (null != totalNo ? totalNo.shortValue():null));
		logger.info("============更新预付费参数结束============");
		return SUCCESS;
	}
	
	/**
	 * 根据购电单号、终端id、电表id查询终端工况
	 */
	public String queryTmnlInfo() throws Exception {
		logger.info("============根据根据购电单号、终端id、电表id查询终端工况开始============");
		this.ppdTmnlInfoBean = this.prePaidDebugManager.findTmnlInfo(appNo, terminalId, meterId);
		logger.info("============根据根据购电单号、终端id、电表id查询终端工况结束============");
		return SUCCESS;
	}
	
	/**
	 * 查询预付费用户调试状态具体流程信息
	 */
	public String queryUserDebugInfo() throws Exception {
		logger.info("============查询预付费用户调试状态具体流程信息开始============");
		this.ppdUserStatusInfoList = this.prePaidDebugManager.findUserDebugInfo(appNo, terminalId, meterId);
		logger.info("============查询预付费用户调试状态具体流程信息结束============");
		return SUCCESS;
	}
	
	/**
	 * 预付费投入调试召测电能表电能示值，下发前置，一类数据召测
	 * @return String
	 * @throws Exception 
	 */
	public String fetchRead() throws Exception {
		if(null == tmnlAssetNo || null == totalNo){
			return SUCCESS;
		}
		
		if(null == realFns && null == statFns){
			return SUCCESS;
		}
		
		logger.info("============预付费投入调试召测电能表电能示值开始============");
		try {
			//修改或插入流程表此条记录，不修改主表
			if(null != appNo && null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean = new PPDebugWFeectrlFlowBean();
				bean.setAppNo(appNo);
				bean.setTerminalId(terminalId);
				bean.setMeterId(Long.valueOf(meterId));
				bean.setFlowNoCode(FlowCodeNodeUtil.FIR_TMNL_READ);
				bean.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_DOING);//调试中
				bean.setOpTime(DateUtil.dateToSqlDate(new Date()));
			    prePaidDebugManager.saveOrUpdateWFeeCtrlFlow(bean);
			}
			
			List<Response> responseRealList = new ArrayList<Response>();//定义实时数据召测返回结果
			List<Response> responseStatList = new ArrayList<Response>();//定义冻结数据召测返回结果
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			
			//实时数据召测
			if(null != realFns && 0 < realFns.length()){
				responseRealList = fetchRealData(tmnlAssetNo,realFns);
			}
			
			//冻结数据召测
			if(null != statFns && 0 < statFns.length()){
				responseStatList = fetchStatData(tmnlAssetNo,statFns);
			}
			
			// 使用map一次查询对应可以提交执行效率
			// 记录查到的编码的名称
			List<String> names = new ArrayList<String>();
			Short state = null;//前置返回的状态编码
			
			//循环获取返回实时召测结果
			if(null != responseRealList && 0 < responseRealList.size()){
				for (Response r : responseRealList) {
					 if(null != r.getTmnlAssetNo() && tmnlAssetNo.equals(r.getTmnlAssetNo())){
						 List<DbData> dbs = r.getDbDatas();
						 if(dbs==null){
							 continue;
						 }
						 for (DbData db : dbs) {
							ArrayList<DataCode> dcs = db.getDataCodes();
							for (DataCode dc : dcs) {
								PPDebugCallDataBean bean = new PPDebugCallDataBean();
								bean.setTmnlAssetNo(r.getTmnlAssetNo());
								bean.setItem(dc.getName());//透明规约子项
								bean.setPn(db.getPn());
								bean.setValue("-9999.0".equals(dc.getValue().toString())?"":dc.getValue().toString());
								bean.setTime(DateUtil.dateToTSString(db.getTime()));
								bean.setErrorCode(r.getErrorCode());
								readList.add(bean);
								names.add(dc.getName());
								//获取任务状态，如果为3，且errorcode为null则表示成功，这时候，如果status不为3，则赋值为3
								state = r.getTaskStatus();
								if(null != state && FlowCodeNodeUtil.FRONT_STATE3.equals(state.toString()) 
										/*&& null == r.getErrorCode()*/){
									if(null == status || !FlowCodeNodeUtil.FRONT_STATE3.equals(status)){
										status = FlowCodeNodeUtil.FRONT_STATE3;
									}
								}
							}
						 }
						 break;
					 }
				}
			}
			
			//循环获取返回冻结数据结果
			if(null != responseStatList && 0 < responseStatList.size()){
				for (Response r : responseStatList) {
					 if(null != r.getTmnlAssetNo() && tmnlAssetNo.equals(r.getTmnlAssetNo())){
						 List<DbData> dbs = r.getDbDatas();
						 if(dbs==null){
							 continue;
						 }
						 for (DbData db : dbs) {
							ArrayList<DataCode> dcs = db.getDataCodes();
							for (DataCode dc : dcs) {
								PPDebugCallDataBean bean = new PPDebugCallDataBean();
								bean.setTmnlAssetNo(r.getTmnlAssetNo());
								bean.setItem(dc.getName());//透明规约子项
								bean.setPn(db.getPn());
								bean.setValue("-9999.0".equals(dc.getValue().toString())?"":dc.getValue().toString());
								bean.setTime(DateUtil.dateToTSString(db.getTime()));
								bean.setErrorCode(r.getErrorCode());
								readList.add(bean);
								names.add(dc.getName());
								//获取任务状态，如果为3，则表示成功，这时候，如果status不为3，则赋值为3
								state = r.getTaskStatus();
								if(null != state && FlowCodeNodeUtil.FRONT_STATE3.equals(state.toString())
										/*&& null == r.getErrorCode()*/){
									if(null == status || !FlowCodeNodeUtil.FRONT_STATE3.equals(status)){
										status = FlowCodeNodeUtil.FRONT_STATE3;
									}
								}
							}
						 }
						 break;
					 }
				}
			}
			
			//获取显示具体明细项的中文含义
			Map<String, String> map = dataFetchManager.findCodeName(names);
			
			if(null != readList && 0 < readList.size()){				
				for(PPDebugCallDataBean tempBean : readList){
					tempBean.setItemName(map.get(tempBean.getItem()));
				}
			}
			 
			//不管成功与否，写入流程表，修改状态
			if(null != appNo && null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean2 = new PPDebugWFeectrlFlowBean();
				bean2.setAppNo(appNo);
				bean2.setTerminalId(terminalId);
				bean2.setMeterId(Long.valueOf(meterId));
				bean2.setFlowNoCode(FlowCodeNodeUtil.FIR_TMNL_READ);
				if(null == status || "".equals(status)){//无数据，表示失败
					bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_FAILED);
					bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR2);
				}else {
					if(FlowCodeNodeUtil.FRONT_STATE3.endsWith(status)){
						bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_SUCCESS);
						bean2.setErrCause(null);
					}else{
						bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_FAILED);
						if(FlowCodeNodeUtil.FRONT_STATE0.endsWith(status)){
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR0);
						}else if(FlowCodeNodeUtil.FRONT_STATE1.endsWith(status)){
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR1);
						}else{
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR2);
						}
					}
				}
			    prePaidDebugManager.updateWFeeCtrlFlowStatus(bean2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("============预付费投入调试召测电能表电能示值结束============");
		return SUCCESS;
	}
	
	/**
	 * 召测实时数据
	 */
	private List<Response> fetchRealData(String tmnlAssetNo, String realFns){
		TaskDeal taskDeal=new TaskDeal();//每次调用需要new一次，比如，既要调用遥控，又要调用遥信，则需要new两次
		
		//定义终端资产编号列表
		List<String> terminalList = new ArrayList<String>();
		terminalList.add(tmnlAssetNo);
		
		//实时数据召测
		String[] rFns = realFns.split(",");
		
		//定义参数列表
		List<RealDataItem> reals = new ArrayList<RealDataItem>();
		
		if(1 == rFns.length){
			if("1".equals(rFns[0])){
				RealDataItem real = new RealDataItem();
				
				//写入透明规约编码
				ArrayList<Item> itemList = new ArrayList<Item>();
				itemList.add(new Item("1E11FF"));//F25

				real.setCodes(itemList);
				real.setPoint(totalNo.shortValue());//写入总加组号
				
				reals.add(real);
			}else{
				RealDataItem real = new RealDataItem();
				
				//写入透明规约编码
				ArrayList<Item> itemList = new ArrayList<Item>();
				itemList.add(new Item("1112FF"));//F33

				real.setCodes(itemList);
				real.setPoint(totalNo.shortValue());//写入总加组号
				
				reals.add(real);
			}
		}else if(2 == rFns.length){
			RealDataItem real = new RealDataItem();
			
			//写入透明规约编码
			ArrayList<Item> itemList = new ArrayList<Item>();
			itemList.add(new Item("1E11FF"));//F25

			real.setCodes(itemList);
			real.setPoint(totalNo.shortValue());//写入总加组号
			
			reals.add(real);
			
			RealDataItem real1 = new RealDataItem();
			
			//写入透明规约编码
			ArrayList<Item> itemList1 = new ArrayList<Item>();
			itemList1.add(new Item("1112FF"));//F33

			real1.setCodes(itemList1);
			real1.setPoint(totalNo.shortValue());//写入总加组号
			
			reals.add(real);
		}else{
			//do nothing
		}
		taskDeal.callRealData(terminalList, reals);
		
		return taskDeal.getAllResponse(this.taskSecond);//调用得到返回结果
	}
	
	/**
	 * 召测冻结数据
	 */
	private List<Response> fetchStatData(String tmnlAssetNo, String statFns){
		TaskDeal taskDeal=new TaskDeal();//每次调用需要new一次，比如，既要调用遥控，又要调用遥信，则需要new两次
		
		//定义终端资产编号列表
		List<String> terminalList = new ArrayList<String>();
		terminalList.add(tmnlAssetNo);
		
		//实时数据召测
		String[] rFns = statFns.split(",");
		
		//定义参数列表
		List<HisDataItem> reals = new ArrayList<HisDataItem>();
		
		if(1 == rFns.length){
			if("1".equals(rFns[0])){
				HisDataItem real = new HisDataItem();
				
				//写入透明规约编码
				ArrayList<Item> itemList = new ArrayList<Item>();
				itemList.add(new Item("1131FF"));//F1

				real.setCodes(itemList);
				real.setPoint(totalNo.shortValue());//写入总加组号
				real.setStartTime(new Date());
				real.setEndTime(new Date());
				
				reals.add(real);
			}else{
				HisDataItem real = new HisDataItem();
				
				//写入透明规约编码
				ArrayList<Item> itemList = new ArrayList<Item>();
				itemList.add(new Item("1631FF"));//F3

				real.setCodes(itemList);
				real.setPoint(totalNo.shortValue());//写入总加组号
				real.setStartTime(new Date());
				real.setEndTime(new Date());
				reals.add(real);
			}
		}else if(2 == rFns.length){
			HisDataItem real = new HisDataItem();
			
			//写入透明规约编码
			ArrayList<Item> itemList = new ArrayList<Item>();
			itemList.add(new Item("1131FF"));//F1

			real.setCodes(itemList);
			real.setPoint(totalNo.shortValue());//写入总加组号
			
			reals.add(real);
			
			HisDataItem real1 = new HisDataItem();
			
			//写入透明规约编码
			ArrayList<Item> itemList1 = new ArrayList<Item>();
			itemList1.add(new Item("1631FF"));//F3

			real1.setCodes(itemList1);
			real1.setPoint(totalNo.shortValue());//写入总加组号
			
			reals.add(real);
		}else{
			//do nothing
		}
		taskDeal.callHisData(terminalList, reals);
		
		return taskDeal.getAllResponse(this.taskSecond);//调用得到返回结果
	}
	
	/**
	 * 召测召测开关状态，不写费控主表和流程表
	 * @return String
	 * @throws Exception 
	 */
	public String fetchTurnStatus() throws Exception {
		if(null == tmnlAssetNo){
			return SUCCESS;
		}
		logger.info("============遥测开关开始============");
		try{
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
			CtrlParamBean bean = new CtrlParamBean();
			bean.setTmnlAssetNo(tmnlAssetNo);
			tmnlList.add(bean);
			
			switchList = remoteCtrlManager.fetchSwitchStatus(tmnlList, taskSecond);
			
			if(null == switchList || 0 == switchList.size()){
				status = null;
				turnStatusList = new ArrayList<RmtCtrlSwitchDto>();
			}else{
				RmtCtrlSwitchDto turnStatusBean = switchList.get(0);
				if(null != turnStatusBean){
					status = turnStatusBean.getStatusCode();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("============遥测开关结束============");
		return SUCCESS;
	}
	
	/**
	 * 合闸
	 * @return String
	 * @throws Exception 
	 */
	public String openTurn() throws Exception {
		if(null == orgNo || null == consNo || null == tmnlAssetNo || null == protocolCode 
				|| null == turnFlag){
			return SUCCESS;
		}
		
		PSysUser user = getPSysUser();
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		
		if(!TaskDeal.isCacheRunning()){
			cacheAndTmnlStatus = "前置集群通信中断";
			return SUCCESS;
		}
		
		if(!TaskDeal.isFrontAlive()){
			cacheAndTmnlStatus = "前置集群服务中断";
			return SUCCESS;
		}
		
		logger.info("============合闸开始============");
		try{
			//修改或插入流程表此条记录
			if(null != appNo && null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean = new PPDebugWFeectrlFlowBean();
				bean.setAppNo(appNo);
				bean.setTerminalId(terminalId);
				bean.setMeterId(Long.valueOf(meterId));
				bean.setFlowNoCode(FlowCodeNodeUtil.FIR_TMNL_TURN_CTRL);
				bean.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_DOING);
				bean.setOpTime(DateUtil.dateToSqlDate(new Date()));
			    prePaidDebugManager.saveOrUpdateWFeeCtrlFlow(bean);
			}
			
			List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
			CtrlParamBean bean = new CtrlParamBean();
			bean.setOrgNo(orgNo);
			bean.setConsNo(consNo);
			bean.setTmnlAssetNo(tmnlAssetNo);
			bean.setProtocolCode(protocolCode);
			
			tmnlList.add(bean);
			
			resultList = remoteCtrlManager.remoteCtrlOpen(tmnlList, taskSecond, user, turnFlag, getLocalIp());
			
			if(null == resultList || 0 == resultList.size()){
				status = null;
			}else{
				RemoteControlDto dto = resultList.get(0);
				if(null != dto && null != dto.getTmnlAssetNo() && tmnlAssetNo.equals(dto.getTmnlAssetNo())){
					status = dto.getStatusCode();
				}
			}	
			
			//不管成功与否，写入流程表，修改状态
			if(null != appNo && null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean2 = new PPDebugWFeectrlFlowBean();
				bean2.setAppNo(appNo);
				bean2.setTerminalId(terminalId);
				bean2.setMeterId(Long.valueOf(meterId));
				bean2.setFlowNoCode(FlowCodeNodeUtil.FIR_TMNL_TURN_CTRL);
				if(null == status || "".equals(status)){//无数据，表示失败
					bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_FAILED);
					bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR2);
				}else {
					if(FlowCodeNodeUtil.FRONT_STATE3.endsWith(status)){
						bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_SUCCESS);
						bean2.setErrCause(null);
					}else{
						bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_FAILED);
						if(FlowCodeNodeUtil.FRONT_STATE0.endsWith(status)){
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR0);
						}else if(FlowCodeNodeUtil.FRONT_STATE1.endsWith(status)){
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR1);
						}else{
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR2);
						}
					}
				}
			    prePaidDebugManager.updateWFeeCtrlFlowStatus(bean2);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("============合闸结束============");
		return SUCCESS;
	}
	
	/**
	 * 跳闸
	 * @return String
	 * @throws Exception 
	 */
	public String closeTurn() throws Exception {
		if(null == orgNo || null == consNo || null == tmnlAssetNo || null == protocolCode 
				|| null == turnFlag){
			return SUCCESS;
		}
		
		PSysUser user = getPSysUser();
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		
		if(!TaskDeal.isCacheRunning()){
			cacheAndTmnlStatus = "前置集群通信中断";
			return SUCCESS;
		}
		
		if(!TaskDeal.isFrontAlive()){
			cacheAndTmnlStatus = "前置集群服务中断";
			return SUCCESS;
		}
		
		logger.info("============跳闸开始============");
		try{
			//修改或插入流程表此条记录
			if(null != appNo && null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean = new PPDebugWFeectrlFlowBean();
				bean.setAppNo(appNo);
				bean.setTerminalId(terminalId);
				bean.setMeterId(Long.valueOf(meterId));
				bean.setFlowNoCode(FlowCodeNodeUtil.FIR_TMNL_TURN_CTRL);
				bean.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_DOING);
				bean.setOpTime(DateUtil.dateToSqlDate(new Date()));
			    prePaidDebugManager.saveOrUpdateWFeeCtrlFlow(bean);
			}
			
			List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
			CtrlParamBean bean = new CtrlParamBean();
			bean.setOrgNo(orgNo);
			bean.setConsNo(consNo);
			bean.setTmnlAssetNo(tmnlAssetNo);
			bean.setProtocolCode(protocolCode);
			
			tmnlList.add(bean);
			
			//跳闸，告警和限电时间都设置为0
			resultList = remoteCtrlManager.remoteCtrlClose(tmnlList, taskSecond, user, turnFlag, 
					Short.valueOf("0"), Short.valueOf("0"), getLocalIp());
			
			if(null == resultList || 0 == resultList.size()){
				status = null;
			}else{
				RemoteControlDto dto = resultList.get(0);
				if(null != dto && null != dto.getTmnlAssetNo() && tmnlAssetNo.equals(dto.getTmnlAssetNo())){
					status = dto.getStatusCode();
				}
			}
			
			//不管成功与否，写入流程表，修改状态
			if(null != appNo && null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean2 = new PPDebugWFeectrlFlowBean();
				bean2.setAppNo(appNo);
				bean2.setTerminalId(terminalId);
				bean2.setMeterId(Long.valueOf(meterId));
				bean2.setFlowNoCode(FlowCodeNodeUtil.FIR_TMNL_TURN_CTRL);
				if(null == status || "".equals(status)){//无数据，表示失败
					bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_FAILED);
					bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR2);
				}else {
					if(FlowCodeNodeUtil.FRONT_STATE3.endsWith(status)){
						bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_SUCCESS);
						bean2.setErrCause(null);
					}else{
						bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_FAILED);
						if(FlowCodeNodeUtil.FRONT_STATE0.endsWith(status)){
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR0);
						}else if(FlowCodeNodeUtil.FRONT_STATE1.endsWith(status)){
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR1);
						}else{
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR2);
						}
					}
				}
			    prePaidDebugManager.updateWFeeCtrlFlowStatus(bean2);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("============跳闸结束============");
		return SUCCESS;
	}
	
	/**
	 * 下发参数 
	 * @return String
	 * @throws Exception 
	 */
	public String sendPPDParam() throws Exception {
		//按照接口标志，orgNo，consNo可以为空，其他不能为空
		if(null==control||0!=control||null == tmnlAssetNo || null == protocolCode || null == totalNo || null == appNo
				|| null == alarmValueUnit || null == buyValueUnit || null == jumpValueUnit
				|| null == alarmValue || null == buyValue || null == jumpValue
				|| null == refreshFlag){
			success=false;
			return SUCCESS;
		}
		PSysUser user = getPSysUser();
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		
		if(!TaskDeal.isCacheRunning()){
			cacheAndTmnlStatus = "前置集群通信中断";
			return SUCCESS;
		}
		
		if(!TaskDeal.isFrontAlive()){
			cacheAndTmnlStatus = "前置集群服务中断";
			return SUCCESS;
		}
		
		logger.info("============下发参数开始============");
		try{
			//首先修改表参数
			this.prePaidDebugManager.updatePPDPara(feeCtrlId.longValue(), appNo, jumpValue, buyValue, 
					alarmValue, turnFlag, (null != totalNo ? totalNo.shortValue():null));
			
			//修改或插入流程表此条记录
			if(null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean = new PPDebugWFeectrlFlowBean();
				bean.setAppNo(appNo);
				bean.setTerminalId(terminalId);
				bean.setMeterId(Long.valueOf(meterId));
				bean.setFlowNoCode(FlowCodeNodeUtil.FIR_SEND_PREPAID_PARAM);
				bean.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_DOING);
				bean.setOpTime(DateUtil.dateToSqlDate(new Date()));
			    prePaidDebugManager.saveOrUpdateWFeeCtrlFlow(bean);
			}
			//修改主表状态为调试中
			if(null != feeCtrlId){
			    prePaidDebugManager.updateWFeeCtrlStatus(feeCtrlId.longValue(),SendStatusCodeUtil.PARAM_SEND_DOING);
			}
			
			List<PrePaidParamSetTmnlBean> tmnlList = new ArrayList<PrePaidParamSetTmnlBean>();
			PrePaidParamSetTmnlBean bean = new PrePaidParamSetTmnlBean();
			bean.setOrgNo(orgNo);
			bean.setConsNo(consNo);
			bean.setTmnlAssetNo(tmnlAssetNo);
			bean.setProtocolCode(protocolCode);
			bean.setTotalNo(totalNo.shortValue());
			
			//若单位为：MWh/元 则将参数数值*1000
			if(1 == alarmValueUnit)
			    bean.setAlarmValue(alarmValue*1000);
			else if(0 == alarmValueUnit)
				bean.setAlarmValue(alarmValue);
			if(1 == buyValueUnit)
			    bean.setBuyValue(buyValue*1000);
			else if(0 == buyValueUnit)
				 bean.setBuyValue(buyValue);
			if(1 == jumpValueUnit)
			    bean.setJumpValue(jumpValue*1000);
			else if(0 == jumpValueUnit)
			    bean.setJumpValue(jumpValue);
			
			bean.setAppNo(appNo);
			bean.setRefreshFlag(refreshFlag);
			
			tmnlList.add(bean);
			
			//调用接口
			paramResultList = prePaidManager.prePaidParamSetView(tmnlList, turnFlag, taskSecond, user, getLocalIp());
			
			//处理结果
			if(null == paramResultList || 0 == paramResultList.size()){
				status = null;
				paramResultList = new ArrayList<PrePaidExecResultBean>();
			}else{
				PrePaidExecResultBean dto = paramResultList.get(0);
				if(null != dto && null != dto.getKeyId() && (tmnlAssetNo+totalNo).equals(dto.getKeyId())){
					status = dto.getStatusCode();
				}
			}
			
			//不管成功与否，写入流程表，修改状态
			if(null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean2 = new PPDebugWFeectrlFlowBean();
				bean2.setAppNo(appNo);
				bean2.setTerminalId(terminalId);
				bean2.setMeterId(Long.valueOf(meterId));
				bean2.setFlowNoCode(FlowCodeNodeUtil.FIR_SEND_PREPAID_PARAM);
				if(null == status || "".equals(status)){//无数据，表示失败
					bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_FAILED);
					bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR2);
				}else {
					if(FlowCodeNodeUtil.FRONT_STATE3.endsWith(status)){//成功
						bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_SUCCESS);
						bean2.setErrCause(null);
					}else{//失败则写入原因
						bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_FAILED);
						if(FlowCodeNodeUtil.FRONT_STATE0.endsWith(status)){
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR0);
						}else if(FlowCodeNodeUtil.FRONT_STATE1.endsWith(status)){
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR1);
						}else{
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR2);
						}
					}
				}
			    prePaidDebugManager.updateWFeeCtrlFlowStatus(bean2);
			}
			
			//修改主表状态
			if(null != feeCtrlId){
				if(null != status && FlowCodeNodeUtil.FRONT_STATE3.endsWith(status)){
			        prePaidDebugManager.updateWFeeCtrlStatus(feeCtrlId.longValue(),SendStatusCodeUtil.PARAM_SEND_SUCCESS);
				}else{
					prePaidDebugManager.updateWFeeCtrlStatus(feeCtrlId.longValue(),SendStatusCodeUtil.PARAM_SEND_FAILED);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("============下发参数结束============");
		return SUCCESS;
	}
	
	/**
	 * 召测下发参数
	 * @return String
	 * @throws Exception 
	 */
	public String fetchPPDParam() throws Exception {
		if(null == tmnlAssetNo || null == protocolCode || null == totalNo){
			return SUCCESS;
		}
		
		PSysUser user = getPSysUser();
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		
		if(!TaskDeal.isCacheRunning()){
			cacheAndTmnlStatus = "前置集群通信中断";
			return SUCCESS;
		}
		
		if(!TaskDeal.isFrontAlive()){
			cacheAndTmnlStatus = "前置集群服务中断";
			return SUCCESS;
		}
		
		logger.info("============召测下发参数开始============");
		try{
			//修改或插入流程表此条记录
			if(null != appNo && null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean = new PPDebugWFeectrlFlowBean();
				bean.setAppNo(appNo);
				bean.setTerminalId(terminalId);
				bean.setMeterId(Long.valueOf(meterId));
				bean.setFlowNoCode(FlowCodeNodeUtil.FIR_CALL_PARAM_COMPARE);
				bean.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_DOING);
				bean.setOpTime(DateUtil.dateToSqlDate(new Date()));
			    prePaidDebugManager.saveOrUpdateWFeeCtrlFlow(bean);
			}
			
			//参数赋值
			List<PrePaidParamSetTmnlBean> tmnlList = new ArrayList<PrePaidParamSetTmnlBean>();
			PrePaidParamSetTmnlBean bean = new PrePaidParamSetTmnlBean();
			bean.setOrgNo(orgNo);
			bean.setConsNo(consNo);
			bean.setTmnlAssetNo(tmnlAssetNo);
			bean.setProtocolCode(protocolCode);
			bean.setTotalNo(totalNo.shortValue());
			
			tmnlList.add(bean);
			
			//调用接口，召测下发参数
			List<PrePaidParamSetTmnlBean> list = prePaidManager.prePaidParamFetch(tmnlList, this.taskSecond);
			
			//处理结果
			if(null == list || 0 == list.size()){
				status = null;
				ppdParamList = new ArrayList<PrePaidParamSetTmnlBean>();
			}else{
				PrePaidParamSetTmnlBean ppdParamBean = list.get(0);
				if(null != ppdParamBean && null != ppdParamBean.getKeyId() && (tmnlAssetNo+totalNo).equals(ppdParamBean.getKeyId())){
					status = FlowCodeNodeUtil.FRONT_STATE3;
					ppdParamList=list;
				}
			}
			
			//不管成功与否，写入流程表，修改状态
			if(null != appNo && null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean2 = new PPDebugWFeectrlFlowBean();
				bean2.setAppNo(appNo);
				bean2.setTerminalId(terminalId);
				bean2.setMeterId(Long.valueOf(meterId));
				bean2.setFlowNoCode(FlowCodeNodeUtil.FIR_CALL_PARAM_COMPARE);
				if(null == status || "".equals(status)){//无数据，表示失败
					bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_FAILED);
					bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR2);
				}else {
					if(FlowCodeNodeUtil.FRONT_STATE3.endsWith(status)){
						bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_SUCCESS);
						bean2.setErrCause(null);
					}else{
						bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_FAILED);
						if(FlowCodeNodeUtil.FRONT_STATE0.endsWith(status)){
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR0);
						}else if(FlowCodeNodeUtil.FRONT_STATE1.endsWith(status)){
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR1);
						}else{
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR2);
						}
					}
				}
			    prePaidDebugManager.updateWFeeCtrlFlowStatus(bean2);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("============召测下发参数结束============");
		return SUCCESS;
	}
	
	/**
	 * 下发方案 
	 * @return String
	 * @throws Exception 
	 */
	public String sendPPDCmd() throws Exception {
		if(null == tmnlAssetNo || null == protocolCode || null == totalNo){
			return SUCCESS;
		}
		
		PSysUser user = getPSysUser();
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		
		if(!TaskDeal.isCacheRunning()){
			cacheAndTmnlStatus = "前置集群通信中断";
			return SUCCESS;
		}
		
		if(!TaskDeal.isFrontAlive()){
			cacheAndTmnlStatus = "前置集群服务中断";
			return SUCCESS;
		}
		
		logger.info("============下发方案开始============");
		try{
			//修改或插入流程表此条记录
			if(null != appNo && null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean = new PPDebugWFeectrlFlowBean();
				bean.setAppNo(appNo);
				bean.setTerminalId(terminalId);
				bean.setMeterId(Long.valueOf(meterId));
				bean.setFlowNoCode(FlowCodeNodeUtil.FIR_SEND_PREPAID_SCHEME);
				bean.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_DOING);
				bean.setOpTime(DateUtil.dateToSqlDate(new Date()));
			    prePaidDebugManager.saveOrUpdateWFeeCtrlFlow(bean);
			}
			//修改主表状态为调试中
			if(null != feeCtrlId){
			    prePaidDebugManager.updateWFeeCtrlStatus(feeCtrlId.longValue(),SendStatusCodeUtil.PARAM_SEND_DOING);
			}
			
			List<PrePaidControlTmnlBean> tmnlList = new ArrayList<PrePaidControlTmnlBean>();
			PrePaidControlTmnlBean bean = new PrePaidControlTmnlBean();
			bean.setOrgNo(orgNo);
			bean.setConsNo(consNo);
			bean.setTmnlAssetNo(tmnlAssetNo);
			bean.setProtocolCode(protocolCode);
			bean.setTotalNo(totalNo.shortValue());
			
			tmnlList.add(bean);
			
			//调用接口，召测下发参数
			paramResultList = prePaidManager.prePaidControlView(tmnlList, taskSecond, user, getLocalIp());
			
			//处理结果
			if(null == paramResultList || 0 == paramResultList.size()){
				status = null;
			}else{
				PrePaidExecResultBean dto = paramResultList.get(0);
				if(null != dto && null != dto.getKeyId() && (tmnlAssetNo+totalNo).equals(dto.getKeyId())){
					status = dto.getStatusCode();
				}
			}
			
			//不管成功与否，写入流程表，修改状态
			if(null != appNo && null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean2 = new PPDebugWFeectrlFlowBean();
				bean2.setAppNo(appNo);
				bean2.setTerminalId(terminalId);
				bean2.setMeterId(Long.valueOf(meterId));
				bean2.setFlowNoCode(FlowCodeNodeUtil.FIR_SEND_PREPAID_SCHEME);
				if(null == status || "".equals(status)){//无数据，表示失败
					bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_FAILED);
					bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR2);
				}else {
					if(FlowCodeNodeUtil.FRONT_STATE3.endsWith(status)){
						bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_SUCCESS);
						bean2.setErrCause(null);
					}else{
						bean2.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_FAILED);
						if(FlowCodeNodeUtil.FRONT_STATE0.endsWith(status)){
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR0);
						}else if(FlowCodeNodeUtil.FRONT_STATE1.endsWith(status)){
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR1);
						}else{
							bean2.setErrCause(FlowCodeNodeUtil.FLOW_ERR2);
						}
					}
				}
			    prePaidDebugManager.updateWFeeCtrlFlowStatus(bean2);
			}
			
			//修改主表状态
			if(null != feeCtrlId){
				if(null != status && FlowCodeNodeUtil.FRONT_STATE3.endsWith(status)){
			        prePaidDebugManager.updateWFeeCtrlStatus(feeCtrlId.longValue(),SendStatusCodeUtil.PARAM_SEND_SUCCESS);
				}else{
					prePaidDebugManager.updateWFeeCtrlStatus(feeCtrlId.longValue(),SendStatusCodeUtil.PARAM_SEND_FAILED);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("============下发方案结束============");
		return SUCCESS;
	}
	
	/**
	 * 反馈营销调试状态  TODO
	 * @return String
	 * @throws Exception 
	 */
	public String send2Sale() throws Exception {		
		PSysUser user = getPSysUser();
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		
		logger.info("============反馈营销调试状态开始============");
		try{
			if(this.checkAll){//全选则调用后台程序
				prePaidDebugManager.findUserInfoByAll(control, appNo, DateUtil.dateToString(startDate), 
		    			DateUtil.dateToString(endDate), consNo, orgNo, tmnlAddr, debugStatus, user);
			}
			//修改或插入流程表此条记录
			if(null != appNo && null != terminalId && null != meterId){
				PPDebugWFeectrlFlowBean bean = new PPDebugWFeectrlFlowBean();
				bean.setAppNo(appNo);
				bean.setTerminalId(terminalId);
				bean.setMeterId(Long.valueOf(meterId));
				bean.setFlowNoCode(FlowCodeNodeUtil.FIR_RETURN_INTO);
				bean.setFlowNodeStatus(SendStatusCodeUtil.PARAM_SEND_DOING);
				bean.setOpTime(DateUtil.dateToSqlDate(new Date()));
			    prePaidDebugManager.saveOrUpdateWFeeCtrlFlow(bean);
			}
						
			//TODO 调用营销接口，召测下发参数
						
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("============反馈营销调试状态结束============");
		return SUCCESS;
	}
	
	/**
	 * 根据终端查询总加组列表，总加组设置调用
	 * @return String
	 * @throws Exception 
	 */
	public String queryTotalInfo() throws Exception {	
		logger.info("============根据终端查询总加组列表开始============");
		
		ppdTotalSetList = prePaidDebugManager.findTotalInfo(tmnlAssetNo);
		
		logger.info("============根据终端查询总加组列表结束============");
		return SUCCESS;
	}
	
	/**
	 * 根据终端查询总加组下属测量点列表，总加组设置调用
	 * @return String
	 * @throws Exception 
	 */
	public String queryTotalMpInfo() throws Exception {			
		logger.info("============根据终端查询总加组下属测量点列表开始============");
		
		ppdTotalMpSetList = prePaidDebugManager.findTotalMpInfo(tmnlAssetNo, totalNo, totalNoAll);
		
		logger.info("============根据终端查询总加组下属测量点列表结束============");
		return SUCCESS;
	}
	
	/**
	 * 总加组设置新建总加组
	 * @return String
	 * @throws Exception 
	 */
	public String addOrUpdateTotalInfo() throws Exception {	
		PSysUser user = getPSysUser();
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		
		if(null == mpList || 0 == mpList.size()){
			return SUCCESS;
		}
		
		logger.info("============总加组设置新建总加组开始============");
		List<PPDebugTotalSetBean> reqList = new ArrayList<PPDebugTotalSetBean>();
		for(int i = 0; i < mpList.size(); i++){
			String[] mpInfo = mpList.get(i).split("`");
			if(null == mpInfo[0] || "undefined".equals(mpInfo[0])){
				continue;
			}else{
				PPDebugTotalSetBean bean = new PPDebugTotalSetBean();
				bean.setAreaNo(areaNo);
				bean.setConsNo(consNo);
				bean.setOrgNo(orgNo);
				bean.setTmnlAssetNo(tmnlAssetNo);
				bean.setTmnlAddr(tmnlAddr);
				bean.setMeterId(null != meterId ? Long.valueOf(meterId) : null);
				bean.setCisTmnlAssetNo(cisTmnlAssetNo);
				bean.setTotalNo(totalNo.shortValue());
				bean.setCpNo(cpNo);
				bean.setMpSn(Integer.valueOf(mpInfo[0]));
				bean.setPulseAttr(mpInfo[1]);
				bean.setTotalName(totalName);
				if(null == mpInfo[2] || "undefined".equals(mpInfo[2])){
					bean.setCalcFlag(null);
				}else{
					bean.setCalcFlag(Short.valueOf(mpInfo[2]));
				}
				reqList.add(bean);
			}
		}
		operCode = prePaidDebugManager.addOrUpdateTotalInfo(reqList, newFlag);
		
		logger.info("============总加组设置新建总加组结束============");
		return SUCCESS;
	}
	
	/**
	 * 校验终端下某个总加组是否下发投入
	 * @return String
	 * @throws Exception 
	 */
	public String checkTmnlTotal() throws Exception{
		logger.info("============校验终端下某个总加组是否下发投入开始============");
		totalRecord = prePaidDebugManager.checkTmnlTotal(tmnlAssetNo, totalNo);
		logger.info("============校验终端下某个总加组是否下发投入结束============");
		return SUCCESS;
	}
	
	/**
	 * 删除终端下某个总加组信息
	 * @return String
	 * @throws Exception 
	 */
	public String dltTmnlTotal() throws Exception{
		logger.info("============删除终端下某个总加组信息开始============");
		operCode = prePaidDebugManager.dltTmnlTotal(tmnlAssetNo, totalNo);
		logger.info("============删除终端下某个总加组信息结束============");
		return SUCCESS;
	}
	
	/**
	 * 下发总加组信息到终端
	 * @return String
	 * @throws Exception 
	 */
	public String sendTotalToTmnl() throws Exception{
		if(null == mpList || 0 == mpList.size() || null == totalNo || null == tmnlAssetNo){
			return SUCCESS;
		}
		logger.info("============下发总加组信息到终端开始============");
		
		TaskDeal taskDeal=new TaskDeal();
		List<String> tmnlAssetNoList = new ArrayList<String>();
		tmnlAssetNoList.add(tmnlAssetNo);
		
		//参数项设置，编码对象集合，取规约项时，1位表示规约，2-3位表示AFN，4-5位表示FN
		List<ParamItem> paramList = new ArrayList<ParamItem>();
		ParamItem pitem = new ParamItem();
		List<Item> items = new ArrayList<Item>();
		Item item = null;
		
		pitem.setFn((short) 14);//设置FN号
		pitem.setPoint((short) 0);//设置pn号
		
		item = new Item(protocolCode+"040E001");
		item.setValue("1");//本次总加组配置数量n：数值范围1~8
		items.add(item);
		
		item = new Item(protocolCode+"040E002");
		item.setValue("1");//总加组序号：数值范围1~8
		items.add(item);
		
		item = new Item(protocolCode+"040E003");
		item.setValue(String.valueOf(mpList.size()));//总加组的测量点数量m1：数值范围0~64，如为“0”，表示本总加组没有要参与总加的测量点，即被“删除”
		items.add(item);
		
		//循环塞入测量点信息
		for (int i = 0; i < mpList.size(); i++) {
			String[] mpInfo = mpList.get(i).split("`");
			if(null == mpInfo[0] || "undefined".equals(mpInfo[0])){
				continue;
			}else{
				item = new Item(protocolCode+"040E004");
				item.setValue(mpInfo[0]);//第m个测量点号
				items.add(item);
				
				item = new Item(protocolCode+"040E005");//表示测量点的正向还是反向有功/无功功率、有功/无功电能量参与总加的标志，置“0”：正向；置“1”：反向
				if(null != mpInfo[1]){
					if("1".equals(mpInfo[1])){
						item.setValue("0");//第m个测量点参与总加的标志
					}else{
						item.setValue("1");//第m个测量点参与总加的标志
					}
				}
				items.add(item);
				
				item = new Item(protocolCode+"040E005");//表示参与总加运算的运算符标志；置“0”：“加”运算；置“1”：“减”运算
				if(null != mpInfo[2]){
					if("0".equals(mpInfo[2])){
						item.setValue("0");//第m个测量点参与总加运算符标志
					}else{
						item.setValue("1");//第m个测量点参与总加运算符标志
					}
				}
				items.add(item);
			}
		}
		pitem.setItems((ArrayList<Item>) items);
		paramList.add(pitem);
		
		taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
		
		//获取结果
		List<Response> paramResultList = taskDeal.getAllResponse(taskSecond);
		
		//处理结果
		if(null == paramResultList || 0 == paramResultList.size()){
			status = null;
		}else{
			for (int i = 0; i < paramResultList.size(); i++) {
				Response res = (Response) paramResultList.get(i);
				if(tmnlAssetNo.equals(res.getTmnlAssetNo())){
					status = String.valueOf(res.getTaskStatus());
					break;
				}
			}
		}
		
		logger.info("============下发总加组信息到终端结束============");
		return SUCCESS;
	}
	
	/**
	 * 获取本机ip
	 * @return String 本地ip
	 * @throws Exception
	 */
    private String getLocalIp() throws Exception{
    	InetAddress addr = InetAddress.getLocalHost();
	    String ipAddr=addr.getHostAddress().toString();//获得本机IP
	    return ipAddr;
    }
	
	public void setPrePaidDebugManager(PrePaidDebugManager prePaidDebugManager) {
		this.prePaidDebugManager = prePaidDebugManager;
	}


	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		PrePaidDebugAction.logger = logger;
	}

	public List<PPDebugUserInfoBean> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<PPDebugUserInfoBean> userInfoList) {
		this.userInfoList = userInfoList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public Integer getControl() {
		return control;
	}

	public void setControl(Integer control) {
		this.control = control;
	}

	public String getCacheAndTmnlStatus() {
		return cacheAndTmnlStatus;
	}

	public void setCacheAndTmnlStatus(String cacheAndTmnlStatus) {
		this.cacheAndTmnlStatus = cacheAndTmnlStatus;
	}
	
	public Integer getTaskSecond() {
		return taskSecond;
	}

	public void setTaskSecond(Integer taskSecond) {
		this.taskSecond = taskSecond;
	}

	public List<String> getTerminalList() {
		return terminalList;
	}

	public void setTerminalList(List<String> terminalList) {
		this.terminalList = terminalList;
	}

	public List<PPDebugCallDataBean> getReadList() {
		return readList;
	}

	public void setReadList(List<PPDebugCallDataBean> readList) {
		this.readList = readList;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public List<PPDebugOperaBean> getPpDeugOpList() {
		return ppDeugOpList;
	}

	public void setPpDeugOpList(List<PPDebugOperaBean> ppDeugOpList) {
		this.ppDeugOpList = ppDeugOpList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<String> getPrepaidDebugList() {
		return prepaidDebugList;
	}

	public void setPrepaidDebugList(List<String> prepaidDebugList) {
		this.prepaidDebugList = prepaidDebugList;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getTmnlAddr() {
		return tmnlAddr;
	}

	public void setTmnlAddr(String tmnlAddr) {
		this.tmnlAddr = tmnlAddr;
	}

	public String getDebugStatus() {
		return debugStatus;
	}

	public void setDebugStatus(String debugStatus) {
		this.debugStatus = debugStatus;
	}

	public List<TreeNodeChecked> getTreeNodeList() {
		return treeNodeList;
	}

	public void setTreeNodeList(List<TreeNodeChecked> treeNodeList) {
		this.treeNodeList = treeNodeList;
	}

	public BigDecimal getFeeCtrlId() {
		return feeCtrlId;
	}

	public void setFeeCtrlId(BigDecimal feeCtrlId) {
		this.feeCtrlId = feeCtrlId;
	}

	public PPDebugUserInfoBean getPpdParamInfoBean() {
		return ppdParamInfoBean;
	}

	public void setPpdParamInfoBean(PPDebugUserInfoBean ppdParamInfoBean) {
		this.ppdParamInfoBean = ppdParamInfoBean;
	}

	public Integer getJumpValue() {
		return jumpValue;
	}

	public void setJumpValue(Integer jumpValue) {
		this.jumpValue = jumpValue;
	}

	public Integer getAlarmValue() {
		return alarmValue;
	}

	public void setAlarmValue(Integer alarmValue) {
		this.alarmValue = alarmValue;
	}

	public Integer getBuyValue() {
		return buyValue;
	}

	public void setBuyValue(Integer buyValue) {
		this.buyValue = buyValue;
	}

	public String getTurnFlag() {
		return turnFlag;
	}

	public void setTurnFlag(String turnFlag) {
		this.turnFlag = turnFlag;
	}

	public PPDebugTmnlInfoBean getPpdTmnlInfoBean() {
		return ppdTmnlInfoBean;
	}

	public void setPpdTmnlInfoBean(PPDebugTmnlInfoBean ppdTmnlInfoBean) {
		this.ppdTmnlInfoBean = ppdTmnlInfoBean;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public List<PPDebugUserStatusInfoBean> getPpdUserStatusInfoList() {
		return ppdUserStatusInfoList;
	}

	public void setPpdUserStatusInfoList(
			List<PPDebugUserStatusInfoBean> ppdUserStatusInfoList) {
		this.ppdUserStatusInfoList = ppdUserStatusInfoList;
	}

	public List<EDataTotal> getPpdParamTotalList() {
		return ppdParamTotalList;
	}

	public void setPpdParamTotalList(List<EDataTotal> ppdParamTotalList) {
		this.ppdParamTotalList = ppdParamTotalList;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public Integer getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(Integer totalNo) {
		this.totalNo = totalNo;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public void setRemoteCtrlManager(RemoteCtrlManager remoteCtrlManager) {
		this.remoteCtrlManager = remoteCtrlManager;
	}

	public List<RmtCtrlSwitchDto> getSwitchList() {
		return switchList;
	}

	public void setSwitchList(List<RmtCtrlSwitchDto> switchList) {
		this.switchList = switchList;
	}

	public List<RemoteControlDto> getResultList() {
		return resultList;
	}

	public void setResultList(List<RemoteControlDto> resultList) {
		this.resultList = resultList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public void setPrePaidManager(PrePaidManager prePaidManager) {
		this.prePaidManager = prePaidManager;
	}

	public List<PrePaidExecResultBean> getParamResultList() {
		return paramResultList;
	}

	public void setParamResultList(List<PrePaidExecResultBean> paramResultList) {
		this.paramResultList = paramResultList;
	}

	public String getRefreshFlag() {
		return refreshFlag;
	}

	public void setRefreshFlag(String refreshFlag) {
		this.refreshFlag = refreshFlag;
	}

	public Byte getAlarmValueUnit() {
		return alarmValueUnit;
	}

	public void setAlarmValueUnit(Byte alarmValueUnit) {
		this.alarmValueUnit = alarmValueUnit;
	}

	public Byte getBuyValueUnit() {
		return buyValueUnit;
	}

	public void setBuyValueUnit(Byte buyValueUnit) {
		this.buyValueUnit = buyValueUnit;
	}

	public Byte getJumpValueUnit() {
		return jumpValueUnit;
	}

	public void setJumpValueUnit(Byte jumpValueUnit) {
		this.jumpValueUnit = jumpValueUnit;
	}

	public String getRealFns() {
		return realFns;
	}

	public void setRealFns(String realFns) {
		this.realFns = realFns;
	}

	public String getStatFns() {
		return statFns;
	}

	public void setStatFns(String statFns) {
		this.statFns = statFns;
	}

	public void setDataFetchManager(DataFetchManager dataFetchManager) {
		this.dataFetchManager = dataFetchManager;
	}

	public List<RmtCtrlSwitchDto> getTurnStatusList() {
		return turnStatusList;
	}

	public void setTurnStatusList(List<RmtCtrlSwitchDto> turnStatusList) {
		this.turnStatusList = turnStatusList;
	}

	public List<PrePaidParamSetTmnlBean> getPpdParamList() {
		return ppdParamList;
	}

	public void setPpdParamList(List<PrePaidParamSetTmnlBean> ppdParamList) {
		this.ppdParamList = ppdParamList;
	}

	public Boolean getCheckAll() {
		return checkAll;
	}

	public void setCheckAll(Boolean checkAll) {
		this.checkAll = checkAll;
	}

	public List<PPDebugTotalBean> getPpdTotalSetList() {
		return ppdTotalSetList;
	}

	public void setPpdTotalSetList(List<PPDebugTotalBean> ppdTotalSetList) {
		this.ppdTotalSetList = ppdTotalSetList;
	}

	public List<PPDebugTotalMpBean> getPpdTotalMpSetList() {
		return ppdTotalMpSetList;
	}

	public void setPpdTotalMpSetList(List<PPDebugTotalMpBean> ppdTotalMpSetList) {
		this.ppdTotalMpSetList = ppdTotalMpSetList;
	}

	public Boolean getTotalNoAll() {
		return totalNoAll;
	}

	public void setTotalNoAll(Boolean totalNoAll) {
		this.totalNoAll = totalNoAll;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getCisTmnlAssetNo() {
		return cisTmnlAssetNo;
	}

	public void setCisTmnlAssetNo(String cisTmnlAssetNo) {
		this.cisTmnlAssetNo = cisTmnlAssetNo;
	}

	public List<String> getMpList() {
		return mpList;
	}

	public void setMpList(List<String> mpList) {
		this.mpList = mpList;
	}

	public String getCpNo() {
		return cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

	public Boolean getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(Boolean newFlag) {
		this.newFlag = newFlag;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getOperCode() {
		return operCode;
	}

	public void setOperCode(int operCode) {
		this.operCode = operCode;
	}

	public String getTotalName() {
		return totalName;
	}

	public void setTotalName(String totalName) {
		this.totalName = totalName;
	}
}
