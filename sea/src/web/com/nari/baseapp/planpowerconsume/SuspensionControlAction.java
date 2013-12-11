package com.nari.baseapp.planpowerconsume;

import java.text.SimpleDateFormat;
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
import com.nari.orderlypower.SuspensionControlDto;
import com.nari.orderlypower.TmnlExecStatusBean;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlBusiness;
import com.nari.orderlypower.WTmnlCtrlStatus;
import com.nari.privilige.PSysUser;
import com.nari.util.CtrlSchemeTypeCode;

/**
 * 营业报停控Action
 * @author 姜海辉
 */
public class SuspensionControlAction extends BaseAction{
	//定义日志
	private static Logger logger = Logger.getLogger(SuspensionControlAction.class);
		
	//自动注入营业报停控业务层 
	private SuspensionCtrlManager suspensionCtrlManager;
	
	//自动注入日志处理业务层 
	private ILOpTmnlLogManager iLOpTmnlLogManager;
	private WtmnlCtrlStatusManger wtmnlCtrlStatusManger;
	
	//自动注入控制方案业务层
	private IWCtrlSchemeManager iWCtrlSchemeManager;
	
	public void setSuspensionCtrlManager(SuspensionCtrlManager suspensionCtrlManager) {
		this.suspensionCtrlManager = suspensionCtrlManager;
	}
	
	public void setiLOpTmnlLogManager(ILOpTmnlLogManager iLOpTmnlLogManager) {
		this.iLOpTmnlLogManager = iLOpTmnlLogManager;
	}
	 
	public void setWtmnlCtrlStatusManger(WtmnlCtrlStatusManger wtmnlCtrlStatusManger) {
		this.wtmnlCtrlStatusManger = wtmnlCtrlStatusManger;
	}

	public void setiWCtrlSchemeManager(IWCtrlSchemeManager iWCtrlSchemeManager) {
		this.iWCtrlSchemeManager = iWCtrlSchemeManager;
	}

	//成功返回标记
	public boolean success = true;
	
	private Date startSusDate;//开始时间
	private Date endSusDate;//结束时间
	private Float powerCtrlData;//报停控功率定值
	private String orgNo;        //供电单位编号
	private String consNo;       //用户编号
	private String tmnlAssetNo;    //终端资产号
	private String totalNo;      //总加组号
	private Long schemeId;    //方案id
	private Date newStartDate;//新添加方案开始时间
	private Date newEndDate;//新添加结束时间
	//private String limitType; //限电类型
	private String ctrlSchemeName;  //方案名
	private String nodeType;//节点类型
	private String operType;//方案操作类型
	private Boolean checkSchemeName;//方案名校验结果，true表示存在，false表示不存在
	private String groupNo;//组号
	private String subsId;//变电站标识
	private String lineId;//线路编号
	private String orgType;//供电单位类型
	private Integer taskSecond=30;    //任务执行时间
	//private String isSendSms;    //是否发送短信
	private Integer  FLAG;       //任务执行状态
	private String schemeRemark;               //方案备注
	private List<String> suspensionCtrlList;  //终端总加组信息
	private List<WCtrlScheme> schemeList;//方案列表，根据方案type查询
	private List<VwLimitType> limitTypeList;//限电类型列表
	private List<SuspensionControlDto> userGridList;//Grid数据列表
	private List<SuspensionControlDto> resultList;//执行召测命令后的返回结果
	private List<TmnlExecStatusBean> tmnlExecStatusList;//终端执行状态列表
	private String cacheAndTmnlStatus;
	public List<WCtrlScheme> getSchemeList() {
		return schemeList;
	}
	 
	public List<VwLimitType> getLimitTypeList() {
		return limitTypeList;
	}

	public List<SuspensionControlDto> getUserGridList() {
		return userGridList;
	}	

	public List<SuspensionControlDto> getResultList() {
		return resultList;
	}

	//private Boolean Debug = true;
	
