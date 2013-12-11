package com.nari.baseapp.planpowerconsume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.basicdata.VwLimitType;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.ResponseItem;
import com.nari.logofsys.LOpTmnlLog;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.EnergyControlDto;
import com.nari.orderlypower.TmnlExecStatusBean;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlCtrlStatus;
import com.nari.orderlypower.WTmnlMonPctrl;
import com.nari.privilige.PSysUser;
import com.nari.util.CtrlSchemeTypeCode;
/**
 * 月电量定值控Action处理类
 * @author 姜海辉
 */
public class EnergyControlAction extends BaseAction{
	//日志对象
	private static Logger logger = Logger.getLogger(EnergyControlAction.class);
	
	//自动注入月电量定值控业务层 
	private EnergyCtrlManager energyCtrlManager;
	
	public void setEnergyCtrlManager(EnergyCtrlManager energyCtrlManager) {
		this.energyCtrlManager = energyCtrlManager;
	}

	//自动注入日志处理业务层 
	private ILOpTmnlLogManager iLOpTmnlLogManager;
		
	//private Long powerConst;     //月电量定值控
	//private Short floatValue;  //浮动系数
	private String tmnlAssetNo;  //终端资产号
	private String orgNo;        //供电单位编号
	private String consNo;       //用户编号
	//private String totalNo;     //总加组号
	//private String isSendSms;    //是否发送短信
	
	private Long schemeId;    //方案id
	private Date newStartDate;  //新添加方案开始时间
	private Date newEndDate;    //新添加结束时间
	private String limitType;   //限电类型
	private String ctrlSchemeName;  //方案名
	private String nodeType;    //节点类型
	private Integer FLAG;        //任务返回状态
	private String operType;//方案操作类型
	private Boolean checkSchemeName;//方案名校验结果，true表示存在，false表示不存在
	private String groupNo;//组号
	private String subsId;//变电站标识
	private String lineId;//线路编号
	private String orgType;//供电单位类型
	private Double ctrlLoad; //调控负荷
	private String schemeRemark;//方案备注
	private Integer taskSecond=30;            //任务执行时间
	private List<String> energyCtrlList;  //月电控终端总加组信息
	private List<WCtrlScheme> schemeList;//方案列表
	private List<VwLimitType> limitTypeList;//限电类型列表
	private List<EnergyControlDto> userGridList;//Grid数据列表
	private List<EnergyControlDto> resultList;//执行命令后的返回结果
	private List<TmnlExecStatusBean> tmnlExecStatusList;//终端执行状态列表
	private String cacheAndTmnlStatus;
	//返回值
	public boolean success = true;
	private WtmnlCtrlStatusManger wtmnlCtrlStatusManger;
	public void setWtmnlCtrlStatusManger(WtmnlCtrlStatusManger wtmnlCtrlStatusManger) {
		this.wtmnlCtrlStatusManger = wtmnlCtrlStatusManger;
	}
	//自动注入控制方案业务层
	private IWCtrlSchemeManager iWCtrlSchemeManager;
	public void setiWCtrlSchemeManager(IWCtrlSchemeManager iWCtrlSchemeManager) {
		this.iWCtrlSchemeManager = iWCtrlSchemeManager;
	}
	
	//自动注入营业报停控业务层 
	private SuspensionCtrlManager suspensionCtrlManager;
	public void setSuspensionCtrlManager(SuspensionCtrlManager suspensionCtrlManager) {
		this.suspensionCtrlManager = suspensionCtrlManager;
	}

	/**
	 * 月电控参数下发
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	public String sendout() throws Exception{
		logger.debug("月电量定值控下发开始");
		try{
			TaskDeal taskDeal=new TaskDeal();
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			List<EnergyControlDto> tmnlTotalList=parseSendTmnlTotalList();
			if(null==tmnlTotalList||0==tmnlTotalList.size()){
				this.success=false;
				return SUCCESS;
			}
			//操作终端时间
			Date OpTime=new Date();
			EnergyControlDto flagBean = tmnlTotalList.get(0);
			//终端资产号集合
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(flagBean.getTmnlAssetNo());//加入终端资产编号列表
			//参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			ParamItem pitem = null;
			if(null!=flagBean.getFloatValue()){
				pitem = new ParamItem();
				pitem =setFloatItem(flagBean);
				paramList.add(pitem);
			}
			pitem = new ParamItem();
			pitem = setPowerConstItem(flagBean);
			paramList.add(pitem);
			if(1==tmnlTotalList.size()){
				//下发
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
			}
			for(int i = 1; i < tmnlTotalList.size(); i++){
				EnergyControlDto bean = tmnlTotalList.get(i);
				//参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
					//下发
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					//一个终端组一个包，遇到终端不同的要新建paramList对象
					paramList = new ArrayList<ParamItem>();
					if(null!=bean.getFloatValue()){
						pitem= new ParamItem();
						pitem = setFloatItem(bean);
						paramList.add(pitem);
					}
					flagBean = bean;
				}
				pitem = new ParamItem();
				pitem = setPowerConstItem(bean);
				paramList.add(pitem);
				if(tmnlTotalList.size()-1==i) {
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
				}
			}
			//等待下发结果
			List<Response> respList = taskDeal.getAllResponse(this.taskSecond);	

			//下发结果
			this.tmnlExecStatusList = new ArrayList<TmnlExecStatusBean>();
			if(null != respList && 0 < respList.size()){
				Response res = null;
				LOpTmnlLog log =null;
				for(int i=0;i < respList.size();i++){
					res = (Response) respList.get(i);
					String tAssetNo=res.getTmnlAssetNo();
					//终端返回状态
					short Status = res.getTaskStatus();
					log = getLOpTmnlLog();
					log.setOpModule("月电量定值控");
					log.setOpButton("参数下发");
					log.setOpType((short)1);
					log.setOpObj((short)4);
					log.setTmnlAssetNo(tAssetNo);			
					log.setOpTime(OpTime);
					log.setCurStatus(String.valueOf(Status));
					if(Status==3||Status==4){
						EnergyControlDto ecTmnl= getTmnlFromList(tmnlTotalList,tAssetNo);
						if(null!=ecTmnl&&null!=ecTmnl.getFloatValue()){
							// 写日志（下浮系数）
							LOpTmnlLog logP = getLOpTmnlLog();
							logP.setOpModule("月电量定值控");
							logP.setOpButton("参数下发");
							logP.setOpType((short)1);
							logP.setOpObj((short)1);
							logP.setTmnlAssetNo(tAssetNo);
							logP.setProtItemNo(ecTmnl.getProtocolCode()+"0414001");
							logP.setCurValue(String.valueOf(ecTmnl.getFloatValue()));
							logP.setOpTime(OpTime);
							logP.setCurStatus(String.valueOf(Status));
							this.iLOpTmnlLogManager.saveTmnlLog(logP);
						}
						for(int j = 0; j < res.getResponseItems().size(); j++) {
							ResponseItem resItem = res.getResponseItems().get(j);
						    short toNo=resItem.getPn();
						    EnergyControlDto ecTmnlTotal=getTmnlTotalFromList(tmnlTotalList,tAssetNo,toNo);
						    if(null!=ecTmnlTotal){
							    log.setOpObjNo((long)toNo);
							    log.setProtItemNo(ecTmnl.getProtocolCode()+"042E001");
							    log.setCurValue(String.valueOf(ecTmnlTotal.getPowerConst()));	
								this.iLOpTmnlLogManager.saveTmnlLog(log);
						    }
							//返回执行状态
							TmnlExecStatusBean tmnlexecStatus=new TmnlExecStatusBean();
							tmnlexecStatus.setKeyId(tAssetNo+ String.valueOf(toNo));
							if (Status == 3) {
								tmnlexecStatus.setExecFlag("1");	
							} 
							else{
								tmnlexecStatus.setExecFlag("0");
							}
							this.tmnlExecStatusList.add(tmnlexecStatus);
						}
					}
					else{
						//对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
						List<EnergyControlDto> noResponseList = noResponseSendTmnl(tmnlTotalList, res.getTmnlAssetNo());
						if(null==noResponseList) continue;
						if(null!=noResponseList.get(0)&&null!=noResponseList.get(0).getFloatValue()){
							// 写日志（下浮系数）
							LOpTmnlLog logP = getLOpTmnlLog();
							logP.setOpModule("月电量定值控");
							logP.setOpButton("参数下发");
							logP.setOpType((short)1);
							logP.setOpObj((short)1);
							logP.setTmnlAssetNo(tAssetNo);
							logP.setProtItemNo(noResponseList.get(0).getProtocolCode()+"0414001");
							logP.setCurValue(String.valueOf(noResponseList.get(0).getFloatValue()));
							logP.setOpTime(OpTime);
							logP.setCurStatus(String.valueOf(Status));
							this.iLOpTmnlLogManager.saveTmnlLog(logP);
						}
						for (int j = 0; j < noResponseList.size(); j++) {
							EnergyControlDto bean = noResponseList.get(j);
							//对未返回的终端要记录日志
							log.setOpObjNo((long)bean.getTotalNo());
							log.setCurValue(String.valueOf(bean.getPowerConst()));	
							log.setProtItemNo(bean.getProtocolCode()+"042E001");
							this.iLOpTmnlLogManager.saveTmnlLog(log);
						
							//返回执行状态
							this.tmnlExecStatusList.add(new TmnlExecStatusBean(bean.getTmnlAssetNo()
									+ String.valueOf(bean.getTotalNo()), "0"));
						}	
					}
				}
			}
			else{
				for(int i = 0; i < tmnlTotalList.size(); i++){
					TmnlExecStatusBean tmnlexecStatus=new TmnlExecStatusBean();
					tmnlexecStatus.setKeyId(tmnlTotalList.get(i).getTmnlAssetNo()+ String.valueOf(tmnlTotalList.get(i).getTotalNo()));
					tmnlexecStatus.setExecFlag("0");
					this.tmnlExecStatusList.add(tmnlexecStatus);
				}
			}
			logger.debug("月电量定值控下发结束");
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public String fetch() throws Exception{
		logger.debug("月电量定值控召测开始");
		try{
			TaskDeal taskDeal=new TaskDeal();
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			List<CtrlParamBean> tmnlTotalList=parseFetchTmnlTotalList();
			if(null==tmnlTotalList||0==tmnlTotalList.size()){
				this.success=false;
				return SUCCESS;
			}
			CtrlParamBean flagBean=tmnlTotalList.get(0);
			//终端资产号集合
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(flagBean.getTmnlAssetNo());//加入终端资产编号列表
			//参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			ParamItem pitem = new ParamItem();
			pitem.setFn((short)20);//设置FN号
			pitem.setPoint((short) 0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
			pitem.setItems(null);
			paramList.add(pitem);	
			pitem = new ParamItem();
			pitem.setFn((short)46);
			pitem.setPoint(flagBean.getTotalNo());
			pitem.setItems(null);
			paramList.add(pitem);
			if(1==tmnlTotalList.size()){
				//召测
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.QUERY_PARAMS, paramList);
			}
			for(int i = 1; i < tmnlTotalList.size(); i++){
				CtrlParamBean bean = tmnlTotalList.get(i);
				//参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
					//召测
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.QUERY_PARAMS, paramList);
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					//一个终端组一个包，遇到终端不同的要新建paramList对象
					paramList = new ArrayList<ParamItem>();
					pitem = new ParamItem();
					pitem.setFn((short)20);//设置FN号
					pitem.setPoint((short) 0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
					pitem.setItems(null);
					paramList.add(pitem);
					flagBean = bean;
				}
				pitem = new ParamItem();
				pitem.setFn((short)46);
				pitem.setPoint(bean.getTotalNo());
				pitem.setItems(null);
				paramList.add(pitem);
				if(tmnlTotalList.size()-1==i) {
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.QUERY_PARAMS, paramList);
				}
			}
			List<Response> respList = taskDeal.getAllResponse(this.taskSecond);
			this.resultList = new ArrayList<EnergyControlDto>();
		    if(null != respList && 0 < respList.size()){
		    	Response res = null;
		    	for(int i=0;i<respList.size();i++){
		    		res = (Response)respList.get(i);
					if (res.getTaskStatus()==3 && null != res.getResponseItems()&& 0 < res.getResponseItems().size()){
						Short fValue=null;
						for(int j = 0; j < res.getResponseItems().size(); j++){
							ResponseItem resItem = res.getResponseItems().get(j);
							if(resItem.getCode().endsWith("0414001")){
								if(null!=resItem.getValue()&&!("").equals(resItem.getValue())){
									fValue=Short.valueOf(resItem.getValue());
								}
							    break;
							}
						}
						for(int k = 0; k< res.getResponseItems().size(); k++){
							ResponseItem resItem = res.getResponseItems().get(k);
							if(resItem.getCode().endsWith("042E001")){
								 EnergyControlDto ec=new EnergyControlDto();
								 ec.setKeyId(res.getTmnlAssetNo()+ String.valueOf(resItem.getPn()));
								 ec.setPowerConst(Long.valueOf(resItem.getValue()));
								 ec.setFloatValue(fValue);
								 this.resultList.add(ec);
							}
						}
					}
		    	}
		    }
		    logger.debug("月电量定值控召测结束");
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}

	/**
	 * 月电控执行
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	public String act() throws Exception{
		logger.debug("月电量定值控执行开始");
		try{			
			TaskDeal taskDeal=new TaskDeal();
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			List<CtrlParamBean> tmnlTotalList=parseExeRelTmnlTotalList();
			if(null==tmnlTotalList||0==tmnlTotalList.size()||null==this.schemeId){
				this.success=false;
				return SUCCESS;
			}
			//操作终端时间
			Date opTime=new Date();
			CtrlParamBean flagBean = tmnlTotalList.get(0);
			//终端资产号集合
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(flagBean.getTmnlAssetNo());//加入终端资产编号列表
			//参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
	    	ParamItem pitem = new ParamItem();
			pitem.setFn((short) 15);//设置FN号
			pitem.setPoint(flagBean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
			pitem.setItems(null);
			paramList.add(pitem);
			if(1==tmnlTotalList.size()){
				//投入
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
			}
			for(int i = 1; i < tmnlTotalList.size(); i++){
				CtrlParamBean bean = tmnlTotalList.get(i);
				//参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
					//投入
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					//一个终端组一个包，遇到终端不同的要新建paramList对象
					paramList = new ArrayList<ParamItem>();
					flagBean = bean;
				}
				pitem = new ParamItem();
				pitem.setFn((short) 15);//设置FN号
				pitem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				pitem.setItems(null);
				paramList.add(pitem);
				if(tmnlTotalList.size()-1==i) {
					//投入
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
				}
			}
			List<Response> respList = taskDeal.getAllResponse(this.taskSecond);	
			Date successTime =new Date();
			//返回结果
			this.tmnlExecStatusList = new ArrayList<TmnlExecStatusBean>();		
			if(null != respList && 0 < respList.size()){
				Response res=null;
				LOpTmnlLog log=null;
				for(int i=0;i < respList.size();i++){
					res = (Response) respList.get(i);
					//终端返回状态
					short Status = res.getTaskStatus();
					String tAssetNo=res.getTmnlAssetNo();
					
					//写日志
					log = getLOpTmnlLog();
				    log.setOpModule("月电量定值控");
				    log.setOpButton("月电控执行");
					log.setOpType((short)2);
					log.setOpObj((short)4);
					log.setTmnlAssetNo(tAssetNo);
					log.setOpTime(opTime);
					log.setCurStatus(String.valueOf(Status));
					if (Status==3||Status==4){
						String tProtocolCode=getProtocolCodeByTmnlAssetNo(tmnlTotalList,tAssetNo);
						for(int j = 0; j < res.getResponseItems().size(); j++) {
							short toNo=res.getResponseItems().get(j).getPn();
							log.setOpObjNo((long)toNo);
							log.setProtocolNo(tProtocolCode+"050F");
							this.iLOpTmnlLogManager.saveTmnlLog(log);
							//写方案明细
							WTmnlMonPctrl wTmnlMonPctrl=new WTmnlMonPctrl();
		    	    		wTmnlMonPctrl.setTmnlAssetNo(tAssetNo);
		    	    		wTmnlMonPctrl.setTotalNo(toNo);
		    	    		wTmnlMonPctrl.setCtrlSchemeId(this.schemeId);
		    	    		wTmnlMonPctrl.setCtrlFlag(true);
		    	    		wTmnlMonPctrl.setSendTime(opTime);
		    	    		
							TmnlExecStatusBean tmnlexecStatus=new TmnlExecStatusBean();
							tmnlexecStatus.setKeyId(tAssetNo+ String.valueOf(toNo));
							if(Status == 3){
								tmnlexecStatus.setExecFlag("1");
								//对月电量定值控制状态属性赋值
			    	    		WTmnlCtrlStatus	ctrlStatus =new  WTmnlCtrlStatus();
			    	    		ctrlStatus.setTmnlAssetNo(tAssetNo);
			    	    		ctrlStatus.setTotalNo(toNo);
			    	    		ctrlStatus.setMonPctrlFlag((short)1);
			    	    		this.wtmnlCtrlStatusManger.saveOrUpdateTmnlCtrlStatusMonPctrlFlag(ctrlStatus);
			    	    		wTmnlMonPctrl.setSuccessTime(successTime);
							}
						    else{
						    	tmnlexecStatus.setExecFlag("0");
						    	wTmnlMonPctrl.setFailureCode(String.valueOf(Status));
						    }
							this.tmnlExecStatusList.add(tmnlexecStatus);
							this.energyCtrlManager.updateSchemeDetail(wTmnlMonPctrl, Status);
						}
					}
					else{
						//对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
						List<CtrlParamBean> noResponseList = noResponseTmnl(tmnlTotalList, res.getTmnlAssetNo());
						if(null==noResponseList) continue;
						for (int j = 0; j < noResponseList.size(); j++) {
							CtrlParamBean bean = noResponseList.get(j);
							log.setOpObjNo((long)bean.getTotalNo());
							log.setProtocolNo(bean.getProtocolCode()+"050F");
							this.iLOpTmnlLogManager.saveTmnlLog(log);
							//返回执行状态
							this.tmnlExecStatusList.add(new TmnlExecStatusBean(bean.getTmnlAssetNo()
									+ String.valueOf(bean.getTotalNo()), "0"));
						}
					}
				}
			}
			else{
				for(int i=0;i<tmnlTotalList.size();i++){
					TmnlExecStatusBean tmnlexecStatus=new TmnlExecStatusBean();
					tmnlexecStatus.setKeyId(tmnlTotalList.get(i).getTmnlAssetNo()+ String.valueOf(tmnlTotalList.get(i).getTotalNo()));
					tmnlexecStatus.setExecFlag("0");
					this.tmnlExecStatusList.add(tmnlexecStatus);
				}
			}
			logger.debug("月电量定值控执行结束");
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}
	
	/**
	 * 月电控解除
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
    public String release() throws Exception{
    	logger.debug("月电量定值控解除开始");
		try{
			TaskDeal taskDeal=new TaskDeal();
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			List<CtrlParamBean> tmnlTotalList=parseExeRelTmnlTotalList();
	    	if(null==tmnlTotalList||0==tmnlTotalList.size()||null==this.schemeId){
	    		this.success=false;
				return SUCCESS;
	    	}
	    	//操作终端时间
			Date opTime=new Date();
			CtrlParamBean flagBean = tmnlTotalList.get(0);
			//终端资产号集合
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(flagBean.getTmnlAssetNo());//加入终端资产编号列表
			
			//参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
	    	ParamItem pitem = new ParamItem();
			pitem.setFn((short) 23);//设置FN号
			pitem.setPoint(flagBean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
			pitem.setItems(null);
			paramList.add(pitem);
			if(1==tmnlTotalList.size()){
				//解除
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
			}
			for(int i = 1; i < tmnlTotalList.size(); i++){
				CtrlParamBean bean = tmnlTotalList.get(i);
				//参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
					//解除
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					//一个终端组一个包，遇到终端不同的要新建paramList对象
					paramList = new ArrayList<ParamItem>();
					flagBean = bean;
				}
				pitem = new ParamItem();
				pitem.setFn((short) 23);//设置FN号
				pitem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				pitem.setItems(null);
				paramList.add(pitem);
				if(tmnlTotalList.size()-1==i) {
					//解除
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
				}
			}
			List<Response> respList = taskDeal.getAllResponse(this.taskSecond);	
			Date successTime =new Date();
			//返回结果
			this.tmnlExecStatusList = new ArrayList<TmnlExecStatusBean>();		
			if(null != respList && 0 < respList.size()){
				Response res=null;
				LOpTmnlLog log=null; 
				for(int i=0;i < respList.size();i++){
					res = (Response) respList.get(i);
					String tAssetNo=res.getTmnlAssetNo();
				
					//终端返回状态
					short Status = res.getTaskStatus();
					//写日志
					log = getLOpTmnlLog();
					log.setOpModule("月电量定值控");
					log.setOpButton("月电控解除");
					log.setOpType((short)2);
					log.setOpObj((short)4);
					log.setTmnlAssetNo(tAssetNo);
					log.setOpTime(opTime);
					log.setCurStatus(String.valueOf(Status));
					if (Status==3||Status==4){
						String tProtocolCode=getProtocolCodeByTmnlAssetNo(tmnlTotalList,tAssetNo);
						for(int j = 0; j < res.getResponseItems().size(); j++) {
							short toNo=res.getResponseItems().get(j).getPn();
							log.setOpObjNo((long)toNo);
							log.setProtocolNo(tProtocolCode+"0517");
							this.iLOpTmnlLogManager.saveTmnlLog(log);
							
							//写方案明细
							WTmnlMonPctrl wTmnlMonPctrl=new WTmnlMonPctrl();
		    	    		wTmnlMonPctrl.setTmnlAssetNo(tAssetNo);
		    	    		wTmnlMonPctrl.setTotalNo(toNo);
		    	    		wTmnlMonPctrl.setCtrlSchemeId(this.schemeId);
		    	    		wTmnlMonPctrl.setCtrlFlag(false);
		    	    		wTmnlMonPctrl.setSendTime(opTime);
		    	    		
							TmnlExecStatusBean tmnlexecStatus=new TmnlExecStatusBean();
							tmnlexecStatus.setKeyId(tAssetNo+ String.valueOf(toNo));
							if(Status == 3){
								tmnlexecStatus.setExecFlag("1");
								//对月电量定值控制状态属性赋值
			    	    		WTmnlCtrlStatus	ctrlStatus = new WTmnlCtrlStatus();
			    	    		ctrlStatus.setTmnlAssetNo(tAssetNo);
			    	    		ctrlStatus.setTotalNo(toNo);
			    	    		ctrlStatus.setMonPctrlFlag((short)0);
			    	    		this.wtmnlCtrlStatusManger.saveOrUpdateTmnlCtrlStatusMonPctrlFlag(ctrlStatus);
			    	    		wTmnlMonPctrl.setSuccessTime(successTime);
							}
						    else{
						    	tmnlexecStatus.setExecFlag("0");
						    	wTmnlMonPctrl.setFailureCode(String.valueOf(Status));
						    }
							this.tmnlExecStatusList.add(tmnlexecStatus);
							this.energyCtrlManager.updateSchemeDetail(wTmnlMonPctrl, Status);
						}
					}
					else{
						//对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
						List<CtrlParamBean> noResponseList = noResponseTmnl(tmnlTotalList, res.getTmnlAssetNo());
						if(null==noResponseList) continue;
						for (int j = 0; j < noResponseList.size(); j++) {
							CtrlParamBean bean = noResponseList.get(j);
							log.setOpObjNo((long)bean.getTotalNo());
							log.setProtocolNo(bean.getProtocolCode()+"0517");
							this.iLOpTmnlLogManager.saveTmnlLog(log);
							//返回执行状态
							this.tmnlExecStatusList.add(new TmnlExecStatusBean(bean.getTmnlAssetNo()
									+ String.valueOf(bean.getTotalNo()), "0"));
						}
					}
				}
			}
			else{
				for(int i=0;i<tmnlTotalList.size();i++){
					TmnlExecStatusBean tmnlexecStatus=new TmnlExecStatusBean();
					tmnlexecStatus.setKeyId(tmnlTotalList.get(i).getTmnlAssetNo()+ String.valueOf(tmnlTotalList.get(i).getTotalNo()));
					tmnlexecStatus.setExecFlag("0");
					this.tmnlExecStatusList.add(tmnlexecStatus);
				}
			}
			logger.debug("月电量定值控解除结束");
	    	return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
    
    /**
	 * 加载Grid数据
	 * @return String 
	 * @throws Exception
	 */
    public String loadGridData() throws Exception{
    	logger.debug("月电量定值控加载用户信息开始");
    	if(null == nodeType || "".equals(nodeType)){
    		return SUCCESS;
    	}
    	try{
	    	PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
	    	if("org".equals(nodeType)){
	    		this.userGridList = this.energyCtrlManager.queryEnergyCtrlGridByOrgNo(this.orgNo,this.orgType,pSysUser);
	    	}
	    	else if("usr".equals(nodeType)){
	    		this.userGridList = this.energyCtrlManager.queryEnergyCtrlGridByTmnlAssetNo(this.tmnlAssetNo);
	    	}
	        else if("line".equals(nodeType)){
	        	this.userGridList = this.energyCtrlManager.queryEnergyCtrlGridByLineId(this.lineId,pSysUser);
	    	}
	        else if("cgp".equals(nodeType) || "ugp".equals(nodeType)){
	        	this.userGridList = this.energyCtrlManager.queryEnergyCtrlGridByGroupNo(this.nodeType,this.groupNo);
	    	}
	        else if("sub".equals(nodeType)){
	        	this.userGridList = this.energyCtrlManager.queryEnergyCtrlGridBySubsId(this.subsId,pSysUser);
	    	}
	        else if("schemeId".equals(nodeType)){
		        this.userGridList = this.energyCtrlManager.queryEnergyCtrlGridBySchemeId(this.schemeId);
	        }
	    	logger.debug("月电量定值控加载用户信息结束");
	    	return SUCCESS;
    	}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    
    /**
	 * 加载方案，在点击方案加载之后的事件处理，主要是把方案显示在页面上，供选择
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
    public String loadScheme()throws Exception{
    	logger.debug("月电量定值控加载方案开始");
    	try{
			PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
			if(null==pSysUser)
				return SUCCESS;
			String staffNo = pSysUser.getStaffNo();
			this.schemeList = this.iWCtrlSchemeManager.querySchemeListByType(staffNo,CtrlSchemeTypeCode.ENERGY_CTRL);
			logger.debug("月电量定值控加载方案结束");
			return SUCCESS;
    	}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    
    
   /* *//**
	 * 新增加方案，保存为方案弹出界面，点击确定时的事件处理
	 * @return Struts 跳转字符串
	 * @throws Exception
	 *//*
    public String saveEnergyScheme() throws Exception{
    	logger.debug("月电量定值控保存方案开始");
	    try{	
			PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
			if(null==pSysUser){
				this.success=false;
				return SUCCESS;
			}
			String staffNo = pSysUser.getStaffNo();
			String userOrgNo=pSysUser.getOrgNo();
			if(null==staffNo||"".equals(staffNo)
				||null==this.ctrlSchemeName||"".equals(this.ctrlSchemeName)){
				this.success=false;
				return SUCCESS;
			}
			List<WTmnlMonPctrl> tmnlTotalList=parseSchemeTmnlTotalList(staffNo);
			if(null==tmnlTotalList||0==tmnlTotalList.size()){
	            this.success=false;
				return SUCCESS;
			}
			//先查询该操作员是否有此方案
			List<WCtrlScheme> schemeList =this.iWCtrlSchemeManager.querySchemesByName(staffNo,this.ctrlSchemeName);
			if(null != schemeList && 0 < schemeList.size()){
				this.FLAG = 0;
			}
			else{
				WCtrlScheme scheme = new WCtrlScheme();
				scheme.setCtrlSchemeName(this.ctrlSchemeName);
				scheme.setCtrlSchemeType(CtrlSchemeTypeCode.ENERGY_CTRL);
				scheme.setStaffNo(staffNo);
				scheme.setOrgNo(userOrgNo);
				scheme.setCreateDate(new Date());
				scheme.setCtrlDateStart(dateToSqlDate(this.newStartDate));
				scheme.setCtrlDateEnd(dateToSqlDate(this.newEndDate));
				scheme.setLimitType(this.limitType);
				scheme.setIsValid(1L);		
				scheme.setCtrlLoad(this.ctrlLoad);
				scheme.setSchemeRemark(this.schemeRemark);
				this.success=this.energyCtrlManager.saveScheme(scheme, tmnlTotalList);
				if(true==this.success)
					this.FLAG = 1;
			}
			logger.debug("月电量定值控保存方案结束");
	    	return SUCCESS;
	    }catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/
    
    /**
     * 增加月电控方案明细并且保存
     */
    public String saveEnergyScheme() throws Exception{
    	logger.debug("月电量定值控保存方案开始");
	    try{
	    	 PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
			 if(null==pSysUser){
				this.success=false;
				return SUCCESS;
			 }
			 String staffNo = pSysUser.getStaffNo();
			 if(null==staffNo||"".equals(staffNo)){
				 this.success=false;
				 return SUCCESS;
			 }
			List<WTmnlMonPctrl> tmnlTotalList=parseSchemeTmnlTotalList(staffNo);
			if(null==tmnlTotalList||0==tmnlTotalList.size()
					||null==this.schemeId){
	            this.success=false;
				return SUCCESS;
			}
			this.energyCtrlManager.saveSchemeDeal(this.schemeId, tmnlTotalList);
			logger.debug("月电量定值控保存方案结束");
	    	return SUCCESS;
	    }catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    /**
     * 保存月定量定值控方案
     * @throws Exception
     */
    public String updateEnergyScheme()throws Exception{
    	logger.debug("月电量定值控保存方案开始");
	    try{	
	    	 List<WCtrlScheme> schemeList =this.iWCtrlSchemeManager.querySchemesById(this.schemeId);
	    	 if(null==schemeList||0==schemeList.size()){
		   		 this.success=false;
				 return SUCCESS;
		   	 }
	    	 PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
			 if(null==pSysUser){
				this.success=false;
				return SUCCESS;
			 }
			 String staffNo = pSysUser.getStaffNo();
			 if(null==staffNo||"".equals(staffNo)){
				 this.success=false;
				 return SUCCESS;
			 }
		     List<WTmnlMonPctrl> tmnlTotalList=parseSchemeTmnlTotalList(staffNo);
		   	 if(null==tmnlTotalList||0==tmnlTotalList.size()){
		   		 this.success=false;
				 return SUCCESS;
		   	 }
		   	 WCtrlScheme scheme = schemeList.get(0);
	    	 scheme.setCtrlDateStart(dateToSqlDate(this.newStartDate));
			 scheme.setCtrlDateEnd(dateToSqlDate(this.newEndDate));
			 scheme.setLimitType(this.limitType);
			 scheme.setCtrlLoad(this.ctrlLoad);
			 scheme.setSchemeRemark(this.schemeRemark);
			 this.success=this.energyCtrlManager.updateScheme(scheme, tmnlTotalList);
			 logger.debug("月电量定值控保存方案结束");
	    	 return SUCCESS;
	    }catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
     
    /**
	 * 加载限电类型
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
    public String loadLimitType() throws Exception{
    	logger.debug("加载限电类型开始");
    	try{
	    	this.limitTypeList = this.suspensionCtrlManager.queryLimitTypeList();
	    	logger.debug("加载限电类型结束");
	    	return SUCCESS;
    	}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }	

    
    /**
	 * date转化为数据库保存格式的date，如果有错返回当日信息
	 * @param date
	 * @return java.sql.Date
	 */
    private java.sql.Date dateToSqlDate(Date date){
    	if(null == date){
    		return new java.sql.Date(new Date().getTime());
    	}
    	try{
    	    return (new java.sql.Date(date.getTime()));
    	}catch(Exception ex){
    		return new java.sql.Date(new Date().getTime());
    	}
    }
   
    /**
	 * 提取页面选择的终端总加组信息（下发）
	 */
	public List<EnergyControlDto> parseSendTmnlTotalList() {
		if(null==this.energyCtrlList||0==this.energyCtrlList.size())
			return null;
		List<EnergyControlDto> tmnlTotalList = new ArrayList<EnergyControlDto>();
		for (int i = 0; i < this.energyCtrlList.size(); i++) {
			String[] tmnlTotal = this.energyCtrlList.get(i).split("`");
			if("".equals(tmnlTotal[0])||"".equals(tmnlTotal[1])||"".equals(tmnlTotal[2])||("null").equals(tmnlTotal[2])||"".equals(tmnlTotal[3])){
				continue;
			}
			EnergyControlDto bean = new EnergyControlDto();
			bean.setTmnlAssetNo(tmnlTotal[0]);
			bean.setTotalNo(Short.valueOf(tmnlTotal[1]));
			bean.setProtocolCode(tmnlTotal[2]);
			bean.setPowerConst(Long.valueOf(tmnlTotal[3]));
			if(!"".equals(tmnlTotal[4])&&!"null".equals(tmnlTotal[4]))
				bean.setFloatValue(Short.valueOf(tmnlTotal[4]));
			tmnlTotalList.add(bean);
		}
		Collections.sort(tmnlTotalList,new Comparator<EnergyControlDto>(){
			@Override
			public int compare(EnergyControlDto o1, EnergyControlDto o2) {
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		return tmnlTotalList;
	}
	
	/**
	 * 提取页面选择的终端总加组信息（下发）
	 */
	public List<EnergyControlDto> parseTmnlTotalList() {
		if(null==this.energyCtrlList||0==this.energyCtrlList.size())
			return null;
		List<EnergyControlDto> tmnlTotalList = new ArrayList<EnergyControlDto>();
		for (int i = 0; i < this.energyCtrlList.size(); i++) {
			String[] tmnlTotal = this.energyCtrlList.get(i).split("`");
			if("".equals(tmnlTotal[0])||"".equals(tmnlTotal[1])||"".equals(tmnlTotal[2])||("null").equals(tmnlTotal[2])||"".equals(tmnlTotal[3])){
				continue;
			}
			EnergyControlDto bean = new EnergyControlDto();
			bean.setTmnlAssetNo(tmnlTotal[0]);
			bean.setTotalNo(Short.valueOf(tmnlTotal[1]));
			bean.setProtocolCode(tmnlTotal[2]);
			bean.setPowerConst(Long.valueOf(tmnlTotal[3]));
			if(!"".equals(tmnlTotal[4])&&!"null".equals(tmnlTotal[4]))
				bean.setFloatValue(Short.valueOf(tmnlTotal[4]));
			tmnlTotalList.add(bean);
		}
		return tmnlTotalList;
	}
	
	 /**
	 * 提取页面选择的终端总加组信息（用于召测）
	 */
	public List<CtrlParamBean> parseFetchTmnlTotalList() {
		if(null==this.energyCtrlList||0==this.energyCtrlList.size())
			return null;
		List<CtrlParamBean> tmnlTotalList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < this.energyCtrlList.size(); i++) {
			String[] tmnlTotal = this.energyCtrlList.get(i).split("`");
			if("".equals(tmnlTotal[0])||"".equals(tmnlTotal[1])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setTmnlAssetNo(tmnlTotal[0]);
			bean.setTotalNo(Short.valueOf(tmnlTotal[1]));
			tmnlTotalList.add(bean);
		}
		Collections.sort(tmnlTotalList,new Comparator<CtrlParamBean>(){
			@Override
			public int compare(CtrlParamBean o1, CtrlParamBean o2) {
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		return tmnlTotalList;
	}
	 /**
	 * 提取页面选择的终端总加组信息（执行、解除）
	 */
	public List<CtrlParamBean> parseExeRelTmnlTotalList() {
		if(null==this.energyCtrlList||0== this.energyCtrlList.size())
			return null;
		List<CtrlParamBean> tmnlTotalList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < this.energyCtrlList.size(); i++) {
			String[] tmnlTotal = this.energyCtrlList.get(i).split("`");
			if("".equals(tmnlTotal[0])||"".equals(tmnlTotal[1])||"".equals(tmnlTotal[2])||("null").equals(tmnlTotal[2])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setTmnlAssetNo(tmnlTotal[0]);
			bean.setTotalNo(Short.valueOf(tmnlTotal[1]));
			bean.setProtocolCode(tmnlTotal[2]);
			tmnlTotalList.add(bean);
		}
		Collections.sort(tmnlTotalList,new Comparator<CtrlParamBean>(){
			@Override
			public int compare(CtrlParamBean o1, CtrlParamBean o2) {
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		return tmnlTotalList;
	}
	
	 /**
	 * 提取页面选择的终端总加组信息（用于保存方案）
	 */
	public List<WTmnlMonPctrl> parseSchemeTmnlTotalList(String staffNo) {
		if(null==this.energyCtrlList||0==this.energyCtrlList.size())
			return null;
		Date saveTime=new Date();
		List<WTmnlMonPctrl> tmnlTotalList = new ArrayList<WTmnlMonPctrl>();
		for (int i = 0; i < this.energyCtrlList.size(); i++) {
			String[] tmnlTotal = this.energyCtrlList.get(i).split("`");
			if("".equals(tmnlTotal[2])||"".equals(tmnlTotal[3])||"".equals(tmnlTotal[4])){
				return null;
			}
			WTmnlMonPctrl bean = new WTmnlMonPctrl();
			bean.setOrgNo(tmnlTotal[0]);
			bean.setConsNo(tmnlTotal[1]);
			bean.setTmnlAssetNo(tmnlTotal[2]);
			bean.setTotalNo(Short.valueOf(tmnlTotal[3]));
			bean.setPowerConst(Long.valueOf(tmnlTotal[4]));
			if(tmnlTotal.length > 5&&!"".equals(tmnlTotal[5])&&!"null".equals(tmnlTotal[5]))
				bean.setFloatValue(Short.valueOf(tmnlTotal[5]));
			bean.setStaffNo(staffNo);
			bean.setSaveTime(saveTime);
			tmnlTotalList.add(bean);
		}
		return tmnlTotalList;
	}
	
	/**
	 * 获取终端总加组信息（发送）
	 * @param tmnlTotalList
	 * @param tmnlAssetNo
	 * @param totalNo
	 * @return
	 */
	public EnergyControlDto getTmnlTotalFromList(List<EnergyControlDto> tmnlTotalList, String tmnlAssetNo,Short totalNo) {
		if(tmnlAssetNo==null ||"".equals(tmnlAssetNo)||null==totalNo) 
			return null;
		for (int i = 0; i < tmnlTotalList.size(); i++) {
			EnergyControlDto bean = tmnlTotalList.get(i);
			if(tmnlAssetNo.equals(bean.getTmnlAssetNo())&& totalNo==bean.getTotalNo()) {
				return bean;
			}
		}
		return null;
	}
	
	/**
	 * 获取终端信息（发送）
	 * @param tmnlTotalList
	 * @param tmnlAssetNo
	 * @return
	 */
	public EnergyControlDto getTmnlFromList(List<EnergyControlDto> tmnlTotalList, String tmnlAssetNo) {
		if(tmnlAssetNo==null ||"".equals(tmnlAssetNo)) 
			return null;
		for (int i = 0; i < tmnlTotalList.size(); i++) {
			EnergyControlDto bean = tmnlTotalList.get(i);
			if(tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				return bean;
			}
		}
		return null;
	}
    
	/**
	 * 从前台页面传入的终端list中查询终端号为指定编号的总加组list(发送)
	 * @param tmnlTotalList
	 * @param tmnlAssetNo
	 * @return
	 */
	private List<EnergyControlDto> noResponseSendTmnl(List<EnergyControlDto> tmnlTotalList,
			String tmnlAssetNo) {
		if(null==tmnlTotalList || 0>=tmnlTotalList.size()||null==tmnlAssetNo||"".equals(tmnlAssetNo)) {
			return null;
		}
		List<EnergyControlDto> resList = new ArrayList<EnergyControlDto>();
		for (int i = 0; i < tmnlTotalList.size(); i++) {
			EnergyControlDto bean = tmnlTotalList.get(i);
			if(tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				resList.add(bean);
			}
		}
		return resList;
	}
	
	
	
	/**
	 * 根据终端资产编号获取终端规约编码（执行、解除）
	 * @param tmnlTotalList
	 * @param tmnlAssetNo
	 * @return
	 */
	public String getProtocolCodeByTmnlAssetNo(List<CtrlParamBean> tmnlTotalList, String tmnlAssetNo) {
		if(tmnlAssetNo==null ||"".equals(tmnlAssetNo)) return "";
		for (int i = 0; i < tmnlTotalList.size(); i++) {
			CtrlParamBean bean = tmnlTotalList.get(i);
			if(tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				return bean.getProtocolCode();
			}
		}
		return "";
	}
	
	/**
	 * 从前台页面传入的终端list中查询终端号为指定编号的总加组list（执行、解除）
	 * @param tmnlTotalList
	 * @param tmnlAssetNo
	 * @return
	 */
	private List<CtrlParamBean> noResponseTmnl(List<CtrlParamBean> tmnlTotalList,String tmnlAssetNo) {
		if(tmnlTotalList==null || tmnlTotalList.size()<=0 ||tmnlAssetNo==null) {
			return null;
		}
		List<CtrlParamBean> resList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < tmnlTotalList.size(); i++) {
			CtrlParamBean bean = tmnlTotalList.get(i);
			if(tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				resList.add(bean);
			}
		}
		return resList;
	}
	
	/**
	 * 设置定值浮动系数
	 * @return
	 */
	public ParamItem setFloatItem(EnergyControlDto bean){
		ParamItem floatItem=new ParamItem();
		floatItem.setFn((short) 20);//设置FN号
		floatItem.setPoint((short) 0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
		List<Item> items=new ArrayList<Item>();
		//编码对象集合
		Item item= new Item(bean.getProtocolCode()+"0414001");
		item.setValue(String.valueOf(bean.getFloatValue()));
		items.add(item);
		floatItem.setItems((ArrayList<Item>) items);
		return floatItem;
	}
	
	/**
	 * 设置月电量定值参数
	 * @return
	 */
	public ParamItem setPowerConstItem(EnergyControlDto bean){
		ParamItem powerConstItem=new ParamItem();
		powerConstItem.setFn((short) 46);//设置FN号
		powerConstItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
		List<Item> items=new ArrayList<Item>();
		//编码对象集合
		Item item = new Item(bean.getProtocolCode()+"042E001");
		item.setValue(String.valueOf(bean.getPowerConst()));
		items.add(item);
		powerConstItem.setItems((ArrayList<Item>) items);
		return powerConstItem;
	}
//	public Long getPowerConst() {
//		return powerConst;
//	}
//
//	public void setPowerConst(Long powerConst) {
//		this.powerConst = powerConst;
//	}
//
//	public Short getFloatValue() {
//		return floatValue;
//	}
//
//	public void setFloatValue(Short floatValue) {
//		this.floatValue = floatValue;
//	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public Date getNewStartDate() {
		return newStartDate;
	}

	public void setNewStartDate(Date newStartDate) {
		this.newStartDate = newStartDate;
	}

	public Date getNewEndDate() {
		return newEndDate;
	}

	public void setNewEndDate(Date newEndDate) {
		this.newEndDate = newEndDate;
	}


	public String getCtrlSchemeName() {
		return ctrlSchemeName;
	}

	public void setCtrlSchemeName(String ctrlSchemeName) {
		this.ctrlSchemeName = ctrlSchemeName;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public List<WCtrlScheme> getSchemeList() {
		return schemeList;
	}

	public List<EnergyControlDto> getUserGridList() {
		return userGridList;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public List<VwLimitType> getLimitTypeList() {
		return limitTypeList;
	}


	public void setiLOpTmnlLogManager(ILOpTmnlLogManager iLOpTmnlLogManager) {
		this.iLOpTmnlLogManager = iLOpTmnlLogManager;
	}

	public Boolean getCheckSchemeName() {
		return checkSchemeName;
	}

	public void setCheckSchemeName(Boolean checkSchemeName) {
		this.checkSchemeName = checkSchemeName;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getSubsId() {
		return subsId;
	}

	public void setSubsId(String subsId) {
		this.subsId = subsId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public void setSchemeList(List<WCtrlScheme> schemeList) {
		this.schemeList = schemeList;
	}

	public List<String> getEnergyCtrlList() {
		return energyCtrlList;
	}

	public void setEnergyCtrlList(List<String> energyCtrlList) {
		this.energyCtrlList = energyCtrlList;
	}
    
	public List<EnergyControlDto> getResultList() {
		return resultList;
	}
	public void setResultList(List<EnergyControlDto> resultList) {
		this.resultList = resultList;
	}
	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

//	public String getIsSendSms() {
//		return isSendSms;
//	}
//
//	public void setIsSendSms(String isSendSms) {
//		this.isSendSms = isSendSms;
//	}

	public Integer getFLAG() {
		return FLAG;
	}

	public void setFLAG(Integer fLAG) {
		FLAG = fLAG;
	}

	public List<TmnlExecStatusBean> getTmnlExecStatusList() {
		return tmnlExecStatusList;
	}

	public void setTmnlExecStatusList(List<TmnlExecStatusBean> tmnlExecStatusList) {
		this.tmnlExecStatusList = tmnlExecStatusList;
	}

	public void setLimitTypeList(List<VwLimitType> limitTypeList) {
		this.limitTypeList = limitTypeList;
	}

	public void setUserGridList(List<EnergyControlDto> userGridList) {
		this.userGridList = userGridList;
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
	public Double getCtrlLoad() {
		return ctrlLoad;
	}
	public void setCtrlLoad(Double ctrlLoad) {
		this.ctrlLoad = ctrlLoad;
	}
	public String getSchemeRemark() {
		return schemeRemark;
	}
	public void setSchemeRemark(String schemeRemark) {
		this.schemeRemark = schemeRemark;
	}
	
}