	/**
	 * 营业报停控发送
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	public String sendout() throws Exception{
		logger.debug("营业报停控下发开始");
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
			List<CtrlParamBean> tmnlTotalList=parseTmnlTotalList();
			if(null==tmnlTotalList||0==tmnlTotalList.size()||null==this.startSusDate||null==this.endSusDate||null==this.powerCtrlData){
				this.success=false;
				return SUCCESS;
			}
			//操作终端时间
			Date OpTime=new Date();
			CtrlParamBean flagBean = tmnlTotalList.get(0);
			//终端资产号集合
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(flagBean.getTmnlAssetNo());//加入终端资产编号列表
			//参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
	    	ParamItem pitem = new ParamItem();
			pitem.setFn((short) 44);//设置FN号
			pitem.setPoint(flagBean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
			//编码对象集合
			List<Item> items = new ArrayList<Item>();
			Item item = null;
			item = new Item(flagBean.getProtocolCode()+"042C001");
			item.setValue(dateToString(this.startSusDate));
			items.add(item);
			item = new Item(flagBean.getProtocolCode()+"042C002");
			item.setValue(dateToString(this.endSusDate));
			items.add(item);
			item = new Item(flagBean.getProtocolCode()+"042C003");
			item.setValue(String.valueOf(this.powerCtrlData));
			items.add(item);
			pitem.setItems((ArrayList<Item>) items);
			paramList.add(pitem);
			if(1==tmnlTotalList.size()){
				//下发
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
			}
			for(int i = 1; i < tmnlTotalList.size(); i++){
				CtrlParamBean bean = tmnlTotalList.get(i);
				//参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
					//下发
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					//一个终端组一个包，遇到终端不同的要新建paramList对象
					paramList = new ArrayList<ParamItem>();
					flagBean = bean;
				}
				pitem = new ParamItem();
				pitem.setFn((short) 44);//设置FN号
				pitem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				//编码对象集合
				items = new ArrayList<Item>();
				item = new Item(bean.getProtocolCode()+"042C001");
				item.setValue(dateToString(this.startSusDate));
				items.add(item);
				item = new Item(bean.getProtocolCode()+"042C002");
				item.setValue(dateToString(this.endSusDate));
				items.add(item);
				item = new Item(bean.getProtocolCode()+"042C003");
				item.setValue(String.valueOf(this.powerCtrlData));
				items.add(item);
				pitem.setItems((ArrayList<Item>) items);
				paramList.add(pitem);
				if(tmnlTotalList.size()-1==i) {
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
				}
			}
			//等待下发结果
			List<Response> respList = taskDeal.getAllResponse(this.taskSecond);
			//前置机返回结果
			this.tmnlExecStatusList = new ArrayList<TmnlExecStatusBean>();
			if (null != respList && 0 < respList.size()) {
				Response res=null;
				LOpTmnlLog log =null;
				for(int i=0;i<respList.size();i++){
					res = (Response) respList.get(i);
					//终端返回状态
					short Status = res.getTaskStatus();
					// 写日志
					log = getLOpTmnlLog();
					log.setOpModule("营业报停控");
					log.setOpButton("参数下发");
					log.setOpType((short)1);
					log.setOpObj((short)4);
					log.setTmnlAssetNo(res.getTmnlAssetNo());
					log.setOpTime(OpTime);
					log.setCurStatus(String.valueOf(Status));
					if (Status==3||Status==4){
						String tProtocolCode=getProtocolCodeByTmnlAssetNo(tmnlTotalList,res.getTmnlAssetNo());
						for(int j = 0; j < res.getResponseItems().size(); j++) {
							short toNo=res.getResponseItems().get(j).getPn();
							log.setOpObjNo((long)toNo);
							log.setProtItemNo(tProtocolCode+"042C001");
							log.setCurValue(dateToString(this.startSusDate));
							this.iLOpTmnlLogManager.saveTmnlLog(log);
							log.setProtItemNo(tProtocolCode+"042C002");
							log.setCurValue(dateToString(this.endSusDate));
							this.iLOpTmnlLogManager.saveTmnlLog(log);
							log.setProtItemNo(tProtocolCode+"042C003");
							log.setCurValue(String.valueOf(this.powerCtrlData));
							this.iLOpTmnlLogManager.saveTmnlLog(log);
							//返回执行状态
							TmnlExecStatusBean tmnlexecStatus=new TmnlExecStatusBean();
							tmnlexecStatus.setKeyId(res.getTmnlAssetNo()+ String.valueOf(toNo));
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
						List<CtrlParamBean> noResponseList = noResponseTmnl(tmnlTotalList, res.getTmnlAssetNo());
						if(null==noResponseList) continue;
						for (int j = 0; j < noResponseList.size(); j++) {
							CtrlParamBean bean = noResponseList.get(j);
							//对未返回的终端要记录日志
							log.setOpObjNo((long)bean.getTotalNo());
							log.setProtItemNo(bean.getProtocolCode()+"042C001");
							log.setCurValue(dateToString(this.startSusDate));
							this.iLOpTmnlLogManager.saveTmnlLog(log);
							log.setProtItemNo(bean.getProtocolCode()+"042C002");
							log.setCurValue(dateToString(this.endSusDate));
							this.iLOpTmnlLogManager.saveTmnlLog(log);
							log.setProtItemNo(bean.getProtocolCode()+"042C003");
							log.setCurValue(String.valueOf(this.powerCtrlData));
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
			logger.debug("营业报停控下发结束");
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 营业报停控召测
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	public String fetch() throws Exception{
		logger.debug("营业报停控召测开始");
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
			CtrlParamBean flagBean = tmnlTotalList.get(0);
			//终端资产号集合
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(flagBean.getTmnlAssetNo());//加入终端资产编号列表
			//参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
	    	ParamItem pitem = new ParamItem();
			pitem.setFn((short) 44);//设置FN号
			pitem.setPoint(flagBean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
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
					flagBean = bean;
				}
				pitem = new ParamItem();
				pitem.setFn((short) 44);//设置FN号
				pitem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				pitem.setItems(null);
				paramList.add(pitem);
				if(tmnlTotalList.size()-1==i) {
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.QUERY_PARAMS, paramList);
				}
			}
			List<Response> respList = taskDeal.getAllResponse(this.taskSecond);
			//前置机返回结果
			this.resultList = new ArrayList<SuspensionControlDto>();
			if(null != respList && 0 < respList.size()){
				Response res = null;
				SuspensionControlDto sc = null;
				for(int i=0;i<respList.size();i++){
					res = (Response)respList.get(i);
					if (res.getTaskStatus()==3 && null != res.getResponseItems()&& 0 < res.getResponseItems().size()){
						sc = new SuspensionControlDto();
						for(int j = 0; j < res.getResponseItems().size(); j++){
							ResponseItem resItem = res.getResponseItems().get(j);
							if(j%3==0){
								sc.setKeyId(res.getTmnlAssetNo()+ String.valueOf(resItem.getPn()));
								sc.setStopStart(resItem.getValue());
							}
							else if(j%3==1){
								sc.setStopEnd(resItem.getValue());
							}
							else{
								if(null!=resItem.getValue()&&!("").equals(resItem.getValue())){
									sc.setStopConst(Float.valueOf(resItem.getValue()));	
								}
								this.resultList.add(sc);
								sc = new SuspensionControlDto();
							}
						}
					}
				}
			}
			logger.debug("营业报停控召测结束");
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 营业报停功控投入
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	public String act() throws Exception{
		logger.debug("营业报停控投入开始");
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
			List<CtrlParamBean> tmnlTotalList=parseTmnlTotalList();
			if(null==tmnlTotalList||0==tmnlTotalList.size()){
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
			pitem.setFn((short) 11);//设置FN号
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
				pitem.setFn((short) 11);//设置FN号
				pitem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				pitem.setItems(null);
				paramList.add(pitem);
				if(tmnlTotalList.size()-1==i) {
					//投入
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
				}
			}
			List<Response> respList = taskDeal.getAllResponse(this.taskSecond);	
			//返回结果
			this.tmnlExecStatusList = new ArrayList<TmnlExecStatusBean>();		
			if(null != respList && 0 < respList.size()){
				Response res = null;
				for(int i=0;i < respList.size();i++){
					res = (Response) respList.get(i);
					//终端返回状态
					short Status = res.getTaskStatus();
					//写日志
					LOpTmnlLog log = getLOpTmnlLog();
					log.setOpModule("营业报停控");
					log.setOpButton("营业报停投入");
					log.setOpType((short)2);
					log.setOpObj((short)4);
					log.setOpTime(opTime);
					log.setTmnlAssetNo(res.getTmnlAssetNo());
					log.setCurStatus(String.valueOf(Status));
					if (Status==3||Status==4){
						String tProtocolCode=getProtocolCodeByTmnlAssetNo(tmnlTotalList,res.getTmnlAssetNo());
						for(int j = 0; j < res.getResponseItems().size(); j++) {
							ResponseItem resItem = res.getResponseItems().get(j);
							log.setOpObjNo((long)resItem.getPn());
							log.setProtocolNo(tProtocolCode+"050B");
							this.iLOpTmnlLogManager.saveTmnlLog(log);
							//返回执行状态
							TmnlExecStatusBean tmnlexecStatus=new TmnlExecStatusBean();
							tmnlexecStatus.setKeyId(res.getTmnlAssetNo()+ String.valueOf(resItem.getPn()));
						    if (Status == 3) {
								tmnlexecStatus.setExecFlag("1");
								//对营业报停控制状态属性赋值
			    	    		WTmnlCtrlStatus	ctrlStatus = new WTmnlCtrlStatus();
			    	    		ctrlStatus.setTmnlAssetNo(res.getTmnlAssetNo());
			    	    		ctrlStatus.setTotalNo(resItem.getPn());
			    	    		ctrlStatus.setBusinessFlag((short)1);
			    	    		wtmnlCtrlStatusManger.saveOrUpdateTmnlCtrlStatusBusinessFlag(ctrlStatus);
							 } 
							 else{
								tmnlexecStatus.setExecFlag("0");
							 }
						     this.tmnlExecStatusList.add(tmnlexecStatus);
						}
					}
					else{
						//对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
						List<CtrlParamBean> noResponseList = noResponseTmnl(tmnlTotalList, res.getTmnlAssetNo());
						if(null==noResponseList) continue;
						for (int j = 0; j < noResponseList.size(); j++) {
							CtrlParamBean bean = noResponseList.get(j);
							//对未返回的终端要记录日志
							log.setOpObjNo((long)bean.getTotalNo());
							log.setProtocolNo(bean.getTmnlAssetNo()+"050B");
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
			logger.debug("营业报停控投入结束");
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 营业报停功控解除
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
    public String release() throws Exception{
    	logger.debug("营业报停控解除开始");
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
			List<CtrlParamBean> tmnlTotalList=parseTmnlTotalList();
	    	if(null==tmnlTotalList||0==tmnlTotalList.size()){
	    		this.success=false;
				return SUCCESS;
	    	}
			//操作终端时间
			Date opTime=new Date();	
			CtrlParamBean flagBean = tmnlTotalList.get(0);
			//终端资产号集合
			List<String> tmnlAssetNoList = new ArrayList<String>();
			//参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			tmnlAssetNoList.add(flagBean.getTmnlAssetNo());//加入终端资产编号列表
	    	ParamItem pitem = new ParamItem();
			pitem.setFn((short) 19);//设置FN号
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
				pitem.setFn((short) 19);//设置FN号
				pitem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				pitem.setItems(null);
				paramList.add(pitem);
				if(tmnlTotalList.size()-1==i) {
					//解除
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
				}
			}
			List<Response> respList = taskDeal.getAllResponse(this.taskSecond);	
			//返回结果
			this.tmnlExecStatusList = new ArrayList<TmnlExecStatusBean>();	
			if(null != respList && 0 < respList.size()){
				Response res = null;
				for(int i=0;i < respList.size();i++){
					res = (Response) respList.get(i);
					//终端返回状态
					short Status = res.getTaskStatus();
					//写日志
					LOpTmnlLog log = getLOpTmnlLog();
				    log.setOpModule("营业报停控");
				    log.setOpButton("营业报停解除");
				    log.setOpType((short)2);
					log.setOpObj((short)4);
					log.setOpTime(opTime);
					log.setTmnlAssetNo(res.getTmnlAssetNo());
					log.setCurStatus(String.valueOf(Status));
					if(Status==3||Status==4){
						String tProtocolCode=getProtocolCodeByTmnlAssetNo(tmnlTotalList,res.getTmnlAssetNo());
						for(int j = 0; j < res.getResponseItems().size(); j++) {
							ResponseItem resItem = res.getResponseItems().get(j);
							log.setOpObjNo((long)resItem.getPn());
						    log.setProtocolNo(tProtocolCode+"0513");
						    this.iLOpTmnlLogManager.saveTmnlLog(log);
						    //返回执行状态
							TmnlExecStatusBean tmnlexecStatus=new TmnlExecStatusBean();
							tmnlexecStatus.setKeyId(res.getTmnlAssetNo()+ String.valueOf(resItem.getPn()));
						    if (Status == 3) {
								tmnlexecStatus.setExecFlag("1");
								//对营业报停控制状态属性赋值
								WTmnlCtrlStatus	ctrlStatus = new WTmnlCtrlStatus();
			    	    		ctrlStatus.setTmnlAssetNo(res.getTmnlAssetNo());
			    	    		ctrlStatus.setTotalNo(resItem.getPn());
			    	    		ctrlStatus.setBusinessFlag((short)0);
			    	    		wtmnlCtrlStatusManger.saveOrUpdateTmnlCtrlStatusBusinessFlag(ctrlStatus);
							} 
							else{
								tmnlexecStatus.setExecFlag("0");
							}
						    this.tmnlExecStatusList.add(tmnlexecStatus);
						}
					}
					else{
						//对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
						List<CtrlParamBean> noResponseList = noResponseTmnl(tmnlTotalList, res.getTmnlAssetNo());
						if(null==noResponseList) continue;
						for (int j = 0; j < noResponseList.size(); j++) {
							CtrlParamBean bean = noResponseList.get(j);
							//对未返回的终端要记录日志
							log.setOpObjNo((long)bean.getTotalNo());
							log.setProtocolNo(bean.getTmnlAssetNo()+"0513");
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
			logger.debug("营业报停控解除结束");
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
    	logger.debug("营业报停控加载方案开始");
    	try{
	    	PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
	    	if(null==pSysUser)
	    		return SUCCESS;
			String staffNo = pSysUser.getStaffNo();
	    	this.schemeList = this.iWCtrlSchemeManager.querySchemeListByType(staffNo,CtrlSchemeTypeCode.SUSPENSION_CTRL);
	    	logger.debug("营业报停控加载方案结束");
	    	return SUCCESS;
    	}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    
    /**
	 * 新增加方案，保存为方案界面，点击确定时的事件处理，orgNo是操作员单位
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
    public String saveSusScheme() throws Exception{
    	logger.debug("营业报停控保存方案开始");
		PSysUser pSysUser =(PSysUser)getSession().getAttribute("pSysUser");
		if(null==pSysUser){
			this.success=false;
			return SUCCESS;
		}
		String staffNo=pSysUser.getStaffNo();
		String userOrgNo=pSysUser.getOrgNo();
		List<CtrlParamBean> tmnlTotalList=parseSchemeTmnlTotalList();
		if(null==tmnlTotalList||0==tmnlTotalList.size()
				||null==staffNo||"".equals(staffNo)
				||null==this.ctrlSchemeName||"".equals(this.ctrlSchemeName)){
            this.success=false;
			return SUCCESS;
		}
		try{
			//先查询该操作员是否有重名方案
			List<WCtrlScheme> schemeList =this.iWCtrlSchemeManager.checkSchemeName(staffNo,CtrlSchemeTypeCode.SUSPENSION_CTRL,this.ctrlSchemeName);
			if(null != schemeList && 0 < schemeList.size()){
				this.FLAG = 0;
			}
			else{
				WCtrlScheme scheme = new WCtrlScheme();
				scheme.setCtrlSchemeName(this.ctrlSchemeName);
				scheme.setCtrlSchemeType(CtrlSchemeTypeCode.SUSPENSION_CTRL);
				scheme.setStaffNo(staffNo);
				scheme.setOrgNo(userOrgNo);
				scheme.setCreateDate(new Date());
				scheme.setCtrlDateStart(dateToSqlDate(this.newStartDate));
				scheme.setCtrlDateEnd(dateToSqlDate(this.newEndDate));
				//scheme.setLimitType(this.limitType);
				scheme.setSchemeRemark(this.schemeRemark);
				scheme.setIsValid(1L);
				
				List<WTmnlBusiness> busiList = new ArrayList<WTmnlBusiness>();
				WTmnlBusiness busi = null;
				for(int i = 0; i <tmnlTotalList.size(); i++){
					String tOrgNo=tmnlTotalList.get(i).getOrgNo();
					String tConsNo=tmnlTotalList.get(i).getConsNo();
					String tTmnlAssetNo=tmnlTotalList.get(i).getTmnlAssetNo(); 
					short  tTotalNo=tmnlTotalList.get(i).getTotalNo();
					busi = new WTmnlBusiness();
					busi.setOrgNo(tOrgNo);
					busi.setConsNo(tConsNo);
					busi.setTmnlAssetNo(tTmnlAssetNo);
					busi.setTotalNo(tTotalNo);
					busi.setStopStart(dateToSqlDate(this.startSusDate));
					busi.setStopEnd(dateToSqlDate(this.endSusDate));
					busi.setStopConst(this.powerCtrlData);
					//busi.setIsSendSms(false);
					busiList.add(busi);
				}
				this.success=this.suspensionCtrlManager.saveScheme(scheme, busiList);
				if(true==this.success)
					this.FLAG = 1;
			}
			logger.debug("营业报停控保存方案结束");
	    	return SUCCESS;
	    }catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    
    
    /**
     * 保存营业报停控方案
     * @return
     * @throws Exception
     */
    public String updateSusScheme() throws Exception{
    	try{
	    	 List<WCtrlScheme> schemeList =this.iWCtrlSchemeManager.querySchemesById(this.schemeId);
	    	 if(null==schemeList||0==schemeList.size()){
	    		 this.success=false;
				 return SUCCESS;
	    	 }
	    	 List<CtrlParamBean> tmnlTotalList=parseSchemeTmnlTotalList();
	    	 if(null==tmnlTotalList||0==tmnlTotalList.size()){
	    		 this.success=false;
	  			 return SUCCESS; 
	    	 }
	    	 WCtrlScheme scheme = schemeList.get(0);
	    	 scheme.setCtrlDateStart(dateToSqlDate(this.newStartDate));
			 scheme.setCtrlDateEnd(dateToSqlDate(this.newEndDate));
			 //scheme.setLimitType(this.limitType);
			 //scheme.setIsValid(1L);
			 scheme.setSchemeRemark(this.schemeRemark);
			 List<WTmnlBusiness> busiList = new ArrayList<WTmnlBusiness>();
			 WTmnlBusiness busi = null;
			 for(int i = 0; i <tmnlTotalList.size(); i++){
				String tOrgNo=tmnlTotalList.get(i).getOrgNo();
				String tConsNo=tmnlTotalList.get(i).getConsNo();
				String tTmnlAssetNo=tmnlTotalList.get(i).getTmnlAssetNo(); 
				short  tTotalNo=tmnlTotalList.get(i).getTotalNo();
				busi = new WTmnlBusiness();
				busi.setOrgNo(tOrgNo);
				busi.setConsNo(tConsNo);
				busi.setTmnlAssetNo(tTmnlAssetNo);
				busi.setTotalNo(tTotalNo);
				busi.setCtrlSchemeId(this.schemeId);
				busi.setStopStart(dateToSqlDate(this.startSusDate));
				busi.setStopEnd(dateToSqlDate(this.endSusDate));
				busi.setStopConst(this.powerCtrlData);
				//busi.setIsSendSms(false);
				busiList.add(busi);
			 }
			 this.success=this.suspensionCtrlManager.updateScheme(scheme,busiList);
			 logger.debug("营业报停控保存方案结束");
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
	 * date转化为String，如果有错返回当日信息
	 * @param date
	 * @return String 
	 */
    private String dateToString(Date date){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	try{
    	    return format.format(date); 
    	}catch(Exception ex){
    		return format.format(new Date());
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
	 * 加载Grid数据
	 * @return String 
	 * @throws Exception
	 */
    public String loadGridData() throws Exception{
    	logger.debug("营业报停控加载用户信息开始");
    	if(null == nodeType || "".equals(nodeType)){
    		return SUCCESS;
    	}
    	try{
	    	PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
	    	if("org".equals(nodeType)){
	    		this.userGridList = this.suspensionCtrlManager.querySuspCtrlGridByOrgNo(this.orgNo,this.orgType,pSysUser);
	    	}
	    	else if("usr".equals(nodeType)){
	    		this.userGridList = this.suspensionCtrlManager.querySuspCtrlGridByTmnlAssetNo(this.tmnlAssetNo);
	    	}
	        else if("line".equals(nodeType)){
	        	this.userGridList = this.suspensionCtrlManager.querySuspCtrlGridByLineId(this.lineId,pSysUser);
	    	}
	        else if("cgp".equals(nodeType) || "ugp".equals(nodeType)){
	        	this.userGridList = this.suspensionCtrlManager.querySuspCtrlGridByGroupNo(this.nodeType,this.groupNo);
	    	}
	        else if("sub".equals(nodeType)){
	        	this.userGridList = this.suspensionCtrlManager.querySuspCtrlGridBySubsId(this.subsId,pSysUser);
	    	}
	        else if("schemeId".equals(nodeType)){
	        	this.userGridList = this.suspensionCtrlManager.querySuspCtrlGridBySchemeId(this.schemeId);
	    	}
	    	logger.debug("营业报停控加载用户信息结束");
	    	return SUCCESS;
    	 }catch (Exception e) {
  			e.printStackTrace();
  			throw e;
  		}
    }
    
    /**
	 * 提取页面选择的终端总加组信息（下发、执行、解除）
	 */
	public List<CtrlParamBean> parseTmnlTotalList() {
		if(null==this.suspensionCtrlList||0== this.suspensionCtrlList.size())
			return null;
		List<CtrlParamBean> tmnlTotalList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < this.suspensionCtrlList.size(); i++) {
			String[] tmnlTotal = this.suspensionCtrlList.get(i).split("`");
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
	 * 提取页面选择的终端总加组信息（用于召测）
	 */
	public List<CtrlParamBean> parseFetchTmnlTotalList() {
		if(null==this.suspensionCtrlList||0== this.suspensionCtrlList.size())
			return null;
		List<CtrlParamBean> tmnlTotalList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < this.suspensionCtrlList.size(); i++) {
			String[] tmnlTotal = this.suspensionCtrlList.get(i).split("`");
			if("".equals(tmnlTotal[0])||"".equals(tmnlTotal[1])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setTmnlAssetNo(tmnlTotal[0]);
			bean.setTotalNo(Short.valueOf(tmnlTotal[1]));
			tmnlTotalList.add(bean);
		}
		return tmnlTotalList;
	}
	
	 /**
	 * 提取页面选择的终端总加组信息（方案）
	 */
	public List<CtrlParamBean> parseSchemeTmnlTotalList() {
		if(null==this.suspensionCtrlList||0==this.suspensionCtrlList.size())
			return null;
		List<CtrlParamBean> tmnlTotalList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < this.suspensionCtrlList.size(); i++) {
			String[] tmnlTotal = this.suspensionCtrlList.get(i).split("`");
			if("".equals(tmnlTotal[2])||"".equals(tmnlTotal[3])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setOrgNo(tmnlTotal[0]);
			bean.setConsNo(tmnlTotal[1]);
			bean.setTmnlAssetNo(tmnlTotal[2]);
			bean.setTotalNo(Short.valueOf(tmnlTotal[3]));
			tmnlTotalList.add(bean);
		}
		return tmnlTotalList;
	}
	
/*	*//**
     * 获取工号   
     * @return
     * @throws Exception
     *//*
    private String getStaffNo() throws Exception{
    	PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
		String staffNo = pSysUser.getStaffNo();
		return staffNo;
    }*/
	/**
	 * 根据终端资产编号获取终端规约编码
	 * @param tmnlList
	 * @param tmnlAssetNo
	 * @return
	 */
	public String getProtocolCodeByTmnlAssetNo(List<CtrlParamBean> tmnlList, String tmnlAssetNo) {
		if(tmnlAssetNo==null ||"".equals(tmnlAssetNo)) return "";
		for (int i = 0; i < tmnlList.size(); i++) {
			CtrlParamBean bean = tmnlList.get(i);
			if(tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				return bean.getProtocolCode();
			}
		}
		return "";
	}
	
	/**
	 * 从前台页面传入的终端list中查询终端号为指定编号的总加组list
	 * @param tmnlList
	 * @param tmnlAssetNo
	 * @return
	 */
	private List<CtrlParamBean> noResponseTmnl(List<CtrlParamBean> tmnlList,String tmnlAssetNo) {
		if(tmnlList==null || tmnlList.size()<=0 ||tmnlAssetNo==null) {
			return null;
		}
		List<CtrlParamBean> resList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < tmnlList.size(); i++) {
			CtrlParamBean bean = tmnlList.get(i);
			if(tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				resList.add(bean);
			}
		}
		return resList;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Date getStartSusDate() {
		return startSusDate;
	}

	public void setStartSusDate(Date startSusDate) {
		this.startSusDate = startSusDate;
	}

	public Date getEndSusDate() {
		return endSusDate;
	}

	public void setEndSusDate(Date endSusDate) {
		this.endSusDate = endSusDate;
	}

	
	public Float getPowerCtrlData() {
		return powerCtrlData;
	}

	public void setPowerCtrlData(Float powerCtrlData) {
		this.powerCtrlData = powerCtrlData;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
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

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(String totalNo) {
		this.totalNo = totalNo;
	}

	public Boolean getCheckSchemeName() {
		return checkSchemeName;
	}

	public void setCheckSchemeName(Boolean checkSchemeName) {
		this.checkSchemeName = checkSchemeName;
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

	public List<String> getSuspensionCtrlList() {
		return suspensionCtrlList;
	}

	public void setSuspensionCtrlList(List<String> suspensionCtrlList) {
		this.suspensionCtrlList = suspensionCtrlList;
	}

	public Integer getFLAG() {
		return FLAG;
	}

	public void setFLAG(Integer fLAG) {
		FLAG = fLAG;
	}

	public void setLimitTypeList(List<VwLimitType> limitTypeList) {
		this.limitTypeList = limitTypeList;
	}

	public void setResultList(List<SuspensionControlDto> resultList) {
		this.resultList = resultList;
	}

	public void setSchemeList(List<WCtrlScheme> schemeList) {
		this.schemeList = schemeList;
	}

	public void setUserGridList(List<SuspensionControlDto> userGridList) {
		this.userGridList = userGridList;
	}

	public List<TmnlExecStatusBean> getTmnlExecStatusList() {
		return tmnlExecStatusList;
	}

	public void setTmnlExecStatusList(List<TmnlExecStatusBean> tmnlExecStatusList) {
		this.tmnlExecStatusList = tmnlExecStatusList;
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

	public String getSchemeRemark() {
		return schemeRemark;
	}

	public void setSchemeRemark(String schemeRemark) {
		this.schemeRemark = schemeRemark;
	}

//	public String getIsSendSms() {
//		return isSendSms;
//	}
//
//	public void setIsSendSms(String isSendSms) {
//		this.isSendSms = isSendSms;
//	}
	
}
